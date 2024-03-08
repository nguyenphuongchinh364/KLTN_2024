<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/components/taglib.jsp" %>
  			<div class="content-box-large">
  				<div class="row">
	  				<div class="panel-heading">
	  					<div class="panel-title ">Quản lý sản phẩm</div>
		  			</div>
				</div>
				<hr>	
				<div class="row">
					<div class="col-md-8" style="width: 500px; float: left">
						<a href="${urlAdminProduct}/add" class="btn btn-success"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span>&nbsp;Thêm</a>
					</div>
                	<div class="col-md-4" style="width: 610px; float: right">
	                	<form action="${urlAdminProduct}/tim-kiem" method="get">
	                		<div class="input-group form">
		                       <input type="text" class="form-control" id="productName" name="productName" value="${productName}" placeholder="Nhập tên sản phẩm" style="width: 58%; float: right">
		                       <select name="catId" class="form-control" id="multilevel-cat" style="width: 41%; float: left; border-radius: 4px">
		                       		<option value="0">-- Danh mục sản phẩm --</option>
		                       </select>
		                       <div class="clear"></div>
		                       <span id="cat-parent-id" style="display: none">${catId}</span>
		                       <span class="input-group-btn">
		                         <button class="btn btn-primary" type="submit" onclick="return validateSearch()">Tìm kiếm</button>
		                       </span>
		                  	</div>
	                  	</form>
                  	</div>
                  	<div class="clear"></div>
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
	  						<c:when test="${not empty listProduct}">
			  					<table class="table table-striped table-bordered" id="example">
									<thead>
										<tr>
											<th width="4%">ID</th>
											<th>Tên</th>
											<th>Danh mục</th>
											<th width="10%">Giá bán</th>
											<th width="7%">Số lượng</th>
											<th width="7%">Lượt xem</th>
											<th width="7%">Hình ảnh</th>
											<th width="14%">Chức năng</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${listProduct}" var="product" >
											<tr class="odd gradeA">
												<td>${product.productId}</td>
												<td>${product.productName}</td>
												<td>${product.cat.catName}</td>
												<td align="right">${product.productPrice}đ</td>
												<td align="center">${product.productQuantity}</td>
												<td align="center">${product.productView}</td>
												<td class="center text-center">
													<a href="${urlAdminProduct}/picture/${product.productId}" title="" class="btn btn-primary"> Xem</a>
												</td>
												<td class="center text-center">
													<a href="${urlAdminProduct}/update/${product.productSlug}-${product.productId}" title="" class="btn btn-primary"><span class="glyphicon glyphicon-pencil"></span> Sửa</a>
				                                    <a href="${urlAdminProduct}/delete/${product.productId}" title="" class="btn btn-danger" onclick="return confirm('Bạn có chắc muốn xoá sản phẩm \'${product.productName}\' không?')"><span class="glyphicon glyphicon-trash"></span> Xóa</a>
												</td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
								
								<nav class="text-center" aria-label="...">
								   <ul class="pagination">
								   	  <c:set value="${urlAdminProduct}" var="url"></c:set>
								   	  <c:if test="${not empty productName or not empty catId}">
								   	      <c:set value="${urlAdminProduct}/tim-kiem/productName=${productName}&productCat=${catId}" var="url"></c:set>
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
	showCat();
	
	async function showCat() {
		let data = await fetch('http://localhost:8080/shopttcn/api/cat/list/parent/0').then(response => response.json());
		var catParentId = $("#cat-parent-id").html();
		if (data.length > 0) {
			var selected = '';
			for(let item of data){
				if (catParentId == item.catId) {
					selected = 'selected';
				} else {
					selected = '';
				}
				$("#multilevel-cat").append('<option value="'+item.catId+'" '+selected+'>'+item.catName+'</option>');
				await multilevelCat(item.catId,'|---',catParentId);
			}
		}
	}
	
	async function multilevelCat(parentId,menu,catParentId) {
		try {
			let data = await fetch('http://localhost:8080/shopttcn/api/cat/list/parent/'+parentId).then(response => response.json());
			if (data.length > 0) {
				var selected = '';
				for(let item of data){
					if (catParentId == item.catId) {
						selected = 'selected';
					} else {
						selected = '';
					}
					$("#multilevel-cat").append('<option value="'+item.catId+'" '+selected+'>'+menu+' '+item.catName+'</option>');
					await multilevelCat(item.catId, menu + '|---',catParentId);
				}
			}
		} catch(error) {
			console.log(error);
		}
	}
	
	function validateSearch() {
		var productName = $("#productName").val();
		var catId = $("#multilevel-cat").val();
		if (productName == '' && catId == 0) {
			alert('Nhập nội dung tìm kiếm trước khi tìm');
			return false;
		}
	}
</script>
  			
<script type="text/javascript">
	document.getElementById("admin_product").className = "current";
</script>