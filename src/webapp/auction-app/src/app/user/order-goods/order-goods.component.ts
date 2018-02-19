import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { UserService } from '../user.service';
@Component({
	selector: 'app-order-goods',
	templateUrl: './order-goods.component.html',
	styleUrls: ['./order-goods.component.css']
})
export class OrderGoodsComponent implements OnInit {

	orderForm: FormGroup;
	categories: any;

	constructor(private userService: UserService) { }

	ngOnInit() {
		this.orderForm = new FormGroup({
			category: new FormControl('',[Validators.required]),
			description: new FormControl('',[Validators.required]),
			estimatedValue: new FormControl('',[Validators.required]),
			receiveDeadline: new FormControl('',[Validators.required]),
			expectedBids: new FormControl('',[Validators.required]),
			serviceDeadline: new FormControl('',[Validators.required]),
				
		});

		this.userService.findCategories()
			.subscribe(
				data=>{this.categories=data}
			)
	}

	orderGoods(){
		if(this.orderForm.valid){
			let value = this.orderForm.value;
			this.userService.orderGoods(value).subscribe(data=>console.log("prosao subscribe"));
		}
	}

}
