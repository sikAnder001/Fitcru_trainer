package com.ennovations.fitcrucoach.fragments

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ennovations.fitcrucoach.Network.CustomProgressLoading
import com.ennovations.fitcrucoach.Network.NetworkResult
import com.ennovations.fitcrucoach.Network.TokenManager
import com.ennovations.fitcrucoach.R
import com.ennovations.fitcrucoach.adapter.NutritionAdapter
import com.ennovations.fitcrucoach.adapter.PlanDetailHabitAdapter
import com.ennovations.fitcrucoach.adapter.PlanDetailsCalendarAdapter
import com.ennovations.fitcrucoach.adapter.WorkoutAdapter
import com.ennovations.fitcrucoach.databinding.FragmentPlanDetailsBinding
import com.ennovations.fitcrucoach.response.ClientDetailResponse
import com.ennovations.fitcrucoach.utility.CalendarUtils
import com.ennovations.fitcrucoach.utility.Util
import com.ennovations.fitcrucoach.view_model.ClientListViewModel
import com.mikhaellopez.circularprogressbar.CircularProgressBar
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class PlanDetailsFragment(val user_id: Int) : Fragment(), PlanDetailsCalendarAdapter.OnItemListener,
    NutritionAdapter.NutritionOnClickListener {

    private lateinit var planDetailsBinding: FragmentPlanDetailsBinding

    private lateinit var planDetailsCalendarAdapter: PlanDetailsCalendarAdapter

    private val clientListViewModel by viewModels<ClientListViewModel>()

    private lateinit var loading: CustomProgressLoading

    private lateinit var selectedDate: String

    @Inject
    lateinit var tokenManager: TokenManager

    private var flag = false

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        planDetailsBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_plan_details, container, false)

        val c: Calendar = Calendar.getInstance()
        val df = SimpleDateFormat("yyyy-MM-dd")
        selectedDate = df.format(c.getTime())

        loading = CustomProgressLoading(requireContext())


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CalendarUtils.selectedDate = LocalDate.now()
            this.setMonthView()
        }


        getClientDetails(selectedDate)

        return planDetailsBinding.root
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun getClientDetails(selectedDate: String) {
        val workoutAdapter = WorkoutAdapter(context)

        val nutritionAdapter =
            NutritionAdapter(context, object : NutritionAdapter.NutritionOnClickListener {
                override fun onClick(id: Int, date: String) {
                    //
                    val bundle = Bundle()
                    bundle.putInt("data", id)
                    bundle.putInt("user_id", user_id)
                    bundle.putString("date", date)

//                view?.findNavController()
//                    ?.navigate(R.id.action_mainPlanFragment_to_editGoalsFragment, bundle)
//
                    view?.findNavController()
                        ?.navigate(R.id.action_mainPlanFragment_to_foodListingFragment, bundle)
                }
            })

        val planDetailHabitAdapter = PlanDetailHabitAdapter(context,
            object : PlanDetailHabitAdapter.MyOnClickListenerHabitPlanDetailed {
                override fun onClick(position: Int) {
                    //
                }
            })

        planDetailsBinding.workoutRv.adapter = workoutAdapter

        planDetailsBinding.nutritionRv.adapter = nutritionAdapter

        planDetailsBinding.habitRV.adapter = planDetailHabitAdapter

        clientListViewModel.getClientDetails(user_id, selectedDate)
        clientListViewModel.clientDetail.observe(viewLifecycleOwner) {
            loading.hideProgress()
            when (it) {
                is NetworkResult.Success -> {
                    val response = it.data as ClientDetailResponse

                    planDetailsBinding.apply {


                        var goals = response.data.profile
                        hittingViews(goals)

                        var progress = response.data.progress

                        try {

                            /*  workoutTV.text =
                                  "${
                                      (Math.round(
                                          progress.workout.workout_kcal_burnt.trim().toFloat()
                                      ) * 100) / Math.round(
                                          progress.workout.workout_kcal_target.trim().toFloat()
                                      )
                                  }% ${Math.round(progress.workout.workout_kcal_burnt.trim().toFloat())}"

                              tvWorkoutUnit.text = "${progress.workout.unit.trim()} burnt"
                          } catch (e: Exception) {
                              workoutTV.text = "0% ${progress.workout.workout_kcal_burnt.trim()}"
                              tvWorkoutUnit.text = "${progress.workout.unit.trim()} burnt"
                             // Log.e(TAG, "getDashBoard: $e")
                          }*/

                            workoutTV2.text =
                                "${
                                    (Math.round(progress.workout.workout_kcal_burnt.toFloat())
                                            )
                                }/ ${Math.round(progress.workout.workout_kcal_target.toFloat())}"
                            tvWorkoutUnit2.text = "${progress.workout.unit} burnt"

                        } catch (e: Exception) {
                            workoutTV2.text = "0/0"
                            tvWorkoutUnit2.text = "${progress.workout.unit.trim()} burnt"

                        }




                        dietTV.text = "${Math.round(progress.diet.kcal_burnt.toFloat())}/${
                            Math.round(progress.diet.diet_val.toFloat())
                        }"


                        waterTV.text =
                            "${Math.round(progress.water.water_used_val.toFloat())}/${
                                Math.round(
                                    progress.water.water_taget.toFloat()
                                )
                            }"

                        stepsTV.text =
                            "${Math.round(progress.steps.step_count_val.toFloat())}/${
                                Math.round(
                                    progress.steps.step_target.toFloat()
                                )
                            }"

//                            var totalWorkout =
//                                progress.workout.workout_kcal_burnt * 100 / progress.workout.workout_kcal_target


                        setCircularProgress(
                            Math.round(progress.workout.workout_kcal_burnt.toFloat()).toLong(),
                            Math.round(progress.workout.workout_kcal_target.toFloat()).toLong(),
                            Math.round(progress.diet.kcal_burnt.toFloat()).toLong(),
                            Math.round(progress.diet.diet_val.toFloat()).toLong(),
                            Math.round(progress.water.water_used_val.toFloat()),
                            Math.round(progress.water.water_taget.toFloat()),
                            Math.round(progress.steps.step_count_val.toFloat()).toLong(),
                            Math.round(progress.steps.step_target.toFloat()).toLong(),
                        )

//                            setCircularProgress(
//                                progress.workout.workout_kcal_burnt,
//                                totalWorkout,
//                                progress.diet.kcal_burnt,
//                                progress.diet.diet_val,
//                                progress.water.water_used_val,
//                                progress.water.water_taget,
//                                progress.steps.step_count_val,
//                                progress.steps.step_target,
//                            )


                    }
                    if (response.data.workout != null && response.data.workout.isNotEmpty()) {
                        planDetailsBinding.workoutToastTv.visibility = View.GONE
                        workoutAdapter.setList(response.data.workout)
                    } else {
                        planDetailsBinding.workoutToastTv.visibility = View.VISIBLE
                    }
                    nutritionAdapter.setList(response.data.todays_task)
                    if (!response.data.todays_task.isNullOrEmpty()) {
                        planDetailsBinding.nutritionToastTv.visibility = View.GONE
                        nutritionAdapter.setList(response.data.todays_task)

                    } else {
                        planDetailsBinding.nutritionToastTv.visibility = View.VISIBLE
                    }

                    if (!response.data.habit.isNullOrEmpty()) {
                        planDetailsBinding.habitToastTv.visibility = View.GONE
                        planDetailHabitAdapter.setList(response.data.habit)
                    } else {
                        planDetailsBinding.habitToastTv.visibility = View.VISIBLE

                    }

                }
                is NetworkResult.Error -> {
                    val response = Util.error(it.data, ClientDetailResponse::class.java)
                    Toast.makeText(context, response.message, Toast.LENGTH_LONG).show()
                }
                is NetworkResult.Loading -> {
                    loading.showProgress()
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun hittingViews(data: ClientDetailResponse.Data.Profile) {
        planDetailsBinding.apply {
            Glide.with(requireContext())
                .load(data.image_url)
                .placeholder(R.drawable.place_holder)
                .into(userImage)

//            val goalProgress = (100 - data.goal.toInt())

//            progreBarHorizontal.progress = goalProgress
            userName.text = data.name
//            goalLeft.text = "${data.goal} % left"
            Log.v("reached", "hitti")
            if (data.short_goal != null || data.short_goal != "0") {
                val sGoal = data.short_goal
                shortGoal.text = sGoal
            } else {
                shortGoal.text = "-"
            }


            if (data.target_value != null || data.target_value != 0) {
                val tValue = data.target_value.toString()
                targetValue.text = tValue
            } else {
                targetValue.text = "-"
            }


            if (data.target_date != null) {
                val date = changeFormat(data.target_date)
                targetDate.text = date
            } else {
                targetDate.text = "-"
            }

            coachProfileContainer.setOnClickListener {
                editGoal(data.id)
            }

        }
    }

    private fun editGoal(id: Int) {
        if (flag) {
            planDetailsBinding.arrowDown.visibility = View.VISIBLE
            planDetailsBinding.arrowUp.visibility = View.GONE
            planDetailsBinding.userdetail.visibility = View.GONE
            flag = false
        } else {
            planDetailsBinding.userdetail.visibility = View.VISIBLE
            planDetailsBinding.arrowDown.visibility = View.GONE
            planDetailsBinding.arrowUp.visibility = View.VISIBLE
            flag = true
            planDetailsBinding.editGoalLl.setOnClickListener {

                val bundle = Bundle()
                bundle.putInt("user_id", user_id)

                view?.findNavController()
                    ?.navigate(R.id.action_mainPlanFragment_to_editGoalsFragment, bundle)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setMonthView() {
        val daysInMonth: List<LocalDate?> =
            CalendarUtils.sevenDaysFun(CalendarUtils.selectedDate!!)

        val year: List<LocalDate?> = CalendarUtils.sevenDaysFun(CalendarUtils.selectedDate!!)

        planDetailsCalendarAdapter = PlanDetailsCalendarAdapter(
            daysInMonth as List<LocalDate>,
            year as List<LocalDate>, this
        )
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.HORIZONTAL, false
        )
        planDetailsBinding!!.planDetailsCalendarRv.layoutManager = layoutManager
        planDetailsBinding!!.planDetailsCalendarRv.adapter = planDetailsCalendarAdapter
        planDetailsBinding!!.planDetailsCalendarRv.smoothScrollToPosition(8)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onItemClick(position: Int, date: LocalDate?) {
        if (date != null) {
            CalendarUtils.selectedDate = date
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            selectedDate = date.format(formatter)

            if (planDetailsCalendarAdapter != null) {
                loading.showProgress()
                getClientDetails(selectedDate)
                planDetailsCalendarAdapter.notifyDataSetChanged()
            }
        }
    }

    private fun setCircularProgress(
        l1: Long,
        l2: Long,
        l3: Long,
        l4: Long,
        l5: Int,
        l6: Int,
        l7: Long,
        l8: Long
    ) {
        planDetailsBinding.progress1.apply {
            if (l1.toFloat() == 0f && l2.toFloat() == 0f) {
                progress = 0f
                setProgressWithAnimation(0f, 1000) // =1s
                progressMax = 0.1f
            } else {
                progress = if (l1.toFloat() > l2.toFloat()) l2.toFloat() else l1.toFloat()
                setProgressWithAnimation(l1.toFloat(), 1000) // =1s
                progressMax = l2.toFloat()
            }

            progressBarColor = Color.parseColor("#ed2b2b")

            backgroundProgressBarColor = Color.parseColor("#4a4a4a")

            progressBarWidth = 10f // in DP
            backgroundProgressBarWidth = 9f // in DP

            roundBorder = true
            //    startAngle = 180f
            progressDirection = CircularProgressBar.ProgressDirection.TO_RIGHT
        }

        planDetailsBinding.progress2.apply {
            if (l3.toFloat() == 0f && l4.toFloat() == 0f) {
                progress = 0f
                setProgressWithAnimation(0f, 1000) // =1s
                progressMax = 0.1f
            } else {
                progress = if (l3.toFloat() > l4.toFloat()) l4.toFloat() else l3.toFloat()

                setProgressWithAnimation(l3.toFloat(), 1000L) // =1s

                progressMax = l4.toFloat()
            }


            progressBarColor = Color.parseColor("#00cf0a")

            backgroundProgressBarColor = Color.parseColor("#4a4a4a")

            progressBarWidth = 10f // in DP
            backgroundProgressBarWidth = 9f // in DP

            roundBorder = true
            //    startAngle = 180f
            progressDirection = CircularProgressBar.ProgressDirection.TO_RIGHT
        }
        planDetailsBinding.progress3.apply {
            if (l5.toFloat() == 0f && l6.toFloat() == 0f) {
                progress = 0f
                setProgressWithAnimation(0f, 1000) // =1s
                progressMax = 0.1f
            } else {
                progress = if (l5.toFloat() > l6.toFloat()) l6.toFloat() else l5.toFloat()

                setProgressWithAnimation(l5.toFloat(), 1000L) // =1s

                progressMax = l6.toFloat()
            }

            progressBarColor = Color.parseColor("#FF02BD")

            backgroundProgressBarColor = Color.parseColor("#FF02BD")

            progressBarWidth = 10f // in DP
            backgroundProgressBarWidth = 9f // in DP

            roundBorder = true
            //    startAngle = 180f
            progressDirection = CircularProgressBar.ProgressDirection.TO_RIGHT
        }
        planDetailsBinding.progress4.apply {
            if (l7.toFloat() == 0f && l8.toFloat() == 0f) {
                progress = 0f
                setProgressWithAnimation(0f, 1000) // =1s
                progressMax = 0.1f
            } else {
                progress = if (l7.toFloat() > l8.toFloat()) l8.toFloat() else l7.toFloat()

                setProgressWithAnimation(l7.toFloat(), 1000) // =1s

                progressMax = l8.toFloat()
            }

            progressBarColor = Color.parseColor("#FF02BD")

            backgroundProgressBarColor = Color.parseColor("#FF02BD")

            progressBarWidth = 10f // in DP
            backgroundProgressBarWidth = 9f // in DP

            roundBorder = true

            progressDirection = CircularProgressBar.ProgressDirection.TO_RIGHT
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun changeFormat(join: String?): String {
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
        val dateTime = simpleDateFormat.parse(join)
        val simpleDateFormat1 = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            SimpleDateFormat("dd MMM YYYY")
        } else {
            TODO("VERSION.SDK_INT < N")
        }
        val date = simpleDateFormat1.format(dateTime)

        return date
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) {
            Log.v("onVisibleHint", "works")
            getClientDetails(selectedDate)
            Log.v("onVisibleHint", "works1")
        } else {
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onStart() {
        super.onStart()
        Log.v("onStart", "works")
        getClientDetails(selectedDate)
        Log.v("onStart", "works1")
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onResume() {
        super.onResume()
        Log.v("onResume", "works")
        getClientDetails(selectedDate)
        Log.v("onResume", "works1")
    }

    override fun onClick(mealId: Int, date: String) {

    }
}