package servlet;

import com.alibaba.fastjson.JSONObject;
import com.sun.net.httpserver.HttpServer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/test")
public class TestServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        /*Servlet类似api接口类型传递数据*/
//        List<Driver> drivers=new ArrayList<>();
//        Driver d1=new Driver("111","he","m123");
//        Driver d2=new Driver("2","Mugen","m123");
//        Driver d3=new Driver("3","Jin","m123");
//        drivers.add(d1); drivers.add(d2); drivers.add(d3);
//
//        JSONObject jsonObject=new JSONObject();
//        jsonObject.put("driverList",drivers);
//
////        resp.setContentType("application/json");
//        resp.getWriter().println(jsonObject);
//        resp.getWriter().close();

    }
}
