import {Component, OnInit} from '@angular/core';
import {AuthenticationService} from "../../../services/authentication.service";
import {Router} from "@angular/router";
import {NgForm} from "@angular/forms";
import {AuthenticationResponse} from "../../../interfaces/requests&responses/authenticationResponse";

@Component({
  selector: 'app-registrationUser',
  templateUrl: './registrationUser.component.html',
  styleUrls: ['./registrationUser.component.css']
})
export class RegistrationUserComponent implements OnInit {
  public isFormValid: boolean | undefined;
  public isAgeValid: boolean | undefined;

  constructor(private authenticationService: AuthenticationService, private router: Router) {

  }

  ngOnInit(): void {
    if (sessionStorage.getItem('token')) {
      this.router.navigate(["/home"]);
    }
  }

  public registerUser(registerForm: NgForm) {
    if (registerForm.invalid) {
      this.isFormValid = false;
      return;
    } else {
      const formFields = registerForm.value;
      this.authenticationService.register(formFields).subscribe({
        next: (response: AuthenticationResponse) => {
          if (!response.ageValid) {
            this.isAgeValid = false;
          }
          sessionStorage.setItem('password', formFields["password"])
          this.authenticateUser(response);
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

}
