import { Component } from '@angular/core';
import {Router} from "@angular/router";
import {AuthenticationService} from "../../services/authentication.service";
import {Calendar} from "../../interfaces/calendar";
import {UserService} from "../../services/user.service";
import {Schedule} from "../../interfaces/schedule";
import {DoctorMinimal} from "../../interfaces/doctorMinimal";
import {User} from "../../interfaces/user";

@Component({
  selector: 'app-schedule',
  templateUrl: './schedule.component.html',
  styleUrls: ['./schedule.component.css']
})
export class ScheduleComponent {
  constructor(private router: Router, public service: AuthenticationService, public userService: UserService) {}

  public schedules!: Schedule[];
  public patientsArr!: Map<any, any>;
  public chosenUser: User | undefined;


  ngOnInit(): void {
    this.service.checkAuthentication('DOCTOR');

    this.userService.getDoctorSchedule(sessionStorage.getItem("docId")).subscribe({
      next: (response: Calendar) => {
        this.schedules = response.schedule;
        this.patientsArr = this.getPatientNames();
      },
      error: console.error
    });
  }

  public getUserInfo(buttonClicked: any) {
    this.userService.getOneUser(buttonClicked.id).subscribe({
      next: response => {
        this.chosenUser = response;
      },
      error: console.error
    });
  }

  private getPatientNames() {
    let patientsArr = new Map();
    let patientsIds:number[] = [];
    this.schedules.forEach((schedule) => {
      if (!patientsIds.includes(schedule.patientId)) {
        patientsIds.push(schedule.patientId);
      }
    })

    patientsIds.forEach((patientId) => {
      this.userService.getOneUser(patientId).subscribe({
        next: (response: User) => {
          patientsArr.set(patientId, response.firstname + " " + response.lastname);
        },
        error: console.error
      });
    })

    console.log(patientsArr)
    return patientsArr;
  }
}
