<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div id="header-wp">
<div id="head-top" class="clearfix">
   <div class="wp-inner clearfix">
      <a href="" title="" id="payment-link" data-toggle="modal"  data-target="#exampleModalCenter" class="fl-left">Hình thức thanh toán</a>
      <div class="modal fade" id="exampleModalCenter" tabindex="-1" role="dialog" aria-hidden="true">
         <div class="modal-dialog modal-dialog-centered" role="document">
            <div class="modal-content">
               <div class="modal-header">
                  <h3 class="modal-title" id="exampleModalLongTitle" style="font-size: 16px;">Hình thức thanh toán</h3>
               </div>
               <div class="modal-body">
                  <img src="${contextPath}/images/thanh-toan.jpg" alt="">
               </div>
               <div class="modal-footer">
                  <button type="button" class="btn btn-primary" data-dismiss="modal">Đóng</button>
               </div>
            </div>
         </div>
      </div>
      
      <div id="main-menu-wp" class="fl-right">
         <ul id="main-menu" class="clearfix">
            <li>
               <a href="${urlIndex}" title="">Trang chủ</a>
            </li>
            <c:choose>
            	<c:when test="${not empty userLogin}">
            		<li>
		               <a href="${urlOrder}" title="Xem lịch sử mua hàng">Đơn hàng</a>
		            </li>
            		<li>
		               <a href="${urlAccount}/thong-tin-ca-nhan" title="">Thông tin cá nhân</a>
		            </li>
		            <li>
		               <a href="javascript:void(0)" title="">Chào, ${userLogin.userFullname}</a>
		            </li>
		            <li>
		               <a href="${urlLogout}" title="">Đăng xuất</a>
		            </li>
            	</c:when>
            	<c:otherwise>
	            	<li>
		               <a href="${urlLogin}" title="">Đăng nhập</a>
		            </li>
            	</c:otherwise>
            </c:choose>
         </ul>
      </div>
   </div>
</div>

<div id="head-body" class="clearfix">
   <div class="wp-inner clearfix">
      <a href="${urlIndex}" title="" id="logo" class="fl-left"><img src="${contextPath}/images/logo.png"/></a>
      <div id="search-wp" class="fl-left">
         <form action="${urlSearch}" method="get" style="position: relative; z-index: 11;">
            <input type="text" name="keyword" value="${keyword}" id="s" placeholder="Tìm kiếm...">
            <input type="submit" id="sm-s" value="Tìm kiếm">
            <div id="countryList">
               <div class="sremain">
                  <ul style="display:none">
                  </ul>
               </div>
            </div>
         </form>
      </div>
      <div id="action-wp" class="fl-right">
         <div id="advisory-wp" class="fl-left">
            <span class="title">Tư vấn</span>
            <span class="phone">0987.654.321</span>
         </div>
         <div id="btn-respon" class="fl-right"><i class="fa fa-bars" aria-hidden="true"></i></div>
         <div id="cart-wp" class="fl-right">
            <div id="btn-cart">
               <a style="color: white" href="${urlCart}" title="Giỏ hàng">
               	   <i class="fa fa-shopping-cart" aria-hidden="true"></i>
	               <c:set value="0" var="totalQuantity"></c:set>
	               <c:if test="${not empty cart}">
	               		<c:set value="${cart.totalQuantity}" var="totalQuantity"></c:set>
	               </c:if>
	               <span id="num" class="num-pd">${totalQuantity}</span>
               </a>
            </div>
         </div>
      </div>
   </div>
</div>
</div>