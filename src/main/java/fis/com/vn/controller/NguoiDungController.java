package fis.com.vn.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.core.JsonProcessingException;

import fis.com.vn.common.CommonUtils;
import fis.com.vn.common.Paginate;
import fis.com.vn.common.StringUtils;
import fis.com.vn.contains.Contains;
import fis.com.vn.exception.ErrorException;
import fis.com.vn.repository.UserGroupRepository;
import fis.com.vn.repository.UserInfoDtoRepository;
import fis.com.vn.repository.UserInfoRepository;
import fis.com.vn.table.UserGroup;
import fis.com.vn.table.UserInfo;
import fis.com.vn.table.UserInfoDto;

@Controller
public class NguoiDungController extends BaseController {
    @Autowired UserInfoRepository userInfoRepository;
    @Autowired UserInfoDtoRepository userInfoDtoRepository;
    @Autowired UserGroupRepository userGroupRepository;

    @GetMapping(value = "/nguoi-dung")
    public String nguoiDung(Model model, HttpServletRequest req, @RequestParam Map<String, String> allParams) {
        handlingGet(allParams, model, req);
        forwartParams(allParams, model);
        return "nguoidung/nguoidung";
    }

    private void handlingGet(Map<String, String> allParams, Model model, HttpServletRequest req) {
        Paginate paginate = new Paginate(allParams.get("page"), allParams.get("limit"));
        // clear all param if reset
        if (allParams.get("reset") != null) {
            allParams.clear();
        }

        Page<UserInfoDto> userInfos = userInfoDtoRepository.selectParams(
                getStringParams(allParams, "s_uname"),
                getStringParams(allParams, "s_fname"),
                getStringParams(allParams, "s_email"),
                getIntParams(allParams, "s_status"),
                getPageable(allParams, paginate));

        model.addAttribute("currentPage", paginate.getPage());
        model.addAttribute("totalPage", userInfos.getTotalPages());
        model.addAttribute("totalElement", userInfos.getTotalElements());
        model.addAttribute("userInfos", userInfos.getContent());
    }

    @GetMapping(value = "/nguoi-dung/them-moi")
    public String getguoiDungThemMoi(Model model, HttpServletRequest req, @RequestParam Map<String, String> allParams) throws JsonProcessingException {
        Iterable<UserGroup> userGroups = userGroupRepository.findByStatus(Contains.TT_NHOM_HOATDONG);
        model.addAttribute("thanhPhos", layTinhThanhPho());
        model.addAttribute("userGroups", userGroups);
        model.addAttribute("userInfo", new UserInfo());
        model.addAttribute("name", language.getMessage("them_moi_config"));
        forwartParams(allParams, model);
        return "nguoidung/addnguoidung";
    }
    
