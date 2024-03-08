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
                        </ul>
                     </div>
                  </div>
                  <div class="main-content fl-right">
                     <div class="section" id="list-product-wp">
                        <div class="section-head clearfix">
                           <h3 class="section-title fl-left"> <strong class="cat-name">${objCat.catName}</strong></h3>
                           <span class="ml-1 text-secondary count" style="font-size: 18px">(${totalRow} sản phẩm)</span>
                        </div>
                        <c:choose>
	                        <c:when test="${not empty listProduct}">
		                        <div class="section-detail" id="list-product">
		                           <ul class="list-item clearfix" id="filter-result">
		                           	  <c:forEach items="${listProduct}" var="product">
			                              <li>
			                                 <a href="${urlDetail}/${product.cat.catSlug}/${product.productSlug}-${product.productId}" title="" class="thumb">  
			                                 	<img src="${pictureContextPath}/product/${product.productImage}">
			                                 </a>
			                                 <a href="${urlDetail}/${product.cat.catSlug}/${product.productSlug}-${product.productId}" title="" class="product-name text-left">${product.productName}</a>
			                                 <div class="price text-left">
			                                    <span class="new">${stringUtil.beautifulPrice(product.productPrice)}đ</span>
			                                 </div>
			                              </li>
		                              </c:forEach>
		                           </ul>
		                        </div>
	                        </c:when>
	                        <c:otherwise>
	                        	<div class="section-detail">
		                        	<div class="alert alert-success" role="alert">
										Không có sản phẩm
									</div>
								</div>
	                        </c:otherwise>
                        </c:choose>
                     </div>
                     <c:if test="${not empty listProduct}">
	                     <div class="section" id="paging-wp">
	                        <div class="section-detail">
							   <ul class="list-item clearfix">
							   	  <c:set value="${currentPage - 1}" var="pagePrevious"></c:set>
							   	  <c:if test="${currentPage == 1}">
							   	  	<c:set value="${currentPage}" var="pagePrevious"></c:set>
							      </c:if>
								  <li <c:if test='${currentPage == 1}'>class="disabled"</c:if>>
								  	<a href="${urlCat}/${objCat.catSlug}-${objCat.catId}/trang-${pagePrevious}">Trang trước</a>
								  </li>
																		      
							      <c:choose>
								      <c:when test="${totalPage > 5}">
								      	  <c:if test="${currentPage > 3 and currentPage < (totalPage - 2)}">
								      	  	  <li><a href="${urlCat}/${objCat.catSlug}-${objCat.catId}">Đầu</a></li>
										      <c:forEach begin="${currentPage - 2}" end="${currentPage + 2}" var="page">
										      	  <li <c:if test='${page == currentPage}'> class="active" </c:if> >
										      	  	  <a href="${urlCat}/${objCat.catSlug}-${objCat.catId}/trang-${page}">${page}</a>
										      	  </li>
										      </c:forEach>
										      <li><a href="${urlCat}/${objCat.catSlug}-${objCat.catId}/trang-${totalPage}">Cuối</a></li>
									      </c:if>
								      	  <c:if test="${currentPage <= 3}">
										      <c:forEach begin="1" end="5" var="page">
										      	  <li <c:if test='${page == currentPage}'> class="active" </c:if> >
										      	  	  <a href="${urlCat}/${objCat.catSlug}-${objCat.catId}/trang-${page}">${page}</a>
										      	  </li>
										      </c:forEach>
										      <li><a href="${urlCat}/${objCat.catSlug}-${objCat.catId}/trang-${totalPage}">Cuối</a></li>
									      </c:if>
								      	  <c:if test="${currentPage >= (totalPage - 2)}">
								      	  	  <li><a href="${urlCat}/${objCat.catSlug}-${objCat.catId}">Đầu</a></li>
										      <c:forEach begin="${totalPage - 4}" end="${totalPage}" var="page">
										      	  <li <c:if test='${page == currentPage}'> class="active" </c:if> >
										      	  	  <a href="${urlCat}/${objCat.catSlug}-${objCat.catId}/trang-${page}">${page}</a>
										      	  </li>
										      </c:forEach>
									      </c:if>
								      </c:when>
								      <c:otherwise>
								      	  <c:forEach begin="1" end="${totalPage}" var="page">
									      	  <li <c:if test='${page == currentPage}'> class="active" </c:if> >
									      	  	  <a href="${urlCat}/${objCat.catSlug}-${objCat.catId}/trang-${page}">${page}</a>
									      	  </li>
									      </c:forEach>
								      </c:otherwise>
							      </c:choose>
							      
							      <c:set value="${currentPage + 1}" var="pageNext"></c:set>
							      <c:if test="${currentPage == totalPage}">
							      	<c:set value="${currentPage}" var="pageNext"></c:set>
							      </c:if>
								  <li <c:if test='${currentPage == totalPage}'>class="disabled"</c:if>>
								  	<a href="${urlCat}/${objCat.catSlug}-${objCat.catId}/trang-${pageNext}">Trang tiếp</a>
								  </li>
							   </ul>
	                        </div>
	                     </div>
                     </c:if>
                  </div>
                  