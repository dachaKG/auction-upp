package auction.spring.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import auction.model.FirmOrder;
import auction.repository.FirmOrderRepository;

@Service
@Transactional
public class FirmOrderServiceImpl implements FirmOrderService{
	
	@Autowired
	FirmOrderRepository repository;

	@Override
	public List<FirmOrder> findAll() {
		// TODO Auto-generated method stub
		return (List<FirmOrder>) repository.findAll();
	}

	@Override
	public FirmOrder save(FirmOrder firmOrder) {
		// TODO Auto-generated method stub
		return repository.save(firmOrder);
	}

}
