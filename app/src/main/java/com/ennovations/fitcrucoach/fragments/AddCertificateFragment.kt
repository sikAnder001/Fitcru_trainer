package com.ennovations.fitcrucoach.fragments


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.ennovations.fitcrucoach.Network.CustomProgressLoading
import com.ennovations.fitcrucoach.Network.NetworkResult
import com.ennovations.fitcrucoach.Network.TokenManager
import com.ennovations.fitcrucoach.R
import com.ennovations.fitcrucoach.body.CertificateBundle
import com.ennovations.fitcrucoach.databinding.FragmentAddCertificateBinding
import com.ennovations.fitcrucoach.response.CoachDetailResponse
import com.ennovations.fitcrucoach.utility.Util
import com.ennovations.fitcrucoach.view_model.CertificateViewModel
import com.github.dhaval2404.imagepicker.ImagePicker
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

@AndroidEntryPoint
class AddCertificateFragment : Fragment() {

    private lateinit var addCertificateBinding: FragmentAddCertificateBinding

    private var TAG = AddCertificateFragment::class.java.simpleName

    private var uri: Uri? = null
    private var filePath: String? = null

    private val certificateViewModel by viewModels<CertificateViewModel>()

    private lateinit var loading: CustomProgressLoading

    // hi
    @Inject
    lateinit var tokenManager: TokenManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        addCertificateBinding = FragmentAddCertificateBinding.inflate(inflater, container, false)

        loading = CustomProgressLoading(requireContext())

        addCertificateBinding.backBtn.setOnClickListener {
            requireActivity().onBackPressed()
            requireActivity().finish()
        }

        hittingViews()

        addCertificateBinding!!.imagePickBtn.setOnClickListener {
            ImagePicker.with(this)
                .crop()
                .compress(3072)
                .maxResultSize(1080, 1080)
                .start()
        }

        addCertificateBinding.backBtn.setOnClickListener {
            requireActivity().onBackPressed()
        }
        return addCertificateBinding.root
    }

    private fun addCertificate() {
        addCertificateBinding.addBtn.setOnClickListener {
            if (validationCheck()) {
                if (filePath == null) {
                    Util.toast(requireContext(), "Please select image")
                } else {
                    addCertificateBinding?.apply {

                        val builder = MultipartBody.Builder()
                        builder.setType(MultipartBody.FORM)
                        builder.addFormDataPart(
                            "image",
                            File(filePath).name,
                            File(filePath).asRequestBody("multipart/form-data".toMediaTypeOrNull())
                        )

                        builder.addFormDataPart(
                            "certificate_name",
                            certificateNames.text.toString().trim()
                        )
                        builder.addFormDataPart(
                            "description",
                            certificateDetail.text.toString().trim()
                        )

                        certificateViewModel.addCertificateDetail(builder.build())

                        certificateViewModel.certificateResponse.observe(viewLifecycleOwner) {
                            //hide progress
                            loading.hideProgress()
                            when (it) {
                                is NetworkResult.Success -> {
                                    val response = it.data as CoachDetailResponse
                                    Toast.makeText(context, response.message, Toast.LENGTH_LONG)
                                        .show()

                                    requireActivity().onBackPressed()
                                    requireActivity().finish()
                                }
                                is NetworkResult.Error -> {
                                    val response =
                                        Util.error(it.data, CoachDetailResponse::class.java)
                                    Toast.makeText(context, response.message, Toast.LENGTH_LONG)
                                        .show()
                                }
                                is NetworkResult.Loading -> {
                                    //show progress
                                    loading.showProgress()
                                }
                            }
                        }
                    }
                }


            }
        }
    }

    private fun validationCheck(): Boolean {
        val certificateName = addCertificateBinding!!.certificateNames.text.toString().trim()
        val description = addCertificateBinding!!.certificateDetail.text.toString().trim()

        if (certificateName.isEmpty()) {
            addCertificateBinding!!.certificateNames.error =
                resources.getString(R.string.please_enter_certificate_name)
            return false
        } else if (description.isEmpty()) {
            addCertificateBinding!!.certificateDetail.error =
                resources.getString(R.string.please_enter_description)
            return false
        }
        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        try {
            uri = data?.data!!
            // addCertificateBinding!!.setIMG.setImageURI(uri)
            filePath = data?.getStringExtra("extra.file_path")
            Glide.with(requireContext()).load(uri).into(addCertificateBinding.setIMG)

        } catch (e: Exception) {
        }
    }

    private fun hittingViews() {
        val bundle = arguments
        var data = bundle!!.getSerializable("data") as CertificateBundle

        Log.d(TAG, "onCreateView: ${data.change}")

        if (data.change == 1) {
            addCertificateBinding.apply {
                addBtn.setText("Add Certificate ")
                addCertificate()
            }
        } else if (data.change == 2) {
            addCertificateBinding.apply {
                addBtn.setText("Update")
                Glide.with(requireContext()).load("${data.image_url}")
                    .placeholder(R.drawable.place_holder)
                    .into(setIMG)
                (certificateNames as TextView).text = data.name
                (certificateDetail as TextView).text = data.description
            }
            updateCertificate(data.id)
        } else {
        }
    }

    private fun updateCertificate(id: Int) {
        addCertificateBinding.addBtn.setOnClickListener {
            if (validationCheck()) {
                addCertificateBinding?.apply {

                    val builder = MultipartBody.Builder()
                    builder.setType(MultipartBody.FORM)
                    if (filePath != null) {
                        builder.addFormDataPart(
                            "image",
                            File(filePath).name,
                            File(filePath).asRequestBody("multipart/form-data".toMediaTypeOrNull())
                        )
                    }
                    builder.addFormDataPart("certificate_id", id.toString().trim())
                    builder.addFormDataPart(
                        "certificate_name",
                        certificateNames.text.toString().trim()
                    )
                    builder.addFormDataPart("description", certificateDetail.text.toString().trim())

                    certificateViewModel.updateCertificateDetail(builder.build())

                    certificateViewModel.certificateResponse.observe(viewLifecycleOwner) {
                        //hide progress
                        loading.hideProgress()
                        when (it) {
                            is NetworkResult.Success -> {
                                val response = it.data as CoachDetailResponse
                                Toast.makeText(context, response.message, Toast.LENGTH_LONG).show()

                                view?.findNavController()
                                    ?.navigate(
                                        R.id.action_addCertificateFragment_to_certificatesFragment
                                    )
                            }
                            is NetworkResult.Error -> {
                                val response = Util.error(it.data, CoachDetailResponse::class.java)
                                Toast.makeText(context, response.message, Toast.LENGTH_LONG).show()
                            }
                            is NetworkResult.Loading -> {
                                //show progress
                                loading.showProgress()
                            }
                        }
                    }
                }
            }
        }
    }//commence
}