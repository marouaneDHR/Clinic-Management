package org.example.backendangulardemo.Services;

import lombok.AllArgsConstructor;
import org.example.backendangulardemo.DTO.PatientDTO;
import org.example.backendangulardemo.Entities.Patient;
import org.example.backendangulardemo.Mappers.PatientMappers;
import org.example.backendangulardemo.Repositories.PatientRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class PatientService {

    private final PatientRepository patientRepository;
    private final PatientMappers patientMappers;

    public PatientService(PatientRepository patientRepository, PatientMappers patientMappers) {
        this.patientRepository = patientRepository;
        this.patientMappers = patientMappers;
    }

    public Patient getPatient(long id) {
        return patientRepository.findById(id).orElse(null);
    }

    public List<PatientDTO> getAllPatients() {
        List<PatientDTO> patientDTOS = new ArrayList<>();
        patientRepository.findAll().forEach(patient -> {
             patientDTOS.add(patientMappers.fromPatient(patient));
        });
        return patientDTOS;
    }

    public Page<Patient> getPatients(int page, int size) {
        return patientRepository.findAll( PageRequest.of(page, size));
    }

    public Patient getPatientByEmail(String email) {
        return patientRepository.getPatientByEmail(email);
    }

    public PatientDTO createPatient(PatientDTO patientDTO) {
        Patient patient = patientMappers.FromPatientDTO(patientDTO);
        return patientMappers.fromPatient(patientRepository.save(patient));
    }

    public void deletePatientById(long id) {
        patientRepository.deleteById(id);
    }

    public PatientDTO updatePatient(PatientDTO patientDTO) {
        if (patientDTO.getId() == null) {
            throw new IllegalArgumentException("Patient ID must not be null for an update operation.");
        }
        Patient existingPatient = patientRepository.findById(patientDTO.getId()).orElse(null);
        if(existingPatient ==null){
            throw new IllegalArgumentException("Patient not found");
        }
        existingPatient.setFirstName(patientDTO.getFirstName());
        existingPatient.setLastName(patientDTO.getLastName());
        existingPatient.setEmail(patientDTO.getEmail());
        existingPatient.setAddress(patientDTO.getAddress());
        existingPatient.setPhone(patientDTO.getPhone());
        existingPatient.setBloodGroup(patientDTO.getBloodGroup());

        return patientMappers.fromPatient(patientRepository.save(existingPatient));
    }

    public void deletePatient(Long patientId) {
        if(patientId == null) {
            throw new RuntimeException("Patient ID must not be null for an delete operation.");
        }
        System.out.println(patientId);
        this.patientRepository.deleteById(patientId);
    }

}
