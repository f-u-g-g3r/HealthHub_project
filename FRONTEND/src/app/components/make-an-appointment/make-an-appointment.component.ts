import { Component } from '@angular/core';
import {Router} from "@angular/router";
import {AuthenticationService} from "../../services/authentication.service";
import {UserService} from "../../services/user.service";
import {Doctor} from "../../interfaces/doctor";
import {NgForm} from "@angular/forms";
import {isEmpty} from "rxjs";

@Component({
  selector: 'app-make-an-appointment',
  templateUrl: './make-an-appointment.component.html',
  styleUrls: ['./make-an-appointment.component.css']
})
export class MakeAnAppointmentComponent {
  constructor(private router: Router, public service: AuthenticationService, public userService: UserService) {}
  public doctors !: Doctor[];
  public availableTime:string[] = [];
  public isReadyForTime = false;
  ngOnInit(): void {
    this.service.checkAuthentication("USER");
    this.userService.getActivatedDoctors().subscribe({
      next: (response: Doctor[]) => {
        this.doctors = response;
      },
      error: console.error
    });
  }

  public getTime(form:NgForm) {
    let data = form.value;
    if (data['doctorsSelect'] !== "" && data['date'] !== "") {
      this.userService.getAvailableTimeByDate(data['doctorsSelect'], data['date']).subscribe({
        next: (response: string[]) => {
          this.availableTime = response;
          this.isReadyForTime = true;
        },
        error: console.error
      });
    }
  }

  public makeAnAppointment(form:NgForm) {
    let data = form.value;
    let appointment = {
      "date": data['date'],
      "time": data['appTime'],
      "patientId": sessionStorage.getItem('uid'),
    };
    this.userService.makeAnAppointment(appointment, data['doctorsSelect']).subscribe({
      next: () => {

      },
      error: console.error
    });

  }

  public handleRadioChange(radio: any) {
    const label:HTMLElement | null = document.getElementById(`label${radio.id}`);
    const allLabels = document.querySelectorAll('[id^="label"]');

    allLabels.forEach((element) => {
      element.classList.remove('btn-success');
    });

    if (radio.checked) {
      // @ts-ignore
      label.classList.add('btn-success');
    }
  }
}
