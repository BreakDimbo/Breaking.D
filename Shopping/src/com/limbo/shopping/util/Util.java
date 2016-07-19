package com.limbo.shopping.util;

import java.sql.*;

/**
 * Created by Break.D on 7/11/16.
 */
public class Util {

    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {

        Connection connection = null;

        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/shopping?useSSL=false", "root", "woshitiancai");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return connection;
    }
    
    //close connection
    public static void close(Connection conn) {
        if(conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            conn = null;
        }
    }

    public static Statement getStatement(Connection conn) {

        Statement stat = null;
        try {
            stat = conn.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return stat;

    }
    
    //close statement
    public static void close(Statement stat) {
        if(stat != null) {
            try {
                stat.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            stat = null;
        }
    }

    public static PreparedStatement getPreStatement(Connection conn, String sql) {
        PreparedStatement prestat = null;
        try {
            prestat = conn.prepareStatement(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return prestat;
    }
    
    //close prepareStatement
    public static void close(PreparedStatement prestat) {
        if(prestat != null) {
            try {
                prestat.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            prestat = null;
        }
    }

    public static ResultSet getResultSet(Connection conn, String sql) {
        ResultSet rs = null;

        try {
            Statement stat = conn.createStatement();
            rs = stat.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }
    
    //close ResultSet
    public static void close(ResultSet rs) {
        if(rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            rs = null;
        }
    }

    public static void update(Connection conn, String sql) {
        Statement statement = getStatement(conn);
        try {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static PreparedStatement getPreStatement(Connection conn, String sql, int returnGeneratedKeys) {
        PreparedStatement prestat = null;
        try {
            prestat = conn.prepareStatement(sql, returnGeneratedKeys);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return prestat;
    }
}
