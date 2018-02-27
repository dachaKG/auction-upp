package auction.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.activiti.engine.RuntimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import auction.model.Firm;
import auction.model.FirmOrder;
import auction.spring.service.FirmService;

@Component
public class AcceptOrderService {
	
	@Autowired
	FirmService firmService;
	
	@Autowired
	RuntimeService runtimeService;

	public String acceptOrder(Long orderId, Long firmId, String executionId) {
		
		Firm firm = firmService.findOne(firmId);
		
		HashMap<String, Object> variables = (HashMap<String, Object>) runtimeService.getVariables(executionId);
		List<FirmOrder> firmOrderList = new ArrayList<FirmOrder>();
		//firmOrderList.add(firmOrder);
		System.out.println("accept order " + executionId);
		variables.put("ordersFromFirm", firmOrderList);
		//int i = (int) variables.get("numberOfCancel");
		//System.out.println("broj otkazivanja " + i);
		runtimeService.setVariables(executionId, variables);
		
		return firm.getUsers().get(0).getUsername();
		
	}
	
}
