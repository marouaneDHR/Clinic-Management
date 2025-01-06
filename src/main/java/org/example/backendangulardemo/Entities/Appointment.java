package org.example.backendangulardemo.Entities;

import jakarta.persistence.*;
import lombok.*;
import org.example.backendangulardemo.Enum.AppointmentStatus;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor @AllArgsConstructor @Builder
@Data @ToString
public class Appointment {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private LocalDateTime date;
    private AppointmentStatus status;
    @ManyToOne
    private Doctor doctor;
    @ManyToOne
    private Patient patient;
}
