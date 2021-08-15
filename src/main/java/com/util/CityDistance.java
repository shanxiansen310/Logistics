package com.util;

import org.checkerframework.checker.units.qual.C;

import java.util.ArrayList;
import java.util.List;

public class CityDistance {
    private String Start;
    private String Dest;
    private String distance;

    public CityDistance() {
    }

    public CityDistance(String start, String dest, String distance) {
        Start = start;
        Dest = dest;
        this.distance = distance;
    }

    public String getStart() {
        return Start;
    }

    public void setStart(String start) {
        Start = start;
    }

    public String getDest() {
        return Dest;
    }

    public void setDest(String dest) {
        Dest = dest;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public static List<CityDistance>  initSCity(){
        List<CityDistance> sichuanCity=new ArrayList<>();

        CityDistance c1=new CityDistance("Leshan","Yaan","60");
        CityDistance c2=new CityDistance("Chengdu","Mianyang","80");
        CityDistance c3=new CityDistance("Chengdu","Leshan","130");
        CityDistance c4=new CityDistance("Chengdu","Meishan","50");
        sichuanCity.add(c1);
        sichuanCity.add(c2);
        sichuanCity.add(c3);
        sichuanCity.add(c4);

        return sichuanCity;

    }

    public static List<CityDistance>  initSHCity(){
        List<CityDistance> SHCity=new ArrayList<>();

        CityDistance c1=new CityDistance("Pudong","Jingan","110");
        CityDistance c2=new CityDistance("Putuo","Xuhui","36");
        CityDistance c3=new CityDistance("Huangpu","Xuhui","53");
        CityDistance c4=new CityDistance("Huangpu","Changning","30");
        SHCity.add(c1);
        SHCity.add(c2);
        SHCity.add(c3);
        SHCity.add(c4);

        return SHCity;

    }

    public static List<CityDistance>  initHNCity(){
        List<CityDistance> HNCity=new ArrayList<>();

        CityDistance c1=new CityDistance("Changsha","Chenzhou","137");
        CityDistance c2=new CityDistance("Changsha","Xiangtan","50");
        CityDistance c3=new CityDistance("Changsha","Liuyang","110");
        CityDistance c4=new CityDistance("Zhangjiajie","Liuyang","60");
        HNCity.add(c1);
        HNCity.add(c2);
        HNCity.add(c3);
        HNCity.add(c4);

        return HNCity;

    }

    public static List<CityDistance>  initHBCity(){
        List<CityDistance> HBCity=new ArrayList<>();

        CityDistance c1=new CityDistance("Wuhan","Huangshi","110");
        CityDistance c2=new CityDistance("Wuhan","Xiaogan","86");
        CityDistance c3=new CityDistance("Wuhan","Huanggang","58");
        HBCity.add(c1);
        HBCity.add(c2);
        HBCity.add(c3);

        return HBCity;

    }


}
