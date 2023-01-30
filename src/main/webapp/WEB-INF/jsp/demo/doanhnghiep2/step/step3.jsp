<%@ page contentType="text/html; charset=UTF-8"%>
<%@include file="../layout/header.jsp"%>
<%@include file="../layout/js.jsp"%>

<div class="col-md-12 register-right">
	<div class="tab-content" id="myTabContent">
		<div class="tab-pane fade show active" id="home" role="tabpanel" aria-labelledby="home-tab">
			<h3 class="register-heading">Upload ID card <br/>
			<i>Tải lên thẻ nhận dạng cá nhân</i></h3>
			<h4 class="register-heading" style="color: #00A950;">( <spring:message code="${loai }" text="${loai }" /> )</h4>
			<div class="row register-form">
				<form action="${contextPath }/ekyc-enterprise/ekyc" style="width: 100%;" method="post" id="submitForm" enctype="multipart/form-data">
					<div class="col-md-12">
						<div class="row">
							<c:choose>
								<c:when test="${(loai eq 'nguoiDuocUyQuyen') or (loai eq 'keToanTruong') or (loai eq 'uyQuyenKeToanTruong') or (loai eq 'nguoiDaiDienPhapLuat') or (loai eq 'banLanhDao') }">
									<div class="col-md-4">
										<div class="form-group">
											<label class="form-label" style="font-weight: bold;">Front ID card(*)<br/><i style="font-weight: normal;">Mặt trước giấy tờ tùy thân</i></label>
											<input type="file" class="form-control" placeholder="" id="anhMatTruoc" name="anhMatTruoc" capture="camera"/>
										</div>
										<img src="" style="display: none;max-width: 100%;max-height: 150px" id="anhMatTruocImg" class="img-thumbnail"/>
									</div>
									<div class="col-md-4">
										<div class="form-group">
											<label class="form-label" style="font-weight: bold;">Back ID card (*)<br/><i style="font-weight: normal;">Mặt sau giấy tờ tùy thân</i></label>
											<input type="file" class="form-control" placeholder="" id="anhMatSau" name="anhMatSau" capture="camera"/>
										</div>
										<img src="" style="display: none;max-width: 100%;max-height: 150px" id="anhMatSauImg" class="img-thumbnail"/>
									</div>
									<div class="col-md-4">
										<div class="form-group">
											<label class="form-label" style="font-weight: bold;">Signature photo (*)<br/><i style="font-weight: normal;">Ảnh chữ ký</i></label>
											<input type="file" class="form-control" placeholder="" id="anhChuKy" name="anhChuKy" capture="camera"/>
										</div>
										<img src="" style="display: none;max-width: 100%;max-height: 150px" id="anhChuKyImg" class="img-thumbnail"/>
									</div>
								</c:when>
								<c:otherwise>
									<div class="col-md-6">
										<div class="form-group">
											<label class="form-label" style="font-weight: bold;">Front ID card(*)<br/><i style="font-weight: normal;">Mặt trước giấy tờ tùy thân</i></label>
											<input type="file" class="form-control" placeholder="" id="anhMatTruoc" name="anhMatTruoc" capture="camera"/>
										</div>
										<img src="" style="display: none;max-width: 100%;max-height: 150px" id="anhMatTruocImg" class="img-thumbnail"/>
									</div>
									<div class="col-md-6">
										<div class="form-group">
											<label class="form-label" style="font-weight: bold;">Back ID card (*)<br/><i style="font-weight: normal;">Mặt sau giấy tờ tùy thân</i></label>
											<input type="file" class="form-control" placeholder="" id="anhMatSau" name="anhMatSau" capture="camera"/>
										</div>
										<img src="" style="display: none;max-width: 100%;max-height: 150px" id="anhMatSauImg" class="img-thumbnail"/>
									</div>
								</c:otherwise>
							</c:choose>
						</div>
						<input type="submit" class="btnRegister" id="btnRegister" value="Upload" />
					</div>
				</form>
			</div>
		</div>
	</div>
</div>
<script>
$(document).ready(function(){
	addEventFile("anhMatTruoc");
	addEventFile("anhMatSau");
	addEventFile("anhChuKy");
	$("#submitForm").validate({
        rules: {
        	anhMatTruoc: {required: true},
        	anhMatSau: {required: true},
        	anhChuKy: {required: true},
        },
        messages: {
        	anhMatTruoc: {
                required: "Tải lên ảnh mặt trước",
            },
            anhMatSau: {
                required: "Tải lên ảnh mặt sau",
            },
            anhChuKy: {
                required: "Tải lên ảnh chữ ký",
            },
        }
    });
});
</script>
<script>
$(document).ready(function() {
    // initialize with defaults
    $("#anhMatTruoc").fileinput({
        'showUpload': false, 'showClose':true,'showUploadedThumbs': false
    });
});
</script>
<%@include file="../layout/footer.jsp"%>
			
