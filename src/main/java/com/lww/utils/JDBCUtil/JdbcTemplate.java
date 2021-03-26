package com.lww.utils.JDBCUtil;

import com.alibaba.druid.pool.DruidDataSource;
import com.lww.utils.JDBCUtil.handler.IResultSetHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

//Jdbc操作的模板类
@Component
public class JdbcTemplate {

    @Autowired
    DruidDataSource dataSource;
    //创建并返回一个Connection对象
    public Connection getConn() {
        try {
            //从连接池中得到连接对象
            Connection conn = dataSource.getConnection();
            //System.out.println(conn);
            return conn;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    //Object...params    可变参数 ，底层其实就是个数组

    /**
     * DML操作（增删改）的模板
     *
     * @param sql    DML操作的SQL模板（带有占位符？）
     * @param params SQL模板中？ 对应的参数值
     * @return 返回所有自动生成的主键
     */
    public Integer updateAndGetKey(String sql, Object... params) {

        Connection connection = null;
        PreparedStatement ps = null;
        try {
            connection = getConn();
            ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            //为预编译语句对象设置占位符参数
            for (int index = 0; index < params.length; index++) {
                //通过SetObject方法设置      第几个问号      第几个参数         问号从一开始  （"1","参数值"）   （"2","参数值"）
                ps.setObject(index + 1, params[index]);
            }
            ps.executeUpdate();
            ResultSet rs=ps.getGeneratedKeys();
            Integer generatorId=null;
            if (rs.next()) {
                System.out.println("jj");
                generatorId = rs.getInt(1);
            }
            return generatorId;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
           close(connection, ps, null);
        }
        return null;

    }

    /**
     * DML操作（增删改）的模板
     *
     * @param sql    DML操作的SQL模板（带有占位符？）
     * @param params SQL模板中？ 对应的参数值
     * @return 返回所有自动生成的主键
     */
    public int update(String sql, Object... params) {
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            connection = getConn();
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
            close(connection, ps, null);
        }
        return 0;
    }

    /**
     * 查询操作模板（DQL）
     *
     * @param sql    DML操作的SQL模板（带有占位符？）
     * @param params SQL模板中？ 对应的参数值
     * @return list集合
     */
    public <T> T query(String sql, IResultSetHandler<T> rsh, Object... params) {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            connection = getConn();
            ps = connection.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);    //设置resultSet的光标可移动
            //为预编译语句对象设置占位符参数
            for (int index = 0; index < params.length; index++) {
                //通过SetObject方法设置      第几个问号      第几个参数         问号从一开始  （"1","参数值"）   （"2","参数值"）
                ps.setObject(index + 1, params[index]);
            }
            rs = ps.executeQuery();      //得到查询的结果
            //处理结果集，把每一行封装成一个对象
            return rsh.handle(rs);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(connection, ps, rs);
        }

        throw new RuntimeException("查询出错！");
    }

    //用于关闭连接对象的方法   避免在具体执行每次都要写一次close等等的trycatch
    public void close(Connection conn, Statement sta, ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (Exception e) {
        } finally {
            try {
                if (sta != null) {
                    sta.close();
                }
            } catch (Exception e) {
            } finally {
                try {
                    if (conn != null) {
                        conn.close();
                    }
                } catch (Exception e) {
                }
            }
        }
    }

}
