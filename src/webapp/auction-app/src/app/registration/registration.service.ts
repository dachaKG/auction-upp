import { Injectable } from '@angular/core';
import { Http, Response, Headers } from "@angular/http";
import 'rxjs/add/operator/map'
import 'rxjs/add/operator/catch';
import { environment } from '../../environments/environment';
import { Observable } from 'rxjs/Observable';
import { Task } from "../task";
import { Firm } from "./firm";

@Injectable()
export class RegistrationService {

	private apiUrl = `${environment.BACKEND_URL}`;

	constructor(private http: Http) { }

	registration(user:any, taskId:string) : Observable<Task> {
		return this.http.post(this.apiUrl + "/registration/" + taskId, user).map(res=>res.json());
	}

	activateProcess(){
		return this.http.get(this.apiUrl + "/registration/activateProcess").map(res=>res.json());
	}

	findCategories(){
		return this.http.get(this.apiUrl + "/category"). map(res=>res.json());
	}

	registerFirm(firm: Firm, taskId:string) : Observable<Task> {
		return this.http.post(this.apiUrl + "/registration/firm/" + taskId, firm).map(res=>res.json());
	}

}
