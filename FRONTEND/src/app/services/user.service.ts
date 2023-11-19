import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { HttpClient } from '@angular/common/http'
import { Observable } from 'rxjs';
import { User } from 'src/app/interfaces/user';
import { HttpHeaders } from '@angular/common/http';
import { UpdatedUser } from '../interfaces/updatedUser';
import { MedCard } from '../interfaces/medCard';
import { MedHistory } from '../interfaces/medCard/MedHistory';
import { Allergy } from '../interfaces/medCard/Allergy';
import { ChronicDisease } from '../interfaces/medCard/chronicDisease';
import { ResultOfSurvey } from '../interfaces/medCard/resultOfSurvey';
import { Doctor } from '../interfaces/doctor';

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

  public getOneDoctor(doctorId: any): Observable<Doctor> {
    return this.http.get<Doctor>(`${this.serverUrl}/doctors/` + doctorId, this.httpOptions);
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

  public addAllergy(medCardId: number, data: Allergy): Observable<Allergy> {
    return this.http.post<Allergy>(`${this.serverUrl}/med-cards/${medCardId}/allergies`, data, this.httpOptions);
  }

  public addChronicDisease(medCardId: number, data: ChronicDisease): Observable<ChronicDisease> {
    return this.http.post<ChronicDisease>(`${this.serverUrl}/med-cards/${medCardId}/chronic-diseases`, data, this.httpOptions);
  }

  public addResultOfSurvey(medCardId: number, data: ResultOfSurvey): Observable<ResultOfSurvey> {
    return this.http.post<ResultOfSurvey>(`${this.serverUrl}/med-cards/${medCardId}/results-of-survey`, data, this.httpOptions);
  }



  public addBloodType(medCardId: number, bloodType: string): Observable<MedCard> {
    return this.http.put<MedCard>(`${this.serverUrl}/med-cards/${medCardId}`, bloodType, this.httpOptions);
  }

  public addRhFactor(medCardId: number, rhFactor: string): Observable<MedCard> {
    return this.http.put<MedCard>(`${this.serverUrl}/med-cards/${medCardId}`, rhFactor, this.httpOptions);
  }



  
}
