package servlet;

import com.alibaba.fastjson.JSONObject;
import com.dao.UserDao;
import com.entity.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLDecoder;
import java.net.URLEncoder;

@WebServlet(name = "androidServlet",urlPatterns = "/android")
public class AndroidServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        /*接受客户端传来的信息*/
//        byte[] buffer=new byte[1024];
//        InputStream inputStream=request.getInputStream();
//        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
//        int length;
//        while ( (length=inputStream.read(buffer))!=-1){
//            byteArrayOutputStream.write(buffer,0,length);
//        }
//        String str= URLDecoder.decode(String.valueOf(byteArrayOutputStream),"UTF-8");
//        String str_result=str.substring(3,str.length()-1);
////        JSONObject jsonObject= JSONObject.fromObject(str_result);
//
//        JSONObject jsonObject=JSONObject.parseObject(str_result);
//
//        System.out.println("从客户端收到的消息为：\n用户名："+jsonObject.getString("name")+
//                "\n密码为:"+jsonObject.getString("password"));
//
//        String str_response="服务器已经收到客户端的消息";
//        response.getWriter().write(URLEncoder.encode(str_response,"UTF-8"));
        response.setContentType("application/json;charset=utf-8");//这里可能修改为text/json
        String message;
        try {
            BufferedReader br= new BufferedReader(new InputStreamReader(request.getInputStream(),"UTF-8"));
            StringBuffer stringBuffer=new StringBuffer("");
            String temp;
            while((temp=br.readLine())!=null) {
                stringBuffer.append(temp);
            }
            br.close();
            message=stringBuffer.toString();
            System.out.println("请求报文"+message);

            /*用于查询某个用户的详细信息*/
            JSONObject object =JSONObject.parseObject(message);
            String username=object.getString("name");
            System.out.println(object.getString("name"));

            User user=new UserDao().getUser(username);

            /*将详细的user信息返回给客户端*/
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("user",user);
            System.out.println("返回数据:"+jsonObject);
            response.getWriter().println(jsonObject);
            response.getWriter().close();


        }catch (Exception e){
            e.printStackTrace();
        }


//        response.getWriter().println("nmsl");

    }

    public void set(User u){
        u=new User("she");
    }
    public static void main(String[] args) {
        User u=new User();
        u.setName("he");
        new AndroidServlet().set(u);
        System.out.println(u.getName());
    }
}
