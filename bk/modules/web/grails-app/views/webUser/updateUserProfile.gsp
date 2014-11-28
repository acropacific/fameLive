<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Update User Profile</title>
</head>

<body>
<g:form controller="webUser" action="updateUserDetail">
    <div>
        <div>Username <g:textField name="username" value="${userDetail.username}"/></div>

        <div>Email ${userDetail.email}<g:hiddenField name="email" value="${userDetail.email}"/></div>

        <div>Fame Name <g:textField name="fameName" value="${userDetail.fameName}"/></div>

        <div>Mobile <g:textField name="mobile" value="${userDetail.mobile}"/></div>

        <div><g:hiddenField name="id" value="${userDetail.id}"/></div>

        <div><g:submitButton name="Update"/></div>

    </div>
</g:form>
</body>
</html>