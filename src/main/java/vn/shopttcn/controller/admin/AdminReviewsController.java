package vn.shopttcn.controller.admin;

import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
import vn.shopttcn.model.Reviews;
import vn.shopttcn.service.ReviewsService;
import vn.shopttcn.util.PageUtil;
import vn.shopttcn.util.bean.AjaxStatus;

@Controller
@RequestMapping(URLConstant.ADMIN_REVIEWS_INDEX)
public class AdminReviewsController {

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private ReviewsService reviewsService;

	@GetMapping({ GlobalConstant.EMPTY, URLConstant.ADMIN_PAGINATION, URLConstant.ADMIN_SEARCH,
			URLConstant.ADMIN_REVIEWS_SEARCH_PAGINATION })
	public String index(@PathVariable(required = false) Integer page,
			@PathVariable(required = false) String productNameURL, @PathVariable(required = false) Integer ratingURL,
			@PathVariable(required = false) Integer statusURL, @RequestParam(required = false) String productName,
			@RequestParam(required = false) Integer rating, @RequestParam(required = false) Integer status, Model model,
			RedirectAttributes ra) {
		int currentPage = GlobalConstant.DEFAULT_PAGE;
		if (page != null) {
			if (page < GlobalConstant.DEFAULT_PAGE) {
				ra.addFlashAttribute("error", messageSource.getMessage("pageError", null, Locale.getDefault()));
				return "redirect:/" + URLConstant.ADMIN_REVIEWS_INDEX;
			}
			currentPage = page;
		}
		int offset = PageUtil.getOffset(currentPage);
		int totalRow = reviewsService.totalRow();
		int totalPage = PageUtil.getTotalPage(totalRow);
		List<Reviews> listReviews = reviewsService.getList(offset, GlobalConstant.TOTAL_ROW);
		if (productNameURL != null) {
			productName = productNameURL;
		}
		if (ratingURL != null) {
			rating = ratingURL;
		}
		if (statusURL != null) {
			status = statusURL;
		}
		if (productName != null || rating != null || status != null) {
			if (productName.equals(GlobalConstant.EMPTY) && rating == -1 && status == -1) {
				ra.addFlashAttribute("error", messageSource.getMessage("searchError", null, Locale.getDefault()));
				return "redirect:/" + URLConstant.ADMIN_REVIEWS_INDEX;
			}
			model.addAttribute("productName", productName);
			model.addAttribute("rating", rating);
			model.addAttribute("status", status);
			totalRow = reviewsService.totalRowSearch(productName, rating, status);
			totalPage = PageUtil.getTotalPage(totalRow);
			listReviews = reviewsService.search(productName, rating, status, offset, GlobalConstant.TOTAL_ROW);
		}
		model.addAttribute("listReviews", listReviews);
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("totalPage", totalPage);
		return ViewNameConstant.ADMIN_REVIEWS_INDEX;
	}

	// ADMIN_REVIEWS_UPDATE_STATUS
	@PostMapping(URLConstant.ADMIN_REVIEWS_UPDATE_STATUS)
	@ResponseBody
	public String updateStatus(@RequestParam int reviewsId, @RequestParam int status) {
		Reviews reviews = new Reviews(reviewsId, status);
		if (reviewsService.updateStatus(reviews) > 0) {
			String message = GlobalConstant.EMPTY;
			if (status == 0) {
				message = messageSource.getMessage("deleteReviewsSuccess", null, Locale.getDefault());
			} else {
				message = messageSource.getMessage("restoreReviewsSuccess", null, Locale.getDefault());
			}
			return new Gson().toJson(new AjaxStatus(0, message));
		}
		return new Gson().toJson(new AjaxStatus(1, messageSource.getMessage("error", null, Locale.getDefault())));
	}

}
