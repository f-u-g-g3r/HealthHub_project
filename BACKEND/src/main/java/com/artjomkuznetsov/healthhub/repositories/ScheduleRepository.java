package com.artjomkuznetsov.healthhub.repositories;

import com.artjomkuznetsov.healthhub.models.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findAllByPatientId(Long patientId);
    void deleteByPatientId(Long patientId);
}
