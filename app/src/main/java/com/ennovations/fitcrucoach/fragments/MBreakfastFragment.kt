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
import com.ennovations.fitcrucoach.adapter.BreakfastAdapter
import com.ennovations.fitcrucoach.databinding.FragmentMBreakfastBinding
import com.ennovations.fitcrucoach.model.BreakfastModel


class MBreakfastFragment : Fragment(), BreakfastAdapter.BreakfastOnClickListener {

    private lateinit var breakfastBinding: FragmentMBreakfastBinding

    private lateinit var breakfastAdapter: BreakfastAdapter

    private lateinit var breakfastModel: ArrayList<BreakfastModel>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        breakfastBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_m_breakfast, container, false)

        getBreakfast()

        return breakfastBinding.root
    }

    private fun getBreakfast() {
        val linearLayout = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        breakfastBinding.breakfastRv.layoutManager = linearLayout

        breakfastBinding.breakfastRv.setHasFixedSize(true)

        breakfastModel = setDataInBreakfastList()

        breakfastAdapter = BreakfastAdapter(breakfastModel, context, this)

        breakfastBinding.breakfastRv.adapter = breakfastAdapter
    }

    private fun setDataInBreakfastList(): ArrayList<BreakfastModel> {
        val breakfast: ArrayList<BreakfastModel> = ArrayList()
        breakfast.apply {
            add(
                BreakfastModel(
                    R.drawable.place_holder,
                    "Peanut Butter Toast",
                    "5.00 PM"
                )
            )

            add(
                BreakfastModel(
                    R.drawable.place_holder,
                    "Peanut Butter Toast",
                    "5.00 PM"
                )
            )

            add(
                BreakfastModel(
                    R.drawable.place_holder,
                    "Peanut Butter Toast",
                    "5.00 PM"
                )
            )

            add(
                BreakfastModel(
                    R.drawable.place_holder,
                    "Peanut Butter Toast",
                    "5.00 PM"
                )
            )

            add(
                BreakfastModel(
                    R.drawable.place_holder,
                    "Peanut Butter Toast",
                    "5.00 PM"
                )
            )

            add(
                BreakfastModel(
                    R.drawable.place_holder,
                    "Peanut Butter Toast",
                    "5.00 PM"
                )
            )

        }

        return breakfast
    }

    override fun onClick(position: Int) {
        Toast.makeText(requireContext(), "You selected item:- $position", Toast.LENGTH_SHORT).show()
    }


}