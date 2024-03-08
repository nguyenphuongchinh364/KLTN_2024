package vn.shopttcn.util;

public class ReviewsUtil {
	public ReviewsUtil() {
		// TODO Auto-generated constructor stub
	}

	public static int ratingProduct(float avg) {
		return (int) Math.floor(avg);
	}

	// ratingCount: số lượt đánh giá sản phẩm theo số sao
	// totalRowRating: tổng số lượt đánh giá sản phẩm
	public static double ratingPercent(int totalRowRating, int ratingCount) {
		return 100 * (double) Math.round(((double) ratingCount / totalRowRating) * 100) / 100;
	}

}
