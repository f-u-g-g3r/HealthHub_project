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

 
}
