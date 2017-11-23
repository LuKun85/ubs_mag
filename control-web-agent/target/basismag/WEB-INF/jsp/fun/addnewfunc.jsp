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
    <title>添加新的用户</title>
</head>
<body>
<div class="bjui-pageHeader">

</div>
<div class="bjui-pageContent tableContent">
 <form id="pagerForm" name="funcManageForm" data-toggle="validate" novalidate="novalidate" action="${pageContext.request.contextPath}/funcManagement/addnew" method="post">
    <div class="bjui-searchBar">
      <div class="form-group" style="margin: 20px 0 20px; ">
      <label class="control-label x85">资&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;源：</label>
      <input type="text" value="<c:out value="${funcManageForm.funcName}"/>" name="funcName" size="15" data-rule="资源名:required;" />&nbsp;
      </div>
      <div class="form-group" style="margin: 20px 0 20px; ">
      <label class="control-label x85">父资源&nbsp;ID&nbsp;：</label>
      <input type="text" value="<c:out value="${funcManageForm.funcFatherId}"/>" name="funcFatherId" size="15" />&nbsp;
      </div>
      <div class="form-group" style="margin: 20px 0 20px; ">
      <label class="control-label x85">描&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;述：</label>
      <input type="text" value="<c:out value="${funcManageForm.funcDesc}"/>" name="funcDesc" size="15" />&nbsp;
      </div>
      <div class="form-group" style="margin: 20px 0 20px; ">
      <label class="control-label x85">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注：</label>
      <input type="text" value="<c:out value="${funcManageForm.funcRemark}"/>" name="funcRemark" size="15" />&nbsp;
      </div>
      <div class="form-group" style="margin: 20px 0 20px; ">
      <label class="control-label x85">标&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;签：</label>
      <input type="text" value="<c:out value="${funcManageForm.funcTag}"/>" name="funcTag" size="15"  data-rule-maxlength="1"/>&nbsp;
      </div>
      <div class="form-group" style="margin: 20px 0 20px; ">
      <label class="control-label x85">等&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;级：</label>
      <input type="text" value="<c:out value="${funcManageForm.funcLevel}"/>" name="funcLevel" size="15" />&nbsp;
      </div>
      <div class="form-group" style="margin: 20px 0 20px; ">
      <label class="control-label x85">权限字符串：</label>
      <input type="text" value="<c:out value="${funcManageForm.funcUrl}"/>" name="funcUrl" size="15" />&nbsp;
      </div>
      <div class="form-group" style="margin: 20px 0 20px; ">
      <label class="control-label x85">图标字符串：</label>
      <input type="text" value="<c:out value="${funcManageForm.funcIcon}"/>" name="funcIcon" size="15" />&nbsp;
      </div>
      <div class="form-group" style="margin: 20px 0 20px; ">
      <label class="control-label x85">优&nbsp;&nbsp;&nbsp;先&nbsp;&nbsp;&nbsp;级：</label>
      <input type="text" value="<c:out value="${funcManageForm.funcPriority}"/>" name="funcPriority" size="15" data-rule="优先级:required" />&nbsp;
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
