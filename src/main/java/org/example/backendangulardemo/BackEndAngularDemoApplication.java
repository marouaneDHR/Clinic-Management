package org.example.backendangulardemo;

import org.example.backendangulardemo.Entities.Doctor;
import org.example.backendangulardemo.Entities.Patient;
import org.example.backendangulardemo.Enum.Gender;
import org.example.backendangulardemo.Repositories.DoctorRepsoitory;
import org.example.backendangulardemo.Repositories.PatientRepository;
import org.example.backendangulardemo.Services.AppointmentService;
import org.example.backendangulardemo.Services.DoctorService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.time.LocalDateTime;

@SpringBootApplication
public class BackEndAngularDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackEndAngularDemoApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(PatientRepository repository, DoctorRepsoitory doctorRepsoitory, AppointmentService appointmentService) {
		return args ->{
			repository.save(Patient.builder()
					.firstName("test")
					.lastName("test")
					.email("test@gmail.com")
					.dateOfBirth(LocalDate.of(2001, 7, 27))
					.bloodGroup("A+")
					.phone("0000000000")
					.address("TestAddress")
					.gender(Gender.MALE)
					.build());
			doctorRepsoitory.save(Doctor.builder()
					.firstName("testD")
					.lastName("testD")
					.bio("TestBio")
					.licenseNumber("1234567")
					.email("testD@gmail.com")
					.phone("0000000000")
					.specialization("Cardio")
					.build());
			appointmentService.createAppointment(LocalDateTime.now(), repository.getPatientByFirstName("test").getId(), doctorRepsoitory.getDoctorByFirstName("testD").getId());
		};
	}
}
