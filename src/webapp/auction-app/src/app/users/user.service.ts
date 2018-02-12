import { Injectable } from '@angular/core';
import { Http, Headers }  from "@angular/http";
import 'rxjs/add/operator/map'
import 'rxjs/add/operator/catch';
import { environment } from '../../environments/environment';


@Injectable()
export class UserService {

	private apiUrl = `${environment.BACKEND_URL}`;


	constructor(private http: Http) { }

	getUsers(){
		
		var headers = new Headers();

		headers.append('Authorization', 'Bearer ' + localStorage.getItem('app-token'));

		return this.http.get(this.apiUrl + '/users', {headers: headers}).map(res=>res.json());
	}

}
