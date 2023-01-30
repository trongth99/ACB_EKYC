<%@ page contentType="text/html; charset=UTF-8"%>
<div class="panel panel-primary setup-content" id="step-4">
	<div class="panel-heading">
		<h3 class="panel-title">Chief Accountant / Person in charge of Accounting / <i>Kế toán trưởng / Người phụ trách Kế toán</i></h3>
	</div>
	<div class="panel-body">
	<!-- 	<div class="row">
			<div class="form-group col-sm-12">
				<div class="form-group  has-feedback">
					<label class="control-label">
					
					<input type="checkbox"  name="xacNhanKttShow" id="xacNhanKttShow" style="margin-right: 20px;" />
					
					
						Does your company have a Chief Accountant (or Person in charge of Accounting) appointed under the Company's constitutional documents? / 
						<i style="font-weight: normal;">Công ty của bạn có Kế toán trưởng (hoặc Người phụ trách Kế toán) được bổ nhiệm theo các văn bản hiến pháp của Công ty không?</i>
					</label>
				</div>
			</div>
		</div> -->
		
		<div  >
			<div id="divTemplateKtt">
				<div style="margin-bottom: 10px;" id="templateKtt">
				<c:forEach items="${chiefAccountant}" var="item" >
				<div class="row">
						<div class="form-group col-sm-3">
							<div class="form-group  has-feedback">
								<label class="control-label">Full name<br/><i style="font-weight: normal;">Họ và tên</i></label>
								<input type="text" class="form-control input-sm" name="hoVaTenKtt" value="<c:out value='${item.hoTen}'/>"/>
							</div>
						</div>	
						<div class="form-group col-sm-3">
							<div class="form-group  has-feedback">
								<label class="control-label">Mobile number<br/><i style="font-weight: normal;">Số điện thoại</i></label>
								<input type="text" class="form-control input-sm" name="soDienThoaiKtt" value="<c:out value='${item.phone}'/>"/>
							</div>
						</div>
						<div class="form-group col-sm-3">
							<div class="form-group  has-feedback">
								<label class="control-label">Email<br/><i style="font-weight: normal;">Email</i></label>
								<input type="text" class="form-control input-sm" name="emailKtt"  value="<c:out value='${item.email}'/>"/>
							</div>
						</div>
						<div class="form-group col-sm-3">
							<div class="form-group  has-feedback">
								<label class="control-label">Type<br/><i style="font-weight: normal;">Loại</i></label>
								<select class="form-control input-sm" id="typeKtt" name="typeKtt">
									<option value="Chief Account / Kế toán trưởng" <c:if test="${item.loai eq 'Chief Account / Kế toán trưởng' }">selected="selected"</c:if>>Chief Account / Kế toán trưởng</option>
									<option value="PIC of Accountant / Người phụ trách Kế toán" <c:if test="${item.loai eq 'PIC of Accountant / Người phụ trách Kế toán' }">selected="selected"</c:if>>PIC of Accountant / Người phụ trách Kế toán</option>
								</select>
							</div>
						</div>

					</div>
				</c:forEach>
				</div>
					<div class="row">
					<div class="form-group col-sm-12">
						<div class="form-group  has-feedback">
							<label class="control-label">
							<c:if test="${haveAChiefAccountant eq 'yes'}">
							<input type="checkbox"  name="xacNhanKtt" id="xacNhanKtt" style="margin-right: 20px;" checked="checked"/>
							</c:if>
							<c:if test="${haveAChiefAccountant eq 'no'}">
							<input type="checkbox"  name="xacNhanKtt" id="xacNhanKtt" style="margin-right: 20px;"/>
							</c:if>
						      
						 
								
								Does your company have anyone authorized by a chief accountant? / <i style="font-weight: normal;">Công ty bạn có ai được kế toán trưởng ủy quyền không?</i>
							</label>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="form-group col-sm-12">
			<button class="btn btn-primary nextBtn pull-right" type="button" onclick="validateStep4Start(this)" data-loading-text="<i class='fa fa-circle-o-notch fa-spin'></i> <spring:message code="ekycdn.dang_xy_ly" />" id="step4"><spring:message code="ekycdn.tiep_theo" /></button>
			<button class="btn btn-default pull-right" type="button" onclick="prevStep(this)" style="margin-right: 10px;"><spring:message code="ekycdn.quay_lai" /></button>
		</div>
	</div>
</div>
<script type="text/javascript">
	$("#themTempalteKtt").click(function(){
		$("#divTemplateKtt").append($("#templateKtt .row").clone());
	});
	$("#boTempalteKtt").click(function(){
		if($("#divTemplateKtt .row").length > 1)
			$("#divTemplateKtt .row:last").remove();
	});
/* 	$("#xacNhanKttShow").click(function(){
		if($("#xacNhanKttShow").is(":checked")) {
			$("#	").show();
		} else {
			$("#confirmKtt").hide();
		}
	}); */
	function validateStep4Start(obj) {
		console.log("gggggg")
		uploadDuLieuStep5(obj);
		console.log("hhhhhhhhhhh")
		/* if($("#xacNhanKtt").is(":checked")) {
			if(validateThongTin("Ktt")) {
				uploadDuLieuStep5(obj);
			} 
	 	} else {
			
		} */ 
	}
	
	var token = "";
	function uploadDuLieuStep5(obj) {
		$(obj).button('loading');
		
		var data = {
		
			"chiefAccountant": 	getArrayPerson("Ktt"),
			"haveAChiefAccountant": 	$("#xacNhanKtt").is(":checked")?"yes":"no",
			
		};
		console.log("mmmmmmmmm")
		console.log("mmmmmmmmm",data)
		$.ajax({
			url:'${contextPath}/ekyc-enterprise/luu-thong-tin-step5',
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
		console.log("jjjjjjjjjjjjj");
		console.log(type);
		var arr = [];
		//if(type == "Ktt" && !$("#xacNhanKtt").is(":checked")) return arr;
		$("input[name='hoVaTen"+type+"']").each(function(index){
			var json = {};
			//if($("input[name='soDienThoai"+type+"']").eq(index).val() != "") {
				var checkMain = $("input[name='checkMain"+type+"']:checked").eq(index).prop("checked")?"Y":"N";
				json["hoTen"] = $("input[name='hoVaTen"+type+"']").eq(index).val();
				json["phone"] = $("input[name='soDienThoai"+type+"']").eq(index).val();
				json["email"] = $("input[name='email"+type+"']").eq(index).val();
				if($("input[name='type"+type+"']"))
					json["loai"] = $("input[name='type"+type+"']").eq(index).val();
				json["checkMain"] = checkMain;
				json["tokenCheck"] = uuidv4();
				json["time"] = Date.now();
		 		console.log(json);
				arr.push(json);
			//}
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