<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="tag" uri="/WEB-INF/taglibs/customTaglib.tld" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<fmt:setLocale value="${sessionScope.USER_LOCALE}"/>
<fmt:setBundle basename="views_locales.users" var="lang"/>

<!doctype html>
<html lang="ua">
<head>
    <c:import url="../colon/css_dependencies.jsp" charEncoding="utf-8"/>
    <title><fmt:message key="page.title" bundle="${lang}"/></title>

</head>
<body>

<c:import url="../colon/navbar.jsp" charEncoding="utf-8"/>
<div class="container">
    <div class="page-header">
        <h1><fmt:message key="page.header" bundle="${lang}"/></h1>
    </div>
</div>

<div class="container">

    <form class="form-inline">
        <div class="input-group">
            <label for="usersOnPage"><fmt:message key="showUsers.count.header.part1" bundle="${lang}"/></label>
            <select id="usersOnPage" class="form-control" onchange="usersOnPageChange();">
                <option value="3"  ${usersOnPage == 3 ? 'selected' : '' } >3</option>
                <option value="5"  ${usersOnPage == 5 ? 'selected' : '' } >5</option>
                <option value="10" ${usersOnPage == 10 ? 'selected' : ''} >10</option>
                <option value="0"  ${usersOnPage == 0 ? 'selected' : '' } ><fmt:message key="showUsers.option.all"
                                                                                        bundle="${lang}"/></option>
            </select>
        </div>
    </form>
    <br>
</div>

<div class="container">
    <div class="row">
        <p id="msg_container" class="b-container"> <!--Text in Popup--></p>
        <div class="table-responsive col-md-12">
            <table class="table table-bordered table-striped" id="train_table">
                <thead>
                <tr align="center">
                    <th><fmt:message key="first_name.table.col" bundle="${lang}"/></th>
                    <th><fmt:message key="last_name.table.col" bundle="${lang}"/></th>
                    <th><fmt:message key="email.time.table.col" bundle="${lang}"/></th>
                    <th><fmt:message key="role.table.col" bundle="${lang}"/></th>
                    <th><fmt:message key="is_banned.table.col" bundle="${lang}"/></th>
                    <th><fmt:message key="save.button.table.col" bundle="${lang}"/></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${users}" var="user" varStatus="loopCount">
                <tr>
                    <td align="center"><c:out value="${user.firstName}" default="" escapeXml="true"/></td>
                    <td align="center"><c:out value="${user.lastName}" default="" escapeXml="true"/></td>
                    <td align="center"><c:out value="${user.email}" default="" escapeXml="true"/></td>
                    <td align="center">
                        <SELECT class="form-control" onchange="userInfoChange(${user.id});" id="user_role_id_${user.id}"
                                value="${user.roleId}">
                            <c:forEach items="${roles}" var="role">
                                <option value="${role.id}"  ${role.id == user.roleId ? 'selected' : '' } >
                                    <c:out value="${role.name}" default="" escapeXml="true"/>
                                </option>
                            </c:forEach>
                        </SELECT>
                    </td>
                    <td align="center">
                        <input onchange="userInfoChange(${user.id});" id="is_user_banned_id_${user.id}"
                               type='checkbox' ${user.banned ? 'checked' : '' } >
                    </td>
                    <td align="center">
                        <button class='btn btn-default' id="choose_btn_id_${user.id}" onclick='updateUser(${user.id});'>
                            <i class="fa fa-pencil-square-o"></i></button><!-- make local or pictogram -->
                    </td>
                <tr>
                    </c:forEach>
                </tbody>
            </table>

            <c:url var="url" value="/users"/>

            <tag:paginate max="3" steps="${usersOnPage}" offset="${offset}" count="${count}"
                          uri="${url}" next="&raquo;" previous="&laquo;"/>
        </div>
    </div>
    <br><br>

    <c:import url="../colon/footer.jsp" charEncoding="utf-8"/>
</body>
<c:import url="../colon/js_dependencies.jsp" charEncoding="utf-8"/>
</html>

<script>

    function userInfoChange(id) {
        $('#choose_btn_id_' + id).removeClass("btn-default");
        $('#choose_btn_id_' + id).addClass("btn-danger");
    }

    function usersOnPageChange() {
        location.href = '<c:url value='/'/>users?usersOnPage=' + $('#usersOnPage').val();
    }

    function updateUser(id) {

        var jsonArg = {
            id: id,
            roleId: $('#user_role_id_' + id).val(),
            isBanned: $('#is_user_banned_id_' + id).is(':checked')
        };

        $.ajax({
            url: "ajax/update_user",
            dataType: "json",
            type: 'POST',
            contentType: "application/json",
            data: JSON.stringify(jsonArg),
            success: function (data, textStatus) {
                var msg = document.getElementById("msg_container");
                if (textStatus === 'success') {
                    msg.innerHTML = '<fmt:message key="successful.update.msg" bundle="${lang}"/>';
                    if (data.success) {
                        msg.className = 'b-container';
                        $('#choose_btn_id_' + id).removeClass("btn-danger");
                        $('#choose_btn_id_' + id).addClass("btn-default");
                    } else {
                        msg.className = 'a-container'
                    }
                } else {
                    msg.className = 'a-container';
                    msg.innerHTML = '<fmt:message key="unsuccessful.update.msg" bundle="${lang}"/>';
                }

                msg.style.display = 'block';
                setTimeout(function () {
                        msg.style.display = 'none';
                    },
                    2000);
            }
        });
    }

</script>
