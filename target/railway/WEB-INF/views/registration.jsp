<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<fmt:setLocale value="${sessionScope.USER_LOCALE}"/>
<fmt:setBundle basename="views_locales.registration" var="lang"/>

<!doctype html>
<html lang="ua">
<head>
    <c:import url="../colon/css_dependencies.jsp" charEncoding="utf-8"/>
    <title><fmt:message key="page.registration.title" bundle="${lang}"/></title>
</head>
<body>

<c:import url="../colon/navbar.jsp" charEncoding="utf-8"/>

<div class="container">
    <div class="row" style="margin-top:20px">
        <div class="col-xs-12 col-sm-8 col-md-6 col-centered">

            <form role="form" method="post" action="#" name="reg_form" id="reg_form">
                <h1><fmt:message key="page.registration.header" bundle="${lang}"/></h1>
                <p id="msg_container" style="display : none;" class="a-container"><fmt:message
                        key="unsuccessful.profile.update.msg" bundle="${lang}"/></p>

                <fieldset>
                    <div class="form-group">
                        <label for="firstName" class="cols-sm-2 control-label"><fmt:message key="first_name.input.label"
                                                                                            bundle="${lang}"/></label>
                        <div class="input-group input-group-lg">
                            <input type="text" class="form-control" id="firstName" name="firstName" required=""
                                   placeholder="<fmt:message key="first_name.input.placeholder" bundle="${lang}"/>"/>
                            <span class="input-group-addon"><i class="fa fa-user fa-fw" aria-hidden="true"></i></span>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="lastName" class="cols-sm-2 control-label"><fmt:message key="last_name.input.label"
                                                                                           bundle="${lang}"/></label>
                        <div class="input-group input-group-lg">
                            <input type="text" class="form-control" id="lastName" name="lastName" required=""
                                   placeholder="<fmt:message key="last_name.input.placeholder" bundle="${lang}"/>"/>
                            <span class="input-group-addon"><i class="fa fa-users fa-fw" aria-hidden="true"></i></span>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="email" class="cols-sm-2 control-label"><fmt:message key="email.input.label"
                                                                                        bundle="${lang}"/></label>
                        <div class="input-group input-group-lg">
                            <input type="text" required=""
                                   placeholder="<fmt:message key="email.input.placeholder" bundle="${lang}"/>"
                                   class="form-control" id="email" name="email"/>
                            <span class="input-group-addon"><i class="fa fa-envelope-o fa-fw"
                                                               aria-hidden="true"></i></span>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="password" class="cols-sm-2 control-label"><fmt:message key="password.input.label"
                                                                                           bundle="${lang}"/></label>
                        <div class="input-group input-group-lg">
                            <input type="password" class="form-control" id="password" name="password" required=""
                                   placeholder="<fmt:message key="password.input.placeholder" bundle="${lang}"/>"/>
                            <span class="input-group-addon"><i class="fa fa-key fa-fw" aria-hidden="true"></i></span>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="repeatedPass" class="cols-sm-2 control-label"><fmt:message
                                key="repeated_password.input.label" bundle="${lang}"/></label>
                        <div class="input-group input-group-lg">
                            <input type="password" class="form-control" id="repeatedPass" name="repeatedPass"
                                   required=""
                                   placeholder="<fmt:message key="repeated_password.input.placeholder" bundle="${lang}"/>"/>
                            <span class="input-group-addon"><i class="fa fa-key fa-fw" aria-hidden="true"></i></span>
                        </div>
                    </div>

                    <!-- Button -->
                    <div class="form-group btn-group-lg">
                        <button id="submit_button" type="submit" name="submit_button"
                                class="btn btn-primary">
                            <fmt:message key="submit.register.button.name" bundle="${lang}"/>
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
        $('#reg_form')
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
                                url: 'ajax/check_email',
                                message: '<fmt:message key="email.exists.msg" bundle="${lang}"/>'
                            }
                        }
                    },

                    password: {
                        validators: {
                            notEmpty: {
                                message: '<fmt:message key="empty.pass.field.validation.msg" bundle="${lang}"/>'
                            },
                            <%--identical: {--%>
                                <%--field: 'repeatedPass',--%>
                                <%--message: '<fmt:message key="identical.pass.with.repeated.field.validation.msg" bundle="${lang}"/>'--%>
                            <%--},--%>
                            different: {
                                field: 'firstName',
                                message: '<fmt:message key="different.pass.with.firstname.field.validation.msg" bundle="${lang}"/>'
                            },
                            different: {
                                field: 'lastName',
                                message: '<fmt:message key="different.pass.with.lastname.field.validation.msg" bundle="${lang}"/>'
                            },
                            different: {
                                field: 'email',
                                message: '<fmt:message key="different.pass.with.email.field.validation.msg" bundle="${lang}"/>'
                            },
                            regexp: {
                                regexp: /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{4,}$/,
                                message: '<fmt:message key="regex.password.requirements.msg" bundle="${lang}"/>'
                            },
                            stringLength: {
                                min: 0,
                                max: 255,
                                message: '<fmt:message key="not.more.than.pass.validation.msg" bundle="${lang}"/>'
                            }
                        }
                    },

                    repeatedPass: {
                        validators: {
                            notEmpty: {
                                message: '<fmt:message key="empty.repeatedpass.field.validation.msg" bundle="${lang}"/>'
                            },
                            stringLength: {
                                min: 0,
                                max: 255,
                                message: '<fmt:message key="not.more.than.repeatedpass.validation.msg" bundle="${lang}"/>'

                            },
                            identical: {
                                field: 'password',
                                message: '<fmt:message key="identical.with.pass.field.validation.msg" bundle="${lang}"/>'
                            }
                        }
                    }
                }
            })
            .on('success.form.bv', function (e) {
                // Prevent form submission
                e.preventDefault();
                saveUserProfile();
            });
    });

    function saveUserProfile() {

        var jsonArg = {
            firstName: $('#firstName').val(),
            lastName: $('#lastName').val(),
            email: $('#email').val(),
            password: $('#password').val().split(''),
            language: $('#language').val()
        };

        $.ajax({
            url: "ajax/new_user_profile",
            dataType: "json",
            type: 'POST',
            contentType: "application/json",
            data: JSON.stringify(jsonArg),
            success: function (data, textStatus) {
                var msg = document.getElementById("msg_container");
                if (textStatus !== 'success' || !data.success) {
                    msg.style.display = 'block';
                    setTimeout(function () {
                            msg.style.display = 'none';
                        },
                        1500);
                } else {
                    location.href = "<c:url value='/'/>login";
                }
            }
        });
    }

</script>