package com.artjomkuznetsov.healthhub.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Entity
public class Schedule {
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;
    private LocalDate date;
    private LocalTime time;
    private Long patientId;
    private Long doctorId;

    public Schedule(Long id, LocalDate date, LocalTime time, Long patientId, Long doctorId) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.patientId = patientId;
        this.doctorId = doctorId;
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

    public String getTime() {
        if (time != null) {
            return time.format(TIME_FORMATTER);
        }
        else {
            return null;
        }
    }

    public void setTime(String time) {
        this.time = LocalTime.parse(time, TIME_FORMATTER);;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Schedule schedule = (Schedule) o;
        return Objects.equals(id, schedule.id) && Objects.equals(date, schedule.date) && Objects.equals(time, schedule.time) && Objects.equals(patientId, schedule.patientId) && Objects.equals(doctorId, schedule.doctorId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date, time, patientId, doctorId);
    }

    @Override
    public String toString() {
        return "Schedule{" +
                "id=" + id +
                ", date=" + date +
                ", time=" + time +
                ", patientId=" + patientId +
                ", doctorId=" + doctorId +
                '}';
    }
}
