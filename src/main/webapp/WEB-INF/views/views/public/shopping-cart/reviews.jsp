<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/components/taglib.jsp"%>
<div class="main-content fl-right">
	<div class="section" id="detail-blog-wp">
		<div class="section-head clearfix">
			<h3 class="section-title">Viết nhận xét về sản phẩm (${product.productName})</h3>
		</div>
		<hr />
		<div class="section-detail">
			<c:if test="${not empty success}">
				<div class="alert alert-success" role="alert">${success}</div>
			</c:if>

			<c:if test="${not empty error}">
				<div class="alert alert-danger" role="alert">${error}</div>
			</c:if>
		
			<div class="row" style="margin-bottom: 35px; border-bottom: 1px solid #DDD">
				<div class="form-group" id="rating-ability-wrapper" style="margin-left: 10px">
					<form action="javascript:void(0)">
						<label class="control-label" for="rating">
						    <input type="hidden" id="selected_rating" name="selected_rating" value="">
						    <input type="hidden" id="present_rating" name="present_rating" value="0">
						</label>
						<h2 class="bold rating-header" style="margin-bottom: 10px; margin-top: -20px">
					    	<span class="selected-rating">Xếp hạng sản phẩm</span>
					    </h2>
					    <button onclick="rating(1)" type="button" class="btnrating btn btn-default btn-lg" data-attr="1" id="rating-star-1">
					        <i class="fa fa-star" aria-hidden="true"></i>
					    </button>
					    <button onclick="rating(2)" type="button" class="btnrating btn btn-default btn-lg" data-attr="2" id="rating-star-2">
					        <i class="fa fa-star" aria-hidden="true"></i>
					    </button>
					    <button onclick="rating(3)" type="button" class="btnrating btn btn-default btn-lg" data-attr="3" id="rating-star-3">
					        <i class="fa fa-star" aria-hidden="true"></i>
					    </button>
					    <button onclick="rating(4)" type="button" class="btnrating btn btn-default btn-lg" data-attr="4" id="rating-star-4">
					        <i class="fa fa-star" aria-hidden="true"></i>
					    </button>
					    <button onclick="rating(5)" type="button" class="btnrating btn btn-default btn-lg" data-attr="5" id="rating-star-5">
					        <i class="fa fa-star" aria-hidden="true"></i>
					    </button>
					    <div style="margin-top: 15px">
					    	<textarea id="reviews-content" rows="6" cols="105" placeholder="Nội dung đánh giá" class="form-control"></textarea>
					    </div>
					    <div style="margin-top: 15px">
					    	<button onclick="reviews(${product.productId})" class="btn btn-success">Đánh giá</button>
					    	<button onclick="history.back()" class="btn btn-primary">Quay lại</button>
					    </div>
				    </form>
				</div>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript">
	var url = window.location.href;
	var orderId = url.split('/')[5].split('-')[2];

	function reviews(productId) {
		var rating = $("#present_rating").val();
		var reviewsContent = $("#reviews-content").val();
		if (rating == 0) {
			alert('Vui lòng xếp hạng sản phẩm trước khi gửi đánh giá!');
			return;
		}
		if (reviewsContent == '') {
			alert('Vui lòng nhập nội dung trước khi gửi đánh giá!');
			return;
		}
		$.ajax({
			url: '${urlReviews}',
			type: 'POST',
			cache: false,
			dataType: 'json',
			data: {
				productId: productId,
				rating: rating,
				reviewsContent: reviewsContent
			},
			success: function(data){
				if (data.code == 1) {
					alert(data.content);
					return;
				}
				alert('Cảm ơn bạn đã đánh giá sản phẩm!');
				window.location.href = 'http://localhost:8081/shopttcn/don-hang/don-hang-'+orderId;
			},
			error: function (){
				alert('Có lỗi xảy ra!');
			}
		});
		return false;
	};

	function rating(selected_value) {
		var previous_value = $("#selected_rating").val();
		$("#selected_rating").val(selected_value);
		$("#present_rating").val(selected_value);
		if (selected_value == 1) {
			$(".selected-rating").html('Xếp hạng sản phẩm: Rất không hài lòng');
		} else if (selected_value == 2) {
			$(".selected-rating").html('Xếp hạng sản phẩm: Không hài lòng');
		} else if (selected_value == 3) {
			$(".selected-rating").html('Xếp hạng sản phẩm: Bình thường');
		} else if (selected_value == 4) {
			$(".selected-rating").html('Xếp hạng sản phẩm: Hài lòng');
		} else {
			$(".selected-rating").html('Xếp hạng sản phẩm: Cực kỳ hài lòng');
		}
	
		for (i = 1; i <= selected_value; ++i) {
			$("#rating-star-"+i).toggleClass('btn-warning');
			$("#rating-star-"+i).toggleClass('btn-default');
		}
		
		for (ix = 1; ix <= previous_value; ++ix) {
			$("#rating-star-"+ix).toggleClass('btn-warning');
			$("#rating-star-"+ix).toggleClass('btn-default');
		}
	}
</script>