"use strict";
window.isDefined = function (target) {
    return (target != null && target != 'null' && typeof(target) != 'undefined')
};
function makeAjaxCall(url, successCallBack, errorCallBack, config) {
    var requestType = config.type || 'GET';
    var requestContentType = config.contentType;
    var requestDataType = config.dataType;
    jQuery.ajaxSetup({
        data: {
            'nc': function () {
                return jQuery.now();
            }
        },
        headers: {
            "Cache-Control": "no-cache"
        }
    });
    var ajaxObject = {
        url: url,
        type: requestType,
        data: config.data,
        success: function (response, textStatus, jqXHR) {
            if (isDefined(successCallBack)) {
                successCallBack(response, textStatus, jqXHR)
            }
        },
        error: function (xhlr, a1, a3) {
            if (xhlr.status === 401) {
                window.location.href = 'login/auth';
            }
            else if (isDefined(errorCallBack)) {
                errorCallBack();
            }
        },
        async: (isDefined(config.async)) ? config.async : true
    };
    if (requestContentType) {
        ajaxObject.contentType = requestContentType;
    }
    if (requestContentType) {
        ajaxObject.dataType = requestDataType;
    }
    $.ajax(ajaxObject);
}

var commonErrorCallBack = function () {
    showErrorMsg("Some Error occurred please try again later");
};

var commonErrorCallbackForDialog = function () {
    showErrorMsgInDialog('Some Error occurred please try again later');
};

function getErrorMessageHtml(msg) {
    var alertHtml = '<div class="error-msg">' +
        '<label type="button" class="close" data-dismiss="alert">×</label>' +
        msg +
        '</div>';
    return alertHtml;
}


function getSuccessMessageHtml(msg) {
    var alertHtml = '<div class="success-msg">' +
        '<label type="button" class="close" data-dismiss="alert">×</label>' +
        msg +
        '</div>';
    return alertHtml;
}


function showErrorMsg(msg) {
    removeExistingMessageHolder();
    var alertHtml = getErrorMessageHtml(msg);
    $('body .messageContainer').prepend(alertHtml);
    $('body').scrollTop(0);
}

function showErrorMsgInDialog(msg) {
    removeExistingMessageHolderInDialog();
    var alertHtml = getErrorMessageHtml(msg);
    $('div .messageContainerForDialog').prepend(alertHtml);
}

function showSuccessMsgInDialog(msg) {
    removeExistingMessageHolderInDialog();
    var alertHtml = getSuccessMessageHtml(msg);
    $('div .messageContainerForDialog').prepend(alertHtml);
}

function removeExistingMessageHolder() {
    $('.messageContainer').html("");
}

function removeExistingMessageHolderInDialog() {
    $('.messageContainerForDialog').html("");
}

function showSuccessMsg(msg) {
    removeExistingMessageHolder();
    var alertHtml = '<div class="success-msg">' +
        '<label type="button" class="close" data-dismiss="alert">×</label>' +
        msg +
        '</div>'
    $('body .messageContainer').prepend(alertHtml);
}

String.prototype.bool = function () {
    return (/^true$/i).test(this);
};


function compareTimes(timeString1, timeString2, dayOfWeek1, dayOfWeek2) {
    return(parseInt(getMinutesFromTime(timeString1, dayOfWeek1), 10) - parseInt(getMinutesFromTime(timeString2, dayOfWeek2), 10));
}


function isSmallerThan(timeString1, timeString2, dayOfWeek1, dayOfWeek2) {
    return(parseInt(compareTimes(timeString1, timeString2, dayOfWeek1, dayOfWeek2), 10) < 0);
}

function isEqualTo(timeString1, timeString2, dayOfWeek1, dayOfWeek2) {
    return(parseInt(compareTimes(timeString1, timeString2, dayOfWeek1, dayOfWeek2), 10) == 0);
}

function isGreaterThan(timeString1, timeString2, dayOfWeek1, dayOfWeek2) {
    return(parseInt(compareTimes(timeString1, timeString2, dayOfWeek1, dayOfWeek2), 10) > 0);
}


function getMinutesFromTime(timeString, baseDay, isEndTime) {
    var minutes = 0;
    if (isDefined(timeString) && timeString.length > 0) {
        var timeElements = timeString.replace(' ', ':').split(":");
        var hour = getHourIn24Format(timeElements[0], timeElements[2]);
        var min = timeElements[1];
        if (isEndTime && parseInt(hour) == 0 && parseInt(min) == 0) {
            minutes = MINUTES_IN_A_DAY;
        }
        else {
            minutes = (parseInt(getMinutesForDay(baseDay), 10) + (parseInt(hour, 10) * parseInt(MINUTES_IN_AN_HOUR, 10)) + parseInt(min, 10));
        }
    }
    return minutes;
}

function getMillisecondsFromTimeString(timeString) {
    var minutes = parseInt(getMinutesFromTime(timeString));
    var milliseconds = 0;
    if (isDefined(minutes)) {
        milliseconds = minutes * 60 * 1000;
    }
    return milliseconds
}

function getMinutesForDay(baseDay) {
    return(isDefined(baseDay) ? parseInt(baseDay, 10) * parseInt(MINUTES_IN_A_DAY, 10) : 0)
}


function getHourIn24Format(hour, period) {
    return(parseInt(hour, 10))
}

