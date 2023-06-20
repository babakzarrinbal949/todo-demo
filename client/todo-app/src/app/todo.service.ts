import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class TodoService {
  private apiUrl = 'http://localhost:8085/todos';

  constructor(private http: HttpClient) { }

  getAllTodos(): Observable<any> {
    return this.http.get(this.apiUrl);
  }

  createTodo(todo: any): Observable<any> {
    return this.http.post(this.apiUrl, todo);
  }

  updateTodoById(id: number, updatedTodo: any): Observable<any> {
    const url = `${this.apiUrl}/${id}`;
    return this.http.put(url, updatedTodo);
  }

  deleteTodoById(id: number): Observable<any> {
    const url = `${this.apiUrl}/${id}`;
    return this.http.delete(url);
  }
}
