<%@ page language="java" import="com.limbo.shopping.service.UserMgr" pageEncoding="GB18030" %>

<%
    int id = Integer.parseInt(request.getParameter("id"));
    String url = request.getParameter("from");
    if(!UserMgr.getInstance().delete(id)) {
        out.print("ɾ��ʧ��");
    }
%>

<html>
<head>
    <title>ɾ���û�</title>
</head>
<body>
<center>
    ��ϲ��, ɾ���ɹ�!
    <br>
    <span id="delay" style="background:red">3</span>���Ӻ���ת����һҳ��, ����������������� �Լ���ת
    <br>
    <a href="<%=url%>">�û��б�
    </a>
</center>
<script type="text/javascript">

    var delay = 3;
    function goBack() {
    	document.getElementById("delay").innerHTML=delay;
    	delay --;
    	if(delay == 0)
    		document.location.href='<%=url%>';
    	else
    		setTimeout(goBack, 1000);
    }
    goBack();

    parent.main.location.reload();

</script>
</body>
</html>