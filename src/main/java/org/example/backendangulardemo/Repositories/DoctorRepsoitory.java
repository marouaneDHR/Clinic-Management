package org.example.backendangulardemo.Repositories;

import org.example.backendangulardemo.Entities.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoctorRepsoitory extends JpaRepository<Doctor, Long> {
    Doctor getDoctorByFirstName(String firstName);
    Doctor getDoctorByLicenseNumber(String licenseNumber);

    @Query("SELECT d FROM Doctor d WHERE d.available = true ")
    List<Doctor> getAvailableDoctors();
}
