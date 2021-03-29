package com.lww.sevice.serviceImpl;

import com.lww.Dao.OrderDao;
import com.lww.sevice.OrderService;
import com.lww.vo.Order;
import com.lww.vo.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderDao orderDao;

    @Override
    public void save(Order order) {
        orderDao.save(order);
    }

    @Override
    public List<Order> getOrdersByUid(Integer uid) {
        return orderDao.getOrdersByUid(uid);
    }

    @Override
    public List<Order> findAll() {
        return orderDao.findAll();
    }

    @Override
    public List<OrderItem> getItemsByOid(Integer Oid) {
        return orderDao.getItemsByOid(Oid);
    }

    @Override
    public Order getByOid(Integer Oid) {
        return orderDao.getByOid(Oid);
    }

    @Override
    public void updateState(Order currentOrder) {
        orderDao.updateState(currentOrder);
    }
}
