package com.ennovations.fitcrucoach.Network

import com.ennovations.fitcrucoach.Network.Api.ADD_COACH_CERTIFICATE
import com.ennovations.fitcrucoach.Network.Api.ADD_NEW_APPOINTMENT
import com.ennovations.fitcrucoach.Network.Api.APPOINTMENT_LIST_FILTER
import com.ennovations.fitcrucoach.Network.Api.CANCEL_APPOINTMENT
import com.ennovations.fitcrucoach.Network.Api.CHAT_LIST
import com.ennovations.fitcrucoach.Network.Api.CLIENT_LIST
import com.ennovations.fitcrucoach.Network.Api.CLIENT_LIST_filter
import com.ennovations.fitcrucoach.Network.Api.COACH_CHANGE_PASSWORD
import com.ennovations.fitcrucoach.Network.Api.CREATE_PASSWORD
import com.ennovations.fitcrucoach.Network.Api.DEACTIVATE_ACCOUNT
import com.ennovations.fitcrucoach.Network.Api.EDIT_GOALS
import com.ennovations.fitcrucoach.Network.Api.EMAIL_LOGIN
import com.ennovations.fitcrucoach.Network.Api.FCM_TOKEN
import com.ennovations.fitcrucoach.Network.Api.FORGOT_PASSWORD
import com.ennovations.fitcrucoach.Network.Api.GET_ASSOCIATION
import com.ennovations.fitcrucoach.Network.Api.GET_AVAILABLE_TIME_SLOT
import com.ennovations.fitcrucoach.Network.Api.GET_CERTIFICATE
import com.ennovations.fitcrucoach.Network.Api.GET_CITY
import com.ennovations.fitcrucoach.Network.Api.GET_CLIENT_DETAIL
import com.ennovations.fitcrucoach.Network.Api.GET_COACH_LOCATION
import com.ennovations.fitcrucoach.Network.Api.GET_COACH_PROFILE
import com.ennovations.fitcrucoach.Network.Api.GET_CONSULTATION_DETAIL
import com.ennovations.fitcrucoach.Network.Api.GET_COUNTRIES
import com.ennovations.fitcrucoach.Network.Api.GET_EDIT_GOALS
import com.ennovations.fitcrucoach.Network.Api.GET_HEALTH_QUESTIONNAIRE_BY_COACH
import com.ennovations.fitcrucoach.Network.Api.GET_LANGUAGES
import com.ennovations.fitcrucoach.Network.Api.GET_MARK_UNAVAILABLE
import com.ennovations.fitcrucoach.Network.Api.GET_MEAL_DETAIL
import com.ennovations.fitcrucoach.Network.Api.GET_SPECIALIZATION
import com.ennovations.fitcrucoach.Network.Api.GET_SPECIALIZATION_DETAILS
import com.ennovations.fitcrucoach.Network.Api.GET_STATES
import com.ennovations.fitcrucoach.Network.Api.GET_VERIFICATION_DETAILS
import com.ennovations.fitcrucoach.Network.Api.LAST_MESSAGE
import com.ennovations.fitcrucoach.Network.Api.LOGOUT
import com.ennovations.fitcrucoach.Network.Api.MARK_UNAVAILABLE
import com.ennovations.fitcrucoach.Network.Api.NOTIFICATION_LIST
import com.ennovations.fitcrucoach.Network.Api.OTP_VERIFICATION
import com.ennovations.fitcrucoach.Network.Api.READ_NOTIFICATION
import com.ennovations.fitcrucoach.Network.Api.REMOVE_UNAVAILABLE_DATE
import com.ennovations.fitcrucoach.Network.Api.STORE_COACH_QUICKBLOX_ID
import com.ennovations.fitcrucoach.Network.Api.UPDATE_CERTIFICATE
import com.ennovations.fitcrucoach.Network.Api.UPDATE_COACH_PROFILE
import com.ennovations.fitcrucoach.Network.Api.UPDATE_COACH_VERIFICATION
import com.ennovations.fitcrucoach.Network.Api.UPDATE_LOCATION
import com.ennovations.fitcrucoach.Network.Api.UPDATE_SPECIALIZATION_SCREEN
import com.ennovations.fitcrucoach.Network.Api.USER_NAME_TIME_SLOT
import com.ennovations.fitcrucoach.body.*
import com.ennovations.fitcrucoach.response.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface Service {

    @POST(EMAIL_LOGIN)
    suspend fun login(
        @Body body: LoginBody
    ): Response<LoginResponse>

    @POST(FORGOT_PASSWORD)
    suspend fun forgotPassword(@Body body: ForgotPasswordBody): Response<ForgotPasswordResponse>

    @POST(OTP_VERIFICATION)
    suspend fun otpVerification(@Body body: OTPVerificationBody): Response<ForgotPasswordResponse>

    @POST(CREATE_PASSWORD)
    suspend fun createPassword(@Body body: CreatePasswordBody): Response<ForgotPasswordResponse>

    @POST(ADD_COACH_CERTIFICATE)
    suspend fun addCertificate(
        @Body body: MultipartBody
    ): Response<CoachDetailResponse>

    @POST(COACH_CHANGE_PASSWORD)
    suspend fun changePassword(
        @Body body: ChangePasswordBody
    ): Response<ChangePasswordResponse>

    @GET(GET_COACH_PROFILE)
    suspend fun getCoachDetails(): Response<CoachProfileResponse>

    @POST(UPDATE_COACH_PROFILE)
    suspend fun updateCoachDetails(@Body body: MultipartBody): Response<CoachProfileResponse>

    @GET(GET_CERTIFICATE)
    suspend fun getCertificate(@Query("coach_id") coach_id: Int): Response<GetCertificateResponse>

    @POST(UPDATE_CERTIFICATE)
    suspend fun updateCertificate(
        @Body body: MultipartBody
    ): Response<CoachDetailResponse>

    @POST("{url}")
    suspend fun consultationFormDetail(
        @Path("url") url: String,
        @Body body: ConsultationFormBody
    ): Response<ConsultationFormResponse>

    @GET(GET_CONSULTATION_DETAIL)
    suspend fun getConsultation(@Query("user_id") id: Int): Response<GetConsultationResponse>

    @GET(GET_COUNTRIES)
    suspend fun getCountries(): Response<CountriesResponse>

    @GET(GET_STATES)
    suspend fun getState(
        @Query("country_id") id: Int
    ): Response<StateResponse>

    @GET(GET_CITY)
    suspend fun getCity(
        @Query("state_id") id: Int
    ): Response<CityResponse>

    @GET(GET_COACH_LOCATION)
    suspend fun getLocationDetails(): Response<LocationDetailsResponse>

    @POST(UPDATE_LOCATION)
    suspend fun updateLocation(@Body body: LocationUpdateBody): Response<LocationUpdateResponse>

    @GET(GET_LANGUAGES)
    suspend fun getLanguages(): Response<LanguagesResponse>

    @GET(GET_SPECIALIZATION)
    suspend fun getSpecialization(): Response<SpecializationResponse>

    @GET(GET_ASSOCIATION)
    suspend fun getAssociation(): Response<AssociationResponse>

    @POST(UPDATE_SPECIALIZATION_SCREEN)
    suspend fun updateSpecializationDetails(@Body body: SpecializationUpdateBody): Response<SpecializationUpdateResponse>

    @GET(GET_SPECIALIZATION_DETAILS)
    suspend fun getSpecializationDetails(): Response<SpecializationDetailsResponse>

    @Multipart
    @POST(UPDATE_COACH_VERIFICATION)
    suspend fun updateCoachVerification(
        @Part body: MultipartBody.Part?,
        @Part image2: MultipartBody.Part?,
        @Part("adhar_card_no") requestBodyCN: RequestBody?,
        @Part("coach_instagram_link") requestBodyLink: RequestBody?
        /* @Part("no_of_slots") requestBodySlots: RequestBody*/
    ): Response<UpdateCoachVerificationResponse>

    @GET(GET_VERIFICATION_DETAILS)
    suspend fun getVerificationDetails(): Response<VerificationDetailsResponse>

    @GET(CLIENT_LIST)
    suspend fun getClientList(): Response<ClientListResponse>

    @GET(CLIENT_LIST_filter)
    suspend fun getClientFilter(@Query("filter_name") filter: String): Response<ClientListResponse>

    @GET(APPOINTMENT_LIST_FILTER)
    suspend fun getAppointmentListFilter(@Query("date") date: String): Response<AppointmentListResponse>

    @POST(CANCEL_APPOINTMENT)
    suspend fun cancelAppointment(@Body body: CancelAppointmentBody): Response<AppointmentListResponse>

    @GET(USER_NAME_TIME_SLOT)
    suspend fun getUserNameAndTimeSlot1(@Query("book_date") date: String): Response<UserNameAndTimeSlotResponse>

    @GET(USER_NAME_TIME_SLOT)
    suspend fun getUserNameAndTimeSlot(): Response<UserNameAndTimeSlotResponse>

    @GET(GET_HEALTH_QUESTIONNAIRE_BY_COACH)
    suspend fun getHealthQuestionnaire(@Query("user_id") id: Int): Response<GetHealthQuestionnaire>

    @POST(ADD_NEW_APPOINTMENT)
    suspend fun addNewAppointment(@Body body: AddNewAppointmentBody): Response<AddNewAppointmentResponse>

    @GET(CHAT_LIST)
    suspend fun getChatList(): Response<ChatListResponse>

    @POST(LAST_MESSAGE)
    suspend fun lastMessage(@Body body: LastMessage): Response<LastMessageResponse>

    @GET(GET_CLIENT_DETAIL)
    suspend fun getClientDetails(
        @Query("user_id") id: Int,
        @Query("start_date") selectedDate: String
    ): Response<ClientDetailResponse>

    @GET(GET_AVAILABLE_TIME_SLOT)
    suspend fun getAvailableTimeSlot(): Response<AvailableTimeSlotResponse>

    @POST(LOGOUT)
    suspend fun logout(): Response<ChangePasswordResponse>

    @POST(DEACTIVATE_ACCOUNT)
    suspend fun deactivateAccount(): Response<ChangePasswordResponse>

    @POST(MARK_UNAVAILABLE)
    suspend fun markUnavailable(@Query("date") date: String): Response<MarkUnavailableResponse>

    @POST(EDIT_GOALS)
    suspend fun editGoals(@Body body: EditGoalsBody): Response<ChangePasswordResponse>

    @GET(GET_EDIT_GOALS)
    suspend fun getEditGoals(@Query("user_id") userId: Int): Response<GetEditGoalsResponse>

    @GET(GET_MARK_UNAVAILABLE)
    suspend fun getMarkedUnavailable(): Response<GetUnmarkedResponse>

    @POST(REMOVE_UNAVAILABLE_DATE)
    suspend fun removeUnavailabilityDate(@Query("date") selectDate: String): Response<RemoveUnavailabilityDate>

    @GET(GET_MEAL_DETAIL)
    suspend fun getMealDetail(
        @Query("meal_type_id") id: Int,
        @Query("user_id") userId: Int,
        @Query("date") date: String

    ): Response<MealTypeDetailResponse>


    @POST(STORE_COACH_QUICKBLOX_ID)
    suspend fun storequickbloxid(

        // @Field("coach_id") CoachId: String,
        //  @Field("quickBloxId") QuickBloxId: String
        @Body body: StoreQuickbloxBody
    ): Response<StoreQuickbloxResponse>


    @POST(FCM_TOKEN)
    suspend fun saveFCMToken(
        @Query("token") deviceToken: String
    ): Response<StoreQuickbloxResponse>

    @GET(NOTIFICATION_LIST)
    suspend fun notificationList(
    ): Response<NotificationListResponse>

    @POST(READ_NOTIFICATION)
    suspend fun readNotification(
        @Query("notification_id") id: Int
    ): Response<ReadNotificationResponse>
}
