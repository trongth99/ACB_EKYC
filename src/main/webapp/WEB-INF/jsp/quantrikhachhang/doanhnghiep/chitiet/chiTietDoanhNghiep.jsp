<%@ page contentType="text/html; charset=UTF-8"%>
<div class="row clearfix">
	<div>
		<div class="panel-body">
			<div class="row">
				<div class="col-sm-12">
					<div class="form-group  has-feedback">
						<label class="control-label">Full Legal Name (*) <br/>
							<i style="font-weight: normal;">Tên pháp lý đầy đủ (*)</i>
						</label>
						<input type="text" class="form-control form-control-sm"  id="nameOfTheAccountHolder" name="nameOfTheAccountHolder" value="<c:out value='${ekycDoanhNghiep.nameOfTheAccountHolder}'/>"/>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-6">
					<div class="form-group  has-feedback">
						<label class="control-label">Number <br/>
							<i style="font-weight: normal;">Số</i>
						</label>
						<input type="text" class="form-control form-control-sm"  id="number" name="number" value="<c:out value='${ekycDoanhNghiep.number}'/>"/>
					</div>
				</div>
				<div class="col-sm-6">
					<div class="form-group  has-feedback">
						<label class="control-label">Date (Account Opening
							Application Form) <br/>
							<i style="font-weight: normal;">Ngày
								(Mẫu đơn đăng ký mở tài khoản)</i>
						</label>
						<input type="text" class="form-control form-control-sm"  id="dateAccountOpening" value="<c:out value='${ekycDoanhNghiep.dateAccountOpening}'/>"/>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-6">
					<div class="form-group  has-feedback">
						<label class="control-label">Name (*) <br/>
							<i style="font-weight: normal;">Tên (*)</i>
						</label>
						<input type="text" class="form-control form-control-sm"  id="nameCompany" name="nameCompany" value="<c:out value='${ekycDoanhNghiep.nameCompany}'/>"/>
					</div>
				</div>
				<div class="col-sm-6">
					<div class="form-group  has-feedback">
						<label class="control-label">Registered Address (*) <br/>
							<i style="font-weight: normal;">Địa chỉ trụ sở chính (*)</i>
						</label>
						<input type="text" class="form-control form-control-sm"  id="registeredAddress" name="registeredAddress" value="<c:out value='${ekycDoanhNghiep.registeredAddress}'/>"/>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-6">
					<div class="form-group  has-feedback">
						<label class="control-label">Operating address (*) <br/>
							<i style="font-weight: normal;">Địa chỉ hoạt động (*)</i>
						</label>
						<input type="text" class="form-control form-control-sm"  id="operatingAddress" name="operatingAddress" value="<c:out value='${ekycDoanhNghiep.operatingAddress}'/>"/>
					</div>
				</div>
				<div class="col-sm-6">
					<div class="form-group  has-feedback">
						<label class="control-label">Country of Incorporation (*)<br/>
							<i style="font-weight: normal;">Quốc gia nơi thành lập (*)</i>
						</label>
						<input type="text" class="form-control form-control-sm"  id="countryOfIncorporation" name="countryOfIncorporation" value="<c:out value='${ekycDoanhNghiep.countryOfIncorporation}'/>"/>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-6">
					<div class="form-group  has-feedback">
						<label class="control-label">Registration Number (*)<br/>
							<i style="font-weight: normal;">Số quyết định/giấy phép/đăng
								ký (*)</i>
						</label>
						<input type="text" class="form-control form-control-sm"  id="registrationNumber" name="registrationNumber" value="<c:out value='${ekycDoanhNghiep.registrationNumber}'/>"/>
					</div>
				</div>
				<div class="col-sm-6">
					<div class="form-group  has-feedback">
						<label class="control-label">Mailing Address (*)<br/>
							<i style="font-weight: normal;">Địa chỉ gửi thư (*)</i>
						</label>
						<input type="text" class="form-control form-control-sm"  id="mailingAddress" name="mailingAddress" value="<c:out value='${ekycDoanhNghiep.mailingAddress}'/>"/>
					</div>
				</div>
				<%-- <div class="col-sm-6">
					<div class="form-group  has-feedback">
						<label class="control-label">Straight2Bank Group ID (If
							applicable)<br/>
							<i style="font-weight: normal;">Mã nhóm
								Straight2Bank (Nếu có)</i>
						</label>
						<input type="text" class="form-control form-control-sm"  id="straight2BankGroupID" name="straight2BankGroupID" value="<c:out value='${ekycDoanhNghiep.straight2BankGroupID}'/>"/>
					</div>
				</div> --%>
			</div>
			<div class="row">
				
				<div class="col-sm-6">
					<div class="form-group  has-feedback">
						<label class="control-label">SWIFT Bank ID Code (If
							applicable)<br/>
							<i style="font-weight: normal;">Mã số
								SWIFT Ngân hàng (Nếu có)</i>
						</label>
						<input type="text" class="form-control form-control-sm"  id="swiftBankIDCode" name="swiftBankIDCode" value="<c:out value='${ekycDoanhNghiep.swiftBankIDCode}'/>"/>
					</div>
				</div>
			</div>
			<hr/>
			<div class="row">
				<div class="col-sm-12">
					<div class="form-group  has-feedback">
						<label class="control-label" style="">Mobile <br/>
							<i style="font-weight: normal;">Di động</i>
							<input type="radio" name="mobileOfficeTelephoneRadio" value="yes"/>
						</label>
						<label class="control-label" style="margin-left: 20px;">Office
							Telephone <br/>
							<i style="font-weight: normal;">Điện thoại
								văn phòng</i>
							<input type="radio" name="mobileOfficeTelephoneRadio" value="no"/>
						</label>
						<input type="text" class="form-control form-control-sm"  id="mobileOfficeTelephone" name="mobileOfficeTelephone" value="<c:out value='${ekycDoanhNghiep.mobileOfficeTelephone}'/>"/>
					</div>
				</div>
			</div>
			<hr/>
			<div class="row">
				<div class="col-sm-6">
					<div class="form-group  has-feedback">
						<label class="control-label">Contact Person (*)<br/>
							<i style="font-weight: normal;">Người liên lạc (*)</i>
						</label>
						<input type="text" class="form-control form-control-sm"  id="contactPerson" name="contactPerson" value="<c:out value='${ekycDoanhNghiep.contactPerson}'/>"/>
					</div>
				</div>
				<div class="col-sm-6">
					<div class="form-group  has-feedback">
						<label class="control-label">Email Address (*)<br/>
							<i style="font-weight: normal;">Địa chỉ Email (*)</i>
						</label>
						<input type="text" class="form-control form-control-sm"  id="emailAddress" name="emailAddress" value="<c:out value='${ekycDoanhNghiep.emailAddress}'/>"/>
					</div>
				</div>
			</div>
			<hr/>
			<div id="divTemplateTk">
				<div style="margin-bottom: 10px;" id="templateTk">
					<div class="row" style="background: #CCC; padding: 5px 0;">
						<c:forEach items="${ekycDoanhNghiepListAccount}" var="item">
							<div class="col-sm-4">
								<div class="form-group  has-feedback">
									<label class="control-label">Account Type (*)<br/>
										<i style="font-weight: normal;">Loại Tài Khoản (*)</i>
									</label>
									<select class="form-control form-control-sm"  id="accountType" name="accountType">
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
							<option value="Offshore Loan account / TK vay – trả nợ nước ngoài"
							<c:if test="${item.accountType eq 'Offshore Loan account / TK vay – trả nợ nước ngoài' }">selected="selected"</c:if>>Offshore
											Loan account / TK vay – trả nợ nước ngoài</option>
					</select>
				</div>
			</div>
			<div class="col-sm-4">
				<div class="form-group  has-feedback">
					<label class="control-label">Currency (*)<br/>
						<i style="font-weight: normal;">Loại tiền tệ (*)</i>
					</label>
					<select class="form-control form-control-sm"  id="currency" name="currency">
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
		<label class="control-label">Account Title<br/>
			<i style="font-weight: normal;">Tên Tài Khoản</i>
		</label>
		<input type="text" class="form-control form-control-sm"  id="accountTitle" name="accountTitle" value="<c:out value='${item.accountTitle}'/>"/>
	</div>
