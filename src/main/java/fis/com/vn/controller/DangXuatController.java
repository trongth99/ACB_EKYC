package fis.com.vn.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DangXuatController {
	@GetMapping(value = "/logout")
	public String get(Model model, HttpServletRequest req) {
		req.getSession().removeAttribute("username");

		return "redirect:/login";
	}

	@GetMapping(value = "/logout-ekyc-doanh-nghiep")
	public String logout(Model model, HttpServletRequest req) {
		req.getSession().removeAttribute("b_username");

		return "loginbusiness";
	}
}
