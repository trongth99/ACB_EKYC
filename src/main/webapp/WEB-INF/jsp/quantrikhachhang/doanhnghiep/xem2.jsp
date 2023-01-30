<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="springForm"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%@include file="../../layout/js.jsp"%>
<style type="text/css">
.help-block{
color: #dc3545;
}
</style>

<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
	<div class="modal-header">
		<h2><spring:message code="ekycdn.thong_tin_doanh_nghiep" /></h2>
	</div>
	<div class="modal-body">
		<div class="nav-tabs-custom">
			<ul class="nav nav-tabs">
				<li class="active">
					<a href="#tab_1" data-toggle="tab">
						<spring:message code="ekycdn.thong_tin_doanh_nghiep" />
					</a>
				</li>
				<li>
					<a href="#tab_2" data-toggle="tab">
						<spring:message code="ekycdn.nguoi_dai_dien_phap_luat" />
					</a>
				</li>
				<li>
					<a href="#tab_3" data-toggle="tab">
						<spring:message code="ekycdn.thong_tin_ke_toan_truong" />
					</a>
				</li>
				<li>
					<a href="#tab_4" data-toggle="tab">
						<spring:message code="ekycdn.nguoi_uy_quyen" />
					</a>
				</li>
				<li>
					<a href="#tab_5" data-toggle="tab">
						<spring:message code="ekycdn.ban_lanh_dao" />
					</a>
				</li>
			</ul>
			<div class="tab-content">
				<div class="tab-pane active " id="tab_1">
					<%@include file="chitiet2/chiTietDoanhNghiep.jsp"%>
				</div>
				<div class="tab-pane" id="tab_2">
					<%@include file="chitiet2/nguoiDaiDienPhapLuat.jsp"%>
				</div>
				<div class="tab-pane" id="tab_3">
					<%@include file="chitiet2/keToanTruong.jsp"%>
				</div>
				<div class="tab-pane" id="tab_4">
					<%@include file="chitiet2/nguoiDuocUyQuyen.jsp"%>
				</div>
				<div class="tab-pane" id="tab_5">
					<%@include file="chitiet2/banLanhDao.jsp"%>
				</div>
			</div>
			<div style="clear: both; margin-top: 10px;"></div>
		</div>
		
		<div class="col-md-12 mb-0 text-right">
			<button class="btn btn-danger btn-sm" data-dismiss="modal">
				<i class="fa fa-times"></i>
				<span><spring:message code="thoat" /></span>
			</button>
		</div>
		<div style="clear: both;margin-top: 20px;"></div>
	</div>
</div>

<%@include file="../../layout/footerAjax.jsp"%>
