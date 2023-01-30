package fis.com.vn.table;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Nationalized;

import lombok.Data;

@Entity
@Table(name = "ekyc_kyso_thong_ke")
@Data
public class ThongKeEkycKyso {
	@Id
	long id;
	
	@Column(name = "trangThaiEkyc")
	@Nationalized
	String trangThaiEkyc;
	
	@Column(name = "tongSo")
	int tongSo;
}
