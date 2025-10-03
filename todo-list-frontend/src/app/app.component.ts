import {Component} from '@angular/core';
import {Todo, TodoService} from "./todo.service";
import {BehaviorSubject, combineLatest, EMPTY, Observable} from "rxjs";
import {catchError, map, switchMap, tap} from "rxjs/operators";

@Component({
  selector: 'app-root',
  template: `
    <app-toast [message]="toast.message" [show]="toast.show" [isError]="toast.isError"></app-toast>

    <div class="title"><h1>A list of TODOs</h1></div>

    <div class="list">
      <label for="search">Search...</label>
      <input id="search" type="text" [(ngModel)]="search$" (ngModelChange)="search$.next($event)">

      <app-progress-bar *ngIf="isLoading"></app-progress-bar>

      <app-todo-item
        *ngFor="let todo of filteredTodos$ | async"
        [item]="todo"
        (deleteRequest)="handleDelete($event)">
      </app-todo-item>
    </div>
  `,
  styleUrls: ['app.component.scss']
})
export class AppComponent {

  private refresh$ = new BehaviorSubject<void>(undefined);

  search$ = new BehaviorSubject<string>('');

  isLoading = true;

  readonly filteredTodos$: Observable<Todo[]>;

  toast = {message: '', show: false, isError: false};

  constructor(private todoService: TodoService) {
    const allTodos$ = this.refresh$.pipe(
      tap(() => this.isLoading = true),
      switchMap(() => this.todoService.getAll()),
      tap(() => this.isLoading = false)
    );

    this.filteredTodos$ = combineLatest([
      allTodos$,
      this.search$
    ]).pipe(
      map(([todos, searchTerm]) =>
        todos.filter(todo =>
          todo.task.toLowerCase().includes(searchTerm.toLowerCase())
        )
      )
    );
  }

  handleDelete(todoToDelete: Todo): void {
    this.todoService.remove(todoToDelete.id).pipe(
      catchError((error) => {
        this.showToast(error, true);
        return EMPTY;
      })
    ).subscribe(() => {
      this.showToast('Item removed successfully!');
      this.refresh$.next();
    });
  }

  private showToast(message: string, isError = false): void {
    this.toast = { message, isError, show: true };
    setTimeout(() => this.toast.show = false, 3000);
  }
}
