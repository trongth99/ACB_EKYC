<%@ page contentType="text/html; charset=UTF-8"%>
<div class="panel panel-primary setup-content" id="step-10">
	<div class="panel-heading">
		Special Instructions / Hướng dẫn đặc biệt
	</div>
	<div class="panel-body">
		<div class="form-group text-center">
		
			<div class="row">
				<div class="form-group col-sm-12">
					<div class="form-group  has-feedback">
						<label class="control-label">Special Instructions <br/> <i style="font-weight: normal;">Hướng dẫn đặc biệt</i></label>
						<input type="text" class="form-control input-sm" name="specialInstructions" id="specialInstructions" value="<c:out value='${ekycDoanhNghiep.specialInstructions}'/>"/>
					</div>
				</div>
			</div>	
			<button class="btn btn-primary nextBtn pull-right" type="button" onclick="validateStep7Start(this)" data-loading-text="<i class='fa fa-circle-o-notch fa-spin'></i> <spring:message code="ekycdn.dang_xy_ly" />" id="step10">Save</button>
		</div>
	</div>
</div>
<script type="text/javascript">
function validateStep7Start(obj) {
	if($("#specialInstructions").val() != "") {
		uploadDuLieuStep9(obj);
		//nextStep(obj);
	} else {
		toastr.error("Special Instructions is not empty");
	}
}
var token = "";
function uploadDuLieuStep9(obj) {
	$(obj).button('loading');
	var data = {
		
		//step3
		
		
		"specialInstructions": 	$("#specialInstructions").val(),
	};
	$.ajax({
		url:'${contextPath}/ekyc-enterprise/luu-thong-tin-step10',
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
//	 		console.log(json);
			arr.push(json);
		}
	});
// 	console.log(arr);
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