package com.artjomkuznetsov.healthhub.controllers;

import com.artjomkuznetsov.healthhub.assemblers.DoctorModelAssembler;
import com.artjomkuznetsov.healthhub.models.*;
import com.artjomkuznetsov.healthhub.repositories.DoctorRepository;
import com.artjomkuznetsov.healthhub.exceptions.DoctorNotFoundException;
import com.artjomkuznetsov.healthhub.repositories.MedCardRepository;
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

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class DoctorController {
    private final DoctorRepository repository;
    private final DoctorModelAssembler assembler;
    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;

    public DoctorController(DoctorRepository repository,
                            DoctorModelAssembler assembler,
                            ScheduleRepository scheduleRepository,
                            UserRepository userRepository) {
        this.repository = repository;
        this.assembler = assembler;
        this.scheduleRepository = scheduleRepository;
        this.userRepository = userRepository;
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
    public EntityModel<DoctorMinimal> oneDoctorName(@PathVariable Long id) {
        Doctor doctor = repository.findById(id)
                .orElseThrow(() -> new DoctorNotFoundException(id));
        DoctorMinimal doctorMinimal = new DoctorMinimal(doctor.getFirstname(), doctor.getLastname(), doctor.getPhone());
        return EntityModel.of(doctorMinimal,
                linkTo(methodOn(DoctorController.class).oneDoctorName(id)).withSelfRel(),
                linkTo(methodOn(DoctorController.class).allDoctorsName()).withRel("all"));
    }

    @GetMapping("/doctors-name")
    @CrossOrigin(origins = "*")
    public CollectionModel<EntityModel<DoctorMinimal>> allDoctorsName() {
        List<EntityModel<DoctorMinimal>> doctors = repository.findAll().stream()
                .map(doctor -> {
                    DoctorMinimal doctorMinimal = new DoctorMinimal(doctor.getFirstname(), doctor.getLastname(), doctor.getPhone());
                    return EntityModel.of(doctorMinimal,
                            linkTo(methodOn(DoctorController.class).oneDoctorName(doctor.getId())).withRel("one"),
                            linkTo(methodOn(DoctorController.class).allDoctorsName()).withSelfRel());

                })
                .collect(Collectors.toList());
        return CollectionModel.of(doctors, linkTo(methodOn(DoctorController.class).allDoctorsName()).withSelfRel());
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
                    if (newDoctor.getStatus() != null) doctor.setStatus(newDoctor.getStatus());
                    if (newDoctor.getCalendarId() != null) doctor.setCalendarId(newDoctor.getCalendarId());
                    return repository.save(doctor);
                })
                .orElseGet(() -> {
                    newDoctor.setId(id);
                    return repository.save(newDoctor);
                });

        if (newDoctor.getStatus() == Status.INACTIVE) {
            List<Schedule> doctorSchedules = scheduleRepository.findAllByDoctorId(id);
            scheduleRepository.deleteAll(doctorSchedules);

            List<User> users = userRepository.findByFamilyDoctorId(id);
            for (User user : users) {
                user.setFamilyDoctorId(null);
                userRepository.save(user);
            }
        }

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

    @GetMapping("/doctors/page/inactivated")
    @CrossOrigin(origins = "*")
    public Page<Doctor> inactivated(@RequestParam Optional<String> sortBy,
                                    @RequestParam Optional<Integer> page,
                                    @RequestParam Optional<String> direction) {
        System.out.println(123421421);
        Sort.Direction sort = Sort.Direction.ASC;
        if (direction.isPresent() && direction.get().equals("DESC")) {
            sort = Sort.Direction.DESC;
        }
        return repository.findAllByStatus(Status.INACTIVE,
                PageRequest.of(
                        page.orElse(0),
                        3,
                        sort, sortBy.orElse("id")
                ));
    }
    @GetMapping("/doctors/page/activated")
    @CrossOrigin(origins="*")
    public Page<Doctor> activated(@RequestParam Optional<String> sortBy,
                                  @RequestParam Optional<Integer> page,
                                  @RequestParam Optional<String> direction) {
        Sort.Direction sort = Sort.Direction.ASC;
        if (direction.isPresent() && direction.get().equals("DESC")) {
            sort = Sort.Direction.DESC;
        }
        return repository.findAllByStatus(Status.ACTIVE,
                PageRequest.of(
                        page.orElse(0),
                        3,
                        sort, sortBy.orElse("id")
                ));
    }

    @GetMapping("/doctors/activated")
    @CrossOrigin(origins="*")
    public CollectionModel<EntityModel<Doctor>> activated() {
        List<EntityModel<Doctor>> doctors = repository.findAllByStatus(Status.ACTIVE).stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(doctors,
                linkTo(methodOn(DoctorController.class).all()).withSelfRel());
    }
}
