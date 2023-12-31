package com.artjomkuznetsov.healthhub.controllers.medCard;

import com.artjomkuznetsov.healthhub.assemblers.MedCardModelAssembler;
import com.artjomkuznetsov.healthhub.exceptions.AllergyNotFoundException;
import com.artjomkuznetsov.healthhub.exceptions.MedCardNotFoundException;
import com.artjomkuznetsov.healthhub.models.MedCard;
import com.artjomkuznetsov.healthhub.models.medcard.Allergy;
import com.artjomkuznetsov.healthhub.repositories.MedCardRepository;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AllergyController {
    private final MedCardRepository repository;
    private final MedCardModelAssembler assembler;

    public AllergyController(MedCardRepository repository, MedCardModelAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    @PostMapping("/med-cards/{id}/allergies")
    @CrossOrigin(origins="*", maxAge=3600)
    public ResponseEntity<?> newAllergy(@RequestBody Allergy newAllergy, @PathVariable Long id) {
        MedCard medCard = repository.findById(id)
                .orElseThrow(() -> new MedCardNotFoundException(id));

        List<Allergy> allergies = medCard.getAllergies();
        allergies.add(newAllergy);

        medCard.setAllergies(allergies);

        EntityModel<MedCard> entityModel = assembler.toModel(repository.save(medCard));

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);

    }

    @PutMapping("/med-cards/{medId}/allergies/{allergyId}")
    @CrossOrigin(origins="*", maxAge=3600)
    public ResponseEntity<?> updateAllergy(
            @RequestBody Allergy updatedAllergy,
            @PathVariable Long medId,
            @PathVariable Long allergyId
    ) {
        MedCard medCard = repository.findById(medId)
                .orElseThrow(() -> new MedCardNotFoundException(medId));

        List<Allergy> newAllergies = updateAllergyInMedCard(medCard, allergyId, updatedAllergy);
        medCard.setAllergies(newAllergies);

        EntityModel<MedCard> entityModel = assembler.toModel(repository.save(medCard));

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    private List<Allergy> updateAllergyInMedCard(MedCard medCard, Long id, Allergy updatedAllergy) {
        List<Allergy> allergies = medCard.getAllergies();
        for (Allergy allergy : allergies) {
            if (allergy.getId() == id) {
                allergy.setAllergy(updatedAllergy.getAllergy());
                allergy.setDescription(updatedAllergy.getDescription());
                return allergies;
            }
        }
        throw new AllergyNotFoundException(id, medCard.getId());
    }

    @DeleteMapping("/med-cards/{medId}/allergies/{allergyId}")
    @CrossOrigin(origins="*", maxAge=3600)
    public ResponseEntity<?> deleteAllergy(@PathVariable Long medId, @PathVariable Long allergyId) {
        MedCard medCard = repository.findById(medId)
                .orElseThrow(() -> new MedCardNotFoundException(medId));

        List<Allergy> newAllergies = deleteAllergyFromMedCard(medCard, allergyId);
        medCard.setAllergies(newAllergies);

        EntityModel<MedCard> entityModel = assembler.toModel(repository.save(medCard));
        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    private List<Allergy> deleteAllergyFromMedCard(MedCard medCard, Long id) {
        List<Allergy> allergies = medCard.getAllergies();

        for (Allergy allergy : allergies) {
            if (allergy.getId() == id) {
                allergies.remove(allergy);
                return allergies;
            }
        }
        throw new AllergyNotFoundException(id, medCard.getId());
    }
}
