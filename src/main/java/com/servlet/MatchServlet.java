package com.servlet;


import com.alibaba.fastjson.JSONObject;
import com.dao.UserDao;
import com.entity.DriverInfo;
import com.entity.User;
import com.util.CityDistance;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/match")
public class MatchServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("application/json;charset=utf-8");//这里可以修改为text/json
        String message;
        try {
            BufferedReader br= new BufferedReader(new InputStreamReader(req.getInputStream(),"UTF-8"));
            StringBuffer stringBuffer=new StringBuffer("");
            String temp;
            while((temp=br.readLine())!=null) {
                stringBuffer.append(temp);
            }
            br.close();
            message=stringBuffer.toString();
            System.out.println("请求报文"+message);

            /*获取需要进行匹配的用户信息*/
            JSONObject object =JSONObject.parseObject(message);
            User user=JSONObject.parseObject(object.getJSONObject("user").toJSONString(),User.class);
            String requiredDate=user.getClientDate();
            int requiredPrice=Integer.parseInt(user.getClientPrice());

            String province=null, distance=null;
            String start=user.getClientStart();
            String dest=user.getClientDest();

            /*获取城市信息*/
            List <CityDistance> SCCityList=CityDistance.initSCity();
            List <CityDistance> HNCityList=CityDistance.initHNCity();
            List <CityDistance> SHCityList=CityDistance.initSHCity();
            List <CityDistance> HBCityList=CityDistance.initHBCity();

            /*得出距离和省份*/
            for (CityDistance c:SCCityList){
                if ( (c.getStart().equals(start)&&c.getDest().equals(dest))
                    ||(c.getStart().equals(dest)&&c.getDest().equals(start))){
                    province="Sichuan";
                    distance=c.getDistance();
                }
            }

            for (CityDistance c:HNCityList){
                if ( (c.getStart().equals(start)&&c.getDest().equals(dest))
                        ||(c.getStart().equals(dest)&&c.getDest().equals(start))){
                    province="Hunan";
                    distance=c.getDistance();
                }
            }

            for (CityDistance c:HBCityList){
                if ( (c.getStart().equals(start)&&c.getDest().equals(dest))
                        ||(c.getStart().equals(dest)&&c.getDest().equals(start))){
                    province="Hubei";
                    distance=c.getDistance();
                }
            }

            for (CityDistance c:SHCityList){
                if ( (c.getStart().equals(start)&&c.getDest().equals(dest))
                        ||(c.getStart().equals(dest)&&c.getDest().equals(start))){
                    province="Shanghai";
                    distance=c.getDistance();
                }
            }

            int dist=Integer.parseInt(distance);

            System.out.println(user.getClientStart()+"--"+user.getClientDest()+": "+distance+"km");

            /*得到司机列表信息*/
            List<DriverInfo> drivers=new UserDao().getDriver();

            /*开始匹配*/
            boolean isMatched=false;
            DriverInfo matchedDriver=new DriverInfo();
            matchedDriver.setBasePrice("1000000");

            /*选择满足省份且满足日期的情况下,price最少的司机*/
            for (DriverInfo d:drivers){
                int driverPrice=Integer.parseInt(d.getBasePrice());
                int per=Integer.parseInt(d.getPerPrice());
                /*不能自己匹配自己!!!*/
                if ( d.getCity().equals(province) &&d.getDate().equals(requiredDate)
                        && (driverPrice+dist*per)<=requiredPrice
                        &&(!user.getName().equals(d.getName()))){
                    isMatched=true;
                    if ( (driverPrice + dist*per)<Integer.parseInt(matchedDriver.getBasePrice())){
                        matchedDriver=d;
                        System.out.println("匹配到的司机:起步价"+d.getBasePrice()+" per:"+d.getPerPrice()+" total:"+(driverPrice+dist*per));

                    }
                }
            }

            JSONObject jsonObject = new JSONObject();
            if (!isMatched){
                jsonObject.put("status","404");  //表示匹配失败
            }else {
                /*将匹配到的详细的driver信息返回给客户端*/
                jsonObject.put("status","200");   //200表示匹配成功!!!
                jsonObject.put("matchedDriver", matchedDriver);
            }
            System.out.println("返回数据:" + jsonObject);
            resp.getWriter().println(jsonObject);
            resp.getWriter().close();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }




}
