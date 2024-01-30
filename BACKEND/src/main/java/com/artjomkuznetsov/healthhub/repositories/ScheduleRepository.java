package com.artjomkuznetsov.healthhub.repositories;

import com.artjomkuznetsov.healthhub.models.Schedule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findAllByPatientId(Long patientId);
    List<Schedule> findAllByDoctorId(Long doctorId);
    Page<Schedule> findAllByDoctorId(Long doctorId, PageRequest pageRequest);

    @Query(value = "SELECT s FROM Schedule s WHERE s.doctorId = ?1 and s.date = ?2")
    Page<Schedule> findAllByDoctorIdAndDate(Long doctorId, LocalDate date, PageRequest pageRequest);

    void deleteByPatientId(Long patientId);
}
