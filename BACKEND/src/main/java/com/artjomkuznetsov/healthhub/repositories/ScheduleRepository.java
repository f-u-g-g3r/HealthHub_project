package com.artjomkuznetsov.healthhub.repositories;

import com.artjomkuznetsov.healthhub.models.Schedule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findAllByPatientId(Long patientId);
    List<Schedule> findAllByDoctorId(Long doctorId);
    Page<Schedule> findAllByDoctorId(Long doctorId, PageRequest pageRequest);
    void deleteByPatientId(Long patientId);
}
