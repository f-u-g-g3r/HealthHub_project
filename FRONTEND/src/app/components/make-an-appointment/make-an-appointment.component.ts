import { Component } from '@angular/core';
import {Router} from "@angular/router";
import {AuthenticationService} from "../../services/authentication.service";
import {UserService} from "../../services/user.service";
import {Doctor} from "../../interfaces/doctor";
import {NgForm} from "@angular/forms";
import {isEmpty} from "rxjs";
import {User} from "../../interfaces/user";
import {DoctorMinimal} from "../../interfaces/doctorMinimal";

@Component({
  selector: 'app-make-an-appointment',
  templateUrl: './make-an-appointment.component.html',
  styleUrls: ['./make-an-appointment.component.css']
})
export class MakeAnAppointmentComponent {
  constructor(private router: Router, public service: AuthenticationService, public userService: UserService) {}
  public availableTime!:string[];
  public isReadyForTime = false;
  public isTimeChosen = false;
  public formSubmitted = false;
  public isMade = false;

  public familyDocId: number | undefined;
  public haveFamDoc = false;
  public doctor: DoctorMinimal | undefined;
  ngOnInit(): void {
    this.service.checkAuthentication("USER");
    this.userService.getOneUser(sessionStorage.getItem('uid')).subscribe({
      next: (response: User) => {
        this.familyDocId = response.familyDoctorId;
        if (this.familyDocId != null) {
          this.haveFamDoc = true;
          this.getDoctor();
        }
      },
      error: console.error
    });
  }

  public getDoctor() {
    this.userService.getDoctorsName(this.familyDocId).subscribe({
      next: response => {
        this.doctor = response;
      },
      error: console.error
    });
  }

  public getTime(form:NgForm) {
    let data = form.value;
    if (data['date'] !== "") {
      this.userService.getAvailableTimeByDate(this.familyDocId, data['date']).subscribe({
        next: (response) => {
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
    this.userService.makeAnAppointment(appointment, this.familyDocId).subscribe({
      next: () => {
        this.isMade = true;
        this.isReadyForTime = false;
        this.formSubmitted = true;
        this.isTimeChosen = false;
        form.resetForm();
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
