package vn.shopttcn.controller.shop;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

import vn.shopttcn.constant.GlobalConstant;
import vn.shopttcn.constant.URLConstant;
import vn.shopttcn.constant.ViewNameConstant;
import vn.shopttcn.model.Product;
import vn.shopttcn.model.User;
import vn.shopttcn.service.ProductService;
import vn.shopttcn.util.bean.AjaxCart;
import vn.shopttcn.util.bean.Cart;
import vn.shopttcn.util.bean.CartDetail;

@Controller
public class ShoppingCartController {

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private ProductService productService;

	@GetMapping(URLConstant.SHOPPING_CART)
	public String cart() {
		return ViewNameConstant.SHOPPING_CART;
	}

	// add to cart
	@PostMapping(URLConstant.SHOPPING_CART_ADD)
	@ResponseBody
	public String addToCart(@RequestParam int productId, @RequestParam int quantity, HttpSession session) {
		AjaxCart ajaxCart = new AjaxCart(0);
		// Giới hạn số sản phẩm của 1 lần mua
		if (quantity > GlobalConstant.MAX_QUANTITY_PRODUCT_ORDER) {
			ajaxCart.setError(1);
			ajaxCart.setErrorContent(messageSource.getMessage("maxQuantityProductOrder", null, Locale.getDefault()));
			return new Gson().toJson(ajaxCart);
		}
		int userId = 0;
		if (session.getAttribute("userLogin") != null) {
			User userLogin = (User) session.getAttribute("userLogin");
			userId = userLogin.getUserId();
		}
		Product product = productService.findById(productId, GlobalConstant.DELETE_STATUS_0);
		if (quantity > product.getProductQuantity()) {
			ajaxCart.setError(1);
			ajaxCart.setErrorContent(messageSource.getMessage("notEnoughProduct", null, Locale.getDefault()));
			return new Gson().toJson(ajaxCart);
		}
		Cart cart = null;
		if (session.getAttribute("cart") == null) {
			// giỏ hàng đang rỗng => thêm mới
			List<CartDetail> listCartDetail = new ArrayList<CartDetail>();
			CartDetail cartDetail = new CartDetail(product, quantity);
			listCartDetail.add(cartDetail);
			int totalPrice = cartDetail.getProduct().getProductPrice() * quantity;
			cart = new Cart(userId, listCartDetail, quantity, totalPrice);
		} else {
			cart = (Cart) session.getAttribute("cart");
			List<CartDetail> listCartDetail = cart.getListCartDetail();
			boolean check = true;
			if (listCartDetail.size() > 0) {
				for (CartDetail cartDetail : listCartDetail) {
					if (productId == cartDetail.getProduct().getProductId()) {
						// đã có sản phẩm trong giỏ hàng => tăng số lượng
						// xét trường hợp lỗi (quá số lượng, hết hàng)
						if ((cartDetail.getQuantity() + quantity) > GlobalConstant.MAX_QUANTITY_PRODUCT_ORDER) {
							ajaxCart.setError(1);
							ajaxCart.setErrorContent(
									messageSource.getMessage("maxQuantityProductOrder", null, Locale.getDefault()));
							return new Gson().toJson(ajaxCart);
						}
						if ((cartDetail.getQuantity() + quantity) > product.getProductQuantity()) {
							ajaxCart.setError(1);
							ajaxCart.setErrorContent(
									messageSource.getMessage("notEnoughProduct", null, Locale.getDefault()));
							return new Gson().toJson(ajaxCart);
						}
						check = false;
						cartDetail.setQuantity(cartDetail.getQuantity() + quantity);
						cart.setTotalPrice(
								cart.getTotalPrice() + (cartDetail.getProduct().getProductPrice() * quantity));
						break;
					}
				}
			}
			if (check) {
				// chưa có sản phẩm trong giỏ hàng => thêm mới
				CartDetail cartDetail = new CartDetail(product, quantity);
				listCartDetail.add(cartDetail);
				cart.setTotalPrice(cart.getTotalPrice() + cartDetail.getProduct().getProductPrice() * quantity);
			}
			cart.setTotalQuantity(cart.getTotalQuantity() + quantity);
		}
		session.setAttribute("cart", cart);
		ajaxCart.setTotalQuantity(cart.getTotalQuantity());
		return new Gson().toJson(ajaxCart);
	}

