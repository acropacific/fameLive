<div class="row profile">
    <div class="col-md-3">
        <ul class="list-unstyled profile-nav">
            <img src='../images/staytuned.jpg' width='200px' height='200px'
                 class='center-block img-thumbnail img-responsive'/>
            %{--<li>
                <admin:displayUserImage imageName="${adminUserInformationVO?.adminUserProfileDto?.imageName}"/>
            </li>--}%
        </ul>
    </div>

    <div class="col-md-9">
        <div class="row">
            <div class="col-md-8 profile-info">
                <h1>${channelInfoDTO?.channelAlias}</h1>

                <p>
                    Name : ${channelInfoDTO?.name}
                </p>

                <p>
                    Is Active Channel :  ${channelInfoDTO?.isActive}
                </p>

                <p>
                    Is VOD Channel :  ${channelInfoDTO?.isVODChannel}
                </p>

                <p>
                    Linked VOD Channel :  <a
                        href="${createLink(controller: "streamingAdmin", action: "fetchChannelDetails", params: [channelId: channelInfoDTO?.linkedVODChannel?.channelId])}">${channelInfoDTO?.linkedVODChannel?.channelAlias}</a>
                </p>

            </div>
        </div>
    </div>
</div>