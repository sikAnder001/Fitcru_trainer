package com.ennovations.fitcrucoach.utility

import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.ennovations.fitcrucoach.R
import com.ennovations.fitcrucoach.databinding.ItemReceiveBinding
import com.ennovations.fitcrucoach.databinding.ItemSentBinding
import java.text.SimpleDateFormat
import java.util.*

class MessageAdapter(
    private val id: String,
    private val onClick: (view: View, imageUrl: String) -> Unit
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var messageList = arrayListOf<Message>()

    private inner class SentViewHolder(val mBinding: ItemSentBinding) :
        RecyclerView.ViewHolder(mBinding.root)

    private inner class ReceiveViewHolder(val mBinding: ItemReceiveBinding) :
        RecyclerView.ViewHolder(mBinding.root)

    override fun getItemViewType(position: Int): Int {
        val message = messageList[position]
        if (message.chat_sender_Id.equals("coach$id", true))
            return R.layout.item_sent
        return R.layout.item_receive
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == R.layout.item_sent)
            return SentViewHolder(
                ItemSentBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        return ReceiveViewHolder(
            ItemReceiveBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val message = messageList[position]

        if (holder is SentViewHolder)
            holder.mBinding.apply {

                if (message.attachment_type.equals(FirebaseUtils.IMAGE, true)) {
                    tvSentMessage.isVisible = false
                    ivSent.isVisible = true
                    GeneralFunctions.loadImage(this.root.context, message.attachment_path, ivSent)
                } else if (message.attachment_type.equals(FirebaseUtils.UPLOADING, true)) {
                    tvSentMessage.isVisible = false
                    ivSent.isVisible = true
                    GeneralFunctions.uploading(this.root.context, ivSent)
                } else {
                    tvSentMessage.isVisible = true
                    ivSent.isVisible = false
                    tvSentMessage.text = message.chat_message
                }

                root.setOnClickListener {
                    if (message.attachment_type.equals(FirebaseUtils.IMAGE, true)) {
                        onClick(root, message.attachment_path)
                    }
                }

                if (message.read_time == "")
                    check.setTint(R.color.unread)
                else
                    check.setTint(R.color.check)

                tvSentTime.text = time2(message.chat_time)
            }

        if (holder is ReceiveViewHolder)
            holder.mBinding.apply {

                if (message.attachment_type.equals(FirebaseUtils.IMAGE, true)) {
                    tvReceivedMessage.isVisible = false
                    ivReceive.isVisible = true
                    GeneralFunctions.loadImage(
                        this.root.context,
                        message.attachment_path,
                        ivReceive
                    )
                } else {
                    tvReceivedMessage.isVisible = true
                    ivReceive.isVisible = false
                    tvReceivedMessage.text = message.chat_message
                }

                root.setOnClickListener {
                    if (message.attachment_type.equals(FirebaseUtils.IMAGE, true)) {
                        onClick(root, message.attachment_path)
                    }
                }

                tvReceiveTime.text = time2(message.chat_time)
            }
    }

    override fun getItemCount(): Int {
        return try {
            messageList.size
        } catch (e: Exception) {
            0
        }
    }

    fun setData(messageList: ArrayList<Message>) {
        this.messageList = messageList
        notifyDataSetChanged()
    }

    fun addData(message: Message) {
        this.messageList.add(message)
        notifyDataSetChanged()
    }

    private fun time2(string: String): String {
        val input = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val dateValue: Date = input.parse(string)
        val messageTime = Calendar.getInstance()
        messageTime.time = dateValue
        val now = Calendar.getInstance()
        val strTimeFormat = "hh:mm a"
        val strDateFormat = "dd/MM/yyyy $strTimeFormat"
        return if (now[Calendar.DATE] === messageTime.get(Calendar.DATE) &&
            now[Calendar.MONTH] === messageTime.get(Calendar.MONTH)
            &&
            now[Calendar.YEAR] === messageTime.get(Calendar.YEAR)
        ) {
            SimpleDateFormat(strTimeFormat).format(dateValue)
        } else if (now[Calendar.DATE] - messageTime.get(Calendar.DATE) === 1
            &&
            now[Calendar.MONTH] === messageTime.get(Calendar.MONTH)
            &&
            now[Calendar.YEAR] === messageTime.get(Calendar.YEAR)
        ) {
            "Yesterday ${SimpleDateFormat(strTimeFormat).format(dateValue)}"
        } else {
            "" + DateFormat.format(strDateFormat, messageTime)
        }
        return ""
    }
}