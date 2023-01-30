package fis.com.vn.controller;

import java.util.Date;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import fis.com.vn.common.CommonUtils;
import fis.com.vn.common.Paginate;
import fis.com.vn.common.StringUtils;
import fis.com.vn.contains.Contains;
import fis.com.vn.exception.ErrorException;
import fis.com.vn.repository.BusinessRepository;
import fis.com.vn.repository.EkycDoanhNghiepRepository;
import fis.com.vn.repository.UserGroupRepository;
import fis.com.vn.table.Business;
import fis.com.vn.table.EkycDoanhNghiepTable;
import fis.com.vn.table.UserGroup;
import fis.com.vn.table.UserInfo;

@Controller
public class BusinessController extends BaseController {

	@Autowired
	BusinessRepository businessRepository;
	@Autowired
	UserGroupRepository userGroupRepository;

	@Autowired
	EkycDoanhNghiepRepository ekycDoanhNghiepRepository;

	@GetMapping(value = "/doanh-nghiep")
	public String nguoiDung(Model model, HttpServletRequest req, @RequestParam Map<String, String> allParams) {
		handlingGet(allParams, model, req);
		forwartParams(allParams, model);
		return "quanlydoanhnghiep/doanhnghiep";
	}

	private void handlingGet(Map<String, String> allParams, Model model, HttpServletRequest req) {
		Paginate paginate = new Paginate(allParams.get("page"), allParams.get("limit"));
		// clear all param if reset
		if (allParams.get("reset") != null) {
			allParams.clear();
		}

		Page<Business> business = businessRepository.selectParams(getStringParams(allParams, "b_uname"),
				getStringParams(allParams, "b_fname"), getStringParams(allParams, "b_email"),
				getIntParams(allParams, "b_status"), getPageable(allParams, paginate));

		model.addAttribute("currentPage", paginate.getPage());
		model.addAttribute("totalPage", business.getTotalPages());
		model.addAttribute("totalElement", business.getTotalElements());
		model.addAttribute("business", business.getContent());
	}

	@GetMapping(value = "/doanh-nghiep/them-moi")
	public String getguoiDungThemMoi(Model model, HttpServletRequest req, @RequestParam Map<String, String> allParams)
			throws JsonProcessingException {
		Iterable<UserGroup> userGroups = userGroupRepository.findByStatus(Contains.TT_NHOM_HOATDONG);
		// model.addAttribute("thanhPhos", layTinhThanhPho());
		model.addAttribute("userGroups", userGroups);
		model.addAttribute("business", new Business());
		model.addAttribute("name", language.getMessage("them_moi"));
		forwartParams(allParams, model);
		return "quanlydoanhnghiep/adddoanhnghiep";
	}

	@PostMapping(value = "/doanh-nghiep/them-moi")
	public String addBusiness(Model model, HttpServletRequest req, RedirectAttributes redirectAttributes,
			@RequestParam Map<String, String> allParams, @ModelAttribute("business") Business business) {

		try {
			checkErrorMessage(business);

			Business checkUserName = businessRepository.findByUsername(business.getUsername());
			if (checkUserName != null)
				throw new ErrorException(language.getMessage("ten_dang_nhap_da_ton_tai"));
			Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();

			business.setCreateTime(new Date());
			business.setPassword(CommonUtils.getMD5(business.getPassword()));
			business.setToken(UUID.randomUUID().toString());
			
			businessRepository.save(business);
			EkycDoanhNghiepTable userameDoanhNghiep = ekycDoanhNghiepRepository.findByUsername(business.getUsername());
			System.out.println("user: " + business.getUsername());
			System.out.println("user: " + userameDoanhNghiep);
			if (userameDoanhNghiep == null) {

				EkycDoanhNghiepTable doanhNghiepTable = new EkycDoanhNghiepTable();
				doanhNghiepTable.setUsername(business.getUsername());
				doanhNghiepTable.setUserNameLogin(req.getSession().getAttribute("username").toString());
				doanhNghiepTable.setStep("1");
				ekycDoanhNghiepRepository.save(doanhNghiepTable);
			}

			redirectAttributes.addFlashAttribute("success", language.getMessage("them_thanh_cong"));
		} catch (ErrorException e) {
			redirectAttributes.addFlashAttribute("error", e.getMessage());
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("error", language.getMessage("loi_he_thong"));
		}

		return "redirect:/doanh-nghiep?" + getParamsQuery(allParams);
	}

