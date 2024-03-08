<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/components/taglib.jsp" %>
	<div class="section text-center">
	   <div class="section-detail">
	   	   <div class="row">
                <div class="col-lg-12 text-center" style="margin-bottom: 15px">
                    <h2 style="font-size: 22px; text-transform: uppercase">Đặt hàng thành công</h2>
                    <div style="font-size: 16px">
                        <a href="${urlIndex}">Trang chủ</a>
                        <span> / Chi tiết đơn hàng</span>
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
	<div class="section" id="info-cart-wp">
	   <div class="section-detail">
	   		<div>
	   			<h3 style="margin-bottom: 10px; color: red">Thông tin khách hàng</h3>
		   		<div>
		   			<p>Tên: ${order.orderName}</p>
		   			<p>Số điện thoại: ${order.orderPhone}</p>
		   			<p>Email: ${order.orderEmail}</p>
		   			<p>Địa chỉ: ${order.orderAddress}</p>
		   		</div>
	   		</div>
	   		<div style="margin: 20px 0px">
	   			<h3 style="margin-bottom: 10px; color: red">Thông tin đơn hàng</h3>
		   		<div>
		   			<p>Mã đơn hàng: ${order.orderId}</p>
		   			<p>Ngày đặt hàng: ${dateUtil.formatDate(order.createAt)}</p>
		   			<p>Trạng thái đơn hàng: 
		   				<c:if test="${order.orderStatus == 0}">
							<span>Chưa xác nhận</span>
						</c:if>
		   				<c:if test="${order.orderStatus == 1}">
							<span>Đã nhận đơn hàng</span>
						</c:if>
						<c:if test="${order.orderStatus == 2}">
							<span>Đang xử lý</span>
						</c:if>
						<c:if test="${order.orderStatus == 3}">
							<span>Đang giao hàng</span>
						</c:if>
						<c:if test="${order.orderStatus == 4}">
							<span>Giao hàng thành công</span>
						</c:if>
						<c:if test="${order.orderStatus == 5}">
							<span>Đã huỷ</span>
						</c:if>
		   			</p>
		   			<p>Ghi chú đơn hàng: 
		   				<c:choose>
		   					<c:when test="${not empty order.orderNote}">
		   						${order.orderNote}
		   					</c:when>
		   					<c:otherwise>
		   						Không có
		   					</c:otherwise>
		   				</c:choose>
		   			</p>
		   		</div>
	   		</div>
	   </div>
	   <div class="section-detail table-responsive">
	      <form action="">
	      	 <h3 style="margin-bottom: 15px; color: red">Thông tin sản phẩm</h3>
	         <table class="table">
	            <thead>
	               <tr>
	                  <td>Tên sản phẩm</td>
	                  <td>Số lượng</td>
	                  <td>Giá</td>
	                  <td>Tổng tiền</td>
	               </tr>
	            </thead>
	            <tbody>
	               <c:forEach items="${listOrderDetail}" var="orderDetail">
		               <tr>
		                  <td>${orderDetail.orderProductName}</td>
		                  <td>${orderDetail.orderProductQuantity}</td>
		                  <td>${stringUtil.beautifulPrice(orderDetail.orderProductPrice)}đ</td>
		                  <td>${stringUtil.beautifulPrice(orderDetail.orderProductQuantity * orderDetail.orderProductPrice)}đ</td>
		               </tr>
	               </c:forEach>
	               <tr>
	               		<td style="text-align: right" colspan="3" align="right">Phí ship</td>
	               		<td align="right">${constantUtil.getShip()}đ</td>
	               </tr>
	               <tr style="font-size: 18px; font-weight: bold">
	               		<td style="text-align: right" colspan="3" align="right">Thành tiền</td>
	               		<td style="color: red" align="right">${order.orderTotalPrice}đ</td>
	               </tr>
	            </tbody>
	         </table>
	      </form>
	   </div>
	</div>