package fis.com.vn.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfImage;
import com.itextpdf.text.pdf.PdfIndirectObject;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;

import fis.com.vn.api.entities.ParamPathImage;
import fis.com.vn.common.CommonUtils;
import fis.com.vn.common.Email;
import fis.com.vn.common.FileHandling;
import fis.com.vn.common.PdfHandling;
import fis.com.vn.common.StringUtils;
import fis.com.vn.common.Utils;
import fis.com.vn.component.ConfigProperties;
import fis.com.vn.component.EncryptionAES;
import fis.com.vn.contains.Contains;
import fis.com.vn.entities.Account;
import fis.com.vn.entities.EkycDoanhNghiep;
import fis.com.vn.entities.FormInfo;
import fis.com.vn.entities.InfoPerson;
import fis.com.vn.entities.ParamsKbank;
import fis.com.vn.esigncloud.ESignCloudConstant;
import fis.com.vn.esigncloud.eSignCall;
import fis.com.vn.esigncloud.datatypes.SignCloudMetaData;
import fis.com.vn.esigncloud.datatypes.SignCloudResp;
import fis.com.vn.ocr.Ocr;
import fis.com.vn.repository.ConfigRepository;
import fis.com.vn.repository.EkycDoanhNghiepRepository;
import fis.com.vn.repository.LogApiDetailRepository;
import fis.com.vn.repository.LogApiRepository;
import fis.com.vn.repository.UserInfoRepository;
import fis.com.vn.table.EkycDoanhNghiepTable;
import fis.com.vn.table.LogApi;
import fis.com.vn.table.LogApiDetail;
import fis.com.vn.thread.EkycDoanhNghiepThread;

@Controller
public class EkycDoanhNghiep2Controller extends BaseController {
	private static final Logger LOGGER = LoggerFactory.getLogger(EkycDoanhNghiepAdminController.class);

	@Value("${LINK_ADMIN}")
	protected String LINK_ADMIN;

	@Value("${PATH_PDF_FILL_FORM}")
	protected String PATH_PDF_FILL_FORM;

	@Value("${MOI_TRUONG}")
	protected String MOI_TRUONG;

	@Autowired
	UserInfoRepository userInfoRepository;
	@Autowired
	ConfigRepository configRepository;
	@Autowired
	ConfigProperties configProperties;
	@Autowired
	EkycDoanhNghiepRepository ekycDoanhNghiepRepository;
	@Autowired
	EkycDoanhNghiepThread ekycDoanhNghiepThread;
	@Autowired
	Email email;
	@Autowired
	PdfHandling pdfHandling;
	@Autowired
	EncryptionAES encryptionAES;
	@Autowired
	LogApiRepository logApiRepository;
	@Autowired
	LogApiDetailRepository logApiDetailRepository;

	public String notificationTemplate = "[FPT-CA] Ma xac thuc (OTP) cua Quy khach la {AuthorizeCode}. Vui long dien ma so nay de ky Hop dong Dien Tu va khong cung cap OTP cho bat ky ai";
	public String notificationSubject = "[FPT-CA] Ma xac thuc (OTP)";

