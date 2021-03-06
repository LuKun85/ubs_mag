<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <script
            src="http://static.runoob.com/assets/jquery-validation-1.14.0/dist/jquery.validate.min.js"></script>
    <title>机构信息更改</title>
</head>
<body>
<div class="bjui-pageHeader"></div>
<div class="bjui-pageContent tableContent">


    <form id="pagerForm" name="instDetailForm" data-toggle="validate"
          novalidate="novalidate"
          action="${pageContext.request.contextPath}/inst/update_inst"
          method="post">

        <div style="margin:15px auto 0; width:96%;">
            <div class="row" style="padding: 0 8px;">

                <!-- 机构属性 -->
                <div class="col-md-12">
                    <div class="panel panel-default">
                        <div class="panel-heading"><h3 class="panel-title">机构${instDetailForm.instId}信息</h3></div>
                        <div class="panel-body">
                            <div class="row-input" style="margin: 20px 0 20px; width: 100%">
                                <div class="form-group col-md-4">
                                    <label class="control-label">机构简称：</label>
                                    <input type="text"
                                           name="instShortName" size="15" data-rule="机构简称:required;"
                                           value="${instDetailForm.instShortName}"/>&nbsp;
                                </div>

                                <div class="form-group col-md-4">
                                    <label class="control-label">机构全称：</label>
                                    <input type="text"
                                           name="instName" size="15" data-rule="机构全称:required;"
                                           value="${instDetailForm.instName}"/>&nbsp;
                                </div>

                                <div class="form-group col-md-4">
                                    <label class="control-label">营业执照：</label>
                                    <input type="text"
                                           name="businessLicense" size="15" data-rule="营业执照:required;"
                                           value="${instDetailForm.businessLicense}"/>&nbsp;
                                </div>
                            </div>

                            <div class="row-input" style="margin: 20px 0 20px;">
                                <div class="form-group col-md-4">
                                    <label class="control-label">机构类型：</label>
                                    <select
                                            name="instType" id="instType" data-rule="机构类型:required;"
                                            value="${instDetailForm.instType}"
                                            data-toggle="selectpicker">
                                        <option value="1">机构代理-</option>
                                        <%--<option value="2">商户代理</option>--%>
                                    </select>&nbsp;
                                </div>

                                <div class="form-group col-md-4">
                                    <label class="control-label">机构类别：</label>
                                    <select
                                            name="category" id="category" data-rule="机构类型:required;"
                                            value="${instDetailForm.category}"
                                            data-toggle="selectpicker">
                                        <option value="0">默认类别</option>
                                    </select>&nbsp;
                                </div>
                                <div class="form-group col-md-4">
                                    <label class="control-label">类别ID&nbsp;：</label>
                                    <%--<select--%>
                                            <%--name="categoryId" id="categoryId" data-rule="机构类型ID:required;"--%>
                                            <%--value="${instDetailForm.categoryId}"--%>
                                            <%--data-toggle="selectpicker">--%>
                                        <%--<option value="">请选择</option>--%>
                                        <%--<option value="P001">违章代缴</option>--%>
                                        <%--<option value="2">接口</option>--%>
                                    <%--</select>&nbsp;--%>
                                    <select name="categoryId" id="categoryId" data-toggle="selectpicker"  data-live-search="true" data-rule="机构类别ID:required;">
                                        <option style="width: 60px; display: inline-block" value="">-请选择-</option>
                                        <c:forEach var="record" items="${prodList}"
                                                   varStatus="status">
                                            <option value="${record.prodId}"
                                                    <c:if test="${record.prodId == instListForm.categoryId}">selected</c:if> >${record.prodName}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>

                            <div class="row-input" style="margin: 20px 0 20px;">
                                <label class="control-label x15"></label>
                                <input name="agentOk" id="agentOk" value="Y" data-toggle="icheck" data-label="允许代理"
                                       value="${instDetailForm.agentOk}"
                                       checked="" type="checkbox">
                                <label class="control-label x85">代理上限</label>
                                <input name="agentCountLimit" value="0" data-toggle="spinner" data-step="10" data-min="0"
                                       value="${instDetailForm.agentCountLimit}"
                                       size="5" type="text">
                            </div>

                            <div class="row-input" style="margin: 20px 0 20px;">
                                <label class="control-label x15"></label>
                                <input name="limitArea" id="limitArea" value="Y" data-toggle="icheck" data-label="地区保护"
                                       value="${instDetailForm.limitArea}"
                                       type="checkbox">
                                <label class="control-label x85">地区代码:</label>
                                <select
                                        name="limitAreaCode" id="limitAreaCode" data-rule="地区代码:required;"
                                        value="${instDetailForm.limitAreaCode}"
                                        data-toggle="selectpicker">
                                    <option value="0">默认-全国</option>
                                    <option value="1">上海地区</option>
                                    <option value="2">浙江地区</option>
                                    <option value="3">新疆地区</option>
                                </select>&nbsp;
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
                                    <label class="control-label">姓名：</label>
                                    <input type="text"
                                           name="legalPersonName" size="15" data-rule="法人姓名:required;"
                                           value="${instDetailForm.legalPersonName}"
                                           placeholder="请输入法人姓名"/>&nbsp;
                                </div>

                                <div class="form-group col-md-4">
                                    <label class="control-label">手机号：</label>
                                    <input type="text"
                                           name="legalPersonPhone" size="15" data-rule="法人手机号:required;"
                                           value="${instDetailForm.legalPersonPhone}"
                                           placeholder="请输入法人手机号"/>&nbsp;
                                </div>

                                <div class="form-group col-md-4">
                                    <label class="control-label">邮件：</label>
                                    <input type="text"
                                           name="legalPersonMail" size="20"
                                           value="${instDetailForm.legalPersonMail}"
                                           placeholder="请输入法人邮件"/>&nbsp;
                                </div>
                            </div>

                            <div class="row-input" style="margin: 20px 0 20px; width: 100%">
                                <div class="form-group col-md-4">
                                    <label class="control-label">证件类型：</label>
                                    <select
                                            name="legalPersonIdType" id="legalPersonIdType" data-rule="证件类型:required;"
                                            value="${instDetailForm.legalPersonIdType}"
                                            data-toggle="selectpicker">
                                        <option value="0">请选择</option>
                                        <option value="1">身份证</option>
                                        <option value="2">护照</option>
                                    </select>&nbsp;
                                </div>

                                <div class="form-group col-md-4">
                                    <label class="control-label">证件ID：</label>
                                    <input type="text"
                                           name="legalPersonId" size="20" data-rule="法人证件ID:required;"
                                           value="${instDetailForm.legalPersonId}"
                                           placeholder="请输入法人证件号"/>&nbsp;
                                </div>

                                <div class="form-group col-md-4">
                                    <label class="control-label">地址：</label>
                                    <input type="text"
                                           name="legalPersonAddress" size="20"
                                           value="${instDetailForm.legalPersonAddress}"
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
                                        <label class="control-label">姓名：</label>
                                        <input type="text"
                                               name="contactName" size="15"
                                               value="${instDetailForm.contactName}"
                                               placeholder="请输入联系人姓名"/>&nbsp;
                                    </div>

                                    <div class="form-group col-md-4">
                                        <label class="control-label">手机号：</label>
                                        <input type="text"
                                               name="contactPhone" size="15"
                                               value="${instDetailForm.contactPhone}"
                                               placeholder="请输入联系人手机号"/>&nbsp;
                                    </div>

                                    <div class="form-group col-md-4">
                                        <label class="control-label">邮件：</label>
                                        <input type="text"
                                               name="contactMail" size="20"
                                               value="${instDetailForm.contactMail}"
                                               placeholder="请输入联系人邮件"/>&nbsp;
                                    </div>
                                </div>

                                <div class="row-input" style="margin: 20px 0 20px; width: 100%">
                                    <div class="form-group col-md-4">
                                        <label class="control-label">证件类型：</label>
                                        <select
                                                name="contactIdType" id="contactIdType"
                                                value="${instDetailForm.contactIdType}"
                                                data-rule="证件类型:required;"
                                                data-toggle="selectpicker">
                                            <option value="0">请选择</option>
                                            <option value="1">身份证</option>
                                            <option value="2">护照</option>
                                        </select>&nbsp;
                                    </div>

                                    <div class="form-group col-md-4">
                                        <label class="control-label">证件ID：</label>
                                        <input type="text"
                                               name="contactCertId" size="20"
                                               value="${instDetailForm.contactCertId}"
                                               placeholder="请输入联系人证件号"/>&nbsp;
                                    </div>

                                    <div class="form-group col-md-4">
                                        <label class="control-label">地址：</label>
                                        <input type="text"
                                               name="contactAddress" size="20"
                                               value="${instDetailForm.contactAddress}"
                                               placeholder="请输入联系人地址"/>&nbsp;
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>


                <!-- 机构费率 -->
                <div class="col-md-12">
                    <div class="panel panel-default">
                        <div class="panel-heading"><h3 class="panel-title">机构费率</h3></div>
                        <div class="panel-body">
                            <div class="row-input" style="margin: 20px 0 20px; width: 100%">
                                <div class="row-input" style="margin: 20px 0 20px; width: 100%">
                                    <div class="form-group col-md-6">
                                        <label class="control-label">成本固定单笔费用：</label>
                                        <input type="text"
                                               name="defaultFeeFixed" size="15" data-rule="number;range[1~200] "
                                               value="${instDetailForm.defaultFeeFixed}"
                                               placeholder="单位笔/元"/>&nbsp;
                                    </div>

                                    <div class="form-group col-md-6">
                                        <label class="control-label">成本交易比例费率：</label>
                                        <input type="text"
                                               name="defaultFeeRate" size="15" data-rule="number;range[0~1] "
                                               value="${instDetailForm.defaultFeeRate}"
                                               placeholder="如:千5, 请输入0.5"/>%&nbsp;
                                    </div>
                                </div>
                                <div class="row-input" style="margin: 20px 0 20px; width: 100%">
                                    <div class="form-group col-md-6">
                                        <label class="control-label">实收固定单笔费用：</label>
                                        <input type="text"
                                               name="effectiveFeeFixed" size="15" data-rule="number;range[1~200] "
                                               value="${instDetailForm.effectiveFeeFixed}"
                                               placeholder="单位笔/元"/>&nbsp;
                                    </div>

                                    <div class="form-group col-md-6">
                                        <label class="control-label">实收交易比例费率：</label>
                                        <input type="text"
                                               name="effectiveFeeRate" size="15" data-rule="number;range[0~1] "
                                               value="${instDetailForm.effectiveFeeRate}"
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
