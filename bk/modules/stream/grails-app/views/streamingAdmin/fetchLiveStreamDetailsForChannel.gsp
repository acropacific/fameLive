<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name='layout' content='streamingAdmin'/>
    <title>Live Stream Details</title>
</head>

<body>
<div>
    <h3 class="form-section">Live Stream Details</h3>

    <div class="portlet-body">
        <div class="tabbable-custom">
            <ul class="nav nav-tabs">
                <li class="active">
                    <a href="#tab_1_1_1" data-toggle="tab">
                        Stream Information
                    </a>
                </li>
                <li class="">
                    <a href="#tab_1_1_2" data-toggle="tab">
                        Stream Statistics
                    </a>
                </li>
                <li class="">
                    <a href="#tab_1_1_3" data-toggle="tab">
                        Stream Connections
                    </a>
                </li>
                <li class="">
                    <a href="#tab_1_1_4" data-toggle="tab">
                        Live Stream Operations
                    </a>
                </li>
                %{--<li class="">
                    <a href="#tab_1_1_5" data-toggle="tab">
                        Outgoing Stream Information
                    </a>
                </li>--}%
            </ul>

            <div class="tab-content">
                <div class="tab-pane active" id="tab_1_1_1">
                    <p>
                        <g:render template="liveStreamInfo"
                                  model="[liveStreamDetailsDTO: liveStreamDetailsDTO]"/>
                    </p>
                </div>

                <div class="tab-pane" id="tab_1_1_2">
                    <p>
                        <g:render template="liveStreamStatistics"
                                  model="[inStreamStatisticsDTO: liveStreamDetailsDTO?.inStreamStatisticsDTO]"/>
                    </p>
                </div>

                <div class="tab-pane" id="tab_1_1_3">
                    <p>
                        <g:render template="liveStreamConnections"
                                  model="[inStreamStatisticsDTO: liveStreamDetailsDTO?.inStreamStatisticsDTO, streamVideoInfoDTO: liveStreamDetailsDTO?.streamVideoInfoDTO]"/>
                    </p>
                </div>

                <div class="tab-pane" id="tab_1_1_4">
                    <p>
                        <g:render template="liveStreamOperations"
                                  model="[liveStreamDetailsDTO: liveStreamDetailsDTO]"/>
                    </p>
                </div>

                %{--<div class="tab-pane" id="tab_1_1_5">
                    <p>
                        <g:render template="liveOutgoingStreamInfo"
                                  model="[eventInfoDTOs: liveStreamDetailsDTO?.liveStreamDetailsDTO]"/>
                    </p>
                </div>--}%

            </div>
        </div>
    </div>
</div>
<tmpl:openFrame confirmDialogId='openFrameForEventDetails' confirmDialogHeader='Confirm'/>
<tmpl:openFrameForVideoPlayer confirmDialogId='openFrameForVideoPlayer' confirmDialogHeader='Confirm'/>
</body>
</html>