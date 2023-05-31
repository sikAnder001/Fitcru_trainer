package com.ennovations.fitcrucoach.repository

import com.ennovations.fitcrucoach.Network.Api
import com.ennovations.fitcrucoach.Network.Api.CONSULTATION_FORM_DETAIL
import com.ennovations.fitcrucoach.Network.Service
import com.ennovations.fitcrucoach.body.*
import com.ennovations.fitcrucoach.response.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import javax.inject.Inject

class Repository @Inject constructor(private val service: Service) {

    suspend fun login(body: LoginBody): Response<LoginResponse> {
        return withContext(Dispatchers.IO) {
            service.login(body)
        }
    }

    suspend fun forgotPassword(body: ForgotPasswordBody): Response<ForgotPasswordResponse> {
        return withContext(Dispatchers.IO) {
            service.forgotPassword(body)
        }
    }

    suspend fun otpVerification(body: OTPVerificationBody): Response<ForgotPasswordResponse> {
        return withContext(Dispatchers.IO) {
            service.otpVerification(body)
        }
    }

    suspend fun createPassword(body: CreatePasswordBody): Response<ForgotPasswordResponse> {
        return withContext(Dispatchers.IO) {
            service.createPassword(body)
        }
    }

    suspend fun addCertificate(body: MultipartBody): Response<CoachDetailResponse> {
        return withContext(Dispatchers.IO) {
            service.addCertificate(body)
        }
    }

    suspend fun changePassword(body: ChangePasswordBody): Response<ChangePasswordResponse> {
        return withContext(Dispatchers.IO) {
            service.changePassword(body)
        }
    }

    suspend fun getCoachDetails(): Response<CoachProfileResponse> {
        return withContext(Dispatchers.IO) {
            service.getCoachDetails()
        }
    }

    suspend fun updateCoachDetails(body: MultipartBody): Response<CoachProfileResponse> {
        return withContext(Dispatchers.IO) {
            service.updateCoachDetails(body)
        }
    }

    suspend fun getCertificateDetail(id: Int): Response<GetCertificateResponse> {
        return withContext(Dispatchers.IO) {
            service.getCertificate(id)
        }
    }

    suspend fun updateCertificate(body: MultipartBody): Response<CoachDetailResponse> {
        return withContext(Dispatchers.IO) {
            service.updateCertificate(body)
        }
    }

    suspend fun consultationFormDetail(body: ConsultationFormBody): Response<ConsultationFormResponse> {
        return withContext(Dispatchers.IO) {
            service.consultationFormDetail(
                if (body.update) Api.UPDATE_CONSULTATION_FORM_DETAIL else CONSULTATION_FORM_DETAIL,
                body
            )
        }
    }

    suspend fun getConsultation(id: Int): Response<GetConsultationResponse> {
        return withContext(Dispatchers.IO) {
            service.getConsultation(id)
        }
    }

    suspend fun getCountries(): Response<CountriesResponse> {
        return withContext(Dispatchers.IO) {
            service.getCountries()
        }
    }

    suspend fun getStates(country: Int): Response<StateResponse> {
        return withContext(Dispatchers.IO) {
            service.getState(country)
        }
    }

    suspend fun getCity(state: Int): Response<CityResponse> {
        return withContext(Dispatchers.IO) {
            service.getCity(state)
        }
    }

    suspend fun getLocationDetails(): Response<LocationDetailsResponse> {
        return withContext(Dispatchers.IO) {
            service.getLocationDetails()
        }
    }

    suspend fun updateLocation(body: LocationUpdateBody): Response<LocationUpdateResponse> {
        return withContext(Dispatchers.IO) {
            service.updateLocation(body)
        }
    }

    suspend fun getLanguages(): Response<LanguagesResponse> {
        return withContext(Dispatchers.IO) {
            service.getLanguages()
        }

    }

    suspend fun getSpecialization(): Response<SpecializationResponse> {
        return withContext(Dispatchers.IO) {
            service.getSpecialization()
        }
    }

    suspend fun getAssociation(): Response<AssociationResponse> {
        return withContext(Dispatchers.IO) {
            service.getAssociation()
        }
    }

    suspend fun updateSpecializationDetails(body: SpecializationUpdateBody): Response<SpecializationUpdateResponse> {
        return withContext(Dispatchers.IO) {
            service.updateSpecializationDetails(body)
        }
    }

    suspend fun getSpecializationDetails(): Response<SpecializationDetailsResponse> {
        return withContext(Dispatchers.IO) {
            service.getSpecializationDetails()
        }
    }

    suspend fun updateCoachVerification(
        body: MultipartBody.Part?,
        image2: MultipartBody.Part?,
        requestBodyCN: RequestBody,
        requestBodyLink: RequestBody
        /* requestBodySlots: RequestBody*/
    ): Response<UpdateCoachVerificationResponse> {
        return withContext(Dispatchers.IO) {
            service.updateCoachVerification(
                body,
                image2,
                requestBodyCN,
                requestBodyLink
                /*  requestBodySlots*/
            )
        }
    }

