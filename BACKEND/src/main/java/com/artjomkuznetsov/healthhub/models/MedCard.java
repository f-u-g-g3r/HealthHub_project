package com.artjomkuznetsov.healthhub.models;

import com.artjomkuznetsov.healthhub.models.medcard.Allergy;
import com.artjomkuznetsov.healthhub.models.medcard.ChronicDisease;
import com.artjomkuznetsov.healthhub.models.medcard.MedHistory;
import com.artjomkuznetsov.healthhub.models.medcard.ResultOfSurvey;
import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "medcards")
public class MedCard {
    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;

    @OneToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "medcard_id")
    private List<MedHistory> medHistory;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "medcard_id")
    private List<Allergy> allergies;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "medcard_id")
    private List<ChronicDisease> chronicDiseases;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "medcard_id")
    private List<ResultOfSurvey> resultsOfSurveys;

    private String bloodType;
    private String rhFactor;

    public MedCard() {}

    public MedCard(User owner, List<MedHistory> medHistory, String bloodType, String rhFactor, List<Allergy> allergies, List<ChronicDisease> chronicDiseases, List<ResultOfSurvey> resultsOfSurveys) {
        this.owner = owner;
        this.medHistory = medHistory;
        this.bloodType = bloodType;
        this.rhFactor = rhFactor;
        this.allergies = allergies;
        this.chronicDiseases = chronicDiseases;
        this.resultsOfSurveys = resultsOfSurveys;
    }

    public MedCard(User owner) {
        this.owner = owner;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOwner() {
        return owner.getId();
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public List<MedHistory> getMedHistory() {
        return medHistory;
    }

    public void setMedHistory(List<MedHistory> medHistory) {
        this.medHistory = medHistory;
    }

    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    public String getRhFactor() {
        return rhFactor;
    }

    public void setRhFactor(String rhFactor) {
        this.rhFactor = rhFactor;
    }

    public List<Allergy> getAllergies() {
        return allergies;
    }

    public void setAllergies(List<Allergy> allergies) {
        this.allergies = allergies;
    }

    public List<ChronicDisease> getChronicDiseases() {
        return chronicDiseases;
    }

    public void setChronicDiseases(List<ChronicDisease> chronicDiseases) {
        this.chronicDiseases = chronicDiseases;
    }

    public List<ResultOfSurvey> getResultsOfSurveys() {
        return resultsOfSurveys;
    }

    public void setResultsOfSurveys(List<ResultOfSurvey> resultsOfSurveys) {
        this.resultsOfSurveys = resultsOfSurveys;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MedCard medCard = (MedCard) o;
        return Objects.equals(id, medCard.id) && Objects.equals(medHistory, medCard.medHistory) && Objects.equals(allergies, medCard.allergies) && Objects.equals(chronicDiseases, medCard.chronicDiseases) && Objects.equals(resultsOfSurveys, medCard.resultsOfSurveys) && Objects.equals(bloodType, medCard.bloodType) && Objects.equals(rhFactor, medCard.rhFactor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, medHistory, allergies, chronicDiseases, resultsOfSurveys, bloodType, rhFactor);
    }

    @Override
    public String toString() {
        return "MedCard{" +
                "id=" + id +
                ", owner=" + owner +
                ", medHistory=" + medHistory +
                ", allergies=" + allergies +
                ", chronicDiseases=" + chronicDiseases +
                ", resultsOfSurveys=" + resultsOfSurveys +
                ", bloodType='" + bloodType + '\'' +
                ", rhFactor='" + rhFactor + '\'' +
                '}';
    }
}
