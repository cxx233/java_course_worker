package com.cxx.db.mapper;

import com.cxx.db.entity.CommodityDetail;
import com.cxx.db.entity.CommodityDetailExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CommodityDetailMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_commodity_details
     *
     * @mbg.generated Fri Apr 15 12:48:50 CST 2022
     */
    long countByExample(CommodityDetailExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_commodity_details
     *
     * @mbg.generated Fri Apr 15 12:48:50 CST 2022
     */
    int deleteByExample(CommodityDetailExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_commodity_details
     *
     * @mbg.generated Fri Apr 15 12:48:50 CST 2022
     */
    int insert(CommodityDetail record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_commodity_details
     *
     * @mbg.generated Fri Apr 15 12:48:50 CST 2022
     */
    int insertSelective(CommodityDetail record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_commodity_details
     *
     * @mbg.generated Fri Apr 15 12:48:50 CST 2022
     */
    List<CommodityDetail> selectByExampleWithBLOBs(CommodityDetailExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_commodity_details
     *
     * @mbg.generated Fri Apr 15 12:48:50 CST 2022
     */
    List<CommodityDetail> selectByExample(CommodityDetailExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_commodity_details
     *
     * @mbg.generated Fri Apr 15 12:48:50 CST 2022
     */
    int updateByExampleSelective(@Param("record") CommodityDetail record, @Param("example") CommodityDetailExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_commodity_details
     *
     * @mbg.generated Fri Apr 15 12:48:50 CST 2022
     */
    int updateByExampleWithBLOBs(@Param("record") CommodityDetail record, @Param("example") CommodityDetailExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_commodity_details
     *
     * @mbg.generated Fri Apr 15 12:48:50 CST 2022
     */
    int updateByExample(@Param("record") CommodityDetail record, @Param("example") CommodityDetailExample example);
}