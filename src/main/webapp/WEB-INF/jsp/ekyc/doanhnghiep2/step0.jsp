<%@ page contentType="text/html; charset=UTF-8"%>
<div class="panel panel-primary setup-content" id="step-0">
	<div class="panel-heading">
		<h3 class="panel-title"><spring:message code="ekycdn.guideline" /></h3>
	</div>
	<div class="panel-body">
		<p>
			<b>Step 1: Upload documents as follows:</b><br/>
			<i>Bước 1: Tải lên tài liệu như sau:</i>
		</p>
		<ul>
			<li>Certificate of business registration <br/> <i>Giấy chứng nhận đăng ký kinh doanh</i></li>
			<li>Decision on appointment of chief accountant <br/> <i>Quyết định bổ nhiệm kế toán trưởng</i></li>
			<li>Investment certificate <br/> <i>Giấy chứng nhận đầu tư</i></li>
			<li>Company charter <br/> <i>Điều lệ công ty</i></li>
			<li>Seal specimen <br/> <i>Mẫu con dấu</i></li>
			<li>FATCA forms <br/> <i>Các biểu mẫu FATCA</i></li>
			<li>Others <br/><i>Khác</i></li>
		</ul>
		<p><b>Step 2: Filling Company information</b><br/><i>Bước 2: Điền thông tin công ty</i></p>
		<p><b>Step 3: Filling Legal representative</b><br/><i>Bước 3: Điền đại diện pháp lý</i></p>
		<p><b>Step 4: Filling Chief accountant information</b><br/><i>Bước 4: Điền thông tin Kế toán trưởng</i></p>
		<p><b>Step 5: Filling BoM info</b><br/><i>Bước 5: Điền thông tin HĐQT</i></p>
		<p><b>Step 6: Filling Special Instructions</b><br/><i>Bước 6: Điền các chỉ dẫn đặc biệt</i></p>
		<button class="btn btn-primary nextBtn pull-right" type="button" onclick="validateStep0(this)" data-loading-text="<i class='fa fa-circle-o-notch fa-spin'></i> <spring:message code="ekycdn.dang_xy_ly" />"><spring:message code="ekycdn.tiep_theo" /></button>
	</div>
</div>

<script type="text/javascript">
	
	function validateStep0(obj) {
		nextStep(obj);
	}
</script>