<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/components/taglib.jsp" %>
  			<div class="content-box-large">
  				<div class="row">
	  				<div class="panel-heading">
	  					<div class="panel-title ">Quản lý đơn hàng</div>
		  			</div>
				</div>
				<hr>	
				<div class="row">
					<div class="col-md-2"></div>
                	<div class="col-md-10">
	                	<form action="${urlAdminOrder}" method="get">
		                 	 <div class="input-group form">
		                       <input type="text" class="form-control" id="orderName" name="orderName" value="${orderName}" placeholder="Nhập tên khách hàng" style="width: 34%; float: right">
		                       <input type="date" class="form-control" id="dateCreate" name="dateCreate" value="${dateCreate}" title="Nhập ngày mua" style="width: 18%; float: left; border-radius: 4px">
		                       <select name="modId" id="modId" class="form-control" style="width: 24%; float: left; border-radius: 4px; margin: 0px 6px">
		                       		<option value="-1">-- Nhân viên xử lý --</option>
		                       		<option <c:if test='${modId == 0}'>selected="selected"</c:if> value="0">Không có</option>
		                       		<c:if test="${not empty listMod}">
		                       			<c:forEach items="${listMod}" var="user">
		                       				<option <c:if test='${modId == user.userId}'>selected="selected"</c:if> value="${user.userId}">${user.userFullname}</option>
		                       			</c:forEach>
		                       		</c:if>
		                       </select>
		                       <select name="orderStatus" id="orderStatus" class="form-control" style="width: 22%; float: left; border-radius: 4px">
		                       		<option value="-1">-- Trạng thái đơn hàng --</option>
		                       		<option <c:if test='${orderStatus == 0}'>selected="selected"</c:if> value="0">Chưa xác nhận</option>
		                       		<option <c:if test='${orderStatus == 1}'>selected="selected"</c:if> value="1">Đã nhận đơn hàng</option>
		                       		<option <c:if test='${orderStatus == 2}'>selected="selected"</c:if> value="2">Đang xử lý</option>
		                       		<option <c:if test='${orderStatus == 3}'>selected="selected"</c:if> value="3">Đang giao hàng</option>
		                       		<option <c:if test='${orderStatus == 4}'>selected="selected"</c:if> value="4">Giao hàng thành công</option>
		                       		<option <c:if test='${orderStatus == 5}'>selected="selected"</c:if> value="5">Đã huỷ</option>
		                       </select>
		                       <div class="clear"></div>
		                       <span class="input-group-btn">
		                         <button class="btn btn-primary" type="submit" onclick="return validateSearch()">Tìm kiếm</button>
		                       </span>
		                  	 </div>
	                  	</form>
                  	</div>
				</div>

				<div class="row">
	  				<div class="panel-body">
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
	  					<c:choose>
	  						<c:when test="${not empty listOrder}">
			  					<table class="table table-striped table-bordered" id="example">
									<thead>
										<tr>
											<th width="4%">ID</th>
											<th>Tên khách hàng</th>
											<th width="7%">Số lượng</th>
											<th width="9%">Tổng tiền</th>
											<th width="9%">Ngày mua</th>
											<th width="13%">Thanh toán</th>
											<th width="12%">Trạng thái</th>
											<th width="13%">Nhân viên xử lý</th>
											<th width="7%">Chi tiết</th>
											<th width="8%">Chức năng</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${listOrder}" var="objOrder" >
											<tr class="odd gradeA">
												<td>${objOrder.orderId}</td>
												<td>${objOrder.orderName}</td>
												<td align="center">${objOrder.orderTotalQuantity}</td>
												<td align="right">${objOrder.orderTotalPrice}đ</td>
												<td align="center">${stringUtil.beautifulDay(objOrder.createAt)}</td>
												<td align="center">
													<c:if test="${objOrder.orderPayment == 1}">
														<div style="width: 20%; float: left">
															<a href="javascript:void(0)">
																<img src="${adminContextPath}/images/active.gif" />
															</a>
														</div>
														<div style="width: 79%; float: left">
															Đã thanh toán
														</div>
														<div class="clear"></div>
													</c:if>
													<c:if test="${objOrder.orderPayment == 0}">
														<div style="width: 20%; float: left">
															<a href="javascript:void(0)">
																<img id="payment-order${objOrder.orderId}" alt="" src="${adminContextPath}/images/deactive.gif" />
															</a>
														</div>
														<div id="payment-order-desc${objOrder.orderId}" style="width: 79%; float: left">
															Chưa thanh toán
														</div>
														<div class="clear"></div>
													</c:if>
												</td>
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
													${objOrder.user.userFullname}
													<c:if test="${empty objOrder.user.userFullname}">Không có</c:if>
												</td>
												<td class="text-center">
													<a href="${urlAdminOrder}/detail/${objOrder.orderId}" title="" class="btn btn-primary"><i class="fa fa-eye"></i> Xem</a>
												</td>
												<td class="text-center" id="confirm-order-${objOrder.orderId}">
													<c:if test="${objOrder.orderStatus == 0}">
														<a href="javascript:void(0)" onclick="confirmOrder(${objOrder.orderId})" class="btn btn-primary">Xác nhận</a>
													</c:if>
												</td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
			
								<nav class="text-center" aria-label="...">
								   <ul class="pagination">
								      <c:set value="${urlAdminOrder}" var="url"></c:set>
								   	  <c:if test="${not empty orderName or not empty dateCreate or not empty orderStatus or not empty modId}">
								   	      <c:set value="${urlAdminOrder}/tim-kiem/orderName=${orderName}&dateCreate=${dateCreate}&orderStatus=${orderStatus}&modId=${modId}" var="url"></c:set>
								   	  </c:if>
								   
								   	  <c:set value="${currentPage - 1}" var="pagePrevious"></c:set>
								   	  <c:if test="${currentPage == 1}">
								   	  	<c:set value="${currentPage}" var="pagePrevious"></c:set>
								      </c:if>
									  <li <c:if test='${currentPage == 1}'>class="disabled"</c:if>>
									  	<a href="${url}/trang-${pagePrevious}" aria-label="Previous" >
									  		<span aria-hidden="true">«</span>
									  	</a>
									  </li>
																			      
								      <c:choose>
									      <c:when test="${totalPage > 5}">
									      	  <c:if test="${currentPage > 3 and currentPage < (totalPage - 2)}">
									      	  	  <li><a href="${url}">Đầu</a></li>
											      <c:forEach begin="${currentPage - 2}" end="${currentPage + 2}" var="page">
											      	  <li <c:if test='${page == currentPage}'> class="active" </c:if> >
											      	  	  <a href="${url}/trang-${page}">${page}</a>
											      	  </li>
											      </c:forEach>
											      <li><a href="${url}/trang-${totalPage}">Cuối</a></li>
										      </c:if>
									      	  <c:if test="${currentPage <= 3}">
											      <c:forEach begin="1" end="5" var="page">
											      	  <li <c:if test='${page == currentPage}'> class="active" </c:if> >
											      	  	  <a href="${url}/trang-${page}">${page}</a>
											      	  </li>
											      </c:forEach>
											      <li><a href="${url}/trang-${totalPage}">Cuối</a></li>
										      </c:if>
									      	  <c:if test="${currentPage >= (totalPage - 2)}">
									      	  	  <li><a href="${url}">Đầu</a></li>
											      <c:forEach begin="${totalPage - 4}" end="${totalPage}" var="page">
											      	  <li <c:if test='${page == currentPage}'> class="active" </c:if> >
											      	  	  <a href="${url}/trang-${page}">${page}</a>
											      	  </li>
											      </c:forEach>
										      </c:if>
									      </c:when>
									      <c:otherwise>
									      	  <c:forEach begin="1" end="${totalPage}" var="page">
										      	  <li <c:if test='${page == currentPage}'> class="active" </c:if> >
										      	  	  <a href="${url}/trang-${page}">${page}</a>
										      	  </li>
										      </c:forEach>
									      </c:otherwise>
								      </c:choose>
								      
								      <c:set value="${currentPage + 1}" var="pageNext"></c:set>
								      <c:if test="${currentPage == totalPage}">
								      	<c:set value="${currentPage}" var="pageNext"></c:set>
								      </c:if>
									  <li <c:if test='${currentPage == totalPage}'>class="disabled"</c:if>>
									  	<a href="${url}/trang-${pageNext}" aria-label="Next">
									  		<span aria-hidden="true">»</span>
									  	</a>
									  </li>
								   </ul>
								</nav>
							</c:when>
							<c:otherwise>
								<div class="alert alert-info" role="alert">
								    Không có dữ liệu
								</div>
							</c:otherwise>
						</c:choose>
	  				</div>
  				</div>
  			</div>

<script type="text/javascript">
	function confirmOrder(orderId) {
		var conf = confirm("Xác nhận xử lý đơn hàng?");
		if (conf == true) {
			$.ajax({
				url: '${urlAdminOrder}/confirm',
				type: 'POST',
				cache: false,
				dataType: 'json',
				data: {
					orderId: orderId
				},
				success: function(data){
					if (data.code == 0) {
						alert('Thành công: ' + data.content);
						// $("#confirm-order-"+orderId).html("");
						location.reload();
					} else {
						alert('Lỗi: ' + data.content);
					}
				},
				error: function (){
					alert('Có lỗi xảy ra!');
				}
			});
			return false;
		}
	}
	
	function validateSearch() {
		var orderName = $("#orderName").val();
		var dateCreate = $("#dateCreate").val();
		var orderStatus = $("#orderStatus").val();
		var modId = $("#modId").val();
		if (orderName == '' && dateCreate == '' && orderStatus == -1 && modId == -1) {
			alert('Nhập nội dung tìm kiếm trước khi tìm');
			return false;
		}
	}
</script>
  			
<script type="text/javascript">
	document.getElementById("admin_order").className = "current";
</script>