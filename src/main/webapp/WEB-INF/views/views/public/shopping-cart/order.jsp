<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/components/taglib.jsp" %>
<c:choose>
	<c:when test="${not empty listOrder}">
		<div class="section text-center">
		   <div class="section-detail">
		   	   <div class="row">
	                <div class="col-lg-12 text-center" style="margin-bottom: 15px">
                       <h2 style="font-size: 22px; text-transform: uppercase">Lịch sử đơn hàng</h2>
                       <div style="font-size: 16px">
                           <a href="${urlIndex}">Trang chủ</a>
                           <span> / Đơn hàng của tôi</span>
                       </div>
	                </div>
	            </div>
		   </div>
		</div>
		<c:if test="${not empty success}">
			<div class="section text-center" style="margin-bottom: 15px">
				<span style="color: blue; font-style: italic">${success}</span>
			</div>
		</c:if>
		<c:if test="${not empty error}">
			<div class="section text-center" style="margin-bottom: 15px">
				<span style="color: red; font-style: italic">${error}</span>
			</div>
		</c:if>
		<div class="section" id="info-cart-wp">
		   <div class="section-detail table-responsive">
		      <form action="">
		         <table class="table">
		            <thead>
		               <tr>
		                  <td>Mã đơn hàng</td>
		                  <td>Ngày mua</td>
		                  <td>Số lượng</td>
		                  <td>Tổng tiền</td>
		                  <td>Trạng thái đơn hàng</td>
		                  <td>Chi tiết</td>
		               </tr>
		            </thead>
		            <tbody>
		               <c:forEach items="${listOrder}" var="objOrder">
			               <tr>
			                  <td>${objOrder.orderId}</td>
			                  <td>${dateUtil.formatDate(objOrder.createAt)}</td>
			                  <td>${objOrder.orderTotalQuantity}</td>
			                  <td>${stringUtil.beautifulPrice(objOrder.orderTotalPrice)}đ</td>
			                  <td>
				                   	<c:if test="${objOrder.orderStatus == 0}">
										<span>Chưa xác nhận</span>
									</c:if>
				                   	<c:if test="${objOrder.orderStatus == 1}">
										<span>Đã nhận đơn hàng</span>
									</c:if>
									<c:if test="${objOrder.orderStatus == 2}">
										<span>Đang xử lý</span>
									</c:if>
									<c:if test="${objOrder.orderStatus == 3}">
										<span>Đang giao hàng</span>
									</c:if>
									<c:if test="${objOrder.orderStatus == 4}">
										<span>Giao hàng thành công</span>
									</c:if>
									<c:if test="${objOrder.orderStatus == 5}">
										<span>Đã huỷ</span>
									</c:if>
			                  </td>
			                  <td>
			                     <a href="${urlOrder}/don-hang-${objOrder.orderId}" title="Chi tiết đơn hàng" class="del-product">Xem</a>
			                  </td>
			               </tr>
		               </c:forEach>
		            </tbody>
		         </table>
		      </form>
		   </div>
		</div>
	</c:when>
	<c:otherwise>
		<div class="section text-center" id="action-cart-wp">
		   <div class="section-detail">
		      <p class=""><img class="img-fluid d-inline-block" src="${contextPath}/images/Shopping-Cart-Icon.png" alt="" style="width:75px;height:71px;"></p>
		      <p class="" style="font-size: 16px;color: #4a4a4a;margin-bottom: 25px;">Bạn chưa có đơn hàng</p>
		      <p class=""><a href="${urlIndex}" class="re_unimart" title="Trang chủ">ĐẾN TRANG CHỦ G18 Shop</a></p>
		   </div>
		</div>
	</c:otherwise>
</c:choose>