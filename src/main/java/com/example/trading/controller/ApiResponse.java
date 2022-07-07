package com.example.trading.controller;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author tanglijuan
 * @date 2022/7/7
 */
public class ApiResponse<T> implements Serializable {
    private static final long serialVersionUID = 7563572234517927934L;

    private Status status;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T content;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String errorCode;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String errorMsg;

    public enum Status {
        ok, fail
    }

    private ApiResponse() {
    }

    public static <T> ApiResponse success(T content) {
        ApiResponse response = new ApiResponse();
        if (content != null) {
            response.content = content;
        }
        response.status = Status.ok;
        return response;
    }

    public static ApiResponse<String> success() {
        return success("ok");
    }

    /**
     * 返回内容是一个map, 把参数数组, 按照k-v, k-v的方式放入map
     *
     * @param keyValues
     * @return
     */
    public static ApiResponse successMap(Object... keyValues) {
        Map<Object, Object> mm = new LinkedHashMap<>(keyValues.length / 2);
        for (int i = 0; i < keyValues.length; i++) {
            mm.put(keyValues[i], keyValues[++i]);
        }
        return success(mm);
    }

    public static ApiResponse failed(String errorCode, String errorMsg) {
        ApiResponse response = new ApiResponse();
        response.status = Status.fail;
        response.errorCode = errorCode;
        response.errorMsg = errorMsg;
        return response;
    }

    /**
     * failed with default <code>errorCode 101</code>
     *
     * @param errorMsg
     * @return
     */
    public static ApiResponse failed(String errorMsg) {
        return failed("101", errorMsg);
    }


    public Status getStatus() {
        return status;
    }

    public T getContent() {
        return content;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    @JsonIgnore
    public boolean isFailed() {
        return Status.fail == status;
    }

    @JsonIgnore
    public boolean isOk() {
        return Status.ok == status;
    }

    public ApiResponse setContent(T content) {
        this.content = content;
        return this;
    }

    @Override
    public String toString() {

        StringBuilder builder = new StringBuilder();

        builder.append("status:").append(status).append(" ")
                .append("content:").append(content).append(" ")
                .append("errorCode:").append(errorCode).append(" ")
                .append("errorMsg:").append(errorMsg);

        return builder.toString();

    }
}
