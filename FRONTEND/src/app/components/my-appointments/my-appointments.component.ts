import { Component } from '@angular/core';
import {Router} from "@angular/router";
import {AuthenticationService} from "../../services/authentication.service";
import {UserService} from "../../services/user.service";
import {Schedule} from "../../interfaces/schedule";
import {DoctorMinimal} from "../../interfaces/doctorMinimal";

@Component({
  selector: 'app-my-appointments',
  templateUrl: './my-appointments.component.html',
  styleUrls: ['./my-appointments.component.css']
})
export class MyAppointmentsComponent {
  public schedules!: Schedule[];
  public doctorsArr!: Map<any, any>;
  constructor(private router: Router, public service: AuthenticationService, public userService: UserService) {}
  ngOnInit(): void {
    this.service.checkAuthentication("USER");
    this.getMyAppointments();

  }

  public getMyAppointments() {
    this.userService.getUserAppointments(sessionStorage.getItem("uid")).subscribe({
      next: (response: Schedule[]) => {
        this.schedules = response;
        this.doctorsArr = this.getDoctorsNames();
      },
      error: console.error
    });
  }

  public removeAppointment(buttonClicked: any) {
    this.userService.deleteSchedule(buttonClicked.id, sessionStorage.getItem("uid")).subscribe({
      next: () => {
        this.getMyAppointments();
      },
      error: console.error
    });
  }

  private getDoctorsNames() {
    let doctorsArr = new Map();
    let doctorsIds:number[] = [];
    this.schedules.forEach((schedule) => {
      if (!doctorsIds.includes(schedule.doctorId)) {
        doctorsIds.push(schedule.doctorId);
      }
    })

    doctorsIds.forEach((docId) => {
      this.userService.getDoctorsName(docId).subscribe({
        next: (response: DoctorMinimal) => {
          doctorsArr.set(docId, response.firstname + " " + response.lastname);
        },
        error: console.error
      });
    })

    return doctorsArr;
  }
}
