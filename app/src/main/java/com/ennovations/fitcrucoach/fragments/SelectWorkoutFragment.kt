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
import com.ennovations.fitcrucoach.adapter.SelectWorkoutChildAdapter
import com.ennovations.fitcrucoach.adapter.SelectWorkoutParentAdapter
import com.ennovations.fitcrucoach.databinding.FragmentSelectWorkoutBinding
import com.ennovations.fitcrucoach.model.SelectWorkoutModel


class SelectWorkoutFragment : Fragment() {

    private lateinit var selectWorkoutBinding: FragmentSelectWorkoutBinding

    private lateinit var selectWorkoutAdapter: SelectWorkoutParentAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        selectWorkoutBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_select_workout, container, false)

        setSelectedWorkout()

        return selectWorkoutBinding.root
    }

    private fun setSelectedWorkout() {
        val selectWorkoutChildItemModel: MutableList<SelectWorkoutModel.SelectWorkoutChildModel> =
            ArrayList()
        selectWorkoutChildItemModel.apply {
            add(
                SelectWorkoutModel.SelectWorkoutChildModel(
                    R.drawable.place_holder,
                    "Home Workout", "200 + Sessions"
                )
            )

            add(
                SelectWorkoutModel.SelectWorkoutChildModel(
                    R.drawable.place_holder,
                    "Home Workout", "200 + Sessions"
                )
            )

            add(
                SelectWorkoutModel.SelectWorkoutChildModel(
                    R.drawable.place_holder,
                    "Home Workout", "200 + Sessions"
                )
            )

            add(
                SelectWorkoutModel.SelectWorkoutChildModel(
                    R.drawable.place_holder,
                    "Home Workout", "200 + Sessions"
                )
            )
        }


        val selectWorkoutChildItemModel1: MutableList<SelectWorkoutModel.SelectWorkoutChildModel> =
            ArrayList()
        selectWorkoutChildItemModel1.apply {
            add(
                SelectWorkoutModel.SelectWorkoutChildModel(
                    R.drawable.place_holder,
                    "Home Workout", "200 + Sessions"
                )
            )

            add(
                SelectWorkoutModel.SelectWorkoutChildModel(
                    R.drawable.place_holder,
                    "Home Workout", "200 + Sessions"
                )
            )

            add(
                SelectWorkoutModel.SelectWorkoutChildModel(
                    R.drawable.place_holder,
                    "Home Workout", "200 + Sessions"
                )
            )

            add(
                SelectWorkoutModel.SelectWorkoutChildModel(
                    R.drawable.place_holder,
                    "Home Workout", "200 + Sessions"
                )
            )
        }

        val selectWorkoutChildItemModel2: MutableList<SelectWorkoutModel.SelectWorkoutChildModel> =
            ArrayList()
        selectWorkoutChildItemModel2.apply {
            add(
                SelectWorkoutModel.SelectWorkoutChildModel(
                    R.drawable.place_holder,
                    "Home Workout", "200 + Sessions"
                )
            )

            add(
                SelectWorkoutModel.SelectWorkoutChildModel(
                    R.drawable.place_holder,
                    "Home Workout", "200 + Sessions"
                )
            )

            add(
                SelectWorkoutModel.SelectWorkoutChildModel(
                    R.drawable.place_holder,
                    "Home Workout", "200 + Sessions"
                )
            )

            add(
                SelectWorkoutModel.SelectWorkoutChildModel(
                    R.drawable.place_holder,
                    "Home Workout", "200 + Sessions"
                )
            )
        }


        val mainSelectWorkoutModel: MutableList<SelectWorkoutModel> = ArrayList()
        mainSelectWorkoutModel.apply {
            add(
                SelectWorkoutModel(
                    "Full Body",
                    selectWorkoutChildItemModel as ArrayList<SelectWorkoutModel.SelectWorkoutChildModel>
                )
            )

            add(
                SelectWorkoutModel(
                    "Back",
                    selectWorkoutChildItemModel1 as ArrayList<SelectWorkoutModel.SelectWorkoutChildModel>
                )
            )

            add(
                SelectWorkoutModel(
                    "Chest",
                    selectWorkoutChildItemModel2 as ArrayList<SelectWorkoutModel.SelectWorkoutChildModel>
                )
            )

            setSelectWorkoutRV(mainSelectWorkoutModel as ArrayList<SelectWorkoutModel>)
        }
    }


    private fun setSelectWorkoutRV(selectWorkoutModel: ArrayList<SelectWorkoutModel>) {

        val linearLayout = LinearLayoutManager(
            context, LinearLayoutManager.VERTICAL, false
        )

        selectWorkoutBinding.selectWorkoutMainRv.layoutManager = linearLayout

        selectWorkoutBinding.selectWorkoutMainRv.setHasFixedSize(true)

        selectWorkoutAdapter =
            SelectWorkoutParentAdapter(
                selectWorkoutModel,
                context,
                object : SelectWorkoutChildAdapter.SelectWorkoutOnClickListener {
                    override fun onClick(position: Int) {

//                        view?.findNavController()
//                            ?.navigate(R.id.action_selectWorkoutFragment2_to_homeWorkoutFragment)

                        Toast.makeText(
                            requireContext(),
                            "You selected item :- ${position}",
                            Toast.LENGTH_SHORT
                        ).show()


                    }
                })

        selectWorkoutBinding.selectWorkoutMainRv.adapter = selectWorkoutAdapter
    }

}