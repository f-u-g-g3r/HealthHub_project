import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { AuthenticationRequest } from '../interfaces/authenticationRequest';
import { Observable } from 'rxjs';
import { AuthenticationResponse } from '../interfaces/authenticationResponse';
import { RegistrationRequest } from '../interfaces/registrationRequest';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {
  private serverUrl = environment.serverUrl;
  constructor(private http: HttpClient) { }

  public authenticate(request: AuthenticationRequest): Observable<AuthenticationResponse> {
    return this.http.post<AuthenticationResponse>(`${this.serverUrl}/auth/authenticate`, request);
  }

  public register(request: RegistrationRequest): Observable<AuthenticationResponse> {
    return this.http.post<AuthenticationResponse>(`${this.serverUrl}/auth/register`, request);
  }

}
