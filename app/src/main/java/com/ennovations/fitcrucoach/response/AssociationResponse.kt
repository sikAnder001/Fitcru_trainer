package com.ennovations.fitcrucoach.response

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class AssociationResponse(
    @SerializedName("GymAssociations")
    val Gym_Associations: List<Data>? = null,
    val error_code: Int,
    val message: String
) : Serializable {

    data class Data(
        val id: Int,
        val gym_name: String,
        val country_id: Int,
        val state_id: Int,
        val city_id: Int,
        val location_name: String,
        val pincode: String
    ) {
        override fun toString(): String {
            return gym_name
        }

    }
}
