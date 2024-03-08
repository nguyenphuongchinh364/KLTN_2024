package vn.shopttcn.controller.admin;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.gson.Gson;

import vn.shopttcn.constant.GlobalConstant;
import vn.shopttcn.constant.URLConstant;
import vn.shopttcn.constant.ViewNameConstant;
import vn.shopttcn.model.Product;
import vn.shopttcn.model.ProductPicture;
import vn.shopttcn.service.ProductPictureService;
import vn.shopttcn.service.ProductService;
import vn.shopttcn.util.CategoryUtil;
import vn.shopttcn.util.FileUtil;
import vn.shopttcn.util.PageUtil;
import vn.shopttcn.util.StringUtil;
import vn.shopttcn.util.bean.AjaxStatus;
import vn.shopttcn.validate.ProductPictureValidate;
import vn.shopttcn.validate.ProductValidate;

@Controller
@RequestMapping(URLConstant.ADMIN_PRODUCT_INDEX)
public class AdminProductController {

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private ServletContext servletContext;

	@Autowired
	private ProductService productService;

	@Autowired
	private ProductValidate productValidate;

	@Autowired
	private ProductPictureService pictureService;

	@Autowired
	private ProductPictureValidate pictureValidate;

	@Autowired
	private CategoryUtil categoryUtil;

	@GetMapping({ GlobalConstant.EMPTY, URLConstant.ADMIN_PAGINATION, URLConstant.ADMIN_SEARCH,
			URLConstant.ADMIN_PRODUCT_SEARCH_PAGINATION })
	public String index(@PathVariable(required = false) Integer page,
			@PathVariable(required = false) String productNameURL, @PathVariable(required = false) Integer catIdURL,
			@RequestParam(required = false) String productName, @RequestParam(required = false) Integer catId,
			Model model, RedirectAttributes ra) {
		int currentPage = GlobalConstant.DEFAULT_PAGE;
		if (page != null) {
			if (page < GlobalConstant.DEFAULT_PAGE) {
				ra.addFlashAttribute("error", messageSource.getMessage("pageError", null, Locale.getDefault()));
				return "redirect:/" + URLConstant.ADMIN_PRODUCT_INDEX;
			}
			currentPage = page;
		}
		int offset = PageUtil.getOffset(currentPage);
		int totalRow = productService.totalRow(GlobalConstant.DELETE_STATUS_0);
		int totalPage = PageUtil.getTotalPage(totalRow);
		List<Product> listProduct = productService.getList(offset, GlobalConstant.TOTAL_ROW,
				GlobalConstant.DELETE_STATUS_0);
		if (productNameURL != null) {
			productName = productNameURL;
		}
		if (catIdURL != null) {
			catId = catIdURL;
		}
		if (productName != null || catId != null) {
			if (productName.equals(GlobalConstant.EMPTY) && catId == 0) {
				ra.addFlashAttribute("error", messageSource.getMessage("searchError", null, Locale.getDefault()));
				return "redirect:/" + URLConstant.ADMIN_PRODUCT_INDEX;
			}
			List<Integer> listCatId = null;
			if (catId != 0) {
				listCatId = new ArrayList<Integer>();
				listCatId.add(catId);
				listCatId = categoryUtil.findCatIdByParentId(listCatId, catId);
			}
			model.addAttribute("productName", productName);
			model.addAttribute("catId", catId);
			totalRow = productService.totalRowSearch(productName, listCatId, GlobalConstant.DELETE_STATUS_0);
			totalPage = PageUtil.getTotalPage(totalRow);
			listProduct = productService.search(productName, listCatId, offset, GlobalConstant.TOTAL_ROW,
					GlobalConstant.DELETE_STATUS_0);
		}
		model.addAttribute("listProduct", listProduct);
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("totalPage", totalPage);
		return ViewNameConstant.ADMIN_PRODUCT_INDEX;
	}

	@GetMapping(URLConstant.ADMIN_ADD)
	public String add() {
		return ViewNameConstant.ADMIN_PRODUCT_ADD;
	}

