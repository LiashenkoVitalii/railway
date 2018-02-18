<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<fmt:setLocale value="${sessionScope.USER_LOCALE}"/>
<fmt:setBundle basename="views_locales.bill" var="lang"/>

<!doctype html>
<html lang="ua">
<head>
    <c:import url="../colon/css_dependencies.jsp" charEncoding="utf-8"/>
    <title><fmt:message key="page.title" bundle="${lang}"/></title>

</head>
<body>

<c:import url="../colon/navbar.jsp" charEncoding="utf-8"/>
<br><br>


<div class="container">
    <div class="jumbotron">

        <h2><fmt:message key="wishing.of.good.travelling.inscription" bundle="${lang}"/></h2>

        <div class="container">
            <div class="row">
                <table class="table table-bordered table-striped table-condensed" id="train_table">
                    <thead>
                    <tr>
                        <th colspan="4" align="center"><fmt:message key="table.header" bundle="${lang}"/></th>
                    </tr>
                    </thead>
                    <tbody>

                    <tr>
                        <td colspan="4" align="center"><c:out value="" default="" escapeXml="true"/>№ <c:out
                                value="${bill.ticketNumber}"/> <fmt:message key="ticket.from.date" bundle="${lang}"/>
                            <c:out value="${bill.ticketDate}"/></td>
                    <tr>
                    <tr>
                        <td align="left"><c:out value="" default="" escapeXml="true"/><fmt:message key="table.name.row"
                                                                                                   bundle="${lang}"/></td>
                        <td align="left"><c:out value="" default="" escapeXml="true"/><c:out value="${bill.lastName}"/>
                            <c:out value="${bill.firstName}"/></td>
                        <td align="left"><c:out value="" default="" escapeXml="true"/><fmt:message key="table.train.row"
                                                                                                   bundle="${lang}"/></td>
                        <td align="left"><c:out value="" default="" escapeXml="true"/><c:out
                                value="${bill.trainNumber}"/></td>
                    <tr>

                    <tr>
                        <td align="left"><c:out value="" default="" escapeXml="true"/><fmt:message
                                key="table.leaving.row" bundle="${lang}"/></td>
                        <td align="left"><c:out value="" default="" escapeXml="true"/><c:out
                                value="${bill.fromStationName}"/></td>
                        <td align="left"><c:out value="" default="" escapeXml="true"/><fmt:message key="table.vagon.row"
                                                                                                   bundle="${lang}"/></td>
                        <td align="left"><c:out value="" default="" escapeXml="true"/><c:out
                                value="${bill.vagonNumber}"/> (<c:out value="${bill.vagonTypeName}"/>)
                        </td>
                    <tr>

                    <tr>
                        <td align="left"><c:out value="" default="" escapeXml="true"/><fmt:message
                                key="table.arrival.row" bundle="${lang}"/></td>
                        <td align="left"><c:out value="" default="" escapeXml="true"/><c:out
                                value="${bill.toStationName}"/></td>
                        <td align="left"><c:out value="" default="" escapeXml="true"/><fmt:message key="table.place.row"
                                                                                                   bundle="${lang}"/></td>
                        <td align="left"><c:out value="" default="" escapeXml="true"/><c:out
                                value="${bill.placeNumber}"/></td>
                    <tr>

                    <tr>
                        <td align="left"><c:out value="" default="" escapeXml="true"/><fmt:message
                                key="table.leaving.time" bundle="${lang}"/></td>
                        <td align="left"><c:out value="" default="" escapeXml="true"/><c:out
                                value="${bill.fromStationLeavingDate}"/></td>
                        <td colspan="2" align="left"></td>
                    <tr>

                    <tr>
                        <td align="left"><c:out value="" default="" escapeXml="true"/><fmt:message
                                key="table.arrival.time" bundle="${lang}"/></td>
                        <td align="left"><c:out value="" default="" escapeXml="true"/><c:out
                                value="${bill.toStationArrivalDate}"/></td>
                        <td colspan="2" align="left"></td>
                    <tr>

                    <tr>
                        <td colspan="4" align="left"><c:out value="" default="" escapeXml="true"/><fmt:message
                                key="table.price.row" bundle="${lang}"/> <c:out value="${bill.ticketPrice}"/>
                            <fmt:message key="money.hrn" bundle="${lang}"/></td>
                    <tr>

                    <tr>
                        <td colspan="4" align="left"><c:out value="" default="" escapeXml="true"/><fmt:message
                                key="table.city_time.row" bundle="${lang}"/> <c:out value="${bill.fromCityName}"/></td>
                    <tr>
                    </tbody>
                </table>
            </div>
            <div class="row">
                <div class="text-right form-group btn-group-lg">
                    <a class="btn btn-success pull-right" role="button" href="<c:url value='/'/>"><fmt:message
                            key="main.page.btn" bundle="${lang}"/></a>
                </div>
            </div>
        </div>
    </div>
</div>

<br><br>

<c:import url="../colon/footer.jsp" charEncoding="utf-8"/>
</body>
<c:import url="../colon/js_dependencies.jsp" charEncoding="utf-8"/>
</html>

