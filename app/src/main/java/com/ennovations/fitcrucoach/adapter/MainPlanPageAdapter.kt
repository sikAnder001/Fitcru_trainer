package com.ennovations.fitcrucoach.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ennovations.fitcrucoach.fragments.ConsultationFormFragment
import com.ennovations.fitcrucoach.fragments.HealthQuestionnaireFragment
import com.ennovations.fitcrucoach.fragments.PlanDetailsFragment

class MainPlanPageAdapter(fa: FragmentActivity, val arg: Int) : FragmentStateAdapter(fa) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> PlanDetailsFragment(arg)
            1 -> ConsultationFormFragment(arg)
            else -> HealthQuestionnaireFragment(arg)
        }
    }
}