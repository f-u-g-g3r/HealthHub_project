import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './components/app/app.component';
import { AuthenticationComponent } from './components/authentication/authenticationUser/authentication.component';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { AuthenticationService } from './services/authentication.service';
import { UserService } from './services/user.service';
import { HomeComponent } from './components/home/users-home/users-home.component';
import { ProfileComponent } from './components/profile/profile.component';
import { DoctorsworkplaceComponent } from './components/doctorsworkplace/doctorsworkplace.component';
import { PatientProfileComponent } from './components/patient-profile/patient-profile.component';
import { AuthenticationDoctorComponent } from './components/authentication/authenticationDoctors/authenticationDoctor.component';
import { DoctorsHomeComponent } from './components/home/doctors-home/doctors-home.component';
import { AdminsHomeComponent } from './components/home/admins-home/admins-home.component';
import { RegistrationUserComponent } from './components/authentication/registrationUser/registrationUser.component';
import { ScheduleComponent } from './components/schedule/schedule.component';
import { MakeAnAppointmentComponent } from './components/make-an-appointment/make-an-appointment.component';
import { WorkingDetailsComponent } from './components/working-details/working-details.component';
import { MyAppointmentsComponent } from './components/my-appointments/my-appointments.component';
import { ShowDoctorComponent } from './components/show-doctor/show-doctor.component';

@NgModule({
  declarations: [
    AppComponent,
    AuthenticationComponent,
    HomeComponent,
    ProfileComponent,
    DoctorsworkplaceComponent,
    PatientProfileComponent,
    AuthenticationDoctorComponent,
    DoctorsHomeComponent,
    AdminsHomeComponent,
    RegistrationUserComponent,
    ScheduleComponent,
    MakeAnAppointmentComponent,
    WorkingDetailsComponent,
    MyAppointmentsComponent,
    ShowDoctorComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule
  ],
  providers: [AuthenticationService, UserService],
  bootstrap: [AppComponent]
})
export class AppModule { }
