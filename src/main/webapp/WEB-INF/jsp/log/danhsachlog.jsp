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
	              		<a class="btn btn-block btn-primary btn-xs"
                                 href="${contextPath}/danh-sach-log/img">
                                 <i class="fa fa-eye" aria-hidden="true"></i>
                                 
                             <span><spring:message code="xem_log_anh" /></span>
                         </a>
	              </h3>
	
	              <div class="box-tools">
	                <div class="form-inline input-group-sm" style="width: 100%;">
						<select class="form-control form-control-sm" name="toChuc" data-toggle="tooltip" title="<spring:message code="to_chuc" />">
							<option value="" <c:if test="${empty toChuc }">selected</c:if>>--<spring:message code="chon_to_chuc" />--</option>
							<c:forEach items="${mOrgs }" var="item">
								<option value="<c:out value="${item.maToChuc}"/>" <c:if test="${toChuc eq item.maToChuc }">selected</c:if>><c:out value="${item.maToChuc}"/></option>
							</c:forEach>
						</select>
						<select class="form-control form-control-sm" name="khachHang" data-toggle="tooltip" title="<spring:message code="khach_hang" />">
							<c:forEach items="${quanLyKhachHangs }" var="item">
								<option value="<c:out value="${item.code}"/>" <c:if test="${khachHang eq item.code }">selected</c:if>><c:out value="${item.name}"/></option>
							</c:forEach>
						</select>
						<select class="form-control form-control-sm" name="status" data-toggle="tooltip" title="<spring:message code="trang_thai" />">
							<option value="" ${empty status ? 'selected' : ''}><spring:message code="tat_ca" /></option>
							<option value="200" ${status eq '200' ? 'selected': ''}><spring:message code="thanh_cong" /> (200)</option>
							<option value="400" ${status eq '400' ? 'selected': ''}><spring:message code="loi_xu_ly_param" /> (400)</option>
							<option value="401" ${status eq '401' ? 'selected': ''}>L???i x??c th???c (401)</option>
							<option value="205" ${status eq '205' ? 'selected': ''}>Kh??ng c?? d??? li???u (205)</option>
							<option value="500" ${status eq '500' ? 'selected': ''}>L???i h??? th???ng (500)</option>
						</select>
						<input class="form-control form-control-sm" type="text" value="<c:out value="${soDienThoai}"/>" name="soDienThoai" placeholder="S??? ??i???n tho???i" data-toggle="tooltip" title="S??? ??i???n tho???i" autocomplete="off"/>
	                	<input class="form-control form-control-sm" type="text" value="<c:out value="${soCmt}"/>" name="soCmt" placeholder="S??? ch???ng minh th??" data-toggle="tooltip" title="S??? ch???ng minh th??" autocomplete="off"/>
	                	<input class="form-control form-control-sm" type="text" value="<c:out value="${soHopDong}"/>" name="soHopDong" placeholder="S??? h???p ?????ng" data-toggle="tooltip" title="S??? h???p ?????ng" autocomplete="off"/>
	                	<input class="form-control form-control-sm" type="text" value="<c:out value="${hoVaTen}"/>" name="hoVaTen" placeholder="H??? v?? t??n" data-toggle="tooltip" title="H??? v?? t??n" autocomplete="off"/>
						<input class="form-control form-control-sm" type="text" value="<c:out value="${maGiaoDich}"/>" name="maGiaoDich" placeholder="M?? giao d???ch" data-toggle="tooltip" title="M?? giao d???ch"/>
						<input class="form-control form-control-sm" type="text" value="<c:out value="${uri}"/>" name="uri" placeholder="Uri" data-toggle="tooltip" title="Uri"/>
						<input class="form-control form-control-sm datepicker" type="text" value="<c:out value="${fromDate}"/>" name="fromDate" placeholder="T??? ng??y" data-toggle="tooltip" title="T??? ng??y" autocomplete="off"/>
						<input class="form-control form-control-sm datepicker" type="text" value="<c:out value="${toDate}"/>" name="toDate" placeholder="?????n ng??y" data-toggle="tooltip" title="?????n ng??y" autocomplete="off"/>
	                    <button type="submit" class="btn btn-sm btn-primary"><i class="fa fa-search"></i></button>
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
									<th>Tr???ng th??i</th>
									<th>Th???i gian x??? l??(ms)</th>
									<th style="width: 180px;">Th???i gian</th>
									<th>Ph????ng th???c</th>
<!-- 												<th>Log ID</th> -->
									<th>M?? giao d???ch</th>
									<th>S??? ??i???n tho???i</th>
									<th>H??? v?? t??n</th>
									<th>S??? cmt</th>
									<th>S??? h???p ?????ng</th>
									<th></th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${logApis}" var="item" varStatus="status">
									<tr>
										<th scope="row" style="text-align: center;">${ (currentPage-1)*20+(status.index+1) }</th>
										<td style="padding-left: 5px;"><a href="javascript:void(0)" onclick="loadEdit('${contextPath}/danh-sach-log/xem?code=<c:out value="${item.code }"/>&id=${item.id}&logId=<c:out value="${item.logId}"/>&time=<fmt:formatDate pattern = "yyyy-MM-dd" value = "${item.date }" />')" data-toggle="modal" data-target="#largeModal" class="text-info"><c:out value="${item.uri }"/></a></td>
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
<%-- 													<td>${item.logId }</td> --%>
										<td><c:out value="${item.codeTransaction }"/></td>
										<td><c:out value="${item.phone }"/></td>
										<td><c:out value="${item.fullName }"/></td>
										<td><c:out value="${item.idCard }"/></td>
										<td><c:out value="${item.idContract }"/></td>
										<td class="text-center">
											<a href="javascript:void(0)" onclick="loadEdit('${contextPath}/danh-sach-log/xem?code=<c:out value="${item.code }"/>&id=${item.id}&logId=<c:out value="${item.logId}"/>&time=<fmt:formatDate pattern = "yyyy-MM-dd" value = "${item.date }" />')" data-toggle="modal" data-target="#largeModal" class="text-info">
												<i class="fa fa-eye"></i>
											</a>
											<a href="javascript:void(0)" onclick="loadEdit('${contextPath}/danh-sach-log/xem2?code=<c:out value="${item.code }"/>&id=${item.id}&logId=<c:out value="${item.logId}"/>&time=<fmt:formatDate pattern = "yyyy-MM-dd" value = "${item.date }" />')" data-toggle="modal" data-target="#largeModal" class="text-info">
												<i class="fa fa-binoculars"></i>
											</a>
											<a style="margin-left: 5px;" href="javascript:void(0)" onclick="loadEdit('${contextPath}/danh-sach-log/unzip?code=<c:out value="${item.code }"/>&id=${item.id}&time=<fmt:formatDate pattern = "yyyy-MM-dd" value = "${item.date }" />')" data-toggle="modal" data-target="#largeModal" class="text-info">
												<i class="fa fa-sticky-note"></i>
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
		$('.datepicker').datepicker({
		     autoclose: true,
		     format: 'dd/mm/yyyy'
		})
	});
</script>	

<%@include file="../layout/footer2.jsp"%>