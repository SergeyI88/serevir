<%@ page import="java.util.List" %>
<%@ page import="db.entity.Shop" %><%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 23.06.2018
  Time: 21:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Обмен</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/login.css"/>
    <script type="text/javascript">
    var check1 = function () {
        var count = 0;
        var inp = document.getElementsByName('shop');
        for (var i = 0; i < inp.length; i++) {
            if (inp[i].type == "radio" && inp[i].checked) {
                count++;
                if (document.getElementById('excel').value != null) {
                    document.getElementById('send').removeAttribute('hidden')
                } else {
                    alert("Загрузите файл")
                }
            }
        }
        if (count === 0 || count > 1) {
            alert("Нужно выбрать один магазин для загрузки")
        }
    };
</script>
    <script type="text/javascript">
        var check2 = function () {
            var count = 0;
            var inp = document.getElementsByName('storeUuid');
            for (var i = 0; i < inp.length; i++) {
                if (inp[i].type == "radio" && inp[i].checked) {
                    count++;
                    document.getElementById('get').removeAttribute('hidden')
                }
            }
            if (count === 0 || count > 1) {
                alert("Нужно выбрать один магазин для выгрузки")
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
    <%
        for (Shop s: (List<Shop>)session.getAttribute("shops") ) {%>

    Магазин: <%= s.getName()%> <input type="radio" name="shop" value="<%=s.getUuid()%>"><br><br><%
    }%>

    <input class="form-field" type="file" name="file" onclick="check1()" id="excel"><br/>

    <input hidden class="form-field" type="submit" value="Загрузить файл" id="send" >
  </form>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<form name="f" method="get" class="form-container" action="downloadGoods" enctype="multipart/form-data">
    <%
        for (Shop s: (List<Shop>)session.getAttribute("shops") ) {%>

    Магазин: <%= s.getName()%> <input type="radio" onclick="check2()" name="storeUuid" value="<%=s.getUuid()%>"><br><br><%
    }%>

    <br/>

    <input hidden class="form-field" type="submit" value="Выгрузить остатки" id="get" >
</form>
  <form action="/download" class="submit-container" enctype="multipart/form-data">
    <button type="submit">Загрузить шаблон</button>
  </form>
  </body>
    <input hidden class="form-field" type="submit" value="Загрузить файл" id="send2">
</form>
</body>
</html>
