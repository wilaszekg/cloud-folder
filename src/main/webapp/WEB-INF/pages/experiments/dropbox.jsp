<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title></title>
</head>
<body>
    <c:if test="${not empty dropboxLogin}">
        <a href="${dropboxLogin}" target="_blank">Login with Dropbox</a>
    </c:if>
</body>
</html>
