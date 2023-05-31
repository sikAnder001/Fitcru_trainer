package com.ennovations.fitcrucoach.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.devstune.searchablemultiselectspinner.SearchableItem
import com.devstune.searchablemultiselectspinner.SearchableMultiSelectSpinner
import com.devstune.searchablemultiselectspinner.SelectionCompleteListener
import com.ennovations.fitcrucoach.Network.CustomProgressLoading
import com.ennovations.fitcrucoach.Network.NetworkResult
import com.ennovations.fitcrucoach.Network.TokenManager
import com.ennovations.fitcrucoach.R
import com.ennovations.fitcrucoach.body.SpecializationUpdateBody
import com.ennovations.fitcrucoach.databinding.FragmentEPSpecializationBinding
import com.ennovations.fitcrucoach.response.*
import com.ennovations.fitcrucoach.utility.Util
import com.ennovations.fitcrucoach.view_model.SpecializationsViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class EPSpecializationFragment : Fragment() {
    private lateinit var specializationBinding: FragmentEPSpecializationBinding

    private var TAG = EPSpecializationFragment::class.java.simpleName

    private lateinit var loading: CustomProgressLoading

    private var items: MutableList<SearchableItem> = ArrayList()

    private var items1: MutableList<SearchableItem> = ArrayList()

    private var items2: MutableList<SearchableItem> = ArrayList()

    private val specializationsViewModel by viewModels<SpecializationsViewModel>()

    @Inject
    lateinit var tokenManager: TokenManager

    private var which = 0

    private var lName = ""

    private var lId = ""

    private var sName = ""

    private var sId = ""

    private var aName = ""

    private var aId = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        specializationBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_e_p_specialization,
            container,
            false
        )

        loading = CustomProgressLoading(requireContext())

        specializationsViewModel.getSpecializationDetails()

        getDetails()

        hittingViews()

        return specializationBinding.root
    }

    private fun getDetails() {
        specializationsViewModel.getSpecializationDetails.observe(viewLifecycleOwner) {

            loading.hideProgress()

            when (it) {
                is NetworkResult.Success -> {

                    val response = it.data!! as SpecializationDetailsResponse

                    specializationsViewModel.getLanguages()
                    specializationsViewModel.getSpecialization()
                    specializationsViewModel.getAssociation()

                    if (response.Specializations?.get(0)!!.language_ids != null) {
                        lId = response.Specializations?.get(0)?.language_ids!!
                    }
                    if (response.Specializations?.get(0)!!.coach_specialization_ids != null) {
                        sId = response.Specializations?.get(0)?.coach_specialization_ids!!
                    }
                    if (response.Specializations?.get(0)!!.gym_association_ids != null) {
                        aId = response.Specializations?.get(0)!!.gym_association_ids!!
                    }


                    val list = lId.split(",").toTypedArray()
                    val list1 = sId.split(",").toTypedArray()
                    val list2 = aId.split(",").toTypedArray()

                    var languageData = ""
                    var specializationData = ""
                    var associationData = ""

                    specializationBinding.apply {

                        spinner2(languageSpinner, items, 1, list, "Select Language")

                        spinner2(specializationSpinner, items1, 2, list1, "Select Specialization")

                        spinner2(gymAssociationSpinner, items2, 3, list2, "Select Gym Association")

                        (qualificationBox as TextView).text =
                            response.Specializations[0].coach_qualifications

                        specializationsViewModel.languagesResponse.observe(viewLifecycleOwner) {

                            loading.hideProgress()

                            specializationBinding.languageSpinner.apply {

                                which = 1

                                when (it) {

                                    is NetworkResult.Success -> {
                                        val languageResponse = it.data!! as LanguagesResponse

                                        languageResponse.Languages!!.forEach {
                                            lName = it.name
                                            items.add(SearchableItem(lName, it.id.toString()))
                                        }
                                        for (i in list) {
                                            for (j in items) {
                                                if (i == j.code) {
                                                    languageData += "${j.text},"
                                                    languageSpinner.text = languageData.replace(
                                                        ",$".toRegex(),
                                                        ""
                                                    )
                                                }
                                            }
                                        }

                                    }
                                    is NetworkResult.Error -> {

                                        val languageResponse =
                                            Util.error(it.data, LanguagesResponse::class.java)

                                        Toast.makeText(
                                            requireContext(),
                                            languageResponse.message,
                                            Toast.LENGTH_LONG
                                        )
                                            .show()
                                    }
                                    is NetworkResult.Loading -> {

                                        loading.showProgress()

                                    }
                                }

                            }
                        }

                        specializationsViewModel.specialization.observe(viewLifecycleOwner) {

                            specializationBinding.specializationSpinner.apply {

                                which = 2

                                when (it) {

                                    is NetworkResult.Success -> {
                                        val special = it.data!! as SpecializationResponse

                                        special.Specializations!!.forEach {
                                            sName = it.name
                                            items1.add(SearchableItem(sName, it.id.toString()))
                                        }
                                        for (i in list1) {
                                            for (j in items1) {
                                                if (i == j.code) {
                                                    specializationData += "${j.text},"
                                                    specializationSpinner.text =
                                                        specializationData.replace(
                                                            ",$".toRegex(),
                                                            ""
                                                        )
                                                }
                                            }
                                        }
                                    }
                                    is NetworkResult.Error -> {

                                        val languageResponse =
                                            Util.error(it.data, LanguagesResponse::class.java)

                                        Toast.makeText(
                                            requireContext(),
                                            languageResponse.message,
                                            Toast.LENGTH_LONG
                                        )
                                            .show()
                                    }
                                    is NetworkResult.Loading -> {
                                    }
                                }
                            }
                        }

                        specializationsViewModel.association.observe(viewLifecycleOwner) {

                            specializationBinding.gymAssociationSpinner.apply {

                                which = 3

                                when (it) {

                                    is NetworkResult.Success -> {
                                        val special = it.data!! as AssociationResponse

                                        special.Gym_Associations?.forEach {
                                            aName = it.gym_name
                                            items2.add(SearchableItem(aName, it.id.toString()))
                                        }
                                        for (i in list2) {
                                            for (j in items2) {
                                                if (i == j.code) {
                                                    associationData += "${j.text},"
                                                    gymAssociationSpinner.text =
                                                        associationData.replace(
                                                            ",$".toRegex(),
                                                            ""
                                                        )
                                                }
                                            }
                                        }

                                    }
                                    is NetworkResult.Error -> {

                                        val languageResponse =
                                            Util.error(it.data, LanguagesResponse::class.java)

                                        Toast.makeText(
                                            requireContext(),
                                            languageResponse.message,
                                            Toast.LENGTH_LONG
                                        )
                                            .show()
                                    }
                                    is NetworkResult.Loading -> {
                                    }
                                }
                            }
                        }

                    }
                }
                is NetworkResult.Error -> {

                    val response = Util.error(it.data, SpecializationDetailsResponse::class.java)

                    Toast.makeText(requireContext(), response.message, Toast.LENGTH_LONG)
                        .show()
                }
                is NetworkResult.Loading -> {
                    loading.showProgress()
                }
            }
        }
    }

    private fun spinner(textView: TextView, items: MutableList<SearchableItem>, i: Int, s: String) {
        textView.setOnClickListener {
            which = i
            SearchableMultiSelectSpinner.show(
                requireContext(),
                "${s}                                                                ",
                "Done",
                items, selectionCompleteListener =
                object :
                    SelectionCompleteListener {
                    override fun onCompleteSelection(selectedItems: ArrayList<SearchableItem>) {

                        var data = ""

                        var ids = ""

                        for ((x, v) in selectedItems.withIndex()) {
                            data += " ${v.text},"
                            ids += "${v.code},"
                        }

                        textView.text = data.replace(
                            ",$".toRegex(),
                            ""
                        )

                        if (which == 1) {
                            lName = data
                            lId = ids
                        }

                        if (which == 2) {
                            sName = data
                            sId = ids
                        }

                        if (which == 3) {
                            aName = data
                            aId = ids
                        }

                    }

                }
            )
        }
    }

    private fun spinner2(
        textView: TextView,
        items: MutableList<SearchableItem>,
        i: Int,
        list: Array<String>,
        s: String
    ) {
        textView.setOnClickListener {
            which = i
            SearchableMultiSelectSpinner.show(
                requireContext(),
                "$s                                                                ",
                "Done",
                items,
                list,
                object :
                    SelectionCompleteListener {
                    override fun onCompleteSelection(selectedItems: ArrayList<SearchableItem>) {

                        var data = ""
                        var ids = ""
                        for ((x, v) in selectedItems.withIndex()) {
                            data += " ${v.text},"
                            ids += "${v.code},"
                        }

                        val langId = ids

                        val chars: Array<String> = langId.split(",").toTypedArray()

                        Log.e(TAG, "getDetails1: $chars")

                        textView.text = data.replace(
                            ",$".toRegex(),
                            ""
                        )

                        if (which == 1) {
                            lName = data
                            lId = ids
                        }

                        if (which == 2) {
                            sName = data
                            sId = ids
                        }

                        if (which == 3) {
                            aName = data
                            aId = ids
                        }

                    }

                }
            )
        }
    }

    private fun hittingViews() {
        specializationBinding.apply {

            spinner(languageSpinner, items, 1, "Select Language")

            spinner(specializationSpinner, items1, 2, "Select Specialization")

            spinner(gymAssociationSpinner, items2, 3, "Select Gym Association")

            saveBtn.setOnClickListener {

                val validation = specializationsViewModel.validationOnQualification(
                    languageSpinner.text.toString(),

                    specializationSpinner.text.toString(),

                    gymAssociationSpinner.text.toString(),

                    qualificationBox.text.trim().toString()
                )

                if (validation.first) {
                    specializationsViewModel.updateSpecializationDetails(
                        SpecializationUpdateBody(
                            lId,
                            sId,
                            aId,
                            /*lName,
                            sName,
                            aName,*/
                            qualificationBox.text.trim().toString()
                        )
                    )

                } else {
                    Util.toast(requireContext(), validation.second)
                }
            }

            specializationsViewModel.updateSpecializationDetails.observe(viewLifecycleOwner) {

                loading.hideProgress()

                when (it) {
                    is NetworkResult.Success -> {

                        val response = it.data as SpecializationUpdateResponse

                        Util.toast(requireContext(), response.message)

                    }
                    is NetworkResult.Error -> {

                        val response = Util.error(it.data, SpecializationUpdateResponse::class.java)

                        Toast.makeText(requireContext(), response.message, Toast.LENGTH_LONG)
                            .show()
                    }
                    is NetworkResult.Loading -> {
                        loading.showProgress()
                    }
                }
            }
        }
    }

}