<%--
  Created by IntelliJ IDEA.
  User: Break.D
  Date: 7/12/16
  Time: 2:58 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<html>
<head>
    <title>UserList</title>
</head>
<body>
<s:iterator value="users">
    <s:property value="username"/>
</s:iterator>
<s:debug/>
</body>
</html>
