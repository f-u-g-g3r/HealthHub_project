import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthenticationComponent } from './components/authentication/authenticationUser/authentication.component';
import { HomeComponent } from './components/home/users-home/users-home.component';
import { ProfileComponent } from './components/profile/profile.component';
import { DoctorsworkplaceComponent } from './components/doctorsworkplace/doctorsworkplace.component';
import { PatientProfileComponent } from './components/patient-profile/patient-profile.component';
import { AuthenticationDoctorComponent } from './components/authentication/authenticationDoctors/authenticationDoctor.component';
import { DoctorsHomeComponent } from './components/home/doctors-home/doctors-home.component';
import { AdminsHomeComponent } from './components/home/admins-home/admins-home.component';

const routes: Routes = [
  { path: 'login', component: AuthenticationComponent },
  { path: 'login/doctor', component: AuthenticationDoctorComponent },
  { path: 'home', component: HomeComponent },
  { path: 'home/profile', component: ProfileComponent },
  { path: 'doctors-workplace', component: DoctorsworkplaceComponent },
  { path: 'doctors-workplace/patient/:patientId', component: PatientProfileComponent },
  { path: 'doctors-home', component: DoctorsHomeComponent },
  { path: 'admins-home', component: AdminsHomeComponent },
  { path: '**', component: AuthenticationComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
