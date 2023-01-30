<%@ page contentType="text/html; charset=UTF-8"%>
<div class="row clearfix">
	<h3 style="clear: both;"><spring:message code="ekycdn.xem_noi_dung_file_ky" /></h3>
	<hr style="color: #CCC;border-top: 1px solid #CCC;clear: both;"/>
	<div class="col-sm-12">
				<iframe id="base64FileKy"
					style="width: 100%; height: 400px; border: 0;"></iframe>
			</div>
</div>
<script type="text/javascript">

var b64toBlob1 = (b64Data, contentType='', sliceSize=512) => {
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

var contentType1 = 'application/pdf';
var b64Data1 = '${fileKy }';

var blob1 = b64toBlob1(b64Data1, contentType1);
var blobUrl1 = URL.createObjectURL(blob1);
$(document).ready(function(){
	$("#base64FileKy").attr("src", blobUrl1);	
})
</script>