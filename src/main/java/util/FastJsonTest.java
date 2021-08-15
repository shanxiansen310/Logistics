package util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.entity.DriverInfo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FastJsonTest {



    public static void main(String[] args) {
//        JSONObject object = new JSONObject();
//        //string
//        object.put("string","string");
//        //int
//        object.put("int",2);
//        //boolean
//        object.put("boolean",true);
//        //array
//        List<Integer> integers = Arrays.asList(1,2,3);
//        object.put("list",integers);
//        //null
//        object.put("null",null);
//
//        User u=new User();
//        u.setId(123); u.setName("he");
//        object.put("User",u);
//
//        User u1=new User();
//        u1.setId(1); u1.setName("user1");
//        User u2=new User();
//        u2.setId(2); u2.setName("user2");
//        List<User> userList=new ArrayList<>();
//        userList.add(u1); userList.add(u2);
//        object.put("uList",userList);
//        System.out.println(object);

//        String str="{\"boolean\":true,\"User\":{\"id\":123,\"name\":\"he\"},\"string\":\"string\",\"uList\":[{\"id\":1,\"name\":\"user1\"},{\"id\":2,\"name\":\"user2\"}],\"list\":[1,2,3],\"int\":2}";
//
//        JSONObject object=JSONObject.parseObject(str);
//        //string
//        String s = object.getString("string");
//        System.out.println(s);
//        //int
//        int i = object.getIntValue("int");
//        System.out.println(i);
//        //boolean
//        boolean b = object.getBooleanValue("boolean");
//        System.out.println(b);
//        //list
//        List<Integer> integers = JSON.parseArray(object.getJSONArray("list").toJSONString(),Integer.class);
////        integers.forEach(System.out::println);
//        //null
//        System.out.println(object.getString("null"));
//
//        User uu=JSON.parseObject(object.getJSONObject("User").toJSONString(),User.class);
//        System.out.println(uu.getName());
//
//        List<User> userList=JSON.parseArray(object.getJSONArray("uList").toJSONString(),User.class);
//        for (User u:userList){
//            System.out.println(u.getName());
//        }

        String driver="{\"driverList\":[{\"date\":\"1-3\",\"name\":\"dr1\",\"price\":\"170\"},{\"date\":\"1-4\",\"name\":\"driver\",\"price\":\"180\"},{\"date\":\"1-6\",\"name\":\"jin\",\"price\":\"300\"},{\"date\":\"1-1\",\"name\":\"mugen\",\"price\":\"50\"},{\"date\":\"1-1\",\"name\":\"riki\",\"price\":\"1200\"},{\"date\":\"1-2\",\"name\":\"spike\",\"price\":\"700\"}]}";
        JSONObject object=JSONObject.parseObject(driver);

        List<DriverInfo> driverInfoList=JSONObject.parseArray(object.getJSONArray("driverList").toJSONString(),DriverInfo.class);

        System.out.println(driverInfoList.get(0).getName());

    }
}
class User{

    int id;
    String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}