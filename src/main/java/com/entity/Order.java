package com.entity;

public class Order {

    private String tradeNo;
    private String clientName;
    private String driverName;
    private String price;

    public Order() {
    }

    public Order(String tradeNo, String clientName, String driverName, String price) {
        this.tradeNo = tradeNo;
        this.clientName = clientName;
        this.driverName = driverName;
        this.price = price;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
