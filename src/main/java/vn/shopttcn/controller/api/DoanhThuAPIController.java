package vn.shopttcn.controller.api;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vn.shopttcn.constant.GlobalConstant;
import vn.shopttcn.constant.URLConstant;
import vn.shopttcn.model.User;
import vn.shopttcn.service.OrderService;
import vn.shopttcn.service.UserService;
import vn.shopttcn.util.DateUtil;
import vn.shopttcn.util.bean.DoanhThu;
import vn.shopttcn.util.bean.NhanVien;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping(URLConstant.API_DOANH_THU)
public class DoanhThuAPIController {

	@Autowired
	private OrderService orderService;

	@Autowired
	private UserService userService;

	@GetMapping(URLConstant.API_TONG_DOANH_THU)
	public ResponseEntity<List<DoanhThu>> getDoanhThuByYear(@PathVariable int year) {
		List<DoanhThu> list = new ArrayList<DoanhThu>();
		List<String> listDateEnd = DateUtil.getListDateEnd(year);
		for (int i = 0; i < listDateEnd.size(); i++) {
			String dateBegin = DateUtil.setDateByDMY(1, (i + 1), year);
			String dateEnd = listDateEnd.get(i);
			long doanhThu = orderService.sumPriceInMonth(dateBegin, dateEnd);
			list.add(new DoanhThu("Tháng " + (i + 1), doanhThu));
		}
		if (list.isEmpty()) {
			return new ResponseEntity<List<DoanhThu>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<DoanhThu>>(list, HttpStatus.OK);
	}

	@GetMapping({ URLConstant.API_DOANH_THU_THEO_NHANVIEN, URLConstant.API_DOANH_THU_THEO_NHANVIEN_2 })
	public ResponseEntity<List<NhanVien>> getDoanhThuByNhanVien(@PathVariable(required = false) Integer year,
			@PathVariable(required = false) Integer month) {
		List<User> listNhanVien = userService.findUserByRole(GlobalConstant.ROLE_MOD);
		List<NhanVien> list = new ArrayList<NhanVien>();
		String dateBegin = GlobalConstant.EMPTY;
		String dateEnd = GlobalConstant.EMPTY;
		if (year != null && month != null) {
			dateBegin = DateUtil.setDateByDMY(1, month, year);
			dateEnd = DateUtil.getDateEnd(month, year);
		}
		for (User user : listNhanVien) {
			int orderQuantity = orderService.countOrderByMod(user.getUserId(), dateBegin, dateEnd);
			int orderProductQuantity = orderService.countOrderProductByMod(user.getUserId(), dateBegin, dateEnd);
			long totalSales = orderService.sumOrderPriceByMod(user.getUserId(), dateBegin, dateEnd);
			list.add(new NhanVien(user.getUserId(), user.getUserFullname(), orderQuantity, orderProductQuantity,
					totalSales));
		}
		if (list.isEmpty()) {
			return new ResponseEntity<List<NhanVien>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<NhanVien>>(list, HttpStatus.OK);
	}

}
