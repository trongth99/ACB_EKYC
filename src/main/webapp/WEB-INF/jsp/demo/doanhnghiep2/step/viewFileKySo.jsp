<%@ page contentType="text/html; charset=UTF-8"%>
<%@include file="../layout/header.jsp"%>
<%@include file="../layout/js.jsp"%>

<div class="col-md-9 register-right">
	<div class="tab-content" id="myTabContent">
		<div class="tab-pane fade show active" id="home" role="tabpanel" aria-labelledby="home-tab">
			<h3 class="register-heading">Signed </h3>
			<div class="row register-form">
				<form action="/fisbank/ekyc-enterprise/ekyc/step4" style="width: 100%;" method="post" id="submitForm" enctype='multipart/form-data'>
					<div class="col-sm-12">
						<h2>Infomation</h2>
						<hr/>
						<iframe id="base64File" style="width: 100%; height: 500px; border: 0;"></iframe>
					</div>
					<div id="maOtpKySo" style="display: none;margin-top: 20px;">
						<label class="control-label"><spring:message code="ekycdn.nha_otp_ky_so" /></label>
						<input maxlength="200" type="text" required="required" class="form-control" id="otpKySo" name="otpKySo"/>
						<input  type="hidden" class="form-control" id="agreementUUID" name="agreementUUID" value='<c:out value="${agreementUUID }"></c:out>'/>
						<input  type="hidden" class="form-control" id="maKy" name="maKy" value='<c:out value="${maKy }"></c:out>'/>
						<input  type="hidden" class="form-control" id="soCmt" name="soCmt" value="${ocr.soCmt }"/>
						<input  type="hidden" class="form-control" id="kiemTraGuiMaOTP" name="kiemTraGuiMaOTP"/>
					</div>
					<input type="button" class="btnRegister" id="btnRegisterDangKy" value="Digital signature" onclick="dangKyKySo(this)" style="margin-left: 5px;"/>
				</form>
			</div>
		</div>
	</div>
</div>
<script>
var contentType = 'application/pdf';
var b64Data = '${file }';

var blob = b64toBlob(b64Data, contentType);
var blobUrl = URL.createObjectURL(blob);

$(document).ready(function(){
	$("#base64File").attr("src", blobUrl);	
	
});
var kiemTraGuiMaOTP = '<c:out value="${kiemTraGuiMaOTP}"/>'
if(kiemTraGuiMaOTP && kiemTraGuiMaOTP == 'true') {
	nhapMaOtp();
	$("#kiemTraGuiMaOTP").val('true');
}
function dangKyKySo(obj) {
		$("#btnRegisterDangKy").prop("disabled",true);
		var base64AnhMatTruoc = $("#base64AnhMatTruoc").html();
		var base64AnhMatSau = $("#base64AnhMatSau").html();
		var base64Pdf = $("#contentFilePdf").html();
		var data = {
			"token": '${token}',
			"tokenCheck": '${tokenCheck}',
			"code": '${code}',
			"file": '${file }'
		};
		$(obj).attr('value', 'Processing information ...');
		$.ajax({
			url:'${contextPath}/ekyc-enterprise/ekyc/register-sign',
		    data: JSON.stringify(data),
		    type: 'POST',
		    processData: false,
		    contentType: 'application/json'
		}).done(function(data) {
			console.log(data)
			if(data.status == 200) {
				$("#agreementUUID").val(data.agreementUUID);
				$("#maKy").val(data.maKy);
				$("#kiemTraGuiMaOTP").val('true');
				nhapMaOtp();
//	 			$("#btnRegister").attr("type", "submit");
//	 			$("#btnRegister").attr("onclick", "").unbind("click");
//	 			toastr.info ("Click vào nút ký số để tiến hành ký");
			} else if(data.status == 405) {
				location.href = '${contextPath}/ekyc-enterprise/ekyc';
			} else {
				toastr.error(data.message);
				$("#btnRegisterDangKy").prop("disabled",false);
			}
			
			$(obj).attr('value', 'Digital signature');
		}).fail(function(data) {
			$("#btnRegisterDangKy").prop("disabled",false);
			toastr.error("Error checking information");
			$(obj).attr('value', 'Digital signature');
		});
}
function nhapMaOtp() {
	swal({
	  title: "Enter OTP!",
	  text: "Please enter the OTP sent to your phone number.",
	  type: "input",
	  showCancelButton: false,
	  closeOnConfirm: false,
	  confirmButtonText: "Digital signature",
	  inputPlaceholder: "OTP"
	}, function (inputValue) {
		  if (inputValue === false) return false;
		  if (inputValue === "") {
		    swal.showInputError("Enter the OTP!");
		    return false
		  }
		  $("#otpKySo").val(inputValue);
		  $("#submitForm").submit();
		  $(".confirm").prop("disabled",true);
	});
}
</script>

<%@include file="../layout/footer.jsp"%>
			
