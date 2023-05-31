package com.ennovations.fitcrucoach.fragments

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.ennovations.fitcrucoach.Network.CustomProgressLoading
import com.ennovations.fitcrucoach.Network.NetworkResult
import com.ennovations.fitcrucoach.R
import com.ennovations.fitcrucoach.databinding.FragmentHealthQuestionnaireBinding
import com.ennovations.fitcrucoach.response.AppointmentListResponse
import com.ennovations.fitcrucoach.response.GetHealthQuestionnaire
import com.ennovations.fitcrucoach.utility.Util
import com.ennovations.fitcrucoach.view_model.QuestionnaireViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_health_questionnaire.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.MultipartBody.Companion.FORM
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileOutputStream

@AndroidEntryPoint
class HealthQuestionnaireFragment(val user_id: Int) : Fragment() {
    //HealthQuestionary
    private lateinit var fragmentHealthQuestionnaireBinding: FragmentHealthQuestionnaireBinding
    private val binding get() = fragmentHealthQuestionnaireBinding
    private lateinit var viewArray: ArrayList<TextView>
    private lateinit var viewGroupArray: ArrayList<LinearLayout>
    private lateinit var checkBox: ArrayList<CheckBox>
    private lateinit var editText: ArrayList<EditText>
    private lateinit var checkbox11: ArrayList<CheckBox>
    private lateinit var checkbox12: ArrayList<CheckBox>
    private lateinit var checkbox70: ArrayList<CheckBox>
    private lateinit var checkbox80: ArrayList<CheckBox>
    private lateinit var checkbox90: ArrayList<CheckBox>
    private lateinit var checkbox100: ArrayList<CheckBox>
    private lateinit var loading: CustomProgressLoading
    private var uri: Uri? = null
    private var filePath: ArrayList<String> = arrayListOf<String>("", "", "")
    private var flag = 0
    private val viewModel by viewModels<QuestionnaireViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentHealthQuestionnaireBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_health_questionnaire,
            container,
            false
        )
        loading = CustomProgressLoading(requireContext())
        binding.btnRegister.setOnClickListener {
            uploadImage()
        }
        binding.apply {
            viewArray = arrayListOf(
                tvQ11,
                tvQ22,
                tvQ30,
                tvQ77,
                tvQ88,
                tvQ99,
                tvQ1010,
                tvQ1212,
                tvQ1313,
                tvQ13130,
                tvQ1414
            )
            viewGroupArray = arrayListOf(
                llQ11,
                llQ22,
                llQ30,
                llQ77,
                llQ88,
                llQ99,
                llQ1010,
                llQ1212,
                llQ1313,
                llQ13130,
                llQ1414
            )
            checkBox = arrayListOf(cbOther, cbOther2, cbInjuries)
            editText = arrayListOf(etOther, etOther2, etInjuries)

            for (i in 0 until viewArray.size) {
                viewArray[i].setOnClickListener {
                    visibility(viewArray[i], viewGroupArray[i])
                }
            }

            for (i in 0 until checkBox.size) {
                checkBox[i].setOnClickListener {
                    visibility2(checkBox[i], editText[i])
                }
            }
        }

        /*binding.apply {
            ivImage1.setOnClickListener {
                flag = 1
                ImagePicker.with(this@HealthQuestionnaireFragment).start()
            }
            ivImage2.setOnClickListener {
                flag = 2
                ImagePicker.with(this@HealthQuestionnaireFragment).start()
            }
            ivImage3.setOnClickListener {
                flag = 3
                ImagePicker.with(this@HealthQuestionnaireFragment).start()
            }
        }*/
        singleSelection()
        addImageAndRemove()
        getAllQuestionsAnswer()
        return binding.root
    }

    private fun singleSelection() {
        binding.apply {
            cb121.setOnClickListener {
                if (cb121.isChecked) {
                    cb122.isChecked = false
                    cb123.isChecked = false
                }
            }
            cb122.setOnClickListener {
                if (cb122.isChecked) {
                    cb121.isChecked = false
                    cb123.isChecked = false
                }
            }
            cb123.setOnClickListener {
                if (cb123.isChecked) {
                    cb121.isChecked = false
                    cb122.isChecked = false
                }
            }
        }
    }

    private fun addImageAndRemove() {
        binding.apply {
            ivRemove1.setOnClickListener {
                ivImage1.setImageResource(R.drawable.upload_img)
                tvAddImage1.visibility = VISIBLE
            }
            ivRemove2.setOnClickListener {
                ivImage2.setImageResource(R.drawable.upload_img)
                tvAddImage2.visibility = VISIBLE
            }
            ivRemove3.setOnClickListener {
                ivImage3.setImageResource(R.drawable.upload_img)
                tvAddImage3.visibility = VISIBLE
            }
        }
    }

    private fun getAllQuestionsAnswer() {
        loading = CustomProgressLoading(requireContext())
        try {
            viewModel.getHealthQuestionnaire(user_id)
            viewModel.questionnaire.observe(viewLifecycleOwner) {
                loading.hideProgress()
                when (it) {
                    is NetworkResult.Success -> {
                        try {
                            fillQuestionnaire(it.data!! as GetHealthQuestionnaire)
                        } catch (e: Exception) {
                            Log.e("TAG", "getAllquestionAnswer: $e")
                        }
                    }
                    is NetworkResult.Error -> {
                        val response = Util.error(it.data!!, AppointmentListResponse::class.java)
                        /*  Toast.makeText(context, response.message, Toast.LENGTH_LONG).show()*/
                    }
                    is NetworkResult.Loading -> {
                        loading.showProgress()
                    }
                }
            }
        } catch (e: Exception) {
            Log.e("TAG", "getAllQuestionsAnswer: ${e.toString()}")
        }
    }

    private fun fillQuestionnaire(body: GetHealthQuestionnaire) {
        binding.apply {
            for (value in body.data.questions.question_data)
                when (value.id) {
                    1 -> for (j in value.answer) {
                        for (i in arrayListOf(cb_11, cb_12, cb_13, cb_14, cb_15)) {
                            var checked = i.text
                            if (checked == j) {
                                i.isChecked = true
                            }
                        }
                    }
                    2 -> if (value.answer.isNotEmpty())
                        et_22.setText(value.answer[0])
                    3 -> if (value.answer.isNotEmpty())
                        et_44.setText(value.answer[0])
                    4 -> if (value.answer.isNotEmpty())
                        et_55.setText(value.answer[0])
                    5 -> if (value.answer.isNotEmpty())
                        et_66.setText(value.answer[0])
                    6 -> if (value.answer.isNotEmpty())
                        et_33.setText(value.answer[0])
                    7 -> for (j in value.answer) {
                        for (i in arrayListOf(
                            cb_71,
                            cb_72,
                            cb_73,
                            cb_74,
                            cb_75,
                            cb_76,
                            cb_77
                        )) {
                            var checked = i.text
                            if (checked == j) {
                                i.isChecked = true
                            }
                        }
                        if (j.startsWith("Injuries:")) {
                            cbInjuries.isChecked = true
                            etInjuries.setText(j.split(":")[1])
                            etInjuries.visibility = VISIBLE
                        }
                        if (j.startsWith("Other:")) {
                            cbOther.isChecked = true
                            etOther.setText(j.split(":")[1])
                            etOther.visibility = VISIBLE
                        }
                    }
                    8 -> for (j in value.answer) {
                        for (i in arrayListOf(
                            cb_81,
                            cb_82,
                            cb_83,
                            cb_84,
                            cb_85,
                        )) {
                            var checked = i.text
                            if (checked == j) {
                                i.isChecked = true
                            }
                        }
                        if (j.startsWith("Other:")) {
                            cbOther2.isChecked = true
                            etOther2.setText(j.split(":")[1])
                            etOther2.visibility = VISIBLE
                        }
                    }
                    9 -> for (j in value.answer) {
                        for (i in arrayListOf(
                            cb_91, cb_92, cb_93, cb_94, cb_95, cb_96, cb_97
                        )) {
                            var checked = i.text
                            if (checked == j) {
                                i.isChecked = true
                            }
                        }
                    }
                    10 -> for (j in value.answer) {
                        for (i in arrayListOf(cb_1010, cb_1011, cb_1012, cb_1013, cb_1014)) {
                            var checked = i.text
                            if (checked == j) {
                                i.isChecked = true
                            }
                        }
                    }
                    11 -> for (j in value.answer) {
                        for (i in arrayListOf(cb_12_1, cb_12_2, cb_12_3)) {
                            var checked = i.text
                            if (checked == j) {
                                i.isChecked = true
                            }
                        }
                    }
                    12 -> if (value.answer.isNotEmpty())
                        etBreakfast.setText(value.answer[0])
                    13 -> if (value.answer.isNotEmpty())
                        etComments.setText(value.answer[0])
                }
            try {
                Util.loadImage(
                    requireContext(),
                    ivImage1,
                    body.data.questions.image[0].image_url
                )
                Util.loadImage(
                    requireContext(),
                    ivImage2,
                    body.data.questions.image[1].image_url
                )
                Util.loadImage(
                    requireContext(),
                    ivImage3,
                    body.data.questions.image[2].image_url
                )
            } catch (e: Exception) {
            }

        }
    }

    private fun visibility(textView: TextView, linearLayout: LinearLayout) {
        linearLayout.visibility =
            if (linearLayout.visibility == View.GONE) View.VISIBLE else View.GONE
        textView.setCompoundDrawablesWithIntrinsicBounds(
            0,
            0,
            if (linearLayout.visibility == View.GONE) R.drawable.ic_arrow_down else R.drawable.ic_arrow_up,
            0
        )
    }

    private fun visibility2(checkBox: CheckBox, editText: EditText) {
        editText.visibility = if (checkBox.isChecked)
            View.VISIBLE
        else View.GONE
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        try {
            uri = data?.data
            when (flag) {
                1 -> binding.apply {
                    ivImage1.setImageURI(uri)
                    tvAddImage1.visibility = GONE
                    filePath[0] = data?.getStringExtra("extra.file_path")!!
                }
                2 -> binding.apply {
                    ivImage2.setImageURI(uri)
                    tvAddImage2.visibility = GONE
                    filePath[1] = data?.getStringExtra("extra.file_path")!!
                }
                3 -> binding.apply {
                    ivImage3.setImageURI(uri)
                    tvAddImage3.visibility = GONE
                    filePath[2] = data?.getStringExtra("extra.file_path")!!
                }
            }
        } catch (e: Exception) {
        }
    }

    private fun createDirectoryAndSaveFile(imageToSave: Bitmap, fileName: String): File {
        val direct = File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                .toString() + "/Fitcru"
        )
        Log.e("TAG", "createDirectoryAndSaveFile: " + direct.path)
        if (!direct.exists()) {
            direct.mkdirs()
        }
        val file = File(direct, fileName)
        if (file.exists()) {
            file.delete()
        }
        try {
            val out = FileOutputStream(file)
            imageToSave.compress(Bitmap.CompressFormat.JPEG, 100, out)
            out.flush()
            out.close()
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        return file
    }

    private fun uploadImage() {
        val builder = MultipartBody.Builder()
        val array = arrayOf(ivImage1, ivImage2, ivImage3)
        builder.setType(FORM)
        for ((index, value) in array.withIndex()) {
            try {
                val drawable: BitmapDrawable = value.drawable as BitmapDrawable
                val bitmap: Bitmap = drawable.bitmap
                val file = createDirectoryAndSaveFile(bitmap, "image$index.jpeg")
                builder.addFormDataPart(
                    "image[]",
                    file.name,
                    file.asRequestBody("multipart/form-data".toMediaTypeOrNull())
                )
            } catch (e: Exception) {
                Log.e("TAG", "uploadImage: $e")
                loading.hideProgress()
            }
        }
        if (valid()) {
            //loading.showProgress()
            //viewModel.uploadImageQuestionnaire(builder.build())
        }
        /*viewModel.image.observe(viewLifecycleOwner) {
            val msg = if (it.isSuccessful && it.body() != null) {
                imageArray.clear()
                imageArray.addAll(it.body()!!.data)
                viewModel.healthQuestion(createBody())
                it.body()!!.message
            } else if (!it.isSuccessful && it.errorBody() != null) {
                Util.error(it.errorBody(), ImageUploadResponse::class.java).message
            } else "Something went wrong"
            Util.toast(requireContext(), msg)
        }*/

    }

    private fun valid(): Boolean {
        binding.apply {
            var pair = Pair(true, "")
            if (!isOneSelected(arrayListOf(cb11, cb12, cb13, cb14, cb15)))
                pair = Pair(false, "Please select reason that you want to sign up")
            else if (et22.text.toString().isEmpty())
                pair = Pair(false, "Please fill the true reason for a change")
            else if (et44.text.toString().isEmpty())
                pair = Pair(false, "Please fill your waist size")
            else if (et55.text.toString().isEmpty())
                pair = Pair(false, "Please fill your neck size")
            else if (et66.text.toString().isEmpty())
                pair = Pair(false, "Please fill your hip size")
            else if (et33.text.toString().isEmpty())
                pair = Pair(false, "Please fill your body weight")
            else if (cbInjuries.isChecked && etInjuries.text.toString().isEmpty())
                pair = Pair(false, "Please fill about injuries details")
            else if (cbOther.isChecked && etOther.text.toString().isEmpty())
                pair = Pair(false, "Please fill about other medical condition")
            else if (cbOther2.isChecked && etOther2.text.toString().isEmpty())
                pair = Pair(false, "Please fill about other dietary allergies")
            else if (!isOneSelected(
                    arrayListOf(
                        cb_91,
                        cb_92,
                        cb_93,
                        cb_94,
                        cb_95,
                        cb_96,
                        cb_97
                    )
                )
            )
                pair = Pair(false, "Please select preferred cuisines")
            else if (!isOneSelected(arrayListOf(cb_1010, cb_1011, cb_1012, cb_1013, cb_1014)))
                pair = Pair(false, "Please select preferred style of nutrition")
            else if (!isOneSelected(arrayListOf(cb_12_1, cb_12_2, cb_12_3)))
                pair = Pair(false, "Please select macronutrient split")
            if (!pair.first)
                Util.toast(requireContext(), pair.second)
            return pair.first
        }
    }

    private fun isOneSelected(checkbox: ArrayList<CheckBox>): Boolean {
        for (i in checkbox)
            if (i.isChecked)
                return true
        return false
    }
}