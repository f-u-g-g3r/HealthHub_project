package com.artjomkuznetsov.healthhub.assemblers;


import com.artjomkuznetsov.healthhub.models.MedCard;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class MedCardModelAssembler implements RepresentationModelAssembler<MedCard, EntityModel<MedCard>> {
    @Override
    public EntityModel<MedCard> toModel(MedCard medCard) {
        return EntityModel.of(medCard,
                linkTo(methodOn(MedCardController.class).one(medCard.getId())).withSelfRel(),
                linkTo(methodOn(MedCardController.class).all()).withRel("medCards"));
    }

}
