<%@ page contentType="text/html; charset=UTF-8"%>
<div class="panel panel-primary setup-content" id="step-9">
	<div class="panel-heading">
		<h3 class="panel-title"><spring:message code="ekycdn.de_nghi_mo_tai_khoan" /></h3>
	</div>
	<div class="panel-body">
		<div class="form-group text-center">
			<div style=";margin: auto;" class="col-sm-12">
	 			<div id="giayMoTaiKhoan" style="text-align: left;">
					<iframe id="openFormBase64" style="width: 100%; height: 600px; border: 0;"></iframe>
				</div>
				<div class="row">
					<div style=";margin: auto;" class="col-sm-2"></div>
					<div style="margin: auto;margin-top: 20px;" class="col-sm-8">
						<div class="row">
							<div class="form-group col-sm-6" style="text-align: left;" >
								<button id="kyDoanhNghiepDangKy" class="btn btn-primary nextBtn" type="button" onclick="validateStepXemFileKy(this)" data-loading-text="<i class='fa fa-circle-o-notch fa-spin'></i> <spring:message code="ekycdn.dang_xy_ly" />" id="uploadStep7"><spring:message code="ekycdn.cong_ty_ky" /></button>
							</div>
							<textarea id="contentFilePdfDangKy" style="display: none;"></textarea>
						</div>
					</div>
					<div class="form-group" style="width: 500px;margin: auto;display: none;" id="kyDoanhNghiep">
						<textarea style="display: none;" id="base64Cert" name="base64Cert"></textarea>
						<button class="btn btn-primary nextBtn pull-right" type="button" onclick="validateStepXemFileKy(this)" data-loading-text="<i class='fa fa-circle-o-notch fa-spin'></i> <spring:message code="ekycdn.dang_xy_ly" />"><spring:message code="ekycdn.ky_so" /></button>
						<input class="btn btn-info nextBtn pull-right" onClick="showCertListDialog()" type="button" value="<spring:message code="ekycdn.lay_chung_thu_so" />" style="margin-right: 10px;">
					</div>
				</div>		
			</div>
		</div>
	</div>
</div>
<script type="text/javascript">
var contentType = 'application/pdf';
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


var blob1 = b64toBlob('${openFormBase64}', contentType);
var blobUrl1 = URL.createObjectURL(blob1);
$("#openFormBase64").attr("src", blobUrl1);	

function goiHamalert() {
	toastr.info ('<spring:message code="ekycdn.click_tiep" /> <spring:message code="ekycdn.cong_ty_ky" />');
}

function validateStepXemFileKy(obj) {
	var base64Cert = $("#base64Cert").val();
	if(base64Cert != "") {
		$(obj).button('loading');
		
		fileContent = '${openFormBase64}';
		$("#contentFilePdf").html(fileContent);
		sign(obj);	
		$(obj).button('reset');;
	} else {
		showCertListDialog();
//			toastr.warning("Vui lòng lấy chứng thư số trước khi ký.")
	}
	
	
}
var noiDungFileDangKy;
var noiDungFileKy;
function luuNoiDungKy(base64, obj) {
	if(base64 != "") {
		noiDungFileDangKy = base64;
		$("#contentFilePdfDangKy").html(base64)
	
		var contentType = 'application/pdf';
		
		var blob = b64toBlob(base64, contentType);
		var blobUrl = URL.createObjectURL(blob);
		toastr.info ('<spring:message code="ekycdn.ky_thanh_cong" />');
		$("#base64FilePdf2").attr("src", blobUrl);	
		$("#kyDoanhNghiep").hide();
		nextStep(obj);
		uploadDuLieuLenHeThong();
	} else {
		toastr.error("Lỗi ký số");
	}
}
function uploadDuLieuLenHeThong() {
	var data = {
		"fileKy": 	noiDungFileKy,
		"fileDangKy": noiDungFileDangKy,
		"token": '${tokens}'
	};
	$.ajax({
		url:'/fisbank/ekyc-enterprise/luu-tru-file',
	    data: JSON.stringify(data),
	    type: 'POST',
	    processData: false,
	    contentType: 'application/json'
	}).done(function(data) {
		console.log(data);
	
	}).fail(function(data) {
		toastr.error("Lỗi kiểm tra thông tin");
	});
}
</script>