import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BrowserModule } from'@angular/platform-browser';
import { Routes, RouterModule } from '@angular/router';

import { OrderGoodsComponent } from './order-goods/order-goods.component';
import { ChangeOrderComponent } from './change-order/change-order.component';


const routes: Routes = [
	{ path: 'user/order', component: OrderGoodsComponent },
	{ path: 'user/change', component: ChangeOrderComponent}

];

@NgModule({
  imports: [
    CommonModule,
	BrowserModule,
	RouterModule.forRoot(routes)
  ],
  declarations: []
})
export class UserRoutingModule { }
