<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>下发注册码</title>
</head>


<style>

    .labelblock{
        display: inline-block;
        width: 8rem;
    }

</style>

<body>
<div class="bjui-pageContent">
    <form:form class="nice-validator n-red" data-alertmsg="false"
               data-toggle="validate" action="recomCode/dispatch_recomCode"
               novalidate="novalidate" modelAttribute="recomCodeDispatchForm" id="recomCodeDispatchForm" name="recomCodeDispatchForm">
        <table class="table table-condensed table-hover" width="100%">
            <div class="row-inut col-md-12" style="margin: 10px 0 10px; width: 100%">
                <div class=" col-md-6">
                    <label class="labellength">所属机构:</label>
                    <input type="text" name="instId" id="instId" value="${recomCodeDispatchForm.instId}" size="15" disabled="true">&nbsp;
                </div>
                <div class=" col-md-6">
                    <label class="labellength">机构名称:</label>
                    <input type="text" name="instName" id="instName" value="${recomCodeDispatchForm.instName}" size="15" disabled="true">&nbsp;
                </div>
            </div>

            <div class="row-inut col-md-12" style="margin: 10px 0 10px; width: 100%">
                <div class=" col-md-6">
                    <label class="labellength ">所属代理:</label>
                    <input type="text" name="agentId" id="agentId" value="${recomCodeDispatchForm.agentId}" size="15" disabled="true">&nbsp;
                </div>
                <div class=" col-md-6">
                    <label class="labellength ">代理名称:</label>
                    <input type="text" name="agentName" id="agentName" value="${recomCodeDispatchForm.agentName}" size="15" disabled="true">&nbsp;
                </div>
            </div>

            <div class="row-inut col-md-12" style="margin: 10px 0 10px; width: 100%">
                <div class=" col-md-6">
                    <label class="labellength ">下发代理:</label>
                    <select name="toAgentId" id="toAgentId" data-toggle="selectpicker" data-live-search="true" style="width: 134px">
                        <option value="">-机构--</option>
                        <c:forEach var="record" items="${toAgentList}"
                                   varStatus="status">
                            <option value="${record.agentId}"
                                    <c:if test="${record.agentId == recomCodeDispatchForm.toAgentId}">selected</c:if> >${record.agentId}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>

            <div class="row-inut col-md-12" style="margin: 10px 0 10px; width: 100%">
                <div class=" col-md-6">
                    <button type="submit" class="btn-default" data-icon="save">下拨</button>
                </div>
            </div>


        </table>
    </form:form>
</div>
<div class="bjui-pageFooter">
</div>
</body>
</html>
