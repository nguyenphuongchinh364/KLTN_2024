<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/components/taglib.jsp"%>
<div class="row">
	<div class="col-md-12 panel-warning">
		<div class="content-box-header panel-heading">
			<div class="panel-title ">Trang chủ</div>
		</div>
		<div class="content-box-large box-with-header">
		   <div class="row">
	           <div class="col-md-4 col-sm-4 col-xs-4">
	               <div class="panel panel-back noti-box">
	                   <span class="icon-box bg-color-green set-icon">
	                   <i class="fa fa-shopping-cart"></i>
	               </span>
	                   <div class="text-box">
	                       <p class="main-text"><a class="fs-14" href="${urlAdminOrder}" title="">Quản lý đơn hàng</a></p>
	                       <p class="text-muted">Có 1 đơn hàng mới</p>
	                   </div>
	               </div>
	           </div>
	           <div class="col-md-4 col-sm-4 col-xs-4">
	               <div class="panel panel-back noti-box">
	                   <span class="icon-box bg-color-blue set-icon">
	                   <span class="glyphicon glyphicon-phone"></span>
	               </span>
	                   <div class="text-box">
	                       <p class="main-text"><a class="fs-14" href="${urlAdminProduct}" title="">Quản lý sản phẩm</a></p>
	                       <p class="text-muted">Có 56 sản phẩm</p>
	                   </div>
	               </div>
	           </div>
	           <div class="col-md-4 col-sm-4 col-xs-4">
	               <div class="panel panel-back noti-box">
	                   <span class="icon-box bg-color-brown set-icon">
	                   <i class="fa fa-user"></i>
	               </span>
	                   <div class="text-box">
	                       <p class="main-text"><a class="fs-14" href="${urlAdminUser}" title="">Quản lý người dùng</a></p>
	                       <p class="text-muted">Có 18 người dùng</p>
	                   </div>
	               </div>
	           </div>
       	   </div>
	   </div>
	</div>
</div>

<script type="text/javascript">
	document.getElementById("admin_index").className = "current";
</script>