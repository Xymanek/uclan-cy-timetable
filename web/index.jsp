<%@ page import="xymanek.timetable.DateSelectionParser" %>
<%@ page import="java.text.ParseException" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Date date = null;
    try {
        date = DateSelectionParser.getDateFromRequest(request);
    } catch (ParseException e) {
        response.setStatus(400);
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Timeline</title>

    <% if (date != null) { %>
    <link href="${pageContext.request.contextPath}/css/jquery-ui.css" rel="stylesheet"/>
    <link href="${pageContext.request.contextPath}/css/jquery.ui.theme.css" rel="stylesheet"/>

    <link href="${pageContext.request.contextPath}/css/timelineScheduler.css" rel="stylesheet"/>
    <link href="${pageContext.request.contextPath}/css/timelineScheduler.styling.css" rel="stylesheet"/>
    <%--<link href="css/calendar.css" rel="stylesheet" />--%>
    <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet"/>

    <script src="${pageContext.request.contextPath}/js/moment.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/jquery-3.1.0.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/jquery-ui-1.10.2.min.js"></script>

    <script src="${pageContext.request.contextPath}/js/timelineScheduler.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/calendar.js"></script>
    <script src="${pageContext.request.contextPath}/js/jquery.sticky-kit.min.js"></script>

    <%--suppress JSUnusedLocalSymbols --%>
    <script>
        var day = '<%= new SimpleDateFormat("dd/MM/yyyy").format(date) %>';
    </script>
    <% } %>
</head>
<body>

<% if (date != null) { %>
<div class="calendar">
    Loading...
</div>
<% } else { %>
<div class="error" style="color: red;">Bad date entered</div>
<% } %>

</body>
</html>