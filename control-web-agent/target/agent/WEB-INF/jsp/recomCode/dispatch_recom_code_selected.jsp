<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>下发选中注册码</title>
</head>


<style>

    .labellength88{
        display: inline-block;
        width: 8rem;
    }

</style>

<body>
<div class="bjui-pageContent">
    <form:form class="nice-validator n-red" data-alertmsg="false"
               data-toggle="validate" action="recomCode/dispatch_recomCode_selected"
               novalidate="novalidate" modelAttribute="recomCodeDispatchSelectedForm" id="recomCodeDispatchSelectedForm" name="recomCodeDispatchSelectedForm">
        <table class="table table-condensed table-hover" width="100%">

            <input type="hidden" id="toDispatchList" name="toDispatchList" value="${recomCodeDispatchSelectedForm.toDispatchList}">
            <input type="hidden" id="toDispatchRecomCodes" name="toDispatchRecomCodes" value="${recomCodeDispatchSelectedForm.toDispatchRecomCodes}">

            <div class="row-inut col-md-12" style="margin: 10px 0 10px; width: 100%">
                <div class=" col-md-6">
                    <label class="labellength8 ">下发代理:</label>
                    <select name="toAgentId" id="toAgentId" data-toggle="selectpicker"  data-live-search="true">
                        <option style="width: 60px; display: inline-block" value="">-请选择-</option>
                        <c:forEach var="record" items="${toAgentList}"
                                   varStatus="status">
                            <option value="${record.agentId}"
                                    <c:if test="${record.agentId == recomCodeDispatchSelectedForm.agentId}">selected</c:if> >${record.agentName}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>

            <div class="row-inut col-md-12" style="margin: 10px 0 10px; width: 100%">
                <div class=" col-md-6">
                    <button type="submit" class="btn-default" data-icon="save" data-confirm-msg="确定下拨？">下拨</button>
                </div>
            </div>


        </table>
    </form:form>
</div>
<div class="bjui-pageFooter">
</div>
</body>
</html>
