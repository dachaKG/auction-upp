package auction.order;

import java.util.List;

public interface OrderGoodsService {
	
	public List<OrderGoods> findAll();
	
	public OrderGoods save(OrderGoods orderGoods);
	
	public OrderGoods findOne(Long id);
	
	public void delete(Long id);

}