    @PostMapping(value = "/nguoi-dung/them-moi")
    public String postnguoiDungThemMoi(Model model, HttpServletRequest req, RedirectAttributes redirectAttributes,
                                       @RequestParam Map<String, String> allParams, @ModelAttribute("userInfo") UserInfo userInfo) {
        try {
            checkErrorMessage(userInfo);

            if (StringUtils.isEmpty(userInfo.getGroupId())) throw new ErrorException(language.getMessage("chon_nhom_nguoi_dung"));
            UserInfo checkUserName = userInfoRepository.findByUsername(userInfo.getUsername());
            if (checkUserName != null) throw new ErrorException(language.getMessage("ten_dang_nhap_da_ton_tai"));

            if(StringUtils.isEmpty(userInfo.getKhuVuc())) userInfo.setKhuVuc(String.join(",", layTinhThanhPho()));
            
            userInfo.setCreateBy(getUserName(req));
            userInfo.setCreateTime(new Date());
            userInfo.setPassword(CommonUtils.getMD5(userInfo.getPassword()));

            userInfoRepository.save(userInfo);

            redirectAttributes.addFlashAttribute("success", language.getMessage("them_thanh_cong"));
        } catch (ErrorException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "loi_he_thong");
        }
        model.addAttribute("thanhPhos", layTinhThanhPho());
        return "redirect:/nguoi-dung?"+ getParamsQuery(allParams);
    }

    @GetMapping(value = "/nguoi-dung/sua")
    public String getnguoiDungSua(Model model, HttpServletRequest req, @RequestParam Map<String, String> allParams, RedirectAttributes redirectAttributes) {
    	model.addAttribute("thanhPhos", layTinhThanhPho());
        try {
            if (StringUtils.isEmpty(allParams.get("id"))) {
                throw new Exception(language.getMessage("sua_that_bai"));
            }
            Optional<UserInfo> userInfo = userInfoRepository.findById(Long.valueOf(allParams.get("id")));
            if (!userInfo.isPresent()) {
                throw new Exception(language.getMessage("khong_ton_tai_ban_ghi"));
            }

            Iterable<UserGroup> userGroups = userGroupRepository.findByStatus(Contains.TT_NHOM_HOATDONG);

            model.addAttribute("userGroups", userGroups);
            model.addAttribute("userInfo", userInfo.get());
            model.addAttribute("name", language.getMessage("sua"));
            forwartParams(allParams, model);
            return "nguoidung/addnguoidung";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/nguoi-dung?"+ getParamsQuery(allParams);
        }
    }
    
    @PostMapping(value = "/nguoi-dung/sua")
    public String postnguoiDungSua(Model model, HttpServletRequest req, RedirectAttributes redirectAttributes,
                                   @RequestParam Map<String, String> allParams, @ModelAttribute("UserInfo") UserInfo userInfo) {
    	model.addAttribute("thanhPhos", layTinhThanhPho());
        try {
            checkErrorMessage(userInfo);

            UserInfo userInfoDb = userInfoRepository.findById(Long.valueOf(allParams.get("id"))).get();

            if (!StringUtils.isEmpty(userInfo.getPassword())) {
                userInfo.setPassword(CommonUtils.getMD5(userInfo.getPassword()));
            } else {
            	userInfo.setPassword(userInfoDb.getPassword());
            }

            updateObjectToObject(userInfoDb, userInfo);

            if(!StringUtils.isEmpty(userInfoDb.getKhuVuc()) && userInfoDb.getKhuVuc().indexOf("tatca") != -1) userInfoDb.setKhuVuc(String.join(",", layTinhThanhPho()));
            
            userInfoRepository.save(userInfoDb);

            redirectAttributes.addFlashAttribute("success", language.getMessage("sua_thanh_cong"));
        } catch (ErrorException e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", language.getMessage("loi_he_thong"));
            e.printStackTrace();
        }
        return "redirect:/nguoi-dung?"+ getParamsQuery(allParams);
    }

    @RequestMapping(value = "/nguoi-dung/xoa", method = {RequestMethod.GET})
    public String delete(Model model, @RequestParam Map<String, String> allParams,
                         RedirectAttributes redirectAttributes, HttpServletRequest req) {
        if (!StringUtils.isEmpty(allParams.get("id"))) {
            UserInfo checkNd = userInfoRepository
                    .findById(Long.valueOf(allParams.get("id"))).get();
            if (checkNd != null && !checkNd.getUsername().equals("supper_admin")) {
                userInfoRepository.delete(checkNd);
            }
            redirectAttributes.addFlashAttribute("success", language.getMessage("xoa_thanh_cong"));
        } else {
            redirectAttributes.addFlashAttribute("error", language.getMessage("xoa_that_bai"));
        }

        return "redirect:/nguoi-dung?" + getParamsQuery(allParams);
    }
    @GetMapping("/api/user-info")
    @ResponseBody
    public long nguoiDungValid(@RequestParam("name") String name) {
        return userInfoRepository.countByUsername(name);
    }
    
    public ArrayList<String> layTinhThanhPho() {
    	ArrayList<String> arr = new ArrayList<String>();
    	arr.add("H?? N???i");
    	arr.add("H??? Ch?? Minh");
    	arr.add("An Giang");
    	arr.add("B?? R???a - V??ng T??u");
    	arr.add("B??nh D????ng");
    	arr.add("B??nh Ph?????c");
    	arr.add("B??nh Thu???n");
    	arr.add("B??nh ?????nh");
    	arr.add("B???c Li??u");
    	arr.add("B???c Giang");
    	arr.add("B???c K???n");
    	arr.add("B???c Ninh");
    	arr.add("B???n Tre");
    	arr.add("Cao B???ng");
    	arr.add("C?? Mau");
    	arr.add("C???n Th??");
    	arr.add("Gia Lai");
    	arr.add("H???i Ph??ng");
    	arr.add("Ho?? B??nh");
    	arr.add("H?? Giang");
    	arr.add("H?? Nam");
    	arr.add("H?? T??nh");
    	arr.add("H??ng Y??n");
    	arr.add("H???i D????ng");
    	arr.add("H???u Giang");
    	arr.add("Kh??nh H??a");
    	arr.add("Ki??n Giang");
    	arr.add("Kon Tum");
    	arr.add("Lai Ch??u");
    	arr.add("Long An");
    	arr.add("L??o Cai");
    	arr.add("L??m ?????ng");
    	arr.add("L???ng S??n");
    	arr.add("Nam ?????nh");
    	arr.add("Ngh??? An");
    	arr.add("Ninh B??nh");
    	arr.add("Ninh Thu???n");
    	arr.add("Ph?? Th???");
    	arr.add("Ph?? Y??n");
    	arr.add("Qu???ng B??nh");
    	arr.add("Qu???ng Nam");
    	arr.add("Qu???ng Ng??i");
    	arr.add("Qu???ng Ninh");
    	arr.add("Qu???ng Tr???");
    	arr.add("S??c Tr??ng");
    	arr.add("S??n La");
    	arr.add("Thanh H??a");
    	arr.add("Th??i B??nh");
    	arr.add("Th??i Nguy??n");
    	arr.add("Th???a Thi??n Hu???");
    	arr.add("Ti???n Giang");
    	arr.add("Tr?? Vinh");
    	arr.add("Tuy??n Quang");
    	arr.add("T??y Ninh");
    	arr.add("V??nh Long");
    	arr.add("V??nh Ph??c");
    	arr.add("Y??n B??i");
    	arr.add("???? N???ng");
    	arr.add("??i???n Bi??n");
    	arr.add("?????k L???k");
    	arr.add("?????k N??ng");
    	arr.add("?????ng Nai");
    	arr.add("?????ng Th??p");
    	
    	return arr;
    }
}
