<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Password-Management-Tool | Home</title>
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto|Varela+Round">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
    <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>
    <link href="${pageContext.request.contextPath}/css/userhome.css" rel="stylesheet">
    <script src="${pageContext.request.contextPath}/js/userhome.js" type="text/javascript"></script>
</head>
<body>
<div class="container">
    <div class="table-responsive">
        <div class="table-wrapper">
            <div class="table-title">
                <div class="row">
                    <div class="col-sm-6">
                        <h2>Password Management Tool</h2>
                    </div>
                    <div class="col-sm-3">
                        <h6 class="btn btn-primary"><i class="material-icons">&#xe853;</i><b>${message}</b>
                        </h6>
                    </div>
                    <div class="col-sm-3">
                        <a href="${pageContext.request.contextPath}/record/showNewRecordForm"
                           class="btn btn-success"><i
                                class="material-icons">&#xE147;</i> <span>Add Record</span></a>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-2">
                    <input type="button" value="Show Records" onclick="enableTable()" class="btn btn-primary">
                </div>
                <div class="col-sm-3">
                    <input type="button" value="Hide Records" onclick="disabledTable()" class="btn btn-primary">
                </div>
                <div class="col-sm-3">
                    <a href="${pageContext.request.contextPath}/group/showAddGroupForm" class="btn btn-success"><span>Add Group</span></a>
                </div>
                <div class="col-sm-4">
                    <a href="${pageContext.request.contextPath}/group/viewGroups" class="btn btn-success"><span>View Group</span></a>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-2">
                    <table class="table table-striped table-hover" id="groupTable">
                        <thead>
                        <tr>
                            <th>Group-Name</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="group" items="${groupList}">
                            <tr>
                                <td>
                                    <a href="${pageContext.request.contextPath}/group/findRecordByGroup/${group.name}"><c:out
                                            value="${group.name}"/></a>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <a href="${pageContext.request.contextPath}/group/showEditGroupForm/${group.name}"
                                       class="edit"><i class="material-icons"
                                                       title="Edit">&#xE254;</i></a>
                                    <a data-toggle="modal" class="Open-deleteGroupModal delete" href="#"
                                       data-id="${group.name}"><i class="material-icons"
                                                                data-toggle="tooltip"
                                                                title="Delete">&#xE872;</i></a>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
                <div class="col-sm-10">
                    <table class="table table-striped table-hover" id="dataTable" style="visibility:hidden;">
                        <thead>
                        <tr>
                            <th>No.</th>
                            <th>UserName</th>
                            <th>Password</th>
                            <th>Url</th>
                            <th>Notes</th>
                            <th>Actions</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="record" items="${recordList}" varStatus="i">
                            <tr>
                                <td><c:out value="${i.index + 1}"/></td>
                                <td><c:out value="${record.userName}"/></td>
                                <td><c:out value="${record.password}"/></td>
                                <td><c:out value="${record.url}"/></td>
                                <td><c:out value="${record.notes}"/></td>
                                <td>
                                    <a href="${pageContext.request.contextPath}/record/showEditRecordForm?url=${record.url}"
                                       class="edit"><i class="material-icons"
                                                       title="Edit">&#xE254;</i></a>
                                    <a data-toggle="modal" class="Open-deleteRecordModal delete" href="#"
                                       data-id="${record.url}"><i class="material-icons"
                                                                  data-toggle="tooltip"
                                                                  title="Delete">&#xE872;</i></a>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
            <span class="text-success">${success}</span>
            <span class="text-danger">${error}</span>
            <div class="border-bottom">
                <h4 class="text-center"><a href="${pageContext.request.contextPath}/logout"><i
                        class="material-icons">&#xe01c;</i><span>Logout</span></a></h4>
            </div>
        </div>
    </div>
</div>

<!-- Delete Record Modal HTML -->
<div id="deleteRecordModal" class="modal fade">
    <div class="modal-dialog">
        <div class="modal-content">
            <form:form method="POST" class="form-signin"
                       action="${pageContext.request.contextPath}/record/deleteRecord">
                <div class="modal-header">
                    <h4 class="modal-title">Delete Record</h4>
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                </div>
                <div class="modal-body">
                    <p>Are you sure want to delete record?</p>
                    <p class="text-warning"><small>This action cannot be undone</small></p>
                    <input type="hidden" name="url_id" id="u_id" value="">
                </div>
                <div class="modal-footer">
                    <input type="button" class="btn btn-default" data-dismiss="modal" value="Cancel">
                    <input type="submit" class="btn btn-danger" value="Delete">
                </div>
            </form:form>
        </div>
    </div>
</div>

<!-- Delete Group Modal HTML -->
<div id="deleteGroupModal" class="modal fade">
    <div class="modal-dialog">
        <div class="modal-content">
            <form:form method="POST" class="form-signin"
                       action="${pageContext.request.contextPath}/group/deleteGroup">
                <div class="modal-header">
                    <h4 class="modal-title">Delete Group</h4>
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                </div>
                <div class="modal-body">
                    <p>Are you sure want to delete group?</p>
                    <p class="text-warning"><small>This action cannot be undone</small></p>
                    <input type="hidden" name="group_name" id="group_name" value="">
                </div>
                <div class="modal-footer">
                    <input type="button" class="btn btn-default" data-dismiss="modal" value="Cancel">
                    <input type="submit" class="btn btn-danger" value="Delete">
                </div>
            </form:form>
        </div>
    </div>
</div>
</body>
</html>