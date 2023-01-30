package fis.com.vn.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import fis.com.vn.table.EkycDoanhNghiepTable;

@Repository
public interface EkycDoanhNghiepRepository extends CrudRepository<EkycDoanhNghiepTable, Long> {


	String name = "ngay_tao";

	@Query(value = "select * from ekyc_doanh_nghiep order by id desc", countQuery = "select count(1) from ekyc_doanh_nghiep", nativeQuery = true)
	List<EkycDoanhNghiepTable> select();

	@Query(value = "select * from ekyc_doanh_nghiep where"
			+ "(:ma_doanh_nghiep is null or ma_doanh_nghiep like %:ma_doanh_nghiep%)"
			
			+ "and (:tenNguoiQuanLy is null or tenNguoiQuanLy like %:tenNguoiQuanLy%)"
			+ "and (:emailNguoiLienHe is null or emailNguoiLienHe like %:emailNguoiLienHe%)"
			+ "and (:soDienThoaiNguoiLienHe is null or soDienThoaiNguoiLienHe like %:soDienThoaiNguoiLienHe%)"
			
			+ "and (:tenNguoiLienHe is null or tenNguoiLienHe like %:tenNguoiLienHe%)"
			+ "and (:ekyc_status is null or status like  %:ekyc_status%)"
			+ "and (:fromDate is null or  ngay_tao >= :fromDate)"
			+ "and (:toDate is null or ngay_tao < :toDate)",
			countQuery = "select count(1) from ekyc_doanh_nghiep where"
					+ "(:ma_doanh_nghiep is null or ma_doanh_nghiep like %:ma_doanh_nghiep%)"
				
					+ "and (:tenNguoiQuanLy is null or tenNguoiQuanLy like %:tenNguoiQuanLy%)"
					+ "and (:emailNguoiLienHe is null or emailNguoiLienHe like %:emailNguoiLienHe%)"
					+ "and (:soDienThoaiNguoiLienHe is null or soDienThoaiNguoiLienHe like %:soDienThoaiNguoiLienHe%)"
				
					+ "and (:tenNguoiLienHe is null or tenNguoiLienHe like %:tenNguoiLienHe%)"
					+ "and (:ekyc_status is null or status like  %:ekyc_status%)"
					+ "and (:fromDate is null or  ngay_tao >= :fromDate)"
					+ "and (:toDate is null or ngay_tao < :toDate)"
					, nativeQuery = true)
	Page<EkycDoanhNghiepTable> selectParams(
			@Param("ma_doanh_nghiep") String ma_doanh_nghiep,
			@Param("fromDate") Date fromDate, 
			@Param("toDate") Date toDate,
            @Param("tenNguoiQuanLy") String tenNguoiQuanLy,
            @Param("emailNguoiLienHe") String emailNguoiLienHe,
            @Param("soDienThoaiNguoiLienHe") String soDienThoaiNguoiLienHe,
            @Param("tenNguoiLienHe") String tenNguoiLienHe,
            @Param("ekyc_status") String ekyc_status,
			 Pageable pageable );

	EkycDoanhNghiepTable findByToken(String dienThoai);

	@Query(value = "select * from ekyc_doanh_nghiep where (:username is null or username like %:username%)", nativeQuery = true)
	EkycDoanhNghiepTable findByUsername(@Param("username") String username);
	
	@Query(value = "select * from ekyc_doanh_nghiep order by ma_doanh_nghiep desc", nativeQuery = true)
	EkycDoanhNghiepTable sortEkyc();
	
}
