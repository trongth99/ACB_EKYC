<%@ page contentType="text/html; charset=UTF-8"%>
<div class="row clearfix">
	<h3 style="clear: both;">
		<spring:message code="ekycdn.nguoi_dai_dien_phap_luat" />
		<c:if test="${ekycDoanhNghiep.nguoiDaiDienPhapLuatCheck eq 'update' }">
			( <b style="color: blue;">Đã cập nhật thông tin</b> )
		</c:if>
	</h3>
	<hr style="color: #CCC;border-top: 1px solid #CCC;"/>
	<div class="col-sm-6">
		<div class="form-group  has-feedback">
			<label class="control-label"><spring:message code="so_dien_thoai" /></label>
			<input type="text" class="form-control form-control-sm" readonly="readonly" value="<c:out value="${ekycDoanhNghiep.soDienThoai }"/>" id="hoVaTenpl"/>
		</div>
	</div>
	<div class="col-sm-6">
		<div class="form-group  has-feedback">
			<label class="control-label"><spring:message code="email" /></label>
			<input type="text" class="form-control form-control-sm" readonly="readonly" value="<c:out value="${ekycDoanhNghiep.emails }"/>" id="soGiayTopl"/>
		</div>
	</div>
	<div class="col-sm-6">
		<div class="form-group  has-feedback">
			<label class="control-label"><spring:message code="ho_va_ten" /></label>
			<input type="text" class="form-control form-control-sm" readonly="readonly" value="<c:out value="${ekycDoanhNghiep.hoVaTen }"/>" id="hoVaTenpl"/>
		</div>
	</div>
	<div class="col-sm-6">
		<div class="form-group  has-feedback">
			<label class="control-label"><spring:message code="e_so_giay_to" /></label>
			<input type="text" class="form-control form-control-sm" readonly="readonly" value="<c:out value="${ekycDoanhNghiep.soGiayTo }"/>" id="soGiayTopl"/>
		</div>
	</div>
	<div class="col-sm-6">
		<div class="form-group  has-feedback">
			<label class="control-label"><spring:message code="ngay_cap" /></label>
			<input type="text" class="form-control form-control-sm" readonly="readonly" value="<c:out value="${ekycDoanhNghiep.ngayCap }"/>" id="ngayCappl"/>
		</div>
	</div>
	<div class="col-sm-6">
		<div class="form-group  has-feedback">
			<label class="control-label"><spring:message code="e_noi_cap" /></label>
			<input type="text" class="form-control form-control-sm" readonly="readonly" value="<c:out value="${ekycDoanhNghiep.noiCap }"/>" id="noiCappl"/>
		</div>
	</div>
	
	<div class="row">
		<div class="col-sm-6">
			<div class="form-group  has-feedback">
				<label class="control-label">
					<spring:message code="anh_mat_truoc" />
				</label>
				<img src="${anhMatTruoc }" style="width: 100%;" />
			</div>
		</div>
		<div class="col-sm-6">
			<div class="form-group  has-feedback">
				<label class="control-label">
					<spring:message code="anh_mat_sau" />
				</label>
				<img src="${anhMatSau }" style="width: 100%;" />
			</div>
		</div>
	</div>
	<div class="row">
		<div class="col-sm-12">
			<div class="form-group  has-feedback">
				<label class="control-label">
					<spring:message code="anh_ca_nhan" />
				</label>
				<img src="${anhCaNhan }" style="width: 100%;" />
			</div>
		</div>
	</div>
	<h3 style="clear: both;">Thư xác nhận</h3>
	<hr style="color: #CCC;border-top: 1px solid #CCC;clear: both;"/>
	<div class="col-sm-12">
				<iframe id="base64FileKy"
					style="width: 100%; height: 400px; border: 0;"></iframe>
			</div>
			
	<h3 style="clear: both;">Form đăng ký</h3>
	<hr style="color: #CCC;border-top: 1px solid #CCC;clear: both;"/>
	<div class="col-sm-12">
				<iframe id="base64FileDangKy"
					style="width: 100%; height: 400px; border: 0;"></iframe>
			</div>
</div>
<script type="text/javascript">

var b64toBlob1 = (b64Data, contentType='', sliceSize=512) => {
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

var contentType1 = 'application/pdf';
var b64Data1 = '${fileKy }';

var blob1 = b64toBlob1(b64Data1, contentType1);
var blobUrl1 = URL.createObjectURL(blob1);
$(document).ready(function(){
	$("#base64FileKy").attr("src", blobUrl1);	
})

var fileDangKy = '${fileDangKy }';

var blobfileDangKy = b64toBlob1(fileDangKy, contentType1);
var blobUrlfileDangKy = URL.createObjectURL(blobfileDangKy);
$(document).ready(function(){
	$("#base64FileKy").attr("src", blobUrl1);	
	$("#base64FileDangKy").attr("src", blobUrlfileDangKy);
})

</script>