<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name='layout' content='admin'/>
    <title></title>
</head>

<body>
<br/>

<div>
    <g:form controller="push" action="sendPushNotification" class="form-horizontal">
        <div class="form-body">
            <h3 class="form-section">Send Push Notification</h3>
            <g:if test="${pushStatusMessage}">

                <div class="row">
                    <div class="col-md-6">
                        <div class="form-group">
                            <label>${pushStatusMessage}</label>
                        </div>
                    </div>
                </div>
            </g:if>
            <div class="row">
                <div class="col-md-6">
                    <div class="form-group">
                        <label class="control-label col-md-3">Select Event</label>

                        <div class="col-md-9">
                            <g:select name="channels" from="${notificationTypes.displayText.asList()}"
                                      keys="${notificationTypes.channelName.asList()}" multiple="true"
                                      class="form-control"/>
                        </div>
                    </div>
                </div>
            </div>

            <div class="row">
                <div class="col-md-6">
                    <div class="form-group">
                        <label class="control-label col-md-3">Message</label>

                        <div class="col-md-9">
                            <g:textArea name="message" placeholder="Enter Message" class="form-control" rows="5"/>
                        </div>
                    </div>
                </div>

            </div>

            <div class="row">
                <div class="col-md-6">
                    <div class="pull-right">
                        <g:submitButton name="Send" class="btn green"/>
                    </div>
                </div>
            </div>
        </div>
    </g:form>
</div>
</body>
</html>