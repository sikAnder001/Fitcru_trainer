package com.ennovations.fitcrucoach.utility

import android.app.DownloadManager
import android.content.ContentValues
import android.content.Context
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.webkit.CookieManager
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.ennovations.fitcrucoach.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

object GeneralFunctions {

    fun loadImage(
        context: Context,
        imageUrl: String,
        imageView: ImageView,
        placeHolderResourceId: Int = R.drawable.place_holder
    ) {
//        imageView.scaleType = ImageView.ScaleType.CENTER_INSIDE
        Glide.with(context).load(imageUrl)
            .thumbnail(Glide.with(context).load(R.raw.loader))
            .listener(object : RequestListener<Drawable?> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable?>?,
                    isFirstResource: Boolean
                ): Boolean {
                    CoroutineScope(Dispatchers.IO).launch {
                        withContext(Dispatchers.Main) {
                            Glide.with(context).load(imageUrl).placeholder(placeHolderResourceId)
                                .into(imageView)
                        }
                    }
//                    imageView.scaleType = ImageView.ScaleType.CENTER_CROP
                    return true
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable?>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
//                    imageView.scaleType = ImageView.ScaleType.CENTER_CROP
                    return false
                }
            })
            .into(imageView)
    }


    fun uploading(
        context: Context,
        imageView: ImageView,
    ) {
        imageView.scaleType = ImageView.ScaleType.FIT_CENTER
        Glide.with(context).load("")
            .thumbnail(Glide.with(context).load(R.raw.loader2))
            .into(imageView)
    }

    fun createImageURI(context: Context): Uri? {
        val imageCollection = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
            MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
        else
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI

        val imageName = System.currentTimeMillis()

        val contentValues = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, "$imageName")
            put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
        }

        return context.contentResolver.insert(imageCollection, contentValues)
    }

    fun downloadImage(context: Context, imageUrl: String) {
        val cookie = CookieManager.getInstance().getCookie(imageUrl)
        val downloadRequest = DownloadManager.Request(Uri.parse(imageUrl)).apply {
            setTitle("Image")
            setDescription("Image")
            addRequestHeader("cookie", cookie)
            setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            setDestinationInExternalPublicDir(
                Environment.DIRECTORY_DOWNLOADS,
                "${System.currentTimeMillis()}.jpg"
            )
        }

        val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        downloadManager.enqueue(downloadRequest)

        (context as AppCompatActivity).showToast("Downloading started.")
    }
}
