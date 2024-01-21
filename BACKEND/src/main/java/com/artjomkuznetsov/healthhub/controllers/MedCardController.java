package com.artjomkuznetsov.healthhub.controllers;

import com.artjomkuznetsov.healthhub.assemblers.MedCardModelAssembler;
import com.artjomkuznetsov.healthhub.exceptions.DiseaseNotFoundException;
import com.artjomkuznetsov.healthhub.exceptions.MedCardNotFoundException;
import com.artjomkuznetsov.healthhub.models.MedCard;
import com.artjomkuznetsov.healthhub.models.medcard.MedHistory;
import com.artjomkuznetsov.healthhub.repositories.MedCardRepository;
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
public class MedCardController {
    private final MedCardRepository repository;
    private final MedCardModelAssembler assembler;

    public MedCardController(MedCardRepository repository, MedCardModelAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    @GetMapping("/med-cards")
    public CollectionModel<EntityModel<MedCard>> all() {
        List<EntityModel<MedCard>> medCards = repository.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(medCards,
                linkTo(methodOn(MedCardController.class).all()).withSelfRel());
    }

    @PostMapping("/med-cards")
    @CrossOrigin(origins="*")
    public ResponseEntity<?> newMedCard(@RequestBody MedCard newMedCard) {
        EntityModel<MedCard> entityModel = assembler.toModel(repository.save(newMedCard));

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @GetMapping("/med-cards/{id}")
    @CrossOrigin(origins="*")
    public EntityModel<MedCard> one(@PathVariable Long id) {
        MedCard medCard = repository.findById(id)
                .orElseThrow(() -> new MedCardNotFoundException(id));

        return assembler.toModel(medCard);
    }

    @PutMapping("/med-cards/{id}")
    @CrossOrigin(origins="*")
    public ResponseEntity<?> replaceMedCard(@RequestBody MedCard newMedCard, @PathVariable Long id) {
        MedCard updatedMedCard = repository.findById(id)
                .map(medCard -> {
                    if (newMedCard.getMedHistory() != null) medCard.setMedHistory(newMedCard.getMedHistory());
                    if (newMedCard.getBloodType() != null) medCard.setBloodType(newMedCard.getBloodType());
                    if (newMedCard.getRhFactor() != null) medCard.setRhFactor(newMedCard.getRhFactor());
                    if (newMedCard.getAllergies() != null) medCard.setAllergies(newMedCard.getAllergies());
                    if (newMedCard.getChronicDiseases() != null) medCard.setChronicDiseases(newMedCard.getChronicDiseases());
                    if (newMedCard.getResultsOfSurveys() != null) medCard.setResultsOfSurveys(newMedCard.getResultsOfSurveys());
                    if (newMedCard.getOwnerID() != null) medCard.setOwnerID(newMedCard.getOwnerID());
                    if (newMedCard.getFamilyDoctorID() != null) medCard.setFamilyDoctorID(newMedCard.getFamilyDoctorID());
                    return repository.save(medCard);
                })
                .orElseGet(() -> {
                    newMedCard.setId(id);
                    return repository.save(newMedCard);
                });

        EntityModel<MedCard> entityModel = assembler.toModel(updatedMedCard);
        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @DeleteMapping("/med-cards/{id}")
    public ResponseEntity<?> deleteMedCard(@PathVariable Long id) {
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }


    public void setFamilyDoctor(Long medCardId, Long familyDoctorId) {
        MedCard medCard = repository.findById(medCardId)
                .orElseThrow(() -> new MedCardNotFoundException(medCardId));

        medCard.setFamilyDoctorID(familyDoctorId);
        repository.save(medCard);
    }
}
