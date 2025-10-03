import {Component, Input} from '@angular/core';

@Component({
  selector: 'app-toast',
  template: `
    <div *ngIf="show" class="toast" [class.show]="show" [class.error]="isError">
      {{ message }}
    </div>
  `,
  styleUrls: ['toast.component.scss'],
})
export class ToastComponent {
    @Input() message!: string;
    @Input() show = false;
    @Input() isError = false;
}
