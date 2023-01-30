<%@ page contentType="text/html; charset=UTF-8"%>
<%@include file="../layout/header.jsp"%>
<%@include file="../layout/js.jsp"%>

<div class="col-md-9 register-right">
	<div class="tab-content" id="myTabContent">
		<div class="tab-pane fade show active" id="home" role="tabpanel" aria-labelledby="home-tab">
			<h3 class="register-heading">Signed complete</h3>
			<div class="row register-form">
				<form action="/fisbank/ekyc-enterprise/ekyc/step4" style="width: 100%;" method="post" id="submitForm" enctype='multipart/form-data'>
					<div class="col-sm-12">
						<h2>Infomation</h2>
						<hr/>
						<iframe id="base64File" style="width: 100%; height: 500px; border: 0;"></iframe>
					</div>
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
	location.href='/fisbank/ekyc-enterprise';
});
</script>

<%@include file="../layout/footer.jsp"%>
			
