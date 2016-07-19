<%@ page language="java" import="com.limbo.shopping.model.User" pageEncoding="GB18030" %>
<%@ page import="com.limbo.shopping.service.UserMgr" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>


<%

    final int PAGE_SIZE = 2; //每页显示多少条记录
    final int PAGES_PER_TIME = 10;//每次显示多少个页码链接
    int pageNo = 1;
    String strPageNo = request.getParameter("pageNo");
    if (strPageNo != null && !strPageNo.trim().equals("")) {
        try {
            pageNo = Integer.parseInt(strPageNo);
        } catch (NumberFormatException e) {
            pageNo = 1;
        }
    }
    if (pageNo <= 0)
        pageNo = 1;
%>
<%
    List<User> users = new ArrayList<>();
    int totalRecords = UserMgr.getInstance().getUsers(users, pageNo, PAGE_SIZE);
    int totalPages = (totalRecords + PAGE_SIZE - 1) / PAGE_SIZE;

    if (pageNo > totalPages)
        pageNo = totalPages;
%>
<html>
<head>
    <title>会员列表</title>

    <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
    <meta name="keywords"
          content="Discuz!,Board,Comsenz,forums,bulletin board,">
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

    .header a {
        color: #FFFFFF
    }

    .tableborder {
        background: #4D76B3;
        border: 0px solid #4D76B3
    }

    .smalltxt {
        font: 11px Verdana, Tahoma
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


    <jsp:include page="head.jsp"/>
<body leftmargin="0" rightmargin="0" topmargin="0"
      onkeydown="if(event.keyCode==27) return false;">
<div id="popLayer" style="position: absolute; z-index: 1000;"
     class="cPopText"></div>


<table style="table-layout: fixed;" align="center" border="0"
       cellpadding="0" cellspacing="0" width="97%">
    <tbody>
    <tr>
        <td class="nav" align="left" nowrap="nowrap" width="90%">
            &nbsp;用户管理 &#187; 会员列表
        </td>
        <td align="right" width="10%">
            &nbsp;
            <a href="#bottom"><img src="../images/arrow_dw.gif"
                                   align="absmiddle" border="0">
            </a>
        </td>
    </tr>
    </tbody>
</table>
<br>

<table align="center" border="0" cellpadding="0" cellspacing="0"
       width="97%">
    <tbody>
    <tr>
        <td>
            <table border="0" cellpadding="0" cellspacing="0">
                <tbody>
                <tr>
                    <td height="3"></td>
                </tr>
                <tr>
                    <td>
                        <table class="tableborder" cellpadding="2" cellspacing="1">
                            <tbody>
                            <tr class="smalltxt" bgcolor="#f3f6fa">
                                <td class="header">
                                    &nbsp;<%=totalRecords %>&nbsp;
                                </td>
                                <td class="header">
                                    &nbsp;<%=pageNo %>/<%=totalPages %>&nbsp;
                                </td>

                                <%
                                    int start = ((pageNo - 1) / PAGES_PER_TIME) * PAGES_PER_TIME + 1;
                                    for (int i = start; i < start + PAGES_PER_TIME; i++) {
                                        if (i > totalPages) break;
                                        if (pageNo == i) {
                                %>
                                <td bgcolor="#ffffff">&nbsp;<u><b><%=i %>
                                </b></u>&nbsp;</td>
                                <%
                                } else {
                                %>
                                <td>&nbsp;
                                    <a href="UserList.jsp?pageNo=<%=i%>"><%=i%>
                                    </a>&nbsp;
                                </td>
                                <%
                                        }
                                    }
                                %>
                                <td>
                                    &nbsp;
                                    <a href="UserList.jsp?pageNo=<%=pageNo+1%>">&gt;</a>&nbsp;
                                </td>
                            </tr>
                            </tbody>
                        </table>
                    </td>
                </tr>
                <tr>
                    <td height="3"></td>
                </tr>
                </tbody>
            </table>
        </td>
    </tr>
    </tbody>
</table>

<table class="tableborder" align="center" cellpadding="4" cellspacing="1" width="97%">
    <tbody>
    <tr class="header">
        <td align="center" width="9%">
            用户名
        </td>
        <td align="center" width="6%">
            UID
        </td>
        <td align="center" width="16%">
            联系电话
        </td>
        <td align="center" width="20%">
            注册时间
        </td>
        <td align="center" width="20%">
            送货地址
        </td>
        <td align="center">
            处理
        </td>
    </tr>

    <%
        for (int i = 0; i < users.size(); i++) {
            User u = users.get(i);
    %>

    <tr>
        <td class="altbg1" align="center" nowrap="nowrap">
            <%=u.getUsername()%>
        </td>
        <td class="altbg2" align="center">
            <%=u.getId()%>
        </td>
        <td class="altbg1" align="center">
            <%=u.getPhone()%>
        </td>
        <td class="altbg1" align="center">
            <%=new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                    .format(u.getRdate())%>
        </td>
        <td class="altbg1" align="center">
            <%=u.getAddr()%>
        </td>
        <td class="altbg1" align="right">
            <%
                String url = request.getRequestURL() + (request.getQueryString() == null ? "" : "?" + request.getQueryString()); %>
            <a target="detail" href="UserDelete.jsp?id=<%=u.getId()%>&from=<%=url%>" onclick="return confirm('真的要删除?')">删除</a>
        </td>
    </tr>

    <%
        }
    %>

</table>

</body>
</html>
