package org.example.backendangulardemo.Repositories;

import org.example.backendangulardemo.Entities.Appointment;
import org.example.backendangulardemo.Enum.AppointmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointementRepository extends JpaRepository<Appointment, Long> {
    Appointment getAppointmentByPatientId(Long patientId);

    List<Appointment> findAppointmentByStatus(AppointmentStatus status);

    List<Appointment> getAppointmentsByPatientId(Long patientId);
}
