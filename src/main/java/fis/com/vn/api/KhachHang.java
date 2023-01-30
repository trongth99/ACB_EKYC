package fis.com.vn.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import fis.com.vn.common.StringUtils;
import fis.com.vn.component.ConfigProperties;

@Component
public class KhachHang {
	@Autowired ConfigProperties configProperties;
	
	
	public Boolean getKiemTraAnh1NhinThang(String maToChuc) {
		Boolean kiemTraFace = !StringUtils.isEmpty(configProperties.getConfig().getKiem_tra_liveness()) && configProperties.getConfig().getKiem_tra_liveness().indexOf("ktanh1nhinthang") != -1?true:false;
		return kiemTraFace;
	}
	
	public Boolean getKiemTraMoMom(String maToChuc) {
		Boolean kiemTraFace = !StringUtils.isEmpty(configProperties.getConfig().getKiem_tra_liveness()) && configProperties.getConfig().getKiem_tra_liveness().indexOf("ktmomom") != -1?true:false;
		return kiemTraFace;
	}
	
	public Boolean getKiemTraNhamMat(String maToChuc) {
		Boolean kiemTraFace = !StringUtils.isEmpty(configProperties.getConfig().getKiem_tra_liveness()) && configProperties.getConfig().getKiem_tra_liveness().indexOf("ktnhammat") != -1?true:false;
		return kiemTraFace;
	}
	public Boolean getKiemTraDoiMu(String maToChuc) {
		Boolean kiemTraFace = !StringUtils.isEmpty(configProperties.getConfig().getKiem_tra_liveness()) && configProperties.getConfig().getKiem_tra_liveness().indexOf("ktdoimu") != -1?true:false;
		return kiemTraFace;
	}
	public Boolean getKiemTraNhinThang(String maToChuc) {
		Boolean kiemTraFace = !StringUtils.isEmpty(configProperties.getConfig().getKiem_tra_liveness()) && configProperties.getConfig().getKiem_tra_liveness().indexOf("ktnhinthang") != -1?true:false;
		return kiemTraFace;
	}
	
	public Boolean getKiemTraDeoKinh(String maToChuc) {
		Boolean kiemTraFace = !StringUtils.isEmpty(configProperties.getConfig().getKiem_tra_liveness()) && configProperties.getConfig().getKiem_tra_liveness().indexOf("ktdeokinh") != -1?true:false;
		return kiemTraFace;
	}
	
	public Boolean getKiemTraDeoKinhTrang(String maToChuc) {
		Boolean kiemTraFace = !StringUtils.isEmpty(configProperties.getConfig().getKiem_tra_liveness()) && configProperties.getConfig().getKiem_tra_liveness().indexOf("ktdeokinhtrang") != -1?true:false;
		return kiemTraFace;
	}
	
	public Boolean getKiemTraDeoKhauTrang(String maToChuc) {
		Boolean kiemTraFace = !StringUtils.isEmpty(configProperties.getConfig().getKiem_tra_liveness()) && configProperties.getConfig().getKiem_tra_liveness().indexOf("ktdeokhautrang") != -1?true:false;
		return kiemTraFace;
	}
	
	public Boolean getKiemTraChuyenDong(String maToChuc) {
		Boolean kiemTraFace = !StringUtils.isEmpty(configProperties.getConfig().getKiem_tra_liveness()) && configProperties.getConfig().getKiem_tra_liveness().indexOf("ktchuyendong") != -1?true:false;
		return kiemTraFace;
	}
	
