<%@ page contentType="text/html; charset=UTF-8"%>
<%@include file="../../layout/header2.jsp"%>
<%@include file="../../layout/js.jsp"%>

<!-- Content Wrapper. Contains page content -->
<div class="content-wrapper">
	<!-- Content Header (Page header) -->
	<section class="content-header">
		<h1>
			<spring:message code="danh_sach_khach_hang" />
		</h1>
		<ol class="breadcrumb">
			<li>
				<a href="${contextPath}/">
					<spring:message code="trang_chu" />
				</a>
			</li>
			<li class="active">
				<spring:message code="danh_sach_khach_hang" />
			</li>
		</ol>
	</section>

	<!-- Main content -->
	<form id="submitForm" action="" method="get">
		<section class="content container-fluid">
			<div class="box box-danger">
				<div class="box-header">
					<h3 class="box-title">
						<button class="btn  btn-primary btn-xs" type="button" onclick="javascript:window.location='${contextPath}/danh-sach-khach-hang/ky-so'">
	                          <i class="fa fa-plus" aria-hidden="true"></i>
	                         <span><spring:message code="them_moi" /></span>
	                     </button>
	                     <button class="btn  btn-primary btn-xs" type="button" id="download" >
	                         <i class="fa fa-download" aria-hidden="true"></i>
	                         <span>Tải danh sách</span>
	                     </button>
					</h3>

					<div class="box-tools" style="position: relative;text-align: right;">
						<div class="form-inline input-group-sm" style="width: 100%;">
							<input class="form-control form-control-sm" type="text" value="<c:out value="${soHopDong}"/>" name="soHopDong" placeholder="Số hợp đồng" data-toggle="tooltip" title="Số hợp đồng" />
							<input class="form-control form-control-sm" type="text" value="<c:out value="${hoVaTen}"/>" name="hoVaTen" placeholder="<spring:message code="ho_va_ten" />" data-toggle="tooltip" title="<spring:message code="ho_va_ten" />" />
							<input class="form-control form-control-sm" type="text" value="<c:out value="${soDienThoai}"/>" name="soDienThoai" placeholder="<spring:message code="so_dien_thoai" />" data-toggle="tooltip" title="<spring:message code="so_dien_thoai" />" />
							<input class="form-control form-control-sm" type="text" value="<c:out value="${soCmt}"/>" name="soCmt" placeholder="<spring:message code="so_cmt" />" data-toggle="tooltip" title="<spring:message code="so_cmt" />" />
							<input class="form-control form-control-sm datepicker" type="text" value="<c:out value="${fromDate}"/>" name="fromDate" placeholder="<spring:message code="tu_ngay" />" data-toggle="tooltip" title="<spring:message code="tu_ngay" />" autocomplete="off"/>
							<input class="form-control form-control-sm datepicker" type="text" value="<c:out value="${toDate}"/>" name="toDate" placeholder="<spring:message code="den_ngay" />" data-toggle="tooltip" title="<spring:message code="den_ngay" />" autocomplete="off"/>
							<select class="form-control show-tick" name="trangThai" id="trangThai" data-toggle="tooltip" title="<spring:message code="trang_thai" />">
								<option value="" ${trangThai eq '' ? 'selected': ''}><spring:message code="tat_ca" /></option>
								<option value="0" ${trangThai eq '0' ? 'selected': ''}>Chưa ký</option>
								<option value="4" ${trangThai eq '4' ? 'selected': ''}>Chờ ký</option>
								<option value="1" ${trangThai eq '1' ? 'selected': ''}>Khách hàng ký</option>
								<option value="2" ${trangThai eq '2' ? 'selected': ''}>Bảo hiểm ký</option>
								<option value="3" ${trangThai eq '3' ? 'selected': ''}>Hoàn thành</option>
							</select>
							<select class="form-control show-tick" name="khuVuc" id="khuVuc" data-toggle="tooltip" title="Khu vực" >
								<option value=''>-- Chọn khu vực --</option>
								<c:forEach items="${khuVucs}" var="item" varStatus="status">
									<option value='<c:out value="${item }"></c:out>' ${khuVuc eq item ? 'selected': ''}><c:out value="${item }"></c:out></option>
								</c:forEach>
							</select>
							<button type="button" class="btn btn-sm btn-primary" id="search">
								<i class="fa fa-search"></i>
							</button>
						</div>
					</div>
				</div>
				<!-- /.box-header -->
				<div class="box-body table-responsive no-padding">
					<table class="table table-striped table-hover table-bordered">
						<thead>
							<tr>
								<th style="width: 50px; text-align: center;">#</th>
								<th>
									Số hợp đồng
								</th>
								<th>
									<spring:message code="so_giay_to" />
								</th>
								<th>
									<spring:message code="ho_va_ten" />
								</th>
								<th>
									<spring:message code="dien_thoai" />
								</th>
								<th>
									<spring:message code="email" />
								</th>
								<th>
									Mật khẩu
								</th>
								<th>
									Trạng Thái
								</th>
								<th>
									Trạng Thái eKYC
								</th>
								<th>
									Lỗi eKYC
								</th>
								<th>
									Bảo Hiểm
								</th>
								<th>
									<spring:message code="ngay_tao" />
								</th>
								<th>
									Khu vực
								</th>
								<th>
									Ngày khách hàng ký
								</th>
								<th>
									Ngày bảo hiểm ký
								</th>
								<th>
									Gửi hợp đồng
								</th>
								<th style="width: 150px;"></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${ekycKysos}" var="item" varStatus="status">
								<tr>
									<th scope="row" style="text-align: center;">${status.index+1 }</th>
									<td><c:out value="${item.soTaiKhoan }"/> </td>
									<td><c:out value="${item.soCmt }"/></td>
									<td><c:out value="${item.hoVaTen }"/></td>
									<td><c:out value="${item.soDienThoai }"/></td>
									<td><c:out value="${item.email }"/></td>
									<td><c:out value="${item.token }"/></td>
									<td>
										<c:if test="${item.trangThai eq '3' }"><span style="color: #3c8dbc;">Hoàn thành</span></c:if>
										<c:if test="${item.trangThai eq '2' }"><span style="color: green;">Bảo hiểm ký</span></c:if>
										<c:if test="${item.trangThai eq '1' }"><span style="color: blue">Khách hàng ký</span></c:if>
										<c:if test="${item.trangThai eq '4' }"><span style="color: orange;">Chờ ký</span></c:if>
										<c:if test="${item.trangThai eq '0' or empty item.trangThai }">
											<span style="color: red;">Chưa ký</span>
										</c:if>
									</td>
									<td>
										<c:if test="${item.trangThaiEkyc eq 'thatbai' }">
											<span style="color: red;">Thất bại</span>
										</c:if>
										<c:if test="${item.trangThaiEkyc eq 'thanhcong' }">
											<span style="color: blue;">Thành công</span>
										</c:if>
										<c:if test="${item.trangThaiEkyc eq 'truyenthong' }">
											<span style="color: orange;">Truyền thống</span>
										</c:if>
										<c:if test="${empty item.trangThaiEkyc}">
											<span style="color: green;">Chưa xác thực</span>
										</c:if>
									</td>
									<td>
										<c:forTokens items="${item.thongBao }" delims="|" var="mySplit" varStatus="st">
											<c:if test="${st.index <= 1 }">
										   		- <c:out value="${mySplit}"/><br/>
										   	</c:if>
										</c:forTokens>
									</td>
									<td>
										<c:if test="${ empty item.chiDinh}">
											<span style="color: red;">Không có</span>
										</c:if>
										<c:if test="${not empty item.chiDinh }">
											<span style="color: blue;">Có</span>
										</c:if>
									</td>
									<td><fmt:formatDate pattern = "dd/MM/yyyy HH:mm:ss" value = "${item.ngayTao }" /></td>
									<td><c:out value="${item.khuVuc}"/></td>
									<td><fmt:formatDate pattern = "dd/MM/yyyy HH:mm:ss" value = "${item.khachHangKy }" /></td>
									<td><fmt:formatDate pattern = "dd/MM/yyyy HH:mm:ss" value = "${item.baoHiemKy }" /></td>
									<td>
										<c:if test="${item.trangThai eq '3'}">
											<a href="javascript:void(0)" onclick="alertRC('${contextPath}/danh-sach-khach-hang/ky-so/gui-mail-hop-dong?id=${item.id}','Bạn có chắc muốn gửi hợp đồng cho khách hàng?')" class="text-info" style="margin-left: 5px;" title="Gửi email">
												Gửi
											</a>
										</c:if>
									</td>
									<td >
										<a href="javascript:void(0)" onclick="loadEdit('${contextPath}/danh-sach-khach-hang/ky-so/gui-mail/chon?id=${item.id}')" data-toggle="modal" data-target="#largeModal" class="text-info" style="margin-left: 5px;" title="Gửi email">
											<i class="fa  fa-send"></i>
										</a>
										<a href="javascript:void(0)" onclick="loadEdit('${contextPath}/danh-sach-khach-hang/trang-thai?id=${item.id}')" data-toggle="modal" data-target="#largeModal" class="text-info" style="margin-left: 5px;" title="Cập nhật ekyc">
											<i class="fa fa-user"></i>
										</a>
										<a href="javascript:void(0)" onclick="loadEdit('${contextPath}/danh-sach-khach-hang/xem?id=${item.id}')" data-toggle="modal" data-target="#largeModal" class="text-info" style="margin-left: 5px;" title="Xem chi tiết">
											<i class="fa fa-eye"></i>
										</a>
										<c:if test="${item.trangThai eq '0' or empty item.trangThai  }">
											<a href="${contextPath}/danh-sach-khach-hang/sua?id=${item.id}" style="margin-left: 5px;" title="Sửa">
												<i class="fa fa-edit"></i>
											</a>
										</c:if>
										<c:if test="${item.trangThai ne '3' }">
											<a href="javascript:void(0)" onclick="alertRC('${contextPath}/danh-sach-khach-hang/thay-doi-trang-thai?id=${item.id}', 'Bạn có chắc muốn hoàn thành hợp đồng?')" class="text-info" style="margin-left: 5px;" title="Hoàn thành hợp đồng">
												<i class="fa fa-check" aria-hidden="true"></i>
											</a>
										</c:if>
										<c:if test="${empty item.token }">
											<a href="javascript:void(0)" onclick="deleteRC('${contextPath}/danh-sach-khach-hang/xoa?id=${item.id}')" class="text-danger" style="margin-left: 5px;" title="Xóa">
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
		$("#download").click(function(){
			$("#submitForm").attr("action", "${contextPath}/danh-sach-khach-hang/export");
			$("#submitForm").submit();
		});
		$("#search").click(function(){
			$("#submitForm").attr("action", "${contextPath}/danh-sach-khach-hang");
			$("#submitForm").submit();
		});
		$('.datepicker').datepicker({
		     autoclose: true,
		     format: 'dd/mm/yyyy'
		})
	});
</script>
<%@include file="../../layout/footer2.jsp"%>