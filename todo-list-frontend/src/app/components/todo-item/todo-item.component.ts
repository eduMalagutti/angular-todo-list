import { Component, HostListener, input, output, computed } from '@angular/core';
import { Todo } from '../../types/todo';

@Component({
  selector: 'app-todo-item',
  standalone: true,
  template: `
    <div class="task-indicator" [class.deleting]="isDeleting()">
      @if (!isDeleting()) {
        {{ item().task }}
      } @else {
        Deleting...
      }
    </div>
    <div class="priority-indicator" [style.background-color]="color()">
      {{ item().priority }}
    </div>
  `,
  styleUrls: ['todo-item.component.scss']
})
export class TodoItemComponent {
  // Signal inputs
  readonly item = input.required<Todo>();
  readonly isDeleting = input(false);

  // Signal output
  readonly deleteRequest = output<Todo>();

  // Computed signal for color based on priority
  readonly color = computed(() => {
    switch (this.item().priority) {
      case 1:
        return 'green';
      case 2:
        return 'yellow';
      case 3:
        return 'red';
      default:
        return 'gray';
    }
  });

  @HostListener('click')
  onClick() {
    if (!this.isDeleting()) {
      this.deleteRequest.emit(this.item());
    }
  }
}
