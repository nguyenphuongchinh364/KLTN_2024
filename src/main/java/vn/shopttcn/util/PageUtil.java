package vn.shopttcn.util;

import vn.shopttcn.constant.GlobalConstant;

public class PageUtil {
	public PageUtil() {
		// TODO Auto-generated constructor stub
	}

	public static int getOffset(int page) {
		return (page - 1) * GlobalConstant.TOTAL_ROW;
	}

	public static int getTotalPage(int totalRow) {
		int page = totalRow / GlobalConstant.TOTAL_ROW;
		if (totalRow % GlobalConstant.TOTAL_ROW == 0) {
			return page;
		}
		return page + 1;
	}

	public static int getOffsetReviews(int page) {
		return (page - 1) * GlobalConstant.TOTAL_ROW_REVIEWS;
	}

	public static int getTotalPageReviews(int totalRow) {
		int page = totalRow / GlobalConstant.TOTAL_ROW_REVIEWS;
		if (totalRow % GlobalConstant.TOTAL_ROW_REVIEWS == 0) {
			return page;
		}
		return page + 1;
	}

}
