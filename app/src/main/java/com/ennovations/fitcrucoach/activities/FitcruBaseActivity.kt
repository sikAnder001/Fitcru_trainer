package com.ennovations.fitcrucoach.activities

import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.ennovations.fitcrucoach.utility.SharedPreferenceUtil

abstract class FitcruBaseActivity : AppCompatActivity() {
    // fitcru
    lateinit var sharedPreference: SharedPreferenceUtil

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
        sharedPreference = SharedPreferenceUtil.getInstance(this)
        //     apiViewModel = ViewModelProvider(this).get(ApiViewModel::class.java)
        //backGroundColor()
    }

    val prefs by lazy {
        SharedPreferenceUtil.getInstance(applicationContext)
    }

    //BackGround Color
    /* @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
     private fun backGroundColor() {
         if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
             window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
         }
         window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
         window.statusBarColor = ContextCompat.getColor(this, android.R.color.transparent)
    //    window.setBackgroundDrawableResource(R.drawable.sccc)
     }*/

    abstract fun initViews()
    abstract fun initControl()

    /* private var back_pressed: Long = 0

     override fun onBackPressed() {
         when {
             //supportFragmentManager.backStackEntryCount > 0 -> super.onBackPressed()
             isTaskRoot -> {
                 if (back_pressed + 2000 > System.currentTimeMillis()) {
                     finishAffinity()
                     super.onBackPressed()
                 } else Toast.makeText(
                     baseContext,
                     "Press once again to exit!",
                     Toast.LENGTH_SHORT
                 ).show()
                 back_pressed = System.currentTimeMillis()
             }
             else -> super.onBackPressed()
         }
     }

     //Activity Forward Animation
     fun forwardAnimation() {
         overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
     }

     //Activity Back Animation
     fun backAnimation() {
         overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)

     }*/
}