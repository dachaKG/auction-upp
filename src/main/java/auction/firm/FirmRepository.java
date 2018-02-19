package auction.firm;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import auction.category.Category;

public interface FirmRepository extends CrudRepository<Firm, Long> {
	
	public List<Firm> findByCategory(Category category);

}
