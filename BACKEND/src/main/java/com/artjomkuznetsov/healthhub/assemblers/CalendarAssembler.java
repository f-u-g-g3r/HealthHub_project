package com.artjomkuznetsov.healthhub.assemblers;

import com.artjomkuznetsov.healthhub.controllers.CalendarController;
import com.artjomkuznetsov.healthhub.models.Calendar;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class CalendarAssembler implements RepresentationModelAssembler<Calendar, EntityModel<Calendar>> {
    @Override
    public EntityModel<Calendar> toModel(Calendar calendar) {
        return EntityModel.of(calendar,
                linkTo(methodOn(CalendarController.class).one(calendar.getId())).withSelfRel(),
                linkTo(methodOn(CalendarController.class).all()).withRel("calendars"));
    }
}
