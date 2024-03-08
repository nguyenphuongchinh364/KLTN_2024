package vn.shopttcn.util.bean;




public class NhanVien {
	
	public NhanVien() {
		// TODO Auto-generated constructor stub
	}
	
	public NhanVien(int userId, String userFullname, int orderQuantity, int orderProductQuantity, long totalSales) {
		super();
		this.userId = userId;
		this.userFullname = userFullname;
		this.orderQuantity = orderQuantity;
		this.orderProductQuantity = orderProductQuantity;
		this.totalSales = totalSales;
	}

	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getUserFullname() {
		return userFullname;
	}
	public void setUserFullname(String userFullname) {
		this.userFullname = userFullname;
	}
	public int getOrderQuantity() {
		return orderQuantity;
	}
	public void setOrderQuantity(int orderQuantity) {
		this.orderQuantity = orderQuantity;
	}
	public int getOrderProductQuantity() {
		return orderProductQuantity;
	}
	public void setOrderProductQuantity(int orderProductQuantity) {
		this.orderProductQuantity = orderProductQuantity;
	}
	public long getTotalSales() {
		return totalSales;
	}
	public void setTotalSales(long totalSales) {
		this.totalSales = totalSales;
	}
	// dùng cho thống kê
	private int userId;
	private String userFullname;
	private int orderQuantity;
	private int orderProductQuantity;
	private long totalSales;

}
