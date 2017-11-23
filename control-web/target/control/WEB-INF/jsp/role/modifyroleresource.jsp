<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<html>
<head>
    <title>角色资源你设置</title>
</head>
<body>
<div class="bjui-pageHeader">

    <div class="bjui-searchBar">
        <label>角 色：</label>
        <label><c:out value="${roleFuncForm.roleName}"/></label>&nbsp;
        <label>角色状态：</label>
        <label><c:out value="${roleFuncForm.roleDisableTag}"/></label>&nbsp;
        <a href="${pageContext.request.contextPath}/roleManagement/roleenable?id=${roleFuncForm.roleId}" class="btn btn-red" data-toggle="doajax" data-confirm-msg="确定启用吗？" >启用</a>&nbsp;
        <a href="${pageContext.request.contextPath}/roleManagement/roledisable?id=${roleFuncForm.roleId}" class="btn btn-red" data-toggle="doajax" data-confirm-msg="确定禁用吗？" >禁用</a>&nbsp;

        <!-- <button type="submit" class="btn-default" data-icon="search">查询</button>&nbsp;-->
    </div>
</div>
<div class="bjui-pageContent">
    <form:form class="nice-validator n-red" data-alertmsg="true"
               data-toggle="validate" action="${pageContext.request.contextPath}/roleManagement/addauthority"
               novalidate="novalidate" modelAttribute="rout" data-callback="roleRefreshAndClose">
        <input type="hidden" name="roleId" id="roleId" value="${roleFuncForm.roleId}">
        <input type="hidden" name="pageSize" value="${roleFuncForm.pageSize}">
        <input type="hidden" name="pageCurrent" value="${roleFuncForm.pageCurrent}">
        <input type="hidden" name="roleAuthoryIds" id="authorized_yes_id_his" />
        <table>
            <tr>
                <td><label>已授权</label><br> <select
                        style="width: 100px; height: 250px;" id="authorized_yes"
                        multiple="multiple" size="10" /></td>
                <td><input type="button" onclick="to_no()" value="&rarr;"><br>
                    <br> <br> <br> <input type="button"
                                          onclick="to_yes()" value="&larr;"></td>
                <td><label>未授权</label><br> <select
                        style="width: 100px; height: 250px;" id="authorized_no"
                        multiple="multiple" size="10" /></td>
            </tr>
        </table>

    </form:form>
</div>
<div class="bjui-pageFooter">
    <ul>
        <li><button type="button" class="btn-close">关闭</button></li>
        <li><button type="submit" class="btn-default">保存</button></li>
    </ul>
</div>
<script type="text/javascript">
    var role_authorized_all = eval(${role_authorized_all});
    var role_authorized_yes = new Array();
    var role_authorized_no = new Array();
    var keyMap = {"key":"funcId","value":"funcName"};

    init_data_by_role();
    addSelectOption("authorized_yes",role_authorized_yes);
    addSelectOption("authorized_no",role_authorized_no);
    ref_libs();

    function init_data_by_role() {
        for(var index=0;index<role_authorized_all.length;index++){
            var authorize = role_authorized_all[index];
            if(authorize.checked == "已授权"){
                var authorize_obj = {"key":authorize[keyMap.key],"value":authorize[keyMap.value]};
                role_authorized_yes.push(authorize_obj);
            }else{
                var authorize_obj = {"key":authorize[keyMap.key],"value":authorize[keyMap.value]};
                role_authorized_no.push(authorize_obj);
            }
        }
    }

    function to_no() {
        var $options = $('#authorized_yes option:selected'); ////获取当前选中的项

        for(var i=0;i<$options.length;i++){
            var dom = $options.get(i);
            var index = role_authorized_yes.indexOf(dom.value);
            role_authorized_yes.slice(index,1);
        }
        var $remove = $options.remove();
        $remove.appendTo("#authorized_no");
        ref_libs();
    }

    function to_yes() {
        var $options = $('#authorized_no option:selected'); ////获取当前选中的项

        for(var i=0;i<$options.length;i++){
            var dom = $options.get(i);
            var index = role_authorized_no.indexOf(dom.value);
            role_authorized_no.slice(index,1);
        }
        var $remove = $options.remove();
        $remove.appendTo("#authorized_yes");
        ref_libs();
    }

    function ref_libs() {
        var authorized_yes_id = $('#authorized_yes option');
        var ids = [];
        for(var keys in authorized_yes_id){
            var yes_id = authorized_yes_id[keys].value;
            if(typeof yes_id == "string"){
                ids.push(yes_id);
            }
        }
        //$('#authorized_yes_id_his').appendTo(ids);
        $('#authorized_yes_id_his').attr("value",ids);
    }

    function addSelectOption(select_id,option_array){
        var select_dom =  document.getElementById(select_id);
        for ( i = 0; i < option_array.length; i++){
            if(typeof option_array[i] === 'undefined' | typeof option_array[i] === 'function')
                continue;
            var _option = document.createElement("option");
            _option.value = option_array[i].key;
            _option.text = option_array[i].value;
            select_dom.appendChild(_option);
        }
    }
    Array.prototype.indexOf = function(key) {
        for (var i = 0; i < this.length; i++) {
            if (this[i][keyMap.key] == key) return i;
        }
        return -1;
    }
    
    function roleRefreshAndClose(json) {
        $(this)
                .bjuiajax('ajaxDone',json)
                .dialog('closeCurrent',true)
               // .navtab('refresh')
    }
</script>
</body>
</html>
