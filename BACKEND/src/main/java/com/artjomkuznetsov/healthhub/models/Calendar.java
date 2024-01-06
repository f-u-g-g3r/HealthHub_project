package com.artjomkuznetsov.healthhub.models;

import jakarta.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "calendar")
public class Calendar {
    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;
    private Long ownerId;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "calendar_id")
    private List<Schedule> schedule;

    public Calendar(Long id, Long ownerId, List<Schedule> schedule) {
        this.id = id;
        this.ownerId = ownerId;
        this.schedule = schedule;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Calendar calendar = (Calendar) o;
        return Objects.equals(id, calendar.id) && Objects.equals(ownerId, calendar.ownerId) && Objects.equals(schedule, calendar.schedule);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, ownerId, schedule);
    }

    @Override
    public String toString() {
        return "Calendar{" +
                "id=" + id +
                ", ownerId=" + ownerId +
                ", schedule=" + schedule +
                '}';
    }
}

