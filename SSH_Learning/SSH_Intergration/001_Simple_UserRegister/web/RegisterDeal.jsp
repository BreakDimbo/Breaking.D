<%@ page import="java.sql.*" %><%--
  Created by IntelliJ IDEA.
  User: Break.D
  Date: 7/7/16
  Time: 11:10 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    String username = request.getParameter("username");
    String password1 = request.getParameter("password1");
    String password2 = request.getParameter("password2");


    if (username == null || password1 == null || password2 == null) {
        response.sendRedirect("RegisterFail.jsp");
    } else if (!password1.equals(password2)) {
        response.sendRedirect("RegisterFail.jsp");
    }

    Connection connection = null;
    PreparedStatement preparedStatement = null;
    Statement statement = null;
    ResultSet resultSet = null;

    try {
        Class.forName("com.mysql.jdbc.Driver");
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/spring", "root", "woshitiancai");
        statement = connection.createStatement();
        resultSet = statement.executeQuery("select * from user WHERE username like '" + username + "'");
        if (resultSet.next()) {
            response.sendRedirect("RegisterFail.jsp");
        } else {
            preparedStatement = connection.prepareStatement("INSERT into user VALUES (null, ?, ?)");
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password1);
            preparedStatement.execute();
            response.sendRedirect("RegisterSuccess.jsp");
        }

    } catch (ClassNotFoundException e) {
        e.printStackTrace();
    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        try {
            if(preparedStatement != null) {
                preparedStatement.close();
            }
            if (resultSet != null) {
                resultSet.close();
            }

            if (statement != null) {
                statement.close();
            }
            if(connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


%>


<html>
<head>
    <title>RegisterDeal</title>
</head>
<body>

</body>
</html>
