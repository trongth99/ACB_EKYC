package fis.com.vn.thread;

import java.io.File;
import java.util.Date;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.tomcat.util.json.JSONParser;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.json.JsonParser;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.google.gson.Gson;

import fis.com.vn.common.CommonUtils;
import fis.com.vn.entities.EkycDoanhNghiep;
import fis.com.vn.entities.KtraDoanhNghiep;
import fis.com.vn.entities.RespApi;
import fis.com.vn.repository.EkycDoanhNghiepRepository;
import fis.com.vn.table.EkycDoanhNghiepTable;

@Component
public class EkycDoanhNghiepThread {
	@Autowired
	EkycDoanhNghiepRepository ekycDoanhNghiepRepository;
	@Value("${TOKEN}")
	protected String token;
	
	@Value("${CODE}")
	protected String code;

	@Value("${API_SERVICE}")
	protected String API_SERVICE;

	@Value("${KY_SO_FOLDER}")
	protected String KY_SO_FOLDER;

	@Async
	public void start(EkycDoanhNghiep ekycDoanhNghiep) {
//		EkycDoanhNghiepTable doanhNghiepTable = new EkycDoanhNghiepTable();
//		doanhNghiepTable.setMaDoanhNghiep(ekycDoanhNghiep.getMaSoDoanhNghiep());
//		doanhNghiepTable.setTenDoanhNghiep(ekycDoanhNghiep.getTenDoanhNghiep());
//		doanhNghiepTable.setNoiDung(new Gson().toJson(ekycDoanhNghiep));
//		doanhNghiepTable.setNgayTao(new Date());
//		
//		JSONObject checkJson = check1414(ekycDoanhNghiep.getAnh1414(), ekycDoanhNghiep.getHoVaTen(), ekycDoanhNghiep.getSoGiayTo(), false); 
//		checkDuLieuDinhDanh(ekycDoanhNghiep.getSoDienThoai(), ekycDoanhNghiep.getHoVaTen(), ekycDoanhNghiep.getSoGiayTo(), checkJson);
//		checkBhxh(ekycDoanhNghiep.getSoDienThoai(), ekycDoanhNghiep.getHoVaTen(), ekycDoanhNghiep.getSoGiayTo(), ekycDoanhNghiep.getDiaChi(), null, checkJson);
//		ekycDoanhNghiep.setCheck1414(checkJson.toString());
//		
//		JSONObject checkJsonKtt = check1414(ekycDoanhNghiep.getAnh1414Ktt(), ekycDoanhNghiep.getHoVaTenKtt(), ekycDoanhNghiep.getSoGiayToKtt(), false); 
//		checkDuLieuDinhDanh(ekycDoanhNghiep.getSoDienThoaiKtt(), ekycDoanhNghiep.getHoVaTenKtt(), ekycDoanhNghiep.getSoGiayToKtt(), checkJsonKtt);
//		checkBhxh(ekycDoanhNghiep.getSoDienThoaiKtt(), ekycDoanhNghiep.getHoVaTenKtt(), ekycDoanhNghiep.getSoGiayToKtt(), ekycDoanhNghiep.getDiaChi(), null, checkJsonKtt);
//		ekycDoanhNghiep.setCheck1414Ktt(checkJsonKtt.toString());
//		
//		JSONObject checkJsonNuq = check1414(ekycDoanhNghiep.getAnh1414Nuq(), ekycDoanhNghiep.getHoVaTenNuq(), ekycDoanhNghiep.getSoGiayToNuq(), true);
//		checkDuLieuDinhDanh(ekycDoanhNghiep.getSoDienThoaiNuq(), ekycDoanhNghiep.getHoVaTenNuq(), ekycDoanhNghiep.getSoGiayToNuq(), checkJsonNuq);
//		checkBhxh(ekycDoanhNghiep.getSoDienThoaiNuq(), ekycDoanhNghiep.getHoVaTenNuq(), ekycDoanhNghiep.getSoGiayToNuq(), ekycDoanhNghiep.getDiaChi(), ekycDoanhNghiep.getMaTinhBhxh(), checkJsonNuq);
//		ekycDoanhNghiep.setCheck1414Nuq(checkJsonNuq.toString());
//		
//		doanhNghiepTable.setNoiDung(new Gson().toJson(ekycDoanhNghiep));
//		ekycDoanhNghiepRepository.save(doanhNghiepTable);
	}

