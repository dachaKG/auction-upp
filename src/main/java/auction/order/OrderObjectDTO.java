package auction.order;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import auction.firm.Firm;

public class OrderObjectDTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private OrderGoods orderGoods;
	
	private List<Firm> firms = new ArrayList<Firm>();

	public OrderObjectDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public OrderGoods getOrderGoods() {
		return orderGoods;
	}

	public void setOrderGoods(OrderGoods orderGoods) {
		this.orderGoods = orderGoods;
	}

	public List<Firm> getFirms() {
		return firms;
	}

	public void setFirms(List<Firm> firms) {
		this.firms = firms;
	}
	
	
	
	

}
