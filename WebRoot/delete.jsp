<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>删除界面</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
    <%
    out.print("<center><br><br><h3>删除成功！</h3></center>");
     %>
      <br>
 <br>
     <center>
     <input type="button" value="返回" 
     onclick="javascript:window.location.href='AllServlet?methodName=<%=1 %>&id=<%="" %>&name=<%="" %>';"/>
     <input type="button" value="输入" onclick="javascript:window.location.href='putin.jsp';" 
     style="margin-left: 50px"/>
      </center>
  </body>
</html>

