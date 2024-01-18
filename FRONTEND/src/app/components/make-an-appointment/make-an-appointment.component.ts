import { Component } from '@angular/core';
import {Router} from "@angular/router";
import {AuthenticationService} from "../../services/authentication.service";
import {UserService} from "../../services/user.service";
import {Doctor} from "../../interfaces/doctor";

@Component({
  selector: 'app-make-an-appointment',
  templateUrl: './make-an-appointment.component.html',
  styleUrls: ['./make-an-appointment.component.css']
})
export class MakeAnAppointmentComponent {
  constructor(private router: Router, public service: AuthenticationService, public userService: UserService) {}
  public doctors !: Doctor[];
  ngOnInit(): void {
    this.service.checkAuthentication("USER");
    this.userService.getActivatedDoctors().subscribe({
      next: (response: Doctor[]) => {
        this.doctors = response;
      },
      error: console.error
    });
  }
}
