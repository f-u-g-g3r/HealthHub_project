import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthenticationResponse } from 'src/app/interfaces/requests&responses/authenticationResponse';
import { DoctorAuthResponse } from 'src/app/interfaces/requests&responses/doctorAuthResponse';
import { AuthenticationService } from 'src/app/services/authentication.service';

@Component({
  selector: 'app-authentication',
  templateUrl: './authentication.component.html',
  styleUrls: ['./authentication.component.css']
})
export class AuthenticationComponent implements OnInit{
  public isFormValid: boolean | undefined;

  constructor(private authenticationService: AuthenticationService, private router: Router) {

  }

  ngOnInit(): void {
      if (sessionStorage.getItem('token')) {
        this.router.navigate(["/home"]);
      }
  }

  public validateUser(loginForm: NgForm) {
    if (loginForm.invalid) {
      this.isFormValid = false;
      return;
    } else {
      const formFields = loginForm.value;
      this.authenticationService.authenticate(formFields).subscribe({
        next: (response: AuthenticationResponse) => {
          sessionStorage.setItem('password', formFields["password"])
          if (response.role === "DOCTOR") {
            this.authenticateDoctor(response);
          } else if (response.role === "USER" || response.role === "ADMIN") {
            this.authenticateUser(response);
          }
        },
        error: console.error
      });
    }


  }



  private authenticateUser(response: AuthenticationResponse) {
    sessionStorage.setItem("token", response.token.toString());
    sessionStorage.setItem("uid", response.uid.toString());
    sessionStorage.setItem("medCardId", response.medCardId.toString());
    sessionStorage.setItem("role", response.role);
    this.router.navigate(["/home"]);
  }

  private authenticateDoctor(response: AuthenticationResponse) {
    sessionStorage.setItem("token", response.token.toString());
    sessionStorage.setItem("docId", response.doctorId.toString());
    sessionStorage.setItem("role", response.role);
    this.router.navigate(["/doctors-home"]);
  }



}
