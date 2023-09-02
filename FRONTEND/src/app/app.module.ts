import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './components/app/app.component';
import { AuthenticationComponent } from './components/authentication/authentication.component';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { AuthenticationService } from './services/authentication.service';
import { UserService } from './services/user.service';

@NgModule({
  declarations: [
    AppComponent,
    AuthenticationComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule
  ],
  providers: [AuthenticationService, UserService],
  bootstrap: [AppComponent]
})
export class AppModule { }
