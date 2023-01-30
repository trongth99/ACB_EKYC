<%@ page contentType="text/html; charset=UTF-8"%>
<div class="panel panel-primary setup-content" id="step-11">
	<div class="panel-heading">
		Complete the registration process / Hoàn tất quá trình đăng ký
	</div>
	<div class="panel-body">
		<div class="form-group text-center">
				<div class="row">
				<div class="form-group col-sm-4">
					<div class="form-group  has-feedback">
						<label class="control-label">Full name of contact person (*)<br/><i style="font-weight: normal;">Họ và tên người liên hệ (*)</i></label>
						<input type="text" class="form-control input-sm" name="nameContractPersion" id="nameContractPersion" value="<c:out value='${ekycDoanhNghiepTable.tenNguoiLienHe}'/>"/>
					</div>
				</div>
				<div class="form-group col-sm-4">
					<div class="form-group  has-feedback">
						<label class="control-label">Email address of contact person (*)<br/><i style="font-weight: normal;">Địa chỉ email của người liên hệ</i></label>
						<input type="text" class="form-control input-sm" name="emailContractPersion" id="emailContractPersion" value="<c:out value='${ekycDoanhNghiepTable.emailNguoiLienHe}'/>"/>
					</div>
				</div>
				<div class="form-group col-sm-4">
					<div class="form-group  has-feedback">
						<label class="control-label">Phone of contact person (*)<br/><i style="font-weight: normal;">Số điện thoại người liên hệ (*)</i></label>
						<input type="text" class="form-control input-sm" name="phoneContractPersion" id="phoneContractPersion" value="<c:out value='${ekycDoanhNghiepTable.soDienThoaiNguoiLienHe}'/>"/>
					</div>
				</div>
			</div>
			<button class="btn btn-primary nextBtn pull-left " type="button" onclick="validateStepSendMail(this)"data-loading-text="<i class='fa fa-circle-o-notch fa-spin'></i> <spring:message code="ekycdn.dang_xy_ly" />" id="textLoad4"><spring:message code="ekycdn.ket_thuc" /></button>
		</div>
	</div>
</div>
<script type="text/javascript">
function validateStepSendMail(obj) {
	 $(document).ready(function() {
		 toastr.options.timeOut = 5000;
		toastr.info("You have successfully completed the first step to register to open an account. Please check your email again to start the eKYC process./Quý khách đã thực hiện thành công bước đầu để đăng ký mở tài khoản. Quý khách vui lòng kiểm tra lại email để bắt đầu quy trình eKYC.");
		  }); 
	
		 
	if($("#emailContractPersion").val().trim() == "") {
		toastr.error("Email address of contact person is not empty");
	} else if(!validateEmail($("#emailContractPersion").val().trim())) {
		toastr.error("Email address of contact invalid");
	} else if ( $("#phoneContractPersion").val().trim() == "" ){
		toastr.error("Phone of contact person is not empty");
	} else if ( $("#nameContractPersion").val().trim() == "" ){
		toastr.error("Full name of contact person is not empty");
	}  else {
		  
		var data = {
				"emailContractPersion": $("#emailContractPersion").val(),
				"phoneContractPersion": $("#phoneContractPersion").val(),
				"nameContractPersion": $("#nameContractPersion").val(),
				"token": token
		};
		$.ajax({
			url:'${contextPath}/ekyc-enterprise/send-mail-edit',
		    data: JSON.stringify(data),
		    type: 'POST',
		    processData: false,
		    contentType: 'application/json'
		}).done(function(data) {
			
			$(document).ready(function() {
				 setInterval( function () {
		        	 location.href='${contextPath }/ekyc-enterprise';
						//nextStep(obj);
						$(obj).button('reset');
		         }, 2000);
			});
			
		}).fail(function(data) {
			toastr.error("Lỗi kiểm tra thông tin");
			$(obj).button('reset');
		});
	}

	
}


 
 
</script>