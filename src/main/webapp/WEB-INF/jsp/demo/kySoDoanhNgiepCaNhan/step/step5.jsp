<%@ page contentType="text/html; charset=UTF-8"%>
<%@include file="../layout/header.jsp"%>
<%@include file="../layout/js.jsp"%>

<div class="col-md-12 register-right">
	<div class="tab-content" id="myTabContent">
		<div class="tab-pane fade show active" id="home" role="tabpanel" aria-labelledby="home-tab">
			<h3 class="register-heading">Xem hợp đồng đã ký</h3>
			<div class="row register-form">
				<div class="col-sm-12">
					<h2>Hợp đồng vay (<a href="${LINK_ADMIN }/download/byte/${file}" target="_blank">Tải file</a>)</h2>
					<hr/>
					<iframe id="base64File" style="width: 100%; height: 500px; border: 0;" src="${LINK_ADMIN }/viewpdf?file=${LINK_ADMIN }/viewpdf/byte/${file}"></iframe>
					<hr/>
					<c:if test="${not empty fileBaoHiem }">
						<h2>Hợp đồng bảo hiểm (<a href="${LINK_ADMIN }/download/byte/${fileBaoHiem}" target="_blank">Tải file</a>)</h2>
						<hr/>
						<iframe id="base64FileBaoHiem" style="width: 100%; height: 500px; border: 0;" src="${LINK_ADMIN }/viewpdf?file=${LINK_ADMIN }/viewpdf/byte/${fileBaoHiem}"></iframe>
					</c:if>
				</div>
				<button class="btnRegister" type="button" style="background: #CCC;color: black;width: 110px;" onclick="javascript:location.href='${contextPath }/khach-hang/ky-so/step1'">Kết thúc</button>
			</div>
		</div>
	</div>
</div>

<%@include file="../layout/footer.jsp"%>
			
