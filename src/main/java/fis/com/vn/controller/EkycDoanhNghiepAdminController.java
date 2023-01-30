package fis.com.vn.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TimeZone;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.gson.Gson;

import fis.com.vn.common.CommonUtils;
import fis.com.vn.common.MediaTypeUtils;
import fis.com.vn.common.Paginate;
import fis.com.vn.common.StringUtils;
import fis.com.vn.component.ConfigProperties;
import fis.com.vn.component.Language;
import fis.com.vn.contains.Contains;
import fis.com.vn.entities.EkycDoanhNghiep;
import fis.com.vn.entities.InfoPerson;
import fis.com.vn.entities.KtraDoanhNghiep;
import fis.com.vn.repository.EkycDoanhNghiepRepository;
import fis.com.vn.table.EkycDoanhNghiepTable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
@Controller
public class EkycDoanhNghiepAdminController extends BaseController {
	@Autowired
	EkycDoanhNghiepRepository ekycDoanhNghiepRepository;
	@Autowired
	Language language;
	@Autowired
	ServletContext servletContext;
	@Autowired
	ConfigProperties configProperties;

	@GetMapping(value = { "/ekyc-doanh-nghiep" })
	public String danhSachXemGiaoDichDemo(Model model, @RequestParam Map<String, String> allParams,
			HttpServletRequest req) throws ParseException {
		Paginate paginate = new Paginate(allParams.get("page"), allParams.get("limit") );

		model.addAttribute("download", isAllowUrl(req, "/download-ekyc-doanh-nghiep"));
		model.addAttribute("xem", isAllowUrl(req, "/xem-ekyc-doanh-nghiep"));
		model.addAttribute("xoa", isAllowUrl(req, "/xoa-ekyc-doanh-nghiep"));
       SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		
		String fromDate = allParams.get("fromDate");
		String toDate = allParams.get("toDate");
		
		if(StringUtils.isEmpty(fromDate)) {
			toDate = dateFormat.format(new Date());
			fromDate = dateFormat.format(new Date());
		}
			
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dateFormat.parse(toDate));
		//calendar.setTime(dateFormat.parse(fromDate));
		calendar.add(Calendar.DATE, 1);
		toDate = dateFormat.format(calendar.getTime());
		
		SimpleDateFormat dateFormatSql = new SimpleDateFormat("dd/MM/yyyy");
		dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
	  
	
	  
	  String colum = req.getParameter("sort");
	  if(colum == null) {
		  colum = "id";
	  }
	
	  System.out.println("aaaaaaa: "+colum);
	
		Page<EkycDoanhNghiepTable> ekycDoanhNghieps = ekycDoanhNghiepRepository
				.selectParams(
						getStringParams(allParams, "ma_doanh_nghiep"),
						dateFormatSql.parse(fromDate),
						dateFormatSql.parse(toDate),
		                getStringParams(allParams, "tenNguoiQuanLy"),
		                getStringParams(allParams, "emailNguoiLienHe"),
		                getStringParams(allParams, "soDienThoaiNguoiLienHe"),
		             
		                getStringParams(allParams, "tenNguoiLienHe"),
		                getStringParams(allParams, "ekyc_status"),
		              
		                getPageableSort(allParams,paginate, colum));

