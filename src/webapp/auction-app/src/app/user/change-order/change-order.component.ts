import { Component, OnInit } from '@angular/core';
import {Router, ActivatedRoute, Params} from '@angular/router';
import { FormControl, FormGroup, Validators } from '@angular/forms'; 
import { UserService } from '../user.service';

@Component({
	selector: 'app-change-order',
	templateUrl: './change-order.component.html',
	styleUrls: ['./change-order.component.css']
})
export class ChangeOrderComponent implements OnInit {

	order: number = null;


	constructor(private activatedRoute: ActivatedRoute, private userService: UserService) { }

	ngOnInit() {
		this.activatedRoute.queryParams.subscribe((params: Params) => {
			this.order = params['order'];
		});


		console.log("change order " + this.order);
	}

	changeOrder(value: string){
		console.log(value);
		this.userService.changeOrder(this.order, value).subscribe(data=>{console.log(data)})
	}

}
