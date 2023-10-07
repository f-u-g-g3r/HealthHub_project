import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { HttpClient } from '@angular/common/http'
import { Observable } from 'rxjs';
import { User } from 'src/app/interfaces/user';
import { HttpHeaders } from '@angular/common/http';
import { UpdatedUser } from '../interfaces/updatedUser';
import { MedCard } from '../interfaces/medCard';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private serverUrl = environment.serverUrl;
  constructor(private http: HttpClient) {}

  public all(): Observable<User[]> {
    return this.http.get<User[]>(`${this.serverUrl}/users`);
  }

  public getOneUser(uid: any): Observable<User> {
    const httpOptions = {
      headers: new HttpHeaders({
        'Authorization': 'Bearer ' + sessionStorage.getItem('token')
      })
    };
    return this.http.get<User>(`${this.serverUrl}/users/` + uid, httpOptions);
  }

  public updateUser(uid: any, user: UpdatedUser): Observable<User> {
    const httpOptions = {
      headers: new HttpHeaders({
        'Authorization': 'Bearer ' + sessionStorage.getItem('token')
      })
    };

    return this.http.put<User>(`${this.serverUrl}/users/` + uid, user, httpOptions);
  }

  public getUsersByDoctorId(doctorId: string): Observable<User[]> {
    const httpOptions = {
      headers: new HttpHeaders({
        'Authorization': 'Bearer ' + sessionStorage.getItem('token')
      })
    };

    return this.http.get<User[]>(`${this.serverUrl}/users-by-doctor/` + doctorId, httpOptions);
  }

  public getOneMedcard(ownerId: any): Observable<MedCard> {
    const httpOptions = {
      headers: new HttpHeaders({
        'Authorization': 'Bearer ' + sessionStorage.getItem('token')
      })
    };
    return this.http.get<MedCard>(`${this.serverUrl}/med-cards/` + ownerId, httpOptions);
  }
}
