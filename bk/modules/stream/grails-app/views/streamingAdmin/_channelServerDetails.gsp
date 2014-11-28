<div class="row">
    <div class="col-md-10">
        <div class="portlet sale-summary">
            <div class="portlet-title">
                <div class="caption">
                    Server Details
                </div>
            </div>

            <div class="portlet-body">
                <ul class="list-unstyled">
                    <li>
                        <span class="sale-info">
                            Server IP <i class="fa fa-img-up"></i>
                        </span>
                        <span class="sale-num">
                            ${channelInfoDTO?.serverIP}
                        </span>
                    </li>
                    <li>
                        <span class="sale-info">
                            Streaming server Port <i class="fa fa-img-up"></i>
                        </span>
                        <span class="sale-num">
                            ${channelInfoDTO?.serverStreamingPort}
                        </span>
                    </li>
                    <li>
                        <span class="sale-info">
                            Channel Name <i class="fa fa-img-up"></i>
                        </span>
                        <span class="sale-num">
                            ${channelInfoDTO?.name}
                        </span>
                    </li>
                    <li>
                        <span class="sale-info">
                            Channel Live Stream Name <i class="fa fa-img-up"></i>
                        </span>
                        <span class="sale-num">
                            ${channelInfoDTO?.liveStreamName}
                        </span>
                    </li>
                </ul>
            </div>
        </div>
    </div>
</div>