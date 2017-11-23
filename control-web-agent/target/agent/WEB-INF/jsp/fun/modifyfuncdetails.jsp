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
    <title>角色资源设置</title>
</head>
<body>
<div class="bjui-pageHeader">
    <input type="hidden" name="pageSize" value="${funcManageForm.pageSize}">
    <input type="hidden" name="pageCurrent" value="${funcManageForm.pageCurrent}">
    <div class="bjui-searchBar">
      <label>资  源：</label>
      <label><c:out value="${funcManageForm.funcName}"/></label>&nbsp;
      <label>资源状态：</label>
      <label><c:out value="${funcManageForm.funcDisableTag}"/></label>&nbsp;
      <a href="${pageContext.request.contextPath}/funcManagement/funcenable?id=${funcManageForm.funcId}" class="btn btn-red" data-toggle="doajax" data-confirm-msg="确定启用吗？" >启用</a>&nbsp;
      <a href="${pageContext.request.contextPath}/funcManagement/funcdisable?id=${funcManageForm.funcId}" class="btn btn-red" data-toggle="doajax" data-confirm-msg="确定禁用吗？" >禁用</a>&nbsp;

      <!-- <button type="submit" class="btn-default" data-icon="search">查询</button>&nbsp;-->
    </div>
</div>
<div class="bjui-pageContent tableContent">
<form id="pagerForm" name="funcManageForm" data-toggle="validate" novalidate="novalidate" action="${pageContext.request.contextPath}/funcManagement/updatefunc" method="post">
    <div class="bjui-searchBar">
      <input type="hidden" name="funcId" value="${funcManageForm.funcId}">
      <br>
      <br>
      <label>父资源&nbsp;ID&nbsp;：</label>
      <input type="text" value="<c:out value="${funcManageForm.funcFatherId}"/>" name="funcFatherId" size="15" />&nbsp;<br><br>

      <label>描&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;述：</label>
      <input type="text" value="<c:out value="${funcManageForm.funcDesc}"/>" name="funcDesc" size="15" />&nbsp;<br><br>

      <label>备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注：</label>
      <input type="text" value="<c:out value="${funcManageForm.funcRemark}"/>" name="funcRemark" size="15" />&nbsp;<br><br>


      <label>标&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;签：</label>
      <input type="text" value="<c:out value="${funcManageForm.funcTag}"/>" name="funcTag" size="15" />&nbsp;<br><br>

      <label>等&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;级：</label>
      <input type="text" value="<c:out value="${funcManageForm.funcLevel}"/>" name="funcLevel" size="15" />&nbsp;<br><br>

      <label>权限字符串：</label>
      <input type="text" value="<c:out value="${funcManageForm.funcUrl}"/>" name="funcUrl" size="15" />&nbsp;<br><br>

      <label>图标字符串：</label>
      <input type="text" value="<c:out value="${funcManageForm.funcIcon}"/>" name="funcIcon" size="15" />&nbsp;<br><br>

      <label>优&nbsp;&nbsp;&nbsp;先&nbsp;&nbsp;&nbsp;级：</label>
      <input type="text" value="<c:out value="${funcManageForm.funcPriority}"/>" name="funcPriority" size="15" data-rule="优先级:required" />&nbsp;<br><br>

      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<button type="submit" class="btn-default" data-icon="save">保存</button>&nbsp;
    </div>
  </form>
</div>
<div class="bjui-pageFooter" id="pageFooter">

</div>

</div>

</body>
</html>
