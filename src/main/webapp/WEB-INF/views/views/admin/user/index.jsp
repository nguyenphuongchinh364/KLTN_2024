<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/components/taglib.jsp" %>
  			<div class="content-box-large">
  				<div class="row">
	  				<div class="panel-heading">
	  					<div class="panel-title ">Quản lý người dùng</div>
		  			</div>
				</div>
				<hr>	
				<div class="row">
					<div class="col-md-6">
						<a href="${urlAdminUser}/add" class="btn btn-success"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span>&nbsp;Thêm</a>
					</div>
                	<div class="col-md-6">
	                	<form action="${urlAdminUser}/tim-kiem" method="get">
		                 	 <div class="input-group form">
		                       <input type="text" class="form-control" id="username" name="username" value="${username}" placeholder="Nhập username" style="width: 43%; float: right">
		                       <select class="form-control" name="roleId" id="roleId" style="width: 26%; float: left; margin-right: 7px; border-radius: 4px">
		                 	   		<option value="0">-- Vai trò --</option>
		                 	   		<c:if test="${not empty listRole}">
		                 	   			<c:forEach items="${listRole}" var="role">
		                 	   				<option value="${role.roleId}" <c:if test='${role.roleId == roleId}'>selected="selected"</c:if>>${role.roleDesc}</option>
		                 	   			</c:forEach>
		                 	   		</c:if>
		                 	   </select>
		                 	   <select class="form-control" name="enabled" id="enabled" style="width: 28%; float: left; border-radius: 4px">
		                 	   		<option value="2">-- Trạng thái --</option>
		                 	   		<option value="1" <c:if test='${enabled == 1}'>selected="selected"</c:if>>Đã kích hoạt</option>
		                 	   		<option value="0" <c:if test='${enabled == 0}'>selected="selected"</c:if>>Vô hiệu hoá</option>
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
	  					<c:when test="${not empty listUser}">
		  					<table class="table table-striped table-bordered" id="example">
								<thead>
									<tr>
										<th width="4%">ID</th>
										<th width="9%">Username</th>
										<th width="12%">Họ tên</th>
										<th>Email</th>
										<th width="8%">Số điện thoại</th>
										<th width="18%">Địa chỉ</th>
										<th width="8%">Avatar</th>
										<th width="9%">Trạng thái</th>
										<th width="7%">Vai trò</th>
										<th width="12%">Chức năng</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${listUser}" var="user">
										<tr class="odd gradeA">
											<td>${user.userId}</td>
											<td>${user.username}</td>
											<td>${user.userFullname}</td>
											<td>${user.userEmail}</td>
											<td>${stringUtil.beautifulPhone(user.userPhone)}</td>
											<td>
												${user.userAddress.addressDetail} - 
												${user.userAddress.ward.wardName} - 
												${user.userAddress.district.districtName} - 
												${user.userAddress.province.provinceName}
											</td>
											<td align="center">
												<c:choose>
													<c:when test="${not empty user.avatar}">
														<img src="${adminPictureContextPath}/avatar/${user.avatar}" width="60px" style="border-radius: 50%" />
													</c:when>
													<c:otherwise>
														<img src="${adminContextPath}/images/user.png" width="60px" style="border-radius: 50%" />
													</c:otherwise>
												</c:choose>
											</td>
											<td align="center" id="status-user-${user.userId}">
												<c:if test="${user.enabled == 1}">Đã kích hoạt</c:if>
												<c:if test="${user.enabled == 0}">Vô hiệu hoá</c:if>
											</td>
											<td align="center">${user.role.roleDesc}</td>
											<td class="center text-center" id="update-status-user-${user.userId}">
												<c:if test="${user.role.roleId != 2}"> <!-- Nếu k phải là admin, admin có quyền khoá tài khoản -->
													<c:if test="${user.enabled == 1}">
														<a href="javascript:void(0)" onclick="confirmUpdateStatus(${user.userId},0)" class="btn btn-primary">Khoá tài khoản</a>
													</c:if>
													<c:if test="${user.enabled == 0}">
														<a href="javascript:void(0)" onclick="confirmUpdateStatus(${user.userId},1)" class="btn btn-danger">Mở khoá</a>
													</c:if>
												</c:if>
											</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>

							<nav class="text-center" aria-label="...">
							   <ul class="pagination">
							   	  <c:set value="${urlAdminUser}" var="url"></c:set>
							   	  <c:if test="${not empty username or not empty enabled or not empty roleId}">
							   	      <c:set value="${urlAdminUser}/tim-kiem/username=${username}&roleId=${roleId}&enabled=${enabled}" var="url"></c:set>
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
	// confirm khoá/mở khoá account
	function confirmUpdateStatus(userId, status) {
		var conf = confirm("Xác nhận khoá/mở khoá tài khoản?");
		if (conf == true) {
			updateStatus(userId, status);
		}
	}

	// ajax khoá/mở khoá tài khoản
	function updateStatus(userId, status) {
		$.ajax({
			url: '${urlAdminUser}/update-status',
			type: 'POST',
			cache: false,
			dataType: 'json',
			data: {
				userId: userId,
				status: status
			},
			success: function(data){
				if (data.code == 0) {
					alert('Thành công: ' + data.content);
				} else {
					alert('Lỗi: ' + data.content);
				}
				if (status == 0) {
					// enabled = 1 (đang sử dụng được) => sau update => đã khoá (enabled = 0)
					$("#update-status-user-"+userId).html('<a href="javascript:void(0)" onclick="confirmUpdateStatus('+userId+',1)" class="btn btn-danger">Mở khoá</a>');
					$("#status-user-"+userId).html('Vô hiệu hoá');
				} else {
					// enabled = 0 (đã khoá) => sau update => đã mở khoá (enabled = 1)
					$("#update-status-user-"+userId).html('<a href="javascript:void(0)" onclick="confirmUpdateStatus('+userId+',0)" class="btn btn-primary">Khoá tài khoản</a>');
					$("#status-user-"+userId).html('Đã kích hoạt');
				}
			},
			error: function (){
				alert('Có lỗi xảy ra!');
			}
		});
		return false;
	};
	
	function validateSearch() {
		var username = $("#username").val();
		var enabled = $("#enabled").val();
		var roleId = $("#roleId").val();
		if (username == '' && roleId == 0 && (enabled != 0 && enabled != 1)) {
			alert('Nhập nội dung tìm kiếm trước khi tìm');
			return false;
		}
	}
</script>
 
<script type="text/javascript">
	document.getElementById("admin_user").className = "current";
</script>