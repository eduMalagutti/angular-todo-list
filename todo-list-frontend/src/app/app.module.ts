import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppComponent} from './app.component';
import {TodoItemComponent} from './todo-item/todo-item.component';
import {FormsModule} from "@angular/forms";
import {ProgressBarComponent} from './progress-bar/progress-bar.component';
import {ToastComponent} from "./toast/toast.component";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";

@NgModule({
  declarations: [
    AppComponent,
    TodoItemComponent,
    ProgressBarComponent,
    ToastComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    FormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}
