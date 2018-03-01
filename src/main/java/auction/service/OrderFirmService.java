package auction.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.activiti.engine.IdentityService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.identity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import auction.model.Firm;
import auction.model.FirmOrder;
import auction.model.OrderGoods;
import auction.spring.service.FirmOrderService;


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
		auction.user.User user = firm.getUsers().get(0);
		org.activiti.engine.identity.User actUser = identityService.createUserQuery().userId(user.getUsername()).singleResult();
		
		System.out.println("send to firms" + actUser.getId());
		return actUser.getId();
	}
	
	public String sendFirmOrder(Long price, Date date, Firm firm, OrderGoods orderGoods, String executionId, List<FirmOrder> ordersFromFirm, String username) {
		HashMap<String, Object> variables = (HashMap<String, Object>) runtimeService.getVariables(executionId);
		User user = identityService.createUserQuery().userId(username).singleResult();
		System.out.println(user.getId());
		int i = 0;
		for(FirmOrder firmOrder : ordersFromFirm) {
			if(firmOrder.getFirm().getId().equals(firm.getId())) {
				firmOrder.setDate(date);
				firmOrder.setPrice(price.intValue());
				firmOrder.setFirm(firm);
				firmOrder.setOrderGoods(orderGoods);
				firmOrder = firmOrderService.save(firmOrder);
				ordersFromFirm.set(i, firmOrder);
				System.out.println("izmenio ponudu");
				FirmOrder.order(ordersFromFirm);
				Long current = getFirmRank(firmOrder, ordersFromFirm);
				return current.toString();
			}
			i++;
		}
			
			
		FirmOrder firmOrder = new FirmOrder();
		firmOrder.setDate(date);
		firmOrder.setPrice(price.intValue());
		firmOrder.setFirm(firm);
		firmOrder.setOrderGoods(orderGoods);
		firmOrder = firmOrderService.save(firmOrder);
		System.out.println("execution id " + executionId);
		System.out.println("usao u send firm order");
		ordersFromFirm.add(firmOrder);
		FirmOrder.order(ordersFromFirm);
		Long current = getFirmRank(firmOrder, ordersFromFirm);
		return current.toString();
		
	}
	
	private Long getFirmRank(FirmOrder firmOrder, List<FirmOrder> ordersFromFirm) {
		// TODO Auto-generated method stub
		long current = 0 ; 
		for( int i = 0 ; i < ordersFromFirm.size(); i++) {
			if(ordersFromFirm.get(i).getId() == firmOrder.getId())
				current = i + 1;
		}
		return current;
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
