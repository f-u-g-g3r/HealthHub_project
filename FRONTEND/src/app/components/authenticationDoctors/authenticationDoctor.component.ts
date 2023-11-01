import { Component, OnInit } from '@angular/core';

import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthenticationResponse } from 'src/app/interfaces/requests&responses/authenticationResponse';
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

  }

}
