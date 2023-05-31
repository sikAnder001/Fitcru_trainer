package com.ennovations.fitcrucoach.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.ennovations.fitcrucoach.R
import com.ennovations.fitcrucoach.adapter.EditProfilePageAdapter
import com.ennovations.fitcrucoach.databinding.FragmentEditProfileBinding
import com.google.android.material.tabs.TabLayoutMediator

class EditProfileFragment : Fragment() {

    private lateinit var editProfileBinding: FragmentEditProfileBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        editProfileBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_edit_profile, container, false)

        editProfileBinding.apply {
            backBtn.setOnClickListener { requireActivity().onBackPressed() }

            editProfileViewPager.adapter = EditProfilePageAdapter(requireActivity())

            TabLayoutMediator(
                editProfileTabLayout,
                editProfileViewPager,
                true,
                false
            ) { tab, position ->
                tab.text = arrayOf(
                    "Basic",
                    "Location",
                    "My Verification",
                    "Specializations",
                    "Change Password"
                )[position]
            }.attach()


        }

        return editProfileBinding.root
    }


}