    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%--
    Created by IntelliJ IDEA.
    User: APPLE
    Date: 15/12/18
    Time: 上午11:22
    To change this template use File | Settings | File Templates.
    --%>
    <%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <html>
    <head>
        <title>资源管理</title>
    </head>
    <body>
    <div class="bjui-pageHeader">
    <form id="pagerForm" name="funcManageForm" data-toggle="ajaxsearch" action="${pageContext.request.contextPath}/funcManagement/query_a_func" method="post">
        <input type="hidden" name="pageSize" value="${funcManageForm.pageSize}">
        <input type="hidden" name="pageCurrent" value="${funcManageForm.pageCurrent}">
        <div class="bjui-searchBar">
        	<table>

	            <td>查找资源：</td>
	            <td><input type="text" name="funcName" id="funcName" value="${funcManageForm.funcName}"  size="15"></td>
            	<td><a href="javascript:;" class="btn btn-default" data-toggle="reloadsearch" data-clear-query="true">重置</a></td>
	            <td><button type="submit" class="btn-default" data-icon="search">查询</button></td>
	            <td><a href="${pageContext.request.contextPath}/funcManagement/addnewpage" class="btn btn-green" data-toggle="dialog" data-width="400" data-height="400" data-id="dialog-normal" data-title="新增资源配置">新增资源</a></td>
        	</table>
        </div>
    </form>
    </div>
    <div class="bjui-pageContent tableContent">
    <table  class="table table-bordered table-hover table-striped table-top" data-toggle="tablefixed" data-width="100%" data-nowrap="true">
        <thead>
        <tr>
        <th align="center">资源ID</th>
        <th align="center">资源名</th>
        <th align="center">上级资源ID</th>
        <th align="center">描述</th>
        <th align="center">备注</th>
        <th align="center">状态</th>
        <th align="center">标签</th>
        <th align="center">等级</th>
        <th align="center">权限字符串</th>
        <th align="center">图标字符串</th>
        <th align="center">优先级</th>
        <th align="center">操作</th>
        </tr>
        </thead>
        <tbody>
    <c:forEach var="record" items="${funcManageForm.pagination.list}" varStatus="status">
    <tr data-id="<c:out value="${record.funcId}"/>">
        <td align="center"><c:out value="${record.funcId}"/></td>
        <td align="center"><c:out value="${record.funcName}"/></td>
        <td align="center"><c:out value="${record.funcFatherId}"/></td>
        <td align="center"><c:out value="${record.funcDesc}"/></td>
        <td align="center"><c:out value="${record.funcRemark}"/></td>
        <td align="center"><c:out value="${record.funcDisableTag}"/></td>
        <td align="center"><c:out value="${record.funcTag}"/></td>
        <td align="center"><c:out value="${record.funcLevel}"/></td>
        <td align="center"><c:out value="${record.funcUrl}"/></td>
        <td align="center"><c:out value="${record.funcIcon}"/></td>
        <td align="center"><c:out value="${record.funcPriority}"/></td>
        <td align="center">
            <a href="${pageContext.request.contextPath}/funcManagement/edit?sid=<c:out value="${record.funcId}"/>" class="btn btn-green" data-toggle="dialog" data-width="800" data-height="400" data-id="dialog-normal" data-title="资源信息">修改</a>
        </td>
        </tr>
    </c:forEach>
        </tbody>
    </table>
    </div>
    <div class="bjui-pageFooter" id="pageFooter">
    <div class="pages">
        <span>每页 </span>
        <div class="selectPagesize">
        <select data-toggle="selectpicker" data-toggle-change="changepagesize">
            <option value="10">10</option>
            <option value="30">30</option>
            <option value="60">60</option>
            <option value="100">100</option>
        </select>
        </div>
        <span> 条，共 <c:out value="${funcManageForm.pagination.itemCount}"/> 条</span>
    </div>
    <div class="pagination-box" data-toggle="pagination" data-total="<c:out value="${funcManageForm.pagination.itemCount}"/>" data-page-size="<c:out value="${funcManageForm.pageSize}"/>" data-page-current="<c:out value="${funcManageForm.pageCurrent}"/>">
    </div>
    </div>

    </div>

    </body>
    </html>
