<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div class="container">
	<div class="copy text-center">
		Thank for template by <a href="https://it.vinaenter.vn">Vinaenter</a>
	</div>
</div>

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