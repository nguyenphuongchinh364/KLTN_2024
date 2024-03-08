package vn.shopttcn.util;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Timestamp;
import java.text.Normalizer;
import java.text.NumberFormat;
import java.util.regex.Pattern;

import vn.shopttcn.constant.GlobalConstant;
import vn.shopttcn.model.Address;

public class StringUtil {
	public StringUtil() {
		// TODO Auto-generated constructor stub
	}

	public static String makeSlug(String title) {
		String slug = Normalizer.normalize(title, Normalizer.Form.NFD);
		Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
		slug = pattern.matcher(slug).replaceAll("");
		slug = slug.toLowerCase();
		slug = slug.replaceAll("đ", "d");
		slug = slug.replaceAll("([^0-9a-z-\\s])", "");
		slug = slug.replaceAll("[\\s]", "-");
		slug = slug.replaceAll("(-+)", "-");
		slug = slug.replaceAll("^-+", "");
		slug = slug.replaceAll("-+$", "");
		return slug;
	}

	// format phone number
	public static String beautifulPhone(String phone) {
		return phone.replaceFirst("(\\d{4})(\\d{3})(\\d+)", "$1 $2 $3");
	}

	// set order address
	public static String setOrderAddress(Address address) {
		return String.join(" - ", address.getAddressDetail(), address.getWard().getWardName(),
				address.getDistrict().getDistrictName(), address.getProvince().getProvinceName());
	}

	// format day
	public static String beautifulDay(Timestamp createAt) {
		String[] dayTime = createAt.toString().split("\\s");
		String[] day = dayTime[0].split("-");
		return String.join("/", day[2], day[1], day[0]);
	}

	// format currency (VNĐ)
	public static String beautifulPrice(long price) {
		NumberFormat formatter = NumberFormat.getCurrencyInstance();
		String rs = formatter.format(price);
		if (rs.endsWith(".00")) {
			int centsIndex = rs.lastIndexOf(".00");
			if (centsIndex != -1) {
				rs = rs.substring(1, centsIndex);
			}
		}
		return rs;
	}

	public static String getIpAddress() {
		try {
			return InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return GlobalConstant.EMPTY;
	}

}
