<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name='layout' content='admin'/>
    <title></title>
</head>

<body>
<sec:ifNotLoggedIn>
    <g:link controller="login" action="auth">Login</g:link>
</sec:ifNotLoggedIn>
<sec:ifLoggedIn>
    <g:link controller="admin" action="index">User List</g:link>
</sec:ifLoggedIn>
</body>
</html>