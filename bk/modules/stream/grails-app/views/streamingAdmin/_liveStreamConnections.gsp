<%@ page import="com.famelive.common.util.CommonMessagesUtil; com.famelive.streamManagement.constant.StreamingConstants;" %>
<div class="row">
    <div class="col-md-12">
        <div class="portlet box yellow">
            <div class="portlet-title">
                <div class="caption">
                    <i class="fa fa-cogs"></i>Live Stream Connections
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
                            <th class="col-md-1">Connection type</th>
                            <th class="col-md-1">No of connections</th>
                            <th class="col-md-1"></th>
                        </tr>
                        </thead>
                        <tbody>
                        <g:if test="${inStreamStatisticsDTO != null}">
                            <g:each in="${inStreamStatisticsDTO?.outConnections?.keySet()}" status="i"
                                    var="connectionType">
                                <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
                                    <td class="col-md-1">${connectionType}</td>
                                    <td class="col-md-1">${inStreamStatisticsDTO?.outConnections?.get(connectionType)}</td>
                                    <td class="col-md-1">
                                        <a href="#openFrameForVideoPlayer" data-toggle="modal" class="btn green"
                                           onclick="$('#openFrameForVideoPlayer_iframe').attr('src', '${grailsApplication.config.famelive.streamingAdminPanel.homepage.url}/test/launchPlayer?streamUrl=${streamVideoInfoDTO?.fetchRTMPStreamUrl()}');">Play</a>
                                    </td>
                                </tr>
                            </g:each>
                        </g:if>
                        <g:else>
                            <tr>
                                <td colspan="7">Currently any stream is not live on this channel</td>
                            </tr>
                        </g:else>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>