package com.ennovations.fitcrucoach.fragments

import android.app.DatePickerDialog
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.ennovations.fitcrucoach.Network.CustomProgressLoading
import com.ennovations.fitcrucoach.Network.NetworkResult
import com.ennovations.fitcrucoach.Network.TokenManager
import com.ennovations.fitcrucoach.R
import com.ennovations.fitcrucoach.body.ConsultationFormBody
import com.ennovations.fitcrucoach.databinding.FragmentConsultationFormBinding
import com.ennovations.fitcrucoach.helper.InputFilterMinMax
import com.ennovations.fitcrucoach.response.ConsultationFormResponse
import com.ennovations.fitcrucoach.response.GetConsultationResponse
import com.ennovations.fitcrucoach.utility.CalendarUtils
import com.ennovations.fitcrucoach.utility.Util
import com.ennovations.fitcrucoach.view_model.ConsultationFormViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_consultation_form.*
import java.util.*
import javax.inject.Inject


@AndroidEntryPoint
class ConsultationFormFragment(val user_id: Int) : Fragment() {

    private lateinit var consultationFormBinding: FragmentConsultationFormBinding

    private var TAG = ConsultationFormFragment::class.java.simpleName

    private var which = 0

    private val consultationFormDetailViewModel by viewModels<ConsultationFormViewModel>()

    private var update = false

    private var dummyData = 100

    private lateinit var loading: CustomProgressLoading

    @Inject
    lateinit var tokenManager: TokenManager

    val data = arrayOf(
        //"Please select a value",
        "No",
        "Yes"
    )

    private val data1 = arrayOf(
        "Please select a value",
        "Weight",
        "Body fat percentage",
        "Metabolic score",
        "Waist measurement",
        "Hip measurement",
        "Chest measurement",
        "Arms measurement",
        "Create custom text goal"
    )

