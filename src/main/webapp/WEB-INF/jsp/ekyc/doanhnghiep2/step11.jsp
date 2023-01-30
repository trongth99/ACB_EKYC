<%@ page contentType="text/html; charset=UTF-8"%>
<div class="panel panel-primary setup-content" id="step-11">
	<div class="panel-heading">
		Complete the registration process
	</div>
	<div class="panel-body">
		<div class="form-group">
			<iframe id="base64FilePdf2"
					style="width: 100%; height: 600px; border: 0;"></iframe>
		</div>
		<div class="form-group text-center">
			<button class="btn btn-primary nextBtn pull-left " type="button" onclick="javascript:location.href='${contextPath }/ekyc-enterprise'" data-loading-text="<i class='fa fa-circle-o-notch fa-spin'></i> <spring:message code="ekycdn.dang_xy_ly" />" id="textLoad4"><spring:message code="ekycdn.ket_thuc" /></button>
		</div>
	</div>
</div>