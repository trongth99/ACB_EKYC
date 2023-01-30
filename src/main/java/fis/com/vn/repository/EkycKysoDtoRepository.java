package fis.com.vn.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import fis.com.vn.table.ThongKeEkycKyso;

@Repository
public interface EkycKysoDtoRepository extends CrudRepository<ThongKeEkycKyso, Long>{

	@Query(value = "select MIN(id)  as id, status as trangThaiEkyc, count(status) as tongSo from ekyc_doanh_nghiep WHERE "+
			"(:fromDate is null or ngay_tao >= :fromDate)  " +
			"and (:toDate is null or ngay_tao < :toDate) " + 
			"group by status;"
			, countQuery = "select count(1) from ekyc_doanh_nghiep WHERE " +
					"(:fromDate is null or ngay_tao >= :fromDate)  " +
					"and (:toDate is null or ngay_tao < :toDate) " 
                    , nativeQuery = true)
	List<ThongKeEkycKyso> thongKeTrangThai(@Param("fromDate") Date fromDate, 
			@Param("toDate") Date toDate );

	@Query(value = "select MIN(id)  as id, status as trangThaiEkyc, count(status) as tongSo from ekyc_doanh_nghiep WHERE "+
			"(:fromDate is null or ngay_tao >= :fromDate)  " +
			"and (:toDate is null or ngay_tao < :toDate) " + 
			"and (:usernamelogin is null or usernamelogin  = :usernamelogin) " +
			"group by status;"
			, countQuery = "select count(1) from ekyc_doanh_nghiep WHERE " +
					"(:fromDate is null or ngay_tao >= :fromDate)  " +
					"and (:usernamelogin is null or usernamelogin  = :usernamelogin) " +
					"and (:toDate is null or ngay_tao < :toDate) " 
                    , nativeQuery = true)
	List<ThongKeEkycKyso> thongKeTrangThaiUser(@Param("fromDate") Date fromDate, 
			@Param("toDate") Date toDate ,@Param("usernamelogin") String userNameLogin);

}
