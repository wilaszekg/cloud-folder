<html>
<head>
    <link rel="stylesheet" type="text/css" href="/resources/css/app.css"/>
    <link rel="stylesheet" type="text/css" href="/resources/css/bootstrap.css"/>
    <link rel="stylesheet" type="text/css" href="/resources/css/bootstrap-theme.css"/>
    <script type="text/jsx" data-main="/resources/scripts/app" src="/resources/scripts/vendor/require.js"></script>
</head>
<body>
<div id="todo"></div>
<div class="main">
    <div class="login">
        <a class="login-btn btn btn-primary" href="${dropbox_redirection_url}">
            Dropbox
            <span class="glyphicon glyphicon-user"></span>
        </a>
        <a class="login-btn btn btn-primary" href="${google_redirection_url}">
            Google
            <span class="glyphicon glyphicon-user"></span>
        </a>
    </div>
    <BR>
    <div class="login">
        ${dropbox_user_id}
    </div>
</div>
</body>
</html>