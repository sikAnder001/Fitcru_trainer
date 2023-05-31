package com.ennovations.fitcrucoach.activities

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.ennovations.fitcrucoach.Network.CustomProgressLoading
import com.ennovations.fitcrucoach.body.LastMessage
import com.ennovations.fitcrucoach.databinding.ActivityChatBinding
import com.ennovations.fitcrucoach.utility.*
import com.ennovations.fitcrucoach.view_model.ChatViewModel
import com.google.firebase.FirebaseApp
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageMetadata
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
//chat
class ChatActivity : AppCompatActivity() {

    private var reference = "chat/one_to_one/%s/%s/messages"
    private var resultUri: Uri? = null
    private lateinit var binding: ActivityChatBinding
    private var user_id = "0"
    private var coach_id = "0"
    var myRef: DatabaseReference? = null
    private lateinit var messageAdapter: MessageAdapter
    private val messageList = arrayListOf<Message>()
    private val firebaseDatabase: FirebaseDatabase
        get() {
            return Firebase.database
        }
    private val firebaseStorage: FirebaseStorage
        get() {
            return FirebaseStorage.getInstance()
        }
    private val viewModel by viewModels<ChatViewModel>()
    private var message = ""
    private val loading by lazy { CustomProgressLoading(applicationContext) }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        binding = ActivityChatBinding.inflate(layoutInflater)

        setContentView(binding.root)

        user_id = intent.getIntExtra("user_id", 0).toString()

        coach_id = intent.getIntExtra("coach_id", 0).toString()

        messageAdapter = MessageAdapter(coach_id) { view, imageUrl ->
            val intent = Intent(this, ViewImageActivity::class.java).apply {
                putExtra(
                    FirebaseUtils.IMAGE,
                    imageUrl
                )
            }
            val transitionName = "transition_name"
            val activityOption =
                ActivityOptionsCompat.makeSceneTransitionAnimation(this, view, transitionName)
            ActivityCompat.startActivity(this, intent, activityOption.toBundle())
        }

        if (user_id == "0" || coach_id == "0") return

        FirebaseApp.initializeApp(applicationContext)

        myRef = firebaseDatabase.getReference(String.format(reference, coach_id, user_id))!!

        binding.btnSend.setOnClickListener {

            val message = binding.etMessageBox.text.toString()

            if (message.isEmpty()) {
                binding.etMessageBox.error = "Please type message."
                return@setOnClickListener
            }

            val time = dateTime()

            this@ChatActivity.message = message

            val messageObj = Message(
                "", "", "", FirebaseUtils.TEXT, message,
                "coach$coach_id", time, ""
            )

            val randomKey = myRef?.push()?.key.toString()

            binding.etMessageBox.text.clear()

            myRef?.child(randomKey)?.setValue(messageObj)?.addOnCompleteListener {
                if (it.isSuccessful) {
                    // Send Notification
                } else
                    it.exception?.message?.let { it1 -> showToast(it1) }
            }
        }

        binding.tvName.text = intent.getStringExtra("user_name")

        GeneralFunctions.loadImage(
            this, intent.getStringExtra("user_profile").toString(),
            binding.ivProfilePic
        )

        binding.rvChats.apply {
            layoutManager = LinearLayoutManager(this@ChatActivity)
            adapter = messageAdapter
        }

        messageAdapter.setData(messageList)

        myRef?.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {

                messageList.clear()

                for (children in snapshot.children) {
                    val message = children.getValue(Message::class.java)
                    if (message?.chat_sender_Id.equals(
                            "client$user_id",
                            true
                        ) && message?.read_time == ""
                    ) {
                        message?.read_time = dateTime()
                        myRef?.child(children.key.toString())?.setValue(message)
                    }
                    message?.let { messageList.add(it) }
                }

                messageAdapter.setData(messageList)

                if (messageList.isNotEmpty())

                    binding.rvChats.scrollToPosition(messageList.size - 1)

            }

            override fun onCancelled(error: DatabaseError) {

            }
        })

        binding.ivBack.setOnClickListener { onBackPressed() }

        val pickImageContract =
            registerForActivityResult(ActivityResultContracts.GetContent()) { imageUri ->
                imageUri?.let {
                    showToast("Sending image...")
                    sendImageMessage(imageUri)
                } ?: showToast("an ERROR occurred.")
            }

        binding.ivAttachment.setOnClickListener {
            pickImageContract.launch("image/*")
        }

        val takePhotoContract =
            registerForActivityResult(ActivityResultContracts.TakePicture()) { status ->
                if (status) {
                    resultUri?.let {
                        showToast("Sending image...")
                        sendImageMessage(it)
                    } ?: showToast("an ERROR occurred.")
                } else
                    showToast("an ERROR occurred.")
            }

        val permissionContract =
            registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { resultMap ->
                resultMap.entries.forEach { entry ->
                    if (entry.value) {
                        resultUri = GeneralFunctions.createImageURI(this)
                        resultUri?.let { takePhotoContract.launch(resultUri) }
                    }
                }
            }

        binding.ivCamera.setOnClickListener {
            if (Build.VERSION.SDK_INT >= 29) {
                resultUri = GeneralFunctions.createImageURI(this)
                resultUri?.let { takePhotoContract.launch(resultUri) }
            } else
                permissionContract.launch(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE))
        }
    }

    private fun sendImageMessage(imageUri: Uri) {
        val name = dateTime()
        message = "${imageUri.pathSegments.last()}.jpeg"
        val msg = Message(
            "", "", "", FirebaseUtils.UPLOADING, "",
            "coach$coach_id", name, ""
        )
        messageList.add(msg)
        messageAdapter.notifyDataSetChanged()
        binding.rvChats.scrollToPosition(messageList.size - 1)
        val reference =
            firebaseStorage.reference.child("chat/one_to_one/$coach_id$user_id").child("$name.jpeg")
        reference.putFile(imageUri, StorageMetadata.Builder().setContentType("image/jpeg").build())
            .addOnCompleteListener { uploadedImageResponse ->
                if (uploadedImageResponse.isSuccessful) {
                    reference.downloadUrl.addOnCompleteListener { uploadedImageDownloadUrlResponse ->
                        if (uploadedImageDownloadUrlResponse.isSuccessful) {
                            val message = Message(
                                "",
                                uploadedImageDownloadUrlResponse.result.toString(),
                                message,
                                FirebaseUtils.IMAGE,
                                "",
                                "coach$coach_id",
                                name,
                                ""
                            )
                            myRef?.child(myRef?.push()?.key.toString())?.setValue(message)
                                ?.addOnCompleteListener {
                                    if (it.isSuccessful) {
                                        // Send Notification
                                    } else
                                        it.exception?.message?.let { it1 -> showToast(it1) }
                                }
                        } else {
                            uploadedImageDownloadUrlResponse.exception?.message?.let { showToast(it) }
                        }
                    }
                } else
                    uploadedImageResponse.exception?.message?.let { showToast(it) }
            }
    }

    override fun onBackPressed() {
        loading.showProgress()

        if (message.isNotEmpty()) {
            viewModel.lastMessage(
                LastMessage(
                    user_id.toInt(),
                    coach_id.toInt(),
                    message,
                    coach_id.toInt(),
                    user_id.toInt()
                )
            )
            viewModel.lastMessageS.observe(this) {
                loading.hideProgress()
                super.onBackPressed()
            }
        } else {
            loading.hideProgress()
            super.onBackPressed()
        }
    }

    private fun dateTime(): String {
        val df = SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z", Locale.US)
        return df.format(Date())
    }
}