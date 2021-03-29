package com.lww.sevice;

import com.lww.vo.Order;
import com.lww.vo.OrderItem;

import java.util.List;

public interface OrderService {

    void save(Order order);

    List<Order> getOrdersByUid(Integer uid);

    List<Order> findAll();

    List<OrderItem> getItemsByOid(Integer Oid);

    Order getByOid(Integer Oid);

    void updateState(Order currentOrder);
}
