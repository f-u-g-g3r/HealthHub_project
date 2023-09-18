import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { User } from 'src/app/interfaces/user';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-doctorsworkplace',
  templateUrl: './doctorsworkplace.component.html',
  styleUrls: ['./doctorsworkplace.component.css']
})
export class DoctorsworkplaceComponent implements OnInit {
  public patients!: User[];

  constructor (private router: Router, public service: AuthenticationService, public userService: UserService) {}

  ngOnInit(): void {
    if (sessionStorage.getItem("role") != "DOCTOR") {
      this.router.navigate(["/home"]);
    }

    this.userService.getUsersByDoctorId(sessionStorage.getItem("uid")!.toString()).subscribe({
      next: (response: User[]) => {
        this.patients = response
        console.log(response)
      },
      error: console.error
    });
  }

  public viewPatientDetails(patientId: number) {

  }

}
