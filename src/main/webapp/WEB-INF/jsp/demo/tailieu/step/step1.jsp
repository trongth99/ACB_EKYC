<%@ page contentType="text/html; charset=UTF-8"%>
<div class="panel panel-primary setup-content" id="step-1">
	<div class="panel-heading">
		<h3 class="panel-title">Nhập thông tin khách hàng</h3>
	</div>
	<div class="panel-body">
		<div class="row">
			<div class="col-sm-6">
				<div class="form-group  has-feedback">
					<label class="control-label"><spring:message code="so_cmt" /></label>
					<input  type="text"  class="form-control input-sm" id="soCmt" name="soCmt" value='<c:out value="${ekycKyso.soCmt }"/>'/>
				</div>
			</div>
			<div class="col-sm-6">
				<div class="form-group  has-feedback">
					<label class="control-label"><spring:message code="ho_va_ten" /></label>
					<input  type="text"  class="form-control input-sm" id="hoVaTen" name="hoVaTen" value='<c:out value="${ekycKyso.hoVaTen }"/>'/>
				</div>
			</div>
		</div>	
		<div class="row">
			<div class="col-sm-6">
				<div class="form-group  has-feedback">
					<label class="control-label"><spring:message code="so_dien_thoai" /></label>
					<input  type="text"  class="form-control input-sm" id="soDienThoai" name="soDienThoai" value='<c:out value="${ekycKyso.soDienThoai }"/>'/>
				</div>
			</div>
			<div class="col-sm-6">
				<div class="form-group  has-feedback">
					<label class="control-label"><spring:message code="email" /></label>
					<input  type="text"  class="form-control input-sm" id="email" name="email" value='<c:out value="${ekycKyso.email }"/>'/>
				</div>
			</div>
		</div>	
		<div class="row">
			<div class="col-sm-6">
				<div class="form-group  has-feedback">
					<label class="control-label">Số hợp đồng</label>
					<input  type="text"  class="form-control input-sm" id="soHopDong" name="soHopDong" value='<c:out value="${ekycKyso.soTaiKhoan }"/>'/>
				</div>
			</div>
			<div class="col-sm-6">
				<div class="form-group  has-feedback">
					<label class="control-label">Khu vực</label><br/>
					<select class="form-control valid input-sm" name="khuVuc" id="khuVuc">
						<option value="Hà Nội" ${ekycKyso.khuVuc eq 'Hà Nội' ? 'selected': ''}>Hà Nội</option>
						<option value="Hồ Chí Minh" ${ekycKyso.khuVuc eq 'Hồ Chí Minh' ? 'selected': ''}>Hồ Chí Minh</option>
                   		<option value="An Giang" ${ekycKyso.khuVuc eq 'An Giang' ? 'selected': ''}>An Giang</option>
                   		<option value="Bà Rịa - Vũng Tàu" ${ekycKyso.khuVuc eq 'Bà Rịa - Vũng Tàu' ? 'selected': ''}>Bà Rịa - Vũng Tàu</option>
                   		<option value="Bình Dương" ${ekycKyso.khuVuc eq 'Bình Dương' ? 'selected': ''}>Bình Dương</option>
                   		<option value="Bình Phước" ${ekycKyso.khuVuc eq 'Bình Phước' ? 'selected': ''}>Bình Phước</option>
                   		<option value="Bình Thuận" ${ekycKyso.khuVuc eq 'Bình Thuận' ? 'selected': ''}>Bình Thuận</option>
                   		<option value="Bình Định" ${ekycKyso.khuVuc eq 'Bình Định' ? 'selected': ''}>Bình Định</option>
                   		<option value="Bạc Liêu" ${ekycKyso.khuVuc eq 'Bạc Liêu' ? 'selected': ''}>Bạc Liêu</option>
                   		<option value="Bắc Giang" ${ekycKyso.khuVuc eq 'Bắc Giang' ? 'selected': ''}>Bắc Giang</option>
                   		<option value="Bắc Kạn" ${ekycKyso.khuVuc eq 'Bắc Kạn' ? 'selected': ''}>Bắc Kạn</option>
                   		<option value="Bắc Ninh" ${ekycKyso.khuVuc eq 'Bắc Ninh' ? 'selected': ''}>Bắc Ninh</option>
                   		<option value="Bến Tre" ${ekycKyso.khuVuc eq 'Bến Tre' ? 'selected': ''}>Bến Tre</option>
                   		<option value="Cao Bằng" ${ekycKyso.khuVuc eq 'Cao Bằng' ? 'selected': ''}>Cao Bằng</option>
                   		<option value="Cà Mau" ${ekycKyso.khuVuc eq 'Cà Mau' ? 'selected': ''}>Cà Mau</option>
                   		<option value="Cần Thơ" ${ekycKyso.khuVuc eq 'Cần Thơ' ? 'selected': ''}>Cần Thơ</option>
                   		<option value="Gia Lai" ${ekycKyso.khuVuc eq 'Gia Lai' ? 'selected': ''}>Gia Lai</option>
                   		<option value="Hải Phòng" ${ekycKyso.khuVuc eq 'Hải Phòng' ? 'selected': ''}>Hải Phòng</option>
                   		<option value="Hoà Bình" ${ekycKyso.khuVuc eq 'Hoà Bình' ? 'selected': ''}>Hoà Bình</option>
                   		<option value="Hà Giang" ${ekycKyso.khuVuc eq 'Hà Giang' ? 'selected': ''}>Hà Giang</option>
                   		<option value="Hà Nam" ${ekycKyso.khuVuc eq 'Hà Nam' ? 'selected': ''}>Hà Nam</option>
                   		<option value="Hà Tĩnh" ${ekycKyso.khuVuc eq 'Hà Tĩnh' ? 'selected': ''}>Hà Tĩnh</option>
                   		<option value="Hưng Yên" ${ekycKyso.khuVuc eq 'Hưng Yên' ? 'selected': ''}>Hưng Yên</option>
                   		<option value="Hải Dương" ${ekycKyso.khuVuc eq 'Hải Dương' ? 'selected': ''}>Hải Dương</option>
                   		<option value="Hậu Giang" ${ekycKyso.khuVuc eq 'Hậu Giang' ? 'selected': ''}>Hậu Giang</option>
                   		<option value="Khánh Hòa" ${ekycKyso.khuVuc eq 'Khánh Hòa' ? 'selected': ''}>Khánh Hòa</option>
                   		<option value="Kiên Giang" ${ekycKyso.khuVuc eq 'Kiên Giang' ? 'selected': ''}>Kiên Giang</option>
                   		<option value="Kon Tum" ${ekycKyso.khuVuc eq 'Kon Tum' ? 'selected': ''}>Kon Tum</option>
                   		<option value="Lai Châu" ${ekycKyso.khuVuc eq 'Lai Châu' ? 'selected': ''}>Lai Châu</option>
                   		<option value="Long An" ${ekycKyso.khuVuc eq 'Long An' ? 'selected': ''}>Long An</option>
                   		<option value="Lào Cai" ${ekycKyso.khuVuc eq 'Lào Cai' ? 'selected': ''}>Lào Cai</option>
                   		<option value="Lâm Đồng" ${ekycKyso.khuVuc eq 'Lâm Đồng' ? 'selected': ''}>Lâm Đồng</option>
                   		<option value="Lạng Sơn" ${ekycKyso.khuVuc eq 'Lạng Sơn' ? 'selected': ''}>Lạng Sơn</option>
                   		<option value="Nam Định" ${ekycKyso.khuVuc eq 'Nam Định' ? 'selected': ''}>Nam Định</option>
                   		<option value="Nghệ An" ${ekycKyso.khuVuc eq 'Nghệ An' ? 'selected': ''}>Nghệ An</option>
                   		<option value="Ninh Bình" ${ekycKyso.khuVuc eq 'Ninh Bình' ? 'selected': ''}>Ninh Bình</option>
                   		<option value="Ninh Thuận" ${ekycKyso.khuVuc eq 'Ninh Thuận' ? 'selected': ''}>Ninh Thuận</option>
                   		<option value="Phú Thọ" ${ekycKyso.khuVuc eq 'Phú Thọ' ? 'selected': ''}>Phú Thọ</option>
                   		<option value="Phú Yên" ${ekycKyso.khuVuc eq 'Phú Yên' ? 'selected': ''}>Phú Yên</option>
                   		<option value="Quảng Bình" ${ekycKyso.khuVuc eq 'Quảng Bình' ? 'selected': ''}>Quảng Bình</option>
                   		<option value="Quảng Nam" ${ekycKyso.khuVuc eq 'Quảng Nam' ? 'selected': ''}>Quảng Nam</option>
                   		<option value="Quảng Ngãi" ${ekycKyso.khuVuc eq 'Quảng Ngãi' ? 'selected': ''}>Quảng Ngãi</option>
                   		<option value="Quảng Ninh" ${ekycKyso.khuVuc eq 'Quảng Ninh' ? 'selected': ''}>Quảng Ninh</option>
                   		<option value="Quảng Trị" ${ekycKyso.khuVuc eq 'Quảng Trị' ? 'selected': ''}>Quảng Trị</option>
                   		<option value="Sóc Trăng" ${ekycKyso.khuVuc eq 'Sóc Trăng' ? 'selected': ''}>Sóc Trăng</option>
                   		<option value="Sơn La" ${ekycKyso.khuVuc eq 'Sơn La' ? 'selected': ''}>Sơn La</option>
                   		<option value="Thanh Hóa" ${ekycKyso.khuVuc eq 'Thanh Hóa' ? 'selected': ''}>Thanh Hóa</option>
                   		<option value="Thái Bình" ${ekycKyso.khuVuc eq 'Thái Bình' ? 'selected': ''}>Thái Bình</option>
                   		<option value="Thái Nguyên" ${ekycKyso.khuVuc eq 'Thái Nguyên' ? 'selected': ''}>Thái Nguyên</option>
                   		<option value="Thừa Thiên Huế" ${ekycKyso.khuVuc eq 'Thừa Thiên Huế' ? 'selected': ''}>Thừa Thiên Huế</option>
                   		<option value="Tiền Giang" ${ekycKyso.khuVuc eq 'Tiền Giang' ? 'selected': ''}>Tiền Giang</option>
                   		<option value="Trà Vinh" ${ekycKyso.khuVuc eq 'Trà Vinh' ? 'selected': ''}>Trà Vinh</option>
                   		<option value="Tuyên Quang" ${ekycKyso.khuVuc eq 'Tuyên Quang' ? 'selected': ''}>Tuyên Quang</option>
                   		<option value="Tây Ninh" ${ekycKyso.khuVuc eq 'Tây Ninh' ? 'selected': ''}>Tây Ninh</option>
                   		<option value="Vĩnh Long" ${ekycKyso.khuVuc eq 'Vĩnh Long' ? 'selected': ''}>Vĩnh Long</option>
                   		<option value="Vĩnh Phúc" ${ekycKyso.khuVuc eq 'Vĩnh Phúc' ? 'selected': ''}>Vĩnh Phúc</option>
                   		<option value="Yên Bái" ${ekycKyso.khuVuc eq 'Yên Bái' ? 'selected': ''}>Yên Bái</option>
                   		<option value="Đà Nẵng" ${ekycKyso.khuVuc eq 'Đà Nẵng' ? 'selected': ''}>Đà Nẵng</option>
                   		<option value="Điện Biên" ${ekycKyso.khuVuc eq 'Điện Biên' ? 'selected': ''}>Điện Biên</option>
                   		<option value="Đắk Lắk" ${ekycKyso.khuVuc eq 'Đắk Lắk' ? 'selected': ''}>Đắk Lắk</option>
                   		<option value="Đắk Nông" ${ekycKyso.khuVuc eq 'Đắk Nông' ? 'selected': ''}>Đắk Nông</option>
                   		<option value="Đồng Nai" ${ekycKyso.khuVuc eq 'Đồng Nai' ? 'selected': ''}>Đồng Nai</option>
                   		<option value="Đồng Tháp" ${ekycKyso.khuVuc eq 'Đồng Tháp' ? 'selected': ''}>Đồng Tháp</option>
					</select>
				</div>
			</div>
			<div class="col-sm-6">
			</div>
		</div>	
		<div class="row">	
			<div class="col-sm-6">	
				<div class="form-group">
					<input  type="file" class="form-control-file" id='taiFile' accept=".pdf" style="display: none;"/>
					<button class="btn btn-default btn-lg" onclick="document.getElementById('taiFile').click(); this.blur();" style="width: 100%" type="button">
		                <i class="glyphicon glyphicon-cloud-upload"></i>
		                Hợp đồng vay                                            
		            </button>
		            <small id="nameTaiFile" style="display: none;"></small>
					<textarea id="base64TaiFile" style="display: none;"><c:out value="${fileKy }"/></textarea>
				</div>
			</div>
			<div class="form-group col-sm-6">
				<div class="form-group">
					<input  type="file" class="form-control-file" id='taiFileBaoHiem' accept=".pdf" style="display: none;"/>
					<button class="btn btn-default btn-lg" onclick="document.getElementById('taiFileBaoHiem').click(); this.blur();" style="width: 100%" type="button">
		                <i class="glyphicon glyphicon-cloud-upload"></i>
		                Hợp đồng bảo hiểm                                            
		            </button>
		            <a href="javascript:void(0)" id="deleteBh">Xóa Hợp đồng bảo hiểm</a>
		            <small id="nameTaiFileBaoHiem" style="display: none;"></small>
					<textarea id="base64TaiFileBaoHiem" style="display: none;"></textarea>
				</div>
			</div>
		</div>
		<div class="row">	
			<div class="form-group col-sm-6">
				<input type="file" class="form-control-file" name="anhCaNhanKtt" id="anhCaNhanKtt" accept=".jpg,.jpeg,.png" style="display: none;"/>
				<button class="btn btn-default btn-lg" onclick="document.getElementById('anhCaNhanKtt').click(); this.blur();" style="width: 100%" type="button">
	                <span class="glyphicon glyphicon-cloud-upload"></span>
	                <spring:message code="ekycdn.anh_ca_nhan" />                                       
	            </button>
	            <small id="nameAnhCaNhanKtt" style="display: none;"></small><br/>
	            <c:if test="${empty anhCaNhan }">
	            	<img src="" style="display: none;max-width: 100%;max-height: 200px" id="base64AnhCaNhanKttImg" class="img-thumbnail"/>
	            </c:if>
	            <c:if test="${not empty anhCaNhan }">
	            	<img src="data:image/jpeg;base64,<c:out value="${anhCaNhan }"/>" style="max-width: 100%;max-height: 200px" id="base64AnhCaNhanKttImg" class="img-thumbnail"/>
	            </c:if>
				<textarea id="base64AnhCaNhanKtt" style="display: none;"><c:out value="${anhCaNhan }"/></textarea>
			</div>
		</div>
		<button class="btn btn-primary nextBtn btn-sm" type="button" onclick="validateStep1(this)">Xem nội dung file ký</button>
	</div>
