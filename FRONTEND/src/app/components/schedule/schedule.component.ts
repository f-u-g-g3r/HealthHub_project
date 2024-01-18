import { Component } from '@angular/core';
import {Router} from "@angular/router";
import {AuthenticationService} from "../../services/authentication.service";
import {Calendar} from "../../interfaces/calendar";
import {UserService} from "../../services/user.service";
import {Schedule} from "../../interfaces/schedule";

@Component({
  selector: 'app-schedule',
  templateUrl: './schedule.component.html',
  styleUrls: ['./schedule.component.css']
})
export class ScheduleComponent {
  constructor(private router: Router, public service: AuthenticationService, public userService: UserService) {}

  public schedule!: Schedule[];


  ngOnInit(): void {
    this.service.checkAuthentication('DOCTOR');

    this.userService.getDoctorSchedule(sessionStorage.getItem("docId")).subscribe({
      next: (response: Calendar) => {
        this.schedule = response.schedule;
      },
      error: console.error
    });
  }
}
