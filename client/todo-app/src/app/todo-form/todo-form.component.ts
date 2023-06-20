import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';



@Component({
  selector: 'app-todo-form',
  templateUrl: './todo-form.component.html',
  styleUrls: ['./todo-form.component.css']
})
export class TodoFormComponent {
  todoTitle = '';
  todoDescription = '';

  constructor(private http: HttpClient) { }

  onSubmit(): void {
    const todoData = {
      name: this.todoTitle,
      description: this.todoDescription,
      tasks: []
    };

    this.http.post<any>('http://localhost:8085/todos', todoData)
      .subscribe(response => {
        console.log('Todo created successfully:', response);
      }, error => {
        console.error('Error creating todo:', error);
      });
  }
}

