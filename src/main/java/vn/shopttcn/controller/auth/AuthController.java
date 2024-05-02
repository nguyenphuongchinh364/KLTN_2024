package vn.shopttcn.controller.auth;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import vn.shopttcn.constant.GlobalConstant;
import vn.shopttcn.constant.URLConstant;
import vn.shopttcn.constant.ViewNameConstant;
import vn.shopttcn.controller.api.CallApi_RS;
import vn.shopttcn.model.Address;
import vn.shopttcn.model.Role;
import vn.shopttcn.model.User;
import vn.shopttcn.service.AddressService;
import vn.shopttcn.service.LocationDistrictService;
import vn.shopttcn.service.LocationProvinceService;
import vn.shopttcn.service.LocationWardService;
import vn.shopttcn.service.ProductService;
import vn.shopttcn.service.UserService;
import vn.shopttcn.util.FileUtil;
import vn.shopttcn.validate.AddressValidate;
import vn.shopttcn.validate.UserValidate;

@Controller
public class AuthController {
	

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private ServletContext servletContext;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private LocationProvinceService provinceService;

	@Autowired
	private LocationDistrictService districtService;

	@Autowired
	private LocationWardService wardService;

	@Autowired
	private ProductService productService;

	@Autowired
	private UserService userService;

	@Autowired
	private AddressService addressService;

	@Autowired
	private UserValidate userValidate;

	@Autowired
	private AddressValidate addressValidate;

	@ModelAttribute
	public void saveData(Model model) {
		model.addAttribute("listBestSell", productService.getBestSellProduct(GlobalConstant.DELETE_STATUS_0));
	}

	@GetMapping(URLConstant.LOGIN)
	public String login() {
		return ViewNameConstant.LOGIN;
	}

	@PostMapping(URLConstant.LOGIN)
	public String login(@RequestParam String username, @RequestParam String password, Model model,
			RedirectAttributes ra, HttpSession session) {
		model.addAttribute("username", username);
		User user = userService.findByUsername(username);
		if (user == null) {
			model.addAttribute("error", messageSource.getMessage("loginError", null, Locale.getDefault()));
			return ViewNameConstant.LOGIN;
		}
		// check password
		if (!bCryptPasswordEncoder.matches(password, user.getPassword())) {
			model.addAttribute("error", messageSource.getMessage("loginError", null, Locale.getDefault()));
			return ViewNameConstant.LOGIN;
		}
		// trường hợp tài khoản bị khoá và check role (chỉ tài khoản khách hàng có quyền
		// login vào trang public)
		if (user.getEnabled() != GlobalConstant.ENABLED_USER
				|| user.getRole().getRoleId() != GlobalConstant.ROLE_USER) {
			ra.addFlashAttribute("error", messageSource.getMessage("loginAuthError", null, Locale.getDefault()));
			return "redirect:/" + URLConstant.LOGIN;
		}
		session.setAttribute("userLogin", user); // login success
		
		return "redirect:/" + URLConstant.INDEX;
	}
	
	@GetMapping(URLConstant.LOGOUT)
	public String logout(HttpSession session) {
		session.removeAttribute("userLogin");
		return "redirect:/" + URLConstant.INDEX;
	}

	@GetMapping(URLConstant.REGISTER)
	public String registerAccount(Model model) {
		model.addAttribute("listProvince", provinceService.getAll());
		return ViewNameConstant.REGISTER;
	}

	@Transactional
	@PostMapping(URLConstant.REGISTER)
	public String registerAccount(@Valid @ModelAttribute("userError") User user, BindingResult userRs,
			@Valid @ModelAttribute("addressError") Address address, BindingResult addressRs,
			@RequestParam String confirmPassword, @RequestParam("picture") MultipartFile multipartFile, Model model,
			RedirectAttributes ra) {
		model.addAttribute("listProvince", provinceService.getAll());
		user.setRole(new Role(GlobalConstant.ROLE_USER));
		if (address.getProvince().getProvinceId() > 0) {
			model.addAttribute("listDistrict", districtService.findByProvinceId(address.getProvince().getProvinceId()));
			if (address.getDistrict().getDistrictId() > 0) {
				model.addAttribute("listWard", wardService.findByDistrictId(address.getDistrict().getDistrictId()));
			}
		}
		model.addAttribute("user", user);
		model.addAttribute("address", address);
		userValidate.validate(user, null, userRs);
		userValidate.validatePassword(user, userRs, null, confirmPassword, null, model);
		userValidate.validatePicture(multipartFile, userRs);
		addressValidate.validate(address, addressRs);
		if (userRs.hasErrors() || addressRs.hasErrors()) {
			model.addAttribute("error", messageSource.getMessage("formError", null, Locale.getDefault()));
			return ViewNameConstant.REGISTER;
		}
		if (addressService.save(address) > 0) {
			user.setUserAddress(addressService.getNewAddress());
		} else {
			ra.addFlashAttribute("error", messageSource.getMessage("error", null, Locale.getDefault()));
			return "redirect:/" + URLConstant.REGISTER;
		}
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		String avatar = FileUtil.uploadFile(multipartFile, GlobalConstant.DIR_UPLOAD_IMAGE_AVATAR, servletContext);
		user.setAvatar(avatar);
		if (userService.save(user) > 0) {
			ra.addFlashAttribute("success",
					messageSource.getMessage("registerAccountSuccess", null, Locale.getDefault()));
		} else {
			// không thành công => xoá avatar (nếu có)
			if (!avatar.equals(GlobalConstant.EMPTY)) {
				FileUtil.delFile(avatar, GlobalConstant.DIR_UPLOAD_IMAGE_AVATAR, servletContext);
			}
			ra.addFlashAttribute("error", messageSource.getMessage("error", null, Locale.getDefault()));
		}
		return "redirect:/" + URLConstant.LOGIN;
	}

}
