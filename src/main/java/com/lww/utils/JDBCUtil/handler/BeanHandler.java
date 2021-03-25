package com.lww.utils.JDBCUtil.handler;


import java.sql.ResultSet;

//把结果集中的一行数据封装称一个对象， 专门针对结果集只有一行数据的情况   也就是单个查询的情况
public class BeanHandler<T> implements IResultSetHandler {

    private Class<T>  classType;            //用于表示被封装的数据类型
    //构造器
    public BeanHandler(Class<T> classType){                      //new BeanHandler（User.class）  此时T就是User
        this.classType=classType;
    }
    public Object handle(ResultSet rs) throws Exception {
        T obj=null;
        if (rs.next()){
            PropSet propSet = new PropSet();
           obj = (T)propSet.propValueSetIn(rs, classType);
        }
       return obj;
    }
}
