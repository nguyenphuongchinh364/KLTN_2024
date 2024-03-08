package vn.shopttcn.controller.shop;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.gson.Gson;

import vn.shopttcn.constant.GlobalConstant;
import vn.shopttcn.constant.URLConstant;
import vn.shopttcn.constant.ViewNameConstant;
import vn.shopttcn.model.Address;
import vn.shopttcn.model.Order;
import vn.shopttcn.model.OrderDetail;
import vn.shopttcn.model.Product;
import vn.shopttcn.model.Reviews;
import vn.shopttcn.model.User;
import vn.shopttcn.service.LocationDistrictService;
import vn.shopttcn.service.LocationProvinceService;
import vn.shopttcn.service.LocationWardService;
import vn.shopttcn.service.OrderDetailService;
import vn.shopttcn.service.OrderService;
import vn.shopttcn.service.ProductService;
import vn.shopttcn.service.ReviewsService;
import vn.shopttcn.util.PageUtil;
import vn.shopttcn.util.StringUtil;
import vn.shopttcn.util.bean.AjaxStatus;
import vn.shopttcn.util.bean.Cart;
import vn.shopttcn.util.bean.CartDetail;
import vn.shopttcn.validate.AddressValidate;
import vn.shopttcn.validate.OrderValidate;

@Controller
public class OrderController {

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private OrderService orderService;

	@Autowired
	private OrderDetailService orderDetailService;

	@Autowired
	private LocationProvinceService provinceService;

	@Autowired
	private LocationDistrictService districtService;

	@Autowired
	private LocationWardService wardService;

	@Autowired
	private ProductService productService;

	@Autowired
	private AddressValidate addressValidate;

	@Autowired
	private OrderValidate orderValidate;

	@Autowired
	private ReviewsService reviewsService;

	@GetMapping(URLConstant.CHECKOUT)
	public String checkout(Model model, HttpSession session) {
		if (session.getAttribute("cart") == null) {
			return "redirect:/" + URLConstant.SHOPPING_CART;
		}
		if (session.getAttribute("userLogin") != null) {
			User user = (User) session.getAttribute("userLogin");
			Order order = new Order(user.getUserFullname(), user.getUserEmail(), user.getUserPhone());
			model.addAttribute("objOrder", order);
		}
		model.addAttribute("listProvince", provinceService.getAll());
		return ViewNameConstant.CHECKOUT;
	}

