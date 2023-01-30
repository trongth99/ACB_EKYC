<%@ page contentType="text/html; charset=UTF-8"%>
<%@include file="../../layout/header2.jsp"%>
<%@include file="../../layout/js.jsp"%>

<!-- Content Wrapper. Contains page content -->
<div class="content-wrapper">
	<!-- Content Header (Page header) -->
	<section class="content-header">
		<h1>
			<spring:message code="ekyc_doanh_nghiep" />
		</h1>
		<ol class="breadcrumb">
			<li>
				<a href="${contextPath}/">
					<spring:message code="trang_chu" />
				</a>
			</li>
			<li class="active">
				<spring:message code="ekyc_doanh_nghiep" />
			</li>
		</ol>
	</section>

	<!-- Main content -->
	<form id="submitForm" action="" method="get">
		<section class="content container-fluid">
			<div class="box box-danger">
				<div class="box-header">
					<h3 class="box-title"></h3>

					<div class="box-tools">
					</div>
					 <div class="box-tools">
					 <div class="box-tools" style="position: relative;text-align: right;">
	             <div class="form-inline input-group-sm" style="width: 100%;">
						<input style="width: 8%" class="form-control form-control-sm" type="text" value="<c:out value='${ma_doanh_nghiep}'/>" name="ma_doanh_nghiep" placeholder="<spring:message code="ekycdn.ma_so_doanh_nghiep" />" data-toggle="tooltip" title="<spring:message code="ekycdn.ma_so_doanh_nghiep" />"/>
						<input style="width: 8%" class="form-control form-control-sm" type="text" value="<c:out value='${ten_doanh_nghiep}'/>" name="ten_doanh_nghiep" placeholder="<spring:message code="ekycdn.ten_doanh_nghiep" />" data-toggle="tooltip" title="<spring:message code="ekycdn.ten_doanh_nghiep" />"/>
