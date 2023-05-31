package com.ennovations.fitcrucoach.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ennovations.fitcrucoach.fragments.CWorkoutFragment

class ClientsPageAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
    override fun getItemCount(): Int {
        return 1
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> CWorkoutFragment()
            /* 1 -> CDietFragment()*/
            else -> CWorkoutFragment()
        }
    }
}