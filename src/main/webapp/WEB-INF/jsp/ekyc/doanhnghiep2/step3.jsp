<%@ page contentType="text/html; charset=UTF-8"%>
<div class="panel panel-primary setup-content" id="step-3">
	<div class="panel-heading">
		<h3 class="panel-title">Legal representative / Đại diện pháp lý</h3>
	</div>
	<div class="panel-body">
		<div id="divTemplateNddpl">
			<div style="margin-bottom: 10px;" id="templateNddpl">

			
			  <c:forEach items="${legalRepresentator}" var="item" >
						<div class="row">
					<div class="form-group col-sm-3">
						<div class="form-group  has-feedback">
							<label class="control-label">Full name<br/><i style="font-weight: normal;">Họ và tên</i></label>
							<input type="text" class="form-control input-sm" name="hoVaTenNddpl" value="<c:out value='${item.hoTen}'/>"/>
						</div>
					</div>	
					<div class="form-group col-sm-3">
						<div class="form-group  has-feedback">
							<label class="control-label">Mobile number<br/><i style="font-weight: normal;">Số điện thoại</i></label>
							<input type="text" class="form-control input-sm" name="soDienThoaiNddpl" value="<c:out value='${item.phone}'/>"/>
						</div>
					</div>	
					<div class="form-group col-sm-3">
						<div class="form-group  has-feedback">
							<label class="control-label">Email<br/><i style="font-weight: normal;">Email</i></label>
							<input type="text" class="form-control input-sm" name="emailNddpl" value="<c:out value='${item.email}'/>"/>
						</div>
					</div>	
					<div class="form-group col-sm-3">
						<div class="form-group  col-sm-2" style="margin-top: 48px;">
						
						  <input type="radio" class="" name="checkMainNddpl" style="margin-right: 20px;" checked="checked" value="Y" />
						
						<%-- <c:if test="${item.checkMain eq 'N' }">
						  <input type="radio" class="" name="checkMainNddpl" style="margin-right: 20px;"  value="N" />
						</c:if>	 --%>	
						</div>
						<div class="form-group  col-sm-10" style="margin-top: 36px;">
							<label class="control-label">Account Holder<br/><i style="font-weight: normal;">Chủ tài khoản</i></label>
						</div>
					</div> 
				</div>
			</c:forEach>

			
				
			</div>
		</div>
		
		<div class="row">
			<div class="form-group col-sm-12">
				<div class="form-group  has-feedback">
					<label class="control-label">
					<%-- <c:if test="${checkMianLegalRepresentator eq 'Y' || checkMianLegalRepresentator.checkMain eq 'Y'}">
					<input type="checkbox"  name="xacNhanNuq" id="xacNhanNuq" style="margin-right: 20px;" checked="checked"/>
					</c:if> --%>
					<c:if test="${haveAcccountHolder eq 'yes' }">
					<input type="checkbox"  name="xacNhanNuq" id="xacNhanNuq" style="margin-right: 20px;" checked="checked"/>
					</c:if>
					<c:if test="${haveAcccountHolder eq 'no' }">
					<input type="checkbox"  name="xacNhanNuq" id="xacNhanNuq" style="margin-right: 20px;" />
					</c:if>
					
					
						
						Is there someone authorized by the account holder representative? / <i style="font-weight: normal;">Có người được đại diện chủ tài khoản ủy quyền không?</i>
					</label>
				</div>
			</div>
		</div>
		
		
		 <div class="row">
			<div class="form-group col-sm-12">
				<div class="form-group  has-feedback">
					<label class="control-label">
					<c:if test="${allInOne eq 'yes'}">
					<input type="checkbox"  name="checkAllInOne" id="checkAllInOne" style="margin-right: 20px;" checked="checked"/>
					</c:if>
						<c:if test="${allInOne eq 'no'}">
					<input type="checkbox"  name="checkAllInOne" id="checkAllInOne" style="margin-right: 20px;" />
					</c:if>
						The company has only one person to take on all the roles<i style="font-weight: normal;">/ Công ty chỉ có một người đảm nhận tất cả các vai trò</i>
					</label>
				</div>
			</div>
		</div> 
		
		
		<button type="button" style="margin-top: 10px;" id="themTempalteNddpl"><i class="fa fa-plus" aria-hidden="true"></i></button>
		<button type="button" style="margin-top: 10px;" id="boTempalteNddpl"><i class="fa fa-minus minus" aria-hidden="true"></i></button>
		<div class="form-group col-sm-12">
			<button class="btn btn-primary nextBtn pull-right" type="button" onclick="validateStep3Start(this)" data-loading-text="<i class='fa fa-circle-o-notch fa-spin'></i> <spring:message code="ekycdn.dang_xy_ly" />" id="step3"><spring:message code="ekycdn.tiep_theo" /></button>
			<button class="btn btn-default pull-right" type="button" onclick="prevStep(this)" style="margin-right: 10px;"><spring:message code="ekycdn.quay_lai" /></button>
		</div>
	</div>
