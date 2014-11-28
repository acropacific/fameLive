<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name='layout' content='admin'/>
    <title>Event Detail</title>
</head>

<body>
<div>
    <h3 class="form-section">Event Detail</h3>

    <div class="portlet-body">
        <div class="tabbable-custom">
            <ul class="nav nav-tabs">
                <li class="active">
                    <a href="#tab_1_1_1" data-toggle="tab">
                        Overview
                    </a>
                </li>
            </ul>

            <div class="tab-content">
                <div class="tab-pane active" id="tab_1_1_1">
                    <p>
                        <g:render template="eventProfile" model="[adminEventVO: adminEventVO]"/>
                    </p>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>