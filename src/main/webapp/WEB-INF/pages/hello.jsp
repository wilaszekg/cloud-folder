<html>
<head>
    <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
            Dropbox ${dropbox_username}
            <span class="glyphicon glyphicon-user"></span>
        </a>
        <a class="login-btn btn btn-primary" href="${google_redirect_url}">
            Google ${google_username}
            <span class="glyphicon glyphicon-user"></span>
        </a>
    </div>
</div>
</body>
</html>