import {Component} from '@angular/core';
import {Todo, TodoService} from "./todo.service";
import {BehaviorSubject, combineLatest, Observable} from "rxjs";
import {finalize, map} from "rxjs/operators";

@Component({
  selector: 'app-root',
  template: `
    <div class="title">
      <h1>
        A list of TODOs
      </h1>
    </div>
    <div class="list">
      <label for="search">Search...</label>
      <input id="search" type="text" [ngModel]="search$ | async" (ngModelChange)="search$.next($event)">
      <app-progress-bar *ngIf="isLoading"></app-progress-bar>
      <app-todo-item *ngFor="let todo of todos$ | async" [item]="todo"></app-todo-item>
    </div>
  `,
  styleUrls: ['app.component.scss']
})
export class AppComponent {

  readonly allTodos$: Observable<Todo[]>;

  readonly todos$: Observable<Todo[]>;

  isLoading = true;

  search$ = new BehaviorSubject<string>('');

  constructor(todoService: TodoService) {
    this.allTodos$ = todoService.getAll().pipe(
        finalize(() => this.isLoading = false)
    );

      this.todos$ = combineLatest([
          this.allTodos$,
          this.search$
      ]).pipe(
          map(([todos, searchTerm]) =>
              todos.filter(todo =>
                  todo.task.toLowerCase().includes(searchTerm.toLowerCase())
              )
          )
      );
  }
}
