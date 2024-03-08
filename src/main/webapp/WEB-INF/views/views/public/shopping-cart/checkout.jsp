<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/components/taglib.jsp" %>
<form method="POST" action="${urlCheckout}">
	<c:set value="${constantUtil.getShip()}" var="ship"></c:set>
	<c:if test="${not empty error}">
		<div class="section" style="margin: 10px 0px">
			<span style="font-style: italic; color: red">${error}</span>
		</div>
	</c:if>
	<div class="section" id="customer-info-wp">
	   <div class="section-head">
	      <h2 class="section-title">Thông tin khách hàng</h2>
	   </div>
	   <div class="section-detail">
	         <input type="hidden" name="_token" value="LWOp0AaDsEhWRO1nwWHWixipQg4U2xNgK7JYuoGJ">
	         <div class="form-row clearfix">
	            <div class="form-col fl-left" style="width: 595px">
	               <label for="orderName">Họ tên</label>
	               <input type="text" name="orderName" id="orderName" placeholder="Nhập họ tên" value="${objOrder.orderName}">
	               <form:errors path="orderError.orderName" cssStyle="color:red;font-style:italic" ></form:errors>
	            </div>
	         </div>
	         <div class="form-row clearfix">
	            <div class="form-col fl-left">
	               <label for="orderPhone">Số điện thoại</label>
	               <input type="text" name="orderPhone" id="orderPhone" placeholder="Nhập số điện thoại" value="${objOrder.orderPhone}">
	               <form:errors path="orderError.orderPhone" cssStyle="color:red;font-style:italic" ></form:errors>
	            </div>
	            <div class="form-col fl-right">
	               <label for="orderEmail">Email</label>
	               <input type="text" name="orderEmail" id="orderEmail" placeholder="Nhập email" value="${objOrder.orderEmail}">
	               <form:errors path="orderError.orderEmail" cssStyle="color:red;font-style:italic" ></form:errors>
	            </div>
	         </div>
	         <div class="form-row clearfix" id="address">
	            <label for="address">Địa chỉ</label>
	            <ul class="clearfix mb-4">
	               <li class="form-col fl-left">
	                  <select name="province.provinceId" id="province" onchange="getDistrictByProvinceId()">
	                     <option value="0">-- Chọn Tỉnh/Thành phố --</option>
	                     <c:if test="${not empty listProvince}">
	                     	<c:forEach items="${listProvince}" var="province">
	                     		<option value="${province.provinceId}" <c:if test='${address.province.provinceId == province.provinceId}'>selected</c:if>>${province.provinceName}</option>
	                     	</c:forEach>
	                     </c:if>
	                  </select>
	                  <form:errors path="addressError.province" cssStyle="color:red;font-style:italic" ></form:errors>
	               </li>
	               <li class="form-col fl-right">
	                  <select name="district.districtId" id="district" onchange="getWardByDistrictId()">
	                     <option value="0">-- Chọn Quận/Huyện --</option>
	                     <c:if test="${not empty listDistrict}">
							 <c:forEach items="${listDistrict}" var="district" >
								 <option value="${district.districtId}" <c:if test='${address.district.districtId == district.districtId}'>selected</c:if> >${district.districtName}</option>
							 </c:forEach>
						 </c:if>
	                  </select>
	                  <form:errors path="addressError.district" cssStyle="color:red;font-style:italic" ></form:errors>
	               </li>
	            </ul>
	            <ul class="clearfix">
	               <li class="form-col fl-left">
	                  <select name="ward.wardId" id="ward">
	                     <option value="0">-- Chọn Xã/Phường --</option>
	                     <c:if test="${not empty listWard}">
							 <c:forEach items="${listWard}" var="ward" >
								 <option value="${ward.wardId}" <c:if test='${address.ward.wardId == ward.wardId}'>selected</c:if> >${ward.wardName}</option>
							 </c:forEach>
						 </c:if>
	                  </select>
	                  <form:errors path="addressError.ward" cssStyle="color:red;font-style:italic" ></form:errors>
	               </li>
	               <li class="form-col fl-right">
	                  <input type="text" name="addressDetail" id="addressDetail" placeholder="Số nhà, tên đường..." value="${address.addressDetail}">
	                  <form:errors path="addressError.addressDetail" cssStyle="color:red;font-style:italic" ></form:errors>
	               </li>
	            </ul>
	         </div>
	         <div class="form-row">
	            <div class="form-col">
	               <label for="orderNote">Ghi chú đơn hàng:<span class="text-secondary"> (không bắt buộc) </span> </label>
	               <textarea name="orderNote" placeholder="Ghi chú đơn hàng...">${objOrder.orderNote}</textarea>
	            </div>
	         </div>
	   </div>
	</div>
	<div class="section" id="order-review-wp">
	   <div class="section-head">
	      <h2 class="section-title">Thông tin đơn hàng</h2>
	   </div>
	   <div class="section-detail">
	   	  <c:if test="${not empty cart}">
		      <table class="shop-table">
		         <thead>
		            <tr>
		               <td>Sản phẩm</td>
		               <td>Tổng</td>
		            </tr>
		         </thead>
		         <tbody>
		         	<c:forEach items="${cart.listCartDetail}" var="cartDetail">
			            <tr class="cart-item">
			               <td class="product-name">${cartDetail.product.productName}
			                  <strong class="product-quantity"> X${cartDetail.quantity}</strong>
			               </td>
			               <td class="product-total">${stringUtil.beautifulPrice(cartDetail.product.productPrice)}đ</td>
			            </tr>
		            </c:forEach>
		         </tbody>
		         <tfoot>
		         	<tr class="order-total">
		               <td>Phí Ship:</td>
		               <td>${stringUtil.beautifulPrice(ship)}đ</td>
		            </tr>
		            <tr class="order-total">
		               <td>Tổng đơn hàng:</td>
		               <td style="font-size: 18px"><strong class="total-price text-danger">${stringUtil.beautifulPrice(cart.totalPrice + ship)}đ</strong></td>
		            </tr>
		         </tfoot>
		      </table>
	      </c:if>
	      <div id="payment-checkout-wp">
	      	 <div class="section-head">
		        <h2 class="section-title">Thanh toán</h2>
		     </div>
	         <ul id="payment_methods">
	         	<li>
	               <input onclick="paymentOffline()" type="radio" id="payment-home" name="orderPayment" value="0" checked="checked">
	               <label for="payment-home">Thanh toán tại nhà</label>
	            </li>
	            <li>
	               <input onclick="return paymentOnline()" type="radio" id="payment-online" name="orderPayment" value="1">
	               <label for="payment-online">Thanh toán online</label>
	            </li>
	         </ul>
	         <!-- PAYPAL -->
	         <div>
	         	<c:set value="${cart.totalPrice + ship}" var="thanhTien"></c:set>
	         	<span style="display: none" id="total-price">${checkoutUtil.usd(thanhTien)}</span>
	         	<div class="payment-default" id="paypal-button-container"></div>
	         </div>
	      </div>
	      <div class="place-order-wp clearfix">
	         <input type="submit" name="order-add" id="order-now" value="Đặt hàng">
	      </div>
	   </div>
	</div>
