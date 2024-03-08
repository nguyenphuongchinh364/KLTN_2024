package vn.shopttcn.util.bean;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


public class OrderManage {
	public OrderManage() {
		// TODO Auto-generated constructor stub
	}
	public int getOrderId() {
		return orderId;
	}
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	public int getTotalQuantity() {
		return totalQuantity;
	}
	public void setTotalQuantity(int totalQuantity) {
		this.totalQuantity = totalQuantity;
	}
	public int getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(int totalPrice) {
		this.totalPrice = totalPrice;
	}
	public String getFullname() {
		return fullname;
	}
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getCreateAt() {
		return createAt;
	}
	public void setCreateAt(String createAt) {
		this.createAt = createAt;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public List<OrderManageDetail> getList() {
		return list;
	}
	public void setList(List<OrderManageDetail> list) {
		this.list = list;
	}
	public OrderManage(int orderId, int totalQuantity, int totalPrice, String fullname, String phone, String email,
			String address, String note, String createAt, int status, List<OrderManageDetail> list) {
		super();
		this.orderId = orderId;
		this.totalQuantity = totalQuantity;
		this.totalPrice = totalPrice;
		this.fullname = fullname;
		this.phone = phone;
		this.email = email;
		this.address = address;
		this.note = note;
		this.createAt = createAt;
		this.status = status;
		this.list = list;
	}
	private int orderId;
	private int totalQuantity;
	private int totalPrice;
	private String fullname;
	private String phone;
	private String email;
	private String address;
	private String note;
	private String createAt;
	private int status;
	private List<OrderManageDetail> list;

}
