package com.artjomkuznetsov.healthhub.models.medcard;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.Objects;

@Entity
public class Allergy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String allergy;
    private String description;

    public Allergy() {}

    public Allergy(Long id, String allergy, String description) {
        this.id = id;
        this.allergy = allergy;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAllergy() {
        return allergy;
    }

    public void setAllergy(String allergy) {
        this.allergy = allergy;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Allergy allergy1 = (Allergy) o;
        return Objects.equals(id, allergy1.id) && Objects.equals(allergy, allergy1.allergy) && Objects.equals(description, allergy1.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, allergy, description);
    }

    @Override
    public String toString() {
        return "Allergy{" +
                "id=" + id +
                ", allergy='" + allergy + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
