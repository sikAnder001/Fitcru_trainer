package com.ennovations.fitcrucoach.utility

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.ennovations.fitcrucoach.R


class SharedPreferenceUtil
private constructor(val context: Context) {
    val TAG = SharedPreferenceUtil::class.java.simpleName

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)

    private val editor: SharedPreferences.Editor = sharedPreferences.edit()

    companion object {

        @SuppressLint("StaticFieldLeak")
        private var instance: SharedPreferenceUtil? = null

        fun getInstance(ctx: Context): SharedPreferenceUtil {
            if (instance == null) {
                instance = SharedPreferenceUtil(ctx)
            }
            return instance!!
        }
    }


    var phoneCode: Int
        get() = sharedPreferences["phoneCode", 91]!!
        set(value) = sharedPreferences.set("phoneCode", value)

    var countryId: Int
        get() = sharedPreferences["countryId", 0]!!
        set(value) = sharedPreferences.set("countryId", value)


    var userId: Int
        get() = sharedPreferences["userId", 0]!!
        set(value) = sharedPreferences.set("userId", value)

    var userLocation: String
        get() = sharedPreferences["userLocation", ""]!!
        set(value) = sharedPreferences.set("userLocation", value)


    var NewsUrl: String
        get() = sharedPreferences["NewsUrl", ""]!!
        set(value) = sharedPreferences.set("NewsUrl", value)

    var chatName: String
        get() = sharedPreferences["chatName", ""]!!
        set(value) = sharedPreferences.set("chatName", value)


    var locationAddComplete: String
        get() = sharedPreferences["locationAddComplete", ""]!!
        set(value) = sharedPreferences.set("locationAddComplete", value)


    var locationAdd: String
        get() = sharedPreferences["locationAdd", ""]!!
        set(value) = sharedPreferences.set("locationAdd", value)


    var mobileNumberEmail: String
        get() = sharedPreferences["mobileNumberEmail", ""]!!
        set(value) = sharedPreferences.set("mobileNumberEmail", value)

    var isLogin_manage: Boolean
        get() = sharedPreferences["isLogin_manage", false]!!
        set(value) = sharedPreferences.set("isLogin_manage", value)

    var isLogout_manage: Boolean
        get() = sharedPreferences["isLogin_manage", true]!!
        set(value) = sharedPreferences.set("isLogin_manage", value)


    var Category_id: String
        get() = sharedPreferences["Category_id", ""]!!
        set(value) = sharedPreferences.set("Category_id", value)


    var Category_name: String
        get() = sharedPreferences["Category_name", ""]!!
        set(value) = sharedPreferences.set("Category_name", value)


    var Cat_Id: Int
        get() = sharedPreferences["Cat_Id", 0]!!
        set(value) = sharedPreferences.set("Cat_Id", value)


    var count_notification: Int
        get() = sharedPreferences["count_notification", 0]!!
        set(value) = sharedPreferences.set("count_notification", value)


    var SubCategory_id: String
        get() = sharedPreferences["SubCategory_id", ""]!!
        set(value) = sharedPreferences.set("SubCategory_id", value)


    var brand_id: String
        get() = sharedPreferences["brand_id", ""]!!
        set(value) = sharedPreferences.set("brand_id", value)


    var user_id: String
        get() = sharedPreferences["user_id", ""]!!
        set(value) = sharedPreferences.set("user_id", value)

    var news_id: Int
        get() = sharedPreferences["news_id", 0]!!
        set(value) = sharedPreferences.set("news_id", value)

    var accessToken: String
        get() = sharedPreferences["accessToken", ""]!!
        set(value) = sharedPreferences.set("accessToken", value)

    var signinToken: String
        get() = sharedPreferences["signinToken", ""]!!
        set(value) = sharedPreferences.set("signinToken", value)

    var deviceToken: String
        get() = sharedPreferences["deviceToken", ""]!!
        set(value) = sharedPreferences.set("deviceToken", value)

    var isVerified: String
        get() = sharedPreferences["isVerified", ""]!!
        set(value) = sharedPreferences.set("isVerified", value)

    var isEnabled: Boolean
        get() = sharedPreferences["isVerified", false]!!
        set(value) = sharedPreferences.set("isVerified", value)

    var firstname: String
        get() = sharedPreferences["firstname", ""]!!
        set(value) = sharedPreferences.set("firstname", value)

    var lastname: String
        get() = sharedPreferences["lastname", ""]!!
        set(value) = sharedPreferences.set("lastname", value)


    var userName: String
        get() = sharedPreferences["userName", ""]!!
        set(value) = sharedPreferences.set("userName", value)

    var name: String
        get() = sharedPreferences["name", ""]!!
        set(value) = sharedPreferences.set("name", value)

    var userType: String
        get() = sharedPreferences["userType", ""]!!
        set(value) = sharedPreferences.set("userType", value)

    var uriPath: String
        get() = sharedPreferences["uriPath", ""]!!
        set(value) = sharedPreferences.set("uriPath", value)

    var lastName: String
        get() = sharedPreferences["lastName", ""]!!
        set(value) = sharedPreferences.set("lastName", value)

    var phoneNumber: String
        get() = sharedPreferences["phoneNumber", ""]!!
        set(value) = sharedPreferences.set("phoneNumber", value)

    var mobile: String
        get() = sharedPreferences["mobileNo", ""]!!
        set(value) = sharedPreferences.set("mobileNo", value)


    var mobileNumber: String
        get() = sharedPreferences["mobileNumber", ""]!!
        set(value) = sharedPreferences.set("mobileNumber", value)

    var full_name: String
        get() = sharedPreferences["full_name", ""]!!
        set(value) = sharedPreferences.set("full_name", value)

    var total_credit_point: String
        get() = sharedPreferences["total_credit_point", ""]!!
        set(value) = sharedPreferences.set("total_credit_point", value)


    var countryCode: String
        get() = sharedPreferences["countryCode", ""]!!
        set(value) = sharedPreferences.set("countryCode", value)


    var CurrentLocationName: String
        get() = sharedPreferences["CurrentLocationName", ""]!!
        set(value) = sharedPreferences.set("CurrentLocationName", value)

    var CurrentLocationCity: String
        get() = sharedPreferences["CurrentLocationCity", ""]!!
        set(value) = sharedPreferences.set("CurrentLocationCity", value)


    var CurrentLocation: String
        get() = sharedPreferences["CurrentLocation", ""]!!
        set(value) = sharedPreferences.set("CurrentLocation", value)


    var NewsPic: String
        get() = sharedPreferences["NewsPic", ""]!!
        set(value) = sharedPreferences.set("NewsPic", value)


    var NewsTitle: String
        get() = sharedPreferences["NewsTitle", ""]!!
        set(value) = sharedPreferences.set("NewsTitle", value)


    var isLogin: Boolean
        get() = sharedPreferences["isLogin", false]!!
        set(value) = sharedPreferences.set("isLogin", value)


    var password: String
        get() = sharedPreferences["password", ""]!!
        set(value) = sharedPreferences.set("password", value)

    var dob: String
        get() = sharedPreferences["dob", ""]!!
        set(value) = sharedPreferences.set("dob", value)

    var gender: String
        get() = sharedPreferences["gender", ""]!!
        set(value) = sharedPreferences.set("gender", value)

    var email: String
        get() = sharedPreferences["email", ""]!!
        set(value) = sharedPreferences.set("email", value)


    var emailId: String
        get() = sharedPreferences["emailId", ""]!!
        set(value) = sharedPreferences.set("emailId", value)

    var firstName: String
        get() = sharedPreferences["firstName", ""]!!
        set(value) = sharedPreferences.set("firstName", value)


    var building: String
        get() = sharedPreferences["building", ""]!!
        set(value) = sharedPreferences.set("building", value)

    var locality: String
        get() = sharedPreferences["locality", ""]!!
        set(value) = sharedPreferences.set("locality", value)

    var city: String
        get() = sharedPreferences["city", ""]!!
        set(value) = sharedPreferences.set("city", value)

    var state: String
        get() = sharedPreferences["state", ""]!!
        set(value) = sharedPreferences.set("state", value)


    var country: String
        get() = sharedPreferences["country", ""]!!
        set(value) = sharedPreferences.set("country", value)

    var registrationId: String
        get() = sharedPreferences["registrationId", ""]!!
        set(value) = sharedPreferences.set("registrationId", value)

    var latitude: Float
        get() = sharedPreferences["latitude", 0.0f]!!
        set(value) = sharedPreferences.set("latitude", value)

    var longitude: Float
        get() = sharedPreferences["longitude", 0.0f]!!
        set(value) = sharedPreferences.set("longitude", value)

    var citycode: String
        get() = sharedPreferences["citycode", ""]!!
        set(value) = sharedPreferences.set("citycode", value)
    var countryName: String
        get() = sharedPreferences["countryName", ""]!!
        set(value) = sharedPreferences.set("countryName", value)

    var accountno: String
        get() = sharedPreferences["accountno", ""]!!
        set(value) = sharedPreferences.set("accountno", value)

    var accountholdername: String
        get() = sharedPreferences["accountholdername", ""]!!
        set(value) = sharedPreferences.set("accountholdername", value)
