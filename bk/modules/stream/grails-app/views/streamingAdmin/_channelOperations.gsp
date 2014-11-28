<div class="row">
    <div class="col-md-10">
        <div class="portlet sale-summary">
            <div class="portlet-title">
                <div class="caption">
                    Channel Operations
                </div>
            </div>

            <div class="portlet-body">
                <ul class="list-unstyled">
                    <g:if test="${channelInfoDTO != null}">

                        <li>
                            <span class="sale-info">
                                Restart Channel <i class="fa fa-img-up"></i>
                            </span>
                            <span class="sale-num">
                                <button class="btn blue" style="width: 150px"
                                        onclick="restartWowzaChannel(${channelInfoDTO?.channelId}, '${channelInfoDTO?.name}', '${grailsApplication.config.famelive.streamingAdminPanel.homepage.url}/api/v1');">Restart</button>
                            </span>
                        </li>

                        <li>
                            <span class="sale-info">
                                Stop Channel <i class="fa fa-img-up"></i>
                            </span>
                            <span class="sale-num">
                                <button class="btn red" style="width: 150px"
                                        onclick="stopWowzaChannel(${channelInfoDTO?.channelId}, '${channelInfoDTO?.name}', '${grailsApplication.config.famelive.streamingAdminPanel.homepage.url}/api/v1');">Stop</button>
                            </span>
                        </li>
                        <li>
                            <span class="sale-info">
                                Start Channel <i class="fa fa-img-up"></i>
                            </span>
                            <span class="sale-num">
                                <button class="btn green" style="width: 150px"
                                        onclick="startWowzaChannel(${channelInfoDTO?.channelId}, '${channelInfoDTO?.name}', '${grailsApplication.config.famelive.streamingAdminPanel.homepage.url}/api/v1');">Start</button>
                            </span>
                        </li>
                    </g:if>
                    <g:else>
                        <li>
                            <span class="sale-info">
                                No data found
                            </span>
                        </li>
                    </g:else>
                </ul>
            </div>
        </div>
    </div>
</div>