import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { MedCard } from 'src/app/interfaces/medCard';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { MedicalService } from 'src/app/services/medical.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  public medCard!: MedCard;
  constructor(private router: Router, public service: AuthenticationService, public medService: MedicalService) {}

  ngOnInit(): void {
      if (!sessionStorage.getItem("token")) {
        this.router.navigate(["/login"])
      }
      this.getMedInfo();
  }

  private getMedInfo() {
    this.medService.one().subscribe({
      next: (response: MedCard) => this.medCard = response,
      error: console.error 
    });
  }

}
