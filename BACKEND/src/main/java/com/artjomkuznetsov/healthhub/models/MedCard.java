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
    private Long ownerID;

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
    private Long familyDoctorID;

    public MedCard() {}

    public MedCard(Long ownerID, List<MedHistory> medHistory, String bloodType, String rhFactor, String allergies, String chronicDiseases, String resultsOfSurveys, Long familyDoctorID) {
        this.ownerID = ownerID;
        this.medHistory = medHistory;
        this.bloodType = bloodType;
        this.rhFactor = rhFactor;
        this.allergies = allergies;
        this.chronicDiseases = chronicDiseases;
        this.resultsOfSurveys = resultsOfSurveys;
        this.familyDoctorID = familyDoctorID;
    }

    public MedCard(Long ownerID) {
        this.ownerID = ownerID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(Long ownerID) {
        this.ownerID = ownerID;
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

    public String getAllergies() {
        return allergies;
    }

    public void setAllergies(String allergies) {
        this.allergies = allergies;
    }

    public String getChronicDiseases() {
        return chronicDiseases;
    }

    public void setChronicDiseases(String chronicDiseases) {
        this.chronicDiseases = chronicDiseases;
    }

    public String getResultsOfSurveys() {
        return resultsOfSurveys;
    }

    public void setResultsOfSurveys(String resultsOfSurveys) {
        this.resultsOfSurveys = resultsOfSurveys;
    }

    public Long getFamilyDoctorID() {
        return familyDoctorID;
    }

    public void setFamilyDoctorID(Long familyDoctorID) {
        this.familyDoctorID = familyDoctorID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MedCard medCard = (MedCard) o;
        return Objects.equals(id, medCard.id) && Objects.equals(ownerID, medCard.ownerID) && Objects.equals(medHistory, medCard.medHistory) && Objects.equals(bloodType, medCard.bloodType) && Objects.equals(rhFactor, medCard.rhFactor) && Objects.equals(allergies, medCard.allergies) && Objects.equals(chronicDiseases, medCard.chronicDiseases) && Objects.equals(resultsOfSurveys, medCard.resultsOfSurveys) && Objects.equals(familyDoctorID, medCard.familyDoctorID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, ownerID, medHistory, bloodType, rhFactor, allergies, chronicDiseases, resultsOfSurveys, familyDoctorID);
    }

    @Override
    public String toString() {
        return "MedCard{" +
                "id=" + id +
                ", ownerID=" + ownerID +
                ", medHistory='" + medHistory + '\'' +
                ", bloodType='" + bloodType + '\'' +
                ", rhFactor='" + rhFactor + '\'' +
                ", allergies='" + allergies + '\'' +
                ", chronicDiseases='" + chronicDiseases + '\'' +
                ", resultsOfSurveys='" + resultsOfSurveys + '\'' +
                ", familyDoctorID=" + familyDoctorID +
                '}';
    }
}
