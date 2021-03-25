package com.lww.utils.JDBCUtil;
import com.lww.utils.JDBCUtil.handler.IResultSetHandler;

import java.sql.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//Jdbc操作的模板类
public class JdbcTemplate {
    //Object...params    可变参数 ，底层其实就是个数组

    /**
     * DML操作（增删改）的模板
     *
     * @param sql    DML操作的SQL模板（带有占位符？）
     * @param params SQL模板中？ 对应的参数值
     * @return   返回所有自动生成的主键
     */
    public static Map<String,Object> updateAndGetKey(String sql, Object... params) {

        Connection connection = null;
        PreparedStatement ps = null;
        try {
            connection = JdbcUtil.getConn();
            ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            //为预编译语句对象设置占位符参数
            for (int index = 0; index < params.length; index++) {
                //通过SetObject方法设置      第几个问号      第几个参数         问号从一开始  （"1","参数值"）   （"2","参数值"）
                ps.setObject(index + 1, params[index]);
            }
            ps.executeUpdate();
            Map<String,Object> alls=new HashMap<>();
            alls.put("resultSet",ps.getGeneratedKeys());
            alls.put("PreparedStatement",ps);
            alls.put("Connection",connection);
            return alls;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
           //JdbcUtil.close(connection, ps, null);
        }
        return null;
    }

    /**
     * DML操作（增删改）的模板
     *
     * @param sql    DML操作的SQL模板（带有占位符？）
     * @param params SQL模板中？ 对应的参数值
     * @return   返回所有自动生成的主键
     */
    public static int update(String sql, Object... params) {

        Connection connection = null;
        PreparedStatement ps = null;
        try {
            connection = JdbcUtil.getConn();
            ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            //为预编译语句对象设置占位符参数
            for (int index = 0; index < params.length; index++) {
                //通过SetObject方法设置      第几个问号      第几个参数         问号从一开始  （"1","参数值"）   （"2","参数值"）
                ps.setObject(index + 1, params[index]);
            }
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JdbcUtil.close(connection, ps, null);
        }
        return 0;
    }

    /**
     * 查询操作模板（DQL）
     * @param sql      DML操作的SQL模板（带有占位符？）
     * @param params   SQL模板中？ 对应的参数值
     * @return     list集合
     */
    public static <T>T query(String sql, IResultSetHandler<T> rsh, Object... params) {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            connection=JdbcUtil.getConn();
            ps=connection.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);    //设置resultSet的光标可移动
            //为预编译语句对象设置占位符参数
            for (int index = 0; index < params.length; index++) {
                //通过SetObject方法设置      第几个问号      第几个参数         问号从一开始  （"1","参数值"）   （"2","参数值"）
                ps.setObject(index + 1, params[index]);
            }
            rs= ps.executeQuery();      //得到查询的结果
            //处理结果集，把每一行封装成一个对象
            return rsh.handle(rs);
        } catch (Exception e) {
                    e.printStackTrace();
        } finally {
                JdbcUtil.close(connection,ps,rs);
        }

        throw new RuntimeException("查询出错！");
    }

}
