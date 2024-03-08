<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/components/taglib.jsp"%>
<div class="main-content fl-right">
	<div class="section" id="detail-blog-wp">
		<div class="section-head clearfix">
			<h3 class="section-title">Thông tin cá nhân</h3>
		</div>
		<hr />
		<div class="section-detail">
			<c:if test="${not empty success}">
				<div class="alert alert-success" role="alert">${success}</div>
			</c:if>

			<c:if test="${not empty error}">
				<div class="alert alert-danger" role="alert">${error}</div>
			</c:if>
		
			<form action="${urlAccount}/thong-tin-ca-nhan" method="post" enctype="multipart/form-data">
				<div class="form-group">
					<label for="username">Tên đăng nhập</label>
					<input type="text" value="${user.username}" name="username" class="form-control" readonly="readonly" >										
				</div>

				<div class="form-group">
					<label for="userFullname">Tên</label>
					<form:errors path="userError.userFullname" cssStyle="color: red; font-italic: red"></form:errors>
					<input type="text" value="${user.userFullname}" name="userFullname" class="form-control" placeholder="Nhập họ tên" >										
				</div>
				
				<div class="form-group">
					<label for="userEmail">Email</label>
					<form:errors path="userError.userEmail" cssStyle="color: red; font-italic: red"></form:errors>
					<input type="text" value="${user.userEmail}" name="userEmail" class="form-control" placeholder="Nhập email" >										
				</div>
				
				<div class="form-group">
					<label for="userPhone">Số điện thoại</label>
					<form:errors path="userError.userPhone" cssStyle="color: red; font-italic: red"></form:errors>
					<input type="text" value="${user.userPhone}" name="userPhone" class="form-control" placeholder="Nhập số điện thoại" >										
				</div>
				
				<div class="form-group">
					<label for="province">Địa chỉ (Tỉnh/Thành phố)</label>
					<form:errors path="addressError.province" cssStyle="color: red; font-italic: red"></form:errors>
					<select name="province.provinceId" class="form-control" id="province" onchange="getDistrictByProvinceId()">
						<option value="0">-- Chọn Tỉnh/Thành phố --</option>
						<c:if test="${not empty listProvince}">
							<c:forEach items="${listProvince}" var="province" >
								<option value="${province.provinceId}" <c:if test='${province.provinceId == address.province.provinceId}'>selected="selected"</c:if>>${province.provinceName}</option>
							</c:forEach>
						</c:if>
					</select>
				</div>
				
				<div class="form-group">
					<label for="district">Địa chỉ (Quận/Huyện)</label>
					<form:errors path="addressError.district" cssStyle="color: red; font-italic: red"></form:errors>
					<select name="district.districtId" class="form-control" id="district" onchange="getWardByDistrictId()">
						<option value="0">-- Chọn Quận/Huyện --</option>
						<c:if test="${not empty listDistrict}">
							<c:forEach items="${listDistrict}" var="district" >
								<option value="${district.districtId}" <c:if test='${district.districtId == address.district.districtId}'>selected="selected"</c:if>>${district.districtName}</option>
							</c:forEach>
						</c:if>
					</select>
				</div>
				
				<div class="form-group">
					<label for="ward">Địa chỉ (Xã/Phường)</label>
					<form:errors path="addressError.ward" cssStyle="color: red; font-italic: red"></form:errors>
					<select name="ward.wardId" class="form-control" id="ward">
						<option value="0">-- Chọn Xã/Phường --</option>
						<c:if test="${not empty listWard}">
							<c:forEach items="${listWard}" var="ward" >
								<option value="${ward.wardId}" <c:if test='${ward.wardId == address.ward.wardId}'>selected="selected"</c:if>>${ward.wardName}</option>
							</c:forEach>
						</c:if>
					</select>
				</div>
				
				<div class="form-group">
					<label for="addressDetail">Địa chỉ (Số nhà, tên đường...)</label>
					<form:errors path="addressError.addressDetail" cssStyle="color: red; font-italic: red"></form:errors>
					<input type="text" name="addressDetail" class="form-control" value="${address.addressDetail}" placeholder="Nhập số nhà, tên đường...">
				</div>
				
				<div class="form-group">
					<div class="row">
						<div class="col-md-6">
							<label>Tải lên ảnh đại diện</label>
							<form:errors path="userError.avatar" cssStyle="color:red;font-style:italic" ></form:errors>
							<input id="input-avatar" type="file" name="picture" class="btn btn-default" onchange="previewAvatar()">
							<p class="help-block">
								<em>Định dạng: jpg, png, jpeg</em> <br />
								<em><a href="javascript:void(0)" onclick="cancelUploadAvatar()">Huỷ tải lên</a></em>
							</p>
						</div>
						<div class="col-md-6">
							<c:set value="${contextPath}/images/user.png" var="userAvatar"></c:set>
							<c:if test="${not empty user.avatar}">
								<c:set value="${pictureContextPath}/avatar/${user.avatar}" var="userAvatar"></c:set>
							</c:if>
							<label>Ảnh đại diện</label> <br />
							<img id="preview-avatar" src="${userAvatar}" style="width: 160px; height: 160px; border-radius: 50%" />
						</div>
					</div>
				</div>
				
				<div>
					<button type="submit" class="btn btn-success">Lưu</button>
					<a href="${urlAccount}/doi-mat-khau" title="Đổi mật khẩu tài khoản cá nhân" class="btn btn-primary">Đổi mật khẩu</a>
				</div>
			</form>
		</div>
	</div>
</div>