
package org.example.backendangulardemo.Repositories;

import org.example.backendangulardemo.Entities.Patient;
import org.example.backendangulardemo.Enum.Gender;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    @Query("SELECT p FROM Patient p WHERE p.gender = :gender")
    List<Patient> findByGender(@Param("gender") Gender gender);

    Patient getPatientByFirstName(String firstName);

    Patient getPatientByEmail(String email);

    Page<Patient> findAll(Pageable pageable);
}
