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
import { UpdatedDoctor } from '../interfaces/updatedDoctor'
import {Calendar} from "../interfaces/calendar";
import {Schedule} from "../interfaces/schedule";
import {DoctorMinimal} from "../interfaces/doctorMinimal";
import {SchedulesPage} from "../interfaces/schedulesPage";
import {UsersPage} from "../interfaces/usersPage";
import {DoctorsPage} from "../interfaces/doctorsPage";

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

  // USERS

  public all(): Observable<User[]> {
    return this.http.get<User[]>(`${this.serverUrl}/users`);
  }

  public getOneUser(uid: any): Observable<User> {
    return this.http.get<User>(`${this.serverUrl}/users/${uid}`, this.httpOptions);
  }

  public updateUser(uid: any, user: UpdatedUser): Observable<User> {
    return this.http.put<User>(`${this.serverUrl}/users/${uid}`, user, this.httpOptions);
  }

  public setFamilyDoctor(uid: any, docId: any): Observable<User> {
    return this.http.get<User>(`${this.serverUrl}/users/family-doctor/${uid}/${docId}`, this.httpOptions);
  }

  public getUsersByDoctorId(docId: any, direction: string, page: number, sortBy: string): Observable<UsersPage> {
    return this.http.get<UsersPage>(`${this.serverUrl}/users-by-doctor/${docId}?direction=${direction}&page=${page}&sort=${sortBy}`, this.httpOptions);
  }

  public getUserByUuid(uuid: string): Observable<User> {
    return this.http.get<User>(`${this.serverUrl}/users/uuid/${uuid}`, this.httpOptions);
  }

  public getOneMedcard(ownerId: any): Observable<MedCard> {
    return this.http.get<MedCard>(`${this.serverUrl}/med-cards/${ownerId}`, this.httpOptions);
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

  // DOCTORS

  public getOneDoctor(docId: any): Observable<Doctor> {
    return this.http.get<Doctor>(`${this.serverUrl}/doctors/${docId}`, this.httpOptions);
  }

  public updateDoctor(docId: any, doctor: UpdatedDoctor): Observable<Doctor> {
    return this.http.put<Doctor>(`${this.serverUrl}/doctors/${docId}`, doctor, this.httpOptions);
  }

  public getInactivatedDoctorsPage(direction: string, page: number, sortBy: string): Observable<DoctorsPage> {
    return this.http.get<DoctorsPage>(`${this.serverUrl}/doctors/page/inactivated?direction=${direction}&page=${page}&sortBy=${sortBy}`, this.httpOptions);
  }

  public getActivatedDoctorsPage(direction: string, page: number, sortBy: string): Observable<DoctorsPage> {
    return this.http.get<DoctorsPage>(`${this.serverUrl}/doctors/page/activated?direction=${direction}&page=${page}&sortBy=${sortBy}`, this.httpOptions);
  }

  public getActivatedDoctors(): Observable<Doctor[]> {
    return this.http.get<Doctor[]>(`${this.serverUrl}/doctors/activated`, this.httpOptions);
  }


  public activateDoctor(docId: any): Observable<Doctor> {
    return this.http.put<Doctor>(`${this.serverUrl}/doctors/${docId}`, {"status": "ACTIVE"}, this.httpOptions);
  }

  public deactivateDoctor(docId: any): Observable<Doctor> {
    return this.http.put<Doctor>(`${this.serverUrl}/doctors/${docId}`, {"status": "INACTIVE"}, this.httpOptions);
  }

  public getDoctorSchedule(docId: any): Observable<Calendar> {
    return this.http.get<Calendar>(`${this.serverUrl}/calendars/${docId}`, this.httpOptions);
  }

  public getDoctorSchedule2(docId: any, direction: string, page: number): Observable<SchedulesPage> {
    return this.http.get<SchedulesPage>(`${this.serverUrl}/calendars/schedules/${docId}?direction=${direction}&page=${page}`, this.httpOptions);
  }

  public updateDoctorCalendar(docId: any, newCalendar: any): Observable<Calendar> {
    return this.http.put<Calendar>(`${this.serverUrl}/calendars/${docId}`, newCalendar, this.httpOptions);
  }

  public getAvailableTimeByDate(docId: any, date: string): Observable<string[]> {
    return this.http.get<string[]>(`${this.serverUrl}/calendars/availableTime/${docId}/${date}`, this.httpOptions);
  }

  public makeAnAppointment(schedule: any, docId: any): Observable<void> {
    return this.http.put<void>(`${this.serverUrl}/calendars/schedule/${docId}`, schedule, this.httpOptions);
  }

  public getUserAppointments(uid:any): Observable<Schedule[]> {
    return this.http.get<Schedule[]>(`${this.serverUrl}/calendars/user-appointments/${uid}`, this.httpOptions);
  }

  public getDoctorsName(docId: any): Observable<DoctorMinimal> {
    return this.http.get<DoctorMinimal>(`${this.serverUrl}/doctors-name/${docId}`, this.httpOptions);
  }

  public deleteSchedule(scheduleId: any, uid:any): Observable<void> {
    return this.http.delete<void>(`${this.serverUrl}/calendars/schedules/${scheduleId}/${uid}`, this.httpOptions);
  }

  public getSchedulesByDate(docId: any, date: string): Observable<SchedulesPage> {
    return this.http.get<SchedulesPage>(`${this.serverUrl}/calendars/schedules/${docId}/${date}`, this.httpOptions);
  }

  public isDoctorCalendarConfigured(docId: any): Observable<boolean> {
    return this.http.get<boolean>(`${this.serverUrl}/calendars/isConfigured/${docId}`, this.httpOptions);
  }
}
