<%@ page contentType="text/html; charset=UTF-8"%>
<div class="row clearfix">
	<h3 style="clear: both;">
		<spring:message code="ekycdn.nguoi_uy_quyen" />
		<c:if test="${ekycDoanhNghiep.nguoiDuocUyQuyenCheck eq 'update' }">
			( <b style="color: blue;">Đã cập nhật thông tin</b> )
		</c:if>
	</h3>
	<hr style="color: #CCC;border-top: 1px solid #CCC;"/>
	<div class="col-sm-6">
		<div class="form-group  has-feedback">
			<label class="control-label"><spring:message code="so_dien_thoai" /></label>
			<input type="text" class="form-control form-control-sm" readonly="readonly" value="<c:out value="${ekycDoanhNghiep.soDienThoaiNuq }"/>" />
		</div>
	</div>
	<div class="col-sm-6">
		<div class="form-group  has-feedback">
			<label class="control-label"><spring:message code="email" /></label>
			<input type="text" class="form-control form-control-sm" readonly="readonly" value="<c:out value="${ekycDoanhNghiep.emailsNuq }"/>" />
		</div>
	</div>
	<div class="col-sm-6">
		<div class="form-group  has-feedback">
			<label class="control-label"><spring:message code="ho_va_ten" /></label>
			<input type="text" class="form-control form-control-sm" readonly="readonly" value="<c:out value="${ekycDoanhNghiep.hoVaTenNuq }"/>" />
		</div>
	</div>
	<div class="col-sm-6">
		<div class="form-group  has-feedback">
			<label class="control-label"><spring:message code="e_so_giay_to" /></label>
			<input type="text" class="form-control form-control-sm" readonly="readonly" value="<c:out value="${ekycDoanhNghiep.soGiayToNuq }"/>" />
		</div>
	</div>
	<div class="col-sm-6">
		<div class="form-group  has-feedback">
			<label class="control-label"><spring:message code="ngay_cap" /></label>
			<input type="text" class="form-control form-control-sm" readonly="readonly" value="<c:out value="${ekycDoanhNghiep.ngayCapNuq }"/>" />
		</div>
	</div>
	<div class="col-sm-6">
		<div class="form-group  has-feedback">
			<label class="control-label"><spring:message code="e_noi_cap" /></label>
			<input type="text" class="form-control form-control-sm" readonly="readonly" value="<c:out value="${ekycDoanhNghiep.noiCapNuq }"/>" />
		</div>
	</div>
	<div style="clear: both;"></div>
	<div class="row">
		<div class="col-sm-6">
			<div class="form-group  has-feedback">
				<label class="control-label">
					<spring:message code="anh_mat_truoc" />
				</label>
				<img src="${anhMatTruocNuq }" style="width: 100%;" />
			</div>
		</div>
		<div class="col-sm-6">
			<div class="form-group  has-feedback">
				<label class="control-label">
					<spring:message code="anh_mat_sau" />
				</label>
				<img src="${anhMatSauNuq }" style="width: 100%;" />
			</div>
		</div>
	</div>
	<div class="row">
		<div class="col-sm-12">
			<div class="form-group  has-feedback">
				<label class="control-label">
					<spring:message code="anh_chu_ky" />
				</label>
				<img src="${anhChuKyNuq }" style="width: 100%;" />
			</div>
		</div>
	</div>
</div>
<script type="text/javascript">

</script>