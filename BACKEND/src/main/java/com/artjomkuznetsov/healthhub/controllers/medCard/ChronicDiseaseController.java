package com.artjomkuznetsov.healthhub.controllers.medCard;

import com.artjomkuznetsov.healthhub.assemblers.MedCardModelAssembler;
import com.artjomkuznetsov.healthhub.exceptions.AllergyNotFoundException;
import com.artjomkuznetsov.healthhub.exceptions.ChronicDiseaseNotFoundException;
import com.artjomkuznetsov.healthhub.exceptions.MedCardNotFoundException;
import com.artjomkuznetsov.healthhub.models.MedCard;
import com.artjomkuznetsov.healthhub.models.medcard.Allergy;
import com.artjomkuznetsov.healthhub.models.medcard.ChronicDisease;
import com.artjomkuznetsov.healthhub.repositories.MedCardRepository;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ChronicDiseaseController {
    private final MedCardRepository repository;
    private final MedCardModelAssembler assembler;

    public ChronicDiseaseController(MedCardRepository repository, MedCardModelAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    @PostMapping("/med-cards/{id}/chronic-diseases")
    public ResponseEntity<?> newChronicDisease(@RequestBody List<ChronicDisease> newChronicDiseases, @PathVariable Long id) {
        MedCard medCard = repository.findById(id)
                .orElseThrow(() -> new MedCardNotFoundException(id));

        List<ChronicDisease> chronicDiseases = medCard.getChronicDiseases();
        for (ChronicDisease chronicDisease : newChronicDiseases) {
            chronicDiseases.add(chronicDisease);
        }
        medCard.setChronicDiseases(chronicDiseases);

        EntityModel<MedCard> entityModel = assembler.toModel(repository.save(medCard));

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);

    }

    @PutMapping("/med-cards/{medId}/chronic-diseases/{chronicDiseaseId}")
    public ResponseEntity<?> updateChronicDisease(
            @RequestBody ChronicDisease updatedChronicDisease,
            @PathVariable Long medId,
            @PathVariable Long chronicDiseaseId
    ) {
        MedCard medCard = repository.findById(medId)
                .orElseThrow(() -> new MedCardNotFoundException(medId));

        List<ChronicDisease> newChronicDiseases = updateChronicDiseaseInMedCard(medCard, chronicDiseaseId, updatedChronicDisease);
        medCard.setChronicDiseases(newChronicDiseases);

        EntityModel<MedCard> entityModel = assembler.toModel(repository.save(medCard));

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    private List<ChronicDisease> updateChronicDiseaseInMedCard(MedCard medCard, Long id, ChronicDisease updatedChronicDisease) {
        List<ChronicDisease> chronicDiseases = medCard.getChronicDiseases();
        for (ChronicDisease chronicDisease : chronicDiseases) {
            if (chronicDisease.getId() == id) {
                chronicDisease.setDisease(updatedChronicDisease.getDisease());
                chronicDisease.setDescription(updatedChronicDisease.getDescription());
                return chronicDiseases;
            }
        }
        throw new ChronicDiseaseNotFoundException(id, medCard.getId());
    }

    @DeleteMapping("/med-cards/{medId}/chronic-diseases/{chronicDiseaseId}")
    public ResponseEntity<?> deleteChronicDisease(@PathVariable Long medId, @PathVariable Long chronicDiseaseId) {
        MedCard medCard = repository.findById(medId)
                .orElseThrow(() -> new MedCardNotFoundException(medId));

        List<ChronicDisease> newChronicDiseases = deleteChronicDiseaseFromMedCard(medCard, chronicDiseaseId);
        medCard.setChronicDiseases(newChronicDiseases);

        EntityModel<MedCard> entityModel = assembler.toModel(repository.save(medCard));
        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    private List<ChronicDisease> deleteChronicDiseaseFromMedCard(MedCard medCard, Long id) {
        List<ChronicDisease> chronicDiseases = medCard.getChronicDiseases();

        for (ChronicDisease chronicDisease : chronicDiseases) {
            if (chronicDisease.getId() == id) {
                chronicDiseases.remove(chronicDisease);
                return chronicDiseases;
            }
        }
        throw new ChronicDiseaseNotFoundException(id, medCard.getId());
    }
}
