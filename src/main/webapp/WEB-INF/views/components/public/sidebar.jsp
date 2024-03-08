<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:url value="/resources/public/images" var="img" ></c:url>
<div class="sidebar fl-left">
   <div class="section" id="category-product-wp">
      <div class="section-head">
         <h3 class="section-title">Danh mục sản phẩm</h3>
      </div>
      <div class="secion-detail">
         <ul class="list-item" id="multilevel-cat"></ul>
      </div>
   </div>
   <br />
   <div class="section" id="selling-wp">
      <div class="section-head">
         <h3 class="section-title">Sản phẩm bán chạy</h3>
      </div>
       <!-- Kiểm tra một tập hợp có phần tử không -->
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