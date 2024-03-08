<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/components/taglib.jsp" %>
<ul class="nav">
	<li id="admin_index">
    	<a href="${urlAdminIndex}"><i class="glyphicon glyphicon-home"></i> Trang chủ</a>
    </li>
    <c:if test="${adminUserLogin.role.roleId == 2}">
    	<li id="admin_sales">
	    	<a href="${urlAdminDoanhThu}"><i class="fa fa-search-dollar"></i> Doanh thu</a>
	    </li>
    </c:if>
    <li id="admin_order">
    	<a href="${urlAdminOrder}"><i class="fa fa-shopping-cart"></i> Đơn hàng</a>
    </li>
    <li id="admin_category">
    	<a href="${urlAdminCat}"><i class="glyphicon glyphicon-list"></i> Danh mục sản phẩm</a>
    </li>
    <li id="admin_product">
    	<a href="${urlAdminProduct}"><i class="glyphicon glyphicon-phone"></i> Sản phẩm</a>
    </li>
    <c:if test="${adminUserLogin.role.roleId == 2}">
	    <li id="admin_user">
	    	<a href="${urlAdminUser}"><i class="fa fa-user"></i> Người dùng</a>
	    </li>
	    <li id="admin_reviews">
	    	<a href="${urlAdminReviews}"><i class="fa fa-comment"></i> Đánh giá sản phẩm</a>
	    </li>
    </c:if>
    <li id="admin_profile">
    	<a href="${urlAdminProfile}"><i class="fa fa-user-circle"></i> Thông tin cá nhân</a>
    </li>
</ul>