</div>
<script type="text/javascript">
	$("#themTempalteNddpl").click(function(){
		$("#divTemplateNddpl").append($("#templateNddpl .row").clone());
	});
	$("#boTempalteNddpl").click(function(){
		if($("#divTemplateNddpl .row").length > 1)
			$("#divTemplateNddpl .row:last").remove();
	});
	function validateStep3Start(obj) {
		
		if($("#checkAllInOne").is(":checked") && validateThongTin("Nddpl")) {
			
			uploadDuLieuStep456(obj);
			nextStep($("#step6"));
		}else if(validateThongTin("Nddpl")){
			uploadDuLieuStep4(obj);
		}
			
	}
	function validateThongTin(sub) {
		var check = 0;
		$("input[name='hoVaTen"+sub+"']").each(function(){
			if($(this).val() == "") {
				check ++;
				toastr.error("Full Name is not empty");
			}
		});
		$("input[name='soDienThoai"+sub+"']").each(function(){
			if($(this).val() == "") {
				check ++;
				toastr.error("Phone number is not empty");
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
	function validateEmail(email) {
        var re = /\S+@\S+\.\S+/;
        return re.test(email);
    }
	
	var token = "";
	function uploadDuLieuStep4(obj) {
		$(obj).button('loading');
		var data = {
			
			//step3
			"legalRepresentator": 	getArrayPerson("Nddpl"),
			"allInOne": 	$("#checkAllInOne").is(":checked")?"yes":"no",
			"haveAcccountHolder": 	$("#xacNhanNuq").is(":checked")?"yes":"no"
					
		};
		$.ajax({
			url:'${contextPath}/ekyc-enterprise/luu-thong-tin-step-4',
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

	var token = "";
	function uploadDuLieuStep456(obj) {
		$(obj).button('loading');
		var data = {
			
			
			"legalRepresentator": 	getArrayPerson("Nddpl"),
			"chiefAccountant": 	getArrayPerson("Nddpl"),
			"listOfLeaders": 	getArrayPerson("Nddpl"),
			"allInOne": 	$("#checkAllInOne").is(":checked")?"yes":"no",
			"haveAcccountHolder": 	$("#xacNhanNuq").is(":checked")?"yes":"no"
		};
		$.ajax({
			url:'${contextPath}/ekyc-enterprise/luu-thong-tin-step-4',
		    data: JSON.stringify(data),
		    type: 'POST',
		    processData: false,
		    contentType: 'application/json'
		}).done(function(data) {
			if(data.status == 200) {
				token = data.token;
				//nextStep(obj);
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
	function uuidv4() {
		  return ([1e7]+-1e3+-4e3+-8e3+-1e11).replace(/[018]/g, c =>
		    (c ^ crypto.getRandomValues(new Uint8Array(1))[0] & 15 >> c / 4).toString(16)
		  );
		}
</script>