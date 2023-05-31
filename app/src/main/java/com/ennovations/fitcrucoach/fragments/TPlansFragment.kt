package com.ennovations.fitcrucoach.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.ennovations.fitcrucoach.R
import com.ennovations.fitcrucoach.adapter.PlansAdapter
import com.ennovations.fitcrucoach.databinding.FragmentTPlansBinding
import com.ennovations.fitcrucoach.model.PlansModel


class TPlansFragment : Fragment(), PlansAdapter.PlansOnClickListener {

    private lateinit var planBinding: FragmentTPlansBinding

    private lateinit var plansAdapter: PlansAdapter

    private lateinit var plansModel: ArrayList<PlansModel>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        planBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_t_plans, container, false)

        getPlansData()

        return planBinding.root
    }

    private fun getPlansData() {
        val linearLayout = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        planBinding.plansRv.layoutManager = linearLayout

        planBinding.plansRv.setHasFixedSize(true)

        plansModel = setDataInPlaList()

        plansAdapter = PlansAdapter(plansModel, this, context)

        planBinding.plansRv.adapter = plansAdapter
    }

    private fun setDataInPlaList(): ArrayList<PlansModel> {
        val plans: ArrayList<PlansModel> = ArrayList()
        plans.apply {
            add(
                PlansModel(
                    R.drawable.place_holder,
                    "Prateek Kumar",
                    "12 Dec 2022",
                    "12 Dec 2022"
                )
            )

            add(
                PlansModel(
                    R.drawable.place_holder,
                    "Prateek Kumar",
                    "12 Dec 2022",
                    "12 Dec 2022"
                )
            )

            add(
                PlansModel(
                    R.drawable.place_holder,
                    "Prateek Kumar",
                    "12 Dec 2022",
                    "12 Dec 2022"
                )
            )

            add(
                PlansModel(
                    R.drawable.place_holder,
                    "Prateek Kumar",
                    "12 Dec 2022",
                    "12 Dec 2022"
                )
            )

            add(
                PlansModel(
                    R.drawable.place_holder,
                    "Prateek Kumar",
                    "12 Dec 2022",
                    "12 Dec 2022"
                )
            )

            add(
                PlansModel(
                    R.drawable.place_holder,
                    "Prateek Kumar",
                    "12 Dec 2022",
                    "12 Dec 2022"
                )
            )
        }

        return plans
    }

    override fun onClick(position: Int) {
        /* view?.findNavController()
             ?.navigate(R.id.action_TPlansFragment_to_planDetailsFragment)
 */
        Toast.makeText(requireContext(), "You clicked on item :- ${position}", Toast.LENGTH_SHORT)
            .show()

    }

}