<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/components/taglib.jsp" %>
<!DOCTYPE html>
<html>
   <head>
      <title>IT Shop</title>
      <meta charset="UTF-8">
      <meta name="viewport" content="width=device-width, initial-scale=1.0">
      <meta name="csrf-token" content="MQvNURAHYs6BnyT3cZEgqS3R6UOVS2Fgej5FDo3E">
      
      <c:url value="/resources/public" var="contextPath" scope="application" ></c:url>
      <c:url value="/upload" var="pictureContextPath" scope="application" ></c:url>
      
      <link rel="shortcut icon" type="image/ico" href="${contextPath}/images/icon-180x180.png" />
      <link href="${contextPath}/css/css/bootstrap/bootstrap-theme.min.css" rel="stylesheet" type="text/css"/>
      <link href="${contextPath}/css/css/bootstrap/bootstrap.min.css" rel="stylesheet" type="text/css"/>
      <link href="${contextPath}/css/reset.css" rel="stylesheet" type="text/css"/>
      <link href="${contextPath}/css/css/carousel/owl.carousel.css" rel="stylesheet" type="text/css"/>
      <link href="${contextPath}/css/css/carousel/owl.theme.css" rel="stylesheet" type="text/css"/>
      <link href="${contextPath}/css/css/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css"/>
      <link href="${contextPath}/css/style.css" rel="stylesheet" type="text/css"/>
      <link href="${contextPath}/css/responsive.css" rel="stylesheet" type="text/css"/>
      <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.4/jquery.min.js"></script>
      <script type="text/javascript" src="https://cdn.rawgit.com/igorlino/elevatezoom-plus/1.1.6/src/jquery.ez-plus.js"></script>
      <script src="${contextPath}/js/jquery-3.5.1.min.js" type="text/javascript"></script>
      
      <script src="https://www.paypalobjects.com/api/checkout.js"></script>
   </head>
   
   <jsp:useBean id="constantUtil" class="vn.shopttcn.util.ConstantUtil" scope="application"></jsp:useBean>
   <jsp:useBean id="checkoutUtil" class="vn.shopttcn.util.CheckoutUtil" scope="application"></jsp:useBean>
   <jsp:useBean id="dateUtil" class="vn.shopttcn.util.DateUtil" scope="application"></jsp:useBean>
   <jsp:useBean id="stringUtil" class="vn.shopttcn.util.StringUtil" scope="application"></jsp:useBean>
   <jsp:useBean id="reviewsUtil" class="vn.shopttcn.util.ReviewsUtil" scope="application"></jsp:useBean>
   
   <c:url value="/" var="urlIndex" scope="application" ></c:url>
   <c:url value="/danh-muc" var="urlCat" scope="application" ></c:url>
   <c:url value="/san-pham" var="urlDetail" scope="application" ></c:url>
   <c:url value="/lien-he" var="urlContact" scope="application" ></c:url>
   <c:url value="/tim-kiem" var="urlSearch" scope="application" ></c:url>
   <c:url value="/gio-hang" var="urlCart" scope="application" ></c:url>
   <c:url value="/thanh-toan" var="urlCheckout" scope="application" ></c:url>
   <c:url value="/don-hang" var="urlOrder" scope="application" ></c:url>
   
   <c:url value="/dang-nhap" var="urlLogin" scope="application" ></c:url>
   <c:url value="/dang-xuat" var="urlLogout" scope="application" ></c:url>
   <c:url value="/dang-ky-tai-khoan" var="urlRegister" scope="application" ></c:url>
   <c:url value="/tai-khoan" var="urlAccount" scope="application" ></c:url>
   
   <c:url value="/danh-gia-san-pham" var="urlReviews" scope="application"></c:url>
   <c:url value="/cart" var="urlCartAjax" scope="application" ></c:url>
   <c:url value="/product/filter" var="urlFilter" scope="application" ></c:url>
   <c:url value="/location/district" var="urlDistrict" scope="application" ></c:url>
   <c:url value="/location/ward" var="urlWard" scope="application" ></c:url>
   
   <body>
      <div id="site">
         <div id="container">
            <tiles:insertAttribute name="header"></tiles:insertAttribute>
            <div id="main-content-wp" class="home-page clearfix category-product-page detail-product-page">
               <div id="wrapper" class="wp-inner clearfix">
                  <tiles:insertAttribute name="body"></tiles:insertAttribute>
                  <tiles:insertAttribute name="sidebar"></tiles:insertAttribute>
               </div>
            </div>
            <tiles:insertAttribute name="footer"></tiles:insertAttribute>
         </div>
         <div id="btn-top"><img class="img-fluid" src="${contextPath}/images/icon-to-top.png" alt=""/></div>
         <div id="fb-root"></div>
      </div>
      
      <script src="https://i-like-robots.github.io/EasyZoom/dist/easyzoom.js"></script>
      <script src="${contextPath}/js/popper.min.js" type="text/javascript"></script>
      <script src="${contextPath}/js/elevatezoom-master/jquery.elevatezoom.js" type="text/javascript"></script>
      <script src="${contextPath}/js/bootstrap/bootstrap.min.js" type="text/javascript"></script>
      <script src="${contextPath}/js/carousel/owl.carousel.js" type="text/javascript"></script>
      <script src="${contextPath}/js/main.js" type="text/javascript"></script>
   </body>
</html>