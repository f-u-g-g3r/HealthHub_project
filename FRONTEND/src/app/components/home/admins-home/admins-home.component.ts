import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Doctor } from 'src/app/interfaces/doctor';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-admins-home',
  templateUrl: './admins-home.component.html',
  styleUrls: ['./admins-home.component.css']
})
export class AdminsHomeComponent implements OnInit {

  public inactivatedDoctors!: Doctor[];
  public activatedDoctors!: Doctor[];

  constructor(private router: Router, public service: AuthenticationService, public userService: UserService) {}


  ngOnInit(): void {
    this.service.checkAuthentication("ADMIN");

    this.getInactivatedDoctors();
    this.getActivatedDoctors();
  }

  private getInactivatedDoctors() {
    this.userService.getInactivatedDoctors().subscribe({
      next: (response: Doctor[]) => {
        this.inactivatedDoctors = response;
      },
      error: console.error
    });
  }

  private getActivatedDoctors() {
    this.userService.getActivatedDoctors().subscribe({
      next: (response: Doctor[]) => this.activatedDoctors = response,
      error: console.error
    });
  }

  public inactivateDoctor(docId: number) {
    this.userService.deactivateDoctor(docId).subscribe({
      next: () => window.location.reload(),
      error: console.error
    });
  }

}
