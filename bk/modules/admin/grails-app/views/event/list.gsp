<%@ page import="com.famelive.admin.util.AdminDateTimeUtils; com.famelive.admin.constant.AdminConstants" contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name='layout' content='admin'/>
    <title>List Events</title>
</head>

<body>
<g:form controller="event" action="list" class="form-horizontal">
    <div class="form-body">
        <div class="col-md-2 pull-right"><g:link controller="event" action="create" class="btn green">
            Create Event
            <i class="fa fa-pencil"></i>
        </g:link></div>

        <h3 class="form-section">Event List</h3>

        <div class="row">
            <div class="col-md-6">
                <div class="form-group">
                    <label class="control-label col-md-3">Start Time</label>

                    <div class="col-md-9">
                        <div class="input-group input-medium date date-picker" data-date-format="dd/mm/yyyy"
                             data-date-start-date="+0d" style="width: 100% !important;">
                            <g:textField name="startDate" class="form-control" readonly="readonly"
                                         value="${adminFetchAllEventCommand?.startDate ?: AdminDateTimeUtils.defaultStartDate}"/>
                            <span class="input-group-btn">
                                <button class="btn default date-set" type="button"><i class="fa fa-calendar"></i>
                                </button>
                            </span>
                        </div>
                    </div>
                </div>
            </div>

        </div>

        <div class="row">
            <div class="col-md-6">
                <div class="form-group">
                    <label class="control-label col-md-3">Member Name</label>

                    <div class="col-md-9">
                        <g:textField name="memberName" value="${adminFetchAllEventCommand?.memberName}"
                                     class="form-control"/>
                    </div>
                </div>
            </div>

            <div class="col-md-6">
                <div class="form-group">
                    <label class="control-label col-md-3">Email</label>

                    <div class="col-md-9">
                        <g:textField name="email" value="${adminFetchAllEventCommand?.email}" class="form-control"/>
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
            <i class="fa fa-cogs"></i>Events
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
                    <th class="col-md-1">Event Id</th>
                    <th class="col-md-1">Event Name</th>
                    <th class="col-md-1">Performer Name</th>
                    <th class="col-md-2">Email Id</th>
                    <th class="col-md-2">Start Date/Time</th>
                    <th class="col-md-1">Duration (in Minutes)</th>
                    <th class="col-md-1">Genre Name</th>
                </tr>
                </thead>
                <tbody>
                <g:if test="${adminFetchEventsDto.apiEventDetailDtos}">
                    <g:each in="${adminFetchEventsDto.apiEventDetailDtos}" status="i" var="event">
                        <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
                            <td class="col-md-1">${event?.eventUId}</td>
                            <td class="col-md-1">${event.name}</td>
                            <td class="col-md-1">${event?.user?.username}</td>
                            <td class="col-md-2">${event?.user?.email}</td>
                            <td class="col-md-2">${event?.startTime?.format(AdminConstants.DATE_TIME_FORMAT)}</td>
                            <td class="col-md-1">${event?.duration}</td>
                            <td class="col-md-1">${event?.genre?.name}</td>
                            <td class="col-md-1"><g:link controller="event" action="viewEventDetail"
                                                         params='[eventId: "${event?.eventId}"]'
                                                         class="btn green">Event Detail <i
                                        class="fa fa-film"></i></g:link></td>
                            <td class="col-md-1"><g:link controller="event" action="edit"
                                                         params='[eventId: "${event.eventId}"]'
                                                         class="btn green">Edit <i class="fa fa-edit"></i></g:link>
                            </td>
                            <td class="col-md-1"><g:link controller="event" action="cancel"
                                                         params='[eventId: "${event?.eventId}"]'
                                                         class="btn red">Cancel <i
                                        class="fa fa-times-circle"></i></g:link></td>
                        </tr>
                    </g:each>
                </g:if>
                <g:else>
                    <tr>
                        <td colspan="7">No event found</td>
                    </tr>
                </g:else>
                </tbody>
            </table>
        </div>

        <div class="dataTables_paginate paging_bootstrap">
            <div class="pagination">
                <g:paginate total="${adminFetchEventsDto.eventCount ?: 0}"/>
            </div>
        </div>
    </div>
</div>
</body>
</html>