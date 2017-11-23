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
    <title>添加新的角色</title>
</head>
<body>
<div class="bjui-pageHeader">

</div>
<div class="bjui-pageContent tableContent">
 <form id="pagerForm" name="roleManagementForm" data-toggle="validate" novalidate="novalidate" action="${pageContext.request.contextPath}/roleManagement/addnew" method="post">
    <div class="bjui-searchBar">
      <div class="form-group" style="margin: 20px 0 20px; ">
      <label class="control-label x85">角 色：</label>
      <input type="text" value="<c:out value="${roleManagementForm.roleName}"/>" name="roleName" size="15" data-rule="角色名:required"/>&nbsp;
      </div>
      <div class="form-group" style="margin: 20px 0 20px; ">
      <label class="control-label x85">说 明：</label>
      <input type="text" value="<c:out value="${roleManagementForm.roleRemark}"/>" name="roleRemark" size="15" />&nbsp;
      </div>
      <div class="form-group" style="margin: 20px 0 20px; ">
      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<button type="submit" class="btn-default" data-icon="save">新增</button>&nbsp;
      </div>
    </div>
  </form>
</div>
<div class="bjui-pageFooter" id="pageFooter">
  <div class="pages">
  </div>
</div>

</div>

</body>
</html>
