package com.ennovations.fitcrucoach.adapter

import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ennovations.fitcrucoach.R
import com.ennovations.fitcrucoach.activities.ChatActivity
import com.ennovations.fitcrucoach.databinding.ClientWorkoutRvItemBinding
import com.ennovations.fitcrucoach.response.ClientListResponse
import com.ennovations.fitcrucoach.utility.SessionManager
import java.text.SimpleDateFormat

class CWorkoutAdapter(

    private val context: Context?,

    private val listener: ClientWorkoutOnClickListener

) :
    RecyclerView.Adapter<CWorkoutAdapter.ViewHolder>() {

    private var list = listOf<ClientListResponse.Client>()

    inner class ViewHolder(cWorkoutRvItemBinding: ClientWorkoutRvItemBinding) :
        RecyclerView.ViewHolder(cWorkoutRvItemBinding.root) {

        var cWorkoutRvItemBinding = cWorkoutRvItemBinding

        @RequiresApi(Build.VERSION_CODES.N)
        fun bind(workoutModel: ClientListResponse.Client, position: Int) {

            cWorkoutRvItemBinding.apply {

                Glide.with(context!!).load(workoutModel.image_url)
                    .placeholder(R.drawable.place_holder)
                    .into(clientImage)

                clientName.text = workoutModel.name
                if (workoutModel.join != null) {
                    val joinDate = changeFormat(workoutModel.join)
                    joinTv.text = joinDate
//                    joinTv.text = workoutModel.join

                    Log.e("TAG", "bind: ${workoutModel.join}")
                }

                if (workoutModel.expire != null) {
                    val expireDate = changeFormat(workoutModel.expire)
                    expireTv.text = expireDate
                    //                  expireTv.text = workoutModel.expire
                }
                statusT.text = workoutModel.status

                chat.setOnClickListener {
                    context.startActivity(Intent(context, ChatActivity::class.java).apply {
                        putExtra("user_name", workoutModel.name)
                        putExtra("user_profile", workoutModel.image_url)
                        putExtra("coach_id", SessionManager.getCoachDetails().id)
                        putExtra("user_id", workoutModel.user_id)
                    })
                }
            }

            itemView.setOnClickListener {

                val position = adapterPosition

                listener.onClick(workoutModel.user_id!!)

            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun changeFormat(join: String?): String {
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
        val dateTime = simpleDateFormat.parse(join)
        val simpleDateFormat1 = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            SimpleDateFormat("dd MMM YYYY")
        } else {
            TODO("VERSION.SDK_INT < N")
        }
        val date = simpleDateFormat1.format(dateTime)

        return date
    }

    fun setList(list: List<ClientListResponse.Client>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CWorkoutAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        val cWorkoutRvItemBinding = ClientWorkoutRvItemBinding.inflate(inflater, parent, false)

        return ViewHolder(cWorkoutRvItemBinding)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onBindViewHolder(holder: CWorkoutAdapter.ViewHolder, position: Int) =
        holder.bind(list[position], position)


    override fun getItemCount(): Int {
        return try {
            list.size
        } catch (e: Exception) {
            0
        }
    }

    interface ClientWorkoutOnClickListener {

        fun onClick(user_id: Int)

    }

}
// android:layout_marginStart="5dp"
// android:layout_marginEnd="2dp"
// android:layout_weight="1.1"