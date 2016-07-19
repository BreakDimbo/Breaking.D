package com.limbo.shopping.dao;

import com.limbo.shopping.model.SalesItem;
import com.limbo.shopping.model.SalesOrder;
import com.limbo.shopping.model.User;

import java.util.List;

/**
 * Created by Break.D on 7/15/16.
 */
public interface OrderDAO {
    int update(SalesOrder so);

    int add(SalesOrder so);

    List<SalesOrder> getOrders(User u);

    int getOrders(List<SalesOrder> orders, int pageNo, int pageSize);

    void delete(int id);

    SalesOrder loadById(int id);

    void delete(String conditionStr);

    int find(List<SalesOrder> products, int pageNo, int pageSize, String queryStr);

    List<SalesItem> getSalesItems(int orderId);

    void updateStatus(SalesOrder order);
}
