package com.ennovations.fitcrucoach.utility

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Patterns
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.bumptech.glide.Glide
import com.ennovations.fitcrucoach.Network.NetworkResult
import com.ennovations.fitcrucoach.R
import com.google.gson.Gson
import okhttp3.ResponseBody
import retrofit2.Response
import java.util.regex.Pattern

class Util {
    fun validEmail(email: String?): Boolean {
        val pattern = Patterns.EMAIL_ADDRESS
        return pattern.matcher(email).matches()
    }

    companion object {
        fun isValid(password: String?): Boolean {
            val PASSWORD_PATTERN =
                "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$"
            val pattern = Pattern.compile(PASSWORD_PATTERN)
            val matcher = pattern.matcher(password)
            return matcher.matches()
        }

        fun toast(context: Context, string: String) =
            Toast.makeText(context, string, Toast.LENGTH_LONG).show()

        fun <T> error(error: ResponseBody?, type: Class<T>?): T {
            return Gson().fromJson(error!!.charStream(), type)
        }

        fun <T> error(error: Any?, type: Class<T>?): T {
            return Gson().fromJson((error!! as ResponseBody).charStream(), type)
        }

        fun <T> handleResponse(
            liveData: MutableLiveData<NetworkResult<Any>>,
            response: Response<T>
        ): Response<T> {
            liveData.value = if (response.isSuccessful && response.body() != null)
                NetworkResult.Success(response.body()!!)
            else if (!response.isSuccessful && response.errorBody() != null) {
                NetworkResult.Error("Error : ", response.errorBody())
            } else
                NetworkResult.Error("Something Went Wrong...")
            return response
        }

        @SuppressLint("LongLogTag")
        fun <T> print(body: Response<T>): Response<T> {
            //Log.e(TAG, "${body.code()} <<<<<< ${Gson().toJson(body.body() ?: body.errorBody())}")
            return body
        }

        fun loadImage(
            context: Context,
            imageView: ImageView,
            imageURL: String?,
        ) {
            Glide.with(context).load(imageURL)
                .placeholder(context.resources.getDrawable(R.drawable.place_holder, null))
                .into(imageView)
        }

        fun openWhatsapp(context: Context, message: String) {
            try {
                val uri =
                    Uri.parse("whatsapp://send?phone=${context.getString(R.string.whatsapp_mobile_no)}")
                val i = Intent(Intent.ACTION_VIEW, uri)
                i.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                context.startActivity(i)
            } catch (e: ActivityNotFoundException) {
                e.printStackTrace()
                Toast.makeText(context, "WhatsApp not installed.", Toast.LENGTH_SHORT).show()
            }
        }

    }
}

object Utils {
    fun validEmail(email: String?): Boolean {
        val pattern = Patterns.EMAIL_ADDRESS
        return pattern.matcher(email).matches()
    }

    fun isValidPass(password: String?): Boolean {
        val PASSWORD_PATTERN =
            "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$"
        val pattern = Pattern.compile(PASSWORD_PATTERN)
        val matcher = pattern.matcher(password)
        return matcher.matches()
    }
}
