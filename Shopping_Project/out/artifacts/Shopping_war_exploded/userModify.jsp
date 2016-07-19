<%@ page import="com.limbo.shopping.model.User" %>
<%@ page import="com.limbo.shopping.service.UserMgr" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    //检查用户是否登录
    User u = (User) session.getAttribute("user");
    if (u == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>

<%
    request.setCharacterEncoding("utf-8");
    String action = request.getParameter("action");

    if (action !=null && action.equals("modify")) {
        String phone = request.getParameter("phone");
        String addr = request.getParameter("addr");
        u.setAddr(addr);
        u.setPhone(phone);
        UserMgr.getInstance().update(u);
        out.println("更改成功!恭喜!");
        return;
    }

%>

<html>
<head>
    <title>信息修改</title>
</head>
<body>

<style type="text/css">
    a {
        text-decoration: none;
        color: #000000
    }

    a:hover {
        text-decoration: underline
    }

    body {
        scrollbar-base-color: #F3F6FA;
        scrollbar-arrow-color: #4D76B3;
        font-size: 12px;
        background-color: #ffffff
    }

    table {
        font: 12px Verdana, Tahoma;
        color: #000000
    }

    input, select, textarea {
        font: 11px Verdana, Tahoma;
        color: #000000;
        font-weight: normal;
        background-color: #F3F6FA
    }

    select {
        font: 11px Verdana, Tahoma;
        color: #000000;
        font-weight: normal;
        background-color: #F3F6FA
    }

    .nav a {
        color: #000000
    }

    .header {
        font: 11px Verdana, Tahoma;
        color: #FFFFFF;
        font-weight: bold;
    }

    .header a {
        color: #FFFFFF
    }

    .tableborder {
        background: #4D76B3;
        border: 0px solid #4D76B3
    }

    .outertxt a {
        color: #000000
    }

    .altbg1 {
        background: #F3F6FA
    }

    .altbg2 {
        background: #FFFFFF
    }
</style>

<form method="post" name="register" action="userModify.jsp" onSubmit="this.regsubmit.disabled=true;">
    <input type="hidden" name="action" value="modify"/>
    <table class="tableborder" align="center" cellpadding="4" cellspacing="1" width="97%">
        <tbody>
        <tr>
            <td colspan="2" class="header">用户修改 - 必填内容</td>
        </tr>
        <tr>
            <td class="altbg1" width="21%">用户名:</td>
            <td class="altbg2"><input name="username" size="25" maxlength="25" type="text" readonly
                                      value="<%=u.getUsername()%>"></td>
        </tr>

        <tr>
            <td class="altbg1">&#30005;&#35805;:</td>
            <td class="altbg2"><input name="phone" type="text" id="phone" size="25" value="<%=u.getPhone()%>"></td>
        </tr>

        <tr>
            <td class="altbg1" valign="top">送货地址:</td>
            <td class="altbg2"><textarea name="addr" cols="60" rows="5" id="addr"><%=u.getAddr()%></textarea></td>
        </tr>
        </tbody>
    </table>
    <br>
    <center><input name="regsubmit" value="提 &nbsp; 交" type="submit"></center>
</form>
</body>
</html>
