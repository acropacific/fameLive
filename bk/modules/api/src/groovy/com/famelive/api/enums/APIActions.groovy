package com.famelive.api.enums

public enum APIActions {
    LOGIN('login', 10001),
    LOGOUT('logout', 10002),
    REGISTER_USER('registerUser', 10003),
    UPDATE_PROFILE('updateProfile', 10004),
    CHANGE_PASSWORD('changePassword', 10005),
    FORGOT_PASSWORD('forgotPassword', 10006),
    VALIDATE_FORGOT_PASSWORD_CODE('validateForgotPasswordCode', 10007),
    UPDATE_FORGOT_PASSWORD('updateForgotPassword', 10008),
    GET_PROFILE_DATA('updateForgotPassword', 10009),
    CONFIGURATION('configuration', 10010),
    USER_IMAGE('saveUserImage', 10011),
    FAME_NAME_AVAILABLE('fameNameAvailable', 10012),
    FETCH_GENRE('fetchGenreList', 10013),
    CREATE_EVENT('createEvent', 10014),
    EDIT_EVENT('editEvent', 10015),
    SAVE_EVENT_IMAGE('saveEventImage', 10016),
    CANCEL_EVENT('cancelEvent', 10017),
    FETCH_EVENT('fetchEvent', 10018),
    UPGRADE_TO_TALENT('upgradeToTalent', 10019),
    FETCH_EVENT_DETAILS('fetchEventDetails', 10020),
    REMOVE_SOCIAL_ACCOUNT('removeSocialAccount', 10021),
    ADD_SOCIAL_ACCOUNT('addSocialAccount', 10022),
    FOLLOW('follow', 10023),
    UN_FOLLOW('unFollow', 10024),
    SEARCH_PERFORMER('searchPerformer', 10025),
    FETCH_FOLLOWERS('fetchPerformer', 10026),
    FETCH_SOCIAL_ACCOUNT('fetchSocialAccount', 10027),
    FETCH_SOCIAL_TEMPLATE('fetchSocialTemplate', 10028),
    CHANGE_EMAIL('changeEmail', 10029),
    CHECK_USER_ACCOUNT('checkEmail', 10030),
    VERIFY_EMAIL('verifyEmail', 10031),
    SEND_EMAIL_VERIFICATION_CODE('sendEmailVerificationCode', 10032)

    String successMessage
    int successCode
    String actionName

    APIActions(String actionName, int successCode) {
        this.actionName = actionName
        this.successCode = successCode
    }
}
