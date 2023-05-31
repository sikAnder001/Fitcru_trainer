package com.ennovations.fitcrucoach.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.ennovations.fitcrucoach.Network.CustomProgressLoading
import com.ennovations.fitcrucoach.Network.NetworkResult
import com.ennovations.fitcrucoach.Network.TokenManager
import com.ennovations.fitcrucoach.R
import com.ennovations.fitcrucoach.adapter.CertificatesAdapter
import com.ennovations.fitcrucoach.body.CertificateBundle
import com.ennovations.fitcrucoach.databinding.FragmentCertificatesBinding
import com.ennovations.fitcrucoach.response.CoachDetailResponse
import com.ennovations.fitcrucoach.response.GetCertificateResponse
import com.ennovations.fitcrucoach.utility.SessionManager
import com.ennovations.fitcrucoach.utility.Util
import com.ennovations.fitcrucoach.view_model.CertificateViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CertificatesFragment : Fragment() {

    private lateinit var certificateBinding: FragmentCertificatesBinding

    private val certificateViewModel by viewModels<CertificateViewModel>()

    private lateinit var loading: CustomProgressLoading

    var newData = ArrayList<GetCertificateResponse.Data>()

    @Inject
    lateinit var tokenManager: TokenManager

    var change = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        certificateBinding = FragmentCertificatesBinding.inflate(inflater, container, false)

        loading = CustomProgressLoading(requireContext())

        getCertificates()

        hittingViews()

/*        certificateBinding.backBtn.setOnClickListener {
            view?.findNavController()
                ?.navigate(R.id.action_certificatesFragment_to_myProfileFragment)
        }*/
        certificateBinding.backBtn.setOnClickListener {
            requireActivity().onBackPressed()
            requireActivity().finish()
        }
        return certificateBinding.root
    }

    private fun getCertificates() {
        var certificatesAdapter =
            CertificatesAdapter(context, object : CertificatesAdapter.CertificatedOnClick {
                override fun onClick(
                    certificate_id: Int,
                    name: String,
                    description: String,
                    image: String,
                    imageUrl: String
                ) {

                    change = 2

                    val bundle = Bundle()
                    bundle.putSerializable(
                        "data",
                        CertificateBundle(
                            change,
                            certificate_id,
                            name,
                            description,
                            image,
                            imageUrl
                        )
                    )

                    view?.findNavController()
                        ?.navigate(
                            R.id.action_certificatesFragment_to_addCertificateFragment,
                            bundle
                        )

                }
            })
        certificateBinding!!.showCertificatesRv.adapter = certificatesAdapter

        certificateViewModel.getCertificateDetail(SessionManager.getInt("coach_id"))

        certificateViewModel.certificateResponse.observe(viewLifecycleOwner) {
            //hide progress
            loading.hideProgress()
            when (it) {
                is NetworkResult.Success -> {
                    val response = it.data as GetCertificateResponse
                    newData = ArrayList()

                    for (i in response.data) {
                        if (i.status == "Approved") {
                            newData.add(i)
                        } else {
                            continue
                        }
                    }
                    /* if (response.data == null) {
                         certificateBinding.toastTv.visibility = View.VISIBLE
                     } else {
                         certificateBinding.toastTv.visibility = View.GONE
                     }*/

                    certificatesAdapter.setList(response.data)
                }
                is NetworkResult.Error -> {
                    val response = Util.error(it.data, CoachDetailResponse::class.java)
                    certificateViewModel.codeSend.observe(viewLifecycleOwner) {
                        val codeGet = it
                        when (it) {
                            404 -> certificateBinding.toastTv.visibility = View.VISIBLE
                            else -> Toast.makeText(context, response.message, Toast.LENGTH_LONG)
                                .show()
                        }
                    }

                }
                is NetworkResult.Loading -> {
                    //show progress
                    loading.showProgress()
                }
            }
        }
    }


    private fun hittingViews() {

        certificateBinding.apply {

            addCertificateBtn.setOnClickListener {

                change = 1


                val bundle = Bundle()
                bundle.putSerializable("data", CertificateBundle(change, 0, "", "", "", ""))

                view?.findNavController()
                    ?.navigate(R.id.action_certificatesFragment_to_addCertificateFragment, bundle)

            }

        }

    }

}