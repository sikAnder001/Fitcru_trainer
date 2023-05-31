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
import com.ennovations.fitcrucoach.adapter.SelectClientAdapter
import com.ennovations.fitcrucoach.databinding.FragmentSelectClientBinding
import com.ennovations.fitcrucoach.model.SelectClientModel


class SelectClientFragment : Fragment(), SelectClientAdapter.SelectClientOnClickListener {

    private lateinit var clientBinding: FragmentSelectClientBinding

    private lateinit var clientAdapter: SelectClientAdapter

    private lateinit var clientModel: ArrayList<SelectClientModel>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        clientBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_select_client, container, false)

        getClient()

        return clientBinding.root
    }

    private fun getClient() {
        val linearLayout = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        clientBinding.clientRv.layoutManager = linearLayout

        clientBinding.clientRv.setHasFixedSize(true)

        clientModel = setDataInClientList()

        clientAdapter = SelectClientAdapter(clientModel, context, this)

        clientBinding.clientRv.adapter = clientAdapter
    }

    private fun setDataInClientList(): ArrayList<SelectClientModel> {
        val client: ArrayList<SelectClientModel> = ArrayList()
        client.apply {
            add(
                SelectClientModel(
                    R.drawable.place_holder,
                    "Prashant S"
                )
            )

            add(
                SelectClientModel(
                    R.drawable.place_holder,
                    "Virendra R"
                )
            )

            add(
                SelectClientModel(
                    R.drawable.place_holder,
                    "Harsh M"
                )
            )

            add(
                SelectClientModel(
                    R.drawable.place_holder,
                    "Prashant S"
                )
            )
            add(
                SelectClientModel(
                    R.drawable.place_holder,
                    "Prashant S"
                )
            )

            add(
                SelectClientModel(
                    R.drawable.place_holder,
                    "Virendra R"
                )
            )

            add(
                SelectClientModel(
                    R.drawable.place_holder,
                    "Harsh M"
                )
            )

            add(
                SelectClientModel(
                    R.drawable.place_holder,
                    "Prashant S"
                )
            )
            add(
                SelectClientModel(
                    R.drawable.place_holder,
                    "Prashant S"
                )
            )

            add(
                SelectClientModel(
                    R.drawable.place_holder,
                    "Virendra R"
                )
            )

            add(
                SelectClientModel(
                    R.drawable.place_holder,
                    "Harsh M"
                )
            )

            add(
                SelectClientModel(
                    R.drawable.place_holder,
                    "Prashant S"
                )
            )

        }

        return client
    }

    override fun onClick(position: Int) {

        Toast.makeText(requireContext(), "You selected item :- $position", Toast.LENGTH_SHORT)
            .show()

    }

}