	@Transactional
	@PostMapping(URLConstant.CHECKOUT)
	public String checkout(@Valid @ModelAttribute("orderError") Order order, BindingResult orderRs, Model model,
			@Valid @ModelAttribute("addressError") Address address, BindingResult addressRs, RedirectAttributes ra,
			HttpSession session) {
		Cart cart = (Cart) session.getAttribute("cart");
		List<CartDetail> listCartDetail = cart.getListCartDetail();
		// check TH hết hàng
		if (listCartDetail.size() > 0) {
			for (CartDetail cartDetail : listCartDetail) {
				if (cartDetail.getQuantity() > cartDetail.getProduct().getProductQuantity()) {
					ra.addFlashAttribute("error",
							messageSource.getMessage("outOfStockError", null, Locale.getDefault()));
					return "redirect:/" + URLConstant.CHECKOUT;
				}
			}
		}
		model.addAttribute("listProvince", provinceService.getAll());
		if (address.getProvince().getProvinceId() > 0) {
			model.addAttribute("listDistrict", districtService.findByProvinceId(address.getProvince().getProvinceId()));
			if (address.getDistrict().getDistrictId() > 0) {
				model.addAttribute("listWard", wardService.findByDistrictId(address.getDistrict().getDistrictId()));
			}
		}
		model.addAttribute("objOrder", order);
		model.addAttribute("address", address);
		addressValidate.validate(address, addressRs);
		orderValidate.validatePhoneNumber(order, orderRs);
		if (orderRs.hasErrors() || addressRs.hasErrors()) {
			return ViewNameConstant.CHECKOUT;
		}
		order.setUserId(0);
		if (session.getAttribute("userLogin") != null) {
			User userLogin = (User) session.getAttribute("userLogin");
			order.setUserId(userLogin.getUserId());
		}
		order.setOrderTotalQuantity(cart.getTotalQuantity());
		order.setOrderTotalPrice(cart.getTotalPrice());
		address.setProvince(provinceService.findById(address.getProvince().getProvinceId()));
		address.setDistrict(districtService.findById(address.getDistrict().getDistrictId()));
		address.setWard(wardService.findById(address.getWard().getWardId()));
		order.setOrderAddress(StringUtil.setOrderAddress(address));
		Order newOrder = null;
		if (orderService.save(order) > 0) {
			boolean check = true;
			newOrder = orderService.getNewOrder();
			if (listCartDetail.size() > 0) {
				for (CartDetail cartDetail : listCartDetail) {
					Product product = productService.findById(cartDetail.getProduct().getProductId(),
							GlobalConstant.DELETE_STATUS_0);
					OrderDetail orderDetail = new OrderDetail(newOrder.getOrderId(), product.getProductId(),
							product.getProductName(), product.getProductPrice(), cartDetail.getQuantity());
					int kq = orderDetailService.save(orderDetail);
					if (kq == 0) {
						check = false;
						break;
					}
					// tăng số lượng sản phẩm đã bán
					product.setProductSold(product.getProductSold() + cartDetail.getQuantity());
					if (productService.updateSold(product) > 0) {
						System.out.println();
					}
					// giảm số lượng sản phẩm trong shop
					product.setProductQuantity(product.getProductQuantity() - cartDetail.getQuantity());
					if (productService.updateQuantity(product) > 0) {
						System.out.println();
					}
				}
			}
			if (check) {
				session.removeAttribute("cart");
				ra.addFlashAttribute("success", messageSource.getMessage("checkoutSuccess", null, Locale.getDefault()));
			} else {
				ra.addFlashAttribute("error", messageSource.getMessage("error", null, Locale.getDefault()));
				return "redirect:/" + URLConstant.CHECKOUT;
			}
		} else {
			ra.addFlashAttribute("error", messageSource.getMessage("error", null, Locale.getDefault()));
			return "redirect:/" + URLConstant.CHECKOUT;
		}
		if (order.getUserId() == 0) {
			// không có account
			session.setAttribute("checkoutSuccess", "CheckoutSuccess");
			return "redirect:/don-hang/dat-hang-thanh-cong-" + newOrder.getOrderId();
		}
		return "redirect:/" + URLConstant.ORDER + "/don-hang-" + newOrder.getOrderId();
	}

	// user (không có tài khoản) mua hàng -> redirect qua trang này
	@GetMapping(URLConstant.CHECKOUT_SUCCESS)
	public String checkoutSuccess(@PathVariable Integer orderId, Model model, RedirectAttributes ra,
			HttpSession session) {
		// trang này chỉ vào được khi redirect từ trang đặt hàng
		if (session.getAttribute("checkoutSuccess") == null) {
			return "redirect:/" + URLConstant.INDEX;
		}
		session.removeAttribute("checkoutSuccess");
		Order order = orderService.findById(orderId);
		if (order == null) {
			return "redirect:/" + URLConstant.INDEX;
		}
		List<OrderDetail> listOrderDetail = orderDetailService.findByOrderId(orderId);
		model.addAttribute("order", order);
		model.addAttribute("listOrderDetail", listOrderDetail);
		return ViewNameConstant.CHECKOUT_SUCCESS;
	}

	@GetMapping({ URLConstant.ORDER, URLConstant.ORDER_PAGINATION })
	public String order(@PathVariable(required = false) Integer page, Model model, RedirectAttributes ra,
			HttpSession session) {
		if (session.getAttribute("userLogin") == null) {
			return "redirect:/" + URLConstant.LOGIN;
		}
		int currentPage = GlobalConstant.DEFAULT_PAGE;
		if (page != null) {
			if (page < GlobalConstant.DEFAULT_PAGE) {
				ra.addFlashAttribute("error", messageSource.getMessage("pageError", null, Locale.getDefault()));
				return "redirect:/" + URLConstant.ORDER;
			}
			currentPage = page;
		}
		User user = (User) session.getAttribute("userLogin");
		int offset = PageUtil.getOffset(currentPage);
		int totalRow = orderService.totalRowByUser(user.getUserId());
		int totalPage = PageUtil.getTotalPage(totalRow);
		List<Order> listOrder = orderService.findByUser(user.getUserId(), offset, GlobalConstant.TOTAL_ROW);
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("totalPage", totalPage);
		model.addAttribute("listOrder", listOrder);
		return ViewNameConstant.ORDER;
	}

