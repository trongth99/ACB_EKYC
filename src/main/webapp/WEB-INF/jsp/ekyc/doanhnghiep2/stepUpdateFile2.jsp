<%@ page contentType="text/html; charset=UTF-8"%>
<div class="panel panel-primary setup-content" id="step-2">
	<div class="panel-heading">
		<h3 class="panel-title">Company Info / Thông tin công ty</h3>
	</div>
	<div class="panel-body">
		<div class="row">
			<div class="col-sm-12">
				<div class="form-group  has-feedback">
					<label class="control-label">Full Legal Name (*) <br/><i style="font-weight: normal;">Tên pháp lý đầy đủ (*)</i></label>
					<input  type="text"  class="form-control input-sm" id="nameOfTheAccountHolder" value="<c:out value='${ekycDoanhNghiep.nameOfTheAccountHolder}'/>"/>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-sm-6">
				<div class="form-group  has-feedback">
					<label class="control-label">Number  <br/><i style="font-weight: normal;">Số</i></label>
					<input  type="text"  class="form-control input-sm"  id="number" name="number" value="<c:out value='${ekycDoanhNghiep.number}'/>"/>
				</div>
			</div>
			<div class="col-sm-6">
				<div class="form-group  has-feedback">
					<label class="control-label">Date (Account Opening Application Form)   <br/><i style="font-weight: normal;">Ngày (Mẫu đơn đăng ký mở tài khoản)</i></label>
					<input  type="text"  class="form-control input-sm datepicker" id="dateAccountOpening" value="<c:out value='${ekycDoanhNghiep.dateAccountOpening}'/>"/>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-sm-6">
				<div class="form-group  has-feedback">
					<label class="control-label">Name (*) <br/><i style="font-weight: normal;">Tên (*)</i></label>
					<input  type="text"  class="form-control input-sm"  id="nameCompany" name="nameCompany" value="<c:out value='${ekycDoanhNghiep.nameCompany}'/>"/>
				</div>
			</div>
			<div class="col-sm-6">
				<div class="form-group  has-feedback">
					<label class="control-label">Registered Address (*) <br/><i style="font-weight: normal;">Địa chỉ trụ sở chính (*)</i></label>
					<input  type="text"  class="form-control input-sm"  id="registeredAddress" name="registeredAddress" value="<c:out value='${ekycDoanhNghiep.registeredAddress}'/>"/>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-sm-6">
				<div class="form-group  has-feedback">
					<label class="control-label">Operating address (*) <br/><i style="font-weight: normal;">Địa chỉ hoạt động (*)</i></label>
					<input  type="text"  class="form-control input-sm"  id="operatingAddress" name="operatingAddress" value="<c:out value='${ekycDoanhNghiep.operatingAddress}'/>"/>
				</div>
			</div>
			<div class="col-sm-6">
				<div class="form-group  has-feedback">
					<label class="control-label">Country of Incorporation (*)<br/><i style="font-weight: normal;">Quốc gia nơi thành lập (*)</i></label>
					<input  type="text"  class="form-control input-sm"  id="countryOfIncorporation" name="countryOfIncorporation" value="<c:out value='${ekycDoanhNghiep.countryOfIncorporation}'/>"/>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-sm-6">
				<div class="form-group  has-feedback">
					<label class="control-label">Registration Number (*)<br/><i style="font-weight: normal;">Số quyết định/giấy phép/đăng ký (*)</i></label>
					<input  type="text"  class="form-control input-sm"  id="registrationNumber" name="registrationNumber" value="<c:out value='${ekycDoanhNghiep.registrationNumber}'/>"/>
				</div>
			</div>
			<div class="col-sm-6">
				<div class="form-group  has-feedback">
					<label class="control-label">Mailing Address (*)<br/><i style="font-weight: normal;">Địa chỉ gửi thư (*)</i></label>
					<input  type="text"  class="form-control input-sm"  id="mailingAddress" name="mailingAddress" value="<c:out value='${ekycDoanhNghiep.mailingAddress}'/>"/>
				</div>
			</div>
			<%-- <div class="col-sm-6">
				<div class="form-group  has-feedback">
					<label class="control-label">Straight2Bank Group ID (If applicable)<br/><i style="font-weight: normal;">Mã nhóm Straight2Bank (Nếu có)</i></label>
					<input  type="text"  class="form-control input-sm"  id="straight2BankGroupID" name="straight2BankGroupID" value="<c:out value='${ekycDoanhNghiep.straight2BankGroupID}'/>"/>
				</div>
			</div> --%>
		</div>		
		<div class="row">
			
			<div class="col-sm-6">
				<div class="form-group  has-feedback">
					<label class="control-label">SWIFT Bank ID Code (If applicable)<br/><i style="font-weight: normal;">Mã số SWIFT Ngân hàng (Nếu có)</i></label>
					<input  type="text"  class="form-control input-sm"  id="swiftBankIDCode" name="swiftBankIDCode" value="<c:out value='${ekycDoanhNghiep.swiftBankIDCode}'/>"/>
				</div>
			</div>
		</div>
		<hr/>
		<div class="row">
			<div class="col-sm-12">
				<div class="form-group  has-feedback">
					<label class="control-label" style="">Mobile (*)<br/><i style="font-weight: normal;">Di động</i> <input  type="radio"  name="mobileOfficeTelephoneRadio" value="yes"/></label>
					<label class="control-label" style="margin-left: 20px;">Office Telephone (*)<br/><i style="font-weight: normal;">Điện thoại văn phòng</i> <input  type="radio"  name="mobileOfficeTelephoneRadio" value="no"/></label>
					<input  type="text"  class="form-control input-sm"  id="mobileOfficeTelephone" name="mobileOfficeTelephone" value="<c:out value='${ekycDoanhNghiep.mobileOfficeTelephone}'/>"/>
				</div>
			</div>
		</div>
		<hr/>
		<div class="row">
			<div class="col-sm-6">
				<div class="form-group  has-feedback">
					<label class="control-label">Contact Person (*)<br/><i style="font-weight: normal;">Người liên lạc (*)</i></label>
					<input  type="text"  class="form-control input-sm"  id="contactPerson" name="contactPerson" value="<c:out value='${ekycDoanhNghiep.contactPerson}'/>"/>
				</div>
			</div>
			<div class="col-sm-6">
				<div class="form-group  has-feedback">
					<label class="control-label">Email Address (*)<br/><i style="font-weight: normal;">Địa chỉ Email (*)</i></label>
					<input  type="text"  class="form-control input-sm"  id="emailAddress" name="emailAddress" value="<c:out value='${ekycDoanhNghiep.emailAddress}'/>"/>
				</div>
			</div>
		</div>
		<hr/>
		<div id="divTemplateTk">
			<div style="margin-bottom: 10px;" id="templateTk">

				<div class="row" style="background: #CCC; padding: 5px 0;">

					<c:forEach items="${listAccount}" var="item">
						<div class="col-sm-4">
							<div class="form-group  has-feedback">
								<label class="control-label">Account Type (*)<br /> <i
									style="font-weight: normal;">Loại Tài Khoản (*)</i></label> <select
									class="form-control input-sm" id="accountType"
									name="accountType">
									<option value="">-- Account Type/Loại Tài Khoản --</option>
									<option value="Payment account  / TK thanh toán"
										<c:if test="${item.accountType eq 'Payment account  / TK thanh toán' }">selected="selected"</c:if>>Payment
										account / TK thanh toán</option>
									<option value="DICA / Tài khoản đầu tư trực tiếp nước ngoài"
										<c:if test="${item.accountType eq 'DICA / Tài khoản đầu tư trực tiếp nước ngoài' }">selected="selected"</c:if>>DICA
										/ Tài khoản đầu tư trực tiếp nước ngoài</option>
									<option value="IICA / Tài khoản đầu tư gián tiếp nước ngoài"
										<c:if test="${item.accountType eq 'IICA / Tài khoản đầu tư gián tiếp nước ngoài' }">selected="selected"</c:if>>IICA
										/ Tài khoản đầu tư gián tiếp nước ngoài</option>
									<option
										value="Offshore Loan account / TK vay – trả nợ nước ngoài"
										<c:if test="${item.accountType eq 'Offshore Loan account / TK vay – trả nợ nước ngoài' }">selected="selected"</c:if>>Offshore
										Loan account / TK vay – trả nợ nước ngoài</option>
								</select>
							</div>
						</div>
						<div class="col-sm-4">
							<div class="form-group  has-feedback">
								<label class="control-label">Currency (*)<br /> <i
									style="font-weight: normal;">Loại tiền tệ (*)</i></label> <select
									class="form-control input-sm" id="currency" name="currency">
									<option value="">-- Currency/Loại tiền tệ --</option>
									<option value="VND"
										<c:if test="${item.currency eq 'VND' }">selected="selected"</c:if>>VND</option>
									<option value="USD"
										<c:if test="${item.currency eq 'USD' }">selected="selected"</c:if>>USD</option>
									<option value="GBP"
										<c:if test="${item.currency eq 'GBP' }">selected="selected"</c:if>>GBP</option>
									<option value="EUR"
										<c:if test="${item.currency eq 'EUR' }">selected="selected"</c:if>>EUR</option>
									<option value="AUD"
										<c:if test="${item.currency eq 'AUD' }">selected="selected"</c:if>>AUD</option>
									<option value="CHF"
										<c:if test="${item.currency eq 'CHF' }">selected="selected"</c:if>>CHF</option>
									<option value="SGD"
										<c:if test="${item.currency eq 'SGD' }">selected="selected"</c:if>>SGD</option>
									<option value="HKD"
										<c:if test="${item.currency eq 'HKD' }">selected="selected"</c:if>>HKD</option>
									<option value="CAD"
										<c:if test="${item.currency eq 'CAD' }">selected="selected"</c:if>>CAD</option>
									<option value="JPY"
										<c:if test="${item.currency eq 'JPY' }">selected="selected"</c:if>>JPY</option>
									<option value="THB"
										<c:if test="${item.currency eq 'THB' }">selected="selected"</c:if>>THB</option>
									<option value="DKK"
										<c:if test="${item.currency eq 'DKK' }">selected="selected"</c:if>>DKK</option>
									<option value="SEK"
										<c:if test="${item.currency eq 'SEK' }">selected="selected"</c:if>>SEK</option>
									<option value="NOK"
										<c:if test="${item.currency eq 'NOK' }">selected="selected"</c:if>>NOK</option>
								</select>
							</div>
						</div>
						<div class="col-sm-4" style="">
							<div class="form-group  has-feedback">
								<label class="control-label">Account Title<br /> <i
									style="font-weight: normal;">Tên Tài Khoản</i></label> <input
									type="text" class="form-control input-sm" id="accountTitle"
									name="accountTitle"
									value="<c:out value='${item.accountTitle}'/>" />
							</div>
						</div>
					</c:forEach>
				</div>

			</div>
		</div>
		<button type="button" style="margin-top: 10px;" id="themTempalteTk">
			<i class="fa fa-plus" aria-hidden="true"></i>
		</button>
		<button type="button" style="margin-top: 10px;" id="boTempalteTk">
			<i class="fa fa-minus minus" aria-hidden="true"></i>
		</button>
		<hr/>
		<div class="row">
			<div class="col-sm-6">
				<div class="form-group  has-feedback">
					<label class="control-label">Registering email address for monthly statement, debit/credit advice, e-invoice<br/><i style="font-weight: normal;">Đăng ký địa chỉ thư điện tử để nhận sao kê hàng tháng, giấy báo nợ/có, hóa đơn điện tử</i></label>
					<input  type="text"  class="form-control input-sm"  id="registeringEmailAddress" name="registeringEmailAddress" value="<c:out value='${ekycDoanhNghiep.registeringEmailAddress}'/>"/>
				</div>
			</div>

			<div class="col-sm-6">
				<div class="form-group  has-feedback">
					<label class="control-label">Short Name (If applicable)<br/><i style="font-weight: normal;">Tên viết tắt (Nếu có)</i></label>
					<input  type="text"  class="form-control input-sm"  id="shortName" name="shortName" value="<c:out value='${ekycDoanhNghiep.shortName}'/>"/>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-sm-6">
				<div class="form-group  has-feedback">
					<label class="control-label">Name in English (In line with constitutional documents)<br/><i style="font-weight: normal;">Tên bằng tiếng Anh (Theo các văn kiện thành lập)</i></label>
					<input  type="text"  class="form-control input-sm"  id="nameInEnglish" name="nameInEnglish" value="<c:out value='${ekycDoanhNghiep.nameInEnglish}'/>"/>
				</div>
			</div>
			<div class="col-sm-6">
				<div class="form-group  has-feedback">
					<label class="control-label">Fax Number (If utilise)<br/><i style="font-weight: normal;">Số fax (Nếu sử dụng)</i></label>
					<input  type="text"  class="form-control input-sm"  id="faxNumber" name="faxNumber" value="<c:out value='${ekycDoanhNghiep.faxNumber}'/>"/>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-sm-6">
				<div class="form-group  has-feedback">
					<label class="control-label">Tax Code (If different from enterprise’s registration number)<br/><i style="font-weight: normal;">Mã số thuế (Nếu khác với số chứng nhận đăng ký doanh nghiệp)</i></label>
					<input  type="text"  class="form-control input-sm"  id="taxCode" name="taxCode" value="<c:out value='${ekycDoanhNghiep.taxCode}'/>"/>
				</div>
			</div>
			<div class="col-sm-6">
				<div class="form-group  has-feedback">
					<label class="control-label">Applicable Accounting Systems<br/><i style="font-weight: normal;">Chế độ kế toán áp dụng</i></label>
					<select class="form-control input-sm" id="applicableAccountingSystems" name="applicableAccountingSystems">
						<option value=""> -- Applicable Accounting Systems/Chế độ kế toán áp dụng -- </option>
						<option value="Vietnamese Accounting Regime/Chế độ kế toán Việt Nam" <c:if test="${ekycDoanhNghiep.applicableAccountingSystems eq 'Vietnamese Accounting Regime/Chế độ kế toán Việt Nam' }">selected="selected"</c:if>>Vietnamese Accounting Regime/Chế độ kế toán Việt Nam</option>
						<option value="Others (Please specify)/Khác (Đề nghị nêu cụ thể)" <c:if test="${ekycDoanhNghiep.applicableAccountingSystems eq 'Others (Please specify)/Khác (Đề nghị nêu cụ thể)' }">selected="selected"</c:if>>Others (Please specify)/Khác (Đề nghị nêu cụ thể)</option>
					</select>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-sm-6">
				<div class="form-group  has-feedback">
					<label class="control-label">Tax Mode <br/><i style="font-weight: normal;">Phương thức khai/nộp thuế</i></label>
					<select class="form-control input-sm" id="taxMode" name="taxMode">
						<option value=""> -- Tax Mode/Phương thức khai/nộp thuế -- </option>
						<option value="Direct / Trực tiếp khai nộp thuế" <c:if test="${ekycDoanhNghiep.taxMode eq 'Direct / Trực tiếp khai nộp thuế' }">selected="selected"</c:if>>Direct / Trực tiếp khai nộp thuế</option>
						<option value="Withholding / Khấu trừ tại nguồn bởi đơn vị trả thu nhập" <c:if test="${ekycDoanhNghiep.taxMode eq 'Withholding / Khấu trừ tại nguồn bởi đơn vị trả thu nhập' }">selected="selected"</c:if>>Withholding / Khấu trừ tại nguồn bởi đơn vị trả thu nhập</option>
					</select>
				</div>
			</div>
			<div class="col-sm-6">
				<div class="form-group  has-feedback">
					<label class="control-label">Resident Status (*)<br/><i style="font-weight: normal;">Tình Trạng Cư Trú (*)</i></label>
					<select class="form-control input-sm" id="residentStatus" name="residentStatus">
						<option value=""> -- Resident Status/Tình Trạng Cư Trú -- </option>
						<option value="Resident / Người Cư Trú" <c:if test="${ekycDoanhNghiep.residentStatus eq 'Resident / Người Cư Trú' }">selected="selected"</c:if>>Resident / Người Cư Trú</option>
						<option value="Non-Resident / Người Không Cư Trú" <c:if test="${ekycDoanhNghiep.residentStatus eq 'Non-Resident / Người Không Cư Trú' }">selected="selected"</c:if>>Non-Resident / Người Không Cư Trú</option>
					</select>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-sm-6">
				<div class="form-group  has-feedback">
					<label class="control-label">Business activities (*)<br/><i style="font-weight: normal;">Hoạt động kinh doanh (*)</i></label>
					<input  type="text"  class="form-control input-sm"  id="businessActivities" name="businessActivities" value="<c:out value='${ekycDoanhNghiep.businessActivities}'/>"/>
				</div>
			</div>
			<div class="col-sm-6">
				<div class="form-group  has-feedback">
					<label class="control-label">Yearly average number of employee contributing to Social Insurance fund<br/><i style="font-weight: normal;">Số lao động tham gia bảo hiểm xã hội bình quân năm</i></label>
					<input  type="text"  class="form-control input-sm"  id="yearlyAveragenumber" name="yearlyAveragenumber" value="<c:out value='${ekycDoanhNghiep.yearlyAveragenumber}'/>"/>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-sm-6">
				<div class="form-group  has-feedback">
					<label class="control-label">Total Sales Turnover (*)<br/><i style="font-weight: normal;">Tổng doanh thu (*)</i></label>
					<input  type="text"  class="form-control input-sm"  id="totalSalesTurnover" name="totalSalesTurnover" value="<c:out value='${ekycDoanhNghiep.totalSalesTurnover}'/>"/>
				</div>
			</div>
			<div class="col-sm-6">
				<div class="form-group  has-feedback">
					<label class="control-label">Total capital (*)<br/><i style="font-weight: normal;">Tổng nguồn vốn (*)</i></label>
					<input  type="text"  class="form-control input-sm"  id="totalCapital" name="totalCapital" value="<c:out value='${ekycDoanhNghiep.totalCapital}'/>"/>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-sm-6">
				<div class="form-group  has-feedback">
					<label class="control-label">
						Agree to receive through all means communication<br/><i style="font-weight: normal;">Đồng ý nhận thông tin từ Ngân hàng qua mọi phương tiện liên lạc</i>
						<input  type="checkbox" style="margin-left: 20px;"  id="agreeToReceive" name="agreeToReceive" value="<c:out value='${ekycDoanhNghiep.agreeToReceive}'/>"/>
					</label>
				</div>
			</div>
		</div>
		<div class="row">
			<%-- <div class="col-sm-12">
				<div class="form-group  has-feedback">
					<label class="control-label">
						SC Relationship manager name <br/><i style="font-weight: normal;">SC tên người quản lý, quan hệ</i>
					</label>
					<input  type="text" class="form-control input-sm"  id="relationshipManagerName" name="relationshipManagerName" value="<c:out value='${ekycDoanhNghiep.relationshipManagerName}'/>"/>
				</div>
			</div> --%>
		</div>
		<button class="btn btn-primary nextBtn pull-right" type="button" onclick="validateStep2(this)" data-loading-text="<i class='fa fa-circle-o-notch fa-spin'></i> <spring:message code="ekycdn.dang_xy_ly" />" id='step2'><spring:message code="ekycdn.tiep_theo" /></button>
		<button class="btn btn-default pull-right" type="button" onclick="prevStep(this)" style="margin-right: 10px;"><spring:message code="ekycdn.quay_lai" /></button>
	</div>
