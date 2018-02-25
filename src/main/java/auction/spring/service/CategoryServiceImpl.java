package auction.spring.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import auction.model.Category;
import auction.repository.CategoryRepository;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {
	
	@Autowired
	CategoryRepository repository;

	@Override
	public List<Category> findAll() {
		return (List<Category>) repository.findAll();
	}

	@Override
	public Category findOne(Long id) {
		return repository.findOne(id);
	}

}
