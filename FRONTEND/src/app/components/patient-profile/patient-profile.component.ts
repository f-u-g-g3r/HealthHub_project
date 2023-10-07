import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { MedCard } from 'src/app/interfaces/medCard';
import { User } from 'src/app/interfaces/user';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-patient-profile',
  templateUrl: './patient-profile.component.html',
  styleUrls: ['./patient-profile.component.css']
})
export class PatientProfileComponent implements OnInit{
  patientId!: string;
  user!: User;
  medCard!: MedCard;
  constructor (private router: Router, private actRoute: ActivatedRoute, private userService: UserService, public service: AuthenticationService) {}

  ngOnInit() {
    if (sessionStorage.getItem("role") != "DOCTOR") {
      this.router.navigate(["/home"]);
    }

    this.actRoute.params.subscribe(params => {
      this.patientId = params['patientId'];
    })

    this.getUserInfo().subscribe({
      next: (response: User) => this.user = response,
      error: console.error
    });

    this.getMedCard().subscribe({
      next: (response: MedCard) => this.medCard = response,
      error: console.error
    })
  }

  private getUserInfo(): Observable<User> {
    return this.userService.getOneUser(this.patientId);
  }

  private getMedCard(): Observable<MedCard> {
    return this.userService.getOneMedcard(this.patientId)
  }
}
