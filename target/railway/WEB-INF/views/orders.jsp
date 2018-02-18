<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="tag" uri="/WEB-INF/taglibs/customTaglib.tld" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<fmt:setLocale value="${sessionScope.USER_LOCALE}"/>
<fmt:setBundle basename="views_locales.orders" var="lang"/>

<!doctype html>
<html lang="ua">
<head>
    <c:import url="../colon/css_dependencies.jsp" charEncoding="utf-8"/>
    <title><fmt:message key="page.orders.title" bundle="${lang}"/></title>

</head>
<body>

<c:import url="../colon/navbar.jsp" charEncoding="utf-8"/>
<div class="container">
    <div class="page-header">
        <h1><fmt:message key="page.orders.header" bundle="${lang}"/></h1>
    </div>
</div>

<div class="container">
    <div class="row">
        <p><fmt:message key="page.is.under.construction.msg" bundle="${lang}"/></p>
    </div>
</div>
<br><br>
<c:import url="../colon/footer.jsp" charEncoding="utf-8"/>
</body>
<c:import url="../colon/js_dependencies.jsp" charEncoding="utf-8"/>
</html>


