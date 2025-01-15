import { Injectable } from '@angular/core';
import {config, Observable} from 'rxjs';
import {HttpClient} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class PatientApiService {
  private apiLink:string = "http://localhost:8082/api/patients"

  constructor(private http : HttpClient) { }


  getAllPatients() : Observable<Patient[]> {
    return this.http.get<Patient[]>(this.apiLink + "/all", {
      headers : {
         'Content-Type' : 'application/json',
         'Accept' : 'application/json',
      }
    });
  }

  createPatient(data:Patient) : Observable<Patient> {
    return this.http.post<Patient>(this.apiLink+"/save",data, {
      headers : {
        'Content-Type' : 'application/json',
        'Accept' : 'application/json',
      }
    } );
  }

  update(data:Patient) : Observable<Patient> {
    return this.http.post<Patient>(this.apiLink+"/update",data, {
      headers : {
        'Content-Type' : 'application/json',
        'Accept' : 'application/json',
      }
    })
  }

  delete(idPatient:bigint) : Observable<void>{
    console.log(this.apiLink+"/delete/"+idPatient);
    return this.http.delete<void>(this.apiLink+"/delete/"+idPatient);
  }

}
export interface Patient {
  id: bigint,
  firstName :string,
  lastName:string,
  gender:string,
  email:string;
  phone:string;
  address:string;
  bloodGroup:string;
}
