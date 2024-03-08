package vn.shopttcn.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

import vn.shopttcn.constant.URLConstant;
import vn.shopttcn.service.LocationDistrictService;
import vn.shopttcn.service.LocationWardService;

@Controller
public class AddressController {

	@Autowired
	private LocationDistrictService locationDistrictService;

	@Autowired
	private LocationWardService locationWardService;

	@PostMapping(URLConstant.LOCATION_DISTRICT)
	@ResponseBody
	public String getDistrictByProvinceId(@RequestParam int provinceId) {
		return new Gson().toJson(locationDistrictService.findByProvinceId(provinceId));
	}

	@PostMapping(URLConstant.LOCATION_WARD)
	@ResponseBody
	public String getWardByDistrictId(@RequestParam int districtId) {
		return new Gson().toJson(locationWardService.findByDistrictId(districtId));
	}

}
