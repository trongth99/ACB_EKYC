<%@ page contentType="text/html; charset=UTF-8"%>
<%@include file="../layout/header.jsp"%>
<%@include file="../layout/js.jsp"%>

<div class="col-md-12 register-right">
	<div class="tab-content" id="myTabContent">
		<div class="tab-pane fade show active" id="home" role="tabpanel" aria-labelledby="home-tab">
			<h3 class="register-heading">Check out customer actions on selfie videos<br/><i style="font-weight: normal;"> Kiểm tra hành động của khách hàng trên video selfie</i></h3>
			<div class="row register-form">
				<form action="${contextPath }/ekyc-enterprise/ekyc/step2" style="width: 100%;" method="post" id="submitForm">
					<div style="width: 100%;margin: auto;margin-bottom: 20px;text-align: center;">
						<button class="btnRegister" type="button" style="background: #CCC;color: black;width: 110px;margin-top: 10px;float: none;" onclick="javascript:location.href='${contextPath }/public/demo/lvb/step1'">Back</button>
						<button class="btnRegister" id="startAndStop" type="button" style="float: none;width: 190px;margin-top: 10px;">Start</button>
						<p>
							Stay straight, then lean slowly left or right<br/>
							<i>Giữ thẳng, sau đó nghiêng người từ từ sang trái hoặc phải</i>
						</p>
					</div>
					<div class="col-md-12">
						<video id="videoInput" width="100%" playsinline autoplay></video>
	    				<canvas id="canvasOutput" width="640" height="480" style="display:none;"></canvas>
	    				<textarea name="listImage" id="listImage" style="display:none;"></textarea>
					</div>
				</form>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript">

'use strict';

//Put variables in global scope to make them available to the browser console.
const video = document.querySelector('video');
const canvas = window.canvas = document.querySelector('canvas');
canvas.width = 640;
canvas.height = 480;
var arrImg = [];
var count = 0;
$(document).ready(function(){
	$("#startAndStop").click(function(){
		$("#startAndStop").html("Start check ....");
		process();	
	});
	
});
function process() {
	try {
		canvas.width = video.videoWidth;
		canvas.height = video.videoHeight;
		canvas.getContext('2d').drawImage(video, 0, 0, canvas.width, canvas.height);
		
		postCanvasToURL();
		
		setTimeout(process, 1000);
	} catch (err) {
		console.log(err);
	}
}

function postCanvasToURL() { 
	if(count < 3) {
		var type = "image/jpeg"
	    var data = document.getElementById("canvasOutput").toDataURL(type);
	    data = data.replace('data:' + type + ';base64,', ''); 
		arrImg.push(data)
		console.log(arrImg.length)
		if(arrImg.length == 3) {
			$("#listImage").html(arrImg.toString());
			$("#submitForm").submit();
		}
		count ++;
	}
}

const constraints = {
	audio: false,
	video: true
};

function handleSuccess(stream) {
	window.stream = stream; 
	video.srcObject = stream;
}

function handleError(error) {
	console.log('navigator.MediaDevices.getUserMedia error: ', error.message, error.name);
}

navigator.mediaDevices.getUserMedia(constraints).then(handleSuccess).catch(handleError);
</script>
<%@include file="../layout/footer.jsp"%>
			
