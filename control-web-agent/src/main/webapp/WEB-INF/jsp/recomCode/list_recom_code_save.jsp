<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>注册码管理</title>
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
    <form id="pagerForm" name="recomCodeListForm" data-toggle="ajaxsearch" action="${pageContext.request.contextPath}/recomCode/query_recomCode_list" method="post" modelAttribute="recomCodeListForm">
        <input type="hidden" id="pageSize" name="pageSize" value="${recomCodeListForm.pageSize}">
        <input type="hidden" id="pageCurrent" name="pageCurrent" value="${recomCodeListForm.pageCurrent}">
        <div class="bjui-searchBar">
            <div class="row-inut">
                <label class="labelblock">机构号:</label>
                <select name="instId" id="instId" data-toggle="selectpicker"  data-live-search="true"
                        data-nextselect="#agentIdL"
                        data-refurl="${pageContext.request.contextPath}/comcon/select_agent_active?instId={value}"
                        >
                    <option style="width: 60px; display: inline-block" value="">-请选择-</option>
                    <c:forEach var="record" items="${instList}"
                               varStatus="status">
                        <option value="${record.instId}"
                                <c:if test="${record.instId == recomCodeListForm.instId}">selected</c:if> >${record.instName}</option>
                    </c:forEach>
                </select>
                <label class="labelblock">代理号:</label>
                <select name="agentId" id="agentIdL" data-toggle="selectpicker" data-live-search="true"
                        >
                    <option value="">-请选择-</option>
                </select>

                <label class="labelblock">用户号:</label>
                <input type="text" name="userId" id="userId" placeholder="输入用户号" size="15">&nbsp;

                <label class="labelblock">状态:</label>
                <select name="status" id="status" data-toggle="selectpicker">
                    <option value="">请选择</option>
                    <option value="N">新申请</option>
                    <option value="E">已激活</option>
                    <option  value="U">已使用</option>
                    <option  value="M">已过期</option>
                </select>&nbsp;
            </div>

            <div class="row-input">
                <label class="labelblock">注册批次:</label>
                <input type="text" name="batchId" id="batchId" placeholder="生成的批次号" size="15">&nbsp;
                <label class="labelblock">注册码:</label>
                <input type="text" name="recomCode" id="recomCode" placeholder="输入6位注册码" size="15">&nbsp;
            </div>

            <div class="row-input">
                <button type="submit" class="btn-default" data-icon="search">查询</button>
                <button type="button" class="btn-default" data-icon="home" data-toggle="navtab" data-id="navtab-recomCode-generate" data-title="生成注册码" href="${pageContext.request.contextPath}/recomCode/addPage">生成</button>
            </div>
            <div class="row-input">
                <%--<button type="button" class="btn-default" data-icon="home" data-toggle="navtab" data-id="navtab-recomCode-dispatch-batch" data-title="批量下拨注册码" href="${pageContext.request.contextPath}/recomCode/addDispatchBatchPage">批量下拨</button>--%>
                <a href="${pageContext.request.contextPath}/recomCode/dispatchBatchPage" data-icon="home" class="btn btn-primary" data-id="navtab-recomCode-dispatch-batch" data-toggle="dialog" data-width="600" data-height="400" data-id="dialog-normal" data-title="批量下拨注册码">批量下拨</a>
            </div>
        </div>
    </form>
</div>
<div class="bjui-pageContent tableContent">
    <table  class="table table-bordered table-hover table-striped table-top" data-toggle="tablefixed" data-width="100%" data-nowrap="true">
        <thead>
        <tr>
            <th align="center">批次号</th>
            <th align="center">序列号</th>
            <th align="center">注册码</th>
            <th align="center">代理号</th>
            <th align="center">绑定用户</th>
            <th align="center">状态</th>
            <th align="center">过期日期</th>
            <th align="center">创建人</th>
            <th align="center">创建日期</th>
            <th align="center">操作</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="record" items="${recomCodeListForm.pagination.list}" varStatus="status">
            <td align="center"><c:out value="${record.batchId}"/></td>
            <td align="center"><c:out value="${record.recomCodeSeq}"/></td>
            <td align="center"><c:out value="${record.recomCode}"/></td>
            <td align="center"><c:out value="${record.userCode}"/></td>
            <td align="center"><c:out value="${record.userId}"/></td>
            <td align="center">
                <c:if test="${record.status=='N'}">新增</c:if>
                <c:if test="${record.status=='E'}">激活</c:if>
                <c:if test="${record.status=='D'}">禁用</c:if>
                <c:if test="${record.status=='M'}">过期</c:if>
                <c:if test="${record.status=='U'}">已用</c:if>
            </td>
            <td align="center"><c:out value="${record.expireDate}"/></td>
            <td align="center"><c:out value="${record.createUser}"/></td>
            <td align="center"><c:out value="${record.createTime}"/></td>
            <td align="center">
                <%--<a href="${pageContext.request.contextPath}/recomCode/activate?recomCode=<c:out value="${record.recomCode}"/>" class="btn btn-green" data-toggle="doajax" <c:if test="${record.status=='E'} || ${record.status=='M'} || ${record.status=='U'}"> disabled=true </c:if>>激活</a>--%>
                <a href="${pageContext.request.contextPath}/recomCode/activate?recomCode=<c:out value="${record.recomCode}"/>" class="btn btn-green" data-toggle="doajax" <c:if test="${record.status=='E' || record.status=='M' || record.status=='U'}"> disabled=true </c:if>>激活</a>
                <a href="${pageContext.request.contextPath}/recomCode/disabled?recomCode=<c:out value="${record.recomCode}"/>" class="btn btn-blue" data-toggle="doajax" data-confirm-msg="确定？" <c:if test="${record.status=='D' || record.status=='U' }"> disabled=true </c:if>>禁用</a>
                <a href="${pageContext.request.contextPath}/recomCode/modifyPage?recomCode=<c:out value="${record.recomCode}"/>" class="btn btn-refresh" data-toggle="dialog" data-width="600" data-height="400" data-id="dialog-normal" data-title="注册码修改" <c:if test="${record.status=='U'}"> disabled=true </c:if>>修改</a>
                <a href="${pageContext.request.contextPath}/recomCode/dispatchPage?recomCode=<c:out value="${record.recomCode}"/>" class="btn btn-primary" data-toggle="dialog" data-width="600" data-height="400" data-id="dialog-normal" data-title="注册码下发" <c:if test="${record.status=='D' || record.status=='M' || record.status=='U' }"> disabled=true </c:if>>下发</a>
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
        <span> 条，共 <c:out value="${recomCodeListForm.pagination.itemCount}"/> 条</span>
    </div>
    <div class="pagination-box" data-toggle="pagination" data-total="<c:out value="${recomCodeListForm.pagination.itemCount}"/>" data-page-size="<c:out value="${recomCodeListForm.pageSize}"/>" data-page-current="<c:out value="${recomCodeListForm.pageCurrent}"/>">
    </div>
</div>
</body>
</html>
