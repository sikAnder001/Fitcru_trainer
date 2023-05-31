package com.ennovations.fitcrucoach.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ennovations.fitcrucoach.fragments.*

class EditProfilePageAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
    override fun getItemCount(): Int {
        return 5
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> EPBasicFragment()
            1 -> EPLocationFragment()
            2 -> EPDocVerificationFragment()
            3 -> EPSpecializationFragment()
            else -> EPChangePasswordFragment()
        }
    }
}