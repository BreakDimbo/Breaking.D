<%@ page import="com.limbo.shopping.service.CategoryMgr" %><%--
  Created by IntelliJ IDEA.
  User: Break.D
  Date: 7/12/16
  Time: 11:36 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    String strId = request.getParameter("id");
    String strPid = request.getParameter("pid");
    if (strId != null && !strId.trim().equals("") && strPid != null && !strPid.trim().equals("")) {
        int id = Integer.parseInt(strId);
        int pid = Integer.parseInt(strPid);
        CategoryMgr.getInstance().delete(id, pid);
        response.sendRedirect("CategoryList.jsp");
    }
%>

<html>
<head>
    <title>Delete</title>
</head>
<body>

</body>
</html>
