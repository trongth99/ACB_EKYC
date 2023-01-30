package fis.com.vn.entities;

import java.util.ArrayList;

import lombok.Data;

@Data
public class KtraDoanhNghiep {
  String diaChiNhanThongBaoThue;
  String keToanTruong;
  ArrayList<InfoNguoiDaiDien> nguoiDaiDienPhapLuat;
  String tenDoanhNghiepVietTat;
  String diaChi;
  String loaiHinhDoanhNghiep;
  String ngayThanhLap;
  String giamDoc;
  String tenDoanhNghiep;
  String tinhTrangHoatDong;
  String noiDangKyNopThue;
  String coQuanCap;
  String maSoDoanhNghiep;
  String tenDoanhNghiepEn;
  String ngayBatDauHD;
  String ngayCap;
  String noiDangKyQuanLyThue;
}
