package com.guannan.network.bean;

import okhttp3.Call;

/**
 * Created by guannan on 2017/7/7.
 */

public class ErrorResponse {

    public int errorCode;

    public String errorMessage;

    public Call call;

    public ErrorResponse(int errorCode, String errorMessage, Call call) {

        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.call = call;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Call getCall() {
        return call;
    }

    public void setCall(Call call) {
        this.call = call;
    }

    @Override
    public String toString() {
        return "ErrorResponse{" +
                "errorCode=" + errorCode +
                ", errorMessage='" + errorMessage + '\'' +
                ", call=" + call +
                '}';
    }
}
