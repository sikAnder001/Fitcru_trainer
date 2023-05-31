package com.ennovations.fitcrucoach.fragments

import android.os.Bundle
import android.os.CountDownTimer
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.ennovations.fitcrucoach.Network.CustomProgressLoading
import com.ennovations.fitcrucoach.Network.NetworkResult
import com.ennovations.fitcrucoach.Network.TokenManager
import com.ennovations.fitcrucoach.R
import com.ennovations.fitcrucoach.body.OTPVerificationBody
import com.ennovations.fitcrucoach.databinding.FragmentOTPVerificationBinding
import com.ennovations.fitcrucoach.response.ForgotPasswordResponse
import com.ennovations.fitcrucoach.utility.Util
import com.ennovations.fitcrucoach.view_model.OTPValidationViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.text.DecimalFormat
import java.text.NumberFormat
import javax.inject.Inject


@AndroidEntryPoint
class OTPVerificationFragment : Fragment() {

    private lateinit var otpBinding: FragmentOTPVerificationBinding

    private var TAG = OTPVerificationFragment::class.java.simpleName

    private val otpViewModel by viewModels<OTPValidationViewModel>()

    private lateinit var loading: CustomProgressLoading

    @Inject
    lateinit var tokenManager: TokenManager

    private var change = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        otpBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_o_t_p_verification,
            container,
            false
        )

        loading = CustomProgressLoading(requireContext())

        hittingViews()

        return otpBinding.root

    }

    private fun hittingViews() {

        val bundle = arguments

        var coachEmail = bundle!!.getString("email")

        otpBinding.apply {

            backBtn.setOnClickListener {
                view?.findNavController()
                    ?.navigate(R.id.action_OTPVerificationFragment_to_forgotPasswordFragment)
            }

//            coachEmailTv.text = coachEmail.toString()
            val styledText =
                "Enter OTP sent to <font color='#ffffff'><b>${coachEmail.toString()}</b></font> to proceed"
            coachEmailTv.text = Html.fromHtml(styledText)

            proceedBtn.setOnClickListener {

                change = 1

                val validation = otpViewModel.otpValidation(otp.text.toString().trim())

                if (validation.first) {

                    otpViewModel.otpVerification(OTPVerificationBody(otp.text.toString().trim()))

                } else Util.toast(requireContext(), validation.second)
            }

            resendOtp.setOnClickListener {

                change = 2

            }
            observer()
        }
    }

    private fun observer() {

        otpViewModel.otpVerification.observe(viewLifecycleOwner) {

            loading.hideProgress()

            when (it) {
                is NetworkResult.Success -> {

                    val response = it.data as ForgotPasswordResponse

                    tokenManager.saveToken(response.data.access_token)

                    view?.findNavController()
                        ?.navigate(R.id.action_OTPVerificationFragment_to_createPasswordFragment)

                    Util.toast(requireContext(), response.message)

                }

                is NetworkResult.Error -> {

                    val response = Util.error(it.data, ForgotPasswordResponse::class.java)

                    Toast.makeText(requireContext(), response.message, Toast.LENGTH_LONG)
                        .show()

                }

                is NetworkResult.Loading -> {
                    loading.showProgress()
                }

            }

        }
    }

    private fun timerCountDown() {
        object : CountDownTimer(120000, 1000) {
            override fun onTick(millisUntilFinished: Long) {

                otpBinding.resendOtp.isEnabled = false

                otpBinding.timer.visibility = View.VISIBLE

                val f: NumberFormat = DecimalFormat("00")

                val hour = millisUntilFinished / 3600000 % 24

                val min = millisUntilFinished / 60000 % 60

                val sec = millisUntilFinished / 1000 % 60

                otpBinding.timer.setText(
                    f.format(min) + ":" + f.format(sec)
                )
            }

            override fun onFinish() {
                otpBinding.timer.text = "Time's finished!"

                otpBinding.timer.visibility = View.GONE

                otpBinding.resendOtp.isEnabled = true
            }
        }.start()
    }
}