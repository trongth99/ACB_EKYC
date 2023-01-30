<%@ page contentType="text/html; charset=UTF-8"%>
<div class="row clearfix">
	<h3 style="clear: both;"><spring:message code="nguoi_duoc_uy_quyen" /></h3>
	<hr style="color: #CCC;border-top: 1px solid #CCC;"/>
	
	<c:forEach items="${personAuthorizedAccountHolders}" var="item" varStatus="status">
		<div class="tab">
						<div class="row">
							<div class="col-md-6">
								<div class="form-group  has-feedback has-feedback">
									<label class="form-label" style="font-weight: bold;">ID card number<br/><i style="font-weight: normal;">Số giấy tờ tùy thân</i> </label>
									<input type="text" class="form-control form-control-sm" readonly="readonly" value="${item.soCmt }" readonly="readonly" name="soCmt"/>
								</div>
							</div>	
							<div class="col-md-6">
								<div class="form-group  has-feedback has-feedback">
									<label class="form-label" style="font-weight: bold;">Full name <br/><i style="font-weight: normal;">Họ và tên</i></label>
									<input type="text" class="form-control form-control-sm" readonly="readonly" value="${item.hoVaTen }" readonly="readonly" name="hoVaTen"/>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-md-6">
								<div class="form-group  has-feedback has-feedback">
									<label class="form-label" style="font-weight: bold;">Expiration date<br/><i style="font-weight: normal;">Ngày hết hạn</i></label>
									<input type="text" class="form-control form-control-sm" readonly="readonly" value="${item.ngayHetHan }" readonly="readonly" name="ngayHetHan"/>
								</div>
							</div>
							<div class="col-md-6">
								<div class="form-group  has-feedback has-feedback">
									<label class="form-label" style="font-weight: bold;">Issue date<br/><i style="font-weight: normal;">Ngày cấp</i></label>
									<input type="text" class="form-control form-control-sm" readonly="readonly" value="${item.ngayCap }" readonly="readonly" name="ngayCap"/>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-md-6">
								<div class="form-group  has-feedback has-feedback">
									<label class="form-label" style="font-weight: bold;">Date of birth<br/><i style="font-weight: normal;">Ngày sinh</i></label>
									<input type="text" class="form-control form-control-sm" readonly="readonly" value="${item.namSinh }" readonly="readonly" name="namSinh"/>
								</div>
							</div>
							<div class="col-md-6">
								<div class="form-group  has-feedback">
									<label class="form-label" style="font-weight: bold;">Issue place<br/><i style="font-weight: normal;">Nơi cấp</i></label>
									<input type="text" class="form-control form-control-sm" readonly="readonly" value="${item.noiCap }" readonly="readonly" name="noiCap"/>
								</div>
							</div>
						</div>
						<hr/>
						<div class="row">
							<div class="col-md-6">
								<div class="form-group  has-feedback">
									<label class="form-label" style="font-weight: bold;">Nationality<br/><i style="font-weight: normal;">Quốc tịch</i></label>
									<input type="text" class="form-control form-control-sm" readonly="readonly" value="${item.quocTich }"  name="quocTich"/>
								</div>
							</div>
							<div class="col-md-6">
								<div class="form-group  has-feedback">
									<label class="form-label" style="font-weight: bold;">Visa/ TR card (for foreigner)<br/><i style="font-weight: normal;">Visa/ Thẻ TR (Cho người ngoại quốc)</i></label>
									<input type="text" class="form-control form-control-sm" readonly="readonly" value="${item.visa }" id="visa" name="visa" />
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-md-6">
								<div class="form-group  has-feedback">
									<label class="form-label" style="font-weight: bold;">Tax Code<br/><i style="font-weight: normal;">Mã số thuế</i></label>
									<input type="text" class="form-control form-control-sm" readonly="readonly" value="${item.maSoThue }" id="maSoThue"  name="maSoThue" />
								</div>
							</div>
							<div class="col-md-6">
								<div class="form-group  has-feedback">
									<label class="form-label" style="font-weight: bold;">Residential Status<br/><i style="font-weight: normal;">Tình Trạng Cư Trú </i></label>
									<select class="form-control form-control-sm" readonly="readonly" name="tinhTrangCuTru">
										<option value="Resident">Resident</option>
										<option value="Non-resident">Non-resident</option>
									</select>
								</div>
							</div>
						</div>
						<hr/>
						<h4>Contact No:<br/><i style="font-weight: normal;font-size: 20px;">Thông tin liên hệ </i></h4>
						<div class="row">
							<div class="col-md-6">
								<div class="form-group  has-feedback">
									<label class="form-label" style="font-weight: bold;">Home<br/><i style="font-weight: normal;">Nhà</i></label>
									<input type="text" class="form-control form-control-sm" readonly="readonly" value="${item.diaChiNha }" id="diaChiNha" name="diaChiNha"/>
								</div>
							</div>
							<div class="col-md-6">
								<div class="form-group  has-feedback">
									<label class="form-label" style="font-weight: bold;">Mobile<br/><i style="font-weight: normal;">Số điện thoại</i></label>
									<input type="text" class="form-control form-control-sm" readonly="readonly" value="${item.mobile }" id="mobile" name="mobile"/>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-md-12">
								<div class="form-group  has-feedback">
									<label class="form-label" style="font-weight: bold;">Office<br/><i style="font-weight: normal;">Văn phòng</i></label>
									<input type="text" class="form-control form-control-sm" readonly="readonly" value="${item.vanPhong }" id="vanPhong" name="vanPhong"/>
								</div>
							</div>
						</div>
						<hr/>
						<h4>Permanent registered address<br/><i style="font-weight: normal;font-size: 20px;">Hộ khẩu thường trú </i></h4>
						<div class="row">
							<div class="col-md-12">
								<div class="form-group  has-feedback">
									<input type="text" class="form-control form-control-sm" readonly="readonly" value="${item.hoKhau }" id="hoKhau"  name="hoKhau"/>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-md-12">
								<div class="form-group  has-feedback">
									<label class="form-label" style="font-weight: bold;">Email</label>
									<input type="text" class="form-control form-control-sm" readonly="readonly" value="${item.email2 }" id="email2" name="email2"/>
								</div>
							</div>
						</div>
					</div>
		<div class="row">
			<div class="col-sm-4">
				<div class="form-group  has-feedback">
					<label class="control-label">
						<spring:message code="anh_mat_truoc" />
					</label>
					<img src="/fisbank/ekyc-doanh-nghiep/img-byte?path=${item.anhMatTruoc }" style="width: 100%;" />
				</div>
			</div>
			<div class="col-sm-4">
				<div class="form-group  has-feedback">
					<label class="control-label">
						<spring:message code="anh_mat_sau" />
					</label>
					<img src="/fisbank/ekyc-doanh-nghiep/img-byte?path=${item.anhMatSau }" style="width: 100%;" />
				</div>
			</div>
			<div class="col-sm-4">
				<div class="form-group  has-feedback">
					<label class="control-label">
						<spring:message code="anh_chu_ky" />
					</label>
					<img src="/fisbank/ekyc-doanh-nghiep/img-byte?path=${item.anhChuKy }" style="width: 100%;" />
				</div>
			</div>
		</div>
		<c:if test="${not empty item.file }">
			<div class="row">
				<div class="col-sm-12">
					<iframe style="width: 100%; height: 300px; border: 0;" src="/fisbank/ekyc-doanh-nghiep/pdf-byte?path=${item.file }"></iframe>
				</div>
			</div>
		</c:if>
		<div style="clear: both;"></div>
		<hr/>
	</c:forEach>
</div>
<script type="text/javascript">


</script>