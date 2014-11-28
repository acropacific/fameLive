/**
 * Created by anil on 13/10/14.
 */


function nativeTriggeredEvent(action, value) {
//    alert('native action:'+action+" value:"+value);
    console.log('native action:' + action + " value:" + value);
//    raiseEvent("nativeTriggered"+action);

    if (value === "play") {
        jwplayer(playerName).play();
    } else if (value === "pause") {
        jwplayer(playerName).pause();
    } else if (value === "ended") {

    } else if (value === "seeking") {

    } else if (value === "seeked") {

    } else if (value === "timeupdate") {

    } else if (value === "progress") {

    } else if (action === "durationchange") {

    } else if (action === "loadedmetadata") {

    }
}

function raiseEvent(eventName) {
    try {
        var iframe = document.createElement("IFRAME");
        iframe.setAttribute("src", "js-frame:" + eventName + ":" + eventName + ":" + "{something:true}");
        document.documentElement.appendChild(iframe);
        iframe.parentNode.removeChild(iframe);
        iframe = null;
//        alert('raisedEvent: '+eventName);
        return true;

    } catch (exception) {
        alert('exception occurred in raiseEvent() function :' + exception);
        return false;
    }
}


function sendSourceUrl(srcURL) {
    try {
//        alert(encodeURIComponent(srcURL));
//        encodeURI(srcURL);
        var iframe = document.createElement("IFRAME");
        var data = '["src","' + encodeURIComponent(srcURL) + '"]';
        iframe.setAttribute("src", "js-frame:" + "setAttribute:" + "0" + ":" + data);
        document.documentElement.appendChild(iframe);
        iframe.parentNode.removeChild(iframe);
        iframe = null;
        return true;
    } catch (exception) {
        alert('exception occurred in raiseEvent() function :' + exception);
        return false;
    }
}

/*$(document).ready(function () {

 jwplayer(playerName).onPlay(function () {
 *//* alert('aaaa');
 raiseEvent("play");
 alert('play clicked');*//*
 });

 jwplayer(playerName).onPause(function () {
 *//*alert('aaaa');
 raiseEvent("pause");
 alert('pause clicked');*//*
 });

 sendSourceUrl(jwplayer("myPlayer").config.file);
 });*/

function sendPlayEventToGoogle() {
    ga('send', {
        'hitType': 'event',          // Required.
        'eventCategory': 'button',   // Required.
        'eventAction': 'click',      // Required.
        'eventLabel': 'Play video',
        'eventValue': new Date().getTime()
    });
}

function sendPauseEventToGoogle() {
    ga('send', {
        'hitType': 'event',          // Required.
        'eventCategory': 'button',   // Required.
        'eventAction': 'click',      // Required.
        'eventLabel': 'Pause video',
        'eventValue': new Date().getTime()
    });
}