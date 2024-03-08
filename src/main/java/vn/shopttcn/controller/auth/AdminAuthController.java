package vn.shopttcn.controller.auth;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import vn.shopttcn.constant.URLConstant;
import vn.shopttcn.constant.ViewNameConstant;

@Controller
public class AdminAuthController {

	@GetMapping(URLConstant.ADMIN_LOGIN)
	public String login(HttpSession session) {
		if (session.getAttribute("adminUserLogin") != null) {
			return "redirect:/" + URLConstant.ADMIN_INDEX;
		}
		return ViewNameConstant.ADMIN_LOGIN;
	}

}
