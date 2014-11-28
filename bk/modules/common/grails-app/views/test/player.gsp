<!DOCTYPE html>
<%--
  Created by IntelliJ IDEA.
  User: anil
  Date: 25/11/14
  Time: 12:41 PM
--%>
.<html>
<head lang="en">
    <meta content="text/html; charset=utf-8" http-equiv="Content-Type">
    <title>player</title>
    %{--<script type="text/javascript" src="../jwplayer/ga.js"></script>--}%
    %{--<script type="text/javascript" src="../jwplayer/analytics.js"></script>--}%

    %{--<script type="text/javascript" src="../jwplayer/jwplayer.js"></script>--}%
    <!--script type="text/javascript">jwplayer.key = "pecnsVfdqG9PuM+IkK1nUDqqQCJ2jTOCrVdwDRphr3w5iJ38jaKqbTk96B8=";</script-->


    <script src="http://p.jwpcdn.com/6/9/jwplayer.js"></script>
    <script>jwplayer.key = "pecnsVfdqG9PuM+IkK1nUDqqQCJ2jTOCrVdwDRphr3w5iJ38jaKqbTk96B8="</script>
    <script type="text/javascript" src="../js/jquery-1.9.1.js"></script>
    <script type="text/javascript" src="../js/FameLiveUtil.js"></script>
    <style type="text/css">
    html, body, video {
        width: 100%;
        height: 100%;
        padding: 0;
        margin: 0;
    }

    body {
        font: normal 13px helvetica, arial, sans-serif;
        background-color: transparent;
        /*background: #000;*/
        /*color: #fff;*/
        overflow: hidden;
    }
    </style>
</head>

<body>

<!--div style="display:none" -->
<!--style="visibility: hidden"-->
<div id="playerPapa">
    <div id="myPlayer" style="height: 100%;width: 100%;">Loading the player...</div>
</div>
<script type="text/javascript">
    //opacity: 0.0;
    //http://rricketts.com/how-to-get-jwplayer-version-6-to-run-behind-a-firewall-or-completely-offline/
    //http://razvantudorica.com/12/how-to-play-hls-with-jwplayer/


    var jwPlayer = "";
    var playerName = "myPlayer";
    var videoSource;

    $(document).ready(function () {

        if (navigator.userAgent.match(/Android/i) != null) {
            //videoSource="http://54.68.108.229:1935/live/test/playlist.m3u8?DVR";
            videoSource = "http://54.68.108.229:1935/vod/mp4:sample.mp4/playlist.m3u8";
            /*$("body").css({
             "background-color":"transparent",
             "overflow": "hidden"
             });
             $("div #playerPapa").css({
             "visibility":"hidden"
             });*/
//        alert("Android");
        } else if (navigator.userAgent.match(/iPhone/i) != null) {
            //videoSource="http://54.68.108.229:1935/live/test/playlist.m3u8?DVR";
            videoSource = "http://54.68.108.229:1935/vod/mp4:sample.mp4/playlist.m3u8";
//        alert("iPhone");
        } else {
//        alert("other");
            //videoSource="rtmp://54.68.108.229:1935/live/test"
            videoSource = "rtmp://54.68.108.229:1935/vod/sample1.mp4";
            $("#playerPapa").css("visibility", "block");
//            videoSource="http://54.68.108.229:1935/vod/mp4:sample.mp4/playlist.m3u8"
        }
        var streamPathInsideURL = "${request.getParameter('streamUrl')}";
        if (streamPathInsideURL != null && streamPathInsideURL.length > 0) {
            videoSource = streamPathInsideURL;
        }


        jwPlayer = jwplayer(playerName).setup({
//            height: "100%",
//            width: "100%",
            height: 432,
            width: 768,
            fallback: true,
            image: "/images/logo.png", /*
            ga: {
                idstring: "title",
                trackingobject: "_gaq"
             },*/
            //http://support.jwplayer.com/customer/portal/articles/1417179-integration-with-google-analytics
//            backcolor: 'transparent',
//            wmode : 'transparent',
            //file: "http://www.youtube.com/watch?v=VrQoCYYSzTE"
            //file: "rtmp://54.68.108.229:1935/vod/sample.mp4"
            //file: "http://54.68.108.229:1935/vod/mp4:sample.mp4/playlist.m3u8"
            file: videoSource,
            startparam: "fs",//starttime, start, ec_seek, apstart, fs
            androidhls: true,
            primary: "flash"
            , deliveryType: "streaming", player: {
                modes: {
                    linear: {
                        controls: {
                            stream: {
                                manage: false, enabled: false
                            }
                        }
                    }
                }
            }, shows: {
                streamTimer: {
                    enabled: false, tickRate: 100
                }
            }
        });
    })

</script>
<!--
<script type="text/javascript" src="jwplayer/Custom.js"></script>
<input type="button" value="Play" onclick="raiseEvent('play');sendPlayEventToGoogle();">
<input type="button" value="Pause" onclick="raiseEvent('pause');sendPauseEventToGoogle();">
<input type="button" value="Toggle Screen" onclick="raiseEvent('toggleFullscreen')">-->
</body>
</html>