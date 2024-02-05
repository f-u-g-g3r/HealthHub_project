package com.artjomkuznetsov.healthhub.security.auth;

import com.artjomkuznetsov.healthhub.controllers.MedCardController;
import com.artjomkuznetsov.healthhub.controllers.UserController;
import com.artjomkuznetsov.healthhub.models.Calendar;
import com.artjomkuznetsov.healthhub.models.Doctor;
import com.artjomkuznetsov.healthhub.models.MedCard;
import com.artjomkuznetsov.healthhub.models.User;
import com.artjomkuznetsov.healthhub.repositories.CalendarRepository;
import com.artjomkuznetsov.healthhub.repositories.DoctorRepository;
import com.artjomkuznetsov.healthhub.repositories.MedCardRepository;
import com.artjomkuznetsov.healthhub.repositories.UserRepository;
import com.artjomkuznetsov.healthhub.security.auth.exceptions.AgeIsNotValidException;
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
    private final CalendarRepository calendarRepository;
    private final MedCardRepository medCardRepository;



    public AuthenticationService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService,
            AuthenticationManager authenticationManager,
            MedCardController medCardController,
            UserController userController,
            DoctorRepository doctorRepository,
            CalendarRepository calendarRepository,
            MedCardRepository medCardRepository
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.medCardController = medCardController;
        this.userController = userController;
        this.doctorRepository = doctorRepository;
        this.calendarRepository = calendarRepository;
        this.medCardRepository = medCardRepository;
    }

    public AuthenticationResponse registerUser(User newUser) {
        try {
            isUsernameNotTaken(newUser.getEmail());
        } catch (UsernameAlreadyTakenException e) {
            return new AuthenticationResponse("Email is taken");
        }

        try {
            isAgeValid(newUser.getAge(), "user");
        } catch (AgeIsNotValidException e) {
            return new AuthenticationResponse("Age is not valid");
        }

        newUser.setUuid(generateUUID());
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));

        MedCard medCard = new MedCard(newUser);
        newUser.addMedCard(medCard);
        userRepository.save(newUser);


        HashMap extraClaims = new HashMap();
        extraClaims.put("id", newUser.getId());
        extraClaims.put("role", newUser.getRole());

        String jwtToken =jwtService.generateToken(extraClaims, newUser);

        return new AuthenticationResponse(jwtToken, newUser.getId(), medCard.getId(), newUser.getRole());
    }

    public DoctorAuthenticationResponse registerDoctor(Doctor newDoctor) {
        try {
            isUsernameNotTaken(newDoctor.getEmail());
        } catch (UsernameAlreadyTakenException e) {
            return new DoctorAuthenticationResponse("Email is taken");
        }

        try {
            isAgeValid(newDoctor.getAge(), "doctor");
        } catch (AgeIsNotValidException e) {
            return new DoctorAuthenticationResponse("Age is not valid");
        }

        newDoctor.setUuid(generateUUID());
        newDoctor.setPassword(passwordEncoder.encode(newDoctor.getPassword()));
        doctorRepository.save(newDoctor);



        HashMap extraClaims = new HashMap();
        extraClaims.put("id", newDoctor.getId());
        extraClaims.put("role", newDoctor.getRole());

        Calendar calendar = new Calendar();
        calendar.setOwnerId(newDoctor.getId());
        calendarRepository.save(calendar);

        newDoctor.setCalendarId(calendar.getId());
        doctorRepository.save(newDoctor);

        String jwtToken =jwtService.generateToken(extraClaims, newDoctor);

        return new DoctorAuthenticationResponse(jwtToken, newDoctor.getId(), newDoctor.getRole());
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));

        HashMap extraClaims = new HashMap();
        extraClaims.put("id", user.getId());
        extraClaims.put("role", user.getRole());

        String jwtToken = jwtService.generateToken(extraClaims, user);

        return new AuthenticationResponse(jwtToken, user.getId(), user.getMedCard(), user.getRole());
    }

    public DoctorAuthenticationResponse authenticateDoctor(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        Doctor doctor = doctorRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));

        HashMap extraClaims = new HashMap();
        extraClaims.put("id", doctor.getId());
        extraClaims.put("role", doctor.getRole());

        String jwtToken = jwtService.generateToken(extraClaims, doctor);


        return new DoctorAuthenticationResponse(jwtToken, doctor.getId(), doctor.getRole(), doctor.getStatus());
    }

    private void isUsernameNotTaken(String username) {
        if (userRepository.findByEmail(username).isPresent() && doctorRepository.findByEmail(username).isPresent()) {
            throw new UsernameAlreadyTakenException(username);
        }
    }

    private void isAgeValid(int age, String entity) {
        if (age <= 0 && entity.equals("user")) {
            throw new AgeIsNotValidException();
        } else if (age < 18 && entity.equals("doctor")) {
            throw new AgeIsNotValidException();
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
