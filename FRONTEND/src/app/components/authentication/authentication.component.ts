import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { AuthenticationResponse } from 'src/app/interfaces/authenticationResponse';
import { AuthenticationService } from 'src/app/services/authentication.service';

@Component({
  selector: 'app-authentication',
  templateUrl: './authentication.component.html',
  styleUrls: ['./authentication.component.css']
})
export class AuthenticationComponent implements OnInit{

  constructor(private authenticationService: AuthenticationService) {

  }

  ngOnInit(): void {
      
  }

  public validateUser(loginForm: NgForm) {
    const formFields = loginForm.value;
    this.authenticationService.authenticate(formFields).subscribe({
      next: (response: AuthenticationResponse) => console.log(response),
      error: console.error
    });
  }
  


}
