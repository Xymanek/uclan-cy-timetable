<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Timeline</title>

    <link href="css/jquery-ui.css" rel="stylesheet"/>
    <link href="css/jquery.ui.theme.css" rel="stylesheet"/>

    <link href="css/timelineScheduler.css" rel="stylesheet"/>
    <link href="css/timelineScheduler.styling.css" rel="stylesheet"/>
    <%--<link href="css/calendar.css" rel="stylesheet" />--%>

    <script src="js/moment.min.js"></script>
    <script src="js/jquery-3.1.0.min.js"></script>
    <script src="js/jquery-ui-1.10.2.min.js"></script>

    <script src="js/timelineScheduler.min.js"></script>
    <script src="js/calendar.js"></script>
    <script src="js/jquery.sticky-kit.min.js"></script>

    <style>
        body {
            font-size: 20px;
            margin-top: 0;
            overflow: hidden;
        }

        .time-sch-header-wrapper {
            display: none;
        }

        .time-sch-content-header-wrap {
            z-index: 99999999999;
            background-color: white;
            padding-top: 8px;
        }

        .time-sch-times-header-0 td:first-child {
            font-size: 47px;
            text-align: center;
            border-bottom: solid 1px #e1e1e1;
        }
    </style>

    <% String day = request.getParameter("day"); %>
    <% if (day != null) { %>
        <script>
            var day = '<%= day %>';
        </script>
    <% } %>
</head>
<body>

<div class="calendar">
Loading...
</div>

</body>
</html>