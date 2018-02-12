package auction.category;

import java.util.List;

public interface CategoryService {
	
	public List<Category> findAll();
	
	public Category findOne(Long id);

}
