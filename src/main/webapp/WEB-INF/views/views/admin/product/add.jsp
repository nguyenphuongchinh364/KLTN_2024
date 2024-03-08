<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/components/taglib.jsp" %>
<div class="row">
	<div class="col-md-12 panel-info">
		<div class="content-box-header panel-heading">
			<div class="panel-title ">Thêm mới sản phẩm</div>
		</div>
		<div class="content-box-large box-with-header">
			<form action="${urlAdminProduct}/add" method="post" enctype="multipart/form-data">
				<div>
					<div class="row mb-10"></div>
					<div class="row">
						<div class="col-sm-6">
							<c:if test="${not empty error}">
								<div class="alert alert-danger" role="alert">
								    ${error}
								</div>
							</c:if>
						
							<div class="form-group">
								<label for="productName">Tên sản phẩm</label>
								<form:errors path="productError.productName" cssStyle="color:red;font-style:italic" ></form:errors>
								<input type="text" name="productName" value="${objProduct.productName}" class="form-control" placeholder="Nhập tên sản phẩm">
							</div>
							
							<div class="form-group">
								<label>Danh mục sản phẩm</label>
								<span id="cat-parent-id" style="display: none">${objProduct.cat.catId}</span> <!-- selected trường hợp validate -->
								<form:errors path="productError.cat" cssStyle="color:red;font-style:italic" ></form:errors>
								<select name="cat.catId" class="form-control" id="multilevel-cat">
								   <option value="0">-- Chọn danh mục --</option>
								</select>
							</div>
							
							<div class="form-group">
								<label for="productPrice">Giá bán</label>
								<form:errors path="productError.productPrice" cssStyle="color:red;font-style:italic" ></form:errors>
								<input type="text" name="productPrice" value="${objProduct.productPrice}" class="form-control" placeholder="Nhập giá sản phẩm (VNĐ)">
							</div>

							<div class="form-group">
								<label for="productQuantity">Số lượng sản phẩm</label>
								<form:errors path="productError.productQuantity" cssStyle="color:red;font-style:italic" ></form:errors>
								<input type="text" name="productQuantity" value="${objProduct.productQuantity}" class="form-control" placeholder="Nhập số lượng sản phẩm đang có">
							</div>
			
							<div class="form-group">
								<label>Hình ảnh chính</label>
								<form:errors path="productError.productImage" cssStyle="color:red;font-style:italic" ></form:errors>
								<input type="file" name="mainImage" class="btn btn-default">
								<p class="help-block"><em>Định dạng: jpg, png, jpeg</em></p>
							</div>

							<div class="form-group">
								<label>Hình ảnh khác</label>
								<form:errors path="pictureError.pictureName" cssStyle="color:red;font-style:italic" ></form:errors>
								<input type="file" name="picture" class="btn btn-default" multiple="multiple">
								<p class="help-block"><em>Định dạng: jpg, png, jpeg</em></p>
							</div>
							
							<div class="form-group">
							   <label>Mô tả</label>
							   <form:errors path="productError.productDesc" cssStyle="color:red;font-style:italic" ></form:errors>
							   <textarea name="productDesc" class="form-control" rows="5" placeholder="Nhập mô tả">${objProduct.productDesc}</textarea>
							</div>
						</div>
						<div class="col-sm-6"></div>
			
						<div class="col-sm-12">
							<div class="form-group">
							   <label>Chi tiết</label>
							   <form:errors path="productError.productDetail" cssStyle="color:red;font-style:italic" ></form:errors>
							   <textarea name="productDetail" id="productDetail" class="form-control" rows="30" placeholder="Nhập chi tiết">${objProduct.productDetail}</textarea>
							</div>
						</div>
					</div>
					<hr>
					<div class="row">
						<div class="col-sm-12">
							<input type="submit" value="Lưu" class="btn btn-success" />
							<input type="reset" value="Nhập lại" class="btn btn-default" />
						</div>
					</div>
				</div>
			</form>
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
</script>

<script type="text/javascript">
	document.getElementById("admin_product").className = "current";
	var ckeditor = CKEDITOR.replace('productDetail');
	CKFinder.setupCKEditor(ckeditor, '${pageContext.request.contextPath}/resources/admin/ckfinder/');
</script>