<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/components/taglib.jsp" %>
<div class="secion" id="breadcrumb-wp">
   <div class="secion-detail">
      <ul class="list-item clearfix">
         <li>
            <a href="${urlIndex}" title="">Trang chủ</a>
         </li>
         <c:if test="${not empty multiLevelCat}">
         	<c:forEach items="${multiLevelCat}" var="cat">
          	    <li>
              		<a title="" class="cat-name">${cat.catName}</a>
           		</li>
          	</c:forEach>
         </c:if>
         <li>
            <a title="">${product.productName}</a>
         </li>
      </ul>
   </div>
</div>
<div class="main-content fl-right">
   <div class="section" id="detail-product-wp">
      <div class="section-detail clearfix">
         <div class="thumb-wp fl-left">
            <div class="easyzoom easyzoom--overlay easyzoom--with-thumbnails">
               <a id="show" href="">
               		<img id="zoom" src="" alt="" width="350" height="350"/>
               </a>
            </div>
            <c:if test="${not empty listPicture}">
	            <ul id="list-thumb-img" class="thumbnails my-4 d-inline-block">
	               <c:forEach items="${listPicture}" var="picture">
		               <li>
		                  <a href="${pictureContextPath}/product/${product.productImage}" data-standard="${pictureContextPath}/product/${product.productImage}">
		                  	<img width="50px"  id="zoom" src="${pictureContextPath}/product/${product.productImage}" />
		                  </a>
		               </li>
	               </c:forEach>
	            </ul>
            </c:if>
         </div>
         <div class="thumb-respon-wp fl-left">
            <img src="" alt="">
         </div>
         <div class="info fl-right">
            <h3 class="product-name">${product.productName}</h3>
            <div style="margin-bottom: 10px; font-style: italic">
            	<p>Lượt xem: ${product.productView}</p>
            </div>
            <div class="desc">
               <p>${product.productDesc}</p>
            </div>
            <form action="">
               <input type="hidden" name="_token" value="sat1xuZ9gmsWSk9JIQAWbiWRxTfmR0XK4057vmV9">                        
               <div class="num-product">
                  <span class="title">Sản phẩm: </span>
                  <c:choose>
                  	<c:when test="${product.productQuantity > 0}">
                  		<span class="status">Còn ${product.productQuantity} sản phẩm</span>
                  	</c:when>
                  	<c:otherwise>
                  		<span class="status">Hết hàng</span>
                  	</c:otherwise>
                  </c:choose>
               </div>
               <p class="price">${stringUtil.beautifulPrice(product.productPrice)}đ</p>
               <input type="hidden" name="_token" value="sat1xuZ9gmsWSk9JIQAWbiWRxTfmR0XK4057vmV9">                            
               <div id="num-order-wp">
                  <span class="title">Số lượng: </span>
                  <a title="" id="minus"><i class="fa fa-minus"></i></a>
                  <input type="text" name="num-order" readonly="readonly" value="1" id="num-order">
                  <a title="" id="plus" ><i class="fa fa-plus"></i></a>
               </div>
               <button type="button" name="btn-add" title="Thêm vào giỏ hàng" class="add-cart mt-3" onclick="addToCart(${product.productId})">Thêm vào giỏ hàng</button>
            </form>
         </div>
      </div>
   </div>
   <div class="section" id="post-product-wp">
      <div class="section-head">
         <h3 class="section-title">Chi tiết sản phẩm</h3>
      </div>
      <div class="section-detail">
      	  <p>${product.productDetail}</p>
      </div>
      <p class="dtcvmodetail" style="display: block;"><span>Đọc thêm <i>▾</i></span></p>
      <p class="dtchide" style="display: none;"><span>Rút gọn <i>▴</i></span></p>
   </div>
   <div class="section" id="same-category-wp">
      <div class="section-head">
         <h3 class="section-title">Sản phẩm khác</h3>
      </div>
      <div class="section-detail">
      	 <c:if test="${not empty productRelate}">
	         <ul class="list-item">
	         	<c:forEach items="${productRelate}" var="product">
		            <li>
		               <a href="${urlDetail}/${product.cat.catSlug}/${product.productSlug}-${product.productId}" title="" class="thumb">
		               		<img style="width: 191px; height: 191px" src="${pictureContextPath}/product/${product.productImage}">
		               </a>
		               <a href="${urlDetail}/${product.cat.catSlug}/${product.productSlug}-${product.productId}" title="" class="product-name">${product.productName}</a>
		               <div class="price">
		                  <span class="new">${stringUtil.beautifulPrice(product.productPrice)}đ</span>
		               </div>
		            </li>
	            </c:forEach>
	         </ul>
         </c:if>
      </div>
   </div>
   <div class="section" style="margin-top: 30px">
   		<div class="section-head" style="margin-bottom: 15px">
            <h3 class="section-title" style="text-transform: uppercase">Đánh giá sản phẩm từ khách hàng</h3>
        </div>
        <div class="section-detail" style="margin-left: 5px">
	   		<div class="row" style="border-bottom: 1px solid #DDD; padding-bottom: 15px; margin-bottom: 20px">
				<div class="col-sm-3">
					<div class="rating-block">
						<h2 style="margin-bottom: 5px"><span style="font-size: 25px">${ratingAverage}</span> <small>/ 5</small></h2>
						<c:set value="${reviewsUtil.ratingProduct(ratingAverage)}" var="ratingProduct"></c:set>
						<c:forEach begin="1" end="${ratingProduct}">
							<button type="button" class="btn btn-warning btn-sm" aria-label="Left Align">
							  <i class="fa fa-star"></i>
							</button>
						</c:forEach>
						<c:if test="${ratingProduct != 5}">
							<c:forEach begin="${ratingProduct + 1}" end="5">
								<button type="button" class="btn btn-default btn-grey btn-sm" aria-label="Left Align">
								   <i class="fa fa-star"></i>
								</button>
							</c:forEach>
						</c:if>
						<p style="margin-top: 5px">${totalRowRating} lượt đánh giá</p>
					</div>
				</div>
				<div class="col-sm-6">
					<h4>Xếp hạng đánh giá</h4>
					<c:set value="6" var="star"></c:set>
					<c:forEach items="${listRatingCount}" var="ratingCount">
						<div class="pull-left">
							<div class="pull-left" style="width:35px; line-height:1;">
								<div style="height:9px; margin:5px 0;">${star = star - 1} <i style="color: #FF9900" class="fa fa-star"></i></div>
							</div>
							<div class="pull-left" style="width:180px">
								<div class="progress" style="height:9px; margin:8px 0">
								  <c:if test="${star == 5}">
								  	  <c:set value="success" var="css"></c:set>
								  </c:if>
								  <c:if test="${star == 4}">
								  	  <c:set value="primary" var="css"></c:set>
								  </c:if>
								  <c:if test="${star == 3}">
								  	  <c:set value="info" var="css"></c:set>
								  </c:if>
								  <c:if test="${star == 2}">
								  	  <c:set value="warning" var="css"></c:set>
								  </c:if>
								  <c:if test="${star == 1}">
								  	  <c:set value="danger" var="css"></c:set>
								  </c:if>
								  <div class="progress-bar progress-bar-${css}" role="progressbar" aria-valuenow="5" aria-valuemin="0" aria-valuemax="5" style="width: ${reviewsUtil.ratingPercent(totalRowRating,ratingCount)}%">
									<!-- <span class="sr-only">80% Complete (danger)</span> -->
								  </div>
								</div>
							</div>
							<div class="pull-right" style="margin-left:10px">${ratingCount}</div>
						</div>
					</c:forEach>
				</div>			
			</div>		
			
			<div id="reviews-list">
				<c:choose>
					<c:when test="${not empty listReviews}">
						<c:forEach items="${listReviews}" var="reviews">
							<div class="row">
								<div class="col-sm-3">
									<c:set value="${contextPath}/images/user.png" var="avt"></c:set>
									<c:if test="${not empty reviews.user.avatar}">
										<c:set value="${pictureContextPath}/avatar/${reviews.user.avatar}" var="avt"></c:set>
									</c:if>
									<img style="width: 145px; height: 145px; border-radius: 50%" src="${avt}" class="img-rounded">
									<div style="margin-top: 10px" class="review-block-name">${reviews.user.userFullname}</div>
								</div>
								<div class="col-sm-9">
									<div class="review-block-rate" style="margin-bottom: 15px">
										<c:forEach begin="1" end="${reviews.reviewsRating}">
											<button type="button" class="btn btn-warning btn-xs" aria-label="Left Align">
											  <i class="fa fa-star"></i>
											</button>
										</c:forEach>
										<c:if test="${reviews.reviewsRating != 5}">
											<c:forEach begin="${reviews.reviewsRating + 1}" end="5">
												<button type="button" class="btn btn-default btn-grey btn-xs" aria-label="Left Align">
												  <i class="fa fa-star"></i>
												</button>
											</c:forEach>
										</c:if>
									</div>
									<div class="review-block-title">${reviews.reviewsContent}</div>
								</div>
							</div>
							<hr/>
						</c:forEach>
						<span style="display: none" id="page-reviews">1</span>
						<div class="row">
							<div class="col-sm-3" style="text-align: left">
								<a onclick="olderReviews(${product.productId})" href="javascript:void(0)">Cũ hơn</a>
							</div>			
							<div class="col-sm-3" style="text-align: right; margin-left: 450px">
								<a onclick="newerReviews(${product.productId})" href="javascript:void(0)">Mới hơn</a>
							</div>			
						</div>
					</c:when>
					<c:otherwise>
						<div class="section-detail">
	                       	<div class="alert alert-success" role="alert">
								Chưa có đánh giá
							</div>
						</div>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
   </div>
