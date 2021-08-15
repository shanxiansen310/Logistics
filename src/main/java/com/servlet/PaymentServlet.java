package com.servlet;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.dao.UserDao;
import com.entity.Order;
import com.util.AlipayUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@WebServlet("/pay")
public class PaymentServlet extends HttpServlet {

    private boolean isPaid=false;
    private final String IS_PAID="TRADE_SUCCESS";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        /*表示预创建订单,需要返回二维码支付的网址串*/
        if (req.getParameter("request").equals("1")) {
            String price=req.getParameter("price");
            String driverName=req.getParameter("driverName");
            String clientName=req.getParameter("clientName");
            String tradeNo = String.valueOf(System.currentTimeMillis());      //根据系统时间设置订单号保证不会重复

            System.out.println("tradeNo = " + tradeNo);
            System.out.println("clientName = " + clientName);
            System.out.println("driverName = " + driverName);

            Order order=new Order(tradeNo,clientName,driverName,price);
            try {
                /*创建订单*/
                String data = new AlipayUtil().preOrder(tradeNo, req.getParameter("price"));
                /*从阿里获取的数据中解析出状态*/
                JSONObject jsonObject = JSONObject.parseObject(data);
                JSONObject jsonResponse = jsonObject.getJSONObject("alipay_trade_precreate_response");
                String code = jsonResponse.getString("code");

                /*表示预创建订单成功,插入数据库,并返回支付地址,同时也要开始轮询支付宝从而得到支付结果*/
                JSONObject objectRes=new JSONObject();
                if (code.equals("10000")) {
                    new UserDao().addOrder(order);     //将订单信息添加到数据库中
                    String qrUrl = jsonResponse.getString("qr_code");

                    System.out.println("qrUrl = " + qrUrl);
                    objectRes.put("status","200");
                    objectRes.put("qrUrl",qrUrl);
                    objectRes.put("tradeNo",tradeNo);

//                    /*开始轮询,5s一次,100s后会停止询问*/
//                    queryTradePolling(tradeNo);


                } else {
                    /*预创建订单失败*/
                    objectRes.put("status","404");
                }
                resp.getWriter().println(objectRes);

                resp.getWriter().close();
            } catch (AlipayApiException e) {
                e.printStackTrace();
            }
        }
        /*查询订单状态*/
        else if (req.getParameter("request").equals("2")){
            String tradeNo=req.getParameter("tradeNo");

            try {
                String result= new AlipayUtil().query(tradeNo);
                /*获取状态*/
                JSONObject jsonObject = JSONObject.parseObject(result);
                JSONObject jsonResponse = jsonObject.getJSONObject("alipay_trade_query_response");
                String trade_status = jsonResponse.getString("trade_status");
                if (IS_PAID.equals(trade_status)){
                    isPaid=true;
                    /*表示订单支付成功*/
                    resp.getWriter().print("true");
                    System.out.println("Polling result: is paid!");
                }
                else {
                    /*表示订单仍然未被支付或不存在*/
                    resp.getWriter().print("false");
                    System.out.println("Polling result: not paid!");
                }
            } catch (AlipayApiException e) {
                e.printStackTrace();
            }

        }
    }


    /*轮询支付结果*/
    public void queryTradePolling(final String tradeNo){
        final AtomicInteger count = new AtomicInteger(0);
        final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
//                System.out.println("run "+ System.currentTimeMillis());
                System.out.println("▼Polling count: "+count.getAndIncrement());
                if (count.get() > 20) {
                    /*关闭轮询线程*/
                    executorService.shutdown();
                }
                try {
                    String result= new AlipayUtil().query(tradeNo);
                    /*获取状态*/
                    JSONObject jsonObject = JSONObject.parseObject(result);
                    JSONObject jsonResponse = jsonObject.getJSONObject("alipay_trade_query_response");
                    String trade_status = jsonResponse.getString("trade_status");
                    if (trade_status.equals(IS_PAID)){
                        isPaid=true;
                        System.out.println("Polling result: is paid!");
                    }
                    else {
                        System.out.println("Polling result: not paid!");
                    }
                } catch (AlipayApiException e) {
                    e.printStackTrace();
                }

            }
        }, 0, 5000, TimeUnit.MILLISECONDS);   //每8s询问一次
    }

    public static void main(String[] args) {
//        System.out.println("run " + System.currentTimeMillis());
        new PaymentServlet().queryTradePolling("30");
    }


}
