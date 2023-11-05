package com.artjomkuznetsov.healthhub.security.auth;

import com.artjomkuznetsov.healthhub.controllers.MedCardController;
import com.artjomkuznetsov.healthhub.controllers.UserController;
import com.artjomkuznetsov.healthhub.models.Doctor;
import com.artjomkuznetsov.healthhub.models.MedCard;
import com.artjomkuznetsov.healthhub.models.User;
import com.artjomkuznetsov.healthhub.repositories.DoctorRepository;
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

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;


@Service
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final MedCardController medCardController;
    private final UserController userController;
    private final DoctorRepository doctorRepository;



    public AuthenticationService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService,
            AuthenticationManager authenticationManager,
            MedCardController medCardController,
            UserController userController,
            DoctorRepository doctorRepository
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.medCardController = medCardController;
        this.userController = userController;
        this.doctorRepository = doctorRepository;
    }

    public AuthenticationResponse register(User newUser) {
        try {
            isUsernameNotTaken(newUser.getEmail());
        } catch (UsernameAlreadyTakenException e) {
            return new AuthenticationResponse("Email is taken");
        }

        newUser.setUuid(generateUUID());
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        userRepository.save(newUser);
        setUidToNewMedCard(newUser);

        Map<String, Long> idClaim = new HashMap<>();
        idClaim.put("id", newUser.getId());

        String jwtToken =jwtService.generateToken(idClaim, newUser);

        return new AuthenticationResponse(jwtToken, newUser.getId(), newUser.getMedCardID(), newUser.getRole());
    }

    private void setUidToNewMedCard(User newEntity) {
        if (newEntity instanceof Doctor) {
            System.out.println("doc");
            Doctor entity = doctorRepository.findByEmail(newEntity.getEmail()).get();
            createMedCard(entity);
        } else if (newEntity instanceof User) {
            System.out.println("user");
            User entity = userRepository.findByEmail(newEntity.getEmail()).get();
            createMedCard(entity);
        }
    }

    private void createMedCard(User entity) {
        Long uid = entity.getId();
        ResponseEntity<?> medCard = medCardController.newMedCard(new MedCard(uid));
        EntityModel<MedCard> entityMedCard = (EntityModel<MedCard>) medCard.getBody();
        Long medCardId = entityMedCard.getContent().getId();
        setMedCardIdToUser(entity, medCardId);
    }

    private User setMedCardIdToUser(User entity, Long medCardId) {
        entity.setMedCardID(medCardId);
        ResponseEntity<?> responseEntity = userController.replaceUser(entity, entity.getId());
        EntityModel<User> entityModel = (EntityModel<User>) responseEntity.getBody();
        User resultUser = entityModel.getContent();
        return resultUser;
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));

        Map<String, Long> idClaim = new HashMap<>();
        idClaim.put("id", user.getId());

        String jwtToken = jwtService.generateToken(idClaim, user);

        return new AuthenticationResponse(jwtToken, user.getId(), user.getMedCardID(), user.getRole());
    }

    private boolean isUsernameNotTaken(String username) {
        System.out.println(username);
        System.out.println(userRepository.findByEmail(username).isEmpty());
        if (userRepository.findByEmail(username).isEmpty()) {
            System.out.println("sc");
            return true;
        } else {
            throw new UsernameAlreadyTakenException(username);
        }
    }

    public String generateUUID() {
        String[] numbers = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "0"};
        StringBuilder uuid = new StringBuilder("");
        Random rand = new Random();
        for (int i = 0; i<10; i++) {
            int randNum = rand.nextInt(0, 10);
            uuid.append(numbers[randNum]);
        }
        if (userRepository.findByUuid(uuid.toString()).equals(Optional.empty()) && doctorRepository.findByUuid(uuid.toString()).equals(Optional.empty())) {
            return uuid.toString();
        } else {
            return generateUUID();
        }
    }



}
