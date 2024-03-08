<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/components/taglib.jsp" %>
<c:choose>
	<c:when test="${not empty cart}">
		<div class="section" id="info-cart-wp">
		   <div class="section-detail table-responsive">
		      <form action="">
		         <table class="table">
		            <thead>
		               <tr>
		                  <td>Mã sản phẩm</td>
		                  <td>Hình ảnh</td>
		                  <td>Tên sản phẩm</td>
		                  <td>Giá sản phẩm</td>
		                  <td>Số lượng</td>
		                  <td colspan="2">Thành tiền</td>
		               </tr>
		            </thead>
		            <tbody>
		               <c:forEach items="${cart.listCartDetail}" var="cartDetail">
			               <tr id="tr-${cartDetail.product.productId}">
			                  <td>${cartDetail.product.productId}</td>
			                  <td>
			                     <a title="" class="thumb">
			                     	<img src="${pictureContextPath}/product/${cartDetail.product.productImage}" alt="">
			                     </a>
			                  </td>
			                  <td>
			                     <a href="${urlDetail}/${cartDetail.product.cat.catSlug}/${cartDetail.product.productSlug}-${cartDetail.product.productId}" title="" class="name-product">${cartDetail.product.productName}</a>
			                  </td>
			                  <td>${stringUtil.beautifulPrice(cartDetail.product.productPrice)}đ</td>
			                  <td>
			                     <div class="d-inline-block">
			                        <span onclick="decCart(${cartDetail.product.productId})" class="btn-num"><i>-</i></span>
			                        <input id="quantity-detail-${cartDetail.product.productId}" type="text" data-id="d7775198f438176fbbc007e3aec5b1bc" readonly="readonly" name="quantity" value="${cartDetail.quantity}" class="num-order id_d7775198f438176fbbc007e3aec5b1bc">
			                        <span onclick="incCart(${cartDetail.product.productId})" class="btn-num"><i>+</i></span>
			                     </div>
			                  </td>
			                  <td id="thanh-tien-${cartDetail.product.productId}" class="sub-total">${stringUtil.beautifulPrice(cartDetail.quantity * cartDetail.product.productPrice)}đ</td>
			                  <td>
			                     <a onclick="delCart(${cartDetail.product.productId})" href="javascript:void(0)" title="Xoá sản phẩm" class="del-product"><i class="fa fa-trash-o"></i></a>
			                  </td>
			               </tr>
		               </c:forEach>
		            </tbody>
		            <tfoot>
		               <tr>
		                  <td colspan="7">
		                     <div class="clearfix">
		                        <p id="total-price" class="fl-right">Tổng giá: <span id="total-price-cart" class="total">${stringUtil.beautifulPrice(cart.totalPrice)}đ</span></p>
		                     </div>
		                  </td>
		               </tr>
		               <tr>
		                  <td colspan="7">
		                     <div class="clearfix">
		                        <div class="fl-right">
		                           <a href="${urlCheckout}" title="" id="checkout-cart">Thanh toán</a>
		                        </div>
		                     </div>
		                  </td>
		               </tr>
		            </tfoot>
		         </table>
		      </form>
		   </div>
		</div>
		<div class="section" id="action-cart-wp">
		   <div class="section-detail">
		      <p class="title">Đăng nhập tài khoản trước khi mua hàng để được hưởng nhiều quyền lợi hơn. Nhấn vào thanh toán để hoàn tất mua hàng.</p>
		      <a href="${urlIndex}" title="Mua tiếp" id="buy-more">Mua tiếp</a><br>
		      <a href="${urlCart}/xoa-tat-ca" title="Xoá giỏ hàng" id="delete-cart">Xóa giỏ hàng</a>
		   </div>
		</div>
	</c:when>
	<c:otherwise>
		<div class="section text-center" id="action-cart-wp">
		   <div class="section-detail">
		      <p class=""><img class="img-fluid d-inline-block" src="${contextPath}/images/Shopping-Cart-Icon.png" alt="" style="width:75px;height:71px;"></p>
		      <p class="" style="font-size: 16px;color: #4a4a4a;margin-bottom: 25px;">Không có sản phẩm nào trong giỏ hàng của bạn</p>
		      <p class=""><a href="${urlIndex}" class="re_unimart" title="Trang chủ">ĐẾN TRANG CHỦ IT SHOP</a></p>
		   </div>
		</div>
	</c:otherwise>
</c:choose>

<script type="text/javascript">
	function incCart(productId) {
		$.ajax({
			url: '${urlCartAjax}/increase',
			type: 'POST',
			cache: false,
			dataType: 'json',
			data: {
				productId: productId
			},
			success: function(data){
				if (data.error == 1) {
					alert(data.errorContent);
					return;
				}
				$("#num").html(data.totalQuantity);
				$("#quantity-detail-"+productId).val(data.quantity);
				$("#thanh-tien-"+productId).html(data.price+'đ');
				$("#total-price-cart").html(data.totalPrice+'đ');
			},
			error: function (){
				alert('Có lỗi xảy ra!');
			}
		});
		return false;
	};
	
	function decCart(productId) {
		$.ajax({
			url: '${urlCartAjax}/decrease',
			type: 'POST',
			cache: false,
			dataType: 'json',
			data: {
				productId: productId
			},
			success: function(data){
				if (data.error == 1) {
					alert(data.errorContent);
					return;
				}
				$("#num").html(data.totalQuantity);
				$("#quantity-detail-"+productId).val(data.quantity);
				$("#thanh-tien-"+productId).html(data.price+'đ');
				$("#total-price-cart").html(data.totalPrice+'đ');
			},
			error: function (){
				alert('Có lỗi xảy ra!');
			}
		});
		return false;
	};
	
	function delCart(productId) {
		$.ajax({
			url: '${urlCartAjax}/delete',
			type: 'POST',
			cache: false,
			dataType: 'json',
			data: {
				productId: productId
			},
			success: function(data){
				if (data.error == 1) {
					location.reload();
					return;
				}
				$("#num").html(data.totalQuantity);
				$("#total-price-cart").html(data.totalPrice+'đ');
				$("#tr-"+productId).html("");
			},
			error: function (){
				alert('Có lỗi xảy ra!');
			}
		});
		return false;
	}
</script>