package vn.shopttcn.controller.admin;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.gson.Gson;

import vn.shopttcn.constant.GlobalConstant;
import vn.shopttcn.constant.URLConstant;
import vn.shopttcn.constant.ViewNameConstant;
import vn.shopttcn.model.Order;
import vn.shopttcn.model.OrderDetail;
import vn.shopttcn.model.User;
import vn.shopttcn.service.OrderDetailService;
import vn.shopttcn.service.OrderService;
import vn.shopttcn.service.UserService;
import vn.shopttcn.util.PageUtil;
import vn.shopttcn.util.bean.AjaxStatus;

@Controller
@RequestMapping(URLConstant.ADMIN_ORDER_INDEX)
public class AdminOrderController {

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private OrderService orderService;

	@Autowired
	private OrderDetailService orderDetailService;

	@Autowired
	private UserService userService;

	@GetMapping({ GlobalConstant.EMPTY, URLConstant.ADMIN_PAGINATION, URLConstant.ADMIN_SEARCH,
			URLConstant.ADMIN_ORDER_SEARCH_PAGINATION })
	public String index(@PathVariable(required = false) Integer page,
			@PathVariable(required = false) String orderNameURL, @PathVariable(required = false) String dateCreateURL,
			@PathVariable(required = false) Integer orderStatusURL, @PathVariable(required = false) Integer modIdURL,
			@RequestParam(required = false) String orderName, @RequestParam(required = false) String dateCreate,
			@RequestParam(required = false) Integer orderStatus, @RequestParam(required = false) Integer modId,
			Model model, RedirectAttributes ra) {
		model.addAttribute("listMod", userService.findUserByRole(GlobalConstant.ROLE_MOD));
		int currentPage = GlobalConstant.DEFAULT_PAGE;
		if (page != null) {
			if (page < GlobalConstant.DEFAULT_PAGE) {
				ra.addFlashAttribute("error", messageSource.getMessage("pageError", null, Locale.getDefault()));
				return "redirect:/" + URLConstant.ADMIN_ORDER_INDEX;
			}
			currentPage = page;
		}
		int offset = PageUtil.getOffset(currentPage);
		int totalRow = orderService.totalRow();
		int totalPage = PageUtil.getTotalPage(totalRow);
		List<Order> listOrder = orderService.getList(offset, GlobalConstant.TOTAL_ROW);
		if (orderNameURL != null) {
			orderName = orderNameURL;
		}
		if (dateCreateURL != null) {
			dateCreate = dateCreateURL;
		}
		if (orderStatusURL != null) {
			orderStatus = orderStatusURL;
		}
		if (modIdURL != null) {
			modId = modIdURL;
		}
		if (orderName != null || dateCreate != null || orderStatus != null || modId != null) {
			if (orderName.equals(GlobalConstant.EMPTY) && dateCreate.equals(GlobalConstant.EMPTY) && orderStatus == -1
					&& modId == -1) {
				ra.addFlashAttribute("error", messageSource.getMessage("searchError", null, Locale.getDefault()));
				return "redirect:/" + URLConstant.ADMIN_ORDER_INDEX;
			}
			model.addAttribute("orderName", orderName);
			model.addAttribute("dateCreate", dateCreate);
			model.addAttribute("orderStatus", orderStatus);
			model.addAttribute("modId", modId);
			totalRow = orderService.totalRowSearch(orderName, dateCreate, orderStatus, modId);
			totalPage = PageUtil.getTotalPage(totalRow);
			listOrder = orderService.search(orderName, dateCreate, orderStatus, modId, offset,
					GlobalConstant.TOTAL_ROW);
		}
		model.addAttribute("listOrder", listOrder);
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("totalPage", totalPage);
		return ViewNameConstant.ADMIN_ORDER_INDEX;
	}

