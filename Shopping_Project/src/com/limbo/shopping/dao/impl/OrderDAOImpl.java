package com.limbo.shopping.dao.impl;

import com.limbo.shopping.dao.OrderDAO;
import com.limbo.shopping.model.Product;
import com.limbo.shopping.model.SalesItem;
import com.limbo.shopping.model.SalesOrder;
import com.limbo.shopping.model.User;
import com.limbo.shopping.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Break.D on 7/15/16.
 */
public class OrderDAOImpl implements OrderDAO {
    @Override
    public int add(SalesOrder so) {
        int orderId = -1;

        Connection conn = null;
        String sql = "insert into salesorder values (null, ?, ?, ? ,?)";
        PreparedStatement pstmt = null;
        String sqlDetail = "insert into salesitem values (null, ?, ?, ?, ?)";
        PreparedStatement pstmtDetail = null;
        ResultSet rsKey = null;
        try {
            conn = Util.getConnection();
            conn.setAutoCommit(false);
            pstmt = Util.getPreStatement(conn, sql,
                    Statement.RETURN_GENERATED_KEYS);

            pstmtDetail = Util.getPreStatement(conn, sqlDetail);

            pstmt.setInt(1, so.getUser().getId());
            pstmt.setString(2, so.getAddr());
            pstmt.setTimestamp(3, new Timestamp(so.getODate().getTime()));
            pstmt.setInt(4, so.getStatus());
            pstmt.executeUpdate();
            rsKey = pstmt.getGeneratedKeys();
            rsKey.next();
            orderId = rsKey.getInt(1);

            List<SalesItem> items = so.getItems();
            Iterator<SalesItem> it = items.iterator();
            while (it.hasNext()) {
                SalesItem si = it.next();
                pstmtDetail.setInt(1, si.getProduct().getId());
                pstmtDetail.setDouble(2, si.getUnitPrice());
                pstmtDetail.setInt(3, si.getCount());
                pstmtDetail.setInt(4, orderId);
                pstmtDetail.addBatch();
            }
            pstmtDetail.executeBatch();
            conn.commit();
            conn.setAutoCommit(false);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

            Util.close(rsKey);
            Util.close(pstmtDetail);
            Util.close(pstmt);
            Util.close(conn);
        }

        return orderId;
    }


    @Override
    public int update(SalesOrder so) {
        return 0;
    }

