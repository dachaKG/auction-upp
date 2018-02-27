import { Component, OnInit } from '@angular/core';
import { FirmService } from './firm.service';
import { Router, ActivatedRoute, ParamMap } from '@angular/router';

@Component({
	selector: 'app-firm',
	templateUrl: './firm.component.html',
	styleUrls: ['./firm.component.css']
})
export class FirmComponent implements OnInit {

	tasks: any;
	formTask: any

	constructor(private firmService: FirmService, private router: Router) { }

	ngOnInit() {
		this.firmService.getFirm()
			.subscribe(
				data=>{
					console.log("aaaa")
					this.tasks = data;
				}

			)
	}

	showTask(taskId: string){
		console.log(taskId);
		this.router.navigate(['/task/' + taskId]);
		
	}

}
