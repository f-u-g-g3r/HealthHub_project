import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { User } from 'src/app/interfaces/user';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { UserService } from 'src/app/services/user.service';
import {NgForm} from "@angular/forms";
import {unusedExport} from "@angular/compiler/testing";
import {UsersPage} from "../../interfaces/usersPage";


@Component({
  selector: 'app-doctorsworkplace',
  templateUrl: './doctorsworkplace.component.html',
  styleUrls: ['./doctorsworkplace.component.css']
})
export class DoctorsworkplaceComponent implements OnInit {
  public patients!: UsersPage;
  public foundUser!: User;
  public totalPagesArr: number[] = [];
  public status = sessionStorage.getItem('doctorStatus');
  public page = 0;
  public searchPatientModalIsOpen = false;
  public foundUserModalIsOpen = false;
  public ascSorting = true;

  constructor (private router: Router, public service: AuthenticationService, public userService: UserService) {}

  ngOnInit(): void {
    if (sessionStorage.getItem("role") != "DOCTOR") {
      this.router.navigate(["/home"]);
    }
    this.getDoctorsUsers(false);
  }

  public getDoctorsUsers(changeSorting:boolean, sortBy = 'id') {
    let sorting = '';
    if (changeSorting) {
      this.ascSorting = !this.ascSorting;
    }
    if (this.ascSorting) {
      sorting = 'ASC'
    } else {
      sorting = 'DESC'
    }

    this.userService.getUsersByDoctorId(sessionStorage.getItem("docId"), sorting, this.page, sortBy).subscribe({
      next: (response) => {
        this.patients = {
          content: response.content,
          pageable: {
            pageNumber: response.pageable.pageNumber,
          },
          last: response.last,
          totalPages: response.totalPages,
          first: response.first,
        };
        this.totalPagesArr = [];
        for (let i = 1; i < this.patients.totalPages + 1; i++) {
          this.totalPagesArr.push(i);
        }
      },
      error: console.error
    });
  }

  public viewPatientDetails(patientId: number) {
    this.router.navigate(["/doctors-workplace/patient/", patientId]);
  }

  public isDisabled(famDocId: number) {
    return famDocId != null;
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
      next: () => this.getDoctorsUsers(false),
      error: console.error
    });
  }

  public search(form: NgForm) {
    const query = form.value;
    let value = query['query'];
    if (!isNaN(Number(value))) {
      this.userService.getUserByUuid(value).subscribe({
        next: (response: User) => {
          if (response.familyDoctorId.toString() == sessionStorage.getItem('docId')) {
            this.patients = {
              content: [response],
              pageable: {
                pageNumber: 0,
              },
              last: true,
              totalPages: 0,
              first: true,
            };
          } else {
            this.patients = {
              content: [],
              pageable: {
                pageNumber: 0,
              },
              last: true,
              totalPages: 0,
              first: true,
            };
          }
          this.totalPagesArr = [1];

        },
        error: () => {
          this.patients = {
            content: [],
            pageable: {
              pageNumber: 0,
            },
            last: true,
            totalPages: 0,
            first: true,
          };
        }
      });
    } else {
      let oldPatients = this.patients;
      this.patients = {
        content: [],
        pageable: {
          pageNumber: 0,
        },
        last: true,
        totalPages: 1,
        first: true,
      };
      value = value.toLowerCase();
      for (let i = 0; i < oldPatients.content.length; i++) {
        let patient = oldPatients.content[i];
        if (patient.firstname.toLowerCase() == value) {
          this.patients.content.push(patient);
        } else if (patient.lastname.toLowerCase() == value) {
          this.patients.content.push(patient);
        } else if (patient.firstname.toLowerCase() + " " + patient.lastname.toLowerCase() == value) {
          this.patients.content.push(patient);
        }
      }
      this.totalPagesArr = [];
      for (let i = 1; i < this.patients.totalPages + 1; i++) {
        this.totalPagesArr.push(i);
      }
    }


  }

  protected readonly unusedExport = unusedExport;
}
