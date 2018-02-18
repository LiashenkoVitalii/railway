<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<fmt:setLocale value="${sessionScope.USER_LOCALE}"/>
<fmt:setBundle basename="views_locales.login" var="lang"/>

<!doctype html>
<html lang="ua">
<head>
    <c:import url="../colon/css_dependencies.jsp" charEncoding="utf-8"/>
    <title><fmt:message key="page.title" bundle="${lang}"/></title>

</head>
<body>
<c:import url="../colon/navbar.jsp" charEncoding="utf-8"/>

<div class="container">
    <div class="row" style="margin-top:20px">
        <div class="col-xs-12 col-sm-8 col-md-6 col-centered">
            <form role="form" action="#" name="login_form" id="login_form">
                <fieldset>
                    <h2><fmt:message key="page.header" bundle="${lang}"/></h2>
                    <hr class="colorgraph">
                    <p id="msg_container" style="display : none;" class="a-container"><fmt:message
                            key="invalid.login.or.password.msg" bundle="${lang}"/></p>

                    <div class="form-group">
                        <div class="input-group ">
                            <input type="email" name="email" id="email" class="form-control input-lg"
                                   placeholder="<fmt:message key="email.input.placeholder" bundle="${lang}"/>"/>
                            <span class="input-group-addon"><i class="fa fa-envelope-o fa-fw"></i></span>
                        </div>
                    </div>

                    <div class="form-group">
                        <div class="input-group ">
                            <input type="password" name="password" id="password" class="form-control input-lg"
                                   placeholder="<fmt:message key="password.input.placeholder" bundle="${lang}"/>"/>
                            <span class="input-group-addon"><i class="fa fa-key fa-fw"></i></span>
                        </div>
                    </div>

                    <hr class="colorgraph">
                    <div class="row">
                        <div class="col-xs-6 col-sm-6 col-md-6">
                            <input type="submit" class="btn btn-lg btn-success btn-block"
                                   value="<fmt:message key="submit.button.name" bundle="${lang}"/>"/>
                        </div>
                        <div class="col-xs-6 col-sm-6 col-md-6">
                            <a href="<c:url value='/'/>registration"
                               class="btn btn-lg btn-primary btn-block"><fmt:message key="register.button.name"
                                                                                     bundle="${lang}"/></a>
                        </div>
                    </div>
                </fieldset>
            </form>
        </div>
    </div>
</div>

<c:import url="../colon/footer.jsp" charEncoding="utf-8"/>
</body>
<c:import url="../colon/js_dependencies.jsp" charEncoding="utf-8"/>
</html>
<script>


    $(document).ready(function () {
        $('#login_form')
            .bootstrapValidator({
                feedbackIcons: {
                    valid: 'glyphicon glyphicon-ok',
                    invalid: 'glyphicon glyphicon-remove',
                    validating: 'glyphicon glyphicon-refresh'
                },
                fields: {
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
                            }
                        }
                    },
                    password: {
                        validators: {
                            notEmpty: {
                                message: '<fmt:message key="empty.pass.field.validation.msg" bundle="${lang}"/>'
                            }
                        }
                    },
                    stringLength: {
                        min: 0,
                        max: 255,
                        message: '<fmt:message key="not.more.than.pass.validation.msg" bundle="${lang}"/>'
                    }
                }
            })
            .on('success.form.bv', function (e) {
                // Prevent form submission
                e.preventDefault();
                checkPrinciples();
            });
    });

    function checkPrinciples() {

        var jsonArg = {
            email: $('#email').val(),
            password: $('#password').val().split('')
        };

        $.ajax({
            url: "ajax/sign_in",
            dataType: "json",
            type: 'POST',
            contentType: "application/json",
            data: JSON.stringify(jsonArg),
            success: function (data, textStatus) {
                var msg = document.getElementById("msg_container");
                if (textStatus !== 'success' || !data.success) {
                    msg.style.display = 'block';
                } else {
                    location.href = "<c:url value='/'/>" + data.message;
                }
            }
        });
    }

</script>
