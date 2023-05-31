package com.ennovations.fitcrucoach.activities

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.ennovations.fitcrucoach.R
import com.ennovations.fitcrucoach.databinding.ActivityMainBinding
import com.ennovations.fitcrucoach.utility.ACCESS_TOKEN
import com.ennovations.fitcrucoach.utility.DEVICE_TOKEN
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.orhanobut.hawk.Hawk
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    //main


    private lateinit var mainBinding: ActivityMainBinding

    private lateinit var navController: NavController

    private var deviceToken = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        setBottomNavigation()

        mainBinding.traineeBottomNavigation.itemIconTintList = null
    }


    private fun setBottomNavigation() {

        val navController = findNavController(R.id.trainee_fragment_container)

        if (Hawk.contains(ACCESS_TOKEN)) {


            navController.navigate(R.id.action_loginFragment_to_CWorkoutFragment2)
            Hawk.put(DEVICE_TOKEN, deviceToken)


        } else {

            findViewById<BottomNavigationView>(R.id.trainee_bottom_navigation).setupWithNavController(
                navController
            )

        }

        findViewById<BottomNavigationView>(R.id.trainee_bottom_navigation).setupWithNavController(
            navController
        )



        navController.addOnDestinationChangedListener { _, destination, _ ->

            when (destination.id) {

                R.id.CWorkoutFragment2 -> showBottomNav()

                R.id.TChatFragment -> showBottomNav()

                /*R.id.TProgramFragment2 -> showBottomNav()*/

                R.id.TAppointmentFragment -> showBottomNav()

                else -> hideBottomNav()
            }


        }


    }

    private fun showBottomNav() {
        mainBinding.traineeBottomNavigation.visibility = View.VISIBLE
    }

    private fun hideBottomNav() {
        mainBinding.traineeBottomNavigation.visibility = View.GONE
    }
}