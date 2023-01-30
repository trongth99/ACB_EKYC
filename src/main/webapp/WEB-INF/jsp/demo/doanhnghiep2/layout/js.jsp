<%@ page contentType="text/html; charset=UTF-8"%>
<input type="hidden" value="${success}" id="success"/>
<input type="hidden" value="${error}" id="error"/>
<script type="text/javascript">
$(document).ready(function(){
	$('ul.pagination').on('click', 'a', function () {
        // Create paging action
        $('#action').val('paging');
        var value = $(this).attr('data-value');
        var $page = $('#page');
        
        switch (value) {
            case 'next':
                $page.val(parseInt($page.val()) + 1);
                break;
            case 'end':
                $page.val('${totalPage}');
                break;
            case 'prev':
                if((parseInt($page.val()) - 1) >= 1)
                    $page.val(parseInt($page.val()) - 1);
                break;
            case 'begin':
                $page.val(1);
                break;
            default:
                $page.val(value);
        }
        $("#submitForm").attr("method", "GET");
        $("#submitForm").submit();
	});
	if($("#success").val() != "") {
		toastr.info($("#success").val());
    }
    if($("#error").val() != "") {
    	toastr.error($("#error").val());
    }
});
function loadAdd(urlAdd) {
	loadUrl(urlAdd);
}
function loadEdit(urlEdit) {
	loadUrl(urlEdit);
}
function loadUrl(url) {
	resetInfo();
	$.ajax({
		url : url,
		success : function(result) {
			$("#modal-content").html(result);
		}
	});
}
function resetInfo() {
	$("#error").val("");
	$("#success").val("");
	showWait();
}
function showWait() {
    $('#modal-content').html('<div  style="padding:10px;" class="d-flex justify-content-center"><div class="spinner-border text-primary" role="status"> <span class="sr-only">Loading...</span> </div></div>');

}

function processTable(data, idField, foreignKey, rootLevel) {
    let hash = {};

    for (let i = 0; i < data.length; i++) {
        let item = data[i];
        let id = item[idField];
        let parentId = item[foreignKey];

        hash[id] = hash[id] || [];
        hash[parentId] = hash[parentId] || [];

        item.items = hash[id];
        item.expanded = true;
        hash[parentId].push(item);
    }

    return hash[rootLevel];
}

function list_to_tree(list) {
        var map = {}, node, roots = [], i;
        for (i = 0; i < list.length; i += 1) {
            map[list[i].id] = i; // initialize the map
            list[i].items = []; // initialize the children
        }
        for (i = 0; i < list.length; i += 1) {
            node = list[i];
            if (node.parentId !== 0) {
                // if you have dangling branches check that map[node.parentId] exists
                list[map[node.parentId]].items.push(node);
            } else {
                roots.push(node);
            }
        }
        return roots;
    }


function alertSC(msg) {
	$.growl.notice({
        title: "Thông báo",
        message: msg
    });
}
function alertER(msg) {
	$.growl.error({
        title: "Thông báo",
        message: msg
    });
}
function deleteRC(url) {
	alertRC(url, "Bạn có chắc muốn xóa?");
}
function change(url) {
	alertRC(url, "Bạn có chắc muốn thay đổi?");
}
function nextUrl(url) {
	location.href=url;
}
function alertRC(url, title) {
	swal({
		title: title,
        text: "",
        type: "warning",
        showCancelButton: true,
        confirmButtonColor: "#DD6B55",
        confirmButtonText: "Đồng ý",
        cancelButtonText: "Thoát",
        closeOnConfirm: false
    }, function () {
    	location.href=url;
    });
}
function goBack() {
  	window.history.back();
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
	function addEventFile(fileInput) {
		$("#"+fileInput).change(function(){
			if(ValidateSingleInput(this)) {
				var file = document.querySelector('#'+fileInput).files[0];
				getBase64(file, fileInput);
			}
		});
	}
	var _validFileExtensions = [".jpg", ".jpeg", ".png"];    
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
	            	toastr.error("Accepted file: " + _validFileExtensions.join(", "));
	                oInput.value = "";
	                return false;
	            }
	        }
	    }
	    return true;
	}
	
	var b64toBlob = (b64Data, contentType='', sliceSize=512) => {
		var byteCharacters = atob(b64Data);
		var byteArrays = [];
		
		for (let offset = 0; offset < byteCharacters.length; offset += sliceSize) {
			var slice = byteCharacters.slice(offset, offset + sliceSize);
			
			var byteNumbers = new Array(slice.length);
			for (let i = 0; i < slice.length; i++) {
				byteNumbers[i] = slice.charCodeAt(i);
			}
			
			var byteArray = new Uint8Array(byteNumbers);
			byteArrays.push(byteArray);
		}
		
		var blob = new Blob(byteArrays, {type: contentType});
		return blob;
	}
</script>