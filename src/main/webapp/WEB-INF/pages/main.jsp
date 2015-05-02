<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <link rel="stylesheet" type="text/css" href="/resources/css/app.css"/>
    <link rel="stylesheet" type="text/css" href="/resources/css/bootstrap.css"/>
    <link rel="stylesheet" type="text/css" href="/resources/css/bootstrap-theme.css"/>
    <script data-main="/resources/scripts/app" src="/resources/scripts/vendor/require.js"></script>

    <script id="account-template" type="text/x-handlebars-template">
        <span>{{name}}</span>
        <span>{{service}}</span>
    </script>

    <script id="file-template" type="text/x-handlebars-template">
        <span>{{name}}</span>
    </script>

    <script id="folder-template" type="text/x-handlebars-template">
        <span>[DIR] {{name}}</span>
    </script>

    <script id="clients-json" type="application/json">
        ${clients}
    </script>
</head>
<body>

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


    <div class="folder">
        <ul id="accounts-list">
        </ul>

        <ul id="files-list">
        </ul>
    </div>
</div>
</body>
</html>