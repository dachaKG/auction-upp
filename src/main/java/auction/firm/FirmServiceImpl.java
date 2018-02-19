package auction.firm;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import auction.category.Category;

@Service
@Transactional
public class FirmServiceImpl implements FirmService {
	
	@Autowired
	FirmRepository repository;

	@Override
	public List<Firm> findAll() {
		return (List<Firm>) repository.findAll();
	}

	@Override
	public Firm save(Firm firm) {
		return repository.save(firm);
	}

	@Override
	public Firm findOne(Long id) {
		return repository.findOne(id);
	}

	@Override
	public List<Firm> findByCategory(Category category) {
		return repository.findByCategory(category);
	}

}
