<br/>

<div>
    <div class="portlet box yellow">
        <div class="portlet-title">
            <div class="caption">
                <i class="fa fa-cogs"></i>Channel Filler Videos Information (Default)
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
                        <th class="col-md-1">Filler Video Type</th>
                        <th class="col-md-1">Name</th>
                        <th class="col-md-1">Extension</th>
                        <th class="col-md-2">Linked Channel</th>
                        <th class="col-md-2">Stream Link</th>
                    </tr>
                    </thead>
                    <tbody>

                    <g:if test="${fillerVideos}">
                        <g:each in="${fillerVideos.keySet()}" status="i" var="fillerVideoType">
                            <g:set var="fillerVideo" value="${fillerVideos.get(fillerVideoType)}"/>
                            <g:if test="${fillerVideo?.channelInfoDTO?.isVODChannel != null}">
                                <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
                                    <td class="col-md-1">${fillerVideoType}</td>
                                    <td class="col-md-1">${fillerVideo?.name}</td>
                                    <td class="col-md-1">${fillerVideo?.extension}</td>
                                    <td class="col-md-2">
                                        <g:link controller="streamingAdmin" action="fetchChannelDetails"
                                                class="btn green"
                                                params='[channelId: "${fillerVideo?.channelInfoDTO?.channelId}"]'>Channel Details <i
                                                class="fa fa-toggle-right"></i></g:link>

                                    </td>
                                    <td class="col-md-2">
                                        <a href="#openFrameForVideoPlayer" data-toggle="modal" class="btn green"
                                           onclick="$('#openFrameForVideoPlayer_iframe').attr('src', '${grailsApplication.config.famelive.streamingAdminPanel.homepage.url}/test/launchPlayer?streamUrl=${fillerVideo?.fetchRTMPStreamUrl()}');">Play</a>
                                    </td>
                                </tr>
                            </g:if>
                        </g:each>
                    </g:if>
                    <g:else>
                        <tr>
                            <td colspan="7">No filler video found</td>
                        </tr>
                    </g:else>
                    </tbody>
                </table>
            </div>

            <div class="dataTables_paginate paging_bootstrap">
                <div class="pagination">
                    <g:paginate total="${fillerVideos.keySet()?.size() ?: 0}"/>
                </div>
            </div>
        </div>
    </div>

</div>

<g:javascript>
    jQuery(document).ready(function () {
        jQuery(".pagination ul").addClass('pagination');
    });
</g:javascript>