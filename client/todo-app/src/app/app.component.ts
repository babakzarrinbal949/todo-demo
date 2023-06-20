import { Component, OnInit } from '@angular/core';
import { TodoService } from './todo.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  todos: any[] = [];

  constructor(private todoService: TodoService) { }

  ngOnInit(): void { }

  getAllTodos(): void {
    this.todoService.getAllTodos().subscribe(
      (response) => {
        this.todos = response;
      },
      (error) => {
        console.error('Error retrieving todos:', error);
      }
    );
  }
}
