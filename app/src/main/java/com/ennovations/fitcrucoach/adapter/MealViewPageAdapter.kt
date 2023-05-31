package com.ennovations.fitcrucoach.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ennovations.fitcrucoach.fragments.MBreakfastFragment
import com.ennovations.fitcrucoach.fragments.MDinnerFragment
import com.ennovations.fitcrucoach.fragments.MLunchFragment

class MealViewPageAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> MBreakfastFragment()
            1 -> MLunchFragment()
            else -> MDinnerFragment()
        }
    }
}