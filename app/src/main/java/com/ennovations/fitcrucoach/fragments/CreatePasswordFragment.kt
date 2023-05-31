package com.ennovations.fitcrucoach.fragments

import android.os.Bundle
import android.text.InputType
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.ennovations.fitcrucoach.Network.CustomProgressLoading
import com.ennovations.fitcrucoach.Network.NetworkResult
import com.ennovations.fitcrucoach.Network.TokenManager
import com.ennovations.fitcrucoach.R
import com.ennovations.fitcrucoach.body.CreatePasswordBody
import com.ennovations.fitcrucoach.databinding.FragmentCreatePasswordBinding
import com.ennovations.fitcrucoach.response.ForgotPasswordResponse
import com.ennovations.fitcrucoach.utility.SessionManager
import com.ennovations.fitcrucoach.utility.Util
import com.ennovations.fitcrucoach.view_model.CreatePasswordViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CreatePasswordFragment : Fragment() {

    private lateinit var createBinding: FragmentCreatePasswordBinding

    private var TAG = CreatePasswordFragment::class.java.simpleName

    private var isPasswordVisible = false

    private var isRetypePasswordVisible = false

    private lateinit var loading: CustomProgressLoading

    private val createPassViewModel by viewModels<CreatePasswordViewModel>()

    @Inject
    lateinit var tokenManager: TokenManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        createBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_create_password, container, false)

        loading = CustomProgressLoading(requireContext())

        hittingViews()

        return createBinding.root
    }

    private fun hittingViews() {

        createBinding.apply {

            val email = SessionManager.getCoachDetails()

            val bundle = Bundle()
            bundle.putString("email", email.coach_email)

            backBtn.setOnClickListener {
                view?.findNavController()
                    ?.navigate(
                        R.id.action_createPasswordFragment_to_OTPVerificationFragment,
                        bundle
                    )
            }

            passVisibility.setOnClickListener { togglePassVisibility() }

            reEnterpassVisibility.setOnClickListener { toggleReTypePassVisibility() }

            proceedBtn.setOnClickListener {

                val validation = createPassViewModel.passCodeValidation(
                    password.text.toString().trim(),
                    newPassword.text.toString().trim()
                )

                if (validation.first) {

                    createPassViewModel.createPassword(
                        CreatePasswordBody(
                            password.text.toString().trim(),
                            newPassword.text.toString().trim()
                        )
                    )

                } else Util.toast(requireContext(), validation.second)
            }

            observer()
        }
    }

    private fun observer() {

        createPassViewModel.response.observe(viewLifecycleOwner) {

            loading.hideProgress()

            when (it) {

                is NetworkResult.Success -> {

                    val response = it.data as ForgotPasswordResponse

                    view?.findNavController()?.apply {
                        navigate(R.id.action_createPasswordFragment_to_loginFragment)
                    }

                    Util.toast(requireContext(), response.message)

                }

                is NetworkResult.Error -> {

                    val response = it.data as ForgotPasswordResponse

                    Util.toast(requireContext(), response.message)
                }

                is NetworkResult.Loading -> {

                    loading.showProgress()

                }
            }

        }

    }

    private fun togglePassVisibility() {
        if (isPasswordVisible) {
            val pass = createBinding!!.password.text.toString()
            createBinding!!.password.transformationMethod =
                PasswordTransformationMethod.getInstance()
            createBinding!!.password.inputType =
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            createBinding!!.password.setText(pass)
            createBinding!!.password.setSelection(pass.length)
            createBinding!!.passVisibility.setImageDrawable(resources.getDrawable(R.drawable.ic_baseline_visibility_24))
        } else {
            val pass = createBinding!!.password.text.toString()
            createBinding!!.password.transformationMethod =
                HideReturnsTransformationMethod.getInstance()
            createBinding!!.password.inputType = InputType.TYPE_CLASS_TEXT
            createBinding!!.password.setText(pass)
            createBinding!!.password.setSelection(pass.length)
            createBinding!!.passVisibility.setImageDrawable(resources.getDrawable(R.drawable.ic_baseline_visibility_off_24))
        }
        isPasswordVisible = !isPasswordVisible
    }

    private fun toggleReTypePassVisibility() {
        if (isRetypePasswordVisible) {

            val pass = createBinding.newPassword.text.toString()

            createBinding.newPassword.transformationMethod =
                PasswordTransformationMethod.getInstance()

            createBinding!!.newPassword.inputType =
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD

            createBinding!!.apply {
                newPassword.setText(pass)
                newPassword.setSelection(pass.length)
                reEnterpassVisibility.setImageDrawable(resources.getDrawable(R.drawable.ic_baseline_visibility_24))
            }
        } else {
            val pass = createBinding.newPassword.text.toString()
            createBinding.newPassword.transformationMethod =
                HideReturnsTransformationMethod.getInstance()
            createBinding.apply {

                newPassword.inputType = InputType.TYPE_CLASS_TEXT

                newPassword.setText(pass)

                newPassword.setSelection(pass.length)

                reEnterpassVisibility.setImageDrawable(resources.getDrawable(R.drawable.ic_baseline_visibility_off_24))
            }
        }
        isRetypePasswordVisible = !isRetypePasswordVisible
    }

}