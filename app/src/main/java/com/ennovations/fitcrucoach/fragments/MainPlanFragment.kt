package com.ennovations.fitcrucoach.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.ennovations.fitcrucoach.R
import com.ennovations.fitcrucoach.adapter.MainPlanPageAdapter
import com.ennovations.fitcrucoach.databinding.FragmentMainPlanBinding
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_main_plan.*

class MainPlanFragment : Fragment() {

    private lateinit var mainPlanBinding: FragmentMainPlanBinding
    private var position = 0
    private var arg = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mainPlanBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_main_plan, container, false)

        backPress()

        hittingViews()

        return mainPlanBinding.root
    }

    private fun backPress() {
        requireActivity()
            .onBackPressedDispatcher
            .addCallback(requireActivity(), object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    /* val bundle = Bundle()
                     bundle.putInt("user_id", arg)*/
                    view?.findNavController()
                        ?.navigate(R.id.action_mainPlanFragment_to_CWorkoutFragment2 /*bundle*/)
                }
            }
            )

    }

    private fun hittingViews() {

        /* try {
             position = requireArguments().getInt("position", 0)
         } catch (e: Exception) {
             position = 0
         }*/

        mainPlanBinding.apply {

            arg = requireArguments().getInt("user_id", 0)

            backBtn.setOnClickListener {
                requireActivity().onBackPressed()
            }

            clientDetailsViewPager.adapter = MainPlanPageAdapter(requireActivity(), arg)

            TabLayoutMediator(
                clientDetailsTabLayout,
                clientDetailsViewPager

            ) { tab, position ->
                tab.text =
                    arrayOf("Profile", "Consultation Form", "Health Questionnaire")[position]
            }.attach()


            if (position != 0)
                client_details_tabLayout.getTabAt(arg)!!.select()
            //  client_details_viewPager.isUserInputEnabled = false
        }

    }
}