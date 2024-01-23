import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Doctor } from 'src/app/interfaces/doctor';
import { AuthenticationRequest } from 'src/app/interfaces/requests&responses/authenticationRequest';
import { AuthenticationResponse } from 'src/app/interfaces/requests&responses/authenticationResponse';
import { UpdatedDoctor } from 'src/app/interfaces/updatedDoctor';
import { UpdatedUser } from 'src/app/interfaces/updatedUser';
import { User } from 'src/app/interfaces/user';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { UserService } from 'src/app/services/user.service';
import {DoctorMinimal} from "../../interfaces/doctorMinimal";

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {

  public userData!: User;
  public doctorData!: Doctor;
  public isDoctor!: boolean;
  public familyDoctor: DoctorMinimal | undefined;
  constructor(private router: Router, public authService: AuthenticationService, private userService: UserService) {

  }

  ngOnInit(): void {
    if (!sessionStorage.getItem("token")) {
      this.router.navigate(["/login"])
    }

    if (sessionStorage.getItem('role') === "DOCTOR") {
      this.isDoctor = true;
      this.userService.getOneDoctor(sessionStorage.getItem('docId')).subscribe({
        next: (response: Doctor) => {
          this.doctorData = response
        },
        error: console.error
      });
    }

    if (sessionStorage.getItem('role') === "USER") {
      this.isDoctor = false;
      this.userService.getOneUser(sessionStorage.getItem('uid')).subscribe({
        next: (response: User) => {
          this.userData = response;
          if (this.userData.familyDoctorId != null) {
            this.getFamilyDoctor(this.userData.familyDoctorId);
          }
        },
        error: console.error
      });


    }
  }

  public onUpdateUser(user: UpdatedUser) {
    this.userService.updateUser(sessionStorage.getItem('uid'), user).subscribe({
      next: (response: User) => {
        this.userData = response;
        const authData: AuthenticationRequest = {
          "email": this.userData.email,
          "password": sessionStorage.getItem('password')!.toString()
        };

        this.authService.authenticate(authData).subscribe({
          next: (response: AuthenticationResponse) => {
            sessionStorage.setItem('token', response.token.toString());
          },
          error: console.error
        });
      },
      error: console.error
    });
  }

  public onUpdateDoctor(doctor: UpdatedDoctor) {
    this.userService.updateDoctor(sessionStorage.getItem('docId'), doctor).subscribe({
      next: (response: Doctor) => {
        this.doctorData = response;
        const authData: AuthenticationRequest = {
          "email": this.doctorData.email,
          "password": sessionStorage.getItem('password')!.toString()
        };

        this.authService.authenticate(authData).subscribe({
          next: (response: AuthenticationResponse) => {
            sessionStorage.setItem('token', response.token.toString());
          },
          error: console.error
        });
      },
        error: console.error
    });
  }

  private getFamilyDoctor(docId: number) {
    this.userService.getDoctorsName(docId).subscribe({
      next: (response) => {
        this.familyDoctor = response;
        console.log(1)
      },
      error: console.error
    });
  }


}
