<%@ page contentType="text/html; charset=UTF-8"%>
<div class="panel panel-primary setup-content" id="step-14">
	<div class="panel-heading">
		<h3 class="panel-title">Register to open a Straight2bank account / đăng ký mở tài khoản Straight2bank</h3>
	</div>
	<div class="panel-body">
		<div id="divTemplateAdd">
			<c:forEach items="${userDesignation}" var="item" varStatus="status">
				<c:if test="${status.index == 0 }"><div style="margin-bottom: 10px;" id="templateAdd"></c:if>
					<div class="row">
						<div class="form-group col-sm-4">
							<div class="form-group  has-feedback">
								<label class="control-label">Full name<br/><i style="font-weight: normal;">Họ và tên</i></label>
								<input type="text" class="form-control input-sm" name="hoVaTenAdd" value="<c:out value='${item.hoTen}'/>"/>
							</div>
						</div>	
						<div class="form-group col-sm-4">
							<div class="form-group  has-feedback">
								<label class="control-label">ID<br/><i style="font-weight: normal;">Số CMND</i></label>
								<input type="text" class="form-control input-sm" name="soCmtAdd" value="<c:out value='${item.soCmt}'/>"/>
							</div>
						</div>
						<div class="form-group col-sm-4">
							<div class="form-group  has-feedback">
								<label class="control-label">Email<br/><i style="font-weight: normal;">Email</i></label>
								<input type="text" class="form-control input-sm" name="emailAdd" value="<c:out value='${item.email}'/>"/>
							</div>
						</div>
					</div>
				<c:if test="${status.index == 0 }"></div></c:if>
			</c:forEach>
		</div>
		<button type="button" style="margin-top: 10px;" id="themTempalteAdd"><i class="fa fa-plus" aria-hidden="true"></i></button>
		<button type="button" style="margin-top: 10px;" id="boTempalteAdd"><i class="fa fa-minus minus" aria-hidden="true"></i></button>
		<div class="row" style="margin-top: 30px;">
			<div class="form-group col-sm-12">
				<table class="table table-striped table-hover table-bordered">
					<tr>
						<td>Authorisation for<br/><i> Ủy quyền đối với</i></td>
						<td>User 1<br/><i>Người Sử Dụng 1</i></td>
						<td>User 2<br/><i>Người Sử Dụng 2</i></td>
						<td>User 3<br/><i>Người Sử Dụng 3</i></td>
						<td>User 4<br/><i>Người Sử Dụng 4</i></td>
						<td>User 5<br/><i>Người Sử Dụng 5</i></td>
						<td>User 6<br/><i>Người Sử Dụng 6</i></td>
					</tr>
					<tr id="tdr1">
						<td>Prepare Instructions<br/><i>Tạo các lệnh</i></td>
						<td class="user1" style="text-align: center;"></td>
						<td class="user2" style="text-align: center;"></td>
						<td class="user3" style="text-align: center;"></td>
						<td class="user4" style="text-align: center;"></td>
						<td class="user5" style="text-align: center;"></td>
						<td class="user6" style="text-align: center;"></td>
					</tr>
					<tr id="tdr2">
						<td>View, print, download reports<br/><i>Xem, in, tải các báo cáo</i></td>
						<td class="user1" style="text-align: center;"></td>
						<td class="user2" style="text-align: center;"></td>
						<td class="user3" style="text-align: center;"></td>
						<td class="user4" style="text-align: center;"></td>
						<td class="user5" style="text-align: center;"></td>
						<td class="user6" style="text-align: center;"></td>
					</tr>
					<tr id="tdr3">
						<td>Approve and release Instructions<br/><i>Chấp thuận và đưa ra các lệnh</i></td>
						<td class="user1" style="text-align: center;"></td>
						<td class="user2" style="text-align: center;"></td>
						<td class="user3" style="text-align: center;"></td>
						<td class="user4" style="text-align: center;"></td>
						<td class="user5" style="text-align: center;"></td>
						<td class="user6" style="text-align: center;"></td>
					</tr>
					<tr id="tdr4">
						<td>Approve and release Instructions only jointly<br/><i>Chấp thuận và đưa ra các Lệnh chỉ thị đồng thời</i></td>
						<td class="user1" style="text-align: center;"></td>
						<td class="user2" style="text-align: center;"></td>
						<td class="user3" style="text-align: center;"></td>
						<td class="user4" style="text-align: center;"></td>
						<td class="user5" style="text-align: center;"></td>
						<td class="user6" style="text-align: center;"></td>
					</tr>
				</table>
			</div>
		</div>
		<div class="row" style="margin-top: 30px;">
			<div class="form-group col-sm-12">
				<div class="form-group  has-feedback">
					<label class="control-label">Register user(s) for e-supporting document upload via S2B<br/><i style="font-weight: normal;">Đăng ký Người sử dụng chức năng tải chứng từ bổ sung qua S2B</i></label>
					<input type="text" class="form-control input-sm" name="dangKyNguoiSuDung" id="dangKyNguoiSuDung" value="All users" value="<c:out value='${ekycDoanhNghiep.registerUser}'/>"/>
				</div>
			</div>	
		</div>
		<div class="row">
				<div class="form-group col-sm-12">
					<div class="form-group  has-feedback">
						<label class="control-label">Special Instructions <br/> <i style="font-weight: normal;">Hướng dẫn đặc biệt</i></label>
						<input type="text" class="form-control input-sm" name="specialInstructionsUser" id="specialInstructionsUser" value="<c:out value='${ekycDoanhNghiep.specialInstructionsUser}'/>"/>
					</div>
				</div>
			</div>
		
		<div class="form-group col-sm-12">
			<button class="btn btn-primary nextBtn pull-right" type="button" onclick="validateStep14Start(this)" data-loading-text="<i class='fa fa-circle-o-notch fa-spin'></i> <spring:message code="ekycdn.dang_xy_ly" />"><spring:message code="ekycdn.tiep_theo" /></button>
			<button class="btn btn-default pull-right" type="button" onclick="prevStep(this)" style="margin-right: 10px;"><spring:message code="ekycdn.quay_lai" /></button>
		</div>
	</div>
