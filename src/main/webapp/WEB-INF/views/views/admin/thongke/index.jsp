<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/components/taglib.jsp" %>
			<div class="content-box-large">	
                <div class="row">
	  				<div class="panel-heading">
	  					<div class="panel-title ">Thống kê doanh thu bán hàng</div>
		  			</div>
				</div>
				<hr>	
				<div class="row">
					<div class="col-md-9">
						<a href="${urlAdminDoanhThu}/nhan-vien" class="btn btn-primary">Thống kê theo nhân viên</a>
					</div>
                	<div class="col-md-3">
	                	<form action="${urlAdminDoanhThu}" method="get">
		                 	 <div class="input-group form">
		                       <select name="yearSelect" class="form-control" id="year">
		                       		<c:if test="${not empty listYear}">
		                       			<c:forEach items="${listYear}" var="y">
		                       				<option value="${y}" <c:if test='${y == year}'>selected</c:if>>Năm ${y}</option>
		                       			</c:forEach>
		                       		</c:if>
		                       </select>
		                       <span class="input-group-btn">
		                         	<button class="btn btn-primary" type="submit">Thống kê</button>
		                       </span>
		                  	 </div>
	                  	</form>
                  	</div>
				</div>
                <div class="x_panel tile fixed_height_320" style="margin-top: 20px">
                    <div>
                        <h4>Tổng doanh thu trong năm: <span id="tongDoanhThu" style="color: red; font-weight: bold">${stringUtil.beautifulPrice(tongDoanhThu)}đ</span></h4>
                    </div>
                    <div class="x_content">
                        <table id="tblStocks" class="table table-striped table-bordered" id="example">
                            <thead>
                                <tr>
                                	<c:forEach begin="1" end="12" var="thang">
                                		<th>Tháng ${thang}</th>
                                	</c:forEach>
                                </tr>
                            </thead>
                            <tbody>
                                <tr class="odd gradeX">
                                	<c:if test="${not empty listDoanhThu}">
                                		<c:set value="0" var="thang"></c:set>
                                		<c:forEach items="${listDoanhThu}" var="doanhThu">
                                			<td align="right">${stringUtil.beautifulPrice(doanhThu)}đ</td>  
                                		</c:forEach>
                                	</c:if>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
            <div class="content-box-header">
                <div class="panel-title">Biểu đồ thống kê doanh thu</div>
            </div>
            <script type="text/javascript">
                google.charts.load('current', {callback: init,'packages':['corechart']});
                google.charts.setOnLoadCallback(drawChart);
                
                function init() {
                	var year = $("#year").val();
                	fetch('http://localhost:8080/shopttcn/api/doanh-thu/'+year).then(response => response.json()).then(data => {
                		let input = [];
                		for(let item of data){
                			input.push([item.month , item.sales]);
                		}
                		input.unshift(["Month", "Sales"]);
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
	document.getElementById("admin_sales").className = "current";
</script>