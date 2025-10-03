import {Component, EventEmitter, HostListener, Input, Output} from '@angular/core';
import {Todo} from "../todo.service";

@Component({
  selector: 'app-todo-item',
  template: `
    <div class="task-indicator" [class.deleting]="isDeleting">
      <ng-container *ngIf="!isDeleting; else loading">
        {{ item.task }}
      </ng-container>
      <ng-template #loading>
        Deleting...
      </ng-template>
    </div>
    <div class="priority-indicator" [style.background-color]="color">
      {{ item.priority }}
    </div>
  `,
  styleUrls: ['todo-item.component.scss']
})
export class TodoItemComponent {

  @Input() item!: Todo;
  @Input() isDeleting = false;

  @Output() deleteRequest = new EventEmitter<Todo>();

  @HostListener('click')
  onClick() {
    if (!this.isDeleting) {
      this.deleteRequest.emit(this.item);
    }
  }

  get color() {
    switch (this.item.priority) {
      case 1:
        return 'green';
      case 2:
        return 'yellow';
      case 3:
        return 'red';
    }
  }
}