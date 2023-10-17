package com.artjomkuznetsov.healthhub.models.medcard;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.Objects;

@Entity
public class ResultOfSurvey {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private String surveysDate;

    public ResultOfSurvey() {}

    public ResultOfSurvey(Long id, String title, String description, String surveysDate) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.surveysDate = surveysDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSurveysDate() {
        return surveysDate;
    }

    public void setSurveysDate(String surveysDate) {
        this.surveysDate = surveysDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResultOfSurvey that = (ResultOfSurvey) o;
        return Objects.equals(id, that.id) && Objects.equals(title, that.title) && Objects.equals(description, that.description) && Objects.equals(surveysDate, that.surveysDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, surveysDate);
    }

    @Override
    public String toString() {
        return "ResultOfSurvey{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", surveysDate='" + surveysDate + '\'' +
                '}';
    }
}
