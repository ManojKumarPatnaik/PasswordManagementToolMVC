<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Password-Management-Tool | Update Record</title>
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
    <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/common.css" rel="stylesheet">
    <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
</head>
<body>
<div class="container">
    <%--@elvariable id="recordDTO" type="com.epam.rd.dto"--%>
    <form:form method="POST" class="form-signin" action="${pageContext.request.contextPath}/record/updateRecord"
               modelAttribute="recordDTO">
        <h2 class="p-3 mb-2 bg-primary text-white">Update Record</h2><br>
        <span class="text-success">${message}</span>
        <div class="form-group-sm">
            <label class="my-1 mr-sm-3">UserName</label>
            <form:input type="text" path="userName" class="form-control" placeholder="UserName"
                        value="${record.userName}"
                        autofocus="true"/>
            <form:errors path="userName" cssClass="has-error"></form:errors>
        </div>
        <div class="form-group-sm">
            <label class="my-1 mr-sm-3">Password</label>
            <form:input type="password" path="password" class="form-control" placeholder="Password"
                        value="${record.password}" autofocus="true"/>
            <form:errors path="password" cssClass="has-error"></form:errors>
        </div>
        <div class="form-group-sm">
            <label class="my-1 mr-sm-3">Url</label>
            <form:input type="text" path="url" id="url" class="form-control" placeholder="Url" value="${record.url}" readonly="true"/>
            <form:errors path="url" cssClass="has-error"></form:errors>
        </div>
        <div class="form-group-sm">
            <label class="my-1 mr-sm-3">Notes</label>
            <form:input type="textarea" path="notes" class="form-control" placeholder="Notes"
                        value="${record.notes}"/>
            <form:errors path="notes" cssClass="has-error"></form:errors>
        </div>
        <span class="has-error">${error}</span>
        <button class="btn btn-lg btn-primary btn-block" type="submit">Update</button>
        <div class="border-bottom">
            <h4 class="text-center"><a href="${pageContext.request.contextPath}/logout"><i
                    class="material-icons">&#xe01c;</i><span>Logout</span></a></h4>
        </div>
    </form:form>
</div>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
</body>
</html>