<%@ page import="com.limbo.shopping.model.User" %>
<%@ page import="com.limbo.shopping.service.UserMgr" %><%--
  Created by IntelliJ IDEA.
  User: Break.D
  Date: 7/11/16
  Time: 10:07 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    User u = (User) session.getAttribute("user");
    String action = request.getParameter("action");
    if (action != null && action.equals("chpass")) {
        String password1 = request.getParameter("newPassword");
        String password2 = request.getParameter("newPassword1");
        if (password1 == null || password2 == null || password1.trim().equals("") || password1.trim().equals("")) {
            out.print("请输入正确密码");
            return;
        } else if (!password1.equals(password2)) {
            out.print("两次密码输入不一致,请重新输入");
            return;
        } else {
            u.setPassword(password1);
            UserMgr.getInstance().update(u);
            out.print("密码修改成功");
            return;
        }
    }
%>
<html>
<head>
    <title>修改密码</title>
</head>
<body>
<form action="changePassword.jsp" method="post">
    <input type="hidden" name="action" value="chpass">
    <tr>
        <td>
            请输入新密码:
        </td>
        <td>
            <input type="password" name="newPassword">
        </td>
    </tr>
    <tr>
        <td>
            请确认新密码:
        </td>
        <td>
            <input type="password" name="newPassword1">
        </td>
    </tr>
    <tr>
        <td>
            <input type="submit" value="提交">
        </td>
    </tr>
</form>
</body>
</html>
