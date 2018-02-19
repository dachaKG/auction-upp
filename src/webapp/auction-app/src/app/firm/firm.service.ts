import { Injectable } from '@angular/core';
import { Http, Response, Headers } from "@angular/http";
import 'rxjs/add/operator/map'
import 'rxjs/add/operator/catch';
import { environment } from '../../environments/environment';
import { Observable } from 'rxjs/Observable';

@Injectable()
export class FirmService {

	private apiUrl = `${environment.BACKEND_URL}`;

	constructor(private http: Http) { }

	getFirm(){
		var headers = new Headers();

		headers.append('Authorization', 'Bearer ' + localStorage.getItem('app-token'));

		return this.http.get(this.apiUrl + "/firm", {headers: headers}).map(res=>res.json());
	}

}
