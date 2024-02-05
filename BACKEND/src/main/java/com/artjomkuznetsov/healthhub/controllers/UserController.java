package com.artjomkuznetsov.healthhub.controllers;


import com.artjomkuznetsov.healthhub.assemblers.UserModelAssembler;
import com.artjomkuznetsov.healthhub.exceptions.CalendarNotFoundException;
import com.artjomkuznetsov.healthhub.exceptions.DoctorNotFoundException;
import com.artjomkuznetsov.healthhub.exceptions.UserNotFoundException;
import com.artjomkuznetsov.healthhub.models.Calendar;
import com.artjomkuznetsov.healthhub.models.Schedule;
import com.artjomkuznetsov.healthhub.models.User;
import com.artjomkuznetsov.healthhub.repositories.CalendarRepository;
import com.artjomkuznetsov.healthhub.repositories.DoctorRepository;
import com.artjomkuznetsov.healthhub.repositories.ScheduleRepository;
import com.artjomkuznetsov.healthhub.repositories.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class UserController {
    private final UserRepository repository;
    private final UserModelAssembler assembler;
    private final MedCardController medCardController;
    private final DoctorRepository doctorRepository;
    private final CalendarRepository calendarRepository;
    private final ScheduleRepository scheduleRepository;

    public UserController(UserRepository repository, CalendarRepository calendarRepository, ScheduleRepository scheduleRepository, UserModelAssembler assembler, MedCardController medCardController, DoctorRepository doctorRepository) {
        this.repository = repository;
        this.assembler = assembler;
        this.medCardController = medCardController;
        this.doctorRepository = doctorRepository;
        this.calendarRepository = calendarRepository;
        this.scheduleRepository = scheduleRepository;
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
    @CrossOrigin(origins = "*")
    public Page<User> getUsersByDoctorId(@PathVariable Long doctorId,
                                         @RequestParam Optional<String> sortBy,
                                         @RequestParam Optional<Integer> page,
                                         @RequestParam Optional<String> direction) {
        Sort.Direction sort = Sort.Direction.ASC;
        if (direction.isPresent() && direction.get().equals("DESC")) {
            sort = Sort.Direction.DESC;
        }

        return repository.findByFamilyDoctorId(doctorId,
                PageRequest.of(
                        page.orElse(0),
                        5,
                        sort, sortBy.orElse("id")
                ));
    }


    @GetMapping("/users/{id}")
    @CrossOrigin(origins = "*", maxAge = 3600)
    public EntityModel<User> one(@PathVariable("id") Long id) {
        User user = repository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        System.out.println(user.toString());
        return assembler.toModel(user);
    }

    @GetMapping("/users/uuid/{uuid}")
    @CrossOrigin(origins = "*")
    public EntityModel<User> oneByUuid(@PathVariable String uuid) {
        User user = repository.findByUuid(uuid)
                .orElseThrow(() -> new UserNotFoundException(uuid));
        return assembler.toModel(user);
    }

    // delete this
//    @GetMapping("/users/family-doctor/{userId}/{familyDoctorId}")
//    @CrossOrigin(origins = "*", maxAge = 3600)
//    public ResponseEntity<?> setFamilyDoctor(@PathVariable Long userId, @PathVariable Long familyDoctorId) {
//        User user = repository.findById(userId)
//                .orElseThrow(() -> new UserNotFoundException(userId));
//
//        if (!doctorRepository.existsById(familyDoctorId)) {
//            throw new DoctorNotFoundException(familyDoctorId);
//        }
//        if (user.getFamilyDoctorId() != null) {
//            List<Schedule> patientSchedules = scheduleRepository.findAllByPatientId(userId);
//            scheduleRepository.deleteAll(patientSchedules);
//        }
//
//        user.setFamilyDoctorId(familyDoctorId);
//        repository.save(user);
//        medCardController.setFamilyDoctor(user.getMedCardID(), familyDoctorId);
//
//        EntityModel<User> entityModel = assembler.toModel(user);
//
//        return ResponseEntity
//                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
//                .body(entityModel);
//    }

    @PutMapping("/users/{id}")
    @CrossOrigin(origins = "*")
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
                    if (newUser.getFamilyDoctorId() != null) {
                        user.setFamilyDoctorId(newUser.getFamilyDoctorId());
                    }

                    if (newUser.getPassword() != null) user.setPassword(newUser.getPassword());
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


}