</div>
<script>
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
</script>
<c:if test="${not empty fileKy }">
<script type="text/javascript">
var contentType = 'application/pdf';
var b64Data = '${fileKy }';
var blob = b64toBlob(b64Data, contentType);
var blobUrl = URL.createObjectURL(blob);
$(document).ready(function(){
	$("#iframeid").attr("src", blobUrl);
});
</script>
</c:if>

<c:if test="${not empty fileKyBaoHiem }">
<script type="text/javascript">
var b64DataBH = '${fileKyBaoHiem }';
var blobBH = b64toBlob(b64DataBH, contentType);
var blobUrlBH = URL.createObjectURL(blobBH);
$(document).ready(function(){
	$("#iframeidBaoHiem").attr("src", blobUrlBH);
	$("#iframeidBaoHiem").show();
});
</script>
</c:if>

<script type="text/javascript">
var thayDoiFileKy = 'false';
var thayDoiFileBaoHiem = 'false';
$(document).ready(function(){
	addEventFile("anhCaNhanKtt", "base64AnhCaNhanKtt", "nameAnhCaNhanKtt");
	$("#taiFile").change(function(){
		if(ValidateSingleInput1(this)) {
			var file =document.getElementById("taiFile").files[0];
			var url =URL.createObjectURL(file );
			$('#iframeid').attr('src',url);
			
			$("#base64TaiFile").html("");
			getBase64(file, "base64TaiFile");
			
			$("#nameTaiFile").html($("#taiFile").val());
			if($("#nameTaiFile").html()!="") {
				$("#nameTaiFile").show();
			}
		}
	}); 
	
	$("#taiFile").click(function(){
		thayDoiFileKy = 'true';
		$("#taiFile").val("");
		$("#nameTaiFile").hide();
		$('#iframeid').attr('src',"");
		$("#base64TaiFile").html("");
	})
	
	$("#taiFileBaoHiem").change(function(){
		if(ValidateSingleInput1(this)) {
			var file =document.getElementById("taiFileBaoHiem").files[0];
			var url =URL.createObjectURL(file );
			$('#iframeidBaoHiem').attr('src',url);
			$('#iframeidBaoHiem').show();
			$("#base64TaiFileBaoHiem").html("");
			getBase64(file, "base64TaiFileBaoHiem");
			
			$("#nameTaiFileBaoHiem").html($("#taiFileBaoHiem").val());
			if($("#nameTaiFileBaoHiem").html()!="") {
				$("#nameTaiFileBaoHiem").show();
			}
		}
	}); 
	$("#deleteBh").click(function(){
		resetBh();
		toastr.info("Đã xóa bỏ hợp đồng bảo hiểm, vui lòng hoàn thành các bước để thực hiện lưu lại việc xóa bỏ hợp đồng");
	})
	$("#taiFileBaoHiem").click(function(){
		resetBh();
	})
	function resetBh() {
		thayDoiFileBaoHiem = 'true';
		$("#taiFileBaoHiem").val("");
		$("#nameTaiFileBaoHiem").hide();
		$('#iframeidBaoHiem').attr('src',"");
		$("#base64TaiFileBaoHiem").html("");
		$('#iframeidBaoHiem').hide();
	}
});
const validateEmail = (email) => {
  return String(email)
    .toLowerCase()
    .match(
      /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/
    );
};
function validateStep1(obj) {
	if($("#soCmt").val() == "") {
		toastr.error("Nhập số chứng minh thư")
	} else if($("#hoVaTen").val() == "") {
		toastr.error("Nhập họ và tên")
	} else if(!validateTruePhone($("#soDienThoai").val())) {
		toastr.error("Số điện thoại không đúng định dạng")
	} else if($("#email").val() == "") {
		toastr.error("Nhập email")
	} else if(!validateEmail($("#email").val())) {
		toastr.error("Email không đúng định dạng");
	} else if ($("#base64AnhCaNhanKtt").html() == "") {
		toastr.error("Tải lên ảnh cá nhân")
	} else if ($("#base64TaiFile").html() == "") {
		toastr.error("Tải lên file ký số")
	} else {
		nextStep(obj);
	}
}
function validateTruePhone(phone) {
	if(phone == "") return false;
	if(!phone.startsWith("0")) return false;
	if(phone.length != 10) return false;
	return true;
}
var _validFileExtensions1 = [".pdf"];    
function ValidateSingleInput1(oInput) {
    if (oInput.type == "file") {
        var sFileName = oInput.value;
         if (sFileName.length > 0) {
            var blnValid = false;
            for (var j = 0; j < _validFileExtensions1.length; j++) {
                var sCurExtension = _validFileExtensions1[j];
                if (sFileName.substr(sFileName.length - sCurExtension.length, sCurExtension.length).toLowerCase() == sCurExtension.toLowerCase()) {
                    blnValid = true;
                    break;
                }
            }
             
            if (!blnValid) {
            	toastr.error("Chỉ chấp nhận file có định dạng " + _validFileExtensions1.join(", "));
                oInput.value = "";
                return false;
            }
        }
    }
    return true;
}
</script>