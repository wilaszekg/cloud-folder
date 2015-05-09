<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <link rel="stylesheet" type="text/css" href="/resources/css/bootstrap.css"/>
    <link rel="stylesheet" type="text/css" href="/resources/css/bootstrap-theme.css"/>
    <link rel="stylesheet" type="text/css" href="/resources/css/app.css"/>
    <script data-main="/resources/scripts/app" src="/resources/scripts/vendor/require.js"></script>

    <script id="account-template" type="text/x-handlebars-template">
        {{#if google}}
        <span class="google"></span>
        {{/if}}
        {{#if dropbox}}
        <span class="dropbox"></span>
        {{/if}}

        <span>{{name}}</span>
    </script>

    <script id="file-template" type="text/x-handlebars-template">
        <span>{{name}}</span>
    </script>

    <script id="folder-template" type="text/x-handlebars-template">
        <span class="glyphicon glyphicon-folder-open"></span>
        <span>{{name}}</span>
    </script>

    <script id="folder-header-template" type="text/x-handlebars-template">
        <div class="top-folder-menu">
            <div class="form-inline">
                <div class="form-group">
                    <button class="btn btn-info btn-sm top-button back-to-accounts">Back to accounts list</button>
                    <div class="input-group">
                        <input type="text" class="folder-add-input form-control" placeholder="New folder"/>
                        <div class="input-group-addon folder-add">+</div>
                    </div>
                </div>
            </div>
        </div>
        <li class="back-to-parent">
            <span class="glyphicon glyphicon-folder-close"></span>
            <span>..</span>
        </li>
    </script>

    <script id="spinner-template" type="text/x-handlebars-template">
        <span class="spinner"></span>
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

    <div class="folder-boxes">
        <div class="folder left">
            <ul id="accounts-list-left">
            </ul>

            <ul id="files-list-left">
            </ul>

            <div id="spinner-left">
            </div>
        </div>

        <div class="folder right">
            <ul id="accounts-list-right">
            </ul>

            <ul id="files-list-right">
            </ul>

            <div id="spinner-right">
            </div>
        </div>
    </div>
</div>
</body>
</html>