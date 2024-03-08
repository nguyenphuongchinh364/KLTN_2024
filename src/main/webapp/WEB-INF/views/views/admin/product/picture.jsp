<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/components/taglib.jsp" %>
<div class="row">
	<div class="col-md-12 panel-info">
		<div class="content-box-header panel-heading">
			<div class="panel-title ">Quản lý hình ảnh sản phẩm</div>
		</div>
		<div class="content-box-large box-with-header">
			<form action="" method="post" enctype="multipart/form-data" style="margin-bottom: 30px">
				<div>
					<div class="row mb-10"></div>
					<div class="row">
						<div class="col-sm-6">
							<h3 style="font-size: 20px; color: red; margin-top: 0px">Thêm mới hình ảnh</h3>
							
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
							
							<div class="form-group">
								<label for="productName">Tên sản phẩm</label>
								<input type="text" name="productName" value="${objProduct.productName}" class="form-control" readonly="readonly">
							</div>
							
							<div class="form-group">
								<label>Hình ảnh</label>
								<form:errors path="pictureError.pictureName" cssStyle="color:red;font-style:italic" ></form:errors>
								<input type="file" name="picture" class="btn btn-default" multiple="multiple" >
								<p class="help-block">
									<em>Định dạng: jpg, png, jpeg</em> <br />
									<em>Số lượng có thể thêm mới: ${20 - 1 - listPicture.size()}</em>
								</p>
							</div>
							
						</div>
					</div>
					<hr>
					<div class="row">
						<div class="col-sm-12">
							<input type="submit" value="Lưu" class="btn btn-success" />
						</div>
					</div>
				</div>
			</form>
			<hr >
			<c:if test="${not empty listPicture or not empty objProduct}">
				<h3 style="font-size: 20px"><span style="color: red">Hình ảnh sản phẩm</span> (${listPicture.size() + 1} hình)</h3>
				<form action="${urlAdminProduct}/picture/delete" method="post">
					<input style="display: none" name="productId" value="${objProduct.productId}" id="productId" />
					<table class="table table-striped table-bordered" id="example">
						<thead>
							<tr>
								<th width="5%">STT</th>
								<th>Hình ảnh</th>
								<th width="15%">Hình ảnh chính</th> <!-- Hiển thị chính (public) -->
								<th width="10%">Chức năng</th>
								<th width="7%">Xoá</th>
							</tr>
						</thead>
						<tbody>
							<tr class="odd gradeA">
								<td>1</td>
								<td>
									<img width="200px" src="${adminPictureContextPath}/product/${objProduct.productImage}" />
								</td>
								<td align="center">
									<button class="btn btn-success">Hình ảnh chính</button>
								</td>
								<td align="center"></td>
								<td align="center"></td>
							</tr>
							<c:set value="1" var="stt" ></c:set>
							<c:forEach items="${listPicture}" var="picture" >
								<tr class="odd gradeA">
									<td>${stt = stt + 1}</td>
									<td>
										<img width="200px" src="${adminPictureContextPath}/product/${picture.pictureName}" />
									</td>
									<td align="center">
										<a href="javascript:void(0)" onclick="setMainImage(${picture.pictureId})" class="btn btn-primary">Đặt làm</a>
									</td>
									<td class="center text-center">
	                                    <a href="${urlAdminProduct}/picture-${picture.productId}/delete/${picture.pictureId}" title="" class="btn btn-danger" onclick="return confirm('Bạn có chắc muốn xoá hình ảnh thứ ${stt} không?')"><span class="glyphicon glyphicon-trash"></span> Xóa</a>
									</td>
									<td align="center"><input type="checkbox" name="pictureDelete" value="${picture.pictureId}" /></td>
								</tr>
							</c:forEach>
							<tr class="odd gradeA">
								<td colspan="4"></td>
								<td align="center"><input type="submit" value="Xoá" class="btn btn-success" onclick="return confirm('Xác nhận xoá hình ảnh đã chọn?')" /></td>
							</tr>
						</tbody>
					</table>
				</form>
				<button onclick="history.back()" class="btn btn-primary">Quay lại</button>
			</c:if>
		</div>
	</div>
</div>

<script type="text/javascript">
	function setMainImage(pictureId) {
		$.ajax({
			url: '${urlAdminProduct}/picture/set-main-image',
			type: 'POST',
			cache: false,
			dataType: 'json',
			data: {
				pictureId: pictureId
			},
			success: function(data){
				if (data.code == 0) {
					alert('Thành công: ' + data.content);
				} else {
					alert('Lỗi: ' + data.content);
				}
				location.reload();
			},
			error: function (){
				alert('Có lỗi xảy ra!');
			}
		});
		return false;
	}
</script>

<script type="text/javascript">
	document.getElementById("admin_product").className = "current";
</script>