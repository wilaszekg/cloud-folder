<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <link rel="stylesheet" type="text/css" href="/resources/css/app.css"/>
    <link rel="stylesheet" type="text/css" href="/resources/css/bootstrap.css"/>
    <link rel="stylesheet" type="text/css" href="/resources/css/bootstrap-theme.css"/>
    <script type="text/jsx" data-main="/resources/scripts/app" src="/resources/scripts/vendor/require.js"></script>
</head>
<body>
<div id="todo"></div>
<div class="main">
    <div class="login">
        <a class="login-btn btn btn-primary" href="${dropbox_redirect_url}">
            Dropbox
            <span class="glyphicon glyphicon-user"></span>
        </a>
        <a class="login-btn btn btn-primary" href="${google_redirect_url}">
            Google
            <span class="glyphicon glyphicon-user"></span>
        </a>
    </div>

    <c:if test="${not empty newClient}">
        <div role="alert" class="alert alert-success">
            You successfully signed in to <strong>${newClient.service}</strong> as ${newClient.name}.
        </div>
    </c:if>

    <table class="table">
        <thead>
        <tr>
            <th>
                Your logged in clients
            </th>
            <th>
                Provider
            </th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${clients}" var="client">
            <tr>
                <td>${client.name}</td>
                <td>${client.service}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html>