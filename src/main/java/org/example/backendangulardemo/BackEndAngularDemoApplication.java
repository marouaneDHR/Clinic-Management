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
					.firstName("dahour")
					.lastName("marouane")
					.email("marouane@gmail.com")
					.dateOfBirth(LocalDate.of(2001, 7, 27))
					.bloodGroup("A+")
					.phone("0665686734")
					.address("AddressDeMarouane")
					.gender(Gender.MALE)
					.build());
			doctorRepsoitory.save(Doctor.builder()
					.firstName("dahour")
					.lastName("daho")
					.bio("MahdiBio")
					.licenseNumber("1234567")
					.email("mahdi@gmail.com")
					.phone("0665686734")
					.specialization("Cardio")
					.build());
			appointmentService.createAppointment(LocalDateTime.now(), repository.getPatientByFirstName("dahour").getId(), doctorRepsoitory.getDoctorByFirstName("dahour").getId());
		};
	}
}
