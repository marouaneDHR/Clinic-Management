package org.example.backendangulardemo.Services;

import lombok.AllArgsConstructor;
import org.example.backendangulardemo.Entities.Patient;
import org.example.backendangulardemo.Repositories.PatientRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PatientService {

    private final PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public Patient getPatient(long id) {
        return patientRepository.findById(id).orElse(null);
    }

    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    public Page<Patient> getPatients(int page, int size) {
        return patientRepository.findAll( PageRequest.of(page, size));
    }

    public Patient getPatientByEmail(String email) {
        return patientRepository.getPatientByEmail(email);
    }

    public Patient createPatient(Patient patient) {
        return patientRepository.save(patient);
    }

    public void deletePatientById(long id) {
        patientRepository.deleteById(id);
    }

    public Patient updatePatient(Patient patient) {
        if (patient.getId() == null) {
            throw new IllegalArgumentException("Patient ID must not be null for an update operation.");
        }
        return patientRepository.save(patient);
    }

}
