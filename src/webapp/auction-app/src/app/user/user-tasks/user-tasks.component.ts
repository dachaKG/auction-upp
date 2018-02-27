import { Component, OnInit } from '@angular/core';
import { UserService } from '../user.service';
import { Router, ActivatedRoute, ParamMap } from '@angular/router';

@Component({
	selector: 'app-user-tasks',
	templateUrl: './user-tasks.component.html',
	styleUrls: ['./user-tasks.component.css']
})
export class UserTasksComponent implements OnInit {

	tasks: any;

	constructor(private userService: UserService, private router: Router) { }

	ngOnInit() {
		this.userService.findTasks()
			.subscribe(
				data=>{
					console.log("aaaa")
					this.tasks = data;
				}

			)
	}


	showTask(taskId: string){
		
		console.log(taskId);
		this.router.navigate(['/user/tasks/' + taskId]);
		
	}

}
