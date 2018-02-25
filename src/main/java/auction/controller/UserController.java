package auction.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import auction.user.User;
import auction.user.UserService;

@RestController
@RequestMapping(value = "/users")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	TaskService taskService;
	
	
	@PreAuthorize("hasRole('ROLE_USER')")
	@GetMapping
	public ResponseEntity<List<User>> getUsers(){
		System.out.println("usao u get user");
		List<User> users = userService.findAll();
		
		return new ResponseEntity<>(users, HttpStatus.OK);
		
	}
	
	@PreAuthorize("hasRole('ROLE_USER')")
	@GetMapping("/tasks")
	public List<Map<String, Object>> getOrderGoods() {

		User user = getUser();
		//Firm firm = user.getFirm();
		
		List<Task> taskList = taskService.createTaskQuery().taskAssignee(user.getUsername()).list();
		
		List<Map<String, Object>> customTaskList = new ArrayList<>();
	    for (Task task : taskList) {
	        Map<String, Object> map = new HashMap<>();
	        map.put("taskId", task.getId());
	        System.out.println("id taska " + task.getId());
	        map.put("taskDefinitionKey", task.getTaskDefinitionKey());
	        map.put("taskName", task.getName());

	        customTaskList.add(map);
	    }
	    System.out.println("task list mapa user " + customTaskList.size());
	    return customTaskList;
	}
	
	private User getUser() {
		SecurityContext context = SecurityContextHolder.getContext();
		Authentication authentication = context.getAuthentication();
		User user = userService.findOneByUsername(authentication.getName());
		return user;
	}

}
