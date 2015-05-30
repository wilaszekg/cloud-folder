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
        {{#if selected}}
            <span class="selected-file">{{name}}</span>
        {{else}}
            <span>{{name}}</span>
        {{/if}}
    </script>

    <script id="folder-template" type="text/x-handlebars-template">
        <span class="glyphicon glyphicon-folder-open folder-open"></span>
        {{#if selected}}
            <span class="folder-description selected-file">{{name}}</span>
        {{else}}
            <span class="folder-description">{{name}}</span>
        {{/if}}
    </script>

    <script id="file-upload-template" type="text/x-handlebars-template">
        {{#if enabled}}
        <form enctype="multipart/form-data" action="{{uploadAction}}" method="post">
            <input name="file" type="file"/>
            <input type="submit" value="Upload Image" name="submit" class="form-control">
        </form>
        {{/if}}
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

                    {{#if selectedFileId}}
                        {{#if destinationDirectory}}
                            <button class="btn copy">C</button>
                            <button class="btn move">M</button>
                        {{/if}}
                        <button class="btn remove">R</button>
                        <a class="btn" href="/storage/{{clientIdForFile}}/{{selectedFileId}}/download/{{nameForFile}}" download>DL</a>
                    {{/if}}
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
            <div id="left-upload" class="upload"></div>

            <ul id="accounts-list-left">
            </ul>

            <ul id="files-list-left">
            </ul>

            <div id="spinner-left">
            </div>
        </div>

        <div class="folder right">
            <div id="right-upload" class="upload"></div>

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