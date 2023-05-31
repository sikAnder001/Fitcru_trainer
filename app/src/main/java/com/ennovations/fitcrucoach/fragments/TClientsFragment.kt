package com.ennovations.fitcrucoach.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.ennovations.fitcrucoach.R
import com.ennovations.fitcrucoach.adapter.ClientsPageAdapter
import com.ennovations.fitcrucoach.databinding.FragmentTClientsBinding
import com.ennovations.fitcrucoach.model.FilterModel
import com.google.android.material.tabs.TabLayoutMediator


class TClientsFragment : Fragment() {

    private lateinit var clientBinding: FragmentTClientsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        clientBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_t_clients, container, false)

        clientBinding.apply {

            clientViewPager.adapter = ClientsPageAdapter(requireActivity())

            TabLayoutMediator(clientTabLayout, clientViewPager) { tab, position ->
                tab.text = arrayOf(" Workout ", " Diet ")[position]
            }.attach()

            hittingViews()

        }

        return clientBinding.root
    }

    private fun addDataToAdapter(): ArrayList<FilterModel> {
        val filter: ArrayList<FilterModel> = ArrayList()
        filter.apply {
            add(
                FilterModel(
                    "3 Months"
                )
            )

            add(
                FilterModel(
                    "6 Months"
                )
            )

            add(
                FilterModel(
                    "Challenges"
                )
            )

            add(
                FilterModel(
                    "All"
                )
            )
        }
        return filter
    }

    private fun hittingViews() {

        clientBinding.apply {

        }
    }
}