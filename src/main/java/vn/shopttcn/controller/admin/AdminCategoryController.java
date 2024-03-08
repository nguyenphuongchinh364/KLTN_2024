package vn.shopttcn.controller.admin;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import vn.shopttcn.constant.GlobalConstant;
import vn.shopttcn.constant.URLConstant;
import vn.shopttcn.constant.ViewNameConstant;
import vn.shopttcn.model.Category;
import vn.shopttcn.service.CategoryService;
import vn.shopttcn.service.ProductService;
import vn.shopttcn.util.CategoryUtil;
import vn.shopttcn.util.PageUtil;
import vn.shopttcn.util.StringUtil;
import vn.shopttcn.validate.CategoryValidate;

@Controller
@RequestMapping(URLConstant.ADMIN_CAT_INDEX)
public class AdminCategoryController {

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private CategoryValidate categoryValidate;

	@Autowired
	private ProductService productService;

	@Autowired
	private CategoryUtil categoryUtil;

	@GetMapping({ GlobalConstant.EMPTY, URLConstant.ADMIN_PAGINATION, URLConstant.ADMIN_SEARCH,
			URLConstant.ADMIN_CAT_SEARCH_PAGINATION })
	public String index(@PathVariable(required = false) Integer page, @PathVariable(required = false) String catNameURL,
			@RequestParam(required = false) String catName, Model model, RedirectAttributes ra) {
		int currentPage = GlobalConstant.DEFAULT_PAGE;
		if (page != null) {
			if (page < GlobalConstant.DEFAULT_PAGE) {
				ra.addFlashAttribute("error", messageSource.getMessage("pageError", null, Locale.getDefault()));
				return "redirect:/" + URLConstant.ADMIN_CAT_INDEX;
			}
			currentPage = page;
		}
		int offset = PageUtil.getOffset(currentPage);
		int totalRow = categoryService.totalRow(GlobalConstant.DELETE_STATUS_0);
		int totalPage = PageUtil.getTotalPage(totalRow);
		List<Category> listCat = categoryService.getList(offset, GlobalConstant.TOTAL_ROW,
				GlobalConstant.DELETE_STATUS_0);
		if (catNameURL != null) {
			catName = catNameURL;
		}
		if (catName != null) {
			if (catName.equals(GlobalConstant.EMPTY)) {
				ra.addFlashAttribute("error", messageSource.getMessage("searchError", null, Locale.getDefault()));
				return "redirect:/" + URLConstant.ADMIN_CAT_INDEX;
			}
			model.addAttribute("catName", catName);
			totalRow = categoryService.totalRowSearch(catName, GlobalConstant.DELETE_STATUS_0);
			totalPage = PageUtil.getTotalPage(totalRow);
			listCat = categoryService.search(catName, offset, GlobalConstant.TOTAL_ROW, GlobalConstant.DELETE_STATUS_0);
		}
		model.addAttribute("listCat", listCat);
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("totalPage", totalPage);
		return ViewNameConstant.ADMIN_CAT_INDEX;
	}

	@GetMapping(URLConstant.ADMIN_ADD)
	public String add() {
		return ViewNameConstant.ADMIN_CAT_ADD;
	}

	@PostMapping(URLConstant.ADMIN_ADD)
	public String add(@Valid @ModelAttribute("catError") Category category, BindingResult rs, RedirectAttributes ra,
			Model model) {
		model.addAttribute("objCat", category);
		categoryValidate.validateAddCat(category, rs);
		if (rs.hasErrors()) {
			model.addAttribute("error", messageSource.getMessage("formError", null, Locale.getDefault()));
			return ViewNameConstant.ADMIN_CAT_ADD;
		}
		category.setCatSlug(StringUtil.makeSlug(category.getCatName()));
		if (categoryService.save(category) > 0) {
			ra.addFlashAttribute("success", messageSource.getMessage("addSuccess", null, Locale.getDefault()));
		} else {
			ra.addFlashAttribute("error", messageSource.getMessage("error", null, Locale.getDefault()));
		}
		return "redirect:/" + URLConstant.ADMIN_CAT_INDEX;
	}

	@GetMapping(URLConstant.ADMIN_CAT_UPDATE)
	public String update(@PathVariable Integer catId, Model model, RedirectAttributes ra) {
		Category cat = categoryService.findById(catId, GlobalConstant.DELETE_STATUS_0);
		if (cat == null) {
			ra.addFlashAttribute("error", messageSource.getMessage("noDataError", null, Locale.getDefault()));
			return "redirect:/" + URLConstant.ADMIN_CAT_INDEX;
		}
		model.addAttribute("objCat", cat);
		return ViewNameConstant.ADMIN_CAT_UPDATE;
	}

	@PostMapping(URLConstant.ADMIN_CAT_UPDATE)
	public String update(@Valid @ModelAttribute("catError") Category category, BindingResult rs, Model model,
			RedirectAttributes ra) {
		model.addAttribute("objCat", category);
		categoryValidate.validateUpdateCat(category, rs);
		if (rs.hasErrors()) {
			model.addAttribute("error", messageSource.getMessage("formError", null, Locale.getDefault()));
			return ViewNameConstant.ADMIN_CAT_UPDATE;
		}
		category.setCatSlug(StringUtil.makeSlug(category.getCatName()));
		if (categoryService.update(category) > 0) {
			ra.addFlashAttribute("success", messageSource.getMessage("updateSuccess", null, Locale.getDefault()));
		} else {
			ra.addFlashAttribute("error", messageSource.getMessage("error", null, Locale.getDefault()));
		}
		return "redirect:/" + URLConstant.ADMIN_CAT_INDEX;
	}

	// Xoá mềm
	@GetMapping(URLConstant.ADMIN_DELETE)
	public String delete(@PathVariable Integer id, Model model, RedirectAttributes ra) {
		Category category = categoryService.findById(id, GlobalConstant.DELETE_STATUS_0);
		if (category == null) {
			ra.addFlashAttribute("error", messageSource.getMessage("noDataError", null, Locale.getDefault()));
			return "redirect:/" + URLConstant.ADMIN_CAT_INDEX;
		}
		List<Category> listCatByParentId = categoryService.findCatByParentId(category.getCatId(),
				GlobalConstant.DELETE_STATUS_0);
		if (listCatByParentId.size() > 0) {
			ra.addFlashAttribute("error", messageSource.getMessage("deleteCatError", null, Locale.getDefault()));
			return "redirect:/" + URLConstant.ADMIN_CAT_INDEX;
		}
		List<Integer> listCatId = new ArrayList<Integer>();
		listCatId.add(category.getCatId());
		listCatId = categoryUtil.findCatIdByParentId(listCatId, category.getCatId());
		if (productService.totalRowByCatId(listCatId, GlobalConstant.DELETE_STATUS_0) > 0) {
			// có sản phẩm -> k cho xoá
			ra.addFlashAttribute("error", messageSource.getMessage("deleteCatError2", null, Locale.getDefault()));
			return "redirect:/" + URLConstant.ADMIN_CAT_INDEX;
		}
		category.setDeleteStatus(GlobalConstant.DELETE_STATUS_1);
		if (categoryService.updateDeleteStatus(category) > 0) {
			ra.addFlashAttribute("success", messageSource.getMessage("deleteSuccess", null, Locale.getDefault()));
		} else {
			ra.addFlashAttribute("error", messageSource.getMessage("error", null, Locale.getDefault()));
		}
		return "redirect:/" + URLConstant.ADMIN_CAT_INDEX;
	}

}
