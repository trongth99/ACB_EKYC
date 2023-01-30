<%@ page contentType="text/html; charset=UTF-8"%>
<%@include file="../layout/header.jsp"%>
<%@include file="../layout/js.jsp"%>

<div class="col-md-12 register-right">
	<div class="tab-content" id="myTabContent">
		<div class="tab-pane fade show active" id="home" role="tabpanel" aria-labelledby="home-tab">
			<h3 class="register-heading">Check and add more information <br/> <i> Kiểm tra và bổ sung thêm thông tin</i> </h3>
			<div class="row register-form">
				<form action="${contextPath }/ekyc-enterprise/ekyc/step3" style="width: 100%;" method="post" id="submitForm" enctype='multipart/form-data'>
					<div class="tab">
						<div class="row">
							<div class="col-md-6">
								<div class="form-group ">
									<label class="form-label" style="font-weight: bold;">ID card number<br/><i style="font-weight: normal;">Số giấy tờ tùy thân</i> </label>
									<input type="text" class="form-control" value="${ocr.soCmt }" readonly="readonly" name="soCmt"/>
								</div>
							</div>	
							<div class="col-md-6">
								<div class="form-group">
									<label class="form-label" style="font-weight: bold;">Full name <br/><i style="font-weight: normal;">Họ và tên</i></label>
									<input type="text" class="form-control" value="${ocr.hoVaTen }" readonly="readonly" name="hoVaTen"/>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-md-6">
								<div class="form-group">
									<label class="form-label" style="font-weight: bold;">Expiration date<br/><i style="font-weight: normal;">Ngày hết hạn</i></label>
									<input type="text" class="form-control" value="${ocr.ngayHetHan }" readonly="readonly" name="ngayHetHan"/>
								</div>
							</div>
							<div class="col-md-6">
								<div class="form-group">
									<label class="form-label" style="font-weight: bold;">Issue date<br/><i style="font-weight: normal;">Ngày cấp</i></label>
									<input type="text" class="form-control" value="${ocr.ngayCap }" readonly="readonly" name="ngayCap"/>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-md-6">
								<div class="form-group">
									<label class="form-label" style="font-weight: bold;">Date of birth<br/><i style="font-weight: normal;">Ngày sinh</i></label>
									<input type="text" class="form-control" value="${ocr.namSinh }" readonly="readonly" name="namSinh"/>
								</div>
							</div>
							<div class="col-md-6">
								<div class="form-group">
									<label class="form-label" style="font-weight: bold;">Issue place<br/><i style="font-weight: normal;">Nơi cấp</i></label>
									<input type="text" class="form-control" value="${ocr.noiCap }" readonly="readonly" name="noiCap"/>
								</div>
							</div>
						</div>
						<hr/>
						<div class="row">
							<div class="col-md-6">
								<div class="form-group">
									<label class="form-label" style="font-weight: bold;">Nationality<br/><i style="font-weight: normal;">Quốc tịch</i></label>
									<input type="text" class="form-control" value="${ocr.quocTich }"  name="quocTich"/>
								</div>
							</div>
							<div class="col-md-6">
								<div class="form-group">
									<label class="form-label" style="font-weight: bold;">Visa/ TR card (for foreigner)<br/><i style="font-weight: normal;">Visa/ Thẻ TR (Cho người ngoại quốc)</i></label>
									<input type="text" class="form-control" value="" id="visa" name="visa" />
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-md-6">
								<div class="form-group">
									<label class="form-label" style="font-weight: bold;">Tax Code<br/><i style="font-weight: normal;">Mã số thuế</i></label>
									<input type="text" class="form-control" value="" id="maSoThue"  name="maSoThue" />
								</div>
							</div>
							<div class="col-md-6">
								<div class="form-group">
									<label class="form-label" style="font-weight: bold;">Residential Status<br/><i style="font-weight: normal;">Tình Trạng Cư Trú </i></label>
									<select class="form-control" name="tinhTrangCuTru">
										<option value="Resident">Resident</option>
										<option value="Non-resident">Non-resident</option>
									</select>
								</div>
							</div>
						</div>
						<hr/>
						<h4>Contact No:<br/><i style="font-weight: normal;font-size: 20px;">Thông tin liên hệ </i></h4>
						<div class="row">
							<div class="col-md-6">
								<div class="form-group">
									<label class="form-label" style="font-weight: bold;">Home<br/><i style="font-weight: normal;">Nhà</i></label>
									<input type="text" class="form-control" value="" id="diaChiNha" name="diaChiNha"/>
								</div>
							</div>
							<div class="col-md-6">
								<div class="form-group">
									<label class="form-label" style="font-weight: bold;">Mobile<br/><i style="font-weight: normal;">Số điện thoại</i></label>
									<input type="text" class="form-control" value="" id="mobile" name="mobile"/>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-md-12">
								<div class="form-group">
									<label class="form-label" style="font-weight: bold;">Office<br/><i style="font-weight: normal;">Văn phòng</i></label>
									<input type="text" class="form-control" value="" id="vanPhong" name="vanPhong"/>
								</div>
							</div>
						</div>
						<hr/>
						<h4>Permanent registered address<br/><i style="font-weight: normal;font-size: 20px;">Hộ khẩu thường trú </i></h4>
						<div class="row">
							<div class="col-md-12">
								<div class="form-group">
									<input type="text" class="form-control" value="" id="hokhau"  name="hokhau"/>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-md-12">
								<div class="form-group">
									<label class="form-label" style="font-weight: bold;">Email</label>
									<input type="text" class="form-control" value="" id="email2" name="email2"/>
								</div>
							</div>
						</div>
					</div>
					<input type="submit" class="btnRegister"  value="Lưu thông tin" />
					<button class="btnRegister" type="button"  style="background: #CCC;color: black;width: 110px;" onclick="javascript:location.href='${contextPath }/ekyc-enterprise/ekyc?<c:out value="${params.queryParams }"></c:out>'">Quay lại</button>
				</form>
			</div>
		</div>
	</div>
</div>
<script>
$(document).ready(function(){
	$("#submitForm").validate({
        rules: {
        	
        	code: {required: true}
        	
        }
    });
});



</script>

<%@include file="../layout/footer.jsp"%>
			
