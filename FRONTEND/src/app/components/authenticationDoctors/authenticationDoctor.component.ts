import { Component, OnInit } from '@angular/core';

import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { doctorAuthResponse } from 'src/app/interfaces/requests&responses/doctorAuthResponse';
import { AuthenticationService } from 'src/app/services/authentication.service';

@Component({
  selector: 'app-authenticationDoctor',
  templateUrl: './authenticationDoctor.component.html',
  styleUrls: ['./authenticationDoctor.component.css']
})
export class AuthenticationDoctorComponent implements OnInit{

  ngOnInit(): void {
      
  }

  constructor(private authenticationService: AuthenticationService, private router: Router) {

  }

  public registerDoctor(form: NgForm) {
    const formFields = form.value;
    this.authenticationService.registerDoctor(formFields).subscribe({
      next: (response: doctorAuthResponse) => this.authenticate(response),
      error: console.error
    });
  }

  private authenticate(response: doctorAuthResponse) {
    sessionStorage.setItem("token", response.token.toString());
    sessionStorage.setItem("docId", response.doctorId.toString());
    sessionStorage.setItem("doctorStatus", response.status);
    sessionStorage.setItem("role", response.role);
    this.router.navigate(["/doctors-home"]);
  }
}