</div>
</c:forEach>
</div>
</div>
</div>
<hr/>
<div class="row">
	<div class="col-sm-6">
		<div class="form-group  has-feedback">
			<label class="control-label">Registering email address for
							monthly statement, debit/credit advice, e-invoice<br/>
				<i style="font-weight: normal;">Đăng ký địa chỉ thư điện tử để
								nhận sao kê hàng tháng, giấy báo nợ/có, hóa đơn điện tử</i>
			</label>
			<input type="text" class="form-control form-control-sm"  id="registeringEmailAddress" name="registeringEmailAddress" value="<c:out value='${ekycDoanhNghiep.registeringEmailAddress}'/>"/>
		</div>
	</div>
	<div class="col-sm-6">
		<div class="form-group  has-feedback">
			<label class="control-label">Short Name (If applicable)<br/>
				<i style="font-weight: normal;">Tên viết tắt (Nếu có)</i>
			</label>
			<input type="text" class="form-control form-control-sm"  id="shortName" name="shortName" value="<c:out value='${ekycDoanhNghiep.shortName}'/>"/>
		</div>
	</div>
</div>
<div class="row">
	<div class="col-sm-6">
		<div class="form-group  has-feedback">
			<label class="control-label">Name in English (In line with
							constitutional documents)<br/>
				<i style="font-weight: normal;">Tên
								bằng tiếng Anh (Theo các văn kiện thành lập)</i>
			</label>
			<input type="text" class="form-control form-control-sm"  id="nameInEnglish" name="nameInEnglish" value="<c:out value='${ekycDoanhNghiep.nameInEnglish}'/>"/>
		</div>
	</div>
	<div class="col-sm-6">
		<div class="form-group  has-feedback">
			<label class="control-label">Fax Number (If utilise)<br/>
				<i style="font-weight: normal;">Số fax (Nếu sử dụng)</i>
			</label>
			<input type="text" class="form-control form-control-sm"  id="faxNumber" name="faxNumber" value="<c:out value='${ekycDoanhNghiep.faxNumber}'/>"/>
		</div>
	</div>
