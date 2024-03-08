package vn.shopttcn.util.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DoanhThu {
	public DoanhThu() {
		// TODO Auto-generated constructor stub
	}
	
	public DoanhThu(String month, long sales) {
		super();
		this.month = month;
		this.sales = sales;
	}

	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public long getSales() {
		return sales;
	}
	public void setSales(long sales) {
		this.sales = sales;
	}

	private String month;
	private long sales;

}
