package vn.shopttcn.model;

import java.sql.Timestamp;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


public class Product {
	private int productId;
	private Category cat;

	@NotEmpty
	private String productName;

	private String productSlug;
	private String productImage; // hình ảnh chính (hiển thị trang chủ, trang shop)

	@NotEmpty
	private String productDesc;

	@NotEmpty
	private String productDetail;

	@NotNull
	@Min(0)
	private Integer productPrice; // giá bán

	@NotNull
	@Min(0)
	private Integer productQuantity; // số lượng sản phẩm đang có

	private int productSold; // số lượng sản phẩm đã bán
	private int productView;
	private int deleteStatus;
	private Timestamp createAt;
	private Timestamp updateAt;

	public Product(int productId) {
		super();
		this.productId = productId;
	}

	public Product(int productId, String productName) {
		super();
		this.productId = productId;
		this.productName = productName;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public Category getCat() {
		return cat;
	}

	public void setCat(Category cat) {
		this.cat = cat;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductSlug() {
		return productSlug;
	}

	public void setProductSlug(String productSlug) {
		this.productSlug = productSlug;
	}

	public String getProductImage() {
		return productImage;
	}

	public void setProductImage(String productImage) {
		this.productImage = productImage;
	}

	public String getProductDesc() {
		return productDesc;
	}

	public void setProductDesc(String productDesc) {
		this.productDesc = productDesc;
	}

	public String getProductDetail() {
		return productDetail;
	}

	public void setProductDetail(String productDetail) {
		this.productDetail = productDetail;
	}

	public Integer getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(Integer productPrice) {
		this.productPrice = productPrice;
	}

	public Integer getProductQuantity() {
		return productQuantity;
	}

	public void setProductQuantity(Integer productQuantity) {
		this.productQuantity = productQuantity;
	}

	public int getProductSold() {
		return productSold;
	}

	public void setProductSold(int productSold) {
		this.productSold = productSold;
	}

	public int getProductView() {
		return productView;
	}

	public void setProductView(int productView) {
		this.productView = productView;
	}

	public int getDeleteStatus() {
		return deleteStatus;
	}

	public void setDeleteStatus(int deleteStatus) {
		this.deleteStatus = deleteStatus;
	}

	public Timestamp getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Timestamp createAt) {
		this.createAt = createAt;
	}

	public Timestamp getUpdateAt() {
		return updateAt;
	}

	public void setUpdateAt(Timestamp updateAt) {
		this.updateAt = updateAt;
	}

	public Product(int productId, Category cat, @NotEmpty String productName, String productSlug, String productImage,
			@NotEmpty String productDesc, @NotEmpty String productDetail, @NotNull @Min(0) Integer productPrice,
			@NotNull @Min(0) Integer productQuantity, int productSold, int productView, int deleteStatus,
			Timestamp createAt, Timestamp updateAt) {
		super();
		this.productId = productId;
		this.cat = cat;
		this.productName = productName;
		this.productSlug = productSlug;
		this.productImage = productImage;
		this.productDesc = productDesc;
		this.productDetail = productDetail;
		this.productPrice = productPrice;
		this.productQuantity = productQuantity;
		this.productSold = productSold;
		this.productView = productView;
		this.deleteStatus = deleteStatus;
		this.createAt = createAt;
		this.updateAt = updateAt;
	}
	public Product() {
		// TODO Auto-generated constructor stub
	}
	
	public Product(int int1, String string, String string2, String string3, String string4, String string5, int int2,
			int int3, int int4, int int5, int int6, Timestamp timestamp, Timestamp timestamp2) {
		
		this.productId = int1;
		this.productName = string;
		this.productSlug = string2;
		this.productImage = string3;
		this.productDesc = string4;
		this.productDetail = string5;
		this.productPrice = int2;
		this.productQuantity = int3;
		this.productSold = int4;
		this.productView = int5;
		this.deleteStatus = int6;
		this.createAt = timestamp;
		this.updateAt = timestamp2;
	}

}
