package auction.service;

import java.util.List;

import org.activiti.engine.RuntimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import auction.model.Firm;
import auction.model.FirmOrder;
import auction.spring.service.FirmService;
import auction.user.User;
import auction.user.UserService;

@Component
public class RankService {
	
	@Autowired
	RuntimeService runtimeService;
	
	@Autowired
	FirmService firmService;
	
	@Autowired
	UserService userService;

	public List<FirmOrder> rankOrders(List<FirmOrder> firmOrders, String executionId) {

		System.out.println("rank orders");
		FirmOrder.order(firmOrders);
		System.out.println(firmOrders.toString());
		
		return firmOrders;
		
	}
	
	public void ranking(Long userRank, Long firmRank, Long firmId, String username) {
		Firm firm = firmService.findOne(firmId);
		List<Integer> firmRankList = firm.getRanks();
		firmRankList.add(firmRank.intValue());
		firm.setRanks(firmRankList);
		firmService.save(firm);
		User user = userService.findOneByUsername(username);
		List<Integer> userRankList = firm.getRanks();
		userRankList.add(userRank.intValue());
		user.setRanks(userRankList);
		userService.save(user);
		
		//firm.getRanks().add(firmRank.intValue());
		
	}
	
}
