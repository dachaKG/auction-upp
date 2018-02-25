package auction.dto;

import java.io.Serializable;
import java.util.Date;

public class OrderGoodsDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long category;

	private String description;

	private int estimatedValue;

	private Date receiveDeadline;

	private int expectedBids;

	private Date serviceDeadline;

	public Long getCategory() {
		return category;
	}

	public void setCategory(Long category) {
		this.category = category;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getEstimatedValue() {
		return estimatedValue;
	}

	public void setEstimatedValue(int estimatedValue) {
		this.estimatedValue = estimatedValue;
	}

	public Date getReceiveDeadline() {
		return receiveDeadline;
	}

	public void setReceiveDeadline(Date receiveDeadline) {
		this.receiveDeadline = receiveDeadline;
	}

	public int getExpectedBids() {
		return expectedBids;
	}

	public void setExpectedBids(int expectedBids) {
		this.expectedBids = expectedBids;
	}

	public Date getServiceDeadline() {
		return serviceDeadline;
	}

	public void setServiceDeadline(Date serviceDeadline) {
		this.serviceDeadline = serviceDeadline;
	}

}
