package org.example.backendangulardemo.Repositories;

import lombok.extern.slf4j.Slf4j;
import org.example.backendangulardemo.Entities.Appointment;
import org.example.backendangulardemo.Entities.Doctor;
import org.example.backendangulardemo.Entities.Patient;
import org.example.backendangulardemo.Enum.AppointmentStatus;
import org.example.backendangulardemo.Enum.Gender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;

@DataJpaTest
@Slf4j
public class AppointmentRepositoryTest {

    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private DoctorRepsoitory doctorRepository;
    @Autowired
    private AppointementRepository underTest;
    private Appointment appointment;

    @BeforeEach
    void setUp() {
        Patient p = Patient.builder()
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

        Doctor d = Doctor.builder()
                .id(1L)
                .firstName("dahour")
                .lastName("mahdi")
                .bio("MahdiBio")
                .licenseNumber("1234567")
                .email("mahdi@gmail.com")
                .phone("0665686734")
                .specialization("Cardio")
                .build();

        appointment = Appointment.builder()
                .date(LocalDateTime.now())
                .patient(p)
                .doctor(d)
                .status(AppointmentStatus.SCHEDULED)
                .build();

        log.info("SetUp done");
    }

    @Test
    void ItShouldReturnAppointmentsByStatus() {
        //given
        AppointmentStatus status = AppointmentStatus.SCHEDULED;

        //when
        List<Appointment> appointmentList = underTest.findAppointmentByStatus(status);

        //then
        assertThat(appointmentList).isNotNull();
        log.info("appointmentList: {}", appointmentList);
        assertThat(appointmentList.size()).isEqualTo(1);
        log.info("appointment size is {}", appointmentList.size());
    }

    @Test
    void ItShouldReturnAppointmentsByPatientId() {
        //given
        Long id = 1L ;
        List<Appointment> appointmentList = List.of(appointment);
        given(underTest.getAppointmentsByPatientId(id)).willReturn(appointmentList);

        //when
        List<Appointment> appointments = underTest.getAppointmentsByPatientId(id);

        //then
        assertThat(appointments).isNotNull();
        log.info("appointments not empty");

        assertThat(appointments.size()).isEqualTo(1);
        log.info("appointments size is {}", appointments.size());
    }
}
