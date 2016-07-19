<%@ page import="com.limbo.shopping.model.SalesOrder" %>
<%@ page import="com.limbo.shopping.service.OrderMgr" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.List" %>
<%@ page import="com.limbo.shopping.model.User" %>
<%@ page import="com.limbo.shopping.model.SalesItem" %><%--
  Created by IntelliJ IDEA.
  User: Break.D
  Date: 7/15/16
  Time: 6:09 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<%
    User u = (User) session.getAttribute("user");
%>

<%
    //get all the users
    List<SalesOrder> orders = OrderMgr.getInstance().getOrders(u);



%>

<html>
<head>
    <title>我的订单</title>
    <script type="text/javascript">
        function showProductInfo(descr) {
            document.getElementById("productInfo").innerHTML = "<font size=3 color=red>" + descr + "</font>";
        }
    </script>
</head>
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
            List<SalesItem> items = OrderMgr.getInstance().getSalesItems(so);
    %>
    <tr>
        <td onmouseleave="document.getElementById('productInfo').innerHTML=''" onmouseover="showProductInfo('' +
                '<br/><table border=1><tr><td>商品名称</td><td>商品价格</td><td>购买数量</td><td></td></tr><%for (int i = 0; i < items.size(); i++) {SalesItem si = items.get(i);%><tr><td><%=si.getProduct().getName()%></td><td><%=si.getUnitPrice() %></td><td><%=si.getCount() %></td><td></td></tr><%}%></table>')"><%=so.getId() %>
        </td>
        <td><%=u.getUsername() %>
        </td>
        <td><%=so.getAddr() %>
        </td>
        <td><%=so.getODate() %>
        </td>
        <td><%=so.getStatus() %>
        </td>

    </tr>
    <%
        }
    %>
</table>

<div id="productInfo" align="center">

</div>

</body>
</html>