	@GetMapping(URLConstant.ORDER_DETAIL)
	public String orderDetail(@PathVariable Integer orderId, Model model, RedirectAttributes ra, HttpSession session) {
		if (session.getAttribute("userLogin") == null) {
			return "redirect:/" + URLConstant.LOGIN;
		}
		Order order = orderService.findById(orderId);
		if (order == null) {
			ra.addFlashAttribute("error", messageSource.getMessage("noDataError", null, Locale.getDefault()));
			return "redirect:/" + URLConstant.ORDER;
		}
		User user = (User) session.getAttribute("userLogin");
		// đơn hàng của người khác => return lỗi
		if (order.getUserId() != user.getUserId()) {
			ra.addFlashAttribute("error", messageSource.getMessage("noDataError", null, Locale.getDefault()));
			return "redirect:/" + URLConstant.ORDER;
		}
		List<OrderDetail> listOrderDetail = orderDetailService.findByOrderId(orderId);
		model.addAttribute("order", order);
		model.addAttribute("listOrderDetail", listOrderDetail);
		return ViewNameConstant.ORDER_DETAIL;
	}

	@GetMapping(URLConstant.ORDER_CANCEL)
	public String orderCancel(@PathVariable int orderId, HttpSession session, RedirectAttributes ra) {
		User userLogin = (User) session.getAttribute("userLogin");
		if (userLogin == null) {
			return "redirect:/" + URLConstant.LOGIN;
		}
		Order order = orderService.findById(orderId);
		if (order == null) {
			ra.addFlashAttribute("error", messageSource.getMessage("noDataError", null, Locale.getDefault()));
			return "redirect:/" + URLConstant.ORDER;
		}
		if (order.getUserId() != userLogin.getUserId()) {
			ra.addFlashAttribute("error", messageSource.getMessage("noDataError", null, Locale.getDefault()));
			return "redirect:/" + URLConstant.ORDER;
		}
		if (order.getOrderStatus() == 4 || order.getOrderStatus() == 5) {
			ra.addFlashAttribute("error", messageSource.getMessage("userCancelOrderError", null, Locale.getDefault()));
			return "redirect:/don-hang/don-hang-" + orderId;
		}
		order.setOrderStatus(5);
		if (orderService.updateStatus(order) > 0) {
			ra.addFlashAttribute("success", messageSource.getMessage("cancelOrderSuccess", null, Locale.getDefault()));
		} else {
			ra.addFlashAttribute("error", messageSource.getMessage("userCancelOrderError", null, Locale.getDefault()));
		}
		return "redirect:/don-hang/don-hang-" + orderId;
	}

	@GetMapping(URLConstant.REVIEWS)
	public String reviews(@PathVariable Integer orderId, @PathVariable Integer productId, Model model,
			HttpSession session) {
		User userLogin = (User) session.getAttribute("userLogin");
		if (userLogin == null) {
			return "redirect:/" + URLConstant.LOGIN;
		}
		Product product = productService.findById(productId, GlobalConstant.DELETE_STATUS_0);
		if (product == null) {
			return "redirect:/don-hang/don-hang-" + orderId + "/danh-gia-san-pham-" + productId;
		}
		if (!orderDetailService.checkProductInOrder(orderId, productId, userLogin.getUserId())) {
			return "redirect:/" + URLConstant.ORDER;
		}
		model.addAttribute("product", product);
		model.addAttribute("listBestSell", productService.getBestSellProduct(GlobalConstant.DELETE_STATUS_0));
		return ViewNameConstant.REVIEWS;
	}

	@PostMapping(URLConstant.AJAX_REVIEWS)
	@ResponseBody
	public String reviews(@RequestParam int productId, @RequestParam int rating, @RequestParam String reviewsContent,
			HttpSession session) {
		User userLogin = (User) session.getAttribute("userLogin");
		Reviews reviews = new Reviews(userLogin, new Product(productId), reviewsContent, rating);
		if (reviewsService.save(reviews) > 0) {
			return new Gson().toJson(new AjaxStatus(0, GlobalConstant.EMPTY));
		}
		return new Gson().toJson(new AjaxStatus(1, messageSource.getMessage("error", null, Locale.getDefault())));
	}

}
