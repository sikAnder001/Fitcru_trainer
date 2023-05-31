package com.ennovations.fitcrucoach.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.ennovations.fitcrucoach.R
import com.ennovations.fitcrucoach.databinding.FragmentTermsAndPrivacyBinding

class TermsAndPrivacyFragment : Fragment() {

    private lateinit var termsBinding: FragmentTermsAndPrivacyBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        termsBinding = FragmentTermsAndPrivacyBinding.inflate(inflater, container, false)

        getDetail()

        return termsBinding.root
    }

    private fun getDetail() {
        termsBinding.apply {
            val b = arguments

            var which = b?.getInt("w", 0)

            when (which) {
                1 -> {

                    tvName.text = "Terms & Conditions"

                    pdfView.fromAsset("fitCru_terms_of_use.pdf").load()

                    goBackBtn.setOnClickListener {
                        view?.findNavController()
                            ?.navigate(R.id.action_termsAndPrivacyFragment_to_myProfileFragment)
                    }

                }
                2 -> {
                    tvName.text = "Privacy Policy"

                    pdfView.fromAsset("fitCru_privacy_policy.pdf").load()

                    goBackBtn.setOnClickListener {
                        view?.findNavController()
                            ?.navigate(R.id.action_termsAndPrivacyFragment_to_myProfileFragment)
                    }
                }
                3 -> {

                    tvName.text = "Terms & Conditions"

                    pdfView.fromAsset("fitCru_terms_of_use.pdf").load()

                    goBackBtn.setOnClickListener {
                        view?.findNavController()
                            ?.navigate(R.id.action_termsAndPrivacyFragment_to_loginFragment)
                    }

                }
                4 -> {
                    tvName.text = "Privacy Policy"

                    pdfView.fromAsset("fitCru_privacy_policy.pdf").load()

                    goBackBtn.setOnClickListener {
                        view?.findNavController()
                            ?.navigate(R.id.action_termsAndPrivacyFragment_to_loginFragment)
                    }
                }
                else -> {
                }
            }
        }
    }
}