package com.ennovations.fitcrucoach.fragments

import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.ennovations.fitcrucoach.Network.CustomProgressLoading
import com.ennovations.fitcrucoach.Network.NetworkResult
import com.ennovations.fitcrucoach.Network.TokenManager
import com.ennovations.fitcrucoach.R
import com.ennovations.fitcrucoach.databinding.FragmentEPBasicBinding
import com.ennovations.fitcrucoach.response.CoachProfileResponse
import com.ennovations.fitcrucoach.utility.CalendarUtils
import com.ennovations.fitcrucoach.utility.Util
import com.ennovations.fitcrucoach.view_model.GetCoachProfileViewModel
import com.github.dhaval2404.imagepicker.ImagePicker
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.text.ParseException
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class EPBasicFragment : Fragment() {

    private lateinit var basicBinding: FragmentEPBasicBinding

    private lateinit var loading: CustomProgressLoading

    private var uri: Uri? = null

    private var filePath: String? = null

    private var TAG = EPBasicFragment::class.java.simpleName

    private val coachProfileViewModel by viewModels<GetCoachProfileViewModel>()

    @Inject
    lateinit var tokenManager: TokenManager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loading = CustomProgressLoading(requireContext())

        getDetails()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        basicBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_e_p_basic, container, false)

        openCalendar()

        openImagePicker()

        coachProfileViewModel.getCoachDetails()

        updateDetails()

        return basicBinding.root
    }

    private fun getDetails() {

        coachProfileViewModel.getCoachDetailsResponse.observe(viewLifecycleOwner) {

            loading.hideProgress()

            when (it) {
                is NetworkResult.Success -> {
                    val coachProfileResponse = it.data as CoachProfileResponse

                    val data = coachProfileResponse.data

                    basicBinding.apply {

                        unameET.setText(data.coach_name)

                        mobileET.setText(data.phone_number)

                        emailET.setText(data.coach_email)

                        bioDataET.setText(data.coach_biodata)

                        if (data.dob != null) {
                            tvDate.text = CalendarUtils.dateFormatFit(data.dob)
                        } else {
                            tvDate.text = ""
                        }

                        Picasso.get().load(data.image_url)
                            .placeholder(
                                requireContext()!!.resources.getDrawable(
                                    R.drawable.upload_img,
                                    null
                                )
                            )
                            .into(setIMG)
                        genderPick.setSelection(
                            requireContext()!!.resources.getStringArray(R.array.gender).indexOf(
                                if (data.gender == "0") "Male" else if (data.gender == "1") "Female" else "Other"
                            )
                        )
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

    @RequiresApi(Build.VERSION_CODES.N)
    private fun updateDetails() {
        basicBinding.apply {

            saveBtn.setOnClickListener {

                val validationDetails = coachProfileViewModel.validateOnDetails(
                    unameET.text.toString().trim(),
                    emailET.text.toString().trim(),
                    mobileET.text.toString().trim(),
                    tvDate.text.toString().trim(),
                    bioDataET.text.toString().trim()
                )

                if (validationDetails.first) {

                    val gender = genderPick.selectedItem.toString()

                    val builder = MultipartBody.Builder()
                    builder.setType(MultipartBody.FORM)
                    if (!filePath.isNullOrEmpty()) {
                        builder.addFormDataPart(
                            "image",
                            File(filePath).name,
                            File(filePath).asRequestBody("multipart/form-data".toMediaTypeOrNull())
                        )
                    }

                    builder.apply {

                        addFormDataPart("coach_name", unameET.text.toString().trim())

                        addFormDataPart("phone_number", mobileET.text.toString().trim())

                        addFormDataPart(
                            "dob",
                            CalendarUtils.dateFormatForApi(tvDate.text.toString().trim())
                        )

                        addFormDataPart("coach_biodata", bioDataET.text.toString().trim())

                        addFormDataPart(
                            "gender", (when {
                                gender.equals("male", true) -> 0
                                gender.equals(
                                    "female",
                                    true
                                ) -> 1
                                else -> 2
                            }).toString()
                        )
                    }
                    coachProfileViewModel.updateCoachDetails(builder.build())

                } else Util.toast(requireContext(), validationDetails.second)
            }

            coachProfileViewModel.updateCoachDetailsResponse.observe(viewLifecycleOwner) {

                when (it) {
                    is NetworkResult.Success -> {

                        val coachProfileResponse = it.data as CoachProfileResponse

                        Util.toast(requireContext(), coachProfileResponse.message)

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
                    }
                }

            }
        }

    }

    private fun openCalendar() {
        basicBinding.tvDate.setOnClickListener { takeDOB() }
    }

    private fun openImagePicker() {
        basicBinding!!.imagePickBtn.setOnClickListener {
            ImagePicker.with(this)
                .crop()
                .start()
        }
    }

    private fun takeDOB() {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val datePicker = DatePickerDialog(
            requireActivity(),
            { view, year, monthOfYear, dayOfMonth ->

                try {
                    val months = monthOfYear + 1
                    var date =
                        "$year-${if (months < 10) "0$months" else months}-${if (dayOfMonth < 10) "0$dayOfMonth" else dayOfMonth}"

                    date = CalendarUtils.dateFormatFit(date)

                    basicBinding!!.tvDate.text = date
                } catch (e: ParseException) {
                    e.printStackTrace()
                }

            }, year, month, day
        )

        datePicker.datePicker.maxDate = System.currentTimeMillis()
        datePicker.show()
        datePicker.getButton(DatePickerDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK)
        datePicker.getButton(DatePickerDialog.BUTTON_NEGATIVE).setTextColor(Color.BLACK)

    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        try {
            uri = data?.data!!
            basicBinding!!.setIMG.setImageURI(uri)
            filePath = data?.getStringExtra("extra.file_path")
        } catch (e: Exception) {
        }
    }


}