package vn.shopttcn.util.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.shopttcn.model.Product;


public class OrderManageDetail {
	private Product product;
	private int quantity;
	private int price;
	public OrderManageDetail() {
		// TODO Auto-generated constructor stub
	}
	public OrderManageDetail(Product product, int quantity, int price) {
		super();
		this.product = product;
		this.quantity = quantity;
		this.price = price;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	

}
