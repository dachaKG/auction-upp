import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BrowserModule } from'@angular/platform-browser';
import { Routes, RouterModule } from '@angular/router';

import { LoginComponent } from './login/login.component';
import { RegistrationComponent } from './registration/registration.component';
import { UserComponent } from './user/user.component';
import { FirmComponent } from './firm/firm.component';
import { ShowTaskComponent } from './show-task/show-task.component';

const routes: Routes = [
	{ path: '',   component: LoginComponent },
	{ path: 'login',   component: LoginComponent },
	{ path: 'registration',   component: RegistrationComponent },
	{ path : 'user', component: UserComponent},
	{ path : 'firm', component: FirmComponent},
	{ path : 'task', component: ShowTaskComponent},
	{ path : 'task/:taskId', component: ShowTaskComponent}

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
