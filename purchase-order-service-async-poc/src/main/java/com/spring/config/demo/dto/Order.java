package com.spring.config.demo.dto;

public class Order {
	int productId;
	String name;
	String productType;
	int qty;
	double price;
	String trackingId;
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	public int getQty() {
		return qty;
	}
	public void setQty(int qty) {
		this.qty = qty;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getTrackingId() {
		return trackingId;
	}
	public void setTrackingId(String trackingId) {
		this.trackingId = trackingId;
	}
	public Order(int productId, String name, String productType, int qty, double price, String trackingId) {
		super();
		this.productId = productId;
		this.name = name;
		this.productType = productType;
		this.qty = qty;
		this.price = price;
		this.trackingId = trackingId;
	}
	
	
	
}
