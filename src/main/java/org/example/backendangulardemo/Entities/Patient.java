package org.example.backendangulardemo.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.backendangulardemo.Enum.Gender;
import org.hibernate.annotations.Cascade;

import java.time.LocalDate;
import java.util.List;

@Entity
@AllArgsConstructor @NoArgsConstructor @Builder
@Data
public class Patient {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private LocalDate dateOfBirth;
    private String address;
    private String bloodGroup;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Appointment> appointments;
}
