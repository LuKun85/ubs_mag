<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>注册码管理</title>
</head>

<style>

    .labelblock {
        display: inline-block;
        width: 5rem;
    }

    .selectlength {
        display: inline-block;
        width: 60px;
    }

    .table .tableWidth100 {
        width: 100%;
    }

</style>

<body>
<div class="bjui-pageHeader">
    <form id="pagerForm" name="recomCodeListForm" data-toggle="ajaxsearch"
          <%--action="${pageContext.request.contextPath}/recomCode/query_recomCode_list" method="post"--%>
          data-options="{searchDatagrid:$.CurrentNavtab.find('#datagrid-recomcode-filter')}"
          modelAttribute="recomCodeListForm">
        <input type="hidden" id="pageSize" name="pageSize" value="${recomCodeListForm.pageSize}">
        <input type="hidden" id="pageCurrent" name="pageCurrent" value="${recomCodeListForm.pageCurrent}">
        <div class="bjui-searchBar">
            <div class="row-inut">
                <label class="labelblock">机构号:</label>
                <select name="instId" id="instId" data-toggle="selectpicker" data-live-search="true"
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
                    <option value="U">已使用</option>
                    <option value="M">已过期</option>
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
                <button type="button" class="btn-default" data-icon="home" data-toggle="navtab"
                        data-id="navtab-recomCode-generate" data-title="生成注册码"
                        href="${pageContext.request.contextPath}/recomCode/addPage">生成
                </button>
            </div>
            <div class="row-input">
                <%--<button type="button" class="btn-default" data-icon="home" data-toggle="navtab" data-id="navtab-recomCode-dispatch-batch" data-title="批量下拨注册码" href="${pageContext.request.contextPath}/recomCode/addDispatchBatchPage">批量下拨</button>--%>
                <a href="${pageContext.request.contextPath}/recomCode/dispatchBatchPage" data-icon="home"
                   class="btn btn-primary" data-id="navtab-recomCode-dispatch-batch" data-toggle="dialog"
                   data-width="600" data-height="400" data-id="dialog-normal" data-title="批量下拨注册码">批量下拨</a>
            </div>
        </div>
    </form>
</div>
<div class="bjui-pageContent">
    <table class="table table-bordered" id="datagrid-recomcode-filter" style="width: 100%" data-toggle="datagrid" data-nowrap="true"
           data-options="{
             height: '100%',
             width: '100%',
             dataUrl: '${pageContext.request.contextPath}/recomCode/query_recomCode_list',
             local: 'remote',
             filterThead:false,
             paging: {pageSize:${recomCodeListForm.pageSize}, pageCurrent:${recomCodeListForm.pageCurrent}},
             showCheckboxcol: true,
             showToolbar: true,
             editUrl: '${pageContext.request.contextPath}/recomCode/dispatchBatchPage?recomCode={recomCode}',
             toolbarItem: 'enable, disable, dispatch,export',
             linenumberAll: true}">
        <thead>
        <tr>
            <th  data-options="{name:'batchId', align:'center'}">批次号</th>
            <th  data-options="{name:'recomCodeSeq', align:'center'}">序列号</th>
            <th  data-options="{name:'recomCode', align:'center'}">注册码</th>
            <th  data-options="{name:'userCode', align:'center'}">代理号</th>
            <th  data-options="{name:'userId', align:'center'}">绑定用户</th>
            <th  data-options="{name:'status', align:'center'}">状态</th>
            <th  data-options="{name:'expireDate', align:'center'}">过期日期</th>
            <th  data-options="{name:'createUser', align:'center'}">创建人</th>
            <th  data-options="{name:'createTime', align:'center'}">创建日期</th>
            <th  data-options="{name:'1', align:'center'}">操作</th>
        </tr>
        </thead>
        <input id="sumAmountForAll" type="hidden" />
    </table>
</div>
</body>
</html>
