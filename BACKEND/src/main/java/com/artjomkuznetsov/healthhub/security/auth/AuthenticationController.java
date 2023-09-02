package com.artjomkuznetsov.healthhub.security.auth;


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
    public ResponseEntity<?> register(@RequestBody User newUser) {
        return ResponseEntity.ok(authenticationService.register(newUser));
    }

    @PostMapping("/authenticate")
    @CrossOrigin(origins="*", maxAge=3600)
    public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

}