</form>

<script type="text/javascript">
	function paymentOnline() {
		var orderName = $("#orderName").val();
		var orderEmail = $("#orderEmail").val();
		var orderPhone = $("#orderPhone").val();
		var province = $("#province").val();
		var district = $("#district").val();
		var ward = $("#ward").val();
		var addressDetail = $("#addressDetail").val();
		if (orderName == '' || orderEmail == '' || orderPhone == '' || addressDetail == ''
				|| province == '0' || district == '0' || ward == '0') {
			alert('Nhập thông tin khách hàng trước khi thanh toán');
			return false;
		}
		$("#paypal-button-container").removeClass("payment-default");
		document.getElementById("order-now").disabled = true;
	}
	
	function paymentOffline() {
		$("#paypal-button-container").addClass("payment-default");
		document.getElementById("order-now").disabled = false;
	}
</script>

<script type="text/javascript">
	var x = $("#total-price").html();
	var buttonSubmit = document.getElementById("order-now");
	paypal.Button.render({
		
		env: 'sandbox',
		
		client: {
			sandbox: 'AfdZOvZXqJ0aGfN6py9bjV_LZlKBWjIIxzIfQ3fuEgwwaRq-Dp7ALhGZw_b3Pj_O_sgMdLvkWhmK7apm',
			production: '<insert production client id>'
		},
		
		commit: true,
		
		payment: function(data, actions) {
			return actions.payment.create({
				payment: {
					transactions: [
						{
							amount: {total: 100, currency: 'USD'}
						}
					]
				}
			});
		},
		
		onAuthorize: function(data, actions) {
			return actions.payment.execute().then(function() {
				window.alert('Thanh toán thành công!');
				buttonSubmit.disabled = false;
				buttonSubmit.click();
			});
		}
    }, '#paypal-button-container');
</script>