	@PostMapping(value = "/ekyc-enterprise/send-mail-edit", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String sendMailEdit(@RequestParam Map<String, String> allParams, HttpServletRequest req, Model model,
			RedirectAttributes redirectAttributes) throws Exception {
		JSONObject jsonResp = new JSONObject();

		String text = IOUtils.toString(req.getInputStream(), StandardCharsets.UTF_8.name());
		JSONObject params = new JSONObject(text);
		System.out.println(params.get("emailContractPersion"));
		EkycDoanhNghiepTable doanhNghiep = ekycDoanhNghiepRepository.findByToken(params.get("token").toString());
		
		EkycDoanhNghiep ekycDoanhNghiep = new Gson().fromJson(doanhNghiep.getNoiDung(), EkycDoanhNghiep.class);
	
		doanhNghiep.setEmailNguoiLienHe(params.get("emailContractPersion").toString());
		doanhNghiep.setSoDienThoaiNguoiLienHe(params.get("phoneContractPersion").toString());
		doanhNghiep.setTenNguoiLienHe(params.get("nameContractPersion").toString());
		doanhNghiep.setStep("11");
		guiMailEkyc(ekycDoanhNghiep);
		ekycDoanhNghiepRepository.save(doanhNghiep);

		// EkycDoanhNghiep ekycDoanhNghiep = new
		// Gson().fromJson(doanhNghiep.getNoiDung(), EkycDoanhNghiep.class);

		LOGGER.info("Send mail: " + LINK_ADMIN + "/fisbank/ekyc-enterprise/update-file?token=" + ekycDoanhNghiep.getToken());

		if (!MOI_TRUONG.equals("dev")) {
			email.sendText(params.get("emailContractPersion").toString(), "Bổ sung thông tin",
					"Khi cần bổ sung chứng từ, quý khách có thể nhấn vào <a href='" + LINK_ADMIN
							+ "/fisbank/ekyc-enterprise/update-file?token=" + ekycDoanhNghiep.getToken() + "'>đây</a>");
		}
		ekycDoanhNghiepThread.startNoiDung(doanhNghiep.getUsername(), doanhNghiep.getMaDoanhNghiep());
		System.out.println("checkNoidung:  " + doanhNghiep.getCheckNoiDung());
		return jsonResp.toString();

	}

	@GetMapping(value = "/ekyc-enterprise/update-ekyc-business")
	public String updateEkycBusiness(Model model, HttpServletRequest req, @RequestParam Map<String, String> allParams) {
		ParamsKbank params = new ParamsKbank();
		Object username = req.getSession().getAttribute("b_username");
		if (username == null)
			return "demo/doanhnghiep2/step/steperror";
		EkycDoanhNghiepTable ekycDoanhNghiepTable = ekycDoanhNghiepRepository
				.findByUsername(username.toString());
		if (ekycDoanhNghiepTable == null)
			return "demo/doanhnghiep2/step/steperror";

		EkycDoanhNghiep ekycDoanhNghiep = new Gson().fromJson(ekycDoanhNghiepTable.getNoiDung(), EkycDoanhNghiep.class);
		InfoPerson infoPerson = new Gson().fromJson(ekycDoanhNghiepTable.getNoiDung(), InfoPerson.class);

		// if(ekycDoanhNghiep.getStatus() != null &&
		// ekycDoanhNghiep.getStatus().equals(Contains.TRANG_THAI_KY_THANH_CONG)) return
		// "demo/doanhnghiep2/step/stepinfofile";

		model.addAttribute("ekycDoanhNghiep", ekycDoanhNghiep);
		model.addAttribute("ekycDoanhNghiepTable", ekycDoanhNghiepTable);
		if (ekycDoanhNghiep.getListAccount() == null) {
			model.addAttribute("listAccount", req.getSession().getAttribute("listAccount"));
		} else if (ekycDoanhNghiep.getListAccount().size() > 0) {
			model.addAttribute("listAccount", ekycDoanhNghiep.getListAccount());
		}
		if (ekycDoanhNghiep.getUserDesignation() == null) {
			model.addAttribute("userDesignation", req.getSession().getAttribute("userDesignation"));
		} else if (ekycDoanhNghiep.getUserDesignation().size() > 0) {
			model.addAttribute("userDesignation", ekycDoanhNghiep.getUserDesignation());
		}

		if (ekycDoanhNghiep.getChiefAccountant() == null) {
			model.addAttribute("chiefAccountant", req.getSession().getAttribute("chiefAccountant"));
			model.addAttribute("haveAChiefAccountant", req.getSession().getAttribute("haveAChiefAccountant"));
		} else if (ekycDoanhNghiep.getChiefAccountant().size() > 0) {
			model.addAttribute("chiefAccountant", ekycDoanhNghiep.getChiefAccountant());
			model.addAttribute("haveAChiefAccountant", ekycDoanhNghiep.getHaveAChiefAccountant());
		}

		if (ekycDoanhNghiep.getListOfLeaders() == null) {
			model.addAttribute("listOfLeaders", req.getSession().getAttribute("listOfLeaders"));
		} else if (ekycDoanhNghiep.getListOfLeaders().size() > 0) {
			model.addAttribute("listOfLeaders", ekycDoanhNghiep.getListOfLeaders());
		}

		if (ekycDoanhNghiep.getPersonAuthorizedAccountHolder() == null) {
			model.addAttribute("personAuthorizedAccountHolder",
					req.getSession().getAttribute("personAuthorizedAccountHolder"));
		} else if (ekycDoanhNghiep.getPersonAuthorizedAccountHolder().size() > 0) {
			model.addAttribute("personAuthorizedAccountHolder", ekycDoanhNghiep.getPersonAuthorizedAccountHolder());

		}
		if (ekycDoanhNghiep.getPersonAuthorizedChiefAccountant() == null) {
			model.addAttribute("personAuthorizedChiefAccountant",
					req.getSession().getAttribute("personAuthorizedChiefAccountant"));
		} else if (ekycDoanhNghiep.getPersonAuthorizedChiefAccountant().size() > 0) {
			model.addAttribute("personAuthorizedChiefAccountant", ekycDoanhNghiep.getPersonAuthorizedChiefAccountant());

		}

		if (ekycDoanhNghiep.getLegalRepresentator() == null) {
			model.addAttribute("haveAcccountHolder",req.getSession().getAttribute("haveAcccountHolder"));
			model.addAttribute("allInOne",req.getSession().getAttribute("allInOne"));
			model.addAttribute("legalRepresentator", req.getSession().getAttribute("legalRepresentator"));
			model.addAttribute("checkMianLegalRepresentator",req.getSession().getAttribute("checkMianLegalRepresentator"));
		} else if (ekycDoanhNghiep.getLegalRepresentator().size() > 0) {
			model.addAttribute("haveAcccountHolder",ekycDoanhNghiep.getHaveAcccountHolder());
			model.addAttribute("allInOne", ekycDoanhNghiep.getAllInOne());
			model.addAttribute("legalRepresentator", ekycDoanhNghiep.getLegalRepresentator());
			model.addAttribute("checkMianLegalRepresentator", ekycDoanhNghiep.getLegalRepresentator());
		}

		model.addAttribute("step", Integer.parseInt(ekycDoanhNghiepTable.getStep()));

		model.addAttribute("fileBusinessRegistration",
				encode(encryptionAES.encrypt(ekycDoanhNghiep.getFileBusinessRegistration())));
		model.addAttribute("fileAppointmentOfChiefAccountant",
				encode(encryptionAES.encrypt(ekycDoanhNghiep.getFileAppointmentOfChiefAccountant())));
		model.addAttribute("fileBusinessRegistrationCertificate",
				encode(encryptionAES.encrypt(ekycDoanhNghiep.getFileBusinessRegistrationCertificate())));
		model.addAttribute("fileDecisionToAppointChiefAccountant",
				encode(encryptionAES.encrypt(ekycDoanhNghiep.getFileDecisionToAppointChiefAccountant())));
		model.addAttribute("fileInvestmentCertificate",
				encode(encryptionAES.encrypt(ekycDoanhNghiep.getFileInvestmentCertificate())));
		model.addAttribute("fileCompanyCharter",
				encode(encryptionAES.encrypt(ekycDoanhNghiep.getFileCompanyCharter())));
		model.addAttribute("fileSealSpecimen", encode(encryptionAES.encrypt(ekycDoanhNghiep.getFileSealSpecimen())));
		model.addAttribute("fileFatcaForms", encode(encryptionAES.encrypt(ekycDoanhNghiep.getFileFatcaForms())));
		model.addAttribute("fileOthers", encode(encryptionAES.encrypt(ekycDoanhNghiep.getFileOthers())));

		model.addAttribute("fileBusinessRegistrationBase64",
				CommonUtils.encodeFileToBase64Binary(new File(ekycDoanhNghiep.getFileBusinessRegistration())));
		model.addAttribute("fileAppointmentOfChiefAccountantBase64", CommonUtils
				.encodeFileToBase64Binary(new File((ekycDoanhNghiep.getFileAppointmentOfChiefAccountant()))));
		model.addAttribute("fileBusinessRegistrationCertificateBase64", CommonUtils
				.encodeFileToBase64Binary(new File((ekycDoanhNghiep.getFileBusinessRegistrationCertificate()))));
		model.addAttribute("fileDecisionToAppointChiefAccountantBase64", CommonUtils
				.encodeFileToBase64Binary(new File((ekycDoanhNghiep.getFileDecisionToAppointChiefAccountant()))));
		model.addAttribute("fileInvestmentCertificateBase64",
				CommonUtils.encodeFileToBase64Binary(new File((ekycDoanhNghiep.getFileInvestmentCertificate()))));
		model.addAttribute("fileCompanyCharterBase64",
				CommonUtils.encodeFileToBase64Binary(new File((ekycDoanhNghiep.getFileCompanyCharter()))));
		model.addAttribute("fileSealSpecimenBase64",
				CommonUtils.encodeFileToBase64Binary(new File((ekycDoanhNghiep.getFileSealSpecimen()))));
		model.addAttribute("fileFatcaFormsBase64",
				CommonUtils.encodeFileToBase64Binary(new File(ekycDoanhNghiep.getFileFatcaForms())));
		model.addAttribute("fileOthersBase64",
				CommonUtils.encodeFileToBase64Binary(new File((ekycDoanhNghiep.getFileOthers()))));

		model.addAttribute("token", ekycDoanhNghiepTable.getToken());
		params.setToken(ekycDoanhNghiepTable.getToken());
		params.setQueryParams(req.getQueryString());

		setParams(params, req);

		return "ekyc/ekycdn2";
	}

	@GetMapping(value = "/ekyc-enterprise/update-file")
	public String updateFile(Model model, HttpServletRequest req, @RequestParam Map<String, String> allParams) {
		ParamsKbank params = new ParamsKbank();
		String token = allParams.get("token");
		if (token == null)
			return "demo/doanhnghiep2/step/steperror";
		EkycDoanhNghiepTable doanhNghiep = ekycDoanhNghiepRepository.findByToken(token);
		if (doanhNghiep == null)
			return "demo/doanhnghiep2/step/steperror";
		
		if(doanhNghiep.getStatus().equals("success"))
			return "demo/doanhnghiep2/step/steperror";

		EkycDoanhNghiep ekycDoanhNghiep = new Gson().fromJson(doanhNghiep.getNoiDung(), EkycDoanhNghiep.class);

		if (ekycDoanhNghiep.getStatus() != null
				&& ekycDoanhNghiep.getStatus().equals(Contains.TRANG_THAI_KY_THANH_CONG))
			return "demo/doanhnghiep2/step/stepinfofile";

		model.addAttribute("ekycDoanhNghiep", ekycDoanhNghiep);
		model.addAttribute("userDesignation", ekycDoanhNghiep.getUserDesignation());
		model.addAttribute("listAccount", ekycDoanhNghiep.getListAccount());
		model.addAttribute("fileBusinessRegistration",
				encode(encryptionAES.encrypt(ekycDoanhNghiep.getFileBusinessRegistration())));
		model.addAttribute("fileAppointmentOfChiefAccountant",
				encode(encryptionAES.encrypt(ekycDoanhNghiep.getFileAppointmentOfChiefAccountant())));
		model.addAttribute("fileBusinessRegistrationCertificate",
				encode(encryptionAES.encrypt(ekycDoanhNghiep.getFileBusinessRegistrationCertificate())));
		model.addAttribute("fileDecisionToAppointChiefAccountant",
				encode(encryptionAES.encrypt(ekycDoanhNghiep.getFileDecisionToAppointChiefAccountant())));
		model.addAttribute("fileInvestmentCertificate",
				encode(encryptionAES.encrypt(ekycDoanhNghiep.getFileInvestmentCertificate())));
		model.addAttribute("fileCompanyCharter",
				encode(encryptionAES.encrypt(ekycDoanhNghiep.getFileCompanyCharter())));
		model.addAttribute("fileSealSpecimen", encode(encryptionAES.encrypt(ekycDoanhNghiep.getFileSealSpecimen())));
		model.addAttribute("fileFatcaForms", encode(encryptionAES.encrypt(ekycDoanhNghiep.getFileFatcaForms())));
		model.addAttribute("fileOthers", encode(encryptionAES.encrypt(ekycDoanhNghiep.getFileOthers())));

		model.addAttribute("fileBusinessRegistrationBase64",
				CommonUtils.encodeFileToBase64Binary(new File(ekycDoanhNghiep.getFileBusinessRegistration())));
		model.addAttribute("fileAppointmentOfChiefAccountantBase64", CommonUtils
				.encodeFileToBase64Binary(new File((ekycDoanhNghiep.getFileAppointmentOfChiefAccountant()))));
		model.addAttribute("fileBusinessRegistrationCertificateBase64", CommonUtils
				.encodeFileToBase64Binary(new File((ekycDoanhNghiep.getFileBusinessRegistrationCertificate()))));
		model.addAttribute("fileDecisionToAppointChiefAccountantBase64", CommonUtils
				.encodeFileToBase64Binary(new File((ekycDoanhNghiep.getFileDecisionToAppointChiefAccountant()))));
		model.addAttribute("fileInvestmentCertificateBase64",
				CommonUtils.encodeFileToBase64Binary(new File((ekycDoanhNghiep.getFileInvestmentCertificate()))));
		model.addAttribute("fileCompanyCharterBase64",
				CommonUtils.encodeFileToBase64Binary(new File((ekycDoanhNghiep.getFileCompanyCharter()))));
		model.addAttribute("fileSealSpecimenBase64",
				CommonUtils.encodeFileToBase64Binary(new File((ekycDoanhNghiep.getFileSealSpecimen()))));
		model.addAttribute("fileFatcaFormsBase64",
				CommonUtils.encodeFileToBase64Binary(new File(ekycDoanhNghiep.getFileFatcaForms())));
		model.addAttribute("fileOthersBase64",
				CommonUtils.encodeFileToBase64Binary(new File((ekycDoanhNghiep.getFileOthers()))));

		model.addAttribute("token", token);
		params.setToken(token);
		params.setQueryParams(req.getQueryString());

		setParams(params, req);

		return "ekyc/ekycdnUpdateFile";
	}

	private String encode(String value) {
		if (StringUtils.isEmpty(value))
			return null;
		return URLEncoder.encode(value, StandardCharsets.UTF_8);
	}

	@PostMapping(value = "/ekyc-enterprise/update-file", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String updateFilePost(HttpServletRequest req, @RequestBody String data) {
		JSONObject jsonObject2 = new JSONObject();

		try {
			ParamsKbank params = getParams(req);
			EkycDoanhNghiepTable doanhNghiep = ekycDoanhNghiepRepository.findByToken(params.getToken());
			if (doanhNghiep == null) {
				jsonObject2.put("status", 400);
			}

			Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();

			EkycDoanhNghiep ekycDoanhNghiepDb = gson.fromJson(doanhNghiep.getNoiDung(), EkycDoanhNghiep.class);

			EkycDoanhNghiep ekycDoanhNghiep = gson.fromJson(data, EkycDoanhNghiep.class);

			System.out.println(new Gson().toJson(ekycDoanhNghiep));
			FileHandling fileHandling = new FileHandling();

			ekycDoanhNghiep.setFileBusinessRegistration(
					luuAnh(ekycDoanhNghiep.getFileBusinessRegistration(), fileHandling, "abc.pdf"));
			ekycDoanhNghiep.setFileAppointmentOfChiefAccountant(
					luuAnh(ekycDoanhNghiep.getFileAppointmentOfChiefAccountant(), fileHandling, "abc.pdf"));
			ekycDoanhNghiep.setFileBusinessRegistrationCertificate(
					luuAnh(ekycDoanhNghiep.getFileBusinessRegistrationCertificate(), fileHandling, "abc.pdf"));
			ekycDoanhNghiep.setFileDecisionToAppointChiefAccountant(
					luuAnh(ekycDoanhNghiep.getFileDecisionToAppointChiefAccountant(), fileHandling, "abc.pdf"));
			ekycDoanhNghiep.setFileInvestmentCertificate(
					luuAnh(ekycDoanhNghiep.getFileInvestmentCertificate(), fileHandling, "abc.pdf"));
			ekycDoanhNghiep
					.setFileCompanyCharter(luuAnh(ekycDoanhNghiep.getFileCompanyCharter(), fileHandling, "abc.pdf"));
			ekycDoanhNghiep.setFileSealSpecimen(luuAnh(ekycDoanhNghiep.getFileSealSpecimen(), fileHandling, "abc.pdf"));
			ekycDoanhNghiep.setFileFatcaForms(luuAnh(ekycDoanhNghiep.getFileFatcaForms(), fileHandling, "abc.pdf"));
			ekycDoanhNghiep.setFileOthers(luuAnh(ekycDoanhNghiep.getFileOthers(), fileHandling, "abc.pdf"));

			updateObjectToObject(ekycDoanhNghiepDb, ekycDoanhNghiep);

			doanhNghiep.setNoiDung(new Gson().toJson(ekycDoanhNghiepDb));

			ekycDoanhNghiepRepository.save(doanhNghiep);
		} catch (Exception e) {
			e.printStackTrace();
		}
		jsonObject2.put("status", 200);
		return jsonObject2.toString();
	}

	@GetMapping(value = { "/ekyc-enterprise/pdf-byte" }, produces = MediaType.APPLICATION_PDF_VALUE)
	@ResponseBody
	public ResponseEntity<byte[]> getPdf(HttpServletResponse resp, @RequestParam Map<String, String> allParams,
			HttpServletRequest req) {
		try {
			ParamsKbank params = getParams(req);
			if (params == null)
				return null;
			if (!allParams.get("token").equals(params.getToken()))
				return null;
			String pathImg = encryptionAES.decrypt(allParams.get("path"));
			File file = new File(pathImg);

			byte[] bytes = StreamUtils.copyToByteArray(new FileInputStream(file));

			return ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF).body(bytes);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

	@GetMapping(value = "/ekyc-enterprise/upload")
	public String upload(Model model, HttpServletRequest req, @RequestParam Map<String, String> allParams) {
		ParamsKbank params = new ParamsKbank();
		String token = allParams.get("token");
		String tokenCheck = allParams.get("tokenCheck");
		if (token == null)
			return "demo/doanhnghiep2/step/steperror";
		if (tokenCheck == null)
			return "demo/doanhnghiep2/step/steperror";
		EkycDoanhNghiepTable doanhNghiep = ekycDoanhNghiepRepository.findByToken(token);
		if (doanhNghiep == null)
			return "demo/doanhnghiep2/step/steperror";

		String loai = new String(Base64.getDecoder().decode(allParams.get("type")));
		params.setCode(loai);
		params.setToken(token);
		params.setTokenCheck(tokenCheck);
		params.setQueryParams(req.getQueryString());

		setParams(params, req);

		return "demo/doanhnghiep2/step/uploadfile";
	}

	@PostMapping(value = "/ekyc-enterprise/upload")
	public String postupload(Model model, HttpServletRequest req, @RequestParam Map<String, String> allParams,
			@RequestParam("files") MultipartFile[] files) throws IOException {
		try {
			ParamsKbank params = getParams(req);
			if (params == null)
				return "redirect:" + "/ekyc-enterprise/upload";

			EkycDoanhNghiepTable doanhNghiep = ekycDoanhNghiepRepository.findByToken(params.getToken());
			if (doanhNghiep == null)
				return "demo/doanhnghiep2/step/steperror";

			Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
			EkycDoanhNghiep ekycDoanhNghiep = gson.fromJson(doanhNghiep.getNoiDung(), EkycDoanhNghiep.class);

			if (params.getCode().equals(Contains.NGUOI_DAI_DIEN_PHAP_LUAT)) {
				for (InfoPerson ip : ekycDoanhNghiep.getLegalRepresentator()) {
					if (ip.getTokenCheck().equals(params.getTokenCheck())) {
						updateInfo(ip, allParams, params, files);
					}
				}

			} else if (params.getCode().equals(Contains.KE_TOAN_TRUONG)) {
				if (ekycDoanhNghiep.getHaveAChiefAccountant().equals("yes")) {
					for (InfoPerson ip : ekycDoanhNghiep.getChiefAccountant()) {
						if (ip.getTokenCheck().equals(params.getTokenCheck())) {
							updateInfo(ip, allParams, params, files);
						}
					}
				}
			} else if (params.getCode().equals(Contains.UY_QUYEN_KE_TOAN_TRUONG)) {
				for (InfoPerson ip : ekycDoanhNghiep.getPersonAuthorizedChiefAccountant()) {
					if (ip.getTokenCheck().equals(params.getTokenCheck())) {
						updateInfo(ip, allParams, params, files);
					}
				}
			} else if (params.getCode().equals(Contains.NGUOI_DUOC_UY_QUYEN)) {
				for (InfoPerson ip : ekycDoanhNghiep.getPersonAuthorizedAccountHolder()) {
					if (ip.getTokenCheck().equals(params.getTokenCheck())) {
						updateInfo(ip, allParams, params, files);
					}
				}
			} else if (params.getCode().equals(Contains.BAN_LANH_DAO)) {
				for (InfoPerson ip : ekycDoanhNghiep.getListOfLeaders()) {
					if (ip.getTokenCheck().equals(params.getTokenCheck())) {
						updateInfo(ip, allParams, params, files);
					}
				}
			}

			doanhNghiep.setNoiDung(new Gson().toJson(ekycDoanhNghiep));
			ekycDoanhNghiepRepository.save(doanhNghiep);

			model.addAttribute("success", "Upload file success");
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("error", "Upload file fail");
		}

		return "demo/doanhnghiep2/step/uploadfile";
	}

	@GetMapping(value = "/ekyc-enterprise/esign")
	public String esign(Model model, HttpServletRequest req, @RequestParam Map<String, String> allParams)
			throws IOException {
		ParamsKbank params = new ParamsKbank();
		String token = allParams.get("token");
		String tokenCheck = allParams.get("tokenCheck");
		if (token == null)
			return "demo/doanhnghiep2/step/steperror";
		if (tokenCheck == null)
			return "demo/doanhnghiep2/step/steperror";
		EkycDoanhNghiepTable doanhNghiep = ekycDoanhNghiepRepository.findByToken(token);
		if (doanhNghiep == null)
			return "demo/doanhnghiep2/step/steperror";

		EkycDoanhNghiep ekycDoanhNghiep = new Gson().fromJson(doanhNghiep.getNoiDung(), EkycDoanhNghiep.class);
		model.addAttribute("ekycDoanhNghiep", ekycDoanhNghiep);

		InfoPerson nguoiUyQuyen = layNguoiDaiDienPhapLuatThaoTac(ekycDoanhNghiep, allParams);

		if (nguoiUyQuyen == null)
			return "demo/doanhnghiep2/step/steperror";

		model.addAttribute("lanhDaos", ekycDoanhNghiep.getListOfLeaders());
		model.addAttribute("nguoiUyQuyens", ekycDoanhNghiep.getPersonAuthorizedAccountHolder());
		model.addAttribute("nguoiUyQuyen", nguoiUyQuyen);
		model.addAttribute("logo",
				"data:image/jpeg;base64," + CommonUtils.encodeFileToBase64Binary(new File("/image/logoSN.png")));

		String pathFileOpenAcc = configProperties.getConfig().getImage_folder_log() + UUID.randomUUID().toString()
				+ ".pdf";
		System.out.println(pathFileOpenAcc);
		pdfHandling.nhapThongTinForm(pathFileOpenAcc, ekycDoanhNghiep, PATH_PDF_FILL_FORM);

		model.addAttribute("openFormBase64", CommonUtils.encodeFileToBase64Binary(new File(pathFileOpenAcc)));

		params.setToken(token);

		setParams(params, req);

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
		model.addAttribute("date", simpleDateFormat.format(new Date()));

		return "ekyc/ekycdn23";
	}

	private InfoPerson layNguoiDaiDienPhapLuatThaoTac(EkycDoanhNghiep ekycDoanhNghiep, Map<String, String> allParams) {
		for (InfoPerson ip : ekycDoanhNghiep.getLegalRepresentator()) {
			if (ip.getTokenCheck().equals(allParams.get("tokenCheck")) && ip.getCheckMain().equals("Y"))
				return ip;
		}
		return null;
	}

	@GetMapping(value = "/ekyc-enterprise/ekyc")
	public String ekyc(Model model, HttpServletRequest req, @RequestParam Map<String, String> allParams) {
		ParamsKbank params = new ParamsKbank();
		String token = allParams.get("token");
		String tokenCheck = allParams.get("tokenCheck");
		if (token == null)
			return "demo/doanhnghiep2/step/steperror";
		if (tokenCheck == null)
			return "demo/doanhnghiep2/step/steperror";
		EkycDoanhNghiepTable doanhNghiep = ekycDoanhNghiepRepository.findByToken(token);
		if (doanhNghiep == null)
			return "demo/doanhnghiep2/step/steperror";

		String loai = new String(Base64.getDecoder().decode(allParams.get("type")));
		params.setCode(loai);
		System.out.println("loai: " + loai);
		model.addAttribute("loai", loai);

		params.setToken(token);
		params.setTokenCheck(tokenCheck);
		params.setQueryParams(req.getQueryString());

		EkycDoanhNghiep ekycDoanhNghiep = new Gson().fromJson(doanhNghiep.getNoiDung(), EkycDoanhNghiep.class);

		InfoPerson infoPerson = layNguoiDaiDienPhapLuatThaoTac(ekycDoanhNghiep, allParams);

		if (daHetThoiGianLamEkyc(doanhNghiep, params, ekycDoanhNghiep, infoPerson)) {
			return "demo/doanhnghiep2/step/steperror";
		}

		setParams(params, req);

		return "demo/doanhnghiep2/step/step3";
	}

	private boolean daHetThoiGianLamEkyc(EkycDoanhNghiepTable doanhNghiep, ParamsKbank params,
			EkycDoanhNghiep ekycDoanhNghiep, InfoPerson infoPerson) {
		if (params.getCode().equals(Contains.NGUOI_DAI_DIEN_PHAP_LUAT)) {
			for (InfoPerson ip : ekycDoanhNghiep.getLegalRepresentator()) {
				if (ip.getTokenCheck().equals(params.getTokenCheck())) {
					if (hetThoiGianLamEkyc(ip, infoPerson))
						return true;
				}
			}

		} else if (params.getCode().equals(Contains.KE_TOAN_TRUONG)) {
			if (ekycDoanhNghiep.getHaveAChiefAccountant().equals("yes")) {
				for (InfoPerson ip : ekycDoanhNghiep.getChiefAccountant()) {
					if (ip.getTokenCheck().equals(params.getTokenCheck())) {
						if (hetThoiGianLamEkyc(ip, infoPerson))
							return true;
					}
				}
			}
		} else if (params.getCode().equals(Contains.UY_QUYEN_KE_TOAN_TRUONG)) {
			for (InfoPerson ip : ekycDoanhNghiep.getPersonAuthorizedChiefAccountant()) {
				if (ip.getTokenCheck().equals(params.getTokenCheck())) {
					if (hetThoiGianLamEkyc(ip, infoPerson))
						return true;
				}
			}
		} else if (params.getCode().equals(Contains.NGUOI_DUOC_UY_QUYEN)) {
			for (InfoPerson ip : ekycDoanhNghiep.getPersonAuthorizedAccountHolder()) {
				if (ip.getTokenCheck().equals(params.getTokenCheck())) {
					if (hetThoiGianLamEkyc(ip, infoPerson))
						return true;
				}
			}
		} else if (params.getCode().equals(Contains.BAN_LANH_DAO)) {
			for (InfoPerson ip : ekycDoanhNghiep.getListOfLeaders()) {
				if (ip.getTokenCheck().equals(params.getTokenCheck())) {
					if (hetThoiGianLamEkyc(ip, infoPerson))
						return true;
				}
			}
		}

		return false;
	}

	private boolean hetThoiGianLamEkyc(InfoPerson ip, InfoPerson infoPerson) {
	
		long thoiGianGuiMail = Long.valueOf(ip.getTime());
		long timeOut = configProperties.getConfig().getTimeout_nhan_link_ky() != null
				? Long.valueOf(configProperties.getConfig().getTimeout_nhan_link_ky())
				: 24;

		if (infoPerson != null) {
					timeOut = configProperties.getConfig().getTimeout_link_ky_form_cuoi() != null
					? Long.valueOf(configProperties.getConfig().getTimeout_link_ky_form_cuoi())
					: 24;
		}

		long thoiGianHetHan = timeOut * 60 * 60 * 1000L;
		long thoiGianhetHanEkyc = thoiGianGuiMail + thoiGianHetHan;
		if (thoiGianhetHanEkyc < System.currentTimeMillis())
			return true;

		return false;
		//return false;
	}

	@PostMapping(value = "/ekyc-enterprise/ekyc")
	public String step2(@RequestParam Map<String, String> allParams, HttpServletRequest req, Model model,
			@RequestParam(name = "anhMatTruoc", required = false) MultipartFile anhMatTruoc,
			@RequestParam(name = "anhMatSau", required = false) MultipartFile anhMatSau,
			@RequestParam(name = "anhChuKy", required = false) MultipartFile anhChuKy) throws IOException {
		forwartParams(allParams, model);

		ParamsKbank params = getParams(req);
		if (params == null)
			return "redirect:" + "/ekyc-enterprise/ekyc";

		String anhMatTruocBase64 = new String(Base64.getEncoder().encode(anhMatTruoc.getBytes()));
		String anhMatSauBase64 = new String(Base64.getEncoder().encode(anhMatSau.getBytes()));

		if (anhChuKy != null) {
			String anhChuKyBase64 = new String(Base64.getEncoder().encode(anhChuKy.getBytes()));
			System.out.println("anh chu ky:" + anhChuKyBase64);
			params.setAnhChuKy(anhChuKyBase64);
		}

		long time1 = System.currentTimeMillis();
		ParamPathImage paramPathImage = taoPathParamsPathImg(anhMatTruocBase64, anhMatSauBase64, null, null);

		String codeTransaction = CommonUtils.layMaGiaoDich(1);
		params.setCodeTransaction(codeTransaction);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("anhMatTruoc", anhMatTruocBase64);
		jsonObject.put("anhMatSau", anhMatSauBase64);

		String respone = postRequest(jsonObject.toString(), "/public/all/doc-noi-dung-ocr", params);
		System.out.println(respone);
		JSONObject object = new JSONObject(respone);

		String logId = luuLogApi(time1, object.getInt("status"), "/public/all/doc-noi-dung-ocr", "POST",
				codeTransaction, respone);
		luuChiTietLichSuApi(logId, respone, new Gson().toJson(paramPathImage));

		if (object.getInt("status") != 200) {
			model.addAttribute("error", object.getString("message"));

			return "demo/doanhnghiep2/step/step3";
		}

		JSONObject objectRespGiayTo = new JSONObject(respone);
		Ocr ocr = new Gson().fromJson(objectRespGiayTo.get("data").toString(), Ocr.class);

		params.setAnhMatTruoc(anhMatTruocBase64);
		params.setAnhMatSau(anhMatSauBase64);
		params.setRespGiayTo(respone);

		setParams(params, req);

		model.addAttribute("ocr", ocr);

		if (params.getCode().equals(Contains.NGUOI_DAI_DIEN_PHAP_LUAT)
				|| params.getCode().equals(Contains.KE_TOAN_TRUONG)) {
			return "demo/doanhnghiep2/step/step4";
		} else if (params.getCode().equals(Contains.NGUOI_DUOC_UY_QUYEN)
				|| params.getCode().equals(Contains.UY_QUYEN_KE_TOAN_TRUONG)
				|| params.getCode().equals(Contains.BAN_LANH_DAO)) {
			return "demo/doanhnghiep2/step/step51";
		} else {
			return "demo/doanhnghiep2/step/step5";
		}
	}

	private ParamPathImage taoPathParamsPathImg(String anhMatTruocBase64, String anhMatSauBase64,
			String anhCaNhanBase64, ArrayList<String> pathAnhVideos) {
		FileHandling fileHandling = new FileHandling();
		ParamPathImage paramPathImage = new ParamPathImage();
		if (!StringUtils.isEmpty(anhMatTruocBase64)) {
			String pathAnhMatTruoc = fileHandling.save(anhMatTruocBase64,
					configProperties.getConfig().getImage_folder_log());
			paramPathImage.setAnhMatTruoc(pathAnhMatTruoc);
		}
		if (!StringUtils.isEmpty(anhMatSauBase64)) {
			String pathAnhMatSau = fileHandling.save(anhMatSauBase64,
					configProperties.getConfig().getImage_folder_log());
			paramPathImage.setAnhMatSau(pathAnhMatSau);
		}
		if (!StringUtils.isEmpty(anhCaNhanBase64)) {
			String pathAnhCaNhan = fileHandling.save(anhCaNhanBase64,
					configProperties.getConfig().getImage_folder_log());
			paramPathImage.setAnhKhachHang(pathAnhCaNhan);
		}
		if (pathAnhVideos != null && pathAnhVideos.size() > 0) {
			paramPathImage.setAnhVideo(pathAnhVideos);
		}

		return paramPathImage;
	}

	@PostMapping(value = "/ekyc-enterprise/ekyc/step2")
	public String nhapThongTin(@RequestParam Map<String, String> allParams, HttpServletRequest req, Model model)
			throws IOException {

		forwartParams(allParams, model);
		String listImage = allParams.get("listImage");
		String[] arr = listImage.split(",");
		JSONArray jsonArray = new JSONArray();
		String anhCaNhan = "";

		FileHandling fileHandling = new FileHandling();
		long time1 = System.currentTimeMillis();

		String codeTransaction = CommonUtils.layMaGiaoDich(1);

		ArrayList<String> pathAnhVideos = new ArrayList<String>();
		for (int i = 0; i < arr.length; i++) {
			jsonArray.put(i, new JSONObject().put("anh", arr[i]).put("thoiGian", (i + 1)));
			if (StringUtils.isEmpty(anhCaNhan)) {
				anhCaNhan = arr[i];
			}

			String pathFileLog = fileHandling.save(arr[i], configProperties.getConfig().getImage_folder_log());
			pathAnhVideos.add(pathFileLog);
		}

		ParamPathImage paramPathImage = taoPathParamsPathImg(null, null, null, pathAnhVideos);

		ParamsKbank params = getParams(req);
		if (params == null)
			return "redirect:" + "/ekyc-enterprise/ekyc";
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("anhMatTruoc", params.getAnhMatTruoc());
		jsonObject.put("anhVideo", jsonArray);

		params.setCodeTransaction(codeTransaction);

		String respone = postRequest(jsonObject.toString(), "/public/all/xac-thuc-khuon-mat", params);
		JSONObject object = new JSONObject(respone);

		String logId = luuLogApi(time1, object.getInt("status"), "/public/all/xac-thuc-khuon-mat", "POST",
				codeTransaction, respone);
		luuChiTietLichSuApi(logId, respone, new Gson().toJson(paramPathImage));

		if (!MOI_TRUONG.equals("dev")) {
			if (object.getInt("status") != 200) {
				model.addAttribute("error", object.getString("message"));
				return "demo/doanhnghiep2/step/step4";
			}
		}

		JSONObject objectRespGiayTo = new JSONObject(params.getRespGiayTo());
		Ocr ocr = new Gson().fromJson(objectRespGiayTo.get("data").toString(), Ocr.class);

		model.addAttribute("params", params);
		model.addAttribute("ocr", ocr);

		params.setAnhCaNhan(anhCaNhan);
		setParams(params, req);
//		if (params.getCode().equals(Contains.BAN_LANH_DAO)
//				|| params.getCode().equals(Contains.NGUOI_DAI_DIEN_PHAP_LUAT) || params.getCode().equals(Contains.UY_QUYEN_KE_TOAN_TRUONG)) {
//			return "demo/doanhnghiep2/step/step51";
//		} else {
//			return "demo/doanhnghiep2/step/step5";
//		}

		return "demo/doanhnghiep2/step/step5";
	}

	@PostMapping(value = "/ekyc-enterprise/ekyc/step3")
	public String step3(@RequestParam Map<String, String> allParams, HttpServletRequest req, Model model)
			throws IOException {
		forwartParams(allParams, model);

		ParamsKbank params = getParams(req);
		if (params == null)
			return "redirect:" + "/ekyc-enterprise/ekyc";
		EkycDoanhNghiepTable doanhNghiep = ekycDoanhNghiepRepository.findByToken(params.getToken());
		EkycDoanhNghiep ekycDoanhNghiep = new Gson().fromJson(doanhNghiep.getNoiDung(), EkycDoanhNghiep.class);
		FileHandling fileHandling = new FileHandling();
		
		if(ekycDoanhNghiep.getAllInOne().equals("yes")) {
			if (params.getCode().equals(Contains.NGUOI_DAI_DIEN_PHAP_LUAT)) {
				for (InfoPerson ip : ekycDoanhNghiep.getLegalRepresentator()) {
					if (ip.getTokenCheck().equals(params.getTokenCheck())) {
						updateInfo(ip, allParams, params, null);
						for (int i = 0 ; i < ekycDoanhNghiep.getChiefAccountant().size() ; i++) {
							if (ip.getTokenCheck().equals(params.getTokenCheck())) {
								ekycDoanhNghiep.getChiefAccountant().get(i).setHoVaTen(allParams.get("hoVaTen"));
								ekycDoanhNghiep.getChiefAccountant().get(i).setSoCmt(allParams.get("soCmt"));
								ekycDoanhNghiep.getChiefAccountant().get(i).setNamSinh(allParams.get("namSinh"));
								ekycDoanhNghiep.getChiefAccountant().get(i).setNoiCap(allParams.get("noiCap"));
								ekycDoanhNghiep.getChiefAccountant().get(i).setHoKhau(allParams.get("hoKhau"));
								ekycDoanhNghiep.getChiefAccountant().get(i).setNgayCap(allParams.get("ngayCap"));
								ekycDoanhNghiep.getChiefAccountant().get(i).setNgayHetHan(allParams.get("ngayHetHan"));
								ekycDoanhNghiep.getChiefAccountant().get(i).setQuocTich(allParams.get("quocTich"));
								ekycDoanhNghiep.getChiefAccountant().get(i).setVisa(allParams.get("visa"));
								ekycDoanhNghiep.getChiefAccountant().get(i).setMaSoThue(allParams.get("maSoThue"));
								ekycDoanhNghiep.getChiefAccountant().get(i).setTinhTrangCuTru(allParams.get("tinhTrangCuTru"));
								ekycDoanhNghiep.getChiefAccountant().get(i).setDiaChiNha(allParams.get("diaChiNha"));
								ekycDoanhNghiep.getChiefAccountant().get(i).setMobile(allParams.get("mobile"));
								ekycDoanhNghiep.getChiefAccountant().get(i).setVanPhong(allParams.get("vanPhong"));
								ekycDoanhNghiep.getChiefAccountant().get(i).setDiaChi(allParams.get("diaChi"));
								ekycDoanhNghiep.getChiefAccountant().get(i).setEmail2(allParams.get("email2"));

								ekycDoanhNghiep.getChiefAccountant().get(i).setAnhMatTruoc(luuAnh(params.getAnhMatTruoc(), fileHandling, "abc.jpg"));
								ekycDoanhNghiep.getChiefAccountant().get(i).setAnhMatSau(luuAnh(params.getAnhMatSau(), fileHandling, "abc.jpg"));
								ekycDoanhNghiep.getChiefAccountant().get(i).setAnhMatTruoc(luuAnh(params.getAnhMatTruoc(), fileHandling, "abc.jpg"));
								ekycDoanhNghiep.getChiefAccountant().get(i).setAnhChuKy(luuAnh(params.getAnhChuKy(), fileHandling, "abc.png"));
								ekycDoanhNghiep.getChiefAccountant().get(i).setKiemTra(Contains.TRANG_THAI_THAO_TAC_THANH_CONG);
							
							}
						}
						for (int i = 0 ; i < ekycDoanhNghiep.getListOfLeaders().size() ; i++) {
							if (ip.getTokenCheck().equals(params.getTokenCheck())) {
								ekycDoanhNghiep.getListOfLeaders().get(i).setHoVaTen(allParams.get("hoVaTen"));
								ekycDoanhNghiep.getListOfLeaders().get(i).setSoCmt(allParams.get("soCmt"));
								ekycDoanhNghiep.getListOfLeaders().get(i).setNamSinh(allParams.get("namSinh"));
								ekycDoanhNghiep.getListOfLeaders().get(i).setNoiCap(allParams.get("noiCap"));
								ekycDoanhNghiep.getListOfLeaders().get(i).setHoKhau(allParams.get("hoKhau"));
								ekycDoanhNghiep.getListOfLeaders().get(i).setNgayCap(allParams.get("ngayCap"));
								ekycDoanhNghiep.getListOfLeaders().get(i).setNgayHetHan(allParams.get("ngayHetHan"));
								ekycDoanhNghiep.getListOfLeaders().get(i).setQuocTich(allParams.get("quocTich"));
								ekycDoanhNghiep.getListOfLeaders().get(i).setVisa(allParams.get("visa"));
								ekycDoanhNghiep.getListOfLeaders().get(i).setMaSoThue(allParams.get("maSoThue"));
								ekycDoanhNghiep.getListOfLeaders().get(i).setTinhTrangCuTru(allParams.get("tinhTrangCuTru"));
								ekycDoanhNghiep.getListOfLeaders().get(i).setDiaChiNha(allParams.get("diaChiNha"));
								ekycDoanhNghiep.getListOfLeaders().get(i).setMobile(allParams.get("mobile"));
								ekycDoanhNghiep.getListOfLeaders().get(i).setVanPhong(allParams.get("vanPhong"));
								ekycDoanhNghiep.getListOfLeaders().get(i).setDiaChi(allParams.get("diaChi"));
								ekycDoanhNghiep.getListOfLeaders().get(i).setEmail2(allParams.get("email2"));

								ekycDoanhNghiep.getListOfLeaders().get(i).setAnhMatTruoc(luuAnh(params.getAnhMatTruoc(), fileHandling, "abc.jpg"));
								ekycDoanhNghiep.getListOfLeaders().get(i).setAnhMatSau(luuAnh(params.getAnhMatSau(), fileHandling, "abc.jpg"));
								ekycDoanhNghiep.getListOfLeaders().get(i).setAnhMatTruoc(luuAnh(params.getAnhMatTruoc(), fileHandling, "abc.jpg"));
								ekycDoanhNghiep.getListOfLeaders().get(i).setAnhChuKy(luuAnh(params.getAnhChuKy(), fileHandling, "abc.png"));
								ekycDoanhNghiep.getListOfLeaders().get(i).setKiemTra(Contains.TRANG_THAI_THAO_TAC_THANH_CONG);
							
							}
						}
					}
				}
				
			}
		}else {
			if (params.getCode().equals(Contains.NGUOI_DAI_DIEN_PHAP_LUAT)) {
				for (InfoPerson ip : ekycDoanhNghiep.getLegalRepresentator()) {
					if (ip.getTokenCheck().equals(params.getTokenCheck())) {
						updateInfo(ip, allParams, params, null);
					}
				}

			} else if (params.getCode().equals(Contains.KE_TOAN_TRUONG)) {
				// if (ekycDoanhNghiep.getHaveAChiefAccountant().equals("yes")) {
				for (InfoPerson ip : ekycDoanhNghiep.getChiefAccountant()) {
					if (ip.getTokenCheck().equals(params.getTokenCheck())) {
						updateInfo(ip, allParams, params, null);
					}
					// }
				}
			} else if (params.getCode().equals(Contains.UY_QUYEN_KE_TOAN_TRUONG)) {
				for (InfoPerson ip : ekycDoanhNghiep.getPersonAuthorizedChiefAccountant()) {
					if (ip.getTokenCheck().equals(params.getTokenCheck())) {
						updateInfo(ip, allParams, params, null);
					}
				}
			} else if (params.getCode().equals(Contains.NGUOI_DUOC_UY_QUYEN)) {
				for (InfoPerson ip : ekycDoanhNghiep.getPersonAuthorizedAccountHolder()) {
					if (ip.getTokenCheck().equals(params.getTokenCheck())) {
						updateInfo(ip, allParams, params, null);
					}
				}
			} else if (params.getCode().equals(Contains.BAN_LANH_DAO)) {
				for (InfoPerson ip : ekycDoanhNghiep.getListOfLeaders()) {
					if (ip.getTokenCheck().equals(params.getTokenCheck())) {
						updateInfo(ip, allParams, params, null);
					}
				}
			} 
		}
		

		doanhNghiep.setNoiDung(new Gson().toJson(ekycDoanhNghiep));
		ekycDoanhNghiepRepository.save(doanhNghiep);

		if (kiemTraDaUpdateDuThongTin(ekycDoanhNghiep)) {
			for (InfoPerson ip : ekycDoanhNghiep.getLegalRepresentator()) {
				if (ip.getCheckMain().equals("Y")) {
					guiMailYeuCauXacThuc(ekycDoanhNghiep, ip, Contains.NGUOI_DAI_DIEN_PHAP_LUAT);

//					LOGGER.info("Send mail end: "+LINK_ADMIN+"/ekyc-enterprise/esign?token="+ekycDoanhNghiep.getToken()+"&tokenCheck="+ip.getTokenCheck());
//					if(!MOI_TRUONG.equals("dev")) 
//						email.sendText(ip.getEmail(), "Email yêu cầu ký số đăng ký mở tài khoản", 
//							"Vui lòng click vào <a href='"+LINK_ADMIN+"/ekyc-enterprise/esign?token="+ekycDoanhNghiep.getToken()+"&tokenCheck="+ip.getTokenCheck()+"'>Bắt đầu ký số</a>, để thực hiện ký số");
				}
			}

		}

		setParams(params, req);

		allParams.put("tokenCheck", params.getTokenCheck());
		InfoPerson nguoiUyQuyen = layNguoiDaiDienPhapLuatThaoTac(ekycDoanhNghiep, allParams);

		if (nguoiUyQuyen != null) {
			model.addAttribute("ekycDoanhNghiep", ekycDoanhNghiep);

			model.addAttribute("lanhDaos", ekycDoanhNghiep.getListOfLeaders());
			model.addAttribute("nguoiUyQuyens", ekycDoanhNghiep.getPersonAuthorizedAccountHolder());
			model.addAttribute("nguoiUyQuyen", nguoiUyQuyen);
			model.addAttribute("logo",
					"data:image/jpeg;base64," + CommonUtils.encodeFileToBase64Binary(new File("/image/logoSN.png")));
			model.addAttribute("tokens", params.getToken());
			try {

				String pathFileOpenAcc = configProperties.getConfig().getImage_folder_log()
						+ UUID.randomUUID().toString() + ".pdf";

				// String pathFileOpenAcc =
				// fileHandling.getFolder(configProperties.getConfig().getImage_folder_log()+code+"/")+UUID.randomUUID().toString()+".pdf";
				System.out.println(pathFileOpenAcc);
				pdfHandling.nhapThongTinForm(pathFileOpenAcc, ekycDoanhNghiep, PATH_PDF_FILL_FORM);

				model.addAttribute("openFormBase64", CommonUtils.encodeFileToBase64Binary(new File(pathFileOpenAcc)));
			} catch (Exception e) {
				e.printStackTrace();
			}

			params.setToken(token);

			setParams(params, req);

			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
			model.addAttribute("date", simpleDateFormat.format(new Date()));

			return "ekyc/ekycdn23";
		}

		return "demo/doanhnghiep2/step/success";
	}

	private InfoPerson layNguoiDaiDien(ArrayList<InfoPerson> legalRepresentator) {
		for (InfoPerson infoPerson : legalRepresentator) {
			if (infoPerson.getCheckMain().equals("Y"))
				return infoPerson;
		}
		return null;
	}

	@PostMapping(value = "/ekyc-enterprise/ekyc/register-sign", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String registerSign(@RequestParam Map<String, String> allParams, HttpServletRequest req, Model model,
			RedirectAttributes redirectAttributes) throws Exception {
		JSONObject jsonResp = new JSONObject();
		try {
			ParamsKbank params = getParams(req);
			if (params == null)
				return "redirect:" + "/ekyc-enterprise/ekyc";
			EkycDoanhNghiepTable doanhNghiep = ekycDoanhNghiepRepository.findByToken(params.getToken());
			EkycDoanhNghiep ekycDoanhNghiep = new Gson().fromJson(doanhNghiep.getNoiDung(), EkycDoanhNghiep.class);

			InfoPerson infoPerson = layThongTinNguoiKy(ekycDoanhNghiep, params);

			if (infoPerson == null)
				throw new Exception();

			String text = IOUtils.toString(req.getInputStream(), StandardCharsets.UTF_8.name());

			JSONObject jsonObjectPr = new JSONObject(text);

			forwartParams(allParams, model);
			String nameFile = UUID.randomUUID().toString() + ".pdf";
			String pathPdf = KY_SO_FOLDER + "/" + nameFile;
			String agreementUUID = UUID.randomUUID().toString();

			ParamsKbank paramsKySo = new ParamsKbank();
			FormInfo formInfo = new FormInfo();
			formInfo.setHoVaTen(infoPerson.getHoVaTen());
			formInfo.setSoCmt(infoPerson.getSoCmt());
			formInfo.setDiaChi("Hà Nội");
			formInfo.setThanhPho("Hà Nội");
			formInfo.setQuocGia("Việt Nam");
			paramsKySo.setSoDienThoai(infoPerson.getPhone());
			paramsKySo.setFormInfo(formInfo);
			paramsKySo.setAnhMatTruoc(Utils.encodeFileToBase64Binary(new File(infoPerson.getAnhMatTruoc())));
			paramsKySo.setAnhMatSau(Utils.encodeFileToBase64Binary(new File(infoPerson.getAnhMatSau())));

			System.out.println(KY_SO_FOLDER);
			byte[] decodedImg = Base64.getDecoder()
					.decode(jsonObjectPr.getString("file").getBytes(StandardCharsets.UTF_8));

			Path destinationFile = Paths.get(KY_SO_FOLDER, nameFile);
			Files.write(destinationFile, decodedImg);

			System.out.println("PDF Created!");

			String jsonRegister = guiThongTinDangKyKySo(paramsKySo, agreementUUID);
			ObjectMapper objectMapper = new ObjectMapper();
			SignCloudResp signCloudRespRegister = objectMapper.readValue(jsonRegister, SignCloudResp.class);

			if (signCloudRespRegister.getResponseCode() != 0) {
				jsonResp.put("message", "Không đăng ký được chữ ký ");
				jsonResp.put("status", 400);

				return jsonResp.toString();
			}
			String page = "1";
			String textPage = "Name of the Account";

			String jsonResponse = guiThongTinKySo(req, pathPdf, nameFile, agreementUUID, page, textPage);

			SignCloudResp signCloudResp = objectMapper.readValue(jsonResponse, SignCloudResp.class);

			if (signCloudResp.getResponseCode() != 1007) {
				jsonResp.put("message", "Không gửi được chữ ký ");
				jsonResp.put("status", 400);

				return jsonResp.toString();
			}

			if (!MOI_TRUONG.equals("dev")) {
				LOGGER.info("dienThoai SMS: {}", infoPerson.getPhone());
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("dienThoai", infoPerson.getPhone());
				postRequest(jsonObject.toString(),
						"/public/gui-ma-otp-ky-so?code=" + signCloudResp.getAuthorizeCredential());
			}

			jsonResp.put("otp", signCloudResp.getAuthorizeCredential());
			jsonResp.put("maKy", signCloudResp.getBillCode());

//			postRequest(jsonObject.toString(), "/public/gui-ma-otp-ky-so?code=1234");
//			
//			jsonResp.put("otp", "1234");
//			jsonResp.put("maKy", "123456789");
			jsonResp.put("pathPdf", pathPdf);
			jsonResp.put("nameFile", nameFile);
			jsonResp.put("agreementUUID", agreementUUID);
		} catch (Exception e) {
			e.printStackTrace();
			jsonResp.put("message", "Lỗi hệ thống");
			jsonResp.put("status", 400);

			return jsonResp.toString();
		}

		jsonResp.put("status", 200);

		return jsonResp.toString();
	}

	private InfoPerson layThongTinNguoiKy(EkycDoanhNghiep ekycDoanhNghiep, ParamsKbank params) {
		if (params.getCode().equals(Contains.NGUOI_DAI_DIEN_PHAP_LUAT)) {
			for (InfoPerson ip : ekycDoanhNghiep.getLegalRepresentator()) {
				if (ip.getTokenCheck().equals(params.getTokenCheck())) {
					return ip;
				}
			}
		} else if (params.getCode().equals(Contains.KE_TOAN_TRUONG)) {
			for (InfoPerson ip : ekycDoanhNghiep.getChiefAccountant()) {
				if (ip.getTokenCheck().equals(params.getTokenCheck())) {
					return ip;
				}
			}
		} else if (params.getCode().equals(Contains.UY_QUYEN_KE_TOAN_TRUONG)) {
			for (InfoPerson ip : ekycDoanhNghiep.getPersonAuthorizedChiefAccountant()) {
				if (ip.getTokenCheck().equals(params.getTokenCheck())) {
					return ip;
				}
			}
		} else if (params.getCode().equals(Contains.NGUOI_DUOC_UY_QUYEN)) {
			for (InfoPerson ip : ekycDoanhNghiep.getPersonAuthorizedAccountHolder()) {
				if (ip.getTokenCheck().equals(params.getTokenCheck())) {
					return ip;
				}
			}
		} else if (params.getCode().equals(Contains.BAN_LANH_DAO)) {
			for (InfoPerson ip : ekycDoanhNghiep.getListOfLeaders()) {
				if (ip.getTokenCheck().equals(params.getTokenCheck())) {
					return ip;
				}
			}
		}
		return null;
	}

	@PostMapping(value = "/ekyc-enterprise/ekyc/step4", produces = MediaType.APPLICATION_JSON_VALUE)
	public String kySoOtpStep4(@RequestParam Map<String, String> allParams, HttpServletRequest req, Model model,
			RedirectAttributes redirectAttributes) throws Exception {
		try {
			eSignCall service = new eSignCall();
			String jsonResponse = service.authorizeSingletonSigningForSignCloud(allParams.get("agreementUUID"),
					allParams.get("otpKySo"), allParams.get("maKy"));
			ObjectMapper objectMapper = new ObjectMapper();
			SignCloudResp signCloudResp = objectMapper.readValue(jsonResponse, SignCloudResp.class);
			if (signCloudResp.getResponseCode() == 0 && signCloudResp.getSignedFileData() != null) {
				String str = KY_SO_FOLDER + "/" + UUID.randomUUID().toString() + ".pdf";
				File file2 = new File(str);
				IOUtils.write(signCloudResp.getSignedFileData(), new FileOutputStream(file2));
				model.addAttribute("file", CommonUtils.encodeFileToBase64Binary(file2));
				model.addAttribute("success", "Ký số thành công");

				capNhatThongTin(req, str);

				return "demo/doanhnghiep2/step/kySoThanhCong";
			} else if (signCloudResp.getResponseCode() == 1004) {
				model.addAttribute("error", "Lỗi OTP");
			} else {
				model.addAttribute("error", "Ký số thất bại");
			}
		} catch (Exception e) {
			model.addAttribute("error", "Lỗi hệ thống");
		}
		model.addAttribute("file",
				Utils.encodeFileToBase64Binary(new File(KY_SO_FOLDER + "/" + "ACCOUNT-OPENING-FORM_Dummy.pdf")));

		return "demo/doanhnghiep2/step/viewFileKySo";
	}

	private boolean capNhatThongTin(HttpServletRequest req, String filePath) {
		ParamsKbank params = getParams(req);
		if (params == null)
			return false;
		EkycDoanhNghiepTable doanhNghiep = ekycDoanhNghiepRepository.findByToken(params.getToken());
		EkycDoanhNghiep ekycDoanhNghiep = new Gson().fromJson(doanhNghiep.getNoiDung(), EkycDoanhNghiep.class);
		if (params.getCode().equals(Contains.NGUOI_DAI_DIEN_PHAP_LUAT)) {
			for (InfoPerson ip : ekycDoanhNghiep.getLegalRepresentator()) {
				if (ip.getTokenCheck().equals(params.getTokenCheck())) {
					ip.setFile(filePath);
				}
			}
		} else if (params.getCode().equals(Contains.KE_TOAN_TRUONG)) {
			if (ekycDoanhNghiep.getHaveAChiefAccountant().equals("yes")) {
				for (InfoPerson ip : ekycDoanhNghiep.getChiefAccountant()) {
					if (ip.getTokenCheck().equals(params.getTokenCheck())) {
						ip.setFile(filePath);
					}
				}
			} else {
				for (InfoPerson ip : ekycDoanhNghiep.getPersonAuthorizedChiefAccountant()) {
					if (ip.getTokenCheck().equals(params.getTokenCheck())) {
						ip.setFile(filePath);
					}
				}
			}
		} else if (params.getCode().equals(Contains.NGUOI_DUOC_UY_QUYEN)) {
			for (InfoPerson ip : ekycDoanhNghiep.getPersonAuthorizedAccountHolder()) {
				if (ip.getTokenCheck().equals(params.getTokenCheck())) {
					ip.setFile(filePath);
				}
			}
		} else if (params.getCode().equals(Contains.BAN_LANH_DAO)) {
			for (InfoPerson ip : ekycDoanhNghiep.getListOfLeaders()) {
				if (ip.getTokenCheck().equals(params.getTokenCheck())) {
					ip.setFile(filePath);
				}
			}
		}
		doanhNghiep.setNoiDung(new Gson().toJson(ekycDoanhNghiep));
		ekycDoanhNghiepRepository.save(doanhNghiep);
		return true;

	}

	private boolean sendMailEkyc(ArrayList<InfoPerson> listInfoPersons, InfoPerson ipSave) {
		if (listInfoPersons.size() == 1)
			return false;
		boolean checkFullUpdate = true;
		for (InfoPerson infoPerson : listInfoPersons) {
			if (infoPerson.getKiemTra() == null && infoPerson.getCheckMain().equals("N"))
				checkFullUpdate = false;
		}

		if (ipSave.getCheckMain().equals("Y"))
			return false;

		if (checkFullUpdate)
			return true;

		return false;
	}

	private void updateInfo(InfoPerson ip, Map<String, String> allParams, ParamsKbank params, MultipartFile[] files)
			throws IOException {
		FileHandling fileHandling = new FileHandling();
		ip.setHoVaTen(allParams.get("hoVaTen"));
		ip.setSoCmt(allParams.get("soCmt"));
		ip.setNamSinh(allParams.get("namSinh"));
		ip.setNoiCap(allParams.get("noiCap"));
		ip.setHoKhau(allParams.get("hoKhau"));
		ip.setNgayCap(allParams.get("ngayCap"));
		ip.setNgayHetHan(allParams.get("ngayHetHan"));
		ip.setQuocTich(allParams.get("quocTich"));
		ip.setVisa(allParams.get("visa"));
		ip.setMaSoThue(allParams.get("maSoThue"));
		ip.setTinhTrangCuTru(allParams.get("tinhTrangCuTru"));
		ip.setDiaChiNha(allParams.get("diaChiNha"));
		ip.setMobile(allParams.get("mobile"));
		ip.setVanPhong(allParams.get("vanPhong"));
		ip.setDiaChi(allParams.get("diaChi"));
		ip.setEmail2(allParams.get("email2"));

		ip.setAnhMatTruoc(luuAnh(params.getAnhMatTruoc(), fileHandling, "abc.jpg"));
		ip.setAnhMatSau(luuAnh(params.getAnhMatSau(), fileHandling, "abc.jpg"));
		ip.setAnhMatTruoc(luuAnh(params.getAnhMatTruoc(), fileHandling, "abc.jpg"));
		ip.setAnhChuKy(luuAnh(params.getAnhChuKy(), fileHandling, "abc.png"));
		ip.setKiemTra(Contains.TRANG_THAI_THAO_TAC_THANH_CONG);
	

		if (files != null) {
			ArrayList<String> listFiles = new ArrayList<>();
			for (MultipartFile multipartFile : files) {
				String base64 = Base64.getEncoder().encodeToString(multipartFile.getBytes());
				listFiles.add(luuAnh(base64, fileHandling, multipartFile.getOriginalFilename()));
			}
			ip.setFiles(listFiles);
		}

	}

	
	private boolean kiemTraDaUpdateDuThongTin(EkycDoanhNghiep ekycDoanhNghiep) {
		try {
			boolean check = true;
			for (InfoPerson ip : ekycDoanhNghiep.getLegalRepresentator()) {

				if (!ip.getCheckMain().equals("Y")) {
					if (!ip.getKiemTra().equals(Contains.TRANG_THAI_THAO_TAC_THANH_CONG))
						return false;
				}
			}
			for (InfoPerson ip : ekycDoanhNghiep.getChiefAccountant()) {
				if (!ip.getKiemTra().equals(Contains.TRANG_THAI_THAO_TAC_THANH_CONG))
					return false;
			}
			if(ekycDoanhNghiep.getPersonAuthorizedChiefAccountant() != null) {
				for (InfoPerson ip : ekycDoanhNghiep.getPersonAuthorizedChiefAccountant()) {
					if (!ip.getKiemTra().equals(Contains.TRANG_THAI_THAO_TAC_THANH_CONG))
						return false;
				}
			}
			
			if(ekycDoanhNghiep.getPersonAuthorizedAccountHolder() != null) {
				for (InfoPerson ip : ekycDoanhNghiep.getPersonAuthorizedAccountHolder()) {
					if (!ip.getKiemTra().equals(Contains.TRANG_THAI_THAO_TAC_THANH_CONG))
						return false;
				}
			}
			
			for (InfoPerson ip : ekycDoanhNghiep.getListOfLeaders()) {
				if (!ip.getKiemTra().equals(Contains.TRANG_THAI_THAO_TAC_THANH_CONG))
					return false;

			}
			return check;
		} catch (Exception e) {
		}
		return false;
	}

	@PostMapping(value = "/ekyc-enterprise/thong-tin-doanh-nghiep", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String ekycThongTinDoanhNghiep(HttpServletRequest req,
			@RequestParam("fileDangKyKinhDoanh") MultipartFile fileDangKyKinhDoanh,
			@RequestParam Map<String, String> allParams) throws IOException {
		/*
		 * if (khongTrongThoiGianXyLy(req)) { return "{\"status\":505}"; }
		 */
		long time1 = System.currentTimeMillis();
		FileHandling fileHandling = new FileHandling();
		ParamPathImage paramPathImage = new ParamPathImage();
		String pathFile = fileHandling.save(Base64.getEncoder().encodeToString(fileDangKyKinhDoanh.getBytes()),
				configProperties.getConfig().getImage_folder_log(), fileDangKyKinhDoanh.getOriginalFilename());
		paramPathImage.setFile(pathFile);
		String codeTransaction = CommonUtils.layMaGiaoDich(1);

		String resp = sendRequest(fileDangKyKinhDoanh, allParams, "/public/all/pr/thong-tin-doanh-nghiep",
				codeTransaction);

		JSONObject jsonObject = new JSONObject(resp);
		String logId = luuLogApi(time1, jsonObject.getInt("status"), "/public/all/pr/thong-tin-doanh-nghiep", "POST",
				codeTransaction, jsonObject.toString());
		luuChiTietLichSuApi(logId, jsonObject.toString(), new Gson().toJson(paramPathImage));

		return resp;
	}

	@PostMapping(value = "/ekyc-enterprise/thong-tin-ktt", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String thongTinKtt(HttpServletRequest req,
			@RequestParam("fileQuyetDinhBoNhiemKtt") MultipartFile fileQuyetDinhBoNhiemKtt,
			@RequestParam Map<String, String> allParams) throws IOException {

		long time1 = System.currentTimeMillis();
		FileHandling fileHandling = new FileHandling();
		ParamPathImage paramPathImage = new ParamPathImage();
		String pathFile = fileHandling.save(Base64.getEncoder().encodeToString(fileQuyetDinhBoNhiemKtt.getBytes()),
				configProperties.getConfig().getImage_folder_log(), fileQuyetDinhBoNhiemKtt.getOriginalFilename());
		paramPathImage.setFile(pathFile);
		String codeTransaction = CommonUtils.layMaGiaoDich(1);

		String resp = sendRequest(fileQuyetDinhBoNhiemKtt, allParams.get("loaiGiayToQuyetDinhBoNhiemKtt"),
				"/public/all/pr/ocr-template");

		JSONObject jsonObject = new JSONObject(resp);
		String logId = luuLogApi(time1, jsonObject.getInt("status"), "/public/all/pr/ocr-template", "POST",
				codeTransaction, jsonObject.toString());
		luuChiTietLichSuApi(logId, jsonObject.toString(), new Gson().toJson(paramPathImage));

		return resp;
	}

	@GetMapping(value = "/ekyc-enterprise/language")
	public String nguoiDung(Model model, HttpServletRequest req, @RequestParam Map<String, String> allParams) {

		return "redirect:" + req.getContextPath() + req.getHeader("referer");
	}

	@GetMapping(value = "/ekyc-enterprise/index")
	public String ekycIndex(Model model) {

		model.addAttribute("urlApi", API_SERVICE);
		model.addAttribute("token", token);
		return "ekyc/index";
	}

	@GetMapping(value = "/ekyc-enterprise")
	public String ekyc(Model model, HttpServletRequest req) {
		/*
		 * HttpSession session = req.getSession(); //Object a = sess Object username =
		 * req.getSession().getAttribute("b_username"); if
		 * (StringUtils.isEmpty(username)) return "redirect:/login-doanh-nghiep";
		 * 
		 * ParamsKbank paramsKbank = new ParamsKbank();
		 * paramsKbank.setTimeStartStep(System.currentTimeMillis());
		 * setParams(paramsKbank, req); EkycDoanhNghiepTable ekycDoanhNghiepTable =
		 * ekycDoanhNghiepRepository .findByUsername(username.toString());
		 * 
		 * if (ekycDoanhNghiepTable != null) { model.addAttribute("step",
		 * ekycDoanhNghiepTable.getStep() != null ? ekycDoanhNghiepTable.getStep() : 1);
		 * System.out.println("step :" + ekycDoanhNghiepTable.getStep()); }
		 * 
		 * EkycDoanhNghiep ekycDoanhNghiep = new
		 * Gson().fromJson(ekycDoanhNghiepTable.getNoiDung(), EkycDoanhNghiep.class);
		 */
		// String checkMianLegalRepresentator = ekycDoanhNghiep.get;
		String haveAChiefAccountant = "no";
		String allInOne = "no";
		String haveAcccountHolder = "no";
		/*
		 * model.addAttribute("ekycDoanhNghiep", ekycDoanhNghiep);
		 * model.addAttribute("ekycDoanhNghiepTable", ekycDoanhNghiepTable);
		 */
		/*
		 * if(ekycDoanhNghiep.getUserDesignation() != null &&
		 * ekycDoanhNghiep.getUserDesignation().size() > 0) {
		 * model.addAttribute("userDesignation", ekycDoanhNghiep.getUserDesignation());
		 * } else {
		 */
		ArrayList<Account> accounts = new ArrayList<>();
		accounts.add(new Account());
		model.addAttribute("listAccount", accounts);
		ArrayList<InfoPerson> infoPersons = new ArrayList<>();
		infoPersons.add(new InfoPerson());
		model.addAttribute("userDesignation", infoPersons);
		model.addAttribute("legalRepresentator", infoPersons);
		model.addAttribute("chiefAccountant", infoPersons);
		model.addAttribute("listOfLeaders", infoPersons);
		model.addAttribute("personAuthorizedAccountHolder", infoPersons);
		model.addAttribute("personAuthorizedChiefAccountant", infoPersons);
		model.addAttribute("haveAChiefAccountant", haveAChiefAccountant);
		// }
		req.getSession().setAttribute("listAccount", accounts);
		 req.getSession().setAttribute("allInOne",allInOne);
		 req.getSession().setAttribute("haveAcccountHolder",haveAcccountHolder);
		
		req.getSession().setAttribute("userDesignation", infoPersons);
		req.getSession().setAttribute("legalRepresentator", infoPersons);
		req.getSession().setAttribute("chiefAccountant", infoPersons);
		req.getSession().setAttribute("listOfLeaders", infoPersons);
		req.getSession().setAttribute("personAuthorizedAccountHolder", infoPersons);
		req.getSession().setAttribute("personAuthorizedChiefAccountant", infoPersons);
		req.getSession().setAttribute("haveAChiefAccountant", haveAChiefAccountant);
		model.addAttribute("urlApi", API_SERVICE);
		model.addAttribute("urlCompare", "/ekyc-enterprise");

		model.addAttribute("token", token);
		return "ekyc/ekycdn2";
	}

	@PostMapping(value = "/ekyc-enterprise/html-pdf", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String htmlPdf(@RequestParam Map<String, String> allParams, HttpServletRequest req, Model model,
			RedirectAttributes redirectAttributes) throws Exception {
		JSONObject jsonResp = new JSONObject();
		String text = IOUtils.toString(req.getInputStream(), StandardCharsets.UTF_8.name());
		JSONObject jsonObjectPr = new JSONObject(text);

		String HTML = jsonObjectPr.getString("contentPdf");
		String nameFile = UUID.randomUUID().toString() + ".pdf";
		String pathPdf = KY_SO_FOLDER + "/" + nameFile;

		HtmlConverter.convertToPdf(HTML, new FileOutputStream(pathPdf));

		jsonResp.put("file", CommonUtils.encodeFileToBase64Binary(new File(pathPdf)));

		return jsonResp.toString();
	}

	@PostMapping(value = "/ekyc-enterprise/fill-form-pdf", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String fillFormPdf(@RequestParam Map<String, String> allParams, HttpServletRequest req, Model model,
			RedirectAttributes redirectAttributes) throws Exception {
		JSONObject jsonResp = new JSONObject();

		ParamsKbank params = getParams(req);
		if (params == null)
			return "redirect:" + "/ekyc-enterprise/ekyc";

		EkycDoanhNghiepTable doanhNghiep = ekycDoanhNghiepRepository.findByToken(params.getToken());
		if (doanhNghiep == null)
			return "demo/doanhnghiep2/step/steperror";
		EkycDoanhNghiep ekycDoanhNghiep = new Gson().fromJson(doanhNghiep.getNoiDung(), EkycDoanhNghiep.class);

		String pathFileOpenAcc = configProperties.getConfig().getImage_folder_log() + UUID.randomUUID().toString()
				+ ".pdf";
		pdfHandling.nhapThongTinForm(pathFileOpenAcc, ekycDoanhNghiep, PATH_PDF_FILL_FORM);

		jsonResp.put("file", CommonUtils.encodeFileToBase64Binary(new File(pathFileOpenAcc)));

		return jsonResp.toString();
	}

	@PostMapping(value = "/ekyc-enterprise/doc-noi-dung-ocr", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String docNoiDung(HttpServletRequest req, @RequestBody String data) {
		JSONObject jsonObject = new JSONObject(data);

		if (jsonObject.has("anhChanDung")) {
			FileHandling fileHandling = new FileHandling();
			long time1 = System.currentTimeMillis();
			String pathAnhChanDung = fileHandling.save(jsonObject.getString("anhChanDung"),
					configProperties.getConfig().getImage_folder_log());
			String pathAnhMatTruoc = fileHandling.save(jsonObject.getString("anhMatTruoc"),
					configProperties.getConfig().getImage_folder_log());
			ParamPathImage paramPathImage = new ParamPathImage();
			paramPathImage.setAnhKhachHang(pathAnhChanDung);
			paramPathImage.setAnhMatTruoc(pathAnhMatTruoc);

			JSONObject object = new JSONObject();
			object.put("anhMatTruoc", jsonObject.getString("anhMatTruoc"));
			object.put("anhKhachHang", jsonObject.getString("anhChanDung"));

			String codeTransaction = CommonUtils.layMaGiaoDich(1);

			String jsonCompare = postRequest(object.toString(), "/public/all/so-sanh-anh");
			JSONObject jsonObject3 = new JSONObject(jsonCompare);

			String logId = luuLogApi(time1, jsonObject3.getInt("status"), "/public/all/so-sanh-anh", "POST",
					codeTransaction, jsonCompare);
			luuChiTietLichSuApi(logId, jsonCompare, new Gson().toJson(paramPathImage));

			if (jsonObject3.getInt("status") != 200)
				return jsonObject3.toString();
		}
		String jsonOcr = postRequest(data, "/public/doc-noi-dung-ocr");
		JSONObject jsonObject2 = new JSONObject(jsonOcr);

		return jsonObject2.toString();
	}

	private String luuLogApi(long time1, int status, String uri, String method, String codeTransaction, String resp) {
		try {
			long time2 = System.currentTimeMillis();
			long timeHandling = time2 - time1;

			LogApi logApi = new LogApi();
			logApi.setLogId(UUID.randomUUID().toString());
			logApi.setTimeHandling(timeHandling);
			logApi.setDate(new Date());
			logApi.setStatus(status);
			logApi.setToken(token);
			logApi.setCode(code);
			logApi.setUri(uri);
			logApi.setMethod(method);
			logApi.setCodeTransaction(codeTransaction);
			logApi.setResponse(resp);
			logApiRepository.save(logApi);

			return logApi.getLogId();
		} catch (Exception e) {
		}

		return null;
	}

	private void luuChiTietLichSuApi(String requestId, String responseBody, Object images) {
		try {
			LogApiDetail logApi = new LogApiDetail();
			logApi.setLogId(requestId);
			logApi.setResponse(responseBody);
			logApi.setImages(images.toString());
			logApiDetailRepository.save(logApi);

		} catch (Exception e) {
		}

	}

	@PostMapping(value = "/ekyc-enterprise/luu-tru-file", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String luuTruFile(HttpServletRequest req, @RequestBody String data) {
		try {
			Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
			EkycDoanhNghiep ekycDoanhNghiep = gson.fromJson(data, EkycDoanhNghiep.class);
			FileHandling fileHandling = new FileHandling();

			EkycDoanhNghiepTable doanhNghiep = ekycDoanhNghiepRepository.findByToken(ekycDoanhNghiep.getToken());
			if (doanhNghiep == null)
				return "demo/doanhnghiep2/step/steperror";

			EkycDoanhNghiep ekycDoanhNghiep2 = gson.fromJson(doanhNghiep.getNoiDung(), EkycDoanhNghiep.class);

			ekycDoanhNghiep2.setFileKy(luuAnh(ekycDoanhNghiep.getFileKy(), fileHandling, "abc.pdf"));
			ekycDoanhNghiep2.setFileDangKy(luuAnh(ekycDoanhNghiep.getFileDangKy(), fileHandling, "abc.pdf"));
			ekycDoanhNghiep2.setStatus(Contains.TRANG_THAI_KY_THANH_CONG);
			doanhNghiep.setStatus(Contains.TRANG_THAI_KY_THANH_CONG);

			doanhNghiep.setNoiDung(new Gson().toJson(ekycDoanhNghiep2));

			ekycDoanhNghiepRepository.save(doanhNghiep);

			sendAllMailSuccessSign(doanhNghiep, ekycDoanhNghiep2);

		} catch (Exception e) {
			e.printStackTrace();
		}
		JSONObject jsonObject2 = new JSONObject();

		return jsonObject2.toString();
	}

	private void sendAllMailSuccessSign(EkycDoanhNghiepTable doanhNghiep, EkycDoanhNghiep ekycDoanhNghiep) {
		try {
			for (InfoPerson ip : ekycDoanhNghiep.getLegalRepresentator()) {
				guiMailKyThanhCong(ekycDoanhNghiep, ip);
			}

			for (InfoPerson ip : ekycDoanhNghiep.getChiefAccountant()) {
				guiMailKyThanhCong(ekycDoanhNghiep, ip);
			}
			for (InfoPerson ip : ekycDoanhNghiep.getPersonAuthorizedChiefAccountant()) {
				guiMailKyThanhCong(ekycDoanhNghiep, ip);
			}

			for (InfoPerson ip : ekycDoanhNghiep.getPersonAuthorizedAccountHolder()) {
				guiMailKyThanhCong(ekycDoanhNghiep, ip);
			}
			for (InfoPerson ip : ekycDoanhNghiep.getListOfLeaders()) {
				guiMailKyThanhCong(ekycDoanhNghiep, ip);
			}
		} catch (Exception e) {
		}
	}

	private void guiMailKyThanhCong(EkycDoanhNghiep ekycDoanhNghiep, InfoPerson ip) {
		LOGGER.info("Send mail: ký thành công hợp đồng {}", ip.getEmail());
		if (!MOI_TRUONG.equals("dev"))
			email.sendText(ip.getEmail(), "Email ký thành công hợp đồng", "Hợp đồng đã được ký thành công");

	}

//	@PostMapping(value = "/ekyc-enterprise/luu-tru-thong-tin", produces = MediaType.APPLICATION_JSON_VALUE)
//	@ResponseBody
//	public String luuTru(HttpServletRequest req, @RequestBody String data) {
//		JSONObject jsonObject2 = new JSONObject();
//
//		if (khongTrongThoiGianXyLy(req)) {
//			jsonObject2.put("status", 505);
//			return jsonObject2.toString();
//		}
//
//		try {
//			Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
//			EkycDoanhNghiep ekycDoanhNghiep = gson.fromJson(data, EkycDoanhNghiep.class);
//
//			
//			  if(!validateData(ekycDoanhNghiep)) { jsonObject2.put("status", 400); return
//			  jsonObject2.toString(); }
//			 
//
//			System.out.println(new Gson().toJson(ekycDoanhNghiep));
//			FileHandling fileHandling = new FileHandling();
//
//			ekycDoanhNghiep.setFileBusinessRegistration(
//					luuAnh(ekycDoanhNghiep.getFileBusinessRegistration(), fileHandling, "abc.pdf"));
//			ekycDoanhNghiep.setFileAppointmentOfChiefAccountant(
//					luuAnh(ekycDoanhNghiep.getFileAppointmentOfChiefAccountant(), fileHandling, "abc.pdf"));
//			ekycDoanhNghiep.setFileBusinessRegistrationCertificate(
//					luuAnh(ekycDoanhNghiep.getFileBusinessRegistrationCertificate(), fileHandling, "abc.pdf"));
//			ekycDoanhNghiep.setFileDecisionToAppointChiefAccountant(
//					luuAnh(ekycDoanhNghiep.getFileDecisionToAppointChiefAccountant(), fileHandling, "abc.pdf"));
//			ekycDoanhNghiep.setFileInvestmentCertificate(
//					luuAnh(ekycDoanhNghiep.getFileInvestmentCertificate(), fileHandling, "abc.pdf"));
//			ekycDoanhNghiep
//					.setFileCompanyCharter(luuAnh(ekycDoanhNghiep.getFileCompanyCharter(), fileHandling, "abc.pdf"));
//			ekycDoanhNghiep.setFileSealSpecimen(luuAnh(ekycDoanhNghiep.getFileSealSpecimen(), fileHandling, "abc.pdf"));
//			ekycDoanhNghiep.setFileFatcaForms(luuAnh(ekycDoanhNghiep.getFileFatcaForms(), fileHandling, "abc.pdf"));
//			ekycDoanhNghiep.setFileOthers(luuAnh(ekycDoanhNghiep.getFileOthers(), fileHandling, "abc.pdf"));
//
//			ekycDoanhNghiep.setStatus(Contains.TRANG_THAI_KY_THAT_BAI);
//			ekycDoanhNghiep.setToken(UUID.randomUUID().toString());
//
//			EkycDoanhNghiepTable doanhNghiepTable = new EkycDoanhNghiepTable();
//			doanhNghiepTable.setMaDoanhNghiep(ekycDoanhNghiep.getTaxCode());
//			doanhNghiepTable.setTenDoanhNghiep(ekycDoanhNghiep.getNameCompany());
//			doanhNghiepTable.setNoiDung(new Gson().toJson(ekycDoanhNghiep));
//			doanhNghiepTable.setNgayTao(new Date());
//			doanhNghiepTable.setToken(ekycDoanhNghiep.getToken());
//			doanhNghiepTable.setStatus(Contains.TRANG_THAI_KY_THAT_BAI);
//			doanhNghiepTable.setTenNguoiQuanLy(ekycDoanhNghiep.getRelationshipManagerName());
//
//			ekycDoanhNghiepRepository.save(doanhNghiepTable);
//
//			guiMailEkyc(ekycDoanhNghiep);
//			jsonObject2.put("token", ekycDoanhNghiep.getToken());
//			jsonObject2.put("status", 200);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		return jsonObject2.toString();
//	}

	@PostMapping(value = "/ekyc-enterprise/luu-thong-tin-step2", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String luuTruStep1(HttpServletRequest req, @RequestBody String data) {
		JSONObject jsonObject2 = new JSONObject();

		/*
		 * if (khongTrongThoiGianXyLy(req)) { jsonObject2.put("status", 505); return
		 * jsonObject2.toString(); }
		 */

		try {
			/*
			 * Object busername = req.getSession().getAttribute("b_username"); if (busername
			 * == null) return "demo/doanhnghiep2/step/steperror"; EkycDoanhNghiepTable
			 * doanhNghiepTable =
			 * ekycDoanhNghiepRepository.findByUsername(busername.toString());
			 * 
			 * if (doanhNghiepTable == null) { return "demo/doanhnghiep2/step/steperror"; }
			 */

			EkycDoanhNghiepTable doanhNghiepTable = new EkycDoanhNghiepTable();
			Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
			EkycDoanhNghiep ekycDoanhNghiep = gson.fromJson(data, EkycDoanhNghiep.class);
			System.out.println("hasbhda: "+ekycDoanhNghiep.getStatus());

			if (!validateDataStep1(ekycDoanhNghiep)) {
				jsonObject2.put("status", 400);
				return jsonObject2.toString();
			}

			System.out.println(new Gson().toJson(ekycDoanhNghiep));
			FileHandling fileHandling = new FileHandling();

			ekycDoanhNghiep.setFileBusinessRegistration(
					luuAnh(ekycDoanhNghiep.getFileBusinessRegistration(), fileHandling, "abc.pdf"));
			ekycDoanhNghiep.setFileAppointmentOfChiefAccountant(
					luuAnh(ekycDoanhNghiep.getFileAppointmentOfChiefAccountant(), fileHandling, "abc.pdf"));
			ekycDoanhNghiep.setFileBusinessRegistrationCertificate(
					luuAnh(ekycDoanhNghiep.getFileBusinessRegistrationCertificate(), fileHandling, "abc.pdf"));
			ekycDoanhNghiep.setFileDecisionToAppointChiefAccountant(
					luuAnh(ekycDoanhNghiep.getFileDecisionToAppointChiefAccountant(), fileHandling, "abc.pdf"));
			ekycDoanhNghiep.setFileInvestmentCertificate(
					luuAnh(ekycDoanhNghiep.getFileInvestmentCertificate(), fileHandling, "abc.pdf"));
			ekycDoanhNghiep
					.setFileCompanyCharter(luuAnh(ekycDoanhNghiep.getFileCompanyCharter(), fileHandling, "abc.pdf"));
			ekycDoanhNghiep.setFileSealSpecimen(luuAnh(ekycDoanhNghiep.getFileSealSpecimen(), fileHandling, "abc.pdf"));
			ekycDoanhNghiep.setFileFatcaForms(luuAnh(ekycDoanhNghiep.getFileFatcaForms(), fileHandling, "abc.pdf"));
			ekycDoanhNghiep.setFileOthers(luuAnh(ekycDoanhNghiep.getFileOthers(), fileHandling, "abc.pdf"));

			ekycDoanhNghiep.setStatus(Contains.TRANG_THAI_KY_THAT_BAI);
			ekycDoanhNghiep.setToken(UUID.randomUUID().toString());

			doanhNghiepTable.setMaDoanhNghiep(ekycDoanhNghiep.getTaxCode());
			doanhNghiepTable.setTenDoanhNghiep(ekycDoanhNghiep.getNameCompany());
			doanhNghiepTable.setNoiDung(new Gson().toJson(ekycDoanhNghiep));
			doanhNghiepTable.setNgayTao(new Date());
			doanhNghiepTable.setToken(ekycDoanhNghiep.getToken());
			doanhNghiepTable.setStatus(Contains.TRANG_THAI_KY_THAT_BAI);
			doanhNghiepTable.setTenNguoiQuanLy(ekycDoanhNghiep.getRelationshipManagerName());
			// doanhNghiepTable.setUsername(busername);
			doanhNghiepTable.setStep("2");
			jsonObject2.put("status", 200);
			ekycDoanhNghiepRepository.save(doanhNghiepTable);

			req.getSession().setAttribute("token", doanhNghiepTable.getToken());

			jsonObject2.put("token", ekycDoanhNghiep.getToken());
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return jsonObject2.toString();
	}

	@PostMapping(value = "/ekyc-enterprise/luu-thong-tin-step3", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String luuTruStep2(HttpServletRequest req, @RequestBody String data) {
		JSONObject jsonObject2 = new JSONObject();

		try {
			Object token = req.getSession().getAttribute("token");
			if (token == null)
				return "demo/doanhnghiep2/step/steperror";
			System.out.println("Token : " + token);
			Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
			EkycDoanhNghiepTable doanhnghiepDb = ekycDoanhNghiepRepository.findByToken(token.toString());
			if (doanhnghiepDb == null) {
				jsonObject2.put("status", 400);
				return jsonObject2.toString();
			}

			EkycDoanhNghiep ekycDoanhNghiep1 = gson.fromJson(doanhnghiepDb.getNoiDung(), EkycDoanhNghiep.class);

			EkycDoanhNghiep ekycDoanhNghiep2 = gson.fromJson(data, EkycDoanhNghiep.class);
			if (!validateDataStep2(ekycDoanhNghiep2)) {
				jsonObject2.put("status", 400);
				return jsonObject2.toString();
			}
			EkycDoanhNghiepTable doanhnghiep = new EkycDoanhNghiepTable();
			doanhnghiep.setNoiDung(new Gson().toJson(ekycDoanhNghiep2));

			doanhnghiepDb.setMaDoanhNghiep(ekycDoanhNghiep2.getTaxCode());
			doanhnghiepDb.setTenDoanhNghiep(ekycDoanhNghiep2.getNameCompany());

			doanhnghiepDb.setNgayTao(new Date());
			doanhnghiepDb.setStatus(Contains.TRANG_THAI_KY_THAT_BAI);
			doanhnghiepDb.setTenNguoiQuanLy(ekycDoanhNghiep2.getRelationshipManagerName());
			doanhnghiepDb.setStep("3");
			updateObjectToObject(ekycDoanhNghiep1, ekycDoanhNghiep2);

			doanhnghiepDb.setNoiDung(new Gson().toJson(ekycDoanhNghiep1));

			ekycDoanhNghiepRepository.save(doanhnghiepDb);

			jsonObject2.put("token", token);
			jsonObject2.put("status", 200);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return jsonObject2.toString();
	}

	@PostMapping(value = "/ekyc-enterprise/luu-thong-tin-step9", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String luuTruStep9(HttpServletRequest req, @RequestBody String data) {
		JSONObject jsonObject2 = new JSONObject();

		try {
			Object token = req.getSession().getAttribute("token");
			if (token == null)
				return "demo/doanhnghiep2/step/steperror";
			Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
			EkycDoanhNghiepTable doanhnghiepDb = ekycDoanhNghiepRepository.findByToken(token.toString());
			if (doanhnghiepDb == null) {
				jsonObject2.put("status", 505);
				return jsonObject2.toString();
			}
			EkycDoanhNghiep ekycDoanhNghiepDb = gson.fromJson(doanhnghiepDb.getNoiDung(), EkycDoanhNghiep.class);

			EkycDoanhNghiep ekycDoanhNghiep = gson.fromJson(data, EkycDoanhNghiep.class);

			updateObjectToObject(ekycDoanhNghiepDb, ekycDoanhNghiep);

			doanhnghiepDb.setNoiDung(new Gson().toJson(ekycDoanhNghiepDb));
			doanhnghiepDb.setStep("9");
			ekycDoanhNghiepRepository.save(doanhnghiepDb);

			jsonObject2.put("status", 200);
		} catch (Exception e) {
			jsonObject2.put("status", 505);
		}

		return jsonObject2.toString();
	}
	@PostMapping(value = "/ekyc-enterprise/luu-thong-tin-step-4", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String luuTruStep(HttpServletRequest req, @RequestBody String data) {
		JSONObject jsonObject2 = new JSONObject();

		try {
			System.out.println("ahii đồ ngốc: "+req.getSession().getAttribute("token"));
			Object token = req.getSession().getAttribute("token");
			if (token == null)
				return "demo/doanhnghiep2/step/steperror";

			Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
			EkycDoanhNghiepTable doanhnghiepDb = ekycDoanhNghiepRepository.findByToken(token.toString());
			if (doanhnghiepDb == null) {
				jsonObject2.put("status", 505);
				return jsonObject2.toString();
			}
			EkycDoanhNghiep ekycDoanhNghiep1 = gson.fromJson(doanhnghiepDb.getNoiDung(), EkycDoanhNghiep.class);

			EkycDoanhNghiep ekycDoanhNghiep2 = gson.fromJson(data, EkycDoanhNghiep.class);
			EkycDoanhNghiepTable doanhnghiep = new EkycDoanhNghiepTable();
			doanhnghiep.setNoiDung(new Gson().toJson(ekycDoanhNghiep2));

			doanhnghiepDb.setMaDoanhNghiep(ekycDoanhNghiep1.getTaxCode());
			doanhnghiepDb.setTenDoanhNghiep(ekycDoanhNghiep1.getNameCompany());

			doanhnghiepDb.setNgayTao(new Date());
			doanhnghiepDb.setStatus(Contains.TRANG_THAI_KY_THAT_BAI);
			doanhnghiepDb.setTenNguoiQuanLy(ekycDoanhNghiep1.getRelationshipManagerName());
			doanhnghiepDb.setStep("4");
			updateObjectToObject(ekycDoanhNghiep1, ekycDoanhNghiep2);

			doanhnghiepDb.setNoiDung(new Gson().toJson(ekycDoanhNghiep1));
			ekycDoanhNghiepRepository.save(doanhnghiepDb);

			jsonObject2.put("token", token);
			jsonObject2.put("status", 200);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return jsonObject2.toString();
	}

	@PostMapping(value = "/ekyc-enterprise/luu-thong-tin-step{step}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String luuTruStep3(HttpServletRequest req, @RequestBody String data, @PathVariable("step") String step) {
		JSONObject jsonObject2 = new JSONObject();

		try {
			
			Object token = req.getSession().getAttribute("token");
			if (token == null)
				return "demo/doanhnghiep2/step/steperror";

			Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
			EkycDoanhNghiepTable doanhnghiepDb = ekycDoanhNghiepRepository.findByToken(token.toString());
			if (doanhnghiepDb == null) {
				jsonObject2.put("status", 505);
				return jsonObject2.toString();
			}
			EkycDoanhNghiep ekycDoanhNghiep1 = gson.fromJson(doanhnghiepDb.getNoiDung(), EkycDoanhNghiep.class);

			EkycDoanhNghiep ekycDoanhNghiep2 = gson.fromJson(data, EkycDoanhNghiep.class);
			EkycDoanhNghiepTable doanhnghiep = new EkycDoanhNghiepTable();
			doanhnghiep.setNoiDung(new Gson().toJson(ekycDoanhNghiep2));

			doanhnghiepDb.setMaDoanhNghiep(ekycDoanhNghiep1.getTaxCode());
			doanhnghiepDb.setTenDoanhNghiep(ekycDoanhNghiep1.getNameCompany());

			doanhnghiepDb.setNgayTao(new Date());
			doanhnghiepDb.setStatus(Contains.TRANG_THAI_KY_THAT_BAI);
			doanhnghiepDb.setTenNguoiQuanLy(ekycDoanhNghiep1.getRelationshipManagerName());
			doanhnghiepDb.setStep(step);
			updateObjectToObject(ekycDoanhNghiep1, ekycDoanhNghiep2);

			doanhnghiepDb.setNoiDung(new Gson().toJson(ekycDoanhNghiep1));
			ekycDoanhNghiepRepository.save(doanhnghiepDb);

			jsonObject2.put("token", token);
			jsonObject2.put("status", 200);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return jsonObject2.toString();
	}

//	private boolean validateData(EkycDoanhNghiep ekycDoanhNghiep) {
//		if (StringUtils.isEmpty(ekycDoanhNghiep.getFileBusinessRegistration()))
//			return false;
//		if (StringUtils.isEmpty(ekycDoanhNghiep.getFileAppointmentOfChiefAccountant()))
//			return false;
//		if (StringUtils.isEmpty(ekycDoanhNghiep.getFileCompanyCharter()))
//			return false;
//		if (StringUtils.isEmpty(ekycDoanhNghiep.getFileFatcaForms()))
//			return false;
//		if (StringUtils.isEmpty(ekycDoanhNghiep.getNameOfTheAccountHolder()))
//			return false;
//		if (StringUtils.isEmpty(ekycDoanhNghiep.getNameCompany()))
//			return false;
//		if (StringUtils.isEmpty(ekycDoanhNghiep.getRegisteredAddress()))
//			return false;
//		if (StringUtils.isEmpty(ekycDoanhNghiep.getRegistrationNumber()))
//			return false;
//		if (StringUtils.isEmpty(ekycDoanhNghiep.getMailingAddress()))
//			return false;
//		if (StringUtils.isEmpty(ekycDoanhNghiep.getContactPerson()))
//			return false;
//		if (StringUtils.isEmpty(ekycDoanhNghiep.getEmailAddress()))
//			return false;
//		if (StringUtils.isEmpty(ekycDoanhNghiep.getResidentStatus()))
//			return false;
//		if (StringUtils.isEmpty(ekycDoanhNghiep.getBusinessActivities()))
//			return false;
//		if (StringUtils.isEmpty(ekycDoanhNghiep.getTotalSalesTurnover()))
//			return false;
//		if (StringUtils.isEmpty(ekycDoanhNghiep.getTotalCapital()))
//			return false;
////		if(StringUtils.isEmpty(ekycDoanhNghiep.getAccountType())) return false;
////		if(StringUtils.isEmpty(ekycDoanhNghiep.getCurrency())) return false;
////		if(StringUtils.isEmpty(ekycDoanhNghiep.getCurrency())) return false;
//
//		if (StringUtils.isEmpty(ekycDoanhNghiep.getListAccount()))
//			return false;
//		if (ekycDoanhNghiep.getListAccount().size() == 0)
//			return false;
//
//		if (StringUtils.isEmpty(ekycDoanhNghiep.getLegalRepresentator()))
//			return false;
//		if (ekycDoanhNghiep.getLegalRepresentator().size() == 0)
//			return false;
//		if (StringUtils.isEmpty(ekycDoanhNghiep.getListOfLeaders()))
//			return false;
//		if (ekycDoanhNghiep.getListOfLeaders().size() == 0)
//			return false;
//		return true;
//	}

	private boolean validateDataStep1(EkycDoanhNghiep ekycDoanhNghiep) {
		if (StringUtils.isEmpty(ekycDoanhNghiep.getFileBusinessRegistration()))
			return false;
		if (StringUtils.isEmpty(ekycDoanhNghiep.getFileAppointmentOfChiefAccountant()))
			return false;
		if (StringUtils.isEmpty(ekycDoanhNghiep.getFileCompanyCharter()))
			return false;
		if (StringUtils.isEmpty(ekycDoanhNghiep.getFileFatcaForms()))
			return false;
		return true;
	}

	private boolean validateDataStep2(EkycDoanhNghiep ekycDoanhNghiep) {
		if (StringUtils.isEmpty(ekycDoanhNghiep.getNameOfTheAccountHolder()))
			return false;
		if (StringUtils.isEmpty(ekycDoanhNghiep.getNameCompany()))
			return false;
		if (StringUtils.isEmpty(ekycDoanhNghiep.getRegisteredAddress()))
			return false;
		if (StringUtils.isEmpty(ekycDoanhNghiep.getRegistrationNumber()))
			return false;
		if (StringUtils.isEmpty(ekycDoanhNghiep.getMailingAddress()))
			return false;
		if (StringUtils.isEmpty(ekycDoanhNghiep.getContactPerson()))
			return false;
		if (StringUtils.isEmpty(ekycDoanhNghiep.getEmailAddress()))
			return false;
		if (StringUtils.isEmpty(ekycDoanhNghiep.getResidentStatus()))
			return false;
		if (StringUtils.isEmpty(ekycDoanhNghiep.getBusinessActivities()))
			return false;
		if (StringUtils.isEmpty(ekycDoanhNghiep.getTotalSalesTurnover()))
			return false;
		if (StringUtils.isEmpty(ekycDoanhNghiep.getTotalCapital()))
			return false;

		if (StringUtils.isEmpty(ekycDoanhNghiep.getListAccount()))
			return false;
		if (ekycDoanhNghiep.getListAccount().size() == 0)
			return false;
		return true;
	}

	private boolean validateDataStep3(EkycDoanhNghiep ekycDoanhNghiep, String step) {
		if (step == "4") {
			if (StringUtils.isEmpty(ekycDoanhNghiep.getLegalRepresentator()))
				return false;
		}

		if (ekycDoanhNghiep.getLegalRepresentator().size() == 0)
			return false;
		if (StringUtils.isEmpty(ekycDoanhNghiep.getListOfLeaders()))
			return false;
		if (ekycDoanhNghiep.getListOfLeaders().size() == 0)
			return false;
		return true;
	}

	private boolean khongTrongThoiGianXyLy(HttpServletRequest req) {

		ParamsKbank params = getParams(req);
		if (params == null)
			return true;
//    	long timeOut = configProperties.getConfig().getTimeout_link_front() != null?Long.valueOf(configProperties.getConfig().getTimeout_link_front()):24;
//    	long thoiGianhetHan = timeOut*60*60*1000L + params.getTimeStartStep();
//    	if(thoiGianhetHan < System.currentTimeMillis()) return true;

		return false;

	}

	private void guiMailEkyc(EkycDoanhNghiep ekycDoanhNghiep) {
		int check = 0;
		try {
			
			if(ekycDoanhNghiep.getAllInOne().equals("yes")) {
				for (InfoPerson ip : ekycDoanhNghiep.getLegalRepresentator()) {
					if (guiMailEkyc(ip, ekycDoanhNghiep.getLegalRepresentator().size(),
							Contains.ALL_IN_ONE)  ) {
						guiMailYeuCauXacThuc(ekycDoanhNghiep, ip, Contains.NGUOI_DAI_DIEN_PHAP_LUAT);
					}
				}
				
			}else if(ekycDoanhNghiep.getAllInOne().equals("no")){
				
				for (InfoPerson ip : ekycDoanhNghiep.getLegalRepresentator()) {
					if (guiMailEkyc(ip, ekycDoanhNghiep.getLegalRepresentator().size(),
							Contains.NGUOI_DAI_DIEN_PHAP_LUAT)  ) {
						System.out.println("1111111");
						guiMailYeuCauXacThuc(ekycDoanhNghiep, ip, Contains.NGUOI_DAI_DIEN_PHAP_LUAT);
					}
				}

				for (InfoPerson ip : ekycDoanhNghiep.getChiefAccountant()) {
					if (guiMailEkyc(ip, ekycDoanhNghiep.getChiefAccountant().size(), Contains.KE_TOAN_TRUONG)) {
						System.out.println("2222222");
						
						guiMailYeuCauXacThuc(ekycDoanhNghiep, ip, Contains.KE_TOAN_TRUONG);
					}
				}
				for (InfoPerson ip : ekycDoanhNghiep.getListOfLeaders()) {
					if (guiMailEkyc(ip, ekycDoanhNghiep.getListOfLeaders().size(), Contains.BAN_LANH_DAO)) {
						System.out.println("3333333");
						
						guiMailYeuCauXacThuc(ekycDoanhNghiep, ip, Contains.BAN_LANH_DAO);
					}
				}
				if(ekycDoanhNghiep.getPersonAuthorizedChiefAccountant()  != null) {	
					for (InfoPerson ip : ekycDoanhNghiep.getPersonAuthorizedChiefAccountant()) {
						if (guiMailEkyc(ip, ekycDoanhNghiep.getPersonAuthorizedChiefAccountant().size(),
								Contains.UY_QUYEN_KE_TOAN_TRUONG)) {
							
							System.out.println("444444444");
							guiMailYeuCauXacThuc(ekycDoanhNghiep, ip, Contains.UY_QUYEN_KE_TOAN_TRUONG);
						}
					}
				}
				
				if(ekycDoanhNghiep.getPersonAuthorizedAccountHolder() != null) {
					for (InfoPerson ip : ekycDoanhNghiep.getPersonAuthorizedAccountHolder()) {
						if (guiMailEkyc(ip, ekycDoanhNghiep.getPersonAuthorizedAccountHolder().size(),
								Contains.NGUOI_DUOC_UY_QUYEN)) {
						
							System.out.println("5555555555");
							guiMailYeuCauXacThuc(ekycDoanhNghiep, ip, Contains.NGUOI_DUOC_UY_QUYEN);
						}
					}
				}
				
//				if(check == 3) {
//					for (InfoPerson ip : ekycDoanhNghiep.getLegalRepresentator()) {
//						if (guiMailEkyc(ip, ekycDoanhNghiep.getLegalRepresentator().size(),
//								Contains.KT_BLD_UQ) ) {
//							System.out.println("666666");
//							guiMailYeuCauXacThuc(ekycDoanhNghiep, ip, Contains.NGUOI_DAI_DIEN_PHAP_LUAT);
//						}
//					}
//				}
			}
			
			
		

		} catch (Exception e) {
		}
	}

	private void guiMailYeuCauXacThuc(EkycDoanhNghiep ekycDoanhNghiep, InfoPerson ip, String type) {
		LOGGER.info("Send mail: " + LINK_ADMIN + "/fisbank/ekyc-enterprise/ekyc?token=" + ekycDoanhNghiep.getToken()
				+ "&tokenCheck=" + ip.getTokenCheck() + "&type=" + Base64.getEncoder().encodeToString(type.getBytes()));
		if (!MOI_TRUONG.equals("dev"))
			email.sendText(ip.getEmail(), "Email yêu cầu xác thực ekyc đăng ký mở tài khoản",
					"Vui lòng click vào <a href='" + LINK_ADMIN + "/fisbank/ekyc-enterprise/ekyc?token="
							+ ekycDoanhNghiep.getToken() + "&tokenCheck=" + ip.getTokenCheck() + "&type="
							+ Base64.getEncoder().encodeToString(type.getBytes())
							+ "'>Bắt đầu ekyc</a>, để thực hiện eKYC. <br/>"
							+ "Khi cần bổ sung chứng từ, quý khách có thể nhấn vào <a href='" + LINK_ADMIN
							+ "/fisbank/ekyc-enterprise/upload?token=" + ekycDoanhNghiep.getToken() + "&tokenCheck="
							+ ip.getTokenCheck() + "&type=" + Base64.getEncoder().encodeToString(type.getBytes())
							+ "'>đây</a>");

	}

	private boolean guiMailEkyc(InfoPerson ip, int size, String loai) {
		if (loai.equals(Contains.NGUOI_DAI_DIEN_PHAP_LUAT) && ip.getCheckMain().equals("Y"))
			return false;
	
		return true;
	}

	@PostMapping(value = "/ekyc-enterprise/liveness", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String liveness(HttpServletRequest req, @RequestBody String data) {
		JSONObject jsonObject = new JSONObject(data);

		if (jsonObject.has("anhVideo")) {
			String listImage = jsonObject.getString("anhVideo");
			String[] arr = listImage.split(",");
			JSONArray jsonArray = new JSONArray();
			String anhCaNhan = "";
			for (int i = 0; i < arr.length; i++) {
				jsonArray.put(i, new JSONObject().put("anh", arr[i]).put("thoiGian", (i + 1)));
				if (StringUtils.isEmpty(anhCaNhan)) {
					anhCaNhan = arr[i];
				}
			}
			JSONObject jsonObject2 = new JSONObject();
			jsonObject2.put("anhMatTruoc", jsonObject.getString("anhMatTruoc"));
			jsonObject2.put("anhVideo", jsonArray);

			String respone = postRequest(jsonObject2.toString(), "/public/all/xac-thuc-khuon-mat");
			JSONObject object = new JSONObject(respone);

			return object.toString();
		}

		return "{'status': 400, 'message':'Không có ảnh video'}";
	}

	private String luuAnh(String base64Img, FileHandling fileHandling, String nameFile) {
		if (!StringUtils.isEmpty(base64Img)) {
			try {
				String path = fileHandling.save(base64Img, configProperties.getConfig().getImage_folder_log() + "web/",
						nameFile);
				return path;
			} catch (Exception e) {
			}
		}
		return "";
	}

	private String sendRequest(MultipartFile file, String codeTemplate, String url) {
		try {
			RequestConfig.Builder requestConfig = RequestConfig.custom();
			CloseableHttpClient httpClient = HttpClients.createDefault();

			HttpPost uploadFile = new HttpPost(API_SERVICE + url);
			uploadFile.setConfig(requestConfig.build());
			uploadFile.addHeader("token", token);
			uploadFile.addHeader("code", code);
//			uploadFile.addHeader("content-type", file.getContentType());

			MultipartEntityBuilder builder = MultipartEntityBuilder.create();
			builder.addTextBody("code", codeTemplate, ContentType.TEXT_PLAIN.withCharset(Charset.forName("utf-8")));
			builder.addBinaryBody("file", file.getBytes(), ContentType.MULTIPART_FORM_DATA, file.getOriginalFilename());

			HttpEntity multipart = builder.build();
			uploadFile.setEntity(multipart);
			CloseableHttpResponse response = httpClient.execute(uploadFile);
			HttpEntity responseEntity = response.getEntity();
			String text = IOUtils.toString(responseEntity.getContent(), StandardCharsets.UTF_8.name());
			System.out.println(text);
			return text;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void setParams(ParamsKbank params, HttpServletRequest req) {
		req.getSession().setAttribute("params", params);
	}

	public ParamsKbank getParams(HttpServletRequest req) {
		return (ParamsKbank) req.getSession().getAttribute("params");
	}

	@PostMapping(value = "/ekyc-enterprise/ky-so", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String kySoEkyc(@RequestParam Map<String, String> allParams, HttpServletRequest req, Model model,
			RedirectAttributes redirectAttributes) throws Exception {
		JSONObject jsonResp = new JSONObject();
		try {
			String text = IOUtils.toString(req.getInputStream(), StandardCharsets.UTF_8.name());

			JSONObject jsonObjectPr = new JSONObject(text);
			forwartParams(allParams, model);
			String HTML = jsonObjectPr.getString("contentPdf");
			String nameFile = UUID.randomUUID().toString() + ".pdf";
			String pathPdf = KY_SO_FOLDER + "/" + nameFile;
			String agreementUUID = UUID.randomUUID().toString();

			ParamsKbank params = new ParamsKbank();
			FormInfo formInfo = new FormInfo();
			formInfo.setHoVaTen(jsonObjectPr.getString("hoVaTen"));
			formInfo.setSoCmt(jsonObjectPr.getString("soCmt"));
			formInfo.setDiaChi("Hà Nội");
			formInfo.setThanhPho("Hà Nội");
			formInfo.setQuocGia("Việt Nam");
			params.setSoDienThoai(jsonObjectPr.getString("soDienThoai"));
			params.setFormInfo(formInfo);
			params.setAnhMatTruoc(jsonObjectPr.getString("anhMatTruoc"));
			params.setAnhMatSau(jsonObjectPr.getString("anhMatSau"));

			if (StringUtils.isEmpty(jsonObjectPr.getString("file"))) {
				ParamsKbank params2 = getParams(req);
				if (params2 == null)
					return "redirect:" + "/ekyc-enterprise/ekyc";

				EkycDoanhNghiepTable doanhNghiep = ekycDoanhNghiepRepository.findByToken(params2.getToken());
				if (doanhNghiep == null)
					return "demo/doanhnghiep2/step/steperror";
				EkycDoanhNghiep ekycDoanhNghiep = new Gson().fromJson(doanhNghiep.getNoiDung(), EkycDoanhNghiep.class);

				pdfHandling.nhapThongTinForm(pathPdf, ekycDoanhNghiep, PATH_PDF_FILL_FORM);
			} else {
				System.out.println(KY_SO_FOLDER);
				byte[] decodedImg = Base64.getDecoder()
						.decode(jsonObjectPr.getString("file").getBytes(StandardCharsets.UTF_8));

				Path destinationFile = Paths.get(KY_SO_FOLDER, nameFile);
				Files.write(destinationFile, decodedImg);
			}

			System.out.println("PDF Created!");

			String jsonRegister = guiThongTinDangKyKySo(params, agreementUUID);
			ObjectMapper objectMapper = new ObjectMapper();
			SignCloudResp signCloudRespRegister = objectMapper.readValue(jsonRegister, SignCloudResp.class);

			if (signCloudRespRegister.getResponseCode() != 0) {
				jsonResp.put("message", "Không đăng ký được chữ ký ");
				jsonResp.put("status", 400);

				return jsonResp.toString();
			}
			String page = "23";
			String textPage = "Đại diện cho Công ty";

			if (jsonObjectPr.has("page")) {
				page = jsonObjectPr.getString("page");
			}
			if (jsonObjectPr.has("textPage")) {
				textPage = jsonObjectPr.getString("textPage");
			}

			String jsonResponse = guiThongTinKySo(req, pathPdf, nameFile, agreementUUID, page, textPage);

			SignCloudResp signCloudResp = objectMapper.readValue(jsonResponse, SignCloudResp.class);

			if (signCloudResp.getResponseCode() != 1007) {
				jsonResp.put("message", "Không gửi được chữ ký ");
				jsonResp.put("status", 400);

				return jsonResp.toString();
			}

			if (!MOI_TRUONG.equals("dev")) {
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("dienThoai", params.getSoDienThoai());
				postRequest(jsonObject.toString(),
						"/public/gui-ma-otp-ky-so?code=" + signCloudResp.getAuthorizeCredential());
			}

			jsonResp.put("otp", signCloudResp.getAuthorizeCredential());
			jsonResp.put("maKy", signCloudResp.getBillCode());

//			postRequest(jsonObject.toString(), "/public/gui-ma-otp-ky-so?code=1234");
//			
//			jsonResp.put("otp", "1234");
//			jsonResp.put("maKy", "123456789");
			jsonResp.put("pathPdf", pathPdf);
			jsonResp.put("nameFile", nameFile);
			jsonResp.put("agreementUUID", agreementUUID);
		} catch (Exception e) {
			e.printStackTrace();
			jsonResp.put("message", "Lỗi hệ thống");
			jsonResp.put("status", 400);

			return jsonResp.toString();
		}

		jsonResp.put("status", 200);

		return jsonResp.toString();
	}

	@PostMapping(value = "/ekyc-enterprise/ky-so-otp", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String kySoOtp(@RequestParam Map<String, String> allParams, HttpServletRequest req, Model model,
			RedirectAttributes redirectAttributes) throws Exception {
		JSONObject jsonResp = new JSONObject();
		try {
			String text = IOUtils.toString(req.getInputStream(), StandardCharsets.UTF_8.name());
			JSONObject jsonObject = new JSONObject(text);

			eSignCall service = new eSignCall();
			String jsonResponse = service.authorizeSingletonSigningForSignCloud(jsonObject.getString("agreementUUID"),
					jsonObject.getString("otp"), jsonObject.getString("maKy"));
			ObjectMapper objectMapper = new ObjectMapper();
			SignCloudResp signCloudResp = objectMapper.readValue(jsonResponse, SignCloudResp.class);
			if (signCloudResp.getResponseCode() == 0 && signCloudResp.getSignedFileData() != null) {
				String str = jsonObject.getString("pathPdf");
				String str1 = str.split("\\/")[str.split("\\/").length - 1];
				File file2 = new File(str.replaceAll(str1, ".signed." + str1));
				String base64Img2 = CommonUtils.encodeFileToBase64Binary(file2);

				jsonResp.put("file", base64Img2);
				jsonResp.put("status", 200);

				return jsonResp.toString();
			} else if (signCloudResp.getResponseCode() == 1004) {
				jsonResp.put("message", "Lỗi OTP");
				jsonResp.put("status", 400);

				return jsonResp.toString();
			} else {
				jsonResp.put("message", "Ký số thất bại");
				jsonResp.put("status", 400);

				return jsonResp.toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
			jsonResp.put("message", "Lỗi hệ thống");
			jsonResp.put("status", 400);

			return jsonResp.toString();
		}
	}

	private String guiThongTinDangKyKySo(ParamsKbank params, String agreementUUID) throws Exception {
		FormInfo formInfo = params.getFormInfo();
		System.out.println(new Gson().toJson(formInfo));
		eSignCall service = new eSignCall();
		byte[] frontSideOfIDDocument = Base64.getDecoder().decode(params.getAnhMatTruoc());
		byte[] backSideOfIDDocument = Base64.getDecoder().decode(params.getAnhMatSau());
		String json = service.prepareCertificateForSignCloud(agreementUUID, formInfo.getHoVaTen(), formInfo.getSoCmt(),
				formInfo.getSoCmt(), formInfo.getDiaChi(), formInfo.getThanhPho(), formInfo.getQuocGia(),
				frontSideOfIDDocument, backSideOfIDDocument, formInfo.getEmail(), params.getSoDienThoai());
		return json;
	}

	private String guiThongTinKySo(HttpServletRequest req, String pathPdf, String nameFile, String agreementUUID,
			String page, String textSign) throws Exception {
		byte[] fileData01;
		String mimeType01;
		SignCloudMetaData signCloudMetaDataForItem01;
		HashMap<String, String> singletonSigningForItem01;

		fileData01 = IOUtils.toByteArray(new FileInputStream(pathPdf));
		mimeType01 = ESignCloudConstant.MIMETYPE_PDF;

		signCloudMetaDataForItem01 = new SignCloudMetaData();
		// -- SingletonSigning (Signature properties for customer)
		singletonSigningForItem01 = new HashMap<>();
		singletonSigningForItem01.put("COUNTERSIGNENABLED", "False");
		singletonSigningForItem01.put("PAGENO", page);
		singletonSigningForItem01.put("POSITIONIDENTIFIER", textSign);
		singletonSigningForItem01.put("RECTANGLEOFFSET", "0,-60");
		singletonSigningForItem01.put("RECTANGLESIZE", "200,50");
		singletonSigningForItem01.put("VISIBLESIGNATURE", "True");
		singletonSigningForItem01.put("VISUALSTATUS", "False");
		singletonSigningForItem01.put("IMAGEANDTEXT", "False");
		singletonSigningForItem01.put("TEXTDIRECTION", "LEFTTORIGHT");
		singletonSigningForItem01.put("SHOWSIGNERINFO", "True");
		singletonSigningForItem01.put("SIGNERINFOPREFIX", "Được ký bởi:");
		singletonSigningForItem01.put("SHOWDATETIME", "True");
		singletonSigningForItem01.put("DATETIMEPREFIX", "Ngày ký:");
		singletonSigningForItem01.put("SHOWREASON", "True");
		singletonSigningForItem01.put("SIGNREASONPREFIX", "Lý do:");
		singletonSigningForItem01.put("SIGNREASON", "Tôi đồng ý");
		singletonSigningForItem01.put("SHOWLOCATION", "True");
//        singletonSigningForItem01.put("LOCATION", "Hà Nội");
//        singletonSigningForItem01.put("LOCATIONPREFIX", "Nơi ký:");
		singletonSigningForItem01.put("TEXTCOLOR", "black");
		eSignCall service = new eSignCall();
		signCloudMetaDataForItem01.setSingletonSigning(singletonSigningForItem01);

		String jsonResponse = service.prepareFileForSignCloud(agreementUUID,
				// ESignCloudConstant.AUTHORISATION_METHOD_SMS,
				ESignCloudConstant.AUTHORISATION_METHOD_EMAIL, null, notificationTemplate, notificationSubject,
				fileData01, nameFile, mimeType01, signCloudMetaDataForItem01);

		return jsonResponse;

	}

	@RequestMapping(path = "/ekyc-enterprise/download", method = RequestMethod.GET)
	public ResponseEntity<Resource> download(String param) throws IOException {
		File file = new File("/image/scanIdCard-0.0.1-SNAPSHOT.jar");
		InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
		HttpHeaders headers = new HttpHeaders();
		headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
		headers.add("Pragma", "no-cache");
		headers.add("Expires", "0");
		headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=craw.jar");
		return ResponseEntity.ok().headers(headers).contentLength(file.length())
				.contentType(MediaType.APPLICATION_OCTET_STREAM).body(resource);
	}
}
