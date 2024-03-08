package vn.shopttcn.controller.shop;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

import vn.shopttcn.constant.GlobalConstant;
import vn.shopttcn.constant.URLConstant;
import vn.shopttcn.constant.ViewNameConstant;
import vn.shopttcn.model.Product;
import vn.shopttcn.service.ProductService;
import vn.shopttcn.util.CategoryUtil;
import vn.shopttcn.util.PageUtil;

@Controller
public class SearchController {

	@Autowired
	private ProductService productService;

	@Autowired
	private CategoryUtil categoryUtil;

	@GetMapping({ URLConstant.SEARCH, URLConstant.SEARCH_PAGINATION })
	public String search(@RequestParam(required = false) String keyword, @PathVariable(required = false) Integer page,
			@PathVariable(required = false) String keywordURL, Model model) {
		model.addAttribute("listBestSell", productService.getBestSellProduct(GlobalConstant.DELETE_STATUS_0));
		int currentPage = GlobalConstant.DEFAULT_PAGE;
		if (page != null) {
			if (page < GlobalConstant.DEFAULT_PAGE) {
				return "redirect:/" + URLConstant.INDEX;
			}
			currentPage = page;
		}
		if (keywordURL != null) {
			keyword = keywordURL;
		}
		int offset = PageUtil.getOffset(currentPage);
		int totalRow = productService.totalRowSearch(keyword, null, GlobalConstant.DELETE_STATUS_0);
		int totalPage = PageUtil.getTotalPage(totalRow);
		List<Product> listProduct = productService.search(keyword, null, offset, GlobalConstant.TOTAL_ROW,
				GlobalConstant.DELETE_STATUS_0);
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("totalPage", totalPage);
		model.addAttribute("totalRow", totalRow);
		model.addAttribute("listProduct", listProduct);
		model.addAttribute("keyword", keyword);
		return ViewNameConstant.SEARCH;
	}

	@PostMapping(URLConstant.PRODUCT_FILTER)
	@ResponseBody
	public String filter(@RequestParam String keyword, @RequestParam int catId, @RequestParam int minPrice,
			@RequestParam int maxPrice, Model model) {
		if (keyword == null) {
			keyword = GlobalConstant.EMPTY;
		}
		List<Integer> listCatId = new ArrayList<Integer>();
		if (catId != 0) {
			listCatId.add(catId);
			listCatId = categoryUtil.findCatIdByParentId(listCatId, catId);
		}
		List<Product> listProduct = productService.filter(keyword, listCatId, minPrice, maxPrice);
		return new Gson().toJson(listProduct);
	}

}
