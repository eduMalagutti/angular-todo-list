import {Component, EventEmitter, Input, Output} from '@angular/core';
import {animate, AnimationEvent, style, transition, trigger} from "@angular/animations";

@Component({
  selector: 'app-toast',
  template: `
    <div class="toast" 
         *ngIf="show" 
         [class.show]="show" 
         [class.error]="isError"
         [@toastAnimation]
         (@toastAnimation.done)="onClose($event)">
      {{ message }}
    </div>
  `,
  styleUrls: ['toast.component.scss'],
  animations: [
    trigger('toastAnimation', [
      transition(':enter', [
        style({transform: 'translateX(100%)', opacity: 0}),
        animate('200ms ease-in', style({transform: 'translateX(0)', opacity: 1}))
      ]),
      transition(':leave', [
        animate('200ms ease-out', style({transform: 'translateX(100%)', opacity: 0}))
      ])
    ])
  ]
})
export class ToastComponent {
  @Input() message!: string;
  @Input() show = false;
  @Input() isError = false;

  @Output() close = new EventEmitter<void>();

  onClose(event: AnimationEvent): void {
    if (event.toState === 'void') {
      this.close.emit();
    }
  }
}
