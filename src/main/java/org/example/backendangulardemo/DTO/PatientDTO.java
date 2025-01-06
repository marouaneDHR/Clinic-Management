package org.example.backendangulardemo.DTO;

import lombok.Data;
import org.example.backendangulardemo.Enum.Gender;

@Data
public class PatientDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String gender;
    private String email;
    private String phone;
    private String address;
    private String bloodGroup;
}
