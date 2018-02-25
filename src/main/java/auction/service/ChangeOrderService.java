package auction.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import auction.user.User;
import auction.user.UserService;

@Component
public class ChangeOrderService {
	
	@Autowired
	UserService userService;
	
	//${changeOrderService.noOrders(initiator)}
	
	public void noOrders(String username) {
		System.out.println("usao u no orders");
		User user = userService.findOneByUsername(username);
		System.out.println("korsnik " + username);
		
	}
	
	public void extendDeadline(String username) {
		System.out.println("usao u extend deadline");
		User user = userService.findOneByUsername(username);
		System.out.println("korsnik " + username);
	}
	

}
