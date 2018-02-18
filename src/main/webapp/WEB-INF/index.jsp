<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:requestEncoding value="UTF-8"/>
<fmt:setLocale value="${sessionScope.USER_LOCALE}"/>
<fmt:setBundle basename="views_locales.index" var="lang"/>

<!doctype html>
<html lang="ua">
<head>

    <link rel="stylesheet" href="./res/css/pikaday.css">
    <c:import url="colon/css_dependencies.jsp" charEncoding="utf-8"/>
    <script src="./res/js/moment.js"></script>
    <link rel="stylesheet" href="./res/bootstrap/css/typeaheadjs.css">

    <title><fmt:message key="page.title" bundle="${lang}"/></title>
</head>
<body>
<c:import url="colon/navbar.jsp" charEncoding="utf-8"/>

<br>

<div class="container">
    <div class="jumbotron">
        <h2><fmt:message key="wishing.of.good.travelling.inscription" bundle="${lang}"/></h2>
        <p><fmt:message key="choose.stations.inscription" bundle="${lang}"/></p>

        <form class="form-inline" action="search_trains" name="search_trains" id="search_trains" method="POST">
            <div class="container">
                <div class="row">
                    <div class="row">
                        <div class="form-group col-lg-4 col-md-4 col-sm-4">
                            <div class="input-group">
                                <input aria-describedby="sizing-addon1" type="text"
                                       class="form-control input-lg typeahead"
                                       name="from" id="from" value="${userRoute.fromStationName}"
                                       autocomplete="off" rel="autocomp1"
                                       placeholder="<fmt:message key="from.input.placeholder" bundle = "${lang}"/>"
                                       onchange="clearId('fromId');"/>
                                <span class="input-group-addon right-radius" id="sizing-addon1"><i
                                        class="fa fa-train"></i></span>
                                <input type="hidden" id="fromId" name="fromId"
                                       value="${userRoute.fromStationId}"/>
                            </div>
                        </div>

                        <div class="form-group col-lg-4 col-md-4 col-sm-4">
                            <div class="input-group">
                                <input aria-describedby="sizing-addon2" type="text"
                                       class="form-control input-lg typeahead"
                                       name="to" value="${userRoute.toStationName}"
                                       id="to" autocomplete="off" rel="autocomp2"
                                       placeholder="<fmt:message key="where.input.placeholder" bundle = "${lang}"/>"
                                       required onchange="clearId('toId');"/>
                                <span class="input-group-addon right-radius" id="sizing-addon2"><i
                                        class="fa fa-train"></i></span>
                                <input type="hidden" id="toId" name="toId" value="${userRoute.toStationId}"/>
                            </div>
                        </div>

                        <div class="form-group col-lg-3 col-md-3 col-sm-3">
                            <div class="input-group">
                                <input type="text" class="form-control input-lg" name="date" id="date"
                                       value="${userRoute.dateString}" autocomplete="off"
                                       placeholder="<fmt:message key="when.input.placeholder" bundle = "${lang}"/>"
                                       required>
                                <span aria-describedby="sizing-addon3" class="input-group-addon" id="sizing-addon3"><i
                                        class="fa fa-calendar"></i></span>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="row">
                    <br>
                    <div class="btn-group  btn-group-lg" role="group">
                        <button type="submit" class="btn btn-primary "><fmt:message key="search.button"
                                                                                    bundle="${lang}"/></button>
                    </div>
                </div>
            </div>
        </form>
    </div>
</div>


<div class="container">
    <div class="row">
        <c:if test="${isListEmpty}">
            <br>
            <h3><fmt:message key="empty.route.list.msg" bundle="${lang}"/></h3>
        </c:if>

        <c:if test="${trains ne null}">
            <div class="table-responsive col-md-12">
                <table class="table table-bordered table-striped" id="train_table">
                    <thead>
                    <tr>
                        <th><fmt:message key="train.number.table.col" bundle="${lang}"/></th>
                        <th><fmt:message key="route.table.col" bundle="${lang}"/></th>
                        <th><fmt:message key="leaving.time.table.col" bundle="${lang}"/></th>
                        <th><fmt:message key="arrival.time.table.col" bundle="${lang}"/></th>
                        <th><fmt:message key="choose.table.col" bundle="${lang}"/></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${trains}" var="train" varStatus="loopCount">
                    <tr>
                        <td align="left"><c:out value="${train.trainNumber}" default="" escapeXml="true"/></td>
                        <td align="left"><c:out value="${train.fromStation}-${train.toStation}" default=""
                                                escapeXml="true"/></td>
                        <td align="left"><c:out value="${train.leavingDate}" default="" escapeXml="true"/></td>
                        <td align="left"><c:out value="${train.arrivalDate}" default="" escapeXml="true"/></td>
                        <td align="left"><a class="btn btn-success" role="button"
                                            href='<c:url value="/"/>order_ticket?trainId=${train.trainId}&routeId=${train.routeId}&train=${train.trainNumber}'><fmt:message
                                key="choose.table.col" bundle="${lang}"/></a></td>
                    <tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </c:if>
    </div>
