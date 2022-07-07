package com.example.trading.task;


import com.alibaba.fastjson.JSONArray;
import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;

/**
 * @author tanglijuan
 * @date 2022/7/7
 */
@Component
public class AggregationTask {
    public static final String BIANCE_URL = "https://api.binance.com/api/v3/ticker/bookTicker";

    public static final String HOUBI_URL = "https://api.huobi.pro/market/tickers";


    @PostConstruct
    @Scheduled(cron = "0 0/1 * * * ?")
    public void runTasks() {
        String response = doGet(BIANCE_URL, "UTF-8");
        List<AggregationTask> priceAggregation = JSONArray.parseArray(response, AggregationTask.class);
        // todo 批量插入数据库
    }

    public static String doGet(String url, String charset) {
        /**
         * 1.生成HttpClient对象并设置参数
         */
        HttpClient httpClient = new HttpClient();
        //设置Http连接超时为5秒
        httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(5000);

        /**
         * 2.生成GetMethod对象并设置参数
         */
        GetMethod getMethod = new GetMethod(url);
        //设置get请求超时为5秒
        getMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 5000);
        //设置请求重试处理，用的是默认的重试处理：请求三次
        getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());

        String response = "";

        /**
         * 3.执行HTTP GET 请求
         */
        try {
            int statusCode = httpClient.executeMethod(getMethod);

            /**
             * 4.判断访问的状态码
             */
            if (statusCode != HttpStatus.SC_OK) {
                System.err.println("请求出错：" + getMethod.getStatusLine());
            }

            /**
             * 5.处理HTTP响应内容
             */
            //HTTP响应头部信息，这里简单打印
            Header[] headers = getMethod.getResponseHeaders();
            for (Header h : headers) {
                System.out.println(h.getName() + "---------------" + h.getValue());
            }
            //读取HTTP响应内容，这里简单打印网页内容
            //读取为字节数组
            byte[] responseBody = getMethod.getResponseBody();
            response = new String(responseBody, charset);
//            System.out.println("-----------response:" + response);
            //读取为InputStream，在网页内容数据量大时候推荐使用
            //InputStream response = getMethod.getResponseBodyAsStream();

        } catch (HttpException e) {
            //发生致命的异常，可能是协议不对或者返回的内容有问题
            System.out.println("请检查输入的URL!");
            e.printStackTrace();
        } catch (IOException e) {
            //发生网络异常
            System.out.println("发生网络异常!");
        } finally {
            /**
             * 6.释放连接
             */
            getMethod.releaseConnection();
        }
        return response;
    }
}
