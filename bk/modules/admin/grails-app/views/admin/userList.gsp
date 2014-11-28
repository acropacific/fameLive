<%@ page import="com.famelive.admin.enums.AdminUserType; com.famelive.admin.enums.AdminUserRegistrationType" contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name='layout' content='admin'/>
    <title>User List</title>
</head>

<body>
<br/>

<div>
    <g:form controller="admin" action="userList" class="form-horizontal">
        <div class="form-body">
            <h3 class="form-section">Registered User</h3>

            <div class="row">
                <div class="col-md-6">
                    <div class="form-group">
                        <label class="control-label col-md-3">Fame Name</label>

                        <div class="col-md-9">
                            <g:textField name="fameName" value="${adminUserSearchCommand?.fameName}"
                                         class="form-control"/>
                        </div>
                    </div>
                </div>

                <div class="col-md-6">
                    <div class="form-group">
                        <label class="control-label col-md-3">Email</label>

                        <div class="col-md-9">
                            <g:textField name="email" value="${adminUserSearchCommand?.email}" class="form-control"/>
                        </div>
                    </div>
                </div>

            </div>

            <div class="row">
                <div class="col-md-6">
                    <div class="form-group">
                        <label class="control-label col-md-3">Fame ID</label>

                        <div class="col-md-9">
                            <g:textField name="fameId" value="${adminUserSearchCommand?.fameId}"
                                         class="form-control"/>
                        </div>
                    </div>
                </div>

                <div class="col-md-6">
                    <div class="form-group">
                        <label class="control-label col-md-3">Registered through</label>

                        <div class="col-md-9">
                            <g:select from="${AdminUserRegistrationType.values()}" name="registrationType"
                                      value="${adminUserSearchCommand?.registrationType}" class="form-control"
                                      optionValue="value" noSelection="['': 'Select']"/>
                        </div>
                    </div>
                </div>

            </div>

            <div class="row">
                <div class="col-md-6">
                    <div class="form-group">
                        <label class="control-label col-md-3">Registered as</label>

                        <div class="col-md-9">
                            <g:select from="${AdminUserType.values()}" name="type"
                                      value="${adminUserSearchCommand?.type}" class="form-control"
                                      optionValue="value" noSelection="['': 'Select']"/>
                        </div>
                    </div>
                </div>

            </div>

            <div class="row">
                <div class="col-md-12">
                    <div class="pull-right">
                        <g:submitButton name="Search" class="btn green"/>
                    </div>
                </div>
            </div>
        </div>
    </g:form>
    <div class="portlet box yellow">
        <div class="portlet-title">
            <div class="caption">
                <i class="fa fa-cogs"></i>User Detail
            </div>

            <div class="tools">
                <a href="javascript:;" class="collapse">
                </a>
            </div>
        </div>

        <div class="portlet-body">
            <div class="table-responsive">
                <table class="table table-striped table-bordered table-hover">
                    <thead>
                    <tr>
                        <th class="col-md-1">Fame Id</th>
                        <th class="col-md-1">Fame Name</th>
                        <th class="col-md-2">Name</th>
                        <th class="col-md-2">Email Id</th>
                        <th class="col-md-2">Date / Time of Registration</th>
                        <th class="col-md-1">Registered through</th>
                        <th class="col-md-1">Registered as</th>
                    </tr>
                    </thead>
                    <tbody>

                    <g:if test="${adminUsersDto.profileDtoList}">
                        <g:each in="${adminUsersDto.profileDtoList}" status="i" var="user">
                            <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
                                <td class="col-md-1">${user?.fameId}</td>
                                <td class="col-md-1">${user?.fameName}</td>
                                <td class="col-md-2">${user?.username}</td>
                                <td class="col-md-2">${user?.email}</td>
                                <td class="col-md-2">${user?.dateCreated?.format("MM/dd/yyyy")}</td>
                                <td class="col-md-1">${user?.registrationType}</td>
                                <td class="col-md-1">${user?.type}</td>
                                <td class="col-md-1"><g:link controller="admin" action="userProfile" class="btn green"
                                                             params='[userId: "${user?.id}"]'>View Profile <i
                                            class="fa fa-user"></i></g:link></td>
                            </tr>
                        </g:each>
                    </g:if>
                    <g:else>
                        <tr>
                            <td colspan="7">No user found</td>
                        </tr>
                    </g:else>
                    </tbody>
                </table>
            </div>

            <div class="dataTables_paginate paging_bootstrap">
                <div class="pagination">
                    <g:paginate total="${adminUsersDto.userCount ?: 0}"/>
                </div>
            </div>
        </div>
    </div>

</div>

<g:javascript>
    jQuery(document).ready(function () {
        jQuery(".pagination ul").addClass('pagination');
    });
</g:javascript>
</body>
</html>