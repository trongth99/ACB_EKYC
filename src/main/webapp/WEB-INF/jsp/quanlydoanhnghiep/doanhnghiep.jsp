<%@ page contentType="text/html; charset=UTF-8"%>
<%@include file="../layout/header2.jsp"%>
<%@include file="../layout/js.jsp"%>

<!-- Content Wrapper. Contains page content -->
<div class="content-wrapper">
	<!-- Content Header (Page header) -->
	<section class="content-header">
		<h1>
			<spring:message code="danh_sach_doanh_nghiep" />
		</h1>
		<ol class="breadcrumb">
			<li><a href="${contextPath}/"><spring:message code="trang_chu" /></a></li>
			<li class="active"><spring:message code="danh_sach_doanh_nghiep" /></li>
		</ol>
	</section>

	<!-- Main content -->
	<form id="submitForm" action="" method="get">
		<section class="content container-fluid">
			<div class="box box-danger">
	            <div class="box-header">
	              <h3 class="box-title">
	              		<button class="btn btn-block btn-primary btn-xs"
	                             data-toggle="modal" data-target="#largeModal" type="button"
	                             onclick="loadAdd('${contextPath}/doanh-nghiep/them-moi')">
	                             <i class="fa fa-plus" aria-hidden="true"></i>
	                             
	                         <span><spring:message code="them_moi" /></span>
	                     </button>
	              </h3>
	
	              <div class="box-tools">
	                <div class="form-inline input-group-sm" style="width: 100%;">
						<input class="form-control form-control-sm" type="text" value="<c:out value='${b_uname}'/>" name="b_uname" placeholder="<spring:message code="ten_dang_nhap" />" data-toggle="tooltip" title="<spring:message code="ten_dang_nhap" />"/>
						<input class="form-control form-control-sm" type="text" value="<c:out value='${b_fname}'/>" name="b_fname" placeholder="<spring:message code="ho_va_ten" />" data-toggle="tooltip" title="<spring:message code="ho_va_ten" />"/>
						<input class="form-control form-control-sm" type="text" value="<c:out value='${b_email}'/>" name="b_email" placeholder="<spring:message code="email" />" data-toggle="tooltip" title="<spring:message code="email" />"/>
						<select class="form-control form-control-sm" name="b_status" data-toggle="tooltip" title="<spring:message code="trang_thai" />">
							<option value="" ${empty b_status ? 'selected' : ''}><spring:message code="tat_ca" /></option>
							<option value="0" ${b_status eq '0' ? 'selected': ''}><spring:message code="khong_hoat_dong" /></option>
							<option value="1" ${b_status eq '1' ? 'selected': ''}><spring:message code="hoat_dong" /></option>
						</select>
	                    <button type="submit" class="btn btn-sm btn-primary"><i class="fa fa-search"></i></button>
	                </div>
	              </div>
	            </div>
	            <!-- /.box-header -->
	            <div class="box-body table-responsive no-padding">
	              	<table class="table table-striped table-hover table-bordered">
						<thead>
							<tr>
								<th style="width: 50px;text-align: center;">#</th>
								<th><spring:message code="ten_dang_nhap" /></th>
								<th><spring:message code="ho_va_ten" /></th>
								<th><spring:message code="email" /></th>
								<th><spring:message code="dien_thoai" /></th>
								<th style="width: 180px;"><spring:message code="trang_thai" /></th>
								<th style="width: 100px;"></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${business}" var="item" varStatus="status">
								<tr>
									<th scope="row" style="text-align: center;">${ (currentPage-1)*20+(status.index+1) }</th>
									<td><c:out value='${item.username }'/></td>
									<td><c:out value='${item.fullName }'/></td>
									<td><c:out value='${item.email }'/></td>
									<td><c:out value='${item.dienThoai }'/></td>
									<td>
										<c:if test="${item.status eq 1 }">
											<span class="text-primary"><spring:message code="hoat_dong" /></span>
										</c:if>
										<c:if test="${item.status ne 1 }">
											<span class="text-danger"><spring:message code="khong_hoat_dong" /></span>
										</c:if>
									</td>
									<td class="text-center">
										<a href="javascript:void(0)" onclick="loadEdit('${contextPath}/doanh-nghiep/sua?id=${item.id}')" data-toggle="modal" data-target="#largeModal" class="text-info" style="margin-left: 5px;">
											<i class="fa fa-edit"></i>
										</a>
										<a href="javascript:void(0)" onclick="deleteRC('${contextPath}/doanh-nghiep/xoa?id=${item.id}')" class="text-danger" style="margin-left: 5px;">
											<i class="fa fa-trash"></i>
										</a> 
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
	
					<%@include file="../layout/paginate.jsp"%>
	            </div>
	            <!-- /.box-body -->
	          </div>
			
		</section>
	</form>
	<!-- /.content -->
</div>

<%@include file="../layout/footer2.jsp"%>