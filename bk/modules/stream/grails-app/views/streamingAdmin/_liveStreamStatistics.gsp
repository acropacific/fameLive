<div class="row">
    <div class="col-md-10">
        <div class="portlet sale-summary">
            <div class="portlet-title">
                <div class="caption">
                    Live Stream Statistics
                </div>
            </div>

            <div class="portlet-body">
                <ul class="list-unstyled">
                    <g:if test="${inStreamStatisticsDTO != null}">
                        <li>
                            <span class="sale-info">
                                Stream name <i class="fa fa-img-up"></i>
                            </span>
                            <span class="sale-num">
                                ${inStreamStatisticsDTO?.streamName}
                            </span>
                        </li>
                        <li>
                            <span class="sale-info">
                                Total connections <i class="fa fa-img-up"></i>
                            </span>
                            <span class="sale-num">
                                ${inStreamStatisticsDTO?.totalConnections}
                            </span>
                        </li>
                        <li>
                            <span class="sale-info">
                                Stream up time <i class="fa fa-img-up"></i>
                            </span>
                            <span class="sale-num">
                                ${inStreamStatisticsDTO?.uptime}
                            </span>
                        </li>
                        <li>
                            <span class="sale-info">
                                Total bytes in <i class="fa fa-img-up"></i>
                            </span>
                            <span class="sale-num">
                                ${inStreamStatisticsDTO?.bytesIn}
                            </span>
                        </li>
                        <li>
                            <span class="sale-info">
                                Total bytes out <i class="fa fa-img-up"></i>
                            </span>
                            <span class="sale-num">
                                ${inStreamStatisticsDTO?.bytesOut}
                            </span>
                        </li>
                        <li>
                            <span class="sale-info">
                                Bytes in rate <i class="fa fa-img-up"></i>
                            </span>
                            <span class="sale-num">
                                ${inStreamStatisticsDTO?.bytesInRate}
                            </span>
                        </li>
                        <li>
                            <span class="sale-info">
                                Bytes out rate <i class="fa fa-img-up"></i>
                            </span>
                            <span class="sale-num">
                                ${inStreamStatisticsDTO?.bytesOutRate}
                            </span>
                        </li>
                    </g:if>
                    <g:else>
                        <li>
                            <span class="sale-info">
                                Currently any stream is not live on this channel
                            </span>
                        </li>
                    </g:else>
                </ul>
            </div>
        </div>
    </div>
</div>