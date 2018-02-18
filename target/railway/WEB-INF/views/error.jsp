<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<fmt:setLocale value="${sessionScope.USER_LOCALE}"/>
<fmt:setBundle basename="views_locales.error" var="lang"/>


<!doctype html>
<html lang="ua">

<head>
    <title><fmt:message key="page.title" bundle="${lang}"/></title>
</head>
<body>
<h3><fmt:message key="page.header" bundle="${lang}"/></h3>

<p><fmt:message key="error.page.message" bundle="${lang}"/></p>

<a href="<c:url value='/'/>"><fmt:message key="error.page.ref" bundle="${lang}"/></a>


<img src="./res/images/404.jpg" alt="Error"
     style="position: absolute; left: 50%; top: 50%; margin-left: -285px; margin-top: -190px;">
</body>
</html>
