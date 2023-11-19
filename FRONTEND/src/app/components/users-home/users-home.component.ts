import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { MedCard } from 'src/app/interfaces/medCard';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-home',
  templateUrl: './users-home.component.html',
  styleUrls: ['./users-home.component.css']
})
export class HomeComponent implements OnInit {
  public medCard!: MedCard;
  public role: string = sessionStorage.getItem("role") + "";
  

  constructor(private router: Router, public service: AuthenticationService, public userService: UserService) {}

  ngOnInit(): void {
      if (!sessionStorage.getItem("token")) {
        this.router.navigate(["/login"])
      } else if (sessionStorage.getItem("role") == "DOCTOR") {
        this.router.navigate(["/doctors-home"]);
      } else {
        this.getMedInfo();
      }
      console.log(sessionStorage.getItem('role'))
  }

  private getMedInfo() {
    this.userService.getOneMedcard(sessionStorage.getItem('uid')).subscribe({
      next: (response: MedCard) => this.medCard = response,
      error: console.error 
    });
  }

}
