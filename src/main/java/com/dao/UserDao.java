package com.dao;

import com.entity.DriverInfo;
import com.entity.Order;
import com.util.DBUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.entity.User;

public class UserDao {

    /*查询是否存在某个User,用于验证用户登录*/
    public boolean query(User user) {
        Connection connection = DBUtils.getConnection();
        String sql = "select * from user where name = ? and password = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,user.getName());
            preparedStatement.setString(2,user.getPassword());

            ResultSet resultSet = preparedStatement.executeQuery();
            /*the first call to the method next makes the first row the current row*/
            return resultSet.next();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return false;
        }
        finally {
            DBUtils.closeConnection();
        }
    }

    public User getUser(String name){
        User user=new User();
        Connection connection = DBUtils.getConnection();
        /*选取第一个数据即可,因为司机可以发布多条信息*/
        String sql = "select * from user where name = ?  limit 0,1";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,name);
            ResultSet resultSet = preparedStatement.executeQuery();

            /*将数据库中的信息全部加入到list中*/
            while (resultSet.next()){
                user.setName(resultSet.getString("name"));
                user.setPassword(resultSet.getString("password"));
                user.setClientDate(resultSet.getString("clientDate"));
                user.setClientPrice(resultSet.getString("clientPrice"));
                user.setClientStart(resultSet.getString("clientStart"));
                user.setClientDest(resultSet.getString("clientDest"));
                user.setDriverCity(resultSet.getString("driverCity"));
                user.setDriverBasePrice(resultSet.getString("driverBasePrice"));
                user.setDriverPerPrice(resultSet.getString("driverPerPrice"));
            }

            return user;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return null;
        }
        finally {
            DBUtils.closeConnection();
        }
    }

    public boolean updateClient(User user)  {
        int result=0;   //result表示DML操作影响的行数,初始值为0
        String sql="update user set clientStart = ? , clientDest = ?  , clientPrice = ? , clientDate = ?" +
                " where name = ?";

        Connection connection= DBUtils.getConnection();
        PreparedStatement preparedStatement=null;
        try {
            preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setString(1,user.getClientStart());
            preparedStatement.setString(2,user.getClientDest());
            preparedStatement.setString(3,user.getClientPrice());
            preparedStatement.setString(4,user.getClientDate());
            preparedStatement.setString(5,user.getName());

            result=preparedStatement.executeUpdate();
            System.out.println("UserDao updateUser:modify "+ result);
            return preparedStatement.getUpdateCount() != 0;

        }catch (Exception e){
            e.printStackTrace();
            return false;
        }finally {
            DBUtils.closeConnection();
        }
    }

    public boolean insertDriver(User user)  {
        int result=0;   //result表示DML操作影响的行数,初始值为0
        String sql="insert into user(name,password,clientStart,clientDest,clientPrice,clientDate,driverBasePrice,driverPerPrice,driverDate,driverCity) " +
                "values(?,'123',null ,null ,null ,null ,?,?,?,?) ";

        Connection connection= DBUtils.getConnection();
        PreparedStatement preparedStatement=null;
        try {
            preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setString(1,user.getName());
            preparedStatement.setString(2,user.getDriverBasePrice());
            preparedStatement.setString(3,user.getDriverPerPrice());
            preparedStatement.setString(4,user.getDriverDate());
            preparedStatement.setString(5,user.getDriverCity());

            result=preparedStatement.executeUpdate();
            System.out.println("UserDao updateUser:modify "+ result);
            return preparedStatement.getUpdateCount() != 0;


        }catch (Exception e){
            e.printStackTrace();
            return false;
        }finally {
            DBUtils.closeConnection();
        }

    }

    /*返回司机available列表*/
    public List<DriverInfo> getDriver(){
        List<DriverInfo> driverInfoList=new ArrayList<>();
        Connection connection = DBUtils.getConnection();
        /*我这里很细心的做了一下排序哦!!!*/
        String sql = "select * from user where driverBasePrice is not null order by driverDate";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            /*将数据库中的信息全部加入到list中*/
            while (resultSet.next()){
                DriverInfo driverInfo=new DriverInfo();

                driverInfo.setName(resultSet.getString("name"));
                driverInfo.setDate(resultSet.getString("driverDate"));
                driverInfo.setBasePrice(resultSet.getString("driverBasePrice"));
                driverInfo.setPerPrice(resultSet.getString("driverPerPrice"));
                driverInfo.setCity(resultSet.getString("driverCity"));

                driverInfoList.add(driverInfo);
            }

            return driverInfoList;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return null;
        }
        finally {
            DBUtils.closeConnection();
        }
    }

    /*添加用户*/
    public boolean add(User user) {
        /*防止输入为空*/
        if (user.getName().equals("")||user.getPassword().equals("")){
            return false;
        }

        Connection connection = DBUtils.getConnection();
        String sql = "insert into user(name,password) values(?,?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,user.getName());
            preparedStatement.setString(2,user.getPassword());

            preparedStatement.executeUpdate();
            /* !=0 表示插入成功*/
            /* Retrieves the current result as an update count */
            return preparedStatement.getUpdateCount() != 0;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return false;
        }
        finally {
            DBUtils.closeConnection();
        }
    }

    /*添加订单*/
    public boolean addOrder(Order order) {
        /*防止输入为空*/
        if (order.getTradeNo().equals("")){
            return false;
        }
        Connection connection = DBUtils.getConnection();
        String sql = "insert into table_order(tradeno,driver,client,price)  values(?,?,?,?)";

//        StringBuffer sql=new StringBuffer("insert into order(tradeno," +
//                "driver,client,price) values(");
//        sql.append("'"+order.getTradeNo()+"',");
//        sql.append("'"+order.getDriverName()+"',");
//        sql.append("'"+order.getClientName()+"'");
//        sql.append("'"+order.getPrice()+"'");
//        sql.append(")");
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,order.getTradeNo());
            preparedStatement.setString(2,order.getDriverName());
            preparedStatement.setString(3,order.getClientName());
            preparedStatement.setString(4,order.getPrice());

            preparedStatement.executeUpdate();
            /* !=0 表示插入成功*/
            /* Retrieves the current result as an update count */
            return preparedStatement.getUpdateCount() != 0;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return false;
        }
        finally {
            DBUtils.closeConnection();
        }
    }



    public static void main(String[] args) {
        /*driverList检测*/
        List<DriverInfo> list=new UserDao().getDriver();
        for (DriverInfo d : list) {
            System.out.println(d.getName());
        }
        User u=new User(); u.setName("User3"); u.setPassword("123");
        System.out.println(new UserDao().query(u));

//        User u=new UserDao().getUser("User3");
//        System.out.println("u = " + u.getClientDate());

//        User u=new User();
//        u.setName("Faye");


//        System.out.println(new UserDao().updateDriver(u));

    }


}
