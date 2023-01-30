package fis.com.vn.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import fis.com.vn.common.CommonUtils;
import fis.com.vn.exception.ErrorException;
import fis.com.vn.repository.BusinessRepository;
import fis.com.vn.repository.EkycDoanhNghiepRepository;
import fis.com.vn.repository.UserModuleRepository;
import fis.com.vn.table.Business;
import fis.com.vn.table.EkycDoanhNghiepTable;
import fis.com.vn.table.UserModule;

@Controller
public class DangNhapDoanhNghiepController extends BaseController{
	private static final Logger LOGGER = LoggerFactory.getLogger(DangNhapDoanhNghiepController.class);
	
    @Autowired BusinessRepository businessRepository;
    @Autowired UserModuleRepository userModuleRepository;
    @Autowired EkycDoanhNghiepRepository ekycDoanhNghiepRepository;

//    @GetMapping(value = "/login-doanh-nghiep")
//    public String get(Model model) {
//        return "loginbusiness";
//    }
//
//    @PostMapping(value = "/login-doanh-nghiep")
//    public String get(Model model, HttpServletRequest req, @RequestParam Map<String, String> allParams) {
//        try {
//        	
//        	LOGGER.info("loginbusiness"+new Date());
//            Business business = businessRepository.findByUsername(getStringParams(allParams, "username"));
//            if (business == null) throw new ErrorException("Username is incorrect");
//            if (!business.getPassword().equals(CommonUtils.getMD5(getStringParams(allParams, "password"))))
//                throw new ErrorException("Password is incorrect");
//            if (business.getStatus() == null || (business.getStatus() != null && business.getStatus() != 1))
//                throw new ErrorException("The account has not activated ");
//            
//			/* String[] groupIds = business.getGroupId().split(","); */
//            
//			/*
//			 * List<UserModule> userModules = userModuleRepository.selectGroupId(groupIds);
//			 */
//            
//           // req.getSession().setAttribute("userModuleMenus", userModules);
//            req.getSession().setAttribute("b_username", business.getUsername());
//            req.getSession().setAttribute("b_fullName", business.getFullName());
//            req.getSession().setAttribute("b_email", business.getEmail());
//            req.getSession().setAttribute("b_userid", business.getId());
//            req.getSession().setAttribute("b_token", business.getToken());
//            System.out.println("username :"+business.getUsername());
//           
//            EkycDoanhNghiepTable doanhNghiepTable = ekycDoanhNghiepRepository.findByUsername(business.getUsername());
//     
//          
//            if(doanhNghiepTable == null || doanhNghiepTable.getStep().equals("1")) {
//            	System.out.println("111111111111");
//            	 return "redirect:/ekyc-enterprise";
//            }else if(doanhNghiepTable != null && doanhNghiepTable.getStep() != "1") {
//            	 System.out.println("222222222222222 :");
//            	return "redirect:/ekyc-enterprise/update-ekyc-business";
//			}
//            
//        	
//          
//        } catch (ErrorException e) {
//			model.addAttribute("message", e.getMessage());
//			LOGGER.error(e.getMessage());
//		} catch (Exception e) {
//			model.addAttribute("message", e.getMessage());
//			LOGGER.error("ErrorException", e);
//		}
//		return "loginbusiness";
//        
//	}
}
