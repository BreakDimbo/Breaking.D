package com.limbo.shopping.dao.impl;

import com.limbo.shopping.dao.CategoryDao;
import com.limbo.shopping.model.Category;
import com.limbo.shopping.util.Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Break.D on 7/11/16.
 */
public class CategoryImpl implements CategoryDao {
    @Override
    public void save(Category c) {
        Connection connection = Util.getConnection();
        String sql = "";
        if (c.getId() == -1) {
            sql = "insert into category values(null, ?, ?, ?, ?, ?)";
        } else {
            sql = "insert into category values(" + c.getId() + ", ?, ?, ?, ?, ?)";
        }
        PreparedStatement prestat = Util.getPreStatement(connection, sql);
        try {
            prestat.setInt(1, c.getPid());
            prestat.setString(2, c.getName());
            prestat.setString(3, c.getDescr());
            prestat.setInt(4, c.isLeaf() ? 0 : 1);
            prestat.setInt(5, c.getGrade());
            prestat.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Util.close(prestat);
            Util.close(connection);
        }
    }

    @Override
    public void delete(int id, int pid) {
        Connection conn = Util.getConnection();
        String sql = "select * from category where pid = " + id;
        ResultSet rs = Util.getResultSet(conn, sql);
        ResultSet rsCount = Util.getResultSet(conn, "select count(*) from category where pid = " + pid);
        try {
            while (rs.next()) {
                delete(rs.getInt("id"), pid);
            }
            Util.update(conn, "delete from category where id = " + id);

            if(rsCount.next()) {
                Util.update(conn, "update category set leaf = 0 where id = " + pid);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Util.close(rsCount);
            Util.close(rs);
            Util.close(conn);
        }
    }

    @Override
    public void update(Category c) {
        Connection conn = Util.getConnection();
        String sql = "update category set name = ?, descr = ? where id = ?";
        PreparedStatement preparedStatement = Util.getPreStatement(conn, sql);

        try {
            preparedStatement.setString(1, c.getName());
            preparedStatement.setString(2, c.getDescr());
            preparedStatement.setInt(3, c.getId());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Util.close(preparedStatement);
            Util.close(conn);
        }

    }


    @Override
    public void getCategories(List<Category> categories, int id) {
        Connection connection = Util.getConnection();
        String sql = "select * from category where pid = " + id;
        ResultSet rs = Util.getResultSet(connection, sql);

        try {
            while (rs.next()) {
                Category c = new Category();
                c.setId(rs.getInt("id"));
                c.setName(rs.getString("name"));
                c.setPid(rs.getInt("pid"));
                c.setGrade(rs.getInt("grade"));
                c.setLeaf(rs.getInt("leaf") == 0);
                c.setDescr(rs.getString("descr"));
                categories.add(c);
                if (!c.isLeaf()) {
                    getCategories(categories, c.getId());
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Util.close(rs);
            Util.close(connection);
        }
    }


    @Override
    public void saveChild(int pid, int grade, String name, String descr) {
        Connection connection = null;

        String sql = "insert into category values(null, ?, ?, ?, ?, ?)";

        PreparedStatement prestat = null;

        try {
            connection = Util.getConnection();
            //事务处理
            connection.setAutoCommit(false);
            prestat = Util.getPreStatement(connection, sql);
            //添加子节点
            prestat.setInt(1, pid);
            prestat.setString(2, name);
            prestat.setString(3, descr);
            prestat.setInt(4, 0);
            prestat.setInt(5, grade + 1);
            prestat.executeUpdate();

            //更改父节点的 设置为非叶子节点
            Util.update(connection, "update category set leaf = 1 where id = " + pid);

            connection.commit();
            connection.setAutoCommit(true);

        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        } finally {
            Util.close(prestat);
            Util.close(connection);
        }
    }

    @Override
    public Category loadById(int id) {
        Category c = new Category();
        Connection conn = Util.getConnection();
        ResultSet rs = Util.getResultSet(conn, "select * from category where id = " + id);
        try {
            rs.next();
            c.setId(rs.getInt("id"));
            c.setDescr(rs.getString("descr"));
            c.setLeaf(rs.getInt("leaf") == 0);
            c.setName(rs.getString("name"));
            c.setGrade(rs.getInt("grade"));
            c.setPid(rs.getInt("pid"));
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Util.close(rs);
            Util.close(conn);
        }

        return c;
    }

}
