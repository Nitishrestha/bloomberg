package com.clusus.Bloomberg.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "deals")
public class ForexDeal {
    @Id
    @Column(name = "deal_unique_id")
    private int id;
    @Column(name = "from_currency_iso_code")
    private String fromCurrencyISOCode;
    @Column(name = "to_currency_iso_code")
    private String toCurrencyISOCode;
    @Column(name = "deal_timestamp")
    private LocalDateTime dealTime;
    @Column(name = "deal_amount")
    private Double dealAmount;

    public ForexDeal() {
    }

    public ForexDeal(int id, String fromCurrencyISOCode, String toCurrencyISOCode, LocalDateTime dealTime, Double dealAmount) {
        this.id = id;
        this.fromCurrencyISOCode = fromCurrencyISOCode;
        this.toCurrencyISOCode = toCurrencyISOCode;
        this.dealTime = dealTime;
        this.dealAmount = dealAmount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public Double getDealAmount() {
        return dealAmount;
    }

    public void setDealAmount(Double dealAmount) {
        this.dealAmount = dealAmount;
    }
}
