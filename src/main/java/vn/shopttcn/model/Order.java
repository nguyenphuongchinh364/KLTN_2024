package vn.shopttcn.model;

import java.sql.Timestamp;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


public class Order {
	private int orderId;
	private int orderTotalQuantity; // tổng số lượng
	private int orderTotalPrice; // tổng tiền
	private int userId; // user đặt hàng
	
	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public int getOrderTotalQuantity() {
		return orderTotalQuantity;
	}

	public void setOrderTotalQuantity(int orderTotalQuantity) {
		this.orderTotalQuantity = orderTotalQuantity;
	}

	public int getOrderTotalPrice() {
		return orderTotalPrice;
	}

	public void setOrderTotalPrice(int orderTotalPrice) {
		this.orderTotalPrice = orderTotalPrice;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getOrderName() {
		return orderName;
	}

	public void setOrderName(String orderName) {
		this.orderName = orderName;
	}

	public String getOrderEmail() {
		return orderEmail;
	}

	public void setOrderEmail(String orderEmail) {
		this.orderEmail = orderEmail;
	}

	public String getOrderPhone() {
		return orderPhone;
	}

	public void setOrderPhone(String orderPhone) {
		this.orderPhone = orderPhone;
	}

	public String getOrderAddress() {
		return orderAddress;
	}

	public void setOrderAddress(String orderAddress) {
		this.orderAddress = orderAddress;
	}

	public String getOrderNote() {
		return orderNote;
	}

	public void setOrderNote(String orderNote) {
		this.orderNote = orderNote;
	}

	public int getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(int orderStatus) {
		this.orderStatus = orderStatus;
	}

	public int getOrderPayment() {
		return orderPayment;
	}

	public void setOrderPayment(int orderPayment) {
		this.orderPayment = orderPayment;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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

	@NotEmpty
	private String orderName;
	
	@NotEmpty
	@Email
	private String orderEmail;
	
	@NotEmpty
	private String orderPhone;
	
	private String orderAddress;
	private String orderNote;
	private int orderStatus; // trạng thái đơn hàng (do nhân viên xử lý)
	private int orderPayment; // trạng thái thanh toán (1: đã thanh toán, 0: chưa thanh toán)
	private User user; // nhân viên xử lý đơn hàng
	private Timestamp createAt;
	private Timestamp updateAt;

	public Order(String orderName, String orderEmail, String orderPhone) {
		super();
		this.orderName = orderName;
		this.orderEmail = orderEmail;
		this.orderPhone = orderPhone;
	}

	public Order(int orderId, int orderTotalQuantity, int orderTotalPrice, int userId, @NotEmpty String orderName,
			@NotEmpty @Email String orderEmail, @NotEmpty String orderPhone, String orderAddress, String orderNote,
			int orderStatus, int orderPayment, User user, Timestamp createAt, Timestamp updateAt) {
		super();
		this.orderId = orderId;
		this.orderTotalQuantity = orderTotalQuantity;
		this.orderTotalPrice = orderTotalPrice;
		this.userId = userId;
		this.orderName = orderName;
		this.orderEmail = orderEmail;
		this.orderPhone = orderPhone;
		this.orderAddress = orderAddress;
		this.orderNote = orderNote;
		this.orderStatus = orderStatus;
		this.orderPayment = orderPayment;
		this.user = user;
		this.createAt = createAt;
		this.updateAt = updateAt;
	}
	public Order() {
		
	}

}
