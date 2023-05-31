package com.ennovations.fitcrucoach.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.ennovations.fitcrucoach.adapter.MyClientAdapter
import com.ennovations.fitcrucoach.databinding.FragmentMyClientBinding
import com.ennovations.fitcrucoach.model.MyClientPjo

class MyClientFragment : Fragment() {

    private lateinit var myClientBinding: FragmentMyClientBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        myClientBinding = FragmentMyClientBinding.inflate(inflater, container, false)

        clientList()

        return myClientBinding.root
    }

    private fun clientList() {
        var myClientAdapter =
            MyClientAdapter(context, object : MyClientAdapter.ClientOnClickListener {
                override fun onClick(position: Int) {

                    Toast.makeText(context, "Clicked", Toast.LENGTH_SHORT).show()

                }
            })
        myClientBinding!!.clientRv!!.adapter = myClientAdapter
        myClient()?.let { myClientAdapter.setList(it) }
    }

    private fun myClient(): ArrayList<MyClientPjo>? {
        val list: ArrayList<MyClientPjo> = ArrayList()
        list.add(MyClientPjo(1, "Prateek", 15, 50, "14 June 2022", "14 June 2022"))
        list.add(MyClientPjo(1, "Rajesh", 13, 30, "16 June 2022", "18 June 2022"))
        list.add(MyClientPjo(1, "Saurabh", 10, 70, "18 June 2022", "12 June 2022"))
        return list
    }


}