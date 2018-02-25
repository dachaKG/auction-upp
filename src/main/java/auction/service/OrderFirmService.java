package auction.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.activiti.engine.IdentityService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.Execution;
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
	
	@SuppressWarnings("unchecked")
	public FirmOrder sendFirmOrder(Long price, Date date, Firm firm, OrderGoods orderGoods, String executionId) {
		
		
		FirmOrder firmOrder = new FirmOrder();
		firmOrder.setDate(date);
		firmOrder.setPrice(price.intValue());
		firmOrder.setFirm(firm);
		firmOrder.setOrderGoods(orderGoods);
		firmOrder = firmOrderService.save(firmOrder);
		System.out.println("execution id " + executionId);
	//	Execution execution = runtimeService.createExecutionQuery().processInstanceId(executionId).singleResult();
		
		/*List<FirmOrder> firmOrderList = new ArrayList<FirmOrder>();
		HashMap<String, Object> variables = (HashMap<String, Object>) runtimeService.getVariables(executionId);
		//List<FirmOrder> firmOrderList = (List<FirmOrder>) variables.get("ordersFromFirm");
		if(variables.get("ordersFromFirm") == null) {
			firmOrderList = new ArrayList<FirmOrder>();
			firmOrderList.add(firmOrder);
			variables.put("ordersFromFirm", firmOrderList);
			runtimeService.setVariables(executionId, variables);
		} else {
			firmOrderList = (List<FirmOrder>) variables.get("ordersFromFirm");
			firmOrderList.add(firmOrder);
			variables.put("ordersFromFirm", firmOrderList);
			
			runtimeService.setVariables(executionId, variables);
			//runtimeService.vari
		}*/
		
		//variables.put("user", user);
		//System.out.println("duzina niza je " + firmOrderList.size());
		System.out.println("usao u send firm order");
		return firmOrder;
		
	}

}