</div>
<script type="text/javascript">
$(document).ready(function(){
	$("#themTempalteTk").click(function(){
		    $("#divTemplateTk").append($("#templateTk .row").clone());
	});
	$("#boTempalteTk").click(function(){
		if($("#divTemplateTk .row").length > 1)
			$("#divTemplateTk .row:last").remove();
	});
});

function validateStep2(obj) {
	if($("#nameOfTheAccountHolder").val() == "") {
		toastr.error("Full Legal Name is not empty");
	} else if($("#nameCompany").val() == "") {
		toastr.error("Name is not empty");
	} else if($("#registeredAddress").val() == "") {
		toastr.error("Registered Address is not empty");
	} else if($("#operatingAddress").val() == "") {
		toastr.error("Operating address is not empty");
	} else if($("#countryOfIncorporation").val() == "") {
		toastr.error("Country of Incorporation is not empty");
	} else if($("#registrationNumber").val() == "") {
		toastr.error("Registration Number is not empty");
	} else if($("#mailingAddress").val() == "") {
		toastr.error("Mailing Address is not empty");
	} else if($("#contactPerson").val() == "") {
		toastr.error("Contact Person is not empty");
	} else if($("#emailAddress").val() == "") {
		toastr.error("Email Address is not empty");
	} else if($("#accountType").val() == "") {
		toastr.error("Account Type is not empty");
	} else if($("#currency").val() == "") {
		toastr.error("Currency is not empty");
	} else if($("#residentStatus").val() == "") {
		toastr.error("Resident Status is not empty");
	} else if($("#businessActivities").val() == "") {
		toastr.error("Business activities is not empty");
	} else if($("#totalSalesTurnover").val() == "") {
		toastr.error("Total Sales Turnover is not empty");
	} else if($("#totalCapital").val() == "") {
		toastr.error("Total capital is not empty");
	} else {
		uploadDuLieuStep3(obj)
	}
}


