package auction.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.activiti.engine.IdentityService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import auction.dto.OrderObjectDTO;
import auction.model.Firm;
import auction.model.FirmOrder;
import auction.model.OrderGoods;
import auction.spring.service.CategoryService;
import auction.spring.service.FirmService;
import auction.spring.service.OrderGoodsService;
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

	@Autowired
	IdentityService identityService;

	public OrderObjectDTO sendOrder(Long category, String description, Long estimatedValue, Date receiveDeadline, Long expectedBids, Date serviceDeadline, String executionId) {
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
		List<Firm> allFirmLists = firmService.findByCategory(orderGoods.getCategory());
		
		Collections.sort(allFirmLists, new Comparator<Firm>() {
		    @Override
		    public int compare(Firm f1, Firm f2) {
		        return Double.compare(f2.getAvgRank(), f1.getAvgRank());
		    }
		});
		List<Firm> allFirmList = new ArrayList<Firm>();
		for(Firm firm : allFirmLists) {
			if(firm.getDistance()>distance(firm.getUsers().get(0).getLatitude(), user.getLatitude(), firm.getUsers().get(0).getLongitude(), user.getLongitude(), 0, 0))
				allFirmList.add(firm);
		}
		List<Firm> firmList = new ArrayList<Firm>();
		int i = 0; 
		if(allFirmList.size() >= expectedBids.intValue()) {
			for(Firm firm : allFirmList) {
				if(i < expectedBids.intValue()) {
					List<OrderGoods> orderList = firm.getOrderGoods();
					orderList.add(orderGoods);
					firm.setOrderGoods(orderList);
					System.out.println("Lista ordera " + firm.getOrderGoods().size());
					firm = firmService.save(firm);
					firmList.add(firm);
					i++;
				} else {
					break;
				}
			}
		} else {
			OrderObjectDTO orderDTO = new OrderObjectDTO();
			orderDTO.setFirms(allFirmList);
			orderDTO.setOrderGoods(orderGoods);
			HashMap<String, Object> variables = (HashMap<String, Object>) runtimeService.getVariables(executionId);
			List<FirmOrder> firmOrderList = new ArrayList<FirmOrder>();
			//firmOrderList.add(firmOrder);
			variables.put("ordersFromFirm", firmOrderList);
			variables.put("numberOfCancel", 0);
			runtimeService.setVariables(executionId, variables);
			return orderDTO;
		}
		OrderObjectDTO orderDTO = new OrderObjectDTO();
		orderDTO.setFirms(firmList);
		orderDTO.setOrderGoods(orderGoods);
		HashMap<String, Object> variables = (HashMap<String, Object>) runtimeService.getVariables(executionId);
		List<FirmOrder> firmOrderList = new ArrayList<FirmOrder>();
		//firmOrderList.add(firmOrder);
		variables.put("ordersFromFirm", firmOrderList);
		variables.put("numberOfCancel", 0);
		runtimeService.setVariables(executionId, variables);
		return orderDTO;
	}

	public void cancelOrder(OrderGoods order) {
		try {
			System.out.println(orderGoodService.findAll().size());
			// orderGoodService.delete(order.getId());
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
			helper.setText("Postovani korisnice nemoguce je dostici vas ocekivani broj ponuda od "
					+ order.getExpectedBids()
					+ ", koliko ste naveli u prijavi zahteva. Molimo Vas da kliknete na link ispod kako bi bili u mogucnosti da promenite broj ponuda \n\n"
					+ "" + "http://localhost:4200/user/change?order=" + order.getId());
			mailSender.send(message);
			System.out.println("poslat mail");
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public List<Firm> prepareOrderList(OrderObjectDTO order) {

		System.out.println("prepare order list ");
		return order.getFirms();
	}

	public void sendLessOrders(OrderObjectDTO order) {
		System.out.println("broj firmi less orders " + order.getFirms().size());
	}

	public void baki(List<FirmOrder> firmOrderList) {
		System.out.println("Baki ");
	}
	
	public static double distance(double lat1, double lat2, double lon1,
	        double lon2, double el1, double el2) {

	    final int R = 6371; // Radius of the earth

	    double latDistance = Math.toRadians(lat2 - lat1);
	    double lonDistance = Math.toRadians(lon2 - lon1);
	    double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
	            + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
	            * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
	    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
	    double distance = R * c * 1000; // convert to meters

	    double height = el1 - el2;

	    distance = Math.pow(distance, 2) + Math.pow(height, 2);

	    return Math.sqrt(distance);
	}

}