</div>

<br><br>
<c:import url="colon/footer.jsp" charEncoding="utf-8"/>
</body>


<c:import url="colon/js_dependencies.jsp" charEncoding="utf-8"/>
<script src="./res/bootstrap/js/typeahead.js"></script>
<script src="./res/js/pikaday.js"></script>
</html>


<script>
    // Get your data source
    var dataSource = new Bloodhound({
        datumTokenizer: function (datum) {
            return Bloodhound.tokenizers.whitespace(datum.value);
        },
        queryTokenizer: Bloodhound.tokenizers.whitespace,
        remote: {
            wildcard: '%QUERY',
            url: 'ajax/station_advice?autocomplete=%QUERY'
        },
        transform: function (response) {
            // Map the remote source JSON array to a JavaScript object array
            return response.items;
        }
    });

    // instantiate the typeahead UI
    var $typeheadFrom = $('#from').typeahead({
            hint: true,
            highlight: true,
            minLength: 2
        },

        {
            displayKey: function (suggestion) {
                return suggestion.autocompleteWord;
            },
            source: dataSource.ttAdapter()
        });

    // fire a select event, what you want once a user has selected an item
    $typeheadFrom.on('typeahead:select', function (obj, datum) {
        $('#fromId').val(datum.id);
    });

    // instantiate the typeahead UI
    var $typeheadTo = $('#to').typeahead({
            hint: true,
            highlight: true,
            minLength: 2
        },

        {
            displayKey: function (suggestion) {
                return suggestion.autocompleteWord;
            },
            minLength: 2,
            source: dataSource.ttAdapter()
        });

    // fire a select event, what you want once a user has selected an item
    $typeheadTo.on('typeahead:select', function (obj, datum) {
        $('#toId').val(datum.id);
    });

    var timepicker = new Pikaday({
        field: document.getElementById("date"),
        firstDay: 1,
        minDate: new Date(),
        maxDate: new Date(2100, 12, 31),
        yearRange: [2017, 2100],
        showTime: false,
        autoClose: false,
        use24hour: false,
        showDaysInNextAndPreviousMonths: true,
        format: 'DD.MM.YYYY'
    });

    $(document).ready(function () {
        $('#search_trains').bootstrapValidator({
            live: 'disabled',
            feedbackIcons: {
                valid: 'glyphicon glyphicon-ok',
                invalid: 'glyphicon glyphicon-remove',
                validating: 'glyphicon glyphicon-refresh'
            },
            fields: {
                from: {
                    validators: {
                        notEmpty: {
                            message: '<fmt:message key="not.empty.from.field.validation.msg" bundle="${lang}"/>'
                        },
                        stringLength: {
                            min: 0,
                            max: 255,
                            message: '<fmt:message key="not.more.than.from.field.validation.msg" bundle="${lang}"/>'
                        }
                    }
                },
                fromId: {
                    validators: {
                        notEmpty: {
                            message: '<fmt:message key="not.empty.from.field.validation.msg" bundle="${lang}"/>'
                        }
                    }
                },

                to: {
                    validators: {
                        notEmpty: {
                            message: '<fmt:message key="not.empty.to.field.validation.msg" bundle="${lang}"/>'
                        },
                        stringLength: {
                            min: 0,
                            max: 255,
                            message: '<fmt:message key="not.more.than.to.field.validation.msg" bundle="${lang}"/>'
                        }
                    }
                },
                toId: {
                    validators: {
                        notEmpty: {
                            message: '<fmt:message key="not.empty.from.field.validation.msg" bundle="${lang}"/>'
                        }
                    }
                },
                date: {
                    validators: {
                        notEmpty: {
                            message: '<fmt:message key="not.empty.date.field.validation.msg" bundle="${lang}"/>'
                        },
                        date: {
                            format: 'DD.MM.YYYY',
                            separator: '.',
                            message: '<fmt:message key="invalid.format.date.field.validation.msg" bundle="${lang}"/>'
                        },
                        stringLength: {
                            min: 0,
                            max: 14,
                            message: '<fmt:message key="not.more.than.date.field.validation.msg" bundle="${lang}"/>'
                        }
                    }
                }
            }
        });
        $('#date')
            .on('blur', function (e) {
                $('#search_trains').data('bootstrapValidator').revalidateField('date');
            });
    });

    function clearId(id) {
        $('#' + id).val("");
    }

</script>