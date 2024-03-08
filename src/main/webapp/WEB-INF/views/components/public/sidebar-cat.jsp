<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="sidebar fl-left">
   <div class="section" id="category-product-wp">
      <div class="section-head">
         <h3 class="section-title">Danh mục sản phẩm</h3>
      </div>
      <div class="secion-detail">
         <ul class="list-item" id="multilevel-cat"></ul>
      </div>
   </div>
   <div class="section" id="filter-product-wp">
      <div class="section-head">
         <h3 class="section-title">Bộ lọc</h3>
      </div>
      <div class="section-detail">
         <form action="" id="filter">
            <table class="filter-price">
               <thead>
                  <tr>
                     <td colspan="2">Đơn giá</td>
                  </tr>
               </thead>
               <tbody>
                  <tr>
                     <td><input onclick="filter()" type="radio" name="r-price" id="0" value="0-0" class="price-filter"></td>
                     <td><label for="0" title="">Tất cả</label></td>
                  </tr>
                  <tr>
                     <td><input onclick="filter()" type="radio" name="r-price" id="1" value="0-1" class="price-filter"></td>
                     <td><label for="0" title="Dưới 1 triệu">Dưới 1 triệu</label></td>
                  </tr>
                  <tr>
                     <td><input onclick="filter()" type="radio" name="r-price" id="2" value="1-5" class="price-filter"></td>
                     <td><label for="1" title="Từ 1 - 5 triệu">Từ 1 - 5 triệu</label></td>
                  </tr>
                  <tr>
                     <td><input onclick="filter()" type="radio" name="r-price" id="3" value="5-10" class="price-filter"></td>
                     <td><label for="2" title="Từ 5 - 10 triệu">Từ 5 - 10 triệu</label></td>
                  </tr>
                  <tr>
                     <td><input onclick="filter()" type="radio" name="r-price" id="4" value="10-15" class="price-filter"></td>
                     <td><label for="3" title="Từ 10 - 15 triệu">Từ 10 - 15 triệu</label></td>
                  </tr>
                  <tr>
                     <td><input onclick="filter()" type="radio" name="r-price" id="5" value="15-0" class="price-filter"></td>
                     <td><label for="4" title="Trên 15 triệu">Trên 15 triệu</label></td>
                  </tr>
               </tbody>
            </table>
         </form>
      </div>
   </div>
   <div class="section" id="selling-wp">
      <div class="section-head">
         <h3 class="section-title">Sản phẩm bán chạy</h3>
      </div>
      <c:if test="${not empty listBestSell}">
	      <div class="section-detail">
	         <ul class="list-item">
	         	<c:forEach items="${listBestSell}" var="product">
		            <li class="clearfix">
		               <a href="${urlDetail}/${product.cat.catSlug}/${product.productSlug}-${product.productId}" title="" class="thumb fl-left">
		               	  <img width="72px" src="${pictureContextPath}/product/${product.productImage}" alt="">
		               </a>
		               <div class="info fl-right">
		                  <a href="${urlDetail}/${product.cat.catSlug}/${product.productSlug}-${product.productId}" title="" class="product-name">${product.productName}</a>
		                  <div class="price">
		                     <span class="new">${stringUtil.beautifulPrice(product.productPrice)}đ</span>
		                  </div>
		                  <a href="${urlDetail}/${product.cat.catSlug}/${product.productSlug}-${product.productId}" title="" class="buy-now">Mua ngay</a>
		               </div>
		            </li>
	            </c:forEach>
	         </ul>
	      </div>
      </c:if>
   </div>
</div>

<script type="text/javascript">
	function filter() {
		var priceRange = $(".price-filter[type='radio']:checked").val();
		var keyword = $("#keyword").html();
		var catId = getCatIdFromURL();
		var minPrice = 0;
		var maxPrice = 0;
		if (priceRange != null) {
			var priceMinAndMax = priceRange.split('-');
			minPrice = priceMinAndMax[0] * 1000000;
			maxPrice = priceMinAndMax[1] * 1000000;
		}
		if (keyword == null) {
			keyword = '';
		}
		$.ajax({
			url: '${urlFilter}',
			type: 'POST',
			cache: false,
			dataType: 'json',
			data: {
				keyword: keyword,
				catId: catId,
				minPrice: minPrice,
				maxPrice: maxPrice
			},
			success: function(data){
				var rs = '';
				if (data.length > 0) {
					for (var i = 0; i < data.length; i++) {
						rs += '<li>';
						rs += '<a href="${urlDetail}/'+data[i].cat.catSlug+'/'+data[i].productSlug+'/'+data[i].productId+'" title="" class="thumb">';
						rs += '<img src="${pictureContextPath}/product/'+data[i].productImage+'"></a>';
						rs += '<a href="${urlDetail}/'+data[i].cat.catSlug+'/'+data[i].productSlug+'/'+data[i].productId+'" title="" class="product-name text-left">'+data[i].productName+'</a>';
						rs += '<div class="price text-left">';
						rs += '<span class="new">'+data[i].productPrice+'đ</span>';
						rs += '</div>';
						rs += '</li>';
					}
				} else {
					rs = '<div class="section-detail"><div style="width: 862px" class="alert alert-success" role="alert">Không có sản phẩm</div></div>';
				}
				$("#filter-result").html(rs);
				$("#paging-wp").html('');
			},
			error: function (error){
				console.log(error);
				alert('Có lỗi xảy ra!');
			}
		});
		return false;
	};

	function getCatIdFromURL() {
		var keyword = $("#keyword").html();
		if (keyword == null) {
			var url = window.location.href;
			var arrURL = url.split("/")[5];
			var arr = arrURL.split('-');
			return arr[arr.length-1];
		}
		return 0;
	}
</script>