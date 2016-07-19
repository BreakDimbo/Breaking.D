<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="com.limbo.shopping.service.ProductMgr" %>

<%
int id = Integer.parseInt(request.getParameter("id"));

ProductMgr.getInstance().delete(id);
%>

<html>
	<head>
		<title>删除产品</title>
	</head>
	<center>
		删除成功！
	</center>
	<body>
		<script type="text/javascript">	
			parent.main.location.reload();
		</script>
	</body>
</html>