<%@ page contentType="text/html; charset=UTF-8"%>
<div class="panel panel-primary setup-content" id="step-6">
	<div class="panel-heading">
		<h3 class="panel-title">Authorized Person(s) of Chief Accountant (PIC of Accounting) / (Các) Người được ủy quyền của Kế toán trưởng (Người phụ trách Kế toán)</h3>
	</div>
	<div class="panel-body">
		<div id="divTemplateNuqKtt">
			<div style="margin-bottom: 10px;" id="templateNuqKtt">
			<c:forEach items="${personAuthorizedChiefAccountant}" var="item" >
			<div class="row">
					<div class="form-group col-sm-4">
						<div class="form-group  has-feedback">
							<label class="control-label">Full name<br/><i style="font-weight: normal;">Họ và tên</i></label>
							<input type="text" class="form-control input-sm" name="hoVaTenNuqKtt" value="<c:out value='${item.hoTen}'/>"/>
						</div>
					</div>	
					<div class="form-group col-sm-4">
						<div class="form-group  has-feedback">
							<label class="control-label">Mobile number<br/><i style="font-weight: normal;">Số điện thoại</i></label>
							<input type="text" class="form-control input-sm" name="soDienThoaiNuqKtt" value="<c:out value='${item.phone}'/>"//>
						</div>
					</div>
					<div class="form-group col-sm-4">
						<div class="form-group  has-feedback">
							<label class="control-label">Email<br/><i style="font-weight: normal;">Email</i></label>
							<input type="text" class="form-control input-sm" name="emailNuqKtt" value="<c:out value='${item.email}'/>"/>
						</div>
					</div>
					<div class="form-group col-sm-3 hide">
						<div class="form-group  col-sm-2" style="margin-top: 28px;">
							<input type="radio" class="" name="checkMainNuqKtt" style="margin-right: 20px;" checked="checked" value="Y"/>
						</div>
						<div class="form-group  col-sm-10" style="margin-top: 19px;">
							<label class="control-label"><spring:message code="ekycdn.dai_dien_nguoi_uy_quyen_ke-toan_truong" /></label>
						</div>
					</div>
				</div>
				</c:forEach>
			</div>
		</div>
		<button type="button" style="margin-top: 10px;" id="themTempalteNuqKtt"><i class="fa fa-plus" aria-hidden="true"></i></button>
		<button type="button" style="margin-top: 10px;" id="boTempalteNuqKtt"><i class="fa fa-minus minus" aria-hidden="true"></i></button>
		<div class="form-group col-sm-12">
			<button class="btn btn-primary nextBtn pull-right" type="button" onclick="validateStep6Start(this)" data-loading-text="<i class='fa fa-circle-o-notch fa-spin'></i> <spring:message code="ekycdn.dang_xy_ly" />" id="step6"><spring:message code="ekycdn.tiep_theo" /></button>
			<button class="btn btn-default pull-right" type="button" onclick="prevStep(this)" style="margin-right: 10px;"><spring:message code="ekycdn.quay_lai" /></button>
		</div>
	</div>
</div>
<script type="text/javascript">
	$(document).ready(function(){
		
	});
	function validateStep6Start(obj) {
		if(validateThongTin("NuqKtt")) {
			uploadDuLieuStep8(obj);
		}
	}
	$(document).ready(function(){
		$("#themTempalteNuqKtt").click(function(){
			if($("#divTemplateNuqKtt .row").length < 4)
			$("#divTemplateNuqKtt").append($("#templateNuqKtt .row").clone());
		});
		$("#boTempalteNuqKtt").click(function(){
			if($("#divTemplateNuqKtt .row").length > 1)
				$("#divTemplateNuqKtt .row:last").remove();
		});
	});
	
	var token = "";
	function uploadDuLieuStep8(obj) {
		$(obj).button('loading');
		var data = {
			
			//step3
			"personAuthorizedChiefAccountant": getArrayPerson("NuqKtt"),
			
			"specialInstructions": 	$("#specialInstructions").val(),
		};
		$.ajax({
			url:'${contextPath}/ekyc-enterprise/luu-thong-tin-step8',
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
				location.href='${contextPath}/ekyc-enterprise';
			} else {
				toastr.error("Not enough information to store / Không đủ thông tin cần lưu trữ");
				$(obj).button('reset');	
			}
		}).fail(function(data) {
			toastr.error("Error check / Lỗi lưu thông tin");
			$(obj).button('reset');
		}); 
	}

	function getArrayPerson(type) {
		var arr = [];
		//if(type == "Ktt" && !$("#xacNhanKtt").is(":checked")) return arr;
		$("input[name='soDienThoai"+type+"']").each(function(index){
			var json = {};
			if($("input[name='soDienThoai"+type+"']").eq(index).val() != "") {
				var checkMain = $("input[name='checkMain"+type+"']:checked").eq(index).prop("checked")?"Y":"N";
				json["hoTen"] = $("input[name='hoVaTen"+type+"']").eq(index).val();
				json["phone"] = $("input[name='soDienThoai"+type+"']").eq(index).val();
				json["email"] = $("input[name='email"+type+"']").eq(index).val();
				if($("input[name='type"+type+"']"))
					json["loai"] = $("input[name='type"+type+"']").eq(index).val();
				json["checkMain"] = checkMain;
				json["tokenCheck"] = uuidv4();
				json["time"] = Date.now();
//		 		console.log(json);
				arr.push(json);
			}
		});
//	 	console.log(arr);
		return arr;
	}


	function getArrayAccount() {
		var arr = [];
		console.log("hdbfhdsfds");
		console.log($( "select[name='accountType']" ).text());
		$("input[name='accountTitle']").each(function(index){
			console.log(index);
			var json = {};
			if($("input[name='accountType']").eq(index).val() != "" && $("input[name='currency']").eq(index).val() != "") {
				//var checkMain = $("input[name='checkMain"+type+"']:checked").eq(index).prop("checked")?"Y":"N";
				json["accountType"] = $( "select[name='accountType']" ).eq(index).find(":selected").val();
				json["currency"] = $( "select[name='currency']" ).eq(index).find(":selected").val();
				json["accountTitle"] = $("input[name='accountTitle']").eq(index).val();
				console.log($( "input[name='accountType']" ).eq(index).text());
		 		console.log(json);
				arr.push(json);
			}
		});
	 	console.log(arr);
		return arr;
	}

	function uuidv4() {
	  return ([1e7]+-1e3+-4e3+-8e3+-1e11).replace(/[018]/g, c =>
	    (c ^ crypto.getRandomValues(new Uint8Array(1))[0] & 15 >> c / 4).toString(16)
	  );
	}
	
</script>