	@Transactional
	@PostMapping(URLConstant.ADMIN_ADD)
	public String add(@Valid @ModelAttribute("productError") Product product, BindingResult productRs,
			@Valid @ModelAttribute("pictureError") ProductPicture picture, BindingResult pictureRs,
			@RequestParam("mainImage") MultipartFile multipartFile,
			@RequestParam("picture") List<MultipartFile> listMultipartFile, Model model, RedirectAttributes ra) {
		model.addAttribute("objProduct", product);
		productValidate.validateAddProduct(product, productRs);
		productValidate.validateCatId(product.getCat().getCatId(), productRs);
		pictureValidate.validatePicture(productRs, multipartFile);
		pictureValidate.validateMultiplePicture(pictureRs, listMultipartFile, GlobalConstant.NON_REQUIRED);
		if (productRs.hasErrors() || pictureRs.hasErrors()) {
			model.addAttribute("error", messageSource.getMessage("formError", null, Locale.getDefault()));
			return ViewNameConstant.ADMIN_PRODUCT_ADD;
		}
		// upload hình ảnh phụ (product picture)
		List<String> listFileName = FileUtil.uploadMultipleFile(listMultipartFile,
				GlobalConstant.DIR_UPLOAD_IMAGE_PRODUCT, servletContext);
		// upload hình ảnh chính
		String mainPicture = FileUtil.uploadFile(multipartFile, GlobalConstant.DIR_UPLOAD_IMAGE_PRODUCT,
				servletContext);
		product.setProductImage(mainPicture);
		product.setProductSlug(StringUtil.makeSlug(product.getProductName()));
		if (productService.save(product) > 0) {
			Product productNew = productService.getNewProduct();
			ProductPicture pictureOther = new ProductPicture(productNew.getProductId());
			boolean check = true;
			if (listFileName.size() > 0) {
				// nếu có upload hình ảnh phụ => insert table product picture
				for (String fileName : listFileName) {
					pictureOther.setPictureName(fileName);
					int kq = pictureService.save(pictureOther);
					if (kq == 0) {
						check = false; // insert lỗi
						break;
					}
				}
			}
			if (check) {
				ra.addFlashAttribute("success", messageSource.getMessage("addSuccess", null, Locale.getDefault()));
			} else {
				// nếu lỗi => xoá hình vừa upload
				FileUtil.delFile(mainPicture, GlobalConstant.DIR_UPLOAD_IMAGE_PRODUCT, servletContext);
				FileUtil.delMultipleFileByName(listFileName, GlobalConstant.DIR_UPLOAD_IMAGE_PRODUCT, servletContext);
				ra.addFlashAttribute("error", messageSource.getMessage("error", null, Locale.getDefault()));
			}
		} else {
			ra.addFlashAttribute("error", messageSource.getMessage("error", null, Locale.getDefault()));
		}
		return "redirect:/" + URLConstant.ADMIN_PRODUCT_INDEX;
	}

	@GetMapping(URLConstant.ADMIN_PRODUCT_UPDATE)
	public String update(@PathVariable int productId, Model model, RedirectAttributes ra) {
		Product product = productService.findById(productId, GlobalConstant.DELETE_STATUS_0);
		if (product == null) {
			ra.addFlashAttribute("error", messageSource.getMessage("noDataError", null, Locale.getDefault()));
			return "redirect:/" + URLConstant.ADMIN_PRODUCT_INDEX;
		}
		model.addAttribute("objProduct", product);
		return ViewNameConstant.ADMIN_PRODUCT_UPDATE;
	}

	@PostMapping(URLConstant.ADMIN_PRODUCT_UPDATE)
	public String update(@Valid @ModelAttribute("productError") Product product, BindingResult rs,
			RedirectAttributes ra, Model model) {
		model.addAttribute("objProduct", product);
		productValidate.validateUpdateProduct(product, rs);
		productValidate.validateCatId(product.getCat().getCatId(), rs);
		if (rs.hasErrors()) {
			model.addAttribute("error", messageSource.getMessage("formError", null, Locale.getDefault()));
			return ViewNameConstant.ADMIN_PRODUCT_UPDATE;
		}
		product.setProductSlug(StringUtil.makeSlug(product.getProductName()));
		if (productService.update(product) > 0) {
			ra.addFlashAttribute("success", messageSource.getMessage("updateSuccess", null, Locale.getDefault()));
		} else {
			ra.addFlashAttribute("error", messageSource.getMessage("error", null, Locale.getDefault()));
		}
		return "redirect:/" + URLConstant.ADMIN_PRODUCT_INDEX;
	}

