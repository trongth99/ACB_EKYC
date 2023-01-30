<%@ page contentType="text/html; charset=UTF-8"%>
<%@include file="../layout/header2.jsp"%>
<%@include file="../layout/js.jsp"%>
<style>
.col-xs-12 {
	background: none;
}
</style>
<div class="content-wrapper">
	<section class="content-header">
		<h1>
			<spring:message code="dashboard" /> (<span id='clock'></span>)
		</h1>
		<ol class="breadcrumb">
			<li>
				<a href="${contextPath}/">
					<spring:message code="trang_chu" />
				</a>
			</li>
			<li class="active">
				<spring:message code="dashboard" />
			</li>
		</ol>
	</section>

	<form id="submitForm" action="" method="get">
		<section class="content container-fluid">
			<div class="row">
				<div class="col-md-4 col-sm-6 col-xs-12">
					<div class="info-box">
						<span class="info-box-icon bg-green">
							<i class="fa fa-check-square-o"></i>
						</span>

						<div class="info-box-content">
							<span class="info-box-text">
								<spring:message code="ekyc_thanh_cong" />
							</span>
							<span class="info-box-number">
								<c:set var = "tnew" scope = "session" value = "0"/>
								<c:forEach items="${thongKeTrangThais}" var="item" varStatus="status">
									<c:if test="${item.trangThaiEkyc eq 'success' }"><c:set var = "tnew" scope = "session" value = "${item.tongSo }"/></c:if>
								</c:forEach>
								${tnew }
							</span>
						</div>
						<!-- /.info-box-content -->
					</div>
					<!-- /.info-box -->
				</div>
				<!-- /.col -->
				<div class="col-md-4 col-sm-6 col-xs-12">
					<div class="info-box">
						<span class="info-box-icon bg-red">
							<i class="fa fa-times"></i>
						</span>

						<div class="info-box-content">
							<span class="info-box-text">
								<spring:message code="ekyc_that_bai" />
							</span>
							<span class="info-box-number">
								<c:set var = "reject" scope = "session" value = "0"/>
								<c:forEach items="${thongKeTrangThais}" var="item" varStatus="status">
									<c:if test="${item.trangThaiEkyc eq 'fail' }"><c:set var = "reject" scope = "session" value = "${item.tongSo }"/></c:if>
								</c:forEach>
								${reject }
							</span>
						</div>
						<!-- /.info-box-content -->
					</div>
					<!-- /.info-box -->
				</div>
				<div class="col-md-4 col-sm-6 col-xs-12">
					<div class="info-box">
						<span class="info-box-icon bg-yellow">
							<i class="ion ion-ios-people-outline"></i>
						</span>

						<div class="info-box-content">
							<span class="info-box-text">
								<spring:message code="tong_so" />
							</span>
							<span class="info-box-number">
								<c:set var = "tongSo" scope = "session" value = "0"/>
								<c:forEach items="${thongKeTrangThais}" var="item" varStatus="status">
									<c:if test="${item.trangThaiEkyc eq 'success' }"><c:set var = "tongSo" scope = "session" value = "${tongSo + item.tongSo }"/></c:if>
									<c:if test="${item.trangThaiEkyc eq 'fail' }"><c:set var = "tongSo" scope = "session" value = "${tongSo + item.tongSo }"/></c:if>
								</c:forEach>
								${tongSo }
							</span>
						</div>
						<!-- /.info-box-content -->
					</div>
					<!-- /.info-box -->
				</div>
				<!-- /.col -->
			</div>
			<div class="box box-danger">
				<div class="box-header">
					<button type="button" class="btn btn-sm btn-primary" onclick="redirectdb('daily')">
						<spring:message code="hang_ngay" />
					</button>
					<button type="button" class="btn btn-sm btn-primary" onclick="redirectdb('weekly')">
						<spring:message code="hang_tuan" />
					</button>
					<button type="button" class="btn btn-sm btn-primary" onclick="redirectdb('monthly')">
						<spring:message code="hang_thang" />
					</button>
					<button type="button" class="btn btn-sm btn-primary" onclick="redirectdb('yearly')">
						<spring:message code="hang_nam" />
					</button>
				</div>
				<div style="clear: both;"></div>
				<div class="box-body table-responsive no-padding"></div>
				<div class="box-footer">
					<div class="row">
						<div class="col-md-3">
							<div class="chart-responsive">
								<canvas id="pieChart" height="160" width="328" style="width: 328px; height: 160px;"></canvas>
							</div>
							<!-- ./chart-responsive -->
						</div>
						<!-- /.col -->
						<div class="col-md-4">
							<ul class="chart-legend clearfix">
								<li>
									<i class="fa fa-circle-o text-red"></i>
									<spring:message code="ekyc_that_bai" />
								</li>
								<li>
									<i class="fa fa-circle-o text-green"></i>
									<spring:message code="ekyc_thanh_cong" />
								</li>
							</ul>
						</div>
					</div>
				</div>
			</div>
		</section>
	</form>
</div>

<link rel="stylesheet" href="${contextPath }/css/morris.css">
<script src="${contextPath }/js/raphael.min.js"></script>
<script src="${contextPath }/js/morris.min.js"></script>
<script src="${contextPath }/js/Chart.js"></script>


<script type="text/javascript">

function redirectdb(type) {
	location.href='${contextPath }?type='+type;
}
function clock(){
   var timer = new Date();
   var month = timer.getUTCMonth() + 1;
   var day = timer.getUTCDate();
   var year = timer.getUTCFullYear();
   var hour = timer.getHours(); 
   var minute = timer.getMinutes(); 
   var second = timer.getSeconds();  
   if(hour < 10) {
       hour = "0" + hour;
   }
   if(minute < 10) {
       minute = "0" + minute;
   }
   if(second < 10) {
       second = "0" + second;
   }
   document.getElementById("clock").innerHTML = day+"/"+month+"/"+year+" "+hour + ":" + minute + ":" + second;
}
setInterval("clock()",1000);
</script>


<script type="text/javascript">
	var pieChartCanvas = $('#pieChart').get(0).getContext('2d');
	var pieChart = new Chart(pieChartCanvas);
	var PieData = [ {
		value : '${tnew}',
		color : '#00a65a',
		highlight : '#00a65a',
		label : '<spring:message code="ekyc_thanh_cong" />'
	}, {
		value : '${reject}',
		color : '#f56954',
		highlight : '#f56954',
		label : '<spring:message code="ekyc_that_bai" />'
	}];
	// Create pie or douhnut chart
	// You can switch between pie and douhnut using the method below.
	pieChart.Doughnut(PieData);
	
</script>

<%@include file="../layout/footer2.jsp"%>