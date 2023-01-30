<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="springForm"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>
<html>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" href="${contextPath }/css/font-awesome.min.css">
<link rel="stylesheet" href="${contextPath }/css/bootstrap.min.css">
  <script src="${contextPath }/js/jquery.min.js"></script>
  <script src="${contextPath }/js/bootstrap.min.js"></script>
<link rel="stylesheet" href="${contextPath }/plugins/toastr/toastr.min.css">
<script src="${contextPath }/plugins/toastr/toastr.min.js"></script>
<link href="${contextPath }/plugins/sweetalert/sweetalert.css" rel="stylesheet" />
<script src="${contextPath }/plugins/sweetalert/sweetalert.min.js"></script>
<script type="text/javascript">
var flag = true;
</script>
<link rel="stylesheet" type="text/css" href="${contextPath }/css/xSigner.css">
<script src='${contextPath }/js/xSignerEkyc.js'></script>
<style>
/*@import url('//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap-theme.min.css');*/
 body {
    margin-top:30px;
}
.stepwizard-step p {
    margin-top: 0px;
    color:#666;
}
.stepwizard-row {
    display: table-row;
}
.stepwizard {
    display: table;
    width: 100%;
    position: relative;
}
.stepwizard-step button[disabled] {
    /*opacity: 1 !important;
    filter: alpha(opacity=100) !important;*/
}
.stepwizard .btn.disabled, .stepwizard .btn[disabled], .stepwizard fieldset[disabled] .btn {
    opacity:1 !important;
    color:#bbb;
}
.stepwizard-row:before {
    top: 14px;
    bottom: 0;
    position: absolute;
    content:" ";
    width: 100%;
    height: 1px;
    background-color: #ccc;
    z-index: 0;
}
.stepwizard-step {
    display: table-cell;
    text-align: center;
    position: relative;
}
.btn-circle {
    width: 30px;
    height: 30px;
    text-align: center;
    padding: 6px 0;
    font-size: 12px;
    line-height: 1.428571429;
    border-radius: 15px;
}
.setup-content {
display: none;
}
.img-thumbnail {
max-width: 400px;
}
@media only screen and (max-width: 500px) {
  .img-thumbnail {
  	  max-width: 100%;
  }
}
</style>
<body>
	<div class="container">
		<div class="row">
			<div class="col-sm-12" style="text-align: right;margin-bottom: 20px;">
				<a href="${contextPath }/ekycdn/language?language=en"><img src="${contextPath }/img/eng-active.jpg"/></a>
				<a href="${contextPath }/ekycdn/language?language=vi"><img src="${contextPath }/img/vn-active.jpg"/></a>
			</div>
		</div>
		<%@include file="menuStepDn22.jsp"%>
		<%@include file="doanhnghiep2/step7.jsp"%>
		<%@include file="doanhnghiep2/step8.jsp"%>
		<%@include file="doanhnghiep2/step9.jsp"%>
		
		<%@include file="doanhnghiep2/step11.jsp"%>
		<div class="text-center"><a href="${contextPath }/ekyc/menu"><spring:message code="trang_chu" /></a></div>
	</div>
	<script type="text/javascript">
		var successIcon = '<span class="glyphicon glyphicon-ok form-control-feedback"></span>';
		var successClass = 'has-success';
		var errorIcon = '<span class="glyphicon glyphicon-remove form-control-feedback"></span>';
		var errorClass = 'has-error';
		$(document).ready(function () {
		    var navListItems = $('div.setup-panel div.stepwizard-step a'),
		        allWells = $('.setup-content'),
		        allNextBtn = $('.nextBtn');
	
		    allWells.hide();
	
		    navListItems.click(function (e) {
		        e.preventDefault();
		        var $target = $($(this).attr('href')),
		            $item = $(this);
		        
				var curClick = $(this).text().trim();
				
				//comment de bo khoa chon
// 		        navListItems.each(function(){
// 		        	if(parseInt($(this).text().trim()) > parseInt(curClick)) {
// 		        		$(this).attr("disabled", "disabled");
// 		        		$(this).addClass("disabled");
// 		        	}
// 		        })
		        if (!$item.hasClass('disabled')) {
		            navListItems.removeClass('btn-success').addClass('btn-default');
		            $item.addClass('btn-success');
		            allWells.hide();
		            $target.show();
		            $target.find('input[type="text"]:eq(0)').focus();
		        }
		    });
	
		    $('div.setup-panel div a.btn-success').trigger('click');
		});
		
		function nextStep(obj) {
			var curStep = $(obj).closest(".setup-content"),
            curStepBtn = curStep.attr("id"),
            nextStepWizard = $('div.setup-panel div a[href="#' + curStepBtn + '"]').parent().next().children("a"),
            curInputs = curStep.find("input[type='text'],input[type='url']"),
            isValid = true;
// 	        isValid = validate(curStepBtn);

	        if (isValid) {
	        	nextStepWizard.removeClass('disabled');
	        	nextStepWizard.removeAttr('disabled').trigger('click');
	        }
		}
		
		function prevStep(obj) {
			var curStep = $(obj).closest(".setup-content"),
            curStepBtn = curStep.attr("id"),
            nextStepWizard = $('div.setup-panel div a[href="#' + curStepBtn + '"]').parent().prev().children("a"),
            curInputs = curStep.find("input[type='text'],input[type='url']"),
            isValid = true;
// 	        isValid = validate(curStepBtn);

	        if (isValid) {
	        	nextStepWizard.removeClass('disabled');
	        	nextStepWizard.removeAttr('disabled').trigger('click');
	        }
		}
		
		//validate step here
		function validate(curStepBtn) {
			if(curStepBtn == "step-2") {
				return validateStep2(curStepBtn);
			}
			return true;
		}
		function getBase64(file, idZen) {
		   var reader = new FileReader();
		   reader.readAsDataURL(file);
		   reader.onload = function () {
			   base64 = reader.result.replace(/^data:.+;base64,/, '');
			   $("#"+idZen).html(base64);
			   $("#"+idZen+"Img").attr("src", "data:image/jpeg;base64,"+base64);
			   $("#"+idZen+"Img").show();
		   };
		   reader.onerror = function (error) {
		     	console.log('Error: ', error);
		   };
		}
		function getBase64File(file, idZen) {
		   var reader = new FileReader();
		   reader.readAsDataURL(file);
		   reader.onload = function () {
			   base64 = reader.result.replace(/^data:.+;base64,/, '');
			   $("#"+idZen).html(base64);
		   };
		   reader.onerror = function (error) {
		     	console.log('Error: ', error);
		   };
		}
		function addCheckClass(value, valueCheck, type, id) {
			$("#"+id).parent().removeClass(successClass);
			$("#"+id).parent().removeClass(errorClass);
			if(type == "string") {
				if((valueCheck != null && value != null && value.toLowerCase() == valueCheck.toLowerCase())) {
					$("#"+id).parent().addClass(successClass);
					$("#"+id).parent().append(successIcon);
				} else if (valueCheck == "" || valueCheck == null || value == "" || value == null) {
					
				} else {
					$("#"+id).parent().addClass(errorClass);
					$("#"+id).parent().append(errorIcon);
					$("#"+id).parent().append('<span class="help-block">'+valueCheck+'</span>');
				}
			}
		}
		var _validFileExtensions = [".jpg", ".pdf", ".jpeg", ".png"];    
		function ValidateSingleInput(oInput) {
		    if (oInput.type == "file") {
		        var sFileName = oInput.value;
		         if (sFileName.length > 0) {
		            var blnValid = false;
		            for (var j = 0; j < _validFileExtensions.length; j++) {
		                var sCurExtension = _validFileExtensions[j];
		                if (sFileName.substr(sFileName.length - sCurExtension.length, sCurExtension.length).toLowerCase() == sCurExtension.toLowerCase()) {
		                    blnValid = true;
		                    break;
		                }
		            }
		             
		            if (!blnValid) {
		            	toastr.error("Chỉ chấp nhận file có định dạng " + _validFileExtensions.join(", "));
		                oInput.value = "";
		                return false;
		            }
		        }
		    }
		    return true;
		}
		
		function addEventAllFile(fileInput, idBase64, idNameShow) {
			$("#"+fileInput).change(function(){
				if(ValidateSingleInput(this)) {
					var file =document.getElementById(fileInput).files[0];
					var url =URL.createObjectURL(file );
					$("#"+idBase64+"Img").attr("src", url);
					$("#"+idBase64).html("");
					$("#"+idBase64+"Img").show();
					getBase64File(file, idBase64);
					
					$("#"+idNameShow).html($("#"+fileInput).val());
					if($("#"+idNameShow).html()!="") {
						$("#"+idNameShow).show();
					}
				}
			}); 
			
			$("#"+fileInput).click(function(){
				$("#"+fileInput).val("");
				$("#"+idNameShow).hide();
			})
		}
		
		function addEventFile(fileInput, idBase64, idNameShow) {
			$("#"+fileInput).change(function(){
				if(ValidateSingleInput(this)) {
					$("#"+idBase64).html("");
					var file = document.querySelector('#'+fileInput).files[0];
					getBase64(file, idBase64);
				}
				$("#"+idNameShow).html($("#"+fileInput).val());
				if($("#"+idNameShow).html()!="") {
					$("#"+idNameShow).show();
				}
			});
			$("#"+fileInput).click(function(){
				$("#"+fileInput).val("");
				$("#"+idNameShow).hide();
				$("#"+idBase64+"Img").attr("src", "");
				$("#"+idBase64+"Img").hide();
			})
		}
		function numberWithCommas(x) {
			if(x && x.trim()!="")
		    	return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
			
			return "";
		}
	</script>
	
</body>
</html>
