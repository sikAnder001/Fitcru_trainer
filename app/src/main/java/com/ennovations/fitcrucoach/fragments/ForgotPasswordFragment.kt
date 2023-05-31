package com.ennovations.fitcrucoach.fragments

import android.os.Bundle
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
import com.ennovations.fitcrucoach.body.ForgotPasswordBody
import com.ennovations.fitcrucoach.databinding.FragmentForgotPasswordBinding
import com.ennovations.fitcrucoach.response.ForgotPasswordResponse
import com.ennovations.fitcrucoach.utility.Util
import com.ennovations.fitcrucoach.view_model.ForgotPasswordViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ForgotPasswordFragment : Fragment() {

    private lateinit var forgotBinding: FragmentForgotPasswordBinding

    private var TAG = ForgotPasswordFragment::class.java.simpleName

    private lateinit var loading: CustomProgressLoading

    private val forgotViewModel by viewModels<ForgotPasswordViewModel>()

    @Inject
    lateinit var tokenManager: TokenManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        forgotBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_forgot_password, container, false)

        loading = CustomProgressLoading(requireContext())

        hittingViews()

        return forgotBinding.root
    }

    private fun hittingViews() {
        forgotBinding.apply {

            backBtn.setOnClickListener {
                view?.findNavController()
                    ?.navigate(R.id.action_forgotPasswordFragment_to_loginFragment)
            }

            proceedBtn.setOnClickListener {

                val validation =
                    forgotViewModel.forgotPasswordValidation(email.text.toString().trim())

                if (validation.first) {
                    forgotViewModel.forgotPassword(ForgotPasswordBody(email.text.toString().trim()))
                } else Util.toast(requireContext(), validation.second)
            }

            observer()

        }
    }

    private fun observer() {

        forgotViewModel.forgotResponse.observe(viewLifecycleOwner) {

            loading.hideProgress()

            when (it) {
                is NetworkResult.Success -> {
                    val response = it.data as ForgotPasswordResponse

                    tokenManager.saveToken(response.data.access_token)

                    val bundle = Bundle()
                    bundle.putString("email", response.data.coach_email)

                    view?.findNavController()
                        ?.navigate(
                            R.id.action_forgotPasswordFragment_to_OTPVerificationFragment,
                            bundle
                        )

//                    Toast.makeText(requireContext(),response.data.email_verification_code,Toast.LENGTH_LONG).show()

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

}
