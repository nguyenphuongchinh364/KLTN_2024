package vn.shopttcn.controller.shop;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

import vn.shopttcn.constant.GlobalConstant;
import vn.shopttcn.constant.URLConstant;
import vn.shopttcn.constant.ViewNameConstant;
import vn.shopttcn.model.Category;
import vn.shopttcn.model.Product;
import vn.shopttcn.model.ProductPicture;
import vn.shopttcn.model.Reviews;
import vn.shopttcn.model.User;
import vn.shopttcn.service.CategoryService;
import vn.shopttcn.service.ProductPictureService;
import vn.shopttcn.service.ProductService;
import vn.shopttcn.service.ReviewsService;
import vn.shopttcn.util.CategoryUtil;
import vn.shopttcn.util.DateUtil;
import vn.shopttcn.util.PageUtil;
import vn.shopttcn.util.StringUtil;
import vn.shopttcn.util.bean.AjaxStatus;

@Controller
public class IndexController {

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private ProductService productService;

	@Autowired
	private ReviewsService reviewsService;

	@Autowired
	private ProductPictureService pictureService;

	@Autowired
	private CategoryUtil categoryUtil;

	@ModelAttribute
	public void saveData(Model model) {
		model.addAttribute("listBestSell", productService.getBestSellProduct(GlobalConstant.DELETE_STATUS_0));
	}

	@GetMapping({ URLConstant.INDEX, GlobalConstant.EMPTY })
	public String index(Model model) {
		List<Category> listCatParent = categoryService.findCatByParentId(0, GlobalConstant.DELETE_STATUS_0);
		model.addAttribute("listCatParent", listCatParent);
		List<Product> listProduct = new ArrayList<Product>();
		if (listCatParent.size() > 0) {
			for (Category category : listCatParent) {
				// Lấy product từ list danh mục đa cấp
				List<Integer> listCatId = new ArrayList<Integer>();
				listCatId.add(category.getCatId());
				listCatId = categoryUtil.findCatIdByParentId(listCatId, category.getCatId());
				listProduct.addAll(productService.findByCatId(listCatId, 0, GlobalConstant.TOTAL_ROW,
						GlobalConstant.DELETE_STATUS_0));
			}
		}
		model.addAttribute("listProduct", listProduct);
		return ViewNameConstant.INDEX;
	}

	@GetMapping({ URLConstant.CAT, URLConstant.CAT_PAGINATION })
	public String cat(@PathVariable(required = false) Integer page, @PathVariable int catId, Model model) {
		int currentPage = GlobalConstant.DEFAULT_PAGE;
		if (page != null) {
			if (page < GlobalConstant.DEFAULT_PAGE) {
				return "redirect:/" + URLConstant.INDEX;
			}
			currentPage = page;
		}
		if (categoryService.findById(catId, GlobalConstant.DELETE_STATUS_0) == null) {
			return "redirect:/" + URLConstant.INDEX;
		}
		List<Integer> listCatId = new ArrayList<Integer>();
		listCatId.add(catId);
		listCatId = categoryUtil.findCatIdByParentId(listCatId, catId);
		int offset = PageUtil.getOffset(currentPage);
		int totalRow = productService.totalRowByCatId(listCatId, GlobalConstant.DELETE_STATUS_0);
		int totalPage = PageUtil.getTotalPage(totalRow);
		List<Product> listProduct = productService.findByCatId(listCatId, offset, GlobalConstant.TOTAL_ROW,
				GlobalConstant.DELETE_STATUS_0);
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("totalPage", totalPage);
		model.addAttribute("totalRow", totalRow);
		model.addAttribute("listProduct", listProduct);
		model.addAttribute("multiLevelCat", categoryUtil.getListCatParent(catId, model));
		return ViewNameConstant.CAT;
	}

	@GetMapping(URLConstant.DETAIL)
	public String detail(@PathVariable int productId, Model model, HttpSession session) {
		Product product = productService.findById(productId, GlobalConstant.DELETE_STATUS_0);
		if (product == null) {
			return "redirect:/" + URLConstant.INDEX;
		}
		// update view
		int userId = 0;
		User userLogin = (User) session.getAttribute("userLogin");
		if (userLogin != null) {
			userId = userLogin.getUserId();
		}
		String ipAddress = StringUtil.getIpAddress();
		StringBuilder sb = new StringBuilder();
		sb.append("views").append(productId).append(userId).append(ipAddress);
		boolean check = false;
		if (session.getAttribute(sb.toString()) != null) {
			String date = (String) session.getAttribute(sb.toString());
			check = DateUtil.checkDateTime(date);
		} else {
			check = true;
		}
		if (check) {
			// tăng view +1
			if (productService.updateView(product) > 0) {
				session.setAttribute(sb.toString(), DateUtil.getDateTime());
				product.setProductView(product.getProductView() + 1);
			}
		}
		List<ProductPicture> listPicture = pictureService.findByProductId(productId);
		listPicture.add(0, new ProductPicture(product.getProductImage()));
		// get product related
		List<Integer> listCatId = new ArrayList<Integer>();
		listCatId.add(product.getCat().getCatId());
		listCatId = categoryUtil.findCatIdByParentId(listCatId, product.getCat().getCatId());
		model.addAttribute("product", product);
		model.addAttribute("listPicture", listPicture);
		model.addAttribute("productRelate",
				productService.getProductRelate(listCatId, productId, GlobalConstant.DELETE_STATUS_0));
		model.addAttribute("listReviews",
				reviewsService.findByProductId(productId, 0, GlobalConstant.TOTAL_ROW_REVIEWS));
		model.addAttribute("totalRowRating", reviewsService.totalRowByProductId(productId));
		model.addAttribute("ratingAverage",
				(double) Math.round(reviewsService.getRatingAverageByProductId(productId) * 10) / 10);
		model.addAttribute("listRatingCount", reviewsService.listRatingCount(productId));
		model.addAttribute("multiLevelCat", categoryUtil.getListCatParent(product.getCat().getCatId(), model));
		return ViewNameConstant.DETAIL;
	}

	// view reviews
	@PostMapping(URLConstant.AJAX_REVIEWS_OLDER)
	@ResponseBody
	public String olderReviews(@RequestParam int productId, @RequestParam int currentPage) {
		int totalRow = reviewsService.totalRowByProductId(productId);
		int totalPage = PageUtil.getTotalPageReviews(totalRow);
		if (currentPage == totalPage) {
			return new Gson()
					.toJson(new AjaxStatus(1, messageSource.getMessage("noReviewsOlder", null, Locale.getDefault())));
		}
		int offset = PageUtil.getOffsetReviews(++currentPage);
		List<Reviews> listReviews = reviewsService.findByProductId(productId, offset, GlobalConstant.TOTAL_ROW_REVIEWS);
		return new Gson().toJson(listReviews);
	}

	@PostMapping(URLConstant.AJAX_REVIEWS_NEWER)
	@ResponseBody
	public String newerReviews(@RequestParam int productId, @RequestParam int currentPage) {
		if (currentPage == GlobalConstant.DEFAULT_PAGE) {
			return new Gson()
					.toJson(new AjaxStatus(1, messageSource.getMessage("noReviewsNewer", null, Locale.getDefault())));
		}
		int offset = PageUtil.getOffsetReviews(--currentPage);
		List<Reviews> listReviews = reviewsService.findByProductId(productId, offset, GlobalConstant.TOTAL_ROW_REVIEWS);
		return new Gson().toJson(listReviews);
	}

}
