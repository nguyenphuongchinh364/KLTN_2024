<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/components/taglib.jsp" %>
<!DOCTYPE html>
<html>
  <head>
    <title>ISMART ADMIN</title>
    
    <c:url value="/resources/admin" var="adminContextPath" scope="application" ></c:url>
    <c:url value="/upload" var="adminPictureContextPath" scope="application" ></c:url>
    
    <link rel="shortcut icon" type="image/ico" href="${adminContextPath}/images/icon-180x180.png" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    
    <link href="${adminContextPath}/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css?family=Lobster" rel="stylesheet">
    <link href="${adminContextPath}/css/style1.css" rel="stylesheet">
    <link href="${adminContextPath}/fontawesome-free/css/all.min.css" rel="stylesheet">
    <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
    
    <script type="text/javascript" src="${adminContextPath}/js/jquery-3.5.1.min.js"></script>
	<script type="text/javascript" src="${adminContextPath}/js/jquery.validate.min.js"></script>

	<script type="text/javascript" src="${adminContextPath}/ckeditor/ckeditor.js"></script>
	<script type="text/javascript" src="${adminContextPath}/ckfinder/ckfinder.js"></script>
  </head>
  
  <jsp:useBean id="stringUtil" class="vn.shopttcn.util.StringUtil" scope="application"></jsp:useBean>
  <jsp:useBean id="constantUtil" class="vn.shopttcn.util.ConstantUtil" scope="application"></jsp:useBean>
  
  <c:url value="/admin" var="urlAdminIndex" scope="application" ></c:url>
  <c:url value="/admin/cat" var="urlAdminCat" scope="application" ></c:url>
  <c:url value="/admin/product" var="urlAdminProduct" scope="application" ></c:url>
  <c:url value="/admin/user" var="urlAdminUser" scope="application" ></c:url>
  <c:url value="/admin/order" var="urlAdminOrder" scope="application" ></c:url>
  <c:url value="/admin/reviews" var="urlAdminReviews" scope="application" ></c:url>
  <c:url value="/admin/doanh-thu" var="urlAdminDoanhThu" scope="application" ></c:url>
  <c:url value="/admin/profile" var="urlAdminProfile" scope="application" ></c:url>
  
  <c:url value="/auth" var="urlAdminAuth" scope="application" ></c:url>
  <c:url value="/location/district" var="urlDistrict" scope="application" ></c:url>
  <c:url value="/location/ward" var="urlWard" scope="application" ></c:url>
  
  <body>
  	<div class="header">
		<tiles:insertAttribute name="header"></tiles:insertAttribute>
	</div>
	
    <div class="page-content">
    	<div class="row">
		  <div class="col-md-2">
		  	<div class="sidebar content-box" style="display: block;">
				<tiles:insertAttribute name="leftbar"></tiles:insertAttribute>
             </div>
		  </div>
		  <div class="col-md-10">
			  <tiles:insertAttribute name="body"></tiles:insertAttribute>	
		  </div>
		</div>
    </div>

      <footer>
          <tiles:insertAttribute name="footer"></tiles:insertAttribute>
      </footer>

    <script src="${adminContextPath}/bootstrap/js/bootstrap.min.js"></script>
    <script src="${adminContextPath}/js/custom.js"></script>
  </body>
</html>