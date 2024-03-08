<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:url value="/resources/public/images" var="img" ></c:url>
<div id="footer-wp">
   <div id="foot-body">
      <div class="wp-inner clearfix">
         <div class="block" id="info-company">
            <h3 class="title">IT Shop</h3>
            <p class="desc">Sản phẩm IT Shop cung cấp luôn là sản phẩm chính hãng có thông tin rõ ràng, chính sách ưu đãi cực lớn cho khách hàng có thẻ thành viên.</p>
            <div id="payment">
               <div class="thumb">
                  <img class="img-fluid" src="${contextPath}/images/img-foot.png"  alt="">
               </div>
            </div>
         </div>
         <div class="block menu-ft" id="info-shop">
            <h3 class="title">Thông tin cửa hàng</h3>
            <ul class="list-item">
               <li>
                  <p>154 Phạm Như Xương - Liên Chiểu - Đà Nẵng</p>
               </li>
               <li>
                  <p>0987.654.321 - 0989.989.989</p>
               </li>
               <li>
                  <p>itshop123@gmail.com</p>
               </li>
            </ul>
         </div>
         <div class="block menu-ft policy" id="info-shop">
            <h3 class="title">Chính sách mua hàng</h3>
            <ul class="list-item">
               <li>
                  <a href="#" title="">Chính sách bảo hành</a>
               </li>
               <li>
                  <a href="#" title="">Chính sách đổi trả</a>
               </li>
               <li>
                  <a href="#" title="">Chính sách bảo mật</a>
               </li>
               <li>
                  <a href="#" title="">Chính sách sử dụng</a>
               </li>
            </ul>
         </div>
         <div class="block" id="newfeed">
            <h3 class="title">Địa chỉ cửa hàng</h3>
            <iframe src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d1298.0579913922068!2d108.14941872168122!3d16.06487730499905!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x314219294f388963%3A0x5eabe6dd04d1ad71!2zMTU0IFBo4bqhbSBOaMawIFjGsMahbmcsIEhvw6AgS2jDoW5oIE5hbSwgTGnDqm4gQ2hp4buDdSwgxJDDoCBO4bq1bmcgNTUwMDAwLCBWaeG7h3QgTmFt!5e0!3m2!1svi!2s!4v1620456748765!5m2!1svi!2s" width="600" height="150" style="border:0"></iframe>
         </div>
      </div>
   </div>
   <div id="foot-bot">
      <div class="wp-inner">
         <p id="copyright">Thanks for template by unitop.vn | PHP Master</p>
      </div>
   </div>
</div>

<script type="text/javascript">
	function getDistrictByProvinceId() {
		var ward = '<option value="0">-- Chọn Xã/Phường --</option>';
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
				var rs = '<option value="0">-- Chọn Quận/Huyện --</option>';
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
				var rs = '<option value="0">-- Chọn Xã/Phường --</option>';
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
	var avatar = $("#preview-avatar").attr("src");

	function previewAvatar() {
		var input = document.getElementById("input-avatar");
		var img = document.getElementById("preview-avatar");
		const [file] = input.files;
		if (file) {
			img.src = URL.createObjectURL(file);
		}
	}
	
	function cancelUploadAvatar() {
		$("#preview-avatar").attr("src",avatar);
		$("#input-avatar").val("");
	}
</script>

<script type="text/javascript">
	// multi level cat
	showCat();
	
	async function showCat() {
		let data = await fetch('http://localhost:8080/shopttcn/api/cat/list/parent/0').then(response => response.json());
		var rs = '';
		if (data.length > 0) {
			for(let item of data){
				rs += '<li><a href="${urlCat}/'+item.catSlug+'-'+item.catId+'" title="">'+item.catName+'</a>';
				rs += await multilevelCat(item.catId);
			}
		}
		$("#multilevel-cat").html(rs);
	}
	
	async function multilevelCat(parentId) {
		var kq = '';
		try {
			let data = await fetch('http://localhost:8080/shopttcn/api/cat/list/parent/'+parentId).then(response => response.json());
			if (data.length > 0) {
				kq += '<ul class="sub-menu">';
				for(let item of data){
					kq += '<li><a href="${urlCat}/'+item.catSlug+'-'+item.catId+'" title="">'+item.catName+'</a>';
					kq += await multilevelCat(item.catId);
				}
				kq += '</ul>';
				kq += '<i class="fa fa-angle-right arrow" aria-hidden="true"></i>';
				kq += '</li>';
			}
		} catch(error) {
			kq += '</li>';
		}
		return kq;
	}
</script>