import { Component } from '@angular/core';
import {Router} from "@angular/router";
import {AuthenticationService} from "../../services/authentication.service";
import {UserService} from "../../services/user.service";
import {Schedule} from "../../interfaces/schedule";
import {Calendar} from "../../interfaces/calendar";
import {NgForm} from "@angular/forms";

@Component({
  selector: 'app-working-details',
  templateUrl: './working-details.component.html',
  styleUrls: ['./working-details.component.css']
})
export class WorkingDetailsComponent {
  public calendar!: Calendar;
  constructor(private router: Router, public service: AuthenticationService, public userService: UserService) {}

  ngOnInit(): void {
    this.service.checkAuthentication('DOCTOR');

    this.userService.getDoctorSchedule(sessionStorage.getItem('docId')).subscribe({
      next: (response: Calendar) => {
        this.calendar = response;
      },
      error: console.error
    });
  }

  public updateOneAppointmentTime(form: NgForm) {
    let time = form.value['oneAppointmentTime'];
    const values = {
      "oneAppointmentTime": time
    }
     this.userService.updateDoctorCalendar(sessionStorage.getItem('docId'), values).subscribe({
       next: (response: Calendar) => {
         this.calendar.oneAppointmentTime = response.oneAppointmentTime;
       },
       error: console.error
     });
  }

  public updateWorkStartTime(form: NgForm) {
    let time = form.value['workStartTime'];
    const values = {
      "workStartTime": time
    }
     this.userService.updateDoctorCalendar(sessionStorage.getItem('docId'), values).subscribe({
       next: (response: Calendar) => {
         this.calendar.workStartTime = response.workStartTime;
       },
       error: console.error
     });
  }

  public updateWorkEndTime(form: NgForm) {
    let time = form.value['workEndTime'];
    const values = {
      "workEndTime": time
    }
     this.userService.updateDoctorCalendar(sessionStorage.getItem('docId'), values).subscribe({
       next: (response: Calendar) => {
         this.calendar.workEndTime = response.workEndTime;
       },
       error: console.error
     });
  }
}
