package com.ennovations.fitcrucoach.fragments

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.text.InputType
import android.text.Spannable.Factory
import android.text.TextUtils
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.ennovations.fitcrucoach.Network.CustomProgressLoading
import com.ennovations.fitcrucoach.Network.NetworkResult
import com.ennovations.fitcrucoach.Network.TokenManager
import com.ennovations.fitcrucoach.R
import com.ennovations.fitcrucoach.activities.ERROR_LOGIN_ALREADY_TAKEN_HTTP_STATUS
import com.ennovations.fitcrucoach.body.LoginBody
import com.ennovations.fitcrucoach.body.StoreQuickbloxBody
import com.ennovations.fitcrucoach.databinding.FragmentLoginBinding
import com.ennovations.fitcrucoach.quickbox.executor.signInUser
import com.ennovations.fitcrucoach.quickbox.executor.signUp
import com.ennovations.fitcrucoach.quickbox.services.LoginService
import com.ennovations.fitcrucoach.quickbox.utils.EXTRA_LOGIN_RESULT_CODE
import com.ennovations.fitcrucoach.quickbox.utils.SharedPrefsHelper
import com.ennovations.fitcrucoach.quickbox.utils.longToast
import com.ennovations.fitcrucoach.repository.Repository
import com.ennovations.fitcrucoach.response.LoginResponse
import com.ennovations.fitcrucoach.response.StoreQuickbloxResponse
import com.ennovations.fitcrucoach.utility.*
import com.ennovations.fitcrucoach.view_model.LoginViewModel
import com.google.firebase.messaging.FirebaseMessaging
import com.orhanobut.hawk.Hawk
import com.quickblox.core.QBEntityCallback
import com.quickblox.core.exception.QBResponseException
import com.quickblox.users.QBUsers
import com.quickblox.users.model.QBUser
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.internal.platform.android.BouncyCastleSocketAdapter.Companion.factory
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import javax.inject.Inject


@AndroidEntryPoint
class LoginFragment : Fragment() {

    private val OVERLAY_PERMISSION_CHECKED_KEY = "overlay_checked"
    private val MI_OVERLAY_PERMISSION_CHECKED_KEY = "mi_overlay_checked"
    private val ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE = 1764


    private lateinit var user: QBUser
    // private  lateinit var sharedPreference: SharedPreferenceUtil


    private lateinit var loginBinding: FragmentLoginBinding

    private var isPasswordVisible = false

    private val loginViewModel by viewModels<LoginViewModel>()

    private lateinit var loading: CustomProgressLoading

    private lateinit var loginResponse: LoginResponse

    private lateinit var deviceId: String
    private lateinit var name: String
    private lateinit var image: String
    private lateinit var repository: Repository


    private lateinit var factory: Factory

    private val deviceType = 0

    private var deviceToken = ""

    private var which = 0
    // private  var isLogin = sharedPreference.isLogin_manage

