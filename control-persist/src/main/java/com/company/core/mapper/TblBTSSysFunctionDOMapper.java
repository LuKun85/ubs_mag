package com.company.core.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;

import com.company.core.entity.TblBTSSysFunctionDO;
import com.company.core.entity.TblBTSSysFunctionDOExample;

public interface TblBTSSysFunctionDOMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TBL_BTS_SYS_FUNCTION
     *
     * @mbggenerated
     */
    int countByExample(TblBTSSysFunctionDOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TBL_BTS_SYS_FUNCTION
     *
     * @mbggenerated
     */
    int deleteByExample(TblBTSSysFunctionDOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TBL_BTS_SYS_FUNCTION
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(String funcId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TBL_BTS_SYS_FUNCTION
     *
     * @mbggenerated
     */
    int insert(TblBTSSysFunctionDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TBL_BTS_SYS_FUNCTION
     *
     * @mbggenerated
     */
    int insertSelective(TblBTSSysFunctionDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TBL_BTS_SYS_FUNCTION
     *
     * @mbggenerated
     */
    List<TblBTSSysFunctionDO> selectByExample(TblBTSSysFunctionDOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TBL_BTS_SYS_FUNCTION
     *
     * @mbggenerated
     */
    TblBTSSysFunctionDO selectByPrimaryKey(String funcId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TBL_BTS_SYS_FUNCTION
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") TblBTSSysFunctionDO record,
                                 @Param("example") TblBTSSysFunctionDOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TBL_BTS_SYS_FUNCTION
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") TblBTSSysFunctionDO record,
                        @Param("example") TblBTSSysFunctionDOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TBL_BTS_SYS_FUNCTION
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(TblBTSSysFunctionDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TBL_BTS_SYS_FUNCTION
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(TblBTSSysFunctionDO record);
}