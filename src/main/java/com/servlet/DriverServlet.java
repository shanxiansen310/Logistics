package com.servlet;


import com.alibaba.fastjson.JSONObject;
import com.dao.UserDao;
import com.entity.DriverInfo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/*返回司机信息给客户端*/
@WebServlet("/driver")
public class DriverServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        List<DriverInfo> drivers=new UserDao().getDriver();
//        for (DriverInfo d :
//                drivers) {
//            System.out.println(d.getName());
//        }

        JSONObject jsonObject=new JSONObject();
        jsonObject.put("driverList",drivers);

//        resp.setContentType("application/json");
        resp.getWriter().println(jsonObject);
        resp.getWriter().close();

    }
}
