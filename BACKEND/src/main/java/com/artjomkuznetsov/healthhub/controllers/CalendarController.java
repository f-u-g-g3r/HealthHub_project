package com.artjomkuznetsov.healthhub.controllers;

import com.artjomkuznetsov.healthhub.assemblers.CalendarAssembler;
import com.artjomkuznetsov.healthhub.exceptions.CalendarNotFoundException;
import com.artjomkuznetsov.healthhub.models.Calendar;
import com.artjomkuznetsov.healthhub.models.Doctor;
import com.artjomkuznetsov.healthhub.models.Schedule;
import com.artjomkuznetsov.healthhub.repositories.CalendarRepository;
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
public class CalendarController {
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

    @GetMapping("/calendars/{id}")
    public EntityModel<Calendar> one(@PathVariable Long id) {
        Calendar calendar = repository.findById(id)
                .orElseThrow(() -> new CalendarNotFoundException(id));
        return assembler.toModel(calendar);
    }

    @PutMapping("/calendars/{ownerId}")
    public ResponseEntity<?> updateSchedule(@RequestBody Schedule newSchedule, @PathVariable Long ownerId) {
        Calendar updatedCalendar = repository.findByOwnerId(ownerId)
                .map(calendar -> {
                    System.out.println(newSchedule);
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
}
