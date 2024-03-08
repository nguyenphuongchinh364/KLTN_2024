<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/components/taglib.jsp"%>
<div class="row">
	<div class="col-md-12 panel-info">
		<div class="content-box-header panel-heading">
			<div class="panel-title ">Đổi mật khẩu</div>
		</div>
		<div class="content-box-large box-with-header">
			<form action="${urlAdminProfile}/doi-mat-khau" method="post">
				<div>
					<div class="row mb-10"></div>
					<div class="row">
						<div class="col-sm-6">
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
								<label for="username">Tên đăng nhập</label>
								<input type="text" name="username" class="form-control" value="${adminUserLogin.username}" readonly="readonly">
							</div>
							
							<div class="form-group">
								<label for="oldPassword">Mật khẩu cũ</label>
								<span style="color:red; font-style:italic">${oldPasswordError}</span>
								<input type="password" value="" name="oldPassword" class="form-control" placeholder="Nhập mật khẩu cũ" >
							</div>
							
							<div class="form-group">
								<label for="password">Mật khẩu mới</label>
								<form:errors path="userError.password" cssStyle="color: red; font-italic: red"></form:errors>
								<input type="password" value="" name="password" class="form-control" placeholder="Nhập mật khẩu mới" >
								<p class="help-block"><em>Định dạng: Mật khẩu từ 6 - 20 ký tự, phải chứa ít nhất 1 ký tự hoa, 1 ký tự thường, 1 chữ số và 1 ký tự đặc biệt</em></p>
							</div>
							
							<div class="form-group">
								<label for="confirmPassword">Xác nhận mật khẩu mới</label>
								<span style="color:red; font-style:italic">${confirmPasswordError}</span>
								<input type="password" value="" name="confirmPassword" class="form-control" placeholder="Xác nhận mật khẩu mới" >										
							</div>
						</div>
					</div>
					<hr>
					<div class="row">
						<div class="col-sm-12">
							<input type="button" value="Quay lại" class="btn btn-primary" onclick="history.back()" />
							<input type="submit" value="Lưu" class="btn btn-success" />
						</div>
					</div>
				</div>
			</form>
		</div>
	</div>
</div>

<script type="text/javascript">
	document.getElementById("admin_profile").className = "current";
</script>