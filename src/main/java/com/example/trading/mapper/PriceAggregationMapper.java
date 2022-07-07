package com.example.trading.mapper;

import com.example.trading.entity.TradingLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author tanglijuan
 * @date 2022/7/7
 */
@Mapper
public interface PriceAggregationMapper {

    @Select("select * from aggregate_price " +
            "<if test= side !=null and side = 1\"> order by price desc  limit 1</if> " +
            "<if test= side !=null and side = 2\"> order by price asc  limit 1</if>")
    BigDecimal getLatestBestAggregatedPrice(Integer side);

    @Select("select * from wallet where account_id =#{acoountId}")
    BigDecimal getWalletBalance(Integer accountId);

    @Select("select * from trading_log where account_id = #{accountId} order by create_time ")
    List<TradingLog> getTradingHistory(Integer accountId);

}
