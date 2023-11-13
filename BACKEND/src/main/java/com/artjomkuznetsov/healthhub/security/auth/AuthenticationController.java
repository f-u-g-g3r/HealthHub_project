package com.artjomkuznetsov.healthhub.security.auth;


import com.artjomkuznetsov.healthhub.models.Doctor;
import com.artjomkuznetsov.healthhub.models.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    @CrossOrigin(origins="*")
    public ResponseEntity<?> register(@RequestBody User newUser) {
        return ResponseEntity.ok(authenticationService.registerUser(newUser));
    }

    @PostMapping("/authenticate")
    @CrossOrigin(origins="*")
    public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

    @PostMapping("/register-doctor")
    @CrossOrigin(origins="*")
    public ResponseEntity<?> registerDoctor(@RequestBody Doctor newDoctor) {
        return ResponseEntity.ok(authenticationService.registerDoctor(newDoctor));
    }

}
