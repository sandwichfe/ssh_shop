package com.lww.utils.JDBCUtil.handler;


import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

//把结果集中的一行数据封装称一个对象， 专门针对结果集有多条数据的情况
public class BeanListHandler<T> implements IResultSetHandler<List<T>> {             //IResultSetHandler<    List<T>   >      这里写什么就决定返回类型是什么


    private Class<T> classType;            //用于表示被封装的数据类型

    //构造器
    public BeanListHandler(Class<T> classType) {                      //new BeanHandler（User.class）  此时T就是User
        this.classType = classType;
    }

    @Override
    public List<T> handle(ResultSet rs) throws Exception {
        List<T> list = new ArrayList<>();
        while (rs.next()) {
            PropSet propSet = new PropSet();
            T obj = (T)propSet.propValueSetIn(rs, classType);
            list.add(obj);
        }
        return list;
    }
}
