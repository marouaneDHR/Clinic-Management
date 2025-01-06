package org.example.backendangulardemo.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@NoArgsConstructor @AllArgsConstructor @Builder
@Data
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String specialization;
    @Column(length = 1000)
    private String bio;
    private Boolean available;
    private String licenseNumber;
    @OneToMany(mappedBy = "doctor")
    List<Appointment> appointments;
}
