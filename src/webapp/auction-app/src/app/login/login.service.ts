import { Injectable } from '@angular/core';
import { Http, Response, Headers } from "@angular/http";
import 'rxjs/add/operator/map'
import 'rxjs/add/operator/catch';
import { environment } from '../../environments/environment';

@Injectable()
export class LoginService {

	private apiUrl = `${environment.BACKEND_URL}`;

	constructor(private http: Http) { }

	authenticate(credentials:any){
		return this.http.post(this.apiUrl + "/login", credentials).map(res=>res.json());
	}

	

}
