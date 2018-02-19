package auction.order;

import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.Execution;
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

import auction.category.CategoryService;
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
	
	@PreAuthorize("hasRole('ROLE_USER')")
	@PostMapping
	public void saveOrder(@RequestBody OrderGoodsDTO order) {
		runtimeService.startProcessInstanceByKey("mainProcess");

		Task task = taskService.createTaskQuery().active().list()
				.get(taskService.createTaskQuery().active().list().size() - 1);
		Map<String, Object> taskMap = new HashMap<String, Object>();
		taskMap.put("taskId", task.getId());
		taskMap.put("name", task.getName());
		System.out.println(task.toString());
		System.out.println("task id " + task.getId() + " task name " + task.getName());
		
		SecurityContext context = SecurityContextHolder.getContext();
		Authentication authentication = context.getAuthentication();
		User user = userService.findOneByUsername(authentication.getName());
		
		OrderGoods orderGoods = new OrderGoods();
		orderGoods.setDescription(order.getDescription());
		orderGoods.setEstimatedValue(order.getEstimatedValue());
		orderGoods.setExpectedBids(order.getExpectedBids());
		orderGoods.setReceiveDeadline(order.getReceiveDeadline());
		orderGoods.setServiceDeadline(order.getServiceDeadline());
		orderGoods.setCategory(categoryService.findOne(order.getCategory()));
		orderGoods.setUser(user);
		orderGoods = orderGoodsService.save(orderGoods);
		
		
		HashMap<String, Object> variables = (HashMap<String, Object>) runtimeService.getVariables(task.getProcessInstanceId());
		variables.put("orderGoods", orderGoods);
		
		taskService.complete(task.getId(), variables);
		
	}
	
	@PreAuthorize("hasRole('ROLE_USER')")
	@GetMapping("/change")
	public ResponseEntity changeOrder(@RequestParam(value = "order",required=true) Long Order,
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

}
