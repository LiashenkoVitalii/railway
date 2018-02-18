<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<fmt:setLocale value="${sessionScope.USER_LOCALE}"/>
<fmt:setBundle basename="views_locales.profile" var="lang"/>

<!doctype html>
<html lang="ua">
<head>
    <c:import url="../colon/css_dependencies.jsp" charEncoding="utf-8"/>
    <title>
        <fmt:message key="page.profile.title" bundle="${lang}"/>
    </title>
</head>
<body>

<c:import url="../colon/navbar.jsp" charEncoding="utf-8"/>

<div class="container">
    <div class="row" style="margin-top:20px">
        <div class="col-xs-12 col-sm-8 col-md-6 col-centered">

            <form role="form" method="post" action="#" name="profile_form" id="profile_form">
                <h1><fmt:message key="page.profile.header" bundle="${lang}"/></h1>
                <%--<p id="msg_container" style="display : none;" class="a-container"><fmt:message--%>
                <%--key="unsuccessful.profile.update.msg" bundle="${lang}"/></p>--%>
                <p id="msg_container" class="b-container"> <!--Text in Popup--></p>

                <fieldset>
                    <div class="form-group">
                        <label for="firstName" class="cols-sm-2 control-label"><fmt:message key="first_name.input.label"
                                                                                            bundle="${lang}"/></label>
                        <div class="input-group input-group-lg">
                            <input type="text" class="form-control" id="firstName" name="firstName" required=""
                                   placeholder=""
                                   value="<c:out value="${user.firstName}" default="" escapeXml="true" />"/>
                            <span class="input-group-addon"><i class="fa fa-user fa-fw" aria-hidden="true"></i></span>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="lastName" class="cols-sm-2 control-label"><fmt:message key="last_name.input.label"
                                                                                           bundle="${lang}"/></label>
                        <div class="input-group input-group-lg">
                            <input type="text" class="form-control" id="lastName" name="lastName" required=""
                                   placeholder=""
                                   value="<c:out value="${user.lastName}" default="" escapeXml="true" />"/>
                            <span class="input-group-addon"><i class="fa fa-users fa-fw" aria-hidden="true"></i></span>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="email" class="cols-sm-2 control-label"><fmt:message key="email.input.label"
                                                                                        bundle="${lang}"/></label>
                        <div class="input-group input-group-lg">
                            <input type="text" required="" placeholder="" class="form-control" id="email" name="email"
                                   value="<c:out value="${user.email}" default="" escapeXml="true" />"/>
                            <span class="input-group-addon"><i class="fa fa-envelope-o fa-fw"
                                                               aria-hidden="true"></i></span>
                        </div>
                    </div>

                    <div class="form-group btn-group-lg">
                        <button id="submit_button" type="submit" name="submit_button"
                                class="btn btn-primary"><fmt:message key="submit.profile.button.name" bundle="${lang}"/>
                        </button>
                    </div>
                </fieldset>
            </form>
        </div>
    </div>
</div>

<br><br>

<c:import url="../colon/footer.jsp" charEncoding="utf-8"/>
</body>
<c:import url="../colon/js_dependencies.jsp" charEncoding="utf-8"/>
</html>

<script>

    $(document).ready(function () {
        $('#profile_form')
            .bootstrapValidator({
                feedbackIcons: {
                    valid: 'glyphicon glyphicon-ok',
                    invalid: 'glyphicon glyphicon-remove',
                    validating: 'glyphicon glyphicon-refresh'
                },
                fields: {
                    firstName: {
                        validators: {
                            notEmpty: {
                                message: '<fmt:message key="empty.firstname.field.validation.msg" bundle="${lang}"/>'
                            },
                            stringLength: {
                                min: 0,
                                max: 255,
                                message: '<fmt:message key="not.more.than.firstname.validation.msg" bundle="${lang}"/>'
                            }
                        }
                    },

                    lastName: {
                        validators: {
                            notEmpty: {
                                message: '<fmt:message key="empty.lastname.field.validation.msg" bundle="${lang}"/>'
                            },
                            stringLength: {
                                min: 0,
                                max: 255,
                                message: '<fmt:message key="not.more.than.lastname.validation.msg" bundle="${lang}"/>'
                            }
                        }
                    },

                    email: {
                        validators: {
                            notEmpty: {
                                message: '<fmt:message key="empty.email.field.validation.msg" bundle="${lang}"/>'
                            },
                            stringLength: {
                                min: 0,
                                max: 255,
                                message: '<fmt:message key="not.more.than.email.validation.msg" bundle="${lang}"/>'
                            },
                            emailAddress: {
                                message: '<fmt:message key="not.valid.email.msg" bundle="${lang}"/>'
                            },
                            remote: {
                                url: 'ajax/check_user_email',
                                message: '<fmt:message key="email.exists.msg" bundle="${lang}"/>'
                            }
                        }
                    }
                }
            })
            .on('success.form.bv', function (e) {
                // Prevent form submission
                e.preventDefault();
                updateUserProfile();
            });
    });

    function updateUserProfile() {

        var jsonArg = {
            firstName: $('#firstName').val(),
            lastName: $('#lastName').val(),
            email: $('#email').val()
        };

        $.ajax({
            url: "ajax/update_user_profile",
            dataType: "json",
            type: 'POST',
            contentType: "application/json",
            data: JSON.stringify(jsonArg),
            success: function (data, textStatus) {
                var msg = document.getElementById("msg_container");

                if (textStatus === 'success') {
                    msg.innerHTML = '<fmt:message key="successful.profile.update.msg" bundle="${lang}"/>';
                    if (data.success) {
                        msg.className = 'b-container';
                    } else {
                        msg.className = 'a-container'
                    }
                } else {
                    msg.className = 'a-container';
                    msg.innerHTML = '<fmt:message key="unsuccessful.profile.update.msg" bundle="${lang}"/>';
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
