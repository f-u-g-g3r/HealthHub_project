package com.artjomkuznetsov.healthhub.assemblers;

import com.artjomkuznetsov.healthhub.models.Doctor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class DoctorModelAssembler implements RepresentationModelAssembler<Doctor, EntityModel<Doctor>> {
    @Override
    public EntityModel<Doctor> toModel(Doctor doctor) {
        return EntityModel.of(doctor,
                linkTo(methodOn(DoctorController.class).one(doctor.getId())).withSelfRel(),
                linkTo(methodOn(DoctorController.class).all()).withRel("doctors"));
    }
}
