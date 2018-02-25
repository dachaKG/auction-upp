package auction.repository;

import org.springframework.data.repository.CrudRepository;

import auction.model.FirmOrder;

public interface FirmOrderRepository extends CrudRepository<FirmOrder, Long>{

}
