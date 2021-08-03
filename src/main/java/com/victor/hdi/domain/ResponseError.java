package com.victor.hdi.domain;

public class ResponseError extends Response{

    private String businessErrorMessage;

    public String getBusinessErrorMessage() {
        return businessErrorMessage;
    }

    public void setBusinessErrorMessage(String businessErrorMessage) {
        this.businessErrorMessage = businessErrorMessage;
    }
}
