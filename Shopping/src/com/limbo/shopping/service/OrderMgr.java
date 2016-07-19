package com.limbo.shopping.service;

import com.limbo.shopping.dao.OrderDAO;
import com.limbo.shopping.dao.impl.OrderDAOImpl;
import com.limbo.shopping.model.Cart;
import com.limbo.shopping.model.SalesItem;
import com.limbo.shopping.model.SalesOrder;
import com.limbo.shopping.model.User;

import java.util.List;

/**
 * Created by Break.D on 7/15/16.
 */
public class OrderMgr {
    private static OrderMgr orderMgr = null;

    private static OrderDAO orderDAO = new OrderDAOImpl();

    private OrderMgr() {}

    public static OrderMgr getInstance() {
        if(orderMgr == null) {
            orderMgr = new OrderMgr();
        }
        return orderMgr;
    }

    public int add(SalesOrder so) {
        return orderDAO.add(so);
    }

    public int userBuy(Cart c, User u) {
        return u.buy(c);
    }

    public int getOrders(List<SalesOrder> list, int pageNo, int pageSize){
        return orderDAO.getOrders(list, pageNo, pageSize);
    }

    public SalesOrder loadById(int id) {
        return orderDAO.loadById(id);
    }

    public List<SalesItem> getSalesItems(SalesOrder order) {
        return orderDAO.getSalesItems(order.getId());
    }

    public void updateStatus(SalesOrder order) {
        orderDAO.updateStatus(order);
        //可以用dao.update来更新整个对象，这是一种更普遍的用法
    }

    public List<SalesOrder> getOrders(User u) {
        return orderDAO.getOrders(u);
    }
}
