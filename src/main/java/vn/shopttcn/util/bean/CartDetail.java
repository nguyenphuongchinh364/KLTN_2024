package vn.shopttcn.util.bean;



import vn.shopttcn.model.Product;


public class CartDetail {
	public CartDetail() {
	// TODO Auto-generated constructor stub
}
	
	public CartDetail(Product product, int quantity) {
		super();
		this.product = product;
		this.quantity = quantity;
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

	private Product product;
	private int quantity;

}