	@GetMapping(URLConstant.ADMIN_ORDER_DETAIL)
	public String detail(@PathVariable int orderId, Model model, RedirectAttributes ra, HttpSession session) {
		Order order = orderService.findById(orderId);
		if (order == null) {
			ra.addFlashAttribute("error", messageSource.getMessage("noDataError", null, Locale.getDefault()));
			return "redirect:/" + URLConstant.ADMIN_ORDER_INDEX;
		}
		User userLogin = (User) session.getAttribute("adminUserLogin");
		if (orderService.findByMod(orderId, userLogin.getUserId()) != null) {
			// nhân viên đang login xử lý đơn hàng này => có quyền update status
			model.addAttribute("confirm", userLogin.getUserId());
		}
		List<OrderDetail> listOrderDetail = orderDetailService.findByOrderId(order.getOrderId());
		model.addAttribute("objOrder", order);
		model.addAttribute("listOrderDetail", listOrderDetail);
		return ViewNameConstant.ADMIN_ORDER_DETAIL;
	}

	// update status order
	@Transactional
	@PostMapping(URLConstant.ADMIN_ORDER_DETAIL)
	public String updateStatus(@ModelAttribute Order order, Model model, RedirectAttributes ra, HttpSession session) {
		User userLogin = (User) session.getAttribute("adminUserLogin");
		Order objOrder = orderService.findById(order.getOrderId());
		List<OrderDetail> listOrderDetail = orderDetailService.findByOrderId(order.getOrderId());
		model.addAttribute("objOrder", objOrder);
		model.addAttribute("listOrderDetail", listOrderDetail);
		// check nhân viên xử lý đơn hàng
		if (objOrder.getUser().getUserId() != userLogin.getUserId()) {
			model.addAttribute("error",
					messageSource.getMessage("noRightUpdateStatusOrderError", null, Locale.getDefault()));
			return ViewNameConstant.ADMIN_ORDER_DETAIL;
		}
		model.addAttribute("confirm", userLogin.getUserId());
		if (objOrder.getOrderStatus() == 4 || objOrder.getOrderStatus() == 5) {
			// đơn hàng đã giao thành công hoặc đã huỷ => k update
			model.addAttribute("error",
					messageSource.getMessage("noRightUpdateStatusOrderError", null, Locale.getDefault()));
			return ViewNameConstant.ADMIN_ORDER_DETAIL;
		}
		if (orderService.updateStatus(order) > 0) {
			if (order.getOrderStatus() == 4) {
				// giao hàng thành công => update trạng thái thanh toán
				objOrder.setOrderPayment(1);
				if (orderService.updatePayment(objOrder) > 0) {
					System.out.println();
				} else {
					model.addAttribute("error", messageSource.getMessage("error", null, Locale.getDefault()));
				}
			}
			objOrder.setOrderStatus(order.getOrderStatus());
			model.addAttribute("objOrder", objOrder);
			model.addAttribute("success",
					messageSource.getMessage("updateStatusOrderSuccess", null, Locale.getDefault()));
			return ViewNameConstant.ADMIN_ORDER_DETAIL;
		}
		model.addAttribute("error", messageSource.getMessage("error", null, Locale.getDefault()));
		return ViewNameConstant.ADMIN_ORDER_DETAIL;
	}

	@Transactional
	@PostMapping(URLConstant.ADMIN_ORDER_CONFIRM)
	@ResponseBody
	public String confirmOrder(@RequestParam int orderId, HttpSession session) {
		User userLogin = (User) session.getAttribute("adminUserLogin");
		if (userLogin.getRole().getRoleId() != GlobalConstant.ROLE_MOD) {
			// chỉ có nhân viên (mod) có quyền xử lý đơn hàng
			return new Gson().toJson(
					new AjaxStatus(1, messageSource.getMessage("noRightConfirmOrderError", null, Locale.getDefault())));
		}
		Order order = orderService.findById(orderId);
		if (order == null) {
			return new Gson()
					.toJson(new AjaxStatus(1, messageSource.getMessage("noDataError", null, Locale.getDefault())));
		}
		order.setUser(userLogin);
		if (orderService.updateMod(order) > 0) {
			order.setOrderStatus(1);
			if (orderService.updateStatus(order) > 0) {
				// success
				return new Gson().toJson(
						new AjaxStatus(0, messageSource.getMessage("confirmOrderSuccess", null, Locale.getDefault())));
			}
		}
		return new Gson().toJson(new AjaxStatus(1, messageSource.getMessage("error", null, Locale.getDefault())));
	}

}
