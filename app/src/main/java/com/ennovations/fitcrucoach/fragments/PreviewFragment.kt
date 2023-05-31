package com.ennovations.fitcrucoach.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.ennovations.fitcrucoach.R
import com.ennovations.fitcrucoach.quickbox.utils.getDrawable

private const val PREVIEW_IMAGE = "preview_image"

class PreviewFragment : Fragment() {

    companion object {
        fun newInstance(imageResourceId: Int): Fragment {
            val previewFragment = PreviewFragment()
            val bundle = Bundle()
            bundle.putInt(PREVIEW_IMAGE, imageResourceId)
            previewFragment.arguments = bundle
            return previewFragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_item_screen_share, container, false)

        val ivPreview: ImageView = view.findViewById(R.id.image_preview)
        val imageDrawable = arguments?.getInt(PREVIEW_IMAGE)

        imageDrawable?.let {
            ivPreview.setImageDrawable(getDrawable(it))
        }
        return view
    }
}