import { Component, OnInit } from '@angular/core';

import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { DoctorAuthResponse } from 'src/app/interfaces/requests&responses/doctorAuthResponse';
import { AuthenticationService } from 'src/app/services/authentication.service';

@Component({
  selector: 'app-authenticationDoctor',
  templateUrl: './authenticationDoctor.component.html',
  styleUrls: ['./authenticationDoctor.component.css']
})
export class AuthenticationDoctorComponent implements OnInit{

  public isFormInvalid: boolean | undefined;
  public isAgeValid: boolean | undefined;
  ngOnInit(): void {
    if (sessionStorage.getItem('token')) {
      this.router.navigate(["/home"]);
    }
  }

  constructor(private authenticationService: AuthenticationService, private router: Router) {

  }

  public registerDoctor(form: NgForm) {
    if (form.invalid) {
      this.isFormInvalid = false;
      return
    } else {
      const formFields = form.value;
      this.authenticationService.registerDoctor(formFields).subscribe({
        next: (response: DoctorAuthResponse) => {
          if (!response.ageValid) {
            this.isAgeValid = false;
          }
          this.authenticate(response)
        },
        error: console.error
      });
    }
  }

  private authenticate(response: DoctorAuthResponse) {
    sessionStorage.setItem("token", response.token.toString());
    sessionStorage.setItem("docId", response.doctorId.toString());
    sessionStorage.setItem("doctorStatus", response.status);
    sessionStorage.setItem("role", response.role);
    this.router.navigate(["/doctors-home"]);
  }
}
