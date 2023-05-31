package com.ennovations.fitcrucoach.Network

object Api {


    //  const val BASE_URL = "https://app.fitcru.com/coach/"

    const val BASE_URL = "http://65.1.160.150/coach/"

    const val AUTHORIZATION = "Authorization"
    const val BEARER = "BEARER"

    const val EMAIL_LOGIN = "coach-login"
    const val FORGOT_PASSWORD = "forgot-password"
    const val CREATE_PASSWORD = "create-password"
    const val OTP_VERIFICATION = "verify-code"
    const val COACH_CHANGE_PASSWORD = "change-coach-password"
    const val ADD_COACH_CERTIFICATE = "add-coach-certificates"
    const val GET_COACH_PROFILE = "get-coach-profile"
    const val UPDATE_COACH_PROFILE = "coach-profile-update"
    const val GET_CERTIFICATE = "get-coach-certificates"
    const val UPDATE_CERTIFICATE = "update-coach-certificates"
    const val CONSULTATION_FORM_DETAIL = "consultation-detail"
    const val UPDATE_CONSULTATION_FORM_DETAIL = "update-consultation-detail"
    const val GET_CONSULTATION_DETAIL = "get-consultation-detail"
    const val GET_COUNTRIES = "countries"
    const val GET_STATES = "states"
    const val GET_CITY = "cities"
    const val UPDATE_LOCATION = "coach-location-update"
    const val GET_COACH_LOCATION = "get-location-details"
    const val GET_LANGUAGES = "list-languages"
    const val GET_SPECIALIZATION = "list-specialization"
    const val GET_ASSOCIATION = "list-gym-associations"
    const val UPDATE_SPECIALIZATION_SCREEN = "coach-specialization-update"
    const val GET_SPECIALIZATION_DETAILS = "get-specialization-details"
    const val UPDATE_COACH_VERIFICATION = "coach-verification-update"
    const val GET_VERIFICATION_DETAILS = "get-verification-details"
    const val CLIENT_LIST = "list-client"
    const val CLIENT_LIST_filter = "filter-client-list"
    const val APPOINTMENT_LIST_FILTER = "list-coach-appointment"
    const val CANCEL_APPOINTMENT = "coach-cancel-appointment"
    const val USER_NAME_TIME_SLOT = "list-time-slots-and-clients"
    const val ADD_NEW_APPOINTMENT = "add-appointment-by-coach"
    const val GET_CLIENT_DETAIL = "client-detail"
    const val GET_AVAILABLE_TIME_SLOT = "get-available-time-slot"
    const val LOGOUT = "coach-logout"
    const val MARK_UNAVAILABLE = "mark-unavailability"
    const val GET_HEALTH_QUESTIONNAIRE_BY_COACH = "get-health-questionnaire-by-coach"
    const val CHAT_LIST = "chat-list"
    const val EDIT_GOALS = "edit-goals"
    const val GET_EDIT_GOALS = "get-edit-goals"
    const val LAST_MESSAGE = "chat_between_user_and_coach"
    const val GET_MARK_UNAVAILABLE = "list-unavailable-dates"
    const val REMOVE_UNAVAILABLE_DATE = "remove-unavailability"
    const val DEACTIVATE_ACCOUNT = "deactivate-coach"
    const val GET_MEAL_DETAIL = "get_user_meal_type_by_food"

    const val STORE_COACH_QUICKBLOX_ID = "store-coach-quickBloxId"
    const val FCM_TOKEN = "fcm-token"


    const val NOTIFICATION_LIST = "list-coach-notifications"
    const val READ_NOTIFICATION = "coach-read-notifications"
}