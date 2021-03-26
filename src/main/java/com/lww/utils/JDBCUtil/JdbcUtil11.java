package com.lww.utils.JDBCUtil;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

/**
 * Jdbc工具类
 */
public class JdbcUtil11 {


    private static DataSource ds = null;

    //静态代码块，当此类的字节码文件被加载进虚拟机时，就会立刻执行此内的代码
    static {

        Properties properties = new Properties();
        try {
            //加载和读取properties文件
            InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("db.properties");
            properties.load(inputStream);

            //根据德鲁伊连接池得到DataSources对象
            ds = DruidDataSourceFactory.createDataSource(properties);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
