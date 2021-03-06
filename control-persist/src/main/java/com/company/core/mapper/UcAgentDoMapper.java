package com.company.core.mapper;

import com.company.core.entity.UcAgentDo;
import com.company.core.entity.UcAgentDoExample;
import com.company.core.entity.UcAgentDoKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UcAgentDoMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table UC_AGENT
     *
     * @mbggenerated
     */
    int countByExample(UcAgentDoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table UC_AGENT
     *
     * @mbggenerated
     */
    int deleteByExample(UcAgentDoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table UC_AGENT
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(UcAgentDoKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table UC_AGENT
     *
     * @mbggenerated
     */
    int insert(UcAgentDo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table UC_AGENT
     *
     * @mbggenerated
     */
    int insertSelective(UcAgentDo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table UC_AGENT
     *
     * @mbggenerated
     */
    List<UcAgentDo> selectByExample(UcAgentDoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table UC_AGENT
     *
     * @mbggenerated
     */
    UcAgentDo selectByPrimaryKey(UcAgentDoKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table UC_AGENT
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") UcAgentDo record, @Param("example") UcAgentDoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table UC_AGENT
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") UcAgentDo record, @Param("example") UcAgentDoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table UC_AGENT
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(UcAgentDo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table UC_AGENT
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(UcAgentDo record);
}