import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { LoginService } from "./login.service";
import { Router, ActivatedRoute, ParamMap } from '@angular/router';

@Component({
	selector: 'app-login',
	templateUrl: './login.component.html',
	styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

	loginForm: FormGroup;
	authenticate: any;
	

	constructor(private loginService: LoginService, private router: Router) { }

	ngOnInit() {
		this.loginForm = new FormGroup({
			username: new FormControl('',[Validators.required]),
			password: new FormControl('',[Validators.required])
		});
	}

	login(){
		let credentials = this.loginForm.value;
		this.loginService.authenticate(credentials)
			.subscribe(
				data => {
					this.authenticate = data;
					console.log(this.authenticate);
					localStorage.setItem('app-token',this.authenticate.token);
					if(data.role == "ROLE_USER")
						this.router.navigate(['/user']);
					else if(data.role == "ROLE_FIRM")
						this.router.navigate(['/firm']);
				}
			);
		//loginService.login(credentials)
	}

}
