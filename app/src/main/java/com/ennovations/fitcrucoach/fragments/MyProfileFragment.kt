package com.ennovations.fitcrucoach.fragments

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.ennovations.fitcrucoach.Network.CustomProgressLoading
import com.ennovations.fitcrucoach.Network.NetworkResult
import com.ennovations.fitcrucoach.Network.TokenManager
import com.ennovations.fitcrucoach.R
import com.ennovations.fitcrucoach.databinding.FragmentMyProfileBinding
import com.ennovations.fitcrucoach.quickbox.utils.SharedPrefsHelper
import com.ennovations.fitcrucoach.response.ChangePasswordResponse
import com.ennovations.fitcrucoach.response.CoachProfileResponse
import com.ennovations.fitcrucoach.utility.SessionManager
import com.ennovations.fitcrucoach.utility.Util
import com.ennovations.fitcrucoach.view_model.GetCoachProfileViewModel
import com.orhanobut.hawk.Hawk
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MyProfileFragment : Fragment() {

    private lateinit var myProfileBinding: FragmentMyProfileBinding

    private var TAG = MyProfileFragment::class.java.simpleName
    // lateinit var sharedPreference: SharedPreferenceUtil


    private lateinit var loading: CustomProgressLoading
    // private  var isLogout = sharedPreference.isLogout_manage

    private val coachProfileViewModel by viewModels<GetCoachProfileViewModel>()

    @Inject
    lateinit var tokenManager: TokenManager

    private var which = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        myProfileBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_my_profile, container, false)

        loading = CustomProgressLoading(requireContext())

        myProfileBinding.backBtn.setOnClickListener {
            requireActivity().onBackPressed()
        }

        callNextFrag()

        coachProfileViewModel.getCoachDetails()

        getDetails()

        deactivateAccount()

        logout()

        return myProfileBinding.root
    }


    private fun callNextFrag() {

        myProfileBinding.apply {
            myCertificationsContainer.setOnClickListener {

                view?.findNavController()
                    ?.navigate(R.id.action_myProfileFragment_to_certificatesFragment)

            }

            profileContainer.setOnClickListener {

                view?.findNavController()
                    ?.navigate(R.id.action_myProfileFragment_to_editProfileFragment)

            }

            locationContainer.setOnClickListener {

                view?.findNavController()
                    ?.navigate(R.id.action_myProfileFragment_to_EPLocationFragment)
            }

            termsContainer.setOnClickListener {

                which = 1
                val b = Bundle()
                b.putInt("w", which)
                view?.findNavController()
                    ?.navigate(R.id.action_myProfileFragment_to_termsAndPrivacyFragment, b)

            }

            privacyContainer.setOnClickListener {

                which = 2
                val b = Bundle()
                b.putInt("w", which)
                view?.findNavController()
                    ?.navigate(R.id.action_myProfileFragment_to_termsAndPrivacyFragment, b)

            }

            helpSupportContainer.setOnClickListener { Util.openWhatsapp(requireContext(), "Hello") }

        }
    }

    private fun getDetails() {

        coachProfileViewModel.getCoachDetailsResponse.observe(viewLifecycleOwner) {

            loading.hideProgress()

            when (it) {
                is NetworkResult.Success -> {
                    val coachProfileResponse = it.data as CoachProfileResponse

                    val data = coachProfileResponse.data

                    myProfileBinding.apply {

                        personName.text = data.coach_name

                        Picasso.get().load(data.image_url)
                            .placeholder(
                                requireContext()!!.resources.getDrawable(
                                    R.drawable.upload_img,
                                    null
                                )
                            )
                            .into(personImage)
                    }

                }
                is NetworkResult.Error -> {

                    val coachProfileResponse =
                        Util.error(it.data, CoachProfileResponse::class.java)

                    Toast.makeText(
                        requireContext(),
                        coachProfileResponse.message,
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

    private fun logout() {

        myProfileBinding.apply {

            logoutContainer.setOnClickListener {
                val dialogBuilder =
                    AlertDialog.Builder(requireActivity(), R.style.AlertDialogStyle1)

                dialogBuilder.setMessage("Do you want to logout?       ")
                    .setCancelable(false)
                    .setPositiveButton("Yes", DialogInterface.OnClickListener { _, _ ->


                        coachProfileViewModel.logout()


                    })
                    .setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, _ ->
                        dialog.cancel()
                    })

                val alert = dialogBuilder.create()
                alert.setTitle("Logout")
                alert.show()
            }

        }

        coachProfileViewModel.logout.observe(viewLifecycleOwner) {

            loading.hideProgress()

            when (it) {
                is NetworkResult.Success -> {
                    val logout = it.data as ChangePasswordResponse

                    // if(isLogout==false){
                    SessionManager.destroySession()
                    Hawk.deleteAll()
                    SharedPrefsHelper.clearAllData()
                    view?.findNavController()
                        ?.navigate(R.id.action_myProfileFragment_to_loginFragment)


                    //  }


                }
                is NetworkResult.Error -> {

                    val logout =
                        Util.error(it.data, ChangePasswordResponse::class.java)

                    Toast.makeText(
                        requireContext(),
                        logout.message,
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


    private fun deactivateAccount() {

        myProfileBinding.apply {

            deactivateContainer.setOnClickListener {
                val dialogBuilder =
                    AlertDialog.Builder(requireActivity(), R.style.AlertDialogStyle1)

                dialogBuilder.setMessage("Do you want to deactivate your account?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", DialogInterface.OnClickListener { _, _ ->
                        loading.showProgress()
                        coachProfileViewModel.deactivateAccount()

                    })
                    .setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, _ ->
                        dialog.cancel()
                    })

                val alert = dialogBuilder.create()
                alert.setTitle("Deactivate Account")
                alert.show()
            }

        }

        coachProfileViewModel.deactivateAccount.observe(viewLifecycleOwner) {

            loading.hideProgress()

            when (it) {
                is NetworkResult.Success -> {
                    val deactivate = it.data as ChangePasswordResponse

                    SessionManager.destroySession()
                    view?.findNavController()
                        ?.navigate(R.id.action_myProfileFragment_to_loginFragment)

                }
                is NetworkResult.Error -> {

                    val logout =
                        Util.error(it.data, ChangePasswordResponse::class.java)

                    Toast.makeText(
                        requireContext(),
                        logout.message,
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


    /* private fun logout() {

         SharedPreferenceUtil.getInstance(this).isLogin = false
         val intent: Intent = Intent(this, MainActivity::class.java)
             .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
         intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
         intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
         startActivity(intent)
     }*/


}
