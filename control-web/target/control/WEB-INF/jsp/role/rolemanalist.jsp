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
    <title>账户管理</title>
</head>
<body>
<div class="bjui-pageHeader">
  <form id="pagerForm"  name="roleManagementForm" data-toggle="ajaxsearch" action="${pageContext.request.contextPath}/roleManagement/query_a_role" method="post">
    <input type="hidden" name="pageSize" value="${roleManagementForm.pageSize}">
    <input type="hidden" name="pageCurrent" value="${roleManagementForm.pageCurrent}">
    <div class="bjui-searchBar">
    	<table>
    	<tr>
        <td>查找角色：</td>
        <td><input type="text" name="username" id="username" value="${roleManagementForm.roleName}"  size="15"></td>
        <td><a href="javascript:;" class="btn btn-default" data-toggle="reloadsearch" data-clear-query="true">重置</a></td>
        <td><button type="submit" class="btn-default" data-icon="search">查询</button></td>
        <td><a href="${pageContext.request.contextPath}/roleManagement/addnewpage" class="btn btn-green" data-toggle="dialog" data-width="400" data-height="250" data-id="dialog-normal" data-title="新增角色信息">新增角色</a></td>
    	</tr>
    	</table>
    </div>
  </form>
</div>
<div class="bjui-pageContent tableContent" id="aaaaaa">
  <table  class="table table-bordered table-hover table-striped table-top" data-toggle="tablefixed" data-width="100%" data-nowrap="true">
    <thead>
    <tr>
      <th align="center">角色ID</th>
      <th align="center">角色名</th>
      <th align="center">组标识</th>
      <th align="center">状态</th>
      <th align="center">操作</th>
    </tr>
    </thead>
    <tbody>
<c:forEach var="record" items="${roleManagementForm.pagination.list}" varStatus="status">
<tr data-id="<c:out value="${record.roleId}"/>">
      <td align="center"><c:out value="${record.roleId}"/></td>
      <td align="center"><c:out value="${record.roleName}"/></td>
      <td align="center"><c:out value="${record.roleRemark}"/></td>
      <td align="center"><c:out value="${record.roleDisableTag}"/></td>
      <td align="center">
        <a href="${pageContext.request.contextPath}/roleManagement/edit?rid=<c:out value="${record.roleId}"/>" class="btn btn-green" data-toggle="dialog" data-width="400" data-height="400" data-id="dialog-normal" data-title="角色资源组信息">授权</a>
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
    <span> 条，共 <c:out value="${roleManagementForm.pagination.itemCount}"/> 条</span>
  </div>
  <div class="pagination-box" data-toggle="pagination" data-total="<c:out value="${roleManagementForm.pagination.itemCount}"/>" data-page-size="<c:out value="${roleManagementForm.pageSize}"/>" data-page-current="<c:out value="${roleManagementForm.pageCurrent}"/>">
  </div>
</div>

</div>
</body>
</html>
