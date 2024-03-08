<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/components/taglib.jsp" %>
	<div class="section text-center">
	   <div class="section-detail">
	   	   <div class="row">
                <div class="col-lg-12 text-center" style="margin-bottom: 15px">
                    <h2 style="font-size: 22px; text-transform: uppercase">Theo dõi đơn hàng</h2>
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
	<c:if test="${not empty error}">
		<div class="section text-center" style="margin-bottom: 15px">
			<span style="color: red; font-style: italic">${error}</span>
		</div>
	</c:if>
	<c:if test="${order.orderStatus != 5}">
		<div class="orderStatus d-flex justify-content-between">
		    <div class="stepper__step">
		          <div class="stepper__step-icon">
		             <svg xmlns="http://www.w3.org/2000/svg" width="70" height="70" fill="currentColor" class="bi bi-coin" viewBox="0 0 16 16">
		                <path d="M5.5 9.511c.076.954.83 1.697 2.182 1.785V12h.6v-.709c1.4-.098 2.218-.846 2.218-1.932 0-.987-.626-1.496-1.745-1.76l-.473-.112V5.57c.6.068.982.396 1.074.85h1.052c-.076-.919-.864-1.638-2.126-1.716V4h-.6v.719c-1.195.117-2.01.836-2.01 1.853 0 .9.606 1.472 1.613 1.707l.397.098v2.034c-.615-.093-1.022-.43-1.114-.9H5.5zm2.177-2.166c-.59-.137-.91-.416-.91-.836 0-.47.345-.822.915-.925v1.76h-.005zm.692 1.193c.717.166 1.048.435 1.048.91 0 .542-.412.914-1.135.982V8.518l.087.02z"></path>
		                <path d="M8 15A7 7 0 1 1 8 1a7 7 0 0 1 0 14zm0 1A8 8 0 1 0 8 0a8 8 0 0 0 0 16z"></path>
		                <path d="M8 13.5a5.5 5.5 0 1 1 0-11 5.5 5.5 0 0 1 0 11zm0 .5A6 6 0 1 0 8 2a6 6 0 0 0 0 12z"></path>
		             </svg>
		          </div>
		          <div class="stepper__step-text">Đã xác nhận đơn hàng</div>
		          <div class="stepper__step-date">15:33 06-06-2021</div>
		    </div>                       
		    <div class="stepper__step ">
		          <div class="stepper__step-icon">
		             <svg  width="70" height="70" aria-hidden="true" focusable="false" data-prefix="fas" data-icon="truck" role="img" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 640 512" class="svg-inline--fa fa-truck fa-w-20 fa-5x"><path fill="currentColor" d="M624 352h-16V243.9c0-12.7-5.1-24.9-14.1-33.9L494 110.1c-9-9-21.2-14.1-33.9-14.1H416V48c0-26.5-21.5-48-48-48H48C21.5 0 0 21.5 0 48v320c0 26.5 21.5 48 48 48h16c0 53 43 96 96 96s96-43 96-96h128c0 53 43 96 96 96s96-43 96-96h48c8.8 0 16-7.2 16-16v-32c0-8.8-7.2-16-16-16zM160 464c-26.5 0-48-21.5-48-48s21.5-48 48-48 48 21.5 48 48-21.5 48-48 48zm320 0c-26.5 0-48-21.5-48-48s21.5-48 48-48 48 21.5 48 48-21.5 48-48 48zm80-208H416V144h44.1l99.9 99.9V256z" class=""></path>
		             </svg>
		          </div>
		          <div class="stepper__step-text">Đang vận chuyển</div>
		          <div class="stepper__step-date">15:33 06-06-2021</div>
		    </div>  
		    <div class="stepper__step ">
		          <div class="stepper__step-icon ">
		             <svg width="70" height="70" aria-hidden="true" focusable="false" data-prefix="fas" data-icon="box" role="img" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 512 512" class="svg-inline--fa fa-box fa-w-16 fa-5x"><path fill="currentColor" d="M509.5 184.6L458.9 32.8C452.4 13.2 434.1 0 413.4 0H272v192h238.7c-.4-2.5-.4-5-1.2-7.4zM240 0H98.6c-20.7 0-39 13.2-45.5 32.8L2.5 184.6c-.8 2.4-.8 4.9-1.2 7.4H240V0zM0 224v240c0 26.5 21.5 48 48 48h416c26.5 0 48-21.5 48-48V224H0z" class=""></path>
		             </svg>
		          </div>
		          <div class="stepper__step-text">Giao hàng thành công</div>
		          <div class="stepper__step-date">15:33 06-06-2021</div>
		    </div>
		    <div class="stepper__line">
		       <div class="stepper__line-background" >
		       </div>
		       <div class="stepper__line-foreground" >
		       </div>
		    </div>                      
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
		                  <td>${orderDetail.orderProductName} (<a href="${urlOrder}/don-hang-${orderDetail.orderId}/danh-gia-san-pham-${orderDetail.productId}">Viết nhận xét</a>)</td>
		                  <td>${orderDetail.orderProductQuantity}</td>
		                  <td>${stringUtil.beautifulPrice(orderDetail.orderProductPrice)}đ</td>
		                  <td>${stringUtil.beautifulPrice(orderDetail.orderProductQuantity * orderDetail.orderProductPrice)}đ</td>
		               </tr>
	               </c:forEach>
	               <tr>
	               		<td style="text-align: right" colspan="3" align="right">Phí ship</td>
	               		<td align="right">${stringUtil.beautifulPrice(constantUtil.getShip())}đ</td>
	               </tr>
	               <tr style="font-size: 18px; font-weight: bold">
	               		<td style="text-align: right" colspan="3" align="right">Thành tiền</td>
	               		<td style="color: red" align="right">${stringUtil.beautifulPrice(order.orderTotalPrice)}đ</td>
	               </tr>
	            </tbody>
	         </table>
	         <div style="margin-top: 20px">
               	<a title="Đơn hàng của tôi" class="btn btn-primary" href="${urlOrder}">Lịch sử đơn hàng</a>
               	<c:if test="${order.orderStatus != 4 and order.orderStatus != 5}">
               		<a title="Huỷ đơn hàng" onclick="return confirm('Xác nhận huỷ đơn hàng?')" class="btn btn-danger" href="${urlOrder}/huy-don-hang-${order.orderId}">Huỷ đơn hàng</a>
           	  	</c:if>
           	  </div>
	      </form>
	   </div>
	</div>