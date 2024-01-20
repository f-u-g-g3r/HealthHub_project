package com.artjomkuznetsov.healthhub.controllers;

import com.artjomkuznetsov.healthhub.assemblers.CalendarAssembler;
import com.artjomkuznetsov.healthhub.exceptions.CalendarNotFoundException;
import com.artjomkuznetsov.healthhub.models.Calendar;
import com.artjomkuznetsov.healthhub.models.Schedule;
import com.artjomkuznetsov.healthhub.repositories.CalendarRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class CalendarController {
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    private final CalendarRepository repository;
    private final CalendarAssembler assembler;


    public CalendarController(CalendarRepository repository, CalendarAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    @GetMapping("/calendars")
    public CollectionModel<EntityModel<Calendar>> all() {
        List<EntityModel<Calendar>> calendars = repository.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(calendars,
                linkTo(methodOn(CalendarController.class).all()).withSelfRel());
    }

    @GetMapping("/calendars/{doctorId}")
    @CrossOrigin(origins="*")
    public EntityModel<Calendar> one(@PathVariable Long doctorId) {
        Calendar calendar = repository.findByOwnerId(doctorId)
                .orElseThrow(() -> new CalendarNotFoundException(doctorId));
        return assembler.toModel(calendar);
    }

    @PutMapping("/calendars/{ownerId}")
    @CrossOrigin(origins="*")
    public ResponseEntity<?> updateCalendar(@RequestBody Calendar newCalendar, @PathVariable Long ownerId) {
        Calendar updatedCalendar = repository.findByOwnerId(ownerId)
                .map(calendar -> {
                    if (newCalendar.getOneAppointmentTime() != null) calendar.setOneAppointmentTime(newCalendar.getOneAppointmentTime());
                    if (newCalendar.getWorkStartTime() != null) calendar.setWorkStartTime(newCalendar.getWorkStartTime());
                    if (newCalendar.getWorkEndTime() != null) calendar.setWorkEndTime(newCalendar.getWorkEndTime());

                    return repository.save(calendar);
                })
                .orElseThrow(() -> new CalendarNotFoundException(ownerId));
        EntityModel<Calendar> entityModel = assembler.toModel(updatedCalendar);

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @PutMapping("/calendars/schedule/{ownerId}")
    @CrossOrigin(origins="*")
    public ResponseEntity<?> updateSchedule(@RequestBody Schedule newSchedule, @PathVariable Long ownerId) {
        Calendar updatedCalendar = repository.findByOwnerId(ownerId)
                .map(calendar -> {
                    if (newSchedule != null) {
                        List<Schedule> oldSchedule = calendar.getSchedule();
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

    @GetMapping("/calendars/availableTime/{ownerId}/{date}")
    @CrossOrigin(origins = "*")
    public List<String> getAvailableTimeByDate(@PathVariable Long ownerId, @PathVariable LocalDate date) {
        Calendar calendar = repository.findByOwnerId(ownerId)
                .orElseThrow(() -> new CalendarNotFoundException(ownerId));

        LocalTime workStartTime = LocalTime.parse(calendar.getWorkStartTime(), TIME_FORMATTER);
        LocalTime workEndTime = LocalTime.parse(calendar.getWorkEndTime(), TIME_FORMATTER);
        LocalTime oneAppointmentTime = LocalTime.parse(calendar.getOneAppointmentTime(), TIME_FORMATTER);
        List<Schedule> schedules = calendar.getSchedule();
        LocalTime resultTime = workStartTime;
        List<String> availableTime = new ArrayList<>();
        List<String> notAvailableTime = new ArrayList<>();

        for(Schedule schedule : schedules) {
            System.out.println(schedule.getDate());
            System.out.println(date);
            if (schedule.getDate().equals(date)) {
                notAvailableTime.add(schedule.getTime().format(TIME_FORMATTER));
            }
        }

        for(String time : notAvailableTime) {
            System.out.println(time);
        }

        while (resultTime.isBefore(workEndTime) || !resultTime.equals(workEndTime)) {
            if (!notAvailableTime.contains(resultTime.format(TIME_FORMATTER))) {
                availableTime.add(resultTime.format(TIME_FORMATTER));
            }

            resultTime = resultTime
                    .plusHours(oneAppointmentTime.getHour())
                    .plusMinutes(oneAppointmentTime.getMinute());
        }

        return availableTime;
    }
}
