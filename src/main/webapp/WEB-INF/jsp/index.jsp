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
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/resources/js/jquery.min.js" type="text/javascript"></script>
    <script type="text/javascript">
        var check1 = function () {
            var count = 0;
            var inp = document.getElementsByName('shop');
            for (var i = 0; i < inp.length; i++) {
                if (inp[i].type === "radio" && inp[i].checked) {
                    count++;
                    document.getElementById('send').removeAttribute('hidden');
                    document.getElementById('get').removeAttribute('hidden');
                    document.getElementById('removeAllGoods').removeAttribute('hidden');
                    document.getElementById('getSells').removeAttribute('hidden');
                    document.getElementById('dateFrom').removeAttribute('hidden');
                    document.getElementById('dateTo').removeAttribute('hidden');
                    document.getElementById('viewSells').setAttribute('value', inp[i].value);
                    document.getElementById('removeAll').setAttribute('value', inp[i].value);
                    document.getElementById('download').setAttribute('value', inp[i].value)
                }
            }

            if (count === 0 || count > 1) {
                alert("Нужно выбрать один магазин для загрузки")
            }
        };

    </script>
</head>
<body class="container">

<div class="form-container">
    <% if (request.getAttribute("list") != null) {
        List<String> list = (List<String>) request.getAttribute("list");
        for (String s : list) { %>
    <h3>Ошибки:</h3>
    <div class="form-error"><%=s%>
    </div>
    <%
            }
        }
    %>
    <br>
    <% if (request.getAttribute("warnings") != null) {
        List<String> list = (List<String>) request.getAttribute("warnings");
        for (String s : list) { %>
    <H3>Предупреждения(исправлять необязательно, но отображение товаров может быть некорректно):</H3>
    <div class="form-warnings"><%=s%>
    </div>
    <%
            }
        }
    %>
    <div class="form-title">Выбор магазина</div>
    <form name="f" method="post" action="uploadFile" enctype="multipart/form-data">
        <%
            for (Shop s : (List<Shop>) session.getAttribute("shops")) {%>

        <div class="form-shop">
            Магазин: <%= s.getName()%> <input onclick="check1()" type="radio" name="shop" value="<%=s.getUuid()%>"/>
        </div>
        <%
            } %>
        <div class="form-title">Загрузить в терминал</div>
        <input class="form-field" type="file" name="file" id="excel"/><br/>

        <input hidden class="form-field" type="submit" value="Загрузить файл" id="send"/>
    </form>
    <br>


    <div class="form-title">Выгрузить с терминала</div>
    <form name="f1" method="get" action="downloadGoods" enctype="multipart/form-data">
        <input hidden class="form-field" type="submit" value="Выгрузить остатки" id="get"/>
        <input type="text" hidden name="store" value="" id="download"/>
    </form>

    <div class="form-title">Удалить все товары</div>
    <form name="f2" method="post" action="removeAll" enctype="multipart/form-data">
        <input type="text" hidden name="store" value="" id="removeAll"/>
        <input hidden class="form-field" type="submit" value="Удалить остатки" id="removeAllGoods"/>
    </form>

    <div class="form-title">Просмотр продаж</div>
    <form name="f3" method="post" action="sells" enctype="multipart/form-data">
        <div hidden id="dateFrom">C:<input type="date" class="form-field-data" name="dateFrom" placeholder="C"/></div>
        <div hidden id="dateTo">ПО:<input type="date" class="form-field-data2" name="dateTo" placeholder="ПО"/></div>
        <input type="text" name="store" value="" id="viewSells" hidden/>
        <input hidden class="form-field" type="submit" value="Посмотреть продажи" id="getSells"/>
    </form>


    <br><br>
    <div class="form-title"></div>
    <form action="download" enctype="multipart/form-data">
        <input type="submit" class="form-field" value="Загрузить шаблон"/>
    </form>
    <form action="manual" enctype="multipart/form-data">
        <input type="submit" class="form-field" value="Загрузить инструкцию"/>
    </form>
</div>
</body>
</html>
