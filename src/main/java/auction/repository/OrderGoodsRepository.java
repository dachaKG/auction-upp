package auction.repository;

import org.springframework.data.repository.CrudRepository;

import auction.model.OrderGoods;

public interface OrderGoodsRepository extends CrudRepository<OrderGoods, Long>{

}
