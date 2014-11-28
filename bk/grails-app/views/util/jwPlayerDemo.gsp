<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>JW Player Demo</title>
    <script src="http://jwpsrv.com/library/ibACgjcfEeSbXyIACtqXBA.js"></script>
</head>

<body>
<div id='myElement'>Loading the player ...</div>
<script type='text/javascript'>
    jwplayer('myElement').setup({
        file: 'https://www.youtube.com/watch?v=7UeNIROyx6o',
        height: 360,
//        image: '/uploads/example.jpg',
        width: 640
    });
</script>

<p>
    <a onclick='jwplayer().play()'>Toggle playback</a> |
    <a onclick='alert(jwplayer().getVolume())'>Report volume</a>
</p>
</body>
</html>
