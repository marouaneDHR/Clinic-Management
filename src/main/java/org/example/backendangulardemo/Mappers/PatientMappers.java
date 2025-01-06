package org.example.backendangulardemo.Mappers;

import org.example.backendangulardemo.DTO.PatientDTO;
import org.example.backendangulardemo.Entities.Patient;
import org.example.backendangulardemo.Enum.Gender;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
public class PatientMappers {

    public  PatientDTO fromPatient(Patient patient) {
        if(patient == null) {
            throw new RuntimeException("Patient is null");
        }

        PatientDTO patientDTO = new PatientDTO();
        BeanUtils.copyProperties(patient, patientDTO);
        patientDTO.setGender(patient.getGender().toString());
        return patientDTO;
    }

    public  Patient FromPatientDTO(PatientDTO patientDTO) {
        if(patientDTO == null) {
            throw new RuntimeException("Patient DTO is null");
        }

        Patient patient = new Patient();
        BeanUtils.copyProperties(patientDTO, patient);
        if ((patientDTO.getGender().equalsIgnoreCase(Gender.MALE.toString()))) {
            patient.setGender(Gender.MALE);
        } else {
            patient.setGender(Gender.FEMALE);
        }
        return patient;
    }
}
