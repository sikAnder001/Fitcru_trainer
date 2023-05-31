package com.ennovations.fitcrucoach.fragments

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.DialogInterface
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.SeekBar
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.ennovations.fitcrucoach.Network.CustomProgressLoading
import com.ennovations.fitcrucoach.Network.NetworkResult
import com.ennovations.fitcrucoach.Network.TokenManager
import com.ennovations.fitcrucoach.R
import com.ennovations.fitcrucoach.body.EditGoalsBody
import com.ennovations.fitcrucoach.databinding.FragmentEditGoalsBinding
import com.ennovations.fitcrucoach.helper.InputFilterMinMax
import com.ennovations.fitcrucoach.response.ChangePasswordResponse
import com.ennovations.fitcrucoach.response.GetEditGoalsResponse
import com.ennovations.fitcrucoach.utility.CalendarUtils
import com.ennovations.fitcrucoach.utility.SessionManager
import com.ennovations.fitcrucoach.utility.Util
import com.ennovations.fitcrucoach.view_model.EditGoalsViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class EditGoalsFragment : Fragment() {

    private lateinit var goalsBinding: FragmentEditGoalsBinding

    var startPoint = 0

    var endPoint = 0

    private var which = 0

    private var userId = 0

    private lateinit var loading: CustomProgressLoading

    private val viewModel by viewModels<EditGoalsViewModel>()

    @Inject
    lateinit var tokenManager: TokenManager

    val dataspinner = arrayOf(
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

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        goalsBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_edit_goals, container, false)

        userId = requireArguments().getInt("user_id", 0)

        loading = CustomProgressLoading(requireContext())

        backPress()
        /*  if(data!=null){

              getEditGoalApi()
          }else{
              validationOnEditGoals(

              )
                  getEditGoalApi()

          }*/



        getEditGoalApi()

        hittingViews()

        editGoalsApi()

        return goalsBinding.root
    }

    private fun seekBar(seekBar: SeekBar, textView: TextView) {

        seekBar.setOnSeekBarChangeListener(@SuppressLint("AppCompatCustomView")

        object : SeekBar.OnSeekBarChangeListener {

            override fun onProgressChanged(
                seekBar: SeekBar?,
                progress: Int,
                fromUser: Boolean
            ) {

                var gmsData = progress.toString()

                textView.text = gmsData
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

                if (seekBar != null) {
                    startPoint = seekBar.progress

                }
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

                if (seekBar != null) {
                    endPoint = seekBar.progress
                }
                /*Toast.makeText(
                    requireContext(),
                    "Changed by:- ${endPoint - startPoint}",
                    Toast.LENGTH_SHORT
                ).show()*/
            }

        })
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun hittingViews() {
        goalsBinding.apply {

            goalsBinding.backBtn.setOnClickListener {

                requireActivity().onBackPressed()

            }
            overallCalEt.filters = arrayOf(InputFilterMinMax(1, 10000))

            var name = SessionManager.getCoachDetails().coach_name
            name = name[0].toString().uppercase()


            backBtn.setOnClickListener {
                requireActivity().onBackPressed()


            }


            seekBar(carbsSeekbar, carbGmsTv)

            seekBar(proteinsSeekbar, proteinsGmsTv)

            seekBar(fatsSeekbar, fatsGmsTv)

            dateTv.setOnClickListener {

                which = 1

                calendar()
            }

            dateTv1.setOnClickListener {

                which = 2

                calendar()
            }

            saveBtn.setOnClickListener {
                viewModel.editGoals(
                    EditGoalsBody(
                        userId,
                        workoutEt.text.toString(),
                        Integer.parseInt(if (carbGmsTv?.text?.isEmpty()!!) "0" else carbGmsTv?.text.toString()),
                        Integer.parseInt(if (proteinsGmsTv?.text?.isEmpty()!!) "0" else proteinsGmsTv?.text.toString()),
                        Integer.parseInt(if (fatsGmsTv?.text?.isEmpty()!!) "0" else fatsGmsTv?.text.toString()),
                        overallCalEt.text.toString(),
                        Integer.parseInt(if (stepsEt?.text?.isEmpty()!!) "0" else stepsEt?.text.toString()),
                        Integer.parseInt(if (waterEt?.text?.isEmpty()!!) "0" else waterEt?.text.toString()),
                        if (shortGoalSP.selectedItem.toString() == "Please select a value") "0" else shortGoalSP.selectedItem.toString(),
                        otherEt.text.toString(),
                        Integer.parseInt(if (targetValueEt?.text?.isEmpty()!!) "0" else targetValueEt?.text.toString()),
                        CalendarUtils.dateFormatForApi(dateTv.text.toString()),
                        if (longGoalSP.selectedItem.toString() == "Please select a value") "0" else longGoalSP.selectedItem.toString(),
                        otherEt1.text.toString(),
                        Integer.parseInt(if (targetValueEt1?.text?.isEmpty()!!) "0" else targetValueEt1?.text.toString()),
                        CalendarUtils.dateFormatForApi(dateTv1.text.toString())
                    )
                )

                viewModel.getEditGoals(userId)
            }

        }

        spinner()
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

                var date1 = CalendarUtils.dateFormatFit(date)

                if (which == 1) {

                    goalsBinding.dateTv.text = date1

                } else if (which == 2) {

                    goalsBinding.dateTv1.text = date1

                } else {
                }

            },
            year,
            month,
            day
        )

        dpd.datePicker.minDate = System.currentTimeMillis()

        dpd.show()

        dpd.getButton(DatePickerDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK)

        dpd.getButton(DatePickerDialog.BUTTON_NEGATIVE).setTextColor(Color.BLACK)

    }

    private fun spinner() {

        val adapter =
            ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_dropdown_item,
                dataspinner
            )

        goalsBinding.apply {
            shortGoalSP.adapter = adapter




            longGoalSP.adapter = adapter


            shortGoalSP.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    p0: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    (p0?.getChildAt(0) as TextView?)?.setTextColor(Color.WHITE)



                    if (p0?.selectedItem.toString() == "Create custom text goal") {
                        otherEt.visibility = View.VISIBLE
                    } else {
                        otherEt.visibility = View.GONE
                        otherEt.text = null
                    }

                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }
            }

            longGoalSP.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    p0: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {

                    (p0?.getChildAt(0) as TextView?)?.setTextColor(Color.WHITE)

                    if (p0?.selectedItem.toString() == "Create custom text goal") {
                        otherEt1.visibility = View.VISIBLE
                    } else {
                        otherEt1.visibility = View.GONE
                        otherEt1.text = null
                    }

                }

                override fun onNothingSelected(p0: AdapterView<*>?) {

                }
            }

        }

    }

    private fun getEditGoalApi() {

        //showToast("hit api")


        viewModel.getEditGoals(userId)


        viewModel.getEditGoals.observe(viewLifecycleOwner) {
            loading.hideProgress()
            when (it) {
                is NetworkResult.Success -> {
                    val response = it!!.data as GetEditGoalsResponse

                    var responseData = response.data

                    goalsBinding.apply {

                        workoutTv.text = "Workout (${responseData.workout.unit})"
                        (workoutEt as TextView).text = responseData.workout.calorie_burn

                        carbsSeekbar.max = responseData.diet.total_carbs
                        var carbs = responseData.diet.consume_carbs
                        carbGmsTv.text = carbs.toString()
                        carbsSeekbar.progress = carbs

                        proteinsSeekbar.max = responseData.diet.total_proteins
                        var proteins = responseData.diet.consume_protein
                        proteinsSeekbar.progress = proteins
                        proteinsGmsTv.text = proteins.toString()

                        fatsSeekbar.max = responseData.diet.total_fats
                        var fat = responseData.diet.consume_fat
                        fatsSeekbar.progress = fat
                        fatsGmsTv.text = fat.toString()

                        overallCalTv.text =
                            "Calories Intake (${responseData.overall_calories.unit})"
                        (overallCalEt as TextView).text =
                            responseData.overall_calories.overall_calories
                        (stepsEt as TextView).text = responseData.steps.toString()
                        (waterEt as TextView).text = responseData.water.toString()
                        if (responseData.short_goals.answer != null && responseData.short_goals.answer!!.isNotEmpty()) {
                            shortGoalSP.setSelection(dataspinner.indexOf(responseData.short_goals.answer))
                        }
                        (otherEt as TextView).text = responseData.short_goals.custom
                        (targetValueEt as TextView).text =
                            responseData.short_goals.target_value.toString()

                        dateTv.text =
                            CalendarUtils.dateFormatFit(responseData.short_goals.target_date ?: "")

                        if (responseData.long_goals.answer != null && responseData.long_goals.answer!!.isNotEmpty()) {
                            longGoalSP.setSelection(dataspinner.indexOf(responseData.long_goals.answer))
                        }
                        (otherEt1 as TextView).text = responseData.long_goals.custom
                        (targetValueEt1 as TextView).text =
                            responseData.long_goals.target_value.toString()

                        if (responseData.long_goals.target_date != null) {
                            dateTv1.text =
                                CalendarUtils.dateFormatFit(responseData.long_goals.target_date)
                        } else dateTv1.text = "Select Date"
                    }

                }
                is NetworkResult.Error -> {

                    val response = Util.error(it.data, GetEditGoalsResponse::class.java)

                    if (response.message == "Data not found!") {
                        popUp()
                    }
                    /*  Toast.makeText(requireContext(), response.message, Toast.LENGTH_LONG)
                          .show()*/
                }
                is NetworkResult.Loading -> {
                    loading.showProgress()
                }
            }
        }
    }

    private fun popUp() {

        val dialogBuilder =
            AlertDialog.Builder(requireActivity(), R.style.AlertDialogStyle1)

        dialogBuilder.setMessage("Please fill the Consultation Form first.")
            .setCancelable(false)
            .setPositiveButton("ok", DialogInterface.OnClickListener { _, _ ->

            })

        val alert = dialogBuilder.create()
        alert.setTitle("Alert")
        alert.show()

    }


    private fun editGoalsApi() {
        viewModel.editGoals.observe(viewLifecycleOwner) {
            loading.hideProgress()
            when (it) {
                is NetworkResult.Success -> {
                    val response = it.data as ChangePasswordResponse
                    viewModel.getEditGoals(userId)
                    Util.toast(requireContext(), response.message)

                }
                is NetworkResult.Error -> {

                    val response = Util.error(it.data, ChangePasswordResponse::class.java)
                    if (response.message == "Data not found!") {
                        popUp()
                    }
                    /*  Toast.makeText(requireContext(), response.message, Toast.LENGTH_LONG)
                          .show()*/
                }
                is NetworkResult.Loading -> {
                    loading.showProgress()
                }
            }
        }

    }


    override fun onResume() {
        super.onResume()
        loading.showProgress()
        // getEditGoalApi()
        viewModel.getEditGoals(userId)
        //  showToast("onResume")

    }


    /* override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
         super.onActivityResult(requestCode, resultCode, data)
         viewModel.getEditGoals(userId)
     }*/


    private fun validationOnEditGoals(
        workout: Int,
        carbs: Int,
        protein: Int,
        fat: Int,
        overAllCal: String,
        steps: Int,
        water: Int,
        sTargetValue: Int,
        sTargetDate: String,
        lTargetValue: Int,
        lTargetDate: String
    ): Pair<Boolean, String> {
        var result = Pair(true, "")
        if (TextUtils.isEmpty(workout.toString()))
            result = Pair(false, "Please fill the workout")
        else if (TextUtils.isEmpty(carbs.toString()))
            result = Pair(false, "Please select the carbs")
        else if (TextUtils.isEmpty(protein.toString()))
            result = Pair(false, "Please select the protein")
        else if (TextUtils.isEmpty(fat.toString()))
            result = Pair(false, "Please select the fat")
        else if (TextUtils.isEmpty(overAllCal))
            result = Pair(false, "Please fill the overall calories")
        else if (TextUtils.isEmpty(steps.toString()))
            result = Pair(false, "Please fill the steps")
        else if (TextUtils.isEmpty(water.toString()))
            result = Pair(false, "Please fill the water")
        else if (TextUtils.isEmpty(sTargetValue.toString()))
            result = Pair(false, "Please fill the short goal's target value")
        else if (TextUtils.isEmpty(sTargetDate))
            result = Pair(false, "Please select short goal's target date")
        else if (TextUtils.isEmpty(lTargetValue.toString()))
            result = Pair(false, "Please fill the long goal's target value")
        else if (TextUtils.isEmpty(lTargetDate))
            result = Pair(false, "Please select long goal's target date")
        return result
    }


    private fun backPress() {
        requireActivity()
            .onBackPressedDispatcher
            .addCallback(requireActivity(), object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    val bundle = Bundle()
                    bundle.putInt("user_id", userId)
                    view?.findNavController()
                        ?.navigate(R.id.action_editGoalsFragment_to_mainPlanFragment, bundle)
                }
            }
            )

    }
}