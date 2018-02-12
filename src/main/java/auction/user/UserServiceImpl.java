package auction.user;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UserServiceImpl implements UserService {
	
	@Autowired
	UserRepository repository;

	@Override
	public User findOneByUsername(String username) {
		
		return repository.findOneByUsername(username);
	}

	@Override
	public List<User> findAll() {
		return (List<User>) repository.findAll();
	}

	@Override
	public User save(User user) {
		return repository.save(user);
	}

	@Override
	public User findOneByConfirmationMail(String confirmationMail) {
		return repository.findOneByConfirmationMail(confirmationMail);
	}

}
