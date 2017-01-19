<%@ page import="com.google.appengine.api.datastore.Entity" %>
<%@ page import="com.google.appengine.api.datastore.EntityNotFoundException" %>
<%@ page import="xymanek.timetable.config.ConfigManager" %>
<%@ page import="xymanek.timetable.config.Validator" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String url;
    String token;

    String url_error = null;
    String token_error = null;

    String alert = null;
    String alert_type = null;

    if (request.getMethod().equalsIgnoreCase("POST")) {
        url = request.getParameter("url").trim();
        token = request.getParameter("token").trim();

        url_error = Validator.validateUrl(url);
        token_error = Validator.validateToken(token);

        if (url_error == null && token_error == null) {
            ConfigManager.save(url, token);

            alert = "Configuration successfully saved";
            alert_type = "success";
        } else {
            alert = "It looks like you've made a mistake - check your input";
            alert_type = "danger";
        }
    } else {
        try {
            Entity settings = ConfigManager.fetch();

            url = (String) settings.getProperty(ConfigManager.URL);
            token = (String) settings.getProperty(ConfigManager.TOKEN);
        } catch (EntityNotFoundException e) {
            url = "";
            token = "";
        }
    }
%>
<html>
<head>
    <title>Configuration</title>

    <link rel="stylesheet" href="css/bootstrap.min.css">
</head>
<body>

<div class="container">
    <h1 style="margin-left: 20px">Timetable configuration</h1>

    <% if (alert != null) {%>
    <div class="alert alert-<%= alert_type %>" role="alert">
        <%= alert %>
    </div>
    <% } %>

    <form class="form-horizontal" method="post">
        <div class="form-group<%= url_error != null ? " has-error" : "" %>">
            <label for="url" class="col-sm-2 control-label">URL</label>
            <div class="col-sm-9">
                <input type="url" class="form-control" id="url" value="<%= url %>" required name="url">
                <% if (url_error != null) { %>
                <span class="help-block"><%= url_error %></span>
                <% } else { %>
                <span class="help-block">Do not include the final question mark ("?")</span>
                <% } %>
            </div>
        </div>
        <div class="form-group<%= token_error != null ? " has-error" : "" %>">
            <label for="token" class="col-sm-2 control-label">Token</label>
            <div class="col-sm-9">
                <input type="text" class="form-control" id="token" value="<%= token %>" required name="token">
                <% if (token_error != null) { %>
                <span class="help-block"><%= token_error %></span>
                <% } %>
            </div>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <button type="submit" class="btn btn-primary">Save</button>
                <a href="${pageContext.request.contextPath}/" class="btn btn-default">Back to timetable</a>
            </div>
        </div>
    </form>
</div>

</body>
</html>
