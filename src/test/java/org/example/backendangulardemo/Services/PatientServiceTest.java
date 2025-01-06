package org.example.backendangulardemo.Services;


import lombok.extern.slf4j.Slf4j;
import org.example.backendangulardemo.Entities.Patient;
import org.example.backendangulardemo.Enum.Gender;
import org.example.backendangulardemo.Repositories.PatientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatcher;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.any;
import static org.mockito.Mockito.when;

@Slf4j
@ExtendWith(MockitoExtension.class)
public class PatientServiceTest {

    @Mock
    private PatientRepository patientRepository;

    @InjectMocks
    private PatientService underTest ;

    private Patient p;


    @BeforeEach
    void setUp(){
        p = Patient.builder()
                .id(1L)
                .firstName("dahour")
                .lastName("marouane")
                .email("marouane@gmail.com")
                .dateOfBirth(LocalDate.of(2001, 7, 27))
                .bloodGroup("A+")
                .phone("0665686734")
                .address("AddressDeMarouane")
                .gender(Gender.MALE)
                .build();
    }

    @Test
    void ItShouldUpdatePatient() {
        //given
        p.setEmail("marouane2001@gmail.com");
        when(patientRepository.save(ArgumentMatchers.<Patient>any())).thenAnswer(invocationOnMock -> invocationOnMock.getArgument(0));

        //when
        Patient result = underTest.updatePatient(p);

        //then

        assertThat(result).isNotNull() ;
        log.info("result not null {}", result);

        assertThat(result.getEmail()).isEqualTo("marouane2001@gmail.com");
        log.info("patient updated ");
        }
}
