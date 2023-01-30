<%@ page contentType="text/html; charset=UTF-8"%>
<%@include file="../layout/header.jsp"%>
<%@include file="../layout/js.jsp"%>

<div class="col-md-9 register-right">
	<div class="tab-content" id="myTabContent">
		<div class="tab-pane fade show active" id="home" role="tabpanel" aria-labelledby="home-tab">
			<h3 class="register-heading">Upload files </h3>
			<div class="row register-form">
				<form action="${contextPath }/ekyc-enterprise/upload" style="width: 100%;" method="post" id="submitForm" enctype='multipart/form-data'>
					<div class="col-sm-12">
						<div class="form-group">
							<label class="form-label" style="font-weight: bold;">Upload files(*)</label>
							<input type="file" class="form-control" placeholder="" name="files" multiple="multiple"/>
						</div>
						<img src="" style="display: none;max-width: 100%;max-height: 150px" id="anhMatTruocImg" class="img-thumbnail"/>
					</div>
					<input type="submit" class="btnRegister" id="btnRegister" value="Upload" />
				</form>
			</div>
		</div>
	</div>
</div>
<script>

</script>

<%@include file="../layout/footer.jsp"%>
			
