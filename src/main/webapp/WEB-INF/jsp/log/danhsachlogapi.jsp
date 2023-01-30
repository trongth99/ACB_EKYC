<%@ page contentType="text/html; charset=UTF-8"%>
<%@include file="../layout/header2.jsp"%>
<%@include file="../layout/js.jsp"%>

<script src="https://cdnjs.cloudflare.com/ajax/libs/ace/1.4.12/ace.js" type="text/javascript" charset="utf-8"></script>

<div class="content-wrapper">
	<section class="content-header">
		<h1>
			<spring:message code="bao_cao_log_request" />
		</h1>
		<ol class="breadcrumb">
			<li><a href="${contextPath}/"><spring:message code="trang_chu" /></a></li>
			<li class="active"><spring:message code="bao_cao_log_request" /></li>
		</ol>
	</section>
	
	<form id="submitForm" action="" method="get">
		<section class="content container-fluid">
			<div class="box box-danger">
	            <div class="box-header">
	              <h3 class="box-title">
	              		<button class="btn  btn-primary btn-xs" type="button" id="download" >
	                         <i class="fa fa-download" aria-hidden="true"></i>
	                         <span><spring:message code="tai_danh_sach" /></span>
	                     </button>
	              </h3>
	
	              <div class="box-tools" style="position: relative;text-align: right;">
	                <div class="form-inline input-group-sm" style="width: 100%;">
						<select class="form-control form-control-sm" name="status" data-toggle="tooltip" title="<spring:message code="trang_thai" />">
							<option value="" ${empty status ? 'selected' : ''}><spring:message code="tat_ca" /></option>
							<option value="200" ${status eq '200' ? 'selected': ''}><spring:message code="thanh_cong" /> (200)</option>
							<option value="400" ${status eq '400' ? 'selected': ''}><spring:message code="loi_xu_ly_param" /> (400)</option>
							<option value="401" ${status eq '401' ? 'selected': ''}><spring:message code="loi_xac_thuc" /> (401)</option>
							<option value="205" ${status eq '205' ? 'selected': ''}><spring:message code="khong_co_du_lieu" /> (205)</option>
							<option value="500" ${status eq '500' ? 'selected': ''}><spring:message code="loi_he_thong" /> (500)</option>
						</select>
						<input class="form-control form-control-sm" type="text" value="<c:out value="${uri}"/>" name="uri" placeholder="Uri" data-toggle="tooltip" title="Uri"/>
						<input class="form-control form-control-sm datepicker" type="text" value="<c:out value="${fromDate}"/>" name="fromDate" placeholder="<spring:message code="tu_ngay" />" data-toggle="tooltip" title="<spring:message code="tu_ngay" />" autocomplete="off"/>
						<input class="form-control form-control-sm datepicker" type="text" value="<c:out value="${toDate}"/>" name="toDate" placeholder="<spring:message code="den_ngay" />" data-toggle="tooltip" title="<spring:message code="den_ngay" />" autocomplete="off"/>
	                    <button type="button" class="btn btn-sm btn-primary" id="search">
							<i class="fa fa-search"></i>
						</button>
	                </div>
	              </div>
	            </div>
	            <!-- /.box-header -->
	            <div class="box-body table-responsive no-padding">
	              	<c:if test="${not empty logApis }">
		            	<table class="table table-bordered table-sm table-striped">
							<thead>
								<tr>
									<th style="max-width: 40px;text-align: center;">#</th>
									<th>Uri</th>
									<th><spring:message code="trang_thai" /></th>
									<th><spring:message code="thoi_gian_xu_ly" />(ms)</th>
									<th style="width: 180px;"><spring:message code="thoi_gian" /></th>
									<th><spring:message code="phuong_thuc" /></th>
									<th><spring:message code="ma_giao_dich" /></th>
									<th></th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${logApis}" var="item" varStatus="status">
									<tr>
										<th scope="row" style="text-align: center;">${ (currentPage-1)*20+(status.index+1) }</th>
										<td style="padding-left: 5px;"><a href="javascript:void(0)" onclick="loadEdit('${contextPath}/danh-sach-log-api/xem2?code=<c:out value="${item.code }"/>&id=${item.id}&logId=<c:out value="${item.logId}"/>&time=<fmt:formatDate pattern = "yyyy-MM-dd" value = "${item.date }" />')" data-toggle="modal" data-target="#largeModal" class="text-info"><c:out value="${item.uri }"/></a></td>
										<td>
											<c:if test="${item.status  eq 200}">
												<b style="color: blue;">${item.status}</b>
											</c:if>
											<c:if test="${item.status  ne 200}">
												<b style="color: red;">${item.status}</b>
											</c:if>
										</td>
										<td><fmt:formatNumber type = "number" maxFractionDigits = "3" value = "${item.timeHandling }" /></td>
										<td><fmt:formatDate pattern = "dd/MM/yyyy HH:mm:ss" value = "${item.date }" /></td>
										<td><c:out value="${item.method }"/></td>
										<td><c:out value="${item.codeTransaction }"/></td>
										<td class="text-center">
											<a href="javascript:void(0)" onclick="loadEdit('${contextPath}/danh-sach-log-api/xem2?code=<c:out value="${item.code }"/>&id=${item.id}&logId=<c:out value="${item.logId}"/>&time=<fmt:formatDate pattern = "yyyy-MM-dd" value = "${item.date }" />')" data-toggle="modal" data-target="#largeModal" class="text-info">
												<i class="fa fa-eye"></i>
											</a>
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
						<%@include file="../layout/paginate.jsp"%>
		            </c:if>
	            </div>
	            <!-- /.box-body -->
	          </div>
			
		</section>
	</form>
</div>
<script type="text/javascript">
	$(document).ready(function(){
		$("#download").click(function(){
			$("#submitForm").attr("action", "${contextPath}/danh-sach-log-api/export");
			$("#submitForm").submit();
		});
		$("#search").click(function(){
			$("#submitForm").attr("action", "${contextPath}/danh-sach-log-api");
			$("#submitForm").submit();
		});
		$('.datepicker').datepicker({
		     autoclose: true,
		     format: 'dd/mm/yyyy'
		})
	});
</script>	

<%@include file="../layout/footer2.jsp"%>