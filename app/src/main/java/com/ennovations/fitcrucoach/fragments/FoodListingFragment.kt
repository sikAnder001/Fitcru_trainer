package com.ennovations.fitcrucoach.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.ennovations.fitcrucoach.Network.NetworkResult
import com.ennovations.fitcrucoach.Network.TokenManager
import com.ennovations.fitcrucoach.R
import com.ennovations.fitcrucoach.adapter.MealDetailAdapter
import com.ennovations.fitcrucoach.databinding.FragmentFoodListingfragmentBinding
import com.ennovations.fitcrucoach.response.MealTypeDetailResponse
import com.ennovations.fitcrucoach.utility.Util
import com.ennovations.fitcrucoach.view_model.ClientListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_food_listingfragment.*
import javax.inject.Inject

@AndroidEntryPoint
class FoodListingFragment : Fragment() {

    private lateinit var binding: FragmentFoodListingfragmentBinding
    private var meal_type_id: Int = 0
    private var userId: Int = 0
    private var date: String = ""

    private val clientListViewModel by viewModels<ClientListViewModel>()

    @Inject
    lateinit var tokenManager: TokenManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =
            FragmentFoodListingfragmentBinding.inflate(inflater, container, false)

        meal_type_id =
            requireArguments().getInt("data", 0)!!
        userId =
            requireArguments().getInt("user_id", 0)
        date =
            requireArguments().getString("date").toString()

        setRecyclerViewFoodInit()

        binding.gobackbtn.setOnClickListener {
            requireActivity().onBackPressed()
        }
        return binding.root
    }

    private fun setRecyclerViewFoodInit() {

        try {
            clientListViewModel.getMealDetail(meal_type_id, userId, date)
            clientListViewModel.mealDetail.observe(viewLifecycleOwner) {
                //hide progress

                when (it) {
                    is NetworkResult.Success -> {
                        val response = it.data as MealTypeDetailResponse
                        getDetail(response.data)
                    }
                    is NetworkResult.Error -> {
                        val response = Util.error(it.data, MealTypeDetailResponse::class.java)
                        Toast.makeText(context, response.message, Toast.LENGTH_LONG).show()
                    }
                    is NetworkResult.Loading -> {
                        //show progress

                    }
                }
            }


        } catch (e: Exception) {
        }
    }

    private fun getDetail(data: MealTypeDetailResponse.Data) {
        binding.apply {
            Glide.with(requireContext()).load(data.food_data[0].food.image_url)
                .placeholder(R.drawable.place_holder)
                .into(this!!.iv)
        }

        mealtypeTV.text = data.food_data[0].meal_type.mealtype

        binding!!.rvMealDetail.adapter =
            MealDetailAdapter(
                data.food_data,
                requireContext(),
            )
        binding!!.rvMealDetail.adapter!!.notifyDataSetChanged()

        try {
            val carbPro =
                ((data.benefits.consume_carbs * 100) / data.benefits.carbs).toFloat()
            progressCarb.progress = carbPro

/*
                    val totalCarbGram = (data.benefits.carbs * 0.129598).toInt()
                    val consumeCarbGram = (data.benefits.consume_carbs * 0.129598).toInt() */
            val leftCarb = (data.benefits.carbs - data.benefits.consume_carbs)
            conCarbGTv.text = ("${leftCarb}g")
            totalCarbGTv.text = ("of ${data.benefits.carbs}g left")
        } catch (e: Exception) {
            progressCarb.progress = 0f
            conCarbGTv.text = ("0g")
            totalCarbGTv.text = ("of 0g left")

        }
        try {
            val proteinPro =
                ((data.benefits.consume_protein * 100) / data.benefits.protein).toFloat()
            progressProtein.progress = proteinPro

            /* val totalProGram = (data.benefits.protein * 0.129598).toInt()
             val consumeProGram = (data.benefits.consume_protein * 0.129598).toInt() */
            val leftPro = (data.benefits.protein - data.benefits.consume_protein)
            consumeProGTv.text = ("${leftPro}g")
            totalProGTv.text = ("of ${data.benefits.protein}g left")

        } catch (e: Exception) {
            progressProtein.progress = 0f
            consumeProGTv.text = ("0g")
            totalProGTv.text = ("of 0g left")

        }
        try {
            val fatPro = ((data.benefits.consume_fat * 100) / data.benefits.fat).toFloat()
            progressFat.progress = fatPro

            /* val totalFatGram = (data.benefits.fat * 0.129598).toInt()
             val consumeFatGram = (data.benefits.consume_fat * 0.129598).toInt() */
            val leftFat = (data.benefits.fat - data.benefits.consume_fat)
            consumeFatGtV.text = ("${leftFat}g")
            totalFatGTv.text = ("of ${data.benefits.fat}g left")

        } catch (e: Exception) {
            progressFat.progress = 0f
            consumeFatGtV.text = ("0g")
            totalFatGTv.text = ("of 0g left")

        }

    }

}