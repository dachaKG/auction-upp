package auction.spring.service;

import java.util.List;

import auction.model.OrderGoods;

public interface OrderGoodsService {
	
	public List<OrderGoods> findAll();
	
	public OrderGoods save(OrderGoods orderGoods);
	
	public OrderGoods findOne(Long id);
	
	public void delete(Long id);

}
