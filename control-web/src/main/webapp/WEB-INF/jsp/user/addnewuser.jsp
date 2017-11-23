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
 <form id="pagerForm" name="userManageForm" data-toggle="validate" novalidate="novalidate" action="${pageContext.request.contextPath}/userManagement/addnew" method="post" data-callback="useRefreshAndClose">
    <div class="bjui-searchBar">
      <div class="form-group" style="margin: 20px 0 20px; ">
      <label class="control-label x85">账  号：</label>
      <input type="text" value="<c:out value="${userManageForm.username}"/>" name="username" size="15" data-rule="账号:required" />&nbsp;
      </div>
      <div class="form-group" style="margin: 20px 0 20px; ">
      <label class="control-label x85">邮  箱：</label>
      <input type="text" value="<c:out value="${userManageForm.usremail}"/>" name="usrEmail" size="15" data-rule="email:required" />&nbsp;
       </div>
       <div class="form-group" style="margin: 20px 0 20px; ">
      <label class="control-label x85">密  码：</label>
      <input type="password" value="<c:out value="${userManageForm.pass}"/>" name="pass" size="15" data-rule="密码:required" />&nbsp;
      </div>
       <div class="form-group" style="margin: 20px 0 20px; ">
      <label class="control-label x85">确认密码：</label>
      <input type="password" data-rule="required;match(pass)" name="" id="secpassword" value="" placeholder="确认新密码" size="15">
      </div>
      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<button type="submit" class="btn-default" data-icon="save">新增</button>&nbsp;
    </div>
  </form>
</div>
<div class="bjui-pageFooter" id="pageFooter">
  <div class="pages">
  </div>
</div>

</div>
</body>
<script type="text/javascript">
    function useRefreshAndClose(json) {
        $(this)
                .bjuiajax('ajaxDone',json)
                .dialog('closeCurrent',true)
               // .navtab('refresh')
    }
</script>
</html>
