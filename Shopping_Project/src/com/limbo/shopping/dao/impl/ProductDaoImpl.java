package com.limbo.shopping.dao.impl;

import com.limbo.shopping.dao.ProductDao;
import com.limbo.shopping.model.Category;
import com.limbo.shopping.model.Product;
import com.limbo.shopping.util.Util;

import java.sql.*;
import java.util.List;

/**
 * Created by Break.D on 7/13/16.
 */
public class ProductDaoImpl implements ProductDao {
    @Override
    public void save(Product p) {
        Connection connection = Util.getConnection();
        String sql = "insert into product values(null, ?, ?, ?, ?, ?, ?)";
        PreparedStatement prestat = Util.getPreStatement(connection, sql);
        try {
            prestat.setString(1, p.getName());
            prestat.setString(2, p.getDescr());
            prestat.setDouble(3, p.getNormalPrice());
            prestat.setDouble(4, p.getMemberPrice());
            prestat.setTimestamp(5, new Timestamp(p.getPdate().getTime()));
            prestat.setInt(6, p.getCategoryId());
            prestat.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Util.close(prestat);
            Util.close(connection);
        }
    }

    @Override
    public void delete(Product p) {
        Connection conn = Util.getConnection();
        Statement stat = Util.getStatement(conn);
        try {
            stat.executeUpdate("delete from product where id = " + p.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Util.close(stat);
            Util.close(conn);
        }

    }

    @Override
    public void update(Product p) {
        Connection conn = Util.getConnection();
        PreparedStatement prestate = Util.getPreStatement(conn, "update product set name = ?, descr = ?, normalprice = ?," +
                " memberprice = ?, pdate = ?, categoryid = ? where id = ?");

        try {
            prestate.setString(1, p.getName());
            prestate.setString(2, p.getDescr());
            prestate.setDouble(3, p.getNormalPrice());
            prestate.setDouble(4, p.getMemberPrice());
            prestate.setTimestamp(5, new Timestamp(p.getPdate().getTime()));
            prestate.setInt(6, p.getCategoryId());
            prestate.setInt(7, p.getId());
            prestate.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Util.close(prestate);
            Util.close(conn);
        }
    }

/*    @Override
    //取得所有的产品以及其所属的类别的信息
    public void getProducts(List<Product> products) {
        Connection connection = null;
        ResultSet rs = null;
        String sql = "select p.id p_id, p.name p_name, p.descr p_descr, pdate, categoryid, memberprice, normalprice, " +
                "c.id c_id, c.name c_name, c.descr c_descr, c.pid, c.grade, c.leaf from product p join category c on p.categoryid = c.id";

        try {
            connection = Util.getConnection();
            rs = Util.getResultSet(connection, sql);

            while (rs.next()) {
                Product product = new Product();
                product.setId(rs.getInt("p_id"));
                product.setName(rs.getString("p_name"));
                product.setDescr(rs.getString("p_descr"));
                product.setPdate(new Date(rs.getTimestamp("pdate").getTime()));
                product.setCategoryId(rs.getInt("categoryid"));
                product.setMemberPrice(rs.getDouble("memberprice"));
                product.setNormalPrice(rs.getDouble("normalprice"));

                Category category = new Category();
                category.setId(rs.getInt("c_id"));
                category.setPid(rs.getInt("pid"));
                category.setName(rs.getString("c_name"));
                category.setGrade(rs.getInt("grade"));
                category.setDescr(rs.getString("c_descr"));
                category.setLeaf(rs.getInt("leaf") == 0);

                product.setCategory(category);
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Util.close(rs);
            Util.close(connection);
        }
    }*/

    //根据所给页码和每页展示的产品数显示产品
    @Override
    public int getProducts(List<Product> products, int pageNum, int pageSize, int lazy) {
        Connection connection = null;
        ResultSet rs = null;
        ResultSet rsCount = null;
        int productNum = 0;
        String sql = "select p.id p_id, p.name p_name, p.descr p_descr, pdate, categoryid, memberprice, normalprice, " +
                "c.id c_id, c.name c_name, c.descr c_descr, c.pid, c.grade, c.leaf from product p join category c on p.categoryid = c.id " +
                "limit " + (pageNum - 1) * pageSize + "," + pageNum * pageSize;

        try {
            connection = Util.getConnection();
            rs = Util.getResultSet(connection, sql);
            rsCount = Util.getResultSet(connection, "select count(*) from product");
            rsCount.next();
            productNum = rsCount.getInt(1);

            while (rs.next()) {
                Product product = new Product();
                product.setId(rs.getInt("p_id"));
                product.setName(rs.getString("p_name"));
                product.setDescr(rs.getString("p_descr"));
                product.setPdate(new Date(rs.getTimestamp("pdate").getTime()));
                product.setCategoryId(rs.getInt("categoryid"));
                product.setMemberPrice(rs.getDouble("memberprice"));
                product.setNormalPrice(rs.getDouble("normalprice"));

                Category category = new Category();
                category.setId(rs.getInt("c_id"));
                category.setPid(rs.getInt("pid"));
                category.setName(rs.getString("c_name"));
                category.setGrade(rs.getInt("grade"));
                category.setDescr(rs.getString("c_descr"));
                category.setLeaf(rs.getInt("leaf") == 0);

                product.setCategory(category);
                products.add(product);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Util.close(rs);
            Util.close(rsCount);
            Util.close(connection);
        }

        return productNum;
    }

    @Override
    public Product loadById(int id) {
        Product product = new Product();
        Connection connection = Util.getConnection();
        ResultSet rs = Util.getResultSet(connection, "select * from product where id = " + id);

        try {
            while (rs.next()) {
                product.setId(rs.getInt("id"));
                product.setName(rs.getString("name"));
                product.setDescr(rs.getString("descr"));
                product.setPdate(new Date(rs.getTimestamp("pdate").getTime()));
                product.setCategoryId(rs.getInt("categoryid"));
                product.setMemberPrice(rs.getDouble("memberprice"));
                product.setNormalPrice(rs.getDouble("normalprice"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Util.close(rs);
            Util.close(connection);
        }
        return product;
    }

    @Override
    public int find(List<Product> products, int pageNo, int pageSize, String queryStr) {
        int totalRecords = -1;
        Connection conn = Util.getConnection();

        String sql = "";
        sql = "select p.id productid, p.name pname, p.descr pdescr, p.normalprice, " +
                " p.memberprice, p.pdate, p.categoryid , " +
                " c.id categoryid, c.name cname, c.descr cdescr, c.pid, c.grade " +
                " from product p join category c on (p.categoryid = c.id)" + queryStr +
                " order by p.pdate desc";

        sql += " limit " + (pageNo - 1) * pageSize + "," + pageSize;
        System.out.println(sql);

        ResultSet rs = Util.getResultSet(conn, sql);

        ResultSet rsCount = Util.getResultSet(conn,
                "select count(*) from product " + queryStr.replaceAll("p\\.", ""));

        try {
            rsCount.next();
            totalRecords = rsCount.getInt(1);

            while (rs.next()) {
                Product p = null;
                p = new Product();
                p.setId(rs.getInt("productid"));
                p.setName(rs.getString("pname"));
                p.setDescr(rs.getString("pdescr"));
                p.setNormalPrice(rs.getDouble("normalprice"));
                p.setMemberPrice(rs.getDouble("memberprice"));
                p.setPdate(rs.getTimestamp("pdate"));
                p.setCategoryId(rs.getInt("categoryid"));

                Category c = new Category();
                c.setId(rs.getInt("categoryid"));
                c.setName(rs.getString("cname"));
                c.setDescr(rs.getString("cdescr"));
                c.setPid(rs.getInt("pid"));
                c.setGrade(rs.getInt("grade"));

                p.setCategory(c);

                products.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Util.close(rsCount);
            Util.close(rs);
            Util.close(conn);
        }

        return totalRecords;
    }

    @Override
    public void getAllProducts(List<Product> products) {
        Product product = null;
        Connection connection = Util.getConnection();
        ResultSet rs = Util.getResultSet(connection, "select * from product");

        try {
            while (rs.next()) {
                product = new Product();
                product.setId(rs.getInt("id"));
                product.setName(rs.getString("name"));
                product.setDescr(rs.getString("descr"));
                product.setPdate(new Date(rs.getTimestamp("pdate").getTime()));
                product.setCategoryId(rs.getInt("categoryid"));
                product.setMemberPrice(rs.getDouble("memberprice"));
                product.setNormalPrice(rs.getDouble("normalprice"));
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Util.close(rs);
            Util.close(connection);
        }
    }

}
