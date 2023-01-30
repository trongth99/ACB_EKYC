<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="springForm"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%@include file="../../layout/js.jsp"%>
<style type="text/css">
.help-block{
color: #dc3545;
}
</style>

<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
	<div class="modal-header">
		<h2>Xem thông tin khách hàng</h2>
	</div>
	<div class="modal-body">
		<div class="row clearfix">
			<div class="col-sm-6">
				<div class="form-group  has-feedback">
					<label class="control-label">Họ và tên</label>
					<input type="text" class="form-control form-control-sm" readonly="readonly" value="<c:out value="${formInfo.hoVaTen }"/>" />
				</div>
			</div>
			<div class="col-sm-6">
				<div class="form-group  has-feedback">
					<label class="control-label">Số điện thoại</label>
					<input type="text" class="form-control form-control-sm" readonly="readonly" value="<c:out value="${formInfo.soDienThoai }"/>" />
				</div>
			</div>
			<div class="col-sm-6">
				<div class="form-group  has-feedback">
					<label class="control-label">Số cmt</label>
					<input type="text" class="form-control form-control-sm" readonly="readonly" value="<c:out value="${formInfo.soCmt }"/>" />
				</div>
			</div>
			<div class="col-sm-6">
				<div class="form-group  has-feedback">
					<label class="control-label">Giới tính</label>
					<input type="text" class="form-control form-control-sm" readonly="readonly" value="<c:out value="${formInfo.gioiTinh }"/>" />
				</div>
			</div>
			<div class="col-sm-6">
				<div class="form-group  has-feedback">
					<label class="control-label">Năm sinh</label>
					<input type="text" class="form-control form-control-sm" readonly="readonly" value="<c:out value="${ocr.namSinh }"/>" />
				</div>
			</div>
			<div class="col-sm-6 clearfix">
				<div class="form-group  has-feedback">
					<label class="control-label">Quê quán</label>
					<input type="text" class="form-control form-control-sm" readonly="readonly" value="<c:out value="${ocr.queQuan }"/>" />
				</div>
			</div>
			<div class="col-sm-6">
				<div class="form-group  has-feedback">
					<label class="control-label">Nơi cấp</label>
					<input type="text" class="form-control form-control-sm" readonly="readonly" value="<c:out value="${ocr.noiCap }"/>" />
				</div>
			</div>
			<div class="col-sm-6">
				<div class="form-group  has-feedback">
					<label class="control-label">Nơi trú</label>
					<input type="text" class="form-control form-control-sm" readonly="readonly" value="<c:out value="${ocr.noiTru }"/>" />
				</div>
			</div>
			<div class="col-sm-6">
				<div class="form-group  has-feedback">
					<label class="control-label">Điểm so sánh khuôn mặt</label>
					<input type="text" class="form-control form-control-sm" readonly="readonly" value="<c:out value="${ekycKyso.diemEkyc }"/>" />
				</div>
			</div>
			<div class="col-sm-6">
				<div class="form-group  has-feedback">
					<label class="control-label">UUID ký số</label>
					<input type="text" class="form-control form-control-sm" readonly="readonly" value="<c:out value="${ekycKyso.uuidKySo }"/>" />
				</div>
			</div>
			<div class="col-sm-6">
				<div class="form-group  has-feedback">
					<label class="control-label">BillCode ký số</label>
					<input type="text" class="form-control form-control-sm" readonly="readonly" value="<c:out value="${ekycKyso.billCodeKySo }"/>" />
				</div>
			</div>
			<div class="col-sm-12">
				<div class="form-group  has-feedback">
					<label class="control-label">Lỗi eKYC</label><br/>
					<c:forTokens items="${ekycKyso.thongBao }" delims="|" var="mySplit" varStatus="st">
					   	- <c:out value="${mySplit}"/> <br/>
					</c:forTokens>
				</div>
			</div>
			<div class="col-sm-6">
				<div class="form-group  has-feedback">
					<label class="control-label">Ảnh mặt trước</label>
					<img src="${anhMatTruoc }" style="width: 100%;"/>
				</div>
			</div>
			<div class="col-sm-6">
				<div class="form-group  has-feedback">
					<label class="control-label">Ảnh mặt sau</label>
					<img src="${anhMatSau }" style="width: 100%;"/>
				</div>
			</div>
			<div style="clear: both;"></div>
			<div class="col-sm-6">
				<div class="form-group  has-feedback">
					<label class="control-label">Ảnh cá nhân</label>
					<img src="${anhCaNhan }" style="width: 100%;"/>
				</div>
			</div>
			<div style="clear: both;"></div>
			<hr/>
			<c:if test="${not empty anhVideo }">
			<h3 style="margin-left: 13px;">Ảnh video</h3>
				<c:forEach items="${anhVideo}" var="item" varStatus="status">
					<div class="col-sm-4">
						<div class="form-group  has-feedback">
							<img src="data:image/jpeg;base64,${item }" style="width: 100%;"/>
						</div>
					</div>
				</c:forEach>
			</c:if>
			<div style="clear: both;"></div>
			<c:if test="${not empty file }">
				<h3 style="margin-left: 13px;">Hợp đồng vay</h3>
				<div class="col-sm-12">
					<iframe id="base64File" style="width: 100%; height: 400px; border: 0;"></iframe>
				</div>
			</c:if>
			<div style="clear: both;"></div>
			<c:if test="${not empty anhTinNhan }">
				<hr/>
				<h3 style="margin-left: 13px;">Đăng ký cấp chứng thư số</h3>
				<div class="col-sm-12">
					<iframe id="base64FileAnhTinNhan" style="width: 100%; height: 400px; border: 0;"></iframe>
				</div>
			</c:if>
			<div style="clear: both;"></div>
			<c:if test="${not empty fileHdBaoHiem }">
				<hr/>
				<h3 style="margin-left: 13px;">Hợp đồng bảo hiểm</h3>
				<div class="col-sm-12">
					<iframe id="base64FileBaoHiem" style="width: 100%; height: 400px; border: 0;"></iframe>
				</div>
			</c:if>
			
		</div>
		<div style="clear: both;"></div>
		<br/><br/>
		<div class="col-md-12 mb-0 text-right">
			<button class="btn btn-danger btn-sm" data-dismiss="modal">
				<i class="fa fa-times"></i>
				<span><spring:message code="thoat" /></span>
			</button>
		</div>
		<br/><br/>
	</div>
</div>
<script type="text/javascript">
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
var b64Data = '${file }';
var blob = b64toBlob(b64Data, contentType);
var blobUrl = URL.createObjectURL(blob);

var b64DataBaoHiem = '${fileHdBaoHiem }';
var blobBaoHiem = b64toBlob(b64DataBaoHiem, contentType);
var blobUrlBaoHiem = URL.createObjectURL(blobBaoHiem);

var b64DataanhTinNhan = '${anhTinNhan }';
var blobanhTinNhan = b64toBlob(b64DataanhTinNhan, contentType);
var blobUrlanhTinNhan = URL.createObjectURL(blobanhTinNhan);

$(document).ready(function(){
	$("#base64File").attr("src", blobUrl);
	$("#base64FileAnhTinNhan").attr("src", blobUrlanhTinNhan);
	$("#base64FileBaoHiem").attr("src", blobUrlBaoHiem);	
});
</script>
<%@include file="../../layout/footerAjax.jsp"%>
