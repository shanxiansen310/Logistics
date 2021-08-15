package com.servlet;


import com.dao.UserDao;
import com.entity.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

@WebServlet(urlPatterns = "/register")
public class RegisterServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        this.doPost(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");//设定编码防止中文乱码
        response.setContentType("text/plain;charset=utf-8");//设置相应类型为html,编码为utf-8

        String name = request.getParameter("name");//根据name获取参数
        String password = request.getParameter("password");//根据password获取参数

        UserDao userDao = new UserDao();
        User user = new User();
        user.setName(name);
        user.setPassword(password);

        if(!userDao.add(user)) //若添加失败
        {
            /*204:无内容。服务器成功处理，但未返回内容。*/
            response.sendError(204,"add failed.");//设置204错误码与出错信息
        }
    }



}
