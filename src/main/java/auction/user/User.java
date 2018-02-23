package auction.user;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;

import auction.firm.Firm;
import auction.order.OrderGoods;

@Entity
public class User implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true)
	@NotBlank
	private String username;

	@NotBlank
	private String password;

	@Column(unique = true)
	@NotBlank
	@Email
	private String email;

	@NotBlank
	private String firstName;

	@NotBlank
	private String lastName;

	@NotBlank
	private String address;

	@NotBlank
	private String city;

	@NotBlank
	private String zipCode;

	@Enumerated(EnumType.STRING)
	private EnumRole role;
	
	@Column(nullable=true, unique=true)
	private String confirmationMail;
	
	private boolean confirmed;
	
	@JsonIgnore
	@OneToMany(mappedBy="user")
	private List<OrderGoods> orderGoods = new ArrayList<OrderGoods>();

	/*
	 * @ManyToMany(fetch = FetchType.EAGER)
	 * 
	 * @JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id",
	 * referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name =
	 * "role_id", referencedColumnName = "id")) private List<Role> roles;
	 */

	@ManyToOne
	private Firm firm;

	public User() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public Firm getFirm() {
		return firm;
	}

	public void setFirm(Firm firm) {
		this.firm = firm;
	}

	public EnumRole getRole() {
		return role;
	}

	public void setRole(EnumRole role) {
		this.role = role;
	}

	public String getConfirmationMail() {
		return confirmationMail;
	}

	public void setConfirmationMail(String confirmationMail) {
		this.confirmationMail = confirmationMail;
	}

	public boolean isConfirmed() {
		return confirmed;
	}

	public void setConfirmed(boolean confirmed) {
		this.confirmed = confirmed;
	}

	public List<OrderGoods> getOrderGoods() {
		return orderGoods;
	}

	public void setOrderGoods(List<OrderGoods> orderGoods) {
		this.orderGoods = orderGoods;
	}

}
