import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthenticationService } from 'src/app/services/authentication.service';

@Component({
  selector: 'app-doctors-home',
  templateUrl: './doctors-home.component.html',
  styleUrls: ['./doctors-home.component.css']
})
export class DoctorsHomeComponent implements OnInit {
  constructor(private router: Router, public service: AuthenticationService) {}

  ngOnInit(): void {
      this.service.checkAuthentication('DOCTOR');
  }
}
