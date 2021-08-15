package com.util;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;

import java.util.Date;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static com.alipay.api.AlipayConstants.CHARSET;

public class AlipayUtil {

    private final String APP_ID = "2021002121611098";
    private final String APP_PRIVATE_KEY = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDJgfx/nWZl84goCJOltbTdj/pJ5Tzz6HW3r8T8KFMGUTocodGwfOWEyOuUIOc1IZMw40PHurmwxOr5pLeLFFUssBm8rhr/raFqhy0jQzExwF52GLtYC9xAM6k/VQIflQego1X4z5jKj0bSjDjOnaokf/mYxHWyt4cy5dinH9lOOQraVdaCsDjZI7G5pZWgnI6KDZBMj5Ef/uUVYk6YxKpgflJynSeKCUEwKHr1P6jxVfYDjVUyyS3X9O/XEYCNsMJp3HhxthlxxhGM3PvGhARTfFKust7e9Ym8wiUuc7GAeFcPahD+o5YaH6LeRW075loA0FkcCmSYJZK+4XoD5z7BAgMBAAECggEAc7xF0uILcxj5pcyvSKjhlbPhFFomtQxZuEOrBwz+Co3Sq6lBb3V8udRQggaX5QtAp/6P6oJTrrSbJQE3LhlihkUqsxToitsl77Jt+WfZNHMBjiDMUViWiwVg+vAPRSZqeBf4A+zwG79I9Rr2ca1akJY0PEryMtB4RYJ/+hUv0V2pf5NvmtmlaHkhFSIZg4VW7w3pf3wrRmUinCgfkurgfavDMgXrkc5IXkBBTVgOs4yKsSw+D9k2HHKHeRpyNOnGQ3HuhA3Kn+DCXNDuwT/s1zka0dW/9A5cPgUOOUkbJb04KyEGeIvi9LVleEaFIEnKJuv+yjhMvwIT4OOiUCLbwQKBgQD8j6kiMErwOMQ3S5uT1e2syHJEJbd98Pq/7nDW7KzKC/YwNutb1DTCEIXt9TLTdzixy/dUqsok91smgUjWwKqfC8Tn11pFoq55rpMn1WEpTAbSKxxCFrUN+x3J2ujYafznZTkFcl2lZ1ahh2SIAv/zei4OYIliHTAji5Hhwl2TmQKBgQDMQF8VDwW0xtKcNYcN6DUql+PHn3a2TBoH8H7X+bfH5Z4oFWWSZHuxiVVVjbimTqrSpAsfQ8eGQERyZjIH0RdWQHjSHjWdPT11yLvUrNr3p2hSOebCCjooCEhwNBCN+IVW1qRocm70R0xcQHgNxY3Tq/3J/HsiPwIegzFuNpR9aQKBgQC/knz5eOYu5vA2/TvKXHgHGphKBs3zUsnwsf2XmTjLnV0lX1MReZp3Ja9hOx9Ndd5kRtGZwF7KwXG7eE92zffd4kB+l0RbE7YCiKAIk/s6Jvf5jUfG/ZBiRXtn9IuwNFzobEhfUEumPgdhSODBpnzdA21R7HnpGTi9jY2fUB5I6QKBgHLbyBxZhEXygMEjQL1LVYqMO6UoQWOmTAUm8s2VOgoJT/UOgz3dj8l4hypDNhEfr+QgJ7j/LpaKUXNbkMrghBUW0FqoblHr/EHeDfI8+i3GUF3c/a0kfyu/7KxcOur7VFBPB8lMgKSR65j9EAX+kBD2aRNLDhtl/1UVRZjKzJnxAoGBAJxUIjek3RXmZ6paw2qXka1MyOopPqKWucu+IrfOqdyNcvdmnQmhzIblghzo5iQJRgWmtzT0nNUFiJpGgahJF/z37LolmC9earSurq6h1C8kqJG5azoyxleXyG2rNnUuWyAk3pR0ylu0E0vA2jKt4HtHsze1UtiniujogjjJiNkV";
    private final String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAqRvl1ISJr6fwz2wAErKI/Ab0Q+YE3YqK6eekkfoVyT/ZRtT6lAYDd2331iE9teLsM7KJco6b0bZOPer0/5Fa8g1MN/q0EiGHBEIh0u+UebsK8Pkmvwa4OItVgWVEnC6iomVotRHGPfTvQYYoolUka3pkwyR5CdawbjMl4YGfDTp0/VYEzTNzIwJQXmY0UfDBDx3W+XddlTw+ePwkKX9SfgRK/M4Clbu3hLD5itjlMyfKVQKVt5sX8DRn8e2sc7wll9+KgFPGweRrtdypLo6BXLTPwndQMnlRkrh4WbKws0imsyEpCqcM2OESCzB4kcSGkm1QRRLtGRLmW2esdqmAmQIDAQAB";


