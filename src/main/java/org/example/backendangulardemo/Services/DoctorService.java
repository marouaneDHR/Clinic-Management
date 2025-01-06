package org.example.backendangulardemo.Services;

import lombok.AllArgsConstructor;
import org.example.backendangulardemo.Entities.Doctor;
import org.example.backendangulardemo.Repositories.DoctorRepsoitory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DoctorService {

    private final DoctorRepsoitory doctorRepsoitory;

    public DoctorService(DoctorRepsoitory doctorRepsoitory) {
        this.doctorRepsoitory = doctorRepsoitory;
    }

    public Doctor getDoctorById(Long id) {
        return doctorRepsoitory.findById(id).orElse(null);
    }

    public List<Doctor> getAllDoctors(){
        return doctorRepsoitory.findAll();
    }

    public Doctor createDoctor(Doctor doctor) {
        return doctorRepsoitory.save(doctor);
    }

    public void deleteDoctorById(Long id) {
        doctorRepsoitory.deleteById(id);
    }

    public Doctor updateDoctor(Doctor doctor) {
        if(doctor.getId() == null){
            throw new IllegalArgumentException("Doctor id cannot be null");
        }
        return doctorRepsoitory.save(doctor);
    }

    public List<Doctor> getAvailableDoctor() {
        return doctorRepsoitory.getAvailableDoctors();
    }
}
