import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import { Router } from '@angular/router';
import { Doctor } from 'src/app/interfaces/doctor';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { UserService } from 'src/app/services/user.service';
import {DoctorsPage} from "../../../interfaces/doctorsPage";
import {NgModel} from "@angular/forms";

@Component({
  selector: 'app-admins-home',
  templateUrl: './admins-home.component.html',
  styleUrls: ['./admins-home.component.css']
})
export class AdminsHomeComponent implements OnInit {
  public pageInactivated = 0;
  public pageActivated = 0;
  public ascSortingForInactivated = true;
  public ascSortingForActivated = true;
  public inactivatedDoctors!: DoctorsPage;
  public activatedDoctors!: DoctorsPage;
  public totalInactivatedPagesArr: number[] = [];
  public totalActivatedPagesArr: number[] = [];
  public searched = false;

  @ViewChild('queryInput') queryInput!: ElementRef;

  public currentInactivatedSorting: string = 'id';
  public currentActivatedSorting: string = 'id';


  constructor(private router: Router, public service: AuthenticationService, public userService: UserService) {}


  ngOnInit(): void {
    this.service.checkAuthentication("ADMIN");

    this.getInactivatedDoctors(false);
    this.getActivatedDoctors(false);
  }

  public getInactivatedDoctors(changeDirection:boolean, sortBy = 'id') {
    let sorting = '';
    if (sortBy == this.currentInactivatedSorting && sortBy != 'id') {
      if (changeDirection) {
        this.ascSortingForInactivated = !this.ascSortingForInactivated;
      }
    }
    if (this.ascSortingForInactivated) {
      sorting = 'ASC'
    } else {
      sorting = 'DESC'
    }
    this.currentInactivatedSorting = sortBy;
    this.userService.getInactivatedDoctorsPage(sorting, this.pageInactivated, sortBy).subscribe({
      next: (response: DoctorsPage) => {
        this.inactivatedDoctors = response;
        this.totalInactivatedPagesArr = [];
        for (let i = 1; i < this.inactivatedDoctors.totalPages + 1; i++) {
          this.totalInactivatedPagesArr.push(i);
        }
      },
      error: console.error
    });
  }

  public getActivatedDoctors(changeDirection:boolean, sortBy = 'id') {
    let sorting = '';
    if (sortBy == this.currentActivatedSorting && sortBy != 'id') {
      if (changeDirection) {
        this.ascSortingForActivated = !this.ascSortingForActivated;
      }
    }
    if (this.ascSortingForActivated) {
      sorting = 'ASC'
    } else {
      sorting = 'DESC'
    }
    this.currentActivatedSorting = sortBy;
    this.userService.getActivatedDoctorsPage(sorting, this.pageActivated, sortBy).subscribe({
      next: (response: DoctorsPage) => {
        this.activatedDoctors = response;
        this.totalActivatedPagesArr = [];
        for (let i = 1; i < this.activatedDoctors.totalPages + 1; i++) {
          this.totalActivatedPagesArr.push(i);
        }
      },
      error: console.error
    });
  }



  public search(query: NgModel) {
    let text = query.value;
    this.searched = true;
    this.userService.searchDoctor(text).subscribe({
      next: (response) => {
        let foundInactivated = [];
        let foundActivated = [];
        for (let doctor of response._embedded.doctorList) {
          if (doctor.status === "INACTIVE") {
            foundInactivated.push(doctor);
          } else {
            foundActivated.push(doctor);
          }
        }
        this.inactivatedDoctors.content = foundInactivated;
        this.activatedDoctors.content = foundActivated;

      },
      error: console.error
    });
  }

  public clearSearch() {
    this.searched = false;
    this.getActivatedDoctors(false);
    this.getInactivatedDoctors(false);
    this.queryInput.nativeElement.value = '';

  }

  public inactivateDoctor(docId: number) {
    this.userService.deactivateDoctor(docId).subscribe({
      next: () => window.location.reload(),
      error: console.error
    });
  }

}
