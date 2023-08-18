package com.artjomkuznetsov.healthhub.controllers;

import com.artjomkuznetsov.healthhub.assemblers.DoctorModelAssembler;
import com.artjomkuznetsov.healthhub.models.Doctor;
import com.artjomkuznetsov.healthhub.repositories.DoctorRepository;
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
public class DoctorController {
    private final DoctorRepository repository;
    private final DoctorModelAssembler assembler;

    public DoctorController(DoctorRepository repository, DoctorModelAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    @GetMapping("/doctors")
    public CollectionModel<EntityModel<Doctor>> all() {
        List<EntityModel<Doctor>> doctors = repository.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(doctors,
                linkTo(methodOn(DoctorController.class).all()).withSelfRel());
    }

    @PostMapping("/doctors")
    public ResponseEntity<?> newDoctor(@RequestBody Doctor newDoctor) {
        EntityModel<Doctor> entityModel = assembler.toModel(repository.save(newDoctor));

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @GetMapping("/doctors/{id}")
    public EntityModel<Doctor> one(@PathVariable Long id) {
        Doctor doctor = repository.findById(id)
                .orElseThrow(DoctorNotFoundException);

        return assembler.toModel(doctor);
    }

    @PutMapping("/doctors/{id}")
    public ResponseEntity<?> replaceDoctor(@RequestBody Doctor newDoctor, @PathVariable Long id) {

    }

    @DeleteMapping("/doctors/{id}")
    public ResponseEntity<?> deleteDoctor(@PathVariable Long id) {
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
