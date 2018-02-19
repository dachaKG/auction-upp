package auction.firm;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;

import auction.category.Category;
import auction.order.OrderGoods;
import auction.user.User;

@Entity
public class Firm {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	private String name;

	@NotNull
	private Integer distance;

	@JsonIgnore
	@OneToMany(mappedBy = "firm", cascade = CascadeType.ALL)
	private List<User> users = new ArrayList<User>();
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "firm_order_goods", joinColumns = @JoinColumn(name = "firm_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "order_goods_id", referencedColumnName = "id"))
	private List<OrderGoods> orderGoods = new ArrayList<OrderGoods>();

	@ManyToOne
	private Category category;

	public Firm() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Integer getDistance() {
		return distance;
	}

	public void setDistance(Integer distance) {
		this.distance = distance;
	}

	public List<OrderGoods> getOrderGoods() {
		return orderGoods;
	}

	public void setOrderGoods(List<OrderGoods> orderGoods) {
		this.orderGoods = orderGoods;
	}
	
	

}
