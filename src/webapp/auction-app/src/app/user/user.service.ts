import { Injectable } from '@angular/core';
import { Http, Response, Headers } from "@angular/http";
import 'rxjs/add/operator/map'
import 'rxjs/add/operator/catch';
import { environment } from '../../environments/environment';
import { Observable } from 'rxjs/Observable';

@Injectable()
export class UserService {

	private apiUrl = `${environment.BACKEND_URL}`;

	constructor(private http: Http) { }

	findCategories(){
		return this.http.get(this.apiUrl + "/category").map(res=>res.json());
	}

	orderGoods(order: any){
		var headers = new Headers();

		headers.append('Authorization', 'Bearer ' + localStorage.getItem('app-token'));

		return this.http.post(this.apiUrl + "/order-goods", order, {headers: headers});
	}

	changeOrder(order: number, decision: string){
		var headers = new Headers();

		headers.append('Authorization', 'Bearer ' + localStorage.getItem('app-token'));

		return this.http.get(this.apiUrl+"/order-goods/change",  { params: {
			order: order,
			decision: decision
		}, headers: headers })
	}

	findTasks(){
		var headers = new Headers();

		headers.append('Authorization', 'Bearer ' + localStorage.getItem('app-token'));

		return this.http.get(this.apiUrl + "/users/tasks", {headers: headers}).map(res=>res.json());
	}



}
