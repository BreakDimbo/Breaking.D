<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
String action = request.getParameter("action");
if(action != null && action.equals("login")) {
String username = request.getParameter("username");
String password = request.getParameter("password");
if( !username.equals("admin") || !password.equals("admin") ) {
out.println("username or password not correct!");
return;
}
session.setAttribute("admin" , "admin");
response.sendRedirect("Admin.jsp");
}
%>

<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1" />
    <meta name="description" content="description of your site" />
    <meta name="author" content="author of the site" />
    <title>IndustryApp Template</title>
    <link rel="stylesheet" href="admin/css/bootstrap.css" />
    <link rel="stylesheet" href="admin/css/bootstrap-responsive.css" />
    <!--<link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Lato:100,300,400,700,900,100italic,300italic,400italic,700italic,900italic" />-->
    <link rel="stylesheet" href="admin/css/styles.css" />
    <link rel="stylesheet" href="admin/css/toastr.css" />
    <link rel="stylesheet" href="admin/css/fullcalendar.css" />
    <!--<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script>-->
    <script src="admin/js/jquery-1.8.3.min.js" ></script>
    <script src="admin/js/bootstrap.js"></script>
    <script src="admin/js/jquery.knob.js"></script>
    <script src="admin/js/jquery.sparkline.min.js"></script>
    <script src="admin/js/toastr.js"></script>
    <script src="admin/js/jquery.tablesorter.min.js"></script>
    <script src="admin/js/jquery.peity.min.js"></script>
    <script src="admin/js/fullcalendar.min.js"></script>
    <script src="admin/js/gcal.js"></script>
    <script src="admin/js/setup.js"></script>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8" /></head>
  <body>
    <div id="in-nav">
      <div class="container">
        <div class="row">
          <div class="span12">
            <ul class="pull-right">
              <li><a href="#">Link1</a></li>
              <li><a href="#">Link2</a></li>
              <li><a href="#">Link3</a></li>
              <li><a href="adminlogin.jsp">Login</a></li>
            </ul><a id="logo" href="Admin.jsp">
              <h4>My<strong>Admin</strong></h4></a>
          </div>
        </div>
      </div>
    </div>
<div class="container">
  <div class="row">
    <div class="span6 offset2">
      <div class="login">
        <form class="form-horizontal" method="post" action="adminlogin.jsp">
        <input type="hidden" name="action" value="login">
          <div class="control-group">
            <div class="controls">
              <h4>Login</h4>
            </div>
          </div>
          <div class="control-group">
            <label for="inputEmail" class="control-label">Username </label>
            <div class="controls">
              <input id="inputEmail" name="username" type="text" placeholder="Username" />
            </div>
          </div>
          <div class="control-group">
            <label for="inputPassword" class="control-label">Password </label>
            <div class="controls">
              <input id="inputPassword" name="password" type="password" placeholder="Password" />
            </div>
          </div>
          <div class="control-group"> 
            <div class="controls">
              <input type="submit" value="Submit">
            </div>
          </div>
        </form>
      </div>
    </div>
  </div>
</div>
</html>