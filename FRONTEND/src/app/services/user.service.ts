import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { HttpClient } from '@angular/common/http'
import { Observable } from 'rxjs';
import { User } from 'src/app/interfaces/user';
import { HttpHeaders } from '@angular/common/http';
import { UpdatedUser } from '../interfaces/updatedUser';
import { MedCard } from '../interfaces/medCard';
import { MedHistory } from '../interfaces/medCard/MedHistory';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private serverUrl = environment.serverUrl;
  constructor(private http: HttpClient) {}

  private httpOptions = {
    headers: new HttpHeaders({
      'Authorization': 'Bearer ' + sessionStorage.getItem('token')
    })
  };

  public all(): Observable<User[]> {
    return this.http.get<User[]>(`${this.serverUrl}/users`);
  }

  public getOneUser(uid: any): Observable<User> {
    return this.http.get<User>(`${this.serverUrl}/users/` + uid, this.httpOptions);
  }

  public updateUser(uid: any, user: UpdatedUser): Observable<User> {
    return this.http.put<User>(`${this.serverUrl}/users/` + uid, user, this.httpOptions);
  }

  public getUsersByDoctorId(doctorId: string): Observable<User[]> {
    return this.http.get<User[]>(`${this.serverUrl}/users-by-doctor/` + doctorId, this.httpOptions);
  }

  public getOneMedcard(ownerId: any): Observable<MedCard> {
    return this.http.get<MedCard>(`${this.serverUrl}/med-cards/` + ownerId, this.httpOptions);
  }

  public addMedHistory(medCardId: number, data: MedHistory): Observable<MedHistory> {
    return this.http.post<MedHistory>(`${this.serverUrl}/med-cards/${medCardId}/med-history`, data, this.httpOptions);
  }

  public addBloodType(medCardId: number, bloodType: string): Observable<MedCard> {
    return this.http.post<MedCard>(`${this.serverUrl}/med-cards/${medCardId}`, bloodType, this.httpOptions);
  }

  
}
