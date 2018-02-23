package auction.order;

import java.util.Date;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import auction.category.CategoryService;
import auction.firm.Firm;
import auction.firm.FirmService;
import auction.user.User;
import auction.user.UserService;

@Component
public class OrderService {
	
	@Autowired
	RuntimeService runtimeService;
	
	@Autowired
	TaskService taskService;
	
	@Autowired
	RepositoryService repositoryService;
	
	@Autowired
	FirmService firmService;
	
	@Autowired
	OrderGoodsService orderGoodService;
	
	@Autowired
	CategoryService categoryService;
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	private UserService userService;
	

	public OrderObjectDTO sendOrder(Long category, String description, Long estimatedValue, Date receiveDeadline, Long expectedBids, Date serviceDeadline) {
		SecurityContext context = SecurityContextHolder.getContext();
		Authentication authentication = context.getAuthentication();
		User user = userService.findOneByUsername(authentication.getName());
		OrderGoods orderGoods = new OrderGoods();
		orderGoods.setDescription(description);
		orderGoods.setEstimatedValue(estimatedValue.intValue());
		orderGoods.setExpectedBids(expectedBids.intValue());
		orderGoods.setReceiveDeadline(receiveDeadline);
		orderGoods.setServiceDeadline(serviceDeadline);
		orderGoods.setCategory(categoryService.findOne(category));
		orderGoods.setUser(user);
		orderGoodService.save(orderGoods);
		List<Firm> firmList = firmService.findByCategory(orderGoods.getCategory());
		if(firmList.size() == expectedBids.intValue()) {
			for(Firm firm : firmList) {
				
				List<OrderGoods> orderList = firm.getOrderGoods();
				orderList.add(orderGoods);
				firm.setOrderGoods(orderList);
				System.out.println("Lista ordera " + firm.getOrderGoods().size());
				firmService.save(firm);
}
		}
		OrderObjectDTO orderDTO = new OrderObjectDTO();
		orderDTO.setFirms(firmList);
		orderDTO.setOrderGoods(orderGoods);
		return orderDTO;
	}
	
	public void cancelOrder(OrderGoods order) {
		try {
			System.out.println(orderGoodService.findAll().size());
			orderGoodService.delete(order.getId());
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
			System.out.println("poslat mail");
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public List<Firm> prepareOrderList(OrderObjectDTO order){
		System.out.println("prepare order lis");
		return order.getFirms();
	}
	
	public void sendToFirms(Firm firm, String executionId) {
		System.out.println("id firme " + firm.getName());
		System.out.println("ex " + executionId);
		User user = firm.getUsers().get(0);
		TaskQuery task = taskService.createTaskQuery().active().taskCandidateOrAssigned(user.getUsername());
		TaskQuery task1 = taskService.createTaskQuery().taskAssignee("manager")
		
		System.out.println(task.taskCandidateOrAssigned(user.getUsername()).count());
		System.out.println("send to firms");
	}
	
	public void baki() {
		System.out.println("Baki ");
	}
	
}
