<%@ page import="com.limbo.shopping.exception.UserNotFoundException" %>
<%@ page import="com.limbo.shopping.exception.WrongPasswordException" %>
<%@ page import="com.limbo.shopping.model.User" %>
<%@ page import="com.limbo.shopping.service.UserMgr" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    request.setCharacterEncoding("utf-8");

    User user = (User) session.getAttribute("user");
    String username = request.getParameter("username");
    String password = request.getParameter("password");

    if (user == null) {
        String action = request.getParameter("action");
        if (username == null || password == null) {
            response.sendRedirect("login.jsp");
        } else {
            try {
                user = UserMgr.getInstance().loginVal(username, password);
                session.setAttribute("user", user);
                if (action != null && action.equals("confirm")) {
                    response.sendRedirect("confirm.jsp");
                }
            } catch (UserNotFoundException u) {
                out.println(u.getMessage());
                return;
            } catch (WrongPasswordException p) {
                out.println(p.getMessage());
                return;
            }
        }
    }


%>
<html>
<head>


    <title>自服务系统</title>

    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">

</head>

<body>
<center>
    欢迎您： <%=user.getUsername() %>
    <br>
    <a href="userModify.jsp">修改我的信息</a>
    <br>
    <a href="changePassword.jsp">修改我的密码</a>
    <br>
    <a href="myOrders.jsp?">浏览以往订单</a>
</center>
</body>
</html>

</body>
</html>
