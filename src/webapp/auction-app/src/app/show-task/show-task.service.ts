import { Injectable } from '@angular/core';
import { Http, Response, Headers } from "@angular/http";
import 'rxjs/add/operator/map'
import 'rxjs/add/operator/catch';
import { environment } from '../../environments/environment';
import { Observable } from 'rxjs/Observable';

@Injectable()
export class ShowTaskService {

	private apiUrl = `${environment.BACKEND_URL}`;

	constructor(private http: Http) { }

	showTask(taskId: string){
		var headers = new Headers();

		headers.append('Authorization', 'Bearer ' + localStorage.getItem('app-token'));
		return this.http.get(this.apiUrl + "/firm/showTask/" + taskId, {headers: headers}).map(res=>res.json());
	}

	executeTask(taskId:string, map: any){
		var headers = new Headers();
		headers.append('Authorization', 'Bearer ' + localStorage.getItem('app-token'));
		return this.http.post(this.apiUrl + "/firm/execute/" + taskId, map, { headers: headers }).map(res=>res.json());
	}
}
