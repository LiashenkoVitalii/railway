<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page pageEncoding="UTF-8" %>
<fmt:setLocale value="${sessionScope.USER_LOCALE}"/>
<fmt:setBundle basename="views_locales.navbar" var="navbar_lang"/>


<nav class="navbar navbar-inverse navbar-static-top">
    <div class="container-fluid">
        <div class="container">
            <div class="navbar-header">
                <a class="navbar-brand" href="<c:url value='/'/>"><fmt:message key="index.page.ref"
                                                                               bundle="${navbar_lang}"/></a>
            </div>
            <ul class="nav navbar-nav">
                <c:if test="${sessionScope.USER_CURRENT_ROLE eq sessionScope.ADMIN_ROLE_ATTR}">
                    <li class="nav-item">
                        <a class="nav-link" href="<c:url value='/'/>users"><fmt:message key="users.page.ref"
                                                                                        bundle="${navbar_lang}"/></a>
                    </li>
                </c:if>

                <c:if test="${sessionScope.USER_CURRENT_ROLE eq sessionScope.USER_ROLE_ATTR}">
                    <li class="nav-item">
                        <a class="nav-link" href="<c:url value='/'/>orders"><fmt:message key="orders.page.ref"
                                                                                         bundle="${navbar_lang}"/></a>
                    </li>
                </c:if>

                <c:if test="${sessionScope.USER_CURRENT_ROLE ne sessionScope.GUEST_ROLE_ATTR}">
                    <li class="nav-item">
                        <a class="nav-link" href="<c:url value='/'/>profile"><fmt:message key="profile.page.ref"
                                                                                          bundle="${navbar_lang}"/></a>
                    </li>
                </c:if>

                <li class="nav-item dropdown">
                    <a class="dropdown-toggle toggle" href="#" data-toggle="dropdown"><fmt:message key="language.menu"
                                                                                                   bundle="${navbar_lang}"/><b
                            class="caret"></b></a>
                    <ul class="dropdown-menu">
                        <li>
                            <a class="dropdown-item" rel="it-IT" href="<c:url value='/'/>?lang=en_GB"><span
                                    class="flag-icon flag-icon-us btn-space"></span>English</a>
                        </li>
                        <li>
                            <a class="dropdown-item" rel="en-US" href="<c:url value='/'/>?lang=uk_UA"><span
                                    class="flag-icon flag-icon-ua btn-space"></span>Українська</a>
                        </li>
                    </ul>
                </li>
            </ul>

            <form class="navbar-form navbar-right">
                <c:if test="${sessionScope.USER_CURRENT_ROLE ne sessionScope.GUEST_ROLE_ATTR}">
                    <div class="form-group">
                        <a class="btn btn-default navbar-btn" role="button"
                           href="<c:url value='/'/>sign_out"><fmt:message key="logout.ref" bundle="${navbar_lang}"/></a>
                    </div>
                </c:if>
                <c:if test="${sessionScope.USER_CURRENT_ROLE eq sessionScope.GUEST_ROLE_ATTR}">
                    <div class="form-group">
                        <a class="btn btn-default navbar-btn" role="button" href="<c:url value='/'/>login"><fmt:message
                                key="login.ref" bundle="${navbar_lang}"/></a>
                    </div>
                    <div class="form-group">
                        <a class="btn btn-success navbar-btn" role="button"
                           href="<c:url value='/'/>registration"><fmt:message key="register.ref"
                                                                              bundle="${navbar_lang}"/></a>
                    </div>
                </c:if>
            </form>
        </div>
    </div>
</nav>