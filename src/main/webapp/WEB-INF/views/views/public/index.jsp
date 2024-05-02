
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/components/taglib.jsp"%>
<div class="main-content fl-right">
	<div class="section" id="slider-wp">
		<div class="section-detail">
			<div class="item">
				<img src="${contextPath}/images/slider-01.png" alt="">
			</div>
			<div class="item">
				<img src="${contextPath}/images/slider-02.png" alt="">
			</div>
			<div class="item">
				<img src="${contextPath}/images/slider-03.png" alt="">
			</div>
		</div>
	</div>
	<div class="section" id="support-wp">
		<div class="section-detail">
			<ul class="list-item clearfix">
				<li>
					<div class="thumb">
						<img src="${contextPath}/images/icon-1.png">
					</div>
					<h3 class="title">Miễn phí vận chuyển</h3>
					<p class="desc">Tới tận tay khách hàng</p>
				</li>
				<li>
					<div class="thumb">
						<img src="${contextPath}/images/icon-2.png">
					</div>
					<h3 class="title">Tư vấn 24/7</h3>
					<p class="desc">0399316266</p>
				</li>
				<li>
					<div class="thumb">
						<img src="${contextPath}/images/icon-3.png">
					</div>
					<h3 class="title">Tiết kiệm hơn</h3>
					<p class="desc">Với nhiều ưu đãi cực lớn</p>
				</li>
				<li>
					<div class="thumb">
						<img src="${contextPath}/images/icon-4.png">
					</div>
					<h3 class="title">Thanh toán nhanh</h3>
					<p class="desc">Hỗ trợ nhiều hình thức</p>
				</li>
				<li>
					<div class="thumb">
						<img src="${contextPath}/images/icon-5.png">
					</div>
					<h3 class="title">Đặt hàng online</h3>
					<p class="desc">Thao tác đơn giản</p>
				</li>
			</ul>
		</div>
	</div>
	<div class="section" id="feature-product-wp"
		style="margin-bottom: 50px;">
		<div class="section-head">
			<h3 class="section-title">Sản phẩm nổi bật</h3>
		</div>
		<c:if test="${not empty listBestSell}">
			<div class="section-detail">
				<ul class="list-item clearfix">
					<c:forEach items="${listBestSell}" var="product">
						<li><a
							href="${urlDetail}/${product.cat.catSlug}/${product.productSlug}-${product.productId}"
							title="" class="thumb"> <img
								src="${pictureContextPath}/product/${product.productImage}">
						</a> <a
							href="${urlDetail}/${product.cat.catSlug}/${product.productSlug}-${product.productId}"
							title="" class="product-name">${product.productName}</a>
							<div class="price text-left">
								<span class="new">${stringUtil.beautifulPrice(product.productPrice)}đ</span>
							</div></li>
					</c:forEach>
				</ul>
			</div>
		</c:if>
	</div>

	<div class="section" id="feature-product-wp"
		style="margin-bottom: 50px;">
		<div class="section-head">
			<h3 class="section-title">Sản phẩm bạn có thể thích (Trang web
				gợi ý cho bạn)</h3>
		</div>
		<c:choose>
			<c:when test="${not empty userLogin}">
				<h2>Chào, ${userLogin.userFullname}</h2>
				<p>Sau đây là một số sản phẩm được gợi ý riêng cho bạn</p>
				<br />
				<!-- Danh sách sản phẩm được gợi ý cho từng user -->
				<c:if test="${not empty listProductRS}">
					<div class="section-detail">
						<ul class="list-item clearfix">
							<c:forEach items="${listProductRS}" var="product">
								<li><a
									href="${urlDetail}/${product.cat.catSlug}/${product.productSlug}-${product.productId}"
									title="" class="thumb"> <img
										src="${pictureContextPath}/product/${product.productImage}">
								</a> <a
									href="${urlDetail}/${product.cat.catSlug}/${product.productSlug}-${product.productId}"
									title="" class="product-name">${product.productName}</a>
									<div class="price text-left">
										<span class="new">${stringUtil.beautifulPrice(product.productPrice)}đ</span>
									</div></li>
							</c:forEach>
						</ul>
					</div>
				</c:if>

			</c:when>
			<c:otherwise>
				<li><a href="${urlLogin}" title="">Bạn cần Đăng nhập để có
						thể nhận được sự gợi ý từ Website, nếu chưa có tài khoản hãy Đăng
						ký</a></li>
			</c:otherwise>
		</c:choose>
	</div>

	<c:if test="${not empty listCatParent}">
		<c:set value="0" var="catQuantity"></c:set>
		<c:forEach items="${listCatParent}" var="catParent">
			<div class="section" id="list-product-wp"
				style="margin-bottom: 50px;">
				<div class="section-head clearfix">
					<h3 class="section-title float-left">${catParent.catName}</h3>
					<a href="${urlCat}/${catParent.catSlug}-${catParent.catId}"
						class="section-title float-right"
						style="font-size: 16px; text-transform: uppercase">Xem tất cả</a>
				</div>
				<c:if test="${not empty listProduct}">
					<div class="section-detail">
						<ul class="list-item clearfix">
							<c:forEach items="${listProduct}" begin="${catQuantity}"
								end="${catQuantity + 11}" var="product">
								<li><a
									href="${urlDetail}/${product.cat.catSlug}/${product.productSlug}-${product.productId}"
									title="" class="thumb"> <img
										src="${pictureContextPath}/product/${product.productImage}">
								</a> <a
									href="${urlDetail}/${product.cat.catSlug}/${product.productSlug}-${product.productId}"
									title="" class="product-name">${product.productName}</a>
									<div class="price text-left">
										<span class="new">${stringUtil.beautifulPrice(product.productPrice)}đ</span>
									</div></li>
							</c:forEach>
						</ul>
					</div>
				</c:if>
			</div>
			<c:set value="${catQuantity + 12}" var="catQuantity"></c:set>
		</c:forEach>
	</c:if>
</div>