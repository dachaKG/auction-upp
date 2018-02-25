package auction.spring.service;

import java.util.List;

import auction.model.Category;
import auction.model.Firm;

public interface FirmService {
	
	public List<Firm> findAll();
	
	public Firm save(Firm firm);
	
	public Firm findOne(Long id);
	
	public List<Firm> findByCategory(Category category);

}
