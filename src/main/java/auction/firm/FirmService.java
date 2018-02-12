package auction.firm;

import java.util.List;

public interface FirmService {
	
	public List<Firm> findAll();
	
	public Firm save(Firm firm);
	
	public Firm findOne(Long id);

}
