<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Web</title>
</head>

<body>
<sec:ifNotLoggedIn>
    <g:link controller="webUser" action="register">Register</g:link>
</sec:ifNotLoggedIn>
<sec:ifLoggedIn>
    <g:link controller="webUser" action="userProfile">Show User Profile</g:link> <br/>
    <g:link controller="webUser" action="updateUserProfile">Update User Profile</g:link>
    <g:link controller="webUser" action="changePassword">Change Password</g:link>
</sec:ifLoggedIn>
</body>
</html>