</div>

<script type="text/javascript">
	function addToCart(productId) {
		var quantity = $("#num-order").val();
		$.ajax({
			url: '${urlCartAjax}/add',
			type: 'POST',
			cache: false,
			dataType: 'json',
			data: {
				productId: productId,
				quantity: quantity
			},
			success: function(data){
				if (data.error == 1) {
					alert(data.errorContent);
					return;
				}
				$("#num").html(data.totalQuantity);
				alert('Thêm sản phẩm vào giỏ hàng thành công!');
			},
			error: function (){
				alert('Có lỗi xảy ra!');
			}
		});
		return false;
	};
	
	function olderReviews(productId) {
		var currentPage = $("#page-reviews").html();
		$.ajax({
			url: '${urlReviews}/cu-hon',
			type: 'POST',
			cache: false,
			dataType: 'json',
			data: {
				productId: productId,
				currentPage: currentPage
			},
			success: function(data){
				if (data.code == 1) {
					alert(data.content);
					return;
				}
				currentPage = parseInt(currentPage) + 1;
				$("#reviews-list").html(viewReviews(data,productId,currentPage));
			},
			error: function (){
				alert('Có lỗi xảy ra!');
			}
		});
		return false;
	};
	
	function newerReviews(productId) {
		var currentPage = $("#page-reviews").html();
		$.ajax({
			url: '${urlReviews}/moi-hon',
			type: 'POST',
			cache: false,
			dataType: 'json',
			data: {
				productId: productId,
				currentPage: currentPage
			},
			success: function(data){
				if (data.code == 1) {
					alert(data.content);
					return;
				}
				currentPage = parseInt(currentPage) - 1;
				$("#reviews-list").html(viewReviews(data,productId,currentPage));
			},
			error: function (){
				alert('Có lỗi xảy ra!');
			}
		});
		return false;
	};
	
	// hiển thị đánh giá ajax
	function viewReviews(data,productId,page) {
		var rs = '';
		for (var i = 0; i < data.length; i++) {
			var rating = data[i].reviewsRating;
			var createAt = data[i].createAt;
			var avt = '${contextPath}/images/user.png';
			if (data[i].user.avatar != '') {
				avt = '${pictureContextPath}/avatar/'+data[i].user.avatar;
			}
			rs += '<div class="row"><div class="col-sm-3">';
			rs += '<img style="width: 145px; height: 145px; border-radius: 50%" src="'+avt+'" class="img-rounded">';
			rs += '<div style="margin-top: 10px" class="review-block-name">'+data[i].user.userFullname+'</div>';
			rs += '</div><div class="col-sm-9"><div class="review-block-rate" style="margin-bottom: 15px">';
			for (var j = 1; j <= rating; j++) {
				rs += '<button type="button" class="btn btn-warning btn-xs" aria-label="Left Align"><i class="fa fa-star"></i></button>';
			}
			if (rating != 5) {
				for (var j = (rating + 1); j <= 5; j++) {
					rs += '<button type="button" class="btn btn-default btn-grey btn-xs" aria-label="Left Align"><i class="fa fa-star"></i></button>';
				}
			}
			rs += '</div><div class="review-block-title">'+data[i].reviewsContent+'</div></div></div><hr/>';
		}
		rs += '<span style="display: none" id="page-reviews">'+page+'</span>';
		rs += '<div class="row"><div class="col-sm-3" style="text-align: left">';
		rs += '<a onclick="olderReviews('+productId+')" href="javascript:void(0)">Cũ hơn</a></div>';
		rs += '<div class="col-sm-3" style="text-align: right; margin-left: 450px">';
		rs += '<a onclick="newerReviews('+productId+')" href="javascript:void(0)">Mới hơn</a></div></div>';
		return rs;
	}
</script>