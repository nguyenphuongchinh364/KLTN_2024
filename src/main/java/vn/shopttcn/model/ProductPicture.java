package vn.shopttcn.model;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


public class ProductPicture {
	private int pictureId;
	private int productId;
	private String pictureName;
	private Timestamp createAt;
	private Timestamp updateAt;

	public ProductPicture(int productId, String pictureName) {
		super();
		this.productId = productId;
		this.pictureName = pictureName;
	}

	public ProductPicture(int productId) {
		super();
		this.productId = productId;
	}

	public ProductPicture(String pictureName) {
		super();
		this.pictureName = pictureName;
	}

	public int getPictureId() {
		return pictureId;
	}

	public void setPictureId(int pictureId) {
		this.pictureId = pictureId;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public String getPictureName() {
		return pictureName;
	}

	public void setPictureName(String pictureName) {
		this.pictureName = pictureName;
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
	
	public ProductPicture(int pictureId, int productId, String pictureName, Timestamp createAt, Timestamp updateAt) {
		super();
		this.pictureId = pictureId;
		this.productId = productId;
		this.pictureName = pictureName;
		this.createAt = createAt;
		this.updateAt = updateAt;
	}

	public ProductPicture() {
		// TODO Auto-generated constructor stub
	}

}
