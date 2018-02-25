package auction.controller;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.Execution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import auction.user.UserService;

@RestController
@RequestMapping(value="confirm_registration")
public class ConfirmRegistration {
	
	@Autowired
	UserService userService;
	
	@Autowired
	RuntimeService runtimeService;
	
	@Autowired
	TaskService taskService;
	
	@GetMapping
	public RedirectView confirmRegistration(@RequestParam("token") String token, @RequestParam("task") String task) {
		Execution execution = runtimeService.createExecutionQuery().processInstanceId(task).signalEventSubscriptionName("Activate user").singleResult();
		runtimeService.signalEventReceived("Activate user", execution.getId());
		return new RedirectView("http://localhost:4200/");
		
	}

}
