package auction.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.FormService;
import org.activiti.engine.TaskService;
import org.activiti.engine.form.FormProperty;
import org.activiti.engine.form.TaskFormData;
import org.activiti.engine.impl.form.EnumFormType;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import auction.user.User;
import auction.user.UserService;

@RestController
@RequestMapping(value = "/firm")
@CrossOrigin(origins = "http://localhost:4200")
public class FirmController {

	
	@Autowired
	TaskService taskService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	FormService formService;

	@PreAuthorize("hasRole('ROLE_FIRM')")
	@GetMapping
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
	    return customTaskList;
	}
	
	

	@PreAuthorize("hasRole('ROLE_FIRM')")
	@GetMapping("/showTask/{taskId}")
	public List<Map<String, Object>> showTask(@PathVariable String taskId){
		TaskFormData taskFormData = formService.getTaskFormData(taskId);
		List<FormProperty> formProperties = taskFormData.getFormProperties();
		//formProperties.get(0).getName()
		List<Map<String, Object>> customTaskProperties = new ArrayList<>();
		for(FormProperty formProperty : formProperties) {
			Map<String, Object> map = new HashMap<>();
			map.put("label", formProperty.getName());
			map.put("input", formProperty.getType().getName());
			map.put("forma", formProperty);
			if(formProperty.getType().getName().equals("enum")) {
				map.put("input", "select");
				EnumFormType enumType = (EnumFormType) formProperty.getType();
				
				map.put("values", formProperty.getType().getInformation("values"));
			} else if (formProperty.getType().getName().equals("long")) {
				map.put("input", "string");

			} else {
				map.put("input", formProperty.getType().getName());
			}
				
			//map.put(formProperty.getName(), formProperty.getType().getName());
			customTaskProperties.add(map);
		}
		
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		//model.addAttribute("task", task);
		
		//U definiciji procesa je definisan formKey, na osnovu kog se odredjuje 
		//koja se stranica prikazuje
		String form = formService.getTaskFormData(taskId).getFormKey();
		
		return customTaskProperties;
	}
	
	@PreAuthorize("hasRole('ROLE_FIRM')")
	@PostMapping("/execute/{taskId}")
	public String execcuteTask(@PathVariable String taskId, @RequestBody Map<String, String> params){
		User user = getUser();
		if (canExecute(taskId, user.getUsername())){
			//pre ovog koraka bi se trebala sprovesti validacija
			//da li su uneti svi potrebni parametri (required), da li ima neslaganja tipova
			//ako se unosi email adresa, da li je validna i sl.
			formService.submitTaskFormData(taskId, params);
			//message = "Zadatak uspeÅ¡no izvrÅ¡en";
		}
		System.out.println(params.toString());
		
		return "uspesno";

	}
	
	private User getUser() {
		SecurityContext context = SecurityContextHolder.getContext();
		Authentication authentication = context.getAuthentication();
		User user = userService.findOneByUsername(authentication.getName());
		return user;
	}
	
	private boolean canExecute(String taskId, String userId){
		for (Task t : taskService.createTaskQuery().taskAssignee(userId).list())
			if (t.getId().equals(taskId))
				return true;
		return false;
	}

}
