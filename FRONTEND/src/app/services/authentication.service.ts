import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { AuthenticationRequest } from '../interfaces/requests&responses/authenticationRequest';
import { Observable } from 'rxjs';
import { AuthenticationResponse } from '../interfaces/requests&responses/authenticationResponse';
import { RegistrationRequest } from '../interfaces/registrationRequest';
import { Router } from '@angular/router';
import { DoctorAuthRequest } from '../interfaces/requests&responses/doctorAuthRequest';
import { DoctorAuthResponse } from '../interfaces/requests&responses/doctorAuthResponse';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {
  private serverUrl = environment.serverUrl;
  constructor(private http: HttpClient, private router: Router) { }

  public authenticate(request: AuthenticationRequest): Observable<AuthenticationResponse> {
    return this.http.post<AuthenticationResponse>(`${this.serverUrl}/auth/authenticate`, request);
  }

  public register(request: RegistrationRequest): Observable<AuthenticationResponse> {
    return this.http.post<AuthenticationResponse>(`${this.serverUrl}/auth/register`, request);
  }

  public registerDoctor(request: DoctorAuthRequest): Observable<DoctorAuthResponse> {
    return this.http.post<DoctorAuthResponse>(`${this.serverUrl}/auth/register-doctor`, request);
  }

  public checkAuthentication(role: string): void {
    if (!sessionStorage.getItem("token")) {
      this.router.navigate(['/login']);
    }

    if (sessionStorage.getItem('role') !== role) {
      if (sessionStorage.getItem('role') === "USER") {
        this.router.navigate(['/home']);
      } else if (sessionStorage.getItem('role') === "DOCTOR") {
        this.router.navigate(['/doctors-home']);
      } else if (sessionStorage.getItem('role') === "ADMIN") {
        this.router.navigate(['/admins-home']);
      }
    }
  }

  

  

  public logOut() {
    sessionStorage.clear();
    this.router.navigate(["/login"]);
  }

}
