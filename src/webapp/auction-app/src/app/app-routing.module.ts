import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BrowserModule } from'@angular/platform-browser';
import { Routes, RouterModule } from '@angular/router';

import { LoginComponent } from './login/login.component';
import { RegistrationComponent } from './registration/registration.component';
import { UsersComponent } from './users/users.component'

const routes: Routes = [
	{ path: '',   component: LoginComponent },
	{ path: 'login',   component: LoginComponent },
	{ path: 'registration',   component: RegistrationComponent },
	{ path : 'users', component: UsersComponent}
];

@NgModule({
	imports: [
	CommonModule,
	BrowserModule,
	RouterModule.forRoot(routes)
	],
	declarations: []
})
export class AppRoutingModule { }
