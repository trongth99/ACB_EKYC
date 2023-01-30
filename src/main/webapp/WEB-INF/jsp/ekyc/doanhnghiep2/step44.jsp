<%@ page contentType="text/html; charset=UTF-8"%>
<div class="panel panel-primary setup-content" id="step-44">
	<div class="panel-heading">
		<h3 class="panel-title">Board of Director / Ban giám đốc</h3>
	</div>
	<div class="panel-body">
		<div id="divTemplateLd">
			<div style="margin-bottom: 10px;" id="templateLd">		
			<c:forEach items="${listOfLeaders}" var="item" >
				<div class="row">
					<div class="form-group col-sm-4">
						<div class="form-group  has-feedback">
							<label class="control-label">Full name<br/><i style="font-weight: normal;">Họ và tên</i></label>
							<input type="text" class="form-control input-sm" name="hoVaTenLd" value="<c:out value='${item.hoTen}'/>"/>
						</div>
					</div>	
					<div class="form-group col-sm-4">
						<div class="form-group  has-feedback">
							<label class="control-label">Mobile number<br/><i style="font-weight: normal;">Số điện thoại</i></label>
							<input type="text" class="form-control input-sm" name="soDienThoaiLd" value="<c:out value='${item.phone}'/>"/>
						</div>
					</div>
					<div class="form-group col-sm-4">
						<div class="form-group  has-feedback">
							<label class="control-label">Email<br/><i style="font-weight: normal;">Email</i></label>
							<input type="text" class="form-control input-sm" name="emailLd" value="<c:out value='${item.email}'/>"/>
						</div>
					</div>
<!-- 					<div class="form-group col-sm-3"> -->
<!-- 						<div class="form-group  col-sm-2" style="margin-top: 28px;"> -->
<!-- 							<input type="radio" class="" name="checkMainLd" style="margin-right: 20px;" checked="checked" value="Y"/> -->
<!-- 						</div> -->
<!-- 						<div class="form-group  col-sm-10" style="margin-top: 19px;"> -->
<!-- 							<label class="control-label"><spring:message code="ekycdn.dai_dien_ke_lanh_dao" /></label> -->
<!-- 						</div> -->
<!-- 					</div> -->
				</div>
			</c:forEach>
	
			
			</div>
		</div>
		<button type="button" style="margin-top: 10px;" id="themTempalteLd"><i class="fa fa-plus" aria-hidden="true"></i></button>
		<button type="button" style="margin-top: 10px;" id="boTempalteLd"><i class="fa fa-minus minus" aria-hidden="true"></i></button>
		<div class="form-group col-sm-12">
			<button class="btn btn-primary nextBtn pull-right" type="button" onclick="validateStep44Start(this)" data-loading-text="<i class='fa fa-circle-o-notch fa-spin'></i> <spring:message code="ekycdn.dang_xy_ly" />"><spring:message code="ekycdn.tiep_theo" /></button>
			<button class="btn btn-default pull-right" type="button" onclick="prevStep(this)" style="margin-right: 10px;"><spring:message code="ekycdn.quay_lai" /></button>
		</div>
	</div>
</div>
<script type="text/javascript">
	function validateStep44Start(obj) {
		if(validateThongTin("Ld")) {
			//if($("#xacNhanNuq").is(":checked")) {
				console.log("aaaaaaaaaa")
				uploadDuLieuStep6(obj);
				//nextStep($("#step5"));
			/* } else {
				if($("#xacNhanKtt").is(":checked")) {
					console.log("bbbbbbb")
					nextStep($("#step5"));
				} else {
					console.log("cccccccc")
					uploadDuLieuStep6(obj);
					nextStep($("#step6"));
				}
			} */
		}
	}
	$(document).ready(function(){
		$("#themTempalteLd").click(function(){
			$("#divTemplateLd").append($("#templateLd .row").clone());
		});
		$("#boTempalteLd").click(function(){
			if($("#divTemplateLd .row").length > 1)
				$("#divTemplateLd .row:last").remove();
		});
	});
	
	var token = "";
	function uploadDuLieuStep6(obj) {
		$(obj).button('loading');
		var data = {
			//step3
			"listOfLeaders": 	getArrayPerson("Ld"),
		};
		$.ajax({
			url:'${contextPath}/ekyc-enterprise/luu-thong-tin-step6',
		    data: JSON.stringify(data),
		    type: 'POST',
		    processData: false,
		    contentType: 'application/json'
		}).done(function(data) {
			if(data.status == 200) {
				token = data.token;
				if($("#xacNhanNuq").is(":checked")) {
					nextStep(obj);
				}else{
					if($("#xacNhanKtt").is(":checked")) {
						console.log("bbbbbbb")
						nextStep($("#step5"));
					} else {
						console.log("cccccccc")
						//uploadDuLieuStep6(obj);
						nextStep($("#step6"));
					}
				}
				
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