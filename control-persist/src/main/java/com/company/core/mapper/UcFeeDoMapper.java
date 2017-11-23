package com.company.core.mapper;

import com.company.core.entity.UcFeeDo;
import com.company.core.entity.UcFeeDoExample;
import com.company.core.entity.UcFeeDoKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UcFeeDoMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table UC_FEE
     *
     * @mbg.generated
     */
    long countByExample(UcFeeDoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table UC_FEE
     *
     * @mbg.generated
     */
    int deleteByExample(UcFeeDoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table UC_FEE
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(UcFeeDoKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table UC_FEE
     *
     * @mbg.generated
     */
    int insert(UcFeeDo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table UC_FEE
     *
     * @mbg.generated
     */
    int insertSelective(UcFeeDo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table UC_FEE
     *
     * @mbg.generated
     */
    List<UcFeeDo> selectByExample(UcFeeDoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table UC_FEE
     *
     * @mbg.generated
     */
    UcFeeDo selectByPrimaryKey(UcFeeDoKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table UC_FEE
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") UcFeeDo record, @Param("example") UcFeeDoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table UC_FEE
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") UcFeeDo record, @Param("example") UcFeeDoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table UC_FEE
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(UcFeeDo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table UC_FEE
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(UcFeeDo record);
}