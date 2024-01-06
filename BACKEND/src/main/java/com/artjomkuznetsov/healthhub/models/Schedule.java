package com.artjomkuznetsov.healthhub.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

@Entity
public class Schedule {
    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;
    private LocalDate date;
    private LocalTime time;
    private Long patientId;

    public Schedule(Long id, LocalDate date, LocalTime time, Long patientId) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.patientId = patientId;
    }

    public Schedule() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Schedule schedule = (Schedule) o;
        return Objects.equals(id, schedule.id) && Objects.equals(date, schedule.date) && Objects.equals(time, schedule.time) && Objects.equals(patientId, schedule.patientId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date, time, patientId);
    }

    @Override
    public String toString() {
        return "Schedule{" +
                "id=" + id +
                ", date=" + date +
                ", time=" + time +
                ", patientId=" + patientId +
                '}';
    }
}
