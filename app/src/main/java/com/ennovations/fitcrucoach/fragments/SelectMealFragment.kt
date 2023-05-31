package com.ennovations.fitcrucoach.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.ennovations.fitcrucoach.R
import com.ennovations.fitcrucoach.adapter.MealViewPageAdapter
import com.ennovations.fitcrucoach.databinding.FragmentSelectMealBinding
import com.google.android.material.tabs.TabLayoutMediator

class SelectMealFragment : Fragment() {

    private lateinit var mealBinding: FragmentSelectMealBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mealBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_select_meal, container, false)
        mealBinding.apply {
            mealViewPager.adapter = MealViewPageAdapter(requireActivity())
            TabLayoutMediator(mealTabLayout, mealViewPager) { tab, position ->
                tab.text = arrayOf(" Breakfast ", " Lunch ", " Dinner ")[position]
            }.attach()
        }

        return mealBinding.root
    }


}