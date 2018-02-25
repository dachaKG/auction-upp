package auction.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import auction.user.User;

@Entity
public class OrderGoods implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	private Category category;

	private String description;

	private Integer estimatedValue;

	private Date receiveDeadline;

	private Integer expectedBids;

	private Date serviceDeadline;
	
	@ManyToOne(cascade = CascadeType.DETACH)
	private User user;

	@JsonIgnore
	@ManyToMany(mappedBy = "orderGoods")
	private List<Firm> firm = new ArrayList<Firm>();

	public OrderGoods() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
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

	public List<Firm> getFirm() {
		return firm;
	}

	public void setFirm(List<Firm> firm) {
		this.firm = firm;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	

}
