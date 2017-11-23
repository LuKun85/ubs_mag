<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>下发注册码</title>
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
               data-toggle="validate" action="recomCode/dispatch_recomCode_batch"
               novalidate="novalidate" modelAttribute="recomCodeDispatchBatchForm" id="recomCodeDispatchBatchForm" name="recomCodeDispatchBatchForm">
        <table class="table table-condensed table-hover" width="100%">
            <div class="row-inut col-md-12" style="margin: 10px 0 10px; width: 100%">
                <div class=" col-md-6">
                    <label class="labellength8">选择机构:</label>
                    <select name="instId" id="instId" data-toggle="selectpicker"    data-live-search="true"
                            data-nextselect="#agentIdD"
                    <%--<!-- status = E 代表enable 激活的状态, 这里查找的该机构下的所有的激活状态的代理列表-->--%>
                    data-refurl="${pageContext.request.contextPath}/comcon/select_agent_active?instId={value}&status=E">
                        <option value="">-机构--</option>
                        <c:forEach var="record" items="${instList}"
                                   varStatus="status">
                            <option value="${record.instId}"
                                    <c:if test="${record.instId == recomCodeDispatchBatchForm.instId}">selected</c:if> >${record.instName}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class=" col-md-6">
                    <label class="labellength8">选择代理:</label>
                    <select name="agentId" id="agentIdD" data-toggle="selectpicker" data-live-search="true" data-emptytxt="--所属代理--"
                            data-nextselect="#toAgentId"
                            <%--这里的dataurl是选择所有机构的直接下级代理, 并且激活状态--%>
                            data-refurl="${pageContext.request.contextPath}/comcon/select_downagent_active?agentId={value}&status=E">
                            style="width: 134px">
                        <option value="all">--所属代理--</option>
                    </select>
                </div>
            </div>

            <div class="row-inut col-md-12" style="margin: 10px 0 10px; width: 100%">
                <div class=" col-md-6">
                    <label class="labellength8 ">已用个数:</label>
                    <input type="text"
                           name="recomCodeUsedCount" id="recomCodeUsedCount" size="19" disabled="true"/>个&nbsp;
                </div>
                <div class=" col-md-6">
                    <label class="labellength8 ">可用个数:</label>
                    <input type="text"
                           name="recomCodeAvailableCount" id="recomCodeAvailableCount" size="19" disabled="true"/>个&nbsp;
                </div>
            </div>

            <div class="row-inut col-md-12" style="margin: 10px 0 10px; width: 100%">
                <div class=" col-md-6">
                    <label class="labellength8 ">下发个数:</label>
                    <input type="text"
                           name="dispatchCount" id="dispatchCount" size="19" placeholder="请输入下发个数" data-rule="下发个数:required;">个&nbsp;
                </div>
            </div>

            <div class="row-inut col-md-12" style="margin: 10px 0 10px; width: 100%">
                <div class=" col-md-6">
                    <label class="labellength8 ">下发代理:</label>
                    <select name="toAgentId" id="toAgentId" data-toggle="selectpicker" data-live-search="true" style="width: 134px" data-emptytxt="--下发代理--">
                        <option value="all">--请选择--</option>
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
