export class Task {
	taskId: string;
	name: string;
	username: string;

	constructor(taskId:string, name:string, username: string){
		this.taskId=taskId;
		this.name=name;
		this.username=username;
	}
}