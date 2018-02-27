import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { UserService } from '../user.service';
import { FormBuilder, FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { BidOrder } from './bid-order';

@Component({
	selector: 'app-show-user-task',
	templateUrl: './show-user-task.component.html',
	styleUrls: ['./show-user-task.component.css']
})
export class ShowUserTaskComponent implements OnInit {

	formTasks: any
	formTask: any
	
	enums: string[] = new Array();
	taskId: string;
	modelTask: any;
	rankOrder: boolean = false;
	showTaskForm : FormGroup = new FormGroup({});

	constructor(private route: ActivatedRoute, private userService: UserService, private formBuilder: FormBuilder) { }

	ngOnInit() {
		this.route.params.subscribe(params => {
			var task_id = params['taskId'];
			this.taskId = task_id;
			console.log("taskId " + this.taskId)
			if (!this.taskId)
				return;
			this.userService.showTask(this.taskId)
			.subscribe(
				data=>{
					this.formTask = data;
					var josGroups
					for (var task in this.formTask) {

						const jobGroup: FormGroup = new FormGroup({});
						for (const key in this.formTask[task].forma) {
							if (this.formTask[task].forma.hasOwnProperty(key)) {
								const control: FormControl = new FormControl("", Validators.required);
					                //jobGroup.addControl(this.formTask[task].forma[key], control);
					                this.showTaskForm.addControl(this.formTask[task].forma[key], control)
					                break;
					            }
					        }

					        console.log(this.formTask[task]);
					        if(this.formTask[task].forma.type.name=="enum"){

					        	var values = this.formTask[task].values
					        	if(this.formTask[task].forma.id=="rankEnum"){
					        		this.enums = values;
					        		this.rankOrder = true;
					        	} else {

					        		for (var key in values) {
					        			if (values.hasOwnProperty(key)) {
					        				console.log(key + " -> " + values[key]);
					        				this.enums.push(key);
					        			}
					        		}
					        	}
					        	this.formTask.enum = this.formTask[task].values;
					        	console.log(this.formTask[task].values)
					        }
					    }


					    console.log("data " + this.formTask)
					    this.formTasks = data;
					}
					)
		}) 
	}

	executeTask(){
		var value = this.showTaskForm.value;
		this.userService.executeTask(this.taskId, value).subscribe();
	}

	acceptBid(values:any){
		//var value = this.showTaskForm.value;

		var bidValue: BidOrder = new BidOrder();
		if(values!="otkazi"){
			for (var key in values) {
				if (values.hasOwnProperty(key) && key=="id") {
					//bidValue.id = values[key];
					//bidValue = new BidOrder(values[key], values.firm.id)
					bidValue.id = values[key];
					bidValue.firm = values.firm.id;
					bidValue.accept = "yes";
					bidValue.cancelOrder = "";
					bidValue.explanation = "";
					console.log(key + " -> " + values[key]);
					break;
					
				}
			}
		}
		if(values=="otkazi"){
			bidValue.cancelOrder = "otkazi";
			bidValue.explanation = "";
			bidValue.accept = "";
		}
		console.log(bidValue)
		this.userService.executeTask(this.taskId, bidValue).subscribe();
	}

	explanation(values: any){
		var bidValue: BidOrder = new BidOrder();
		if(values!="otkazi"){
			for (var key in values) {
				if (values.hasOwnProperty(key) && key=="id") {
					//bidValue.id = values[key];
					//bidValue = new BidOrder(values[key], values.firm.id)
					bidValue.id = values[key];
					bidValue.firm = values.firm.id;
					bidValue.accept = "";
					bidValue.cancelOrder = "";
					bidValue.explanation = "objasnjenje";
					console.log(key + " -> " + values[key]);
					break;
					
				}
			}
		}
		console.log(bidValue)
		this.userService.executeTask(this.taskId, bidValue).subscribe();
	
	}

}
