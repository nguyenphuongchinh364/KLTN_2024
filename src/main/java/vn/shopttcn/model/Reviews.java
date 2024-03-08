package vn.shopttcn.model;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


public class Reviews {
	private int reviewsId;
	private User user;
	private Product product;
	private String reviewsContent;
	private int reviewsRating; // 1 2 3 4 5 (sao)
	private int reviewsStatus; // trạng thái (1: hiển thị, 0: ẩn)
	private Timestamp createAt;
	private Timestamp updateAt;

	// update status
	public Reviews(int reviewsId, int reviewsStatus) {
		super();
		this.reviewsId = reviewsId;
		this.reviewsStatus = reviewsStatus;
	}

	public Reviews(User user, Product product, String reviewsContent, int reviewsRating) {
		super();
		this.user = user;
		this.product = product;
		this.reviewsContent = reviewsContent;
		this.reviewsRating = reviewsRating;
	}

	public int getReviewsId() {
		return reviewsId;
	}

	public void setReviewsId(int reviewsId) {
		this.reviewsId = reviewsId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public String getReviewsContent() {
		return reviewsContent;
	}

	public void setReviewsContent(String reviewsContent) {
		this.reviewsContent = reviewsContent;
	}

	public int getReviewsRating() {
		return reviewsRating;
	}

	public void setReviewsRating(int reviewsRating) {
		this.reviewsRating = reviewsRating;
	}

	public int getReviewsStatus() {
		return reviewsStatus;
	}

	public void setReviewsStatus(int reviewsStatus) {
		this.reviewsStatus = reviewsStatus;
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
	public Reviews() {
		// TODO Auto-generated constructor stub
	}

	public Reviews(int reviewsId, User user, Product product, String reviewsContent, int reviewsRating,
			int reviewsStatus, Timestamp createAt, Timestamp updateAt) {
		super();
		this.reviewsId = reviewsId;
		this.user = user;
		this.product = product;
		this.reviewsContent = reviewsContent;
		this.reviewsRating = reviewsRating;
		this.reviewsStatus = reviewsStatus;
		this.createAt = createAt;
		this.updateAt = updateAt;
	}

}
