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
    <title>账户权限设置</title>
</head>
<body>
<div class="bjui-pageHeader">
    <input type="hidden" name="pageSize" value="${accountOauthForm.pageSize}">
    <input type="hidden" name="pageCurrent" value="${accountOauthForm.pageCurrent}">
    <div class="bjui-searchBar">
      <label>账户名：</label>
      <label><c:out value="${accountOauthForm.usrName}"/></label>&nbsp;
      <label>账户状态：</label>
      <label><c:out value="${accountOauthForm.usrDisableTag}"/></label>&nbsp;
      <a href="${pageContext.request.contextPath}/userManagement/acctenable?id=${accountOauthForm.usrId}" class="btn btn-red" data-toggle="doajax" data-confirm-msg="确定启用吗？" data-callback="quanRefreshAndClose">启用</a>&nbsp;
      <a href="${pageContext.request.contextPath}/userManagement/acctdisable?id=${accountOauthForm.usrId}" class="btn btn-red" data-toggle="doajax" data-confirm-msg="确定禁用吗？" data-callback="quanRefreshAndClose">禁用</a>&nbsp;

      <!-- <button type="submit" class="btn-default" data-icon="search">查询</button>&nbsp;-->
    </div>
</div>
<div class="bjui-pageContent tableContent">
  <table  class="table table-bordered table-hover table-striped table-top" data-toggle="tablefixed" data-width="100%" data-nowrap="true">
    <thead>
    <tr>
      <th align="center">权限组</th>
      <th align="center">授权状态</th>
      <th align="center">操作</th>
    </tr>
    </thead>
    <tbody>
<c:forEach var="record" items="${accountOauthForm.pagination.list}" varStatus="status">
<tr data-id="<c:out value="${record.roleId}"/>">
      <td align="center"><c:out value="${record.roleName}"/></td>
      <td align="center"><c:out value="${record.checked}"/></td>
      <td align="center">
         <a href="${pageContext.request.contextPath}/userManagement/addauthority?id=<c:out value="${record.roleId}"/>&uid=${accountOauthForm.usrId}" class="btn btn-red" data-toggle="doajax" data-callback="quanRefreshAndClose">授权</a>&nbsp;
         <a href="${pageContext.request.contextPath}/userManagement/cancelauthority?id=<c:out value="${record.roleId}"/>&uid=${accountOauthForm.usrId}" class="btn btn-red" data-toggle="doajax" data-callback="quanRefreshAndClose">禁止</a>&nbsp;
      </td>
</tr>
  </c:forEach>
    </tbody>
  </table>

</div>
<div class="bjui-pageFooter" id="pageFooter">

</div>

</div>

</body>
<script type="text/javascript">
    function quanRefreshAndClose(json) {
        $(this)
                .bjuiajax('ajaxDone',json)
                .dialog('closeCurrent',true)
              //  .navtab('refresh')
    }
</script>
</html>
