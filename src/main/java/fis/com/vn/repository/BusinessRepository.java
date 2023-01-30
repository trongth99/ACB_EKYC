package fis.com.vn.repository;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import fis.com.vn.table.Business;

@Repository
public interface BusinessRepository extends CrudRepository<Business,Long>{
	   @Query(value = "SELECT b.* FROM business b " +
	            "WHERE (:uname is null or username like %:uname%) " +
	            "and (:fname is null or full_name like %:fname%) " +
	            "and (:email is null or email like %:email%) " +
	            "and (:status is null or status = :status) " 
	            ,
	            countQuery = "SELECT count(1) FROM business  " +
	                    "WHERE (:uname is null or username like %:uname%) " +
	                    "and (:fname is null or full_name like %:fname%) " +
	                    "and (:email is null or email like %:email%) " +
	                    "and (:status is null or status = :status) " 
	            , nativeQuery = true)
	    Page<Business> selectParams(
	            @Param("uname") String uname,
	            @Param("fname") String fname,
	            @Param("email") String email,
	            @Param("status") Integer status,
	            Pageable pageable);
   Business findByUsername(String username);
   
   @Query(value ="delete from business as b where b.create_time < :create_time" ,nativeQuery=true)
   int deleteAccount(@Param("create_time") Date create_time); 
}
