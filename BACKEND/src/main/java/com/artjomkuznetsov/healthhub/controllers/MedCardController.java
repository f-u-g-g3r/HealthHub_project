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
    public ResponseEntity<?> newMedCard(@RequestBody MedCard newMedCard) {
        EntityModel<MedCard> entityModel = assembler.toModel(repository.save(newMedCard));

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @PostMapping("/med-cards/{id}/med-history")
    public ResponseEntity<?> newDisease(@RequestBody List<MedHistory> diseases, @PathVariable Long id) {
        MedCard medCard = repository.findById(id)
                .orElseThrow(() -> new MedCardNotFoundException(id));

        List<MedHistory> history = medCard.getMedHistory();
        for (MedHistory disease : diseases) {
            history.add(disease);
        }
        medCard.setMedHistory(history);

        EntityModel<MedCard> entityModel = assembler.toModel(repository.save(medCard));

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);

    }

    @PutMapping("/med-cards/{medId}/med-history/{diseaseId}")
    public ResponseEntity<?> updateDisease(
            @RequestBody MedHistory updatedDisease,
            @PathVariable Long medId,
            @PathVariable Long diseaseId
    ) {
        MedCard medCard = repository.findById(medId)
                .orElseThrow(() -> new MedCardNotFoundException(medId));

        List<MedHistory> newHistory = updateDiseaseInMedCard(medCard, diseaseId, updatedDisease);
        medCard.setMedHistory(newHistory);

        EntityModel<MedCard> entityModel = assembler.toModel(repository.save(medCard));

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    private List<MedHistory> updateDiseaseInMedCard(MedCard medCard, Long id, MedHistory updatedDisease) {
        List<MedHistory> history = medCard.getMedHistory();
        for (MedHistory disease : history) {
            if (disease.getId() == id) {
                disease.setDisease(updatedDisease.getDisease());
                disease.setDescription(updatedDisease.getDescription());
                disease.setDateOfIllness(updatedDisease.getDateOfIllness());
                return history;
            }
        }
        throw new DiseaseNotFoundException(id, medCard.getId());
    }

    @GetMapping("/med-cards/{id}")
    @CrossOrigin(origins="*")
    public EntityModel<MedCard> one(@PathVariable Long id) {
        MedCard medCard = repository.findById(id)
                .orElseThrow(() -> new MedCardNotFoundException(id));

        return assembler.toModel(medCard);
    }



    @PutMapping("/med-cards/{id}")
    public ResponseEntity<?> replaceMedCard(@RequestBody MedCard newMedCard, @PathVariable Long id) {
        MedCard updatedMedCard = repository.findById(id)
                .map(medCard -> {
                    medCard.setMedHistory(newMedCard.getMedHistory());
                    medCard.setBloodType(newMedCard.getBloodType());
                    medCard.setRhFactor(newMedCard.getRhFactor());
                    medCard.setAllergies(newMedCard.getAllergies());
                    medCard.setChronicDiseases(newMedCard.getChronicDiseases());
                    medCard.setResultsOfSurveys(newMedCard.getResultsOfSurveys());
                    medCard.setOwnerID(newMedCard.getOwnerID());
                    medCard.setFamilyDoctorID(newMedCard.getFamilyDoctorID());
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



}
