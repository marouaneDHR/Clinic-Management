package org.example.backendangulardemo.Controllers;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.example.backendangulardemo.DTO.PatientDTO;
import org.example.backendangulardemo.Services.PatientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/patients")
@CrossOrigin(origins = "*")
public class PatientsController {
    private final PatientService patientService;

    public PatientsController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<PatientDTO>> getAllPatients(){
        return ResponseEntity.ok(patientService.getAllPatients());
    }

    @PostMapping("/save")
    public ResponseEntity<PatientDTO> createPatient(@RequestBody PatientDTO patientDTO){
        return ResponseEntity.ok(patientService.createPatient(patientDTO)) ;
    }

    @PostMapping("/update")
    public ResponseEntity<PatientDTO> updatePatient(@RequestBody PatientDTO patientDTO){
        return ResponseEntity.ok(patientService.updatePatient(patientDTO)) ;
    }

    @DeleteMapping("/delete/{PatientId}")
    public void deletePatient(@PathVariable("PatientId") int PatientId){
        System.out.println("delete");
        this.patientService.deletePatient((long) PatientId);
    }
}
