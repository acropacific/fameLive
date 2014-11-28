<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Registration</title>
    <g:javascript src="util.js"/>
</head>

<body>
<g:form controller="webUser" action="saveUserDetail">
    Username <g:textField name="username"/>
    Email <g:textField name="email"/>
    password <g:passwordField name="password"/>
    Fame Name <g:textField name="fameName"/>
    Mobile <g:textField name="mobile"/>
    <g:submitButton name="save"/>
</g:form>

</body>
</html>