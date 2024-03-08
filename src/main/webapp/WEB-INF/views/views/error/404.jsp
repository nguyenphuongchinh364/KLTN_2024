<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/components/taglib.jsp" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>Error 404 | Không tìm thấy trang</title>
	
	<c:url value="/resources/error" var="errorContextPath"></c:url>
	
	<link href="https://fonts.googleapis.com/css?family=Montserrat:400,700,900" rel="stylesheet">
	<link type="text/css" rel="stylesheet" href="${errorContextPath}/css/style.css" />
</head>

<body>
	<div id="notfound">
		<div class="notfound">
			<div class="notfound-404">
				<h1>Oops!</h1>
			</div>
			<h2>404 - Không tìm thấy trang</h2>
			<p>Trang bạn đang tìm kiếm có thể đã bị xóa do đổi tên hoặc tạm thời không có sẵn.</p>
			<a href="javascript:void(0)" onclick="history.back()">Quay lại</a>
		</div>
	</div>
</body>
</html>
