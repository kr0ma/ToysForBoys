package be.vdab.entities;

import java.math.BigDecimal;

public class OrderDetail {
	private int orderID, quantityOrdered;
	private BigDecimal priceEach;
	private Product product;
	
	public OrderDetail(int orderID, Product product, int quantityOrdered,
			BigDecimal priceEach) {
		super();
		this.orderID = orderID;
		this.product = product;
		this.quantityOrdered = quantityOrdered;
		this.priceEach = priceEach;
	}
	
	public int getOrderID() {
		return orderID;
	}
	public void setOrderID(int orderID) {
		this.orderID = orderID;
	}
	
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	
	public int getQuantityOrdered() {
		return quantityOrdered;
	}
	public void setQuantityOrdered(int quantityOrdered) {
		this.quantityOrdered = quantityOrdered;
	}
	
	public BigDecimal getPriceEach() {
		return priceEach;
	}
	public void setPriceEach(BigDecimal priceEach) {
		this.priceEach = priceEach;
	}
	

}
