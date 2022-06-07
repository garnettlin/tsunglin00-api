package com.tsunglin.tsunglin00.util;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class Result<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    //業務碼，比如成功、失敗、權限不足等 code，可自行定義
    @ApiModelProperty("返回碼")
    private int resultCode;
    //返回信息，後端在進行業務處理後返回給前端一個提示信息，可自行定義
    @ApiModelProperty("返回信息")
    private String message;
    //數據結果，泛型，可以是列表、單個對象、數字、布爾值等
    @ApiModelProperty("返回數據")
    private T data;

    public Result() {
    }

    public Result(int resultCode, String message) {
        this.resultCode = resultCode;
        this.message = message;
    }

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Result{" +
                "resultCode=" + resultCode +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
