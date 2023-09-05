import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { HttpClient } from '@angular/common/http'
import { Observable } from 'rxjs';
import { User } from 'src/app/interfaces/user';
import { HttpHeaders } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private serverUrl = environment.serverUrl;
  constructor(private http: HttpClient) { }

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
}
