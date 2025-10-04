import {Component} from '@angular/core';
import {Todo, TodoService} from "./todo.service";
import {BehaviorSubject, combineLatest, EMPTY, Observable} from "rxjs";
import {catchError, map, switchMap, tap} from "rxjs/operators";

@Component({
  selector: 'app-root',
  template: `
    <div class="toast-container">
      <app-toast
        *ngFor="let toast of toasts"
        [message]="toast.message"
        [show]="toast.show"
        [isError]="toast.isError">
      </app-toast>
    </div>

    <div class="title"><h1>A list of TODOs</h1></div>

    <div class="list">
      <label for="search">Search...</label>
      <input id="search" type="text" [ngModel]="search$ | async" (ngModelChange)="search$.next($event)">

      <app-progress-bar *ngIf="isLoading"></app-progress-bar>

      <app-todo-item
        *ngFor="let todo of filteredTodos$ | async"
        [item]="todo"
        [isDeleting]="deletingIds.has(todo.id)"
        (deleteRequest)="handleDelete($event)">
      </app-todo-item>
    </div>
  `,
  styleUrls: ['app.component.scss']
})
export class AppComponent {

  private todos$ = new BehaviorSubject<Todo[]>([]);
  private refresh$ = new BehaviorSubject<void>(undefined);

  search$ = new BehaviorSubject<string>('');
  readonly filteredTodos$: Observable<Todo[]>;
  isLoading = true;
  deletingIds = new Set<number>();
  toasts: { id: number, message: string, show: boolean, isError: boolean }[] = [];

  constructor(private todoService: TodoService) {
    this.refresh$.pipe(
      tap(() => this.isLoading = true),
      switchMap(() => this.todoService.getAll()),
      tap((todos) => {
        this.todos$.next(todos);
        this.isLoading = false;
      })
    ).subscribe();

    this.filteredTodos$ = combineLatest([
      this.todos$,
      this.search$
    ]).pipe(
      map(([todos, search]) =>
        todos.filter(todo =>
          todo.task.toLowerCase().includes(search.toLowerCase())
        )
      )
    );
  }

  handleDelete(todoToDelete: Todo): void {
    this.deletingIds.add(todoToDelete.id);
    this.todoService.remove(todoToDelete.id).pipe(
      catchError((error) => {
        this.showToast(error, true);
        this.deletingIds.delete(todoToDelete.id)
        return EMPTY;
      })
    ).subscribe(() => {
      this.showToast('Item removed successfully!');
      this.refresh$.next();
      this.deletingIds.delete(todoToDelete.id)
    });
  }

  private showToast(message: string, isError = false): void {
    const toastId = Date.now();
    this.toasts.push({id: toastId, message, isError, show: true});
    setTimeout(() => {
      const toastIndex = this.toasts.findIndex(toast => toast.id === toastId);
      if (toastIndex > -1) {
        this.toasts.splice(toastIndex, 1);
      }
    }, 3000);
  }
}