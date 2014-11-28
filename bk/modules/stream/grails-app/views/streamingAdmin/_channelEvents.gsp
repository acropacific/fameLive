<%@ page import="com.famelive.common.util.CommonMessagesUtil; com.famelive.streamManagement.constant.StreamingConstants;" %>
<div class="row">
    <div class="col-md-12">
        <div class="portlet box yellow">
            <div class="portlet-title">
                <div class="caption">
                    <i class="fa fa-cogs"></i>Channel Events
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
                            <th class="col-md-1"></th>
                        </tr>
                        </thead>
                        <tbody>
                        <g:if test="${eventInfoDTOs}">
                            <g:each in="${eventInfoDTOs}" status="i"
                                    var="event">
                                <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
                                    <td class="col-md-1">${event?.eventUId}</td>
                                    <td class="col-md-1">${event.name}</td>
                                    <td class="col-md-1">${event?.eventPerformer}</td>
                                    <td class="col-md-2">${event?.eventPerformerEmail}</td>
                                    <td class="col-md-2">${event?.eventStartTime?.format(StreamingConstants.DATE_TIME_FORMAT)}</td>
                                    <td class="col-md-1">${event?.eventDuration}</td>
                                    <td class="col-md-1">${event?.eventGenre}</td>
                                    <td class="col-md-1">
                                        <a href="#openFrameForEventDetails" data-toggle="modal"
                                           onclick="$('#openFrameForEventDetails_iframe').attr('src', '${grailsApplication.config.famelive.adminPanel.homepage.url}/event/viewEventDetail?eventId=${event?.eventId}');">Event Detail</a>
                                    </td>
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