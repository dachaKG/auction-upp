import { Component, OnInit } from '@angular/core';
import { UserService } from '../user.service';

@Component({
	selector: 'app-user-tasks',
	templateUrl: './user-tasks.component.html',
	styleUrls: ['./user-tasks.component.css']
})
export class UserTasksComponent implements OnInit {

	tasks: any;

	constructor(private userService: UserService) { }

	ngOnInit() {
		this.userService.findTasks()
			.subscribe(
				data=>{
					console.log("aaaa")
					this.tasks = data;
				}

			)
	}

}
