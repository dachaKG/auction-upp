package auction.spring.service;

import java.util.List;

import auction.model.FirmOrder;

public interface FirmOrderService {
	
	public List<FirmOrder> findAll();
	
	public FirmOrder save(FirmOrder firmOrder);

}
