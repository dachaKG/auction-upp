import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule  } from '@angular/forms';
import { HttpModule } from '@angular/http';
import { HttpClientModule } from '@angular/common/http';
import { RouterModule } from '@angular/router';

import { AppComponent } from './app.component';
import { AppRoutingModule } from './/app-routing.module';
import { LoginComponent } from './login/login.component';
import { RegistrationComponent } from './registration/registration.component';
import { LoginService } from './login/login.service';
import { UsersComponent } from './users/users.component';
import { ConfigComponent } from './config/config.component';
import { UserService } from './users/user.service';
import { RegistrationService } from './registration/registration.service';


@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegistrationComponent,
    UsersComponent,
    ConfigComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    ReactiveFormsModule,
    HttpModule,
    RouterModule,
    AppRoutingModule,
    HttpClientModule
  ],
  providers: [LoginService, UserService, RegistrationService],
  bootstrap: [AppComponent]
})
export class AppModule { }
