package com.artjomkuznetsov.healthhub.security.auth;

import com.artjomkuznetsov.healthhub.controllers.MedCardController;
import com.artjomkuznetsov.healthhub.controllers.UserController;
import com.artjomkuznetsov.healthhub.models.MedCard;
import com.artjomkuznetsov.healthhub.models.User;
import com.artjomkuznetsov.healthhub.repositories.UserRepository;
import com.artjomkuznetsov.healthhub.security.auth.exceptions.UsernameAlreadyTakenException;
import com.artjomkuznetsov.healthhub.security.config.JwtService;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class AuthenticationService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final MedCardController medCardController;
    private final UserController userController;

    public AuthenticationService(UserRepository repository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager, MedCardController medCardController, UserController userController) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.medCardController = medCardController;
        this.userController = userController;
    }

    public AuthenticationResponse register(User newUser) {
        try {
            isUsernameEmpty(newUser.getEmail());
        } catch (UsernameAlreadyTakenException e) {
            return new AuthenticationResponse("Email is taken");
        }


        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        repository.save(newUser);
        setUidToNewMedCard(newUser);

        String jwtToken =jwtService.generateToken(newUser);
        return new AuthenticationResponse(jwtToken, newUser.getId());
    }


    /*

        1. get user from repository by email
        2. get user id
        3. create medcard and pass the user id
        4. get new medcard's id
        5. set to the user his medcardid
         */
    private void setUidToNewMedCard(User newUser) {
        User user = repository.findByEmail(newUser.getEmail()).get();
        Long uid = user.getId();
        ResponseEntity<?> medCard = medCardController.newMedCard(new MedCard(uid));
        EntityModel<MedCard> entityMedCard = (EntityModel<MedCard>) medCard.getBody();
        Long medCardId = entityMedCard.getContent().getId();
        setMedCardIdToUser(user, medCardId);


    }

    private User setMedCardIdToUser(User user, Long medCardId) {
        user.setMedCardID(medCardId);
        ResponseEntity<?> responseEntity = userController.replaceUser(user, user.getId());
        EntityModel<User> entityModel = (EntityModel<User>) responseEntity.getBody();
        User user1 = entityModel.getContent();
        return user1;
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