<%-- 						<input style="width: 8%" class="form-control form-control-sm" type="text" value="<c:out value='${tenNguoiQuanLy}'/>" name="tenNguoiQuanLy" placeholder="<spring:message code="ekycdn.ten_nguoi_quan_ly" />" data-toggle="tooltip" title="<spring:message code="ekycdn.ten_nguoi_quan_ly" />"/>
 --%>						
                        <input style="width: 8%" class="form-control form-control-sm" type="text" value="<c:out value='${tenNguoiLienHe}'/>" name="tenNguoiLienHe" placeholder="<spring:message code="ekycdn.ten_nguoi_lien_he" />" data-toggle="tooltip" title="<spring:message code="ekycdn.ten_nguoi_lien_he" />"/>
						<input style="width: 8%" class="form-control form-control-sm" type="text" value="<c:out value='${soDienThoaiNguoiLienHe}'/>" name="soDienThoaiNguoiLienHe" placeholder="<spring:message code="ekycdn.so_dien_thoai_nguoi_lien_he" />" data-toggle="tooltip" title="<spring:message code="ekycdn.so_dien_thoai_nguoi_lien_he" />"/>
						<input style="width: 8%" class="form-control form-control-sm" type="text" value="<c:out value='${emailNguoiLienHe}'/>" name="emailNguoiLienHe" placeholder="<spring:message code="ekycdn.email_nguoi_lien_he" />" data-toggle="tooltip" title="<spring:message code="ekycdn.email_nguoi_lien_he" />"/>
						<input style="width: 8%" class="form-control form-control-sm datepicker" type="text" value="<c:out value="${fromDate}"/>" name="fromDate" placeholder="<spring:message code="tu_ngay" />" data-toggle="tooltip" title="<spring:message code="tu_ngay" />" autocomplete="off"/>
						<input style="width: 8%" class="form-control form-control-sm datepicker" type="text" value="<c:out value="${toDate}"/>" name="toDate" placeholder="<spring:message code="den_ngay" />" data-toggle="tooltip" title="<spring:message code="den_ngay" />" autocomplete="off"/>
						<select style="width: 8%" class="form-control form-control-sm" name="sort" data-toggle="tooltip" >
							<option value="id" ><spring:message code="sort_colum" /></option>
							<option value="ma_doanh_nghiep"><spring:message code="ekycdn.ma_so_doanh_nghiep" /> </option>
							<option value="ten_doanh_nghiep" ><spring:message code="ekycdn.ten_doanh_nghiep" /> </option>
							<%-- <option value="tenNguoiQuanLy" ><spring:message code="ekycdn.ten_nguoi_quan_ly" /> </option> --%>
							<option value="tenNguoiLienHe" ><spring:message code="ekycdn.ten_nguoi_lien_he" /></option>
							<option value="soDienThoaiNguoiLienHe" ><spring:message code="ekycdn.so_dien_thoai_nguoi_lien_he" /></option>
							<option value="emailNguoiLienHe" ><spring:message code="ekycdn.email_nguoi_lien_he" /> </option>
							<option value="ngay_tao" ><spring:message code="ngay_tao" /> </option>
							<option value="status" ><spring:message code="trang_thai" /> </option>
						</select>
						 <input style="width: 8%" class="form-control form-control-sm" type="text" value="<c:out value='${ekyc_status}'/>" name="ekyc_status" placeholder="<spring:message code="trang_thai" />" data-toggle="tooltip" title="<spring:message code="trang_thai" />"/>
	                    <button type="submit" class="btn btn-sm btn-primary"><i class="fa fa-search"></i></button>
	                </div> 
	                </div>
	              </div>
				</div>
				<!-- /.box-header -->
				<div class="box-body table-responsive no-padding">
					<table class="table table-striped table-hover table-bordered">
						<thead>
							<tr>
								<th style="width: 50px;text-align: center;">#</th>
								<th ><spring:message code="ekycdn.ma_so_doanh_nghiep" /></th>
								<th ><spring:message code="ekycdn.ten_doanh_nghiep" /></th>
								<%-- <th ><spring:message code="ekycdn.ten_nguoi_quan_ly" /></th> --%>
								<th ><spring:message code="ekycdn.ten_nguoi_lien_he" /></th>
								<th ><spring:message code="ekycdn.so_dien_thoai_nguoi_lien_he" /></th>
								<th ><spring:message code="ekycdn.email_nguoi_lien_he" /></th>
								<th ><spring:message code="ngay_tao" /></th>
								<th ><spring:message code="trang_thai" /></th>
								<th ></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${ekycDoanhNghieps}" var="item" varStatus="status">
								<tr >
								   <th scope="row" style="text-align: center;">${ (currentPage-1)*20+(status.index+1) }</th>
									<%-- <td style="text-align: center;width: 50px;">
										<span >${status.index+1 }</span>
									</td> --%>
									<td>
										<span><c:out value="${item.maDoanhNghiep }"/></span>
									</td>
									<td>
										<span ><c:out value="${item.tenDoanhNghiep }"/></span>
									</td>
									<%-- <td>
										<span ><c:out value="${item.tenNguoiQuanLy }"/></span>
									</td> --%>
									<td>
										<span ><c:out value="${item.tenNguoiLienHe }"/></span>
									</td>
									<td>
										<span ><c:out value="${item.soDienThoaiNguoiLienHe }"/></span>
									</td>
									<td>
										<span ><c:out value="${item.emailNguoiLienHe }"/></span>
									</td>
									<td><fmt:formatDate pattern = "dd/MM/yyyy HH:mm:ss" value = "${item.ngayTao }" /></td>
									<td>
										<c:if test="${item.status eq 'success' }">
											<span style="color: blue;"><spring:message code="da_ky" /></span>
										</c:if>
										<c:if test="${item.status ne 'success' }">
											<span style="color: red;"><spring:message code="chua_ky" /></span>
										</c:if>
									</td>
									<td class="text-center">
										<c:if test="${item.status eq 'success' }">
											<c:if test="${download eq true }">
												<a href="/fisbank/download-ekyc-doanh-nghiep?id=${item.id}">
													<i class="fa fa-download"></i>
												</a>
											</c:if>
										</c:if>
										<c:if test="${xem eq true }">
											<a href="javascript:void(0)" onclick="loadEdit('${contextPath}/xem-ekyc-doanh-nghiep?id=${item.id}')" data-toggle="modal" data-target="#largeModal" class="text-info" style="margin-left: 5px;">
												<i class="fa fa-eye"></i>
											</a>
										</c:if>
										<c:if test="${xoa eq true }">
											<a href="javascript:void(0)" onclick="deleteRC('${contextPath}/xoa-ekyc-doanh-nghiep?id=${item.id}')" class="text-danger" style="margin-left: 5px;">
												<i class="fa fa-trash"></i>
											</a>
										</c:if>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>

					<%@include file="../../layout/paginate.jsp"%>
				</div>
				<!-- /.box-body -->
			</div>

		</section>
	</form>
	<!-- /.content -->
</div>
<script type="text/javascript">
	$(document).ready(function(){
		$('.datepicker').datepicker({
		     autoclose: true,
		     format: 'dd/mm/yyyy'
		})
	});
</script>
<%@include file="../../layout/footer2.jsp"%>