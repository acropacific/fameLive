<!DOCTYPE html>
<html>
<head>
    <title>#Fame</title>
    <link rel="shortcut icon" href="${resource(dir: 'images', file: 'as.jpg')}"/>
    <script type="text/javascript" src="jwplayer/jwplayer.js"></script>
    <script type="text/javascript">jwplayer.key = "pecnsVfdqG9PuM+IkK1nUDqqQCJ2jTOCrVdwDRphr3w5iJ38jaKqbTk96B8=";</script>
    <link href="${resource(dir: 'css', file: 'bootstrap.min.css')}" rel="stylesheet" type="text/css"/>
    <link href="${resource(dir: 'css', file: 'style.css')}" rel="stylesheet" type="text/css"/>
    <g:javascript src="jquery-1.9.1.js"/>
    <g:javascript src="jquery.cookie.js"/>
    <g:javascript src="bootstrap.min.js"/>
    <script type="text/javascript" src="http://code.jquery.com/jquery-1.6.1.js"></script>
    <link href="pubnub/stylesheets/jquery.cssemoticons.css" media="screen" rel="stylesheet" type="text/css"/>
    <script src="pubnub/javascripts/jquery.cssemoticons.min.js" type="text/javascript"></script>
    <script src="http://cdn.pubnub.com/pubnub.js"></script>
    <script>
        var _channelName = "devHashFame15Sep";
        var userUUID = 'User';
        $(document).ready(function () {
            $("#msg").keyup(function (e) {
                e.preventDefault();
                if ($("#msg:focus") && e.keyCode == 13) {
                    if ($.trim($("#msg").val()).length != 0) {
                        $("#sendMessage").click();
                    }
                }
            });

            if (getCookie('fameName')) {
                loadUserProfile(getCookie('fameName'))
            }

            $("#chatName").submit(function (e) {
                e.preventDefault();
                var name = $("#hashName").val();
                if (name) {
                    setCookie('fameName', name, new Date() + 30);
                    loadUserProfile(name)
                }
            });
        });

        function loadUserProfile(name) {
            $("#lightBox").remove();
            userUUID = name;
            $('.userName').html(name);
            getChatHistory();
        }

        function setCookie(cname, cvalue, exdays) {
            var d = new Date();
            d.setTime(d.getTime() + (exdays * 24 * 60 * 60 * 1000));
            var expires = "expires=" + d.toUTCString();
            document.cookie = cname + "=" + cvalue + "; " + expires;
        }

        function getCookie(cname) {
            var name = cname + "=";
            var ca = document.cookie.split(';');
            for (var i = 0; i < ca.length; i++) {
                var c = ca[i];
                while (c.charAt(0) == ' ') c = c.substring(1);
                if (c.indexOf(name) != -1) return c.substring(name.length, c.length);
            }
            return "";
        }

        var pubnub = PUBNUB.init({
            publish_key: 'pub-c-5ba1e46a-6d89-4a50-9185-18811a6bba4c',
            subscribe_key: 'sub-c-bef7b91a-1c91-11e4-bbbf-02ee2ddab7fe'
        });

        pubnub.subscribe({
            channel: _channelName,
            callback: receiveMessage
            /* connect: function () {
             pubnub.publish({
             channel: _channelName,
             message: {
             uuid: userUUID,
             message: 'connecting to myChannel. :-)'
             }
             });
             }*/
        });

        function getChatHistory() {
            pubnub.history({
                channel: _channelName,
                count: 15,
                callback: function (a) {
                    oldMessage(a[0])
                }
            });
        }

        function sendMessage() {
            pubnub.publish({channel: _channelName, message: {uuid: userUUID, message: $('#msg').val()}});
            $('#msg').val('');
        }

        function oldMessage(messages) {
            for (var i = 0; i <= messages.length; i++) {
                receiveMessage(messages[i])
            }
        }
        function receiveMessage(message) {
            var newDiv;
            if (message.uuid == userUUID) {
                newDiv = $('#chatBody').append("<div class='myChat'><div class='talk-bubble tri-right round right-in'><div class='talktext'><p>" + message.message + "</p></div></div><span class='profileImage' id='myFameName'> " + message.uuid.charAt(0) + "</span><div><br/>");
            } else {
                newDiv = $('#chatBody').append("<div class='otherChat'><span class='profileImage' id='guestFameName'>" + message.uuid.charAt(0) + " </span><div class='talk-bubble tri-right round left-in'><div class='talktext'><p>" + message.message + "</p></div></div><div><br/>");
            }
            $("#chatBody").animate({ scrollTop: $('#chatBody')[0].scrollHeight}, 10);
        }

        function changeUserName(uName) {
            userUUID = uName;
        }

        $(document).ready(function () {
            pubnub.subscribe();
        });


    </script>
</head>

<body>

<div id="container">
    <div id="lightBox">
        <div class="submitBox">
            <form name="chatName" id="chatName">
                <label>please provide a name for chat server</label>
                <input type="text" placeholder="User" id="hashName" required="required"/>
                <input type="submit" value="Submit" id="chatNameButton"/>
            </form>
        </div>
    </div>
    <header id="header">
        <div class="wrapper clearfix">
            <div class="logo"><a href="#"><img src="images/logo.png" alt="logo"/></a></div>
        </div>
    </header>

    <div id="content">
        <div class="wrapper">
            <div class="box clearfix">
                <div class="main"><div id="myElement">Loading the player...</div></div>

                <div class="aside">
                    <div style="width:298px;padding: 5px;color: rgb(43, 26, 112);font-weight: bold;" id="chatTitle">
                        #Fame  <span class="userName"></span>
                    </div>

                    <div id="chatBox">
                        <div id="chatBody"
                             style=" overflow-y:auto;width:298px;height:350px">

                        </div>

                        <div class="row">
                            <div class="col-md-8">
                                <g:textArea name="msg" id="msg" style="width:298px;opacity: 0.7;border: none;"/>
                            </div>

                            <div class="col-md-3">
                                <input type="button" style="width: 55px;margin-top: -1px;display: none"
                                       onclick="sendMessage();"
                                       value="Send" id="sendMessage"/>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <footer id="footer">
        <div class="wrapper">
            <p class="copyright">&copy; 2014 Copyright -Channel: ${params.channel}</p>
        </div>
    </footer>

</div>
<script type="text/javascript">
    //    if (navigator.userAgent.match(/android/i) != null) {
    $(document).ready(function () {
        jwplayer("myElement").setup({
            height: 418,
            width: 698,
            file: "${params.channel?'rtmp://54.68.132.165:1935/'+params.channel:'http://www.youtube.com/watch?v=VrQoCYYSzTE'}"
        });
    })
</script>
</body>
</html>
