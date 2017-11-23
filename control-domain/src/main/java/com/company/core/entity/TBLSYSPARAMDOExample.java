package com.company.core.entity;

import java.util.ArrayList;
import java.util.List;

public class TBLSYSPARAMDOExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public TBLSYSPARAMDOExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andParamCodeIsNull() {
            addCriterion("PARAM_CODE is null");
            return (Criteria) this;
        }

        public Criteria andParamCodeIsNotNull() {
            addCriterion("PARAM_CODE is not null");
            return (Criteria) this;
        }

        public Criteria andParamCodeEqualTo(String value) {
            addCriterion("PARAM_CODE =", value, "paramCode");
            return (Criteria) this;
        }

        public Criteria andParamCodeNotEqualTo(String value) {
            addCriterion("PARAM_CODE <>", value, "paramCode");
            return (Criteria) this;
        }

        public Criteria andParamCodeGreaterThan(String value) {
            addCriterion("PARAM_CODE >", value, "paramCode");
            return (Criteria) this;
        }

        public Criteria andParamCodeGreaterThanOrEqualTo(String value) {
            addCriterion("PARAM_CODE >=", value, "paramCode");
            return (Criteria) this;
        }

        public Criteria andParamCodeLessThan(String value) {
            addCriterion("PARAM_CODE <", value, "paramCode");
            return (Criteria) this;
        }

        public Criteria andParamCodeLessThanOrEqualTo(String value) {
            addCriterion("PARAM_CODE <=", value, "paramCode");
            return (Criteria) this;
        }

        public Criteria andParamCodeLike(String value) {
            addCriterion("PARAM_CODE like", value, "paramCode");
            return (Criteria) this;
        }

        public Criteria andParamCodeNotLike(String value) {
            addCriterion("PARAM_CODE not like", value, "paramCode");
            return (Criteria) this;
        }

        public Criteria andParamCodeIn(List<String> values) {
            addCriterion("PARAM_CODE in", values, "paramCode");
            return (Criteria) this;
        }

        public Criteria andParamCodeNotIn(List<String> values) {
            addCriterion("PARAM_CODE not in", values, "paramCode");
            return (Criteria) this;
        }

        public Criteria andParamCodeBetween(String value1, String value2) {
            addCriterion("PARAM_CODE between", value1, value2, "paramCode");
            return (Criteria) this;
        }

        public Criteria andParamCodeNotBetween(String value1, String value2) {
            addCriterion("PARAM_CODE not between", value1, value2, "paramCode");
            return (Criteria) this;
        }

        public Criteria andVersionIsNull() {
            addCriterion("VERSION is null");
            return (Criteria) this;
        }

        public Criteria andVersionIsNotNull() {
            addCriterion("VERSION is not null");
            return (Criteria) this;
        }

        public Criteria andVersionEqualTo(Long value) {
            addCriterion("VERSION =", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionNotEqualTo(Long value) {
            addCriterion("VERSION <>", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionGreaterThan(Long value) {
            addCriterion("VERSION >", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionGreaterThanOrEqualTo(Long value) {
            addCriterion("VERSION >=", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionLessThan(Long value) {
            addCriterion("VERSION <", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionLessThanOrEqualTo(Long value) {
            addCriterion("VERSION <=", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionIn(List<Long> values) {
            addCriterion("VERSION in", values, "version");
            return (Criteria) this;
        }

        public Criteria andVersionNotIn(List<Long> values) {
            addCriterion("VERSION not in", values, "version");
            return (Criteria) this;
        }

        public Criteria andVersionBetween(Long value1, Long value2) {
            addCriterion("VERSION between", value1, value2, "version");
            return (Criteria) this;
        }

        public Criteria andVersionNotBetween(Long value1, Long value2) {
            addCriterion("VERSION not between", value1, value2, "version");
            return (Criteria) this;
        }

        public Criteria andParamValueIsNull() {
            addCriterion("PARAM_VALUE is null");
            return (Criteria) this;
        }

        public Criteria andParamValueIsNotNull() {
            addCriterion("PARAM_VALUE is not null");
            return (Criteria) this;
        }

        public Criteria andParamValueEqualTo(String value) {
            addCriterion("PARAM_VALUE =", value, "paramValue");
            return (Criteria) this;
        }

        public Criteria andParamValueNotEqualTo(String value) {
            addCriterion("PARAM_VALUE <>", value, "paramValue");
            return (Criteria) this;
        }

        public Criteria andParamValueGreaterThan(String value) {
            addCriterion("PARAM_VALUE >", value, "paramValue");
            return (Criteria) this;
        }

        public Criteria andParamValueGreaterThanOrEqualTo(String value) {
            addCriterion("PARAM_VALUE >=", value, "paramValue");
            return (Criteria) this;
        }

        public Criteria andParamValueLessThan(String value) {
            addCriterion("PARAM_VALUE <", value, "paramValue");
            return (Criteria) this;
        }

        public Criteria andParamValueLessThanOrEqualTo(String value) {
            addCriterion("PARAM_VALUE <=", value, "paramValue");
            return (Criteria) this;
        }

        public Criteria andParamValueLike(String value) {
            addCriterion("PARAM_VALUE like", value, "paramValue");
            return (Criteria) this;
        }

        public Criteria andParamValueNotLike(String value) {
            addCriterion("PARAM_VALUE not like", value, "paramValue");
            return (Criteria) this;
        }

        public Criteria andParamValueIn(List<String> values) {
            addCriterion("PARAM_VALUE in", values, "paramValue");
            return (Criteria) this;
        }

        public Criteria andParamValueNotIn(List<String> values) {
            addCriterion("PARAM_VALUE not in", values, "paramValue");
            return (Criteria) this;
        }

        public Criteria andParamValueBetween(String value1, String value2) {
            addCriterion("PARAM_VALUE between", value1, value2, "paramValue");
            return (Criteria) this;
        }

        public Criteria andParamValueNotBetween(String value1, String value2) {
            addCriterion("PARAM_VALUE not between", value1, value2, "paramValue");
            return (Criteria) this;
        }

        public Criteria andParamCheckIsNull() {
            addCriterion("PARAM_CHECK is null");
            return (Criteria) this;
        }

        public Criteria andParamCheckIsNotNull() {
            addCriterion("PARAM_CHECK is not null");
            return (Criteria) this;
        }

        public Criteria andParamCheckEqualTo(String value) {
            addCriterion("PARAM_CHECK =", value, "paramCheck");
            return (Criteria) this;
        }

        public Criteria andParamCheckNotEqualTo(String value) {
            addCriterion("PARAM_CHECK <>", value, "paramCheck");
            return (Criteria) this;
        }

        public Criteria andParamCheckGreaterThan(String value) {
            addCriterion("PARAM_CHECK >", value, "paramCheck");
            return (Criteria) this;
        }

        public Criteria andParamCheckGreaterThanOrEqualTo(String value) {
            addCriterion("PARAM_CHECK >=", value, "paramCheck");
            return (Criteria) this;
        }

        public Criteria andParamCheckLessThan(String value) {
            addCriterion("PARAM_CHECK <", value, "paramCheck");
            return (Criteria) this;
        }

        public Criteria andParamCheckLessThanOrEqualTo(String value) {
            addCriterion("PARAM_CHECK <=", value, "paramCheck");
            return (Criteria) this;
        }

        public Criteria andParamCheckLike(String value) {
            addCriterion("PARAM_CHECK like", value, "paramCheck");
            return (Criteria) this;
        }

        public Criteria andParamCheckNotLike(String value) {
            addCriterion("PARAM_CHECK not like", value, "paramCheck");
            return (Criteria) this;
        }

        public Criteria andParamCheckIn(List<String> values) {
            addCriterion("PARAM_CHECK in", values, "paramCheck");
            return (Criteria) this;
        }

        public Criteria andParamCheckNotIn(List<String> values) {
            addCriterion("PARAM_CHECK not in", values, "paramCheck");
            return (Criteria) this;
        }

        public Criteria andParamCheckBetween(String value1, String value2) {
            addCriterion("PARAM_CHECK between", value1, value2, "paramCheck");
            return (Criteria) this;
        }

        public Criteria andParamCheckNotBetween(String value1, String value2) {
            addCriterion("PARAM_CHECK not between", value1, value2, "paramCheck");
            return (Criteria) this;
        }

        public Criteria andParamRuleIsNull() {
            addCriterion("PARAM_RULE is null");
            return (Criteria) this;
        }

        public Criteria andParamRuleIsNotNull() {
            addCriterion("PARAM_RULE is not null");
            return (Criteria) this;
        }

        public Criteria andParamRuleEqualTo(String value) {
            addCriterion("PARAM_RULE =", value, "paramRule");
            return (Criteria) this;
        }

        public Criteria andParamRuleNotEqualTo(String value) {
            addCriterion("PARAM_RULE <>", value, "paramRule");
            return (Criteria) this;
        }

        public Criteria andParamRuleGreaterThan(String value) {
            addCriterion("PARAM_RULE >", value, "paramRule");
            return (Criteria) this;
        }

        public Criteria andParamRuleGreaterThanOrEqualTo(String value) {
            addCriterion("PARAM_RULE >=", value, "paramRule");
            return (Criteria) this;
        }

        public Criteria andParamRuleLessThan(String value) {
            addCriterion("PARAM_RULE <", value, "paramRule");
            return (Criteria) this;
        }

        public Criteria andParamRuleLessThanOrEqualTo(String value) {
            addCriterion("PARAM_RULE <=", value, "paramRule");
            return (Criteria) this;
        }

        public Criteria andParamRuleLike(String value) {
            addCriterion("PARAM_RULE like", value, "paramRule");
            return (Criteria) this;
        }

        public Criteria andParamRuleNotLike(String value) {
            addCriterion("PARAM_RULE not like", value, "paramRule");
            return (Criteria) this;
        }

        public Criteria andParamRuleIn(List<String> values) {
            addCriterion("PARAM_RULE in", values, "paramRule");
            return (Criteria) this;
        }

        public Criteria andParamRuleNotIn(List<String> values) {
            addCriterion("PARAM_RULE not in", values, "paramRule");
            return (Criteria) this;
        }

        public Criteria andParamRuleBetween(String value1, String value2) {
            addCriterion("PARAM_RULE between", value1, value2, "paramRule");
            return (Criteria) this;
        }

        public Criteria andParamRuleNotBetween(String value1, String value2) {
            addCriterion("PARAM_RULE not between", value1, value2, "paramRule");
            return (Criteria) this;
        }

        public Criteria andParamModifyFlagIsNull() {
            addCriterion("PARAM_MODIFY_FLAG is null");
            return (Criteria) this;
        }

        public Criteria andParamModifyFlagIsNotNull() {
            addCriterion("PARAM_MODIFY_FLAG is not null");
            return (Criteria) this;
        }

        public Criteria andParamModifyFlagEqualTo(String value) {
            addCriterion("PARAM_MODIFY_FLAG =", value, "paramModifyFlag");
            return (Criteria) this;
        }

        public Criteria andParamModifyFlagNotEqualTo(String value) {
            addCriterion("PARAM_MODIFY_FLAG <>", value, "paramModifyFlag");
            return (Criteria) this;
        }

        public Criteria andParamModifyFlagGreaterThan(String value) {
            addCriterion("PARAM_MODIFY_FLAG >", value, "paramModifyFlag");
            return (Criteria) this;
        }

        public Criteria andParamModifyFlagGreaterThanOrEqualTo(String value) {
            addCriterion("PARAM_MODIFY_FLAG >=", value, "paramModifyFlag");
            return (Criteria) this;
        }

        public Criteria andParamModifyFlagLessThan(String value) {
            addCriterion("PARAM_MODIFY_FLAG <", value, "paramModifyFlag");
            return (Criteria) this;
        }

        public Criteria andParamModifyFlagLessThanOrEqualTo(String value) {
            addCriterion("PARAM_MODIFY_FLAG <=", value, "paramModifyFlag");
            return (Criteria) this;
        }

        public Criteria andParamModifyFlagLike(String value) {
            addCriterion("PARAM_MODIFY_FLAG like", value, "paramModifyFlag");
            return (Criteria) this;
        }

        public Criteria andParamModifyFlagNotLike(String value) {
            addCriterion("PARAM_MODIFY_FLAG not like", value, "paramModifyFlag");
            return (Criteria) this;
        }

        public Criteria andParamModifyFlagIn(List<String> values) {
            addCriterion("PARAM_MODIFY_FLAG in", values, "paramModifyFlag");
            return (Criteria) this;
        }

        public Criteria andParamModifyFlagNotIn(List<String> values) {
            addCriterion("PARAM_MODIFY_FLAG not in", values, "paramModifyFlag");
            return (Criteria) this;
        }

        public Criteria andParamModifyFlagBetween(String value1, String value2) {
            addCriterion("PARAM_MODIFY_FLAG between", value1, value2, "paramModifyFlag");
            return (Criteria) this;
        }

        public Criteria andParamModifyFlagNotBetween(String value1, String value2) {
            addCriterion("PARAM_MODIFY_FLAG not between", value1, value2, "paramModifyFlag");
            return (Criteria) this;
        }

        public Criteria andParamDescIsNull() {
            addCriterion("PARAM_DESC is null");
            return (Criteria) this;
        }

        public Criteria andParamDescIsNotNull() {
            addCriterion("PARAM_DESC is not null");
            return (Criteria) this;
        }

        public Criteria andParamDescEqualTo(String value) {
            addCriterion("PARAM_DESC =", value, "paramDesc");
            return (Criteria) this;
        }

        public Criteria andParamDescNotEqualTo(String value) {
            addCriterion("PARAM_DESC <>", value, "paramDesc");
            return (Criteria) this;
        }

        public Criteria andParamDescGreaterThan(String value) {
            addCriterion("PARAM_DESC >", value, "paramDesc");
            return (Criteria) this;
        }

        public Criteria andParamDescGreaterThanOrEqualTo(String value) {
            addCriterion("PARAM_DESC >=", value, "paramDesc");
            return (Criteria) this;
        }

        public Criteria andParamDescLessThan(String value) {
            addCriterion("PARAM_DESC <", value, "paramDesc");
            return (Criteria) this;
        }

        public Criteria andParamDescLessThanOrEqualTo(String value) {
            addCriterion("PARAM_DESC <=", value, "paramDesc");
            return (Criteria) this;
        }

        public Criteria andParamDescLike(String value) {
            addCriterion("PARAM_DESC like", value, "paramDesc");
            return (Criteria) this;
        }

        public Criteria andParamDescNotLike(String value) {
            addCriterion("PARAM_DESC not like", value, "paramDesc");
            return (Criteria) this;
        }

        public Criteria andParamDescIn(List<String> values) {
            addCriterion("PARAM_DESC in", values, "paramDesc");
            return (Criteria) this;
        }

        public Criteria andParamDescNotIn(List<String> values) {
            addCriterion("PARAM_DESC not in", values, "paramDesc");
            return (Criteria) this;
        }

        public Criteria andParamDescBetween(String value1, String value2) {
            addCriterion("PARAM_DESC between", value1, value2, "paramDesc");
            return (Criteria) this;
        }

        public Criteria andParamDescNotBetween(String value1, String value2) {
            addCriterion("PARAM_DESC not between", value1, value2, "paramDesc");
            return (Criteria) this;
        }

        public Criteria andParamRemarkIsNull() {
            addCriterion("PARAM_REMARK is null");
            return (Criteria) this;
        }

        public Criteria andParamRemarkIsNotNull() {
            addCriterion("PARAM_REMARK is not null");
            return (Criteria) this;
        }

        public Criteria andParamRemarkEqualTo(String value) {
            addCriterion("PARAM_REMARK =", value, "paramRemark");
            return (Criteria) this;
        }

        public Criteria andParamRemarkNotEqualTo(String value) {
            addCriterion("PARAM_REMARK <>", value, "paramRemark");
            return (Criteria) this;
        }

        public Criteria andParamRemarkGreaterThan(String value) {
            addCriterion("PARAM_REMARK >", value, "paramRemark");
            return (Criteria) this;
        }

        public Criteria andParamRemarkGreaterThanOrEqualTo(String value) {
            addCriterion("PARAM_REMARK >=", value, "paramRemark");
            return (Criteria) this;
        }

        public Criteria andParamRemarkLessThan(String value) {
            addCriterion("PARAM_REMARK <", value, "paramRemark");
            return (Criteria) this;
        }

        public Criteria andParamRemarkLessThanOrEqualTo(String value) {
            addCriterion("PARAM_REMARK <=", value, "paramRemark");
            return (Criteria) this;
        }

        public Criteria andParamRemarkLike(String value) {
            addCriterion("PARAM_REMARK like", value, "paramRemark");
            return (Criteria) this;
        }

        public Criteria andParamRemarkNotLike(String value) {
            addCriterion("PARAM_REMARK not like", value, "paramRemark");
            return (Criteria) this;
        }

        public Criteria andParamRemarkIn(List<String> values) {
            addCriterion("PARAM_REMARK in", values, "paramRemark");
            return (Criteria) this;
        }

        public Criteria andParamRemarkNotIn(List<String> values) {
            addCriterion("PARAM_REMARK not in", values, "paramRemark");
            return (Criteria) this;
        }

        public Criteria andParamRemarkBetween(String value1, String value2) {
            addCriterion("PARAM_REMARK between", value1, value2, "paramRemark");
            return (Criteria) this;
        }

        public Criteria andParamRemarkNotBetween(String value1, String value2) {
            addCriterion("PARAM_REMARK not between", value1, value2, "paramRemark");
            return (Criteria) this;
        }

        public Criteria andLastUpdateOrgIsNull() {
            addCriterion("LAST_UPDATE_ORG is null");
            return (Criteria) this;
        }

        public Criteria andLastUpdateOrgIsNotNull() {
            addCriterion("LAST_UPDATE_ORG is not null");
            return (Criteria) this;
        }

        public Criteria andLastUpdateOrgEqualTo(String value) {
            addCriterion("LAST_UPDATE_ORG =", value, "lastUpdateOrg");
            return (Criteria) this;
        }

        public Criteria andLastUpdateOrgNotEqualTo(String value) {
            addCriterion("LAST_UPDATE_ORG <>", value, "lastUpdateOrg");
            return (Criteria) this;
        }

        public Criteria andLastUpdateOrgGreaterThan(String value) {
            addCriterion("LAST_UPDATE_ORG >", value, "lastUpdateOrg");
            return (Criteria) this;
        }

        public Criteria andLastUpdateOrgGreaterThanOrEqualTo(String value) {
            addCriterion("LAST_UPDATE_ORG >=", value, "lastUpdateOrg");
            return (Criteria) this;
        }

        public Criteria andLastUpdateOrgLessThan(String value) {
            addCriterion("LAST_UPDATE_ORG <", value, "lastUpdateOrg");
            return (Criteria) this;
        }

        public Criteria andLastUpdateOrgLessThanOrEqualTo(String value) {
            addCriterion("LAST_UPDATE_ORG <=", value, "lastUpdateOrg");
            return (Criteria) this;
        }

        public Criteria andLastUpdateOrgLike(String value) {
            addCriterion("LAST_UPDATE_ORG like", value, "lastUpdateOrg");
            return (Criteria) this;
        }

        public Criteria andLastUpdateOrgNotLike(String value) {
            addCriterion("LAST_UPDATE_ORG not like", value, "lastUpdateOrg");
            return (Criteria) this;
        }

        public Criteria andLastUpdateOrgIn(List<String> values) {
            addCriterion("LAST_UPDATE_ORG in", values, "lastUpdateOrg");
            return (Criteria) this;
        }

        public Criteria andLastUpdateOrgNotIn(List<String> values) {
            addCriterion("LAST_UPDATE_ORG not in", values, "lastUpdateOrg");
            return (Criteria) this;
        }

        public Criteria andLastUpdateOrgBetween(String value1, String value2) {
            addCriterion("LAST_UPDATE_ORG between", value1, value2, "lastUpdateOrg");
            return (Criteria) this;
        }

        public Criteria andLastUpdateOrgNotBetween(String value1, String value2) {
            addCriterion("LAST_UPDATE_ORG not between", value1, value2, "lastUpdateOrg");
            return (Criteria) this;
        }

        public Criteria andLastUpdateAccIsNull() {
            addCriterion("LAST_UPDATE_ACC is null");
            return (Criteria) this;
        }

        public Criteria andLastUpdateAccIsNotNull() {
            addCriterion("LAST_UPDATE_ACC is not null");
            return (Criteria) this;
        }

        public Criteria andLastUpdateAccEqualTo(String value) {
            addCriterion("LAST_UPDATE_ACC =", value, "lastUpdateAcc");
            return (Criteria) this;
        }

        public Criteria andLastUpdateAccNotEqualTo(String value) {
            addCriterion("LAST_UPDATE_ACC <>", value, "lastUpdateAcc");
            return (Criteria) this;
        }

        public Criteria andLastUpdateAccGreaterThan(String value) {
            addCriterion("LAST_UPDATE_ACC >", value, "lastUpdateAcc");
            return (Criteria) this;
        }

        public Criteria andLastUpdateAccGreaterThanOrEqualTo(String value) {
            addCriterion("LAST_UPDATE_ACC >=", value, "lastUpdateAcc");
            return (Criteria) this;
        }

        public Criteria andLastUpdateAccLessThan(String value) {
            addCriterion("LAST_UPDATE_ACC <", value, "lastUpdateAcc");
            return (Criteria) this;
        }

        public Criteria andLastUpdateAccLessThanOrEqualTo(String value) {
            addCriterion("LAST_UPDATE_ACC <=", value, "lastUpdateAcc");
            return (Criteria) this;
        }

        public Criteria andLastUpdateAccLike(String value) {
            addCriterion("LAST_UPDATE_ACC like", value, "lastUpdateAcc");
            return (Criteria) this;
        }

        public Criteria andLastUpdateAccNotLike(String value) {
            addCriterion("LAST_UPDATE_ACC not like", value, "lastUpdateAcc");
            return (Criteria) this;
        }

        public Criteria andLastUpdateAccIn(List<String> values) {
            addCriterion("LAST_UPDATE_ACC in", values, "lastUpdateAcc");
            return (Criteria) this;
        }

        public Criteria andLastUpdateAccNotIn(List<String> values) {
            addCriterion("LAST_UPDATE_ACC not in", values, "lastUpdateAcc");
            return (Criteria) this;
        }

        public Criteria andLastUpdateAccBetween(String value1, String value2) {
            addCriterion("LAST_UPDATE_ACC between", value1, value2, "lastUpdateAcc");
            return (Criteria) this;
        }

        public Criteria andLastUpdateAccNotBetween(String value1, String value2) {
            addCriterion("LAST_UPDATE_ACC not between", value1, value2, "lastUpdateAcc");
            return (Criteria) this;
        }

        public Criteria andLastUpdateTimeIsNull() {
            addCriterion("LAST_UPDATE_TIME is null");
            return (Criteria) this;
        }

        public Criteria andLastUpdateTimeIsNotNull() {
            addCriterion("LAST_UPDATE_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andLastUpdateTimeEqualTo(String value) {
            addCriterion("LAST_UPDATE_TIME =", value, "lastUpdateTime");
            return (Criteria) this;
        }

        public Criteria andLastUpdateTimeNotEqualTo(String value) {
            addCriterion("LAST_UPDATE_TIME <>", value, "lastUpdateTime");
            return (Criteria) this;
        }

        public Criteria andLastUpdateTimeGreaterThan(String value) {
            addCriterion("LAST_UPDATE_TIME >", value, "lastUpdateTime");
            return (Criteria) this;
        }

        public Criteria andLastUpdateTimeGreaterThanOrEqualTo(String value) {
            addCriterion("LAST_UPDATE_TIME >=", value, "lastUpdateTime");
            return (Criteria) this;
        }

        public Criteria andLastUpdateTimeLessThan(String value) {
            addCriterion("LAST_UPDATE_TIME <", value, "lastUpdateTime");
            return (Criteria) this;
        }

        public Criteria andLastUpdateTimeLessThanOrEqualTo(String value) {
            addCriterion("LAST_UPDATE_TIME <=", value, "lastUpdateTime");
            return (Criteria) this;
        }

        public Criteria andLastUpdateTimeLike(String value) {
            addCriterion("LAST_UPDATE_TIME like", value, "lastUpdateTime");
            return (Criteria) this;
        }

        public Criteria andLastUpdateTimeNotLike(String value) {
            addCriterion("LAST_UPDATE_TIME not like", value, "lastUpdateTime");
            return (Criteria) this;
        }

        public Criteria andLastUpdateTimeIn(List<String> values) {
            addCriterion("LAST_UPDATE_TIME in", values, "lastUpdateTime");
            return (Criteria) this;
        }

        public Criteria andLastUpdateTimeNotIn(List<String> values) {
            addCriterion("LAST_UPDATE_TIME not in", values, "lastUpdateTime");
            return (Criteria) this;
        }

        public Criteria andLastUpdateTimeBetween(String value1, String value2) {
            addCriterion("LAST_UPDATE_TIME between", value1, value2, "lastUpdateTime");
            return (Criteria) this;
        }

        public Criteria andLastUpdateTimeNotBetween(String value1, String value2) {
            addCriterion("LAST_UPDATE_TIME not between", value1, value2, "lastUpdateTime");
            return (Criteria) this;
        }

        public Criteria andReserved1IsNull() {
            addCriterion("RESERVED1 is null");
            return (Criteria) this;
        }

        public Criteria andReserved1IsNotNull() {
            addCriterion("RESERVED1 is not null");
            return (Criteria) this;
        }

        public Criteria andReserved1EqualTo(String value) {
            addCriterion("RESERVED1 =", value, "reserved1");
            return (Criteria) this;
        }

        public Criteria andReserved1NotEqualTo(String value) {
            addCriterion("RESERVED1 <>", value, "reserved1");
            return (Criteria) this;
        }

        public Criteria andReserved1GreaterThan(String value) {
            addCriterion("RESERVED1 >", value, "reserved1");
            return (Criteria) this;
        }

        public Criteria andReserved1GreaterThanOrEqualTo(String value) {
            addCriterion("RESERVED1 >=", value, "reserved1");
            return (Criteria) this;
        }

        public Criteria andReserved1LessThan(String value) {
            addCriterion("RESERVED1 <", value, "reserved1");
            return (Criteria) this;
        }

        public Criteria andReserved1LessThanOrEqualTo(String value) {
            addCriterion("RESERVED1 <=", value, "reserved1");
            return (Criteria) this;
        }

        public Criteria andReserved1Like(String value) {
            addCriterion("RESERVED1 like", value, "reserved1");
            return (Criteria) this;
        }

        public Criteria andReserved1NotLike(String value) {
            addCriterion("RESERVED1 not like", value, "reserved1");
            return (Criteria) this;
        }

        public Criteria andReserved1In(List<String> values) {
            addCriterion("RESERVED1 in", values, "reserved1");
            return (Criteria) this;
        }

        public Criteria andReserved1NotIn(List<String> values) {
            addCriterion("RESERVED1 not in", values, "reserved1");
            return (Criteria) this;
        }

        public Criteria andReserved1Between(String value1, String value2) {
            addCriterion("RESERVED1 between", value1, value2, "reserved1");
            return (Criteria) this;
        }

        public Criteria andReserved1NotBetween(String value1, String value2) {
            addCriterion("RESERVED1 not between", value1, value2, "reserved1");
            return (Criteria) this;
        }

        public Criteria andReserved2IsNull() {
            addCriterion("RESERVED2 is null");
            return (Criteria) this;
        }

        public Criteria andReserved2IsNotNull() {
            addCriterion("RESERVED2 is not null");
            return (Criteria) this;
        }

        public Criteria andReserved2EqualTo(String value) {
            addCriterion("RESERVED2 =", value, "reserved2");
            return (Criteria) this;
        }

        public Criteria andReserved2NotEqualTo(String value) {
            addCriterion("RESERVED2 <>", value, "reserved2");
            return (Criteria) this;
        }

        public Criteria andReserved2GreaterThan(String value) {
            addCriterion("RESERVED2 >", value, "reserved2");
            return (Criteria) this;
        }

        public Criteria andReserved2GreaterThanOrEqualTo(String value) {
            addCriterion("RESERVED2 >=", value, "reserved2");
            return (Criteria) this;
        }

        public Criteria andReserved2LessThan(String value) {
            addCriterion("RESERVED2 <", value, "reserved2");
            return (Criteria) this;
        }

        public Criteria andReserved2LessThanOrEqualTo(String value) {
            addCriterion("RESERVED2 <=", value, "reserved2");
            return (Criteria) this;
        }

        public Criteria andReserved2Like(String value) {
            addCriterion("RESERVED2 like", value, "reserved2");
            return (Criteria) this;
        }

        public Criteria andReserved2NotLike(String value) {
            addCriterion("RESERVED2 not like", value, "reserved2");
            return (Criteria) this;
        }

        public Criteria andReserved2In(List<String> values) {
            addCriterion("RESERVED2 in", values, "reserved2");
            return (Criteria) this;
        }

        public Criteria andReserved2NotIn(List<String> values) {
            addCriterion("RESERVED2 not in", values, "reserved2");
            return (Criteria) this;
        }

        public Criteria andReserved2Between(String value1, String value2) {
            addCriterion("RESERVED2 between", value1, value2, "reserved2");
            return (Criteria) this;
        }

        public Criteria andReserved2NotBetween(String value1, String value2) {
            addCriterion("RESERVED2 not between", value1, value2, "reserved2");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}