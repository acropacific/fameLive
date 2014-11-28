<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title></title>
<head>

    <title>PubNub Messenger</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <link rel="apple-touch-icon href=icon.png">
    <link rel="apple-touch-startup-image href=startup.png">
    <link rel="icon" type="image/png" href="favicon.png">
    <link href="https://fonts.googleapis.com/css?family=Ubuntu:300,400,700,400italic" rel="stylesheet" type="text/css">
    <link rel="stylesheet" href="http://code.jquery.com/mobile/1.2.0/jquery.mobile-1.2.0.min.css"/>
    %{--<link rel="stylesheet" href="css/screen.css" type="text/stylesheet"/>--}%
    <script src="http://code.jquery.com/jquery-1.8.2.min.js"></script>
    <script src="http://code.jquery.com/mobile/1.2.0/jquery.mobile-1.2.0.min.js"></script>
    <script src="http://cdn.pubnub.com/pubnub-3.4.4.js"></script>
    %{--<script src="js/messenger.js"></script>--}%

</head>

<body>

<div data-role="page" id="chatPage" data-theme="c" class="type-interior">

    <div data-role="content">
        <div data-role="header" data-position="fixed" data-tap-toggle="false">
            <h1>Pub Messenger</h1>
        </div>

        <div data-role="content">
            <ul data-role="listview" id="messageList">
            </ul>
        </div>

        <div data-role="footer" data-theme="c">
            <textarea id="messageContent"></textarea>

            <div class="ui-grid-a">
                <div class="ui-block-a"><a href="#" id="sendMessageButton" data-role="button">Send Message</a></div>

            </div>
        </div>

    </div>

</div>





<script>
    $(document).ready(function () {
// Initialize the PubNub API connection.
        var pubnub = PUBNUB.init({
            publish_key: 'pub-c-7ae01ec2-d530-4da2-9ae8-530d4e2243f2',
            subscribe_key: 'sub-c-b3bdb970-6334-11e4-8b69-02ee2ddab7fe',
            uuid: '${userName}'
        });

// Grab references for all of our elements.
        var messageContent = $('#messageContent'),
                sendMessageButton = $('#sendMessageButton'),
                messageList = $('#messageList');

// Handles all the messages coming in from pubnub.subscribe.
        function handleMessage(message) {
            var messageEl = $("<li class='message'>"
                    + "<span class='username'>" + message.senderName + ": </span>"
                    + message.message
                    + "</li>");
            messageList.append(messageEl);
            messageList.listview('refresh');
// Scroll to bottom of page
            $("html, body").animate({ scrollTop: $(document).height() - $(window).height() }, 'slow');
        };

        sendMessageButton.click(function (event) {
            var message = messageContent.val();
            if (message != '') {
                $.ajax({
                    url: "${g.createLink(controller:'chatUtil',action:'broadcastMessage')}",
                    dataType: 'json',
                    data: {message: message, senderName: '${userName}' }
                });
                messageContent.val("");
            }
        });

        messageContent.bind('keydown', function (event) {
            if ((event.keyCode || event.charCode) !== 13) return true;
            sendMessageButton.click();
            return false;
        });

        pubnub.subscribe({
            channel: '${channel}',
            message: handleMessage
        });

    });
</script>
</body>
</html>