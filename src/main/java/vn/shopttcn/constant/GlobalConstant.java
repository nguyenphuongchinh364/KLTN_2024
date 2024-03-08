package vn.shopttcn.constant;

public class GlobalConstant {

	public static final String EMPTY = "";

	public static final String DIR_UPLOAD_IMAGE_PRODUCT = "upload/product"; // folder upload file (image product)
	public static final String DIR_UPLOAD_IMAGE_AVATAR = "upload/avatar"; // folder upload file (image avatar)
	public static final String[] FILE_EXTENSION = { "jpg", "png", "jpeg" }; // định dạng file image trong hệ thống

	public static final int TOTAL_ROW = 12; // số dữ liệu hiển thị trong 1 trang
	public static final int TOTAL_ROW_REVIEWS = 5; // hiển thị đánh giá sản phẩm

	public static final int DEFAULT_PAGE = 1;

	public static final int MAX_PICTURE_QUANTITY = 20;

	public static final int SHIP = 30000;

	public static final int MAX_QUANTITY_PRODUCT_ORDER = 5;
	public static final int MIN_QUANTITY_PRODUCT_ORDER = 1;

	// required
	public static final int REQUIRED = 1; // bắt buộc
	public static final int NON_REQUIRED = 0; // không bắt buộc

	// DATABASE
	// trạng thái delete
	public static final int DELETE_STATUS_0 = 0; // chưa xoá
	public static final int DELETE_STATUS_1 = 1; // đã xoá mềm
	// role
	public static final int ROLE_USER = 1; // người dùng, khách hàng
	public static final int ROLE_ADMIN = 2; // admin
	public static final int ROLE_MOD = 3; // nhân viên
	// enabled (user)
	public static final int ENABLED_USER = 1;

	// REGEX => validate form
	public static final String REGEX_USERNAME = "[A-Za-z0-9]{6,20}";
	public static final String REGEX_PHONE_NUMBER = "(0[3|5|7|8|9])+([0-9]{8})";
	public static final String REGEX_PASSWORD = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[^\\w\\s])(?=\\S+$).{6,20}";

}
