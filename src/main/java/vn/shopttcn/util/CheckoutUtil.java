package vn.shopttcn.util;

public class CheckoutUtil {
public CheckoutUtil() {
	// TODO Auto-generated constructor stub
}
	public static float usd(int vnd) {
		float kq = (float) vnd / 22715;
		return (float) Math.round(kq * 100) / 100;
	}

}
