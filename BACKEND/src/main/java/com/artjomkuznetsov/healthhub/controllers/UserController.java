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
import java.util.Random;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class UserController {
    private final UserRepository repository;
    private final UserModelAssembler assembler;
    private final JwtService jwtService;
    private final MedCardController medCardController;

    public UserController(UserRepository repository, UserModelAssembler assembler, JwtService jwtService, MedCardController medCardController) {
        this.repository = repository;
        this.assembler = assembler;
        this.jwtService = jwtService;
        this.medCardController = medCardController;
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

    @GetMapping("/users-by-doctor/{doctorId}")
    @CrossOrigin(origins="*")
    public List<User> getUsersByDoctorId(@PathVariable Long doctorId) {
        List<User> users = repository.findByFamilyDoctorId(doctorId);

        return users;
    }

    /*@PostMapping("/users")
    public ResponseEntity<?> newUser(@RequestBody User newUser) {
        newUser.setUuid(generateUUID());
        EntityModel<User> entityModel = assembler.toModel(repository.save(newUser));
        String jwtToken = jwtService.generateToken(newUser);
        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel)
                .ok(jwtToken);
    }*/

    @GetMapping("/users/{id}")
    @CrossOrigin(origins="*", maxAge=3600)
    public EntityModel<User> one(@PathVariable("id") Long id) {
        User user = repository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        return assembler.toModel(user);
    }

    @PutMapping("/users/{id}")
    @CrossOrigin(origins="*", maxAge=3600)
    public ResponseEntity<?> replaceUser(@RequestBody User newUser, @PathVariable Long id) {
        User updatedUser = repository.findById(id)
                .map(user -> {
                    if (newUser.getFirstname() != null) user.setFirstname(newUser.getFirstname());
                    if (newUser.getLastname() != null) user.setLastname(newUser.getLastname());
                    if (newUser.getDateOfBirth() != null) user.setDateOfBirth(newUser.getDateOfBirth());
                    if (newUser.getGender() != null) user.setGender(newUser.getGender());
                    if (newUser.getAddress() != null) user.setAddress(newUser.getAddress());
                    if (newUser.getEmail() != null) user.setEmail(newUser.getEmail());
                    if (newUser.getPhone() != null) user.setPhone(newUser.getPhone());
                    if (newUser.getRole() != null) user.setRole(newUser.getRole());
                    if (newUser.getFamilyDoctorId() != null) user.setFamilyDoctorId(newUser.getFamilyDoctorId());
                    if (newUser.getPassword() != null) user.setPassword(newUser.getPassword());
                    if (newUser.getMedCardID() != null) user.setMedCardID(newUser.getMedCardID());
                    if (newUser.getUuid() != null) user.setUuid(newUser.getUuid());
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

    @RequestMapping(value = "/users/{id}", method = RequestMethod.OPTIONS)
    public ResponseEntity<?> options() {
        return ResponseEntity.ok().build();
    }

    /*public String generateUUID() {
        String[] numbers = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "0"};
        StringBuilder uuid = new StringBuilder("");
        Random rand = new Random();
        for (int i = 0; i<10; i++) {
            int randNum = rand.nextInt(0, 10);
            uuid.append(numbers[randNum]);
        }
        System.out.println(uuid);
        return uuid.toString();
    }*/

}
