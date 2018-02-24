import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { ShowTaskService } from './show-task.service';
import { FormBuilder, FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';

@Component({
	selector: 'app-show-task',
	templateUrl: './show-task.component.html',
	styleUrls: ['./show-task.component.css']
})
export class ShowTaskComponent implements OnInit {

	formTasks: any
	formTask: any
	
	enums: string[] = new Array();
	taskId: string;
	modelTask: any;
	showTaskForm : FormGroup = new FormGroup({});

	constructor(private route: ActivatedRoute, private showTaskService: ShowTaskService, private formBuilder: FormBuilder) { }

	ngOnInit() {
		this.route.params.subscribe(params => {
			var task_id = params['taskId'];
			this.taskId = task_id;
			console.log("taskId " + this.taskId)
			if (!this.taskId)
				return;
			this.showTaskService.showTask(this.taskId)
			.subscribe(
				data=>{
						//let enumeracija : string[] = new Array();
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


					        	for (var key in values) {
					        		if (values.hasOwnProperty(key)) {
					        			console.log(key + " -> " + values[key]);
					        			this.enums.push(values[key]);
					        		}
					        	}

								//this.enums = enumeracija;
								this.formTask.enum = this.formTask[task].values;
								console.log(this.formTask[task].values)
							}
								//console.log(this.formTask[task].forma.getType());
								//this.formTask[task].forma.getType().information['values']
							//if(task.forma.type.informatio['values'])
						}


						console.log("data " + this.formTask)
						this.formTasks = data;
					}
					)
			/*this.factorService.get(id)
			.subscribe(
				factor => this.factor = factor,
				);*/
			}) 
	}

	proba(){
		var value = this.showTaskForm.value;
		var map = new Map<string, string>();
/*map.set("A",1);
map.set("B",2);
map.set("C",3);*/
		/*for (var key in value) {
			if (value.hasOwnProperty(key)) {
				console.log(key + " -> " + value[key]);
				map.set(key,value[key]);
				//this.enums.push(value[key]);
			}
		}
*/
		this.showTaskService.executeTask(this.taskId, value).subscribe();
		console.log("mapa " + map);
		console.log("modelTask " + this.showTaskForm.value);
	}

}
