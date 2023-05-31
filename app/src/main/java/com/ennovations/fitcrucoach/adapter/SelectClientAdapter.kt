package com.ennovations.fitcrucoach.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ennovations.fitcrucoach.databinding.ClientRvItemBinding
import com.ennovations.fitcrucoach.model.SelectClientModel

class SelectClientAdapter(

    private val clientModel: ArrayList<SelectClientModel>,

    private val context: Context?,

    private val listener: SelectClientOnClickListener

) : RecyclerView.Adapter<SelectClientAdapter.ViewHolder>() {

    inner class ViewHolder(clientBinding: ClientRvItemBinding) :
        RecyclerView.ViewHolder(clientBinding.root) {

        var clientBinding = clientBinding

        fun bind(
            clientModel: SelectClientModel,
            position: Int
        ) {
            clientBinding.apply {

                Glide.with(context!!).load(clientModel.clientImg)
                    .into(clientImage)

                clientName.text = clientModel.clientName

            }

            itemView.setOnClickListener {

                val position = adapterPosition

                listener.onClick(position)

            }

        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SelectClientAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        val clientBinding =
            ClientRvItemBinding.inflate(inflater, parent, false)

        return ViewHolder(clientBinding)
    }

    override fun onBindViewHolder(holder: SelectClientAdapter.ViewHolder, position: Int) {
        holder.bind(clientModel[position], position)
    }

    override fun getItemCount(): Int {
        return clientModel.size
    }

    interface SelectClientOnClickListener {

        fun onClick(position: Int)

    }
}