	public Boolean getKiemTraChupTuManHinh(String maToChuc) {
		Boolean kiemTraOcr = !StringUtils.isEmpty(configProperties.getConfig().getKiem_tra()) && configProperties.getConfig().getKiem_tra().indexOf("mh") != -1?true:false;
		return kiemTraOcr;
	}
	public Boolean getKiemTraVanTay(String maToChuc) {
		Boolean kiemTraOcr = !StringUtils.isEmpty(configProperties.getConfig().getKiem_tra()) && configProperties.getConfig().getKiem_tra().indexOf("ktvt") != -1?true:false;
		return kiemTraOcr;
	}
	public Boolean getKiemTraMaTinhMatTruocSauCmt9So(String maToChuc) {
		Boolean kiemTraOcr = !StringUtils.isEmpty(configProperties.getConfig().getKiem_tra()) && configProperties.getConfig().getKiem_tra().indexOf("ktmt") != -1?true:false;
		return kiemTraOcr;
	}
	public Boolean getKiemTraDuThongTinOcr(String maToChuc) {
		Boolean kiemTraOcr = !StringUtils.isEmpty(configProperties.getConfig().getKiem_tra()) && configProperties.getConfig().getKiem_tra().indexOf("ktdtt") != -1?true:false;
		return kiemTraOcr;
	}
	public Boolean getKiemTraAnhBiLoa(String maToChuc) {
		Boolean kiemTraOcr = !StringUtils.isEmpty(configProperties.getConfig().getKiem_tra()) && configProperties.getConfig().getKiem_tra().indexOf("abls") != -1?true:false;
		return kiemTraOcr;
	}
	public Boolean getKiemTraSuaDoi(String maToChuc) {
		Boolean kiemTraOcr = !StringUtils.isEmpty(configProperties.getConfig().getKiem_tra()) && configProperties.getConfig().getKiem_tra().indexOf("sd") != -1?true:false;
		return kiemTraOcr;
	}
	public Boolean getKiemTraAnh(String maToChuc) {
		Boolean kiemTraOcr = !StringUtils.isEmpty(configProperties.getConfig().getKiem_tra()) && configProperties.getConfig().getKiem_tra().indexOf("kta") != -1?true:false;
		return kiemTraOcr;
	}
	public Boolean getKiemTraNgayhetHan(String maToChuc) {
		Boolean kiemTraOcr = !StringUtils.isEmpty(configProperties.getConfig().getKiem_tra()) && configProperties.getConfig().getKiem_tra().indexOf("nhh") != -1?true:false;
		return kiemTraOcr;
	}
	public Boolean getKiemTraDauQuocHuy(String maToChuc) {
		Boolean kiemTraOcr = !StringUtils.isEmpty(configProperties.getConfig().getKiem_tra()) && configProperties.getConfig().getKiem_tra().indexOf("qh") != -1?true:false;
		return kiemTraOcr;
	}
	public Boolean getKiemTraThayTheAnh(String maToChuc) {
		Boolean kiemTraOcr = !StringUtils.isEmpty(configProperties.getConfig().getKiem_tra()) && configProperties.getConfig().getKiem_tra().indexOf("tta") != -1?true:false;
		return kiemTraOcr;
	}
	public Boolean getKiemTraDauNoi(String maToChuc) {
		Boolean kiemTraOcr = !StringUtils.isEmpty(configProperties.getConfig().getKiem_tra()) && configProperties.getConfig().getKiem_tra().indexOf("dn") != -1?true:false;
		return kiemTraOcr;
	}
	public Boolean getKiemTraPhoto(String maToChuc) {
		Boolean kiemTraOcr = !StringUtils.isEmpty(configProperties.getConfig().getKiem_tra()) && configProperties.getConfig().getKiem_tra().indexOf("pt") != -1?true:false;
		return kiemTraOcr;
	}
	public Boolean getKiemTraCatGoc(String maToChuc) {
		Boolean kiemTraOcr = !StringUtils.isEmpty(configProperties.getConfig().getKiem_tra()) && configProperties.getConfig().getKiem_tra().indexOf("cg") != -1?true:false;
		return kiemTraOcr;
	}
	public Boolean getKiemTraDauVanTay(String maToChuc) {
		Boolean kiemTraOcr = !StringUtils.isEmpty(configProperties.getConfig().getKiem_tra()) && configProperties.getConfig().getKiem_tra().indexOf("vt") != -1?true:false;
		return kiemTraOcr;
	}
	public Boolean getKiemTraDauXacNhanMs(String maToChuc) {
		Boolean kiemTraOcr = !StringUtils.isEmpty(configProperties.getConfig().getKiem_tra()) && configProperties.getConfig().getKiem_tra().indexOf("xn") != -1?true:false;
		return kiemTraOcr;
	}
	public Boolean getKiemTraQuyLuatSoCccd(String maToChuc) {
		Boolean kiemTraOcr = !StringUtils.isEmpty(configProperties.getConfig().getKiem_tra()) && configProperties.getConfig().getKiem_tra().indexOf("ql") != -1?true:false;
		return kiemTraOcr;
	}
	public Double getNguongSoSanh(String maToChuc) {
		try {
			Double nguong = Double.valueOf(configProperties.getConfig().getNguong_chinh_xac());
			return nguong;
		} catch (Exception e) {
		}
		return null;
	}
}
