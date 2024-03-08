package vn.shopttcn.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import vn.shopttcn.constant.GlobalConstant;
import vn.shopttcn.constant.URLConstant;
import vn.shopttcn.constant.ViewNameConstant;

@Controller
@RequestMapping(URLConstant.ADMIN_INDEX)
public class AdminIndexController {

	@GetMapping(GlobalConstant.EMPTY)
	public String index() {
		return ViewNameConstant.ADMIN_INDEX;
	}

}