    /*直接返回从支付宝获取的json数据*/
    public String preOrder(String tradeNo, String price) throws AlipayApiException {
//        String  tradeNo = String.valueOf(new Random().nextInt(9000) + 1000);  //每次调用生成一位四位数的随机数

        AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do", APP_ID, APP_PRIVATE_KEY, "json", "GBK", ALIPAY_PUBLIC_KEY, "RSA2");  //获得初始化的AlipayClient
        AlipayTradePrecreateRequest request = new AlipayTradePrecreateRequest(); //创建API对应的request类
        request.setBizContent("{" +
                "\"out_trade_no\":\"" + tradeNo + "\"," +   //商户订单号,需要每次保证不同,由服务器端发出!!!
                "\"total_amount\":\"" + price + "\"," +            //设置金额
                "\"subject\":\"Logistics Fee\"," +          //商品标题
                "\"store_id\":\"NJ_001\"," +              //商品门店编号
                "\"timeout_express\":\"90m\"}");       //订单允许的最晚付款时间
        AlipayTradePrecreateResponse response = alipayClient.execute(request);
        System.out.println(response.getBody());

        return response.getBody();
        //根据response中的结果继续业务逻辑处理
    }

    public String query(String tradeNo) throws AlipayApiException {
        AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do", APP_ID, APP_PRIVATE_KEY, "json", "GBK", ALIPAY_PUBLIC_KEY, "RSA2"); //获得初始化的AlipayClient
        AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();//创建API对应的request类
        request.setBizContent("{" +
                "\"out_trade_no\":\""+tradeNo+"\"}");
//                "\"trade_no\":\"2014112611001004680073956707\"}"); //设置业务参数
        AlipayTradeQueryResponse response = alipayClient.execute(request);//通过alipayClient调用API，获得对应的response类
        System.out.println(response.getBody());
        return response.getBody();
    }

    public static void main(String[] args) throws AlipayApiException {
//        new AlipayUtil().preOrder("30","0.88");
        new AlipayUtil().query("30");

//        String data = "{\"alipay_trade_precreate_response\":{\"code\":\"10000\",\"msg\":\"Success\",\"out_trade_no\":\"9682\",\"qr_code\":\"https:\\/\\/qr.alipay.com\\/bax09119sghghho0041a20bc\"},\"sign\":\"GBX0LDIZscyqjuCBlGo4VML8PQ6OBU5vP053F0yd4QvxM0xAKu1ma+VIYVC053HrQgcvqe5lX0MePH3C8mlK5fCUu+vLvR+ky07X2EBWAgXSCQRKuCv1TzDR683OlxsII1N6p7FGr5W6viQ5g2FozE7b80W7QybX+6nwiZJGeAmo3xM8ag4lHmHl+P+u9jo6RrjLn5wSjebGVUpxpKiiUYI3UI2SDU5kTv7Tw+Q8TtukJd9cZfFHbjOjI9llMXYIjW6z2+dTO44UFjGB7dJ5VpViCWpKuIs4vRRhh2N3clXFuWwKcvb1YY8DpRGKZ3ND7MzlVoONRZsOrp9Or3tDiA==\"}";
//
//        JSONObject jsonObject = JSONObject.parseObject(data);
//        JSONObject jsonResponse = jsonObject.getJSONObject("alipay_trade_precreate_response");
//        String code = jsonResponse.getString("code");
//        String qrUrl=jsonResponse.getString("qr_code");
//        System.out.println(code);
//        System.out.println(qrUrl);

//        final AtomicInteger count = new AtomicInteger(0);
//        final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
//        executorService.scheduleAtFixedRate(new Runnable() {
//            @Override
//            public void run() {
//                System.out.println("run "+ System.currentTimeMillis());
//                System.out.println(count.getAndIncrement());
//                if (count.get() > 5) {
//                    /*关闭轮询线程*/
//                    executorService.shutdown();
//                }
//                try {
//                    new AlipayUtil().query();
//                } catch (AlipayApiException e) {
//                    e.printStackTrace();
//                }
//
//            }
//        }, 0, 1000, TimeUnit.MILLISECONDS);
    }

}


