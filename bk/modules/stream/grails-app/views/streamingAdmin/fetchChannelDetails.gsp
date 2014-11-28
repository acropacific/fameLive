<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name='layout' content='streamingAdmin'/>
    <title>Channel Details</title>
</head>

<body>
<div>
    <h3 class="form-section">Channel Details</h3>

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
                        Server Details
                    </a>
                </li>
                <li class="">
                    <a href="#tab_1_1_3" data-toggle="tab">
                        Events
                    </a>
                </li>
                <li class="">
                    <a href="#tab_1_1_4" data-toggle="tab">
                        Today Events
                    </a>
                </li>
                <li class="">
                    <a href="#tab_1_1_5" data-toggle="tab">
                        Filler Video Info
                    </a>
                </li>
                <li class="">
                    <a href="#tab_1_1_6" data-toggle="tab">
                        Channel Operations
                    </a>
                </li>
            </ul>

            <div class="tab-content">
                <div class="tab-pane active" id="tab_1_1_1">
                    <p>
                        <g:render template="channelOverview"
                                  model="[channelInfoDTO: channelDetailsDTO?.channelInfoDTO]"/>
                    </p>
                </div>

                <div class="tab-pane" id="tab_1_1_2">
                    <p>
                        <g:render template="channelServerDetails"
                                  model="[channelInfoDTO: channelDetailsDTO?.channelInfoDTO]"/>
                    </p>
                </div>

                <div class="tab-pane" id="tab_1_1_3">
                    <p>
                        <g:render template="channelEvents" model="[eventInfoDTOs: channelDetailsDTO?.eventInfoDTOs]"/>
                    </p>
                </div>

                <div class="tab-pane" id="tab_1_1_4">
                    <p>
                        <g:render template="todayChannelEvents"
                                  model="[eventInfoDTOs: channelDetailsDTO?.todayEventInfoDTOs]"/>
                    </p>
                </div>

                <div class="tab-pane" id="tab_1_1_5">
                    <p>
                        <g:render template="channelFillerVideoInfo"
                                  model="[fillerVideos: channelDetailsDTO?.fillerVideos]"/>
                    </p>
                </div>

                <div class="tab-pane" id="tab_1_1_6">
                    <p>
                        <g:render template="channelOperations"
                                  model="[channelInfoDTO: channelDetailsDTO?.channelInfoDTO]"/>
                    </p>
                </div>

            </div>
        </div>
    </div>
</div>
<tmpl:openFrame confirmDialogId='openFrameForEventDetails' confirmDialogHeader='Confirm'/>
<tmpl:openFrameForVideoPlayer confirmDialogId='openFrameForVideoPlayer' confirmDialogHeader='Confirm'/>
</body>
</html>