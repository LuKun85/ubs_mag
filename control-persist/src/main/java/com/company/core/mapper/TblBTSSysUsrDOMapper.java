package com.company.core.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;

import com.company.core.entity.TblBTSSysUsrDO;
import com.company.core.entity.TblBTSSysUsrDOExample;

public interface TblBTSSysUsrDOMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TBL_BTS_SYS_USR
     *
     * @mbggenerated
     */
    int countByExample(TblBTSSysUsrDOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TBL_BTS_SYS_USR
     *
     * @mbggenerated
     */
    int deleteByExample(TblBTSSysUsrDOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TBL_BTS_SYS_USR
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(String usrId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TBL_BTS_SYS_USR
     *
     * @mbggenerated
     */
    int insert(TblBTSSysUsrDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TBL_BTS_SYS_USR
     *
     * @mbggenerated
     */
    int insertSelective(TblBTSSysUsrDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TBL_BTS_SYS_USR
     *
     * @mbggenerated
     */
    List<TblBTSSysUsrDO> selectByExample(TblBTSSysUsrDOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TBL_BTS_SYS_USR
     *
     * @mbggenerated
     */
    TblBTSSysUsrDO selectByPrimaryKey(String usrId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TBL_BTS_SYS_USR
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") TblBTSSysUsrDO record,
                                 @Param("example") TblBTSSysUsrDOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TBL_BTS_SYS_USR
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") TblBTSSysUsrDO record,
                        @Param("example") TblBTSSysUsrDOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TBL_BTS_SYS_USR
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(TblBTSSysUsrDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TBL_BTS_SYS_USR
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(TblBTSSysUsrDO record);
}