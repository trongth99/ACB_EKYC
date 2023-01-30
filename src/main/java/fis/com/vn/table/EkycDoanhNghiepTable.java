package fis.com.vn.table;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Nationalized;

import lombok.Data;

@Entity
@Table(name = "ekyc_doanh_nghiep")
@Data
public class EkycDoanhNghiepTable {
	@Id
	long id;

	@Column(name = "noi_dung")
	@Nationalized
	String noiDung;

	@Column(name = "check_noi_dung")
	@Nationalized
	String checkNoiDung;

	@Column(name = "ma_doanh_nghiep")
	@Nationalized
	String maDoanhNghiep;

	@Column(name = "ten_doanh_nghiep")
	@Nationalized
	String tenDoanhNghiep;

	
	@Column(name = "ngay_tao")
	Date ngayTao;

	@Column(name = "token")
	@Nationalized
	String token;

	@Column(name = "status")
	@Nationalized
	String status;

	@Column(name = "tenNguoiQuanLy")
	@Nationalized
	String tenNguoiQuanLy;

	@Column(name = "emailNguoiLienHe")
	@Nationalized
	String emailNguoiLienHe;

	@Column(name = "soDienThoaiNguoiLienHe")
	@Nationalized
	String soDienThoaiNguoiLienHe;

	@Column(name = "tenNguoiLienHe")
	@Nationalized
	String tenNguoiLienHe;

	@Column(name = "step")
	@Nationalized
	String step;

	@Nationalized
	@Size(max = 255, message = "Tên đăng nhập dài không quá 255 ký tự")
	@Column(name = "username", length = 255)
	String username;

	@Nationalized
	@Size(max = 255, message = "Tên đăng nhập dài không quá 255 ký tự")
	@Column(name = "usernamelogin", length = 255)
	String userNameLogin;
}
