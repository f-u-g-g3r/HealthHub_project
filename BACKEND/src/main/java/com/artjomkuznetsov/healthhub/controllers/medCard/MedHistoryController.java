package com.artjomkuznetsov.healthhub.controllers.medCard;

import com.artjomkuznetsov.healthhub.assemblers.MedCardModelAssembler;
import com.artjomkuznetsov.healthhub.exceptions.DiseaseNotFoundException;
import com.artjomkuznetsov.healthhub.exceptions.MedCardNotFoundException;
import com.artjomkuznetsov.healthhub.models.MedCard;
import com.artjomkuznetsov.healthhub.models.medcard.MedHistory;
import com.artjomkuznetsov.healthhub.repositories.MedCardRepository;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MedHistoryController {
    private final MedCardRepository repository;
    private final MedCardModelAssembler assembler;

    public MedHistoryController(MedCardRepository repository, MedCardModelAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
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

        List<MedHistory> newHistory = updateDiseaseInMedHistory(medCard, diseaseId, updatedDisease);
        medCard.setMedHistory(newHistory);

        EntityModel<MedCard> entityModel = assembler.toModel(repository.save(medCard));

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    private List<MedHistory> updateDiseaseInMedHistory(MedCard medCard, Long id, MedHistory updatedDisease) {
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

    @DeleteMapping("/med-cards/{medId}/med-history/{diseaseId}")
    public ResponseEntity<?> deleteDisease(@PathVariable Long medId, @PathVariable Long diseaseId) {
        MedCard medCard = repository.findById(medId)
                .orElseThrow(() -> new MedCardNotFoundException(medId));

        List<MedHistory> newMedHistory = deleteDiseaseFromMedHistory(medCard, diseaseId);
        medCard.setMedHistory(newMedHistory);

        EntityModel<MedCard> entityModel = assembler.toModel(repository.save(medCard));
        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    private List<MedHistory> deleteDiseaseFromMedHistory(MedCard medCard, Long id) {
        List<MedHistory> history = medCard.getMedHistory();

        for (MedHistory disease : history) {
            if (disease.getId() == id) {
                history.remove(disease);
                return history;
            }
        }
        throw new DiseaseNotFoundException(id, medCard.getId());
    }
}