	@Async
	public void startNoiDung( String userName , String maSoDoanhNghiep) {
		EkycDoanhNghiepTable doanhNghiepTable = ekycDoanhNghiepRepository.findByUsername(userName);
		
//		System.out.println("ma so doanh nghiep: "+maSoDoanhNghiep);
//		JSONObject jsonObject = new JSONObject();
//		jsonObject.put("maSoDoanhNghiep", maSoDoanhNghiep);
//		String resp = postRequest(jsonObject.toString(), "/public/all/kiem-tra-thong-tin-doanh-nghiep");
//		if (resp == null) {
//			System.out.println("jsadjhsahdh");
//			jsonObject.put("message", "Lỗi hệ thống");
//			jsonObject.put("status", 400);
//		}
//		System.out.println("Repshhhhhhh: "+resp);
//		JSONObject jsonObject2 = new JSONObject(resp);
	
		
		
		//checkNoiDung(checkJson);
		//ekycDoanhNghiep.setCheck1414(checkJson.toString());
		System.out.println("json doanh nghiep: "+JsonThongTinDoanhNghiep());
		KtraDoanhNghiep ktraDoanhNghiep =  new Gson().fromJson(JsonThongTinDoanhNghiep().toString(), KtraDoanhNghiep.class);
		System.out.println("ktradoanhnghie: "+ktraDoanhNghiep);
		
		doanhNghiepTable.setCheckNoiDung(new Gson().toJson(ktraDoanhNghiep));
		ekycDoanhNghiepRepository.save(doanhNghiepTable);
	}

	private JSONObject checkNoiDung(JSONObject checkJson) {
	
			JSONObject jsonObject = new JSONObject();

			String resp = postRequest(jsonObject.toString(), "/public/all/kiem-tra-thong-tin-doanh-nghiep");
			if (resp != null) {
				JSONObject jsonObject2 = new JSONObject(resp);
				if (jsonObject2.getInt("status") == 200) {
					org.json.JSONArray jsonArray = jsonObject2.getJSONObject("data").getJSONArray("thongTin");
					for (int i = 0; i < jsonArray.length(); i++) {
						checkJson.put(jsonArray.getJSONObject(i).getString("ten"),
								jsonArray.getJSONObject(i).getString("trangThai"));
					}
				}
			}
			return jsonObject;
		
	}

	private void checkBhxh(String dienThoai, String hoVaTen, String soCmt, String diaChi, String maTinh,
			JSONObject checkJson) {
		try {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("hoVaTen", hoVaTen);
			jsonObject.put("soCmt", soCmt);
			jsonObject.put("dienThoai", dienThoai);
			if (!StringUtils.isEmpty(maTinh)) {
				jsonObject.put("maTinh", maTinh);
			} else {
				jsonObject.put("diaChi", diaChi);
			}

			String resp = postRequest(jsonObject.toString(), "/public/all/kiem-tra-bhxh");
			if (resp != null) {
				JSONObject jsonObject2 = new JSONObject(resp);
				if (jsonObject2.getInt("status") == 200) {
					org.json.JSONArray jsonArray = jsonObject2.getJSONObject("data").getJSONArray("thongTin");
					for (int i = 0; i < jsonArray.length(); i++) {
						checkJson.put(jsonArray.getJSONObject(i).getString("ten"),
								jsonArray.getJSONObject(i).getString("trangThai"));
					}
				}
			}
		} catch (Exception e) {
		}
	}

	private void checkDuLieuDinhDanh(String dienThoai, String hoVaTen, String soCmt, JSONObject checkJson) {
		try {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("hoVaTen", hoVaTen);
			jsonObject.put("soCmt", soCmt);
			jsonObject.put("dienThoai", dienThoai);
			String resp = postRequest(jsonObject.toString(), "/public/all/kiem-tra-du-lieu-dinh-danh");
			if (resp != null) {
				JSONObject jsonObject2 = new JSONObject(resp);
				if (jsonObject2.getInt("status") == 200) {
					org.json.JSONArray jsonArray = jsonObject2.getJSONObject("data").getJSONArray("thongTin");
					for (int i = 0; i < jsonArray.length(); i++) {
						checkJson.put(jsonArray.getJSONObject(i).getString("ten"),
								jsonArray.getJSONObject(i).getString("trangThai"));
					}
				}
			}
		} catch (Exception e) {
		}
	}
	


