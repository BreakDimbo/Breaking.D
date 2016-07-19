<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<%@ page import="java.util.ArrayList, java.util.Iterator" %>
<%@ page import="java.util.List" %>
<%@ page import="com.limbo.shopping.model.SalesOrder" %>
<%@ page import="com.limbo.shopping.service.OrderMgr" %>

<%!
    private static final int PAGE_SIZE = 2;
%>

<%
    String strPageNo = request.getParameter("pageno");
    int pageNo = 1;
    if (strPageNo != null) {
        pageNo = Integer.parseInt(strPageNo);
    }
    if (pageNo < 1) pageNo = 1;
%>

<%
    //get all the users
    List<SalesOrder> orders = new ArrayList<>();
    int totalRecords = OrderMgr.getInstance().getOrders(orders, pageNo, PAGE_SIZE);

    int totalPages = (totalRecords + PAGE_SIZE - 1) / PAGE_SIZE;
    if (pageNo > totalPages) pageNo = totalPages;
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=GB18030">
    <title>Insert title here</title>
<jsp:include page="head.jsp"/>

<body>
<table border="1" align="center">
    <tr>
        <td>ID</td>
        <td>username</td>
        <td>addr</td>
        <td>odate</td>
        <td>status</td>

        <td></td>
    </tr>
    <%
        for (Iterator<SalesOrder> it = orders.iterator(); it.hasNext(); ) {
            SalesOrder so = it.next();
    %>
    <tr>
        <td><%=so.getId() %>
        </td>
        <td><%=so.getUser().getUsername() %>
        </td>
        <td><%=so.getAddr() %>
        </td>
        <td><%=so.getODate() %>
        </td>
        <td><%=so.getStatus() %>
        </td>
        <td>
            <a href="OrderDetailShow.jsp?id=<%=so.getId()%>" target="_self">订单明细</a>
            &nbsp;
            <a href="OrderModify.jsp?id=<%=so.getId()%>" target="_self">订单修改</a>
        </td>
    </tr>
    <%
        }
    %>
</table>
<center>
    第<%=pageNo %>页
    &nbsp;
    共<%=totalPages %>页
    &nbsp;
    <a href="OrderList.jsp?pageno=<%=pageNo-1 %>">上一页</a>
    &nbsp;
    <a href="OrderList.jsp?pageno=<%=pageNo+1 %>">下一页</a>
    &nbsp;
    <a href="OrderList.jsp?pageno=<%=totalPages %>">最后一页</a>
</center>

</body>
</html>