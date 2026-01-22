import { Component, input, output } from '@angular/core';
import { animate, AnimationEvent, style, transition, trigger } from '@angular/animations';

@Component({
  selector: 'app-toast',
  standalone: true,
  template: `
    @if (show()) {
      <div class="toast" 
           [class.show]="show()" 
           [class.error]="isError()"
           [@toastAnimation]
           (@toastAnimation.done)="onClose($event)">
        {{ message() }}
      </div>
    }
  `,
  styleUrls: ['toast.component.scss'],
  animations: [
    trigger('toastAnimation', [
      transition(':enter', [
        style({ transform: 'translateX(100%)', opacity: 0 }),
        animate('200ms ease-in', style({ transform: 'translateX(0)', opacity: 1 }))
      ]),
      transition(':leave', [
        animate('200ms ease-out', style({ transform: 'translateX(100%)', opacity: 0 }))
      ])
    ])
  ]
})
export class ToastComponent {
  // Signal inputs
  readonly message = input.required<string>();
  readonly show = input(false);
  readonly isError = input(false);

  // Signal output
  readonly close = output<void>();

  onClose(event: AnimationEvent): void {
    if (event.toState === 'void') {
      this.close.emit();
    }
  }
}
