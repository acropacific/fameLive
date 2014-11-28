/**
 * Created by anil on 25/11/14.
 */

function initializeAjaxHandler() {
    $(document).ajaxStart(function () {
        console.debug('ajax requested');
        App.blockUI({boxed: true});
    });

    $(document).ajaxStop(function () {
        console.debug('ajax completed');
        App.unblockUI();
    });
}

function showTopFullWidthToaster(message) {
    //status=> error,info, success, warning
    var alertType = 'success';
    var title = 'Notification'
    toastr[alertType](message, title);
}

function showTopFullWidthToasterWithStatus(message, status) {
    //status=> error,info, success, warning
    //alert("inside showTopFullWidthToasterWithStatus");
    var alertType = status;
    var title = 'Notification'
    toastr[alertType](message, title);
}
