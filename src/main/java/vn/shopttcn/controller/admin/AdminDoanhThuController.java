package vn.shopttcn.controller.admin;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import vn.shopttcn.constant.GlobalConstant;
import vn.shopttcn.constant.URLConstant;
import vn.shopttcn.constant.ViewNameConstant;
import vn.shopttcn.model.User;
import vn.shopttcn.service.OrderService;
import vn.shopttcn.service.UserService;
import vn.shopttcn.util.DateUtil;
import vn.shopttcn.util.bean.NhanVien;

@Controller
@RequestMapping(URLConstant.ADMIN_THONGKE_INDEX)
public class AdminDoanhThuController {

	@Autowired
	private OrderService orderService;

	@Autowired
	private UserService userService;

	@GetMapping(GlobalConstant.EMPTY)
	public String index(@RequestParam(required = false) Integer yearSelect, Model model) {
		String datePresent = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
		int year = DateUtil.getYear(datePresent);
		if (yearSelect != null) {
			year = yearSelect;
		}
		List<String> listDateEnd = DateUtil.getListDateEnd(year);
		List<Long> listDoanhThu = new ArrayList<Long>();
		long tongDoanhThu = 0;
		for (int i = 0; i < listDateEnd.size(); i++) {
			String dateBegin = DateUtil.setDateByDMY(1, (i + 1), year);
			String dateEnd = listDateEnd.get(i);
			long doanhThu = orderService.sumPriceInMonth(dateBegin, dateEnd);
			tongDoanhThu += doanhThu;
			listDoanhThu.add(doanhThu);
		}
		model.addAttribute("year", year);
		model.addAttribute("tongDoanhThu", tongDoanhThu);
		model.addAttribute("listDoanhThu", listDoanhThu);
		model.addAttribute("listYear", DateUtil.getListYearPresent());
		return ViewNameConstant.ADMIN_THONGKE_INDEX;
	}

	@GetMapping(URLConstant.ADMIN_THONGKE_THEO_NHANVIEN)
	public String thongKeTheoNhanVien(@RequestParam(required = false) Integer yearSelect,
			@RequestParam(required = false) Integer monthSelect, Model model) {
		List<User> listNhanVien = userService.findUserByRole(GlobalConstant.ROLE_MOD);
		List<NhanVien> list = new ArrayList<NhanVien>();
		String dateBegin = GlobalConstant.EMPTY;
		String dateEnd = GlobalConstant.EMPTY;
		if (yearSelect != null && monthSelect != null) {
			dateBegin = DateUtil.setDateByDMY(1, monthSelect, yearSelect);
			dateEnd = DateUtil.getDateEnd(monthSelect, yearSelect);
			model.addAttribute("month", monthSelect);
			model.addAttribute("year", yearSelect);
		}
		long tongDoanhThu = 0;
		for (User user : listNhanVien) {
			int orderQuantity = orderService.countOrderByMod(user.getUserId(), dateBegin, dateEnd);
			int orderProductQuantity = orderService.countOrderProductByMod(user.getUserId(), dateBegin, dateEnd);
			long totalSales = orderService.sumOrderPriceByMod(user.getUserId(), dateBegin, dateEnd);
			tongDoanhThu += totalSales;
			list.add(new NhanVien(user.getUserId(), user.getUserFullname(), orderQuantity, orderProductQuantity,
					totalSales));
		}
		Collections.sort(list, (o2, o1) -> Long.compare(o1.getTotalSales(), o2.getTotalSales()));
		model.addAttribute("list", list);
		model.addAttribute("tongDoanhThu", tongDoanhThu);
		model.addAttribute("listYear", DateUtil.getListYearPresent());
		return ViewNameConstant.ADMIN_THONGKE_THEO_NHANVIEN;
	}

}
