<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <script
            src="http://static.runoob.com/assets/jquery-validation-1.14.0/dist/jquery.validate.min.js"></script>
    <title>新增注册码</title>
</head>


<body>
<div class="bjui-pageHeader"></div>
<div class="bjui-pageContent tableContent">
    <form id="pagerForm" name="recomCodeForm" data-toggle="validate"
          novalidate="novalidate"
          action="${pageContext.request.contextPath}/recomCode/add_new_recomCode"
          method="post">

        <div style="margin:15px auto 0; width:96%;">
            <div class="row" style="padding: 0 8px;">

                <!-- 机构属性 -->
                <div class="col-md-12">
                    <div class="panel panel-default">
                        <div class="panel-heading"><h3 class="panel-title">注册码信息</h3></div>
                        <div class="panel-body">
                            <div class="row-input" style="margin: 20px 0 20px; width: 100%">
                                <div class="form-group col-md-6">
                                    <label class="control-label">选择机构：</label>
                                    <select name="instId" id="instId" data-toggle="selectpicker"    data-live-search="true"
                                            data-nextselect="#agentIdA"
                                            <%--<!-- status = E 代表enable 激活的状态-->--%>
                                            data-refurl="${pageContext.request.contextPath}/comcon/select_agent_active?instId={value}&level=1&status=E"
                                            style="width: 134px">
                                        <option value="">--机构--</option>
                                        <c:forEach var="record" items="${instList}"
                                                   varStatus="status">
                                            <option value="${record.instId}"
                                                    <c:if test="${record.instId == recomCodeForm.instId}">selected</c:if> >${record.instName}</option>
                                        </c:forEach>
                                    </select>
                                </div>

                                <div class="form-group col-md-6">
                                    <label class="control-label">选择代理：</label>
                                    <select name="agentId" id="agentIdA" data-toggle="selectpicker" data-live-search="true"
                                            style="width: 134px">
                                        <option value="">--代理--</option>
                                    </select>
                                </div>
                            </div>

                            <div class="row-input" style="margin: 20px 0 20px;">
                                <div class="form-group col-md-4">
                                    <label class="control-label">注册码类型：</label>
                                    <select
                                            name="recomCodeType" id="recomCodeType"
                                            data-toggle="selectpicker">
                                        <option value="0">默认-类型-</option>
                                        <option value="1">预留类型1</option>
                                        <option value="2">预留类型2</option>
                                    </select>&nbsp;
                                </div>

                                <div class="form-group col-md-4">
                                    <label class="control-label">机构类别：</label>
                                    <select
                                            name="category" id="category" data-rule="机构类型:required;"
                                            data-toggle="selectpicker">
                                        <option value="0">默认-类型</option>
                                        <option value="1">预留类别1</option>
                                        <option value="2">预留类型2</option>
                                    </select>&nbsp;
                                </div>
                                <div class="form-group col-md-4">
                                    <label class="control-label">类别ID&nbsp;：</label>
                                    <select
                                            name="categoryId" id="categoryId" data-rule="机构类型ID:required;"
                                            data-toggle="selectpicker">
                                        <option value="0">请选择</option>
                                        <option value="1">服务</option>
                                        <option value="2">接口</option>
                                    </select>&nbsp;
                                </div>
                            </div>

                            <div class="row-input" style="margin: 20px 0 20px;">
                                <div class="form-group col-md-4" style="padding-left: 15px">
                                    <label class="control-label">是否加密</label>
                                    <input name="pwdRequired" id="pwdRequired" value="Y" data-toggle="icheck"
                                           checked="" type="checkbox" onclick="clickCheckBox()">
                                    <%--<input type="text"--%>
                                           <%--name="recomCodePwd" id="recomCodePwd" size="10"--%>
                                           <%--placeholder="注册码密码"/>&nbsp;--%>
                                </div>
                                <div class="form-group col-md-4" style="padding-left: 15px">
                                    <label class="control-label">过期天数:</label>
                                    <input type="text"
                                           name="expireDays" id="expireDays" size="10"
                                           placeholder="180"/>天&nbsp;
                                </div>
                                <div class="form-group col-md-4" style="padding-left: 15px">
                                    <label class="control-label">单价</label>
                                    <input type="text"
                                           name="price" id="price" size="10"
                                           placeholder="单位:元"/>&nbsp;
                                </div>
                            </div>

                            <div class="row-input" style="margin: 20px 0 20px;">
                                <div class="form-group" style="padding-left: 15px">
                                    <label class="control-label">生成个数:</label>
                                    <input type="text"
                                           name="recomCodeCount" id="recomCodeCount" size="10"
                                           placeholder="注册码个数"/>&nbsp;
                                </div>
                            </div>

                        </div>
                    </div>
                </div>

                <div class="col-md-12" style="margin: 20px 0 20px; ">
                    <button type="submit" class="btn-default" data-icon="save" style="float: right">提交</button>&nbsp;
                </div>
            </div>
        </div>
    </form>
</div>

</body>

<script type="text/javascript">

//    $("#pwdRequired").onclick();

    function clickCheckBox() {
        alert("0");
    }

</script>


</html>
