package org.example.backendangulardemo.Services;

import lombok.extern.slf4j.Slf4j;
import org.example.backendangulardemo.Entities.Appointment;
import org.example.backendangulardemo.Entities.Doctor;
import org.example.backendangulardemo.Entities.Patient;
import org.example.backendangulardemo.Enum.AppointmentStatus;
import org.example.backendangulardemo.Enum.Gender;
import org.example.backendangulardemo.Repositories.AppointementRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.BDDMockito.given;

@Slf4j
@ExtendWith(MockitoExtension.class)
public class AppointmentServiceTest {

    @Mock
    private AppointementRepository appointementRepository;
    @Mock
    private DoctorService doctorService;
    @Mock
    private PatientService patientService;

    @InjectMocks
    private AppointmentService undertest;
    
    private Patient p;
    private Doctor d1;
    private Doctor d2;

    private Appointment appointment;
    
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

         d1 = Doctor.builder()
                .id(1L)
                .firstName("dahour")
                .lastName("mahdi")
                .bio("MahdiBio")
                .licenseNumber("1234567")
                .email("mahdi@gmail.com")
                .phone("0665686734")
                .specialization("Cardio")
                .build();

         d2 = Doctor.builder()
                 .id(2L)
                 .firstName("jassab")
                 .lastName("khalil")
                 .bio("khalilBio")
                 .licenseNumber("1234567")
                 .email("khalil@gmail.com")
                 .phone("0665686734")
                 .specialization("Cardio")
                 .build();

         appointment = Appointment.builder()
                 .id(1L)
                .status(AppointmentStatus.SCHEDULED)
                .date(LocalDateTime.now())
                .patient(p)
                .doctor(d1)
                .build();
    }

    @AfterEach
    void tearDown(){
        appointment = null;
        p = null;
        d1 = null;
        d2 = null;
    }

    @Test
    void ItShouldCreateAppointment()    {
        // given
        // (mocking the methods used in the createAppointment method , instead of using the real implementation)
        when(patientService.getPatient(anyLong())).thenReturn(p);
        when(doctorService.getDoctorById(anyLong())).thenReturn(d1);
        when(appointementRepository.save(any(Appointment.class))).thenReturn(appointment);

        // when
        Appointment result = undertest.createAppointment(LocalDateTime.now(), p.getId(), d1.getId());

        // then
        assertThat(result).isNotNull();
        log.info("Appointment created successfully");

        assertThat(result.getPatient()).isEqualTo(p);
        assertThat(result.getDoctor()).isEqualTo(d1);
        log.info("Appointment data is correct");

        verify(appointementRepository).save(any(Appointment.class));
        log.info("save in appointment repo is being called");
    }

    @Test
    void ItShouldRescheduleAppointment() {
        // given
        LocalDateTime dateTime = LocalDateTime.of(2025, 1, 1, 10, 10);
        appointment.setDate(dateTime);
        appointment.setStatus(AppointmentStatus.RESCHEDULED);
        when(appointementRepository.findById(anyLong())).thenReturn(Optional.of(appointment));
        when(appointementRepository.save(any(Appointment.class))).thenAnswer(invocationOnMock -> invocationOnMock.getArgument(0));

        // when
        Appointment result = undertest.rescheduleAppointment(appointment.getId(), dateTime);

        // then
        assertThat(result).isNotNull();
        log.info("Appointment Not Null");
        assertThat(result.getDate()).isEqualTo(dateTime);
        assertThat(result.getStatus()).isEqualTo(AppointmentStatus.RESCHEDULED);
        log.info("Appointment infos are correct");
    }

    @Test
    void ItShouldReassignDoctor(){
        //given
        when(doctorService.getDoctorById(anyLong())).thenReturn(d2) ;
        when(appointementRepository.save(any(Appointment.class))).thenAnswer(invocationOnMock -> invocationOnMock.getArgument(0));

        //when
        Appointment result = undertest.reassignDoctor(2L, appointment);

        //then
        assertThat(result).isNotNull();
        log.info("Appointment Not Null");

        assertThat(result.getDoctor()).isEqualTo(d2);
        log.info("Appointment reassigned to doctor d2");
    }

    @Test
    void ItShouldReturnAppointmentOfPatient() {
        //given
        Long id = 1L;
        given(appointementRepository.getAppointmentsByPatientId(id)).willReturn(List.of(appointment));

        //when
        List<Appointment> result = undertest.getAppointmentsByIdPatient(id);

        //then
        assertThat(result).isNotNull();
        log.info("List of Appointment Not Null");

        assertThat(result).contains(appointment);
        log.info("the appointment list contains appointment");

        verify(appointementRepository) .getAppointmentsByPatientId(id);
        log.info("the desired method was invoked");
    }

}