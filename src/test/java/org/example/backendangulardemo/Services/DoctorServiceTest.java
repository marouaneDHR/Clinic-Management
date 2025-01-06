package org.example.backendangulardemo.Services;

import lombok.extern.slf4j.Slf4j;
import org.example.backendangulardemo.Entities.Doctor;

import org.example.backendangulardemo.Repositories.DoctorRepsoitory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;


@Slf4j
@ExtendWith(SpringExtension.class)
public class DoctorServiceTest {

    @Mock
    private DoctorRepsoitory doctorRepsoitory;

    @InjectMocks
    private DoctorService underTest;
    private Doctor d1 ;
    private Doctor d2 ;

    @BeforeEach
    void setUp() {
        d1 = Doctor.builder()
                .id(1L)
                .firstName("dahour")
                .lastName("mahdi")
                .bio("MahdiBio")
                .licenseNumber("1234567")
                .email("mahdi@gmail.com")
                .phone("0665686734")
                .available(true)
                .specialization("Cardio")
                .build();
        d2 = Doctor.builder()
                .id(1L)
                .firstName("dahour")
                .lastName("marouane")
                .bio("MarouaneBio")
                .licenseNumber("12345")
                .email("marouane@gmail.com")
                .phone("0665686734")
                .available(false)
                .specialization("chirurgie")
                .build();
    }

    @Test
    void ItShouldReturnAvailableDoctors(){
        //given
        given(doctorRepsoitory.getAvailableDoctors()).willReturn(List.of(d1));

        //when
        List<Doctor> result = underTest.getAvailableDoctor();

        //then

        assertThat(result).isNotNull();
        log.info("result not null");

        assertThat(result.get(0)).isEqualTo(d1);
        log.info("first element is d1");
    }
}
