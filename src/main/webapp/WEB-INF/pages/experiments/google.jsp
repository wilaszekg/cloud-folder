<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title></title>
</head>
<body>
    <c:if test="${not empty user}">
        <h1>Welcome, ${user}</h1>
    </c:if>

    <c:if test="${not empty googleLogin}">
        <a href="${googleLogin}" target="_blank">Login with Google</a>
    </c:if>
</body>
</html>
