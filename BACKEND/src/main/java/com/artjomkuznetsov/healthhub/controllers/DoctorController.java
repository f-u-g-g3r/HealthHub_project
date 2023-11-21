package com.artjomkuznetsov.healthhub.controllers;

import com.artjomkuznetsov.healthhub.assemblers.DoctorModelAssembler;
import com.artjomkuznetsov.healthhub.models.Doctor;
import com.artjomkuznetsov.healthhub.models.DoctorMinimal;
import com.artjomkuznetsov.healthhub.models.Status;
import com.artjomkuznetsov.healthhub.repositories.DoctorRepository;
import com.artjomkuznetsov.healthhub.exceptions.DoctorNotFoundException;
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

    @GetMapping("/doctors-name/{id}")
    @CrossOrigin(origins="*")
    public DoctorMinimal doctorName(@PathVariable Long id) {
        Doctor doctor = repository.findById(id)
                .orElseThrow(() -> new DoctorNotFoundException(id));

        return new DoctorMinimal(doctor.getFirstname(), doctor.getLastname(), doctor.getPhone());
    }

    @PostMapping("/doctors")
    public ResponseEntity<?> newDoctor(@RequestBody Doctor newDoctor) {
        EntityModel<Doctor> entityModel = assembler.toModel(repository.save(newDoctor));

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @GetMapping("/doctors/{id}")
    @CrossOrigin(origins="*")
    public EntityModel<Doctor> one(@PathVariable Long id) {
        Doctor doctor = repository.findById(id)
                .orElseThrow(() -> new DoctorNotFoundException(id));

        return assembler.toModel(doctor);
    }

    @PutMapping("/doctors/{id}")
    @CrossOrigin(origins="*")
    public ResponseEntity<?> replaceDoctor(@RequestBody Doctor newDoctor, @PathVariable Long id) {
        Doctor updatedDoctor = repository.findById(id)
                .map(doctor -> {
                    if (newDoctor.getFirstname() != null) doctor.setFirstname(newDoctor.getFirstname());
                    if (newDoctor.getLastname() != null) doctor.setLastname(newDoctor.getLastname());
                    if (newDoctor.getDateOfBirth() != null)  doctor.setDateOfBirth(newDoctor.getDateOfBirth());
                    if (newDoctor.getGender() != null) doctor.setGender(newDoctor.getGender());
                    if (newDoctor.getAddress() != null) doctor.setAddress(newDoctor.getAddress());
                    if (newDoctor.getEmail() != null) doctor.setEmail(newDoctor.getEmail());
                    if (newDoctor.getPhone() != null) doctor.setPhone(newDoctor.getPhone());
                    if (newDoctor.getPassword() != null) doctor.setPassword(newDoctor.getPassword());
                    if (newDoctor.getSpecialization() != null) doctor.setSpecialization(newDoctor.getSpecialization());
                    if (newDoctor.getPlaceOfWork() != null) doctor.setPlaceOfWork(newDoctor.getPlaceOfWork());
                    if (newDoctor.getLicenseNumber() != null) doctor.setLicenseNumber(newDoctor.getLicenseNumber());
                    if (newDoctor.getLicenseIssuingDate() != null) doctor.setLicenseIssuingDate(newDoctor.getLicenseIssuingDate());
                    if (newDoctor.getLicenseIssuingAuthority() != null) doctor.setLicenseIssuingAuthority(newDoctor.getLicenseIssuingAuthority());
                    return repository.save(doctor);
                })
                .orElseGet(() -> {
                    newDoctor.setId(id);
                    return repository.save(newDoctor);
                });
        EntityModel<Doctor> entityModel = assembler.toModel(updatedDoctor);

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @DeleteMapping("/doctors/{id}")
    public ResponseEntity<?> deleteDoctor(@PathVariable Long id) {
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/doctors/inactivated")
    @CrossOrigin(origins="*")
    public CollectionModel<EntityModel<Doctor>> inactivated() {
        List<EntityModel<Doctor>> inactivatedDoctors = repository.findAllByStatus(Status.INACTIVE).stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(inactivatedDoctors,
                linkTo(methodOn(DoctorController.class).inactivated()).withSelfRel());
    }

    @GetMapping("/doctors/activated")
    @CrossOrigin(origins="*")
    public CollectionModel<EntityModel<Doctor>> activated() {
        List<EntityModel<Doctor>> activatedDoctors = repository.findAllByStatus(Status.ACTIVE).stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(activatedDoctors,
                linkTo(methodOn(DoctorController.class).activated()).withSelfRel());
    }


}
