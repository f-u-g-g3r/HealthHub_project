import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { MedCard } from 'src/app/interfaces/medCard';
import { MedHistory } from 'src/app/interfaces/medCard/MedHistory';
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

  public onOpenModal(action: string): void {
    const btn: HTMLElement = document.createElement('button');
    btn.setAttribute('data-bs-toggle', 'modal');
    btn.style.display = "none";
    document.body.appendChild(btn);

    if (action === "medHistory") {
      btn.setAttribute('data-bs-target', '#medHistoryModal');
      btn.click();
    } else if (action === "bloodType") {
      btn.setAttribute('data-bs-target', '#bloodTypeModal');
      btn.click();
    } else if (action === "rhFactor") {
      btn.setAttribute('data-bs-target', '#rhFactorModal');
      btn.click();
    } else if (action === "allergies") {
      btn.setAttribute('data-bs-target', '#allergiesModal');
      btn.click();
    } else if (action === "chronicDiseases") {
      btn.setAttribute('data-bs-target', '#chronicDiseasesModal');
      btn.click();
    } else if (action === "resultsOfSurveys") {
      btn.setAttribute('data-bs-target', '#resultsOfSurveysModal');
      btn.click();
    }
  }

  public addMedHistory(form: NgForm) {
    const formFields = form.value;

    this.userService.addMedHistory(this.medCard.id, formFields).subscribe({
      next: () => location.reload(),
      error: console.error
    });
  }

  public changeBloodType(form: NgForm) {
    const formFields = form.value;

    this.userService.addBloodType(this.medCard.id, formFields).subscribe({
      next: () => location.reload(),
      error: console.error
    })
  }

  public changeRhFactor(form: NgForm) {
    const formFields = form.value;

    this.userService.addRhFactor(this.medCard.id, formFields).subscribe({
      next: () => location.reload(),
      error: console.error
    })
  }

  public addAllergy(form: NgForm) {
    const formFields = form.value;

    this.userService.addAllergy(this.medCard.id, formFields).subscribe({
      next: () => location.reload(),
      error: console.error
    });
  }

  public addChronicDisease(form: NgForm) {
    const formFields = form.value;

    this.userService.addChronicDisease(this.medCard.id, formFields).subscribe({
      next: () => location.reload(),
      error: console.error
    });
  }

  public addResultOfSurvey(form: NgForm) {
    const formFields = form.value;

    this.userService.addResultOfSurvey(this.medCard.id, formFields).subscribe({
      next: () => location.reload(),
      error: console.error
    });
  }

 


}
