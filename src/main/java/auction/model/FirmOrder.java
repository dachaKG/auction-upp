package auction.model;

import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class FirmOrder implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private int price;

	private Date date;

	@ManyToOne
	private Firm firm;

	@ManyToOne
	private OrderGoods orderGoods;

	public FirmOrder() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Firm getFirm() {
		return firm;
	}

	public void setFirm(Firm firm) {
		this.firm = firm;
	}

	public OrderGoods getOrderGoods() {
		return orderGoods;
	}

	public void setOrderGoods(OrderGoods orderGoods) {
		this.orderGoods = orderGoods;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	/* Comparator for sorting the list by Student Name */
	public static Comparator<FirmOrder> priceComparator = new Comparator<FirmOrder>() {

		public int compare(FirmOrder f1, FirmOrder f2) {
			Integer price1 = f1.getPrice();
			Integer price2 = f2.getPrice();
			// ascending order
			return price1.compareTo(price2);

			// descending order
			// return StudentName2.compareTo(StudentName1);
		}
	};

	/* Comparator for sorting the list by roll no */
	public static Comparator<FirmOrder> dateComparator = new Comparator<FirmOrder>() {

		public int compare(FirmOrder f1, FirmOrder f2) {

			Date date1 = f1.getDate();
			Date date2 = f2.getDate();
			// int rollno1 = s1.getRollno();
			// int rollno2 = s2.getRollno();

			/* For ascending order */
			return date1.compareTo(date2);

			/* For descending order */
			// rollno2-rollno1;
		}
	};

	@SuppressWarnings("unchecked")
	public static void order(List<FirmOrder> firmOrders) {

		Collections.sort(firmOrders, new Comparator() {

			public int compare(Object o1, Object o2) {

				Integer x1 = ((FirmOrder) o1).getPrice();
				Integer x2 = ((FirmOrder) o2).getPrice();
				int sComp = x1.compareTo(x2);

				if (sComp != 0) {
					return sComp;
				} else {
					Date d1 = ((FirmOrder) o1).getDate();
					Date d2 = ((FirmOrder) o2).getDate();
					return d1.compareTo(d2);
				}
			}
		});
	}

}
