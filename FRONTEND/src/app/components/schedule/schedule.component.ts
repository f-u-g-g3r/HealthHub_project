import { Component } from '@angular/core';
import {Router} from "@angular/router";
import {AuthenticationService} from "../../services/authentication.service";
import {Calendar} from "../../interfaces/calendar";
import {UserService} from "../../services/user.service";
import {Schedule} from "../../interfaces/schedule";
import {DoctorMinimal} from "../../interfaces/doctorMinimal";
import {User} from "../../interfaces/user";
import {SchedulesPage} from "../../interfaces/schedulesPage";

@Component({
  selector: 'app-schedule',
  templateUrl: './schedule.component.html',
  styleUrls: ['./schedule.component.css']
})
export class ScheduleComponent {
  constructor(private router: Router, public service: AuthenticationService, public userService: UserService) {}
  public page = 0;
  public schedules!: SchedulesPage;
  public patientsArr!: Map<any, any>;
  public chosenUser: User | undefined;
  public ascSorting = true;
  public totalPagesArr: number[] = [];




  ngOnInit(): void {
    this.service.checkAuthentication('DOCTOR');
    this.getSchedules();

  }

  public getSchedules() {
    let sorting = '';
    if (this.ascSorting) {
      sorting = 'ASC'
    } else {
      sorting = 'DESC'
    }
    this.ascSorting = !this.ascSorting;

    this.userService.getDoctorSchedule2(sessionStorage.getItem("docId"), sorting, this.page).subscribe({
      next: (response) => {
        this.schedules = {
          content: response.content,
          pageable: {
            pageNumber: response.pageable.pageNumber,
          },
          last: response.last,
          totalPages: response.totalPages,
          first: response.first,
        };
        this.patientsArr = this.getPatientNames();
        if (this.totalPagesArr.length == 0) {
          for (let i = 1; i < this.schedules.totalPages + 1; i++) {
            this.totalPagesArr.push(i);
          }
          console.log(this.totalPagesArr);
        }
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
    this.schedules.content.forEach((schedule) => {
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

    return patientsArr;
  }
}
