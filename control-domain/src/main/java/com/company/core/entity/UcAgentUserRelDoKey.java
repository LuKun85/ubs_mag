package com.company.core.entity;

public class UcAgentUserRelDoKey {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column UC_AGENT_USER_REL.AGENT_ID
     *
     * @mbggenerated
     */
    private String agentId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column UC_AGENT_USER_REL.USER_ID
     *
     * @mbggenerated
     */
    private String userId;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column UC_AGENT_USER_REL.AGENT_ID
     *
     * @return the value of UC_AGENT_USER_REL.AGENT_ID
     *
     * @mbggenerated
     */
    public String getAgentId() {
        return agentId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column UC_AGENT_USER_REL.AGENT_ID
     *
     * @param agentId the value for UC_AGENT_USER_REL.AGENT_ID
     *
     * @mbggenerated
     */
    public void setAgentId(String agentId) {
        this.agentId = agentId == null ? null : agentId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column UC_AGENT_USER_REL.USER_ID
     *
     * @return the value of UC_AGENT_USER_REL.USER_ID
     *
     * @mbggenerated
     */
    public String getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column UC_AGENT_USER_REL.USER_ID
     *
     * @param userId the value for UC_AGENT_USER_REL.USER_ID
     *
     * @mbggenerated
     */
    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }
}