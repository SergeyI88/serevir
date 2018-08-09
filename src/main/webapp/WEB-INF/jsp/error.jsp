<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 03.07.2018
  Time: 19:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Ошибка</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/error.css"/>
</head>
<body class="container">
<div class="form-container">
    <% if (request.getAttribute("msg") == null) { %>
    <div class="form-title">Возникла непредвиденная ошибка, возможно у вас закончилась сессия или сервер недоступен.
        Если ошибка повторится, просьба написать в тех. поддержку.
    </div>
    <%} else {%>
    <div class="form-title"><%=request.getAttribute("msg")%>
    </div>
    <%}%>
    </div>

</body>
</html>
