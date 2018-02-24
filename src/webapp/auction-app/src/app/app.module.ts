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
import { ConfigComponent } from './config/config.component';
import { UserService } from './user/user.service';
import { RegistrationService } from './registration/registration.service';
import { UserComponent } from './user/user.component';
import { UserRoutingModule } from './user/user-routing.module';
import { OrderGoodsComponent } from './user/order-goods/order-goods.component';
import { FirmComponent } from './firm/firm.component';
import { FirmService } from './firm/firm.service';
import { ChangeOrderComponent } from './user/change-order/change-order.component';
import { ShowTaskComponent } from './show-task/show-task.component';
import { ShowTaskService } from './show-task/show-task.service';


@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegistrationComponent,
    ConfigComponent,
    UserComponent,
    OrderGoodsComponent,
    FirmComponent,
    ChangeOrderComponent,
    ShowTaskComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    ReactiveFormsModule,
    HttpModule,
    RouterModule,
    AppRoutingModule,
    HttpClientModule,
    UserRoutingModule
  ],
  providers: [LoginService, UserService, RegistrationService, FirmService, ShowTaskService],
  bootstrap: [AppComponent]
})
export class AppModule { }
