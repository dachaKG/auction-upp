package auction.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.FormService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.form.FormProperty;
import org.activiti.engine.form.StartFormData;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import auction.dto.OrderGoodsDTO;
import auction.spring.service.CategoryService;
import auction.spring.service.OrderGoodsService;
import auction.user.User;
import auction.user.UserService;

@RestController
@RequestMapping(value = "/order-goods")
@CrossOrigin(origins = "http://localhost:4200")
public class OrderGoodsController {
	
	@Autowired
	RuntimeService runtimeService;

	@Autowired
	TaskService taskService;

	@Autowired
	RepositoryService repositoryService;
	
	@Autowired
	OrderGoodsService orderGoodsService;
	
	@Autowired
	CategoryService categoryService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	FormService formService;
	
	@Autowired
	IdentityService identityService;
	
	@PreAuthorize("hasRole('ROLE_USER')")
	@PostMapping
	public void saveOrder(@RequestBody OrderGoodsDTO order) {
		
		//runtimeService.startProcessInstanceByKey("mainProcess");
		
		ProcessDefinition procDef = repositoryService.createProcessDefinitionQuery().processDefinitionKey("mainProcess").latestVersion().singleResult();
		StartFormData formData = formService.getStartFormData(procDef.getId());
		List<FormProperty> formProperties = formData.getFormProperties();
		
		Map<String, String> stringMap = getMapFromObject(order);
		User user = getUser();
		identityService.setAuthenticatedUserId(user.getUsername());
		formService.submitStartFormData(procDef.getId(), stringMap);
		System.out.println("ajde prodjei");
		//return orderGoods;
		//taskService.complete(task.getId(), variables);
		
	}
	
	@PreAuthorize("hasRole('ROLE_USER')")
	@GetMapping("/change")
	public ResponseEntity<String> changeOrder(@RequestParam(value = "order",required=true) Long Order,
			@RequestParam(value="decision", required=true) String decision) {
		
			Task task = taskService.createTaskQuery().active().list()
					.get(taskService.createTaskQuery().active().list().size() - 1);
			//Task task = taskService.createTaskQuery().active().taskId(taskId).singleResult();
			System.out.println(task.getName());
			HashMap<String, Object> variables = (HashMap<String, Object>) runtimeService.getVariables(task.getProcessInstanceId());
			variables.put("changeExpectedBids", decision);
			taskService.complete(task.getId(), variables);
			//runtimeService.
		
		
		return null;
	}
	
	@SuppressWarnings("unchecked")
	private Map<String, String> getMapFromObject(OrderGoodsDTO order){
		ObjectMapper oMapper = new ObjectMapper();
		Map<String, Object> map = oMapper.convertValue(order, Map.class);
		Map<String, String> stringMap = new HashMap<String, String>();
		for(String strKey: map.keySet())
		{
			
			if(strKey.equals("receiveDeadline") || strKey.equals("serviceDeadline")) {
				//String date = new SimpleDateFormat("dd-MM-yyyy").format(map.get(strKey));
				DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss.SSS");

		        // Get date and time information in milliseconds
				long now = System.currentTimeMillis();

		        // Create a calendar object that will convert the date and time value
		        // in milliseconds to date. We use the setTimeInMillis() method of the
		        // Calendar object.
				Calendar calendar = Calendar.getInstance();
				calendar.setTimeInMillis(Long.valueOf((Long) map.get(strKey)));

		        System.out.println(now + " = " + formatter.format(calendar.getTime()));
				map.put(strKey, formatter.format(calendar.getTime()));
			}
			stringMap.put(strKey, String.valueOf(map.get(strKey)));
		}
		return stringMap;
		
	}
	
	private User getUser() {
		SecurityContext context = SecurityContextHolder.getContext();
		Authentication authentication = context.getAuthentication();
		User user = userService.findOneByUsername(authentication.getName());
		return user;
	}

}
