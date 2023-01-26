package com.clusus.Bloomberg.dto;

import java.time.LocalDateTime;

public class ForexDealDTO {
    private String id;
    private String fromCurrencyISOCode;
    private String toCurrencyISOCode;
    private LocalDateTime dealTime;
    private String dealAmount;

    public ForexDealDTO() {
    }

    public ForexDealDTO(String id, String fromCurrencyISOCode, String toCurrencyISOCode, LocalDateTime dealTime, String dealAmount) {
        this.id = id;
        this.fromCurrencyISOCode = fromCurrencyISOCode;
        this.toCurrencyISOCode = toCurrencyISOCode;
        this.dealTime = dealTime;
        this.dealAmount = dealAmount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFromCurrencyISOCode() {
        return fromCurrencyISOCode;
    }

    public void setFromCurrencyISOCode(String fromCurrencyISOCode) {
        this.fromCurrencyISOCode = fromCurrencyISOCode;
    }

    public String getToCurrencyISOCode() {
        return toCurrencyISOCode;
    }

    public void setToCurrencyISOCode(String toCurrencyISOCode) {
        this.toCurrencyISOCode = toCurrencyISOCode;
    }

    public LocalDateTime getDealTime() {
        return dealTime;
    }

    public void setDealTime(LocalDateTime dealTime) {
        this.dealTime = dealTime;
    }

    public String getDealAmount() {
        return dealAmount;
    }

    public void setDealAmount(String dealAmount) {
        this.dealAmount = dealAmount;
    }
}
