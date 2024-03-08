<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/components/taglib.jsp"%>
<div class="main-content fl-right">
	<div class="section" id="detail-blog-wp">
		<div class="section-head clearfix">
			<h3 class="section-title">Đăng nhập vào hệ thống</h3>
		</div>
		<hr />
		<div class="section-detail">
			<c:if test="${not empty success}">
				<div class="alert alert-success" role="alert">${success}</div>
			</c:if>

			<c:if test="${not empty error}">
				<div class="alert alert-danger" role="alert">${error}</div>
			</c:if>
		
			<form action="${urlLogin}" method="post">
				<div class="form-group">
					<label for="username">Tên đăng nhập</label>
					<input type="text" class="form-control" name="username" value="${username}" id="username" placeholder="Tên đăng nhập" required="required">
				</div>
				<div class="form-group">
					<label for="password">Mật khẩu</label>
					<input type="password" class="form-control" name="password" value="" id="password" placeholder="Mật khẩu" required="required">
				</div>
				<div>
					<button type="submit" class="btn btn-success">Đăng nhập</button>
				</div>
				<div style="margin-top: 10px; font-style: italic">
					<p>Bạn chưa có tài khoản? <a href="${urlRegister}" title="Đăng ký tài khoản">Đăng ký ngay</a></p>
				</div>
			</form>
		</div>
	</div>
</div>