    @Override
    public List<SalesOrder> getOrders(User u) {
        List<SalesOrder> orders = new ArrayList<>();
        Connection conn = Util.getConnection();
        ResultSet rs = Util.getResultSet(conn, "select * from salesorder where userid = " + u.getId());

        try {
            while (rs.next()) {
                SalesOrder order = new SalesOrder();
                order.setId(rs.getInt("id"));
                order.setAddr(rs.getString("addr"));
                order.setODate(new Date(rs.getTimestamp("odate").getTime()));
                order.setStatus(rs.getInt("status"));
                orders.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Util.close(rs);
            Util.close(conn);
        }

        return orders;
    }

    @Override
    public int getOrders(List<SalesOrder> orders, int pageNo, int pageSize) {
        Connection conn = null;
        ResultSet rs = null;
        ResultSet rsCount = null;
        int totalRecords = 0;
        try {
            conn = Util.getConnection();
            rsCount = Util.getResultSet(conn,
                    "select count(*) from salesorder");
            rsCount.next();
            totalRecords = rsCount.getInt(1);
            String sql = "select salesorder.id, salesorder.userid, salesorder.odate, salesorder.addr, salesorder.status , "
                    + " user.id uid, user.username, user.password, user.addr uaddr, user.phone, user.rdate from salesorder "
                    + " left join user on (salesorder.userid = user.id)"
                    + " limit " + (pageNo - 1) * pageSize + "," + pageSize;
            System.out.println(sql);
            rs = Util.getResultSet(conn, sql);
            while (rs.next()) {
                User u = new User();
                u.setId(rs.getInt("uid"));
                u.setAddr(rs.getString("uaddr"));
                u.setUsername(rs.getString("username"));
                u.setPassword(rs.getString("password"));
                u.setPhone(rs.getString("phone"));
                u.setRdate(rs.getTimestamp("rdate"));

                SalesOrder so = new SalesOrder();
                so.setId(rs.getInt("id"));
                so.setAddr(rs.getString("addr"));
                so.setODate(rs.getTimestamp("odate"));
                so.setStatus(rs.getInt("status"));
                so.setUser(u);

                orders.add(so);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

            Util.close(rs);
            Util.close(rsCount);
            Util.close(conn);
        }

        return totalRecords;
    }

    @Override
    public void delete(int id) {

    }

    @Override
    public SalesOrder loadById(int id) {

        Connection conn = Util.getConnection();
        ResultSet rs = null;
        SalesOrder so = null;
        try {
            String sql = "select salesorder.id, salesorder.userid, salesorder.odate, salesorder.addr, salesorder.status , " +
                    " user.id uid, user.username, user.password, user.addr uaddr, user.phone, user.rdate from salesorder " +
                    " join user on (salesorder.userid = user.id) where salesorder.id = " + id;
            rs = Util.getResultSet(conn, sql);
            if (rs.next()) {
                User u = new User();
                u.setId(rs.getInt("uid"));
                u.setAddr(rs.getString("uaddr"));
                u.setUsername(rs.getString("username"));
                u.setPassword(rs.getString("password"));
                u.setPhone(rs.getString("phone"));
                u.setRdate(rs.getTimestamp("rdate"));

                so = new SalesOrder();
                so.setId(rs.getInt("id"));
                so.setAddr(rs.getString("addr"));
                so.setODate(rs.getTimestamp("odate"));
                so.setStatus(rs.getInt("status"));
                so.setUser(u);


            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Util.close(rs);
            Util.close(conn);
        }

        return so;
    }

    @Override
    public void delete(String conditionStr) {

    }

    @Override
    public int find(List<SalesOrder> products, int pageNo, int pageSize, String queryStr) {
        return 0;
    }

    @Override
    public List<SalesItem> getSalesItems(int orderId) {

        Connection conn = Util.getConnection();
        ResultSet rs = null;
        List<SalesItem> items = new ArrayList<>();
        try {

            String sql = "select salesorder.id, salesorder.userid, salesorder.odate, salesorder.addr, salesorder.status , " +
                    " salesitem.id itemid, salesitem.productid, salesitem.unitprice, salesitem.pcount, salesitem.orderid, " +
                    " product.id pid, product.name, product.descr, product.normalprice, product.memberprice, product.pdate, product.categoryid" +
                    " from salesorder join salesitem on (salesorder.id = salesitem.orderid)" +
                    " join product on (salesitem.productid = product.id) where salesorder.id = " + orderId;
            System.out.println(sql);
            rs = Util.getResultSet(conn, sql);
            while (rs.next()) {
                Product p = new Product();
                p.setId(rs.getInt("pid"));
                p.setCategoryId(rs.getInt("categoryid"));
                p.setName(rs.getString("name"));
                p.setDescr(rs.getString("descr"));
                p.setPdate(rs.getTimestamp("pdate"));
                p.setNormalPrice(rs.getDouble("normalprice"));
                p.setMemberPrice(rs.getDouble("memberprice"));

                SalesItem si = new SalesItem();
                si.setOrderId(orderId);
                si.setId(rs.getInt("itemid"));
                si.setUnitPrice(rs.getDouble("unitprice"));
                si.setCount(rs.getInt("pcount"));
                si.setProduct(p);

                items.add(si);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Util.close(rs);
            Util.close(conn);
        }

        return items;
    }

    @Override
    public void updateStatus(SalesOrder order) {
        Connection conn = Util.getConnection();
        try {
            String sql = "update salesorder set status = " + order.getStatus() + " where id = " + order.getId();
            Util.update(conn, sql);
        } finally {
            Util.close(conn);
        }
    }
}
