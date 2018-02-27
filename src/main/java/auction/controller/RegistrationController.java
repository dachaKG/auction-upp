package auction.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.activiti.engine.IdentityService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import auction.dto.FirmDTO;
import auction.model.Category;
import auction.model.Firm;
import auction.spring.service.CategoryService;
import auction.spring.service.FirmService;
import auction.user.EnumRole;
import auction.user.User;
import auction.user.UserService;

@RestController
@RequestMapping(value = "/registration")
@CrossOrigin(origins = "http://localhost:4200")
public class RegistrationController {

	@Autowired
	UserService userService;

	@Autowired
	RuntimeService runtimeService;

	@Autowired
	TaskService taskService;

	@Autowired
	RepositoryService repositoryService;
	
	@Autowired
	CategoryService categoryService;
	
	@Autowired
	FirmService firmService;
	
	@Autowired
	IdentityService identityService;

	private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

	@PostMapping("/{taskId}")
	public ResponseEntity<Map<String, Object>> registration(@PathVariable String taskId, @RequestBody User user) {
		System.out.println("task kod registracije " + taskId);
		user.setPassword(encoder.encode(user.getPassword()));
		user.setConfirmationMail(UUID.randomUUID().toString());
		user.setConfirmed(false);
		user = userService.save(user);
		
		Task task = taskService.createTaskQuery().active().taskId(taskId).singleResult();
		System.out.println("aaaaa " + task.getProcessInstanceId());
		HashMap<String, Object> variables = (HashMap<String, Object>) runtimeService.getVariables(task.getProcessInstanceId());
		org.activiti.engine.identity.User newUser;
		newUser = identityService.newUser(user.getUsername());
		newUser.setFirstName(user.getFirstName());
		newUser.setLastName(user.getLastName());
		newUser.setEmail(user.getEmail());
		newUser.setPassword(user.getPassword());
		identityService.saveUser(newUser);
		identityService.createMembership(newUser.getId(), user.getRole().toString());
		//identUsers.add(newUser);

		variables.put("user", user);
		System.out.println(variables);
		taskService.complete(taskId, variables);
		if(user.getRole().compareTo(EnumRole.ROLE_FIRM) == 0) {
			Task taskFirm = taskService.createTaskQuery().active().list().get(taskService.createTaskQuery().active().list().size() - 1);
			Map<String, Object> taskMap = new HashMap<String, Object>();
			taskMap.put("taskId", taskFirm.getId());
			taskMap.put("name", taskFirm.getName());
			taskMap.put("username", user.getUsername());
			System.out.println("firma task id " + taskFirm.getId() + " task name " + taskFirm.getName());
			return new ResponseEntity<>(taskMap, HttpStatus.OK);
		}
		
		return new ResponseEntity<>(variables, HttpStatus.OK);
		// User newUser = userService.save(user);

	}

	@GetMapping("/activateProcess")
	public ResponseEntity<Map<String, Object>> getTask() {
		/*for (Deployment d : repositoryService.createDeploymentQuery().list()) {
            repositoryService.deleteDeployment(d.getId(), true);
        }*/
		System.out.println("Ukupan broj deployment-a: " + repositoryService.createDeploymentQuery().count());
		runtimeService.startProcessInstanceByKey("myProcess");

		Task task = taskService.createTaskQuery().active().list()
				.get(taskService.createTaskQuery().active().list().size() - 1);
		Map<String, Object> taskMap = new HashMap<String, Object>();
		taskMap.put("taskId", task.getId());
		taskMap.put("name", task.getName());
		System.out.println(task.toString());
		System.out.println("task id " + task.getId() + " task name " + task.getName());
		return new ResponseEntity<>(taskMap, HttpStatus.OK);
	}
	
	@PostMapping("/firm/{taskId}")
	public ResponseEntity<Map<String, Object>> registerFirm(@PathVariable String taskId, @RequestBody FirmDTO firmDTO){
		Category category = categoryService.findOne(firmDTO.getCategory());
		//User mailUser = userService.findOneByUsername(firmDTO.getUsername());
		Firm firm = new Firm();
		firm.setName(firmDTO.getName());
		firm.setCategory(category);
		firm.setDistance(firmDTO.getDistance());
		User user = userService.findOneByUsername(firmDTO.getUsername());
		firm = firmService.save(firm);
		
		user.setFirm(firm);
		userService.save(user);
		Task task = taskService.createTaskQuery().active().taskId(taskId).singleResult();
		System.out.println("aaaaa " + task.getProcessInstanceId());
		HashMap<String, Object> variables = (HashMap<String, Object>) runtimeService.getVariables(task.getProcessInstanceId());
		variables.put("user", user);
		System.out.println(variables);
		
		taskService.complete(taskId, variables);
		
		return new ResponseEntity<>(variables, HttpStatus.OK);
	}

}
