import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthenticationService } from 'src/app/services/authentication.service';

@Component({
  selector: 'app-doctors-home',
  templateUrl: './doctors-home.component.html',
  styleUrls: ['./doctors-home.component.css']
})
export class DoctorsHomeComponent {
  constructor(private router: Router, public service: AuthenticationService) {}

  ngOnInit(): void {
      if (!sessionStorage.getItem("token")) {
        this.router.navigate(["/login"]);
      } else if (sessionStorage.getItem("role") != "DOCTOR") {
        this.router.navigate(["/home"]);
      }
  }
}
