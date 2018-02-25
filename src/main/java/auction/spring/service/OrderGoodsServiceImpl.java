package auction.spring.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import auction.model.OrderGoods;
import auction.repository.OrderGoodsRepository;

@Service
@Transactional
public class OrderGoodsServiceImpl implements OrderGoodsService{
	
	@Autowired
	OrderGoodsRepository repository;

	@Override
	public OrderGoods save(OrderGoods orderGoods) {
		return repository.save(orderGoods);
	}

	@Override
	public OrderGoods findOne(Long id) {
		// TODO Auto-generated method stub
		return repository.findOne(id);
	}

	@Override
	public void delete(Long id) {
		repository.delete(id);
		
	}

	@Override
	public List<OrderGoods> findAll() {
		return (List<OrderGoods>) repository.findAll();
	}

}
