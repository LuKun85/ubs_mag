<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="com.company.core.Enum.StatisticalTypeEnum"%>
<html>
<head>
<title>首页</title>
</head>
<body>
<div class="bjui-pageHeader">
    <form action="${pageContext.request.contextPath}/statistical/flush/<%=StatisticalTypeEnum.USER_LOGIN%>" data-toggle="ajaxsearch">
        <label>开始时间：</label>
        <input name="startTime" id="startTime" value="${start}" data-toggle="datepicker" class="form-control" size="15" data-pattern="yyyy-MM-dd" />
        <label>结束时间：</label>
        <input name="endTime" id="endTime" value="${end}" data-toggle="datepicker" class="form-control" size="15" data-pattern="yyyy-MM-dd"/>
        <button type="submit" class="btn-default" data-icon="search"">查询</button>
        </form>
        </div>
		<%-- <div id="abc"  data-toggle="echarts"
			data-url="statistical/<%=StatisticalTypeEnum.USER_LOGIN%>?start=${start}&end=${end}"></div> --%>
		<div id="main" style="mini-width: 400px; height: 350px"></div>
	<script type="text/javascript">
		var flush_data = '${data}';
	    if(flush_data !=''){
	    	var data = JSON.parse(flush_data);
	    	var myChart = echarts.init(document.getElementById('main'));
	   		myChart.setOption(data);
	    }
	</script>
</body>
</html>