package auction.order;

import java.util.Date;

public class OrderGoodsDTO {

	private Long category;

	private String description;

	private Integer estimatedValue;

	private Date receiveDeadline;

	private Integer expectedBids;

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

	public Integer getEstimatedValue() {
		return estimatedValue;
	}

	public void setEstimatedValue(Integer estimatedValue) {
		this.estimatedValue = estimatedValue;
	}

	public Date getReceiveDeadline() {
		return receiveDeadline;
	}

	public void setReceiveDeadline(Date receiveDeadline) {
		this.receiveDeadline = receiveDeadline;
	}

	public Integer getExpectedBids() {
		return expectedBids;
	}

	public void setExpectedBids(Integer expectedBids) {
		this.expectedBids = expectedBids;
	}

	public Date getServiceDeadline() {
		return serviceDeadline;
	}

	public void setServiceDeadline(Date serviceDeadline) {
		this.serviceDeadline = serviceDeadline;
	}

}
