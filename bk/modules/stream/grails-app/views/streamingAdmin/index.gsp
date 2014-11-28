<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name='layout' content='streamingAdmin'/>
    <title></title>
</head>

<body>
<div>
    <sec:ifNotLoggedIn>
        <g:link controller="login" action="auth">Login</g:link>
    </sec:ifNotLoggedIn>
    <sec:ifLoggedIn>
    %{--<g:link controller="streamingAdmin" action="userList">User List</g:link>--}%
    </sec:ifLoggedIn>
</div>
</body>
</html>