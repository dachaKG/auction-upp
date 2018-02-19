package auction.firm;

import java.util.List;

import auction.category.Category;

public interface FirmService {
	
	public List<Firm> findAll();
	
	public Firm save(Firm firm);
	
	public Firm findOne(Long id);
	
	public List<Firm> findByCategory(Category category);

}
