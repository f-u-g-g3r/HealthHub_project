package com.artjomkuznetsov.healthhub.models;

import jakarta.persistence.*;
import org.springframework.data.repository.NoRepositoryBean;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "calendar")
public class Calendar {
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;
    private Long ownerId;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "calendar_id")
    private List<Schedule> schedule;

    private LocalTime oneAppointmentTime;
    private LocalTime workStartTime;
    private LocalTime workEndTime;

    public Calendar(Long id, Long ownerId, List<Schedule> schedule, LocalTime oneAppointmentTime, LocalTime workStartTime, LocalTime workEndTime) {
        this.id = id;
        this.ownerId = ownerId;
        this.schedule = schedule;
        this.oneAppointmentTime = oneAppointmentTime;
        this.workStartTime = workStartTime;
        this.workEndTime = workEndTime;
    }

    public Calendar() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public List<Schedule> getSchedule() {
        return schedule;
    }

    public void setSchedule(List<Schedule> schedule) {
        this.schedule = schedule;
    }


    public String getOneAppointmentTime() {
        if (oneAppointmentTime != null) {
            return oneAppointmentTime.format(TIME_FORMATTER);
        }
        else {
            return null;
        }
    }

    public void setOneAppointmentTime(String oneAppointmentTime) {
        this.oneAppointmentTime = LocalTime.parse(oneAppointmentTime, TIME_FORMATTER);
    }

    public String getWorkStartTime() {
        if (workStartTime != null) {
            return workStartTime.format(TIME_FORMATTER);
        }
        else {
            return null;
        }
    }

    public void setWorkStartTime(String workStartTime) {
        this.workStartTime = LocalTime.parse(workStartTime, TIME_FORMATTER);
    }

    public String getWorkEndTime() {
        if (workEndTime != null) {
            return workEndTime.format(TIME_FORMATTER);
        }
        else {
            return null;
        }
    }

    public void setWorkEndTime(String workEndTime) {
        this.workEndTime = LocalTime.parse(workEndTime, TIME_FORMATTER);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Calendar calendar = (Calendar) o;
        return Objects.equals(id, calendar.id) && Objects.equals(ownerId, calendar.ownerId) && Objects.equals(schedule, calendar.schedule) && Objects.equals(oneAppointmentTime, calendar.oneAppointmentTime) && Objects.equals(workStartTime, calendar.workStartTime) && Objects.equals(workEndTime, calendar.workEndTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, ownerId, schedule, oneAppointmentTime, workStartTime, workEndTime);
    }

    @Override
    public String toString() {
        return "Calendar{" +
                "id=" + id +
                ", ownerId=" + ownerId +
                ", schedule=" + schedule +
                ", oneAppointmentTime=" + oneAppointmentTime +
                ", workStartTime=" + workStartTime +
                ", workEndTime=" + workEndTime +
                '}';
    }
}

