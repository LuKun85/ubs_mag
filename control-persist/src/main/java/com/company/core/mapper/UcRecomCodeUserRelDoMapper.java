package com.company.core.mapper;

import com.company.core.entity.UcRecomCodeUserRelDo;
import com.company.core.entity.UcRecomCodeUserRelDoExample;
import com.company.core.entity.UcRecomCodeUserRelDoKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UcRecomCodeUserRelDoMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table UC_RECCOM_CODE_USER_REL
     *
     * @mbggenerated
     */
    int countByExample(UcRecomCodeUserRelDoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table UC_RECCOM_CODE_USER_REL
     *
     * @mbggenerated
     */
    int deleteByExample(UcRecomCodeUserRelDoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table UC_RECCOM_CODE_USER_REL
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(UcRecomCodeUserRelDoKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table UC_RECCOM_CODE_USER_REL
     *
     * @mbggenerated
     */
    int insert(UcRecomCodeUserRelDo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table UC_RECCOM_CODE_USER_REL
     *
     * @mbggenerated
     */
    int insertSelective(UcRecomCodeUserRelDo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table UC_RECCOM_CODE_USER_REL
     *
     * @mbggenerated
     */
    List<UcRecomCodeUserRelDo> selectByExample(UcRecomCodeUserRelDoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table UC_RECCOM_CODE_USER_REL
     *
     * @mbggenerated
     */
    UcRecomCodeUserRelDo selectByPrimaryKey(UcRecomCodeUserRelDoKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table UC_RECCOM_CODE_USER_REL
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") UcRecomCodeUserRelDo record, @Param("example") UcRecomCodeUserRelDoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table UC_RECCOM_CODE_USER_REL
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") UcRecomCodeUserRelDo record, @Param("example") UcRecomCodeUserRelDoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table UC_RECCOM_CODE_USER_REL
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(UcRecomCodeUserRelDo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table UC_RECCOM_CODE_USER_REL
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(UcRecomCodeUserRelDo record);
}