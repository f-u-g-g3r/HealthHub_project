package com.artjomkuznetsov.healthhub.security.auth;

import com.artjomkuznetsov.healthhub.models.User;
import com.artjomkuznetsov.healthhub.repositories.UserRepository;
import com.artjomkuznetsov.healthhub.security.auth.exceptions.UsernameAlreadyTakenException;
import com.artjomkuznetsov.healthhub.security.config.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(UserRepository repository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public AuthenticationResponse register(User newUser) {
        try {
            isUsernameEmpty(newUser.getEmail());
        } catch (UsernameAlreadyTakenException e) {
            return new AuthenticationResponse("Email is taken");
        }


        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        repository.save(newUser);
        String jwtToken =jwtService.generateToken(newUser);
        return new AuthenticationResponse(jwtToken, newUser.getId());
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        User user = repository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));
        String jwtToken = jwtService.generateToken(user);
        return new AuthenticationResponse(jwtToken, user.getId());
    }

    private boolean isUsernameEmpty(String username) {
        if (repository.findByEmail(username).isEmpty()) {
            return true;
        } else {
            throw new UsernameAlreadyTakenException(username);
        }

    }

}