	// increase product in cart
	@PostMapping(URLConstant.SHOPPING_CART_INC)
	@ResponseBody
	public String incCart(@RequestParam int productId, HttpSession session) {
		AjaxCart ajaxCart = new AjaxCart(0);
		Product product = productService.findById(productId, GlobalConstant.DELETE_STATUS_0);
		Cart cart = (Cart) session.getAttribute("cart");
		List<CartDetail> listCartDetail = cart.getListCartDetail();
		if (listCartDetail.size() > 0) {
			for (CartDetail cartDetail : listCartDetail) {
				if (productId == cartDetail.getProduct().getProductId()) {
					// check lỗi (hết hàng, quá số lượng)
					if (cartDetail.getQuantity() == GlobalConstant.MAX_QUANTITY_PRODUCT_ORDER) {
						ajaxCart.setError(1);
						ajaxCart.setErrorContent(
								messageSource.getMessage("maxQuantityProductOrder", null, Locale.getDefault()));
						return new Gson().toJson(ajaxCart);
					}
					if (cartDetail.getQuantity() == product.getProductQuantity()) {
						ajaxCart.setError(1);
						ajaxCart.setErrorContent(
								messageSource.getMessage("notEnoughProduct", null, Locale.getDefault()));
						return new Gson().toJson(ajaxCart);
					}
					// tăng (+1) số lượng sản phẩm trong giỏ hàng
					cartDetail.setQuantity(cartDetail.getQuantity() + 1);
					ajaxCart.setQuantity(cartDetail.getQuantity());
					ajaxCart.setPrice(cartDetail.getQuantity() * cartDetail.getProduct().getProductPrice());
					cart.setTotalPrice(cart.getTotalPrice() + cartDetail.getProduct().getProductPrice());
					ajaxCart.setTotalPrice(cart.getTotalPrice());
				}
			}
		}
		cart.setTotalQuantity(cart.getTotalQuantity() + 1);
		ajaxCart.setTotalQuantity(cart.getTotalQuantity());
		session.setAttribute("cart", cart);
		return new Gson().toJson(ajaxCart);
	}

	// decrease product in cart
	@PostMapping(URLConstant.SHOPPING_CART_DEC)
	@ResponseBody
	public String decCart(@RequestParam int productId, HttpSession session) {
		AjaxCart ajaxCart = new AjaxCart(0);
		Cart cart = (Cart) session.getAttribute("cart");
		List<CartDetail> listCartDetail = cart.getListCartDetail();
		if (listCartDetail.size() > 0) {
			for (CartDetail cartDetail : listCartDetail) {
				if (productId == cartDetail.getProduct().getProductId()) {
					// check lỗi (số lượng)
					if (cartDetail.getQuantity() == GlobalConstant.MIN_QUANTITY_PRODUCT_ORDER) {
						ajaxCart.setError(1);
						ajaxCart.setErrorContent(
								messageSource.getMessage("minQuantityProductOrder", null, Locale.getDefault()));
						return new Gson().toJson(ajaxCart);
					}
					// giảm (-1) số lượng sản phẩm trong giỏ hàng
					cartDetail.setQuantity(cartDetail.getQuantity() - 1);
					ajaxCart.setQuantity(cartDetail.getQuantity());
					ajaxCart.setPrice(cartDetail.getQuantity() * cartDetail.getProduct().getProductPrice());
					cart.setTotalPrice(cart.getTotalPrice() - cartDetail.getProduct().getProductPrice());
					ajaxCart.setTotalPrice(cart.getTotalPrice());
				}
			}
		}
		cart.setTotalQuantity(cart.getTotalQuantity() - 1);
		ajaxCart.setTotalQuantity(cart.getTotalQuantity());
		session.setAttribute("cart", cart);
		return new Gson().toJson(ajaxCart);
	}

	// delete product in cart
	@PostMapping(URLConstant.SHOPPING_CART_DEL)
	@ResponseBody
	public String delCart(@RequestParam int productId, HttpSession session) {
		AjaxCart ajaxCart = new AjaxCart(0);
		Cart cart = (Cart) session.getAttribute("cart");
		List<CartDetail> listCartDetail = cart.getListCartDetail();
		// TH giỏ hàng chỉ có 1 sản phẩm
		if (listCartDetail.size() == 1) {
			ajaxCart.setError(1); // reload
			session.removeAttribute("cart");
			return new Gson().toJson(ajaxCart);
		}
		if (listCartDetail.size() > 0) {
			for (int i = 0; i < listCartDetail.size(); i++) {
				CartDetail cartDetail = listCartDetail.get(i);
				if (productId == cartDetail.getProduct().getProductId()) {
					cart.setTotalQuantity(cart.getTotalQuantity() - cartDetail.getQuantity());
					ajaxCart.setTotalQuantity(cart.getTotalQuantity());
					cart.setTotalPrice(cart.getTotalPrice()
							- (cartDetail.getProduct().getProductPrice() * cartDetail.getQuantity()));
					ajaxCart.setTotalPrice(cart.getTotalPrice());
					listCartDetail.remove(i); // xoá sản phẩm khỏi giỏ hàng
					break;
				}
			}
		}
		session.setAttribute("cart", cart);
		return new Gson().toJson(ajaxCart);
	}

	// delete all product in cart
	@GetMapping(URLConstant.SHOPPING_CART_DEL_ALL)
	public String delAllCart(HttpSession session) {
		if (session.getAttribute("cart") != null) {
			session.removeAttribute("cart");
		}
		return "redirect:/" + URLConstant.SHOPPING_CART;
	}

}