    suspend fun getVerificationDetails(): Response<VerificationDetailsResponse> {
        return withContext(Dispatchers.IO) {
            service.getVerificationDetails()
        }
    }

    suspend fun getClientList(): Response<ClientListResponse> {
        return withContext(Dispatchers.IO) {
            service.getClientList()
        }
    }

    suspend fun getClientFilter(filter: String): Response<ClientListResponse> {
        return withContext(
            Dispatchers.IO
        ) {
            service.getClientFilter(filter)
        }
    }

    suspend fun getAppointmentListFilter(date: String): Response<AppointmentListResponse> {
        return withContext(
            Dispatchers.IO
        ) {
            service.getAppointmentListFilter(date)
        }
    }

    suspend fun cancelAppointment(body: CancelAppointmentBody): Response<AppointmentListResponse> {
        return withContext(
            Dispatchers.IO
        ) {
            service.cancelAppointment(body)
        }
    }

    suspend fun getUserNameAndTimeSlot1(date: String): Response<UserNameAndTimeSlotResponse> {
        return withContext(Dispatchers.IO) {
            service.getUserNameAndTimeSlot1(date)
        }
    }

    suspend fun getUserNameAndTimeSlot(): Response<UserNameAndTimeSlotResponse> {
        return withContext(Dispatchers.IO) {
            service.getUserNameAndTimeSlot()
        }
    }

    suspend fun getHealthQuestionnaire(id: Int): Response<GetHealthQuestionnaire> {
        return withContext(Dispatchers.IO) {
            service.getHealthQuestionnaire(id)
        }
    }

    suspend fun addNewAppointment(body: AddNewAppointmentBody): Response<AddNewAppointmentResponse> {
        return withContext(Dispatchers.IO) {
            service.addNewAppointment(body)
        }
    }

    suspend fun getChatList(): Response<ChatListResponse> {
        return withContext(Dispatchers.IO) {
            service.getChatList()
        }
    }

    suspend fun lastMessage(lastMessage: LastMessage): Response<LastMessageResponse> {
        return withContext(Dispatchers.IO) {
            service.lastMessage(lastMessage)
        }
    }

    suspend fun getClientDetails(
        clientId: Int,
        selectedDate: String
    ): Response<ClientDetailResponse> {
        return withContext(Dispatchers.IO) {
            service.getClientDetails(clientId, selectedDate)
        }
    }

    suspend fun getAvailableTimeSlot(): Response<AvailableTimeSlotResponse> {
        return withContext(Dispatchers.IO) {
            service.getAvailableTimeSlot()
        }
    }

    suspend fun logout(): Response<ChangePasswordResponse> {
        return withContext(Dispatchers.IO) {
            service.logout()
        }
    }

    suspend fun deactivateAccount(): Response<ChangePasswordResponse> {
        return withContext(Dispatchers.IO) {
            service.deactivateAccount()
        }
    }

    suspend fun markUnavailable(date: String): Response<MarkUnavailableResponse> {
        return withContext(Dispatchers.IO) {
            service.markUnavailable(date)
        }
    }

    suspend fun editGoals(body: EditGoalsBody): Response<ChangePasswordResponse> {
        return withContext(Dispatchers.IO) {
            service.editGoals(body)
        }
    }

    suspend fun getEditGoals(userId: Int): Response<GetEditGoalsResponse> {
        return withContext(Dispatchers.IO) {
            service.getEditGoals(userId)

        }
    }

    suspend fun getMarkedUnavailable(): Response<GetUnmarkedResponse> {
        return withContext(Dispatchers.IO) {
            service.getMarkedUnavailable()
        }
    }

    suspend fun removeUnavailabilityDate(selectedDate: String): Response<RemoveUnavailabilityDate> {
        return withContext(Dispatchers.IO) {
            service.removeUnavailabilityDate(selectedDate)
        }
    }

    suspend fun getMealDetail(
        id: Int,
        userId: Int,
        date: String
    ): Response<MealTypeDetailResponse> {
        return withContext(Dispatchers.IO) {
            service.getMealDetail(id, userId, date)
        }
    }


    suspend fun storequickbloxid(request: StoreQuickbloxBody): Response<StoreQuickbloxResponse> {
        return withContext(Dispatchers.IO) {
            service.storequickbloxid(request)
        }
    }

    suspend fun saveFCMToken(deviceToken: String): Response<StoreQuickbloxResponse> {
        return withContext(Dispatchers.IO) {
            service.saveFCMToken(deviceToken)
        }
    }

    suspend fun notificationList(): Response<NotificationListResponse> {
        return withContext(Dispatchers.IO) {
            service.notificationList()
        }
    }

    suspend fun readNotification(id: Int): Response<ReadNotificationResponse> {
        return withContext(Dispatchers.IO) {
            service.readNotification(id)
        }
    }

}