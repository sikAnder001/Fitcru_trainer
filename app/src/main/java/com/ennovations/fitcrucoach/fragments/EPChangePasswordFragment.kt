package com.ennovations.fitcrucoach.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.ennovations.fitcrucoach.Network.CustomProgressLoading
import com.ennovations.fitcrucoach.Network.NetworkResult
import com.ennovations.fitcrucoach.Network.TokenManager
import com.ennovations.fitcrucoach.R
import com.ennovations.fitcrucoach.body.ChangePasswordBody
import com.ennovations.fitcrucoach.databinding.FragmentEPChangePasswordBinding
import com.ennovations.fitcrucoach.response.ChangePasswordResponse
import com.ennovations.fitcrucoach.utility.Util
import com.ennovations.fitcrucoach.view_model.ChangePasswordViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class EPChangePasswordFragment : Fragment() {

    private lateinit var changePasswordBinding: FragmentEPChangePasswordBinding

    private var TAG = EPChangePasswordFragment::class.java.simpleName

    private lateinit var loading: CustomProgressLoading

    private val changePasswordViewModel by viewModels<ChangePasswordViewModel>()

    @Inject
    lateinit var tokenManager: TokenManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        changePasswordBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_e_p_change_password,
            container,
            false
        )

        loading = CustomProgressLoading(requireContext())

        hittingViews()

        return changePasswordBinding.root
    }

    private fun hittingViews() {

        changePasswordBinding.apply {

            updatePassBtn.setOnClickListener {

                val passwordValidation = changePasswordViewModel.validatePassword(
                    oldPassword.text.toString().trim(),
                    newPassword.text.toString().trim(),
                    confNewPassword.text.toString().trim()
                )

                if (passwordValidation.first) {
                    changePasswordViewModel.changePassword(
                        ChangePasswordBody(
                            oldPassword.text.toString().trim(),
                            newPassword.text.toString().trim(),
                            confNewPassword.text.toString().trim()
                        )
                    )

                } else Util.toast(requireContext(), passwordValidation.second)

            }

            changePasswordViewModel.changePasswordResponse.observe(viewLifecycleOwner) {

                loading.hideProgress()

                when (it) {
                    is NetworkResult.Success -> {
                        val createPasswordResponse = it.data as ChangePasswordResponse

                        Toast.makeText(
                            requireContext(),
                            createPasswordResponse.message,
                            Toast.LENGTH_LONG
                        )
                            .show()

                        /*view?.findNavController()
                            ?.navigate(R.id.action_editProfileFragment_to_loginFragment)*/

                    }
                    is NetworkResult.Error -> {

                        val createPasswordResponse =
                            Util.error(it.data, ChangePasswordResponse::class.java)

                        Toast.makeText(
                            requireContext(),
                            createPasswordResponse.message,
                            Toast.LENGTH_LONG
                        )
                            .show()
                    }
                    is NetworkResult.Loading -> {
                        loading.showProgress()
                    }
                }
            }

        }

    }
}