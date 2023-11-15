package com.artjomkuznetsov.healthhub.security.auth;


import com.artjomkuznetsov.healthhub.models.Doctor;
import com.artjomkuznetsov.healthhub.models.User;
import com.artjomkuznetsov.healthhub.repositories.DoctorRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final DoctorRepository doctorRepository;

    public AuthenticationController(AuthenticationService authenticationService, DoctorRepository doctorRepository) {
        this.authenticationService = authenticationService;
        this.doctorRepository = doctorRepository;
    }

    @PostMapping("/register")
    @CrossOrigin(origins="*")
    public ResponseEntity<?> register(@RequestBody User newUser) {
        return ResponseEntity.ok(authenticationService.registerUser(newUser));
    }

    @PostMapping("/authenticate")
    @CrossOrigin(origins="*")
    public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequest request) {
        if (doctorRepository.findByEmail(request.getEmail()).isPresent()) {
            return ResponseEntity.ok(authenticationService.authenticateDoctor(request));
        }
        else {
            return ResponseEntity.ok(authenticationService.authenticate(request));
        }

    }

    @PostMapping("/register-doctor")
    @CrossOrigin(origins="*")
    public ResponseEntity<?> registerDoctor(@RequestBody Doctor newDoctor) {
        return ResponseEntity.ok(authenticationService.registerDoctor(newDoctor));
    }

}
