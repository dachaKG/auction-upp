package auction.firm;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import auction.config.TokenUtils;
import auction.order.OrderGoods;
import auction.user.User;
import auction.user.UserService;

@RestController
@RequestMapping(value = "/firm")
@CrossOrigin(origins = "http://localhost:4200")
public class FirmController {

	@Autowired
	private TokenUtils tokenUtils;
	
	@Autowired
	UserService userService;

	@PreAuthorize("hasRole('ROLE_FIRM')")
	@GetMapping
	public ResponseEntity<List<OrderGoods>> getOrderGoods() {

		SecurityContext context = SecurityContextHolder.getContext();
		Authentication authentication = context.getAuthentication();
		User user = userService.findOneByUsername(authentication.getName());
		Firm firm = user.getFirm();
		
		List<OrderGoods> orderGoods = firm.getOrderGoods();
		System.out.println("pronadjeni korisnik " + user.getUsername());
		System.out.println("Firma " + firm.getName());
		System.out.println("Lista ordera " + firm.getOrderGoods().size());
		// User user = authentication.getName();

		return new ResponseEntity<>(orderGoods, HttpStatus.OK);
	}

}
