package vn.shopttcn.model;

import java.sql.Timestamp;

import javax.validation.constraints.NotEmpty;

public class Category {
	private int catId;

	@NotEmpty
	private String catName;

	private String catSlug; // string hiển thị trên url
	private int catParentId; // danh mục cha
	private int deleteStatus;
	private Timestamp createAt;
	private Timestamp updateAt;

	public Category(int catId, String catName, String catSlug, int catParentId, Timestamp createAt,
			Timestamp updateAt) {
		super();
		this.catId = catId;
		this.catName = catName;
		this.catSlug = catSlug;
		this.catParentId = catParentId;
		this.createAt = createAt;
		this.updateAt = updateAt;
	}

	public int getCatId() {
		return catId;
	}

	public void setCatId(int catId) {
		this.catId = catId;
	}

	public String getCatName() {
		return catName;
	}

	public void setCatName(String catName) {
		this.catName = catName;
	}

	public String getCatSlug() {
		return catSlug;
	}

	public void setCatSlug(String catSlug) {
		this.catSlug = catSlug;
	}

	public int getCatParentId() {
		return catParentId;
	}

	public void setCatParentId(int catParentId) {
		this.catParentId = catParentId;
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

	public Category(int catId, @NotEmpty String catName, String catSlug, int catParentId, int deleteStatus,
			Timestamp createAt, Timestamp updateAt) {
		super();
		this.catId = catId;
		this.catName = catName;
		this.catSlug = catSlug;
		this.catParentId = catParentId;
		this.deleteStatus = deleteStatus;
		this.createAt = createAt;
		this.updateAt = updateAt;
	}

	public Category() {
	}

}
