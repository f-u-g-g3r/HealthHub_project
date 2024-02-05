import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { MedCard } from 'src/app/interfaces/medCard';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { UserService } from 'src/app/services/user.service';
import { themeChange } from 'theme-change';
import {DoctorMinimal} from "../../../interfaces/doctorMinimal";
import {Doctor} from "../../../interfaces/doctor";
import {NgForm} from "@angular/forms";
import {UpdatedUser} from "../../../interfaces/updatedUser";

@Component({
  selector: 'app-home',
  templateUrl: './users-home.component.html',
  styleUrls: ['./users-home.component.css']
})
export class HomeComponent implements OnInit {
  public medCard!: MedCard;
  famDocId: number | null = null;
  public role: string = sessionStorage.getItem("role") + "";
  public doctor: DoctorMinimal | undefined;
  public doctors: Doctor[] | undefined;


  constructor(private router: Router, public service: AuthenticationService, public userService: UserService) {}

  ngOnInit(): void {
    this.service.checkAuthentication("USER");
    this.getMedInfo();
    this.getAllDoctors();
  }

  private getMedInfo() {
    this.userService.getOneMedcard(sessionStorage.getItem('uid')).subscribe({
      next: response => {
        this.medCard = response;
        this.famDocId = response.familyDoctorID;
        if (this.famDocId != null) {
          this.getDoctor()
        }
      },
      error: console.error
    });
  }

  public getDoctor() {
    this.userService.getDoctorsName(this.famDocId).subscribe({
      next: response => {
        this.doctor = response;
      },
      error: console.error
    });
  }

  public getAllDoctors() {
    this.userService.getActivatedDoctors().subscribe({
      next: response => {
        this.doctors = response;
      },
      error: console.error
    });
  }

  public setFamilyDoctor(form: NgForm) {
    const data = form.value;
    if (data['familyDoctor'] != null) {
      let updatedData = {
        familyDoctorId: data['familyDoctor']
      };
      this.userService.setFamilyDoctor(sessionStorage.getItem("uid"), updatedData).subscribe({
        next: response => {
          this.getMedInfo();
        },
        error: console.error
      });
    }

  }
}
