/**
 * Created by anil on 27/11/14.
 */


function resetLiveStream(channelId, channelName, streamName, actionUrl) {
    var data = {
        "appKey": "myAppKey",
        "apiVersion": "1.0",
        "actionName": "resetWowzaIncomingStream",
        "applicationName": channelName,
        "streamName": streamName,
        "channelId": channelId
    };
    //alert(JSON.stringify(data));
    $.ajax(
        {
            url: actionUrl,
            type: 'POST',
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            data: JSON.stringify(data),
            success: function (response) {
                //alert(JSON.stringify(response));
                var responseData = response;//JSON.parse(response);
                if (responseData.success == true) {
                    //alert("success true")
                    showTopFullWidthToasterWithStatus(responseData.message, 'success');
                    return true;
                } else {
                    //alert("success false")
                    showTopFullWidthToasterWithStatus(responseData.message, 'error');
                }
                return false;
            },
            error: function (jqXHR, textStatus, errorThrown) {
                showTopFullWidthToasterWithStatus('Some error occurred while performing operation', 'error');
                return false;
            }
        });
}

function disconnectLiveStream(channelId, channelName, streamName, actionUrl) {
    var data = {
        "appKey": "myAppKey",
        "apiVersion": "1.0",
        "actionName": "disconnectWowzaIncomingStream",
        "applicationName": channelName,
        "streamName": streamName,
        "channelId": channelId
    };
    $.ajax(
        {
            url: actionUrl,
            type: 'POST',
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            data: JSON.stringify(data),
            success: function (response) {
                var responseData = response;//JSON.parse(response);
                if (responseData.success == true) {
                    showTopFullWidthToasterWithStatus(responseData.message, 'success');
                    return true;
                } else {
                    showTopFullWidthToasterWithStatus(responseData.message, 'error');
                }
                return false;
            },
            error: function (jqXHR, textStatus, errorThrown) {
                showTopFullWidthToasterWithStatus('Some error occurred while performing operation', 'error');
                return false;
            }
        });
}

function restartWowzaChannel(channelId, channelName, actionUrl) {
    var data = {
        "appKey": "myAppKey",
        "apiVersion": "1.0",
        "actionName": "restartWowzaApplication",
        "applicationName": channelName,
        "channelId": channelId
    };
    //alert(JSON.stringify(data));
    $.ajax(
        {
            url: actionUrl,
            type: 'POST',
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            data: JSON.stringify(data),
            success: function (response) {
                //alert(JSON.stringify(response));
                var responseData = response;//JSON.parse(response);
                if (responseData.success == true) {
                    //alert("success true")
                    showTopFullWidthToasterWithStatus(responseData.message, 'success');
                    return true;
                } else {
                    //alert("success false")
                    showTopFullWidthToasterWithStatus(responseData.message, 'error');
                }
                return false;
            },
            error: function (jqXHR, textStatus, errorThrown) {
                showTopFullWidthToasterWithStatus('Some error occurred while performing operation', 'error');
                return false;
            }
        });
}

function stopWowzaChannel(channelId, channelName, actionUrl) {
    var data = {
        "appKey": "myAppKey",
        "apiVersion": "1.0",
        "actionName": "stopWowzaApplication",
        "applicationName": channelName,
        "channelId": channelId
    };
    //alert(JSON.stringify(data));
    $.ajax(
        {
            url: actionUrl,
            type: 'POST',
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            data: JSON.stringify(data),
            success: function (response) {
                //alert(JSON.stringify(response));
                var responseData = response;//JSON.parse(response);
                if (responseData.success == true) {
                    //alert("success true")
                    showTopFullWidthToasterWithStatus(responseData.message, 'success');
                    return true;
                } else {
                    //alert("success false")
                    showTopFullWidthToasterWithStatus(responseData.message, 'error');
                }
                return false;
            },
            error: function (jqXHR, textStatus, errorThrown) {
                showTopFullWidthToasterWithStatus('Some error occurred while performing operation', 'error');
                return false;
            }
        });
}

function startWowzaChannel(channelId, channelName, actionUrl) {
    var data = {
        "appKey": "myAppKey",
        "apiVersion": "1.0",
        "actionName": "startWowzaApplication",
        "applicationName": channelName,
        "channelId": channelId
    };
    //alert(JSON.stringify(data));
    $.ajax(
        {
            url: actionUrl,
            type: 'POST',
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            data: JSON.stringify(data),
            success: function (response) {
                //alert(JSON.stringify(response));
                var responseData = response;//JSON.parse(response);
                if (responseData.success == true) {
                    //alert("success true")
                    showTopFullWidthToasterWithStatus(responseData.message, 'success');
                    return true;
                } else {
                    //alert("success false")
                    showTopFullWidthToasterWithStatus(responseData.message, 'error');
                }
                return false;
            },
            error: function (jqXHR, textStatus, errorThrown) {
                showTopFullWidthToasterWithStatus('Some error occurred while performing operation', 'error');
                return false;
            }
        });
}