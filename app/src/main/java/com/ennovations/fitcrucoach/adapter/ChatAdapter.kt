package com.ennovations.fitcrucoach.adapter

import android.app.Activity
import android.content.Intent
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ennovations.fitcrucoach.activities.ChatActivity
import com.ennovations.fitcrucoach.databinding.ChatRvItemBinding
import com.ennovations.fitcrucoach.response.ChatListResponse
import com.ennovations.fitcrucoach.utility.SessionManager
import com.ennovations.fitcrucoach.utility.Util
import java.text.SimpleDateFormat
import java.util.*

class ChatAdapter(

    private val activity: Activity?,

    private val listener: ChatOnClickListener

) : RecyclerView.Adapter<ChatAdapter.ViewHolder>() {

    private var list = ArrayList<ChatListResponse.Data>()

    inner class ViewHolder(chatItemBinding: ChatRvItemBinding) :
        RecyclerView.ViewHolder(chatItemBinding.root) {

        var chatItemBinding = chatItemBinding

        fun bind(
            chatModel: ChatListResponse.Data,
            position: Int
        ) {
            chatItemBinding.apply {

                Util.loadImage(activity?.applicationContext!!, personImage, chatModel.image_url)

                personName.text = chatModel.name

                chatHint.text = chatModel.last_message

                time.text = time(chatModel.updated_at)

                date.text = time2(chatModel.updated_at)

            }

            itemView.setOnClickListener {
                activity?.startActivity(Intent(activity, ChatActivity::class.java).apply {
                    putExtra("user_name", chatModel.name)
                    putExtra("user_profile", chatModel.image_url)
                    putExtra("user_id", chatModel.id)
                    putExtra("coach_id", SessionManager.getCoachDetails().id)
                })

            }
        }

    }

    private fun time(string: String): String {
        val input = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val dateValue: Date = input.parse(string)
        val output = SimpleDateFormat("hh:mm a");
        return output.format(dateValue)
    }

    private fun time2(string: String): String {
        val input = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val dateValue: Date = input.parse(string)
        val messageTime = Calendar.getInstance()
        messageTime.time = dateValue
        val now = Calendar.getInstance()
        val strTimeFormat = "h:mm aa"
        val strDateFormat = "dd/MM/yyyy"
        return if (now[Calendar.DATE] === messageTime.get(Calendar.DATE) &&
            now[Calendar.MONTH] === messageTime.get(Calendar.MONTH)
            &&
            now[Calendar.YEAR] === messageTime.get(Calendar.YEAR)
        ) {
            "Today" // + DateFormat.format(strTimeFormat, messageTime)
        } else if (now[Calendar.DATE] - messageTime.get(Calendar.DATE) === 1
            &&
            now[Calendar.MONTH] === messageTime.get(Calendar.MONTH)
            &&
            now[Calendar.YEAR] === messageTime.get(Calendar.YEAR)
        ) {
            "Yesterday" // + DateFormat.format(strTimeFormat, messageTime)
        } else {
            "" + DateFormat.format(strDateFormat, messageTime)
        }
        return ""
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        val chatItemBinding =
            ChatRvItemBinding.inflate(inflater, parent, false)

        return ViewHolder(chatItemBinding)
    }

    override fun onBindViewHolder(holder: ChatAdapter.ViewHolder, position: Int) {
        holder.bind(list[position], position)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setList(list: List<ChatListResponse.Data>) {

        this.list.clear()

        this.list.addAll(list)

        notifyDataSetChanged()
    }

    interface ChatOnClickListener {

        fun onClick(position: Int)

    }
}