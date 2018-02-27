package auction.service;

import java.util.Date;
import java.util.List;

import org.activiti.engine.IdentityService;
import org.activiti.engine.RuntimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import auction.model.Firm;
import auction.model.FirmOrder;
import auction.model.OrderGoods;
import auction.spring.service.FirmOrderService;
import auction.user.User;

@Component
public class OrderFirmService {
	
	@Autowired
	IdentityService identityService;
	
	@Autowired
	RuntimeService runtimeService;
	
	@Autowired
	FirmOrderService firmOrderService;
	
	public String sendToFirms(Firm firm, String executionId) {
		System.out.println("id firme " + firm.getName());
		System.out.println("ex " + executionId);
		User user = firm.getUsers().get(0);
		org.activiti.engine.identity.User actUser = identityService.createUserQuery().userId(user.getUsername()).singleResult();
		
		System.out.println("send to firms");
		return actUser.getId();
	}
	
	public FirmOrder sendFirmOrder(Long price, Date date, Firm firm, OrderGoods orderGoods, String executionId) {
	
		FirmOrder firmOrder = new FirmOrder();
		firmOrder.setDate(date);
		firmOrder.setPrice(price.intValue());
		firmOrder.setFirm(firm);
		firmOrder.setOrderGoods(orderGoods);
		firmOrder = firmOrderService.save(firmOrder);
		System.out.println("execution id " + executionId);
		System.out.println("usao u send firm order");
		return firmOrder;
		
	}
	
	public String checkOrder(String decision) {
		
		System.out.println("Usao u checkOrder " + decision);
		
		return null;
	}
	
	public void checkFirmList(List<FirmOrder> orderList) {
		System.out.println("checkFirmList velicina liste " + orderList.size());
	}
	
	public List<FirmOrder> checkFirmOrderList(List<FirmOrder> orderList, List<Firm> firms){
		System.out.println("checkFirmList order velicina liste " + orderList.size());
		System.out.println("lista firmi " + firms.size());
		return orderList;
	}

}
