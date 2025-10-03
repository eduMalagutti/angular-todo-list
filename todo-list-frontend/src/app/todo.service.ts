import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable, throwError} from "rxjs";

export interface Todo {
  id: number;
  task: string;
  priority: 1 | 2 | 3;
}

const API_URL = '/api/v1/todos';

@Injectable({
  providedIn: 'root'
})
export class TodoService {

  constructor(private http: HttpClient) {
  }

  getAll(): Observable<Todo[]> {
    return this.http.get<Todo[]>(API_URL);
  }

  remove(id: number): Observable<void> {
    if (Math.random() < .8) {
      return this.http.delete<void>(`${API_URL}/${id}`);
    } else {
      return new Observable<void>((observer) => {
        setTimeout(() => {
          observer.error('Error deleting item');
        }, 1000);
      })
    }
  }
}