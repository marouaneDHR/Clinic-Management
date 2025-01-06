package org.example.backendangulardemo.Services;
import org.example.backendangulardemo.Entities.Appointment;
import org.example.backendangulardemo.Entities.Doctor;
import org.example.backendangulardemo.Entities.Patient;
import org.example.backendangulardemo.Enum.AppointmentStatus;
import org.example.backendangulardemo.Repositories.AppointementRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AppointmentService {

    private final AppointementRepository appointementRepository;
    private final PatientService patientService;
    private final DoctorService doctorService;

    public AppointmentService(AppointementRepository appointementRepository, PatientService patientService, DoctorService doctorService) {
        this.appointementRepository = appointementRepository;
        this.patientService = patientService;
        this.doctorService = doctorService;
    }

    public Appointment getAppointmentById(Long id) {
        return appointementRepository.findById(id).orElse(null);
    }

    public List<Appointment> getAllAppointments() {
        return appointementRepository.findAll();
    }

    public List<Appointment> getAppointmentsByIdPatient(Long id) {
        if(id==null){
            throw new RuntimeException("id is null");
        }
        return appointementRepository.getAppointmentsByPatientId(id);
    }

    public Appointment createAppointment( LocalDateTime dateTime, Long PatientId, Long DoctorId) {
        Appointment appointment = new Appointment();
        Patient p = patientService.getPatient(PatientId);
        Doctor d = doctorService.getDoctorById(DoctorId);
        appointment.setDate(dateTime);
        appointment.setStatus(AppointmentStatus.SCHEDULED);
        appointment.setPatient(p);
        appointment.setDoctor(d);
        return appointementRepository.save(appointment);
    }

    public Appointment rescheduleAppointment( Long AppointmentId, LocalDateTime dateTime) {
        Appointment appointment = this.getAppointmentById(AppointmentId);
        appointment.setDate(dateTime);
        appointment.setStatus(AppointmentStatus.RESCHEDULED);
        return appointementRepository.save(appointment);
    }

    public Appointment reassignDoctor(Long DoctorId, Appointment appointment) {
        Doctor doctor = doctorService.getDoctorById(DoctorId);
        appointment.setDoctor(doctor);
        return appointementRepository.save(appointment);
    }

    public void deleteAppointment(Long appointmentId){
        appointementRepository.deleteById(appointmentId);
    }

    public List<Appointment> getAppointmentsByStatus(AppointmentStatus status) {
        return appointementRepository.findAppointmentByStatus(status);
    }

}
