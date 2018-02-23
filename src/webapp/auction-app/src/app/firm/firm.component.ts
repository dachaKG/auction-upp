import { Component, OnInit } from '@angular/core';
import { FirmService } from './firm.service';

@Component({
	selector: 'app-firm',
	templateUrl: './firm.component.html',
	styleUrls: ['./firm.component.css']
})
export class FirmComponent implements OnInit {

	orderGoods: any;

	constructor(private firmService: FirmService) { }

	ngOnInit() {
		this.firmService.getFirm()
			.subscribe(
				data=>{
					console.log("aaaa")
					this.orderGoods = data;
				}

			)
	}

}