function areIntervalsConflicting(interval1, interval2) {
    var result = false;

    if ((isGreaterThan(interval1.startTime, interval2.startTime)) && (isSmallerThan(interval1.startTime, interval2.endTime))) {
        result = true
    }
    else if ((isGreaterThan(interval1.endTime, interval2.startTime)) && (isSmallerThan(interval1.endTime, interval2.endTime))) {
        result = true
    }
    return (result);
}

function showAlertMsg(msg) {
    alert(msg);
}

function log() {
    if (console) {
        console.log.apply(console, arguments);
    }
}

function isContainedInInterval(interval1, interval2) {
    return(( parseInt((interval2.startTime), 10) <= parseInt(getMinutesFromTime(interval1.startTime), 10)) && (parseInt(getMinutesFromTime(interval1.endTime), 10) <= parseInt(interval2.endTime, 10)) )
}

function getIntervalDuration(interval) {
    return(getMinutesFromTime(interval.endTime, null, true) - getMinutesFromTime(interval.startTime))
}

function getMinutesStringFromTime(time) {
    var hours = Math.floor(time / 60);
    var minutes = time % 60;
    return(padToTwo(hours == 24 ? 0 : hours) + ":" + padToTwo(minutes));
}

function padToTwo(number) {
    if (number <= 99) {
        number = ("0" + number).slice(-2);
    }
    return number;
}

function getMagicSuggestElement() {
    return $(' input[id^="ms-input"]')
}

function applyTooltip(element, message, placement) {
    element.tooltip({title: message, placement: placement, trigger: 'manual'}).tooltip('show');
}

function destroyTooltip(element) {
    element.tooltip("destroy");
}

function showStatus(data) {
    if (data.status)
        showSuccessMsg(data.message);
    else
        showErrorMsg(data.message);
}
function checkAllCheckBox(parentSelector, childSelector, callBack) {
    $(parentSelector).click(function () {
        $(childSelector).prop("checked", $(parentSelector).prop("checked"));
        if (isDefined(callBack))
            callBack();
    });
}

function parseToBoolean(data) {
    switch (data.toLowerCase()) {
        case "true":
        case "yes":
        case "1":
            return true;
        case "false":
        case "no":
        case "null":
        case "0":
        case null:
            return false;
        default:
            return Boolean(data);
    }
}

function closeModal() {
    $('.modalDiv').modal('hide');
}

function showMessagesAccordingToStatus(data) {
    data.status ? showSuccessMsg(data.msg) : showErrorMsg(data.msg);
}

function getLinkUrl(elementSelector) {
    return elementSelector.attr('href');
}

function getFormUrlWithData(elementSelector) {
    return $(elementSelector).attr('action') + '?' + $(elementSelector).serialize();
}

function getDataUrl(elementSelector) {
    return elementSelector.data('url');
}

function showConfirmDialog(element, message) {
    element.click(function () {
        return confirm(message);
    });
}
function commonSuccessCallback(data) {
    $(AJAX_RESPONSE_CONTAINER).html(data);
}

function preventEvent(event) {
    event.preventDefault();
}

function search() {
    $('#search-input').bind('keyup', function () {
        var searchText = $(this).val();
        if (searchText != "") {
            $(".searchable-table tbody>tr").hide();
            $('.searchable-table td:contains("' + searchText + '")').parent().show();
        }
        else {
            $(".searchable-table tbody>tr").show();
        }
    })
}

function bindDateTimePicker() {
    var datePickerTag = $(".datePicker");
    datePickerTag.datepicker({
        changeMonth: true,
        changeYear: true,
        yearRange: "-5:+1",
        dateFormat: "dd/mm/yy",
        constrainInput: "true"
    });
    datePickerTag.keypress(function (event) {
        event.preventDefault();
    });
}

Array.prototype.containsArray = function (array, index, last) {
    if (arguments[1]) {
        var index = arguments[1], last = arguments[2];
    } else {
        var index = 0, last = 0;
        this.sort();
        array.sort();
    }
    return index == array.length
        || ( last = this.indexOf(array[index], last) ) > -1
        && this.containsArray(array, ++index, ++last);
};

Array.prototype.compare = function (testArr) {
    if (this.length != testArr.length) return false;
    for (var i = 0; i < testArr.length; i++) {
        if (this[i].compare) {
            if (!this[i].compare(testArr[i])) return false;
        }
        if (this[i] !== testArr[i]) return false;
    }
    return true;
};

Array.prototype.remove = function (element) {
    for (var i = this.length - 1; i >= 0; i--) {
        if (this[i] === element) {
            this.splice(i, 1);
        }
    }
};

function arrayHasDuplicate(A) {                          // finds any duplicate array elements using the fewest possible comparison
    var i, j, n;
    n = A.length;
    // to ensure the fewest possible comparisons
    for (i = 0; i < n; i++) {                        // outer loop uses each item i at 0 through n
        for (j = i + 1; j < n; j++) {              // inner loop only compares items j at i+1 to n
            if (A[i] == A[j]) return true;
        }
    }
    return false;
}


function convertFormToJson(form) {
    var array = jQuery(form).serializeArray();
    var json = {};

    jQuery.each(array, function () {
        json[this.name] = this.value || '';
    });

    return json;
}


String.prototype.toBoolean = function () {
    return Boolean(this) && (this === 'true');
};


function addMonthToDate(date, months) {
    return new Date(new Date(date).setMonth(date.getMonth() + months));
}

function subtractMonthToDate(date, months) {
    return new Date(new Date(date).setMonth(date.getMonth() - months))
}

function getDateAsString(newDate){
    return newDate.getDate() + "/" + (newDate.getMonth() * 1 + 1) + "/" + newDate.getFullYear();
}



