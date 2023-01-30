//package fis.com.vn.controller;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.nio.charset.StandardCharsets;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Base64;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.UUID;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.apache.commons.io.IOUtils;
//import org.apache.http.HttpResponse;
//import org.apache.http.client.HttpClient;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.entity.StringEntity;
//import org.apache.http.impl.client.BasicResponseHandler;
//import org.apache.http.impl.client.HttpClientBuilder;
//import org.json.JSONArray;
//import org.json.JSONObject;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.core.io.InputStreamResource;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.util.StreamUtils;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.google.gson.Gson;
//
//import fis.com.vn.common.Common;
//import fis.com.vn.common.CommonUtils;
//import fis.com.vn.common.Email;
//import fis.com.vn.common.EnDeCryption;
//import fis.com.vn.common.FileHandling;
//import fis.com.vn.common.PdfHandling;
//import fis.com.vn.common.SendSMS;
//import fis.com.vn.common.StringUtils;
//import fis.com.vn.common.Utils;
//import fis.com.vn.component.ConfigProperties;
//import fis.com.vn.component.KySoComponent;
//import fis.com.vn.contains.Contains;
//import fis.com.vn.entities.FileObject;
//import fis.com.vn.entities.FormInfo;
//import fis.com.vn.entities.ParamsKbank;
//import fis.com.vn.entities.RespApi;
//import fis.com.vn.esigncloud.ESignCloudConstant;
//import fis.com.vn.esigncloud.eSignCall;
//import fis.com.vn.esigncloud.eSignCall2;
//import fis.com.vn.esigncloud.datatypes.MultipleSignedFileData;
//import fis.com.vn.esigncloud.datatypes.MultipleSigningFileData;
//import fis.com.vn.esigncloud.datatypes.SignCloudMetaData;
//import fis.com.vn.esigncloud.datatypes.SignCloudResp;
//import fis.com.vn.exception.ErrorException;
//import fis.com.vn.ocr.Ocr;
//import fis.com.vn.repository.EkycKysoRepository;
//import fis.com.vn.table.EkycKyso;
//
//@Controller
//public class KySoDoanhNghiepCaNhanController extends BaseController{
//	private static final Logger LOGGER = LoggerFactory.getLogger(KySoDoanhNghiepCaNhanController.class);
//	
//	@Value("${KY_SO_FOLDER}")
//	String KY_SO_FOLDER;
//	
//	@Value("${LINK_ADMIN}")
//	String LINK_ADMIN;
//	
//	@Value("${CODE}")
//	protected String code;
//	
//	@Value("${MOI_TRUONG}")
//	String MOI_TRUONG;
//	
//	@Autowired EkycKysoRepository ekycKysoRepository;
//	@Autowired ConfigProperties configProperties;
//	@Autowired PdfHandling pdfHandling;
//	@Autowired EnDeCryption enDeCryption;
//	@Autowired Email email;
//	@Autowired KySoComponent kySoComponent;
//	@Autowired SendSMS sendSMS;
//	
//	public String notificationTemplate = "[FPT-CA] Ma xac thuc (OTP) cua Quy khach la {AuthorizeCode}. Vui long dien ma so nay de ky Hop dong Dien Tu va khong cung cap OTP cho bat ky ai";
//    public String notificationSubject = "[FPT-CA] Ma xac thuc (OTP)";
//    
//    @GetMapping(value = "/reg")
//	public String reg( HttpServletRequest req, Model model) {
//		return "redirect:/khach-hang/ky-so/step1"; 
//	}
//    
//	@GetMapping(value = "/khach-hang/ky-so/step1")
//	public String step1( HttpServletRequest req, Model model) {
//		try {
//			ParamsKbank params = new ParamsKbank();
//			params.setCodeTransaction(Common.layMaGiaoDich(1));
//			setParams(params, req); 
//			return "demo/kySoDoanhNgiepCaNhan/step/step1"; 
//		} catch (Exception e) {
//			LOGGER.error("Error step1: {}", e);
//		}
//		return "demo/kySoDoanhNgiepCaNhan/error"; 
//	}
//	
//	@PostMapping(value = "/khach-hang/ky-so/step1")
//	public String poststep1( HttpServletRequest req, @RequestParam Map<String, String> allParams, Model model) {
//		try {
//			EkycKyso ekycKyso = ekycKysoRepository.findBySoCmtAndToken(allParams.get("soCmt"), allParams.get("matKhau"));
//			
//			if(ekycKyso == null) {
//				model.addAttribute("error", "Số chứng minh thư hoặc mật khẩu không đúng");
//				
//				return "demo/kySoDoanhNgiepCaNhan/step/step1";
//			}
//			
//			if(
//					(!StringUtils.isEmpty(ekycKyso.getTrangThai()) && ekycKyso.getTrangThai().equals("1")) ||
//					(!StringUtils.isEmpty(ekycKyso.getTrangThai()) && ekycKyso.getTrangThai().equals("2")) ||
//					(!StringUtils.isEmpty(ekycKyso.getTrangThai()) && ekycKyso.getTrangThai().equals("3"))
//			     ) {
//				model.addAttribute("error", "Hợp đồng đã được ký");
//				
//				return "demo/kySoDoanhNgiepCaNhan/step/step1";
//			}
//			
//			if(System.currentTimeMillis() > ekycKyso.getThoiGianHetHanToken()) {
//				model.addAttribute("error", "Mật khẩu hết hạn");
//				
//				return "demo/kySoDoanhNgiepCaNhan/step/step1";
//			}
//			
//			ParamsKbank params = new ParamsKbank();
//			params.setSoCmt(allParams.get("soCmt"));
//			params.setMatKhau(allParams.get("matKhau"));
//			params.setCodeTransaction(Common.layMaGiaoDich(1));
//			setParams(params, req); 
//			
//			LOGGER.info("CodeTransaction: "+params.getCodeTransaction() );
//			
//			return "demo/kySoDoanhNgiepCaNhan/step/step3";
//		} catch (Exception e) {
//			LOGGER.error("Error step1 post: {}", e);
//		}
//		return "demo/kySoDoanhNgiepCaNhan/error"; 
//	}
//	
//	@PostMapping(value = "/khach-hang/ky-so/step2")
//	public String step2(@RequestParam Map<String, String> allParams, HttpServletRequest req, Model model
////			@RequestParam(name = "anhMatTruoc", required = false) MultipartFile anhMatTruoc, 
////			@RequestParam(name = "anhMatSau", required = false) MultipartFile anhMatSau
//			) throws IOException {
//		try {
//			forwartParams(allParams, model);
//			
//			ParamsKbank params = getParams(req);
//			if(params == null) return "redirect:/khach-hang/ky-so/step1";
//			
//			LOGGER.info("CodeTransaction: "+params.getCodeTransaction() );
//			
//			String anhMatSauBase64 = allParams.get("anhMatSauBase64") ;
//			String anhMatTruocBase64 = allParams.get("anhMatTruocBase64") ;
//			
////			anhMatTruocBase64 = ImageScaler.resizeImageBase64(anhMatTruocBase64, 1500, 1500);
////			anhMatSauBase64 = ImageScaler.resizeImageBase64(anhMatSauBase64, 1500, 1500);
//			
//			EkycKyso ekycKyso = ekycKysoRepository.findBySoCmtAndToken(params.getSoCmt(), params.getMatKhau());
//			
////			if(StringUtils.isEmpty(ekycKyso.getTrangThaiEkyc()) || (!StringUtils.isEmpty(ekycKyso.getTrangThaiEkyc()) && !ekycKyso.getTrangThaiEkyc().equals(Contains.TT_EKYC_TRUYEN_THONG)))
////				ekycKyso.setTrangThaiEkyc(Contains.TT_EKYC_THAT_BAI);
//			
//			params.setSoDienThoai(ekycKyso.getSoDienThoai());
//			params.setSoCmt(ekycKyso.getSoCmt());
//			params.setSoHopDong(ekycKyso.getSoTaiKhoan());
//			params.setHoVaTen(ekycKyso.getHoVaTen());
//			
//			JSONObject jsonObject = new JSONObject();
//			jsonObject.put("anhMatTruoc", anhMatTruocBase64);
//			jsonObject.put("anhMatSau", anhMatSauBase64);
//			
//			String respone = postRequest(jsonObject.toString(), "/public/all/doc-noi-dung-ocr", params);
//			System.out.println(respone);
//			JSONObject object = new JSONObject(respone);
//			if(object.getInt("status") != 200) {
//				model.addAttribute("error", object.getString("message"));
//				String json = ekycKyso.getThongBao();
//				ekycKyso.setThongBao(themLoi(json, object.getString("message")));
//				ekycKysoRepository.save(ekycKyso);
//				return "demo/kySoDoanhNgiepCaNhan/step/step3";
//			}
//			
////			params.setAnhMatSau(anhMatSauBase64);
////			params.setAnhMatTruoc(anhMatTruocBase64);
//			params.setRespGiayTo(respone);
//			
//			setParams(params, req);
//			
//			JSONObject objectRespGiayTo = new JSONObject(respone);
//			Ocr ocr = new Gson().fromJson(objectRespGiayTo.get("data").toString(), Ocr.class);
//			
//			if(!ekycKyso.getSoCmt().equals(ocr.getSoCmt())) {
//				model.addAttribute("error", "Số chứng minh thư không khớp với hồ sơ");
//				String json = ekycKyso.getThongBao();
//				ekycKyso.setThongBao(themLoi(json, "Số chứng minh thư không khớp với hồ sơ"));
//				ekycKysoRepository.save(ekycKyso);
//				return "demo/kySoDoanhNgiepCaNhan/step/step3";
//			}
//			
//			if(!Utils.alias2(ekycKyso.getHoVaTen()).equals(Utils.alias2(ocr.getHoVaTen()))) {
//				model.addAttribute("error", "Họ tên trên giấy tờ không khớp với hồ sơ");
//				String json = ekycKyso.getThongBao();
//				ekycKyso.setThongBao(themLoi(json, "Họ tên trên giấy tờ không khớp với hồ sơ"));
//				ekycKysoRepository.save(ekycKyso);
//				return "demo/kySoDoanhNgiepCaNhan/step/step3";
//			}
//			
//			//5-6-2022
//			if(kiemTraAnhKhongDuChatLuong(ocr)) {
//				model.addAttribute("error", "Ảnh không đủ chất lượng");
//				String json = ekycKyso.getThongBao();
//				ekycKyso.setThongBao(themLoi(json, "Ảnh không đủ chất lượng"));
//				ekycKysoRepository.save(ekycKyso);
//				return "demo/kySoDoanhNgiepCaNhan/step/step3";
//			}
//			
//			//5-6-2022
//			if(kiemTraNgayCapKhongDung(ocr, ekycKyso)) {
//				model.addAttribute("error", "Thông tin ngày cấp giấy tờ không đúng");
//				String json = ekycKyso.getThongBao();
//				ekycKyso.setThongBao(themLoi(json, "Thông tin ngày cấp giấy tờ không đúng"));
//				ekycKysoRepository.save(ekycKyso);
//				return "demo/kySoDoanhNgiepCaNhan/step/step3";
//			}
//			
//			if(!StringUtils.isEmpty(ekycKyso.getTrangThai()) && ekycKyso.getTrangThai().equals("1")) {
//				model.addAttribute("error", "Hợp đồng đã được ký");
//				
//				return "demo/kySoDoanhNgiepCaNhan/step/step3";
//			}
//			
//			JSONObject jsonObjectSS = new JSONObject();
//			jsonObjectSS.put("anhMatTruoc", anhMatTruocBase64 );
//			jsonObjectSS.put("anhKhachHang", CommonUtils.encodeFileToBase64Binary(new File(ekycKyso.getAnhCaNhan())));
//			
//			String respone1 = postRequest(jsonObjectSS.toString(), "/public/all/so-sanh-anh", params);
//			object = new JSONObject(respone1);
//			if(object.getInt("status") != 200) {
//				model.addAttribute("error", object.getString("message"));
//				String json = ekycKyso.getThongBao();
//				ekycKyso.setThongBao(themLoi(json, object.getString("message")));
//				ekycKysoRepository.save(ekycKyso);
//				return "demo/kySoDoanhNgiepCaNhan/step/step3";
//			}
//			
//			String str = ekycKyso.getDuongDanFileKy();
//	        File file2 = new File(str);
//			String base64Img2 = CommonUtils.encodeFileToBase64Binary(file2);
//	        model.addAttribute("file", base64Img2);
//			
//			model.addAttribute("ocr", ocr);
//			
//			String imgFolderLog = configProperties.getConfig().getImage_folder_log()+code+"/";
//			FileHandling fileHandling = new FileHandling();
//			String pathAnhMatTruoc = fileHandling.save(anhMatTruocBase64, imgFolderLog);
//			String pathAnhMatSau = fileHandling.save(anhMatSauBase64, imgFolderLog);
//			
//			ekycKyso.setOcr(respone);
//			ekycKyso.setAnhMatTruoc(pathAnhMatTruoc);
//			ekycKyso.setAnhMatSau(pathAnhMatSau);
//			
//			ekycKysoRepository.save(ekycKyso);
//			
//			if(!StringUtils.isEmpty(ekycKyso.getTrangThaiEkyc()) && ekycKyso.getTrangThaiEkyc().equals(Contains.TT_EKYC_TRUYEN_THONG)) {
//				model.addAttribute("anhVideo", Utils.encodeFileToBase64Binary(new File(ekycKyso.getAnhVideo())));
//			} 
//			
//			return "demo/kySoDoanhNgiepCaNhan/step/step31";
//		} catch (Exception e) {
//			LOGGER.error("Error step2 post: {}", e);
//		}
//		return "demo/kySoDoanhNgiepCaNhan/error"; 
//	}
//	
//	private boolean kiemTraNgayCapKhongDung(Ocr ocr, EkycKyso ekycKyso) {
//		try {
//			String str = pdfHandling.layThongTinNgayCap(Utils.encodeFileToBase64Binary(new File(ekycKyso.getDuongDanFileKy())));
//			LOGGER.info("Ngay cap pdf "+ekycKyso.getSoCmt()+": {}", str);
//			LOGGER.info("Ngay cap ocr "+ekycKyso.getSoCmt()+": {}", ocr.getNgayCap());
//			
//			if(ocr.getNgayCap().equals(str)) return false;
//		} catch (Exception e) {
//		}
//		return true;
//	}
//
//	private boolean kiemTraAnhKhongDuChatLuong(Ocr ocr) {
//		Double scoreCheck = configProperties.getConfig().getDoubleScore_check();
//		try {
//			if(getScoreDouble(ocr.getSoCmtScore()) < scoreCheck) return true;
//			if(getScoreDouble(ocr.getHoVaTenScore()) < scoreCheck) return true;
//			if(getScoreDouble(ocr.getNamSinhScore()) < scoreCheck) return true;
//			if(getScoreDouble(ocr.getQueQuanScore()) < scoreCheck) return true;
//			if(getScoreDouble(ocr.getNoiTruScore()) < scoreCheck) return true;
//			if(getScoreDouble(ocr.getNoiCapScore()) < scoreCheck) return true;
//			if(getScoreDouble(ocr.getNgayCapScore()) < scoreCheck) return true;
//			if(ocr.getLoaiCmtMatTruoc() != null && ocr.getLoaiCmtMatTruoc().equals("cccd_chip_mt")) {
//				if(getScoreDouble(ocr.getNgayHetHanScore()) < scoreCheck) return true;
//				if(getScoreDouble(ocr.getGioiTinhScore()) < scoreCheck) return true;
//				if(getScoreDouble(ocr.getQuocTichScore()) < scoreCheck) return true;
//			}
//			if(ocr.getLoaiCmtMatTruoc() != null && ocr.getLoaiCmtMatTruoc().equals("cccd/cmt_12_mt")) {
//				if(getScoreDouble(ocr.getNgayHetHanScore()) < scoreCheck) return true;
//				if(getScoreDouble(ocr.getGioiTinhScore()) < scoreCheck) return true;
//			}
//		} catch (Exception e) {
//		}
//		return false;
//	}
//	private Double getScoreDouble(String str) {
//		try {
//			String st = str.replaceAll("[^0-9.]+", "");
//			return Double.valueOf(st);
//		} catch (Exception e) {
//		}
//		return 0.0;
//	}
//	@PostMapping(value = "/khach-hang/ky-so/step31")
//	public String ste31(@RequestParam Map<String, String> allParams, HttpServletRequest req, Model model) throws IOException {
//		
//		try {
//			forwartParams(allParams, model);
//			String listImage = allParams.get("listImage");
//			String [] arr = listImage.split(",");
//			JSONArray jsonArray = new JSONArray();
//			String anhVideo = "";
//			FileHandling fileHandling = new FileHandling();
//			String imgFolderLog = configProperties.getConfig().getImage_folder_log()+code+"/";
//			for (int i = 0; i < arr.length; i++) {
//				jsonArray.put(i, new JSONObject().put("anh", arr[i]).put("thoiGian", (i+1)));
//				String pathImg = fileHandling.save(arr[i], imgFolderLog);
//				if(StringUtils.isEmpty(anhVideo)) {
//					anhVideo = pathImg;
//				} else {
//					anhVideo += ","+pathImg;
//				}
//			}
//			
//			ParamsKbank params = getParams(req);
//			if(params == null) return "redirect:/khach-hang/ky-so/step1";
//			
//			LOGGER.info("CodeTransaction: "+params.getCodeTransaction() );
//			
//			EkycKyso ekycKyso = ekycKysoRepository.findBySoCmtAndToken(params.getSoCmt(), params.getMatKhau());
//			
//			JSONObject jsonObject = new JSONObject();
//			jsonObject.put("anhMatTruoc", CommonUtils.encodeFileToBase64Binary(new File(ekycKyso.getAnhMatTruoc())));
//			jsonObject.put("anhVideo", jsonArray);
//			
//			Double similar = null;
//			
//			if(StringUtils.isEmpty(ekycKyso.getTrangThaiEkyc()) || (!StringUtils.isEmpty(ekycKyso.getTrangThaiEkyc()) && !ekycKyso.getTrangThaiEkyc().equals(Contains.TT_EKYC_TRUYEN_THONG))) {
//				String respone = postRequest(jsonObject.toString(), "/public/all/xac-thuc-khuon-mat", params);
//				JSONObject object = new JSONObject(respone);
//				
//				ekycKyso.setAnhVideo(anhVideo);
//				
//				if(object.getInt("status") != 200) {
//					model.addAttribute("error", object.getString("message"));
//					String json = ekycKyso.getThongBao();
//					ekycKyso.setTrangThaiEkyc(Contains.TT_EKYC_THAT_BAI);
//					ekycKyso.setThongBao(themLoi(json, object.getString("message")));
//					ekycKyso.setDiemEkyc("");
//					ekycKysoRepository.save(ekycKyso);
//					return "demo/kySoDoanhNgiepCaNhan/step/step31";
//				}
//				similar = object.getDouble("data");
//			}
//			setParams(params, req);
//			
//			JSONObject objectRespGiayTo = new JSONObject(ekycKyso.getOcr());
//			Ocr ocr = new Gson().fromJson(objectRespGiayTo.get("data").toString(), Ocr.class);
//			
//			params.setHoVaTen(ekycKyso.getHoVaTen());
//			params.setSoDienThoai(ekycKyso.getSoDienThoai());
//			FormInfo formInfo = new FormInfo();
//			formInfo.setNgayCap(ocr.getNgayCap());
//			formInfo.setNoiCap(ocr.getNoiCap());
//			formInfo.setDiaChi(ocr.getNoiTru());
//			formInfo.setNamSinh(ocr.getNamSinh());
//			params.setFormInfo(formInfo);
//			setParams(params, req); 
//			
//			if(StringUtils.isEmpty(ekycKyso.getTrangThaiEkyc()) || (!StringUtils.isEmpty(ekycKyso.getTrangThaiEkyc()) && !ekycKyso.getTrangThaiEkyc().equals(Contains.TT_EKYC_TRUYEN_THONG))) {
//				ekycKyso.setTrangThaiEkyc(Contains.TT_EKYC_THANH_CONG);
//			}
//			
//			try {
//				ekycKyso.setDiemEkyc(String.valueOf(similar));
//			} catch (Exception e) {
//			}
//			
//			ekycKysoRepository.save(ekycKyso);
//			
//			//edit
//			String outputFilePath = fileHandling.getFolder(configProperties.getConfig().getImage_folder_log()+code+"/")+UUID.randomUUID().toString()+".pdf";
//			fileHandling.createFolder(configProperties.getConfig().getImage_folder_log()+code+"/");
//			
//			pdfHandling.editContentPdf(outputFilePath, params);
//			model.addAttribute("pdfSign", enDeCryption.encrypt(outputFilePath));
//			model.addAttribute("LINK_ADMIN", LINK_ADMIN);
//			
//			LOGGER.info("Complete step 31: "+params.getCodeTransaction()+"|"+outputFilePath );
//			
//			return "demo/kySoDoanhNgiepCaNhan/step/step32";
//		} catch (Exception e) {
//			LOGGER.error("Error step31 post: {}", e);
//		}
//		return "demo/kySoDoanhNgiepCaNhan/error"; 
//	}
//	
//	@PostMapping(value = "/khach-hang/ky-so/step32")
//	public String step32( HttpServletRequest req, Model model, @RequestParam Map<String, String> allParams) {
//		try {
//			ParamsKbank params = getParams(req);
//			if(params == null) return "redirect:/khach-hang/ky-so/step1";
//			
//			LOGGER.info("CodeTransaction: "+params.getCodeTransaction() );
//			
//			if(StringUtils.isEmpty(allParams.get("chuKySo"))) {
//				LOGGER.info("EMPTY SIGN: "+ params.getSoCmt());
//				return "redirect:/khach-hang/ky-so/step1";
//			}
//			
//			params.setAnhChuKy(allParams.get("chuKySo"));
//			
//			//edit
//			FileHandling fileHandling = new FileHandling();
//			
//			String outputFilePath = fileHandling.getFolder(configProperties.getConfig().getImage_folder_log()+code+"/")+UUID.randomUUID().toString()+".pdf";
//			fileHandling.createFolder(configProperties.getConfig().getImage_folder_log()+code+"/");
//			
//			pdfHandling.editContentPdf(outputFilePath, params);
//			model.addAttribute("pdfSign", enDeCryption.encrypt(outputFilePath));
//			model.addAttribute("LINK_ADMIN", LINK_ADMIN);
//			model.addAttribute("LINK_DKDK", configProperties.getConfig().getLink_dieu_khoan_dieu_kien());
//			
//			if(StringUtils.isEmpty(outputFilePath)) {
//				LOGGER.info("EMPTY SIGN OUTPUTFILEPATH: "+ params.getSoCmt());
//				return "redirect:/khach-hang/ky-so/step1";
//			}
//			
//			params.setAnhChuKy(outputFilePath);
//			setParams(params, req); 
//			
//			kysoPage(allParams, model, params);
//			return "demo/kySoDoanhNgiepCaNhan/step/step4"; 
//		} catch (Exception e) {
//			LOGGER.error("Error step32 post: {}", e);
//		}
//		
//		return "demo/kySoDoanhNgiepCaNhan/error"; 
//	}
//	private void kysoPage(Map<String, String> allParams, Model model, ParamsKbank params) {
//		EkycKyso ekycKyso = ekycKysoRepository.findBySoCmtAndToken(params.getSoCmt(), params.getMatKhau());
//		
//        model.addAttribute("file", enDeCryption.encrypt(ekycKyso.getDuongDanFileKy()));
//        
//        if(!StringUtils.isEmpty(ekycKyso.getChiDinh())) {
//	        model.addAttribute("fileBaoHiem", enDeCryption.encrypt(ekycKyso.getChiDinh()));
//        }
//
//		JSONObject objectRespGiayTo = new JSONObject(params.getRespGiayTo());
//		Ocr ocr = new Gson().fromJson(objectRespGiayTo.get("data").toString(), Ocr.class);
//		
//		model.addAttribute("params", params);
//		model.addAttribute("ocr", ocr);
//	}
//	@PostMapping(value = "/khach-hang/ky-so/step3")
//	public String nhapThongTin(@RequestParam Map<String, String> allParams, HttpServletRequest req, Model model) throws IOException {
//		ParamsKbank params = getParams(req);
//		if(params == null) return "redirect:/khach-hang/ky-so/step1";
//		
//		LOGGER.info("CodeTransaction: "+params.getCodeTransaction() );
//		
//		forwartParams(allParams, model);
//		
//		if(StringUtils.isEmpty(params.getAnhChuKy())) {
//			LOGGER.info("EMPTY SIGN SIGN: "+ params.getSoCmt());
//			return "redirect:/khach-hang/ky-so/step1";
//		}
//		
//		try {
//			EkycKyso ekycKyso = ekycKysoRepository.findBySoCmtAndToken(allParams.get("soCmt"), params.getMatKhau());
//			String jsonResponse = "";
//			if(MOI_TRUONG.equals("dev")) {
//				eSignCall2 service = new eSignCall2();
//		        jsonResponse = service.authorizeCounterSigningForSignCloud(allParams.get("agreementUUID"), allParams.get("otpKySo"), allParams.get("maKy"));
//			} else {
//				eSignCall service = new eSignCall();
//		        jsonResponse = service.authorizeCounterSigningForSignCloud(allParams.get("agreementUUID"), allParams.get("otpKySo"), allParams.get("maKy"));
//			}
//	        ObjectMapper objectMapper = new ObjectMapper();
//	        SignCloudResp signCloudResp = objectMapper.readValue(jsonResponse, SignCloudResp.class);
//	        if ((signCloudResp.getResponseCode() == 0 && signCloudResp.getMultipleSignedFileData() != null) ||
//	        		signCloudResp.getResponseCode() == 0 && signCloudResp.getSignedFileData() != null
//	        		) {
////	        	FileHandling fileHandling = new FileHandling();
////            	String imgFolderLog = fileHandling.getFolder(KY_SO_FOLDER+"/") ;
//	        	if (signCloudResp.getSignedFileData() != null) {
//	                String file = ekycKyso.getDuongDanFileKy();
//	                if(file.indexOf(signCloudResp.getSignedFileName()) == -1 && !StringUtils.isEmpty(ekycKyso.getChiDinh())) file = ekycKyso.getChiDinh();
//	                System.out.println("Saved in " + file);
//	                System.out.println("MimeType: " + signCloudResp.getMimeType());
//	                FileOutputStream fos = new FileOutputStream(file);
//	                IOUtils.write(signCloudResp.getSignedFileData(), fos);
//	                fos.close();
//	            } else {
//	                System.out.println(signCloudResp.getSignedFileUUID());
//	                System.out.println(signCloudResp.getDmsMetaData());
//	            }
//	            
//	            // for multiple files
//	            if (signCloudResp.getMultipleSignedFileData() != null) {
//	                if (!signCloudResp.getMultipleSignedFileData().isEmpty()) {
//	                    for (int i = 0; i < signCloudResp.getMultipleSignedFileData().size(); i++) {
//	                        MultipleSignedFileData multipleSignedFileData = signCloudResp.getMultipleSignedFileData().get(i);
//	                        if (multipleSignedFileData.getSignedFileData() != null) {
//	                            String file = ekycKyso.getDuongDanFileKy();
//	        	                if(file.indexOf(multipleSignedFileData.getSignedFileName()) == -1 && !StringUtils.isEmpty(ekycKyso.getChiDinh())) file = ekycKyso.getChiDinh();
//	                            System.out.println("Saved in " + file);
//	                            System.out.println("MimeType: " + multipleSignedFileData.getMimeType());
//	                            IOUtils.write(multipleSignedFileData.getSignedFileData(), new FileOutputStream(file));
//	                        } else {
//	                            System.out.println(multipleSignedFileData.getSignedFileUUID());
//	                            System.out.println(multipleSignedFileData.getDmsMetaData());
//	                        }
//	                    }
//	                }
//	            }
//	        	
//	        	String str = ekycKyso.getDuongDanFileKy();
////	            String str1 = str.split("\\/")[str.split("\\/").length - 1];
//	        	
//	            model.addAttribute("file", enDeCryption.encrypt(str));
//	            
//	            if(!StringUtils.isEmpty(ekycKyso.getChiDinh())) {
//		            str = ekycKyso.getChiDinh();
////		            str1 = str.split("\\/")[str.split("\\/").length - 1];
//		            model.addAttribute("fileBaoHiem", enDeCryption.encrypt(str));
//	            }
//	            
//	            
//	            ekycKyso.setTrangThai("1");
//	            ekycKyso.setAnhTinNhan(params.getAnhChuKy());
//	            ekycKyso.setKhachHangKy(new Date());
//	            ekycKysoRepository.save(ekycKyso);
//	            
//	            ArrayList<FileObject> fileObjects = taoFileGuiMail(ekycKyso);
//	            
//	            email.sendMultipleFile(ekycKyso.getEmail(), "Hợp đồng điện tử pdf", "Xin chào "+ekycKyso.getHoVaTen()+", \n\n"
//						+ "Bạn đã thực hiện thành công hợp đồng vay", fileObjects);
//	            
//	            setParams(null, req);
//	            
//	        } else if(signCloudResp.getResponseCode() == 1004) {
//	        	model.addAttribute("error", "Lỗi OTP");
//	        	kysoPage(allParams, model, params);
//				return "demo/kySoDoanhNgiepCaNhan/step/step4";
//	        } else {
//	        	model.addAttribute("error", "Ký số thất bại");
//	        	kysoPage(allParams, model, params);
//				return "demo/kySoDoanhNgiepCaNhan/step/step4";
//	        }
//		} catch (Exception e) {
//			LOGGER.error("Error step3 post: {}", e);
//			model.addAttribute("error", "Lỗi hệ thống");
//			kysoPage(allParams, model, params);
//			return "demo/kySoDoanhNgiepCaNhan/step/step4";
//		}
//		model.addAttribute("LINK_ADMIN", LINK_ADMIN);
//		return "demo/kySoDoanhNgiepCaNhan/step/step5";
//	}
//	private ArrayList<FileObject> taoFileGuiMail(EkycKyso ekycKyso) {
//		ArrayList<FileObject> fileObjects = new ArrayList<FileObject>();
//		FileObject fileObject = taoObject("hop_dong_vay.pdf", ekycKyso.getDuongDanFileKy(), ekycKyso.getTrangThai(), true);
//		if(fileObject != null) fileObjects.add(fileObject);
//		
//		fileObject = taoObject("hop_dong_bao_hiem.pdf", ekycKyso.getChiDinh(), ekycKyso.getTrangThai(), true);
//		if(fileObject != null) fileObjects.add(fileObject);
//		
//		fileObject = taoObject("dang_ky_cap_chung_thu_so.pdf", ekycKyso.getAnhTinNhan(), ekycKyso.getTrangThai(), false);
//		if(fileObject != null) fileObjects.add(fileObject);
//		
//		return fileObjects;
//	}
//	
//	private FileObject taoObject(String tenFile, String pathFile, String trangThai, boolean checkSign ) {
//		if(StringUtils.isEmpty(pathFile)) return null;
//		FileObject fileObject = new FileObject();
//		fileObject.setTen(tenFile);
//		String str = pathFile;
////        String str1 = str.split("\\/")[str.split("\\/").length - 1];
////        if(!StringUtils.isEmpty(trangThai) && trangThai.equals("1") && checkSign) {
////        	str = str.replaceAll(str1, ".signed."+str1).replaceAll("/[0-9_]+/", "/");
////        }
//		fileObject.setDuongDan(str);
//		return fileObject;
//	}
//	
//	public String postRequestFull(String data, String url, String codeTransaction) {
//		return postRequestFull(data, url, token, code, codeTransaction);
//	}
//	public String postRequestFull(String data, String url, String tokenApi, String codeApi, String codeTransaction) {
//		RespApi respApi = new RespApi();
//		try {
//			HttpClient httpClient = HttpClientBuilder.create().build();
//			HttpPost request = new HttpPost(url);
//			StringEntity params = new StringEntity(data, "UTF-8");
//			request.addHeader("content-type", "application/json");
//			request.addHeader("token", tokenApi);
//			request.addHeader("code", codeApi);
//			request.addHeader("Accept-Language", "en");
//			request.addHeader("code_transaction", codeTransaction);
//			request.setEntity(params);
//			HttpResponse response = httpClient.execute(request);
//			
//			String responseString = new BasicResponseHandler().handleResponse(response);
//			
//			return responseString;
//		} catch (Exception e) {
//			LOGGER.error("Error postRequestFull: {}", e);
//			respApi.setStatus(400);
//			respApi.setMessage("Lỗi hệ thống");
//		}
//		return new Gson().toJson(respApi);
//	}
//	public void setParams (ParamsKbank params, HttpServletRequest req) {
//		 req.getSession().setAttribute("params", params);
//	}
//	public ParamsKbank getParams (HttpServletRequest req) {
//		return (ParamsKbank) req.getSession().getAttribute("params");
//	}
//	
//	@PostMapping(value = "/khach-hang/ky-so/dang-ky", produces = MediaType.APPLICATION_JSON_VALUE)
//	@ResponseBody
//	public String kySo(@RequestParam Map<String, String> allParams, HttpServletRequest req, Model model, RedirectAttributes redirectAttributes) throws Exception {
//		JSONObject jsonResp = new JSONObject();
//		forwartParams(allParams, model);
//		ParamsKbank params = getParams(req);
//		if(params == null) {
//			jsonResp.put("status", 405);
//			return jsonResp.toString();
//		}
//		
//		if(StringUtils.isEmpty(params.getAnhChuKy())) {
//			LOGGER.info("EMPTY SIGN REGISTER: "+ params.getSoCmt());
//			return "redirect:/khach-hang/ky-so/step1";
//		}
//		
//		try {
//			String text = IOUtils.toString(req.getInputStream(), StandardCharsets.UTF_8.name());
//			
//			JSONObject jsonObjectPr = new JSONObject(text);
//			
//			EkycKyso ekycKyso = ekycKysoRepository.findBySoCmtAndToken(jsonObjectPr.getString("soCmt"), params.getMatKhau());
//			
//			forwartParams(allParams, model);
//			SignCloudResp signCloudResp;
//			String agreementUUID = UUID.randomUUID().toString();
//			String billCode = "";
//			String pathPdf = "";
//			String[] arr = ekycKyso.getDuongDanFileKy().split("\\/");
//			String nameFile = arr[arr.length - 1];
//			
//			if(!StringUtils.isEmpty(params.getUuid()) && !StringUtils.isEmpty(params.getBilCode())) {
//				agreementUUID = params.getUuid();
//				billCode = params.getBilCode();
//				pathPdf = ekycKyso.getDuongDanFileKy();
//				
//				LOGGER.info("Resend esign: "+ agreementUUID);
//				LOGGER.info("Resend esign phone: "+ ekycKyso.getSoDienThoai());
//				
//				if(MOI_TRUONG.equals("dev")) {
//					eSignCall2 service = new eSignCall2();
//					signCloudResp = service.regenerateAuthorizationCodeForSignCloud(
//					   params.getUuid(),
//		               notificationTemplate,
//		               notificationSubject,
//		               ESignCloudConstant.AUTHORISATION_METHOD_SMS);
//				} else {
//					 eSignCall service = new eSignCall();
//					 signCloudResp = service.regenerateAuthorizationCodeForSignCloud(
//						   params.getUuid(),
//			               notificationTemplate,
//			               notificationSubject,
//			               ESignCloudConstant.AUTHORISATION_METHOD_SMS);
//				}
//			} else {
//				pathPdf = ekycKyso.getDuongDanFileKy();
//				
//				String nameFileBaoHiem = "";
//				String pathPdfBaoHiem = "";
//				if(!StringUtils.isEmpty(ekycKyso.getChiDinh())) {
//					String[] arrBaoHiem = ekycKyso.getChiDinh().split("\\/");
//					nameFileBaoHiem = arrBaoHiem[arrBaoHiem.length - 1];
//					pathPdfBaoHiem = ekycKyso.getChiDinh();
//				}
//				
//				ParamsKbank paramsSign = new ParamsKbank();
//				FormInfo formInfo = new FormInfo();
//				formInfo.setHoVaTen(ekycKyso.getHoVaTen());
//				formInfo.setSoCmt(ekycKyso.getSoCmt());
//				formInfo.setDiaChi("Hà Nội");
//				formInfo.setThanhPho("Hà Nội");
//				formInfo.setQuocGia("Việt Nam");
//				paramsSign.setSoDienThoai(ekycKyso.getSoDienThoai());
//				paramsSign.setFormInfo(formInfo);
//				paramsSign.setAnhMatTruoc(CommonUtils.encodeFileToBase64Binary(new File(ekycKyso.getAnhMatTruoc())));
//				paramsSign.setAnhMatSau(CommonUtils.encodeFileToBase64Binary(new File(ekycKyso.getAnhMatSau())));
//				
//				System.out.println("PDF Created!");
//				
//				String jsonRegister = guiThongTinDangKyKySo(paramsSign, agreementUUID);
//				ObjectMapper objectMapper = new ObjectMapper();
//		        SignCloudResp signCloudRespRegister = objectMapper.readValue(jsonRegister, SignCloudResp.class);
//				
//		        if(signCloudRespRegister.getResponseCode() != 0) {
//		        	jsonResp.put("message",  "Không đăng ký được chữ ký ");
//		        	jsonResp.put("status", 400);
//		            
//		    		return jsonResp.toString();
//		        }
//				
//				String jsonResponse = guiThongTinKySo(req, pathPdf, nameFile, agreementUUID, ekycKyso, pathPdfBaoHiem, nameFileBaoHiem);
//				
//		        signCloudResp = objectMapper.readValue(jsonResponse, SignCloudResp.class);
//				
//		        if(signCloudResp.getResponseCode() != 1007) {
//		        	jsonResp.put("message", "Không gửi được chữ ký ");
//		        	jsonResp.put("status", 400);
//		            
//		        	LOGGER.error("Send new esign error: "+ signCloudResp.getResponseCode());
//					LOGGER.error("Send new esign phone error: "+ ekycKyso.getSoDienThoai());
//		        	
//		    		return jsonResp.toString();
//		        }
//		        billCode = signCloudResp.getBillCode();
//		        
//		        LOGGER.info("Send new esign: "+ agreementUUID);
//				LOGGER.info("Send new esign phone: "+ ekycKyso.getSoDienThoai());
//			}
//			
////			if(MOI_TRUONG.equals("dev")) {
////				
////			} else {
//				sendSMS.postRequestSMS(ekycKyso.getSoDienThoai(), "Quy Khach dang thuc hien giao dich xac thuc va ky Hop dong tin dung voi PTF. Mat khau OTP cua Quy Khach la: "+signCloudResp.getAuthorizeCredential());
////			}
//			
//			ekycKyso.setUuidKySo(agreementUUID);
//			ekycKyso.setBillCodeKySo(billCode);
//			ekycKyso.setDuongDanFileKy(pathPdf);
//			ekycKysoRepository.save(ekycKyso);
//			
//			jsonResp.put("otp", signCloudResp.getAuthorizeCredential());
//			jsonResp.put("maKy", billCode);
//			jsonResp.put("pathPdf", pathPdf);
//			jsonResp.put("nameFile", nameFile);
//			jsonResp.put("agreementUUID", agreementUUID);
//			
//			params.setUuid(agreementUUID);
//			params.setBilCode(signCloudResp.getBillCode());
//			setParams(params, req);
//		} catch (ErrorException e) {
//			LOGGER.error("Error register post: {}", e);
//			jsonResp.put("message",  e.getMessage());
//        	jsonResp.put("status", 400);
//            
//    		return jsonResp.toString();
//		} catch (Exception e) {
//			LOGGER.error("Error register2 post: {}", e);
//			jsonResp.put("message",  "Lỗi hệ thống");
//        	jsonResp.put("status", 400);
//            
//    		return jsonResp.toString();
//		}
//        
//        jsonResp.put("status", 200);
//        
//		return jsonResp.toString();
//	}
//	
//	private String guiThongTinDangKyKySo(ParamsKbank params, String agreementUUID) throws Exception {
//		FormInfo formInfo = params.getFormInfo();
//		System.out.println(new Gson().toJson(formInfo));
//		
//		byte[] frontSideOfIDDocument = Base64.getDecoder().decode(params.getAnhMatTruoc());
//        byte[] backSideOfIDDocument = Base64.getDecoder().decode(params.getAnhMatSau());
//		
//		String json = "";
//		if(MOI_TRUONG.equals("dev")) {
//			eSignCall2 service = new eSignCall2();
//			
//	        json = service.prepareCertificateForSignCloud(
//	                agreementUUID,
//	                formInfo.getHoVaTen(),
//	                formInfo.getSoCmt(),
//	                formInfo.getSoCmt(),
//	                formInfo.getDiaChi(),
//	                formInfo.getThanhPho(),
//	                formInfo.getQuocGia(),
//	                frontSideOfIDDocument,
//	                backSideOfIDDocument,
//	                formInfo.getEmail(),
//	                params.getSoDienThoai());
//		} else {
//			eSignCall service = new eSignCall();
//			
//	        json = service.prepareCertificateForSignCloud(
//	                agreementUUID,
//	                formInfo.getHoVaTen(),
//	                formInfo.getSoCmt(),
//	                formInfo.getSoCmt(),
//	                formInfo.getDiaChi(),
//	                formInfo.getThanhPho(),
//	                formInfo.getQuocGia(),
//	                frontSideOfIDDocument,
//	                backSideOfIDDocument,
//	                formInfo.getEmail(),
//	                params.getSoDienThoai());
//		}
//		return json;
//	}
//	private String guiThongTinKySo(HttpServletRequest req, String pathPdf, String nameFile, String agreementUUID, EkycKyso ekycKyso, String pathPdfBaoHiem, String nameFileBaoHiem) throws Exception {
//		byte[] fileData01, fileData02;
//        String mimeType01, mimeType02;
//        SignCloudMetaData signCloudMetaDataForItem01;
//        SignCloudMetaData signCloudMetaDataForItem02;
//        HashMap<String, String> singletonSigningForItem01;
//        HashMap<String, String> singletonSigningForItem02;
//        MultipleSigningFileData fileItem01;
//        MultipleSigningFileData fileItem02;
//        
//     // file thứ nhất =========================================================================
//        fileData01 = IOUtils.toByteArray(new FileInputStream(pathPdf));
//        mimeType01 = ESignCloudConstant.MIMETYPE_PDF;
//        fileItem01 = new MultipleSigningFileData();
//
//        fileItem01.setSigningFileData(fileData01);
//        fileItem01.setMimeType(mimeType01);
//        fileItem01.setSigningFileName(nameFile);
//
//        FormInfo formInfo = new Gson().fromJson(ekycKyso.getNoiDungForm(), FormInfo.class);
//        
//        signCloudMetaDataForItem01 = new SignCloudMetaData();      
//        singletonSigningForItem01 = new HashMap<>();
//        singletonSigningForItem01.put("COUNTERSIGNENABLED", "True");
//        singletonSigningForItem01.put("PAGENO", "Last");
//        singletonSigningForItem01.put("POSITIONIDENTIFIER", formInfo.getViTriKyCaNhan());
//        singletonSigningForItem01.put("RECTANGLEOFFSET", "-30,-70");
//        singletonSigningForItem01.put("RECTANGLESIZE", "170,70");
//        singletonSigningForItem01.put("VISIBLESIGNATURE", "True");
//        singletonSigningForItem01.put("VISUALSTATUS", "False");
//        singletonSigningForItem01.put("IMAGEANDTEXT", "False");
//        singletonSigningForItem01.put("TEXTDIRECTION", "LEFTTORIGHT");
//        singletonSigningForItem01.put("SHOWSIGNERINFO", "True");
//        singletonSigningForItem01.put("SIGNERINFOPREFIX", "Ký bởi:");
//        singletonSigningForItem01.put("SHOWDATETIME", "True");
//        singletonSigningForItem01.put("DATETIMEPREFIX", "Ký ngày:");
//        singletonSigningForItem01.put("SHOWREASON", "True");
//        singletonSigningForItem01.put("SIGNREASONPREFIX", "Lý do:");
//        singletonSigningForItem01.put("SIGNREASON", "Tôi đồng ý");
//        singletonSigningForItem01.put("SHOWLOCATION", "True");
//        singletonSigningForItem01.put("LOCATION", "Hà Nội");
//        singletonSigningForItem01.put("LOCATIONPREFIX", "Nơi ký:");
//        singletonSigningForItem01.put("TEXTCOLOR", "black");
//        singletonSigningForItem01.put("SHADOWSIGNATUREPROPERTIES", "all");
//        
//        signCloudMetaDataForItem01.setSingletonSigning(singletonSigningForItem01);
//        fileItem01.setSignCloudMetaData(signCloudMetaDataForItem01);
//
//        List<MultipleSigningFileData> listOfSigningFileData = new ArrayList<>();
//        listOfSigningFileData.add(fileItem01);
//        
//        // file thứ hai =============================================================================
//        if(!StringUtils.isEmpty(pathPdfBaoHiem)) {
//	        fileData02 = IOUtils.toByteArray(new FileInputStream(pathPdfBaoHiem));
//	        mimeType02 = ESignCloudConstant.MIMETYPE_PDF;
//	        fileItem02 = new MultipleSigningFileData();
//	
//	        fileItem02.setSigningFileData(fileData02);
//	        fileItem02.setMimeType(mimeType02);
//	        fileItem02.setSigningFileName(nameFileBaoHiem);
//	
//	        signCloudMetaDataForItem02 = new SignCloudMetaData();      
//	        singletonSigningForItem02 = new HashMap<>();
//	        singletonSigningForItem02.put("COUNTERSIGNENABLED", "True");
//	        singletonSigningForItem02.put("PAGENO", "Last");
//	        singletonSigningForItem02.put("POSITIONIDENTIFIER", formInfo.getViTriKyCaNhan());
//	        singletonSigningForItem02.put("RECTANGLEOFFSET", "-30,-70");
//	        singletonSigningForItem02.put("RECTANGLESIZE", "170,70");
//	        singletonSigningForItem02.put("VISIBLESIGNATURE", "True");
//	        singletonSigningForItem02.put("VISUALSTATUS", "False");
//	        singletonSigningForItem02.put("IMAGEANDTEXT", "False");
//	        singletonSigningForItem02.put("TEXTDIRECTION", "LEFTTORIGHT");
//	        singletonSigningForItem02.put("SHOWSIGNERINFO", "True");
//	        singletonSigningForItem02.put("SIGNERINFOPREFIX", "Ký bởi:");
//	        singletonSigningForItem02.put("SHOWDATETIME", "True");
//	        singletonSigningForItem02.put("DATETIMEPREFIX", "Ký ngày:");
//	        singletonSigningForItem02.put("SHOWREASON", "True");
//	        singletonSigningForItem02.put("SIGNREASONPREFIX", "Lý do:");
//	        singletonSigningForItem02.put("SIGNREASON", "Tôi đồng ý");
//	        singletonSigningForItem02.put("SHOWLOCATION", "True");
//	        singletonSigningForItem02.put("LOCATION", "Hà Nội");
//	        singletonSigningForItem02.put("LOCATIONPREFIX", "Nơi ký:");
//	        singletonSigningForItem02.put("TEXTCOLOR", "black");
//	        singletonSigningForItem02.put("SHADOWSIGNATUREPROPERTIES", "all");
//	        signCloudMetaDataForItem02.setSingletonSigning(singletonSigningForItem02);
//	
//	        fileItem02.setSignCloudMetaData(signCloudMetaDataForItem02);
//	        
//	        listOfSigningFileData.add(fileItem02);
//        }
//        
//        String jsonResponse = "";
//		if(MOI_TRUONG.equals("dev")) {
//			eSignCall2 service = new eSignCall2();
//			jsonResponse = service.prepareMultipleFilesForSignCloud(
//               agreementUUID,
//               ESignCloudConstant.AUTHORISATION_METHOD_SMS,
//               null,
//               notificationTemplate,
//               notificationSubject,
//               listOfSigningFileData);
//		} else {
//			 eSignCall service = new eSignCall();
//			 jsonResponse = service.prepareMultipleFilesForSignCloud(
//                agreementUUID,
//                ESignCloudConstant.AUTHORISATION_METHOD_SMS,
//                null,
//                notificationTemplate,
//                notificationSubject,
//                listOfSigningFileData);
//		}
//        return jsonResponse;
//		
//	}
//	
//	@GetMapping(value = "/viewpdf/byte/{path}",produces = MediaType.APPLICATION_PDF_VALUE)
//	@ResponseBody
//	public ResponseEntity<byte[]> getImage(HttpServletResponse resp, @RequestParam Map<String, String> allParams, @PathVariable("path") String path) {
//		try {
//			String pathImg = enDeCryption.decrypt(path);
//			File file = new File(pathImg);
//			
//			byte[] bytes = StreamUtils.copyToByteArray(new FileInputStream(file));
//	        return ResponseEntity
//	                .ok()
//	                .contentType(MediaType.APPLICATION_PDF)
//	                .body(bytes);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
//	
//	@GetMapping(value = "/download/byte/{path}",produces = MediaType.APPLICATION_PDF_VALUE)
//	@ResponseBody
//	public ResponseEntity<InputStreamResource> download(HttpServletResponse resp, @RequestParam Map<String, String> allParams, @PathVariable("path") String path) {
//		try {
//			String pathImg = enDeCryption.decrypt(path);
//			File file = new File(pathImg);
//			
//			InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
//	        return ResponseEntity
//	                .ok()
//	                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + file.getName())
//	                .contentType(MediaType.APPLICATION_PDF)
//	                .contentLength(file.length())
//	                .body(resource);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
//	
//	public String themLoi(String json, String check) {
//		if(StringUtils.isEmpty(json)) return check;
//		json += "|"+check;
//		String[] arr = json.split("\\|");
//		
//		if(arr.length >= 11) arr = Arrays.copyOfRange(arr, 1, arr.length);
//		json = "";
//		for (String string : arr) {
//			if(StringUtils.isEmpty(json)) json = string;
//			else json += "|"+string;
//		}
//		
//		return json;
//	}
//	@GetMapping(value = "/viewpdf")
//    public String viewpdf(Model model) {
//        return "viewpdf";
//    }
//}
