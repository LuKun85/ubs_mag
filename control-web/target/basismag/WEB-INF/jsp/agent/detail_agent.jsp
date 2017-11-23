<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <script
            src="http://static.runoob.com/assets/jquery-validation-1.14.0/dist/jquery.validate.min.js"></script>
    <title>代理详细信息</title>
</head>

<style>

    .labelblock{
        display: inline-block;
        width: 5rem;
        text-align: left;
    }

</style>

<body>
<div class="bjui-pageHeader"></div>
<div class="bjui-pageContent tableContent">


    <form id="pagerForm" name="agentDetailForm" data-toggle="validate"
          novalidate="novalidate"
          action="${pageContext.request.contextPath}/agent/update_agent"
          method="post">

        <div style="margin:15px auto 0; width:96%;">
            <div class="row" style="padding: 0 8px;">

                <!-- 代理属性 -->
                <div class="col-md-12">
                    <div class="panel panel-default">
                        <div class="panel-heading"><h3 class="panel-title">代理${agentDetailForm.agentId}信息</h3></div>
                        <div class="panel-body">
                            <div class="row-input" style="margin: 20px 0 20px; width: 100%">
                                <div class="form-group col-md-4">
                                    <label class="control-label labelblock">代理简称：</label>
                                    <input type="text"
                                           name="agentShortName" size="15" data-rule="代理简称:required;"
                                           value="${agentDetailForm.agentShortName}"/>&nbsp;
                                </div>

                                <div class="form-group col-md-4">
                                    <label class="control-label labelblock">代理全称：</label>
                                    <input type="text"
                                           name="agentName" size="15" data-rule="代理全称:required;"
                                           value="${agentDetailForm.agentName}"/>&nbsp;
                                </div>

                                <div class="form-group col-md-4">
                                    <label class="control-label labelblock">营业执照：</label>
                                    <input type="text"
                                           name="businessLicense" size="15" data-rule="营业执照:required;"
                                           value="${agentDetailForm.businessLicense}"/>&nbsp;
                                </div>
                            </div>

                            <div class="row-input" style="margin: 20px 0 20px;">
                                <div class="form-group col-md-4">
                                    <label class="control-label labelblock">代理类型：</label>
                                    <select disabled = "true"
                                            name="agentType" id="agentType" data-rule="代理类型:required;"
                                            value="${agentDetailForm.agentType}"
                                            data-toggle="selectpicker">
                                        <option value="1">-请选择-</option>
                                        <option value="1" <c:if test="${agentDetailForm.agentType == '1'}">selected</c:if>>发展代理</option>
                                        <option value="0" <c:if test="${agentDetailForm.agentType == '0'}">selected</c:if>>直属代理</option>
                                    </select>&nbsp;
                                </div>

                                <div class="form-group col-md-4">
                                    <label class="control-label labelblock">代理类别：</label>
                                    <select name="category" id="category" data-rule="代理类型:required;"
                                            data-toggle="selectpicker" data-live-search="true"
                                            data-nextselect="#categoryIdM"
                                            data-refurl="${pageContext.request.contextPath}/comcon/select_catagory_ids?catagory={value}">
                                        <option value="all">-请选择-</option>
                                        <option value="0" <c:if test="${agentDetailForm.category == '0'}">selected</c:if>>基础业务</option>
                                    </select>&nbsp;
                                </div>
                                <div class="form-group col-md-4">
                                    <label class="control-label labelblock">类别ID&nbsp;：</label>
                                    <select name="categoryId" id="categoryIdM" data-toggle="selectpicker"  data-live-search="true" data-rule="代理类别ID:required;">
                                        <option style="width: 60px; display: inline-block" value="">-请选择-</option>
                                    </select>
                                </div>
                            </div>

                            <div class="row-input" style="margin: 20px 0 20px;">
                                <label class="control-label x15"></label>
                                <input name="agentOk" id="agentOk" value="Y" data-toggle="icheck" data-label="允许代理"
                                       value="${agentDetailForm.agentOk}"
                                       checked="" type="checkbox">
                                <label class="control-label x85">代理上限</label>
                                <input name="agentCountLimit" value="0" data-toggle="spinner" data-step="10" data-min="0"
                                       value="${agentDetailForm.agentCountLimit}"
                                       size="5" type="text">
                            </div>
                        </div>
                    </div>
                </div>

                <!-- 法人信息 -->
                <div class="col-md-12">
                    <div class="panel panel-default">
                        <div class="panel-heading"><h3 class="panel-title">法人信息</h3></div>
                        <div class="panel-body">
                            <div class="row-input" style="margin: 20px 0 20px; width: 100%">
                                <div class="form-group col-md-4">
                                    <label class="control-label labelblock">姓名：</label>
                                    <input type="text"
                                           name="legalPersonName" size="15" data-rule="法人姓名:required;"
                                           value="${agentDetailForm.legalPersonName}"
                                           placeholder="请输入法人姓名"/>&nbsp;
                                </div>

                                <div class="form-group col-md-4">
                                    <label class="control-label labelblock">手机号：</label>
                                    <input type="text"
                                           name="legalPersonPhone" size="15" data-rule="法人手机号:required; mobile"
                                           value="${agentDetailForm.legalPersonPhone}"
                                           placeholder="请输入法人手机号"/>&nbsp;
                                </div>

                                <div class="form-group col-md-4">
                                    <label class="control-label labelblock">邮件：</label>
                                    <input type="text"
                                           name="legalPersonMail" size="20" data-rule="邮箱:required; email"
                                           value="${agentDetailForm.legalPersonMail}"
                                           placeholder="请输入法人邮件"/>&nbsp;
                                </div>
                            </div>

                            <div class="row-input" style="margin: 20px 0 20px; width: 100%">
                                <div class="form-group col-md-4">
                                    <label class="control-label labelblock">证件类型：</label>
                                    <select
                                            name="legalPersonIdType" id="legalPersonIdType" data-rule="证件类型:required;"
                                            value="${agentDetailForm.legalPersonIdType}"
                                            data-toggle="selectpicker">
                                        <option value="0">请选择</option>
                                        <option value="1">身份证</option>
                                        <option value="2">护照</option>
                                    </select>&nbsp;
                                </div>

                                <div class="form-group col-md-4">
                                    <label class="control-label labelblock">证件ID：</label>
                                    <input type="text"
                                           name="legalPersonId" size="20" data-rule="法人证件ID:required;"
                                           value="${agentDetailForm.legalPersonId}"
                                           placeholder="请输入法人证件号"/>&nbsp;
                                </div>

                                <div class="form-group col-md-4">
                                    <label class="control-label labelblock">地址：</label>
                                    <input type="text"
                                           name="legalPersonAddress" size="20"
                                           value="${agentDetailForm.legalPersonAddress}"
                                           placeholder="请输入法人地址"/>&nbsp;
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- 联系人信息 -->
                <div class="col-md-12">
                    <div class="panel panel-default">
                        <div class="panel-heading"><h3 class="panel-title">联系人信息</h3></div>
                        <div class="panel-body">
                            <div class="row-input" style="margin: 20px 0 20px; width: 100%">
                                <div class="row-input" style="margin: 20px 0 20px; width: 100%">
                                    <div class="form-group col-md-4">
                                        <label class="control-label labelblock">姓名：</label>
                                        <input type="text"
                                               name="contactName" size="15"
                                               value="${agentDetailForm.contactName}"
                                               placeholder="请输入联系人姓名"/>&nbsp;
                                    </div>

                                    <div class="form-group col-md-4">
                                        <label class="control-label labelblock">手机号：</label>
                                        <input type="text"
                                               name="contactPhone" size="15" data-rule="手机号:required; mobile"
                                               value="${agentDetailForm.contactPhone}"
                                               placeholder="请输入联系人手机号"/>&nbsp;
                                    </div>

                                    <div class="form-group col-md-4">
                                        <label class="control-label labelblock">邮件：</label>
                                        <input type="text"
                                               name="contactMail" size="20"  data-rule="邮箱:required; email"
                                               value="${agentDetailForm.contactMail}"
                                               placeholder="请输入联系人邮件"/>&nbsp;
                                    </div>
                                </div>

                                <div class="row-input" style="margin: 20px 0 20px; width: 100%">
                                    <div class="form-group col-md-4">
                                        <label class="control-label labelblock">证件类型：</label>
                                        <select
                                                name="contactIdType" id="contactIdType"
                                                value="${agentDetailForm.contactIdType}"
                                                data-rule="证件类型:required;"
                                                data-toggle="selectpicker">
                                            <option value="0">请选择</option>
                                            <option value="1">身份证</option>
                                            <option value="2">护照</option>
                                        </select>&nbsp;
                                    </div>

                                    <div class="form-group col-md-4">
                                        <label class="control-label labelblock">证件ID：</label>
                                        <input type="text"
                                               name="contactCertId" size="20"
                                               value="${agentDetailForm.contactCertId}"
                                               placeholder="请输入联系人证件号"/>&nbsp;
                                    </div>

                                    <div class="form-group col-md-4">
                                        <label class="control-label labelblock">地址：</label>
                                        <input type="text"
                                               name="contactAddress" size="20"
                                               value="${agentDetailForm.contactAddress}"
                                               placeholder="请输入联系人地址"/>&nbsp;
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>


                <!-- 代理费率 -->
                <div class="col-md-12">
                    <div class="panel panel-default">
                        <div class="panel-heading"><h3 class="panel-title">代理费率</h3></div>
                        <div class="panel-body">
                            <div class="row-input" style="margin: 20px 0 20px; width: 100%">
                                <div class="row-input" style="margin: 20px 0 20px; width: 100%">
                                    <div class="form-group col-md-6">
                                        <label class="control-label">成本固定单笔费用：</label>
                                        <input type="text"
                                               name="defaultFeeFixed" size="15" data-rule="number;range[1~200] "
                                               value="${agentDetailForm.defaultFeeFixed}"
                                               placeholder="单位笔/元"/>&nbsp;
                                    </div>

                                    <div class="form-group col-md-6">
                                        <label class="control-label">成本交易比例费率：</label>
                                        <input type="text"
                                               name="defaultFeeRate" size="15" data-rule="number;range[0~1] "
                                               value="${agentDetailForm.defaultFeeRate}"
                                               placeholder="如:千5, 请输入0.5"/>%&nbsp;
                                    </div>
                                </div>
                                <div class="row-input" style="margin: 20px 0 20px; width: 100%">
                                    <div class="form-group col-md-6">
                                        <label class="control-label">实收固定单笔费用：</label>
                                        <input type="text"
                                               name="effectiveFeeFixed" size="15" data-rule="number;range[1~200] "
                                               value="${agentDetailForm.effectiveFeeFixed}"
                                               placeholder="单位笔/元"/>&nbsp;
                                    </div>

                                    <div class="form-group col-md-6">
                                        <label class="control-label">实收交易比例费率：</label>
                                        <input type="text"
                                               name="effectiveFeeRate" size="15" data-rule="number;range[0~1] "
                                               value="${agentDetailForm.effectiveFeeRate}"
                                               placeholder="如:千5, 请输入0.5"/>%&nbsp;
                                    </div>
                                </div>
                        </div>
                    </div>
                </div>

                <div class="col-md-12" style="margin: 20px 0 20px; ">
                    <button type="submit" class="btn-default" data-icon="save" style="float: right">更新</button>&nbsp;
                </div>
            </div>
        </div>
    </form>
</div>
<%--<div class="bjui-pageFooter" id="pageFooter">--%>
    <%--<div class="pages"></div>--%>
<%--</div>--%>
</body>
</html>