    @Inject
    lateinit var tokenManager: TokenManager
    private var email = ""


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        loginBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)

        // sharedPreference = SharedPreferenceUtil.getInstance(requireContext())
        // getExternalStoragePermission()
        FirebaseMessaging.getInstance().token
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val token = task.result
                    deviceToken = token.toString()
                    Hawk.put(DEVICE_TOKEN, deviceToken)
                } else {
                    Log.v(
                        "LoginMainActivity",
                        "Fetching FCM registration token failed",
                        task.exception
                    )
                    deviceToken = ""
                }
                Log.v("FCMToken", deviceToken)
            }

        // showToast("Login Fragemnt")


        val userEmail = SessionManager.getEmailForCreate()










        deviceId =
            Settings.Secure.getString(requireActivity().contentResolver, Settings.Secure.ANDROID_ID)

        email = loginBinding.emailET.text.toString().trim()

        loading = CustomProgressLoading(requireContext())

        if (checkOverlayPermissions()) {

        }
        move()






        hittingViews()
        //TODO Uncomment when need to redirect from login screen to T&C or Privacy Policy


        return loginBinding.root
        getExternalStoragePermission()


    }


    private fun togglePassVisibility() {

        if (isPasswordVisible) {

            val pass = loginBinding!!.password.text.toString()

            loginBinding!!.password.transformationMethod =

                PasswordTransformationMethod.getInstance()

            loginBinding!!.password.inputType =
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD

            loginBinding!!.password.setText(pass)

            loginBinding!!.password.setSelection(pass.length)

            loginBinding!!.passVisibility.setImageDrawable(resources.getDrawable(R.drawable.ic_baseline_visibility_24))
        } else {

            val pass = loginBinding!!.password.text.toString()

            loginBinding!!.password.transformationMethod =
                HideReturnsTransformationMethod.getInstance()

            loginBinding!!.password.inputType = InputType.TYPE_CLASS_TEXT

            loginBinding!!.password.setText(pass)

            loginBinding!!.password.setSelection(pass.length)

            loginBinding!!.passVisibility.setImageDrawable(resources.getDrawable(R.drawable.ic_baseline_visibility_off_24))
        }
        isPasswordVisible = !isPasswordVisible
    }

    private fun move() {
        loginBinding.apply {
            loginBtn.setOnClickListener {

                val validateCredentials = loginViewModel.validateCredentials(
                    loginBinding.emailET.text.toString().trim(),
                    loginBinding.password.text.toString().trim()
                )


                if (validateCredentials.first) {
                    quickbloxlogin()


                    loginViewModel.loginUser(


                        LoginBody(
                            loginBinding.emailET.text.toString().trim(),
                            loginBinding.password.text.toString().trim(),
                            deviceToken,
                            deviceId,
                            deviceType
                        )
                    )


                } else {
                    Util.toast(requireContext(), validateCredentials.second)
                }
            }

            loginViewModel.loginResponse.observe(viewLifecycleOwner) {


                loading.showProgress()
                when (it) {
                    is NetworkResult.Success -> {

                        Log.e("move", "1")
                        loginResponse = it.data as LoginResponse
                        /*   longToast("quickNewUserCreated ID:"+user.id.toString())*/

                        try {
                            saveFcmToken()

                        } catch (e: Exception) {
                        }
                        tokenManager.saveToken(loginResponse.data.access_token)

                        Hawk.put(ACCESS_TOKEN, loginResponse.data.access_token)
                        Hawk.put(DEVICE_TOKEN, deviceToken)
                        Hawk.put(NAME, loginResponse.data.coach_name)
                        Hawk.put(
                            IMAGE,
                            if (loginResponse.data.image_url != null) loginResponse.data.image_url else "empty"
                        )

                        SessionManager.saveInt("coach_id", loginResponse.data.id ?: 0)

                        SessionManager.setCoachDetails(loginResponse.data)

                        //  saveFcmToken()


                        // if (isLogin==true) {


                        /*  view?.findNavController()
                              ?.navigate(R.id.action_loginFragment_to_CWorkoutFragment2)*/
                        //  showToast("login response redirect")
                        //}
                    }
                    is NetworkResult.Error -> {
                        loading.hideProgress()
                        Log.e("move", "2")
                        val loginResponse = Util.error(it.data, LoginResponse::class.java)
                        //  loginResponse.error_code
                        Toast.makeText(requireContext(), loginResponse.message, Toast.LENGTH_LONG)
                            .show()
                    }
                    is NetworkResult.Loading -> {
                        Log.e("move", "3")
                        //   loading.showProgress()
                    }
                }
            }

            passVisibility.setOnClickListener {

                togglePassVisibility()

            }

            forgetPasswordTv.setOnClickListener {

                view?.findNavController()
                    ?.navigate(R.id.action_loginFragment_to_forgotPasswordFragment)

            }
        }

        loginViewModel.storeQuickbloxResponse.observe(viewLifecycleOwner) {
            when (it) {
                is NetworkResult.Success -> {
                    /*    SharedPrefsHelper.saveQbUser(user)
                        updateUserOnServer(user)*/
                    //  longToast("Store")
                    /*SharedPrefsHelper.saveQbUser(user)
                    loginToChat(user)*/
                }
                is NetworkResult.Error -> {

                    val loginResponse = Util.error(it.data, LoginResponse::class.java)

                    Toast.makeText(requireContext(), loginResponse.message, Toast.LENGTH_LONG)
                        .show()
                }
                is NetworkResult.Loading -> {
                    // loading.showProgress()
                }
            }
        }
    }

    private fun saveFcmToken() {
        loginViewModel.saveFCMToken(deviceToken)


        try {
            loginViewModel.fcmToken.observe(viewLifecycleOwner) {

                when (it) {
                    is NetworkResult.Success -> {
                        Log.v("FCM Response", it.message.toString())
                    }
                    is NetworkResult.Error -> {

                        val loginResponse = Util.error(it.data, StoreQuickbloxResponse::class.java)

//                        Toast.makeText(requireContext(), loginResponse.message, Toast.LENGTH_LONG)
//                            .show()
                    }
                    is NetworkResult.Loading -> {

                    }
                }

            }
        } catch (e: Exception) {

        }
    }

    private fun hittingViews() {
        loginBinding.policyTv.makeLinks(Pair("Privacy Policy", View.OnClickListener {

            which = 4
            val b = Bundle()
            b.putInt("w", which)

            view?.findNavController()
                ?.navigate(R.id.action_loginFragment_to_termsAndPrivacyFragment, b)
        }), Pair("Terms and Condition", View.OnClickListener {

            which = 3
            val b = Bundle()
            b.putInt("w", which)

            view?.findNavController()
                ?.navigate(R.id.action_loginFragment_to_termsAndPrivacyFragment, b)
        })
        )
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        Log.e("TAG", "onActivityResult")

        if (requestCode == ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE) {
            if (checkOverlayPermissions()) {
                runNextScreen()
            }
        }
    }

    private fun runNextScreen() {
        /* if (sharedPreference.isLogin) {
            startActivity(Intent(this,   MainActivity::class.java))
            finishAffinity()
        }else {
            val v = Intent(this, LoginMainActivity::class.java)
            startActivity(v)
            getExternalStoragePermission()
            finish()
        }*/
    }

    //permission
    private fun getExternalStoragePermission() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            //Permission not granted
            ActivityCompat.requestPermissions(
                context as Activity,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                101
            )
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    context as Activity,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE
                )
            ) {
                //Can ask user for permission

                ActivityCompat.requestPermissions(
                    context as Activity, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                    101
                )

            } else {
                var userAskedPermissionBefore = false

                if (userAskedPermissionBefore == false) {
                    //If User was asked permission before and denied
                    var alertDialogBuilder = AlertDialog.Builder(requireContext());

                    alertDialogBuilder.setTitle("Permission needed");
                    alertDialogBuilder.setMessage("Location permission Required");
                    alertDialogBuilder.setPositiveButton("Open Setting") { dialogInterface, which ->

                        var intent = Intent()
                        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        var uri = Uri.fromParts(
                            "package", requireContext().packageName,
                            null
                        )
                        intent.data = uri
                        startActivity(intent)
                    }

                    //alertDialogBuilder.setNegativeButton("Cancel"){dialogInterface, which->
                    //    Log.d(ContentValues.TAG, "onClick: Cancelling");

                    //  }

                    //  var dialog = alertDialogBuilder.create();
                    //   dialog.show();
                } else {
                    //If user is asked permission for first time
                    ActivityCompat.requestPermissions(
                        requireContext() as Activity,
                        arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                        101
                    );


                }
            }

        }

    }


    private fun checkOverlayPermissions(): Boolean {
        Log.e("TAG", "Checking Permissions")
        val overlayChecked = SharedPrefsHelper.get(OVERLAY_PERMISSION_CHECKED_KEY, false)
        val miOverlayChecked = SharedPrefsHelper.get(MI_OVERLAY_PERMISSION_CHECKED_KEY, false)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(requireContext()) && !overlayChecked) {
                Log.e("TAG", "Android Overlay Permission NOT Granted")
                buildOverlayPermissionAlertDialog()
                return false
            } else if (isMiUi() && !miOverlayChecked) {
                Log.e("TAG", "Xiaomi Device. Need additional Overlay Permissions")
                buildMIUIOverlayPermissionAlertDialog()
                return false
            }
        }
        Log.e("TAG", "All Overlay Permission Granted")
        SharedPrefsHelper.save(OVERLAY_PERMISSION_CHECKED_KEY, true)
        SharedPrefsHelper.save(MI_OVERLAY_PERMISSION_CHECKED_KEY, true)
        return true
    }

    fun isMiUi(): Boolean {
        return !TextUtils.isEmpty(getSystemProperty("ro.miui.ui.version.name")) ||
                !TextUtils.isEmpty(getSystemProperty("ro.miui.ui.version.code"))
    }

    fun getSystemProperty(propName: String): String? {
        val line: String
        var input: BufferedReader? = null
        try {
            val p = Runtime.getRuntime().exec("getprop $propName")
            input = BufferedReader(InputStreamReader(p.inputStream), 1024)
            line = input.readLine()
            input.close()
        } catch (ex: IOException) {
            return null
        } finally {
            if (input != null) {
                try {
                    input.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }

            }
        }
        return line
    }

    private fun buildOverlayPermissionAlertDialog() {
        val builder =
            androidx.appcompat.app.AlertDialog.Builder(requireContext(), R.style.AlertDialogTheme)
        builder.setTitle("Overlay Permission Required")
        builder.setIcon(R.drawable.ic_error_outline_gray_24dp)
        builder.setMessage("To receive calls in background - \nPlease Allow overlay permission in Android Settings")
        builder.setCancelable(false)

        builder.setNeutralButton("No") { dialog, which ->
            showToast("You might miss calls while your application in background")
            SharedPrefsHelper.save(OVERLAY_PERMISSION_CHECKED_KEY, true)
        }

        builder.setPositiveButton("Settings") { dialog, which ->
            showAndroidOverlayPermissionsSettings()
        }

        val alertDialog = builder.create()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            alertDialog.create()
            alertDialog.show()
        }
    }


    private fun showAndroidOverlayPermissionsSettings() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(
                requireContext()
            )
        ) {
            val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION)
            intent.data = Uri.parse("package:" + requireContext().packageName)
            startActivityForResult(intent, ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE)
        } else {
            Log.d("TAG", "Application Already has Overlay Permission")
        }
    }

    fun buildMIUIOverlayPermissionAlertDialog() {
        val builder =
            androidx.appcompat.app.AlertDialog.Builder(requireContext(), R.style.AlertDialogTheme)
        builder.setTitle("Additional Overlay Permission Required")
        builder.setIcon(R.drawable.ic_error_outline_orange_24dp)
        builder.setMessage("Please make sure that all additional permissions granted")
        builder.setCancelable(false)

        builder.setNeutralButton("I'm sure") { dialog, which ->
            SharedPrefsHelper.save(MI_OVERLAY_PERMISSION_CHECKED_KEY, true)
            runNextScreen()
        }

        builder.setPositiveButton("Mi Settings") { dialog, which ->
            showMiUiPermissionsSettings()
        }

        val alertDialog = builder.create()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            alertDialog.create()
            alertDialog.show()
        }
    }

    private fun showMiUiPermissionsSettings() {
        val intent = Intent("miui.intent.action.APP_PERM_EDITOR")
        intent.setClassName(
            "com.miui.securitycenter",
            "com.miui.permcenter.permissions.PermissionsEditorActivity"
        )
        intent.putExtra("extra_pkgname", requireContext().packageName)
        startActivityForResult(intent, ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE)
    }


    private fun quickbloxlogin() {

        Log.e("move", "4")
        val user = createUserWithEnteredData()
        signUpNewUser(user)
    }

    private fun createUserWithEnteredData(): QBUser {
        val qbUser = QBUser()

        // val userLogin =   binding.emailET.text.trim().toString()
        val userEmail = loginBinding.emailET.text.trim().toString()
        val userPassword = loginBinding.password.text.trim().toString()

        qbUser.fullName = userEmail

        // qbUser.fullName = loginResponse.data.coach_name
        //  qbUser.fullName = loginResponse.data.coach_name
        //  qbUser.password = userPassword

        qbUser.login = userEmail

        qbUser.password = userEmail
        return qbUser
    }

    private fun signUpNewUser(newUser: QBUser) {
        // loading.hideProgress()
        //loading.showProgress()
        Log.e("move", "4")
        signUp(newUser, object : QBEntityCallback<QBUser> {
            override fun onSuccess(result: QBUser, params: Bundle) {
                loading.hideProgress()
                //   longToast("shivam :"+newUser.id.toString())
                //  showToast("signup Onsuccess")

                if (loginResponse.data.quick_blox_id == null || loginResponse.data.quick_blox_id == "null") {

                    user = newUser
                    val request = StoreQuickbloxBody(
                        coach_id = loginResponse.data.id.toString(),
                        quickBloxId = newUser.id.toString()

                    )
                    loginViewModel.storequickbloxid(request)
                    view?.findNavController()
                        ?.navigate(R.id.action_loginFragment_to_CWorkoutFragment2)
                } else if (newUser.id.toString() == loginResponse.data.quick_blox_id) {
                    SharedPrefsHelper.saveQbUser(newUser)
                    loginToChat(result)


                } else {


                    view?.findNavController()
                        ?.navigate(R.id.action_loginFragment_to_CWorkoutFragment2)


                }


            }

            override fun onError(e: QBResponseException) {
                Log.e("move", "7")
                //   showToast("signup OnError")
                if (e.httpStatusCode == ERROR_LOGIN_ALREADY_TAKEN_HTTP_STATUS) {
                    signInCreatedUser(newUser)
                } else {
                    loading.hideProgress()
                    view?.findNavController()
                        ?.navigate(R.id.action_loginFragment_to_CWorkoutFragment2)

                    // longToast(R.string.sign_up_error)
                }
            }
        })
    }

    private fun signInCreatedUser(user: QBUser) {
        signInUser(user, object : QBEntityCallback<QBUser> {
            override fun onSuccess(result: QBUser, params: Bundle) {
                SharedPrefsHelper.saveQbUser(user)
                updateUserOnServer(user)
                // showToast("login qb")


            }

            override fun onError(responseException: QBResponseException) {
                loading.hideProgress()
                // longToast(R.string.sign_in_error)
            }
        })
    }


    private fun updateUserOnServer(user: QBUser) {

        user.password = null
        // loading.hideProgress()
        QBUsers.updateUser(user).performAsync(object : QBEntityCallback<QBUser> {
            override fun onSuccess(updUser: QBUser?, params: Bundle?) {
                //  loading.showProgress()
                loading.hideProgress()

                //longToast("loginuser ID:"+SharedPrefsHelper.getQbUser().id.toString())
                // showToast("update Onsuccess")
                if (loginResponse.data.quick_blox_id == null || loginResponse.data.quick_blox_id == "null") {


                    this@LoginFragment.user = user
                    //  loading.showProgress()
                    val request = StoreQuickbloxBody(
                        coach_id = loginResponse.data.id.toString(),
                        quickBloxId = SharedPrefsHelper.getQbUser().id.toString()
                    )
                    loginViewModel.storequickbloxid(request)
                    view?.findNavController()
                        ?.navigate(R.id.action_loginFragment_to_CWorkoutFragment2)


                } else if (user.id.toString() == loginResponse.data.quick_blox_id) {
                    view?.findNavController()
                        ?.navigate(R.id.action_loginFragment_to_CWorkoutFragment2)

                } else {
                    //  loading.hideProgress()
                    this@LoginFragment.user = user
                    val request = StoreQuickbloxBody(
                        coach_id = loginResponse.data.id.toString(),
                        quickBloxId = SharedPrefsHelper.getQbUser().id.toString()
                    )
                    loginViewModel.storequickbloxid(request)
                    view?.findNavController()
                        ?.navigate(R.id.action_loginFragment_to_CWorkoutFragment2)
                    /*  Log.e(
                          "shivam",
                          "response ${loginResponse.data.quick_blox_id} newUser${user.id} result${result.id}"
                      )*/
                    // showToast("Something wrong with quickBloxId_signIncreated_user")
                }
                /*    OpponentsActivity.start(this@LoginActivity)
                    finish()
                    if (cb_login.isChecked)
                        sharedPreference.isLogin = true
                    mailto:startactivity(intent(this@loginactivity,homeactivity::class.java))
                    finishAffinity()*/
                //showLoginUpdateDetailsDialog()


                /* LoginBody(
                     loginBinding.emailET.text.toString().trim(),
                     loginBinding.password.text.toString().trim(),
                     deviceToken,
                     deviceId,
                     deviceType
                 )*/

                // viewModel.sendLoginRequest( numberBinding!!.moNumber.text.toString().trim { it <= ' ' }, deviceToken, deviceId, deviceType)
                //  loading.showProgress()

                //  if(isLogin== true)
                //  {


                /*showToast("update")

                if(loginResponse.data.quick_blox_id==null){
                    val request = StoreQuickbloxBody(
                        coach_id = loginResponse.data.id.toString(),
                        quickBloxId = user.id.toString()
                    )
                    loginViewModel.storequickbloxid(request)
                }
                else{
                    showToast("movequickblox function")
                    view?.findNavController()
                        ?.navigate(R.id.action_loginFragment_to_CWorkoutFragment2)

                }*/


                // }


            }

            override fun onError(responseException: QBResponseException?) {
                loading.hideProgress()
                //   longToast(R.string.update_user_error)
            }
        })
    }


    private fun loginToChat(qbUser: QBUser) {

        val userEmail = SessionManager.getEmailForCreate()
        qbUser.password = userEmail
        user = qbUser
        startLoginService(qbUser)
    }

    private fun startLoginService(qbUser: QBUser) {
        val tempIntent = Intent(requireContext(), LoginService::class.java)
        val pendingIntent =
            requireActivity().createPendingResult(EXTRA_LOGIN_RESULT_CODE, tempIntent, 0)
        LoginService.start(requireContext(), qbUser, pendingIntent)
    }


    /*  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
          super.onActivityResult(requestCode, resultCode, data)

      }*/


}