    private val data2 = arrayOf(
        //"Please select a value",
        "Married",
        "Single",
        "Would not say"
    )

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        consultationFormBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_consultation_form, container, false)

        loading = CustomProgressLoading(requireContext())

        hittingViews()

        consultationFormBinding.apply {
            saveBtn.setOnClickListener {

                val validation = consultationFormDetailViewModel.validationConsultation(
                    ans1.text.toString(),
                    ans2.selectedItem.toString(),
                    ans3.text.toString(),
                    ans4.selectedItem.toString(),
                    ans5.selectedItem.toString(),
                    stageAns.text.toString(),
                    caloriesAns.text.toString(),
                    /*Integer.parseInt(carbsAns?.text.toString()),
                    Integer.parseInt(proteinsAns?.text.toString()),
                    Integer.parseInt(fatsAns?.text.toString()),*/
                    workoutProgramAns.text.toString(),
                    shortGoalAns.selectedItem.toString(),
//                    Integer.parseInt(targetValueEt?.text.toString()),
                    CalendarUtils.dateFormatForApi(dateTv.text.toString()),
                    longGoalAns.selectedItem.toString(),
//                    Integer.parseInt(targetValueEt1?.text.toString()),
                    CalendarUtils.dateFormatForApi(dateTv1.text.toString()),
                    CalendarUtils.dateFormatForApi(startDateAns.text.toString()),
                    /* Integer.parseInt(waterAns?.text.toString()),
                     Integer.parseInt(stepAns?.text.toString()),*/
                    overallCalAns.text.toString()
                )

                if (validation.first) {

                    consultationFormBinding.apply {

                        consultationFormDetailViewModel.consultationFormDetail(addBody(update))

                        consultationFormDetailViewModel.response1.observe(viewLifecycleOwner) {

                            when (it) {
                                is NetworkResult.Success -> {

                                    val consultationFormResponse =
                                        it.data as ConsultationFormResponse

                                    Util.toast(requireContext(), consultationFormResponse.message)

                                }
                                is NetworkResult.Error -> {

                                    val consultationFormResponse =
                                        Util.error(it.data, ConsultationFormResponse::class.java)

                                    Toast.makeText(
                                        requireContext(),
                                        consultationFormResponse.message,
                                        Toast.LENGTH_LONG
                                    )
                                        .show()
                                }
                                is NetworkResult.Loading -> {
                                }
                                else -> {}
                            }
                        }
                    }

                } else Util.toast(requireContext(), validation.second)
            }
        }

        getAllQuestionsAnswer()

        return consultationFormBinding.root
    }

    private fun getAllQuestionsAnswer() {

        consultationFormDetailViewModel.getConsultation(user_id)

        consultationFormDetailViewModel.response.observe(viewLifecycleOwner) {

            loading.hideProgress()

            when (it) {
                is NetworkResult.Success -> {
                    if (it.data != null) {

                        val response = it!!.data as GetConsultationResponse

                        fillQuestionnaire(it.data!! as GetConsultationResponse)
                        update = true
                    }

                }
                is NetworkResult.Error -> {
                    val response = Util.error(it.data, GetConsultationResponse::class.java)
                    consultationFormDetailViewModel.sendCode.observe(viewLifecycleOwner) {
                        when (it) {
                            // 401 -> Util.toast(requireContext(), response.message)
                            404 -> Log.e(TAG, "getAllQuestionsAnswer: $it")
                            else -> Util.toast(requireContext(), response.message)
                        }
                    }
                }
                is NetworkResult.Loading -> {

                    loading.showProgress()
                }
                else -> {}
            }
        }
    }

    private fun fillQuestionnaire(body: GetConsultationResponse) {

        for (value in body.data.questions.questions) {
            when (value.id) {
                1 -> if (value.answer != null && value.answer!!.isNotEmpty())
                    (ans1 as TextView).text = value.answer
                2 -> if (value.answer != null && value.answer!!.isNotEmpty()) ans2.setSelection(
                    data2.indexOf(value.answer)
                )
                3 -> if (value.answer != null && value.answer!!.isNotEmpty())
                    (ans3 as TextView).text = value.answer
                6 -> if (value.answer != null && value.answer!!.isNotEmpty())
                    (notes_ans as TextView).text = value.answer
                7 -> if (value.answer != null && value.answer!!.isNotEmpty())
                    (stage_ans as TextView).text = value.answer
                9 -> if (value.overall_calories != null && value.overall_calories!!.isNotEmpty())
                    (calories_ans as TextView).text = value.overall_calories
                10 -> if (value.consume_carbs != null /*&& value.consume_carbs!!.isNotEmpty()*/)
                    (carbs_ans as TextView).text = value.consume_carbs.toString()
                11 -> if (value.consume_protein != null /*&& value.consume_protein!!.isNotEmpty()*/)
                    (proteins_ans as TextView).text = value.consume_protein.toString()
                12 -> if (value.consume_fat != null /*&& value.consume_fat!!.isNotEmpty()*/)
                    (fats_ans as TextView).text = value.consume_fat.toString()
                13 -> if (value.calorie_burn != null && value.calorie_burn!!.isNotEmpty())
                    (workout_program_ans as TextView).text = value.calorie_burn
                14 -> if (value.answer != null && value.answer!!.isNotEmpty())
                    (start_date_ans as TextView).text =
                        CalendarUtils.dateFormatFit(value.answer.toString())
                15 -> if (value.answer != null && value.answer!!.isNotEmpty())
                    (suggestion_ans as TextView).text = value.answer
                /*18 -> if (value.answer != null && value.answer!!.isNotEmpty())
                    *//*(target_value_et as TextView).text = value.answer*//*
                19 -> if (value.answer != null && value.answer!!.isNotEmpty())
                    (date_tv as TextView).text = value.answer
                20 -> if (value.answer != null && value.answer!!.isNotEmpty())
                    (target_value_et1 as TextView).text = value.answer
                21 -> if (value.answer != null && value.answer!!.isNotEmpty())
                    (date_tv1 as TextView).text = value.answer*/
                22 -> if (value.overall_calories != null && value.overall_calories!!.isNotEmpty())
                    (overall_cal_ans as TextView).text = value.overall_calories
                23 -> if (value.water != null) {
                    (water_ans as TextView).text = value.water.toString()
                }
                24 -> if (value.steps != null) {
                    (step_ans as TextView).text = value.steps.toString()
                }
            }
        }

        for (value in body.data.questions.edit) {
            when (value.id) {
                4 -> if (value.ifYes != null && value.answer!!.isNotEmpty()) {
                    ans4.setSelection(data.indexOf(value.answer))

                    (ans4_1 as EditText).setText(value.ifYes)
                }
                5 -> if (value.ifYes != null && value.answer!!.isNotEmpty()) {

                    ans5.setSelection(data.indexOf(value.answer))

                    (ans5_1 as EditText).setText(value.ifYes)
                }
            }
        }

        for (value in body.data.questions.goals) {
            when (value.id) {
                16 -> if (value.answer != null && value.answer!!.isNotEmpty()) {

                    short_goal_ans.setSelection(data1.indexOf(value.answer))

                    var custom = value.custom

                    (short_goal_ans_1 as EditText).setText(custom)

                    consultationFormBinding.dateTv.text =
                        CalendarUtils.dateFormatFit(value.target_date.toString())

                    (target_value_et as TextView).text = value.target_value.toString()

                }
                17 -> if (value.answer != null && value.answer!!.isNotEmpty()) {

                    long_goal_ans.setSelection(data1.indexOf(value.answer))

                    var custom = value.custom

                    (long_goal_ans_1 as EditText).setText(custom)

                    consultationFormBinding.dateTv1.text =
                        CalendarUtils.dateFormatFit(value.target_date.toString())

                    (target_value_et1 as TextView).text = value.target_value.toString()

                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun addBody(update: Boolean): ConsultationFormBody {

        consultationFormBinding.apply {

            val questionList = ArrayList<ConsultationFormBody.Question>()

            val goalsList = ArrayList<ConsultationFormBody.Goals>()

            val editList = ArrayList<ConsultationFormBody.Edit>()

            questionList.add(
                ConsultationFormBody.Question(
                    1, tvQ11.text.toString(), ans1.text.toString()
                )
            )

            questionList.add(
                ConsultationFormBody.Question(
                    2, tvQ12.text.toString(), ans2.selectedItem.toString()
                )
            )

            questionList.add(
                ConsultationFormBody.Question(
                    3, tvQ13.text.toString(), ans3.text.toString()
                )
            )

            editList.add(
                ConsultationFormBody.Edit(
                    4, tvQ14.text.toString(), ans4.selectedItem.toString(), ans41.text.toString()
                )
            )

            editList.add(
                ConsultationFormBody.Edit(
                    5, tvQ15.text.toString(), ans5.selectedItem.toString(), ans51.text.toString()
                )
            )

            questionList.add(
                ConsultationFormBody.Question(
                    6, notesTv.text.toString(), notesAns.text.toString()
                )
            )

            questionList.add(
                ConsultationFormBody.Question(
                    7, stageTv.text.toString(), stageAns.text.toString()
                )
            )

            questionList.add(
                ConsultationFormBody.Question(
                    8, nutritionTv.text.toString(),
                )

            )

            questionList.add(
                ConsultationFormBody.Question(
                    9,
                    caloriesTv.text.toString(),
                    null,
                    caloriesAns.text.toString() ?: "0",
                    "kcal",
                )
            )

            questionList.add(
                ConsultationFormBody.Question(
                    10,
                    carbsTv.text.toString(),
                    null,
                    null,
                    null,
                    Integer.parseInt(if (carbsAns?.text?.isEmpty()!!) "0" else carbsAns?.text.toString())
                )
            )

            questionList.add(
                ConsultationFormBody.Question(
                    11,
                    proteinsTv.text.toString(),
                    null,
                    null,
                    null,
                    null,
                    Integer.parseInt(if (proteinsAns?.text?.isEmpty()!!) "0" else proteinsAns?.text.toString()),
                    null
                )
            )

            questionList.add(
                ConsultationFormBody.Question(
                    12, fatsTv.text.toString(), null, null, null, null,
                    null,
                    Integer.parseInt(if (fatsAns?.text?.isEmpty()!!) "0" else fatsAns?.text.toString()),
                    null,
                )
            )

            questionList.add(
                ConsultationFormBody.Question(
                    13, workoutProgramTv.text.toString(), null, null, "kcal", null,
                    null,
                    null,
                    workoutProgramAns.text.toString() ?: "0"
                )
            )

            questionList.add(
                ConsultationFormBody.Question(
                    14,
                    startDateTv.text.toString(),
                    CalendarUtils.dateFormatForApi(startDateAns.text.toString())
                )
            )

            questionList.add(
                ConsultationFormBody.Question(
                    15, suggestionTv.text.toString(), suggestionAns.text.toString()
                )
            )

            goalsList.add(
                ConsultationFormBody.Goals(
                    16,
                    shortGoalTv.text.toString(),
                    shortGoalAns.selectedItem.toString(),
                    shortGoalAns1.text.toString(),
                    CalendarUtils.dateFormatForApi(dateTv.text.toString()),
                    Integer.parseInt(if (targetValueEt?.text?.isEmpty()!!) "0" else targetValueEt?.text.toString())
                )
            )

            goalsList.add(
                ConsultationFormBody.Goals(
                    17,
                    longGoalTv.text.toString(),
                    longGoalAns.selectedItem.toString(),
                    longGoalAns1.text.toString(),
                    CalendarUtils.dateFormatForApi(dateTv1.text.toString()),
                    Integer.parseInt(if (targetValueEt1?.text?.isEmpty()!!) "0" else targetValueEt1?.text.toString())
                )
            )

            questionList.add(
                ConsultationFormBody.Question(
                    18, targetValueTv.text.toString(),
                )
            )

            questionList.add(
                ConsultationFormBody.Question(
                    19,
                    targetDateTv.text.toString(),
                )
            )

            questionList.add(
                ConsultationFormBody.Question(
                    20, targetValueTv1.text.toString(),
                )
            )

            questionList.add(
                ConsultationFormBody.Question(
                    21,
                    targetDateTv1.text.toString(),
                )
            )
            questionList.add(
                ConsultationFormBody.Question(
                    22,
                    overallCalTv.text.toString(),
                    null,
                    overallCalAns.text.toString(),
                    "kcal",
                )
            )

            questionList.add(
                ConsultationFormBody.Question(
                    23,
                    waterTv.text.toString(),
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    Integer.parseInt(if (waterAns?.text?.isEmpty()!!) "0" else waterAns?.text.toString())
                )
            )

            questionList.add(
                ConsultationFormBody.Question(
                    24,
                    stepsTv.text.toString(),
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    Integer.parseInt(if (stepAns?.text?.isEmpty()!!) "0" else stepAns?.text.toString())
                )
            )

            return ConsultationFormBody(
                update,
                user_id,
                questionList, goalsList, editList
            )
        }
    }

    private fun hittingViews() {

        consultationFormBinding.apply {

            carbsAns.filters = arrayOf(InputFilterMinMax(1, dummyData))

            proteinsAns.filters = arrayOf(InputFilterMinMax(1, dummyData))

            fatsAns.filters = arrayOf(InputFilterMinMax(1, dummyData))

            overallCalAns.filters = arrayOf(InputFilterMinMax(1, 10000))

            tvQ11.setOnClickListener {
                visibility(tvQ11, llQ11)
            }

            tvQ12.setOnClickListener {
                visibility(tvQ12, llQ12)
            }

            tvQ13.setOnClickListener {
                visibility(tvQ13, llQ13)
            }

            tvQ14.setOnClickListener {
                visibility(tvQ14, llQ14)
            }

            tvQ15.setOnClickListener {
                visibility(tvQ15, llQ15)
            }

            notesTv.setOnClickListener {
                visibility(notesTv, notesLl)
            }

            stageTv.setOnClickListener {
                visibility(stageTv, stageLl)
            }

            workoutProgramTv.setOnClickListener {
                visibility(workoutProgramTv, workoutProgramLl)
            }

            shortGoalTv.setOnClickListener {
                visibility(shortGoalTv, shortGoalLl)
            }

            longGoalTv.setOnClickListener {
                visibility(longGoalTv, longGoalLl)
            }

            startDateTv.setOnClickListener {
                visibility(startDateTv, startDateLl)
            }

            suggestionTv.setOnClickListener {
                visibility(suggestionTv, suggestionLl)
            }

            nutritionTv.setOnClickListener {
                visibility(nutritionTv, nutritionLl)
            }

            waterTv.setOnClickListener {
                visibility(waterTv, waterLl)
            }

            stepsTv.setOnClickListener {
                visibility(stepsTv, stepsLl)
            }

            overallCalTv.setOnClickListener {
                visibility(overallCalTv, overallCalLl)
            }

            dateTv.setOnClickListener {

                which = 1

                calendar()
            }

            dateTv1.setOnClickListener {

                which = 2

                calendar()
            }

            startDateAns.setOnClickListener {

                which = 3

                calendar()
            }
            spinner(ans4, ans41)

            spinner(ans5, ans51)

            spinner2(shortGoalAns, shortGoalAns1)

            spinner2(longGoalAns, longGoalAns1)

            spinner3()
        }
    }

    private fun spinner(spinner: Spinner, editText: EditText) {

        val adapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, data)
        consultationFormBinding.apply {

            spinner.adapter = adapter

            spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    p0: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    (p0?.getChildAt(0) as TextView).setTextColor(Color.WHITE)

                    if (p0?.selectedItem.toString() == "Yes") {
                        editText.visibility = View.VISIBLE
                    } else {
                        editText.visibility = View.GONE
                        editText.text = null
                    }

                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }
            }
        }
    }

    private fun spinner2(spinner: Spinner, editText: EditText) {

        val adapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, data1)
        consultationFormBinding.apply {

            spinner.adapter = adapter

            spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    p0: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {

                    (p0?.getChildAt(0) as TextView).setTextColor(Color.WHITE)

                    if (p0?.selectedItem.toString() == "Create custom text goal") {
                        editText.visibility = View.VISIBLE
                    } else {
                        editText.visibility = View.GONE
                        editText.text = null
                    }

                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }
            }

        }

    }

    private fun spinner3() {

        val adapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, data2)
        consultationFormBinding.apply {

            ans2.adapter = adapter

            ans2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    p0: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    (p0?.getChildAt(0) as TextView).setTextColor(Color.WHITE)
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }
            }
        }
    }

    private fun calendar() {

        val c = Calendar.getInstance()

        val year = c.get(Calendar.YEAR)

        val month = c.get(Calendar.MONTH)

        val day = c.get(Calendar.DAY_OF_MONTH)

        val dpd = DatePickerDialog(
            requireContext(),
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->

                val months = monthOfYear + 1
                val date =
                    "$year-${if (months < 10) "0$months" else months}-${if (dayOfMonth < 10) "0$dayOfMonth" else dayOfMonth}"

                var originalDate = CalendarUtils.dateFormatFit(date)

                if (which == 1) {

                    consultationFormBinding.dateTv.text = originalDate

                } else if (which == 2) {

                    consultationFormBinding.dateTv1.text = originalDate

                } else if (which == 3) {

                    consultationFormBinding!!.startDateAns.text = originalDate

                } else {
                }

            },
            year,
            month,
            day
        )
        if (which == 3) {
            //  dpd.datePicker.maxDate = System.currentTimeMillis()
        } else {
            dpd.datePicker.minDate = System.currentTimeMillis()
        }

        dpd.show()

        dpd.getButton(DatePickerDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK)

        dpd.getButton(DatePickerDialog.BUTTON_NEGATIVE).setTextColor(Color.BLACK)

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
}
