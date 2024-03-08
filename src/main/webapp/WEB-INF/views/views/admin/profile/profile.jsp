<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/components/taglib.jsp"%>
<div class="row">
	<div class="col-md-12 panel-info">
		<div class="content-box-header panel-heading">
			<div class="panel-title ">Thông tin cá nhân</div>
		</div>
		<div class="content-box-large box-with-header">
			<form action="${urlAdminProfile}" method="post" enctype="multipart/form-data">
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
								<input type="text" name="username" class="form-control" value="${objUser.username}" readonly="readonly">
							</div>
						
							<div class="form-group">
								<label for="userFullname">Họ tên</label>
								<form:errors path="userError.userFullname" cssStyle="color:red;font-style:italic" ></form:errors>
								<input type="text" name="userFullname" class="form-control" value="${objUser.userFullname}" placeholder="Nhập họ tên">
							</div>

							<div class="form-group">
								<label for="userEmail">Email</label>
								<form:errors path="userError.userEmail" cssStyle="color:red;font-style:italic" ></form:errors>
								<input type="text" name="userEmail" class="form-control" value="${objUser.userEmail}" placeholder="Nhập email">
							</div>

							<div class="form-group">
								<label for="userPhone">Số điện thoại</label>
								<form:errors path="userError.userPhone" cssStyle="color:red;font-style:italic" ></form:errors>
								<input type="text" name="userPhone" class="form-control" value="${objUser.userPhone}" placeholder="Nhập số điện thoại">
							</div>

							<div class="form-group">
								<label for="province.provinceId">Địa chỉ (Tỉnh/Thành phố)</label>
								<form:errors path="addressError.province" cssStyle="color:red;font-style:italic" ></form:errors>
								<select name="province.provinceId" class="form-control" id="province" onchange="getDistrictByProvinceId()">
									<option value="0">-- Tỉnh/Thành phố --</option>
									<c:if test="${not empty listProvince}">
										<c:forEach items="${listProvince}" var="province" >
											<option value="${province.provinceId}" <c:if test='${objAddress.province.provinceId == province.provinceId}'>selected</c:if> >${province.provinceName}</option>
										</c:forEach>
									</c:if>
								</select>
							</div>
							
							<div class="form-group">
								<label for="district.districtId">Địa chỉ (Quận/Huyện)</label>
								<form:errors path="addressError.district" cssStyle="color:red;font-style:italic" ></form:errors>
								<select name="district.districtId" class="form-control" id="district" onchange="getWardByDistrictId()">
									<option value="0">-- Quận/Huyện --</option>
									<c:if test="${not empty listDistrict}">
										<c:forEach items="${listDistrict}" var="district" >
											<option value="${district.districtId}" <c:if test='${objAddress.district.districtId == district.districtId}'>selected</c:if> >${district.districtName}</option>
										</c:forEach>
									</c:if>
								</select>
							</div>
							
							<div class="form-group">
								<label for="ward.wardId">Địa chỉ (Xã/Phường)</label>
								<form:errors path="addressError.ward" cssStyle="color:red;font-style:italic" ></form:errors>
								<select name="ward.wardId" class="form-control" id="ward">
									<option value="0">-- Xã/Phường --</option>
									<c:if test="${not empty listWard}">
										<c:forEach items="${listWard}" var="ward" >
											<option value="${ward.wardId}" <c:if test='${objAddress.ward.wardId == ward.wardId}'>selected</c:if> >${ward.wardName}</option>
										</c:forEach>
									</c:if>
								</select>
							</div>
							
							<div class="form-group">
								<label for="addressDetail">Địa chỉ (Số nhà, tên đường...)</label>
								<form:errors path="addressError.addressDetail" cssStyle="color:red;font-style:italic" ></form:errors>
								<input type="text" name="addressDetail" class="form-control" value="${objAddress.addressDetail}" placeholder="Nhập số nhà, tên đường...">
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
									<div class="col-md-6" style="padding-left: 130px">
										<c:set value="${adminContextPath}/images/user.png" var="userAvatar"></c:set>
										<c:if test="${not empty objUser.avatar}">
											<c:set value="${adminPictureContextPath}/avatar/${objUser.avatar}" var="userAvatar"></c:set>
										</c:if>
										<label>Ảnh đại diện</label> <br />
										<img id="preview-avatar" src="${userAvatar}" style="width: 160px; height: 160px; border-radius: 50%" />
									</div>
								</div>
							</div>
						</div>
					</div>
					<hr>
					<div class="row">
						<div class="col-sm-12">
							<input type="submit" value="Lưu" class="btn btn-success" />
							<a href="${urlAdminProfile}/doi-mat-khau" class="btn btn-primary">Đổi mật khẩu</a>
						</div>
					</div>
				</div>
			</form>
		</div>
	</div>
</div>

<script type="text/javascript">
	// ajax: chọn tỉnh/tp => hiển thị list quận/huyện
	function getDistrictByProvinceId() {
		var ward = '<option value="0">-- Xã/Phường --</option>';
		$("#ward").html(ward);
		var provinceId = $("#province").val();
		$.ajax({
			url: '${urlDistrict}',
			type: 'POST',
			cache: false,
			dataType: 'json',
			data: {
				provinceId: provinceId
			},
			success: function(data){
				var rs = '<option value="0">-- Quận/Huyện --</option>';
				for (var i = 0; i < data.length; i++) {
					rs += '<option value="'+data[i].districtId+'">'+data[i].districtName+'</option>';
				}
				$("#district").html(rs);
			},
			error: function (){
				alert('Có lỗi xảy ra!');
			}
		});
		return false;
	};
	
	// ajax: chọn quận/huyện => hiển thị list xã/phường
	function getWardByDistrictId() {
		var districtId = $("#district").val();
		$.ajax({
			url: '${urlWard}',
			type: 'POST',
			cache: false,
			dataType: 'json',
			data: {
				districtId: districtId
			},
			success: function(data){
				var rs = '<option value="0">-- Xã/Phường --</option>';
				for (var i = 0; i < data.length; i++) {
					rs += '<option value="'+data[i].wardId+'">'+data[i].wardName+'</option>';
				}
				$("#ward").html(rs);
			},
			error: function (){
				alert('Có lỗi xảy ra!');
			}
		});
		return false;
	}
</script>

<script type="text/javascript">
	document.getElementById("admin_profile").className = "current";
</script>