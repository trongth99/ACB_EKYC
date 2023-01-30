<%@ page contentType="text/html; charset=UTF-8"%>
<div class="row clearfix">
	<div class="col-sm-6">
		<div class="form-group  has-feedback">
			<label class="control-label"><spring:message code="ekycdn.ma_so_doanh_nghiep" /></label>
			<input type="text" class="form-control form-control-sm" readonly="readonly" id="maSoDoanhNghiep" name="maSoDoanhNghiep" />
		</div>
	</div>
	<div class="col-sm-6">
		<div class="form-group  has-feedback">
			<label class="control-label"><spring:message code="ekycdn.ten_doanh_nghiep" /></label>
			<input type="text" class="form-control form-control-sm" readonly="readonly" id="tenDoanhNghiep" name="tenDoanhNghiep" />
		</div>
	</div>
	<div class="col-sm-6">
		<div class="form-group  has-feedback">
			<label class="control-label"><spring:message code="ekycdn.loai_hinh_doanh_nghiep" /></label>
			<input type="text" class="form-control form-control-sm" readonly="readonly" id="loaiHinhDoanhNghiep" name="loaiHinhDoanhNghiep" />
		</div>
	</div>
	<div class="col-sm-6">
		<div class="form-group  has-feedback">
			<label class="control-label"><spring:message code="ekycdn.dia_chi_tru_so_chinh" /></label>
			<input type="text" class="form-control form-control-sm" readonly="readonly" id="diaChi" name="diaChi" />
		</div>
	</div>
	<div class="col-sm-6 clearfix">
		<div class="form-group  has-feedback">
			<label class="control-label"><spring:message code="ekycdn.dien_thoai" /></label>
			<input type="text" class="form-control form-control-sm" readonly="readonly" id="dienThoai" name="dienThoai" />
		</div>
	</div>
	<div class="col-sm-6">
		<div class="form-group  has-feedback">
			<label class="control-label">Fax</label>
			<input type="text" class="form-control form-control-sm" readonly="readonly" id="fax" name="fax" />
		</div>
	</div>
	<div class="col-sm-6">
		<div class="form-group  has-feedback">
			<label class="control-label">Email</label>
			<input type="text" class="form-control form-control-sm" readonly="readonly" id="email" name="email" />
		</div>
	</div>
	<div class="col-sm-6">
		<div class="form-group  has-feedback">
			<label class="control-label">Web site</label>
			<input type="text" class="form-control form-control-sm" readonly="readonly" id="website" name="website" />
		</div>
	</div>
	<div class="col-sm-6">
		<div class="form-group  has-feedback">
			<label class="control-label"><spring:message code="ekycdn.von_dieu_le" /></label>
			<input type="text" class="form-control form-control-sm" readonly="readonly" id="vonDieuLe" name="vonDieuLe" />
		</div>
	</div>
	<div class="col-sm-6">
		<div class="form-group  has-feedback">
			<label class="control-label"><spring:message code="ekycdn.lan_dang_ky" /></label>
			<input type="text" class="form-control form-control-sm" readonly="readonly" id="lanDangKy" name="lanDangKy" />
		</div>
	</div>
	<div class="col-sm-6">
		<div class="form-group  has-feedback">
			<label class="control-label"><spring:message code="ekycdn.ngay_dang_ky" /></label>
			<input type="text" class="form-control form-control-sm" readonly="readonly" id="ngayDangKy" name="ngayDangKy" />
		</div>
	</div>
	<div class="col-sm-6">
		<div class="form-group  has-feedback">
			<label class="control-label"><spring:message code="ekycdn.quy_mo_doanh_nghiep" /></label>
			<input type="text" class="form-control form-control-sm" readonly="readonly" id="quyMoDoanhNghiep" name="quyMoDoanhNghiep" />
		</div>
	</div>
	<div class="col-sm-6">
		<div class="form-group  has-feedback">
			<label class="control-label"><spring:message code="ekycdn.trang_thai" /></label>
			<input type="text" class="form-control form-control-sm" readonly="readonly" id="trangThai" name="trangThai" />
		</div>
	</div>
	<div style="clear: both;"></div>
	<h3 style="clear: both;margin-top: 10px;"><spring:message code="ekycdn.thong_tin_chu_ky_so" /></h3>
	<hr style="color: #CCC;border-top: 1px solid #CCC;margin: 0"/>
	<div id='thongTinChuKySo'>
	
	</div>
	
	<hr style="color: #CCC;border-top: 1px solid #CCC;clear: both;"/>
	<div class="col-sm-12">
				<iframe id="base64File"
					style="width: 100%; height: 400px; border: 0;"></iframe>
			</div>
			
	<hr style="color: #CCC;border-top: 1px solid #CCC;clear: both;"/>
	<div class="col-sm-12">
				<iframe id="base64FileQuyetDinhBoNhiemKtt"
					style="width: 100%; height: 400px; border: 0;"></iframe>
			</div>
