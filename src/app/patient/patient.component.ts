import {Component, OnInit} from '@angular/core';
import {Patient, PatientApiService} from '../PatientService/patient-api.service';
import {FormGroup, FormBuilder, ReactiveFormsModule} from '@angular/forms';
import {NgForOf} from '@angular/common';

@Component({
  selector: 'app-patient',
  imports: [
    NgForOf,
    ReactiveFormsModule
  ],
  templateUrl: './patient.component.html',
  styleUrl: './patient.component.css'
})
export class PatientComponent implements OnInit{
   patientForm!: FormGroup ;
   patients : Patient[] = [] ;
   tableHead : string[] = ["Id", "Full Name", "Gender", "Blood Group", "Address", "Email","Phone"];
   patientToUpdate : Patient = {} as Patient;
   isEdit:boolean =false;

  constructor(private patientService : PatientApiService, private fb:FormBuilder) {
  }

  ngOnInit() : void {
    this.patientForm = this.fb.group({
      id : [0],
      firstName : [''],
      lastName : [''],
      gender : [''],
      email: [''],
      phone:[''],
      address:[''],
      bloodGroup:[''],
    })

    this.fetchAllPatients();
  }

  fetchAllPatients(): void {
    this.patientService.getAllPatients().subscribe({
      next : (patients : Patient[])=> {
        this.patients = patients;
      },
      error:(error)=>{
        console.log("Error Fetching Patient : ", error);
      }
    })
  }

  createPatient():void {
    const newPatient : Patient = this.patientForm.value;

    this.patientService.createPatient(newPatient).subscribe({
      next : (data : Patient)=> {
        this.patients.push(data);
      },
      error:(error)=>{
        console.log("Error Creating Patient: ", error);
      }
    });
  }

  setUpdateForm(toUpdate : Patient):void {
    this.patientToUpdate = toUpdate;
    this.isEdit = true;
    this.patientForm.patchValue(this.patientToUpdate);
  }

  updatePatient(): void {
    const patient : Patient = this.patientForm.value;
    this.patientService.update(patient).subscribe({
      next : (data : Patient)=> {
        console.log("Patient Updated Successfully");
        console.log(data);
        this.fetchAllPatients();
      },
      error:(error)=>{
        console.log("Error Updating Patient: ", error);
      }
    });
    this.isEdit = false;
    this.patientForm.reset();
  }

  deletePatient(patientId : bigint) : void {
    this.patientService.delete(patientId).subscribe({
      next: () => {
        console.log("patient deleted successfully") ;
        this.fetchAllPatients();
      },
      error:(error)=>{
        console.log("Error Deleting Patient : ", error);
      }
    });
  }

}

