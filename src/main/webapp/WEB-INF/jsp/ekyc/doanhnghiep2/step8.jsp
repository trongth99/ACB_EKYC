<%@ page contentType="text/html; charset=UTF-8"%>
<div class="panel panel-primary setup-content" id="step-8">
	<div class="panel-heading">
		<h3 class="panel-title"><spring:message code="ekycdn.xem_noi_dung_file_ky" /></h3>
	</div>
	<div class="panel-body">
		<div class="form-group">
			<iframe id="base64FilePdf2"
					style="width: 100%; height: 600px; border: 0;"></iframe>
		</div>
		<textarea style="display: none;" id="base64Cert" name="base64Cert"></textarea>
		<button class="btn btn-primary nextBtn pull-right" type="button" onclick="nextStep(this)" data-loading-text="<i class='fa fa-circle-o-notch fa-spin'></i> <spring:message code="ekycdn.dang_xy_ly" />"><spring:message code="ekycdn.tiep_theo" /></button>
	</div>
</div>