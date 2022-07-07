package com.example.trading.controller;

import com.example.trading.dao.PriceAggregationDAO;
import com.example.trading.entity.TradingLog;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author tanglijuan
 * @date 2022/7/7
 */
@RestController
public class TradeController {

    @Resource
    private PriceAggregationDAO priceAggregationDAO;

    @RequestMapping(value = "/get/bestPrice", method = RequestMethod.GET)
    ApiResponse<BigDecimal> getLatestBestAggregatedPrice(@Param("side") Integer side) {
        return ApiResponse.success(priceAggregationDAO.getLatestBestAggregatedPrice(side));
    }

    @RequestMapping(value = "/get/wallteBalance", method = RequestMethod.GET)
    ApiResponse<BigDecimal> getWalletBalance(Integer accountId) {
        return ApiResponse.success(priceAggregationDAO.getWalletBalance(accountId));
    }

    @RequestMapping(value = "/get/tradingHistory", method = RequestMethod.GET)
    ApiResponse<List<TradingLog>> getTradingHistory(Integer accountId) {
        return ApiResponse.success(priceAggregationDAO.getTradingHistory(accountId));

    }
}
