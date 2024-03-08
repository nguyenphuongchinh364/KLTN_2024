<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/components/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Demo</title>
	<c:url value="/resources/admin" var="demoContextPath" scope="application" ></c:url>
	<script type="text/javascript" src="${adminContextPath}/js/jquery-3.5.1.min.js"></script>
	<script type="text/javascript" src="${adminContextPath}/js/jquery.validate.min.js"></script>
</head>
<body>
	<h2>This is demo page</h2>
	<form>
		<input class="demo" type="checkbox" name="demo" value="Java" /> Java <br>
		<input class="demo" type="checkbox" name="demo" value="PHP" /> PHP <br>
		<input class="demo" type="checkbox" name="demo" value="JS" /> JS <br>
		<input class="demo" type="checkbox" name="demo" value="Python" /> Python <br>
		<input type="button" onclick="abc()" value="Submit" />
	</form>
	
	<script type="text/javascript">
		function abc() {
			window.location.href = 'https://lol.fandom.com/wiki/League_of_Legends_Esports_Wiki';
		}
	</script>
</body>
</html>