<%@ page import="com.limbo.User" %>
<%@ page import="com.limbo.UserMgr" %>
<%@ page import="com.limbo.UserMgrImpl" %>
<%--
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

    User user = new User();
    user.setUsername(username);
    user.setPassword(password1);

    if(user.exists()) {
        response.sendRedirect("RegisterFail.jsp");
    } else {
        UserMgr userMgr = new UserMgrImpl();
        userMgr.add(user);
        response.sendRedirect("RegisterSuccess.jsp");
    }

%>


<html>
<head>
    <title>RegisterDeal</title>
</head>
<body>

</body>
</html>