	@GetMapping(URLConstant.ADMIN_DELETE)
	public String delete(@PathVariable int id, Model model, RedirectAttributes ra) {
		Product product = productService.findById(id, GlobalConstant.DELETE_STATUS_0);
		if (product == null) {
			ra.addFlashAttribute("error", messageSource.getMessage("noDataError", null, Locale.getDefault()));
			return "redirect:/" + URLConstant.ADMIN_PRODUCT_INDEX;
		}
		product.setDeleteStatus(GlobalConstant.DELETE_STATUS_1); // update status => xoá mềm
		if (productService.updateDeleteStatus(product) > 0) {
			ra.addFlashAttribute("success", messageSource.getMessage("deleteSuccess", null, Locale.getDefault()));
		} else {
			ra.addFlashAttribute("error", messageSource.getMessage("error", null, Locale.getDefault()));
		}
		return "redirect:/" + URLConstant.ADMIN_PRODUCT_INDEX;
	}

	@GetMapping(URLConstant.ADMIN_PRODUCT_PICTURE)
	public String picture(@PathVariable Integer productId, Model model, RedirectAttributes ra) {
		Product product = productService.findById(productId, GlobalConstant.DELETE_STATUS_0);
		if (product == null) {
			ra.addFlashAttribute("error", messageSource.getMessage("noDataError", null, Locale.getDefault()));
			return "redirect:/" + URLConstant.ADMIN_PRODUCT_INDEX;
		}
		List<ProductPicture> listPicture = pictureService.findByProductId(product.getProductId());
		model.addAttribute("objProduct", product);
		model.addAttribute("listPicture", listPicture);
		return ViewNameConstant.ADMIN_PRODUCT_PICTURE;
	}

	@Transactional
	@PostMapping(URLConstant.ADMIN_PRODUCT_PICTURE)
	public String addPicture(@Valid @ModelAttribute("pictureError") ProductPicture picture, BindingResult rs,
			@RequestParam("picture") List<MultipartFile> listMultipartFile, @ModelAttribute Product product,
			RedirectAttributes ra, Model model) {
		// nếu số lượng hình (trong db) + số hình upload > số lượng hình (max) => lỗi
		int totalPicture = pictureService.rowCountByProductId(product.getProductId()) + listMultipartFile.size() + 1;
		if (totalPicture > GlobalConstant.MAX_PICTURE_QUANTITY) {
			ra.addFlashAttribute("error", messageSource.getMessage("quantityPictureError", null, Locale.getDefault()));
			return "redirect:/" + URLConstant.ADMIN_PRODUCT_INDEX + "/" + URLConstant.ADMIN_PRODUCT_PICTURE;
		}
		List<ProductPicture> listPicture = pictureService.findByProductId(product.getProductId());
		model.addAttribute("objProduct", product);
		model.addAttribute("listPicture", listPicture);
		pictureValidate.validateMultiplePicture(rs, listMultipartFile, GlobalConstant.REQUIRED);
		if (rs.hasErrors()) {
			return ViewNameConstant.ADMIN_PRODUCT_PICTURE;
		}
		List<String> listFileName = FileUtil.uploadMultipleFile(listMultipartFile,
				GlobalConstant.DIR_UPLOAD_IMAGE_PRODUCT, servletContext);
		picture.setProductId(product.getProductId());
		// insert database
		boolean check = true;
		if (listFileName.size() > 0) {
			for (String fileName : listFileName) {
				picture.setPictureName(fileName);
				int kq = pictureService.save(picture);
				if (kq == 0) {
					check = false; // insert lỗi
					break;
				}
			}
		}
		if (check) {
			ra.addFlashAttribute("success", messageSource.getMessage("addSuccess", null, Locale.getDefault()));
		} else {
			// nếu lỗi => xoá hình vừa upload
			FileUtil.delMultipleFileByName(listFileName, GlobalConstant.DIR_UPLOAD_IMAGE_PRODUCT, servletContext);
			ra.addFlashAttribute("error", messageSource.getMessage("error", null, Locale.getDefault()));
		}
		return "redirect:/" + URLConstant.ADMIN_PRODUCT_INDEX + "/" + URLConstant.ADMIN_PRODUCT_PICTURE;
	}

