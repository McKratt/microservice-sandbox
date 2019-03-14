import {Injectable} from '@angular/core';
import {Partner} from '../model';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PersonClientService {

  constructor(private http: HttpClient) {
  }

  getBaseUrl(): string {
    return '/person/rest/api/v1/partners';
  }

  public createPartner(partner: Partner): Observable<Partner> {
    return this.http.post<Partner>(this.getBaseUrl(), partner);
  }

  public readPartner(pNummer: string): Observable<Partner> {
    return this.http.get<Partner>(`${this.getBaseUrl()}/${pNummer}`);
  }
}
