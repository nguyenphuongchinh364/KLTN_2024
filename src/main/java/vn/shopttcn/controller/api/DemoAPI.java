package vn.shopttcn.controller.api;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import vn.shopttcn.util.bean.DoanhThu;

@RestController
public class DemoAPI {

	@GetMapping("api/demo")
	public ResponseEntity<List<DoanhThu>> getAll() {
		List<DoanhThu> list = new ArrayList<DoanhThu>();
		list.add(new DoanhThu("Tháng 1", 24000000));
		list.add(new DoanhThu("Tháng 2", 200000000));
		list.add(new DoanhThu("Tháng 3", 120000000));
		list.add(new DoanhThu("Tháng 4", 90000000));
		list.add(new DoanhThu("Tháng 5", 60000000));
		list.add(new DoanhThu("Tháng 6", 45000000));
		list.add(new DoanhThu("Tháng 7", 85000000));
		list.add(new DoanhThu("Tháng 8", 70000000));
		list.add(new DoanhThu("Tháng 9", 124000000));
		list.add(new DoanhThu("Tháng 10", 300000000));
		list.add(new DoanhThu("Tháng 11", 40000000));
		list.add(new DoanhThu("Tháng 12", 65000000));
		if (list.isEmpty()) {
			return new ResponseEntity<List<DoanhThu>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<DoanhThu>>(list, HttpStatus.OK);
	}

}
