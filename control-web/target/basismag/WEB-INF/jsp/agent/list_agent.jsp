<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>代理列表</title>
</head>

<style>

    .labelblock{
        display: inline-block;
        width: 5rem;
    }

    .selectlength{
        display: inline-block;
        width: 60px;
    }

</style>

<body>
<div class="bjui-pageHeader">
    <form id="pagerForm" name="agentListForm" data-toggle="ajaxsearch" action="${pageContext.request.contextPath}/agent/query_agent_list" method="get" modelAttribute="agentListForm">
        <input type="hidden" id="pageSize" name="pageSize" value="${agentListForm.pageSize}">
        <input type="hidden" id="pageCurrent" name="pageCurrent" value="${agentListForm.pageCurrent}">
        <div class="bjui-searchBar">
            <div class="row-inut">

                <label class="labelblock">机构号:</label>
                <select name="instId" id="instId" data-toggle="selectpicker"    data-live-search="true"
                        data-nextselect="#agentIdF"
                        data-refurl="${pageContext.request.contextPath}/comcon/select_agent_active?instId={value}"
                        style="width: 134px">
                    <option value="">--机构--</option>
                    <c:forEach var="record" items="${instList}"
                               varStatus="status">
                        <option value="${record.instId}"
                                <c:if test="${record.instId == agentListForm.instId}">selected</c:if> >${record.instName}</option>
                    </c:forEach>
                </select>

                <label class="labelblock">代理号:</label>
                <select name="agentId" id="agentIdF" data-toggle="selectpicker"  data-live-search="true">
                    <option value="">--代理--</option>
                <%--<option style="width: 60px; display: inline-block" value="">-请选择-</option>--%>
                    <%--<c:forEach var="record" items="${agentList}"--%>
                               <%--varStatus="status">--%>
                        <%--<option value="${record.agentId}"--%>
                                <%--<c:if test="${record.agentId == agentListForm.agentId}">selected</c:if> >${record.agentName}</option>--%>
                    <%--</c:forEach>--%>
                </select>

                <label class="labelblock">代理名称:</label>
                <input type="text" name="agentName" size="15"/>

                <label class="labelblock">状态:</label>
                <select name="status" id="status" data-toggle="selectpicker">
                    <option value="">请选择</option>
                    <option value="N">新申请</option>
                    <option value="E">已激活</option>
                    <option  value="D">被禁用</option>
                    <option  value="C">已注销</option>
                </select>&nbsp;
            </div>

            <div class="row-input">
                <button type="submit" class="btn-green" data-icon="save" >查询</button>&nbsp;
            </div>
        </div>
    </form>
</div>
<div class="bjui-pageContent tableContent">
    <table  class="table table-bordered table-hover table-striped table-top" data-toggle="tablefixed" data-width="100%" data-nowrap="true">
        <thead>
        <tr>
            <th align="center">代理号</th>
            <th align="center">代理名称</th>
            <th align="center">状态</th>
            <th align="center">创建人</th>
            <th align="center">创建日期</th>
            <th align="center">操作</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="record" items="${agentListForm.pagination.list}" varStatus="status">
            <td align="center"><c:out value="${record.agentId}"/></td>
            <td align="center"><c:out value="${record.agentName}"/></td>
            <td align="center">
                <c:if test="${record.status=='N'}">新增</c:if>
                <c:if test="${record.status=='E'}">激活</c:if>
                <c:if test="${record.status=='D'}">禁用</c:if>
                <c:if test="${record.status=='C'}">注销</c:if>
            </td>
            <td align="center"><c:out value="${record.createUser}"/></td>
            <td align="center"><c:out value="${record.createTime}"/></td>
            <td align="center">
                <a href="${pageContext.request.contextPath}/agent/activate_agent?agentId=<c:out value="${record.agentId}"/>" class="btn btn-green" data-toggle="doajax" <c:if test="${record.status=='E'}"> disabled=true </c:if>>激活</a>
                <a href="${pageContext.request.contextPath}/agent/disable_agent?agentId=<c:out value="${record.agentId}"/>" class="btn btn-blue" data-toggle="doajax" data-confirm-msg="确定？" <c:if test="${record.status=='D'}"> disabled=true </c:if>>禁用</a>
                <a href="${pageContext.request.contextPath}/agent/cancel_agent?agentId=<c:out value="${record.agentId}"/>" class="btn btn-red" data-toggle="doajax" data-confirm-msg="确定？" <c:if test="${record.status=='C'}"> disabled=true </c:if>>注销</a>
                <a href="${pageContext.request.contextPath}/agent/detailPage?agentId=<c:out value="${record.agentId}"/>" class="btn btn-primary" data-toggle="navtab" data-id="navtab-agent-detail" data-title="代理详细信息">详细</a>
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
        <span> 条，共 <c:out value="${agentListForm.pagination.itemCount}"/> 条</span>
    </div>
    <div class="pagination-box" data-toggle="pagination" data-total="<c:out value="${agentListForm.pagination.itemCount}"/>" data-page-size="<c:out value="${agentListForm.pageSize}"/>" data-page-current="<c:out value="${agentListForm.pageCurrent}"/>">
    </div>
</div>

</body>

</html>
