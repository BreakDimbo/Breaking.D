<%--
  Created by IntelliJ IDEA.
  User: Break.D
  Date: 7/7/16
  Time: 11:09 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>注册页面</title>
</head>
<body>
<form action="user.action" method="post">
    <table align="center">
        <tr>
            <td colspan="2" align="center">注册</td>
        </tr>
        <tr>
            <td>用户名:</td>
            <td>
                <input type="text" name="username">
            </td>
        </tr>
        <tr>
            <td>密码:</td>
            <td>
                <input type="password" name="password1">
            </td>
        </tr>
        <tr>
            <td>确认密码:</td>
            <td>
                <input type="password" name="password2">
            </td>
        </tr>
        <tr>
            <td colspan="2" align="center">
                <input type="submit" value="提交">
            </td>
        </tr>
    </table>
</form>
</body>
</html>
