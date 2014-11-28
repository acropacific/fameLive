<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Reset Password</title>
</head>

<body>
<g:form controller="webUser" action="updatePassword">
    <div>
        <div>Old Password <g:passwordField name="password"/></div>

        <div>New Password <g:passwordField name="newPassword"/></div>

        <div>Confirm Password <g:passwordField name="confirmPassword"/></div>

        <div><g:hiddenField name="email" value="${userDetail?.email}"/></div>

        <div><g:submitButton name="Update"/></div>
    </div>
</g:form>
</body>
</html>