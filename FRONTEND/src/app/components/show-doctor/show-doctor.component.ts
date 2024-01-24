import { Component } from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {AuthenticationService} from "../../services/authentication.service";
import {UserService} from "../../services/user.service";
import {Doctor} from "../../interfaces/doctor";
import {Calendar} from "../../interfaces/calendar";

@Component({
  selector: 'app-show-doctor',
  templateUrl: './show-doctor.component.html',
  styleUrls: ['./show-doctor.component.css']
})
export class ShowDoctorComponent {
  public docId: number | undefined;
  public doctor: Doctor | undefined;
  public calendar: Calendar | undefined;

  constructor(private router: Router, private actRoute: ActivatedRoute, public service: AuthenticationService, public userService: UserService) {}

  ngOnInit(): void {
    this.service.checkAuthentication("ADMIN");

    this.actRoute.params.subscribe(params => {
      if (!isNaN(parseInt(params['docId']))) {
        this.docId = parseInt(params['docId']);

        this.userService.getOneDoctor(this.docId).subscribe({
          next: (response) => {
            this.doctor = response;
          },
          error: console.error
        });

        this.userService.getDoctorSchedule(this.docId).subscribe({
          next: (response) => {
            this.calendar = response;
          },
          error: console.error
        });
      } else {
        this.router.navigate(['/admins-home']);
      }
    })


  }

  public activateDoctor(docId: number) {
    this.userService.activateDoctor(docId).subscribe({
      next: () => this.router.navigate(['/admins-home']),
      error: console.error
    });
  }

  public inactivateDoctor(docId: number) {
    this.userService.deactivateDoctor(docId).subscribe({
      next: () => this.router.navigate(['/admins-home']),
      error: console.error
    });
  }

}