var token = "";
function uploadDuLieuStep3(obj) {
	$(obj).button('loading');
	var data = {
		
		
		//step2
		"nameOfTheAccountHolder": 	$("#nameOfTheAccountHolder").val(),
		"number": 	$("#number").val(),
		"dateAccountOpening": 	$("#dateAccountOpening").val(),
		"nameCompany": 	$("#nameCompany").val(),
		"registeredAddress": 	$("#registeredAddress").val(),
		"operatingAddress": 	$("#operatingAddress").val(),
		"countryOfIncorporation": 	$("#countryOfIncorporation").val(),
		"registrationNumber": 	$("#registrationNumber").val(),
		"straight2BankGroupID": 	$("#straight2BankGroupID").val(),
		"mailingAddress": 	$("#mailingAddress").val(),
		"swiftBankIDCode": 	$("#swiftBankIDCode").val(),
		"contactPerson": 	$("#contactPerson").val(),
		"emailAddress": 	$("#emailAddress").val(),
		
		/* "accountType": 	$("#accountType").val(),
		"currency": 	$("#currency").val(),
		"accountTitle": 	$("#accountTitle").val(), */
		
		"listAccount":  getArrayAccount(),
		
		"registeringEmailAddress": 	$("#registeringEmailAddress").val(),
		"shortName": 	$("#shortName").val(),
		"nameInEnglish": 	$("#nameInEnglish").val(),
		"faxNumber": 	$("#faxNumber").val(),
		"taxCode": 	$("#taxCode").val(),
		"applicableAccountingSystems": 	$("#applicableAccountingSystems").val(),
		"taxMode": 	$("#taxMode").val(),
		"residentStatus": 	$("#residentStatus").val(),
		"businessActivities": 	$("#businessActivities").val(),
		"yearlyAveragenumber": 	$("#yearlyAveragenumber").val(),
		"totalSalesTurnover": 	$("#totalSalesTurnover").val(),
		"totalCapital": 	$("#totalCapital").val(),
		"companysSealRegistration": 	$("#companysSealRegistration").val(),
		"sampleOfTheSeal": 	$("#sampleOfTheSeal").val(),
		"agreeToReceive": 	$("#agreeToReceive").val(),
		"applicantsRepresentative": 	$("#applicantsRepresentative").val(),
		"relationshipManagerName": 	$("#relationshipManagerName").val(),
	
	};
	$.ajax({
		url:'${contextPath}/ekyc-enterprise/luu-thong-tin-step3',
	    data: JSON.stringify(data),
	    type: 'POST',
	    processData: false,
	    contentType: 'application/json'
	}).done(function(data) {
		if(data.status == 200) {
			token = data.token;
			nextStep(obj);
			$(obj).button('reset');
		} else if(data.status == 505){
			location.href='${contextPath}/ekyc-enterprise';
		} else {
			toastr.error("Not enough information to store / Không đủ thông tin cần lưu trữ");
			$(obj).button('reset');	
		}
	}).fail(function(data) {
		toastr.error("Error check / Lỗi lưu thông tin");
		$(obj).button('reset');
	}); 
}

function getArrayAccount() {
	var arr = [];
	console.log("hdbfhdsfds");
	console.log($( "select[name='accountType']" ).text());
	$("input[name='accountTitle']").each(function(index){
		console.log(index);
		var json = {};
		if($("input[name='accountType']").eq(index).val() != "" && $("input[name='currency']").eq(index).val() != "") {
			//var checkMain = $("input[name='checkMain"+type+"']:checked").eq(index).prop("checked")?"Y":"N";
			json["accountType"] = $( "select[name='accountType']" ).eq(index).find(":selected").val();
			json["currency"] = $( "select[name='currency']" ).eq(index).find(":selected").val();
			json["accountTitle"] = $("input[name='accountTitle']").eq(index).val();
			console.log($( "input[name='accountType']" ).eq(index).text());
	 		console.log(json);
			arr.push(json);
		}
	});
 	console.log(arr);
	return arr;
}
function uuidv4() {
	  return ([1e7]+-1e3+-4e3+-8e3+-1e11).replace(/[018]/g, c =>
	    (c ^ crypto.getRandomValues(new Uint8Array(1))[0] & 15 >> c / 4).toString(16)
	  );
	}
$(document).ready(function(){
	
});
</script>