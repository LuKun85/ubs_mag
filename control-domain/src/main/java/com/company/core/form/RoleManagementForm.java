package com.company.core.form;

import java.util.List;

/**
 * Created by fireWorks on 2016/2/2.
 */
public class RoleManagementForm extends BaseForm {

    private String roleId;

    private String roleName;

    private String roleDisableTag;

    private String roleRemark;

    public List<String> getRoleAuthoryIds() {
        return roleAuthoryIds;
    }

    public void setRoleAuthoryIds(List<String> roleAuthoryIds) {
        this.roleAuthoryIds = roleAuthoryIds;
    }

    private List<String> roleAuthoryIds;

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId == null ? null : roleId.trim();
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName == null ? null : roleName.trim();
    }

    public String getRoleDisableTag() {
        return roleDisableTag;
    }

    public void setRoleDisableTag(String roleDisableTag) {
        this.roleDisableTag = roleDisableTag == null ? null : roleDisableTag.trim();
    }

    public String getRoleRemark() {
        return roleRemark;
    }

    public void setRoleRemark(String roleRemark) {
        this.roleRemark = roleRemark == null ? null : roleRemark.trim();
    }


}
