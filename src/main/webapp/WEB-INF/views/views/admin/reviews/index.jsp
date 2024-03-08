<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/components/taglib.jsp" %>
  			<div class="content-box-large">
  				<div class="row">
	  				<div class="panel-heading">
	  					<div class="panel-title ">Quản lý đánh giá sản phẩm từ phía khách hàng</div>
		  			</div>
				</div>
				<hr>	
				<div class="row">
					<div class="col-md-6"></div>
                	<div class="col-md-6">
                		<form action="${urlAdminReviews}" method="get">
		                 	 <div class="input-group form">
		                       <input type="text" class="form-control" id="productName" name="productName" value="${productName}" placeholder="Nhập tên sản phẩm" style="width: 43%; float: right">
		                       <select name="status" id="status" class="form-control" style="width: 28%; float: left; border-radius: 4px; margin-right: 5px">
		                       		<option value="-1">-- Trạng thái --</option>
		                       		<option <c:if test='${status == 1}'>selected="selected"</c:if> value="1">Bình thường</option>
		                       		<option <c:if test='${status == 0}'>selected="selected"</c:if> value="0">Đã xoá</option>
		                       </select>
		                       <select name="rating" id="rating" class="form-control" style="width: 27%; float: left; border-radius: 4px">
		                       		<option value="-1">-- Xếp hạng --</option>
		                       		<option <c:if test='${rating == 1}'>selected="selected"</c:if> value="1">1 sao</option>
		                       		<option <c:if test='${rating == 2}'>selected="selected"</c:if> value="2">2 sao</option>
		                       		<option <c:if test='${rating == 3}'>selected="selected"</c:if> value="3">3 sao</option>
		                       		<option <c:if test='${rating == 4}'>selected="selected"</c:if> value="4">4 sao</option>
		                       		<option <c:if test='${rating == 5}'>selected="selected"</c:if> value="5">5 sao</option>
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
	  					<c:when test="${not empty listReviews}">
		  					<table class="table table-striped table-bordered" id="example">
								<thead>
									<tr>
										<th width="4%">ID</th>
										<th width="18%">Khách hàng</th>
										<th width="25%">Sản phẩm</th>
										<th>Nội dung</th>
										<th width="7%">Xếp hạng</th>
										<th width="8%">Trạng thái</th>
										<th width="9%">Chức năng</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${listReviews}" var="objReviews">
										<tr class="odd gradeA">
											<td>${objReviews.reviewsId}</td>
											<td>${objReviews.user.userFullname}</td>
											<td>${objReviews.product.productName}</td>
											<td>${objReviews.reviewsContent}</td>
											<td align="center">${objReviews.reviewsRating} <i style="color: #FF9900" class="fa fa-star"></i></td>
											<c:if test="${objReviews.reviewsStatus == 1}">
												<td>Bình thường</td>
												<td class="center text-center">
													<a href="javascript:void(0)" class="btn btn-danger" onclick="updateStatus(${objReviews.reviewsId},0)"><span class="glyphicon glyphicon-trash"></span> Xoá</a>
												</td>
											</c:if>
											<c:if test="${objReviews.reviewsStatus == 0}">
												<td>Đã xoá</td>
												<td class="center text-center">
													<a href="javascript:void(0)" class="btn btn-primary" onclick="updateStatus(${objReviews.reviewsId},1)">Khôi phục</a>
												</td>
											</c:if>
										</tr>
									</c:forEach>
								</tbody>
							</table>
							
							<nav class="text-center" aria-label="...">
							   <ul class="pagination">
							   	  <c:set value="${currentPage - 1}" var="pagePrevious"></c:set>
							   	  <c:if test="${currentPage == 1}">
							   	  	<c:set value="${currentPage}" var="pagePrevious"></c:set>
							      </c:if>
								  <li <c:if test='${currentPage == 1}'>class="disabled"</c:if>>
								  	<a href="${urlAdminReviews}/trang-${pagePrevious}" aria-label="Previous" >
								  		<span aria-hidden="true">«</span>
								  	</a>
								  </li>
																		      
							      <c:choose>
								      <c:when test="${totalPage > 5}">
								      	  <c:if test="${currentPage > 3 and currentPage < (totalPage - 2)}">
								      	  	  <li><a href="${urlAdminReviews}">Đầu</a></li>
										      <c:forEach begin="${currentPage - 2}" end="${currentPage + 2}" var="page">
										      	  <li <c:if test='${page == currentPage}'> class="active" </c:if> >
										      	  	  <a href="${urlAdminReviews}/trang-${page}">${page}</a>
										      	  </li>
										      </c:forEach>
										      <li><a href="${urlAdminReviews}/trang-${totalPage}">Cuối</a></li>
									      </c:if>
								      	  <c:if test="${currentPage <= 3}">
										      <c:forEach begin="1" end="5" var="page">
										      	  <li <c:if test='${page == currentPage}'> class="active" </c:if> >
										      	  	  <a href="${urlAdminReviews}/trang-${page}">${page}</a>
										      	  </li>
										      </c:forEach>
										      <li><a href="${urlAdminReviews}/trang-${totalPage}">Cuối</a></li>
									      </c:if>
								      	  <c:if test="${currentPage >= (totalPage - 2)}">
								      	  	  <li><a href="${urlAdminReviews}">Đầu</a></li>
										      <c:forEach begin="${totalPage - 4}" end="${totalPage}" var="page">
										      	  <li <c:if test='${page == currentPage}'> class="active" </c:if> >
										      	  	  <a href="${urlAdminReviews}/trang-${page}">${page}</a>
										      	  </li>
										      </c:forEach>
									      </c:if>
								      </c:when>
								      <c:otherwise>
								      	  <c:forEach begin="1" end="${totalPage}" var="page">
									      	  <li <c:if test='${page == currentPage}'> class="active" </c:if> >
									      	  	  <a href="${urlAdminReviews}/trang-${page}">${page}</a>
									      	  </li>
									      </c:forEach>
								      </c:otherwise>
							      </c:choose>
							      
							      <c:set value="${currentPage + 1}" var="pageNext"></c:set>
							      <c:if test="${currentPage == totalPage}">
							      	<c:set value="${currentPage}" var="pageNext"></c:set>
							      </c:if>
								  <li <c:if test='${currentPage == totalPage}'>class="disabled"</c:if>>
								  	<a href="${urlAdminReviews}/trang-${pageNext}" aria-label="Next">
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
	function updateStatus(reviewsId, status) {
		var conf = false;
		if (status == 0) {
			conf = confirm("Xác nhận xoá đánh giá của khách hàng?");
		} else {
			conf = confirm("Xác nhận khôi phục đánh giá của khách hàng?");
		}
		if (conf == true) {
			$.ajax({
				url: '${urlAdminReviews}/update-status',
				type: 'POST',
				cache: false,
				dataType: 'json',
				data: {
					reviewsId: reviewsId,
					status: status
				},
				success: function(data){
					if (data.code == 0) {
						alert('Thành công: ' + data.content);
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
		var productName = $("#productName").val();
		var rating = $("#rating").val();
		var status = $("#status").val();
		if (productName == '' && rating == -1 && status == -1) {
			alert('Nhập nội dung tìm kiếm trước khi tìm');
			return false;
		}
	}
</script>
  			
<script type="text/javascript">
	document.getElementById("admin_reviews").className = "current";
</script>