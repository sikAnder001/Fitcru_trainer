package com.ennovations.fitcrucoach.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.ennovations.fitcrucoach.Network.CustomProgressLoading
import com.ennovations.fitcrucoach.Network.NetworkResult
import com.ennovations.fitcrucoach.Network.TokenManager
import com.ennovations.fitcrucoach.R
import com.ennovations.fitcrucoach.databinding.FragmentEPDocVerificationBinding
import com.ennovations.fitcrucoach.response.UpdateCoachVerificationResponse
import com.ennovations.fitcrucoach.response.VerificationDetailsResponse
import com.ennovations.fitcrucoach.utility.Util
import com.ennovations.fitcrucoach.view_model.CoachVerificationViewModel
import com.github.dhaval2404.imagepicker.ImagePicker
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject


@AndroidEntryPoint
class EPDocVerificationFragment : Fragment() {

    private lateinit var verificationBinding: FragmentEPDocVerificationBinding

    private lateinit var loading: CustomProgressLoading

    private var uri: Uri? = null

    private var filePath: String? = null
    private var filePath2: String? = null

    private var which = 0

    private var TAG = EPDocVerificationFragment::class.java.simpleName

    private val updateVerificationViewModel by viewModels<CoachVerificationViewModel>()

    @Inject
    lateinit var tokenManager: TokenManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        verificationBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_e_p_doc_verification,
            container,
            false
        )

        loading = CustomProgressLoading(requireContext())

        getDetails()

        verificationBinding.imagePickBtn.setOnClickListener {
            which = 1
            ImagePicker.with(this)
                .crop()
                .compress(3072)
                .maxResultSize(1080, 1080)
                .start()
        }
        verificationBinding.docPickBtn.setOnClickListener {
            which = 2
            ImagePicker.with(this)
                .crop()
                .compress(3072)
                .maxResultSize(1080, 1080)
                .start()
        }

        updateDetails()

        return verificationBinding.root
    }

    private fun updateDetails() {
        verificationBinding.apply {

            saveBtn.setOnClickListener {

                val cardNumber = cardsEt.text.toString().trim()

                val link = linkET.text.toString().trim()

//                val slots = slotsEt.text.toString().trim()

                val validation =
                    updateVerificationViewModel.validate(cardNumber, link/*, slots*/)

                if (validation.first) {

//                    val builder = MultipartBody.Builder()

                    var image1: MultipartBody.Part? = null
                    if (filePath != null) {
                        val file = File(filePath)
                        val requestBody = file.asRequestBody("image/*".toMediaTypeOrNull())
                        image1 = MultipartBody.Part.createFormData(
                            "adhar_card_front_image",
                            (file).name,
                            requestBody
                        )
                    }

                    var image2: MultipartBody.Part? = null
                    if (filePath2 != null) {
                        val file2 = File(filePath2)
                        val requestBody2 = file2.asRequestBody("image/*".toMediaTypeOrNull())
                        image2 = MultipartBody.Part.createFormData(
                            "adhar_card_back_image",
                            (file2).name,
                            requestBody2
                        )
                    }

//                    builder.setType(MultipartBody.FORM)
//                    if (filePath != null) {
//                        Log.v("front image",File(filePath).name.toString())
//                        builder.addFormDataPart(
//                            "adhar_card_front_image",
//                            File(filePath).name,
//                            File(filePath).asRequestBody("multipart/form-data".toMediaTypeOrNull())
//                        )
//                    }

//                    if (filePath2 != null) {
//                        Log.v("back image",File(filePath2).name.toString())
//                        builder.addFormDataPart(
//                            "adhar_card_back_image",
//                            File(filePath2).name,
//                            File(filePath2).asRequestBody("multipart/form-data".toMediaTypeOrNull())
//                        )
//                    }

                    val requestBodyCN =
                        RequestBody.create("text/plain".toMediaTypeOrNull(), cardNumber)
                    val requestBodyLink =
                        RequestBody.create("text/plain".toMediaTypeOrNull(), link)
                    /* val requestBodySlots =
                         RequestBody.create("text/plain".toMediaTypeOrNull(), slots);*/

//                    builder.addFormDataPart("adhar_card_no", cardNumber)
//
//                    builder.addFormDataPart("coach_instagram_link", link)
//
//                    builder.addFormDataPart("no_of_slots", slots)

                    updateVerificationViewModel.updateCoachVerification(
                        image1,
                        image2,
                        requestBodyCN,
                        requestBodyLink
                        /*requestBodySlots*/
                    )
                } else Util.toast(requireContext(), validation.second)
            }

            updateVerificationViewModel.verificationUpdateResponse.observe(viewLifecycleOwner) {

                when (it) {
                    is NetworkResult.Success -> {

                        val response = it.data!! as UpdateCoachVerificationResponse

                        Util.toast(requireContext(), response.message)
                    }
                    is NetworkResult.Error -> {

                        val response =
                            Util.error(it.data!!, UpdateCoachVerificationResponse::class.java)

                        Toast.makeText(
                            requireContext(),
                            response.message,
                            Toast.LENGTH_LONG
                        )
                            .show()
                    }
                    is NetworkResult.Loading -> {
                    }
                }
            }
        }
    }

    /*  override fun onResume() {
          super.onResume()

          try {

              uri = data!!.data
              if (which == 1) {
                  filePath = data.getStringExtra("extra.file_path")
                  verificationBinding.setIMG.setImageURI(uri)
              } else if (which == 2) {
                  filePath2 = data.getStringExtra("extra.file_path")
                  verificationBinding.setDOC.setImageURI(uri)
              }
          } catch (e: Exception) {
          }

      }*/

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        super.onActivityResult(requestCode, resultCode, data)

        try {
            uri = data!!.data
            if (which == 1) {
                filePath = data.getStringExtra("extra.file_path")
                // verificationBinding.setIMG.setImageURI(uri)
                Glide.with(requireContext()).load(uri).into(verificationBinding.setIMG)
            } else if (which == 2) {
                filePath2 = data.getStringExtra("extra.file_path")
                verificationBinding.setDOC.setImageURI(uri)
            }
        } catch (e: Exception) {

        }
    }

    private fun getDetails() {

        updateVerificationViewModel.getVerificationDetails()

        updateVerificationViewModel.getVerificationDetails.observe(viewLifecycleOwner) {

            loading.hideProgress()

            when (it) {
                is NetworkResult.Success -> {
                    val response = it.data!! as VerificationDetailsResponse

                    verificationBinding.apply {

                        (cardsEt as TextView).text = response.Verifications.adhar_card_no

                        (linkET as TextView).text = response.Verifications.coach_instagram_link

                        /* (slotsEt as TextView).text = response.Verifications.no_of_slots*/

                        Glide.with(requireContext())
                            .load(response.Verifications.adhar_card_front_image)
                            .placeholder(R.drawable.place_holder)
                            .into(setIMG)

                        Glide.with(requireContext())
                            .load(response.Verifications.adhar_card_back_image)
                            .placeholder(R.drawable.place_holder)
                            .into(setDOC)
                    }

                }
                is NetworkResult.Error -> {

                    val response =
                        Util.error(it.data!!, VerificationDetailsResponse::class.java)

                    Toast.makeText(
                        requireContext(),
                        response.message,
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