</div>
<div class="row">
			<div class="col-sm-6">
				<div class="form-group  has-feedback">
					<label class="control-label">Tax Code (If different from
						enterprise’s registration number)<br />
					<i style="font-weight: normal;">Mã số thuế (Nếu khác với số
							chứng nhận đăng ký doanh nghiệp)</i>
					</label> <input type="text" class="form-control input-sm" id="taxCode"
						name="taxCode" value="<c:out value='${ekycDoanhNghiep.taxCode}'/>"/>
				</div>
			</div>
			<div class="col-sm-6">
				<div class="form-group  has-feedback">
					<label class="control-label">Applicable Accounting Systems<br />
					<i style="font-weight: normal;">Chế độ kế toán áp dụng</i></label> <select
						class="form-control input-sm" id="applicableAccountingSystems"
						name="applicableAccountingSystems">
						<option value="">-- Applicable Accounting Systems/Chế độ
							kế toán áp dụng --</option>
						<option
							value="Vietnamese Accounting Regime/Chế độ kế toán Việt Nam" <c:if test="${ekycDoanhNghiep.applicableAccountingSystems eq 'Vietnamese Accounting Regime/Chế độ kế toán Việt Nam' }">selected="selected"</c:if>>Vietnamese
							Accounting Regime/Chế độ kế toán Việt Nam</option>
						<option value="Others (Please specify)/Khác (Đề nghị nêu cụ thể)" <c:if test="${ekycDoanhNghiep.applicableAccountingSystems eq 'Others (Please specify)/Khác (Đề nghị nêu cụ thể)' }">selected="selected"</c:if>>Others
							(Please specify)/Khác (Đề nghị nêu cụ thể)</option>
					</select>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-sm-6">
				<div class="form-group  has-feedback">
					<label class="control-label">Tax Mode <br />
					<i style="font-weight: normal;">Phương thức khai/nộp thuế</i></label> <select
						class="form-control input-sm" id="taxMode" name="taxMode">
						<option value="">-- Tax Mode/Phương thức khai/nộp thuế --
						</option>
						<option value="Direct / Trực tiếp khai nộp thuế" <c:if test="${ekycDoanhNghiep.taxMode eq 'Direct / Trực tiếp khai nộp thuế' }">selected="selected"</c:if>>Direct /
							Trực tiếp khai nộp thuế</option>
						<option
							value="Withholding / Khấu trừ tại nguồn bởi đơn vị trả thu nhập" <c:if test="${ekycDoanhNghiep.taxMode eq 'Withholding / Khấu trừ tại nguồn bởi đơn vị trả thu nhập' }">selected="selected"</c:if>>Withholding
							/ Khấu trừ tại nguồn bởi đơn vị trả thu nhập</option>
					</select>
				</div>
			</div>
			<div class="col-sm-6">
				<div class="form-group  has-feedback">
					<label class="control-label">Resident Status (*)<br />
					<i style="font-weight: normal;">Tình Trạng Cư Trú (*)</i></label> <select
						class="form-control input-sm" id="residentStatus"
						name="residentStatus">
						<option value="">-- Resident Status/Tình Trạng Cư Trú --
						</option>
						<option value="Resident / Người Cư Trú" <c:if test="${ekycDoanhNghiep.residentStatus eq 'Resident / Người Cư Trú' }">selected="selected"</c:if>>Resident / Người
							Cư Trú</option>
						<option value="Non-Resident / Người Không Cư Trú" <c:if test="${ekycDoanhNghiep.residentStatus eq 'Non-Resident / Người Không Cư Trú' }">selected="selected"</c:if>>Non-Resident
							/ Người Không Cư Trú</option>
					</select>
				</div>
			</div>
		</div>
