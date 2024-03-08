<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/components/taglib.jsp"%>
<div class="row">
	<div class="col-md-12 panel-info">
		<div class="content-box-header panel-heading">
			<div class="panel-title ">Thêm mới danh mục</div>
		</div>
		<div class="content-box-large box-with-header">
			<form action="${urlAdminCat}/add" method="post">
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
								<label for="name">Tên danh mục</label>
								<form:errors path="catError.catName" cssStyle="color:red; font-style:italic" ></form:errors>
								<input type="text" name="catName" class="form-control" value="${objCat.catName}" placeholder="Nhập tên danh mục">
							</div>
							
							<div class="form-group">
								<label>Danh mục cha</label>
								<span id="cat-parent-id" style="display: none">${objCat.catParentId}</span> <!-- selected trường hợp validate -->
								<select name="catParentId" class="form-control" id="multilevel-cat">
									<option value="0">-- Không có --</option>
								</select>
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
		let data = await fetch('http://localhost:8081/shopttcn/api/cat/list/parent/0').then(response => response.json());
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
			let data = await fetch('http://localhost:8081/shopttcn/api/cat/list/parent/'+parentId).then(response => response.json());
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
	document.getElementById("admin_category").className = "current";
</script>