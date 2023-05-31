package com.ennovations.fitcrucoach.fragments

import android.app.ProgressDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.ennovations.fitcrucoach.utility.SharedPreferenceUtil


abstract class BaseFragment : Fragment() {
    lateinit var sharedPreference: SharedPreferenceUtil
    private var progressDialog: ProgressDialog? = null

    // private lateinit var callback: OnBackPressedCallback
    // lateinit var navController: NavController
    var view_: View? = null


    //lateinit var apiViewModel: ApiViewModel
    /**
     * SetBackButtonDispatcher in OnCreate
     */


    internal fun showProgressDialog(@StringRes messageId: Int) {
        if (progressDialog == null) {
            progressDialog = ProgressDialog(context)
            progressDialog?.isIndeterminate = true
            progressDialog?.setCancelable(false)
            progressDialog?.setCanceledOnTouchOutside(false)
            progressDialog?.setOnKeyListener(KeyEventListener())
        }
        progressDialog?.setMessage(getString(messageId))
        progressDialog?.show()
    }

    internal fun hideProgressDialog() {
        if (progressDialog?.isShowing == true) {
            progressDialog?.dismiss()
        }
    }

    inner class KeyEventListener : DialogInterface.OnKeyListener {
        override fun onKey(dialog: DialogInterface?, keyCode: Int, keyEvent: KeyEvent?): Boolean {
            return keyCode == KeyEvent.KEYCODE_BACK
        }
    }

    var hasInitializedRootView = false
    private var rootView: View? = null

    fun getPersistentView(
        inflater: LayoutInflater?,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
        layout: Int
    ): View? {
        if (rootView == null) {
            // Inflate the layout for this fragment
            rootView = inflater?.inflate(layout, container, false)
        } else {
            // Do not inflate the layout again.
            // The returned View of onCreateView will be added into the fragment.
            // However it is not allowed to be added twice even if the parent is same.
            // So we must remove rootView from the existing parent view group
            // (it will be added back).
            (rootView?.parent as? ViewGroup)?.removeView(rootView)
        }

        return rootView
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreference = SharedPreferenceUtil.getInstance(requireActivity())
        //   apiViewModel = ViewModelProvider(this).get(ApiViewModel::class.java)
        // setBackButtonDispatcher()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view_ = view
    }


    fun setToolbarName(tittleName: String?) {
        //   Navigation.findNavController(view_!!).currentDestination?.label=tittleName!!.toString()
    }
    /**
     * Adding BackButtonDispatcher callback to activity
     */
    /* private fun setBackButtonDispatcher() {
         callback = object : OnBackPressedCallback(true) {
             override fun handleOnBackPressed() {
                 onBackPressed()
             }
         }
         requireActivity().onBackPressedDispatcher.addCallback(this, callback)
     }

     */
    /**
     * Override this method into your fragment to handleBackButton
     *//*
    open fun onBackPressed() {
   //     val fm: FragmentManager? = fragmentManager
//        if (fm?.getBackStackEntryCount()!! > 0) {
//            Log.i("MainActivity", "popping backstack")
//            fm?.popBackStack()
//        } else {
//            Log.i("MainActivity", "nothing on backstack, calling super")
//            onBackPressed()
//        }
    }
    //Activity Forward Animation
    fun forwardAnimation() {
        requireActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    }

    //Activity Back Animation
    fun backAnimation() {
        requireActivity().overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }

*/

    interface OnBackPressed {
        fun onBackPressed()
    }
}