<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/components/taglib.jsp"%>
<!DOCTYPE html>
<html>
  <head>
    <title>ISMART ADMIN | LOGIN</title>
    <link rel="shortcut icon" type="image/ico" href="${adminContextPath}/images/icon-180x180.png" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    
    <c:url value="/resources/admin" var="adminContextPath" scope="application" ></c:url>
    
    <link href="${adminContextPath}/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css?family=Lobster" rel="stylesheet">
    <link href="${adminContextPath}/css/style1.css" rel="stylesheet">
  </head>
  <body>
  	<div class="header">
	     <div class="container">
	        <div class="row">
	           <div class="col-md-5">
	              <div class="logo">
	                 <h1><a href="${urlAdminIndex}">G18 Shop</a></h1>
	              </div>
	           </div>
	           <div class="col-md-3">
	              <div class="row">
	                <div class="col-lg-12"></div>
	              </div>
	           </div>
	           <div class="col-md-4">
	              <div class="navbar navbar-inverse" role="banner">
	                  <nav class="collapse navbar-collapse bs-navbar-collapse navbar-right" role="navigation">
	                    <ul class="nav navbar-nav">
	                      <li class="dropdown">
	                        <a href="#" class="dropdown-toggle" data-toggle="dropdown">Đăng nhập vào trang quản trị</a>
	                      </li>
	                    </ul>
	                  </nav>
	              </div>
	           </div>
	        </div>
	     </div>
	</div>

	<div class="page-content container">
		<div class="row">
			<div class="col-md-4 col-md-offset-4">
				<div class="login-wrapper">
			        <div class="box">
			            <div class="content-wrap">
			            	<img width="100px" height="100px" class="img-circle" src="${adminContextPath}/images/icon-180x180.png">
			                <h6>Đăng nhập</h6>
			                <form action="" method="post">
			                	<c:if test="${not empty param.msg}">
									<div class="alert alert-danger" role="alert">
									    Đăng nhập thất bại, tài khoản hoặc mật khẩu không chính xác
									</div>
								</c:if>
			                
				                <div class="form-group">
				                	<label class="text-left pull-left" for="username">Tên đăng nhập</label>
				               		<input class="form-control" name="username" type="text" placeholder="Username">
				                </div>
				                
				                <div class="form-group">
				                	<label class="text-left pull-left" for="password">Mật khẩu</label>
				                	<input class="form-control" name="password" type="password" placeholder="Password">
				                </div>
				                
				                <div class="action">
				                    <input type="submit" value="Đăng nhập" class="btn btn-primary btn-block" />
				                </div>
			                </form>            
			            </div>
			        </div>

			        <div class="already">
			            <a href="#">Quên mật khẩu?</a>
			        </div>
			    </div>
			</div>
		</div>
	</div>

    <script src="https://code.jquery.com/jquery.js"></script>
    <script src="${adminContextPath}/bootstrap/js/bootstrap.min.js"></script>
    <script src="${adminContextPath}/js/custom.js"></script>
  </body>
</html>