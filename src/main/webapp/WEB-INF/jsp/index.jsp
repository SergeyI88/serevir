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
        var check = function () {
            var count = 0;
            var inp = document.getElementsByName('shop');
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
    <%
        for (Shop s: (List<Shop>)request.getAttribute("shops") ) {%>
            <form action="/downloadGoods" class="submit-container" enctype="multipart/form-data">
    <button type="submit" name="storeUuid" value="<%=s.getUuid()%>">Загрузить товары</button>
  </form>
    Магазин<%= s.getName()%> <input type="radio" name="shop" value="<%=s.getUuid()%>"><br><br><%
    }%>

    <input class="form-field" type="file" name="file" onclick="check()" id="excel"><br/>

    <input hidden class="form-field" type="submit" value="Загрузить файл" id="send" >
    <%--<form action="/download">--%>
      <%--<button type="submit">Загрузить шаблон</button>--%>
    <%--</form>--%>
  </form>
  <form action="/download" class="submit-container" enctype="multipart/form-data">
    <button type="submit">Загрузить шаблон</button>
  </form>
  </body>
    <input hidden class="form-field" type="submit" value="Загрузить файл" id="send2">
</form>
</body>
</html>
