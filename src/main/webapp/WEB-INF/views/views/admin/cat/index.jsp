<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/components/taglib.jsp" %>
  			<div class="content-box-large">
  				<div class="row">
	  				<div class="panel-heading">
	  					<div class="panel-title ">Quản lý danh mục sản phẩm</div>
		  			</div>
				</div>
				<hr>	
				<div class="row">
					<div class="col-md-8">
						<a href="${urlAdminCat}/add" class="btn btn-success"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span>&nbsp;Thêm</a>
					</div>
                	<div class="col-md-4">
	                	<form action="${urlAdminCat}/tim-kiem" method="get">
		                 	 <div class="input-group form">
		                       <input type="text" class="form-control" id="catName" name="catName" value="${catName}" placeholder="Nhập tên danh mục sản phẩm">
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
	  					<c:when test="${not empty listCat}">
		  					<table class="table table-striped table-bordered" id="example">
								<thead>
									<tr>
										<th width="5%">ID</th>
										<th width="40%">Tên danh mục</th>
										<th>Danh mục cha</th>
										<th width="15%">Chức năng</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${listCat}" var="category">
										<tr class="odd gradeA">
											<td>${category.catId}</td>
											<td>${category.catName}</td>
											<td id="cat-parent-${category.catId}"></td>
											<td class="center text-center">
												<a href="${urlAdminCat}/update/${category.catSlug}-${category.catId}" title="" class="btn btn-primary"><span class="glyphicon glyphicon-pencil "></span> Sửa</a>
			                                    <a href="${urlAdminCat}/delete/${category.catId}" title="" class="btn btn-danger" onclick="return confirm('Bạn có chắc muốn xoá danh mục sản phẩm \'${category.catName}\' không?')"><span class="glyphicon glyphicon-trash"></span> Xóa</a>
											</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>

							<nav class="text-center" aria-label="...">
							   <ul class="pagination">
							   	  <c:set value="${urlAdminCat}" var="url"></c:set>
							   	  <c:if test="${not empty catName}">
							   	      <c:set value="${urlAdminCat}/tim-kiem/catName=${catName}" var="url"></c:set>
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
	fetch('http://localhost:8081/shopttcn/api/cat').then(response => response.json()).then(data => {
		for(let item of data){
			if (item.catParentId != 0) {
				fetch('http://localhost:8081/shopttcn/api/cat/parent/'+item.catParentId).then(response => response.json()).then(data => {
					$("#cat-parent-"+item.catId).html(data.catName);
				});
			} else {
				$("#cat-parent-"+item.catId).html("Không có");
			}
		}
	});
	
	function validateSearch() {
		var catName = $("#catName").val();
		if (catName == '') {
			alert('Nhập nội dung tìm kiếm trước khi tìm');
			return false;
		}
	}
</script>
  			
<script type="text/javascript">
	document.getElementById("admin_category").className = "current";
</script>