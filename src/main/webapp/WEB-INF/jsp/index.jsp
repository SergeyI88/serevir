<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 23.06.2018
  Time: 21:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>$Title$</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/login.css"/>
    <script type="text/javascript">
        var check = function () {
            var count = 0;
            var inp = document.getElementsByName('a');
            for (var i = 0; i < inp.length; i++) {
                if (inp[i].type == "radio" && inp[i].checked) {
                    count++;
                    if (document.getElementById('excel').valueOf() != null) {
                        document.getElementById('send').removeAttribute('hidden')
                    }
                }
            }
            if (count === 0 || count > 1) {
                alert("Нужно выбрать один магазин")
            }
        };
    </script>
</head>
<body class="container">
<% if (request.getAttribute("list") != null) {
    List<String> list = (List<String>) request.getAttribute("list");
for (String s: list) { %>
<%=s%><br>
<%
    }
    }
%>
<form name="f" method="post" class="form-container" action="uploadFile" enctype="multipart/form-data">
    Рынок:<input type="radio" value="1" id="1" name="a">
    Петр:<input type="radio" value="2" id="2" name="a">

    <input class="form-field" type="file" name="file" onclick="check()" id="excel"><br/>

    <input hidden class="form-field" type="submit" value="Загрузить файл" id="send">
</form>
</body>
</html>
