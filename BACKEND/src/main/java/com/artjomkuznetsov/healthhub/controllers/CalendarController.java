package com.artjomkuznetsov.healthhub.controllers;

import com.artjomkuznetsov.healthhub.assemblers.CalendarAssembler;
import com.artjomkuznetsov.healthhub.exceptions.CalendarNotFoundException;
import com.artjomkuznetsov.healthhub.exceptions.DoctorNotFoundException;
import com.artjomkuznetsov.healthhub.exceptions.InvalidDateException;
import com.artjomkuznetsov.healthhub.models.Calendar;
import com.artjomkuznetsov.healthhub.models.Doctor;
import com.artjomkuznetsov.healthhub.models.Schedule;
import com.artjomkuznetsov.healthhub.repositories.CalendarRepository;
import com.artjomkuznetsov.healthhub.repositories.DoctorRepository;
import com.artjomkuznetsov.healthhub.repositories.ScheduleRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class CalendarController {
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    private final CalendarRepository repository;
    private final ScheduleRepository scheduleRepository;
    private final DoctorRepository doctorRepository;
    private final CalendarAssembler assembler;


    public CalendarController(CalendarRepository repository, ScheduleRepository scheduleRepository, DoctorRepository doctorRepository, CalendarAssembler assembler) {
        this.repository = repository;
        this.scheduleRepository = scheduleRepository;
        this.doctorRepository = doctorRepository;
        this.assembler = assembler;
    }

    @GetMapping("/calendars")
    @CrossOrigin(origins = "*")
    public CollectionModel<EntityModel<Calendar>> all() {
        List<EntityModel<Calendar>> calendars = repository.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(calendars,
                linkTo(methodOn(CalendarController.class).all()).withSelfRel());
    }

    @GetMapping("/calendars/{doctorId}")
    @CrossOrigin(origins = "*")
    public EntityModel<Calendar> one(@PathVariable Long doctorId) {
        Calendar calendar = repository.findByOwnerId(doctorId)
                .orElseThrow(() -> new CalendarNotFoundException(doctorId));
        return assembler.toModel(calendar);
    }

    @GetMapping("/calendars/schedules/{doctorId}")
    @CrossOrigin(origins = "*")
    public Page<Schedule> getDoctorSchedules(@PathVariable Long doctorId,
                                             @RequestParam Optional<String> sortBy,
                                             @RequestParam Optional<Integer> page,
                                             @RequestParam Optional<String> direction) {
        Sort.Direction sort = Sort.Direction.ASC;
        if (direction.isPresent() && direction.get().equals("DESC")) {
            sort = Sort.Direction.DESC;
        }
        return scheduleRepository.findAllByDoctorId(doctorId,
                PageRequest.of(
                        page.orElse(0),
                        5,
                        sort, sortBy.orElse("date")
                ));
    }

    @PutMapping("/calendars/{ownerId}")
    @CrossOrigin(origins = "*")
    public ResponseEntity<?> updateCalendar(@RequestBody Calendar newCalendar, @PathVariable Long ownerId) {
        Calendar updatedCalendar = repository.findByOwnerId(ownerId)
                .map(calendar -> {
                    if (newCalendar.getOneAppointmentTime() != null)
                        calendar.setOneAppointmentTime(newCalendar.getOneAppointmentTime());
                    if (newCalendar.getWorkStartTime() != null)
                        calendar.setWorkStartTime(newCalendar.getWorkStartTime());
                    if (newCalendar.getWorkEndTime() != null) calendar.setWorkEndTime(newCalendar.getWorkEndTime());
                    return repository.save(calendar);
                })
                .orElseThrow(() -> new CalendarNotFoundException(ownerId));
        if (updatedCalendar.getOneAppointmentTime() != null &&
                updatedCalendar.getWorkStartTime() != null && updatedCalendar.getWorkEndTime() != null) {
            Doctor doctor = doctorRepository.findById(ownerId)
                    .orElseThrow(() -> new DoctorNotFoundException(ownerId));

            doctor.setConfigured(true);
            doctorRepository.save(doctor);
        }

        EntityModel<Calendar> entityModel = assembler.toModel(updatedCalendar);

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @PutMapping("/calendars/schedule/{ownerId}")
    @CrossOrigin(origins = "*")
    public ResponseEntity<?> updateSchedule(@RequestBody Schedule newSchedule, @PathVariable Long ownerId) {

        LocalDate currentDate = LocalDate.now();
        try {
            if (newSchedule.getDate().isBefore(currentDate) || newSchedule.getDate().equals(currentDate)) {
                throw new InvalidDateException(newSchedule.getDate());
            }
        } catch (InvalidDateException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Invalid date: " + e.getMessage());
        }

        Calendar updatedCalendar = repository.findByOwnerId(ownerId)
                .map(calendar -> {
                    if (newSchedule != null) {
                        List<Schedule> oldSchedule = calendar.getSchedule();
                        newSchedule.setDoctorId(ownerId);
                        oldSchedule.add(newSchedule);
                        calendar.setSchedule(oldSchedule);
                        return repository.save(calendar);
                    }
                    return repository.save(calendar);

                })
                .orElseThrow(() -> new CalendarNotFoundException(ownerId));

        EntityModel<Calendar> entityModel = assembler.toModel(updatedCalendar);

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @GetMapping("/calendars/user-appointments/{patientId}")
    @CrossOrigin(origins = "*")
    public CollectionModel<EntityModel<Schedule>> getSchedulesByPatientId(@PathVariable Long patientId) {
        List<EntityModel<Schedule>> schedules = scheduleRepository.findAllByPatientId(patientId).stream()
                .map(schedule -> EntityModel.of(schedule,
                        linkTo(methodOn(CalendarController.class).getSchedulesByPatientId(patientId)).withSelfRel()))
                .collect(Collectors.toList());
        return CollectionModel.of(schedules,
                linkTo(methodOn(CalendarController.class).getSchedulesByPatientId(patientId)).withSelfRel());
    }

    @GetMapping("/calendars/schedules/{doctorId}/{date}")
    @CrossOrigin(origins = "*")
    public Page<Schedule> getSchedulesByDate(@PathVariable Long doctorId,
                                             @PathVariable LocalDate date,
                                             @RequestParam Optional<String> sortBy,
                                             @RequestParam Optional<Integer> page,
                                             @RequestParam Optional<String> direction) {
        Sort.Direction sort = Sort.Direction.ASC;
        if (direction.isPresent() && direction.get().equals("DESC")) {
            sort = Sort.Direction.DESC;
        }
        return scheduleRepository.findAllByDoctorIdAndDate(doctorId, date,
                PageRequest.of(
                        page.orElse(0),
                        5,
                        sort, sortBy.orElse("date")
                ));

    }


    @GetMapping("/calendars/availableTime/{ownerId}/{date}")
    @CrossOrigin(origins = "*")
    public List<String> getAvailableTimeByDate(@PathVariable Long ownerId, @PathVariable LocalDate date) {
        LocalDate currentDate = LocalDate.now();
        try {
            if (date.isBefore(currentDate) || date.equals(currentDate)) {
                throw new InvalidDateException(date);
            }
        } catch (InvalidDateException e) {
            return new ArrayList<>();
        }

        Calendar calendar = repository.findByOwnerId(ownerId)
                .orElseThrow(() -> new CalendarNotFoundException(ownerId));

        LocalTime workStartTime = LocalTime.parse(calendar.getWorkStartTime(), TIME_FORMATTER);
        LocalTime workEndTime = LocalTime.parse(calendar.getWorkEndTime(), TIME_FORMATTER);
        LocalTime oneAppointmentTime = LocalTime.parse(calendar.getOneAppointmentTime(), TIME_FORMATTER);
        List<Schedule> schedules = calendar.getSchedule();
        LocalTime resultTime = workStartTime;
        List<String> availableTime = new ArrayList<>();
        List<String> notAvailableTime = new ArrayList<>();

        for (Schedule schedule : schedules) {
            if (schedule.getDate().equals(date)) {
                notAvailableTime.add(schedule.getTime());
            }
        }

        while (resultTime.isBefore(workEndTime) && !resultTime.equals(workEndTime)) {
            if (!notAvailableTime.contains(resultTime.format(TIME_FORMATTER))) {
                availableTime.add(resultTime.format(TIME_FORMATTER));
            }

            resultTime = resultTime
                    .plusHours(oneAppointmentTime.getHour())
                    .plusMinutes(oneAppointmentTime.getMinute());
        }

        return availableTime;
    }


    @DeleteMapping("/calendars/schedules/{scheduleId}/{patientId}")
    @CrossOrigin(origins = "*")
    public ResponseEntity<?> deleteSchedule(@PathVariable Long scheduleId) {
        scheduleRepository.deleteById(scheduleId);
        return ResponseEntity.noContent().build();
    }
}
