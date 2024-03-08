package vn.shopttcn.constant;

public class URLConstant {
	// khai báo các hằng số URL (mapping)

	// PUBLIC
	public static final String INDEX = "trang-chu";
	public static final String CAT = "danh-muc/{catSlug}-{catId}";
	public static final String CAT_PAGINATION = "danh-muc/{catSlug}-{catId}/trang-{page}";
	public static final String DETAIL = "san-pham/{catName}/{productName}-{productId}";
	public static final String SEARCH = "tim-kiem";
	public static final String SEARCH_PAGINATION = "tim-kiem/{keywordURL}/trang-{page}";
	public static final String PRODUCT_FILTER = "product/filter"; // AJAX FILTER PRICE

	public static final String SHOPPING_CART = "gio-hang";
	public static final String CHECKOUT = "thanh-toan";
	public static final String CHECKOUT_SUCCESS = "don-hang/dat-hang-thanh-cong-{orderId}";
	public static final String ORDER = "don-hang";
	public static final String ORDER_PAGINATION = "don-hang/trang-{page}";
	public static final String ORDER_DETAIL = "don-hang/don-hang-{orderId}";
	public static final String ORDER_CANCEL = "don-hang/huy-don-hang-{orderId}";
	public static final String SHOPPING_CART_DEL_ALL = "gio-hang/xoa-tat-ca";
	public static final String REVIEWS = "don-hang/don-hang-{orderId}/danh-gia-san-pham-{productId}";
	public static final String AJAX_REVIEWS = "danh-gia-san-pham";
	public static final String AJAX_REVIEWS_OLDER = "danh-gia-san-pham/cu-hon";
	public static final String AJAX_REVIEWS_NEWER = "danh-gia-san-pham/moi-hon";

	// Shopping Cart (AJAX)
	public static final String SHOPPING_CART_ADD = "cart/add";
	public static final String SHOPPING_CART_INC = "cart/increase";
	public static final String SHOPPING_CART_DEC = "cart/decrease";
	public static final String SHOPPING_CART_DEL = "cart/delete";

	// ADMIN
	public static final String ADMIN_INDEX = "admin";

	public static final String ADMIN_CAT_INDEX = "admin/cat";
	public static final String ADMIN_CAT_SEARCH_PAGINATION = "tim-kiem/catName={catNameURL}/trang-{page}";
	public static final String ADMIN_CAT_UPDATE = "update/{title}-{catId}";

	public static final String ADMIN_PRODUCT_INDEX = "admin/product";
	public static final String ADMIN_PRODUCT_SEARCH_PAGINATION = "tim-kiem/productName={productNameURL}&productCat={catIdURL}/trang-{page}";
	public static final String ADMIN_PRODUCT_UPDATE = "update/{title}-{productId}";
	public static final String ADMIN_PRODUCT_PICTURE = "picture/{productId}";
	public static final String ADMIN_PRODUCT_PICTURE_DELETE = "picture/delete";
	public static final String ADMIN_PRODUCT_PICTURE_DELETE_BY_ID = "picture-{productId}/delete/{pictureId}";
	public static final String ADMIN_PRODUCT_PICTURE_SET_MAIN_IMAGE = "picture/set-main-image";

	public static final String ADMIN_USER_INDEX = "admin/user";
	public static final String ADMIN_USER_SEARCH_PAGINATION = "tim-kiem/username={usernameURL}&roleId={roleIdURL}&enabled={enabledURL}/trang-{page}";
	public static final String ADMIN_USER_UPDATE_STATUS = "update-status";

	public static final String ADMIN_ORDER_INDEX = "admin/order";
	public static final String ADMIN_ORDER_SEARCH_PAGINATION = "tim-kiem/orderName={orderNameURL}&dateCreate={dateCreateURL}&orderStatus={orderStatusURL}&modId={modIdURL}/trang-{page}";
	public static final String ADMIN_ORDER_DETAIL = "detail/{orderId}";
	public static final String ADMIN_ORDER_CONFIRM = "confirm"; // xác nhận đơn hàng (AJAX)

	public static final String ADMIN_REVIEWS_INDEX = "admin/reviews";
	public static final String ADMIN_REVIEWS_SEARCH_PAGINATION = "tim-kiem/productName={productNameURL}&rating={ratingURL}&status={statusURL}/trang-{page}";
	public static final String ADMIN_REVIEWS_UPDATE_STATUS = "update-status";

	public static final String ADMIN_THONGKE_INDEX = "admin/doanh-thu";
	public static final String ADMIN_THONGKE_THEO_NHANVIEN = "nhan-vien";

	public static final String ADMIN_PROFILE = "admin/profile";
	public static final String ADMIN_CHANGE_PASSWORD = "admin/profile/doi-mat-khau";

	public static final String ADMIN_SEARCH = "tim-kiem";
	public static final String ADMIN_PAGINATION = "trang-{page}";

	public static final String ADMIN_ADD = "add";
	public static final String ADMIN_DELETE = "delete/{id}";

	// AUTH
	public static final String ADMIN_LOGIN = "auth/login"; // ADMIN
	public static final String LOGIN = "dang-nhap";
	public static final String LOGOUT = "dang-xuat";
	public static final String REGISTER = "dang-ky-tai-khoan";
	public static final String PROFILE = "tai-khoan/thong-tin-ca-nhan";
	public static final String CHANGE_PASSWORD = "tai-khoan/doi-mat-khau";

	// API
	public static final String API_CAT = "api/cat";
	public static final String API_CAT_PARENT = "parent/{parentId}";
	public static final String API_CAT_BY_PARENT_ID = "list/parent/{parentId}";

	public static final String API_DOANH_THU = "api/doanh-thu";
	public static final String API_TONG_DOANH_THU = "{year}"; // 12 tháng trong năm
	public static final String API_DOANH_THU_THEO_NHANVIEN = "nhan-vien";
	public static final String API_DOANH_THU_THEO_NHANVIEN_2 = "nhan-vien/{month}/{year}"; // theo tháng

	// Address (AJAX)
	public static final String LOCATION_DISTRICT = "location/district";
	public static final String LOCATION_WARD = "location/ward";

	// ERROR
	public static final String ADMIN_ERROR_400 = "error/400";
	public static final String ADMIN_ERROR_404 = "error/404";
	public static final String ADMIN_ERROR_403 = "error/403";

}
