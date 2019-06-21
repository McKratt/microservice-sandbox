import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Person} from '../../model';

@Injectable({
  providedIn: 'root'
})
export class PersonClientService {

  constructor(private http: HttpClient) {
  }

  getBaseUrl(): string {
    return '/person/rest/api/v1/persons';
  }

  public createPerson(person: Person): Observable<Person> {
    return this.http.post<Person>(this.getBaseUrl(), person);
  }

  public readPerson(pNummer: string): Observable<Person> {
    return this.http.get<Person>(`${this.getBaseUrl()}/${pNummer}`);
  }
}
