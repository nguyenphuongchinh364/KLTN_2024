<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/components/taglib.jsp" %>
			<div class="content-box-large">	
                <div class="row">
	  				<div class="panel-heading">
	  					<div class="panel-title ">Thống kê doanh thu bán hàng theo nhân viên</div>
		  			</div>
				</div>
				<hr>	
				<div class="row">
					<div class="col-md-6"></div>
                	<div class="col-md-6">
	                	<form action="${urlAdminDoanhThu}/nhan-vien" method="get">
		                 	 <div class="input-group form">
		                       <select name="yearSelect" class="form-control" id="year" style="width: 33%; float: right">
		                       		<option value="0">Chọn năm</option>
		                       		<c:if test="${not empty listYear}">
		                       			<c:forEach items="${listYear}" var="y">
		                       				<option value="${y}" <c:if test='${y == year}'>selected</c:if>>Năm ${y}</option>
		                       			</c:forEach>
		                       		</c:if>
		                       </select>
		                       <select name="monthSelect" class="form-control" id="month" style="width: 33%; float: right">
		                       		<option value="0">Chọn tháng</option>
		                       		<c:forEach begin="1" end="12" var="m">
		                       			<option value="${m}" <c:if test='${m == month}'>selected</c:if>>Tháng ${m}</option>
		                       		</c:forEach>
		                       </select>
		                       <div class="clear"></div>
		                       <span class="input-group-btn">
		                         	<button class="btn btn-primary" type="submit" onclick="return validate()">Thống kê</button>
		                       </span>
		                  	 </div>
	                  	</form>
                  	</div>
				</div>
                <div class="x_panel tile fixed_height_320">
                    <div>
                        <h3>Tổng doanh thu: <span id="tongDoanhThu" style="color: red; font-weight: bold">${stringUtil.beautifulPrice(tongDoanhThu)}đ</span></h3>
                    </div>
                    <div class="x_content">
                    	<c:if test="${not empty list}">
	                        <table id="tblStocks" class="table table-striped table-bordered" id="example">
	                            <thead>
	                                <tr>
	                                    <th>ID</th>
	                                    <th>Tên nhân viên</th>
	                                    <th>Số lượng đơn hàng</th>
	                                    <th>Số lượng sản phẩm</th>
	                                    <th>Tổng doanh thu</th> 
	                                </tr>
	                            </thead>
	                            <tbody>
	                            	<c:forEach items="${list}" var="nv">
		                                <tr class="odd gradeX">
		                                    <td>${nv.userId}</td>
		                                    <td>${nv.userFullname}</td>
		                                    <td>${nv.orderQuantity}</td>
		                                    <td>${nv.orderProductQuantity}</td>
		                                    <td align="right">${stringUtil.beautifulPrice(nv.totalSales)}đ</td>
		                                </tr>
	                                </c:forEach>
	                            </tbody>
	                        </table>
                        </c:if>
                    </div>
                </div>
            </div>
            <div class="content-box-header">
                <div class="panel-title">Biểu đồ thống kê doanh thu theo nhân viên</div>
            </div>
            <script type="text/javascript">
            	google.charts.load('current', {callback: init,'packages':['corechart']});
                google.charts.setOnLoadCallback(drawChart);
                
                function init() {
                	var year = $("#year").val();
            		var month = $("#month").val();
            		var url = 'http://localhost:8081/shopttcn/api/doanh-thu/nhan-vien';
            		if (year != '0' && month != '0') {
						url += '/' + month + '/' + year;
					}
                	fetch(url).then(response => response.json()).then(data => {
                		let input = [];
                		for(let item of data){
                			input.push([item.userFullname , item.totalSales]);
                		}
                		input.unshift(["Fullname", "Sales"]);
                		drawChart(input);
                	});
                }
                
                function drawChart(a) {
                  var data = google.visualization.arrayToDataTable(a);
                  var tongDoanhThu = $("#tongDoanhThu").html();
                  var options = {'title':'Tổng doanh thu ('+tongDoanhThu+')'};
                
                  var chart = new google.visualization.PieChart(document.getElementById('piechart'));
                  chart.draw(data, options);
                }
            </script>
            <div id="piechart" style="width: 100%; height: 600px;"></div>

<script type="text/javascript">
	function validate() {
		var year = $("#year").val();
		var month = $("#month").val();
		if (year == '0' || month == '0') {
			alert('Chọn tháng, năm trước khi thống kê');
			return false;
		}
		return true;
	}
</script>
            
<script type="text/javascript">
	document.getElementById("admin_sales").className = "current";
</script>