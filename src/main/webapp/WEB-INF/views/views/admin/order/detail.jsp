<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/components/taglib.jsp" %>
  			<div class="content-box-large">
  				<div class="row">
	  				<div class="panel-heading">
	  					<div class="panel-title ">Chi tiết đơn hàng</div>
		  			</div>
				</div>
				<hr>	
				<c:if test="${not empty objOrder}">
					<div class="row">
						<div class="col-md-6">
							<h3 style="font-size: 20px; color: red; margin-top: 0px">Thông tin khách hàng</h3>
							<p><span class="order-infor">Tên:</span> ${objOrder.orderName}</p>
							<p><span class="order-infor">Email:</span> ${objOrder.orderEmail}</p>
							<p><span class="order-infor">Số điện thoại:</span> ${stringUtil.beautifulPhone(objOrder.orderPhone)}</p>
							<p><span class="order-infor">Địa chỉ:</span> ${objOrder.orderAddress}</p>
						</div>
						<div class="col-md-6">
							<h3 style="font-size: 20px; color: red; margin-top: 0px">Thông tin đơn hàng</h3>
							<p><span class="order-infor">Mã đơn hàng:</span> #<span id="order-id">${objOrder.orderId}</span></p>
							<p><span class="order-infor">Số lượng:</span> ${objOrder.orderTotalQuantity}</p>
							<p><span class="order-infor">Tổng tiền:</span> ${objOrder.orderTotalPrice}đ</p>
							<p><span class="order-infor">Ghi chú:</span> ${objOrder.orderNote}<c:if test="${empty objOrder.orderNote}">Không có</c:if></p>
							<p>
								<span class="order-infor">Thanh toán:</span>
								<c:if test="${objOrder.orderPayment == 0}"><img src="${adminContextPath}/images/deactive.gif" /> Chưa thanh toán</c:if>
								<c:if test="${objOrder.orderPayment == 1}"><img src="${adminContextPath}/images/active.gif" /> Đã thanh toán</c:if>
							</p>
						</div>
					</div>
					<hr>
					<c:if test="${not empty confirm}">
						<div class="row">
							<div class="col-md-6">
								<h3 style="font-size: 20px; color: red; margin-top: 0px">Trạng thái đơn hàng</h3>
								<c:if test="${not empty success}">
									<div class="alert alert-success" role="alert">
									    ${success}
									</div>
								</c:if>
								<c:if test="${not empty error}">
									<div class="alert alert-danger" role="alert">
									    ${error}
									</div>
								</c:if>
								<form method="post" action="">
	                              	<input type="text" value="Trạng thái đơn hàng" class="btn btn-primary btn-sm" style="width: 140px; float:left" readonly="readonly"/>
	                              	<select name="orderStatus" class="btn-sm" style="float:left; margin: 0px 5px; height: 30px">
	                                	<option value="1" <c:if test='${objOrder.orderStatus == 1}'>selected</c:if>>Đã nhận đơn hàng</option>
	                                	<option value="2" <c:if test='${objOrder.orderStatus == 2}'>selected</c:if>>Đang xử lý</option>
	                                	<option value="3" <c:if test='${objOrder.orderStatus == 3}'>selected</c:if>>Đang giao hàng</option>
	                                	<option value="4" <c:if test='${objOrder.orderStatus == 4}'>selected</c:if>>Giao hàng thành công</option>
	                                </select>
	                                <input type="submit" name="submit" value="Cập nhật" class="btn btn-success btn-sm" style="float:left" <c:if test='${objOrder.orderStatus == 5 or objOrder.orderStatus == 4}'>disabled</c:if> />
	                                <div class="clear"></div>
	                            </form>
							</div>
						</div>
						<hr>
					</c:if>
					<div class="row">
		  				<div class="panel-body">
		  					<c:choose>
		  						<c:when test="${not empty listOrderDetail}">
		  							<h3 style="font-size: 20px; color: red; margin-top: -15px">Thông tin sản phẩm</h3>
				  					<table class="table table-striped table-bordered" id="example">
										<thead>
											<tr>
												<th width="4%">ID</th>
												<th>Tên sản phẩm</th>
												<th width="7%">Số lượng</th>
												<th width="10%">Giá</th>
												<th width="10%">Tổng tiền</th>
											</tr>
										</thead>
										<tbody>
											<c:set value="${constantUtil.getShip()}" var="ship"></c:set>
											<c:forEach items="${listOrderDetail}" var="orderDetail" >
												<c:set value="${orderDetail.orderProductQuantity * orderDetail.orderProductPrice}" var="tongTien"></c:set>
												<tr class="odd gradeA">
													<td>${orderDetail.orderDetailId}</td>
													<td>${orderDetail.orderProductName}</td>
													<td align="center">${orderDetail.orderProductQuantity}</td>
													<td align="right">
														${orderDetail.orderProductPrice}đ
													</td>
													<td align="right">
														${tongTien}đ
													</td>
												</tr>
											</c:forEach>
											<tr>
												<td colspan="4" align="right">Phí ship</td>
												<td align="right">${ship}đ</td>
											</tr>
											<tr style="font-size: 16px; font-weight: bold">
												<td colspan="4" align="right">Thành tiền</td>
												<td style="color: red" align="right">
													${objOrder.orderTotalPrice}đ
												</td>
											</tr>
										</tbody>
									</table>
									<button onclick="history.back()" class="btn btn-primary">Quay lại</button>
								</c:when>
							</c:choose>
		  				</div>
	  				</div>
  				</c:if>
  			</div>

<script type="text/javascript">
	document.getElementById("admin_order").className = "current";
</script>