package com.ennovations.fitcrucoach.adapter

import android.content.Context
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ennovations.fitcrucoach.R
import com.ennovations.fitcrucoach.databinding.CertificateListBinding
import com.ennovations.fitcrucoach.response.GetCertificateResponse
import com.tooltip.Tooltip


class CertificatesAdapter(val context: Context?, private val listener: CertificatedOnClick) :
    RecyclerView.Adapter<CertificatesAdapter.ViewHolder>() {

    private var list = listOf<GetCertificateResponse.Data>()

    inner class ViewHolder(val binding: CertificateListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: GetCertificateResponse.Data, position: Int) {
            binding.apply {
                Glide.with(context!!).load("${data.image_url}")
                    .placeholder(R.drawable.place_holder)
                    .into(image)
                certificateNameDetail.text = "${data.certificate_name} ${data.description}"

                if (data.status == "Approved") {
                    status.text = "Approved"
                    statusIcon.setImageResource(R.drawable.coach_certified_icon)
                } else if (data.status == "Rejected") {

                    status.text = "Rejected"
                    statusIcon.setImageResource(R.drawable.rejec_icon)

                    val tooltip: Tooltip = Tooltip.Builder(binding.statusIcon)
                        .setText("Click to know the reason")
                        .setCornerRadius(5f)
                        .setTextColor(ContextCompat.getColor(context, R.color.offwhite))
                        .show()

                    val timer: CountDownTimer = object : CountDownTimer(3000, 1000) {
                        override fun onTick(millisUntilFinished: Long) {}
                        override fun onFinish() {
                            tooltip.dismiss() //(or GONE)
                        }
                    }.start()

                    /* val balloon: Balloon = Balloon.Builder(context)
                         .setArrowSize(5)
                         .setArrowOrientation(ArrowOrientation.TOP)
                         .setArrowPositionRules(ArrowPositionRules.ALIGN_ANCHOR)
                         .setArrowPosition(0.1f)
                         .setWidth(WRAP)
                         .setHeight(WRAP)
                         .setTextSize(8f)
                         .setPadding(5)
                         .setMarginLeft(1)
                         .setCornerRadius(6f)
                         .setAlpha(0.9f)
                         .setText("Click to know the reason")
                         .setTextColor(ContextCompat.getColor(context, R.color.offwhite))
                         .setTextIsHtml(true)
                         .setBackgroundColor(ContextCompat.getColor(context, R.color.text_color_dark_grey))
                         .setBalloonAnimation(BalloonAnimation.FADE)
                         .build()

                     balloon.showAlignTop(binding!!.statusIcon)*/

                    statusLl.setOnClickListener {
                        val alert: AlertDialog.Builder =
                            AlertDialog.Builder(context, R.style.AlertDialogTheme)

                        alert.setTitle("Reason for Rejection")
                        alert.setMessage(if (data.certi_reject_reason != null) data.certi_reject_reason else "")

                        alert.setPositiveButton(
                            "Ok"
                        ) { dialog, whichButton ->
                        }

                        alert.show();
                    }
                } else {
                    status.text = "Under Review"
                    statusIcon.setImageResource(R.drawable.ic_watch)
                }


                editBtn.setOnClickListener {
                    val position = adapterPosition
                    listener.onClick(
                        data.id,
                        data.certificate_name,
                        data.description,
                        data.image ?: "",
                        data.image_url ?: ""
                    )
                }
            }
        }
    }

    fun setList(list: List<GetCertificateResponse.Data>) {
        this.list = list
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CertificatesAdapter.ViewHolder {
        return ViewHolder(
            CertificateListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CertificatesAdapter.ViewHolder, position: Int) =
        holder.bind(list[position], position)

    override fun getItemCount(): Int {
        return try {
            list.size
        } catch (e: Exception) {
            0
        }
    }

    interface CertificatedOnClick {

        fun onClick(
            position: Int,
            name: String,
            description: String,
            image: String,
            imageUrl: String,
        )

    }

}