		model.addAttribute("currentPage", paginate.getPage());
		model.addAttribute("totalPage", ekycDoanhNghieps.getTotalPages());
		model.addAttribute("totalElement", ekycDoanhNghieps.getTotalElements());
		model.addAttribute("ekycDoanhNghieps", ekycDoanhNghieps.getContent());
		forwartParams(allParams, model);
		return "quantrikhachhang/doanhnghiep/danhsach";
	}
	  
	 

	@GetMapping(value = { "/download-ekyc-doanh-nghiep/start" })
	public ResponseEntity<InputStreamResource> downloadStart(Model model, @RequestParam Map<String, String> allParams,
			RedirectAttributes redirectAttributes) throws Exception {
		if (StringUtils.isEmpty(allParams.get("id"))) {
			throw new Exception(language.getMessage("khong_ton_tai_ban_ghi"));
		}
		Optional<EkycDoanhNghiepTable> userInfo = ekycDoanhNghiepRepository.findById(Long.valueOf(allParams.get("id")));
		if (!userInfo.isPresent()) {
			throw new Exception(language.getMessage("khong_ton_tai_ban_ghi"));
		}

		EkycDoanhNghiepTable ekycDoanhNghiepTable = userInfo.get();

		if (!ekycDoanhNghiepTable.getStatus().equals(Contains.TRANG_THAI_KY_THANH_CONG))
			throw new Exception(language.getMessage("khong_the_download"));

		EkycDoanhNghiep ekycDoanhNghiep = new Gson().fromJson(ekycDoanhNghiepTable.getNoiDung(), EkycDoanhNghiep.class);

		String zipFile = configProperties.getConfig().getImage_folder_log() + "web/download.zip";
		String[] srcFiles = {};
		srcFiles = append(srcFiles, ekycDoanhNghiep.getFileDangKy());
		srcFiles = append(srcFiles, ekycDoanhNghiep.getFileKy());
		srcFiles = append(srcFiles, ekycDoanhNghiep.getFileBusinessRegistration());
		srcFiles = append(srcFiles, ekycDoanhNghiep.getFileAppointmentOfChiefAccountant());
		srcFiles = append(srcFiles, ekycDoanhNghiep.getFileInvestmentCertificate());
		srcFiles = append(srcFiles, ekycDoanhNghiep.getFileCompanyCharter());
		srcFiles = append(srcFiles, ekycDoanhNghiep.getFileSealSpecimen());
		srcFiles = append(srcFiles, ekycDoanhNghiep.getFileFatcaForms());
		srcFiles = append(srcFiles, ekycDoanhNghiep.getFileOthers());
		try {
			byte[] buffer = new byte[1024];
			FileOutputStream fos = new FileOutputStream(zipFile);
			ZipOutputStream zos = new ZipOutputStream(fos);
			for (int i = 0; i < srcFiles.length; i++) {
				File srcFile = new File(srcFiles[i]);
				FileInputStream fis = new FileInputStream(srcFile);
				zos.putNextEntry(new ZipEntry(srcFile.getName()));
				int length;
				while ((length = fis.read(buffer)) > 0) {
					zos.write(buffer, 0, length);
				}
				zos.closeEntry();
				fis.close();
			}
			zos.close();
		} catch (IOException ioe) {
			System.out.println("Error creating zip file: " + ioe);
		}

		MediaType mediaType = MediaTypeUtils.getMediaTypeForFileName(this.servletContext, "download.zip");
		File fileZip = new File(zipFile);
		InputStreamResource resource = new InputStreamResource(new FileInputStream(fileZip));

		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + fileZip.getName())
				.contentType(mediaType).contentLength(fileZip.length()).body(resource);
	}

	@GetMapping(value = { "/download-ekyc-doanh-nghiep" })
	public String download(Model model, @RequestParam Map<String, String> allParams,
			RedirectAttributes redirectAttributes) throws Exception {
		try {
			if (StringUtils.isEmpty(allParams.get("id"))) {
				throw new Exception(language.getMessage("khong_ton_tai_ban_ghi"));
			}
			Optional<EkycDoanhNghiepTable> userInfo = ekycDoanhNghiepRepository
					.findById(Long.valueOf(allParams.get("id")));
			if (!userInfo.isPresent()) {
				throw new Exception(language.getMessage("khong_ton_tai_ban_ghi"));
			}

			EkycDoanhNghiepTable ekycDoanhNghiepTable = userInfo.get();

			if (!ekycDoanhNghiepTable.getStatus().equals(Contains.TRANG_THAI_KY_THANH_CONG))
				throw new Exception(language.getMessage("khong_the_download"));

			return "redirect:/download-ekyc-doanh-nghiep/start?id=" + allParams.get("id");
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("error", e.getMessage());
			return "redirect:/ekyc-doanh-nghiep";
		}
	}

	private <T> T[] append(T[] arr, T element) {
		if (!StringUtils.isEmpty(element)) {
			final int N = arr.length;
			arr = Arrays.copyOf(arr, N + 1);
			arr[N] = element;
		}
		return arr;
	}

	@GetMapping(value = "/xem-ekyc-doanh-nghiep")
	public String getnguoiDungXem(Model model, HttpServletRequest req, @RequestParam Map<String, String> allParams,
			RedirectAttributes redirectAttributes) {

		try {
			if (StringUtils.isEmpty(allParams.get("id"))) {
				throw new Exception(language.getMessage("xem_that_bai"));
			}
			Optional<EkycDoanhNghiepTable> userInfo = ekycDoanhNghiepRepository
					.findById(Long.valueOf(allParams.get("id")));
			if (!userInfo.isPresent()) {
				throw new Exception(language.getMessage("khong_ton_tai_ban_ghi"));
			}

			EkycDoanhNghiepTable ekycDoanhNghiepTable = userInfo.get();

			EkycDoanhNghiep ekycDoanhNghiep = new Gson().fromJson(ekycDoanhNghiepTable.getNoiDung(),EkycDoanhNghiep.class);
			KtraDoanhNghiep ktraDoanhNghiep = new Gson().fromJson(ekycDoanhNghiepTable.getCheckNoiDung(),KtraDoanhNghiep.class);
//            if(StringUtils.isEmpty(ekycDoanhNghiep.getThongTinChuKy())) ekycDoanhNghiep.setThongTinChuKy("{}");

			if (!StringUtils.isEmpty(ekycDoanhNghiep.getFileBusinessRegistration())) {
				model.addAttribute("fileBusinessRegistration",
						CommonUtils.encodeFileToBase64Binary(new File(ekycDoanhNghiep.getFileBusinessRegistration())));
			}
			if (!StringUtils.isEmpty(ekycDoanhNghiep.getFileAppointmentOfChiefAccountant())) {
				model.addAttribute("fileAppointmentOfChiefAccountant", CommonUtils
						.encodeFileToBase64Binary(new File(ekycDoanhNghiep.getFileAppointmentOfChiefAccountant())));
			}
			if (!StringUtils.isEmpty(ekycDoanhNghiep.getFileBusinessRegistrationCertificate())) {
				model.addAttribute("fileBusinessRegistrationCertificate", CommonUtils
						.encodeFileToBase64Binary(new File(ekycDoanhNghiep.getFileBusinessRegistrationCertificate())));
			}
			if (!StringUtils.isEmpty(ekycDoanhNghiep.getFileDecisionToAppointChiefAccountant())) {
				model.addAttribute("fileDecisionToAppointChiefAccountant", CommonUtils
						.encodeFileToBase64Binary(new File(ekycDoanhNghiep.getFileDecisionToAppointChiefAccountant())));
			}
			if (!StringUtils.isEmpty(ekycDoanhNghiep.getFileInvestmentCertificate())) {
				model.addAttribute("fileInvestmentCertificate",
						CommonUtils.encodeFileToBase64Binary(new File(ekycDoanhNghiep.getFileInvestmentCertificate())));
			}
			if (!StringUtils.isEmpty(ekycDoanhNghiep.getFileCompanyCharter())) {
				model.addAttribute("fileCompanyCharter",
						CommonUtils.encodeFileToBase64Binary(new File(ekycDoanhNghiep.getFileCompanyCharter())));
			}
			if (!StringUtils.isEmpty(ekycDoanhNghiep.getFileSealSpecimen())) {
				model.addAttribute("fileSealSpecimen",
						CommonUtils.encodeFileToBase64Binary(new File(ekycDoanhNghiep.getFileSealSpecimen())));
			}
			if (!StringUtils.isEmpty(ekycDoanhNghiep.getFileFatcaForms())) {
				model.addAttribute("fileFatcaForms",
						CommonUtils.encodeFileToBase64Binary(new File(ekycDoanhNghiep.getFileFatcaForms())));
			}
			if (!StringUtils.isEmpty(ekycDoanhNghiep.getFileOthers())) {
				model.addAttribute("fileOthers",
						CommonUtils.encodeFileToBase64Binary(new File(ekycDoanhNghiep.getFileOthers())));
			}

			if (!StringUtils.isEmpty(ekycDoanhNghiep.getFileKy())) {
				model.addAttribute("fileKy",
						CommonUtils.encodeFileToBase64Binary(new File(ekycDoanhNghiep.getFileKy())));
			}
			if (!StringUtils.isEmpty(ekycDoanhNghiep.getFileDangKy())) {
				model.addAttribute("fileDangKy",
						CommonUtils.encodeFileToBase64Binary(new File(ekycDoanhNghiep.getFileDangKy())));
			}

			model.addAttribute("legalRepresentators", ekycDoanhNghiep.getLegalRepresentator());
			//if (ekycDoanhNghiep.getHaveAChiefAccountant().equals("yes")) {
				model.addAttribute("chiefAccountants", ekycDoanhNghiep.getChiefAccountant());
			//} else {
			//	model.addAttribute("chiefAccountants", ekycDoanhNghiep.getPersonAuthorizedChiefAccountant());
			//}
			model.addAttribute("personAuthorizedAccountHolders", ekycDoanhNghiep.getPersonAuthorizedAccountHolder());
			model.addAttribute("listOfLeaders", ekycDoanhNghiep.getListOfLeaders());
			System.out.println("ban lanh dao:"+ekycDoanhNghiep.getListOfLeaders());

			model.addAttribute("ekycDoanhNghiepTable", ekycDoanhNghiepTable);
			model.addAttribute("ekycDoanhNghiep", ekycDoanhNghiep);
			model.addAttribute("ktraDoanhNghiep", ktraDoanhNghiep);
			System.out.println("Ktra doanh nghiep:"+ ktraDoanhNghiep);
			model.addAttribute("ekycDoanhNghiepListAccount", ekycDoanhNghiep.getListAccount());

			model.addAttribute("checkLegalRepFinish", checkFinish(ekycDoanhNghiep.getLegalRepresentator()));
			model.addAttribute("checkChiefAccFinish", checkChiefAccFinish(ekycDoanhNghiep.getChiefAccountant()));
			model.addAttribute("checkPAuthorChiefFinish",checkPersonAuthorizedChiefFinish(ekycDoanhNghiep.getPersonAuthorizedAccountHolder()));
			model.addAttribute("checkListOfLeadersFinish",
					checkListOfLeadersFinish(ekycDoanhNghiep.getListOfLeaders()));
			/*
			 * model.addAttribute("noidung", ekycDoanhNghiepTable.getNoiDung());
			 * model.addAttribute("checknoidung", ekycDoanhNghiepTable.getCheckNoiDung());
			 */
			forwartParams(allParams, model);
			return "quantrikhachhang/doanhnghiep/xem";
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("error", e.getMessage());
			return "redirect:/ekyc-doanh-nghiep";
		}
	}

	private Object checkListOfLeadersFinish(ArrayList<InfoPerson> listOfLeaders) {
		boolean check = false;
		if(listOfLeaders != null) {
			if (listOfLeaders.size() == 0) {
				check = false;
			}else if (listOfLeaders.size() > 0) {
				for (int i = 0; i < listOfLeaders.size(); i++) {
					if (listOfLeaders.get(i).getKiemTra() == null) {
						check = false;
					} else if (listOfLeaders.get(i).getKiemTra().equals("update")) {
						check = true;
					} else {
						check = false;
					}
					// check = true;
				}
			}
		}else {
			check = false;
		}
		
		return check;
	}

	private Object checkPersonAuthorizedChiefFinish(ArrayList<InfoPerson> personAuthorizedChief) {
		boolean check = false;
		if(personAuthorizedChief != null) {
			if (personAuthorizedChief.size() == 0) {
				check = false;
			}else if(personAuthorizedChief.size() > 0){
				for (int i = 0; i < personAuthorizedChief.size(); i++) {
					System.out.println(personAuthorizedChief.get(i).getKiemTra());
					if (personAuthorizedChief.get(i).getKiemTra() == null) {
						check = false;
					} else if (personAuthorizedChief.get(i).getKiemTra().equals("update")) {
						check = true;
					} else {
						check = false;
					}
					// check = true;
				}
			}
		}else {
			check = false;
		}
		
			
		
		return check;
	}

	private Object checkChiefAccFinish(ArrayList<InfoPerson> chiefAccountant) {
		boolean check = false;
		if(chiefAccountant != null) {
			if (chiefAccountant.size() == 0) {
				check = false;
			}else if(chiefAccountant.size() > 0) {
				for (int i = 0; i < chiefAccountant.size(); i++) {
					System.out.println(chiefAccountant.get(i).getKiemTra());
					if (chiefAccountant.get(i).getKiemTra() == null) {
						check = false;
					} else if (chiefAccountant.get(i).getKiemTra().equals("update")) {
						check = true;
					} else {
						check = false;
					}
					// check = true;
				}	
			}
		}else {
			check = false;
		}
		
			
		
		return check;
	}

	private Object checkFinish(ArrayList<InfoPerson> legalRepresentator) {
		boolean check = false;
		if(legalRepresentator != null) {
			if (legalRepresentator.size() == 0) {
				check = false;
			}else if(legalRepresentator.size() > 0) {
				for (int i = 0; i < legalRepresentator.size(); i++) {
					System.out.println(legalRepresentator.get(i).getKiemTra());
					if (legalRepresentator.get(i).getKiemTra() == null) {
						check = false;
					} else if (legalRepresentator.get(i).getKiemTra().equals("update")) {
						check = true;
					} else {
						check = false;
					}
					// check = true;
				}
			}
		}else {
			check = false;
		}
		
			
		
		return check;
	}

	@GetMapping(value = { "/ekyc-doanh-nghiep-2" })
	public String danhSachXemGiaoDichDemo2(Model model, @RequestParam Map<String, String> allParams,
			HttpServletRequest req) throws ParseException {

		List<EkycDoanhNghiepTable> ekycDoanhNghieps = ekycDoanhNghiepRepository.select();

		model.addAttribute("ekycDoanhNghieps", ekycDoanhNghieps);
		model.addAttribute("uri", req.getRequestURI());
		forwartParams(allParams, model);
		return "quantrikhachhang/doanhnghiep/danhsach";
	}

