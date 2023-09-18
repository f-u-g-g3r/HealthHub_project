import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { MedCard } from '../interfaces/medCard';

@Injectable({
  providedIn: 'root'
})
export class MedicalService {
  private serverUrl = environment.serverUrl;
  constructor(private http: HttpClient) {}

  public one(): Observable<MedCard> {
    const httpOptions = {
      headers: new HttpHeaders({
        'Authorization': 'Bearer ' + sessionStorage.getItem('token')
      })
    };
    return this.http.get<MedCard>(`${this.serverUrl}/med-cards/` + sessionStorage.getItem("medCardId"), httpOptions);
  }
}
