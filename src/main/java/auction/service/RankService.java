package auction.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.activiti.engine.RuntimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import auction.model.FirmOrder;

@Component
public class RankService {
	
	@Autowired
	RuntimeService runtimeService;

	public List<FirmOrder> rankOrders(List<FirmOrder> firmOrders, String executionId) {
		
		
/*		
		HashMap<String, Object> variables = (HashMap<String, Object>) runtimeService.getVariables(executionId);
		List<FirmOrder> firmOrderList = new ArrayList<FirmOrder>();
		//firmOrderList.add(firmOrder);
		System.out.println("cancel execution " + executionId);
		//variables.put("ordersFromFirm", firmOrderList);
		Integer i = (Integer) variables.get("numberOfCancel");
		//System.out.println("broj otkazivanja " + i);
		runtimeService.setVariables(executionId, variables);
*/		
		
		
		
		
		//Collections.sort(firmOrders, FirmOrder.priceComparator);
		System.out.println("rank orders");
		FirmOrder.order(firmOrders);
		System.out.println(firmOrders.toString());
		
		return firmOrders;
		
	}
	
}
