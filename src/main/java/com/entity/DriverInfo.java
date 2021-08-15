package com.entity;

/*   这个类主要用于显示司机的available情况     */
/*   一个司机可以重复发布信息,但必须在不同的天数  */
public class DriverInfo {
    private String name;
    private String date;
    /*起步价*/
    private String basePrice;
    /*每km加价*/
    private String perPrice;

    /*city表示经营范围*/
    private String city;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(String basePrice) {
        this.basePrice = basePrice;
    }

    public String getPerPrice() {
        return perPrice;
    }

    public void setPerPrice(String perPrice) {
        this.perPrice = perPrice;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}


