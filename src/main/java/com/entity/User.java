package com.entity;

public class User {

    /*基本信息*/
    private String name;
    private String password;

    /*client*/
    private String clientPrice;       //总价格
    private String clientDate;
    private String clientStart;
    private String clientDest;

    /*driver*/
    private String driverBasePrice;   //起步价
    private String driverPerPrice;    //距离加价
    private String driverCity;        //经营范围
    private String driverDate;        //日期



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getClientPrice() {
        return clientPrice;
    }

    public void setClientPrice(String clientPrice) {
        this.clientPrice = clientPrice;
    }

    public String getClientDate() {
        return clientDate;
    }

    public void setClientDate(String clientDate) {
        this.clientDate = clientDate;
    }

    public String getClientStart() {
        return clientStart;
    }

    public void setClientStart(String clientStart) {
        this.clientStart = clientStart;
    }

    public String getClientDest() {
        return clientDest;
    }

    public void setClientDest(String clientDest) {
        this.clientDest = clientDest;
    }

    public String getDriverBasePrice() {
        return driverBasePrice;
    }

    public void setDriverBasePrice(String driverBasePrice) {
        this.driverBasePrice = driverBasePrice;
    }

    public String getDriverPerPrice() {
        return driverPerPrice;
    }

    public void setDriverPerPrice(String driverPerPrice) {
        this.driverPerPrice = driverPerPrice;
    }

    public String getDriverCity() {
        return driverCity;
    }

    public void setDriverCity(String driverCity) {
        this.driverCity = driverCity;
    }

    public String getDriverDate() {
        return driverDate;
    }

    public void setDriverDate(String driverDate) {
        this.driverDate = driverDate;
    }

    public User(){}
    public User(String name){
        this.name=name;
    }
}
