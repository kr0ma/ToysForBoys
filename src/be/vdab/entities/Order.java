package be.vdab.entities;

import java.util.Date;
import java.util.List;

public class Order {	
	public enum Statussen {CANCELLED,DISPUTED,PROCESSING,RESOLVED,SHIPPED,WAITING}
	
	private int id ;
	private Date orderDate, requiredDate, shippedDate;
	private String comments;
	private Customer customer;
	private Statussen status;
	
	private List<OrderDetail> orderDetails;	
	
	public Order(int id,Date orderDate, Date requiredDate,
			Date shippedDate, String comments, Customer customer, Statussen status) {
		this.id = id;
		this.customer = customer;
		this.orderDate = orderDate;
		this.requiredDate = requiredDate;
		this.shippedDate = shippedDate;
		this.comments = comments;
		this.status = status;
	}
	
	public Order(int id,Date orderDate, Date requiredDate,
			Date shippedDate, String comments, Customer customer, Statussen status, List<OrderDetail> orderDetails) {
		this(id, orderDate, requiredDate, shippedDate, comments, customer, status);
		this.orderDetails = orderDetails;		
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
	public Date getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}
	
	public Date getRequiredDate() {
		return requiredDate;
	}
	public void setRequiredDate(Date requiredDate) {
		this.requiredDate = requiredDate;
	}
	
	public Date getShippedDate() {
		return shippedDate;
	}
	public void setShippedDate(Date shippedDate) {
		this.shippedDate = shippedDate;
	}
	
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	
	public String getStatus() {
		return status.toString();
	}
	public void setStatus(Statussen status) {
		this.status = status;
	}

	public List<OrderDetail> getOrderDetails() {
		return orderDetails;
	}
	
	
	
	
}
