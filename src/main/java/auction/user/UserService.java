package auction.user;

import java.util.List;

public interface UserService {
	
	public List<User> findAll();
	
	public User save(User user);
	
	public User findOneByUsername(String username);
	
	public User findOneByConfirmationMail(String confirmationMail);

}
