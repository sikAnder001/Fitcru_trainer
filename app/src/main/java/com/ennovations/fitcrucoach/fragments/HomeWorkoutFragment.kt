package com.ennovations.fitcrucoach.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.ennovations.fitcrucoach.R
import com.ennovations.fitcrucoach.adapter.HomeWorkoutChildAdapter
import com.ennovations.fitcrucoach.adapter.HomeWorkoutParentAdapter
import com.ennovations.fitcrucoach.databinding.FragmentHomeWorkoutBinding
import com.ennovations.fitcrucoach.model.HomeWorkoutModel

class HomeWorkoutFragment : Fragment() {

    private lateinit var homeWorkoutBinding: FragmentHomeWorkoutBinding

    private lateinit var homeWorkoutAdapter: HomeWorkoutParentAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        homeWorkoutBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_home_workout, container, false)

        homeWorkoutBinding.backBtn.setOnClickListener { requireActivity().onBackPressed() }

        setHomeWorkout()

        return homeWorkoutBinding.root
    }

    private fun setHomeWorkout() {
        val homeWorkoutChildModel: MutableList<HomeWorkoutModel.HomeWorkoutChildModel> =
            ArrayList()
        homeWorkoutChildModel.apply {
            add(
                HomeWorkoutModel.HomeWorkoutChildModel(
                    R.drawable.dark_place_hold,
                    "S&C Lower Body", "1 Hr 30 Min", "Paul Grand", "Intermediate"
                )
            )

            add(
                HomeWorkoutModel.HomeWorkoutChildModel(
                    R.drawable.place_holder,
                    "S&C Lower Body", "1 Hr 30 Min", "Niran Ponnappa", "Beginner"
                )
            )

            add(
                HomeWorkoutModel.HomeWorkoutChildModel(
                    R.drawable.dark_place_hold,
                    "S&C Lower Body", "1 Hr 30 Min", "Paul Grand", "Intermediate"
                )
            )

            add(
                HomeWorkoutModel.HomeWorkoutChildModel(
                    R.drawable.place_holder,
                    "S&C Lower Body", "1 Hr 30 Min", "Niran Ponnappa", "Beginner"
                )
            )

            add(
                HomeWorkoutModel.HomeWorkoutChildModel(
                    R.drawable.dark_place_hold,
                    "S&C Lower Body", "1 Hr 30 Min", "Paul Grand", "Intermediate"
                )
            )

            add(
                HomeWorkoutModel.HomeWorkoutChildModel(
                    R.drawable.place_holder,
                    "S&C Lower Body", "1 Hr 30 Min", "Paul Grand", "Intermediate"
                )
            )

            add(
                HomeWorkoutModel.HomeWorkoutChildModel(
                    R.drawable.place_holder,
                    "S&C Lower Body", "1 Hr 30 Min", "Niran Ponnappa", "Beginner"
                )
            )
        }

        val mainHomeWorkoutModel: MutableList<HomeWorkoutModel> = ArrayList()
        mainHomeWorkoutModel.apply {
            add(
                HomeWorkoutModel(
                    "Full Body",
                    homeWorkoutChildModel as ArrayList<HomeWorkoutModel.HomeWorkoutChildModel>
                )
            )
            setHomeWorkoutRV(mainHomeWorkoutModel as ArrayList<HomeWorkoutModel>)

        }
    }


    private fun setHomeWorkoutRV(homeWorkoutModel: ArrayList<HomeWorkoutModel>) {

        val linearLayout = LinearLayoutManager(
            context, LinearLayoutManager.VERTICAL, false
        )

        homeWorkoutBinding.homeWorkoutMainRv.layoutManager = linearLayout

        homeWorkoutBinding.homeWorkoutMainRv.setHasFixedSize(true)

        homeWorkoutAdapter =
            HomeWorkoutParentAdapter(
                homeWorkoutModel,
                context,
                object : HomeWorkoutChildAdapter.MyOnClickListenerHomeWorkout {
                    override fun onClick(position: Int) {

//                        view?.findNavController()
//                            ?.navigate(R.id.action_homeWorkoutFragment_to_selectClientFragment)

                        Toast.makeText(
                            requireContext(),
                            "You selected item:- $position",
                            Toast.LENGTH_SHORT
                        ).show()

                    }
                })

        homeWorkoutBinding.homeWorkoutMainRv.adapter = homeWorkoutAdapter
    }


}