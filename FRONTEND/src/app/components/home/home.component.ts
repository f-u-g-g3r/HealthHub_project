import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthenticationService } from 'src/app/services/authentication.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  constructor(private router: Router, public service: AuthenticationService) {}

  ngOnInit(): void {
      if (!sessionStorage.getItem("token")) {
        this.router.navigate(["/login"])
      }
  }


}