<div class="row">
	<div class="col-sm-6">
		<div class="form-group  has-feedback">
			<label class="control-label">Business activities (*)<br/>
				<i style="font-weight: normal;">Hoạt động kinh doanh (*)</i>
			</label>
			<input type="text" class="form-control form-control-sm"  id="businessActivities" name="businessActivities" value="<c:out value='${ekycDoanhNghiep.businessActivities}'/>"/>
		</div>
	</div>
	<div class="col-sm-6">
		<div class="form-group  has-feedback">
			<label class="control-label">Yearly average number of
							employee contributing to Social Insurance fund<br/>
				<i style="font-weight: normal;">Số lao động tham gia bảo hiểm
								xã hội bình quân năm</i>
			</label>
			<input type="text" class="form-control form-control-sm"  id="yearlyAveragenumber" name="yearlyAveragenumber" value="<c:out value='${ekycDoanhNghiep.yearlyAveragenumber}'/>"/>
		</div>
	</div>
</div>
<div class="row">
	<div class="col-sm-6">
		<div class="form-group  has-feedback">
			<label class="control-label">Total Sales Turnover (*)<br/>
				<i style="font-weight: normal;">Tổng doanh thu (*)</i>
			</label>
			<input type="text" class="form-control form-control-sm"  id="totalSalesTurnover" name="totalSalesTurnover" value="<c:out value='${ekycDoanhNghiep.totalSalesTurnover}'/>"/>
		</div>
	</div>
	<div class="col-sm-6">
		<div class="form-group  has-feedback">
			<label class="control-label">Total capital (*)<br/>
				<i style="font-weight: normal;">Tổng nguồn vốn (*)</i>
			</label>
			<input type="text" class="form-control form-control-sm"  id="totalCapital" name="totalCapital" value="<c:out value='${ekycDoanhNghiep.totalCapital}'/>"/>
		</div>
	</div>
