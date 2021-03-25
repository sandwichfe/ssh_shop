package com.lww.utils.JDBCUtil.handler;
import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 通过反射将一个数据库的结果集中，对象所有的属性设置进去
 *
 * @param <T>
 */
public class PropSet<T> {
    /**
     *
     * @param rs    数据库结果集对象
     * @param classType   对象的字节码类型
     * @return      返回对应的对象类型（根据传进来的字节码不同而类型不同）
     * @throws Exception
     */
    public T propValueSetIn(ResultSet rs,Class<T> classType) throws Exception{
        T obj = classType.newInstance();
        //2.取出结果集中当前光标所在行的某一列的数据
        BeanInfo beanInfo = Introspector.getBeanInfo(classType, Object.class);    //反射
        //获取此对象中存在的所有属性
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        //遍历属性
        for (PropertyDescriptor pd : propertyDescriptors) {
            String columnName = pd.getName();     //获取对象的属性名
            //根据此属性名 找出数据库返回的结果集中同名的列名的数据
            try {
                Object val = rs.getObject(columnName);
                //调用该对象的set方法，将值设置进去
                //System.out.println("propsetin....");
                pd.getWriteMethod().invoke(obj, val);     //将遍历到的当期属性  给他设置对应的值进去     。。。下一次遍历下一个属性
            } catch (SQLException e) {
                //这里的异常主要是  数据库结果集中有Bean中找不到的字段就会报此异常  但不应该影响程序的下一步执行
                // 让他不要循环很多每一次都打印， 如果一次以上就只打印一次
                    //System.out.println(e.getMessage());
            }
        }
        return obj;
    }
}
