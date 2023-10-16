package com.artjomkuznetsov.healthhub.models.medcard;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Objects;

@Entity
public class MedHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String disease;
    private String description;
    private String dateOfIllness;

    public MedHistory() {}

    public MedHistory(Long id, String disease, String description, String dateOfIllness) {
        this.id = id;
        this.disease = disease;
        this.description = description;
        this.dateOfIllness = dateOfIllness;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDisease() {
        return disease;
    }

    public void setDisease(String disease) {
        this.disease = disease;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDateOfIllness() {
        return dateOfIllness;
    }

    public void setDateOfIllness(String dateOfIllness) {
        this.dateOfIllness = dateOfIllness;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MedHistory that = (MedHistory) o;
        return Objects.equals(id, that.id) && Objects.equals(disease, that.disease) && Objects.equals(description, that.description) && Objects.equals(dateOfIllness, that.dateOfIllness);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, disease, description, dateOfIllness);
    }

    @Override
    public String toString() {
        return "MedHistory{" +
                "id=" + id +
                ", disease='" + disease + '\'' +
                ", description='" + description + '\'' +
                ", dateOfIllness='" + dateOfIllness + '\'' +
                '}';
    }
}
