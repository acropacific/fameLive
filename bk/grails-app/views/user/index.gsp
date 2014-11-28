<%@ page import="com.famelive.common.user.User" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'user.label', default: 'User')}"/>
    <title><g:message code="default.list.label" args="[entityName]"/></title>
</head>

<body>
<a href="#list-user" class="skip" tabindex="-1"><g:message code="default.link.skip.label"
                                                           default="Skip to content&hellip;"/></a>

<div class="nav" role="navigation">
    <ul>
        <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
        <li><g:link class="create" action="create"><g:message code="default.new.label"
                                                              args="[entityName]"/></g:link></li>
    </ul>
</div>

<div id="list-user" class="content scaffold-list" role="main">
    <h1><g:message code="default.list.label" args="[entityName]"/></h1>
    <g:if test="${flash.message}">
        <div class="message" role="status">${flash.message}</div>
    </g:if>
    <table>
        <thead>
        <tr>

            <g:sortableColumn property="username" title="${message(code: 'user.username.label', default: 'Username')}"/>

            <g:sortableColumn property="password" title="${message(code: 'user.password.label', default: 'Password')}"/>

            <g:sortableColumn property="email" title="${message(code: 'user.email.label', default: 'Email')}"/>

            <g:sortableColumn property="mobile" title="${message(code: 'user.mobile.label', default: 'Mobile')}"/>

            <g:sortableColumn property="imageName"
                              title="${message(code: 'user.imageName.label', default: 'Image Name')}"/>

            <g:sortableColumn property="fameName"
                              title="${message(code: 'user.fameName.label', default: 'Fame Name')}"/>

        </tr>
        </thead>
        <tbody>
        <g:each in="${userInstanceList}" status="i" var="userInstance">
            <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">

                <td><g:link action="show"
                            id="${userInstance.id}">${fieldValue(bean: userInstance, field: "username")}</g:link></td>

                <td>${fieldValue(bean: userInstance, field: "password")}</td>

                <td>${fieldValue(bean: userInstance, field: "email")}</td>

                <td>${fieldValue(bean: userInstance, field: "mobile")}</td>

                <td>${fieldValue(bean: userInstance, field: "imageName")}</td>

                <td>${fieldValue(bean: userInstance, field: "fameName")}</td>

            </tr>
        </g:each>
        </tbody>
    </table>

    <div class="pagination">
        <g:paginate total="${userInstanceCount ?: 0}"/>
    </div>
</div>
</body>
</html>
