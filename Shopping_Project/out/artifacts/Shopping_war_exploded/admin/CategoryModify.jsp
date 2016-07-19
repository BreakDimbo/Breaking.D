<%@ page import="com.limbo.shopping.model.Category" %>
<%@ page import="com.limbo.shopping.service.CategoryMgr" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    request.setCharacterEncoding("utf-8");
    int id = Integer.parseInt(request.getParameter("id"));
    Category c = CategoryMgr.getInstance().loadById(id);
    String action = request.getParameter("action");

    if (action != null && action.trim().equals("modify")) {
        String name = request.getParameter("name");
        String descr = request.getParameter("descr");
        c.setName(name);
        c.setDescr(descr);
        CategoryMgr service = CategoryMgr.getInstance();
        service.update(c);
        out.println("类别修改成功!恭喜!");
        return;
    }
%>

<html>
<head><title>类别修改</title>

    <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
    <meta name="keywords" content="Discuz!,Board,Comsenz,forums,bulletin board,">
    <meta name="generator" content="Discuz! 4.0.0RC4 with Templates 4.0.0">
    <meta name="MSSmartTagsPreventParsing" content="TRUE">
    <meta http-equiv="MSThemeCompatible" content="Yes">

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

    .nav {
        font: 12px Verdana, Tahoma;
        color: #000000;
        font-weight: bold
    }

    .nav a {
        color: #000000
    }

    .header {
        font: 11px Verdana, Tahoma;
        color: #FFFFFF;
        font-weight: bold;
        background-image: url("images/green/bg01.gif")
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

    --></style>
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


<table style="table-layout: fixed;" align="center" border="0" cellpadding="0" cellspacing="0" width="97%">
    <tbody>
    <tr>
        <td class="nav" align="left" nowrap="nowrap" width="90%">&nbsp;&#37329;&#23578;&#21830;&#22478; &#187; 注册</td>
        <td align="right" width="10%">&nbsp;</td>
    </tr>
    </tbody>
</table>
<br>
<form method="post" name="register" action="CategoryModify.jsp">
    <input type="hidden" name="action" value="modify"/>
    <input type="hidden" name="id" value="<%=id%>"/>
    <table class="tableborder" align="center" cellpadding="4" cellspacing="1" width="97%">
        <tbody>
        <tr>
            <td colspan="2" class="header">添加类别 - 必填内容</td>
        </tr>
        <tr>
            <td class="altbg1" width="21%">类别名称:</td>
            <td class="altbg2"><input name="name" size="25" maxlength="25" type="text" value="<%=c.getName()%>">
        </tr>

        <tr>
            <td class="altbg1" valign="top">类别描述:</td>
            <td class="altbg2"><textarea name="descr" cols="60" rows="5" id="descr"><%=c.getDescr()%></textarea></td>
        </tr>
        </tbody>
    </table>
    <br>
    <center><input name="regsubmit" value="提 &nbsp; 交" type="submit"></center>
</form>

</body>
</html>