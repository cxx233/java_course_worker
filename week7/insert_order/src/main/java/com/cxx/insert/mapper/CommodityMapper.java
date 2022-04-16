package com.cxx.insert.mapper;

import com.cxx.insert.entity.Commodity;
import com.cxx.insert.entity.CommodityExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CommodityMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_commodity
     *
     * @mbg.generated Fri Apr 15 12:48:50 CST 2022
     */
    long countByExample(CommodityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_commodity
     *
     * @mbg.generated Fri Apr 15 12:48:50 CST 2022
     */
    int deleteByExample(CommodityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_commodity
     *
     * @mbg.generated Fri Apr 15 12:48:50 CST 2022
     */
    int insert(Commodity record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_commodity
     *
     * @mbg.generated Fri Apr 15 12:48:50 CST 2022
     */
    int insertSelective(Commodity record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_commodity
     *
     * @mbg.generated Fri Apr 15 12:48:50 CST 2022
     */
    List<Commodity> selectByExample(CommodityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_commodity
     *
     * @mbg.generated Fri Apr 15 12:48:50 CST 2022
     */
    int updateByExampleSelective(@Param("record") Commodity record, @Param("example") CommodityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_commodity
     *
     * @mbg.generated Fri Apr 15 12:48:50 CST 2022
     */
    int updateByExample(@Param("record") Commodity record, @Param("example") CommodityExample example);
}