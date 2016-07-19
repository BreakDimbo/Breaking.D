<%@ page language="java" import="com.limbo.shopping.model.Product" contentType="text/html;charset=utf-8"
         pageEncoding="utf-8" %>
<%@ page import="com.limbo.shopping.service.ProductMgr" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>

<%
    request.setCharacterEncoding("utf-8");
    String keyword = request.getParameter("keyword");

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
    List<Product> products = new ArrayList<>();
    int totalRecords = ProductMgr.getInstance().find(products, pageNo, PAGE_SIZE, keyword);
    int totalPages = (totalRecords + PAGE_SIZE - 1) / PAGE_SIZE;

    if (pageNo > totalPages)
        pageNo = totalPages;
%>
<html>
<head>
    <title>搜索结果</title>

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

    .header {
        font: 11px Verdana, Tahoma;
        color: #000000;
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
<body leftmargin="0" rightmargin="0" topmargin="0">
<div id="popLayer" style="position: absolute; z-index: 1000;"
     class="cPopText"></div>


<center><a href="ProductAdd.jsp" target="detail">搜索结果</a></center>

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
                                    <a href="SearchResult.jsp?pageNo=<%=i%>&keyword=<%=keyword%>"><%=i%>
                                    </a>&nbsp;
                                </td>
                                <%
                                        }
                                    }
                                %>
                                <td>
                                    &nbsp;
                                    <a href="ProductList.jsp?pageNo=<%=pageNo+1%>">&gt;</a>&nbsp;
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

<form action="ProductDeleteMultiple.jsp" name="formDelete" method="post" target="detail">
    <table class="tableborder" align="center" cellpadding="4"
           cellspacing="1" width="97%">
        <tbody>
        <tr class="header">
            <td align="center" width="10%">
                选择
            </td>
            <td align="center" width="9%">
                产品ID
            </td>
            <td align="center" width="6%">
                产品名称
            </td>
            <td align="center" width="16%">
                产品描述
            </td>
            <td align="center" width="10%">
                市场价格
            </td>
            <td align="center" width="10%">
                会员价格
            </td>
            <td align="center" width="20%">
                上架时间
            </td>
            <td align="center" width="10%">
                所属类别
            </td>
            <td align="center">
                处理
            </td>
        </tr>

            <%
					for (int i = 0; i < products.size(); i++) {
						Product p = products.get(i);
				%>

        <tr>
            <td class="altbg2" align="center">
                <input type="checkbox" name="id" value="<%=p.getId()%>"/>
            </td>
            <td class="altbg1" align="center" nowrap="nowrap">
                <%=p.getId()%>
            </td>
            <td class="altbg2" align="center">
                <%=p.getName()%>
            </td>
            <td class="altbg1" align="center">
                <%=p.getDescr()%>
            </td>
            <td class="altbg1" align="center">
                <%=p.getNormalPrice()%>
            </td>
            <td class="altbg1" align="center">
                <%=p.getMemberPrice()%>
            </td>
            <td class="altbg1" align="center">
                <%=new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                        .format(p.getPdate())%>
            </td>
            <td class="altbg1" align="center">
                <%//=CategoryService.getInstance().loadById(p.getCategoryId()).getName()%>
                <%=p.getCategory().getName()%>
            </td>
            <td class="altbg1" align="right">
                <a target="detail" href="ProductDelete.jsp?id=<%=p.getId()%>" onclick="return confirm('真的要删?')">删</a>
                <a target="detail" href="ProductModify.jsp?id=<%=p.getId()%>">改</a>
            </td>
        </tr>

            <%
				}
				%>

        <tr>
            <td>
                <input name="selectAll" type="checkbox" onclick="checkDelete()"/>
                <input type="submit" value="Delete"/>
            </td>

</form> <!-- end formDelete -->
</body>
</html>