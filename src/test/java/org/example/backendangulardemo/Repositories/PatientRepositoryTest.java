package org.example.backendangulardemo.Repositories;

import lombok.extern.slf4j.Slf4j;
import org.example.backendangulardemo.Entities.Patient;
import org.example.backendangulardemo.Enum.Gender;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;


import java.util.Collections;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
@Slf4j
public class PatientRepositoryTest {
    @Mock
    private PatientRepository patientRepository;

    private Patient p1;
    private Patient p2;

    @BeforeEach
    void setUp() {
        p1 = Patient.builder().id(1L).firstName("marouane").lastName("dahour").email("marouane@gmail.com").gender(Gender.MALE).build();
        p2 = Patient.builder().id(1L).firstName("test").lastName("test").email("test@gmail.com").gender(Gender.FEMALE).build();
    }

    @AfterEach
    void tearDown() {
        patientRepository.deleteAll();
    }

    @Test
    void ItShouldReturnPatientsBasedOnGivenGender(){
        //given
        when(patientRepository.findByGender(Gender.MALE)).thenReturn(List.of(p1));

        //when
        List<Patient> patients = patientRepository.findByGender(Gender.MALE);

        //then
        assertThat(patients).isNotEmpty();
        log.info("The patients list is not empty");
        assertThat(patients.size()).isEqualTo(1);
        log.info("The patients list contain only one element ");
    }


    @Test
    void ItShouldNotFindMalePatient(){
        //given
        when(patientRepository.findByGender(Gender.MALE)).thenReturn(Collections.emptyList());

        //when
        List<Patient> patients = patientRepository.findByGender(Gender.MALE);

        //then
        assertThat(patients).isEmpty();
        log.info("The patients list is empty / No Male patient found");
        assertThat(patients.size()).isEqualTo(0);
        log.info("The patients list contain only one element");
    }

    @Test
    void ItShouldReturnPaginatedPatients(){
        //given
        Pageable page = PageRequest.of(0,2);
        List<Patient> patients = List.of(p1, p2);
        int pageSize = patients.size();
        Page<Patient> pageResponse = new PageImpl<>(patients, page, pageSize);
        when(patientRepository.findAll(ArgumentMatchers.<Pageable>any())).thenReturn(pageResponse);

        //when
        Page<Patient> result = patientRepository.findAll(page);

        //then
        assertThat(result).isNotNull();
        log.info("The patients list is not empty");

        assertThat(result.getTotalElements()).isEqualTo(2);
        assertThat(result.getContent()).isEqualTo(patients);
        log.info("The desired reponse ");

        verify(patientRepository).findAll(ArgumentMatchers.<Pageable>any());
        log.info("the method findAll is invoked");
    }

    @Test
    void ItShouldNotReturnPatientByEmail(){
        //given
        String email = "mahdi@gmail.com";
        given(patientRepository.getPatientByEmail(email)).willReturn(null);

        //when
        Patient result = patientRepository.getPatientByEmail(email);

        //then
        assertThat(result).isNull();
        log.info("no patient with email is found");
    }

    @Test
    void ItShouldReturnPatientBasedOnEmail(){
        //given
        String email = "marouane@gmail.com";
        given(patientRepository.getPatientByEmail(email)).willReturn(p1);

        //when
        Patient result = patientRepository.getPatientByEmail(email);

        //then
        assertThat(result).isNotNull();
        log.info("no patient with email is found");

        assertThat(result.getEmail()).isEqualTo(email);
        log.info("compatibility in emails");
    }
}
