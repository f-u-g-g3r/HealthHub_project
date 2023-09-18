import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthenticationResponse } from 'src/app/interfaces/authenticationResponse';
import { AuthenticationService } from 'src/app/services/authentication.service';

@Component({
  selector: 'app-authentication',
  templateUrl: './authentication.component.html',
  styleUrls: ['./authentication.component.css']
})
export class AuthenticationComponent implements OnInit{

  constructor(private authenticationService: AuthenticationService, private router: Router) {

  }

  ngOnInit(): void {
      if (sessionStorage.getItem('token')) {
        this.router.navigate(["/home"]);
      }
  }

  public validateUser(loginForm: NgForm) {
    const formFields = loginForm.value;
    this.authenticationService.authenticate(formFields).subscribe({
      next: (response: AuthenticationResponse) => {
        sessionStorage.setItem('password', formFields["password"])
        this.authenticate(response);
      },
      error: console.error
    });
  }

  public registerUser(registerForm: NgForm) {
    const formFields = registerForm.value;
    this.authenticationService.register(formFields).subscribe({
      next: (response: AuthenticationResponse) => {
        sessionStorage.setItem('password', formFields["password"])
        this.authenticate(response);
      },
      error: console.error
    });
  }

  private authenticate(response: AuthenticationResponse) {
    console.log(response);
    sessionStorage.setItem("token", response.token.toString());
    sessionStorage.setItem("uid", response.uid.toString());
    sessionStorage.setItem("medCardId", response.medCardId.toString());
    sessionStorage.setItem("role", response.role);
    this.router.navigate(["/home"]);
  }
  


}
