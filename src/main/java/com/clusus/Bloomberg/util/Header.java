package com.clusus.Bloomberg.util;


public enum Header {
    DEAL_UNIQUE_ID("DealUniqueId"),
    FROM_ISO_CURRENCY_CODE("FromISOCurrencyCode"),
    TO_ISO_CURRENCY_CODE("ToCurrencyISOCode"),
    DEAL_TIME_STAMP("DealTimeStamp"),
    DEAL_AMOUNT("DealAmount");

    private String header;

    Header(String header) {
        this.header = header;
    }

    public String getHeader() {
        return header;
    }
}
