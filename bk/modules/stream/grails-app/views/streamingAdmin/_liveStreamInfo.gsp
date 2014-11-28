<div class="row profile">
    <div class="col-md-3">
        <ul class="list-unstyled profile-nav">
            <img src='../images/staytuned.jpg' width='200px' height='200px'
                 class='center-block img-thumbnail img-responsive'/>
        </ul>
    </div>

    <div class="col-md-9">
        <div class="row">
            <div class="col-md-8 profile-info">
                <g:if test="${liveStreamDetailsDTO != null}">
                    <h1>${liveStreamDetailsDTO?.eventInfoDTO?.name}</h1>

                    <p>
                        Stream Name : ${liveStreamDetailsDTO?.inStreamInfoDTO?.streamName}
                    </p>

                    <p>
                        Stream status : ${liveStreamDetailsDTO?.eventStatus}
                    </p>

                    <p>
                        Server Name :  ${liveStreamDetailsDTO?.inStreamInfoDTO?.serverName}
                    </p>

                    <p>
                        Source IP :  ${liveStreamDetailsDTO?.inStreamInfoDTO?.sourceIP}
                    </p>

                    <p>
                        Is Recording Set :  ${liveStreamDetailsDTO?.inStreamInfoDTO?.isRecordingSet}
                    </p>

                    <p>
                        Is StreamManager  Stream :  ${liveStreamDetailsDTO?.inStreamInfoDTO?.isStreamManagerStream}
                    </p>

                    <p>
                        IsPublished  To VOD :  ${liveStreamDetailsDTO?.inStreamInfoDTO?.isPublishedToVOD}
                    </p>

                    <p>
                        Is Connected  :  ${liveStreamDetailsDTO?.inStreamInfoDTO?.isConnected}
                    </p>

                    <p>
                        Event :  <a href="#openFrameForEventDetails" data-toggle="modal"
                                    onclick="$('#openFrameForEventDetails_iframe').attr('src', '${grailsApplication.config.famelive.adminPanel.homepage.url}/event/viewEventDetail?eventId=${liveStreamDetailsDTO?.eventInfoDTO?.eventId}');">Event Detail</a>
                    </p>
                </g:if>
                <g:else>
                    <h1>Currently any stream is not live on this channel</h1>
                </g:else>
            </div>
        </div>
    </div>
</div>