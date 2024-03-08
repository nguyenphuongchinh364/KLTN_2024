<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/components/taglib.jsp"%>
<div class="main-content fl-right">
	<div class="section" id="detail-blog-wp">
		<div class="section-head clearfix">
			<h3 class="section-title">Đổi mật khẩu</h3>
		</div>
		<hr />
		<div class="section-detail">
			<c:if test="${not empty success}">
				<div class="alert alert-success" role="alert">${success}</div>
			</c:if>

			<c:if test="${not empty error}">
				<div class="alert alert-danger" role="alert">${error}</div>
			</c:if>
		
			<form action="${urlAccount}/doi-mat-khau" method="post">
				<div class="form-group">
					<label for="username">Tên đăng nhập</label>
					<input type="text" value="${userLogin.username}" name="username" class="form-control" readonly="readonly" >
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
				
				<div>
					<button type="submit" class="btn btn-success">Lưu</button>
					<button type="button" onclick="history.back()" class="btn btn-primary">Quay lại</button>
				</div>
			</form>
		</div>
	</div>
</div>