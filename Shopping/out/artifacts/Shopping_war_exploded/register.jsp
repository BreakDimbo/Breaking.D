<%@ page import="com.limbo.shopping.model.User" %>
<%@ page import="com.limbo.shopping.service.UserMgr" %>
<%@ page import="java.util.Date" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%

    request.setCharacterEncoding("utf-8");
    String action = request.getParameter("action");

    if (action != null && action.trim().equals("register")) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        //String password2 = request.getParameter("password2");
        String phone = request.getParameter("phone");
        String addr = request.getParameter("addr");
        User u = new User();
        u.setUsername(username);
        u.setPassword(password);
        u.setPhone(phone);
        u.setAddr(addr);
        u.setRdate(new Date());

        /*单例模式*/
        UserMgr.getInstance().addUser(u);
        out.println("注册成功!恭喜!");
        return;
    }
%>

<html>
<head><title>会员注册</title>


    <%--ajaxt--%>
    <script type="text/javascript">
        var req;
        function validate() {
            var idField = document.getElementById("userid");
            var url = "Validate.jsp?id=" + escape(idField.value);
            if (window.XMLHttpRequest) {
                req = new XMLHttpRequest();
            } else if (window.ActiveXObject) {
                req = new ActiveXObject("Microsoft.XMLHTTP");
            }
            req.open("GET", url, true);
            req.onreadystatechange = callback;
            req.send(null);
        }

        function callback() {
            if (req.readyState == 4) {
                if (req.status == 200) {
                    //alert(req.responseText);
                    var msg = req.responseXML.getElementsByTagName("msg")[0];
                    //alert(msg);
                    setMsg(msg.childNodes[0].nodeValue);
                }
            }
        }

        function setMsg(msg) {
            //alert(msg);
            mdiv = document.getElementById("usermsg");
            if (msg == "invalid") {
                mdiv.innerHTML = "<font color='red'>username exists</font>";
            } else {
                mdiv.innerHTML = "<font color='green'>congratulations! you can use this username!</font>";
            }
        }


    </script>


    <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
    <meta name="keywords" content="Discuz!,Board,Comsenz,forums,bulletin board,">
    <meta name="description" content="专区  - Discuz! Board">
    <meta name="generator" content="Discuz! 4.0.0RC4 with Templates 4.0.0">
    <meta name="MSSmartTagsPreventParsing" content="TRUE">
    <meta http-equiv="MSThemeCompatible" content="Yes">

    <%--CSS--%>
    <style type="text/css"><!--
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

    --></style>
    <script language="JavaScript" src="images/common.js"></script>
    <style type="text/css" id="defaultPopStyle">.cPopText {
        font-family: Tahoma, Verdana;
        background-color: #FFFFCC;
        border: 1px #000000 solid;
        font-size: 12px;
        padding-right: 4px;
        padding-left: 4px;
        height: 20px;
        padding-top: 2px;
        padding-bottom: 2px;
        visibility: hidden;
        filter: Alpha(Opacity=80)
    }</style>


</head>
<body leftmargin="0" rightmargin="0" topmargin="0" onkeydown="if(event.keyCode==27) return false;">
<div id="popLayer" style="position: absolute; z-index: 1000;" class="cPopText"></div>
</head>


<form method="post" name="register" action="register.jsp" onSubmit="this.regsubmit.disabled=true;">
    <input type="hidden" name="action" value="register"/>

    <table class="tableborder" align="center" cellpadding="4" cellspacing="1" width="97%">
        <tbody>
        <tr>
            <td colspan="2" class="header">注册 - 必填内容</td>
        </tr>
        <tr>
            <td class="altbg1" width="21%">用户名:</td>
            <td class="altbg2"><input id="userid" name="username" size="25" maxlength="25" type="text"
                                      onblur="validate()"></td>
            <span id="usermsg"></span>
        </tr>

        <tr>
            <td class="altbg1">密码:</td>
            <td class="altbg2"><input name="password" size="25" type="password"></td>
        </tr>
        <tr>
            <td class="altbg1">确认密码:</td>
            <td class="altbg2"><input name="password2" size="25" type="password"></td>
        </tr>

        <tr>
            <td class="altbg1">&#30005;&#35805;:</td>
            <td class="altbg2"><input name="phone" type="text" id="phone" size="25"></td>
        </tr>

        <tr>
            <td class="altbg1" valign="top">送货地址:</td>
            <td class="altbg2"><textarea name="addr" cols="60" rows="5" id="addr"></textarea></td>
        </tr>
        </tbody>
    </table>
    <br>
    <center><input name="regsubmit" value="提交" type="submit"></center>
</form>

<script language="JavaScript">
    document.register.username.focus();
</script>


<p align="center">
    <a name="bottom"></a>
    <a target="_blank" href="http://www.thinkpadclub.com.cn/"><u>市集</u></a><br>
</p>
</body>
</html>