package auction.spring.service;

import java.util.List;

import auction.model.Category;

public interface CategoryService {
	
	public List<Category> findAll();
	
	public Category findOne(Long id);

}