</div>

<script type="text/javascript">
var tdr1 = [];
var tdr2 = [];
var tdr3 = [];
var tdr4 = [];
<c:forEach items="${userDesignation}" var="item" varStatus="status">
tdr1.push('${item.taoLenh}');
tdr2.push('${item.baoCao}');
tdr3.push('${item.chapThuanLenh}');
tdr4.push('${item.chapThuanLenhDongThoi}');
</c:forEach>
</script>

<script type="text/javascript">
	function validateStep14Start(obj) {
		if(validateThongTin("Add")) {
			$(obj).button('loading');
			var data = {
				"userDesignation": 	getArrayPersonUserDesignation("Add"),
				"registerUser": $("#dangKyNguoiSuDung").val(),
				"specialInstructionsUser": $("#specialInstructionsUser").val()
			};
			$.ajax({
				url:'/ekyc-enterprise/luu-thong-tin-step9',
			    data: JSON.stringify(data),
			    type: 'POST',
			    processData: false,
			    contentType: 'application/json'
			}).done(function(data) {
				if(data.status == 200) {
					token = data.token;
					nextStep(obj);
					$(obj).button('reset');
				} else if(data.status == 505){
					location.href='/ekyc-enterprise';
				} else {
					toastr.error("Not enough information to store / Không đủ thông tin cần lưu trữ");
					$(obj).button('reset');	
				}
			}).fail(function(data) {
				toastr.error("Error check / Lỗi lưu thông tin");
				$(obj).button('reset');
			}); 
		}
	}
	$(document).ready(function(){
		addCheckUser();
		$("#themTempalteAdd").click(function(){
			if($("#divTemplateAdd .row").length < 6) {
				$("#divTemplateAdd").append($("#templateAdd .row").clone());
				addCheckUser();
			}
		});
		$("#boTempalteAdd").click(function(){
			if($("#divTemplateAdd .row").length > 1) {
				$("#divTemplateAdd .row:last").remove();
				addCheckUser();
			}
		});
		function addCheckUser() {
			for(i=1; i<=6; i++) {
				$("#tdr1 .user"+i).html("");
				$("#tdr2 .user"+i).html("");
				$("#tdr3 .user"+i).html("");
				$("#tdr4 .user"+i).html("");
			}
			for(i=1; i<=$("#divTemplateAdd .row").length; i++) {
				var j = i-1;
				/* if(tdr1[j] && tdr1[j] == 'Y')  */
					$("#tdr1 .user"+i).html("<input type='checkbox' name='tdr1user"+i+"' checked>");
				/* else
					$("#tdr1 .user"+i).html("<input type='checkbox' name='tdr1user"+i+"'>"); */
				
				if(tdr2[j] && tdr2[j] == 'Y') 
					$("#tdr2 .user"+i).html("<input type='checkbox' name='tdr2user"+i+"' checked>");
				else
					$("#tdr2 .user"+i).html("<input type='checkbox' name='tdr2user"+i+"'>");
				
				if(tdr3[j] && tdr3[j] == 'Y') 
					$("#tdr3 .user"+i).html("<input type='checkbox' name='tdr3user"+i+"' checked>");
				else
					$("#tdr3 .user"+i).html("<input type='checkbox' name='tdr3user"+i+"'>");
				
				if(tdr4[j] && tdr4[j] == 'Y')
					$("#tdr4 .user"+i).html("<input type='checkbox' name='tdr4user"+i+"' checked>");
				else
					$("#tdr4 .user"+i).html("<input type='checkbox' name='tdr4user"+i+"'>");
			}
		}
	});
	function validateThongTin2(sub) {
		var check = 0;
		$("input[name='hoVaTen"+sub+"']").each(function(){
			if($(this).val() == "") {
				check ++;
				toastr.error("Full Name is not empty");
			}
		});
		$("input[name='soCmt"+sub+"']").each(function(){
			if($(this).val() == "") {
				check ++;
				toastr.error("ID card number is not empty");
			}
		});
		$("input[name='email"+sub+"']").each(function(){
			if(!validateEmail( $(this).val())) {
				check ++;
				toastr.error("Email invalid");
			}
		});
		if(check > 0) return false;
		return true;
	}
	function getArrayPersonUserDesignation(type) {
		var arr = [];
		$("input[name='soCmt"+type+"']").each(function(index){
			var json = {};
			if($("input[name='soCmt"+type+"']").eq(index).val() != "") {
				var userIndex = index+1;
				json["hoTen"] = $("input[name='hoVaTen"+type+"']").eq(index).val();
				json["soCmt"] = $("input[name='soCmt"+type+"']").eq(index).val();
				json["email"] = $("input[name='email"+type+"']").eq(index).val();
				if($("input[name='tdr1user"+userIndex+"']")) {
					var valtaoLenh = $("input[name='tdr1user"+userIndex+"']").prop("checked")?"Y":"N";
					json["taoLenh"] = valtaoLenh;		
					var valbaoCao = $("input[name='tdr2user"+userIndex+"']").prop("checked")?"Y":"N";
					json["baoCao"] = valbaoCao;	
					var valchapThuanLenh = $("input[name='tdr3user"+userIndex+"']").prop("checked")?"Y":"N";
					json["chapThuanLenh"] = valchapThuanLenh;	
					var valchapThuanLenhDongThoi = $("input[name='tdr4user"+userIndex+"']").prop("checked")?"Y":"N";
					json["chapThuanLenhDongThoi"] = valchapThuanLenhDongThoi;	
				}
				arr.push(json);
			}
		});
//	 	console.log(arr);
		return arr;
	}
</script>