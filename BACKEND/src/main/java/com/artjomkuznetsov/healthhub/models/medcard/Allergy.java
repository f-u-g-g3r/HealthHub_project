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

    private String Allergy;
    private String Description;

    public Allergy() {}

    public Allergy(Long id, String allergy, String description) {
        this.id = id;
        Allergy = allergy;
        Description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAllergy() {
        return Allergy;
    }

    public void setAllergy(String allergy) {
        Allergy = allergy;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Allergy allergy = (Allergy) o;
        return Objects.equals(id, allergy.id) && Objects.equals(Allergy, allergy.Allergy) && Objects.equals(Description, allergy.Description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, Allergy, Description);
    }

    @Override
    public String toString() {
        return "Allergy{" +
                "id=" + id +
                ", Allergy='" + Allergy + '\'' +
                ", Description='" + Description + '\'' +
                '}';
    }
}
