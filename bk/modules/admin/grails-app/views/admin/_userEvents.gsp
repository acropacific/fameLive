<%@ page import="com.famelive.admin.constant.AdminConstants" %>
<div class="row">
    <div class="col-md-12">
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
                        <g:if test="${adminUserInformationVO?.adminFetchEventsDto?.apiEventDetailDtos}">
                            <g:each in="${adminUserInformationVO.adminFetchEventsDto.apiEventDetailDtos}" status="i"
                                    var="event">
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
            </div>
        </div>
    </div>
</div>