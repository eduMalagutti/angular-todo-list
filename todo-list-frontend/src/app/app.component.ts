import {Component, computed, inject, signal} from '@angular/core';
import {CommonModule} from '@angular/common';
import {FormsModule} from '@angular/forms';
import {firstValueFrom} from 'rxjs';
import {Todo} from './types/todo';
import {TodoService} from './services/todo.service';
import {TodoItemComponent} from './components/todo-item/todo-item.component';
import {ProgressBarComponent} from './components/progress-bar/progress-bar.component';
import {ToastComponent} from './components/toast/toast.component';

interface Toast {
  id: number;
  message: string;
  show: boolean;
  isError: boolean;
}

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, FormsModule, TodoItemComponent, ProgressBarComponent, ToastComponent],
  template: `
    <div class="toast-container">
      @for (toast of toasts(); track toast.id) {
        <app-toast
          [message]="toast.message"
          [show]="toast.show"
          [isError]="toast.isError"
          (close)="removeToast(toast.id)">
        </app-toast>
      }
    </div>

    <div class="title"><h1>A list of TODOs</h1></div>

    <div class="list">
      <label for="search">Search...</label>
      <input id="search" type="text" [ngModel]="search()" (ngModelChange)="search.set($event)">

      @if (isLoading()) {
        <app-progress-bar></app-progress-bar>
      }

      @for (todo of filteredTodos(); track todo.id) {
        <app-todo-item
          [item]="todo"
          [isDeleting]="deletingIds().has(todo.id)"
          (deleteRequest)="handleDelete($event)">
        </app-todo-item>
      }
    </div>
  `,
  styleUrls: ['app.component.scss']
})
export class AppComponent {
  private readonly todoService = inject(TodoService);

  // Signals for state management
  readonly todos = signal<Todo[]>([]);
  readonly isLoading = signal(false);
  readonly search = signal('');
  readonly toasts = signal<Toast[]>([]);
  readonly deletingIds = signal(new Set<number>());

  constructor() {
    this.loadTodos();
  }

  private async loadTodos(): Promise<void> {
    this.isLoading.set(true);
    try {
      const data = await firstValueFrom(this.todoService.getAll());
      this.todos.set(data);
    } catch (error) {
      this.showToast('Failed to load todos', true);
    } finally {
      this.isLoading.set(false);
    }
  }

  // Computed signal for filtered todos
  readonly filteredTodos = computed(() => {
    const todos = this.todos();
    const searchTerm = this.search().toLowerCase();
    return todos.filter((todo: Todo) =>
      todo.task.toLowerCase().includes(searchTerm)
    );
  });

  async handleDelete(todoToDelete: Todo): Promise<void> {
    // Add to deleting set
    this.deletingIds.update(ids => {
      const newIds = new Set(ids);
      newIds.add(todoToDelete.id);
      return newIds;
    });

    try {
      await firstValueFrom(this.todoService.remove(todoToDelete.id));
      this.showToast('Item removed successfully!');
      await this.loadTodos();
    } catch (error) {
      this.showToast(error as string, true);
    } finally {
      // Remove from deleting set
      this.deletingIds.update(ids => {
        const newIds = new Set(ids);
        newIds.delete(todoToDelete.id);
        return newIds;
      });
    }
  }

  private showToast(message: string, isError = false): void {
    const toastId = Date.now();
    this.toasts.update(toasts => [
      ...toasts,
      {id: toastId, message, isError, show: true}
    ]);

    setTimeout(() => {
      this.toasts.update(toasts =>
        toasts.map(t =>
          t.id === toastId ? {...t, show: false} : t
        )
      );
    }, 3000);
  }

  removeToast(toastId: number): void {
    this.toasts.update(toasts =>
      toasts.filter(t => t.id !== toastId)
    );
  }
}
