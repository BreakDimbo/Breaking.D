<%@ page import="com.limbo.shopping.model.User" %>
<%@ page import="com.limbo.shopping.service.UserMgr" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<%
    String action = request.getParameter("action");
    boolean fmConfirm = false;
    if (action != null && action.equals("confirm")) {
        fmConfirm = true;
    }
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Login Box Concept</title>


    <link rel='stylesheet prefetch'
          href='http://ajax.googleapis.com/ajax/libs/jqueryui/1.11.2/themes/smoothness/jquery-ui.css'>

    <link rel="stylesheet" href="login/css/style.css">


</head>

<body>
<div class='login'>
    <div class='login_title'>
        <span>Login to your account</span>
    </div>
    <div class='login_fields'>
        <form action="<%= fmConfirm ? "selfservice.jsp?action=confirm" : "selfservice.jsp"%>" method="post">
            <div class='login_fields__user'>
                <div class='icon'>
                    <img src='https://s3-us-west-2.amazonaws.com/s.cdpn.io/217233/user_icon_copy.png'>
                </div>
                <input placeholder='Username' type='text' name="username">
                <div class='validation'>
                    <img src='https://s3-us-west-2.amazonaws.com/s.cdpn.io/217233/tick.png'>
                </div>
                </input>
            </div>
            <div class='login_fields__password'>
                <div class='icon'>
                    <img src='https://s3-us-west-2.amazonaws.com/s.cdpn.io/217233/lock_icon_copy.png'>
                </div>
                <input placeholder='Password' type='password' name="password">
                <div class='validation'>
                    <img src='https://s3-us-west-2.amazonaws.com/s.cdpn.io/217233/tick.png'>
                </div>
            </div>
            <div class='login_fields__submit'>
                <input type='submit' value='Log In'>
                <div class='forgot'>
                    <a href='register.jsp'>Don't have an account?</a>
                </div>
            </div>
        </form>
    </div>
    <div class='success'>
        <h2>Authentication Success</h2>
        <p>Welcome back</p>
    </div>
    <div class='disclaimer'>
        <p>Enjoy your shopping and find somethind interesting.</p>
    </div>
</div>
<div class='authent'>
    <img src='https://s3-us-west-2.amazonaws.com/s.cdpn.io/217233/puff.svg'>
    <p>Authenticating...</p>
</div>

<script src='http://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js'></script>
<script src='http://ajax.googleapis.com/ajax/libs/jqueryui/1.11.2/jquery-ui.min.js'></script>

<script src="login/js/index.js"></script>


</body>
</html>
