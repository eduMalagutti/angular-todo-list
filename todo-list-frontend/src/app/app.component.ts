import {Component, OnDestroy} from '@angular/core';
import {Todo, TodoService} from "./todo.service";
import {BehaviorSubject, combineLatest, EMPTY, Observable, Subject} from "rxjs";
import {catchError, map, switchMap, takeUntil, tap} from "rxjs/operators";

@Component({
  selector: 'app-root',
  template: `
    <div class="toast-container">
      <app-toast
        *ngFor="let toast of toasts"
        [message]="toast.message"
        [show]="toast.show"
        [isError]="toast.isError"
        (close)="removeToast(toast.id)">
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
export class AppComponent implements OnDestroy{
  private refresh$ = new BehaviorSubject<void>(undefined);

  readonly filteredTodos$: Observable<Todo[]>;
  private todos$ = new BehaviorSubject<Todo[]>([]);
  search$ = new BehaviorSubject<string>('');

  isLoading = true;
  deletingIds = new Set<number>();
  toasts: { id: number, message: string, show: boolean, isError: boolean }[] = [];

  private unsubscribeRefresh$ = new Subject<void>();

  constructor(private todoService: TodoService) {
    this.refresh$.pipe(
      tap(() => this.isLoading = true),
      switchMap(() => this.todoService.getAll()),
      tap((todos) => {
        this.todos$.next(todos);
        this.isLoading = false;
        takeUntil(this.unsubscribeRefresh$)
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

  ngOnDestroy(): void {
    this.unsubscribeRefresh$.next();
    this.unsubscribeRefresh$.complete();
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
      const toast = this.toasts.find(t => t.id === toastId);
      if (toast) {
        toast.show = false;
      }
    }, 3000);
  }

  removeToast(toastId: number): void {
    const toastIndex = this.toasts.findIndex(t => t.id === toastId);
    if (toastIndex > -1) {
      this.toasts.splice(toastIndex, 1);
    }
  }
}