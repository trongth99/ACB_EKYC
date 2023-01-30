<%@ page contentType="text/html; charset=UTF-8"%>
<div class="panel panel-primary setup-content" id="step-7">
	<div class="panel-heading">
		<h3 class="panel-title"><spring:message code="ekycdn.tao_giay_uy_quyen" /></h3>
	</div>
	<div class="panel-body">
		<div class="form-group text-center">
			<form id="dataUyQuyen" method="post" enctype="multipart/form-data">
				<div style=";margin: auto;" class="col-sm-2"></div>
				<div style=";margin: auto;" class="col-sm-8">
		 			<div id="giayUyQuyen" style="text-align: left;">
						<p>Date/Ngày: ${date }</p>
						<p style='text-align: center;font-weight: bold;font-size: 25px;'>CONFIRMATION LETTER</p>
						<p style='text-align: center;font-weight: bold;font-size: 25px;'>THƯ XÁC NHẬN</p>
						<p>Confirmation of Company’s Existing Members’ Council/Board of Director; Board of Management 2; and subsidiaries, branches and representative offices</p>
						<p>Xác nhận danh sách Hội đồng thành viên/ Hội đồng quản trị, Ban điều hành, và Công ty con, chi nhánh, và văn phòng đại diện</p>
						<p>I, being the Representative of </p>
						<p>Tôi, là Đại diện của </p>
						<p>${ekycDoanhNghiep.nameCompany } (the “Company”) (“Công ty”), </p>
						<p>Hereby certify that the below persons are all existing Members’ Council/ Board of Directors1, Board of Management; and duly appointed under the constitutional documents of the Company:</p>
						<p>Qua đây xác nhận những người dưới đây là thành viên trong Hội đồng thành viên/ Hội đồng quản trị, Ban điều hành của Công ty dựa trên văn bản lập hiến của công ty:</p>
						<p style='font-weight: bold;font-size: 20px;'>Members’ Council/Board of Director; Board of Management </p>
						<p>(Hội đồng thành viên/ Hội đồng quản trị, Ban điều hành) </p>
						<p>1.  </p>
						
						<c:forEach items="${lanhDaos}" var="item" varStatus="status">
							<p>Full Name/ Họ và tên: ${item.hoVaTen }</p>
							<p>Position/Chức danh: _______________________</p>
							<p>Date of Birth/Ngày sinh: _______________________</p>
							<p>Nationality/Quốc tịch: _______________________</p>
							<p>National ID number or Passport number/Chứng minh nhân dân hoặc số hộ chiếu: ${item.soCmt } </p>
							<p>issued by/được cấp bởi: __________ </p>
							<p>issue date/vào ngày: ${item.ngayCap }</p>
							<p>Permanent Resident address/Địa chỉ thường trú: _______________________</p>
							<p>Phone number/ Số điện thoại: ${item.phone }</p>
						</c:forEach>
						<p style='font-weight: bold;font-size: 20px;'>Subsidiaries, branches and representative offices </p>
						<p>(Công ty con, chi nhánh, và văn phòng đại diện)</p>
						<p>Does your company have Subsidiaries, branches and representative offices?	Yes1  / No</p>
						<p>(Xác nhận nếu công ty có bất kì Công ty con, chi nhánh, và văn phòng đại diện)	Có/ Không</p>
						<p style='font-weight: bold;font-size: 15px;'>1. Name Subsidiaries, branches and representative offices /Tên Công ty con, chi nhánh, và văn phòng đại diện: ___________</p>
						<p>Register address/ Địa chỉ: ___________</p>
						<p>Legal representative/Đại diện theo pháp luật: ___________</p>
						<p style='font-weight: bold;font-size: 15px;'>2. Name Subsidiaries, branches and representative offices /Tên Công ty con, chi nhánh, và văn phòng đại diện: ___________</p>
						<p>Register address/ Địa chỉ: ___________</p>
						<p>Legal representative/Đại diện theo pháp luật: ___________</p>
						<p>With regards/Trân trọng,</p>
						<p>For and on behalf of the Company</p>
						<p>Đại diện cho Công ty </p>
						<p style="margin-top: 100px;">__________________</p>
						<p>(Position/ Chức Danh)</p>
						<p>__________________</p>
						<p>  If Yes, please provide information as below (Nếu có, vui lòng cung cấp các thông tin theo yêu cầu)</p>
						<p>2 Board Management must include all senior managements, including but not limited to  <br/>(i)<span style='color:blue;'>Chairman / Co-Chairman or equivalent; (ii) Chief Executive or equivalent; (iii) Deputy Chief Executive (s); (iv) Chief Financial Officer or equivalent; or (v) Chief Operating Officer or equivalent (Ban quản trị phải bao gồm tất cả các quản lý cấp cao, bao gồm, nhưng không giới hạn bởi, (i) Chủ tịch / Đồng Chủ tịch hoặc tương đương; (ii) Tổng Giám đốc hoặc tương đương; (iii) (Các) Phó Tổng Giám đốc ; (iv) Giám đốc Tài chính hoặc tương đương; hoặc (v) Giám đốc điều hành hoặc tương đương)</span></p>
						

					</div>
				</div>
				<div style=";margin: auto;" class="col-sm-2"></div>
				<div style="clear: both;"></div>
				<div class="row">
					<div style=";margin: auto;" class="col-sm-2"></div>
					<div style=";margin: auto;" class="col-sm-8">
						<div class="row">
							<div class="form-group col-sm-6" style="text-align: left;" >
								<button id="kyDoanhNghiep" class="btn btn-primary nextBtn" type="button" onclick="validateStepXemFileKy(this)" data-loading-text="<i class='fa fa-circle-o-notch fa-spin'></i> <spring:message code="ekycdn.dang_xy_ly" />" id="uploadStep7"><spring:message code="ekycdn.cong_ty_ky" /></button>
							</div>
							<div class="form-group col-sm-6">
								<button class="btn btn-primary nextBtn pull-right" id="nddpl" type="button" onclick="validateStepUyQuyen(this)" data-loading-text="<i class='fa fa-circle-o-notch fa-spin'></i> <spring:message code="ekycdn.dang_xy_ly" />" id="uploadStep7"><spring:message code="ekycdn.nguoi_ddpl_ky" /></button>
							</div>
							<textarea id="contentFilePdf" style="display: none;"></textarea>
						</div>
						<div id="maOtpKySo" style="display: none;">
							<label class="control-label"><spring:message code="ekycdn.nha_otp_ky_so" /></label>
							<input maxlength="200" type="text" required="required" class="form-control" id="otpKySo" name="otpKySo"/>
							<button class="btn btn-primary nextBtn pull-right" style="margin-top: 10px;" type="button" onclick="kySo(this)" data-loading-text="<i class='fa fa-circle-o-notch fa-spin'></i> <spring:message code="ekycdn.dang_xy_ly" />"><spring:message code="ekycdn.nguoi_ddpl_ky" /></button>
						</div>
						<iframe id="base64FilePdf"
								style="width: 100%; height: 400px; border: 0;display: none;"></iframe>
					</div>
					<div style=";margin: auto;" class="col-sm-2"></div>
					<div style="clear: both;"></div>
				</div>
			</form>
		</div>
		<div class="form-group" style="width: 500px;margin: auto;display: none;" id="kyDoanhNghiep">
			<textarea style="display: none;" id="base64Cert" name="base64Cert"></textarea>
			<button class="btn btn-primary nextBtn pull-right" type="button" onclick="validateStepXemFileKy(this)" data-loading-text="<i class='fa fa-circle-o-notch fa-spin'></i> <spring:message code="ekycdn.dang_xy_ly" />"><spring:message code="ekycdn.ky_so" /></button>
			<input class="btn btn-info nextBtn pull-right" onClick="showCertListDialog()" type="button" value="<spring:message code="ekycdn.lay_chung_thu_so" />" style="margin-right: 10px;">
		</div>
		<div style="clear: both;"></div>
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


	var otpKyso;
	var pathPdf;
	var nameFile;
	var agreementUUID;
	var maKy;
	var noiDungFileKy;
	var checkKy = 0;
	var noiDungFileDangKy;
	function validateStepUyQuyen(obj) {
		var base64AnhMatTruoc = '${base64AnhMatTruoc}';
		var base64AnhMatSau = '${base64AnhMatSau}';
		var base64Pdf = $("#contentFilePdf").html();
		var data = {
			"anhMatTruoc": 	base64AnhMatTruoc,
			"anhMatSau": 	base64AnhMatSau,
			"hoVaTen": '${nguoiUyQuyen.hoVaTen }',
			"soCmt": '${nguoiUyQuyen.soCmt }',
			"ngayCap": '${nguoiUyQuyen.ngayCap }',
			"noiCap": '${nguoiUyQuyen.noiCap }',
			"soDienThoai": '${nguoiUyQuyen.phone }',
			"contentPdf": $("#giayUyQuyen").html(),
			"file": base64Pdf
		};
		$(obj).button('loading');
		$.ajax({
			url:'${contextPath}/ekyc-enterprise/ky-so',
		    data: JSON.stringify(data),
		    type: 'POST',
		    processData: false,
		    contentType: 'application/json'
		}).done(function(data) {
			console.log(data)
			if(data.status == 200) {
				otpKyso = data.otp;
				pathPdf = data.pathPdf;
				agreementUUID = data.agreementUUID;
				nameFile = data.nameFile;
				console.log(agreementUUID)
				console.log(otpKyso)
				maKy = data.maKy;
				console.log(maKy)
				$("#maOtpKySo").show();
				$("#nddpl").hide();
			} else {
				toastr.error(data.message);
			}
			
			$(obj).button('reset');;
		}).fail(function(data) {
			toastr.error("Lỗi kiểm tra thông tin");
			$(obj).button('reset');;
		});
	}
	function kySo(obj) {
		$(obj).button('loading');
		if($("#otpKySo").val() == otpKyso) {
			var data = {
				"pathPdf": 	pathPdf,
				"nameFile": 	nameFile,
				"agreementUUID": agreementUUID,
				"maKy": maKy,
				"otp": otpKyso
			};
			$.ajax({
				url:'/fisbank/ekyc-enterprise/ky-so-otp',
			    data: JSON.stringify(data),
			    type: 'POST',
			    processData: false,
			    contentType: 'application/json'
			}).done(function(data) {
				
				if(data.status == 200) {
					fileContent = data.file;
					noiDungFileKy = fileContent;
					
					var blob = b64toBlob(fileContent, contentType);
					var blobUrl = URL.createObjectURL(blob);
					
					$("#base64FilePdf2").attr("src", blobUrl);	
					$("#maOtpKySo").hide();
					toastr.info ('<spring:message code="ekycdn.ky_thanh_cong" />');
					checkKy++;
					checkKyFc(obj);
				} else {
					toastr.error(data.message);
				}
				$(obj).button('reset');;
			}).fail(function(data) {
				toastr.error("Lỗi kiểm tra thông tin");
				$(obj).button('reset');;
			});
		} else {
			toastr.error("Mã OTP không dúng");
			$(obj).button('reset');;
		}
		
	}
	function validateStepXemFileKy(obj) {
		var base64Cert = $("#base64Cert").val();
		if(base64Cert != "") {
			if(fileContent == "" || !fileContent) {
				$(obj).button('loading');
				var data = {
					"contentPdf": $("#giayUyQuyen").html()	
				}
				$.ajax({
					url:'${contextPath}/ekyc-enterprise/html-pdf',
				    data: JSON.stringify(data),
				    type: 'POST',
				    processData: false,
				    contentType: 'application/json'
				}).done(function(data) {
					fileContent = data.file;
					$("#contentFilePdf").html(fileContent);
					sign(obj);	
					$(obj).button('reset');;
				}).fail(function(data) {
					toastr.error("Lỗi kiểm tra thông tin");
					$(obj).button('reset');;
				});
			} else {
				sign(obj);				
			}
		} else {
			showCertListDialog();
// 			toastr.warning("Vui lòng lấy chứng thư số trước khi ký.")
		}
		
		
	}
	function goiHamalert() {
		toastr.info ('<spring:message code="ekycdn.click_tiep" /> <spring:message code="ekycdn.cong_ty_ky" />');
	}
	function luuNoiDungKy(base64, obj) {
		console.log(base64)
		if(base64 != "") {
			checkKy ++;
			console.log("luuNoiDungKy: "+checkKy)
			if(checkKy >= 3) {
				noiDungFileDangKy = base64;
				$("#contentFilePdfDangKy").html(base64)
			} else {
				noiDungFileKy = base64;
				$("#contentFilePdf").html(base64)
			}
			
			var contentType = 'application/pdf';
			
			var blob = b64toBlob(base64, contentType);
			var blobUrl = URL.createObjectURL(blob);
			toastr.info ('<spring:message code="ekycdn.ky_thanh_cong" />');
			$("#base64FilePdf2").attr("src", blobUrl);	
			$("#kyDoanhNghiep").hide();
			if(checkKy == 4) {
				nextStep($("#kyDoanhNghiepDangKy"));
				uploadDuLieuLenHeThong();	
			}
			checkKyFc(obj);
		} else {
			toastr.error("Lỗi ký số");
		}
	}
	function checkKyFc(obj) {
		if(checkKy == 2) {
			nextStep(obj);
		}
	}
	function uploadDuLieuLenHeThong() {
		var data = {
			"fileKy": 	noiDungFileKy,
			"fileDangKy": noiDungFileDangKy
			
		};
		$.ajax({
			url:'${contextPath}/ekyc-enterprise/luu-tru-file',
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