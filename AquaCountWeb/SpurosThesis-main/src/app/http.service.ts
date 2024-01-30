import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { HttpHeaders } from '@angular/common/http';


@Injectable({
  providedIn: 'root'
})
export class HttpService {

  public clocksUrl = 'http://192.168.1.104:8080/clocks';
  public countersUrl = 'http://192.168.1.104:8080/counters';
  public measurementUrl = 'http://192.168.1.104:8080/measurement';
  public routesUrl = 'http://192.168.1.104:8080/routes';
  public loginUrl = 'http://192.168.1.104:8080/counters/login';

  private TOKEN_KEY = 'token';

  constructor(private http: HttpClient) {}

  public getAllClocks() {
    const headers = new HttpHeaders().set('Authorization', `Bearer ${this.getToken()}`)
    return this.http.get(`${this.clocksUrl}/allclocks`, {headers});
  }

  public getAllCounters() {
    const headers = new HttpHeaders().set('Authorization', `Bearer ${this.getToken()}`)
    return this.http.get(`${this.countersUrl}/allCounters`, {headers});
  }

  public getAllRoutes() {
    const headers = new HttpHeaders().set('Authorization', `Bearer ${this.getToken()}`)
    return this.http.get(`${this.routesUrl}/allRoutes`, {headers});
  }

  
  public addNewRoute() {
    const headers = new HttpHeaders().set('Authorization', `Bearer ${this.getToken()}`)
    return this.http.post(`${this.routesUrl}/register`, {headers});
  }
  
  public addNewCounter() {
    const headers = new HttpHeaders().set('Authorization', `Bearer ${this.getToken()}`)
    return this.http.post(`${this.countersUrl}/register`, {headers});
  }
  
  public addNewClock() {
    const headers = new HttpHeaders().set('Authorization', `Bearer ${this.getToken()}`)
    return this.http.post(`${this.clocksUrl}/register`, {headers});
  }
  
  public deleteRoute(id: number) {
    const headers = new HttpHeaders().set('Authorization', `Bearer ${this.getToken()}`)
    return this.http.delete(`${this.routesUrl}/delete/${id}`, {headers});
  }
  
  public deleteCounter(id: number) {
    const headers = new HttpHeaders().set('Authorization', `Bearer ${this.getToken()}`)
    return this.http.delete(`${this.countersUrl}/delete/${id}`, {headers});
  }
  
  public deleteClock(id: number) {
    const headers = new HttpHeaders().set('Authorization', `Bearer ${this.getToken()}`)
    return this.http.delete(`${this.clocksUrl}/deleteClock/${id}`, {headers});
  }
  
  public getRouteClocks(id: number) {
    const headers = new HttpHeaders().set('Authorization', `Bearer ${this.getToken()}`)
    return this.http.get(`${this.clocksUrl}/getClockids/${id}`, {headers});
  }

  public getCounterMeasurements(id: number) {
    const headers = new HttpHeaders().set('Authorization', `Bearer ${this.getToken()}`)
    return this.http.get(`${this.measurementUrl}/measurementsOfaCounter/${id}`, {headers});
  }

  public getClockMeasurements(id: number) {
    const headers = new HttpHeaders().set('Authorization', `Bearer ${this.getToken()}`)
    return this.http.get(`${this.measurementUrl}/measurementsOfClock/${id}`, {headers});
  }

  public login(credentials: any): Observable<any> {
    return this.http.post(this.loginUrl, credentials, { observe: 'response' });
  }

  public saveToken(token: string): void {
    localStorage.setItem(this.TOKEN_KEY, token);
  }

  public getToken(): string | null {
    return localStorage.getItem(this.TOKEN_KEY);
  }

  public removeToken(): void {
    localStorage.removeItem(this.TOKEN_KEY);
  }
}
