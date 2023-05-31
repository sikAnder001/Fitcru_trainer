package com.ennovations.fitcrucoach.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.ennovations.fitcrucoach.Network.CustomProgressLoading
import com.ennovations.fitcrucoach.Network.NetworkResult
import com.ennovations.fitcrucoach.Network.TokenManager
import com.ennovations.fitcrucoach.R
import com.ennovations.fitcrucoach.body.LocationUpdateBody
import com.ennovations.fitcrucoach.databinding.FragmentEPLocationBinding
import com.ennovations.fitcrucoach.response.*
import com.ennovations.fitcrucoach.utility.Util
import com.ennovations.fitcrucoach.view_model.LocationViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class EPLocationFragment : Fragment() {

    private lateinit var locationBinding: FragmentEPLocationBinding

    private var TAG = EPLocationFragment::class.java.simpleName

    private lateinit var loading: CustomProgressLoading

    private val locationViewModel by viewModels<LocationViewModel>()

    @Inject
    lateinit var tokenManager: TokenManager

    private var countryList = ArrayList<CountriesResponse.Data>()

    private var stateList = ArrayList<StateResponse.Data>()

    private var cityList = ArrayList<CityResponse.Data>()

    private var country: Int = 0

    private var countryU: Int = 0

    private var state: Int = 0

    private var stateU: Int = 0

    private var city: Int = 0

    private var cityU: Int = 0

    private var countryName = "India"

    private var stateName = ""

    private var cityName = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        locationBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_e_p_location, container, false)

        loading = CustomProgressLoading(requireContext())

        locationViewModel.getCountries()

        locationViewModel.getLocationDetails()

        getLocationDetails()

        observer()

        hittingViews()

        return locationBinding.root
    }

    private fun observer() {

        locationViewModel.countriesResponse.observe(viewLifecycleOwner) {

            locationBinding.countrySpinner.apply {

                loading.hideProgress()

                when (it) {
                    is NetworkResult.Success -> {
                        val body = it.data as CountriesResponse

                        countryList.clear()
                        countryList.addAll(body.data)
                        val adapter: ArrayAdapter<*> =
                            ArrayAdapter<Any?>(
                                requireContext(),
                                android.R.layout.simple_list_item_activated_1,
                                body.data
                            )
                        setAdapter(adapter)
                        if (countryU != 0) {
                            run breaking@{
                                body.data.forEachIndexed { index, element ->

                                    if (element.id == countryU) {
                                        countryU = 0
                                        country = element.id
                                        setSelection(index)
                                        return@breaking
                                    }
                                }
                            }
                        } else {
                            run breaking@{
                                body.data.forEachIndexed { index, element ->
                                    if (element.name.equals(
                                            countryName,
                                            true
                                        ) || element.phonecode == 91
                                    ) {
                                        setSelection(index)
                                        return@breaking
                                    }
                                }
                            }
                        }
                        onItemSelectedListener = object :
                            AdapterView.OnItemSelectedListener {
                            override fun onItemSelected(
                                parent: AdapterView<*>?,
                                view: View,
                                position: Int,
                                id: Long
                            ) {
                                (parent?.getChildAt(0) as TextView).setTextColor(Color.WHITE)
                                country = (parent!!.selectedItem as CountriesResponse.Data).id
                                locationViewModel.getState(country)
                            }

                            override fun onNothingSelected(parent: AdapterView<*>?) {}
                        }

                    }
                    is NetworkResult.Error -> {

                        val body = Util.error(it.data, CountriesResponse::class.java)

                        Toast.makeText(requireContext(), body.message, Toast.LENGTH_LONG)
                            .show()
                    }
                    is NetworkResult.Loading -> {

                        loading.showProgress()

                    }
                }

            }
        }

        locationViewModel.stateResponse.observe(viewLifecycleOwner) {

            locationBinding.stateSpinner.apply {

                when (it) {
                    is NetworkResult.Success -> {
                        val body = it.data as StateResponse

                        stateList.clear()

                        stateList.addAll(body.data)
                        val adapter: ArrayAdapter<*> =
                            ArrayAdapter<Any?>(
                                requireContext(),
                                android.R.layout.simple_list_item_activated_1,
                                body.data
                            )
                        setAdapter(adapter)
                        if (stateU != 0) {
                            run breaking@{
                                body.data.forEachIndexed { index, element ->
                                    if (element.id == stateU) {
                                        stateU = 0
                                        state = element.id
                                        setSelection(index)
                                        return@breaking
                                    }
                                }
                            }
                        } else {
                            run breaking@{
                                body.data.forEachIndexed { index, element ->
                                    if (element.name.equals(
                                            stateName,
                                            true
                                        )
                                    ) {
                                        setSelection(index)
                                        return@breaking
                                    }
                                }
                            }
                        }
                        onItemSelectedListener = object :
                            AdapterView.OnItemSelectedListener {
                            override fun onItemSelected(
                                parent: AdapterView<*>?,
                                view: View,
                                position: Int,
                                id: Long
                            ) {
                                (parent?.getChildAt(0) as TextView).apply {
                                    setTextColor(Color.WHITE)
                                    /*text = "Select State"*/
                                }

                                state = (parent!!.selectedItem as StateResponse.Data).id

                                locationViewModel.getCity(state)
                            }

                            override fun onNothingSelected(parent: AdapterView<*>?) {}
                        }


                    }
                    is NetworkResult.Error -> {

                        val body = Util.error(it.data, StateResponse::class.java)

                        Toast.makeText(requireContext(), body.message, Toast.LENGTH_LONG)
                            .show()
                    }
                    is NetworkResult.Loading -> {
                    }
                }
            }
        }

        locationViewModel.cityResponse.observe(viewLifecycleOwner) {
            locationBinding.citySpinner.apply {

                when (it) {
                    is NetworkResult.Success -> {
                        val body = it.data as CityResponse
                        cityList.clear()
                        cityList.addAll(body.data)
                        val adapter: ArrayAdapter<*> =
                            ArrayAdapter<Any?>(
                                requireContext(),
                                android.R.layout.simple_list_item_activated_1,
                                body.data
                            )
                        setAdapter(adapter)
                        if (cityU != 0) {
                            run breaking@{
                                body.data.forEachIndexed { index, element ->
                                    if (element.id == cityU) {
                                        cityU = 0
                                        city = element.id
                                        setSelection(index)
                                        return@breaking
                                    }
                                }
                            }
                        } else {
                            run breaking@{
                                body.data.forEachIndexed { index, element ->
                                    if (element.name.equals(
                                            cityName,
                                            true
                                        )
                                    ) {
                                        setSelection(index)
                                        return@breaking
                                    }
                                }
                            }
                        }
                        onItemSelectedListener = object :
                            AdapterView.OnItemSelectedListener {
                            override fun onItemSelected(
                                parent: AdapterView<*>?,
                                view: View,
                                position: Int,
                                id: Long
                            ) {

                                (parent?.getChildAt(0) as TextView).apply {
                                    setTextColor(Color.WHITE)
                                    /* text = "Select City"*/
                                }
                                city = (parent!!.selectedItem as CityResponse.Data).id
                            }

                            override fun onNothingSelected(parent: AdapterView<*>?) {}
                        }


                    }
                    is NetworkResult.Error -> {

                        val loginResponse = Util.error(it.data, LoginResponse::class.java)

                        Toast.makeText(requireContext(), loginResponse.message, Toast.LENGTH_LONG)
                            .show()
                    }
                    is NetworkResult.Loading -> {
                    }
                }

            }
        }

    }

    private fun hittingViews() {

        locationBinding.apply {

            saveBtn.setOnClickListener {

                val validation =

                    locationViewModel.validationOnPinCode(pinCodeEt.text.toString().trim())

                if (validation.first) {

                    locationViewModel.updateLocation(
                        LocationUpdateBody(
                            country, state, city, pinCodeEt.text.toString()
                        )
                    )

                } else Util.toast(requireContext(), validation.second)

            }

            locationViewModel.updateLocationResponse.observe(viewLifecycleOwner) {

                loading.hideProgress()

                when (it) {

                    is NetworkResult.Success -> {

                        val body = it.data as LocationUpdateResponse

                        Util.toast(requireContext(), body.message)

                    }
                    is NetworkResult.Error -> {

                        val body = Util.error(it.data, LocationUpdateResponse::class.java)

                        Toast.makeText(requireContext(), body.message, Toast.LENGTH_LONG)
                            .show()
                    }
                    is NetworkResult.Loading -> {

                        loading.showProgress()

                    }
                }
            }

        }

    }

    private fun getLocationDetails() {

        locationViewModel.getLocationDetails.observe(viewLifecycleOwner) {

            loading.hideProgress()

            when (it) {

                is NetworkResult.Success -> {

                    val body = it.data as LocationDetailsResponse

                    locationBinding.apply {


                        (pinCodeEt as TextView).text = body.locations.pincode

                        countryName = body.locations.country

                        stateName = body.locations.state

                        cityName = body.locations.city

                    }

                }
                is NetworkResult.Error -> {

                    val body = Util.error(it.data, LocationDetailsResponse::class.java)

                    Toast.makeText(requireContext(), body.message, Toast.LENGTH_LONG)
                        .show()
                }
                is NetworkResult.Loading -> {

                    loading.showProgress()

                }
            }
        }
    }

}