	private JSONObject check1414(String path, String hoVaTen, String soCmt, boolean nuq) {
		JSONObject checkJson = new JSONObject();
		try {
			if (StringUtils.isEmpty(path))
				return checkJson;
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("anhTinNhan", CommonUtils.encodeFileToBase64Binary(new File(path)));
			String resp = postRequest(jsonObject.toString(), "/public/all/ocr-tin-nhan-1414");
			if (resp != null) {
				JSONObject jsonObject2 = new JSONObject(resp);
				if (jsonObject2.getInt("status") == 200) {
					if (jsonObject2.getJSONObject("data").has("hoVaTen") && jsonObject2.getJSONObject("data")
							.getString("hoVaTen").toLowerCase().equals(alias(hoVaTen.toLowerCase()))) {
						checkJson.put("Compare customers name on the ID Card with the name from 1414", "true");
					} else {
						checkJson.put("Compare customers name on the ID Card with the name from 1414", "false");
					}
					if (jsonObject2.getJSONObject("data").has("soGiayTo")
							&& jsonObject2.getJSONObject("data").getString("soGiayTo").equals(soCmt)) {
						checkJson.put("Compare customers ID Card number with the ID Card number from 1414", "true");
					} else {
						checkJson.put("Compare customers ID Card number with the ID Card number from 1414", "false");
					}
				}
			}
			checkJson.put("Check personal photos and ID photos", "true");
			if (nuq) {
				checkJson.put("Check customers actions on the selfie video", "true");
			}
			return checkJson;
		} catch (Exception e) {
		}
		checkJson.put("Check personal photos and ID photos", "true");
		if (nuq) {
			checkJson.put("Check customers actions on the selfie video", "true");
		}
		return checkJson;
	}

	public String postRequest(String data, String url) {
		return postRequest(data, url, token, code);
	}

	public String postRequest(String data, String url, String tokenApi, String codeApi) {
		RespApi respApi = new RespApi();
		try {
			HttpClient httpClient = HttpClientBuilder.create().build();
			HttpPost request = new HttpPost(API_SERVICE + url);
			StringEntity params = new StringEntity(data, "UTF-8");
			request.addHeader("content-type", "application/json");
			request.addHeader("token", tokenApi);
			request.addHeader("code", codeApi);
			request.addHeader("Accept-Language", "en");
			request.setEntity(params);
			HttpResponse response = httpClient.execute(request);

			String responseString = new BasicResponseHandler().handleResponse(response);

			return responseString;
		} catch (Exception e) {
			respApi.setStatus(400);
			respApi.setMessage("Lỗi hệ thống");
		}
		return new Gson().toJson(respApi);
	}

	public static String alias(String str) {
		try {
			if (!StringUtils.isEmpty(str)) {
				String alias = org.apache.commons.lang3.StringUtils.stripAccents(str).trim().toLowerCase()
						.replace("đ", "d").replace("Đ", "D");
				;

				return alias;
			}
		} catch (Exception e) {
		}
		return str;
	}
	
	public JSONObject JsonThongTinDoanhNghiep() {
		JSONObject js = new JSONObject();
		js.put("hoVaTen","DƯƠNG DŨNG TRIỀU");
		js.put("loaiGiayTo","Chứng minh nhân dân");
		js.put("soCmt","011674617");
		js.put("ngaySinh","10/06/1973");
		js.put("gioiTinh","Nam");
		JSONArray jsarr = new JSONArray();
		jsarr.put(js);
		
		JSONObject ja = new JSONObject();
		ja.put("diaChiNhanThongBaoThue","Tầng 22 tòa nhà Keangnam Landmark72, E6 đường Phạm Hùng - Phường Mễ Trì - Quận Nam Từ Liêm - Hà Nội.");
		ja.put("keToanTruong","Ngô Thị Minh Huệ");
		ja.put("nguoiDaiDienPhapLuat",jsarr);
		ja.put("tenDoanhNghiepVietTat","FIS.,CORP");
		ja.put("diaChi","Tầng 22 tòa nhà Keangnam Landmark72, E6 đường Phạm Hùng, Phường Mễ Trì, Quận Nam Từ Liêm, Thành phố Hà Nội, Việt Nam");
		ja.put("loaiHinhDoanhNghiep","Công ty trách nhiệm hữu hạn một thành viên");
		ja.put("ngayThanhLap","13/08/2009");
		ja.put("giamDoc","Nguyễn Văn Khoa");
		ja.put("tenDoanhNghiep","CÔNG TY TNHH HỆ THỐNG THÔNG TIN FPT");
		ja.put("tinhTrangHoatDong","Đang hoạt động");
		ja.put("noiDangKyNopThue","Cục thuế Doanh nghiệp lớn");
		ja.put("coQuanCap","Thành phố Hà Nội.");
		ja.put("maSoDoanhNghiep","0104128565");
		ja.put("tenDoanhNghiepEn","FPT INFORMATION SYSTEM CORPORATION");
		ja.put("ngayBatDauHD","01/09/2009");
		ja.put("noiDangKyNopThue","25/08/2009");
		ja.put("noiDangKyQuanLyThue","Cục thuế Doanh nghiệp lớn");
		return ja;
	}
}
