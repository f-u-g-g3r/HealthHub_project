package com.artjomkuznetsov.healthhub.controllers.medCard;

import com.artjomkuznetsov.healthhub.assemblers.MedCardModelAssembler;
import com.artjomkuznetsov.healthhub.exceptions.ChronicDiseaseNotFoundException;
import com.artjomkuznetsov.healthhub.exceptions.MedCardNotFoundException;
import com.artjomkuznetsov.healthhub.exceptions.ResultOfSurveyNotFoundException;
import com.artjomkuznetsov.healthhub.models.MedCard;
import com.artjomkuznetsov.healthhub.models.medcard.ChronicDisease;
import com.artjomkuznetsov.healthhub.models.medcard.ResultOfSurvey;
import com.artjomkuznetsov.healthhub.repositories.MedCardRepository;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ResultOfSurveyController {
    private final MedCardRepository repository;
    private final MedCardModelAssembler assembler;

    public ResultOfSurveyController(MedCardRepository repository, MedCardModelAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    @PostMapping("/med-cards/{id}/results-of-survey")
    public ResponseEntity<?> newResult(@RequestBody List<ResultOfSurvey> newResultOfSurvey, @PathVariable Long id) {
        MedCard medCard = repository.findById(id)
                .orElseThrow(() -> new MedCardNotFoundException(id));

        List<ResultOfSurvey> resultsOfSurveys = medCard.getResultsOfSurveys();
        resultsOfSurveys.addAll(newResultOfSurvey);
        medCard.setResultsOfSurveys(resultsOfSurveys);

        EntityModel<MedCard> entityModel = assembler.toModel(repository.save(medCard));

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);

    }

    @PutMapping("/med-cards/{medId}/results-of-survey/{resultOfSurveyId}")
    public ResponseEntity<?> updateResultOfSurvey(
            @RequestBody ResultOfSurvey updatedResultOfSurvey,
            @PathVariable Long medId,
            @PathVariable Long resultOfSurveyId
    ) {
        MedCard medCard = repository.findById(medId)
                .orElseThrow(() -> new MedCardNotFoundException(medId));

        List<ResultOfSurvey> newResultOfSurveys = updateResultsOfSurveysInMedCard(medCard, resultOfSurveyId, updatedResultOfSurvey);
        medCard.setResultsOfSurveys(newResultOfSurveys);

        EntityModel<MedCard> entityModel = assembler.toModel(repository.save(medCard));

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    private List<ResultOfSurvey> updateResultsOfSurveysInMedCard(MedCard medCard, Long id, ResultOfSurvey updatedResultOfSurvey) {
        List<ResultOfSurvey> resultsOfSurveys = medCard.getResultsOfSurveys();
        for (ResultOfSurvey resultOfSurvey : resultsOfSurveys) {
            if (resultOfSurvey.getId() == id) {
                resultOfSurvey.setTitle(updatedResultOfSurvey.getTitle());
                resultOfSurvey.setDescription(updatedResultOfSurvey.getDescription());
                resultOfSurvey.setSurveysDate(updatedResultOfSurvey.getSurveysDate());
                return resultsOfSurveys;
            }
        }
        throw new ResultOfSurveyNotFoundException(id, medCard.getId());
    }

    @DeleteMapping("/med-cards/{medId}/results-of-surveys/{resultOfSurveyId}")
    public ResponseEntity<?> deleteResultOfSurvey(@PathVariable Long medId, @PathVariable Long resultOfSurveyId) {
        MedCard medCard = repository.findById(medId)
                .orElseThrow(() -> new MedCardNotFoundException(medId));

        List<ResultOfSurvey> newResultOfSurveys = deleteResultOfSurveyFromMedCard(medCard, resultOfSurveyId);
        medCard.setResultsOfSurveys(newResultOfSurveys);

        EntityModel<MedCard> entityModel = assembler.toModel(repository.save(medCard));
        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    private List<ResultOfSurvey> deleteResultOfSurveyFromMedCard(MedCard medCard, Long id) {
        List<ResultOfSurvey> resultsOfSurveys = medCard.getResultsOfSurveys();

        for (ResultOfSurvey resultOfSurvey : resultsOfSurveys) {
            if (resultOfSurvey.getId() == id) {
                resultsOfSurveys.remove(resultOfSurvey);
                return resultsOfSurveys;
            }
        }
        throw new ResultOfSurveyNotFoundException(id, medCard.getId());
    }
}
