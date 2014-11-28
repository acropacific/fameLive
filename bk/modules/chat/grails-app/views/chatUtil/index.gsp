<!DOCTYPE html>
<html>
<head>
    <title><g:if env="development">Grails Runtime Exception</g:if><g:else>Error</g:else></title>
    <meta name="layout" content="main">
    <g:if env="development"><asset:stylesheet src="errors.css"/></g:if>

</head>

<body>
<g:form action="chat" controller="chatUtil">
    Enter Your Name:<br>
    <g:textField name="name" placeholder="enter your name"/><br><br>
    Select Chanel:<br>
    <g:select name="chanel" from="${['promtionChannel', 2, 3, 4]}" keys="${['promtionChannel', 2, 3, 4]}"/><br><br>
    <g:submitButton name="submit" value="Click To Start Chat"/><br><br><br><br>
</g:form>
</body>
</html>