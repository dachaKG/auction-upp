import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { RegistrationService } from './registration.service';
import { Task } from "../task";
import { Firm } from "./firm";

@Component({
	selector: 'app-registration',
	templateUrl: './registration.component.html',
	styleUrls: ['./registration.component.css']
})
export class RegistrationComponent implements OnInit {

	isFirm: boolean = false;
	registrationForm: FormGroup;
	taskId: string;
	registrationFirm: boolean = false;
	task: Task;
	firmForm: FormGroup;
	categories: any;
	firm: Firm = null;
	constructor(private registrationService: RegistrationService) { }

	ngOnInit() {
		this.registrationForm = new FormGroup({
			firstName: new FormControl('uros',[Validators.required]),
			lastName: new FormControl('vasiljevic',[Validators.required]),
			email: new FormControl('uppauction1@gmail.com',[Validators.email, Validators.required]),
			username: new FormControl('parti',[Validators.required]),
			password: new FormControl('bor',[Validators.required]),
			city: new FormControl('bor',[Validators.required]),
			address: new FormControl('adresa',[Validators.required]),
			zipCode: new FormControl('3333',[Validators.required]),
			role: new FormControl('ROLE_USER',[Validators.required])
				
		})

		this.registrationService.activateProcess()
			.subscribe(
				data=> {
					this.taskId = data.taskId;
					console.log("id taska " + this.taskId)}
			);

		this.registrationService.findCategories()
			.subscribe(
				data=>{this.categories=data}
			)

	}

	registration(){
		let value = this.registrationForm.value;
		this.registrationService.registration(value, this.taskId)
			.subscribe(
				data => {console.log(data);
					this.task = data;
					if(data.name == "Firma"){
						this.firmForm = new FormGroup({
							category : new FormControl('',[Validators.required]),
							distance : new FormControl('20',[Validators.required]),
							name : new FormControl('firma a',[Validators.required])
						});
						console.log("kategorije " + this.categories);
						this.registrationFirm = true;
					}
				}
			);

	}

	changeValue(){
		if(this.registrationForm.controls['role'].value === 'ROLE_USER')
			this.isFirm = false;
		else if(this.registrationForm.controls['role'].value === 'ROLE_FIRM')
			this.isFirm = true;
			
	}

	registerFirm(){
		let value = this.firmForm.value;
		this.firm = new Firm(value.name, value.distance,value.category,this.task.username);
		this.registrationService.registerFirm(this.firm, this.task.taskId)
			.subscribe(
				data=>{console.log("firmaaaaa " + data)}
			)
	}

}
