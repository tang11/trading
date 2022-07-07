package com.example.trading.dao;

import com.example.trading.entity.TradingLog;
import com.example.trading.mapper.PriceAggregationMapper;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author tanglijuan
 * @date 2022/7/7
 */
@Repository
public class PriceAggregationDAO {

    @Resource
    private PriceAggregationMapper mapper;

    public BigDecimal getLatestBestAggregatedPrice(Integer side) {

        return mapper.getLatestBestAggregatedPrice(side);
    }

    public BigDecimal getWalletBalance(Integer accountId) {
        return mapper.getWalletBalance(accountId);
    }

    public List<TradingLog> getTradingHistory(Integer accountId) {
        return mapper.getTradingHistory(accountId);
    }
}
