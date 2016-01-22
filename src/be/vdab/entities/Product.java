package be.vdab.entities;

import java.math.BigDecimal;

public class Product {
	private int id, quantityInStock, quantityInOrder, productlineID;
	private String name, scale, description;
	private BigDecimal buyPrice;	
	
	public Product(int id, String name, String scale, String description, int quantityInStock, int quantityInOrder,
			BigDecimal buyPrice, int productlineID) {
		this.id = id;
		this.quantityInStock = quantityInStock;
		this.quantityInOrder = quantityInOrder;
		this.productlineID = productlineID;
		this.name = name;
		this.scale = scale;
		this.description = description;
		this.buyPrice = buyPrice;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public int getQuantityInStock() {
		return quantityInStock;
	}
	public void setQuantityInStock(int quantityInStock) {
		this.quantityInStock = quantityInStock;
	}
	
	public int getQuantityInOrder() {
		return quantityInOrder;
	}
	public void setQuantityInOrder(int quantityInOrder) {
		this.quantityInOrder = quantityInOrder;
	}
	
	public int getProductlineID() {
		return productlineID;
	}
	public void setProductlineID(int productlineID) {
		this.productlineID = productlineID;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getScale() {
		return scale;
	}
	public void setScale(String scale) {
		this.scale = scale;
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public BigDecimal getBuyPrice() {
		return buyPrice;
	}
	public void setBuyPrice(BigDecimal buyPrice) {
		this.buyPrice = buyPrice;
	}
	
	
	
}
