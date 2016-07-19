<%@ page import="com.limbo.shopping.model.User" %>
<%@ page import="com.limbo.shopping.model.Cart" %>
<%@ page language="java" contentType="text/html; charset=utf-8"
		 pageEncoding="utf-8"%>


<%
	request.setCharacterEncoding("GBK");

	User u = (User) session.getAttribute("user");
	if (u == null) {
		response.sendRedirect("UserLogin.jsp");
		return;
	}

	Cart c = (Cart) session.getAttribute("cart");
	if (c == null) {
		c = new Cart();
		session.setAttribute("cart", c);
	}

	String addr = request.getParameter("addr");
	u.setAddr(addr);

	int orderId = u.buy(c); 

	session.removeAttribute("cart");
%>

单已下! 订单号:
<%=orderId%>