	@RequestMapping(value = "/doanh-nghiep/xoa", method = { RequestMethod.GET })
	public String delete(Model model, @RequestParam Map<String, String> allParams,
			RedirectAttributes redirectAttributes, HttpServletRequest req) {
		if (!StringUtils.isEmpty(allParams.get("id"))) {
			Business checkDN = businessRepository.findById(Long.valueOf(allParams.get("id"))).get();
			if (checkDN != null && !checkDN.getUsername().equals("supper_admin")) {
				businessRepository.delete(checkDN);
			}
			redirectAttributes.addFlashAttribute("success", language.getMessage("xoa_thanh_cong"));
		} else {
			redirectAttributes.addFlashAttribute("error", language.getMessage("xoa_that_bai"));
		}

		return "redirect:/doanh-nghiep?" + getParamsQuery(allParams);
	}

	@GetMapping(value = "/doanh-nghiep/sua")
	public String getnguoiDungSua(Model model, HttpServletRequest req, @RequestParam Map<String, String> allParams,
			RedirectAttributes redirectAttributes) {
		// model.addAttribute("thanhPhos", layTinhThanhPho());
		try {
			if (StringUtils.isEmpty(allParams.get("id"))) {
				throw new Exception(language.getMessage("sua_that_bai"));
			}
			Optional<Business> business = businessRepository.findById(Long.valueOf(allParams.get("id")));
			if (!business.isPresent()) {
				throw new Exception(language.getMessage("khong_ton_tai_ban_ghi"));
			}

			Iterable<UserGroup> userGroups = userGroupRepository.findByStatus(Contains.TT_NHOM_HOATDONG);

			model.addAttribute("userGroups", userGroups);
			model.addAttribute("business", business.get());
			model.addAttribute("name", language.getMessage("sua"));
			forwartParams(allParams, model);
			return "quanlydoanhnghiep/adddoanhnghiep";
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("error", e.getMessage());
			return "redirect:/doanh-nghiep?" + getParamsQuery(allParams);
		}
	}

	@PostMapping(value = "/doanh-nghiep/sua")
	public String editBusiness(Model model, HttpServletRequest req, RedirectAttributes redirectAttributes,
			@RequestParam Map<String, String> allParams, @ModelAttribute("Business") Business business) {
		// model.addAttribute("thanhPhos", layTinhThanhPho());
		try {
			checkErrorMessage(business);

			Business businessDb = businessRepository.findById(Long.valueOf(allParams.get("id"))).get();

			if (!StringUtils.isEmpty(business.getPassword())) {
				business.setPassword(CommonUtils.getMD5(business.getPassword()));
			} else {
				business.setPassword(businessDb.getPassword());
			}

			updateObjectToObject(businessDb, business);

			// if(!StringUtils.isEmpty(userInfoDb.getKhuVuc()) &&
			// userInfoDb.getKhuVuc().indexOf("tatca") != -1)
			// userInfoDb.setKhuVuc(String.join(",", layTinhThanhPho()));

			businessRepository.save(businessDb);

			redirectAttributes.addFlashAttribute("success", language.getMessage("sua_thanh_cong"));
		} catch (ErrorException e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("error", e.getMessage());
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("error", language.getMessage("loi_he_thong"));
			e.printStackTrace();
		}
		return "redirect:/doanh-nghiep?" + getParamsQuery(allParams);
	}
}