</div>
<script type="text/javascript">
var successIcon = '<span class="glyphicon glyphicon-ok form-control-feedback"></span>';
var successClass = 'is-valid';
var errorIcon = '<span class="glyphicon glyphicon-remove form-control-feedback"></span>';
var errorClass = 'is-invalid';
$(document).ready(function(){
	$("#maSoDoanhNghiep").val('${ekycDoanhNghiep.maSoDoanhNghiep}');
	addCheckClass('${ekycDoanhNghiep.maSoDoanhNghiep}', '${ekycDoanhNghiep.maSoDoanhNghiepCp}', "string", "maSoDoanhNghiep");
	
	$("#tenDoanhNghiep").val('${ekycDoanhNghiep.tenDoanhNghiep}');
	addCheckClass('${ekycDoanhNghiep.tenDoanhNghiep}', '${ekycDoanhNghiep.tenDoanhNghiepCp}', "string", "tenDoanhNghiep");
	
	$("#loaiHinhDoanhNghiep").val('${ekycDoanhNghiep.loaiHinhDoanhNghiep}');
	addCheckClass('${ekycDoanhNghiep.loaiHinhDoanhNghiep}', '${ekycDoanhNghiep.loaiHinhDoanhNghiepCp}', "string", "loaiHinhDoanhNghiep");
	
	$("#diaChi").val('${ekycDoanhNghiep.diaChi}');
	addCheckClass('${ekycDoanhNghiep.diaChi}', '${ekycDoanhNghiep.diaChiCp}', "string", "diaChi");
	
	$("#dienThoai").val('${ekycDoanhNghiep.dienThoai}');
	$("#fax").val('${ekycDoanhNghiep.fax}');
	$("#email").val('${ekycDoanhNghiep.email}');
	$("#website").val('${ekycDoanhNghiep.website}');
	
	var von = numberWithCommas('${ekycDoanhNghiep.vonDieuLe}');
	var vonCp = numberWithCommas('${ekycDoanhNghiep.vonDieuLeCp}');
	$("#vonDieuLe").val(von);
	addCheckClass(von, vonCp, "string", "vonDieuLe");
	
	$("#lanDangKy").val('${ekycDoanhNghiep.lanDangKy}');
	addCheckClass('${ekycDoanhNghiep.lanDangKy}', '${ekycDoanhNghiep.lanDangKyCp}', "string", "lanDangKy");
	
	$("#ngayDangKy").val('${ekycDoanhNghiep.ngayDangKy}');
	addCheckClass('${ekycDoanhNghiep.ngayDangKy}', '${ekycDoanhNghiep.ngayDangKyCp}', "string", "ngayDangKy");
	
	$("#quyMoDoanhNghiep").val('${ekycDoanhNghiep.quyMoDoanhNghiep}');
	$("#trangThai").val('${ekycDoanhNghiep.trangThai}');
	
	var thongTinChuKy = JSON.parse('${ekycDoanhNghiep.thongTinChuKy}');
	var table = "<table class='table'>";
	for (x in thongTinChuKy) {
		for (y in thongTinChuKy[x]) {
			var title = y;
			if(y=='error') title = '<spring:message code="ekycdn.thong_bao" />';
			if(y=='signature') title = '<spring:message code="ekycdn.chu_ky" />';
			if(y=='subject') title = '<spring:message code="ekycdn.ten" />';
			if(y=='signtime') title = '<spring:message code="ekycdn.thoi_gian" />';
			if(y=='certificate') title = '<spring:message code="ekycdn.chung_chi" />';
			if(y=='from') title = '<spring:message code="ekycdn.tu_ngay" />';
			if(y=='to') title = '<spring:message code="ekycdn.den_ngay" />';
			if(y=='validity') title = '<spring:message code="ekycdn.hieu_luc" />';
			if(y=='issuer') title = '<spring:message code="ekycdn.nguoi_phat_hanh" />';
			if(y=='mst') title = '<spring:message code="ekycdn.mst" />';
			if(thongTinChuKy[x][y] == "Signature is not exists") thongTinChuKy[x][y] = '<spring:message code="ekycdn.khong_co_chu_ky_so" />';
			table += "<tr><td>"+title+"</td><td>"+thongTinChuKy[x][y]+"</td></tr>";
		}
	}
	table += "</table>";
	
	$("#thongTinChuKySo").html(table);
});

function addCheckClass(value, valueCheck, type, id) {
	if(type == "string") {
		if((valueCheck != null && value != null && value.toLowerCase() == valueCheck.toLowerCase())) {
			$("#"+id).addClass(successClass);
		} else if (valueCheck == "" || valueCheck == null || value == "" || value == null) {
			
		} else {
			$("#"+id).addClass(errorClass);
			$("#"+id).parent().append('<span class="help-block">'+valueCheck+'</span>');
		}
	}
}
function numberWithCommas(x) {
	if(x && x.trim()!="")
    	return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
	
	return "";
}

var b64toBlob = (b64Data, contentType='', sliceSize=512) => {
	var byteCharacters = atob(b64Data);
	var byteArrays = [];
	
	for (let offset = 0; offset < byteCharacters.length; offset += sliceSize) {
		var slice = byteCharacters.slice(offset, offset + sliceSize);
		
		var byteNumbers = new Array(slice.length);
		for (let i = 0; i < slice.length; i++) {
			byteNumbers[i] = slice.charCodeAt(i);
		}
		
		var byteArray = new Uint8Array(byteNumbers);
		byteArrays.push(byteArray);
	}
	
	var blob = new Blob(byteArrays, {type: contentType});
	return blob;
}

var contentType = 'application/pdf';
var b64Data = '${fileDangKyKinhDoanh }';

var blob = b64toBlob(b64Data, contentType);
var blobUrl = URL.createObjectURL(blob);

var fileQuyetDinhBoNhiemKtt = '${fileQuyetDinhBoNhiemKtt }';

var blobQuyetDinhBoNhiemKtt = b64toBlob(fileQuyetDinhBoNhiemKtt, contentType);
var blobUrlQuyetDinhBoNhiemKtt = URL.createObjectURL(blobQuyetDinhBoNhiemKtt);
$(document).ready(function(){
	$("#base64File").attr("src", blobUrl);
	$("#base64FileQuyetDinhBoNhiemKtt").attr("src", blobUrlQuyetDinhBoNhiemKtt);	
})
</script>