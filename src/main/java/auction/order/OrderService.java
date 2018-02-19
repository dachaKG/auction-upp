package auction.order;

import java.util.HashMap;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import auction.firm.Firm;
import auction.firm.FirmService;

@Component
public class OrderService {
	
	@Autowired
	RuntimeService runtimeService;
	
	@Autowired
	TaskService taskService;
	
	@Autowired
	FirmService firmService;
	
	@Autowired
	OrderGoodsService orderGoodService;
	
	@Autowired
	private JavaMailSender mailSender;
	

	public void sendOrder(OrderGoods order, String taskId) {
		//Task task = taskService.createTaskQuery().active().taskId(taskId).singleResult();
		List<Firm> firmList = firmService.findByCategory(order.getCategory());
		if(firmList.isEmpty()) {
			//Execution execution = runtimeService.createExecutionQuery().processInstanceId(taskId).singleResult();
			//execution.getProcessInstanceId();
			//execution.
			//Task task = taskService.createTaskQuery().active().processInstanceId(execution.getProcessInstanceId()).singleResult();
			HashMap<String, Object> variables = (HashMap<String, Object>) runtimeService.getVariables(taskId);
			variables.put("firms", "empty");
			runtimeService.setVariables(taskId, variables);
			//return;
		} else if (firmList.size() < order.getExpectedBids()) {
			HashMap<String, Object> variables = (HashMap<String, Object>) runtimeService.getVariables(taskId);
			variables.put("firms", "not enough firms");
			runtimeService.setVariables(taskId, variables);
			
			
		} else {
			for(Firm firm : firmList) {
		
				List<OrderGoods> orderList = firm.getOrderGoods();
				orderList.add(order);
				firm.setOrderGoods(orderList);
				System.out.println("Lista ordera " + firm.getOrderGoods().size());
				firmService.save(firm);
			}
		}
		System.out.println("lista " + firmList.size());
		
		System.out.println("usao u send order");
		System.out.println("task " + taskId);
	}
	
	public void cancelOrder(OrderGoods orderGoods) {
		try {
			System.out.println(orderGoodService.findAll().size());
			orderGoodService.delete(orderGoods.getId());
			System.out.println("Cancel order");
			System.out.println(orderGoodService.findAll().size());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void changeOrder(OrderGoods order) {
		System.out.println("change order");
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper;
		try {
			helper = new MimeMessageHelper(message, true);

			helper.setFrom("uppauctionmain@gmail.com");
			helper.setTo(order.getUser().getEmail());
			helper.setSubject("Nedovoljan broj firmi");
			helper.setText("Postovani korisnice nemoguce je dostici vas ocekivani broj ponuda od " + order.getExpectedBids()
					+ ", koliko ste naveli u prijavi zahteva. Molimo Vas da kliknete na link ispod kako bi bili u mogucnosti da promenite broj ponuda \n\n"
					+ ""
					+ "http://localhost:4200/user/change?order="+order.getId());
			mailSender.send(message);
			/*Execution execution = runtimeService.createExecutionQuery().processInstanceId(taskId).singleResult();
			Task task = taskService.createTaskQuery().active().processInstanceId(execution.getProcessInstanceId()).singleResult();
			System.out.println("task name " + task.getName());*/
			System.out.println("poslat mail");
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