</div>
<div class="row">
	<div class="col-sm-6">
		<div class="form-group  has-feedback">
			<label class="control-label"> Agree to receive through all
							means communication<br/>
				<i style="font-weight: normal;">Đồng ý nhận thông tin từ Ngân
								hàng qua mọi phương tiện liên lạc</i>
				<input type="checkbox" style="margin-left: 20px;" id="agreeToReceive" name="agreeToReceive" value="<c:out value='${ekycDoanhNghiep.agreeToReceive}'/>"/>
			</label>
		</div>
	</div>
</div>
<div class="row">
	<%-- <div class="col-sm-12">
		<div class="form-group  has-feedback">
			<label class="control-label"> SC Relationship manager name
							<br/>
				<i style="font-weight: normal;">SC tên người quản lý, quan hệ</i>
			</label>
			<input type="text" class="form-control form-control-sm"  id="relationshipManagerName" name="relationshipManagerName" value="<c:out value='${ekycDoanhNghiep.relationshipManagerName}'/>"/>
		</div>
	</div> --%>
</div>
</div>
</div>
<div style="clear: both;"/>
<c:if test="${not empty ekycDoanhNghiep.fileKy }">
	<hr style="color: #CCC; border-top: 1px solid #CCC; clear: both;"/>
	<h2>Opening an account</h2>
	<div class="col-sm-12">
		<iframe style="width: 100%; height: 400px; border: 0;" src="${contextPath }/fisbank/ekyc-doanh-nghiep/pdf-byte?path=${ekycDoanhNghiep.fileKy }"/>
	</div>
	<div style="clear: both;"/>
</c:if>
<c:if test="${not empty ekycDoanhNghiep.fileBusinessRegistration }">
	<hr style="color: #CCC; border-top: 1px solid #CCC; clear: both;"/>
	<h2>Certificate of business registration</h2>
	<div class="col-sm-12">
		<iframe style="width: 100%; height: 400px; border: 0;" src="${contextPath }/fisbank/ekyc-doanh-nghiep/pdf-byte?path=${ekycDoanhNghiep.fileBusinessRegistration }"/>
	</div>
	<div style="clear: both;"/>
</c:if>
<c:if test="${not empty ekycDoanhNghiep.fileAppointmentOfChiefAccountant }">
	<hr style="color: #CCC; border-top: 1px solid #CCC; clear: both;"/>
	<h2>Decision on appointment of chief accountant</h2>
	<div class="col-sm-12">
		<iframe style="width: 100%; height: 400px; border: 0;" src="${contextPath }/fisbank/ekyc-doanh-nghiep/pdf-byte?path=${ekycDoanhNghiep.fileAppointmentOfChiefAccountant }"/>
	</div>
	<div style="clear: both;"/>
</c:if>
<c:if test="${not empty ekycDoanhNghiep.fileInvestmentCertificate }">
	<hr style="color: #CCC; border-top: 1px solid #CCC; clear: both;"/>
	<h2>Investment certificate</h2>
	<div class="col-sm-12">
		<iframe style="width: 100%; height: 400px; border: 0;" src="${contextPath }/fisbank/ekyc-doanh-nghiep/pdf-byte?path=${ekycDoanhNghiep.fileInvestmentCertificate }"/>
	</div>
	<div style="clear: both;"/>
</c:if>
<c:if test="${not empty ekycDoanhNghiep.fileCompanyCharter }">
	<hr style="color: #CCC; border-top: 1px solid #CCC; clear: both;"/>
	<h2>Company charter</h2>
	<div class="col-sm-12">
		<iframe style="width: 100%; height: 400px; border: 0;" src="${contextPath }/fisbank/ekyc-doanh-nghiep/pdf-byte?path=${ekycDoanhNghiep.fileCompanyCharter }"/>
	</div>
	<div style="clear: both;"/>
</c:if>
<c:if test="${not empty ekycDoanhNghiep.fileSealSpecimen }">
	<hr style="color: #CCC; border-top: 1px solid #CCC; clear: both;"/>
	<h2>Seal specimen</h2>
	<div class="col-sm-12">
		<iframe style="width: 100%; height: 400px; border: 0;" src="${contextPath }/fisbank/ekyc-doanh-nghiep/pdf-byte?path=${ekycDoanhNghiep.fileSealSpecimen }"/>
	</div>
	<div style="clear: both;"/>
