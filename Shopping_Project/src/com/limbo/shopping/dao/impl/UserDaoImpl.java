package com.limbo.shopping.dao.impl;

import com.limbo.shopping.dao.UserDao;
import com.limbo.shopping.exception.UserNotFoundException;
import com.limbo.shopping.exception.WrongPasswordException;
import com.limbo.shopping.model.User;
import com.limbo.shopping.util.Util;

import java.sql.*;
import java.util.List;

/**
 * Created by Break.D on 7/11/16.
 */
public class UserDaoImpl implements UserDao {
    @Override
    public boolean checkUser(User u) {
        boolean exists = false;
        Connection connection = Util.getConnection();
        String sql = "select count(*) from user where username = " + "'" + u.getUsername() + "'";
//        System.out.print(sql);
        ResultSet rs = Util.getResultSet(connection, sql);
        try {
            rs.next();
            if (rs.getInt(1) != 0)
                exists = true;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Util.close(rs);
            Util.close(connection);
        }
        return exists;
    }

    @Override
    public User checkUser(String username, String password) throws UserNotFoundException, WrongPasswordException {
        Connection connection = Util.getConnection();
        String sql = "select * from user where username = " + "'" + username + "'";
        ResultSet rs = Util.getResultSet(connection, sql);
        User u = null;

        try {

            if (!rs.next()) {
                throw new UserNotFoundException("用户不存在:" + username);
            } else {

                if (!rs.getString("password").equals(password)) {
                    throw new WrongPasswordException("密码不正确!");
                }

                u = new User();
                u.setPhone(rs.getString("phone"));
                u.setAddr(rs.getString("addr"));
                u.setPassword(password);
                u.setUsername(username);
                u.setId(rs.getInt("id"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Util.close(rs);
            Util.close(connection);
        }

        return u;

    }

    @Override
    public void save(User u) {
        Connection connection = Util.getConnection();
        String sql = "insert into user values(null, ?, ?, ?, ?, ?)";
        PreparedStatement prestat = Util.getPreStatement(connection, sql);
        try {
            prestat.setString(1, u.getUsername());
            prestat.setString(2, u.getPassword());
            prestat.setString(3, u.getPhone());
            prestat.setString(4, u.getAddr());
            prestat.setTimestamp(5, new Timestamp(u.getRdate().getTime()));
            prestat.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Util.close(prestat);
            Util.close(connection);
        }
    }

    @Override
    public boolean delete(int id) {
        Connection conn = Util.getConnection();
        Statement stat = Util.getStatement(conn);
        boolean success = false;
        try {
            stat.executeUpdate("delete from user where id = " + id);
            success = true;
        } catch (SQLException e) {
            e.printStackTrace();
            success = false;
        } finally {
            Util.close(stat);
            Util.close(conn);
        }

        return success;
    }

    @Override
    public void update(User u) {
        Connection conn = Util.getConnection();
        PreparedStatement prestate = Util.getPreStatement(conn, "update user set phone = ?, addr = ?, password = ? where id = ?");

        try {
            prestate.setString(1, u.getPhone());
            prestate.setString(2, u.getAddr());
            prestate.setString(3, u.getPassword());
            prestate.setInt(4, u.getId());
            prestate.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Util.close(prestate);
            Util.close(conn);
        }

    }

    @Override
    public User loadById(int id) {
        return null;
    }

    @Override
    /**
     *
     * @param users
     * @param pageNo
     * @param pageSize
     * @return 总共有多少条记录
     */
    public int getUsers(List<User> users, int pageNo, int pageSize) {

        int totalRecords = -1;

        Connection conn = Util.getConnection();
        String sql = "select * from user limit " + (pageNo - 1) * pageSize + "," + pageSize;

        ResultSet rs = Util.getResultSet(conn, sql);
        ResultSet rsCount = Util.getResultSet(conn, "select count(*) from user");

        try {
            rsCount.next();
            totalRecords = rsCount.getInt(1);

            while (rs.next()) {
                User u = new User();
                u.setId(rs.getInt("id"));
                u.setUsername(rs.getString("username"));
                u.setPassword(rs.getString("password"));
                u.setPhone(rs.getString("phone"));
                u.setAddr(rs.getString("addr"));
                u.setRdate(rs.getTimestamp("rdate"));
                users.add(u);
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
}
