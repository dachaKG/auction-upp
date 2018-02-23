package auction.user;

import org.springframework.data.repository.PagingAndSortingRepository;

import auction.firm.Firm;

public interface UserRepository extends PagingAndSortingRepository<User, Long> {
	
	public User findOneByUsername(String username);

	public User findOneByConfirmationMail(String confirmationMail);
	
	public User findOneByFirm(Firm firm);
}
