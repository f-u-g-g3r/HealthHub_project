package com.artjomkuznetsov.healthhub.controllers;


import com.artjomkuznetsov.healthhub.assemblers.UserModelAssembler;
import com.artjomkuznetsov.healthhub.exceptions.UserNotFoundException;
import com.artjomkuznetsov.healthhub.models.User;
import com.artjomkuznetsov.healthhub.repositories.UserRepository;
import com.artjomkuznetsov.healthhub.security.config.JwtService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class UserController {
    private final UserRepository repository;
    private final UserModelAssembler assembler;
    private final JwtService jwtService;

    public UserController(UserRepository repository, UserModelAssembler assembler, JwtService jwtService) {
        this.repository = repository;
        this.assembler = assembler;
        this.jwtService = jwtService;
    }

    // Aggregate root
    @GetMapping("/users")
    public CollectionModel<EntityModel<User>> all() {
        List<EntityModel<User>> users = repository.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(users,
                linkTo(methodOn(UserController.class).all()).withSelfRel());
    }

    @PostMapping("/users")
    public ResponseEntity<?> newUser(@RequestBody User newUser) {
        EntityModel<User> entityModel = assembler.toModel(repository.save(newUser));
        String jwtToken = jwtService.generateToken(newUser);
        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel)
                .ok(jwtToken);
    }

    @GetMapping("/users/{id}")
    @CrossOrigin(origins="*", maxAge=3600,
            allowedHeaders={"x-auth-token", "x-requested-with", "x-xsrf-token"})
    public EntityModel<User> one(@PathVariable Long id) {
       User user = repository.findById(id)
               .orElseThrow(() -> new UserNotFoundException(id));

       return assembler.toModel(user);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<?> replaceUser(@RequestBody User newUser, @PathVariable Long id) {
        User updatedUser = repository.findById(id)
                .map(user -> {
                    user.setFirstname(newUser.getFirstname());
                    user.setLastname(newUser.getLastname());
                    user.setDateOfBirth(newUser.getDateOfBirth());
                    user.setGender(newUser.getGender());
                    user.setAddress(newUser.getAddress());
                    user.setEmail(newUser.getEmail());
                    user.setPhone(newUser.getPhone());
                    user.setPassword(newUser.getPassword());
                    user.setMedCardID(newUser.getMedCardID());
                    return repository.save(user);
                })
                .orElseGet(() -> {
                    newUser.setId(id);
                    return repository.save(newUser);
                });

        EntityModel<User> entityModel = assembler.toModel(updatedUser);

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);


    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
