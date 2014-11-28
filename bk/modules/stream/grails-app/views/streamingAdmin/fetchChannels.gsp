<html>
<head>
    <meta name='layout' content='streamingAdmin'/>
    <title>Channel List</title>
</head>

<body>
<br/>

<div>
    <div class="portlet box yellow">
        <div class="portlet-title">
            <div class="caption">
                <i class="fa fa-cogs"></i>Channels
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
                        <th class="col-md-1">Channel</th>
                        <th class="col-md-1">Name</th>
                        <th class="col-md-2">Is Active Channel</th>
                        <th class="col-md-2">Is VOD Channel</th>
                        <th></th>
                    </tr>
                    </thead>
                    <tbody>

                    <g:if test="${channelInfoDTOs}">
                        <g:each in="${channelInfoDTOs}" status="i" var="channel">
                            <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
                                <td class="col-md-1">${channel?.channelAlias}</td>
                                <td class="col-md-1">${channel?.name}</td>
                                <td class="col-md-2">${channel?.isActive}</td>
                                <td class="col-md-2">${channel?.isVODChannel}</td>
                                <td class="col-md-1">
                                    <g:link controller="streamingAdmin" action="fetchChannelDetails"
                                            class="btn green"
                                            params='[channelId: "${channel?.channelId}"]'>Channel Details <i
                                            class="fa fa-toggle-right"></i></g:link>
                                </td>
                            </tr>
                        </g:each>
                    </g:if>
                    <g:else>
                        <tr>
                            <td colspan="5">No channel found</td>
                        </tr>
                    </g:else>
                    </tbody>
                </table>
            </div>

            <div class="dataTables_paginate paging_bootstrap">
                <div class="pagination">
                    <g:paginate total="${channelInfoDTOs?.size() ?: 0}"/>
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
</body>
</html>