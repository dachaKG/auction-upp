package auction.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import auction.model.Category;
import auction.model.Firm;

public interface FirmRepository extends CrudRepository<Firm, Long> {
	
	public List<Firm> findByCategory(Category category);

}