//	@GetMapping(value = "/ekyc-doanh-nghiep-2/xem")
//    public String getnguoiDungXem2(Model model, HttpServletRequest req, @RequestParam Map<String, String> allParams, RedirectAttributes redirectAttributes) {
//
//        try {
//            if (StringUtils.isEmpty(allParams.get("id"))) {
//                throw new Exception(language.getMessage("xem_that_bai"));
//            }
//            Optional<EkycDoanhNghiepTable> userInfo = ekycDoanhNghiepRepository.findById(Long.valueOf(allParams.get("id")));
//            if (!userInfo.isPresent()) {
//                throw new Exception(language.getMessage("khong_ton_tai_ban_ghi"));
//            }
//            
//            EkycDoanhNghiepTable ekycDoanhNghiepTable = userInfo.get();
//            
//            EkycDoanhNghiep ekycDoanhNghiep = new Gson().fromJson(ekycDoanhNghiepTable.getNoiDung(), EkycDoanhNghiep.class);
//            if(StringUtils.isEmpty(ekycDoanhNghiep.getThongTinChuKy())) ekycDoanhNghiep.setThongTinChuKy("{}");
//            if(!StringUtils.isEmpty(ekycDoanhNghiep.getFileDangKyKinhDoanh())) {
//            	model.addAttribute("fileDangKyKinhDoanh", CommonUtils.encodeFileToBase64Binary(new File(ekycDoanhNghiep.getFileDangKyKinhDoanh())));
//            }
//            if(!StringUtils.isEmpty(ekycDoanhNghiep.getFileQuyetDinhBoNhiemKtt())) {
//            	model.addAttribute("fileQuyetDinhBoNhiemKtt", CommonUtils.encodeFileToBase64Binary(new File(ekycDoanhNghiep.getFileQuyetDinhBoNhiemKtt())));
//            }
//            if(!StringUtils.isEmpty(ekycDoanhNghiep.getFileKy())) {
//            	model.addAttribute("fileKy", CommonUtils.encodeFileToBase64Binary(new File(ekycDoanhNghiep.getFileKy())));
//            }
//            if(!StringUtils.isEmpty(ekycDoanhNghiep.getFileDangKy())) {
//            	model.addAttribute("fileDangKy", CommonUtils.encodeFileToBase64Binary(new File(ekycDoanhNghiep.getFileDangKy())));
//            }
//            if(!StringUtils.isEmpty(ekycDoanhNghiep.getAnhMatTruoc()))
//            	model.addAttribute("anhMatTruoc", "data:image/jpeg;base64,"+CommonUtils.encodeFileToBase64Binary(new File(ekycDoanhNghiep.getAnhMatTruoc())));
//            if(!StringUtils.isEmpty(ekycDoanhNghiep.getAnhMatSau()))
//            	model.addAttribute("anhMatSau", "data:image/jpeg;base64,"+CommonUtils.encodeFileToBase64Binary(new File(ekycDoanhNghiep.getAnhMatSau())));
//            if(!StringUtils.isEmpty(ekycDoanhNghiep.getAnhCaNhan()))
//            	model.addAttribute("anhCaNhan", "data:image/jpeg;base64,"+CommonUtils.encodeFileToBase64Binary(new File(ekycDoanhNghiep.getAnhCaNhan()))); 
//            
//            if(!StringUtils.isEmpty(ekycDoanhNghiep.getAnhMatTruocKtt()))
//            	model.addAttribute("anhMatTruocKtt", "data:image/jpeg;base64,"+CommonUtils.encodeFileToBase64Binary(new File(ekycDoanhNghiep.getAnhMatTruocKtt())));
//            if(!StringUtils.isEmpty(ekycDoanhNghiep.getAnhMatSauKtt()))
//            	model.addAttribute("anhMatSauKtt", "data:image/jpeg;base64,"+CommonUtils.encodeFileToBase64Binary(new File(ekycDoanhNghiep.getAnhMatSauKtt())));
//            if(!StringUtils.isEmpty(ekycDoanhNghiep.getAnhCaNhanKtt()))
//            	model.addAttribute("anhCaNhanKtt", "data:image/jpeg;base64,"+CommonUtils.encodeFileToBase64Binary(new File(ekycDoanhNghiep.getAnhCaNhanKtt())));
//            
//            if(!StringUtils.isEmpty(ekycDoanhNghiep.getAnhMatTruocNuq()))
//            	model.addAttribute("anhMatTruocNuq", "data:image/jpeg;base64,"+CommonUtils.encodeFileToBase64Binary(new File(ekycDoanhNghiep.getAnhMatTruocNuq())));
//            if(!StringUtils.isEmpty(ekycDoanhNghiep.getAnhMatSauNuq()))
//            	model.addAttribute("anhMatSauNuq", "data:image/jpeg;base64,"+CommonUtils.encodeFileToBase64Binary(new File(ekycDoanhNghiep.getAnhMatSauNuq())));
//            if(!StringUtils.isEmpty(ekycDoanhNghiep.getMauChuKyNuq()))
//            	model.addAttribute("anhChuKyNuq", "data:image/jpeg;base64,"+CommonUtils.encodeFileToBase64Binary(new File(ekycDoanhNghiep.getMauChuKyNuq())));
//            
//            if(!StringUtils.isEmpty(ekycDoanhNghiep.getAnhMatTruocLd()))
//            	model.addAttribute("anhMatTruocLd", "data:image/jpeg;base64,"+CommonUtils.encodeFileToBase64Binary(new File(ekycDoanhNghiep.getAnhMatTruocLd())));
//            if(!StringUtils.isEmpty(ekycDoanhNghiep.getAnhMatSauLd()))
//            	model.addAttribute("anhMatSauLd", "data:image/jpeg;base64,"+CommonUtils.encodeFileToBase64Binary(new File(ekycDoanhNghiep.getAnhMatSauLd())));
//            
//            model.addAttribute("ekycDoanhNghiepTable", ekycDoanhNghiepTable);
//            model.addAttribute("ekycDoanhNghiep", ekycDoanhNghiep);
//            forwartParams(allParams, model);
//            return "quantrikhachhang/doanhnghiep/xem2";
//        } catch (Exception e) {
//        	e.printStackTrace();
//            redirectAttributes.addFlashAttribute("error", e.getMessage());
//            return "redirect:/ekyc-doanh-nghiep";
//        }
//    }

	@RequestMapping(value = "/xoa-ekyc-doanh-nghiep", method = { RequestMethod.GET })
	public String delete(Model model, @RequestParam Map<String, String> allParams,
			RedirectAttributes redirectAttributes, HttpServletRequest req) {
		if (!StringUtils.isEmpty(allParams.get("id"))) {
			EkycDoanhNghiepTable checkNd = ekycDoanhNghiepRepository.findById(Long.valueOf(allParams.get("id"))).get();
			if (checkNd != null) {
				ekycDoanhNghiepRepository.delete(checkNd);
			}
			redirectAttributes.addFlashAttribute("success", language.getMessage("xoa_thanh_cong"));
		} else {
			redirectAttributes.addFlashAttribute("error", language.getMessage("xoa_that_bai"));
		}

		return "redirect:/ekyc-doanh-nghiep?" + queryStringBuilder(allParams);
	}

	@GetMapping(value = { "/ekyc-doanh-nghiep/img-byte" }, produces = MediaType.IMAGE_JPEG_VALUE)
	@ResponseBody
	public ResponseEntity<byte[]> getImage(HttpServletResponse resp, @RequestParam Map<String, String> allParams) {
		try {
			String pathImg = allParams.get("path");
			File file = new File(pathImg);

			byte[] bytes = StreamUtils.copyToByteArray(new FileInputStream(file));

			return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(bytes);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

	@GetMapping(value = { "/ekyc-doanh-nghiep/pdf-byte" }, produces = MediaType.APPLICATION_PDF_VALUE)
	@ResponseBody
	public ResponseEntity<byte[]> getPdf(HttpServletResponse resp, @RequestParam Map<String, String> allParams) {
		try {
			String pathImg = allParams.get("path");
			File file = new File(pathImg);

			byte[] bytes = StreamUtils.copyToByteArray(new FileInputStream(file));

			return ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF).body(bytes);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}
}
