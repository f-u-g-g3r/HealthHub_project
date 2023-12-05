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
  public patients: User[] = [];
  public foundUser!: User;

  public searchPatientModalIsOpen = false;
  public foundUserModalIsOpen = false;

  constructor (private router: Router, public service: AuthenticationService, public userService: UserService) {}

  ngOnInit(): void {
    if (sessionStorage.getItem("role") != "DOCTOR") {
      this.router.navigate(["/home"]);
    }

    this.getDoctorsUsers();
  }

  private getDoctorsUsers() {
    this.userService.getUsersByDoctorId(sessionStorage.getItem("docId")).subscribe({
      next: (response: User[]) => {
        this.patients = response;
      },
      error: console.error
    });
  }

  public viewPatientDetails(patientId: number) {
    this.router.navigate(["/doctors-workplace/patient/", patientId]);
  }

  public findPatientByUuid(data: any) {
    const uuid = data['uuid'];
    this.userService.getUserByUuid(uuid).subscribe({
      next: (response: User) => {
        this.foundUser = response
      },
          error: console.error
      });
  }

  public addPatientToDoctor(user: User) {
    this.userService.setFamilyDoctor(user.id, sessionStorage.getItem('docId')).subscribe({
      next: () => this.getDoctorsUsers(),
      error: console.error
    });
  }
}