//ibannumber

    var ibannumber: String
        get() = sharedPreferences["ibannumber", ""]!!
        set(value) = sharedPreferences.set("ibannumber", value)

    var bankname: String
        get() = sharedPreferences["bankname", ""]!!
        set(value) = sharedPreferences.set("bankname", value)

    var walletbalance: String
        get() = sharedPreferences["walletbalance", ""]!!
        set(value) = sharedPreferences.set("walletbalance", value)

    var logintype: String
        get() = sharedPreferences["logintype", ""]!!
        set(value) = sharedPreferences.set("logintype", value)

    var loyaltyFlag: Boolean
        get() = sharedPreferences["loyaltyFlag", false]!!
        set(value) = sharedPreferences.set("loyaltyFlag", value)


    //Product Detail prefrence
    var productUserprofilePic: String
        get() = sharedPreferences["userprofilePic", ""]!!
        set(value) = sharedPreferences.set("userprofilePic", value)

    var productUserName: String
        get() = sharedPreferences["productUserName", ""]!!
        set(value) = sharedPreferences.set("productUserName", value)
    var productUserCity: String
        get() = sharedPreferences["productUserCity", ""]!!
        set(value) = sharedPreferences.set("productUserCity", value)

    //Product Detail Info
    var productId: String
        get() = sharedPreferences["productId", ""]!!
        set(value) = sharedPreferences.set("productId", value)

    var productImg: String
        get() = sharedPreferences["productImg", ""]!!
        set(value) = sharedPreferences.set("productImg", value)

    var productName: String
        get() = sharedPreferences["productName", ""]!!
        set(value) = sharedPreferences.set("productName", value)
    var productBrandName: String
        get() = sharedPreferences["productBrandName", ""]!!
        set(value) = sharedPreferences.set("productBrandName", value)

    var productpriceType: String
        get() = sharedPreferences["productpriceType", ""]!!
        set(value) = sharedPreferences.set("productpriceType", value)

    var productColor: String
        get() = sharedPreferences["productColor", ""]!!
        set(value) = sharedPreferences.set("productColor", value)

    var productSize: String
        get() = sharedPreferences["productSize", ""]!!
        set(value) = sharedPreferences.set("productSize", value)

    var productPrice: String
        get() = sharedPreferences["productPrice", ""]!!
        set(value) = sharedPreferences.set("productPrice", value)
    var productCategories: String
        get() = sharedPreferences["productCategories", ""]!!
        set(value) = sharedPreferences.set("productCategories", value)

    var productuserDetailId: String
        get() = sharedPreferences["productuserDetailId", ""]!!
        set(value) = sharedPreferences.set("productuserDetailId", value)
    var producttotalLikes: String
        get() = sharedPreferences["producttotalLikes", ""]!!
        set(value) = sharedPreferences.set("producttotalLikes", value)

    var productuserId: String
        get() = sharedPreferences["productuserId", ""]!!
        set(value) = sharedPreferences.set("productuserId", value)

    var packagesize: String
        get() = sharedPreferences["packagesize", ""]!!
        set(value) = sharedPreferences.set("packagesize", value)

    var productorderid: String
        get() = sharedPreferences["productorderid", ""]!!
        set(value) = sharedPreferences.set("productorderid", value)

    /**
     * puts a key value pair in shared prefs if doesn't exists, otherwise updates value on given [key]
     */
    operator fun SharedPreferences.set(key: String, value: Any?) {
        when (value) {
            is Int -> edit { it.putInt(key, value) }
            is String? -> edit { it.putString(key, value) }
            is Boolean -> edit { it.putBoolean(key, value) }
            is Float -> edit { it.putFloat(key, value) }
            is Long -> edit { it.putLong(key, value) }
            else -> Log.e(TAG, "Setting shared pref failed for key: $key and value: $value ")
        }
    }

    private inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
        val editor = this.edit()
        operation(editor)
        editor.apply()
    }

    /**
     * finds value on given key.
     * [T] is the type of value
     * @param defaultValue optional default value - will take null for strings, false for bool and -1 for numeric values if [defaultValue] is not specified
     */
    inline operator fun <reified T : Any> SharedPreferences.get(
        key: String,
        defaultValue: T? = null
    ): T? {
        return when (T::class) {
            String::class -> getString(key, defaultValue as? String) as T?
            Int::class -> getInt(key, defaultValue as? Int ?: -1) as T?
            Boolean::class -> getBoolean(key, defaultValue as? Boolean ?: false) as T?
            Float::class -> getFloat(key, defaultValue as? Float ?: -1f) as T?
            Long::class -> getLong(key, defaultValue as? Long ?: -1) as T?
            else -> throw UnsupportedOperationException("Not yet implemented")
        }
    }

    fun deletePreferences() {
        editor.clear()
        editor.apply()
    }
}