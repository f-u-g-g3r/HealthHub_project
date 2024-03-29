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
import {RegistrationUserComponent} from "./components/authentication/registrationUser/registrationUser.component";
import {ScheduleComponent} from "./components/schedule/schedule.component";
import {MakeAnAppointmentComponent} from "./components/make-an-appointment/make-an-appointment.component";
import {WorkingDetailsComponent} from "./components/working-details/working-details.component";
import {MyAppointmentsComponent} from "./components/my-appointments/my-appointments.component";
import {ShowDoctorComponent} from "./components/show-doctor/show-doctor.component";

const routes: Routes = [
  { path: 'login', component: AuthenticationComponent },
  { path: 'register', component: RegistrationUserComponent },
  { path: 'login/doctor', component: AuthenticationDoctorComponent },
  { path: 'home', component: HomeComponent },
  { path: 'home/profile', component: ProfileComponent },
  { path: 'doctors-workplace', component: DoctorsworkplaceComponent },
  { path: 'doctors-workplace/patient/:patientId', component: PatientProfileComponent },
  { path: 'doctors-home', component: DoctorsHomeComponent },
  { path: 'admins-home', component: AdminsHomeComponent },
  { path: 'schedule', component: ScheduleComponent },
  { path: 'appointment', component: MakeAnAppointmentComponent },
  { path: 'workingDetails', component: WorkingDetailsComponent },
  { path: 'my-appointments', component: MyAppointmentsComponent },
  { path: 'admin/show-doctor/:docId', component: ShowDoctorComponent },
  { path: '**', component: AuthenticationComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
