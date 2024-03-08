package vn.shopttcn.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


public class OrderDetail {
	
	public int getOrderDetailId() {
		return orderDetailId;
	}

	public void setOrderDetailId(int orderDetailId) {
		this.orderDetailId = orderDetailId;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public String getOrderProductName() {
		return orderProductName;
	}

	public void setOrderProductName(String orderProductName) {
		this.orderProductName = orderProductName;
	}

	public int getOrderProductPrice() {
		return orderProductPrice;
	}

	public void setOrderProductPrice(int orderProductPrice) {
		this.orderProductPrice = orderProductPrice;
	}

	public int getOrderProductQuantity() {
		return orderProductQuantity;
	}

	public void setOrderProductQuantity(int orderProductQuantity) {
		this.orderProductQuantity = orderProductQuantity;
	}

	private int orderDetailId;
	private int orderId;
	private int productId;
	private String orderProductName;
	private int orderProductPrice;
	private int orderProductQuantity;

	public OrderDetail(int orderId, int productId, String orderProductName, int orderProductPrice,
			int orderProductQuantity) {
		super();
		this.orderId = orderId;
		this.productId = productId;
		this.orderProductName = orderProductName;
		this.orderProductPrice = orderProductPrice;
		this.orderProductQuantity = orderProductQuantity;
	}

	public OrderDetail(int orderDetailId, int orderId, int productId, String orderProductName, int orderProductPrice,
			int orderProductQuantity) {
		super();
		this.orderDetailId = orderDetailId;
		this.orderId = orderId;
		this.productId = productId;
		this.orderProductName = orderProductName;
		this.orderProductPrice = orderProductPrice;
		this.orderProductQuantity = orderProductQuantity;
	}
	public OrderDetail() {
		
	}

}
