package auction.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import auction.model.Firm;
import auction.spring.service.FirmService;

@Component
public class ExplanationService {
	
	@Autowired
	FirmService firmService;
	
	public String sendToFirm(Long firmId) {
		
		Firm firm = firmService.findOne(firmId);
		return firm.getUsers().get(0).getUsername();
	}
	
	public String sendToInitiator(String explanation) {
		//TO DO: posalji mail initiatoru;
		System.out.println("send to initiator");
		return null;
	}
	
	public String acceptBid() {
		System.out.println("Usao u accept bid");
		return "yes";
	}
	

}
