<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name='layout' content='admin'/>
    <title>User Profile</title>
</head>

<body>
<div>
    <h3 class="form-section">Fame User Profile</h3>

    <div class="portlet-body">
        <div class="tabbable-custom">
            <ul class="nav nav-tabs">
                <li class="active">
                    <a href="#tab_1_1_1" data-toggle="tab">
                        Overview
                    </a>
                </li>
                <li class="">
                    <a href="#tab_1_1_2" data-toggle="tab">
                        Account
                    </a>
                </li>
                <li class="">
                    <a href="#tab_1_1_3" data-toggle="tab">
                        Events
                    </a>
                </li>
                <li class="">
                    <a href="#tab_1_1_4" data-toggle="tab">
                        Followers
                    </a>
                </li>
                <li class="">
                    <a href="#tab_1_1_5" data-toggle="tab">
                        Following
                    </a>
                </li>
                <li class="pull-right">
                    <g:link controller="admin" action="blockOrUnBlockUserAccount"
                            params='[userId: "${adminUserInformationVO?.adminUserProfileDto?.id}"]'
                            class="btn ${adminUserInformationVO?.adminUserProfileDto?.accountLocked ? 'green' : 'red'}">
                        ${adminUserInformationVO?.adminUserProfileDto?.accountLocked ? 'Un Block' : 'Block'}</g:link>
                </li>
            </ul>

            <div class="tab-content">
                <div class="tab-pane active" id="tab_1_1_1">
                    <p>
                        <g:render template="userProfile" model="[adminUserInformationVO: adminUserInformationVO]"/>
                    </p>
                </div>

                <div class="tab-pane" id="tab_1_1_2">
                    <p>
                        <g:render template="userAccount" model="[adminUserInformationVO: adminUserInformationVO]"/>
                    </p>
                </div>

                <div class="tab-pane" id="tab_1_1_3">
                    <p>
                        <g:render template="userEvents" model="[adminUserInformationVO: adminUserInformationVO]"/>
                    </p>
                </div>
                <div class="tab-pane" id="tab_1_1_4">
                    <p>
                        <g:render template="followers" model="[adminUserInformationVO: adminUserInformationVO]"/>
                    </p>
                </div>
                <div class="tab-pane" id="tab_1_1_5">
                    <p>
                        <g:render template="following" model="[adminUserInformationVO: adminUserInformationVO]"/>
                    </p>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>