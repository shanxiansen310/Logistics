package com.servlet;


import com.dao.UserDao;
import com.entity.User;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/release")
public class ReleaseServlet extends HttpServlet {

    @Override
    protected    void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        this.doGet(req,resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("utf-8");
        req.setCharacterEncoding("utf-8");

        resp.setContentType("text/plain;charset=utf-8");//设置相应类型为html,编码为utf-8

        String name=req.getParameter("name");
        String date=req.getParameter("date");
        String price=req.getParameter("price");
        String role=req.getParameter("role");

        System.out.println("role = " + role);
        System.out.println("name = " + name);
        UserDao userDao=new UserDao();

        User user=new User();
        user.setName(name);

        /*0表示顾客,对顾客是更新*/
        if (role.equals("0")){
            user.setClientDate(date);
            user.setClientStart(req.getParameter("start"));
            user.setClientDest(req.getParameter("dest"));
            user.setClientPrice(price);
            System.out.println("start = " + req.getParameter("start"));

            if (!userDao.updateClient(user)){
                resp.sendError(204,"update failed!");
            }
        }

        /*1表示司机,对司机是添加*/
        else if (role.equals("1")){
            user.setDriverDate(date);
            user.setDriverCity(req.getParameter("district"));
            user.setDriverBasePrice(price);
            user.setDriverPerPrice(req.getParameter("perPrice"));

            if(!userDao.insertDriver(user)){
                resp.sendError(204,"update failed!");
            }
        }
        else {
            resp.sendError(204,"update failed!");
        }






    }
}