</c:if>
<c:if test="${not empty ekycDoanhNghiep.fileFatcaForms }">
	<hr style="color: #CCC; border-top: 1px solid #CCC; clear: both;"/>
	<h2>FATCA forms</h2>
	<div class="col-sm-12">
		<iframe style="width: 100%; height: 400px; border: 0;" src="${contextPath }/fisbank/ekyc-doanh-nghiep/pdf-byte?path=${ekycDoanhNghiep.fileFatcaForms }"/>
	</div>
	<div style="clear: both;"/>
</c:if>
<c:if test="${not empty ekycDoanhNghiep.fileOthers }">
	<hr style="color: #CCC; border-top: 1px solid #CCC; clear: both;"/>
	<h2>Others</h2>
	<div class="col-sm-12">
		<iframe style="width: 100%; height: 400px; border: 0;" src="${contextPath }/fisbank/ekyc-doanh-nghiep/pdf-byte?path=${ekycDoanhNghiep.fileOthers }"/>
	</div>
	<div style="clear: both;"/>
</c:if>
</div>
<script type="text/javascript">
	var successIcon = '<span class="glyphicon glyphicon-ok form-control-feedback"/>';
	var successClass = 'is-valid';
	var errorIcon = '<span class="glyphicon glyphicon-remove form-control-feedback"/>';
	var errorClass = 'is-invalid';
	$(document)
			.ready(
					function() {
						$("#registrationNumber").val('${ekycDoanhNghiep.registrationNumber}');
						addCheckClass('${ekycDoanhNghiep.registrationNumber}','${ktraDoanhNghiep.maSoDoanhNghiep}', "string","registrationNumber");
						
						$("#nameOfTheAccountHolder").val('${ekycDoanhNghiep.nameCompany}');
						addCheckClass('${ekycDoanhNghiep.nameCompany}','${ktraDoanhNghiep.tenDoanhNghiep}', "string","nameOfTheAccountHolder");

						$("#nameCompany").val('${ekycDoanhNghiep.nameCompany}');
						addCheckClass('${ekycDoanhNghiep.nameCompany}','${ktraDoanhNghiep.tenDoanhNghiep}', "string","nameCompany");
						
						$("#nameInEnglish").val('${ekycDoanhNghiep.nameInEnglish}');
						addCheckClass('${ekycDoanhNghiep.nameInEnglish}','${ktraDoanhNghiep.tenDoanhNghiepEn}', "string","nameInEnglish");
						
						$("#shortName").val('${ekycDoanhNghiep.shortName}');
						addCheckClass('${ekycDoanhNghiep.shortName}','${ktraDoanhNghiep.tenDoanhNghiepVietTat}', "string","shortName");

						$("#registeredAddress").val('${ekycDoanhNghiep.registeredAddress}');
						addCheckClass('${ekycDoanhNghiep.registeredAddress}','${ktraDoanhNghiep.diaChi}',"string", "registeredAddress");

						$("#fax").val('${ekycDoanhNghiep.faxNumber}');
						$("#email").val('${ekycDoanhNghiep.emailAddress}');
						$("#dienThoai").val('${ekycDoanhNghiep.mobileOfficeTelephone}');

					});

	function addCheckClass(value, valueCheck, type, id) {
		if (type == "string") {
			if ((valueCheck != null && value != null && value.toLowerCase() == valueCheck
					.toLowerCase())) {
				$("#" + id).addClass(successClass);
			} else if (valueCheck == "" || valueCheck == null || value == ""
					|| value == null) {

			} else {
				$("#" + id).addClass(errorClass);
				$("#" + id).parent().append(
						'<span class="help-block">' + valueCheck + '</span>');
			}
		}
	}
	function numberWithCommas(x) {
		if (x && x.trim() != "")
			return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");

		return "";
	}
</script>