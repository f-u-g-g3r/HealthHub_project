package com.artjomkuznetsov.healthhub.security.config;


import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Collection;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.io.IOException;
import java.util.Arrays;
import java.util.function.Function;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private static final Logger logger = Logger.getLogger(JwtAuthenticationFilter.class.getName());

    @Autowired
    public JwtAuthenticationFilter(JwtService jwtService, UserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;
        Long id = null;

        boolean isPermitted = false;

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            logger.info("Authorization header is missing or does not start with 'Bearer'");
            filterChain.doFilter(request, response);
            return;
        }

        jwt = authHeader.substring(7); // cutting word "Bearer " from our token
        userEmail = jwtService.extractUsername(jwt);

        Long userId = jwtService.extractId(jwt);

        String requestURI = request.getRequestURI();
        String[] uriParts = requestURI.split("/");

        for (String part : uriParts) {
            logger.info("Checking part of URI: " + part);
            if (part.equals("activated") || part.equals("inactivated")) {
                isPermitted = true;
            }

            if (part.equals("uuid") && jwtService.extractRole(jwt).equals("DOCTOR")) {
                isPermitted = true;
            }
            if (part.equals("family-doctor") && jwtService.extractRole(jwt).equals("DOCTOR") ||
                part.equals("family-doctor") && jwtService.extractRole(jwt).equals("USER")) {
                isPermitted = true;
            }

            if (part.equals("users") && jwtService.extractRole(jwt).equals("DOCTOR")) {
                isPermitted = true;
            }

            if (part.equals("calendars") && jwtService.extractRole(jwt).equals("DOCTOR") ||
                    part.equals("availableTime") && jwtService.extractRole(jwt).equals("USER") ||
                    part.equals("schedule") && jwtService.extractRole(jwt).equals("USER")
            ) {
                isPermitted = true;
            }

            if (part.equals("user-appointments") && jwtService.extractRole(jwt).equals("USER") ||
                part.equals("schedules") && request.getMethod().equals("DELETE") && jwtService.extractRole(jwt).equals("USER")){
                if (uriParts.length > 2) {
                    id = Long.parseLong(uriParts[uriParts.length-1]);
                    if (id.equals(userId)) {
                        isPermitted = true;
                    }
                } else {
                    filterChain.doFilter(request, response);
                    return;
                }
            }

            if (part.equals("med-cards") && jwtService.extractRole(jwt).equals("DOCTOR")) {
                isPermitted = true;
            }
        }
        logger.info("isPermitted: " + isPermitted);

        if (!isPermitted) {
            if (uriParts.length > 2) {
                id = Long.parseLong(uriParts[2]);
            } else {
                filterChain.doFilter(request, response);
                return;
            }
        }

        logger.info("firstIf: " + (isPermitted || userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null && id.equals(userId) || jwtService.extractRole(jwt).equals("ADMIN")));
        if (isPermitted || userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null && id.equals(userId) || jwtService.extractRole(jwt).equals("ADMIN")) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
            logger.info("secondIf: " + jwtService.isTokenValid(jwt, userDetails));
            if (jwtService.isTokenValid(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}
