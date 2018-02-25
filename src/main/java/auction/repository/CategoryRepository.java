package auction.repository;

import org.springframework.data.repository.CrudRepository;

import auction.model.Category;

public interface CategoryRepository extends CrudRepository<Category, Long>{

}