	@GetMapping(URLConstant.ADMIN_PRODUCT_PICTURE_DELETE_BY_ID)
	public String deletePicture(@PathVariable Integer pictureId, @PathVariable Integer productId, Model model,
			RedirectAttributes ra) {
		// nếu k có data trong db => lỗi
		if (productService.findById(productId, GlobalConstant.DELETE_STATUS_0) == null) {
			ra.addFlashAttribute("error", messageSource.getMessage("noDataError", null, Locale.getDefault()));
			return "redirect:/" + URLConstant.ADMIN_PRODUCT_INDEX;
		}
		ProductPicture picture = pictureService.findById(pictureId);
		if (picture == null) {
			ra.addFlashAttribute("error", messageSource.getMessage("noDataError", null, Locale.getDefault()));
			return "redirect:/" + URLConstant.ADMIN_PRODUCT_INDEX + "/picture/" + productId;
		}
		if (pictureService.del(pictureId) > 0) {
			// Thành công => xoá file trên server
			FileUtil.delFile(picture.getPictureName(), GlobalConstant.DIR_UPLOAD_IMAGE_PRODUCT, servletContext);
			ra.addFlashAttribute("success", messageSource.getMessage("deleteSuccess", null, Locale.getDefault()));
		} else {
			ra.addFlashAttribute("error", messageSource.getMessage("error", null, Locale.getDefault()));
		}
		return "redirect:/" + URLConstant.ADMIN_PRODUCT_INDEX + "/picture/" + productId;
	}

	// Xoá nhiều
	@PostMapping(URLConstant.ADMIN_PRODUCT_PICTURE_DELETE)
	public String deletePicture(@RequestParam(value = "pictureDelete", required = false) int[] listPictureId,
			@RequestParam int productId, Model model, RedirectAttributes ra, HttpServletRequest request) {
		if (listPictureId == null) {
			ra.addFlashAttribute("error",
					messageSource.getMessage("notSelectedImageDelete", null, Locale.getDefault()));
			return "redirect:/" + URLConstant.ADMIN_PRODUCT_INDEX + "/picture/" + productId;
		}
		boolean check = true;
		for (int pictureId : listPictureId) {
			ProductPicture picture = pictureService.findById(pictureId);
			if (picture != null) {
				if (pictureService.del(pictureId) > 0) {
					// thành công => xoá file trên server
					FileUtil.delFile(picture.getPictureName(), GlobalConstant.DIR_UPLOAD_IMAGE_PRODUCT, servletContext);
				} else {
					check = false;
				}
			}
		}
		if (check) {
			ra.addFlashAttribute("success", messageSource.getMessage("deleteSuccess", null, Locale.getDefault()));
		} else {
			ra.addFlashAttribute("error", messageSource.getMessage("error", null, Locale.getDefault()));
		}
		return "redirect:/" + URLConstant.ADMIN_PRODUCT_INDEX + "/picture/" + productId;
	}

	// Xử lý chức năng đặt làm hình ảnh chính (Quản lý hình ảnh)
	@Transactional
	@PostMapping(URLConstant.ADMIN_PRODUCT_PICTURE_SET_MAIN_IMAGE)
	@ResponseBody
	public String setMainImage(@RequestParam int pictureId) {
		ProductPicture picture = pictureService.findById(pictureId);
		if (picture == null) {
			return new Gson()
					.toJson(new AjaxStatus(1, messageSource.getMessage("noDataError", null, Locale.getDefault())));
		}
		Product product = productService.findById(picture.getProductId(), GlobalConstant.DELETE_STATUS_0);
		if (product == null) {
			return new Gson()
					.toJson(new AjaxStatus(1, messageSource.getMessage("noDataError", null, Locale.getDefault())));
		}
		String mainImage = product.getProductImage(); // image chính trước update
		// swap image
		product.setProductImage(picture.getPictureName());
		picture.setPictureName(mainImage);
		// update to database
		if (productService.updateImage(product) > 0) {
			if (pictureService.update(picture) > 0) {
				return new Gson().toJson(
						new AjaxStatus(0, messageSource.getMessage("setMainImageSuccess", null, Locale.getDefault())));
			}
		}
		return new Gson().toJson(new AjaxStatus(1, messageSource.getMessage("error", null, Locale.getDefault())));
	}

}
